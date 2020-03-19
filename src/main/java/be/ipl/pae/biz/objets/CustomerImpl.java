package be.ipl.pae.biz.objets;

public class CustomerImpl implements Customer {

 private String id_customer;
 private String lastname;    
 private String firstname;
 private String address;   
 private int postal_code;
 private String city;       
 private String email;      
 private String tel_nbr;  
 private int id_user;
 
 
 
 
public CustomerImpl() {
  super();
}
public CustomerImpl(String id_customer, String lastname, String firstname, String address,
    int postal_code, String city, String email, String tel_nbr, int id_user) {
  super();
  this.id_customer = id_customer;
  this.lastname = lastname;
  this.firstname = firstname;
  this.address = address;
  this.postal_code = postal_code;
  this.city = city;
  this.email = email;
  this.tel_nbr = tel_nbr;
  this.id_user = id_user;
}
public String getId_customer() {
  return id_customer;
}
public void setId_customer(String id_customer) {
  this.id_customer = id_customer;
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
public int getPostal_code() {
  return postal_code;
}
public void setPostal_code(int postal_code) {
  this.postal_code = postal_code;
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
public String getTel_nbr() {
  return tel_nbr;
}
public void setTel_nbr(String tel_nbr) {
  this.tel_nbr = tel_nbr;
}
public int getId_user() {
  return id_user;
}
public void setId_user(int id_user) {
  this.id_user = id_user;
}     
}
