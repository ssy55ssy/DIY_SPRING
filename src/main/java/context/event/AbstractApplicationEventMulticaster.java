package context.event;

import beans.BeansException;
import beans.factory.BeanFactory;
import beans.factory.BeanFactoryAware;
import context.ApplicationEvent;
import context.ApplicationListener;
import utils.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster,BeanFactoryAware{

    public final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();

    private BeanFactory beanFactory;

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
    }
    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove(listener);
    }

    @Override
    public final void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    // select listeners which support the event
    public Collection<ApplicationListener> getApplicationListeners(ApplicationEvent applicationEvent){
        LinkedList<ApplicationListener> applicationListenersRes = new LinkedList<>();
        for(ApplicationListener<ApplicationEvent> applicationEventListener : applicationListeners){
            if(supportsEvent(applicationEventListener,applicationEvent))
                applicationListenersRes.add(applicationEventListener);
        }
        return applicationListenersRes;
    }

    // judge whether listeners support this event
    protected boolean supportsEvent(ApplicationListener<ApplicationEvent> applicationEventListener, ApplicationEvent applicationEvent){
        Class<? extends ApplicationListener> listenerClass = applicationEventListener.getClass();
        // if the target is a cglibproxy, we need to achieve its superclass
        Class<?> targetClass = ClassUtils.isCglibProxyClass(listenerClass) ? listenerClass.getSuperclass() : listenerClass;
        Type genericInterface = targetClass.getGenericInterfaces()[0];
        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();
        Class<?> eventClassName;
        try {
            eventClassName = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("wrong event class name: " + className);
        }
        return eventClassName.isAssignableFrom(applicationEvent.getClass());

    }

}
