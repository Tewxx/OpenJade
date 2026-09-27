// Jade recovery: original class: jade.deps.eLz.WcV2INOqX
package jade.client.common;

public enum TextInputAction {
   TYPE,
   CANCEL,
   SUBMIT;

   public static TextInputAction from(int var0, int var1, int var2, int var3) {
      if (var0 == var1) {
         return CANCEL;
      } else {
         return var0 != var2 && var0 != var3 ? TYPE : SUBMIT;
      }
   }
}
