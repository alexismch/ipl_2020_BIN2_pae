package be.ipl.pae.dal;

public class DalUtils {

  /**
   * 
   * @param s
   * @return
   */
  public static String changeSpecialLikeChar(String string) {

    if (string == null) {
      return null;
    }

    return string.replace("_", "\\_").replace("%", "\\%");

  }

}
