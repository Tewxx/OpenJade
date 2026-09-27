// Jade recovery: original class: jade.deps.eLz.HcXg1y
package jade.client.core;

import jade.client.common.Account;
import jade.client.common.AccountStore;
import jade.client.common.SessionAccessor;
import jade.client.common.Subscribe;
import jade.client.event.GuiOpenEvent;
import jade.client.event.LoadWorldEvent;
import jade.client.event.PacketReceiveEvent;
import jade.inject.RuntimeAccess;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Session;

public class BanTracker {
   private static final long QSe = 1000L;
   private static final long ONE_MINUTE_MILLIS = 60000L;
   private static final long ONE_HOUR_MILLIS = 3600000L;
   private static final long ONE_DAY_MILLIS = 86400000L;
   private static final Pattern BAN_DURATION_PATTERN = Pattern.compile("(?:temporarily banned for|temporarily blocked for)\\s+((?:\\d+\\s*[dhms]\\s*)+)", 2);
   private static final Pattern DURATION_UNIT_PATTERN = Pattern.compile("(\\d+)\\s*([dhms])", 2);
   private final Minecraft mc = Minecraft.getMinecraft();

   @Subscribe
   public void onPacketReceive(PacketReceiveEvent var1) {
      if (var1.ys98() instanceof S40PacketDisconnect) {
         S40PacketDisconnect var2 = (S40PacketDisconnect)var1.ys98();
         IChatComponent var3 = var2.getReason();
         if (var3 != null) {
            String var4 = EnumChatFormatting.getTextWithoutFormattingCodes(var3.getFormattedText());
            if (var4 == null) {
               var4 = var3.getUnformattedText();
            }

            if (var4 != null) {
               long var5 = this.parseBanExpiry(var4);
               if (var5 != 0L) {
                  this.setAccountBanExpiry(var5);
               }
            }
         }
      }
   }

   @Subscribe
   public void onGuiOpen(GuiOpenEvent var1) {
      if (var1.guiScreen instanceof GuiDisconnected) {
         IChatComponent var2 = this.getDisconnectMessage((GuiDisconnected)var1.guiScreen);
         if (var2 != null) {
            String var3 = EnumChatFormatting.getTextWithoutFormattingCodes(var2.getFormattedText());
            if (var3 == null) {
               var3 = var2.getUnformattedText();
            }

            if (var3 != null) {
               long var4 = this.parseBanExpiry(var3);
               if (var4 != 0L) {
                  this.setAccountBanExpiry(var4);
               }
            }
         }
      }
   }

   @Subscribe
   public void onLoadWorld(LoadWorldEvent var1) {
      ServerData var2 = this.mc.getCurrentServerData();
      if (var2 != null && var2.serverIP != null) {
         String var3 = var2.serverIP.toLowerCase();
         if (var3.endsWith("hypixel.net") || var3.endsWith("hypixel.io")) {
            this.setAccountBanExpiry(0L);
         }
      }
   }

   private long parseBanExpiry(String var1) {
      String var2 = var1.trim().replace('\n', ' ');
      String var3 = var2.toLowerCase();
      if (var3.indexOf("permanently banned from this server") < 0 && var3.indexOf("your account has been blocked") < 0) {
         String var4 = this.extractBetween(var3, "you are temporarily banned for ", " from this server!");
         if (var4 == null) {
            var4 = this.extractBetween(var3, "your account is temporarily blocked for ", " from this server!");
         }

         if (var4 == null) {
            Matcher var5 = BAN_DURATION_PATTERN.matcher(var3);
            if (var5.find()) {
               var4 = var5.group(1);
            }
         }

         if (var4 == null) {
            return 0L;
         } else {
            long var7 = this.parseDurationMillis(var4);
            return var7 > 0L ? System.currentTimeMillis() + var7 : 0L;
         }
      } else {
         return -1L;
      }
   }

   private String extractBetween(String var1, String var2, String var3) {
      int var4 = var1.indexOf(var2);
      if (var4 < 0) {
         return null;
      } else {
         var4 += var2.length();
         int var5 = var1.indexOf(var3, var4);
         return var5 < 0 ? null : var1.substring(var4, var5).trim();
      }
   }

   private long parseDurationMillis(String var1) {
      long var2 = 0L;
      Matcher var4 = DURATION_UNIT_PATTERN.matcher(var1);

      while (var4.find()) {
         long var5 = Long.parseLong(var4.group(1));
         char var7 = Character.toLowerCase(var4.group(2).charAt(0));
         switch (var7) {
            case 'd':
               var2 += var5 * 86400000L;
               break;
            case 'h':
               var2 += var5 * 3600000L;
               break;
            case 'm':
               var2 += var5 * 60000L;
               break;
            case 's':
               var2 += var5 * 1000L;
         }
      }

      return var2;
   }

   private IChatComponent getDisconnectMessage(GuiDisconnected var1) {
      try {
         Field var2 = this.NSoK(GuiDisconnected.class, "message", "field_146304_f");
         return var2 == null ? null : (IChatComponent)var2.get(var1);
      } catch (Exception var3) {
         return null;
      }
   }

   private Field NSoK(Class<?> var1, String... var2) {
      Field var3 = RuntimeAccess.resolveMappedField(var1, var2);
      if (var3 != null) {
         return var3;
      } else {
         for (Field var7 : var1.getDeclaredFields()) {
            if (IChatComponent.class.isAssignableFrom(var7.getType())) {
               var7.setAccessible(true);
               return var7;
            }
         }

         return null;
      }
   }

   private void setAccountBanExpiry(long var1) {
      Session var3 = SessionAccessor.getSession();
      if (var3 != null && var3.getUsername() != null) {
         String var4 = var3.getUsername();

         for (Account var6 : AccountStore.getAccounts()) {
            if (var4.equalsIgnoreCase(var6.zYgb())) {
               var6.setTimestamp(var1);
               AccountStore.saveToDisk();
               return;
            }
         }
      }
   }
}
