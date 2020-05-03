package be.ipl.pae.biz.objets;

class UsersFilterImpl implements UsersFilter {

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
    return "UsersFilter{" + "name='" + name + '\'' + ", city='" + city + '\'' + '}';
  }
}
