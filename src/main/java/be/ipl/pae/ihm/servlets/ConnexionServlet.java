package be.ipl.pae.ihm.servlets;

import static be.ipl.pae.util.Util.verifNonVide;

import be.ipl.pae.biz.dto.UtilisateurDTO;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConnexionServlet extends AbstractHttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse rep)
      throws IOException {
    String email = req.getParameter("email");
    String mdp = req.getParameter("mdp");

    if (verifNonVide(email, mdp)) {
      //TODO: envoyer les infos au biz pour vérifier
      UtilisateurDTO utilisateurDTO = null;
      if (utilisateurDTO == null) {
        envoyerErreur(rep, 200, "Adresse email ou mot de passe incorrect");
      }
    } else {
      envoyerErreur(rep, 400, "Paramètres invalides");
    }
  }
}
