package context.annoation;

import beans.factory.config.BeanDefinition;
import cn.hutool.core.util.ClassUtil;

import java.util.HashSet;
import java.util.Set;

public class ClassPathScanningCandidateComponentProvider {

    // add classes with Component annoation  into candidates
    public Set<BeanDefinition> findCandidateComponents(String path){
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(path,Component.class);
        Set<BeanDefinition> candidates = new HashSet<>();
        for(Class<?> clazz : classes){
            candidates.add(new BeanDefinition(clazz));
        }
        return candidates;
    }

}
