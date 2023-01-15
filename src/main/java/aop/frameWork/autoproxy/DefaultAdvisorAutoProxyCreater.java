package aop.frameWork.autoproxy;

import aop.*;
import aop.aspectJ.AspectJExpressionPointcutAdvisor;
import aop.frameWork.ProxyFactory;
import beans.BeansException;
import beans.PropertyValues;
import beans.factory.BeanFactory;
import beans.factory.BeanFactoryAware;
import beans.factory.config.InstantiationAwareBeanPostProcessor;
import beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/*
implement the beanFactoryAware since we need to use the beanFactory to achieve AspectJExpressionPointcutAdvisor class
 */
public class DefaultAdvisorAutoProxyCreater implements InstantiationAwareBeanPostProcessor,BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    private final Set<Object> earlyProxyReferences = Collections.synchronizedSet(new HashSet<Object>());

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException{
        return pvs;
    }

    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException{
        return null;
    }

    public Object postProcessAfterInstantiation(Object bean, String beanName) throws BeansException{
        return bean;
    }

    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass) || PointCut.class.isAssignableFrom(beanClass) || Advisor.class.isAssignableFrom(beanClass);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (isInfrastructureClass(bean.getClass())) return bean;

        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfTypes(AspectJExpressionPointcutAdvisor.class).values();

        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            ClassFilter classFilter = advisor.getPointCut().getClassFilter();
            // filter class
            if (!classFilter.matches(bean.getClass())) continue;

            AdvisedSupport advisedSupport = new AdvisedSupport();

            TargetSource targetSource = new TargetSource(bean);
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointCut().getMethocMatcher());
            advisedSupport.setProxyTargetClass(false);

            // return proxy object
            return new ProxyFactory(advisedSupport).getProxy();
        }

        return bean;
    }

}
