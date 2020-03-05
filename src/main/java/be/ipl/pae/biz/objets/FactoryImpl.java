package be.ipl.pae.biz.objets;

import be.ipl.pae.biz.dto.UtilisateurDTOImpl;

public class FactoryImpl implements Factory {

  @Override
  public UtilisateurDTOImpl getUtilisateur() {
    return new UtilisateurDTOImpl();
  }
  
  
}
