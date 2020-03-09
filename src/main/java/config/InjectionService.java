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
    } catch (Throwable ex) {
      throw new RuntimeException(ex);
    }
  }

  /**
   * Methode permettant de faire une injection de dépendance
   * @param <T>
   * @param 
   * @return
   */
  public static <T> T getDependance(Class<?> c) {
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
    } catch (Throwable ex) {
      ex.printStackTrace();
      throw new RuntimeException(ex);
    }
  }
}
