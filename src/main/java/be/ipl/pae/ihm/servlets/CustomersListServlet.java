package be.ipl.pae.ihm.servlets;

import be.ipl.pae.biz.dto.CustomersFilterDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.ucc.CustomerUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.FatalException;
import be.ipl.pae.util.Util;

import com.owlike.genson.GensonBuilder;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomersListServlet extends AbstractServlet {

  @Injected
  private DtoFactory dtoFactory;
  @Injected
  private CustomerUcc customerUcc;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String name = req.getParameter("name");
    String postalCodeString = req.getParameter("postalCode");
    String city = req.getParameter("city");
    int postalCode = 0;
    if (postalCodeString != null) {
      postalCode = Integer.valueOf(postalCodeString);
    }
    CustomersFilterDto customersFilterDto = dtoFactory.getCustomersFilter();
    customersFilterDto.setCity(city);
    customersFilterDto.setName(name);
    customersFilterDto.setPostalCode(postalCode);

    GensonBuilder gensonBuilder = Util.createGensonBuilder().acceptSingleValueAsList(true);

    try {
      sendSuccessWithJson(resp, "customers",
          gensonBuilder.create().serialize(customerUcc.getCustomers(customersFilterDto)));
    } catch (FatalException ex) {
      sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    }

  }

}
