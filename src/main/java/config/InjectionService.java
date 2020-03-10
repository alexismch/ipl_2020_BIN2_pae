package config;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class InjectionService {

  private static Properties props = new Properties();
  private static Map<String, Object> mapDesDependances = new HashMap<>();

  static {
    String configPath = "src/main/java/config/dependance.properties";
    Path path = FileSystems.getDefault().getPath(configPath);
    try (InputStream in = Files.newInputStream(path)) {
      props.load(in);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Methode permettant de faire une injection de dépendance.
   *
   * @param classe la classe dans laquelle on va faire l'injection
   * @return une factory ou lance une exception
   */
  public static <T> T getDependance(Class<?> classe) {
    String implName = props.getProperty(classe.getName());

    if (mapDesDependances.containsKey(implName)) {
      return (T) mapDesDependances.get(implName);
    }
    try {
      // System.out.println("\n"+ classe.getName());
      // System.out.println(implName);
      Class<?> classeAImplementer = Class.forName(implName);
      // System.out.println(classeAImplementer.getDeclaredConstructor());
      // System.out.println(classeAImplementer.getConstructors());
      // System.out.println(implName);
      Constructor<?> constructor = classeAImplementer.getDeclaredConstructor();
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
