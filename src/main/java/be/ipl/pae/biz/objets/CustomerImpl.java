package be.ipl.pae.biz.objets;

class CustomerImpl implements Customer {

  private int idCustomer;
  private String lastname;
  private String firstname;
  private String address;
  private int postalCode;
  private String city;
  private String email;
  private String telNbr;
  private int idUser;

  public CustomerImpl() {
    super();
  }

  @Override
  public int getIdCustomer() {
    return idCustomer;
  }

  @Override
  public void setIdCustomer(int idcustomer) {
    this.idCustomer = idcustomer;
  }

  @Override
  public String getLastName() {
    return lastname;
  }

  @Override
  public void setLastName(String lastname) {
    this.lastname = lastname;
  }

  @Override
  public String getFirstName() {
    return firstname;
  }

  @Override
  public void setFirstName(String firstname) {
    this.firstname = firstname;
  }

  @Override
  public String getAddress() {
    return address;
  }

  @Override
  public void setAddress(String address) {
    this.address = address;
  }

  @Override
  public int getPostalCode() {
    return postalCode;
  }

  @Override
  public void setPostalCode(int postalcode) {
    this.postalCode = postalcode;
  }

  @Override
  public String getCity() {
    return city;
  }

  @Override
  public void setCity(String city) {
    this.city = city;
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
  public String getPhoneNumber() {
    return telNbr;
  }

  @Override
  public void setPhoneNumber(String telnbr) {
    this.telNbr = telnbr;
  }

  @Override
  public int getIdUser() {
    return idUser;
  }

  @Override
  public void setIdUser(int idUser) {
    this.idUser = idUser;
  }
}
