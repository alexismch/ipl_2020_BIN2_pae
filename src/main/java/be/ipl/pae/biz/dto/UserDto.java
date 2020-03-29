package be.ipl.pae.biz.dto;

import be.ipl.pae.biz.objets.UserStatus;

import java.time.LocalDate;

public interface UserDto {

  String getPseudo();

  void setPseudo(String pseudo);

  int getId();

  void setId(int id);

  String getLastName();

  void setLastName(String lastName);

  String getFirstName();

  void setFirstName(String firstName);

  String getPassword();

  void setPassword(String password);

  String getCity();

  void setCity(String city);

  String getEmail();

  void setEmail(String email);

  LocalDate getRegistrationDate();

  void setRegistrationDate(LocalDate registrationDate);

  UserStatus getStatus();

  void setStatus(UserStatus status);
}
