package be.ipl.pae.main;

import be.ipl.pae.dependencies.InjectionService;
import be.ipl.pae.util.PropertiesLoader;


public class Main {

  /**
   * Application's entry point.
   *
   * @param args ignored
   * @throws Exception Thrown if an error occurred during initialization or server startup
   */
  public static void main(String[] args) throws Exception {
    Server server = new Server();
    PropertiesLoader propertiesLoader = new PropertiesLoader();
    propertiesLoader.loadProperties("props/dev.properties");
    InjectionService injectionService = new InjectionService(propertiesLoader);
    injectionService.inject(server);
    server.start();
  }
}
