package utils;

public class ClassUtils {

    public static ClassLoader getDefaultClassLoader(){
        ClassLoader classLoader = null;
        try{
            classLoader = Thread.currentThread().getContextClassLoader();
        }catch(Exception e){
            e.printStackTrace();
        }
        if(classLoader == null)
            classLoader = ClassUtils.class.getClassLoader();
        return classLoader;
    }

    public static boolean isCglibProxyClass(Class<?> clazz) {
        return (clazz != null && isCglibProxyClassName(clazz.getName()));
    }

    public static boolean isCglibProxyClassName(String className) {
        return (className != null && className.contains("$$"));
    }

}
