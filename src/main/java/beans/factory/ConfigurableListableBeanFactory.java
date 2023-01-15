package beans.factory;

import beans.BeansException;
import beans.factory.config.AutowireCapableBeanFactory;
import beans.factory.config.BeanDefinition;
import beans.factory.config.ConfigurableBeanFactory;
import utils.StringValueResolver;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    BeanDefinition getBeanDefinition(String name) throws BeansException;

    void preInstantionSingleton() throws BeansException;



}
