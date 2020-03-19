package be.ipl.pae.biz.objets;

public class DevelopmentTypeImpl implements DevelopmentType {
  
  private int idType;
  private String title;
  
  public DevelopmentTypeImpl() {
    super();
  }
  
  public DevelopmentTypeImpl(int idType, String title) {
    super();
    this.idType = idType;
    this.title = title;
  }
  public int getIdType() {
    return idType;
  }
  public void setIdType(int idType) {
    this.idType = idType;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  
  
}
