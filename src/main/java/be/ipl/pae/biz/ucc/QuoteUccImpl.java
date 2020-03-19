package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.dal.dao.QuoteDao;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuoteUccImpl implements QuoteUcc {

  @Injected
  private QuoteDao quoteDao;

  @Override
  public QuoteDto insert(QuoteDto quoteDto) throws FatalException, BizException {
    if (quoteDao.checkQuoteIdInDb(quoteDto.getIdQuote())) {
      throw new BizException("Id de devis déjà utilisé!");
    }
    return quoteDao.insertQuote(quoteDto);
  }

  public List<QuoteDto> getQuotes() throws SQLException {
    return quoteDao.getAllQuote();
  }
}
