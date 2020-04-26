package be.ipl.pae.ihm.servlets;

import be.ipl.pae.biz.ucc.PhotoUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;
import be.ipl.pae.ihm.Util;

import com.owlike.genson.GensonBuilder;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PhotoPrincipalServlet extends AbstractServlet {

  @Injected
  PhotoUcc photoUcc;

  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    System.out.println("GET /api/photos-Principal by " + req.getRemoteAddr());

    String id = req.getParameter("idPhotoPrincipal");
    int id_photo = Integer.parseInt(id);

    try {
      GensonBuilder gensonBuilder = Util.createGensonBuilder().acceptSingleValueAsList(true);
      sendSuccessWithJson(resp, "photosPrincipal",
          gensonBuilder.create().serialize(photoUcc.getPhotoById(id_photo)));
    } catch (FatalException fatalE) {
      sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, fatalE.getMessage());
    } catch (BizException bizE) {
      sendError(resp, HttpServletResponse.SC_CONFLICT, bizE.getMessage());
    } catch (Exception ex) {
      sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED, "Paramètres invalides");
    }
  }
}
