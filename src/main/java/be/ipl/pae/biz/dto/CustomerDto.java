package be.ipl.pae.biz.dto;

public interface CustomerDto {
  public String getId_customer() ;
  public void setId_customer(String id_customer) ;
  public String getLastname();
  public void setLastname(String lastname) ;
  public String getFirstname() ;
  public void setFirstname(String firstname) ;
  public String getAddress() ;
  public void setAddress(String address);
  public int getPostal_code() ;
  public void setPostal_code(int postal_code) ;
  public String getCity();
  public void setCity(String city) ;
  public String getEmail();
  public void setEmail(String email);
  public String getTel_nbr() ;
  public void setTel_nbr(String tel_nbr) ;
  public int getId_user() ;
  public void setId_user(int id_user);
}
