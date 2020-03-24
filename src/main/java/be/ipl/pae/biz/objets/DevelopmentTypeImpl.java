
package be.ipl.pae.biz.objets;

public class DevelopmentTypeImpl implements DevelopmentType {

  private int idType;
  private String title;

  public DevelopmentTypeImpl() {
    super();
  }

  /**
   * Create a DevelopmentTypeImpl object.
   *
   * @param idType the development type id
   * @param title  the development type title
   */
  public DevelopmentTypeImpl(int idType, String title) {
    super();
    this.idType = idType;
    this.title = title;
  }

  @Override
  public int getIdType() {
    return idType;
  }

  @Override
  public void setIdType(int idType) {
    this.idType = idType;
  }

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public void setTitle(String title) {
    this.title = title;
  }
}

