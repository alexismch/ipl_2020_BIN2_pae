package be.ipl.pae.ihm.servlets;

import static be.ipl.pae.ihm.servlets.utils.Util.createToken;
import static be.ipl.pae.ihm.servlets.utils.Util.getUId;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.ucc.UserUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.ihm.servlets.utils.ParameterException;
import be.ipl.pae.ihm.servlets.utils.ParametersUtils;

import com.owlike.genson.GensonBuilder;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginServlet extends AbstractServlet {

  @Injected
  private UserUcc ucc;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String token = (String) req.getSession().getAttribute("token");
    System.out.println("\tUsed token : " + token);

    if (token == null) {
      sendSuccessWithJson(resp, "user", null);
      return;
    }

    int id = getUId(token);
    UserDto userDto;
    userDto = ucc.getUser(id);

    GensonBuilder genson = createGensonBuilder();
    sendSuccessWithJson(resp, "user", genson.create().serialize(userDto));
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String token = (String) req.getSession().getAttribute("token");
    if (token != null) {
      sendError(resp, HttpServletResponse.SC_UNAUTHORIZED, "Already connected.");
      return;
    }

    String pseudo;
    String password;
    try {
      pseudo = ParametersUtils.getParam(req, "pseudo");
      password = ParametersUtils.getParam(req, "password");
    } catch (ParameterException ex) {
      sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED, ex.getMessage());
      return;
    }

    UserDto userDto;
    try {
      userDto = ucc.login(pseudo, password);
    } catch (BizException ex) {
      sendError(resp, HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
      return;
    }

    HttpSession session = req.getSession();
    token = createToken(userDto);
    session.setAttribute("token", token);
    System.out.println("\tGenerated token : " + token);

    GensonBuilder genson = createGensonBuilder();
    sendSuccessWithJson(resp, "user", genson.create().serialize(userDto));
  }
}
