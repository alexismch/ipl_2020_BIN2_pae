package be.ipl.pae.ihm.servlets;

import static be.ipl.pae.util.Util.hasAccess;
import static be.ipl.pae.util.Util.isAllInside;
import static be.ipl.pae.util.Util.verifyNotEmpty;
import static be.ipl.pae.util.Util.verifySameLength;

import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.QuoteState;
import be.ipl.pae.biz.objets.UserStatus;
import be.ipl.pae.biz.ucc.DevelopmentTypeUcc;
import be.ipl.pae.biz.ucc.QuoteUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;
import be.ipl.pae.util.Util;

import com.owlike.genson.GensonBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QuoteServlet extends AbstractServlet {

  @Injected
  QuoteUcc quoteUcc;

  @Injected
  DtoFactory dtoFactory;

  @Injected
  DevelopmentTypeUcc developmentTypeUcc;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    System.out.println("POST /api/insertQuote by " + req.getRemoteAddr());

    String token = (String) req.getSession().getAttribute("token");
    if (!hasAccess(token, req.getRemoteAddr(), UserStatus.WORKER)) {
      sendError(resp, HttpServletResponse.SC_UNAUTHORIZED, "Wong token.");
      return;
    }

    // For testing with JSON object
    // System.out.println(Util.convertInputStreamToString(req.getInputStream()));
    // QuoteDto quote = dtoFactory.getQuote();
    // Util.createGensonBuilder().create().deserializeInto(req.getInputStream(), quote);

    // System.out.println(req.getParameterMap());

    String[] types = req.getParameterValues("types"); // only one
    if (types == null) {
      types = req.getParameterValues("types[]"); // multiple
    }
    String[] photos = req.getParameterValues("pictureData"); // only one
    if (photos == null) {
      photos = req.getParameterValues("pictureData[]"); // multiple
    }
    String[] photosTitles = req.getParameterValues("pictureTitle"); // only one
    if (photosTitles == null) {
      photosTitles = req.getParameterValues("pictureTitle[]"); // multiple
    }
    String[] photosDevelopmentTypes = req.getParameterValues("pictureDevelopmentType"); // only one
    if (photosDevelopmentTypes == null) {
      photosDevelopmentTypes = req.getParameterValues("pictureDevelopmentType[]"); // multiple
    }

    String quoteId = req.getParameter("quoteId");
    String customerIdString = req.getParameter("customerId");
    String dateString = req.getParameter("date");
    String amountString = req.getParameter("amount");
    String durationString = req.getParameter("duration");

    if (verifyNotEmpty(quoteId, customerIdString, dateString, amountString, durationString)
        && verifyNotEmpty(photos, photosTitles, photosDevelopmentTypes)
        && verifySameLength(photos, photosTitles, photosDevelopmentTypes)) {
      try {
        int customerId = Integer.parseInt(customerIdString);
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(amountString));
        int duration = Integer.parseInt(durationString);

        Date date = Date.valueOf(dateString);

        QuoteDto quoteToInsert = dtoFactory.getQuote();

        quoteToInsert.setIdQuote(quoteId);
        quoteToInsert.setIdCustomer(customerId);
        quoteToInsert.setQuoteDate(date.toLocalDate());
        quoteToInsert.setTotalAmount(amount);
        quoteToInsert.setWorkDuration(duration);
        quoteToInsert.setState(QuoteState.QUOTE_ENTERED);

        Object[] typesArray = Stream.of(types).map(Integer::valueOf).toArray();
        for (Object typeId : typesArray) {
          Integer id = (Integer) typeId;
          quoteToInsert.addDevelopmentType(developmentTypeUcc.getDevelopmentType(id));
        }

        Object[] photosTypesArray =
            Stream.of(photosDevelopmentTypes).map(Integer::valueOf).toArray();

        if (!isAllInside(typesArray, photosTypesArray)) {
          throw new Exception();
        }

        for (int i = 0; i < photos.length; i++) {
          PhotoDto photoDto = dtoFactory.getPhoto();

          photoDto.setBase64(photos[i]);
          photoDto.setIdQuote(quoteToInsert.getIdQuote());
          photoDto.setVisible(false);
          photoDto.setBeforeWork(true);
          photoDto.setTitle(photosTitles[i]);
          photoDto.setIdType((Integer) photosTypesArray[i]);

          quoteToInsert.addToListPhotoBefore(photoDto);
        }

        quoteUcc.insert(quoteToInsert);

        sendSuccess(resp);
      } catch (FatalException fatalE) {
        sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, fatalE.getMessage());
      } catch (BizException bizE) {
        sendError(resp, HttpServletResponse.SC_CONFLICT, bizE.getMessage());
      } catch (Exception ex) {
        sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED, "Paramètres invalides");
      }
    } else {
      sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED, "Paramètres invalides");
    }
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    System.out.println("GET /api/quote by " + req.getRemoteAddr());

    String token = (String) req.getSession().getAttribute("token");
    if (!hasAccess(token, req.getRemoteAddr(), UserStatus.WORKER)) {
      sendError(resp, HttpServletResponse.SC_UNAUTHORIZED, "Wong token.");
      return;
    }

    GensonBuilder genson = Util.createGensonBuilder().exclude("idQuote", PhotoDto.class);

    String quoteId = req.getParameter("quoteId");
    try {
      QuoteDto quoteDto = quoteUcc.getQuote(quoteId);
      sendSuccessWithJson(resp, "quote", genson.create().serialize(quoteDto));
    } catch (FatalException ex) {
      sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    } catch (BizException be) {
      sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED, be.getMessage());
    }
  }


  /**
   * @Override protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws
   *           ServletException, IOException { System.out.println("PUT /api/quote by " +
   *           req.getRemoteAddr());
   * 
   *           String dateString = req.getParameter("date"); String quoteId =
   *           req.getParameter("quoteId"); System.out.println("date= " + dateString);
   *           System.out.println("id = " + quoteId); if (verifyNotEmpty(dateString, quoteId)) {
   *           LocalDate date = Date.valueOf(dateString).toLocalDate();
   * 
   *           QuoteDto quoteToModify = dtoFactory.getQuote(); quoteToModify.setIdQuote(quoteId);
   *           quoteToModify.setStartDate(date);
   * 
   *           try { quoteUcc.setStartDateQuoteInDb(quoteToModify); QuoteDto quoteToReturn =
   *           quoteUcc.getQuote(quoteId);
   * 
   *           sendSuccessWithJson(resp, "quote date", genson.create().serialize(quoteToReturn)); }
   *           catch (FatalException ex) { sendError(resp,
   *           HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage()); } catch (BizException
   *           ex) { sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED, ex.getMessage()); }
   *           } else { sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED, "Paramètres
   *           invalides"); } }
   */


}
