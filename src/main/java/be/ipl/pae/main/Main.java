package be.ipl.pae.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class Main {

  public static void main(String[] args) throws Exception {
    // server to listen on specific port 8080
    Server server = new Server(80);
    // create the object to configure the web application
    WebAppContext context = new WebAppContext();

    System.out.println(context.getContextPath());
    context.setContextPath("/");

    // this is to be able to make changes to .js files without having to restart everything
    context.setInitParameter("cacheControl", "no-store,no-cache,must-revalidate");

    // handling static content : create the shared folder of your web app
    context.setResourceBase("public");

    // provide the configuration object to the server
    server.setHandler(context);
    server.start();
  }

}
