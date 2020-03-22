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
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    File file = new File(this.getResource("/").getName() + req.getRequestURI());

    if (!file.exists() || file.isDirectory()) {
      // Requested page does not exist -> return index.html
      try (InputStream inputStream =
          this.getResource("/").getResource("index.html").getInputStream()) {

        byte[] buffer = new byte[1024];
        int length;

        while ((length = inputStream.read(buffer)) > 0) {
          resp.getOutputStream().write(buffer, 0, length);
        }
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
      } catch (Exception ex) {
        resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      }
    } else {
      super.doGet(req, resp);
    }

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
  }

  @Override
  protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
    resp.setHeader("Allow", "GET,HEAD,OPTIONS");
  }

}
