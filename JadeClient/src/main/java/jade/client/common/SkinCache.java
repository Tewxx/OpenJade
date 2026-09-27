// Jade recovery: original class: jade.deps.eLz.ow54Qv6V
package jade.client.common;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import jade.client.Jade;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class SkinCache {
   private static final long CACHE_TTL_MILLIS = 1800000L;
   private static final SkinLookupCache skinLookupCache = new SkinLookupCache(1800000L);
   private static final ProfileLookup profileLookup = new ProfileLookup(3);

   private SkinCache() {
   }

   public static ResourceLocation getPlayerSkin(String var0, NetworkPlayerInfo var1) {
      String var2 = OTXby(var0);
      if (var2.isEmpty()) {
         return DefaultPlayerSkin.getDefaultSkin(EntityPlayer.getOfflineUUID("Steve"));
      } else {
         if (var1 != null && var1.getGameProfile() != null) {
            GameProfile var3 = var1.getGameProfile();
            UUID var4 = var3.getId();
            if (var4 != null) {
               skinLookupCache.putPlayerId(var2, var4);
            }

            if (var3.getName() != null && Jade.relationManager != null) {
               Jade.relationManager.refreshDisplayName(var3.getName());
            }

            ResourceLocation var5 = var1.getLocationSkin();
            if (var5 != null) {
               skinLookupCache.putSkinLocation(var2, var5);
               return var5;
            }
         }

         ResourceLocation var6 = skinLookupCache.getSkinLocation(var2);
         if (var6 != null) {
            return var6;
         } else {
            if (!skinLookupCache.hasLookupFailed(var2)) {
               requestSkinLookup(var2, var0);
            }

            UUID var7 = skinLookupCache.THHIYC(var2);
            if (var7 == null) {
               var7 = EntityPlayer.getOfflineUUID(var0);
               skinLookupCache.putPlayerId(var2, var7);
            }

            return DefaultPlayerSkin.getDefaultSkin(var7);
         }
      }
   }

   public static void cachePlayerId(String var0, UUID var1) {
      String var2 = OTXby(var0);
      if (!var2.isEmpty() && var1 != null) {
         skinLookupCache.putPlayerId(var2, var1);
         requestSkinLookup(var2, var0);
      }
   }

   public static void RJcjhaH(String var0) {
      String var1 = OTXby(var0);
      if (!var1.isEmpty()) {
         skinLookupCache.invalidate(var1);
      }
   }

   public static void processPendingLookup() {
      SkinLookupCache$1 var0 = skinLookupCache.pollPendingLookup();
      if (var0 != null) {
         try {
            Minecraft var1 = Minecraft.getMinecraft();
            if (var1 == null || var1.getSkinManager() == null) {
               return;
            }

            Map var2 = var1.getSkinManager().loadSkinFromCache(var0.DxCclG());
            MinecraftProfileTexture var3 = var2 == null ? null : (MinecraftProfileTexture)var2.get(Type.SKIN);
            if (var3 != null) {
               ResourceLocation var4 = var1.getSkinManager().loadSkin(var3, Type.SKIN);
               if (var4 == null) {
                  skinLookupCache.markLookupFailed(var0.getCacheKey());
               } else {
                  skinLookupCache.putSkinLocation(var0.getCacheKey(), var4);
                  skinLookupCache.LqrS(var0.getCacheKey());
               }

               return;
            }

            skinLookupCache.markLookupFailed(var0.getCacheKey());
         } catch (Exception var8) {
            skinLookupCache.markLookupFailed(var0.getCacheKey());
            return;
         } finally {
            skinLookupCache.IsRuo(var0.getCacheKey());
         }
      }
   }

   private static void requestSkinLookup(final String var0, String var1) {
      long var2 = System.currentTimeMillis();
      SkinLookupCache$2 var4 = skinLookupCache.getProfileEntry(var0);
      if (var4 != null && var4.QHMXet(var2)) {
         if (var4.getProfile() == null) {
            skinLookupCache.markLookupFailed(var0);
         } else {
            applyGameProfile(var0, var4.getProfile());
         }
      } else if (skinLookupCache.markLookupPending(var0)) {
         profileLookup.lookupProfileAsync(var1, new ProfileLookup$2() {
            @Override
            public void onProfileResolved(GameProfile var1) {
               try {
                  if (var1 == null) {
                     SkinCache.cacheProfileResult(var0, null);
                     SkinCache.QSWNI().markLookupFailed(var0);
                     return;
                  }

                  Minecraft var2x = Minecraft.getMinecraft();
                  GameProfile var3 = var2x.getSessionService().fillProfileProperties(var1, false);
                  if (var3 != null) {
                     SkinCache.cacheProfileResult(var0, var3);
                     SkinCache.applyResolvedProfile(var0, var3);
                     return;
                  }

                  SkinCache.cacheProfileResult(var0, null);
                  SkinCache.QSWNI().markLookupFailed(var0);
               } catch (Exception var7) {
                  SkinCache.cacheProfileResult(var0, null);
                  SkinCache.QSWNI().markLookupFailed(var0);
                  return;
               } finally {
                  SkinCache.QSWNI().clearLookupPending(var0);
               }
            }
         });
      }
   }

   private static void applyGameProfile(String var0, GameProfile var1) {
      if (var1 == null) {
         skinLookupCache.markLookupFailed(var0);
      } else {
         if (var1.getId() != null) {
            skinLookupCache.putPlayerId(var0, var1.getId());
         }

         if (var1.getName() != null && Jade.relationManager != null) {
            Jade.relationManager.refreshDisplayName(var1.getName());
         }

         skinLookupCache.CgFb(var0, var1);
      }
   }

   private static void cacheProfileWithTimestamp(String var0, GameProfile var1) {
      skinLookupCache.putProfileEntry(var0, var1, System.currentTimeMillis());
   }

   private static String OTXby(String var0) {
      return SkinLookupCache.normalizeName(var0);
   }

   public static void shutdown() {
      profileLookup.RWAZV();
      skinLookupCache.clearAll();
   }

   public static void cacheProfileResult(String var0, GameProfile var1) {
      cacheProfileWithTimestamp(var0, var1);
   }

   public static SkinLookupCache QSWNI() {
      return skinLookupCache;
   }

   public static void applyResolvedProfile(String var0, GameProfile var1) {
      applyGameProfile(var0, var1);
   }
}
