package aop.frameWork;

import aop.AdvisedSupport;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class Cglib2AopProxy implements AopProxy {

    private final AdvisedSupport advise;

    public Cglib2AopProxy(AdvisedSupport advisedSupport){
        this.advise = advisedSupport;
    }

    public Object getProxy(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(advise.getTargetSource().getTarget().getClass());
        enhancer.setInterfaces(advise.getTargetSource().getTargetClass());
        enhancer.setCallback(new DynamicAdvisedInterceptor(advise));
        return enhancer.create();
    }

    private static class DynamicAdvisedInterceptor implements MethodInterceptor{

        private final AdvisedSupport advisedSupport;

        public DynamicAdvisedInterceptor(AdvisedSupport advisedSupport){
            this.advisedSupport = advisedSupport;
        }

        public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable{
            CglibMethodInvocation cglibMethodInvocation = new CglibMethodInvocation(advisedSupport.getTargetSource().getTarget(),method,args,methodProxy);
            if(advisedSupport.getMethodMatcher().matches(method,advisedSupport.getTargetSource().getTarget().getClass())){
                return advisedSupport.getMethodInterceptor().invoke(cglibMethodInvocation);
            }
            return cglibMethodInvocation.proceed();
        }
    }

    private static class CglibMethodInvocation extends ReflectiveMethodInvocation {

        private final MethodProxy methodProxy;

        public CglibMethodInvocation(Object object, Method method, Object[] args, MethodProxy methodProxy){
            super(object,method,args);
            this.methodProxy = methodProxy;
        }

        @Override
        public Object proceed() throws Throwable {
            return this.methodProxy.invoke(this.target, this.arguments);
        }
    }


}
