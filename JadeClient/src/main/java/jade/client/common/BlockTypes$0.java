// Jade recovery: original class: jade.deps.eLz.rmxXxM$0
package jade.client.common;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public final class BlockTypes$0 {
   private static final Set<Block> blocks = buildNonFullBlockOverrides();

   BlockTypes$0() {
   }

   private static Set<Block> buildNonFullBlockOverrides() {
      Set var0 = Collections.newSetFromMap(new IdentityHashMap());
      Collections.addAll(
         var0,
         Blocks.gravel,
         Blocks.sand,
         Blocks.soul_sand,
         Blocks.tnt,
         Blocks.crafting_table,
         Blocks.furnace,
         Blocks.dispenser,
         Blocks.dropper,
         Blocks.noteblock,
         Blocks.command_block
      );
      return var0;
   }

   public static Set getNonFullBlockOverrides() {
      return blocks;
   }
}
