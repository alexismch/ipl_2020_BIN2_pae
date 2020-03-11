package be.ipl.pae.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Main {

  private final static String PROPERTIES_FILE_NAME = "dependance.properties";
  private static Properties properties;
  private static Map<String, Object> injectedObjects = new HashMap<>();

  static {
    properties = new Properties();
    try (InputStream in = new FileInputStream(PROPERTIES_FILE_NAME)) {
      properties.load(in);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public static void main(String[] args) throws Exception {
    Serveur serveur = new Serveur();
    // injecter(serveur);
    serveur.demarrer();
  }

  private static void injecter(Object ob) {

    for (Field field : ob.getClass().getDeclaredFields()) {
      Inject inject = field.getAnnotation(Inject.class);

      if (inject != null) {
        try {
          String dependenceName = field.getType().getName();
          Object injectedObject = injectedObjects.get(dependenceName);

          if (injectedObject == null) {

            if (!properties.containsKey(dependenceName)) {
              throw new InternalError(
                  "Cette dependence n'existe pas dans le fichier " + PROPERTIES_FILE_NAME + " !");
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
