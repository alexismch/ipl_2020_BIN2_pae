package be.ipl.pae.biz.objets;

public class CustomerImpl implements Customer {

  private String idCustomer;
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

  public CustomerImpl(String idcustomer, String lastname, String firstname, String address,
      int postalcode, String city, String email, String telnbr, int idUser) {
    super();
    this.idCustomer = idcustomer;
    this.lastname = lastname;
    this.firstname = firstname;
    this.address = address;
    this.postalCode = postalcode;
    this.city = city;
    this.email = email;
    this.telNbr = telnbr;
    this.idUser = idUser;
  }

  @Override
  public String getIdcustomer() {
    return idCustomer;
  }

  @Override
  public void setIdcustomer(String idcustomer) {
    this.idCustomer = idcustomer;
  }

  @Override
  public String getLastname() {
    return lastname;
  }

  @Override
  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  @Override
  public String getFirstname() {
    return firstname;
  }

  @Override
  public void setFirstname(String firstname) {
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
  public int getPostalcode() {
    return postalCode;
  }

  @Override
  public void setPostalcode(int postalcode) {
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
  public String getTelnbr() {
    return telNbr;
  }

  @Override
  public void setTelnbr(String telnbr) {
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
