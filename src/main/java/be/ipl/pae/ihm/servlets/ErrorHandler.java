package be.ipl.pae.ihm.servlets;

import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorHandler extends ErrorPageErrorHandler {

  @Override
  protected void generateAcceptableResponse(Request baseRequest, HttpServletRequest request,
      HttpServletResponse response, int code, String message, String mimeType)
      throws IOException {
    baseRequest.setHandled(true);
    Writer writer = getAcceptableWriter(baseRequest, request, response);
    if (null != writer) {
      response.setContentType(MimeTypes.Type.APPLICATION_JSON.asString());
      response.setStatus(code);
      handleErrorPage(request, writer, code, message);
    }
  }

  @Override
  protected Writer getAcceptableWriter(Request baseRequest, HttpServletRequest request,
      HttpServletResponse response)
      throws IOException {
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    return response.getWriter();
  }

  @Override
  protected void writeErrorPage(HttpServletRequest request, Writer writer, int code, String message,
      boolean showStacks) {
    try {

      writer.write('{');
      writer.write("\"success\":false,\"error\":\"" + serializeForJson(message) + "\"");

      if (showStacks) {
        Throwable th = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        boolean printCause = false;
        if (th != null) {
          printCause = true;
          writer.write(",\"cause\":\"");
        }

        while (th != null) {
          StringWriter sw = new StringWriter();
          PrintWriter pw = new PrintWriter(sw);
          th.printStackTrace(pw);
          pw.flush();
          writer.write(serializeForJson(sw.getBuffer().toString()));

          writer.write("\\n");
          th = th.getCause();
        }

        if (printCause) {
          writer.write("\"");
        }
      }

      writer.write('}');
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private String serializeForJson(String string) {
    if (string == null) {
      return "";
    }
    return string.replace("\b", "\\b")
        .replace("\f", "\\f")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\t", "\\t")
        .replace("\"", "\\\"")
        .replace("\\", "\\\\");
  }

}
