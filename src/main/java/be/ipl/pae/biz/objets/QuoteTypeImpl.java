package be.ipl.pae.biz.objets;

public class QuoteTypeImpl implements QuoteType {

 private String id_quote;
 
private int id_type;


public QuoteTypeImpl() {
  super();
}

public QuoteTypeImpl(String id_quote, int id_type) {
  super();
  this.id_quote = id_quote;
  this.id_type = id_type;
}

public String getId_quote() {
  return id_quote;
}

public void setId_quote(String id_quote) {
  this.id_quote = id_quote;
}

public int getId_type() {
  return id_type;
}

public void setId_type(int id_type) {
  this.id_type = id_type;
}
}
