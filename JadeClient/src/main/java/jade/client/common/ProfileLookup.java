// Jade recovery: original class: jade.deps.eLz.YUCNnlg
package jade.client.common;

import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.ProfileLookupCallback;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.net.Proxy;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ProfileLookup {
   private final GameProfileRepository gameProfileRepository = new YggdrasilAuthenticationService(Proxy.NO_PROXY, UUID.randomUUID().toString()).createProfileRepository();
   private final ExecutorService executorService;

   public ProfileLookup(int var1) {
      this.executorService = Executors.newFixedThreadPool(var1);
   }

   public void lookupProfileAsync(final String var1, final ProfileLookup$2 var2) {
      this.executorService.execute(new Runnable() {
         @Override
         public void run() {
            final GameProfile[] var1x = new GameProfile[1];

            try {
               ProfileLookup.getGameProfileRepository(ProfileLookup.this).findProfilesByNames(new String[]{var1}, Agent.MINECRAFT, new ProfileLookupCallback() {
                  public void onProfileLookupSucceeded(GameProfile var1xx) {
                     var1x[0] = var1xx;
                  }

                  public void onProfileLookupFailed(GameProfile var1xx, Exception var2x) {
                  }
               });
            } catch (Exception var3) {
            }

            var2.onProfileResolved(var1x[0]);
         }
      });
   }

   public void RWAZV() {
      this.executorService.shutdownNow();
   }

   public static GameProfileRepository getGameProfileRepository(ProfileLookup var0) {
      return var0.gameProfileRepository;
   }
}
