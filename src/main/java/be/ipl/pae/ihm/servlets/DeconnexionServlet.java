package be.ipl.pae.ihm.servlets;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeconnexionServlet extends AbstractServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse rep) throws IOException {
    System.out.println("POST /api/deconnexion by " + req.getRemoteAddr());
    System.out.println("\tParamètres reçus : " + req.getParameterMap());
    req.getSession().invalidate();
    envoyerSucces(rep);
  }
}
