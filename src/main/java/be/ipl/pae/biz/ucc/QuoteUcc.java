package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.QuoteDto;

public interface QuoteUcc {

  /**
   * Insert a new quote into the database.
   *
   * @param quoteDto the quote to insert
   * @return a QuoteDto object that represent the quote
   */
  QuoteDto insert(QuoteDto quoteDto);

}
