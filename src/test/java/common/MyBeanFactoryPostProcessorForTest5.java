package common;

import beans.BeansException;
import beans.PropertyValue;
import beans.PropertyValues;
import beans.factory.ConfigurableListableBeanFactory;
import beans.factory.config.BeanDefinition;
import beans.factory.config.BeanFactoryPostProcessor;

public class MyBeanFactoryPostProcessorForTest5 implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("company", "change to:google"));
    }
}
