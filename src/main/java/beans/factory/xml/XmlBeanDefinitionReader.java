package beans.factory.xml;

import beans.BeansException;
import beans.PropertyValue;
import context.annoation.ClassPathBeanDefinitionScanner;
import core.io.Resource;
import core.io.ResourceLoader;
import beans.factory.config.BeanDefinition;
import beans.factory.config.BeanReference;
import beans.factory.support.AbstractBeanDefinitionReader;
import beans.factory.support.BeanDefinitionRegistry;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry){
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader){
        super(registry,resourceLoader);
    }

    public void loadBeanDefinitions(Resource resource) throws BeansException{
        try{
            try(InputStream inputStream = resource.getInputStream()){
                doLoadBeanDefinitions(inputStream);
            }
        }catch(IOException | ClassNotFoundException e){
            throw new BeansException("IOException parsing xml document from " + resource,e);
        }
    }

    public void loadBeanDefinitions(Resource... resource) throws BeansException{
        for(Resource resource1 : resource)
            loadBeanDefinitions(resource1);
    }

    public void loadBeanDefinitions(String location) throws BeansException{
        Resource resource = getResourceLoader().getResource(location);
        loadBeanDefinitions(resource);
    }

    public void loadBeanDefinitions(String[] locations) throws BeansException{
        for(String location : locations){
            loadBeanDefinitions(location);
        }
    }

    protected void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException {
        Document doc =  XmlUtil.readXML(inputStream);
        Element root = doc.getDocumentElement();
        NodeList childNodes =  root.getChildNodes();

        // component-scan
        for(int i = 0; i < childNodes.getLength(); i++){
            // judge element
            if(! (childNodes.item(i) instanceof  Element))
                continue;
            // judge object
            if(! "component-scan".equals(childNodes.item(i).getNodeName()))
                continue;
            Element componentScan = (Element)childNodes.item(i);
            String packPaths = componentScan.getAttribute("base-package");
            if(StrUtil.isEmpty(packPaths))
                throw new BeansException("The value of basepackage attribute can not be empty or null");
            scanPackage(packPaths);
        }

        for(int i = 0; i < childNodes.getLength(); i++){
            // judge element
            if(! (childNodes.item(i) instanceof  Element))
                continue;
            //judge object
            if(! "bean".equals(childNodes.item(i).getNodeName()))
                continue;
            Element bean = (Element)childNodes.item(i);
            String id = bean.getAttribute("id");
            String name = bean.getAttribute("name");
            String className = bean.getAttribute("class");
            String initMethodName = bean.getAttribute("init-method");
            String destroyMethodName = bean.getAttribute("destroy-method");
            String scope = bean.getAttribute("scope");
            Class<?> clazz = Class.forName(className);
            // the id's priority is higher than name
            String beanName = StrUtil.isNotEmpty(id) ? id : name;
            if(StrUtil.isEmpty(beanName))
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            // define the bean
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setInitMethodName(initMethodName);
            beanDefinition.setDestroyMethodName(destroyMethodName);
            if(StrUtil.isNotEmpty(scope))
                beanDefinition.setScope(scope);
            // read the property value and inject them
            for(int j = 0; j < bean.getChildNodes().getLength(); j++){
                if(! (bean.getChildNodes().item(j) instanceof  Element))
                    continue;
                if(! "property".equals(bean.getChildNodes().item(j).getNodeName()))
                    continue;
                Element property = (Element)bean.getChildNodes().item(j);
                String attrName = property.getAttribute("name");
                String attrValue = property.getAttribute("value");
                String attrRef = property.getAttribute("ref");
                Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
                // create propertyValues
                PropertyValue propertyValue = new PropertyValue(attrName,value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);

            }
            // judge whether the bean factory has duplicate bean name
            if(getRegistry().containsBeanDefinition(beanName))
                throw new BeansException("duplicate bean name : " + beanName + " is not allowed");
            // register beanDefinition
            getRegistry().registerBeanDefinition(beanName,beanDefinition);
        }
    }

    private void scanPackage(String packPath){
        String[] basePackages = StrUtil.split(packPath,",");
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(getRegistry());
        scanner.doScan(basePackages);
    }

}
