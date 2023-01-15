package beans.factory.config;

import beans.PropertyValues;

public class BeanDefinition {
    String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;
    String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;

    private Class bean;

    private PropertyValues propertyValues;

    // this two property enable users modify the initMode and destroyMode in xml file
    private String initMethodName;

    private String destroyMethodName;

    private String scope = SCOPE_SINGLETON;
    private boolean singleton = true;
    private boolean prototype = false;

    public BeanDefinition(Class bean){
        this.bean = bean;
        propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class bean, PropertyValues propertyValues){
        this.bean = bean;
        // create the propertyValues if the propertyValues is null, is case of the nullPointerException
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    public Class getBeanClass(){
        return bean;
    }

    public void setBeanClass(Class bean){
        this.bean = bean;
    }

    public PropertyValues getPropertyValues(){
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues){
        this.propertyValues = propertyValues;
    }

    public String getInitMethodName(){return initMethodName;}

    public void setInitMethodName(String methodName){this.initMethodName = methodName;}

    public String getDestroyMethodName(){return destroyMethodName;}

    public void setDestroyMethodName(String methodName){this.destroyMethodName = methodName;}

    public String getScope(){return this.scope;}

    public void setScope(String scope){this.scope = scope;}

    public boolean isSingleton(){return this.scope.equals(SCOPE_SINGLETON);}
}
