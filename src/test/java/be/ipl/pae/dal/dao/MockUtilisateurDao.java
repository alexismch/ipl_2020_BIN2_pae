package be.ipl.pae.dal.dao;

import be.ipl.pae.pae.biz.dto.UtilisateurDto;
import be.ipl.pae.pae.biz.objets.DtoFactory;
import be.ipl.pae.pae.dal.dao.UtilisateurDao;
import be.ipl.pae.main.Inject;

import org.mindrot.bcrypt.BCrypt;

public class MockUtilisateurDao implements UtilisateurDao {

  @Inject
  private DtoFactory dtoFactory;


  @Override
  public UtilisateurDto getUtilisateurParPseudo(String pseudo) {
    if (!pseudo.equals("sousou")) {
      return null;
    }

    UtilisateurDto utilisateurDto = dtoFactory.getUtilisateur();
    utilisateurDto.setPseudo("sousou");
    utilisateurDto.setMdp(BCrypt.hashpw("123456", BCrypt.gensalt()));
    utilisateurDto.setId(1);


    return utilisateurDto;
  }

  @Override
  public UtilisateurDto getUser(int idUtilisateur) {
    // TODO Auto-generated method stub
    return null;
  }

}
