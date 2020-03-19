package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.exceptions.FatalException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface QuoteDao {

  ArrayList<QuoteDto> getAllQuote() throws SQLException;

  QuoteDto insertQuote(QuoteDto quoteDto) throws FatalException;

  QuoteDto createQuoteDto(ResultSet res) throws SQLException;

  boolean checkQuoteIdInDb(String quoteId) throws FatalException;
}
