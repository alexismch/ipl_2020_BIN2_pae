package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.dto.QuotesFilterDto;
import be.ipl.pae.biz.objets.QuoteState;
import be.ipl.pae.exceptions.FatalException;

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
   * Get all the customer's quotes from the database from his id.
   *
   * @param idCustomer the customer's id
   * @return a list of all the customer's quotes
   */
  List<QuoteDto> getCustomerQuotes(int idCustomer) throws FatalException;

  /**
   * set the startdate in the db.
   *
   * @param quote a quote with an id and the startdate
   * @throws FatalException if you had an error with the db
   */
  void setStartDate(QuoteDto quote) throws FatalException;

  /**
   * change the state of the quote in the db.
   *
   * @param confirmedDate the new state
   * @param quoteId the quote of the id
   * @throws FatalException error with the db
   */
  void setStateQuote(QuoteState confirmedDate, String quoteId) throws FatalException;

  /**
   * Get quotes via filters.
   *
   * @param quotesFilterDto all the filters that the user chosed
   * @return a list of quotes depending on the user's filters
   * @throws FatalException error with the db
   */
  List<QuoteDto> getQuotesFiltered(QuotesFilterDto quotesFilterDto) throws FatalException;

  /**
   * return the workDuration of a quote.
   * 
   * @param idQuote the quote of the id
   * @return the workDuration
   * @throws FatalException error with the db
   */
  int getWorkduRation(String idQuote) throws FatalException;

  /**
   * Get the current state of the quote.
   * 
   * @param idQuote the id of the quote
   * @return the state of the quote
   * @throws FatalException error with the db
   */
  QuoteState getStateQuote(String idQuote) throws FatalException;
}
