package be.ipl.pae.ihm.servlets;

import static be.ipl.pae.util.Util.hasAccess;
import static be.ipl.pae.util.Util.isAllInside;
import static be.ipl.pae.util.Util.verifyNotEmpty;
import static be.ipl.pae.util.Util.verifySameLength;

import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.UserStatus;
import be.ipl.pae.biz.ucc.DevelopmentTypeUcc;
import be.ipl.pae.biz.ucc.PhotoUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PhotoServlet extends AbstractServlet {

  @Injected
  private DevelopmentTypeUcc developmentTypeUcc;

  @Injected
  private DtoFactory dtoFactory;

  @Injected
  private PhotoUcc photoUcc;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String token = (String) req.getSession().getAttribute("token");
    if (!hasAccess(token, req.getRemoteAddr(), UserStatus.WORKER)) {
      sendError(resp, HttpServletResponse.SC_UNAUTHORIZED, "Wrong token.");
      return;
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
    String[] photosIsVisible = req.getParameterValues("pictureIsVisible"); // only one
    if (photosIsVisible == null) {
      photosIsVisible = req.getParameterValues("pictureIsVisible[]"); // multiple
    }
    String quoteId = req.getParameter("quoteId");

    if (verifyNotEmpty(quoteId)
        && verifyNotEmpty(photos, photosTitles, photosDevelopmentTypes, photosIsVisible)
        && verifySameLength(photos, photosTitles, photosDevelopmentTypes, photosIsVisible)) {

      try {
        Object[] types = developmentTypeUcc.getDevelopmentTypes(quoteId).stream()
            .map(DevelopmentTypeDto::getIdType).toArray();
        Object[] photosTypesArray =
            Stream.of(photosDevelopmentTypes).map(Integer::valueOf).toArray();

        if (isAllInside(types, photosTypesArray)) {
          List<PhotoDto> photoDtos = createObjects(quoteId, photos, photosTitles, photosTypesArray,
              photosIsVisible);
          photoUcc.insert(photoDtos);
        } else {
          throw new Exception();
        }
      } catch (BizException bizE) {
        sendError(resp, HttpServletResponse.SC_CONFLICT, bizE.getMessage());
      } catch (Exception ex) {
        sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED, "Paramètres invalides");
      }
    } else {
      sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED, "Paramètres invalides");
    }
  }

  private List<PhotoDto> createObjects(String quoteId, String[] photos,
      String[] photosTitles, Object[] photosDevelopmentTypes, String[] photosIsVisible) {
    List<PhotoDto> list = new ArrayList<>();

    for (int i = 0; i < photos.length; i++) {
      PhotoDto photo = dtoFactory.getPhoto();

      photo.setBase64(photos[i]);
      photo.setIdQuote(quoteId);
      photo.setVisible(Boolean.parseBoolean(photosIsVisible[i]));
      photo.setBeforeWork(false);
      photo.setTitle(photosTitles[i]);
      photo.setIdType((Integer) photosDevelopmentTypes[i]);

      list.add(photo);
    }
    return list;
  }
}
