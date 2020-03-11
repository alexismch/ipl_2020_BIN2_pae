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
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws Exception {
    Serveur serveur = new Serveur();
    // injecter(serveur);
    serveur.demarrer();
  }

  private static void injecter(Object o) {

    for (Field field : o.getClass().getDeclaredFields()) {
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
            } catch (ClassNotFoundException e) {
              throw new InternalError(
                  "La valeur pour " + dependenceName + " n'est pas une classes connue !", e);
            }
            try {
              injectedObject = injectedClass.getConstructors()[0].newInstance();
              injecter(injectedObject);
              injectedObjects.put(dependenceName, injectedObject);
            } catch (InstantiationException | InvocationTargetException e) {
              throw new InternalError(
                  "Impossible de créer une instance de " + injectedClass.getName() + " !", e);
            }
          }

          field.setAccessible(true);
          field.set(o, injectedObject);
        } catch (IllegalAccessException e) {
          throw new InternalError(e);
        }
      }
    }
  }

}
