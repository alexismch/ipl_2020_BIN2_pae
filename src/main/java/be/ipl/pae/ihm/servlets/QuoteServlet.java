package be.ipl.pae.ihm.servlets;

import static be.ipl.pae.ihm.Util.hasAccess;
import static be.ipl.pae.ihm.Util.isAllInside;
import static be.ipl.pae.ihm.Util.verifyNotEmpty;
import static be.ipl.pae.ihm.Util.verifySameLength;

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
import be.ipl.pae.ihm.Util;
import be.ipl.pae.ihm.servlets.utils.ParameterException;
import be.ipl.pae.ihm.servlets.utils.ParametersUtils;

import com.owlike.genson.GensonBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
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

  GensonBuilder genson = Util.createGensonBuilder().exclude("idQuote", PhotoDto.class);

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    System.out.println("POST /api/insertQuote by " + req.getRemoteAddr());

    String token = (String) req.getSession().getAttribute("token");
    if (!hasAccess(token, req.getRemoteAddr(), UserStatus.WORKER)) {
      sendError(resp, HttpServletResponse.SC_UNAUTHORIZED, "Wong token.");
      return;
    }

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

    if (verifyNotEmpty(quoteId, customerIdString, dateString, amountString, durationString)) {
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

        if (verifyNotEmpty(photos, photosTitles, photosDevelopmentTypes)) {
          Object[] photosTypesArray =
              Stream.of(photosDevelopmentTypes).map(Integer::valueOf).toArray();

          if (verifySameLength(photos, photosTitles, photosDevelopmentTypes)
              && isAllInside(typesArray, photosTypesArray)) {
            insertPhotos(quoteToInsert, photos, photosTitles, photosTypesArray);
          } else {
            throw new Exception();
          }
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

  /**
   * Insert photos into the quote.
   *
   * @param quoteDto the quote
   * @param photos photos to insert
   * @param photosTitles titles of photos
   * @param photosTypes types of photos
   */
  private void insertPhotos(QuoteDto quoteDto, String[] photos, String[] photosTitles,
      Object[] photosTypes) {
    for (int i = 0; i < photos.length; i++) {
      PhotoDto photoDto = dtoFactory.getPhoto();

      photoDto.setBase64(photos[i]);
      photoDto.setIdQuote(quoteDto.getIdQuote());
      photoDto.setVisible(false);
      photoDto.setBeforeWork(true);
      photoDto.setTitle(photosTitles[i]);
      photoDto.setIdType((Integer) photosTypes[i]);

      quoteDto.addToListPhotoBefore(photoDto);
    }
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    System.out.println("GET /api/quote by " + req.getRemoteAddr());

    String token = (String) req.getSession().getAttribute("token");
    if (!hasAccess(token, req.getRemoteAddr(), UserStatus.CUSTOMER)) {
      sendError(resp, HttpServletResponse.SC_UNAUTHORIZED, "Wrong token.");
      return;
    }

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

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    System.out.println("PUT /api/quote by " + req.getRemoteAddr());

    try {
      QuoteDto quote = dtoFactory.getQuote();
      QuoteState quoteState = ParametersUtils.getParamAsQuoteState(req, "stateId");
      String idQuote = ParametersUtils.getParam(req, "quoteId");
      String dateString = req.getParameter("date");

      if (verifyNotEmpty(dateString)) {
        LocalDate date = Date.valueOf(dateString).toLocalDate();
        quote.setStartDate(date);
      } else {
        quote.setStartDate(null);
      }
      quote.setIdQuote(idQuote);
      quote.setState(quoteState);
      sendSuccessWithJson(resp, "quote",
          genson.create().serialize(quoteUcc.useStateManager(quote)));
    } catch (FatalException ex) {
      sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    } catch (BizException | ParameterException ex) {
      sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED, ex.getMessage());
    }

  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    String quoteId = req.getParameter("quoteId");

    if (verifyNotEmpty(quoteId)) {
      QuoteDto quote = dtoFactory.getQuote();
      quote.setIdQuote(quoteId);
      try {
        quoteUcc.setStartDateQuoteInDb(quote);
        sendSuccessWithJson(resp, "quote",
            genson.create().serialize(quoteUcc.getQuote(quote.getIdQuote())));
      } catch (FatalException ex) {
        sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
      } catch (BizException ex) {
        sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED, ex.getMessage());
      }
    } else {
      sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED, "Paramètres invalides");
    }
  }


}
