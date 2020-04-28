package be.ipl.pae.ihm.servlets;

import static be.ipl.pae.ihm.Util.hasAccess;
import static be.ipl.pae.ihm.servlets.utils.ParametersUtils.getParamAsInt;
import static be.ipl.pae.ihm.servlets.utils.ParametersUtils.getParamAsUserStatus;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.objets.UserStatus;
import be.ipl.pae.biz.ucc.UserUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;
import be.ipl.pae.ihm.Util;
import be.ipl.pae.ihm.servlets.utils.ParameterException;

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

    System.out.println("POST /api/user by " + req.getRemoteAddr());

    String token = (String) req.getSession().getAttribute("token");
    if (!hasAccess(token, req.getRemoteAddr(), UserStatus.WORKER)) {
      sendError(resp, HttpServletResponse.SC_UNAUTHORIZED, "Wrong token.");
      return;
    }

    GensonBuilder gensonBuilder = Util.createGensonBuilder().acceptSingleValueAsList(true);

    try {
      int id = getParamAsInt(req, "id");
      sendSuccessWithJson(resp, "user",
          gensonBuilder.create().serialize(userUcc.getUser(id)));
    } catch (FatalException ex) {
      sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    } catch (ParameterException ex) {
      sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED, ex.getMessage());
    }
  }

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    System.out.println("PUT /api/user by " + req.getRemoteAddr());

    String token = (String) req.getSession().getAttribute("token");
    if (!hasAccess(token, req.getRemoteAddr(), UserStatus.WORKER)) {
      sendError(resp, HttpServletResponse.SC_UNAUTHORIZED, "Wrong token.");
      return;
    }

    GensonBuilder gensonBuilder = Util.createGensonBuilder();

    try {

      int userId = getParamAsInt(req, "userId");
      UserStatus status = getParamAsUserStatus(req, "status");

      UserDto userDb = userUcc.changeUserStatus(userId, status);
      sendSuccessWithJson(resp, "user", gensonBuilder.create().serialize(userDb));
    } catch (BizException | ParameterException ex) {
      sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED, ex.getMessage());
    } catch (FatalException fe) {
      sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, fe.getMessage());
    }
  }
}