package beans.factory.config;

import beans.BeansException;
import beans.PropertyValues;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException;

    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    public Object postProcessAfterInstantiation(Object bean, String beanName) throws BeansException;

}
