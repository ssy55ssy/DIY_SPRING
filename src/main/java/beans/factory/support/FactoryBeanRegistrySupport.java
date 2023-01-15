package beans.factory.support;

import beans.BeansException;
import beans.factory.FactoryBean;
import beans.factory.config.DefaultSingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
this class mainly deal with the registion of factorybean object
 */
public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    // cache of singleton objects created by factory bean
    private final Map<String,Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    protected Object getCachedObjectForFactoryBean(String beanName){
        Object target = factoryBeanObjectCache.get(beanName);
        return target == NULL_OBJECT ? null : target;
    }

    protected Object getObjectFromFactoryBean(FactoryBean bean, String beanName){
        if(bean.isSingleton()){
            Object target = getCachedObjectForFactoryBean(beanName);
            if(target == null){
                target = doGetObjectFromFactoryBean(bean,beanName);
                factoryBeanObjectCache.put(beanName,target == null ? NULL_OBJECT : target);
            }
            return (target != NULL_OBJECT ? target : null);
        }else{
            return doGetObjectFromFactoryBean(bean,beanName);
        }
    }

    private Object doGetObjectFromFactoryBean(final FactoryBean bean, final String beanName){
        try{
            return bean.getObject();
        }catch(Exception e){
            throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
        }
    }
}
