package be.ipl.pae.ihm.servlets.utils;

public class ParameterException extends Exception {

  public ParameterException() {
  }

  public ParameterException(String message) {
    super(message);
  }

  public ParameterException(String message, Throwable cause) {
    super(message, cause);
  }

  public ParameterException(Throwable cause) {
    super(cause);
  }

  public ParameterException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}


