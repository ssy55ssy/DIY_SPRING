package beans.factory;

import beans.BeansException;

import java.util.Map;

public interface ListableBeanFactory extends BeanFactory {

    <T> Map<String,T> getBeansOfTypes(Class<T> type) throws BeansException;

    String[] getBeanDefinitionNames();

}
