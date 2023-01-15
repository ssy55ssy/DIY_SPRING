package context.support;

import beans.BeansException;
import beans.factory.ConfigurableListableBeanFactory;
import beans.factory.config.BeanDefinition;
import beans.factory.config.BeanFactoryPostProcessor;
import beans.factory.config.BeanPostProcessor;
import beans.factory.config.ConfigurableBeanFactory;
import context.ApplicationEvent;
import context.ApplicationListener;
import context.ConfigurableApplicationContext;
import context.event.ApplicationEventMulticaster;
import context.event.ContextClosedEvent;
import context.event.ContextRefreshedEvent;
import context.event.SimpleApplicationEventMulticaster;
import core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void refresh() throws BeansException{

        // create the bean factory and load the bean definition
        refreshBeanFactory();

        // achieve the bean factory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // add  ApplicationContextAwareProcessor, cause we can't achieve the application context information during the process of bean creation
        // so the bean object which use the interface of ApplicationContextAware can aware it
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        // execute the bean post processor before the bean instanition
        invokeBeanFactoryPostProcessors(beanFactory);

        // beanPostProcessor should be registered before other bean object's instanition
        registerBeanPostProcessors(beanFactory);

        // initialize the event multicaster
        initApplicationEventMulticaster();

        // register event listeners
        registerListener();

        // preInstantion other singleton bean object
        beanFactory.preInstantionSingleton();

        // publish the refresh event
        finishRefresh();

    }

    private void initApplicationEventMulticaster(){
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME,beanFactory);
    }

    private void registerListener(){
        Collection<ApplicationListener> applicationListeners = getBeansOfTypes(ApplicationListener.class).values();
        for(ApplicationListener applicationListener : applicationListeners){
            applicationEventMulticaster.addApplicationListener(applicationListener);
        }
    }

    private void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    public void publishEvent(ApplicationEvent applicationEvent){
        applicationEventMulticaster.multicastEvent(applicationEvent);
    }

    @Override
    public void registerShutdownHook(){
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close(){
        getBeanFactory().destroySingletons();
        publishEvent(new ContextClosedEvent(this));
    }

    protected abstract void refreshBeanFactory() throws BeansException;

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory){
        Map<String,BeanFactoryPostProcessor> beanFactoryPostProcessorMap =  beanFactory.getBeansOfTypes(BeanFactoryPostProcessor.class);
        for(BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values())
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory){
        Map<String,BeanPostProcessor> beanPostProcessorMap =  beanFactory.getBeansOfTypes(BeanPostProcessor.class);
        for(BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()){
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    @Override
    public Object getBean(String beanName) throws BeansException{
        return getBeanFactory().getBean(beanName);
    }

    @Override
    public Object getBean(String beanName, Object... args) throws BeansException{
        return getBeanFactory().getBean(beanName, args);
    }

    @Override
    public <T> T getBean(String beanName,Class<T> requiredType){
        return getBeanFactory().getBean(beanName,requiredType);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(requiredType);
    }

    public <T> Map<String,T> getBeansOfTypes(Class<T> type) throws BeansException{
        return getBeanFactory().getBeansOfTypes(type);
    }

    public String[] getBeanDefinitionNames(){
        return getBeanFactory().getBeanDefinitionNames();
    }

}
