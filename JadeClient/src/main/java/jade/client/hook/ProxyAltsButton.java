// Jade recovery: original class: jade.deps.eLz.BH7V09Y
package jade.client.hook;

import jade.client.gui.ProxyAltsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class ProxyAltsButton {
   private static final int BUTTON_MARGIN = 4;
   private static final int BUTTON_HEIGHT = 20;
   private static boolean mouseWasDown;

   public static void WXs6(Minecraft var0) {
      if (var0 != null && var0.currentScreen != null && isServerOrWorldSelectScreen(var0.currentScreen)) {
         ScaledResolution var1 = new ScaledResolution(var0);
         int var2 = var1.getScaledWidth();
         int var3 = var1.getScaledHeight();
         int var4 = Mouse.getX() * var2 / var0.displayWidth;
         int var5 = var3 - Mouse.getY() * var3 / var0.displayHeight - 1;
         int var6 = var0.fontRendererObj.getStringWidth("Proxy & Alts");
         int var7 = Math.max(104, var6 + 20);
         int var8 = var0.currentScreen.width - 4 - var7;
         byte var9 = 4;
         boolean var10 = Mouse.isButtonDown(0);
         boolean var11 = var10 && !mouseWasDown;
         mouseWasDown = var10;
         if (var11 && var4 >= var8 && var4 < var8 + var7 && var5 >= var9 && var5 < var9 + 20) {
            var0.displayGuiScreen(new ProxyAltsScreen(var0.currentScreen));
         } else {
            GL11.glMatrixMode(5889);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0, var2, var3, 0.0, 1000.0, 3000.0);
            GL11.glMatrixMode(5888);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
            GuiButton var12 = new GuiButton(0, var8, var9, var7, 20, "Proxy & Alts");
            var12.drawButton(var0, var4, var5);
            GL11.glMatrixMode(5889);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
            GL11.glPopMatrix();
         }
      } else {
         mouseWasDown = Mouse.isButtonDown(0);
      }
   }

   public static boolean isServerOrWorldSelectScreen(GuiScreen var0) {
      if (var0 == null) {
         return false;
      } else if (!(var0 instanceof GuiMultiplayer) && !(var0 instanceof GuiSelectWorld)) {
         Class var1 = var0.getClass();

         while (var1 != null && var1 != Object.class) {
            String var2 = var1.getSimpleName().toLowerCase();
            if (!var2.contains("multiplayer") && !var2.contains("serverlist") && !var2.contains("server_list")) {
               if (!var2.contains("selectworld") && !var2.contains("worldselection") && !var2.contains("world_selection") && !var2.contains("singleplayer")) {
                  var1 = var1.getSuperclass();
                  continue;
               }

               return true;
            }

            return true;
         }

         return false;
      } else {
         return true;
      }
   }
}
