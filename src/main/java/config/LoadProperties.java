package config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class LoadProperties {

  private Properties properties;

  private String fichier = "prod.properties";

  public LoadProperties() {
    this.properties = new Properties();
  }

  public void createPropertiesFile(String fileName) {
    try (OutputStream output = new FileOutputStream("./target/classes/config/" + fileName)) {

      Properties prop = new Properties();

      // set the properties value
      prop.setProperty("db.url", "localhost");
      prop.setProperty("db.user", "mkyong");
      prop.setProperty("db.password", "password");

      // save properties to project root folder
      prop.store(output, null);

      System.out.println(prop);

    } catch (IOException io) {
      io.printStackTrace();
    }
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

  public Properties getProperties() {
    return properties;
  }



}
