package be.ipl.pae.ihm.servlets.utils;

import be.ipl.pae.biz.objets.QuoteState;
import be.ipl.pae.biz.objets.UserStatus;

import javax.servlet.http.HttpServletRequest;

public class ParametersUtils {

  /**
   * Check if the request has a parameter identified by {@code name}.
   *
   * @param req  the request
   * @param name the name of the parameter
   * @return true if the request has a parameter for the {@code name}
   */
  public static boolean checkParamExist(HttpServletRequest req, String name) {
    return req.getParameter(name) != null;
  }

  /**
   * Get the parameter identified by {@code name} in the request.
   *
   * @param req  the request
   * @param name the name of the parameter
   * @return the value of the parameter
   * @throws ParameterException thrown if the parameter do not exit or an empty text
   */
  public static String getParam(HttpServletRequest req, String name)
      throws ParameterException {

    String param = req.getParameter(name);

    if (param != null && !"".equals(param)) {
      return param;
    }

    throw new ParameterException(name + " est requis");
  }

  /**
   * Get the parameter identified by {@code name} in the request.
   *
   * @param req  the request
   * @param name the name of the parameter
   * @return the value of the parameter
   * @throws ParameterException thrown if the parameter do not exit or the value is not a valid
   *                            integer
   */
  public static int getParamAsInt(HttpServletRequest req, String name) throws ParameterException {

    String param = getParam(req, name);

    try {
      return Integer.parseInt(param);
    } catch (NumberFormatException ex) {
      throw new ParameterException(name + ":" + param + " n'est pas un nombre entier");
    }

  }

  /**
   * Get the parameter identified by {@code name} in the request.
   *
   * @param req  the request
   * @param name the name of the parameter
   * @return the value of the parameter
   * @throws ParameterException thrown if the parameter do not exit or the value is not a valid
   *                            {@link QuoteState}
   */
  public static QuoteState getParamAsQuoteState(HttpServletRequest req, String name)
      throws ParameterException {

    String param = getParam(req, name);

    try {
      return QuoteState.valueOf(param);
    } catch (NullPointerException ex) {
      throw new ParameterException(name + ":" + param + " n'est pas un état du devis");
    }

  }

  /**
   * Get the parameter identified by {@code name} in the request.
   *
   * @param req  the request
   * @param name the name of the parameter
   * @return the value of the parameter
   * @throws ParameterException thrown if the parameter do not exit or the value is not a valid
   *                            {@link UserStatus}
   */
  public static UserStatus getParamAsUserStatus(HttpServletRequest req, String name)
      throws ParameterException {

    String param = getParam(req, name);

    try {
      return UserStatus.valueOf(param);
    } catch (NullPointerException ex) {
      throw new ParameterException(name + ":" + param + " n'est pas un status d'utilisateur");
    }

  }

}
