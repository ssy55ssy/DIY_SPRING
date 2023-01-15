package common;

import bean.UserServiceForTest5;
import beans.BeansException;
import beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessorForTest5 implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("userService".equals(beanName)) {
            UserServiceForTest5 userService = (UserServiceForTest5) bean;
            userService.setLocation("cahnge to:vancouver");
        }
        return bean;
    }
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
