package aop.frameWork;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

public class ReflectiveMethodInvocation implements MethodInvocation {

    protected final Object target;

    protected final Method method;

    protected final Object[] arguments;

    public ReflectiveMethodInvocation(Object target, Method method, Object[] arguments){
        this.target = target;
        this.method = method;
        this.arguments = arguments;
    }

    public Method getMethod(){
        return this.method;
    }

    public Object[] getArguments(){
        return this.arguments;
    }

    public Object proceed() throws Throwable{
        return method.invoke(target,arguments);
    }

    public Object getThis(){
        return this.target;
    }

    public AccessibleObject getStaticPart(){
        return this.method;
    }

}
