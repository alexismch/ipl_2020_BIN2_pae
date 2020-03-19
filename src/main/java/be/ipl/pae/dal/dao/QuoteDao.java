package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.exceptions.FatalException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface QuoteDao {

  List<QuoteDto> getAllQuote() throws SQLException;

  QuoteDto createQuoteDto(ResultSet res) throws SQLException;

  /**
   * Insert a new quote into the database.
   *
   * @param quoteDto the quote to insert
   * @return the quote inserted
   * @throws FatalException if a problem occurred with the db
   */
  QuoteDto insertQuote(QuoteDto quoteDto) throws FatalException;

  /**
   * Verify if the quote with the quoteId exists in the db.
   *
   * @param quoteId the quoteId to verify
   * @return true if exists, false if not
   * @throws FatalException if a problem occurred with the db
   */
  boolean checkQuoteIdInDb(String quoteId) throws FatalException;
}
