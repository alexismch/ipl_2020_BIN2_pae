package be.ipl.pae.biz.objets;

import be.ipl.pae.biz.dto.UsersFilterDto;

public class UsersFilter implements UsersFilterDto {

  private String name;
  private String city;

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
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
  public String toString() {
    return "UsersFilter{" +
        "name='" + name + '\'' +
        ", city='" + city + '\'' +
        '}';
  }
}
