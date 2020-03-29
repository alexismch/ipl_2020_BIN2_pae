package be.ipl.pae.ihm.servlets;

import static be.ipl.pae.util.Util.checkFormat;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.UserStatus;
import be.ipl.pae.biz.ucc.UserUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;
import be.ipl.pae.util.Util;

import com.owlike.genson.GensonBuilder;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends AbstractServlet {

  static final String REGEX_MAIL = "^.+\\@.+\\..+";
  static final int MAX_SIZE_MAIL = 50;
  static final int MAX_SIZE_PSEUDO = 25;
  static final int MAX_SIZE_LAST_NAME = 25;
  static final int MAX_SIZE_FIRST_NAME = 25;
  static final int MAX_SIZE_PWD = 255;
  static final int MAX_SIZE_CITY = 25;

  GensonBuilder genson = Util.createGensonBuilder();

  @Injected
  UserUcc userUcc;

  @Injected
  DtoFactory dtoFactory;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    System.out.println("POST /api/register by " + req.getRemoteAddr());

    String token = (String) req.getSession().getAttribute("token");
    if (token != null) {
      sendError(resp, HttpServletResponse.SC_UNAUTHORIZED, "Already connected.");
      return;
    }

    String pseudo = req.getParameter("pseudo");
    String pwd = req.getParameter("mdp");
    String lastName = req.getParameter("nom");
    String firstName = req.getParameter("prenom");
    String email = req.getParameter("email");
    String city = req.getParameter("ville");

    if (checkFormat(email, MAX_SIZE_MAIL, REGEX_MAIL) && checkFormat(pseudo, MAX_SIZE_PSEUDO)
        && checkFormat(pwd, MAX_SIZE_PWD) && checkFormat(lastName, MAX_SIZE_LAST_NAME)
        && checkFormat(firstName, MAX_SIZE_FIRST_NAME) && checkFormat(city, MAX_SIZE_CITY)) {

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
        sendSuccessWithJson(resp, "user", genson.create().serialize(userDb));
      } catch (BizException be) {
        sendError(resp, HttpServletResponse.SC_CONFLICT, be.getMessage());
      } catch (FatalException fe) {
        sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, fe.getMessage());
      }

    } else {
      sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED, "Paramètres invalides");
    }
  }
}
