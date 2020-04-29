package be.ipl.pae.ihm.servlets;

import static be.ipl.pae.ihm.Util.hasAccess;

import be.ipl.pae.biz.dto.CustomersFilterDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.UserStatus;
import be.ipl.pae.biz.ucc.CustomerUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.FatalException;
import be.ipl.pae.ihm.Util;

import com.owlike.genson.GensonBuilder;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomersListServlet extends AbstractServlet {

  @Injected
  private DtoFactory dtoFactory;
  @Injected
  private CustomerUcc customerUcc;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String token = (String) req.getSession().getAttribute("token");
    if (!hasAccess(token, UserStatus.WORKER)) {
      sendError(resp, HttpServletResponse.SC_UNAUTHORIZED, "Wrong token.");
      return;
    }

    CustomersFilterDto customersFilterDto = dtoFactory.getCustomersFilter();

    String name = req.getParameter("name");
    customersFilterDto.setName(name);

    String city = req.getParameter("city");
    customersFilterDto.setCity(city);

    String postalCodeString = req.getParameter("postalCode");
    int postalCode = 0;
    if (postalCodeString != null) {
      postalCode = Integer.parseInt(postalCodeString);
    }
    customersFilterDto.setPostalCode(postalCode);

    boolean onlyNotLinked = false;
    String onlyNotLinkedString = req.getParameter("onlyNotLinked");
    if (onlyNotLinkedString != null) {
      onlyNotLinked = Boolean.parseBoolean(onlyNotLinkedString);
    }
    customersFilterDto.setOnlyNotLinked(onlyNotLinked);

    GensonBuilder gensonBuilder = Util.createGensonBuilder().acceptSingleValueAsList(true);

    try {
      sendSuccessWithJson(resp, "customers",
          gensonBuilder.create().serialize(customerUcc.getCustomers(customersFilterDto)));
    } catch (FatalException ex) {
      sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    }

  }

}
