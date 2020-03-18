package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.dto.UsersFilterDto;
import be.ipl.pae.dal.dao.QuoteDao;
import be.ipl.pae.dal.dao.UserDao;
import be.ipl.pae.dependencies.Injected;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuoteUccImpl implements QuoteUcc{

  @Injected
  private QuoteDao quoteDao;
  
  public ArrayList<QuoteDto> getQuotes() throws SQLException {
    return quoteDao.getAllQuote();
  }
  
  
  
}
