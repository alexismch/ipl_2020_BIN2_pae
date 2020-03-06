package be.ipl.pae.util;

public class Util {

  /**
   * Vérifie si la chaine n'est pas vide.
   *
   * @param chaine la chaine à vérifier
   * @return true si la chaine n'est pas vide, false si oui
   */
  public static boolean verifNonVide(String chaine) {
    return chaine != null && !"".equals(chaine);
  }

  /**
   * Vérifie si toutes les chaines ne sont pas vides.
   *
   * @param chaines les chaines à vérifier
   * @return true si toutes les chaines ne sont pas vides, false si oui
   */
  public static boolean verifNonVide(String... chaines) {
    for (String chaine : chaines) {
      if (!verifNonVide(chaine)) {
        return false;
      }
    }
    return true;
  }
}
