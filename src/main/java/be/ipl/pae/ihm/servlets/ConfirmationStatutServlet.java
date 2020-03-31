package be.ipl.pae.ihm.servlets;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.ucc.UserUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.FatalException;
import be.ipl.pae.util.Util;

import com.owlike.genson.GensonBuilder;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConfirmationStatutServlet extends AbstractServlet {
  @Injected
  private UserUcc userUcc;

  @Injected
  DtoFactory dtoFactory;

  GensonBuilder genson = Util.createGensonBuilder();

  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    System.out.println("POST /api/confirmerInscription by " + req.getRemoteAddr());
    System.out.println("teeeeeqt    " + req.getParameter("statusChoice"));
    UserDto userDb = dtoFactory.getUser();
    String pseudo = req.getParameter("pseudo");
    String statutComplet = req.getParameter("statusChoice");
    char statut;

    if (statutComplet == "CUSTOMER") {
      statut = 'c';
    } else {
      statut = 'o';
    }
    try {
      userDb = userUcc.userConfirmation(pseudo, statut);
      sendSuccessWithJson(resp, "user", genson.create().serialize(userDb));
    } catch (FatalException fe) {
      sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, fe.getMessage());
    }
  }
}

