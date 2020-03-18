package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.dal.dao.QuoteDao;
import be.ipl.pae.dependencies.Injected;

import java.sql.SQLException;
import java.util.ArrayList;

public class QuoteUccImpl implements QuoteUcc {

  @Injected
  private QuoteDao quoteDao;

  @Override
  public QuoteDto insert(QuoteDto quoteDto) {
    return null;
  }

  public ArrayList<QuoteDto> getQuotes() throws SQLException {
    return quoteDao.getAllQuote();
  }
}
