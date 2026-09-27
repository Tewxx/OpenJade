// Jade recovery: original class: jade.deps.eLz.KroGqX
package jade.client.common;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBanner;
import net.minecraft.block.BlockBeacon;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockClay;
import net.minecraft.block.BlockDaylightDetector;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockEndPortal;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockTripWire;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.block.BlockWeb;

public final class PassableBlocks {
   private static final Class<?>[] tqC = new Class[]{
      BlockSnow.class,
      BlockWeb.class,
      BlockSapling.class,
      BlockDaylightDetector.class,
      BlockBeacon.class,
      BlockBanner.class,
      BlockEndPortalFrame.class,
      BlockEndPortal.class,
      BlockLever.class,
      BlockButton.class,
      BlockSkull.class,
      BlockLiquid.class,
      BlockCactus.class,
      BlockDoublePlant.class,
      BlockLilyPad.class,
      BlockCarpet.class,
      BlockTripWire.class,
      BlockTripWireHook.class,
      BlockTallGrass.class,
      BlockFlower.class,
      BlockFlowerPot.class,
      BlockSign.class,
      BlockLadder.class,
      BlockTorch.class,
      BlockRedstoneTorch.class,
      BlockStairs.class,
      BlockSlab.class,
      BlockFence.class,
      BlockPane.class,
      BlockStainedGlassPane.class,
      BlockGravel.class,
      BlockClay.class,
      BlockSand.class,
      BlockSoulSand.class,
      BlockRailBase.class
   };

   private PassableBlocks() {
   }

   public static boolean isPassable(Block var0) {
      return var0 != null && !BlockUtils.isInteractiveBlock(var0) ? isPassableClass(var0.getClass()) : false;
   }

   public static boolean isPassableClass(Class<?> var0) {
      for (Class var4 : tqC) {
         if (var4.isAssignableFrom(var0)) {
            return false;
         }
      }

      return true;
   }
}
