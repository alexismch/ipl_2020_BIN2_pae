package be.ipl.pae.ihm.servlets;

import static be.ipl.pae.ihm.Util.createToken;
import static be.ipl.pae.ihm.Util.getUId;
import static be.ipl.pae.ihm.Util.verifyNotEmpty;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.ucc.UserUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;
import be.ipl.pae.ihm.Util;

import com.owlike.genson.GensonBuilder;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginServlet extends AbstractServlet {

  @Injected
  UserUcc ucc;

  GensonBuilder genson = Util.createGensonBuilder();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    System.out.println("GET /api/login by " + req.getRemoteAddr());

    String token = (String) req.getSession().getAttribute("token");
    System.out.println("\tUsed token : " + token);

    if (token != null) {
      int id = getUId(token, req.getRemoteAddr());
      UserDto userDto;
      try {
        userDto = ucc.getUser(id);
        sendSuccessWithJson(resp, "user", genson.create().serialize(userDto));
      } catch (FatalException ex) {
        sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
      }
    } else {
      sendSuccessWithJson(resp, "user", null);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    System.out.println("POST /api/login by " + req.getRemoteAddr());

    String token = (String) req.getSession().getAttribute("token");
    if (token != null) {
      sendError(resp, HttpServletResponse.SC_UNAUTHORIZED, "Already connected.");
      return;
    }

    String pseudo = req.getParameter("pseudo");
    String passwd = req.getParameter("password");

    if (verifyNotEmpty(pseudo, passwd)) {
      try {
        UserDto userDto = ucc.login(pseudo, passwd);

        HttpSession session = req.getSession();
        token = createToken(req.getRemoteAddr(), userDto);
        session.setAttribute("token", token);
        System.out.println("\tGenerated token : " + token);

        sendSuccessWithJson(resp, "user", genson.create().serialize(userDto));
      } catch (BizException ex) {
        sendError(resp, HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
      }
    } else {
      sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED, "Paramètres invalides");
    }
  }
}
