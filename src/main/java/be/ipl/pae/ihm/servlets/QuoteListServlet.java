package be.ipl.pae.ihm.servlets;

import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.ucc.QuoteUcc;
import be.ipl.pae.dependencies.Injected;

import com.owlike.genson.Genson;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

public class QuoteListServlet extends AbstractServlet {

  @Injected
  private QuoteUcc quoteUcc;

  protected void listQuotes(HttpServletResponse resp) {
    ArrayList<QuoteDto> listQuotes;
    try {
      listQuotes = quoteUcc.getQuotes();
      Genson genson = new Genson();
      resp.setContentType("application/json");
      resp.getOutputStream().print(genson.serialize(listQuotes));
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }

  }
}
