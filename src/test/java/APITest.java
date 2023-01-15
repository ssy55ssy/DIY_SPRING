import aop.AdvisedSupport;
import aop.AspectJExpressionPointcut;
import aop.TargetSource;
import aop.frameWork.Cglib2AopProxy;
import aop.frameWork.JdkDynamicAopProxy;
import bean.*;
import beans.PropertyValue;
import beans.PropertyValues;
import beans.factory.config.BeanDefinition;
import beans.factory.config.BeanReference;
import context.support.ClassPathXmlApplicationContext;
import core.io.DefaultResourceLoader;
import core.io.Resource;
import beans.factory.support.DefaultListableBeanFactory;
import beans.factory.xml.XmlBeanDefinitionReader;
import cn.hutool.core.io.IoUtil;
import event.CustomEvent;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class APITest {

    private DefaultResourceLoader resourceLoader;
    @Before
    public void init() {
        resourceLoader = new DefaultResourceLoader();
    }

    @Test
    public void test_BeanFactory_1(){
        System.out.println("test_BeanFactory_1:");
        // 1.initialize BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2.register bean
        BeanDefinition beanDefinition = new BeanDefinition(UserServiceForTest1.class);
        beanFactory.registerBeanDefinition("userServiceForTest1", beanDefinition);
        // 3.obtain bean for the first time
        UserServiceForTest1 userService = (UserServiceForTest1) beanFactory.getBean("userServiceForTest1");
        userService.queryUserInfo();
        // 4.obtain bean from Singleton
        UserServiceForTest1 userService_singleton = (UserServiceForTest1) beanFactory.getBean("userServiceForTest1");
        userService_singleton.queryUserInfo();
        System.out.println("--------------------------------------");
    }

    @Test
    public void test_BeanFactory_Constructors_2() {
        System.out.println("test_BeanFactory_Constructors_2:");
        // 1.initial BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2. inject bean
        BeanDefinition beanDefinition = new BeanDefinition(UserServiceForTest2.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);
        // 3.获取 bean
        UserServiceForTest2 userService = (UserServiceForTest2) beanFactory.getBean("userService", "felix");
        userService.queryUserInfo();
        System.out.println("--------------------------------------");
    }

    @Test
    public void test_BeanFactory_PropertyValue_3() {
        System.out.println("test_BeanFactory_PropertyValue_3:");
        // 1.initial BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2. UserDao register
        beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDaoForTest3.class));
        // 3. UserService set property[uId、userDao]
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("uId", "10001"));
        propertyValues.addPropertyValue(new PropertyValue("userDao",new BeanReference("userDao")));
        // 4. inject bean UserService
        BeanDefinition beanDefinition = new BeanDefinition(UserServiceForTest3.class, propertyValues);
        beanFactory.registerBeanDefinition("userService", beanDefinition);
        // 5. achieve bean UserService
        UserServiceForTest3 userService = (UserServiceForTest3) beanFactory.getBean("userService");
        userService.queryUserInfo();
        System.out.println("--------------------------------------");
    }


    @Test
    public void test_xml_LoadBeanDefinitionThroughXML_4() {
        System.out.println("test_xml_LoadBeanDefinitionThroughXML_4:");
        // 1.initialize BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2. read definition file & register Bean
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:springForTest4.xml");
        // 3. achieve Bean
        UserServiceForTest4 userService = beanFactory.getBean("userService", UserServiceForTest4.class);
        String result = userService.queryUserInfo();
        System.out.println("test result：" + result);
        System.out.println("--------------------------------------");
    }

    @Test
    public void test_xml_Context_5() {
        System.out.println("test_xml_Context_5:");
        // 1.initial BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springForTest5.xml");
        // 2. achieve Bean object
        UserServiceForTest5 userService = applicationContext.getBean("userService", UserServiceForTest5.class);
        String result = userService.queryUserInfo();
        System.out.println("test result：" + result);
        System.out.println("--------------------------------------");
    }

    @Test
    public void test_xml_InitialAndDestroy_6() {
        System.out.println("test_xml_InitialAndDestroy_6:");
        // 1.initial BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springForTest6.xml");
        applicationContext.registerShutdownHook();
        // 2. achieve Bean object
        UserServiceForTest6 userService = applicationContext.getBean("userService", UserServiceForTest6.class);
        String result = userService.queryUserInfo();
        System.out.println("test result：" + result);
        System.out.println("--------------------------------------");
    }

    @Test
    public void test_xml_Aware_7() {
        System.out.println("test_xml_Aware_7:");
        // 1.initial BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springForTest7.xml");
        applicationContext.registerShutdownHook();
        // 2. achieve Bean object
        UserServiceForTest7 userService = applicationContext.getBean("userService", UserServiceForTest7.class);
        String result = userService.queryUserInfo();
        System.out.println("test result：" + result);
        System.out.println("ApplicationContextAware： "+userService.getApplicationContext());
        System.out.println("BeanFactoryAware："+userService.getBeanFactory());
        System.out.println("--------------------------------------");
    }

    @Test
    public void test_factory_bean_8() {
        System.out.println("test_factory_bean_8:");
        // 1.initial BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springForTest8.xml");
        applicationContext.registerShutdownHook();
        // 2. use the proxy method
        UserServiceForTest8 userService = applicationContext.getBean("userService", UserServiceForTest8.class);
        System.out.println("test result：" + userService.queryUserInfo());
        System.out.println("--------------------------------------");
    }

    @Test
    public void test_event_9() {
        System.out.println("test_event_9:");
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springForTest9.xml");
        applicationContext.publishEvent(new CustomEvent(applicationContext, 1019129009086763L, "success！"));
        applicationContext.registerShutdownHook();
        System.out.println("--------------------------------------");
    }

    @Test
    public void test_dynamic_10() {
        System.out.println("test_dynamic_10:");
        // target object
        IUserServiceForTest10 userService = new UserServiceForTest10();
        // create advisedSupport
        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(userService));
        advisedSupport.setMethodInterceptor(new UserServiceInterceptorForTest10());
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* bean.IUserServiceForTest10.*(..))"));
        // JdkDynamicAopProxy
        IUserServiceForTest10 proxy_jdk = (IUserServiceForTest10) new JdkDynamicAopProxy(advisedSupport).getProxy();
        // use proxy
        System.out.println("test result：" + proxy_jdk.queryUserInfo());
        // Cglib2AopProxy
        IUserServiceForTest10 proxy_cglib = (IUserServiceForTest10) new Cglib2AopProxy(advisedSupport).getProxy();
        // use proxy
        System.out.println("test result：" + proxy_cglib.register("felix"));
        System.out.println("--------------------------------------");
    }

    @Test
    public void test_aop_11() {
        System.out.println("test_aop_11:");
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springForTest11.xml");
        IUserServiceForTest11 userService = applicationContext.getBean("userService", IUserServiceForTest11.class);
        System.out.println("test result：" + userService.queryUserInfo());
        System.out.println("--------------------------------------");
    }

    @Test
    public void test_scan_12_1() {
        System.out.println("test_scan_12_1:");
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springForTest12_1.xml");
        IUserServiceForTest12 userService = applicationContext.getBean("userService", IUserServiceForTest12.class);
        System.out.println("test result：" + userService.queryUserInfo());
        System.out.println("--------------------------------------");
    }

    @Test
    public void test_property_12_2() {
        System.out.println("test_property_12_2:");
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springForTest12_2.xml");
        IUserServiceForTest12 userService = applicationContext.getBean("userService", IUserServiceForTest12.class);
        System.out.println("test result：" + userService);
        System.out.println("--------------------------------------");
    }

    @Test
    public void test_scan_13() {
        System.out.println("test_scan_13:");
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springForTest13.xml");
        IUserServiceForTest13 userService = applicationContext.getBean("userServiceForTest13", IUserServiceForTest13.class);
        System.out.println("test result：" + userService.queryUserInfo());
        System.out.println("--------------------------------------");
    }

    @Test
    public void test_autoProxy_14() {
        System.out.println("test_autoProxy_14:");
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springForTest14.xml");
        IUserServiceForTest14 userService = applicationContext.getBean("userService", IUserServiceForTest14.class);
        System.out.println("test result：" + userService.queryUserInfo());
        System.out.println("--------------------------------------");
    }

}
