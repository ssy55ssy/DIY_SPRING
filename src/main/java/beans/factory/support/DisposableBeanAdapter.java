package beans.factory.support;

import beans.BeansException;
import beans.factory.DisposableBean;
import beans.factory.config.BeanDefinition;
import cn.hutool.core.util.StrUtil;

import java.lang.reflect.Method;

public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;
    private final String beanName;
    private final String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition){
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    public void destroy() throws Exception{
        // judge whether the bean object use the DisposableBean interface
        if(bean instanceof DisposableBean)
            ((DisposableBean)bean).destroy();

        // jude whether the bean definition has a destroy method
        if(!(bean instanceof  DisposableBean) && StrUtil.isNotEmpty(destroyMethodName) && destroyMethodName.equals("destroy")){
            Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
            if (null == destroyMethod)
                throw new BeansException("Couldn't find a destroy method named '" + destroyMethodName + "' on bean with name '" + beanName + "'");
            destroyMethod.invoke(bean);
        }
    }

}
