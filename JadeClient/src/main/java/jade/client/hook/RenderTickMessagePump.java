// Jade recovery: original class: jade.deps.eLz.ilUaD3s2
package jade.client.hook;

import jade.client.Jade;
import jade.client.common.ChatUtils;
import jade.client.common.ClientUtils;
import jade.client.common.EventBus;
import jade.client.common.EventPhase;
import jade.client.common.ExternalChatOverlay;
import jade.client.event.RenderTickEvent;
import jade.client.module.Module;
import jade.client.module.client.Gui;
import jade.client.module.client.Settings;
import jade.deps.loader107.StartupAnnouncement$0;
import jade.deps.loader107.StartupAnnouncement;
import jade.deps.loader107.IrcMessageBus$1;
import jade.deps.loader107.IrcMessageBus;
import jade.deps.loader107.SubscriptionState;
import jade.deps.loader107.PendingChatQueue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;

public final class RenderTickMessagePump {
   private static final float gxVx = 1.75F;
   private static final float ANNOUNCEMENT_HIDE_SOUND_PITCH = 0.55F;
   private static final float IRC_SOUND_PITCH = 1.15F;
   private static boolean insideRenderTick;
   private static boolean sentPresenceMessage;
   private static boolean announcementVisible;
   private static long lastNotificationTimestamp = -1L;

   private RenderTickMessagePump() {
   }

   public static void Rlzak() {
      resetGlState();
      insideRenderTick = false;
   }

   public static void onRenderTickEnd(float var0) {
      if (!insideRenderTick) {
         insideRenderTick = true;

         try {
            Minecraft var1 = Minecraft.getMinecraft();
            EventBus.post(new RenderTickEvent(EventPhase.END, var0));
            if (var1 != null && var1.thePlayer != null) {
               dIda();

               String var2;
               while ((var2 = PendingChatQueue.pollMessage()) != null) {
                  if (!var2.isEmpty()) {
                     try {
                        deliverChatMessage(var1, formatExternalChatMessage(var2));
                        if (owzI()) {
                           playNotificationSound(var1, 1.15F);
                        }
                     } catch (Throwable var10) {
                        break;
                     }
                  }
               }

               while ((var2 = IrcMessageBus.pollNotice()) != null) {
                  if (!var2.isEmpty()) {
                     ClientUtils.sendJadeMessage("Jade", var2);
                  }
               }

               IrcMessageBus$1 var3;
               while ((var3 = IrcMessageBus.pollIncomingMessage()) != null) {
                  if (Settings.irc == null || Settings.irc.isToggled()) {
                     try {
                        deliverChatMessage(var1, formatIrcMessage(var3));
                        if (isIrcSoundsEnabled()) {
                           playNotificationSound(var1, 1.15F);
                        }
                     } catch (Throwable var9) {
                        break;
                     }
                  }
               }
            }

            StartupAnnouncement$0 var13 = StartupAnnouncement.getActiveAnnouncement();
            if (var13 != null) {
               if (owzI() && (!announcementVisible || lastNotificationTimestamp != var13.startedAt)) {
                  playNotificationSound(var1, 1.75F);
                  announcementVisible = true;
                  lastNotificationTimestamp = var13.startedAt;
               }

               UpdateNotification.LkDv(var13.startedAt, var13.firstText, var13.secondText);
            } else if (announcementVisible) {
               if (owzI()) {
                  playNotificationSound(var1, 0.55F);
               }

               announcementVisible = false;
               lastNotificationTimestamp = -1L;
            }
         } finally {
            resetGlState();
         }
      }
   }

   private static void resetGlState() {
      try {
         GL20.glUseProgram(0);
         Minecraft var0 = Minecraft.getMinecraft();
         if (var0 != null && var0.getFramebuffer() != null) {
            var0.getFramebuffer().bindFramebuffer(true);
         }

         GlStateManager.setActiveTexture(33984);
         GL13.glActiveTexture(33984);
         GlStateManager.bindTexture(0);
         GlStateManager.disableBlend();
         GL11.glDisable(3042);
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GL14.glBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.enableTexture2D();
         GL11.glEnable(3553);
         GlStateManager.enableAlpha();
         GlStateManager.alphaFunc(516, 0.1F);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      } catch (Throwable var1) {
      }
   }

   private static String formatExternalChatMessage(String var0) {
      String var1 = var0.replace('&', '§');
      String[] var2 = ClientUtils.getThemeGradientColors();
      return ChatUtils.applyFlowGradient("§lJade", var2) + " " + var1;
   }

   private static String formatIrcMessage(IrcMessageBus$1 var0) {
      String[] var1 = ClientUtils.getThemeGradientColors();
      String var2 = IrcMessageBus.sanitizeSenderName(var0.senderName);
      String var3 = var0.timestamp >= 0L ? String.valueOf(var0.timestamp) : "?";
      String var4 = IrcMessageBus.sanitizeMessage(var0.message);
      String var5 = ChatUtils.applyFlowGradient("§8[§r" + var3 + "§8]", var1);
      return ChatUtils.applyFlowGradient("§lIRC", var1) + " §b" + var2 + " " + var5 + "§8: §f" + var4;
   }

   private static void deliverChatMessage(Minecraft var0, String var1) {
      if (!ExternalChatOverlay.WBGA(var1)) {
         var0.thePlayer.addChatMessage(new ChatComponentText(ChatUtils.resolveFlowText(var1)));
      }
   }

   private static boolean owzI() {
      return Settings.systemNotificationSounds == null || Settings.systemNotificationSounds.isToggled();
   }

   private static boolean isIrcSoundsEnabled() {
      return Settings.ircSounds == null || Settings.ircSounds.isToggled();
   }

   private static void dIda() {
      if (!sentPresenceMessage) {
         sentPresenceMessage = true;
         String var0 = SubscriptionState.getDiscordUsername();
         if (var0 == null || var0.isEmpty()) {
            try {
               Minecraft var1 = Minecraft.getMinecraft();
               if (var1 != null && var1.getSession() != null) {
                  var0 = var1.getSession().getUsername();
               }
            } catch (Throwable var2) {
            }
         }

         if (var0 == null || var0.isEmpty()) {
            var0 = "player";
         }

         StartupAnnouncement.publishAnnouncement(
            System.currentTimeMillis(), "Hi |" + var0 + "|", "Open GUI with |" + Fnroft() + "|"
         );
      }
   }

   private static String Fnroft() {
      Module var0 = Jade.getModuleManager().getModule(Gui.class);
      int var1 = var0 == null ? 25 : var0.getKeycode();
      if (var1 == 1069) {
         return "MScrollUp";
      } else if (var1 == 1070) {
         return "MScrollDown";
      } else if (var1 >= 1000) {
         return "M" + (var1 - 1000);
      } else {
         String var2 = Keyboard.getKeyName(var1);
         return var2 != null && !var2.isEmpty() ? var2 : "P";
      }
   }

   private static void playNotificationSound(Minecraft var0, float var1) {
      if (var0 != null && var0.thePlayer != null) {
         try {
            var0.thePlayer.playSound("note.pling", 1.0F, var1);
         } catch (Throwable var3) {
         }
      }
   }
}
