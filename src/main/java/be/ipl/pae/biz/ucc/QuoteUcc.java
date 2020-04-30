package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.dto.QuotesFilterDto;
import be.ipl.pae.biz.objets.QuoteState;
import be.ipl.pae.exceptions.BizException;

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
  // List<QuoteDto> getQuotes() throws BizException;

  /**
   * Get a quote from the database thanks to its id.
   *
   * @param idQuote id of the quote
   * @return an object quoteDto
   * @throws BizException if the quote doesn't exist
   */
  QuoteDto getQuote(String idQuote) throws BizException;

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
   * @return true if it has changed in the db
   */
  boolean setStartDateQuoteInDb(QuoteDto quote);


  /**
   * Redirect to the right method.
   *
   * @param quote object Quote
   * @return an QuoteDto object
   * @throws BizException if idQuote or date isn't send by the user
   */
  QuoteDto useStateManager(QuoteDto quote) throws BizException;

  /**
   * Set the state of the in the db.
   *
   * @param idQuote id of the quote
   * @param state state of the quote
   * @return a new QuoteDto object
   * @throws BizException if idQuote or date isn't send by the user
   */
  QuoteDto setState(String idQuote, QuoteState state) throws BizException;

  /**
   * Get quotes via filters.
   *
   * @param quotesFilterDto all the filters that the user chosed
   * @return a list of quotes depending on the user's filters
   */
  List<QuoteDto> getQuotesFiltered(QuotesFilterDto quotesFilterDto);

  /**
   * Get quotes via filters and the customer's id.
   *
   * @param quotesFilterDto all the filters that the user chosed
   * @param idCustomer the id of the customer
   * @return a list of quotes depending on the user's filters
   */
  List<QuoteDto> getQuotesFiltered(QuotesFilterDto quotesFilterDto, int idCustomer);

  /**
   * Set the favorite photo to the quote.
   *
   * @param quoteId the id of the quote
   * @param photoId the id of the photo
   * @throws BizException if an error occurred with the db
   */
  void setFavoritePhoto(String quoteId, int photoId) throws BizException;
}
