package beans.factory.support;

import core.io.DefaultResourceLoader;
import core.io.ResourceLoader;

public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader{

    private BeanDefinitionRegistry beanDefinitionRegistry;

    private ResourceLoader resourceLoader;

    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry){
        this(registry,new DefaultResourceLoader());
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry,ResourceLoader resourceLoader){
        this.beanDefinitionRegistry = registry;
        this.resourceLoader = resourceLoader;
    }

    public BeanDefinitionRegistry getRegistry(){
        return beanDefinitionRegistry;
    }

    public ResourceLoader getResourceLoader(){
        return resourceLoader;
    }

}
