package beans.factory.config;

// define functions about get singleton and register singleton
public interface SingletonBeanRegistry {

    Object getSingleton(String beanName);

    void registerSingleton(String beanName, Object singletonObject);

}
