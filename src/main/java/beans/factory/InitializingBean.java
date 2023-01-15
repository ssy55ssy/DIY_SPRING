package beans.factory;

public interface InitializingBean {
    // execute after the bean object has inject the property value
    void afterPropertiesSet() throws Exception;

}
