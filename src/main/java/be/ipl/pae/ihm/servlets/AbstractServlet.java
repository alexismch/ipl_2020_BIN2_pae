package be.ipl.pae.ihm.servlets;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractServlet extends HttpServlet {

  protected void envoyerMessage(HttpServletResponse rep, String messageType, int statut, String msg)
      throws IOException {
    rep.setContentType(messageType);
    rep.setCharacterEncoding("UTF-8");
    rep.setStatus(statut);
    rep.getWriter().write(msg);
  }

  protected void envoyerErreur(HttpServletResponse rep, int numErreur, String msgErreur)
      throws IOException {
    String json = "{\""
        + "success\":\"false\", "
        + "\"error\":" + msgErreur
        + "}";
    envoyerMessage(rep, "application/json", numErreur, json);
  }
}
