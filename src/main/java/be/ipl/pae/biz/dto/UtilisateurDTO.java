package be.ipl.pae.biz.dto;

import java.util.Date;

public interface UtilisateurDTO {

  String getNom();

  void setNom(String nom);

  String getPrenom();

  void setPrenom(String prenom);

  String getMdp();

  void setMdp(String mdp);

  String getVille();

  void setVille(String ville);

  String getEmail();

  void setEmail(String email);

  Date getDateInscription();

  void setDateInscription(Date dateInscription);

  String getStatut();

  void setStatut(String statut);
}
