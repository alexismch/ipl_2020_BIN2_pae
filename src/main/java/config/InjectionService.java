package config;

import be.ipl.pae.main.Inject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class InjectionService {

  private static Properties properties;
  private static Map<String, Object> injectedObjects = new HashMap<>();


  /**
   * Permet de charger les properties.
   * 
   * @param nomFichier le nom du fichier properties a donner
   */
  public void chargerProperties(String nomFichier) {

    properties = new Properties();
    injectedObjects = new HashMap<>();
    try (InputStream in = new FileInputStream(nomFichier)) {
      properties.load(in);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Permet d'injecter les dependances.
   * 
   * @param ob object à injecter
   */
  public void injecter(Object ob) {

    for (Field field : ob.getClass().getDeclaredFields()) {
      Inject inject = field.getAnnotation(Inject.class);

      if (inject != null) {
        try {
          String dependenceName = field.getType().getName();

          Object injectedObject = injectedObjects.get(dependenceName);

          if (injectedObject == null) {

            if (!properties.containsKey(dependenceName)) {
              throw new InternalError("La dependence '" + dependenceName + "' n'existe pas !");
            }

            Class<?> injectedClass;

            try {
              injectedClass = Class.forName(properties.getProperty(dependenceName));
            } catch (ClassNotFoundException ex) {
              throw new InternalError(
                  "La valeur pour " + dependenceName + " n'est pas une classes connue !", ex);
            }
            try {
              injectedObject = injectedClass.getConstructors()[0].newInstance();
              injecter(injectedObject);
              injectedObjects.put(dependenceName, injectedObject);
            } catch (InstantiationException | InvocationTargetException ex) {
              throw new InternalError(
                  "Impossible de créer une instance de " + injectedClass.getName() + " !", ex);
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
