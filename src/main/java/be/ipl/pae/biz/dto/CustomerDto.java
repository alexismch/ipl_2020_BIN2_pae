package be.ipl.pae.biz.dto;

public interface CustomerDto {

  String getIdcustomer();

  void setIdcustomer(String idcustomer);

  String getLastname();

  void setLastname(String lastname);

  String getFirstname();

  void setFirstname(String firstname);

  String getAddress();

  void setAddress(String address);

  int getPostalcode();

  void setPostalcode(int postalcode);

  String getCity();

  void setCity(String city);

  String getEmail();

  void setEmail(String email);

  String getTelnbr();

  void setTelnbr(String telnbr);

  int getIdUser();

  void setIdUser(int iduser);
}
