package be.ipl.pae.main;

import config.InjectionService;


public class Main {

  /**
   * Application's entry point.
   *
   * @param args ignored
   * @throws Exception Thrown if an error occurred during initialization or server startup
   */
  public static void main(String[] args) throws Exception {
    Server server = new Server();
    InjectionService injectionService = new InjectionService();
    injectionService.loadProperties("dev.properties");
    injectionService.inject(server);
    server.start();
  }
}
