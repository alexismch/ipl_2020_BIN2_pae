package be.ipl.pae.ihm.servlets;

import static be.ipl.pae.util.Util.creerClef;
import static be.ipl.pae.util.Util.verifNonVide;

import be.ipl.pae.biz.dto.UtilisateurDto;
import be.ipl.pae.biz.ucc.UtilisateurUcc;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import config.InjectionService;


public class ConnexionServlet extends AbstractServlet {

  UtilisateurUcc ucc = InjectionService.getDependance(UtilisateurUcc.class);

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    System.out.println("GET /api/connexion by " + req.getRemoteAddr());

    Object clef = req.getSession().getAttribute("clef");
    boolean estConnecte = clef != null;
    String json;

    if (estConnecte) {
      // TODO renvoyer un UtilisateurDto sérialisé au format JSON
      json = "{\"statut\":\"ouvrier\"}";
    } else {
      json = "{}";
    }
    envoyerMessage(resp, "application/json", 200, json);

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse rep) throws IOException {
    System.out.println("POST /api/connexion by " + req.getRemoteAddr());
    System.out.println("\tParamètres reçus : " + req.getParameterMap());
    String pseudo = req.getParameter("pseudo");
    String mdp = req.getParameter("mdp");

    if (verifNonVide(pseudo, mdp)) {
      UtilisateurDto utilisateurDto = ucc.seConnecter(pseudo, mdp);

      if (utilisateurDto == null) {
        envoyerErreur(rep, HttpServletResponse.SC_UNAUTHORIZED, "Pseudo ou mot de passe incorrect");
      } else {
        HttpSession session = req.getSession();
        String clef = creerClef(req.getRemoteAddr(), utilisateurDto.getId());
        session.setAttribute("clef", clef);
        System.out.println("\nClef générée : " + clef);

        // TODO renvoyer un UtilisateurDto sérialisé au format JSON
        String json = "{\"statut\":\"ouvrier\"}";
        envoyerMessage(rep, "application/json", 200, json);
      }
    } else {
      envoyerErreur(rep, HttpServletResponse.SC_PRECONDITION_FAILED, "Paramètres invalides");
    }
  }
}
