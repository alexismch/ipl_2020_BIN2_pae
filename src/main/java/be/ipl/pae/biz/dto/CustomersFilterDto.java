package be.ipl.pae.biz.dto;

public interface CustomersFilterDto {

  String getName();

  void setName(String name);

  int getPostalCode();

  void setPostalCode(int postalCode);

  String getCity();

  void setCity(String city);

  boolean isOnlyNotLinked();

  void setOnlyNotLinked(boolean onlyNotLinked);

}
