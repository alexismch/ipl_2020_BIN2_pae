package be.ipl.pae.ihm.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutServlet extends AbstractServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    req.getSession().invalidate();
    sendSuccess(resp);
  }
}
