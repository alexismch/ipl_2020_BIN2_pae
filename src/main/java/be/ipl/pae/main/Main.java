package be.ipl.pae.main;

import be.ipl.pae.ihm.servlets.ConnexionServlet;
import be.ipl.pae.ihm.servlets.DeconnexionServlet;
import javax.servlet.http.HttpServlet;
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
    Server serveur = new Server(8080);
    ContextHandlerCollection contexte = new ContextHandlerCollection();
    contexte.setHandlers(new Handler[]{createBackendHandler(), createFrontendHandler()});
    serveur.setHandler(contexte);
    System.out.println("Démarrage du serveur...");
    serveur.start();
    serveur.join();
  }

  private static Handler createFrontendHandler() {
    WebAppContext frontendContexte = new WebAppContext();
    frontendContexte.setContextPath("/");
    frontendContexte.setResourceBase("public");
    frontendContexte.setInitParameter("cacheControl", "no-store,no-cache,must-revalidate");
    ErrorPageErrorHandler erreurHandler = new ErrorPageErrorHandler();
    erreurHandler.addErrorPage(404, "/index.html");
    frontendContexte.setErrorHandler(erreurHandler);
    frontendContexte.addServlet(new ServletHolder(new DefaultServlet()), "/");
    return frontendContexte;
  }

  private static Handler createBackendHandler() {
    ServletContextHandler backendContext = new ServletContextHandler(1);
    backendContext.setContextPath("/api");

    HttpServlet connexionServlet = new ConnexionServlet();
    backendContext.addServlet(new ServletHolder(connexionServlet), "/connexion");

    HttpServlet deconnexionServlet = new DeconnexionServlet();
    backendContext.addServlet(new ServletHolder(deconnexionServlet), "/deconnexion");

    return backendContext;
  }
}
