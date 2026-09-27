// Jade recovery: original class: jade.deps.eLz.rRiiO2d2
package jade.client.common;

import jade.mixin.impl.accessor.IAccessorGuiIngame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public final class GameStateUtils {
   private GameStateUtils() {
   }

   public static boolean isInWorld(Minecraft var0) {
      return var0.thePlayer != null && var0.theWorld != null;
   }

   public static boolean isInGameWithFocus(Minecraft var0) {
      return var0.currentScreen == null && var0.inGameHasFocus;
   }

   public static boolean isOnHypixel(Minecraft var0) {
      return !var0.isSingleplayer() && var0.getCurrentServerData() != null && var0.getCurrentServerData().serverIP.contains("hypixel.net");
   }

   public static boolean isPlayerInventoryOpen(Minecraft var0) {
      return !isInWorld(var0) ? false : var0.currentScreen instanceof GuiInventory && var0.thePlayer.inventoryContainer instanceof ContainerPlayer;
   }

   public static boolean isMovementKeyDown(Minecraft var0) {
      int[] var1 = new int[]{
         var0.gameSettings.keyBindForward.getKeyCode(),
         var0.gameSettings.keyBindBack.getKeyCode(),
         var0.gameSettings.keyBindLeft.getKeyCode(),
         var0.gameSettings.keyBindRight.getKeyCode()
      };

      for (int var5 : var1) {
         if (Keyboard.isKeyDown(var5)) {
            return true;
         }
      }

      return false;
   }

   public static boolean yrvB(Minecraft var0) {
      return Keyboard.isKeyDown(var0.gameSettings.keyBindJump.getKeyCode());
   }

   public static boolean isLookingAtBlock(Minecraft var0) {
      MovingObjectPosition var1 = var0.objectMouseOver;
      return var1 != null && var1.typeOfHit == MovingObjectType.BLOCK && var1.getBlockPos() != null;
   }

   public static boolean isPrimaryClickHeld(boolean var0) {
      return var0 ? Mouse.isButtonDown(0) : MiddleClickFriend.getLeftCps() > 1 && System.currentTimeMillis() - MiddleClickFriend.Qbel < 300L;
   }

   public static boolean isDeathStateOrReturnItem(Minecraft var0) {
      boolean var1 = var0.thePlayer.inventory.getStackInSlot(8) != null && var0.thePlayer.inventory.getStackInSlot(8).getDisplayName().contains("Return");
      String var2 = ((IAccessorGuiIngame)var0.ingameGUI).getDisplayedTitle();
      return var1 || ScoreboardUtils.IwugSbg(var2).contains("YOU DIED");
   }
}
