// Jade recovery: original class: jade.deps.eLz.EXQhDjf8$1
package jade.client.common;

import net.minecraft.client.gui.FontRenderer;

public final class FormattingState {
   private char XlB;
   private boolean obfuscated;
   private boolean AMq3;
   private boolean mzFb;
   private boolean underlined;
   private boolean italic;

   FormattingState() {
   }

   private void acceptCode(char var1) {
      char var2 = Character.toLowerCase(var1);
      if ("0123456789abcdef".indexOf(var2) >= 0) {
         this.XlB = var2;
         this.resetFormatting();
      } else {
         switch (var2) {
            case 'k':
               this.obfuscated = true;
               break;
            case 'l':
               this.AMq3 = true;
               break;
            case 'm':
               this.mzFb = true;
               break;
            case 'n':
               this.underlined = true;
               break;
            case 'o':
               this.italic = true;
            case 'p':
            case 'q':
            default:
               break;
            case 'r':
               this.XlB = 0;
               this.resetFormatting();
         }
      }
   }

   private void resetFormatting() {
      this.obfuscated = false;
      this.AMq3 = false;
      this.mzFb = false;
      this.underlined = false;
      this.italic = false;
   }

   private String buildFormatString() {
      StringBuilder var1 = new StringBuilder(12);
      if (this.XlB != 0) {
         var1.append('§').append(this.XlB);
      }

      if (this.obfuscated) {
         var1.append('§').append('k');
      }

      if (this.AMq3) {
         var1.append('§').append('l');
      }

      if (this.mzFb) {
         var1.append('§').append('m');
      }

      if (this.underlined) {
         var1.append('§').append('n');
      }

      if (this.italic) {
         var1.append('§').append('o');
      }

      return var1.toString();
   }

   private int applyTrackedColor(int var1, FontRenderer var2) {
      int var3 = var1 >>> 24;
      if (var3 == 0) {
         var3 = 255;
      }

      int var4 = this.XlB == 0 ? var1 & 16777215 : var2.getColorCode(this.XlB) & 16777215;
      return var3 << 24 | var4;
   }

   public static void acceptFormatCode(FormattingState var0, char var1) {
      var0.acceptCode(var1);
   }

   public static boolean isBold(FormattingState var0) {
      return var0.AMq3;
   }

   public static String getFormattingString(FormattingState var0) {
      return var0.buildFormatString();
   }

   public static int applyColorCode(FormattingState var0, int var1, FontRenderer var2) {
      return var0.applyTrackedColor(var1, var2);
   }
}
