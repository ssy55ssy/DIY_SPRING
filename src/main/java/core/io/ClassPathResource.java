package core.io;

import utils.ClassUtils;
import cn.hutool.core.lang.Assert;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/*
use classloader to read the file info under classpath
 */
public class ClassPathResource implements  Resource{

    private final String path;

    private ClassLoader classLoader;

    ClassPathResource(String path){
        this(path,(ClassLoader)null);
    }

    ClassPathResource(String path,ClassLoader classLoader){
        Assert.notNull(path,"path must be not null");
        this.path = path;
        this.classLoader = classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
    }

    public InputStream getInputStream() throws IOException{
        InputStream is = classLoader.getResourceAsStream(path);
        if(is == null)
            throw new FileNotFoundException(this.path + "can not be opened");
        return is;
    }

}
