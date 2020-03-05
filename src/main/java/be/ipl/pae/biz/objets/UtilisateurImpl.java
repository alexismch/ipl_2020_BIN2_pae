package be.ipl.pae.biz.objets;

import java.util.Date;

class UtilisateurImpl implements Utilisateur {

  private String nom;
  private String prenom;
  private String mdp;
  private String ville;
  private String email;
  private Date dateInscription;
  private String statut;



  public UtilisateurImpl() {
    super();
  }

  public UtilisateurImpl(String nom, String prenom, String mdp, String ville, String email,
      Date dateInscription, String statut) {

    this.nom = nom;
    this.prenom = prenom;
    this.mdp = mdp;
    this.ville = ville;
    this.email = email;
    this.dateInscription = dateInscription;
    this.statut = statut;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getPrenom() {
    return prenom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  public String getMdp() {
    return mdp;
  }

  public void setMdp(String mdp) {
    this.mdp = mdp;
  }

  public String getVille() {
    return ville;
  }

  public void setVille(String ville) {
    this.ville = ville;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getDateInscription() {
    return dateInscription;
  }

  public void setDateInscription(Date dateInscription) {
    this.dateInscription = dateInscription;
  }

  public String getStatut() {
    return statut;
  }

  public void setStatut(String statut) {
    this.statut = statut;
  }



}
