package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.QuoteDto;
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
   * @throws FatalException if you have a problem with the db
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
}
