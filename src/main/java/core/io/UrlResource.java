package core.io;

import cn.hutool.core.lang.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/*
use http method to read online file info
 */
public class UrlResource implements  Resource{

    private final URL url;

    public UrlResource(URL url){
        Assert.notNull(url,"url must not be null");
        this.url = url;
    }

    public InputStream getInputStream() throws IOException {
        URLConnection urlConnection = url.openConnection();
        try{
            return urlConnection.getInputStream();
        }catch (IOException e){
            if(urlConnection instanceof HttpURLConnection){
                ((HttpURLConnection) urlConnection).disconnect();
            }
            throw e;
        }
    }

}
