package be.ipl.pae.ihm.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractServlet extends HttpServlet {

  /**
   * Return a message to the request.
   *
   * @param rep         the request that gonna recieve the message
   * @param messageType returned message type
   * @param status      returned request status
   * @param msg         returned message
   * @throws IOException in case of problem with request writer
   */
  protected void sendMessage(HttpServletResponse rep, String messageType, int status, String msg)
      throws IOException {
    rep.setContentType(messageType);
    rep.setCharacterEncoding("UTF-8");
    rep.setStatus(status);
    rep.getWriter().write(msg);
    System.out.println("\tRéponse : " + msg + "\n");
  }

  /**
   * Return an error message to the request.
   *
   * @param rep    the request that gonna recieve the message
   * @param status returned request status
   * @param msg    returned message
   * @throws IOException in case of problem with request writer
   */
  protected void sendError(HttpServletResponse rep, int status, String msg) throws IOException {
    String json = "{\"" + "success\":false, " + "\"error\":\"" + msg + "\"}";
    sendMessage(rep, "application/json", status, json);
  }

  /**
   * Return a success message to the request.
   *
   * @param rep the request that gonna recieve the message
   * @throws IOException in case of problem with request writer
   */
  protected void sendSuccess(HttpServletResponse rep) throws IOException {
    String json = "{\"" + "success\":true" + "}";
    sendMessage(rep, "application/json", 200, json);
  }

  /**
   * Return a success message to the request with a specific json.
   *
   * @param rep      the request that gonna recieve the message
   * @param jsonName the name of the additional json
   * @param json     the json that is returned with the success
   * @throws IOException in case of problem with request writer
   */
  protected void sendSuccessWithJson(HttpServletResponse rep, String jsonName, String json)
      throws IOException {
    json = "{\"" + "success\":true, "
        + "\"" + jsonName + "\":" + json + "}";
    sendMessage(rep, "application/json", 200, json);
  }
}
