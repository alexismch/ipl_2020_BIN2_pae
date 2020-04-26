package be.ipl.pae.ihm.servlets;

import static be.ipl.pae.ihm.Util.hasAccess;
import static be.ipl.pae.ihm.Util.verifyNotEmpty;

import be.ipl.pae.biz.objets.UserStatus;
import be.ipl.pae.biz.ucc.LinkCcUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LinkCcServlet extends AbstractServlet {

  @Injected
  private LinkCcUcc linkCcUcc;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    System.out.println("POST /api/link-cc by " + req.getRemoteAddr());

    String token = (String) req.getSession().getAttribute("token");
    if (!hasAccess(token, req.getRemoteAddr(), UserStatus.WORKER)) {
      sendError(resp, HttpServletResponse.SC_UNAUTHORIZED, "Wrong token.");
      return;
    }

    String userIdString = req.getParameter("userId");
    String customerIdString = req.getParameter("customerId");

    if (verifyNotEmpty(userIdString, customerIdString)) {
      try {
        int userId = Integer.parseInt(userIdString);
        int customerId = Integer.parseInt(customerIdString);
        linkCcUcc.link(customerId, userId);

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
