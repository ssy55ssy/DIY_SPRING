package context;

import beans.BeansException;

public interface ConfigurableApplicationContext extends ApplicationContext {

    // refresh the container
    void refresh() throws BeansException;

    void registerShutdownHook();

    void close();

}
