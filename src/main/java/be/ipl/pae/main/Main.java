package be.ipl.pae.main;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

public class Main {

  public static void main(String[] args) throws Exception {
    Server server = new Server(8080);
    ContextHandlerCollection context = new ContextHandlerCollection();
    context.setHandlers(new Handler[]{createBackendHandler(), createFrontendHandler()});
    server.setHandler(context);
    System.out.println("Démarrage du serveur...");
    server.start();
    server.join();
  }

  private static Handler createFrontendHandler() {
    WebAppContext frontendContext = new WebAppContext();
    frontendContext.setContextPath("/");
    frontendContext.setResourceBase("public");
    frontendContext.setInitParameter("cacheControl", "no-store,no-cache,must-revalidate");
    ErrorPageErrorHandler errorHandler = new ErrorPageErrorHandler();
    errorHandler.addErrorPage(404, "/index.html");
    frontendContext.setErrorHandler(errorHandler);
    frontendContext.addServlet(new ServletHolder(new DefaultServlet()), "/");
    return frontendContext;
  }

  private static Handler createBackendHandler() {
    ServletContextHandler backendContext = new ServletContextHandler(1);
    backendContext.setContextPath("/api");

    //

    return backendContext;
  }

}
