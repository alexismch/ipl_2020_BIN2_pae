package be.ipl.pae.biz.objets;

public enum State {
  DEVIS_INTRODUIT("Devis introduit"),
  COMMANDE_PASSEE("Commande passée"),
  DATE_CONFIRMEE("Date confirmée"),
  DATE_REPOUSSEE("Date repoussée"),
  FACTURE_PARTIELLEMENT("Facturé partiellement"),
  FACTURE_TOTALEMENT("Facturé totalement"),
  PAYE("Payé"),
  ANNULE("Annulé");

  private String title;

  State(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }
}
