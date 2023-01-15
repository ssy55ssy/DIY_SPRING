package core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/*
Read file info through file path, usual use this method to read xml file info
 */
public class FileSystemResource implements Resource {

    private final String path;

    private final File file;

    public FileSystemResource(String path,File file){
        this.path = path;
        this.file = file;
    }

    public FileSystemResource(String path){
        this.path = path;
        this.file = new File(path);
    }


    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.file);
    }

    public String getPath(){
        return this.path;
    }

}
