package be.ipl.pae.ihm.servlets;

import static be.ipl.pae.util.Util.creerClef;
import static be.ipl.pae.util.Util.recuperUId;
import static be.ipl.pae.util.Util.verifNonVide;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.ucc.UserUcc;
import be.ipl.pae.dependencies.Injected;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginServlet extends AbstractServlet {

  @Injected
  UserUcc ucc;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse rep) throws IOException {
    System.out.println("GET /api/login by " + req.getRemoteAddr());

    String clef = (String) req.getSession().getAttribute("clef");
    System.out.println("\tClef utilisée : " + clef);

    if (clef != null) {
      int id = recuperUId(clef, req.getRemoteAddr());
      UserDto utilisateurDto = ucc.recuprer(id);

      envoyerSuccesAvecJson(rep, "utilisateur", utilisateurDto.toJson());
    } else {
      envoyerErreur(rep, HttpServletResponse.SC_UNAUTHORIZED, "Clef invalide");
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse rep) throws IOException {
    System.out.println("POST /api/login by " + req.getRemoteAddr());
    // System.out.println("\tParamètres reçus : " + req.getParameterMap());
    String pseudo = req.getParameter("pseudo");
    String mdp = req.getParameter("mdp");

    if (verifNonVide(pseudo, mdp)) {
      UserDto utilisateurDto = ucc.seConnecter(pseudo, mdp);
      if (utilisateurDto == null) {
        envoyerErreur(rep, HttpServletResponse.SC_UNAUTHORIZED, "Pseudo ou mot de passe incorrect");
      } else {
        HttpSession session = req.getSession();
        String clef = creerClef(req.getRemoteAddr(), utilisateurDto.getId());
        session.setAttribute("clef", clef);
        System.out.println("\tClef générée : " + clef);

        envoyerSuccesAvecJson(rep, "utilisateur", utilisateurDto.toJson());
      }
    } else {
      envoyerErreur(rep, HttpServletResponse.SC_PRECONDITION_FAILED, "Paramètres invalides");
    }
  }
}
