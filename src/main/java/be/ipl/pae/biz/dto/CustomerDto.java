package be.ipl.pae.biz.dto;

public interface CustomerDto {

  int getIdCustomer();

  void setIdCustomer(int idcustomer);

  String getLastName();

  void setLastName(String lastname);

  String getFirstName();

  void setFirstName(String firstname);

  String getAddress();

  void setAddress(String address);

  int getPostalCode();

  void setPostalCode(int postalcode);

  String getCity();

  void setCity(String city);

  String getEmail();

  void setEmail(String email);

  String getPhoneNumber();

  void setPhoneNumber(String telnbr);

  int getIdUser();

  void setIdUser(int iduser);
}
