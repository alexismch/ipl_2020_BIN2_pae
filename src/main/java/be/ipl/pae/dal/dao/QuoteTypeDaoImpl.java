package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dal.services.DalService;
import be.ipl.pae.dependencies.Injected;

public class QuoteTypeDaoImpl implements QuoteTypeDao {

  @Injected
  DalService dalService;
  @Injected
  DtoFactory userDtoFactory;
}
