package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.exceptions.FatalException;

import java.sql.ResultSet;
import java.util.List;

public interface QuoteDao {

  /**
   * collect all quotes from the database.
   *
   * @return the quote list collected
   * @throws FatalException if a problem occurred with the db
   */
  List<QuoteDto> getAllQuote() throws FatalException;

  /**
   * Create a new Quote with all the informations collected in the db.
   *
   * @param res the result from the query
   * @return the quote collected as a dto object
   * @throws FatalException if a problem occurred with the db
   */
  QuoteDto createQuoteDto(ResultSet res) throws FatalException;

  /**
   * Insert a new quote into the database.
   *
   * @param quoteDto the quote to insert
   * @return the quote inserted
   * @throws FatalException if a problem occurred with the db
   */
  QuoteDto insertQuote(QuoteDto quoteDto) throws FatalException;

  /**
   * Link the quote to a development type.
   *
   * @param quoteId the quote id
   * @param typeId the development type id
   * @throws FatalException if a problem occurred with the db
   */
  void linkToType(String quoteId, int typeId) throws FatalException;

  /**
   * Verify if the quote with the quoteId exists in the db.
   *
   * @param quoteId the quoteId to verify
   * @return true if exists, false if not
   * @throws FatalException if a problem occurred with the db
   */
  boolean checkQuoteIdInDb(String quoteId) throws FatalException;

  /**
   * Get an idQuote object.
   * 
   * @param idQuote id of the quote
   * @return an idQuote object
   * @throws FatalException if you had a problem with the db
   */
  QuoteDto getQuote(String idQuote) throws FatalException;

  /**
   * Get all the customer's quotes from the database from his id
   * 
   * @param the customer's id
   * @return a list of all the customer's quotes
   */
  List<QuoteDto> getCustomerQuotes(int idCustomer) throws FatalException;

}
