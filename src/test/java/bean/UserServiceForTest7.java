package bean;

import beans.BeansException;
import beans.factory.BeanClassLoaderAware;
import beans.factory.BeanFactory;
import beans.factory.BeanFactoryAware;
import beans.factory.BeanNameAware;
import context.ApplicationContext;
import context.ApplicationContextAware;

public class UserServiceForTest7 implements BeanNameAware, BeanClassLoaderAware, ApplicationContextAware, BeanFactoryAware {
    private ApplicationContext applicationContext;
    private BeanFactory beanFactory;
    private String uId;
    private String company;
    private String location;
    private UserDaoForTest7 userDao;
    public String queryUserInfo() {
        return userDao.queryUserName(uId);
    }
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws
            BeansException {
        this.applicationContext = applicationContext;
    }
    @Override
    public void setBeanName(String name) {
        System.out.println("Bean Name is：" + name);
    }
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("ClassLoader：" + classLoader);
    }

    public String getUId() {
        return uId;
    }

    public UserDaoForTest7 getUserDao() {
        return userDao;
    }

    public void setUId(String uId) {
        this.uId = uId;
    }

    public void setUserDao(UserDaoForTest7 userDao) {
        this.userDao = userDao;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
}
