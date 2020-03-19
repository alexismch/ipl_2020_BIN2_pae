package be.ipl.pae.biz.ucc;



import be.ipl.pae.dal.dao.QuoteTypeDao;
import be.ipl.pae.dependencies.Injected;

public class QuoteTypeUccImpl implements QuoteTypeUcc {

  @Injected
  private QuoteTypeDao quoteTypeDao;
 
}
