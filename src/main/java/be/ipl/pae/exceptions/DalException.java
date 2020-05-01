package be.ipl.pae.exceptions;

public class DalException extends Exception {

  public DalException(String message) {
    super(message);
  }

  public DalException() {
    super();
  }

  public DalException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public DalException(String message, Throwable cause) {
    super(message, cause);
  }

  public DalException(Throwable cause) {
    super(cause);
  }


}
