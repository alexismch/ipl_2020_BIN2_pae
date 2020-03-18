package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.QuoteDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface QuoteDao {

  ArrayList<QuoteDto> getAllQuote() throws SQLException;

  QuoteDto insertQuote(QuoteDto quoteDto);

  QuoteDto createQuoteDto(ResultSet res) throws SQLException;
}
