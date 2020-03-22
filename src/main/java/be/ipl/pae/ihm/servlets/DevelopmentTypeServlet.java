package be.ipl.pae.ihm.servlets;

import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.ucc.DevelopmentTypeUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;

import com.owlike.genson.GensonBuilder;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DevelopmentTypeServlet extends AbstractServlet {

  @Injected
  private DevelopmentTypeUcc developmentTypeUcc;

  @Injected
  private DtoFactory dtoFactory;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    GensonBuilder gensonBuilder =
        new GensonBuilder().acceptSingleValueAsList(true).useMethods(true);

    try {
      sendSuccessWithJson(resp, "developementTypesList",
          gensonBuilder.create().serialize(developmentTypeUcc.getDevelopmentTypes()));
    } catch (BizException ex) {
      sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    }

  }


}
