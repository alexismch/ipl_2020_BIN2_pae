package be.ipl.pae.ihm.servlets;

import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.ucc.QuoteUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.util.Util;

import com.owlike.genson.GensonBuilder;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomerDetailServlet extends AbstractServlet {

  @Injected
  private DtoFactory dtoFactory;

  @Injected
  private QuoteUcc quoteUcc;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    int id = 0;
    String idString = req.getParameter("idCustomer");
    if (idString != null) {
      id = Integer.valueOf(idString);
    }
    System.out.println("ici");
    GensonBuilder gensonBuilder = Util.createGensonBuilder().acceptSingleValueAsList(true);

    try {
      sendSuccessWithJson(resp, "customerDetail",
          gensonBuilder.create().serialize(quoteUcc.getCustomerQuotes(id)));
    } catch (BizException ex) {
      sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }
}
