package be.ipl.pae.ihm.servlets;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.biz.objets.UserStatus;
import be.ipl.pae.biz.ucc.CustomerUcc;
import be.ipl.pae.biz.ucc.QuoteUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;
import be.ipl.pae.util.Util;

import com.owlike.genson.GensonBuilder;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomerDetailsServlet extends AbstractServlet {

  @Injected
  private QuoteUcc quoteUcc;

  @Injected
  private CustomerUcc customerUcc;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    int id = 0;

    String status =
        Util.getUStatus(req.getSession().getAttribute("token").toString(), req.getRemoteAddr());

    if (status.equals(UserStatus.CUSTOMER.getCode())) {

      int idUtilisateur =
          Util.getUId(req.getSession().getAttribute("token").toString(), req.getRemoteAddr());

      try {

        CustomerDto customerDto = null;
        customerDto = customerUcc.getCustomerByIdUser(idUtilisateur);
        id = customerDto.getIdCustomer();

      } catch (FatalException e) {
        e.printStackTrace();
      }

    } else {
      String idString = req.getParameter("idCustomer");
      if (idString != null) {
        id = Integer.parseInt(idString);
      }
    }
    GensonBuilder gensonBuilder = Util.createGensonBuilder().acceptSingleValueAsList(true);
    try {
      sendSuccessWithJson(resp, "customerDetails",
          gensonBuilder.create().serialize(quoteUcc.getCustomerQuotes(id)));
    } catch (BizException ex) {
      sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }
}
