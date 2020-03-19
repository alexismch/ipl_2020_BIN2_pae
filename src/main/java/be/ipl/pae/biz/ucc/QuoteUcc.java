package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QuoteUcc {

  /**
   * Insert a new quote into the database.
   *
   * @param quoteDto the quote to insert
   * @return a QuoteDto object that represent the quote
   */
  QuoteDto insert(QuoteDto quoteDto) throws FatalException, BizException;

  ArrayList<QuoteDto> getQuotes() throws SQLException;
}
