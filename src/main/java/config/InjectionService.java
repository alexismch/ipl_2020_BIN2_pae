package config;

import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class InjectionService {

  private static Properties props = new Properties();
  private static Map<String, Object> dependencies = new HashMap<String, Object>();

  static {
    try {
      FileInputStream file = new FileInputStream("./config/dependance.properties");
      props.load(file);
      file.close();
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T getDependency(Class<?> c) {
    String implName = props.getProperty(c.getName());
    System.out.println(implName);
    if (dependencies.containsKey(implName)) {

      return (T) dependencies.get(implName);
    }
    try {
      Constructor<?> constructor = Class.forName(implName).getDeclaredConstructor();
      constructor.setAccessible(true);
      Object dependency = constructor.newInstance();
      dependencies.put(implName, dependency);
      return (T) dependency;
    } catch (Throwable e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}
