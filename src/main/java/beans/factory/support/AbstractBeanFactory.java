package beans.factory.support;

import beans.BeansException;
import beans.factory.BeanFactory;
import beans.factory.FactoryBean;
import beans.factory.config.*;
import utils.ClassUtils;
import utils.StringValueResolver;

import java.util.ArrayList;
import java.util.List;

/*
achieve the function of the registration of singleton class.
the main function in this class is getBean, it enables users to achieve bean object and create bean object if it can't be find.
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<>();

    @Override
    public Object getBean(String beanName) throws BeansException{
        return doGetBean(beanName,null);
    }

    @Override
    public Object getBean(String beanName, Object... args) throws BeansException{
        return doGetBean(beanName,args);
    }

    @Override
    public <T> T getBean(String beanName,Class<T> requiredType){
        return (T) getBean(beanName);
    }

    protected <T> T doGetBean(String beanName, Object[] args){
        Object bean = getSingleton(beanName);
        if(bean != null){
            // if it's a factory bean, then use factoryBean.getObject
            return (T)getObjectForBeanInstance(beanName,bean);
        }
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        return (T)createBean(beanName,beanDefinition,args);
    }

    public void addEmbeddedValueResolver(StringValueResolver stringValueResolver){
        embeddedValueResolvers.remove(stringValueResolver);
        embeddedValueResolvers.add(stringValueResolver);
    }

    public String resolveEmbeddedValue(String value){
        String result = value;
        for(StringValueResolver stringValueResolver : embeddedValueResolvers){
            result = stringValueResolver.resolveStringValues(result);
        }
        return result;
    }

    private Object getObjectForBeanInstance(String beanName,Object bean){
        if(!(bean instanceof FactoryBean))
            return bean;

        Object object = getCachedObjectForFactoryBean(beanName);

        if (object == null) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) bean;
            object = getObjectFromFactoryBean(factoryBean, beanName);
        }

        return object;
    }

    // achieved by DefaultListableBeanFactory
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    //achieved by AbstractAutowireCapableBeanFactory
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors(){
        return this.beanPostProcessors;
    }

    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

}
