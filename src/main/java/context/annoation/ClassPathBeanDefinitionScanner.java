package context.annoation;

import beans.factory.config.BeanDefinition;
import beans.factory.support.BeanDefinitionRegistry;
import cn.hutool.core.util.StrUtil;
import context.support.ClassPathXmlApplicationContext;

import java.util.Set;

public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

    private BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry){
        this.registry = registry;
    }

    public void doScan(String... paths){
        for(String path:paths){
            Set<BeanDefinition> beanDefinitions = findCandidateComponents(path);
            for(BeanDefinition beanDefinition : beanDefinitions){
                // set bean's scope
                String scope = resolveScope(beanDefinition);
                if(StrUtil.isNotEmpty(scope)){
                    beanDefinition.setScope(scope);
                }
                // set bean's name
                String beanName = setName(beanDefinition);
                // register beanDefinition
                registry.registerBeanDefinition(beanName,beanDefinition);
            }
        }
    }

    private String resolveScope(BeanDefinition beanDefinition){
        Class<?> clazz = beanDefinition.getBeanClass();
        Scope scope = clazz.getAnnotation(Scope.class);
        if(null != scope)
            return scope.value();
        return StrUtil.EMPTY;
    }

    private String setName(BeanDefinition beanDefinition){
        Class<?> clazz = beanDefinition.getBeanClass();
        Component component = clazz.getAnnotation(Component.class);
        if(StrUtil.isNotEmpty(component.value())){
            return component.value();
        }
        return StrUtil.lowerFirst(clazz.getSimpleName());
    }

}
