package be.ipl.pae.ihm.servlets;


import static be.ipl.pae.util.Util.hasAccess;

import be.ipl.pae.biz.dto.QuotesFilterDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.UserStatus;
import be.ipl.pae.biz.ucc.QuoteUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.util.Util;

import com.owlike.genson.GensonBuilder;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QuotesListServlet extends AbstractServlet {

  @Injected
  private DtoFactory dtoFactory;
  @Injected
  private QuoteUcc quoteUcc;

  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    System.out.println("GET /api/quotes-list by " + req.getRemoteAddr());

    String token = (String) req.getSession().getAttribute("token");
    if (!hasAccess(token, req.getRemoteAddr(), UserStatus.WORKER)) {
      sendError(resp, HttpServletResponse.SC_UNAUTHORIZED, "Wong token.");
      return;
    }

    String quoteDateString = req.getParameter("dateDevis");
    String minAmountString = req.getParameter("montantMin");
    String maxAmountString = req.getParameter("montantMax");
    // String developmentType = req.getParameter("amenagements");
    int minAmount = -1;
    int maxAmount = -1;
    if (quoteDateString != null) {
      LocalDate.parse(quoteDateString);
    }
    if (maxAmountString != null) {
      maxAmount = Integer.parseInt(maxAmountString);
    }
    if (minAmountString != null) {
      minAmount = Integer.parseInt(minAmountString);
    }
    LocalDate quoteDate = null;
    String name = req.getParameter("name");

    QuotesFilterDto quotesFilterDto = dtoFactory.getQuotesFilter();
    quotesFilterDto.setCustomerName(name);
    // quotesFilterDto.setDevelopmentType(developmentType);
    quotesFilterDto.setQuoteDate(quoteDate);
    quotesFilterDto.setTotalAmountMax(maxAmount);
    quotesFilterDto.setTotalAmountMin(minAmount);


    GensonBuilder gensonBuilder = Util.createGensonBuilder().acceptSingleValueAsList(true);

    try {
      sendSuccessWithJson(resp, "quotesList",
          gensonBuilder.create().serialize(quoteUcc.getQuotes()));
    } catch (BizException ex) {
      sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }
}
