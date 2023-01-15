package aop;

public interface ClassFilter {

    // should the point cut apply to the class or interface ?
    boolean matches(Class<?> clazz);

}
