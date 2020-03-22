package be.ipl.pae.exceptions;

public class FatalException extends Exception {

  public FatalException(String message) {
    super(message);
  }

  public FatalException() {
    super();
  }

  public FatalException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public FatalException(String message, Throwable cause) {
    super(message, cause);
  }

  public FatalException(Throwable cause) {
    super(cause);
  }


}
