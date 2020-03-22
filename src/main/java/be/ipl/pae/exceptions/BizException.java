package be.ipl.pae.exceptions;

public class BizException extends Exception {
  public BizException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public BizException(String message, Throwable cause) {
    super(message, cause);
  }

  public BizException(Throwable cause) {
    super(cause);
  }


  public BizException(String string) {
    super(string);
  }

}
