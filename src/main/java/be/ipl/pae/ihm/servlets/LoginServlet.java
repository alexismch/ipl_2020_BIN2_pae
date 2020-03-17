package be.ipl.pae.ihm.servlets;

import static be.ipl.pae.util.Util.createToken;
import static be.ipl.pae.util.Util.getUId;
import static be.ipl.pae.util.Util.verifyNotEmpty;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.ucc.UserUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;

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

    String token = (String) req.getSession().getAttribute("token");
    System.out.println("\tUsed token : " + token);

    if (token != null) {
      int id = getUId(token, req.getRemoteAddr());
      UserDto utilisateurDto = ucc.recuprer(id);

      envoyerSuccesAvecJson(rep, "user", utilisateurDto.toJson());
    } else {
      envoyerErreur(rep, HttpServletResponse.SC_UNAUTHORIZED, "Token invalide");
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse rep) throws IOException {
    System.out.println("POST /api/login by " + req.getRemoteAddr());
    String pseudo = req.getParameter("pseudo");
    String passwd = req.getParameter("mdp"); // TODO: traduire

    if (verifyNotEmpty(pseudo, passwd)) {
      try {
        UserDto userDto = ucc.login(pseudo, passwd);

        HttpSession session = req.getSession();
        String token = createToken(req.getRemoteAddr(), userDto.getId());
        session.setAttribute("token", token);
        System.out.println("\tGenerated token : " + token);

        envoyerSuccesAvecJson(rep, "user", userDto.toJson());
      } catch (BizException ex) {
        envoyerErreur(rep, HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
      }
    } else {
      envoyerErreur(rep, HttpServletResponse.SC_PRECONDITION_FAILED, "Paramètres invalides");
    }
  }
}
