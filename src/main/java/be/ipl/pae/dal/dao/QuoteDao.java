package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.QuoteDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface QuoteDao {
  public QuoteDto createQuoteDto(ResultSet res) throws SQLException;
  
  public ArrayList<QuoteDto> getAllQuote() throws SQLException;
}
