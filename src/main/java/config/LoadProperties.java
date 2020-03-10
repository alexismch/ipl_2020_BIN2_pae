package config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class LoadProperties {

  private Properties properties;

  private String fichier = "prod.properties";

  public LoadProperties() {
    this.properties = new Properties();
  }

  /**
   * Permet de récuperer les valeurs d'un fichier properties.
   */
  public void loadProperties() {

    try (FileInputStream file = new FileInputStream("./target/classes/config/" + fichier)) {
      properties.load(file);
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
