package be.ipl.pae.ihm.servlets.utils;

import be.ipl.pae.biz.objets.QuoteState;

import javax.servlet.http.HttpServletRequest;

public class ParametersUtils {

  public static boolean checkParamExist(HttpServletRequest req, String name) {
    return req.getParameter(name) != null;
  }

  public static String getParam(HttpServletRequest req, String name)
      throws ParameterException {

    String param = req.getParameter(name);

    if (param != null && !"".equals(param)) {
      return param;
    }

    throw new ParameterException(name + " est requis");
  }

  public static int getParamAsInt(HttpServletRequest req, String name) throws ParameterException {

    String param = getParam(req, name);

    try {
      return Integer.parseInt(param);
    } catch (NumberFormatException ignored) {
    }

    throw new ParameterException(name + ":" + param + " n'est pas un entier");
  }

  public static QuoteState getParamAsQuoteState(HttpServletRequest req, String name)
      throws ParameterException {

    String param = getParam(req, name);

    try {
      return QuoteState.valueOf(param);
    } catch (NullPointerException ignored) {
    }

    throw new ParameterException(name + ":" + param + " n'est pas un état du devis");
  }

}
