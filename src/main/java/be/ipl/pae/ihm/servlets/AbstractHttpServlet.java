package be.ipl.pae.ihm.servlets;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractHttpServlet extends HttpServlet {

  /**
   * Renvoie un message à la requête.
   *
   * @param rep         la requête qui va recevoir le message
   * @param messageType le type de message à renvoyer
   * @param statut      le statut de la requête en retour
   * @param msg         le message à renvoyer
   * @throws IOException en cas de problème avec le writer du message
   */
  protected void envoyerMessage(HttpServletResponse rep, String messageType, int statut, String msg)
      throws IOException {
    rep.setContentType(messageType);
    rep.setCharacterEncoding("UTF-8");
    rep.setStatus(statut);
    rep.getWriter().write(msg);
  }

  /**
   * Renvoie un message d'erreur à la requête.
   *
   * @param rep    la requête qui va recevoir le message
   * @param statut le statut de la requête en retour
   * @param msg    le message à renvoyer
   * @throws IOException en cas de problème avec le writer du message
   */
  protected void envoyerErreur(HttpServletResponse rep, int statut, String msg)
      throws IOException {
    String json = "{\""
        + "success\":\"false\", "
        + "\"error\":" + msg
        + "}";
    envoyerMessage(rep, "application/json", statut, json);
  }
}
