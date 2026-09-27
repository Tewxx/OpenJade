// Jade recovery: original class: jade.deps.eLz.OCeQucL
package jade.client.common;

public enum LoginMethod {
   MICROSOFT("Microsoft"),
   MICROSOFT_REFRESH("Refresh Token"),
   MICROSOFT_V2("Microsoft v2"),
   MSA_ARTIFACT("MSA"),
   TOKEN("Token"),
   COOKIE("Cookie");

   private final String displayName;

   private LoginMethod(String var3) {
      this.displayName = var3;
   }

   public String getDisplayName() {
      return this.displayName;
   }
}
