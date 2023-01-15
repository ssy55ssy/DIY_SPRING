package bean;

import beans.BeansException;
import beans.factory.BeanFactory;
import beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProxyBeanFactoryForTest8 implements FactoryBean<IUserDaoForTest8> {

    public IUserDaoForTest8 getObject() throws Exception{
        InvocationHandler handler = (proxy, method, args) -> {
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("10001", "felix");
            hashMap.put("10002", "tony");
            hashMap.put("10003", "robot");
            return "you have been proxyed " + method.getName() + "：" + hashMap.get(args[0].toString());
        };
        return (IUserDaoForTest8) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{IUserDaoForTest8.class}, handler);
    }

    public Class<?> getObjectType(){
        return IUserDao.class;
    }

    public boolean isSingleton(){
        return true;
    }

}
