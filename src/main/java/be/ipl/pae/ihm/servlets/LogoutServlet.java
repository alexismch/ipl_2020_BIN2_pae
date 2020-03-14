package be.ipl.pae.ihm.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutServlet extends AbstractServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse rep) throws IOException {
    System.out.println("POST /api/logout by " + req.getRemoteAddr());
    // System.out.println("\tParameters received : " + req.getParameterMap());
    req.getSession().invalidate();
    sendSuccess(rep);
  }
}
