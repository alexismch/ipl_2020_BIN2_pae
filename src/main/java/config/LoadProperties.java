package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoadProperties {

  private Properties properties;

  private String fichier = "props/prod.properties";

  public LoadProperties() {
    this.properties = new Properties();
  }

  /**
   * Permet de récuperer les valeurs d'un fichier properties.
   */
  public void loadProperties() {

    try (FileInputStream file = new FileInputStream(fichier)) {
      properties.load(file);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public Properties getProperties() {
    return properties;
  }
}
