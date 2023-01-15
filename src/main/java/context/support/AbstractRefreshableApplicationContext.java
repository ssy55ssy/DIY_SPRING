package context.support;

import beans.BeansException;
import beans.factory.ConfigurableListableBeanFactory;
import beans.factory.support.DefaultListableBeanFactory;

public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    private DefaultListableBeanFactory beanFactory;

    // achieve the bean factory instanition of DefaultListableBeanFactory & load bean defintion
    protected void refreshBeanFactory() throws BeansException{
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    private DefaultListableBeanFactory createBeanFactory(){
        return new DefaultListableBeanFactory();
    }

    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

    protected ConfigurableListableBeanFactory getBeanFactory(){
        return beanFactory;
    }

}
