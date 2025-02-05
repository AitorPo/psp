package util;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class R {

    public static InputStream getImage(String name){
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("images" + File.separator + name);
    }

    public static URL getUi(String name){
        return Thread.currentThread().getContextClassLoader().getResource("ui" + File.separator + name);
    }
}
