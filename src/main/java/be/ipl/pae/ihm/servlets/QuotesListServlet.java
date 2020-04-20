package be.ipl.pae.ihm.servlets;


import static be.ipl.pae.util.Util.hasAccess;

import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.biz.dto.QuotesFilterDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.UserStatus;
import be.ipl.pae.biz.ucc.DevelopmentTypeUcc;
import be.ipl.pae.biz.ucc.QuoteUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;
import be.ipl.pae.util.Util;

import com.owlike.genson.GensonBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QuotesListServlet extends AbstractServlet {

  @Injected
  private DtoFactory dtoFactory;
  @Injected
  private QuoteUcc quoteUcc;
  @Injected
  private DevelopmentTypeUcc developmentTypeUcc;

  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    System.out.println("GET /api/quotes-list by " + req.getRemoteAddr());

    String token = (String) req.getSession().getAttribute("token");
    if (!hasAccess(token, req.getRemoteAddr(), UserStatus.WORKER)) {
      sendError(resp, HttpServletResponse.SC_UNAUTHORIZED, "Wrong token.");
      return;
    }

    String quoteDateString = req.getParameter("quoteDate");
    String maxAmountString = req.getParameter("montantMax");
    String minAmountString = req.getParameter("montantMin");
    String[] types = req.getParameterValues("types"); // only one
    if (types == null) {
      types = req.getParameterValues("types[]"); // multiple
    }
    int minAmount = -1;
    int maxAmount = -1;
    LocalDate quoteDate = null;
    if (quoteDateString != null) {
      quoteDate = LocalDate.parse(quoteDateString);
    }
    if (maxAmountString != null) {
      maxAmount = Integer.parseInt(maxAmountString);
    }
    if (minAmountString != null) {
      minAmount = Integer.parseInt(minAmountString);
    }


    String name = req.getParameter("name");

    QuotesFilterDto quotesFilterDto = dtoFactory.getQuotesFilter();
    quotesFilterDto.setCustomerName(name);

    ArrayList<DevelopmentTypeDto> listDevelopment = new ArrayList<>();
    if (types != null) {
      Object[] typesArray = Stream.of(types).map(Integer::valueOf).toArray();
      for (Object typeId : typesArray) {
        Integer id = (Integer) typeId;
        try {
          listDevelopment.add(developmentTypeUcc.getDevelopmentType(id));
        } catch (BizException ex) {
          // TODO Auto-generated catch block
          ex.printStackTrace();
        } catch (FatalException ex) {
          // TODO Auto-generated catch block
          ex.printStackTrace();
        }
      }
    }
    quotesFilterDto.setDevelopmentType(listDevelopment);
    quotesFilterDto.setQuoteDate(quoteDate);
    quotesFilterDto.setTotalAmountMax(maxAmount);
    quotesFilterDto.setTotalAmountMin(minAmount);


    GensonBuilder gensonBuilder = Util.createGensonBuilder().acceptSingleValueAsList(true);
    try {
      quoteUcc.getQuotesFiltered(quotesFilterDto);
    } catch (FatalException ex) {
      // TODO Auto-generated catch block
      ex.printStackTrace();
    }
    try {
      sendSuccessWithJson(resp, "quotesList",
          gensonBuilder.create().serialize(quoteUcc.getQuotesFiltered(quotesFilterDto)));
    } catch (FatalException ex) {
      // TODO Auto-generated catch block
      ex.printStackTrace();
    }
  }
}
