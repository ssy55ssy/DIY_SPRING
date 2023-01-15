package core.io;

import cn.hutool.core.lang.Assert;

import java.net.MalformedURLException;
import java.net.URL;

public class DefaultResourceLoader implements  ResourceLoader{

    public Resource getResource(String location){
        Assert.notNull(location, "location must not be null");
        // judge whether this file is a class file or not
        if(location.startsWith(CLASSPATH_URL_PREFIX)){
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        }else{
            try{
                // try to read this file through http method
                URL url = new URL(location);
                return new UrlResource(url);
            }catch(MalformedURLException e){
                return new FileSystemResource(location);
            }
        }
    }

}
