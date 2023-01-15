package beans.factory;

import beans.BeansException;
import beans.PropertyValue;
import beans.PropertyValues;
import beans.factory.config.BeanDefinition;
import beans.factory.config.BeanFactoryPostProcessor;
import core.io.DefaultResourceLoader;
import core.io.Resource;
import utils.StringValueResolver;


import java.util.Properties;

public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    // property file location
    private String location;

    // edit bean's property before bean's instanition
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException{
        try{
            // load property file
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(location);
            Properties properties = new Properties();
            properties.load(resource.getInputStream());
            // add stringValueResolver into the container, this will be used in @Value
            PlaceholderResolvingStringValueResolver stringValueResolver = new PlaceholderResolvingStringValueResolver(properties);
            beanFactory.addEmbeddedValueResolver(stringValueResolver);

            String[] definationNames = beanFactory.getBeanDefinitionNames();
            for(String beanName : definationNames){
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                PropertyValues propertyValues = beanDefinition.getPropertyValues();
                for(PropertyValue propertyValue : propertyValues.getPropertyValues()){
                    Object value = propertyValue.getValue();
                    String name = propertyValue.getName();
                    if(!(value instanceof String))
                        continue;
                    String strValue = (String) value;
                    String replacedVal = resolvePlaceholder(strValue,properties);
                    if(replacedVal != null)
                        propertyValues.addPropertyValue(new PropertyValue(name,replacedVal));
                }
            }

        }catch(Exception e){
            throw new BeansException("Could not load properties", e);
        }
    }

    private String resolvePlaceholder(String strVal, Properties properties){
        StringBuilder sb = new StringBuilder(strVal);
        int startPoint = strVal.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
        int endPoint = strVal.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
        if(startPoint != 1 && endPoint != -1 && startPoint < endPoint){
            String replaceValue = strVal.substring(startPoint+2,endPoint);
            sb.replace(startPoint,endPoint+1,properties.getProperty(replaceValue));
            return sb.toString();
        }
        return null;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private class PlaceholderResolvingStringValueResolver implements StringValueResolver{

        private final Properties properties;

        private PlaceholderResolvingStringValueResolver(Properties properties){
            this.properties = properties;
        }

        public String resolveStringValues(String strVal){
            return PropertyPlaceholderConfigurer.this.resolvePlaceholder(strVal,properties);
        }
    }

}
