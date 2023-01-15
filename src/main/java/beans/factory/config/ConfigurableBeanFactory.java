package beans.factory.config;

import beans.factory.HierarchicalBeanFactory;
import utils.StringValueResolver;

public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    void destroySingletons();

    void addEmbeddedValueResolver(StringValueResolver stringValueResolver);

    String resolveEmbeddedValue(String value);
}
