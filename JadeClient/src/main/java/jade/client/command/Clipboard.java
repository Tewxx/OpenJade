// Jade recovery: original class: jade.deps.eLz.zaEsU08
package jade.client.command;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

public final class Clipboard {
   private Clipboard() {
   }

   public static void copyToClipboard(String var0) {
      StringSelection var1 = new StringSelection(var0);
      Toolkit.getDefaultToolkit().getSystemClipboard().setContents(var1, null);
   }
}
