package be.ipl.pae.ihm.servlets;

import be.ipl.pae.biz.objets.QuoteState;
import be.ipl.pae.biz.objets.UserStatus;

import com.owlike.genson.GensonBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

abstract class AbstractServlet extends HttpServlet {

  /**
   * Create a Genson Builder.
   *
   * @return the Genson Builder
   */
  protected GensonBuilder createGensonBuilder() {
    GensonBuilder gensonBuilder =
        new GensonBuilder().exclude("password").useMethods(true).useRuntimeType(true)
            .setHtmlSafe(true);

    gensonBuilder.withSerializer(
        (value, writer, ctx) -> writer.writeString(value.format(DateTimeFormatter.ISO_LOCAL_DATE)),
        LocalDate.class);

    gensonBuilder.withSerializer((value, writer, ctx) -> writer.writeName("status").beginObject()
            .writeString("id", value.toString()).writeString("name", value.getName()).endObject(),
        UserStatus.class);

    gensonBuilder.withSerializer((value, writer, ctx) -> writer.writeName("state").beginObject()
            .writeString("id", value.toString()).writeString("title", value.getTitle()).endObject(),
        QuoteState.class);

    return gensonBuilder;
  }

  /**
   * Return a message to the request.
   *
   * @param resp        the request that gonna recieve the message
   * @param messageType returned message type
   * @param status      returned request status
   * @param msg         returned message
   * @throws IOException in case of problem with request writer
   */
  protected void sendMessage(HttpServletResponse resp, String messageType, int status, String msg)
      throws IOException {
    resp.setContentType(messageType);
    resp.setCharacterEncoding("UTF-8");
    resp.setStatus(status);
    resp.getWriter().write(msg);
  }

  /**
   * Return an error message to the request.
   *
   * @param resp the request that gonna recieve the message
   * @param status returned request status
   * @param msg returned message
   * @throws IOException in case of problem with request writer
   */
  protected void sendError(HttpServletResponse resp, int status, String msg) throws IOException {
    String json = "{\"" + "success\":false, " + "\"error\":\"" + msg + "\"}";
    sendMessage(resp, "application/json", status, json);
  }

  /**
   * Return a success message to the request.
   *
   * @param resp the request that gonna recieve the message
   * @throws IOException in case of problem with request writer
   */
  protected void sendSuccess(HttpServletResponse resp) throws IOException {
    String json = "{\"" + "success\":true" + "}";
    sendMessage(resp, "application/json", 200, json);
  }

  /**
   * Return a success message to the request with a specific json.
   *
   * @param resp the request that gonna recieve the message
   * @param jsonName the name of the additional json
   * @param json the json that is returned with the success
   * @throws IOException in case of problem with request writer
   */
  protected void sendSuccessWithJson(HttpServletResponse resp, String jsonName, String json)
      throws IOException {
    json = "{\"" + "success\":true, " + "\"" + jsonName + "\":" + json + "}";
    sendMessage(resp, "application/json", 200, json);
  }
}
