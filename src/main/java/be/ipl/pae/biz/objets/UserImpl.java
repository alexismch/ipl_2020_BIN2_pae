package be.ipl.pae.biz.objets;

import org.mindrot.bcrypt.BCrypt;

import java.time.LocalDate;

class UserImpl implements User {

  private String pseudo;
  private int id;
  private String lastName;
  private String firstName;
  private String password;
  private String city;
  private String email;
  private LocalDate registrationDate;
  private UserStatus status;

  public UserImpl() {
    super();
  }

  public UserImpl(String pseudo, String lastName, String firstName, String password, String city,
      String email,
      UserStatus status) {
    this.pseudo = pseudo;
    this.lastName = lastName;
    this.firstName = firstName;
    this.password = password;
    this.city = city;
    this.email = email;
    this.registrationDate = LocalDate.now();
    this.status = status;
  }

  @Override
  public String getPseudo() {
    return this.pseudo;
  }

  @Override
  public void setPseudo(String pseudo) {
    this.pseudo = pseudo;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String getLastName() {
    return lastName;
  }

  @Override
  public void setLastName(String nom) {
    this.lastName = nom;
  }

  @Override
  public String getFirstName() {
    return firstName;
  }

  @Override
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public void setPassword(String mdp) {
    this.password = mdp;
  }

  @Override
  public String getCity() {
    return city;
  }

  @Override
  public void setCity(String ville) {
    this.city = ville;
  }

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public LocalDate getRegistrationDate() {
    return registrationDate;
  }

  @Override
  public void setRegistrationDate(LocalDate registrationDate) {
    this.registrationDate = registrationDate;
  }

  @Override
  public UserStatus getStatus() {
    return status;
  }

  @Override
  public void setStatus(UserStatus status) {
    this.status = status;
  }

  @Override
  public boolean verifierMdp(String mdp) {
    return BCrypt.checkpw(mdp, this.password);
  }
}
