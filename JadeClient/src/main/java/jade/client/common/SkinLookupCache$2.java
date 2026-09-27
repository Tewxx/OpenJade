// Jade recovery: original class: jade.deps.eLz.JuozSR$2
package jade.client.common;

import com.mojang.authlib.GameProfile;

public final class SkinLookupCache$2 {
   private final GameProfile tr5;
   private final long expiresAtMillis;

   SkinLookupCache$2(GameProfile var1, long var2) {
      this.tr5 = var1;
      this.expiresAtMillis = var2;
   }

   public GameProfile getProfile() {
      return this.tr5;
   }

   public boolean QHMXet(long var1) {
      return var1 < this.expiresAtMillis;
   }
}
