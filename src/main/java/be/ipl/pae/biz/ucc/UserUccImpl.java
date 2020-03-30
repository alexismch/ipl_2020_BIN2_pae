package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.dto.UsersFilterDto;
import be.ipl.pae.biz.objets.User;
import be.ipl.pae.biz.objets.UserStatus;
import be.ipl.pae.dal.dao.UserDao;
import be.ipl.pae.dal.services.DalServiceTransaction;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;

import java.util.List;


public class UserUccImpl implements UserUcc {

  @Injected
  private UserDao userDao;

  @Injected
  private DalServiceTransaction dalService;


  @Override
  public UserDto login(String pseudo, String mdp) throws BizException {
    try {
      UserDto userDto;
      try {
        dalService.startTransaction();
        userDto = userDao.getUserByPseudo(pseudo);
      } catch (Exception ex) {
        dalService.rollbackTransaction();
        throw new BizException(ex);
      } finally {
        dalService.commitTransaction();
      }

      if (userDto == null || !((User) userDto).verifierMdp(mdp)) {
        throw new BizException("Pseudo ou mot de passe incorrect !");
      }

      if (UserStatus.NOT_ACCEPTED.equals(userDto.getStatus())) {
        throw new BizException("Votre inscription est en attente de validation!");
      }

      return userDto;

    } catch (FatalException ex) {
      throw new BizException(ex);
    }

  }


  @Override
  public UserDto register(UserDto userDto) throws BizException {
    try {
      try {
        dalService.startTransaction();
        if (userDao.checkPseudoInDb(userDto.getPseudo())) {
          throw new BizException("Pseudo déjà utilisé!");
        }
        if (userDao.checkEmailInDb(userDto.getEmail())) {
          throw new BizException("Email déjà utilisé!");
        }

        return userDao.insertUser(userDto);

      } catch (FatalException fx) {
        dalService.rollbackTransaction();
        throw new BizException(fx);
      } finally {
        dalService.commitTransaction();
      }
    } catch (FatalException ex) {
      throw new BizException(ex);
    }
  }

  @Override
  public UserDto getUser(int id) throws FatalException {
    try {
      dalService.startTransaction();
      return userDao.getUser(id);
    } catch (FatalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex);
    } finally {
      dalService.commitTransaction();
    }
  }

  @Override
  public List<UserDto> getUsers(UsersFilterDto usersFilterDto) throws FatalException {
    try {
      dalService.startTransaction();
      return userDao.getUsers(usersFilterDto);
    } catch (FatalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex);
    } finally {
      dalService.commitTransaction();
    }

  }


  @Override
  public UserDto userConfirmation(String pseudo, char statut) throws FatalException {
    UserDto user;
    try {
      dalService.startTransaction();
      user = userDao.userConfirmation(pseudo, statut);
    } catch (FatalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex);
    } finally {
      dalService.commitTransaction();
    }
    return user;
  }

}
