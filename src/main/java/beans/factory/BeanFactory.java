package beans.factory;

import beans.BeansException;

public interface BeanFactory {
    Object getBean(String beanName) throws BeansException;

    // this method require user input some construction args, through this the objects can be instanced with particular constructor
    Object getBean(String beanName, Object... args) throws BeansException;

    <T> T getBean(String beanName,Class<T> requiredType);

    <T> T getBean(Class<T> requiredType) throws BeansException;
}
