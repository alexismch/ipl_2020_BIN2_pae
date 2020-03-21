package be.ipl.pae.ihm.servlets;

import be.ipl.pae.biz.dto.UsersFilterDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.UserStatus;
import be.ipl.pae.biz.ucc.UserUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.util.Util;

import com.owlike.genson.GensonBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * UserListServlet is the interface for everything that deals with a list of users. This class does
 * nothing to add a new user or update a user profile.
 */
public class UserListServlet extends AbstractServlet {

  @Injected
  DtoFactory dtoFactory;
  @Injected
  private UserUcc userUcc;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    String name = req.getParameter("name");
    String city = req.getParameter("city");

    UsersFilterDto usersFilterDto = dtoFactory.getUsersFilterDto();
    usersFilterDto.setName(name);
    usersFilterDto.setCity(city);

    GensonBuilder gensonBuilder = new GensonBuilder()
        .exclude("password")
        .useMethods(true)
        .acceptSingleValueAsList(true);

    Util.addSerializer(gensonBuilder, LocalDate.class,
        (value, writer, ctx) -> writer.writeString(value.format(DateTimeFormatter.ISO_LOCAL_DATE)));

    Util.addSerializer(gensonBuilder, UserStatus.class,
        (value, writer, ctx) -> writer.writeString(value.getName()));

    sendSuccessWithJson(resp, "users",
        gensonBuilder.create().serialize(userUcc.getUsers(usersFilterDto)));

  }
}
