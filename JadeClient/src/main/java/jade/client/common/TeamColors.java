// Jade recovery: original class: jade.deps.eLz.GZAMgwA1mf
package jade.client.common;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.IChatComponent;

public final class TeamColors {
   private static final int[] COLOR_PALETTE = new int[]{
      TeamColorPalette.qxQ0,
      TeamColorPalette.Scj,
      TeamColorPalette.DARK_GREEN,
      TeamColorPalette.DARK_AQUA,
      TeamColorPalette.zX1,
      TeamColorPalette.DARK_PURPLE,
      TeamColorPalette.JnTv,
      TeamColorPalette.GRAY,
      TeamColorPalette.vxM,
      TeamColorPalette.Kmp,
      TeamColorPalette.GREEN,
      TeamColorPalette.VaZ,
      TeamColorPalette.YVw,
      TeamColorPalette.LIGHT_PURPLE,
      TeamColorPalette.bqS,
      -1
   };

   private TeamColors() {
   }

   public static int getEntityTeamColor(Entity var0) {
      if (var0 == null) {
         return -1;
      } else {
         if (var0 instanceof EntityPlayer) {
            ScorePlayerTeam var1 = (ScorePlayerTeam)((EntityPlayer)var0).getTeam();
            String var2 = var1 == null ? "" : FontRenderer.getFormatFromString(var1.getColorPrefix());
            if (var2.length() >= 2) {
               char var3 = Character.toLowerCase(var2.charAt(1));
               if (var3 == 'f') {
                  return 16777215;
               }

               int var4 = getColorForCode(var3);
               if (var4 != -1) {
                  return var4;
               }
            }
         }

         IChatComponent var5 = var0.getDisplayName();
         if (var5 == null) {
            return -1;
         } else {
            String var6 = var5.getFormattedText().replaceAll("(?i)\\u00a7[klmnor]", "");
            return var6.length() >= 2 && var6.charAt(0) == 167 ? getColorForCode(var6.charAt(1)) : -1;
         }
      }
   }

   private static int getColorForCode(char var0) {
      int var1 = "0123456789abcdef".indexOf(Character.toLowerCase(var0));
      return var1 < 0 ? -1 : COLOR_PALETTE[var1];
   }
}
