package be.ipl.pae.main;

import config.InjectionBis;

public class Main {

  public static void main(String[] args) throws Exception {
    Serveur serveur = new Serveur();
    InjectionBis injectionService = new InjectionBis();
    injectionService.chargerProperties("dependance.properties");
    injectionService.injecter(serveur);
    serveur.demarrer();
  }
}
