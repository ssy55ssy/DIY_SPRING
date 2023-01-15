package beans.factory.config;

import beans.factory.DisposableBean;
import cn.hutool.core.bean.BeanException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DefaultSingletonBeanRegistry implements  SingletonBeanRegistry {

    protected static final Object NULL_OBJECT = new Object();

    private Map<String,Object> singletonObjects = new HashMap<>();

    private Map<String,DisposableBean> disposableBeanMap = new HashMap<>();

    @Override
    public Object getSingleton(String beanName){
        return singletonObjects.get(beanName);
    }

    public void registerSingleton(String beanName, Object singletonObject){
        singletonObjects.put(beanName,singletonObject);
    }

    // this function can be used by its subClass, such as:AbstractBeanFactory & DefaultListableBeanFactory
    protected  void addSingleton(String beanName, Object singletonObject){
        singletonObjects.put(beanName,singletonObject);
    }

    public void registerDisposableBean(String beanName, DisposableBean disposableBean){
        disposableBeanMap.put(beanName,disposableBean);
    }

    public void destroySingletons() {
        Set<String> disposableBeanNameSet = disposableBeanMap.keySet();
        Object[] disposableBeanNameArray = disposableBeanNameSet.toArray();
        for(int i = disposableBeanNameArray.length - 1; i >= 0; i-- ){
            String beanName = (String)disposableBeanNameArray[i];
            DisposableBean disposableBean = disposableBeanMap.remove(beanName);
            try{
                disposableBean.destroy();
            }catch(Exception e){
                throw new BeanException("destruction of bean: " + beanName + " failed",e);
            }
        }
    }

}
