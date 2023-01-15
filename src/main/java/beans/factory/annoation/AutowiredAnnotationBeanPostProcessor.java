package beans.factory.annoation;

import beans.BeansException;
import beans.PropertyValues;
import beans.factory.BeanFactory;
import beans.factory.BeanFactoryAware;
import beans.factory.ConfigurableListableBeanFactory;
import beans.factory.config.InstantiationAwareBeanPostProcessor;
import cn.hutool.core.bean.BeanUtil;
import utils.ClassUtils;

import java.lang.reflect.Field;

public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor,BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;

    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException{
        Class<?> clazz = bean.getClass();
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
        Field[] fields = clazz.getDeclaredFields();
        // deal with @Value
        for(Field field:fields){
            Value value = field.getAnnotation(Value.class);
            if(value != null){
                String strValue = value.value();
                // resolve the placeHolder
                strValue = beanFactory.resolveEmbeddedValue(strValue);
                BeanUtil.setFieldValue(bean,field.getName(),strValue);
            }
        }
        // deal with @AutoWired & @Qualifier
        for(Field field:fields){
            Autowired autowired = field.getAnnotation(Autowired.class);
            if(autowired != null){
                Class<?> fieldType = field.getType();
                Qualifier qualifier = field.getAnnotation(Qualifier.class);
                Object targetBean = null;
                if(qualifier != null){
                    // if @Qualifier has value, get the bean object in qualifier
                    String targetBeanName = qualifier.value();
                    targetBean = beanFactory.getBean(targetBeanName);
                }else{
                    targetBean = beanFactory.getBean(fieldType);
                }
                BeanUtil.setFieldValue(bean,field.getName(),targetBean);
            }
        }
        return pvs;
    }

    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException{
        return null;
    }

    public Object postProcessAfterInstantiation(Object bean, String beanName) throws BeansException{
        return bean;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException{
        this.beanFactory = (ConfigurableListableBeanFactory)beanFactory;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException{
        return null;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException{
        return null;
    }
}
