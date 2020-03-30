package be.ipl.pae.main;

import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.ihm.servlets.ConfirmationStatutServlet;
import be.ipl.pae.ihm.servlets.CustomerDetailsServlet;
import be.ipl.pae.ihm.servlets.CustomerServlet;
import be.ipl.pae.ihm.servlets.CustomersListServlet;
import be.ipl.pae.ihm.servlets.DevelopmentTypeListServlet;
import be.ipl.pae.ihm.servlets.DevelopmentTypeServlet;
import be.ipl.pae.ihm.servlets.ErrorHandler;
import be.ipl.pae.ihm.servlets.FrontendServlet;
import be.ipl.pae.ihm.servlets.LinkCcServlet;
import be.ipl.pae.ihm.servlets.LoginServlet;
import be.ipl.pae.ihm.servlets.LogoutServlet;
import be.ipl.pae.ihm.servlets.QuoteServlet;
import be.ipl.pae.ihm.servlets.QuotesListServlet;
import be.ipl.pae.ihm.servlets.RegisterServlet;
import be.ipl.pae.ihm.servlets.UserServlet;
import be.ipl.pae.ihm.servlets.UsersListServlet;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

public class Server {

  @Injected
  QuoteServlet quoteServlet;

  @Injected
  private QuotesListServlet quotesListServlet;

  @Injected
  private LoginServlet loginServlet;

  @Injected
  private LogoutServlet logoutServlet;

  @Injected
  private UsersListServlet usersListServlet;

  @Injected
  private UserServlet userServlet;

  @Injected
  private RegisterServlet registerServlet;

  @Injected
  private DevelopmentTypeServlet developmentTypeServlet;

  @Injected
  private DevelopmentTypeListServlet developmentTypeListServlet;

  @Injected
  private CustomerServlet customerServlet;

  @Injected
  private CustomersListServlet customersListServlet;

  @Injected
  private LinkCcServlet linkCcServlet;

  @Injected
  private CustomerDetailsServlet customerDetailsServlet;

  @Injected
  private ConfirmationStatutServlet confirmationStatutServlet;

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
    backendContext.setErrorHandler(new ErrorHandler());

    backendContext.setMaxFormContentSize(1000000000);

    backendContext.addServlet(new ServletHolder(loginServlet), "/login");
    backendContext.addServlet(new ServletHolder(logoutServlet), "/logout");
    backendContext.addServlet(new ServletHolder(usersListServlet), "/users-list");
    backendContext.addServlet(new ServletHolder(userServlet), "/user");
    backendContext.addServlet(new ServletHolder(registerServlet), "/register");
    backendContext.addServlet(new ServletHolder(quoteServlet), "/quote");
    backendContext.addServlet(new ServletHolder(quotesListServlet), "/quotes-list");
    backendContext.addServlet(new ServletHolder(developmentTypeServlet), "/developmentType");
    backendContext
        .addServlet(new ServletHolder(developmentTypeListServlet), "/developmentType-list");
    backendContext.addServlet(new ServletHolder(customerServlet), "/customer");
    backendContext.addServlet(new ServletHolder(customersListServlet), "/customers-list");
    backendContext.addServlet(new ServletHolder(linkCcServlet), "/link-cc");
    backendContext.addServlet(new ServletHolder(customerDetailsServlet), "/customer-details");
    backendContext.addServlet(new ServletHolder(confirmationStatutServlet), "/confirmationStatut");
    return backendContext;
  }
}
