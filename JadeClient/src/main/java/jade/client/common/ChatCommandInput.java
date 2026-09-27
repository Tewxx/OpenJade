// Jade recovery: original class: jade.deps.eLz.jR5yA9
package jade.client.common;

public final class ChatCommandInput {
   public final String normalizedInput;
   public final boolean hasArguments;
   public final String[] vlF;
   public final String znSty;

   private ChatCommandInput(String var1, boolean var2, String[] var3, String var4) {
      this.normalizedInput = var1;
      this.hasArguments = var2;
      this.vlF = var3;
      this.znSty = var4;
   }

   public static ChatCommandInput parse(String var0) {
      String var1 = var0.toLowerCase();
      boolean var2 = var0.contains(" ");
      String[] var3 = var2 ? var0.split(" ") : null;
      String var4 = var3 != null && var3.length > 0 ? var3[0] : var1;
      return new ChatCommandInput(var1, var2, var3, var4);
   }

   public String getTruncatedInput() {
      return this.normalizedInput.length() > 5 ? this.normalizedInput.substring(0, 5) + "..." : this.normalizedInput;
   }
}
