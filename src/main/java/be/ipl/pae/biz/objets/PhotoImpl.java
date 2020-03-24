package be.ipl.pae.biz.objets;

public class PhotoImpl implements Photo {

  private String base64;
  private int id;
  private String title;
  private String idQuote;
  private boolean isVisible;
  private int idType;
  private boolean beforeWork;

  public PhotoImpl() {
    super();
  }

  /**
   * Create a PhotoImpl object.
   *
   * @param base64 the base64 infos
   * @param id the photo id
   * @param title the photo title
   * @param idQuote the quote id which is linked with the photo
   * @param isVisible the visibility of the photo
   * @param idType the development type on the photo
   * @param beforeWork the moment when the photo was taken
   */
  public PhotoImpl(String base64, int id, String title, String idQuote, boolean isVisible,
      int idType, boolean beforeWork) {
    super();
    this.base64 = base64;
    this.id = id;
    this.title = title;
    this.idQuote = idQuote;
    this.isVisible = isVisible;
    this.idType = idType;
    this.beforeWork = beforeWork;
  }

  /*
   * (non-Javadoc)
   *
   * @see be.ipl.pae.biz.objets.PhotoTest#getBase64()
   */
  @Override
  public String getBase64() {
    return base64;
  }

  /*
   * (non-Javadoc)
   *
   * @see be.ipl.pae.biz.objets.PhotoTest#setBase64(java.lang.String)
   */
  @Override
  public void setBase64(String base64) {
    this.base64 = base64;
  }

  /*
   * (non-Javadoc)
   *
   * @see be.ipl.pae.biz.objets.PhotoTest#getId()
   */
  @Override
  public int getId() {
    return id;
  }

  /*
   * (non-Javadoc)
   *
   * @see be.ipl.pae.biz.objets.PhotoTest#setId(int)
   */
  @Override
  public void setId(int id) {
    this.id = id;
  }

  /*
   * (non-Javadoc)
   *
   * @see be.ipl.pae.biz.objets.PhotoTest#getTitle()
   */
  @Override
  public String getTitle() {
    return title;
  }

  /*
   * (non-Javadoc)
   *
   * @see be.ipl.pae.biz.objets.PhotoTest#setTitle(int)
   */
  @Override
  public void setTitle(String title) {
    this.title = title;
  }

  /*
   * (non-Javadoc)
   *
   * @see be.ipl.pae.biz.objets.PhotoTest#getIdQuote()
   */
  @Override
  public String getIdQuote() {
    return idQuote;
  }

  /*
   * (non-Javadoc)
   *
   * @see be.ipl.pae.biz.objets.PhotoTest#setIdQuote(java.lang.String)
   */
  @Override
  public void setIdQuote(String idQuote) {
    this.idQuote = idQuote;
  }

  /*
   * (non-Javadoc)
   *
   * @see be.ipl.pae.biz.objets.PhotoTest#isVisible()
   */
  public boolean isVisible() {
    return isVisible;
  }

  /*
   * (non-Javadoc)
   *
   * @see be.ipl.pae.biz.objets.PhotoTest#setVisible(boolean)
   */
  @Override
  public void setVisible(boolean isVisible) {
    this.isVisible = isVisible;
  }

  /*
   * (non-Javadoc)
   *
   * @see be.ipl.pae.biz.objets.PhotoTest#getIdType()
   */
  @Override
  public int getIdType() {
    return idType;
  }

  /*
   * (non-Javadoc)
   *
   * @see be.ipl.pae.biz.objets.PhotoTest#setIdType(int)
   */
  @Override
  public void setIdType(int idType) {
    this.idType = idType;
  }

  /*
   * (non-Javadoc)
   *
   * @see be.ipl.pae.biz.objets.PhotoTest#isBeforeWork()
   */
  @Override
  public boolean isBeforeWork() {
    return beforeWork;
  }

  /*
   * (non-Javadoc)
   *
   * @see be.ipl.pae.biz.objets.PhotoTest#setBeforeWork(boolean)
   */
  @Override
  public void setBeforeWork(boolean beforeWork) {
    this.beforeWork = beforeWork;
  }
}
