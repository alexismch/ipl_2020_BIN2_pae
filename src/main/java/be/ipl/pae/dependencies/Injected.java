package be.ipl.pae.dependencies;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Injected {

  String DEFAULT = "";

  /**
   * If set to something different than {@link Injected#DEFAULT}, {@link InjectionService} will
   * search for the property specified instead of the full class name
   *
   * @return the name of the property
   */
  String value() default DEFAULT;
}
