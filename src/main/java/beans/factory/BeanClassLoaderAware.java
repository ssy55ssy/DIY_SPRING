package beans.factory;

import beans.BeansException;

public interface BeanClassLoaderAware extends  Aware {
    void setBeanClassLoader(ClassLoader classLoader) throws BeansException;
}
