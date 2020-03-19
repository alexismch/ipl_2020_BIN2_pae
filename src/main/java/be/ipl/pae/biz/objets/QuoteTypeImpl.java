package be.ipl.pae.biz.objets;

public class QuoteTypeImpl implements QuoteType {

 private String idquote;
 
private int idtype;


public QuoteTypeImpl() {
  super();
}

public QuoteTypeImpl(String idquote, int idtype) {
  super();
  this.idquote = idquote;
  this.idtype = idtype;
}

public String getIdquote() {
  return idquote;
}

public void setIdquote(String idquote) {
  this.idquote = idquote;
}

public int getIdtype() {
  return idtype;
}

public void setIdtype(int idtype) {
  this.idtype = idtype;
}
}
