package be.ipl.pae.biz.objets;

import be.ipl.pae.biz.dto.QuoteDto;

public interface Quote extends QuoteDto {

  /**
   * check if the quote has the same {@code state}. If the quote state is {@link
   * QuoteState#CANCELLED} this method will always return true
   *
   * @param state the state of the quote in the db
   * @return true if the state in the db is the same that we received or if the ste state ==
   * CANCELED, otherwise false
   */
  boolean checkState(QuoteState state);
}
