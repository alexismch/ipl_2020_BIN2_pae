package be.ipl.pae.ihm.servlets;

import static be.ipl.pae.ihm.Util.hasAccess;
import static be.ipl.pae.ihm.Util.verifyNotEmpty;

import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.UserStatus;
import be.ipl.pae.biz.ucc.DevelopmentTypeUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;
import be.ipl.pae.ihm.Util;

import com.owlike.genson.GensonBuilder;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DevelopmentTypeServlet extends AbstractServlet {

  @Injected
  private DevelopmentTypeUcc developmentTypeUcc;

  @Injected
  private DtoFactory dtoFactory;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    System.out.println("POST /api/developmentType by " + req.getRemoteAddr());

    String token = (String) req.getSession().getAttribute("token");
    if (!hasAccess(token, UserStatus.WORKER)) {
      sendError(resp, HttpServletResponse.SC_UNAUTHORIZED, "Wrong token.");
      return;
    }

    String typeTitle = req.getParameter("typeTitle");

    if (verifyNotEmpty(typeTitle)) {
      DevelopmentTypeDto developmentType = dtoFactory.getDevelopmentType();
      developmentType.setTitle(typeTitle);

      try {
        developmentType = developmentTypeUcc.insert(developmentType);

        GensonBuilder gensonBuilder = Util.createGensonBuilder().acceptSingleValueAsList(true);

        sendSuccessWithJson(resp, "developmentType",
            gensonBuilder.create().serialize(developmentType));
      } catch (BizException bizE) {
        sendError(resp, HttpServletResponse.SC_CONFLICT, bizE.getMessage());
      } catch (FatalException fatalE) {
        sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, fatalE.getMessage());
      }
    } else {
      sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED, "Paramètres invalides");
    }
  }
}
