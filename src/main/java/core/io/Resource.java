package core.io;

import java.io.IOException;
import java.io.InputStream;

/*
provide a method to achieve an inputStream
 */
public interface Resource {

    InputStream getInputStream() throws IOException;

}
