// Jade recovery: original class: jade.deps.eLz.brI8qAjH
package jade.client.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Subscribe {
   EventPriority priority() default EventPriority.NORMAL;
}
