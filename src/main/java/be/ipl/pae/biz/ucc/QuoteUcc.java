package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.dto.QuotesFilterDto;
import be.ipl.pae.biz.objets.QuoteState;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;

import java.util.List;

public interface QuoteUcc {

  /**
   * Insert a new quote into the database.
   *
   * @param quoteDto the quote to insert
   * @return a QuoteDto object that represents the quote
   * @throws BizException if an error occurred
   */
  QuoteDto insert(QuoteDto quoteDto) throws BizException;

  /**
   * Get all quotes.
   *
   * @return the quotes list.
   * @throws BizException if you have a problem with the db
   */
  List<QuoteDto> getQuotes() throws BizException;

  /**
   * Get a quote from the database thanks to its id.
   *
   * @param idQuote id of the quote
   * @return an object quoteDto
   * @throws FatalException if you have a problem with the db
   * @throws BizException if the quote doesn't exist
   */
  QuoteDto getQuote(String idQuote) throws FatalException, BizException;

  /**
   * Get all the customer's quotes from the database from his id.
   *
   * @param customerId the customer's id
   * @return a list of all the customer's quotes
   * @throws BizException if an error occurred
   */
  List<QuoteDto> getCustomerQuotes(int customerId) throws BizException;



  /**
   * Add the start date of the quote.
   *
   * @param quote object quote with the id and date
   * @throws FatalException if you had a problem with the db
   * @throws BizException if the quote doesn't exist
   */
  void setStartDateQuoteInDb(QuoteDto quote) throws FatalException, BizException;

  /**
   * Get quotes via filters.
   *
   * @param quotesFilterDto all the filters that the user chosed
   * @return a list of quotes depending on the user's filters
   * @throws FatalException if you had a problem with the db
   */
  List<QuoteDto> getQuotesFiltered(QuotesFilterDto quotesFilterDto) throws FatalException;

  /**
   * Redirect to the right method.
   * 
   * @param quote object Quote
   * @return an QuoteDto object
   * @throws FatalException if problem with the db
   * @throws BizException if idQuote or date isn't send by the user
   */
  QuoteDto useStateManager(QuoteDto quote) throws BizException, FatalException;

  /**
   * Set the state of the in the db.
   * 
   * @param idQuote id of the quote
   * @param state state of the quote
   * @return a new QuoteDto object
   * @throws FatalException if problem with the db
   * @throws BizException if idQuote or date isn't send by the user
   */
  QuoteDto setState(String idQuote, QuoteState state) throws BizException, FatalException;

  /**
   * Get quotes via filters and the customer's id.
   * 
   * @param quotesFilterDto all the filters that the user chosed
   * @param idCustomer the id of the customer
   * @return a list of quotes depending on the user's filters
   * @throws FatalException error with the db
   */
  List<QuoteDto> getQuotesFiltered(QuotesFilterDto quotesFilterDto, int idCustomer)
      throws FatalException;
}
