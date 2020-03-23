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
   */
  QuoteDto insert(QuoteDto quoteDto) throws FatalException, BizException;

  List<QuoteDto> getQuotes() throws BizException;

  /**
   * Get a quote from the database thanks to its id.
   * 
   * @param idQuote id of the quote
   * @return an object quoteDto
   * @throws FatalException if you have a problem with the db
   */
  QuoteDto getQuote(String idQuote) throws FatalException;
}
