package be.ipl.pae.ihm.servlets;

import static be.ipl.pae.ihm.servlets.utils.Util.hasAccess;
import static be.ipl.pae.ihm.servlets.utils.Util.verifyNotEmpty;

import be.ipl.pae.biz.objets.UserStatus;
import be.ipl.pae.biz.ucc.QuoteUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FavoritePhotoServlet extends AbstractServlet {

  @Injected
  private QuoteUcc quoteUcc;

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String token = (String) req.getSession().getAttribute("token");
    if (!hasAccess(token, UserStatus.WORKER)) {
      sendError(resp, HttpServletResponse.SC_UNAUTHORIZED, "Wrong token.");
      return;
    }

    String quoteId = req.getParameter("quoteId");
    String photoIdString = req.getParameter("photoId");

    if (verifyNotEmpty(quoteId, photoIdString)) {
      try {
        int photoId = Integer.parseInt(photoIdString);

        quoteUcc.setFavoritePhoto(quoteId, photoId);

        sendSuccess(resp);
      } catch (NumberFormatException nbE) {
        sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED, "Paramètres invalides");
      } catch (BizException bizE) {
        sendError(resp, HttpServletResponse.SC_CONFLICT, bizE.getMessage());
      }
    } else {
      sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED, "Paramètres invalides");
    }
  }
}
