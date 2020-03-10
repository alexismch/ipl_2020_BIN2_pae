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

  public void loadProperties() {

    try (FileInputStream file = new FileInputStream("./target/classes/config/" + fichier)) {
      properties.load(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
