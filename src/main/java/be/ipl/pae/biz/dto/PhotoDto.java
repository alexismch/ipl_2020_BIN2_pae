package be.ipl.pae.biz.dto;

public interface PhotoDto {

  String getBase64();

  void setBase64(String base64);

  int getId();

  void setId(int id);

  int getTitle();

  void setTitle(int title);

  String getIdQuote();

  void setIdQuote(String idQuote);

  boolean isVisible();

  void setVisible(boolean isVisible);

  int getIdType();

  void setIdType(int idType);

  boolean isBeforeWork();

  void setBeforeWork(boolean beforeWork);
}
