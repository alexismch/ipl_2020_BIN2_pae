package be.ipl.pae.main;

import be.ipl.pae.ihm.servlets.ConnexionServlet;
import be.ipl.pae.ihm.servlets.DeconnexionServlet;
import be.ipl.pae.ihm.servlets.FrontendServlet;
import be.ipl.pae.ihm.servlets.RegisterServlet;
import be.ipl.pae.ihm.servlets.UserListServlet;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

public class Server {

  @Inject
  private ConnexionServlet connexionServlet;

  @Inject
  private DeconnexionServlet deconnexionServlet;

  @Inject
  private UserListServlet userListServlet;

  @Inject
  private RegisterServlet registerServlet;

  /**
   * Start the server.
   *
   * @throws Exception Thrown if an error occurred during startup
   */
  public void start() throws Exception {
    org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(8080);
    ContextHandlerCollection context = new ContextHandlerCollection();
    context.setHandlers(new Handler[] {createBackendHandler(), createFrontendHandler()});
    server.setHandler(context);
    System.out.println("Server starting...");
    server.start();
    server.join();
  }

  private Handler createFrontendHandler() {
    WebAppContext frontendContext = new WebAppContext();
    frontendContext.setContextPath("/");
    frontendContext.setResourceBase("public");
    frontendContext.setInitParameter("cacheControl", "no-store,no-cache,must-revalidate");
    frontendContext.addServlet(new ServletHolder(new FrontendServlet()), "/");
    return frontendContext;
  }

  private Handler createBackendHandler() {
    ServletContextHandler backendContext =
        new ServletContextHandler(ServletContextHandler.SESSIONS);
    backendContext.setContextPath("/api");

    backendContext.addServlet(new ServletHolder(connexionServlet), "/connexion");
    backendContext.addServlet(new ServletHolder(deconnexionServlet), "/deconnexion");
    backendContext.addServlet(new ServletHolder(userListServlet), "/user-list");
    backendContext.addServlet(new ServletHolder(registerServlet), "/register");

    return backendContext;
  }
}
