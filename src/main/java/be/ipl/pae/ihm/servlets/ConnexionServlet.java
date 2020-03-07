package be.ipl.pae.ihm.servlets;

import be.ipl.pae.biz.dto.UtilisateurDto;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static be.ipl.pae.util.Util.verifNonVide;

public class ConnexionServlet extends AbstractServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse rep) throws IOException {
    System.out.println("POST /api/connexion");
    System.out.println(req.getParameterMap());
    String email = req.getParameter("email");
    String mdp = req.getParameter("mdp");

    if (verifNonVide(email, mdp)) {
      System.out.println("Connexion de " + email + " avec le mot de passe " + mdp);
      // TODO: envoyer les infos au biz pour vérifier
      UtilisateurDto utilisateurDTO = null;
      if (utilisateurDTO == null) {
        envoyerErreur(rep, 200, "Adresse email ou mot de passe incorrect");
      } else {
        // TODO cree une session
      }
    } else {
      envoyerErreur(rep, 400, "Paramètres invalides");
    }
  }
}
