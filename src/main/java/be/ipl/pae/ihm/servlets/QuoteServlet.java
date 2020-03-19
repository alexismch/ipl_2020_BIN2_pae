package be.ipl.pae.ihm.servlets;

import static be.ipl.pae.util.Util.verifyMapTypes;
import static be.ipl.pae.util.Util.verifyNotEmpty;

import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.State;
import be.ipl.pae.biz.ucc.QuoteUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;

import com.owlike.genson.Genson;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QuoteServlet extends AbstractServlet {

  @Injected
  QuoteUcc quoteUcc;

  @Injected
  DtoFactory dtoFactory;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse rep) throws IOException {
    System.out.println("POST /api/insertQuote by " + req.getRemoteAddr());

    String qId = req.getParameter("qId");
    String cIdString = req.getParameter("cId");
    String dateString = req.getParameter("date");
    String amountString = req.getParameter("amount");
    String durationString = req.getParameter("duration");
    String typesJson = req.getParameter("types");

    if (verifyNotEmpty(qId, cIdString, dateString, amountString, durationString, typesJson)) {
      try {
        int cId = Integer.parseInt(cIdString);
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(amountString));
        int duration = Integer.parseInt(durationString);

        Date date = Date.valueOf(dateString);

        Genson genson = new Genson();
        Map types = genson.deserialize(typesJson, Map.class);

        if (verifyMapTypes(types, Long.class)) {
          QuoteDto quoteToInsert = dtoFactory.getQuote();

          quoteToInsert.setIdQuote(qId);
          quoteToInsert.setIdCustomer(cId);
          quoteToInsert.setQuoteDate(date);
          quoteToInsert.setTotalAmount(amount);
          quoteToInsert.setWorkDuration(duration);
          quoteToInsert.setState(State.DEVIS_INTRODUIT);

          quoteUcc.insert(quoteToInsert);

          sendSuccess(rep);
        } else {
          sendError(rep, HttpServletResponse.SC_PRECONDITION_FAILED,
              "Types d'aménagements invalides");
        }
      } catch (FatalException e) {
        sendError(rep, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
      } catch (BizException e) {
        sendError(rep, HttpServletResponse.SC_CONFLICT, e.getMessage());
      }
    } else {
      sendError(rep, HttpServletResponse.SC_PRECONDITION_FAILED, "Paramètres invalides");
    }
  }
}
