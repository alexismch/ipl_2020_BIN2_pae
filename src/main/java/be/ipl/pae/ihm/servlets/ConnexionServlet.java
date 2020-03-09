package be.ipl.pae.ihm.servlets;

import static be.ipl.pae.util.Util.creerClef;
import static be.ipl.pae.util.Util.verifNonVide;

import be.ipl.pae.biz.dto.UtilisateurDto;
import be.ipl.pae.biz.objets.DtoFactory;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import config.InjectionService;


public class ConnexionServlet extends AbstractServlet {

  DtoFactory factory = InjectionService.getDependency(DtoFactory.class);

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse rep) throws IOException {
    System.out.println("POST /api/connexion by " + req.getRemoteAddr());
    System.out.println("\tParamètres reçus : " + req.getParameterMap());
    String email = req.getParameter("email");
    String mdp = req.getParameter("mdp");

    if (verifNonVide(email, mdp)) {
      // TODO: envoyer les infos au biz pour vérifier
      UtilisateurDto utilisateurDto = factory.getUtilisateur();

      if (utilisateurDto == null) {
        envoyerErreur(rep, 401, "Adresse email ou mot de passe incorrect");
      } else {
        HttpSession session = req.getSession();
        // TODO: Modifier l'id 252 par l'id récupéré via l'utilisateur
        String clef = creerClef(req.getRemoteAddr(), utilisateurDto.getId());
        session.setAttribute("clef", clef);
        System.out.println("\nClef générée : " + clef);
        envoyerSucces(rep);
      }
    } else {
      envoyerErreur(rep, 400, "Paramètres invalides");
    }
  }
}
