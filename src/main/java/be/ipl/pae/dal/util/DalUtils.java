package be.ipl.pae.dal.util;

public class DalUtils {

  /**
   * Escape '_' and '%' to prevent their usage in SQL LIKE cause.
   *
   * @param string The string with chars to escape
   * @return A string with '_' and '%' escaped
   */
  public static String escapeSpecialLikeChar(String string) {

    if (string == null) {
      return "";
    }

    return string.replace("\\", "\\\\").replace("_", "\\_").replace("%", "\\%");

  }

}
