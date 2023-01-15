package beans.factory.config;

import beans.BeansException;
import beans.factory.ConfigurableListableBeanFactory;


/*
allow users edit the bean definiton before the bean object instanition
 */
public interface BeanFactoryPostProcessor {

    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;

}
