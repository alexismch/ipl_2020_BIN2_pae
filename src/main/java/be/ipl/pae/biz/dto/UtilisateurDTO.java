package be.ipl.pae.biz.dto;

import java.util.Date;

public interface UtilisateurDTO {


  public String getNom();

  public void setNom(String nom);

  public String getPrenom();

  public void setPrenom(String prenom);

  public String getMdp();

  public void setMdp(String mdp);

  public String getVille();

  public void setVille(String ville);

  public String getEmail();

  public void setEmail(String email);

  public Date getDateInscription();

  public void setDateInscription(Date dateInscription);

  public String getStatut();

  public void setStatut(String statut);


}
