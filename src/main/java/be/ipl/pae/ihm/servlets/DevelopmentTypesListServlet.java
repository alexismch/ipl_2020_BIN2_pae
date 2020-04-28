package be.ipl.pae.ihm.servlets;

import be.ipl.pae.biz.ucc.DevelopmentTypeUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.ihm.Util;

import com.owlike.genson.GensonBuilder;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DevelopmentTypesListServlet extends AbstractServlet {

  @Injected
  private DevelopmentTypeUcc developmentTypeUcc;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    System.out.println("GET /api/developmentType-list by " + req.getRemoteAddr());

    GensonBuilder gensonBuilder = Util.createGensonBuilder().acceptSingleValueAsList(true);
    try {
      sendSuccessWithJson(resp, "developmentTypesList",
          gensonBuilder.create().serialize(developmentTypeUcc.getDevelopmentTypes()));
    } catch (BizException ex) {
      sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }
}
