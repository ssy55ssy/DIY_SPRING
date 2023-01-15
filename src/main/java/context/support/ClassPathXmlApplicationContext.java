package context.support;

import beans.BeansException;

import java.beans.Beans;

/*
provide config file location other functions will be achieved in AbstractXmlApplicationContext
 */
public class ClassPathXmlApplicationContext extends  AbstractXmlApplicationContext {

    String[] configLocations;

    public ClassPathXmlApplicationContext(){
    }

    public ClassPathXmlApplicationContext(String configLocation) throws BeansException{
        this(new String[]{configLocation});
    }

    public ClassPathXmlApplicationContext(String[] configLocation) throws BeansException{
        this.configLocations = configLocation;
        refresh();
    }

    protected String[] getConfigLocations(){
        return configLocations;
    }

}
