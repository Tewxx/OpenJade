// Jade recovery: original class: jade.deps.eLz.bj3B9yb6
package jade.client.common;

import jade.client.Jade;
import jade.client.module.client.Rendering;
import jade.client.module.render.nametags.MinecraftFont;
import jade.client.module.shared.FormattedTextRenderer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;

public final class ExternalChatOverlay {
   private static final int vUube = 100;
   private static final long MESSAGE_LIFETIME_MILLIS = 10000L;
   private static final long Puec = 1000L;
   private static final List<ExternalChatOverlay$2> XTQhR = new ArrayList<>();

   private ExternalChatOverlay() {
   }

   public static boolean WBGA(String var0) {
      if (Rendering.isExternalOutput() && var0 != null && !var0.isEmpty()) {
         if (ChatUtils.hasFlowTag(var0)) {
            ChatUtils.resolveFlowText(var0);
         }

         synchronized (XTQhR) {
            XTQhR.add(new ExternalChatOverlay$2(var0, System.currentTimeMillis()));

            while (XTQhR.size() > 100) {
               XTQhR.remove(0);
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public static boolean hasVisibleMessages() {
      if (!Rendering.isExternalOutput()) {
         return false;
      } else {
         synchronized (XTQhR) {
            Minecraft var1 = Minecraft.getMinecraft();
            if (var1 == null || !(var1.currentScreen instanceof GuiChat)) {
               removeExpiredMessages(System.currentTimeMillis() - 10000L);
            }

            return !XTQhR.isEmpty();
         }
      }
   }

   public static void renderChatOverlay(ExternalRenderBuffer var0) {
      Minecraft var1 = Minecraft.getMinecraft();
      if (var0 != null && var1 != null && var1.fontRendererObj != null && Rendering.isExternalOutput()) {
         long var2 = System.currentTimeMillis();
         boolean var4 = var1.currentScreen instanceof GuiChat;
         ArrayList var5;
         synchronized (XTQhR) {
            if (!var4) {
               removeExpiredMessages(var2 - 10000L);
            }

            var5 = new ArrayList<>(XTQhR);
         }

         if (!var5.isEmpty()) {
            ScaledResolution var31 = new ScaledResolution(var1);
            float var7 = var31.getScaleFactor();
            float var8 = Math.max(0.01F, var1.gameSettings.chatScale);
            float var9 = var7 * var8;
            int var10 = Math.max(40, (int)((var1.gameSettings.chatWidth * 280.0F + 40.0F) / var8));
            int var11 = (int)((var4 ? var1.gameSettings.chatHeightFocused : var1.gameSettings.chatHeightUnfocused) * 160.0F + 20.0F);
            int var12 = Math.max(1, var11 / 9);
            MinecraftFont var13 = new MinecraftFont(var1.fontRendererObj);
            Rendering var14 = Jade.getModuleManager().getModule(Rendering.class);
            float var15 = var14 == null ? 0.0F : var14.NGLeqy();
            float var16 = var14 == null ? 0.0F : var14.getExternalChatY();
            float var17 = var15 * var31.getScaledWidth() * var7;
            float var18 = var16 * var31.getScaledHeight() * var7;
            boolean var19 = var16 < 0.5F;
            ArrayList var20 = new ArrayList();

            for (ExternalChatOverlay$2 var22 : (java.lang.Iterable<ExternalChatOverlay$2>) (java.lang.Iterable<?>) (var5)) {
               float var23 = var4 ? 1.0F : computeFadeAlpha(var2 - ExternalChatOverlay$2.getTimestampMillis(var22));
               if (!(var23 <= 0.0F)) {
                  int var24 = Math.max(0, Math.min(255, Math.round(255.0F * var23 * (var1.gameSettings.chatOpacity * 0.9F + 0.1F))));
                  String var25 = ChatUtils.kWr1(ExternalChatOverlay$2.getText(var22));
                  List var26 = var1.fontRendererObj.listFormattedStringToWidth(var25, var10);
                  if (var26 == null || var26.isEmpty()) {
                     var26 = Collections.singletonList(var25);
                  }

                  for (String var28 : (java.lang.Iterable<String>) (java.lang.Iterable<?>) (var26)) {
                     String var29 = var26.size() == 1 ? ExternalChatOverlay$2.getText(var22) : ChatUtils.restoreFormattedText(var28);
                     if (var29 == null) {
                        var29 = var28;
                     }

                     var20.add(new ExternalChatOverlay$1(var29, var24 << 24 | 16777215));
                  }
               }
            }

            int var32 = Math.max(0, var20.size() - var12);
            int var33 = 0;
            if (var19) {
               for (int var34 = var32; var34 < var20.size(); var34++) {
                  ExternalChatOverlay$1 var36 = (ExternalChatOverlay$1)var20.get(var34);
                  FormattedTextRenderer.QHFrl(var0, var13, ExternalChatOverlay$1.getText(var36), var17, var18 + var33 * 9.0F * var9, var9, ExternalChatOverlay$1.getColor(var36), true, var2);
                  var33++;
               }
            } else {
               for (int var35 = var20.size() - 1; var35 >= var32; var35--) {
                  ExternalChatOverlay$1 var37 = (ExternalChatOverlay$1)var20.get(var35);
                  FormattedTextRenderer.QHFrl(var0, var13, ExternalChatOverlay$1.getText(var37), var17, var18 - var33 * 9.0F * var9, var9, ExternalChatOverlay$1.getColor(var37), true, var2);
                  var33++;
               }
            }
         }
      }
   }

   public static void clearMessages() {
      synchronized (XTQhR) {
         XTQhR.clear();
      }
   }

   public static float[] getPreviewBounds(float var0, float var1) {
      Minecraft var2 = Minecraft.getMinecraft();
      if (var2 != null && var2.fontRendererObj != null) {
         String var3 = "§lJade §fExternal chat preview";
         String var4 = "§lIRC §bPlayer §8[1]: §fHello!";
         boolean var5 = var1 < new ScaledResolution(var2).getScaledHeight() * 0.5F;
         float var6 = var5 ? 1.0F : -1.0F;
         var2.fontRendererObj.drawStringWithShadow(var3, var0, var1, -1);
         var2.fontRendererObj.drawStringWithShadow(var4, var0, var1 + var6 * 9.0F, -1);
         int var7 = Math.max(var2.fontRendererObj.getStringWidth(var3), var2.fontRendererObj.getStringWidth(var4));
         float var8 = var5 ? var1 : var1 - 9.0F;
         float var9 = var5 ? var1 + 18.0F : var1 + 9.0F;
         return new float[]{var0, var8, var0 + var7, var9};
      } else {
         return new float[]{var0, var1 - 27.0F, var0 + 180.0F, var1};
      }
   }

   private static float computeFadeAlpha(long var0) {
      if (var0 <= 9000L) {
         return 1.0F;
      } else if (var0 >= 10000L) {
         return 0.0F;
      } else {
         float var2 = (float)(10000L - var0) / 1000.0F;
         return var2 * var2;
      }
   }

   private static void removeExpiredMessages(long var0) {
      while (!XTQhR.isEmpty() && ExternalChatOverlay$2.getTimestampMillis(XTQhR.get(0)) < var0) {
         XTQhR.remove(0);
      }
   }
}
