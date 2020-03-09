package config;

import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class InjectionService {

  private static Properties props = new Properties();
  private static Map<String, Object> mapDesDependances = new HashMap<String, Object>();

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
   * Methode permettant de faire une injection de dépendance.
   * 
   * @param <T>
   * @param classe la classe dans laquelle on va faire l'injection
   * @return une factory ou lance une exception
   */
  public static <T> T getDependance(Class<?> classe) {
    String implName = props.getProperty(classe.getName());
    System.out.println(implName);
    if (mapDesDependances.containsKey(implName)) {

      return (T) mapDesDependances.get(implName);
    }
    try {
      Constructor<?> constructor = Class.forName(implName).getDeclaredConstructor();
      constructor.setAccessible(true);
      Object dependency = constructor.newInstance();
      mapDesDependances.put(implName, dependency);
      return (T) dependency;
    } catch (Throwable ex) {
      ex.printStackTrace();
      throw new RuntimeException(ex);
    }
  }
}
