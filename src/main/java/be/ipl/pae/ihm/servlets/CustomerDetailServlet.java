package be.ipl.pae.ihm.servlets;

import static be.ipl.pae.util.Util.hasAccess;

import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.UserStatus;
import be.ipl.pae.biz.ucc.QuoteUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.util.Util;

import com.owlike.genson.GensonBuilder;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomerDetailServlet extends AbstractServlet {

  @Injected
  private DtoFactory dtoFactory;

  @Injected
  private QuoteUcc quoteUcc;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String token = (String) req.getSession().getAttribute("token");
    if (!hasAccess(token, req.getRemoteAddr(), UserStatus.CUSTOMER)) {
      sendError(resp, HttpServletResponse.SC_UNAUTHORIZED, "Wong token.");
      return;
    }

    int id = 0;
    String idString = req.getParameter("idCustomer");
    if (idString != null) {
      id = Integer.parseInt(idString);
    }

    GensonBuilder gensonBuilder = Util.createGensonBuilder().acceptSingleValueAsList(true);

    // try {
    // sendSuccessWithJson(resp, "customers",
    // gensonBuilder.create().serialize(quoteUcc.getQuote(idString)));
    // } catch (FatalException ex) {
    // sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    // }
  }



}
