package be.ipl.pae.dependencies;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class InjectionService {

  private static String fileName;
  private static Properties properties;
  private static Map<String, Object> injectedObjects = new HashMap<>();

  /**
   * Load the properties file used for dependencies injection.
   *
   * @param fileName The name of the file
   * @throws IOException Exception thrown if an error occurred with the properties file
   */
  public void loadProperties(String fileName) throws IOException {
    InjectionService.fileName = fileName;
    properties = new Properties();
    injectedObjects = new HashMap<>();
    try (InputStream in = new FileInputStream(fileName)) {
      properties.load(in);
    } catch (IOException ex) {
      ex.printStackTrace();
      throw new IOException(ex);
    }
  }

  /**
   * Inject all dependencies in the object and all nested object.
   *
   * @param ob the object with dependencies to resolve
   */
  public void inject(Object ob) {

    for (Field field : ob.getClass().getDeclaredFields()) {
      Injected injected = field.getAnnotation(Injected.class);

      if (injected != null) {
        try {
          String dependenceName = field.getType().getName();

          Object injectedObject = injectedObjects.get(dependenceName);

          if (injectedObject == null) {

            if (!properties.containsKey(dependenceName)) {
              throw new InternalError(
                  "Dependence '" + dependenceName + "' does not exist in " + fileName + " !");
            }

            Class<?> injectedClass;

            try {
              injectedClass = Class.forName(properties.getProperty(dependenceName));
            } catch (ClassNotFoundException ex) {
              throw new InternalError(dependenceName + "'s value is not a known class !", ex);
            }
            try {
              injectedObject = injectedClass.getConstructors()[0].newInstance();
              inject(injectedObject);
              injectedObjects.put(dependenceName, injectedObject);
            } catch (InstantiationException | InvocationTargetException ex) {
              throw new InternalError(
                  "Can not create an instance of " + injectedClass.getName() + " !", ex);
            }
          }

          field.setAccessible(true);
          field.set(ob, injectedObject);
        } catch (IllegalAccessException ex) {
          throw new InternalError(ex);
        }
      }
    }
  }
}
