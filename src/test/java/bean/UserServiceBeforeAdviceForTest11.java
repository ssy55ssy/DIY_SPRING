package bean;

import aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class UserServiceBeforeAdviceForTest11 implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) {
        System.out.println("intercept method：" + method.getName());
    }


}
