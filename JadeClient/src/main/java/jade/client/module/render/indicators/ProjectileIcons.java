// Jade recovery: original class: jade.deps.eLz.uTPUw6vpe
package jade.client.module.render.indicators;

import jade.mixin.impl.accessor.IAccessorEntityArrow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public final class ProjectileIcons {
   private ProjectileIcons() {
   }

   public static ItemStack getProjectileIcon(Entity var0) {
      if (var0 instanceof EntityArrow) {
         return ((IAccessorEntityArrow)var0).getInGround() ? null : new ItemStack(Items.arrow);
      } else if (var0 instanceof EntityFireball) {
         return new ItemStack(Items.fire_charge);
      } else if (var0 instanceof EntityEnderPearl) {
         return new ItemStack(Items.ender_pearl);
      } else if (var0 instanceof EntityEgg) {
         return new ItemStack(Items.egg);
      } else {
         return var0 instanceof EntitySnowball ? new ItemStack(Items.snowball) : null;
      }
   }

   public static boolean shouldRenderProjectile(Entity var0, IndicatorSettings var1) {
      if (var0 instanceof EntityArrow) {
         return !((IAccessorEntityArrow)var0).getInGround() && var1.renderArrows.isToggled();
      } else if (var0 instanceof EntityLargeFireball) {
         return var1.renderFireballs.isToggled();
      } else if (var0 instanceof EntityEnderPearl) {
         return var1.renderEnderPearls.isToggled();
      } else {
         return var0 instanceof EntityEgg ? var1.renderEggs.isToggled() : var0 instanceof EntitySnowball && var1.renderSnowballs.isToggled();
      }
   }
}
