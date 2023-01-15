package aop.frameWork;

import aop.AdvisedSupport;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/*
use jdk to get proxy class
need to implement AopProxy and InvocationHandler, so that we can deal with getProxy() and invoke() sepreatly
 */
public class JdkDynamicAopProxy implements AopProxy,InvocationHandler {

    private final AdvisedSupport advisedSupport;

    public JdkDynamicAopProxy(AdvisedSupport advisedSupport){
        this.advisedSupport = advisedSupport;
    }

    public Object getProxy(){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),advisedSupport.getTargetSource().getTargetClass(),this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
        // if the methodMatcher in advisedSupport can match the method, use the methodInterceptor defind by user
        if(advisedSupport.getMethodMatcher().matches(method,advisedSupport.getTargetSource().getTarget().getClass())){
            MethodInterceptor methodInterceptor = advisedSupport.getMethodInterceptor();
            //  ReflectiveMethodInvocation encapsulate the needed information to invoke the method
            return methodInterceptor.invoke(new ReflectiveMethodInvocation(advisedSupport.getTargetSource().getTarget(),method,args));
        }
        return method.invoke(advisedSupport.getTargetSource().getTarget(),args);
    }

}
