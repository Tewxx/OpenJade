// Jade recovery: original class: jade.deps.eLz.AebiCPW
package jade.client.common;

import jade.client.module.Category;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class CategoryIcons {
   private final Map<Category, ItemStack[]> iconStacks = new EnumMap<>(Category.class);

   public CategoryIcons() {
      this.Bbqo7(Category.combat, Items.diamond_sword);
      this.Bbqo7(Category.movement, Items.diamond_boots);
      this.Bbqo7(Category.player, Items.golden_apple);
      this.Bbqo7(Category.render, Items.ender_eye);
      this.Bbqo7(Category.minigames, Items.gold_ingot);
      this.Bbqo7(Category.other, Items.clock);
      this.Bbqo7(Category.client, Items.compass);
      this.Bbqo7(Category.profiles, Items.book);
   }

   private void Bbqo7(Category var1, Item var2) {
      ItemStack var3 = new ItemStack(var2);
      if (var1 == Category.player) {
         var3.setItemDamage(1);
      } else {
         var3.addEnchantment(Enchantment.unbreaking, 2);
      }

      this.iconStacks.put(var1, new ItemStack[]{new ItemStack(var2), var3});
   }

   public void renderIcon(Category var1, int var2, int var3, boolean var4) {
      RenderItem var5 = Minecraft.getMinecraft().getRenderItem();
      double var6 = 0.55;
      GlStateManager.pushMatrix();
      GlStateManager.scale(var6, var6, var6);
      ItemStack[] var8 = this.iconStacks.get(var1);
      if (var8 != null) {
         RenderHelper.enableGUIStandardItemLighting();
         GlStateManager.disableBlend();
         GlStateManager.translate((float)(var2 / var6), (float)(var3 / var6), 0.0F);
         var5.renderItemAndEffectIntoGUI(var8[var4 ? 1 : 0], 0, 0);
         GlStateManager.enableBlend();
         RenderHelper.disableStandardItemLighting();
      }

      GlStateManager.scale(1.0F, 1.0F, 1.0F);
      GlStateManager.popMatrix();
   }
}
