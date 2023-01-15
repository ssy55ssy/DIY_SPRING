package context.support;

import beans.BeansException;
import beans.factory.config.BeanPostProcessor;
import context.ApplicationContext;
import context.ApplicationContextAware;

// since we can't achieve application context during the process of creating bean, so we need to add this processor int the refresh method
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException{
        if(bean instanceof ApplicationContextAware)
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException{
        return bean;
    }

}
