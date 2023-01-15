package beans.factory.support;

import beans.BeansException;
import beans.PropertyValue;
import beans.PropertyValues;
import beans.factory.*;
import beans.factory.config.*;
import cn.hutool.core.bean.BeanException;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

/*
this class achieve the creation of bean object.
 */
public abstract class AbstractAutowireCapableBeanFactory extends  AbstractBeanFactory implements AutowireCapableBeanFactory{

    // use cglib strategy to create objects
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException{
        Object bean = null;
        try{
            bean = resolveBeforeInstantiation(beanName, beanDefinition);
            if(bean != null)
                return bean;
            // use the cglib strategy to create bean instance
            bean = createBeanInstance(beanDefinition,beanName,args);
            // allow beanPostProcessor edit bean's property value before inject property value
            applyBeanPostProcessorsBeforeApplyingPropertyValues(beanName,bean,beanDefinition);
            // inject property value for the class
            applyPropertyValues(beanName,bean,beanDefinition);
            //execute bean's initial method and beanPostProcessor
            bean = initializeBean(beanName,bean,beanDefinition);
        }catch(Exception e){
            throw new BeansException("instantiation of bean failed",e);
        }
        // register the bean object if it has disposable method
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);
        // if this bean object is s singleton, add this bean object into the map which is defined in DefaultSingletonBeanRegistry.
        if(beanDefinition.isSingleton())
            addSingleton(beanName,bean);
        return bean;
    }

    protected void applyBeanPostProcessorsBeforeApplyingPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition){
        for(BeanPostProcessor postProcessor : getBeanPostProcessors()){

            if(postProcessor instanceof InstantiationAwareBeanPostProcessor){
                PropertyValues propertyValues = ((InstantiationAwareBeanPostProcessor)postProcessor).postProcessPropertyValues(beanDefinition.getPropertyValues(),bean,beanName);
                if(propertyValues != null){
                    for(PropertyValue propertyValue : propertyValues.getPropertyValues()){
                        beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
                    }
                }
            }
        }
    }

    protected Object resolveBeforeInstantiation(String beanName,BeanDefinition beanDefinition){
        Object target = applyBeanPostProcessorsBeforeInstantiation(beanDefinition.getBeanClass(),beanName);
        if(target != null){
            target = applyBeanPostProcessorsAfterInitialization(target,beanName);
            return target;
        }
        return null;
    }

    protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> clazz, String beanName){
        List<BeanPostProcessor> beanPostProcessors = getBeanPostProcessors();
        for(BeanPostProcessor beanPostProcessor:beanPostProcessors){
            if(beanPostProcessor instanceof InstantiationAwareBeanPostProcessor){
                Object result = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessBeforeInstantiation(clazz,beanName);
                if(result != null){
                    return result;
                }
            }
        }
        return null;
    }

    protected void registerDisposableBeanIfNecessary(String beanName,Object bean,BeanDefinition beanDefinition){
        // only singleton object will need to register disposable method
        if(!beanDefinition.isSingleton())
            return;
        if(StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName()) || bean instanceof DisposableBean){
            registerDisposableBean(beanName,new DisposableBeanAdapter(bean,beanName,beanDefinition));
        }
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) {
        Class clazz = beanDefinition.getBeanClass();
        Constructor usedConstructor = null;
        Constructor[] availableConstructors = clazz.getDeclaredConstructors();
        for(Constructor constructor : availableConstructors){
            // here is different from the original spring code. here we just compare the number of arguments,it may cause some problem.
            if(args != null && args.length == constructor.getParameterCount()){
                usedConstructor = constructor;
            }
        }
        return getInstantiationStrategy().instantiate(beanDefinition,beanName,usedConstructor,args);
    }

    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        for(PropertyValue propertyValue : propertyValues.getPropertyValues()){
            String name = propertyValue.getName();
            Object value = propertyValue.getValue();
            // if value is a reference, get the instanation of the reference.
            if(value instanceof BeanReference){
                BeanReference beanReference = (BeanReference) value;
                value = getBean(beanReference.getBeanName());
            }
            BeanUtil.setFieldValue(bean,name,value);
        }
    }

    public InstantiationStrategy getInstantiationStrategy(){return instantiationStrategy;}

    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition){
        // invoke aware methods
        if (bean instanceof Aware) {
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
            if (bean instanceof BeanClassLoaderAware){
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
            }
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
        }
        // execute beanPostProcessor postProcessBeforeInitialization method
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean,beanName);
        // execute the bean object's initial method
        try{
            invokeInitMethods(beanName,wrappedBean,beanDefinition);
        }catch(Exception e){
            throw new BeanException("initializing of bean: " + beanName + " failed",e);
        }
        // execute beanPostProcessor postProcessAfterInitialization method
        wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean,beanName);
        return wrappedBean;
    }

    private void invokeInitMethods(String beanName, Object wrappedBean, BeanDefinition beanDefinition) throws Exception {
        // judge whether the bean object use the InitializingBean interface, if true, execute the afterPropertiesSet method
        try{
            if(wrappedBean instanceof InitializingBean)
                ((InitializingBean) wrappedBean).afterPropertiesSet();
        }catch(Exception e){
            throw new BeanException("initializing of bean:" + beanName + "failed",e);
        }

        String methodName = beanDefinition.getInitMethodName();

        // judge whether the initial mode exists in the bean definition or not
        if(StrUtil.isNotEmpty(methodName)){
            Method initializingMethod = beanDefinition.getBeanClass().getMethod(methodName);
            if(null == initializingMethod)
                throw new BeansException("Could not find an init method named '" +
                        methodName + "' on bean with name '" + beanName + "'");
            initializingMethod.invoke(wrappedBean);
        }


    }

    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException{
        Object result = existingBean;
        for(BeanPostProcessor beanPostProcessor : getBeanPostProcessors()){
            Object curr = beanPostProcessor.postProcessBeforeInitialization(result,beanName);
            if(curr == null)
                return result;
            result = curr;
        }
        return result;
    }

    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException{
        Object result = existingBean;
        for(BeanPostProcessor beanPostProcessor : getBeanPostProcessors()){
            Object curr = beanPostProcessor.postProcessAfterInitialization(result,beanName);
            if(curr == null)
                return result;
            result = curr;
        }
        return result;
    }

}
