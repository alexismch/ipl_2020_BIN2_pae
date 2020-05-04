package be.ipl.pae.biz.objets;

class PhotoVisibleImpl implements PhotoVisible {

  private int id;
  private String title;
  private String base64;
  private String developmentType;

  public PhotoVisibleImpl() {
    super();
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBase64() {
    return base64;
  }

  public void setBase64(String base64) {
    this.base64 = base64;
  }

  public String getDevelopmentType() {
    return developmentType;
  }

  public void setDevelopmentType(String developmentType) {
    this.developmentType = developmentType;
  }
}
