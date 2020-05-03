package be.ipl.pae.ihm.servlets;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.UserStatus;
import be.ipl.pae.biz.ucc.UserUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.ihm.servlets.utils.ParameterException;
import be.ipl.pae.ihm.servlets.utils.ParametersUtils;
import be.ipl.pae.ihm.servlets.utils.Util;

import com.owlike.genson.GensonBuilder;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends AbstractServlet {

  static final String REGEX_MAIL = "^.+@.+\\..+";
  static final int MAX_SIZE_MAIL = 50;
  static final int MAX_SIZE_PSEUDO = 25;
  static final int MAX_SIZE_LAST_NAME = 25;
  static final int MAX_SIZE_FIRST_NAME = 25;
  static final int MAX_SIZE_PWD = 255;
  static final int MAX_SIZE_CITY = 25;

  @Injected
  private UserUcc userUcc;

  @Injected
  private DtoFactory dtoFactory;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    String token = (String) req.getSession().getAttribute("token");
    if (token != null) {
      sendError(resp, HttpServletResponse.SC_UNAUTHORIZED, "Already connected.");
      return;
    }

    String pseudo;
    String pwd;
    String lastName;
    String firstName;
    String email;
    String city;
    try {
      pseudo = ParametersUtils.getParam(req, "pseudo", MAX_SIZE_PSEUDO);
      pwd = ParametersUtils.getParam(req, "mdp", MAX_SIZE_PWD);
      lastName = ParametersUtils.getParam(req, "nom", MAX_SIZE_LAST_NAME);
      firstName = ParametersUtils.getParam(req, "prenom", MAX_SIZE_FIRST_NAME);
      email = ParametersUtils.getParam(req, "email", MAX_SIZE_MAIL);
      city = ParametersUtils.getParam(req, "ville", MAX_SIZE_CITY);
    } catch (ParameterException ex) {
      sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED, ex.getMessage());
      return;
    }

    if (!email.matches(REGEX_MAIL)) {
      sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED, "L'email est incorrect");
      return;
    }

    UserDto userDtoToInsert = dtoFactory.getUser();

    userDtoToInsert.setPseudo(pseudo);
    userDtoToInsert.setPassword(Util.cryptPwd(pwd));
    userDtoToInsert.setLastName(lastName);
    userDtoToInsert.setFirstName(firstName);
    userDtoToInsert.setEmail(email);
    userDtoToInsert.setCity(city);
    userDtoToInsert.setRegistrationDate(LocalDate.now());
    userDtoToInsert.setStatus(UserStatus.NOT_ACCEPTED);

    UserDto userDb;
    try {
      userDb = userUcc.register(userDtoToInsert);
    } catch (BizException be) {
      sendError(resp, HttpServletResponse.SC_CONFLICT, be.getMessage());
      return;
    }

    GensonBuilder genson = createGensonBuilder();
    sendSuccessWithJson(resp, "user", genson.create().serialize(userDb));
  }
}
