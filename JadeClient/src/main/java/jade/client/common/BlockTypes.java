// Jade recovery: original class: jade.deps.eLz.rmxXxM
package jade.client.common;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockBasePressurePlate;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockBrewingStand;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDropper;
import net.minecraft.block.BlockEnchantmentTable;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockNote;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

public final class BlockTypes {
   private static final Class<?>[] WWjl = new Class[]{
      BlockFenceGate.class,
      BlockLadder.class,
      BlockFlowerPot.class,
      BlockBasePressurePlate.class,
      BlockFence.class,
      BlockAnvil.class,
      BlockEnchantmentTable.class,
      BlockChest.class
   };
   private static final Class<?>[] INTERACTIVE_BLOCK_TYPES = new Class[]{
      BlockTrapDoor.class,
      BlockDoor.class,
      BlockContainer.class,
      BlockJukebox.class,
      BlockFenceGate.class,
      BlockChest.class,
      BlockEnderChest.class,
      BlockEnchantmentTable.class,
      BlockBrewingStand.class,
      BlockBed.class,
      BlockDropper.class,
      BlockDispenser.class,
      BlockHopper.class,
      BlockAnvil.class,
      BlockNote.class,
      BlockWorkbench.class
   };

   private BlockTypes() {
   }

   public static boolean EuLv5(Block var0) {
      return aIyuF(var0) || hczi(var0, WWjl);
   }

   public static boolean ONLmva(Block var0) {
      if (var0 == Blocks.glass) {
         return true;
      } else {
         return !var0.isFullBlock() ? false : !BlockTypes$0.getNonFullBlockOverrides().contains(var0);
      }
   }

   public static boolean aIyuF(Block var0) {
      Material var1 = var0.getMaterial();
      return var1 == Material.lava || var1 == Material.water;
   }

   public static boolean DSLVc(Block var0) {
      return hczi(var0, INTERACTIVE_BLOCK_TYPES);
   }

   private static boolean hczi(Block var0, Class<?>[] var1) {
      for (Class var5 : var1) {
         if (var5.isInstance(var0)) {
            return true;
         }
      }

      return false;
   }
}
