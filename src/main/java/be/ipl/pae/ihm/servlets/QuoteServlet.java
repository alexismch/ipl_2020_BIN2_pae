package be.ipl.pae.ihm.servlets;

import static be.ipl.pae.util.Util.verifyNotEmpty;

import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.QuoteState;
import be.ipl.pae.biz.ucc.QuoteUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;
import be.ipl.pae.util.Util;

import com.owlike.genson.GensonBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QuoteServlet extends AbstractServlet {

  @Injected
  QuoteUcc quoteUcc;

  @Injected
  DtoFactory dtoFactory;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    System.out.println("POST /api/insertQuote by " + req.getRemoteAddr());

    String quoteId = req.getParameter("quoteId");
    String customerIdString = req.getParameter("customerId");
    String dateString = req.getParameter("date");
    String amountString = req.getParameter("amount");
    String durationString = req.getParameter("duration");
    String[] types = req.getParameterValues("types"); // only one
    if (types == null) {
      types = req.getParameterValues("types[]"); // multiple
    }

    if (verifyNotEmpty(quoteId, customerIdString, dateString, amountString, durationString)
        && types.length > 0) {
      try {
        int customerId = Integer.parseInt(customerIdString);
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(amountString));
        int duration = Integer.parseInt(durationString);

        Date date = Date.valueOf(dateString);

        List<Long> typesList = Stream.of(types).map(Long::valueOf).collect(Collectors.toList());

        QuoteDto quoteToInsert = dtoFactory.getQuote();

        quoteToInsert.setIdQuote(quoteId);
        quoteToInsert.setIdCustomer(customerId);
        quoteToInsert.setQuoteDate(date.toLocalDate());
        quoteToInsert.setTotalAmount(amount);
        quoteToInsert.setWorkDuration(duration);
        quoteToInsert.setState(QuoteState.QUOTE_ENTERED);



        quoteUcc.insert(quoteToInsert);

        // TODO: typesList
        sendSuccess(resp);
      } catch (FatalException fatalE) {
        fatalE.printStackTrace();
        sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, fatalE.getMessage());
      } catch (BizException bizE) {
        bizE.printStackTrace();
        sendError(resp, HttpServletResponse.SC_CONFLICT, bizE.getMessage());
      } catch (Exception ex) {
        ex.printStackTrace();
        sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED, "Paramètres invalides");
      }
    } else {
      sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED, "Paramètres invalides");
    }
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    System.out.println("GET /api/quote by " + req.getRemoteAddr());

    GensonBuilder genson = Util.createGensonBuilder();

    String quoteId = req.getParameter("quoteId");
    try {
      QuoteDto quoteDto = quoteUcc.getQuote(quoteId);
      sendSuccessWithJson(resp, "quote", genson.create().serialize(quoteDto));
    } catch (FatalException ex) {
      sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    }

  }



}
