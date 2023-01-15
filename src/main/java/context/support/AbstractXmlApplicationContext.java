package context.support;

import beans.factory.support.DefaultListableBeanFactory;
import beans.factory.xml.XmlBeanDefinitionReader;

public abstract class AbstractXmlApplicationContext extends  AbstractRefreshableApplicationContext{

    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory){
        XmlBeanDefinitionReader definitionReader = new XmlBeanDefinitionReader(beanFactory,this);
        String[] configLocations = getConfigLocations();
        if(configLocations != null)
            definitionReader.loadBeanDefinitions(configLocations);
    }

    protected abstract String[] getConfigLocations();

}
