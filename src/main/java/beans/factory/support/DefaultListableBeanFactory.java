package beans.factory.support;

import beans.BeansException;
import beans.factory.ConfigurableListableBeanFactory;
import beans.factory.config.BeanDefinition;
import beans.factory.config.BeanPostProcessor;

import java.util.*;

/*
this class enables users to achieve the bean definition
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry,ConfigurableListableBeanFactory {

    private Map<String,BeanDefinition> beanDefinitionMap = new HashMap<>();

    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition){
        beanDefinitionMap.put(beanName,beanDefinition);
    }

    public BeanDefinition getBeanDefinition(String beanName) throws BeansException{
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if(beanDefinition == null)
            throw new BeansException("No bean named" + beanName + "has been defined");
        return beanDefinition;
    }

    public <T> T getBean(Class<T> requiredType) throws BeansException{
        List<String> beanNames = new ArrayList<>();
        for(Map.Entry<String,BeanDefinition> entry : beanDefinitionMap.entrySet()){
            BeanDefinition beanDefinition = entry.getValue();
            Class<?> beanType = beanDefinition.getBeanClass();
            if(beanType.isAssignableFrom(requiredType)){
                beanNames.add(entry.getKey());
            }
        }
        if(beanNames.size() == 1){
            return getBean(beanNames.get(0),requiredType);
        }
        throw new BeansException(requiredType + "expected single bean but found " + beanNames.size() + ": " + beanNames);
    }

    public boolean containsBeanDefinition(String beanName){
        return beanDefinitionMap.containsKey(beanName);
    }


    public void preInstantionSingleton() throws BeansException{
        beanDefinitionMap.keySet().forEach(this::getBean);
    }

    public String[] getBeanDefinitionNames(){
        Set<String> set = beanDefinitionMap.keySet();
        return set.toArray(new String[0]);
    }

    public <T> Map<String,T> getBeansOfTypes(Class<T> type) throws BeansException{
        Map<String,T> res = new HashMap<>();
        Set<String> set = beanDefinitionMap.keySet();
        for(String beanName : set){
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if(type.isAssignableFrom(beanDefinition.getBeanClass())){
                res.put(beanName,(T)getBean(beanName));
            }
        }
        return res;
    }

}
