package aop.frameWork;

// since we have 2 proxy method : jdk & cglib, use this interface can use these two method more easily
public interface AopProxy {

    Object getProxy();

}
