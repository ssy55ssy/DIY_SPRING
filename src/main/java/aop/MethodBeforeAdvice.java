package aop;

import java.lang.reflect.Method;


// callback before a method is invoked
public interface MethodBeforeAdvice extends BeforeAdvice {
    void before(Method method,Object[] args, Object target);
}
