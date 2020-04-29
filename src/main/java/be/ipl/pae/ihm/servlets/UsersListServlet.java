package be.ipl.pae.ihm.servlets;

import static be.ipl.pae.ihm.Util.hasAccess;

import be.ipl.pae.biz.dto.UsersFilterDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.UserStatus;
import be.ipl.pae.biz.ucc.UserUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.FatalException;
import be.ipl.pae.ihm.Util;

import com.owlike.genson.GensonBuilder;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * UsersListServlet is the interface for everything that deals with a list of users. This class does
 * nothing to add a new user or update a user profile.
 */
public class UsersListServlet extends AbstractServlet {

  @Injected
  DtoFactory dtoFactory;
  @Injected
  private UserUcc userUcc;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    System.out.println("GET /api/users-list by " + req.getRemoteAddr());

    String token = (String) req.getSession().getAttribute("token");
    if (!hasAccess(token, UserStatus.WORKER)) {
      sendError(resp, HttpServletResponse.SC_UNAUTHORIZED, "Wrong token.");
      return;
    }

    String name = req.getParameter("name");
    String city = req.getParameter("city");

    UsersFilterDto usersFilterDto = dtoFactory.getUsersFilterDto();
    usersFilterDto.setName(name);
    usersFilterDto.setCity(city);

    GensonBuilder gensonBuilder = Util.createGensonBuilder().acceptSingleValueAsList(true);

    try {
      sendSuccessWithJson(resp, "users",
          gensonBuilder.create().serialize(userUcc.getUsers(usersFilterDto)));
    } catch (FatalException ex) {
      sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    }

  }
}
