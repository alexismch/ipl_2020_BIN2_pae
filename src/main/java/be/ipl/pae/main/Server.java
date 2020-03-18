package be.ipl.pae.main;

import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.ihm.servlets.FrontendServlet;
import be.ipl.pae.ihm.servlets.LoginServlet;
import be.ipl.pae.ihm.servlets.LogoutServlet;
import be.ipl.pae.ihm.servlets.QuoteListServlet;
import be.ipl.pae.ihm.servlets.RegisterServlet;
import be.ipl.pae.ihm.servlets.UserListServlet;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.Source;
import org.eclipse.jetty.webapp.WebAppContext;

public class Server {

  @Injected
  private QuoteListServlet quoteListServlet;

  @Injected
  private LoginServlet loginServlet;

  @Injected
  private LogoutServlet logoutServlet;

  @Injected
  private UserListServlet userListServlet;

  @Injected
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
    
    backendContext.addServlet(new ServletHolder(loginServlet), "/login");
    backendContext.addServlet(new ServletHolder(logoutServlet), "/logout");
    backendContext.addServlet(new ServletHolder(userListServlet), "/users-list");
    backendContext.addServlet(new ServletHolder(registerServlet), "/register");
    backendContext.addServlet(new ServletHolder(quoteListServlet), "/devis");

    return backendContext;
  }
}
