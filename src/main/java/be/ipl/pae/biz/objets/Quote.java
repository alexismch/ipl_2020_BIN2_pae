package be.ipl.pae.biz.objets;

import be.ipl.pae.biz.dto.QuoteDto;

public interface Quote extends QuoteDto {

  /**
   * check if the quote that we received has the same state in the db.
   * 
   * @param quote an object quote
   * @param state the state of the quote in the db
   * @return true if the state in the db is the same that we received or if the ste state ==
   *         CANCELED, otherwise false
   */
  boolean checkStateQuote(QuoteDto quote, QuoteState state);
}
