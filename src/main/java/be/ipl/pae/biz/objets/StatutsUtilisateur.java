package be.ipl.pae.biz.objets;

public enum StatutsUtilisateur {

  NON_VALIDE("nv"), CLIENT("c"), OUVRIER("o");

  private String statut;

  private StatutsUtilisateur(String statut) {
    this.statut = statut;
  }

  public String getStatut() {
    return this.statut;
  }

}
