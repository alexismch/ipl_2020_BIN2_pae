package be.ipl.pae.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

  private String fileName;
  private Properties properties;

  public PropertiesLoader() {
  }

  /**
   * Load the properties file into this object.
   *
   * @param fileName The name of the file
   * @throws IOException Exception thrown if an error occurred with the properties file
   */
  public void loadProperties(String fileName) throws IOException {
    this.fileName = fileName;
    properties = new Properties();
    try (InputStream in = new FileInputStream(fileName)) {
      properties.load(in);
    } catch (IOException ex) {
      throw new IOException(ex);
    }
  }

  /**
   * Get the value of a property.
   *
   * @param name The name of the file
   * @throws IllegalStateException Exception thrown if no properties file are loaded
   */
  public String getProperty(String name) throws PropertiesLoaderException {
    if (properties == null) {
      throw new IllegalStateException();
    }
    if (!properties.containsKey(name)) {
      throw new PropertiesLoaderException(
          "The property '" + name + "' does not exist in " + fileName + " !");
    }
    return properties.getProperty(name);
  }

  public static class PropertiesLoaderException extends Exception {

    public PropertiesLoaderException(String message) {
      super(message);
    }
  }
}
