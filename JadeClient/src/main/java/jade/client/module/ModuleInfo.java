// Jade recovery: original class: jade.deps.eLz.xk5ua6EQ
package jade.client.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModuleInfo {
   String[] aliases() default {};

   boolean listed() default true;
}
