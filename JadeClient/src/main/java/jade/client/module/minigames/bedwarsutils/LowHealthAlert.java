// Jade recovery: original class: jade.deps.eLz.RgIe366
package jade.client.module.minigames.bedwarsutils;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public final class LowHealthAlert {
   private static final Pattern pattern = Pattern.compile("^([A-Za-z0-9_]{1,16}) is on \\d+(?:\\.\\d+)? HP!$");
   private static final long TRACKING_WINDOW_MILLIS = 15000L;
   private static final int MIN_BOW_DRAW_TICKS = 3;
   private double w00;
   private double ZoG;
   private double shotPosZ;
   private long shotAtMillis;

   public void recordBowRelease(Packet<?> var1, Minecraft var2) {
      if (var1 instanceof C07PacketPlayerDigging
         && ((C07PacketPlayerDigging)var1).getStatus() == Action.RELEASE_USE_ITEM
         && var2 != null
         && var2.thePlayer != null) {
         ItemStack var3 = var2.thePlayer.getHeldItem();
         if (var3 != null && var3.getItem() instanceof ItemBow && var2.thePlayer.getItemInUseDuration() >= 3) {
            this.w00 = var2.thePlayer.posX;
            this.ZoG = var2.thePlayer.posY;
            this.shotPosZ = var2.thePlayer.posZ;
            this.shotAtMillis = System.currentTimeMillis();
         }
      }
   }

   public boolean appendShooterDistance(IChatComponent var1, String var2, Minecraft var3) {
      if (this.isTrackingWindowActive() && var1 != null && var2 != null && var3 != null && var3.theWorld != null) {
         Matcher var4 = pattern.matcher(var2.trim());
         if (!var4.matches()) {
            return false;
         } else {
            EntityPlayer var5 = var3.theWorld.getPlayerEntityByName(var4.group(1));
            if (var5 == null) {
               return false;
            } else {
               double var6 = this.w00 - var5.posX;
               double var8 = this.ZoG - var5.posY;
               double var10 = this.shotPosZ - var5.posZ;
               double var12 = Math.sqrt(var6 * var6 + var8 * var8 + var10 * var10);
               ChatComponentText var14 = new ChatComponentText(String.format(Locale.ROOT, " (%.1f blocks)", var12));
               var14.getChatStyle().setColor(NSBrp(var12));
               var1.appendSibling(var14);
               this.SQxv();
               return true;
            }
         }
      } else {
         return false;
      }
   }

   public void SQxv() {
      this.shotAtMillis = 0L;
   }

   private boolean isTrackingWindowActive() {
      if (this.shotAtMillis == 0L) {
         return false;
      } else if (System.currentTimeMillis() - this.shotAtMillis > 15000L) {
         this.SQxv();
         return false;
      } else {
         return true;
      }
   }

   public static EnumChatFormatting NSBrp(double var0) {
      if (var0 >= 100.0) {
         return EnumChatFormatting.DARK_PURPLE;
      } else if (var0 >= 90.0) {
         return EnumChatFormatting.LIGHT_PURPLE;
      } else if (var0 >= 80.0) {
         return EnumChatFormatting.DARK_RED;
      } else if (var0 >= 70.0) {
         return EnumChatFormatting.RED;
      } else if (var0 >= 60.0) {
         return EnumChatFormatting.GOLD;
      } else if (var0 >= 50.0) {
         return EnumChatFormatting.YELLOW;
      } else if (var0 >= 40.0) {
         return EnumChatFormatting.DARK_GREEN;
      } else if (var0 >= 30.0) {
         return EnumChatFormatting.GREEN;
      } else {
         return var0 >= 20.0 ? EnumChatFormatting.WHITE : EnumChatFormatting.GRAY;
      }
   }
}
