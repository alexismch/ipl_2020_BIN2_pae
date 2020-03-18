package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.dal.dao.QuoteDao;
import be.ipl.pae.dependencies.Injected;

public class QuoteUccImpl implements QuoteUcc {

  @Injected
  private QuoteDao quoteDao;

  @Override
  public QuoteDto insert(QuoteDto quoteDto) {
    return null;
  }
}
