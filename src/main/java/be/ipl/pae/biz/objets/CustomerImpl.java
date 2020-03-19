package be.ipl.pae.biz.objets;

public class CustomerImpl implements Customer {

 private String idCustomer;
 private String lastname;    
 private String firstname;
 private String address;   
 private int postalCode;
 private String city;       
 private String email ;      
 private String telNbr;  
 private int iduser;
 
 
 
 
public CustomerImpl() {
  super();
}
public CustomerImpl(String idcustomer, String lastname, String firstname, String address,
    int postalcode, String city, String email, String telnbr, int iduser) {
  super();
  this.idCustomer = idcustomer;
  this.lastname = lastname;
  this.firstname = firstname;
  this.address = address;
  this.postalCode = postalcode;
  this.city = city;
  this.email = email;
  this.telNbr = telnbr;
  this.iduser = iduser;
}
public String getIdcustomer() {
  return idCustomer;
}
public void setIdcustomer(String idcustomer) {
  this.idCustomer = idcustomer;
}
public String getLastname() {
  return lastname;
}
public void setLastname(String lastname) {
  this.lastname = lastname;
}
public String getFirstname() {
  return firstname;
}
public void setFirstname(String firstname) {
  this.firstname = firstname;
}
public String getAddress() {
  return address;
}
public void setAddress(String address) {
  this.address = address;
}
public int getPostalcode() {
  return postalCode;
}
public void setPostalcode(int postalcode) {
  this.postalCode = postalcode;
}
public String getCity() {
  return city;
}
public void setCity(String city) {
  this.city = city;
}
public String getEmail() {
  return email;
}
public void setEmail(String email) {
  this.email = email;
}
public String getTelnbr() {
  return telNbr;
}
public void setTelnbr(String telnbr) {
  this.telNbr = telnbr;
}
public int getIduser() {
  return iduser;
}
public void setIduser(int id_user) {
  this.iduser = id_user;
}     
}
