package be.ipl.pae.ihm.servlets;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.ucc.UserUcc;
import be.ipl.pae.dependencies.Injected;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * UserListServlet is the interface for everything that deals with a list of users. This class does
 * nothing to add a new user or update a user profile.
 */
public class UserListServlet extends AbstractServlet {

  @Injected
  private UserUcc userUcc;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    StringBuilder sb = new StringBuilder();
    sb.append('[');
    for (UserDto userDto : userUcc.getUsers()) {
      sb.append(userDto.toJson()).append(',');
    }
    sb.delete(sb.length() - 1, sb.length()).append(']');

    envoyerSuccesAvecJson(resp, "users", sb.toString());

  }
}
