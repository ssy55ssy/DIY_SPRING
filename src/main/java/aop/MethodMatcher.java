package aop;

import java.lang.reflect.Method;

public interface MethodMatcher {

    // should the point cut apply to the method ?
    boolean matches(Method method, Class<?> targetClass);

}
