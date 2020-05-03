package be.ipl.pae.dependencies;

import be.ipl.pae.main.PropertiesLoader;
import be.ipl.pae.main.PropertiesLoader.PropertiesLoaderException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class InjectionService {

  private PropertiesLoader properties;
  private Map<String, Object> injectedObjects = new HashMap<>();

  public InjectionService(PropertiesLoader propertiesLoader) {
    this.properties = propertiesLoader;
  }

  /**
   * Inject all dependencies in the object and all nested object.
   *
   * @param ob the object with dependencies to resolve
   * @throws InjectionException if an injection fail
   */
  public void inject(Object ob) throws InjectionException {

    for (Field field : ob.getClass().getDeclaredFields()) {
      Injected injected = field.getAnnotation(Injected.class);

      if (injected != null) {
        try {
          String dependenceName;
          if (Injected.DEFAULT.equals(injected.value())) {
            dependenceName = field.getType().getName();
          } else {
            dependenceName = injected.value();
          }

          Object injectedObject = injectedObjects.get(dependenceName);

          if (injectedObject == null) {
            if (String.class.equals(field.getType())) {
              try {
                injectedObject = properties.getProperty(dependenceName);
              } catch (PropertiesLoaderException ex) {
                throw new InjectionException(ex.getMessage());
              }
            } else {

              Class<?> injectedClass;

              try {
                injectedClass = Class.forName(properties.getProperty(dependenceName));
              } catch (ClassNotFoundException ex) {
                throw new InjectionException(dependenceName + "'s value is not a known class !",
                    ex);
              } catch (PropertiesLoaderException ex) {
                throw new InjectionException(ex.getMessage());
              }

              try {
                Constructor<?> constructor = injectedClass.getDeclaredConstructors()[0];
                constructor.setAccessible(true);
                injectedObject = constructor.newInstance();
                inject(injectedObject);
                injectedObjects.put(dependenceName, injectedObject);
              } catch (InstantiationException | InvocationTargetException ex) {
                throw new InjectionException(
                    "Can not create an instance of " + injectedClass.getName() + " !", ex);
              }
            }
          }

          field.setAccessible(true);
          field.set(ob, injectedObject);
        } catch (IllegalAccessException ex) {
          throw new InjectionException(ex);
        }
      }
    }
  }

  public class InjectionException extends RuntimeException {

    public InjectionException() {
    }

    public InjectionException(String message) {
      super(message);
    }

    public InjectionException(String message, Throwable cause) {
      super(message, cause);
    }

    public InjectionException(Throwable cause) {
      super(cause);
    }

    public InjectionException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
      super(message, cause, enableSuppression, writableStackTrace);
    }
  }

}
