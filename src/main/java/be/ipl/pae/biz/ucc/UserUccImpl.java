package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.dto.UsersFilterDto;
import be.ipl.pae.biz.objets.User;
import be.ipl.pae.biz.objets.UserStatus;
import be.ipl.pae.dal.dao.UserDao;
import be.ipl.pae.dal.services.DalServiceTransaction;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.DalException;
import be.ipl.pae.exceptions.FatalException;

import java.util.List;

class UserUccImpl implements UserUcc {

  @Injected
  private UserDao userDao;

  @Injected
  private DalServiceTransaction dalService;

  @Override
  public UserDto login(String pseudo, String mdp) throws BizException {
    UserDto userDto;
    try {
      dalService.startTransaction();
      userDto = userDao.getUserByPseudo(pseudo);
    } catch (DalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex);
    } finally {
      dalService.commitTransaction();
    }

    if (userDto == null || !((User) userDto).verifierMdp(mdp)) {
      throw new BizException("Pseudo ou mot de passe incorrect ");
    }

    if (UserStatus.NOT_ACCEPTED.equals(userDto.getStatus())) {
      throw new BizException("Votre inscription est en attente de validation");
    }

    return userDto;
  }

  @Override
  public UserDto register(UserDto userDto) throws BizException {
    UserDto userDtoRet;
    try {
      dalService.startTransaction();
      if (userDao.checkPseudoInDb(userDto.getPseudo())) {
        throw new BizException("Pseudo déjà utilisé");
      }
      if (userDao.checkEmailInDb(userDto.getEmail())) {
        throw new BizException("Email déjà utilisé");
      }

      userDtoRet = userDao.insertUser(userDto);

    } catch (DalException fx) {
      dalService.rollbackTransaction();
      throw new FatalException(fx);
    } finally {
      dalService.commitTransaction();
    }
    return userDtoRet;
  }

  @Override
  public UserDto getUser(int id) {
    try {
      dalService.startTransaction();
      return userDao.getUser(id);
    } catch (DalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex);
    } finally {
      dalService.commitTransaction();
    }
  }

  @Override
  public List<UserDto> getUsers(UsersFilterDto usersFilterDto) {
    List<UserDto> userDtoList;
    try {
      dalService.startTransaction();
      userDtoList = userDao.getUsers(usersFilterDto);
    } catch (DalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex);
    } finally {
      dalService.commitTransaction();
    }
    return userDtoList;
  }

  @Override
  public UserDto changeUserStatus(int userId, UserStatus newStatus) throws BizException {
    try {
      dalService.startTransaction();
      UserStatus status = userDao.getUserStatus(userId);

      if (status == null) {
        throw new BizException("Il n'y a aucun utilisateur avec l'id " + userId);
      }

      if (!UserStatus.NOT_ACCEPTED.equals(status)) {
        throw new BizException("Le status de l'utilisateur doit être égal à "
            + UserStatus.NOT_ACCEPTED.getName() + " pour pouvoir être changé.");
      }

      return userDao.changeUserStatus(userId, newStatus);
    } catch (DalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex);
    } finally {
      dalService.commitTransaction();
    }
  }
}
