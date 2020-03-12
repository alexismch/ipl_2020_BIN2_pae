package be.ipl.pae.ihm.servlets;

import org.eclipse.jetty.servlet.DefaultServlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FrontendServlet extends DefaultServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    File file = new File(this.getResource("/").getName() + request.getRequestURI());

    // La page demandée n'existe pas -> renvoi de la page index.html
    if (!file.exists()) {
      try (InputStream inputStream =
          this.getResource("/").getResource("index.html").getInputStream()) {

        byte[] buffer = new byte[1024];
        int length;

        while ((length = inputStream.read(buffer)) > 0) {
          response.getOutputStream().write(buffer, 0, length);
        }
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
      } catch (Exception ex) {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      }
    } else {
      super.doGet(request, response);
    }

  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
  }

  @Override
  protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
    resp.setHeader("Allow", "GET,HEAD,OPTIONS");
  }

}
