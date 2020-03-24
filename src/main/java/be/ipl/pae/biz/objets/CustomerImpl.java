package be.ipl.pae.biz.objets;

public class CustomerImpl implements Customer {

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

  /**
   * Create a CustomerImpl object.
   *
   * @param idCustomer the customer id
   * @param lastname   the customer lastname
   * @param firstname  the customer firstname
   * @param address    the customer address
   * @param postalCode the customer postal code
   * @param city       the customer city
   * @param email      the customer email
   * @param telNbr     the customer tele Nbr
   * @param idUser     the user id which is linked with the customer
   */
  public CustomerImpl(int idCustomer, String lastname, String firstname, String address,
      int postalCode, String city, String email, String telNbr, int idUser) {
    super();
    this.idCustomer = idCustomer;
    this.lastname = lastname;
    this.firstname = firstname;
    this.address = address;
    this.postalCode = postalCode;
    this.city = city;
    this.email = email;
    this.telNbr = telNbr;
    this.idUser = idUser;
  }

  @Override
  public int getIdcustomer() {
    return idCustomer;
  }

  @Override
  public void setIdcustomer(int idcustomer) {
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
