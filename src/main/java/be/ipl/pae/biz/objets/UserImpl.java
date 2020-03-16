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

  public String getPseudo() {
    return this.pseudo;
  }

  public void setPseudo(String pseudo) {
    this.pseudo = pseudo;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String nom) {
    this.lastName = nom;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String mdp) {
    this.password = mdp;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String ville) {
    this.city = ville;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LocalDate getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(LocalDate registrationDate) {
    this.registrationDate = registrationDate;
  }

  public UserStatus getStatus() {
    return status;
  }

  public void setStatus(UserStatus status) {
    this.status = status;
  }

  @Override
  public boolean verifierMdp(String mdp) {
    return BCrypt.checkpw(mdp, this.password);
  }
}
