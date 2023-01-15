package beans.factory.config;

import beans.BeansException;

/*
allow user modify the bean object after its instanition
 */
public interface BeanPostProcessor {

    // this will be used before bean object's initialization
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    // this will be used after bean object's initialization
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

}
