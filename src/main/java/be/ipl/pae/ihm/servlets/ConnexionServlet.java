package be.ipl.pae.ihm.servlets;

import static be.ipl.pae.util.Util.creerClef;
import static be.ipl.pae.util.Util.verifNonVide;

import be.ipl.pae.biz.dto.UtilisateurDto;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ConnexionServlet extends AbstractServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse rep) throws IOException {
    System.out.println("POST /api/connexion by " + req.getRemoteAddr());
    System.out.println("\tParamètres reçus : " + req.getParameterMap());
    String email = req.getParameter("email");
    String mdp = req.getParameter("mdp");

    if (verifNonVide(email, mdp)) {
      // TODO: envoyer les infos au biz pour vérifier
      UtilisateurDto utilisateurDTO = null;

      if (utilisateurDTO == null) {
        envoyerErreur(rep, 200, "Adresse email ou mot de passe incorrect");
      } else {
        HttpSession session = req.getSession();
        //TODO: Modifier l'id 252 par l'id récupéré via l'utilisateur
        String clef = creerClef(req.getRemoteAddr(), utilisateurDTO.getId());
        session.setAttribute("clef", clef);
        System.out.println("\nClef générée : " + clef);
        envoyerSucces(rep);
      }
    } else {
      envoyerErreur(rep, 400, "Paramètres invalides");
    }
  }
}
