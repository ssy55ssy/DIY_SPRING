package beans.factory.support;

import beans.BeansException;
import beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SimpleInstantiationStrategy implements InstantiationStrategy {

    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException{
        Class clazz = beanDefinition.getBeanClass();
        try{
            // if the ctor is null, we use the no constructor instanition, otherwise we use the constructor instanition
            if(ctor != null)
                return clazz.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);
            else
                return clazz.getDeclaredConstructor().newInstance();
        }catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e){
                throw new BeansException("fail to instantiate " + clazz.getName(),e);
        }
    }

}
