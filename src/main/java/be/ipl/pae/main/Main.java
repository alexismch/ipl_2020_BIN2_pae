package be.ipl.pae.main;

import be.ipl.pae.dependencies.InjectionService;
import be.ipl.pae.exceptions.DalException;
import be.ipl.pae.ihm.server.Server;
import be.ipl.pae.ihm.servlets.utils.Util;

import java.util.Arrays;
import java.util.List;


public class Main {

  /**
   * Application's entry point.
   *
   * @param args ignored
   * @throws Exception Thrown if an error occurred during initialization or server startup
   */
  public static void main(String[] args) throws Exception {

    PropertiesLoader propertiesLoader = new PropertiesLoader();
    List<String> argsList = Arrays.asList(args);

    if (argsList.contains("--prod")) {
      propertiesLoader.loadProperties("props/prod.properties");
    } else {
      propertiesLoader.loadProperties("props/dev.properties");
    }

    int port;
    try {
      port = Integer.parseInt(propertiesLoader.getProperty("port"));
    } catch (NumberFormatException ex) {
      throw new DalException("Specified server port is invalid [min=1,max=65535]", ex);
    }
    if (port <= 0 || port >= 65535) {
      throw new DalException("Specified server port is invalid [min=1,max=65535]");
    }

    Server server = new Server(port);
    InjectionService injectionService = new InjectionService(propertiesLoader);
    injectionService.inject(server);
    Util.setJwt(propertiesLoader.getProperty("jwt"));
    server.start();
  }
}
