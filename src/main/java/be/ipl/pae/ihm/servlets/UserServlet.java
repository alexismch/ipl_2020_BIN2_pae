package be.ipl.pae.ihm.servlets;

import static be.ipl.pae.util.Util.hasAccess;

import be.ipl.pae.biz.objets.UserStatus;
import be.ipl.pae.biz.ucc.UserUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.FatalException;
import be.ipl.pae.util.Util;

import com.owlike.genson.GensonBuilder;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserServlet extends AbstractServlet {

  @Injected
  private UserUcc userUcc;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    String token = (String) req.getSession().getAttribute("token");
    if (!hasAccess(token, req.getRemoteAddr(), UserStatus.WORKER)) {
      sendError(resp, HttpServletResponse.SC_UNAUTHORIZED, "Wong token.");
      return;
    }

    int id;
    String idString = req.getParameter("id");
    if (idString != null) {
      try {
        id = Integer.parseInt(idString);
      } catch (NumberFormatException ex) {
        sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED,
            "L'id d'utilisteur passé en paramètre n'est pas un nombre entier");
        return;
      }
    } else {
      sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED,
          "Un id d'utilisteur doit être passé en paramètre");
      return;
    }

    GensonBuilder gensonBuilder = Util.createGensonBuilder().acceptSingleValueAsList(true);

    try {
      sendSuccessWithJson(resp, "userDetail",
          gensonBuilder.create().serialize(userUcc.getUser(id)));
    } catch (FatalException ex) {
      sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }
}