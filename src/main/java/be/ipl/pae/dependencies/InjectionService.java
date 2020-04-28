package be.ipl.pae.dependencies;

import be.ipl.pae.main.PropertiesLoader;
import be.ipl.pae.main.PropertiesLoader.PropertiesLoaderException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class InjectionService {

  private PropertiesLoader properties;
  private Map<String, Object> injectedObjects = new HashMap<>();

  public InjectionService(PropertiesLoader propertiesLoader) {
    this.properties = propertiesLoader;
    this.injectedObjects.put(propertiesLoader.getClass().getName(), propertiesLoader);
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

            Class<?> injectedClass;

            try {
              injectedClass = Class.forName(properties.getProperty(dependenceName));
            } catch (ClassNotFoundException ex) {
              throw new InternalError(dependenceName + "'s value is not a known class !", ex);
            } catch (PropertiesLoaderException ex) {
              throw new InternalError(ex.getMessage());
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
