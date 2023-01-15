package beans.factory;

import beans.BeansException;

public interface BeanFactoryAware extends  Aware {
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
