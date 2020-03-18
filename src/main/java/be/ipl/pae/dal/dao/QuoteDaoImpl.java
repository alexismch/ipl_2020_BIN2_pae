package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dal.DalUtils;
import be.ipl.pae.dal.services.DalService;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.FatalException;

public class QuoteDaoImpl implements QuoteDao{

  @Injected
  DalService dalService;
  @Injected
  DtoFactory quoteDto;
  
  
  
  
}
