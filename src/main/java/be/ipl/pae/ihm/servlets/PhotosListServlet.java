package be.ipl.pae.ihm.servlets;

import static be.ipl.pae.ihm.Util.verifyNotEmpty;

import be.ipl.pae.biz.ucc.PhotoUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.ihm.Util;

import com.owlike.genson.GensonBuilder;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PhotosListServlet extends AbstractServlet {

  @Injected
  private PhotoUcc photoUcc;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    System.out.println("GET /api/photos-list by " + req.getRemoteAddr());

    String typeId = req.getParameter("typeId");

    try {
      GensonBuilder gensonBuilder = Util.createGensonBuilder().acceptSingleValueAsList(true);

      if (typeId == null) {
        sendSuccessWithJson(resp, "photosList",
            gensonBuilder.create().serialize(photoUcc.getVisiblePhotos()));
      } else if (verifyNotEmpty(typeId)) {
        int id = Integer.parseInt(typeId);
        sendSuccessWithJson(resp, "photosList",
            gensonBuilder.create().serialize(photoUcc.getVisiblePhotos(id)));
      } else {
        sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED, "Paramètres invalides");
      }
    } catch (BizException bizE) {
      sendError(resp, HttpServletResponse.SC_CONFLICT, bizE.getMessage());
    } catch (Exception ex) {
      sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED, "Paramètres invalides");
    }
  }
}
