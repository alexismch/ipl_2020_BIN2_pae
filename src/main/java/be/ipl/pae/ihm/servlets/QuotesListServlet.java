package be.ipl.pae.ihm.servlets;


import static be.ipl.pae.ihm.Util.hasAccess;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.dto.QuotesFilterDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.UserStatus;
import be.ipl.pae.biz.ucc.CustomerUcc;
import be.ipl.pae.biz.ucc.DevelopmentTypeUcc;
import be.ipl.pae.biz.ucc.QuoteUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.ihm.Util;

import com.owlike.genson.GensonBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QuotesListServlet extends AbstractServlet {

  @Injected
  private DtoFactory dtoFactory;

  @Injected
  private QuoteUcc quoteUcc;

  @Injected
  private CustomerUcc customerUcc;

  @Injected
  private DevelopmentTypeUcc developmentTypeUcc;

  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    System.out.println("GET /api/quotes-list by " + req.getRemoteAddr());

    String token = (String) req.getSession().getAttribute("token");
    if (!hasAccess(token, UserStatus.CUSTOMER)) {
      sendError(resp, HttpServletResponse.SC_UNAUTHORIZED, "Wrong token.");
      return;
    }

    // Managing the quotes filters
    String quoteDateString = req.getParameter("quoteDate");
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
    String maxAmountString = req.getParameter("montantMax");
    if (maxAmountString != null) {
      maxAmount = Integer.parseInt(maxAmountString);
    }
    String minAmountString = req.getParameter("montantMin");
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
          ex.printStackTrace();
        }
      }
    }
    quotesFilterDto.setDevelopmentType(listDevelopment);
    quotesFilterDto.setQuoteDate(quoteDate);
    quotesFilterDto.setTotalAmountMax(maxAmount);
    quotesFilterDto.setTotalAmountMin(minAmount);
    // end



    // Handling the calls depending on the user
    int id;
    List<QuoteDto> listToReturn;
    String status = Util.getUStatus(token);

    if (status.equals(UserStatus.CUSTOMER.getCode())) {
      // A customer wants to get all of his quotes
      int idUser = Util.getUId(req.getSession().getAttribute("token").toString());

      CustomerDto customerDto = customerUcc.getCustomerByIdUser(idUser);
      if (customerDto != null) {
        int idCustomer = customerDto.getIdCustomer();
        listToReturn = quoteUcc.getQuotesFiltered(quotesFilterDto, idCustomer);
      } else {
        listToReturn = new ArrayList<>();
      }

    } else {
      String idCustomerString = req.getParameter("idCustomer");

      if (idCustomerString == null) {
        // A worker wants to get all the quotes of all the users
        listToReturn = quoteUcc.getQuotesFiltered(quotesFilterDto);
      } else {
        // A worker wants to get the quotes of a specific user
        id = Integer.parseInt(idCustomerString);
        listToReturn = quoteUcc.getQuotesFiltered(quotesFilterDto, id);
      }
    }

    for (QuoteDto quoteDto2 : listToReturn) {
      for (DevelopmentTypeDto dev : quoteDto2.getDevelopmentTypes()) {
        System.out.println("test dev : " + dev.getTitle());

      }
    }

    GensonBuilder gensonBuilder = Util.createGensonBuilder().acceptSingleValueAsList(true);

    sendSuccessWithJson(resp, "quotesList", gensonBuilder.create().serialize(listToReturn));
  }
}
