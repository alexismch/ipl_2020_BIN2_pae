package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dal.services.DalService;
import be.ipl.pae.dependencies.Injected;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuoteDaoImpl implements QuoteDao{

  @Injected
  DalService dalService;
  @Injected
  DtoFactory quoteDto;
  
  public List<QuoteDto> getAllQuote() throws SQLException {
    List<QuoteDto> users = new ArrayList<QuoteDto>();
    PreparedStatement ps = dalService.getPreparedStatement("SELECT * FROM mystherbe.quotes");
    ResultSet res = ps.executeQuery();
    while (res.next()) {
      users.add(createQuoteDto(res));
    }
    return users;
  }

  public QuoteDto createQuoteDto(ResultSet res) throws SQLException {
    QuoteDto quote = quoteDto.getQuote();
    quote.setIdQuote(res.getNString(1));
    quote.setIdCustomer(res.getString(2));
    quote.setQuoteDate(res.getDate(3));
    quote.setTotalAmount(res.getDouble(4));
    quote.setWorkDuration(res.getInt(5));
    quote.setStartDate(res.getDate(6));
    quote.setIdState(res.getString(7));
    return quote;
  }
  
  
}
