package jade.local;

import jade.client.Jade;
import jade.client.common.WindowIcon;
import jade.client.module.client.Settings;

public final class LocalEntrypoint {
   private LocalEntrypoint() {
   }

   public static void initialize() {
      Jade.bbKvwi();
      if (Settings.customWindowIcon != null && Settings.customWindowIcon.isToggled()) {
         WindowIcon.applyCustomIcon();
      }
   }
}
