package aop;

import utils.ClassUtils;

public class TargetSource {

    private final Object target;

    public TargetSource(Object object){
        this.target = object;
    }

    // target may be created by jdp or cglib, if it's created by cglib, we need to get its superclass
    public Class<?>[] getTargetClass(){
        Class<?> clazz = this.target.getClass();
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
        return clazz.getInterfaces();
    }

    public Object getTarget(){
        return this.target;
    }

}
