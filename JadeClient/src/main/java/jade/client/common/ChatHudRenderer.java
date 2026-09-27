// Jade recovery: original class: jade.deps.eLz.CYtgV7V
package jade.client.common;

import java.awt.Color;
import java.util.List;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;

public final class ChatHudRenderer implements IMinecraft {
   private static final int[] BACKGROUND_COLORS = new int[]{
      new Color(170, 107, 148, 50).getRGB(), new Color(122, 158, 134, 50).getRGB(), new Color(16, 16, 16, 50).getRGB(), new Color(64, 114, 148, 50).getRGB()
   };
   private static int LJbi0;
   private static int backgroundIndex = -1;
   private static boolean YGVxj = true;

   private ChatHudRenderer() {
   }

   public static void pickBackgroundColor() {
      int var0 = ClientUtils.getRandom().nextInt(BACKGROUND_COLORS.length);
      int var1 = DistinctIndexPicker.pickDistinctIndex(var0, backgroundIndex, BACKGROUND_COLORS.length);
      backgroundIndex = var1;
      LJbi0 = BACKGROUND_COLORS[var1];
   }

   public static void qzhR8(FontRenderer var0, int var1, int var2, double var3) {
      int var5 = var2 - 195;
      int var6 = var1 - 130;
      int var7 = var1 - 345;
      short var8 = 230;
      double var9 = var2 * var3;
      int var11 = (int)(var9 - (var9 < 2.0 ? 0 : 2));
      int var12 = (int)(var8 * var3 - 2.0);
      int var13 = (int)(mc.displayHeight - (var7 + var8) * var3);
      GL11.glEnable(3089);
      GL11.glScissor(0, var13, var11, var12);
      RenderUtils.NUNei(1000, 1000, LJbi0);
      drawChatLines(var0, var5, var6);
      GL11.glDisable(3089);
   }

   private static void drawChatLines(FontRenderer var0, int var1, int var2) {
      if (YGVxj) {
         YGVxj = false;
         FMMBeTEkVt.appendChatLine("Welcome,", 0);
         FMMBeTEkVt.appendChatLine("Use \"help\" for help.", 0);
      }

      List var3 = FMMBeTEkVt.getChatLines();

      for (int var4 = var3.size() - 1; var4 >= 0; var4--) {
         FVLujVCsQb var5 = FVLujVCsQb.parseColorCode((String)var3.get(var4));
         var0.drawString(var5.LZy, var1, var2, var5.textColor);
         var2 -= var0.FONT_HEIGHT + 5;
      }
   }
}
