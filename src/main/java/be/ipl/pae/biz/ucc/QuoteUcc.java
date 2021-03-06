package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.dto.QuotesFilterDto;
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
   * Get a quote from the database thanks to its id.
   *
   * @param idQuote id of the quote
   * @return an object quoteDto
   * @throws BizException if the quote doesn't exist
   */
  QuoteDto getQuote(String idQuote) throws BizException;

  /**
   * Add the start date of the quote.
   *
   * @param quote object quote with the id and date
   * @throws BizException if quote state doesn't accept start date change
   */
  void setStartDateQuoteInDb(QuoteDto quote) throws BizException;

  /**
   * Redirect to the right method.
   *
   * @param quote object Quote
   * @return an QuoteDto object
   * @throws BizException if idQuote or date isn't send by the user
   */
  QuoteDto useStateManager(QuoteDto quote) throws BizException;

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
