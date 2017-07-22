package utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {
    static Properties property = new Properties();
    public static boolean loadFile(){
        try {
            //property.load(PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName));
            InputStream inputStream  = new BufferedInputStream(new FileInputStream("resources/jdbc.properties"));
            property.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static String getPropertyValue(String key){
        return property.getProperty(key);
    }
}
