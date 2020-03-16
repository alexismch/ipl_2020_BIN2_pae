package be.ipl.pae.ihm.servlets;

import static be.ipl.pae.util.Util.checkFormat;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.UserStatus;
import be.ipl.pae.biz.ucc.UserUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.util.Util;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends AbstractServlet {

  @Injected
  UserUcc userUcc;

  @Injected
  DtoFactory dtoFactory;


  final static String REGEX_MAIL = "^.+\\@.+\\..+";
  final static int MAX_SIZE_MAIL = 50;
  final static int MAX_SIZE_PSEUDO = 25;
  final static int MAX_SIZE_LAST_NAME = 25;
  final static int MAX_SIZE_FIRST_NAME = 25;
  final static int MAX_SIZE_PWD = 255;
  final static int MAX_SIZE_CITY = 25;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    System.out.println("POST /api/register by " + req.getRemoteAddr());

    String pseudo = req.getParameter("pseudo");
    String pwd = req.getParameter("mdp");
    String lastName = req.getParameter("nom");
    String firstName = req.getParameter("prenom");
    String email = req.getParameter("email");
    String city = req.getParameter("ville");

    if (checkFormat(email, MAX_SIZE_MAIL, REGEX_MAIL) && checkFormat(pseudo, MAX_SIZE_PSEUDO)
        && checkFormat(pwd, MAX_SIZE_PWD) && checkFormat(lastName, MAX_SIZE_LAST_NAME)
        && checkFormat(firstName, MAX_SIZE_FIRST_NAME) && checkFormat(city, MAX_SIZE_CITY)) {

      UserDto userDtoToInsert = dtoFactory.getUtilisateur();

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
        envoyerSuccesAvecJson(resp, "user", userDb.toJson());
      } catch (Exception e) {
        envoyerErreur(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
      }

    } else {
      envoyerErreur(resp, HttpServletResponse.SC_PRECONDITION_FAILED, "Paramètres invalides");
    }
  }
}
