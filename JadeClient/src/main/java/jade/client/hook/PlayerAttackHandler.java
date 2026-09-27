// Jade recovery: original class: jade.deps.eLz.YqbZpQlmg
package jade.client.hook;

import jade.client.Jade;
import jade.client.common.EventBus;
import jade.client.event.PlayerAttackEvent;
import jade.client.module.combat.KeepSprint;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;

public final class PlayerAttackHandler {
   private PlayerAttackHandler() {
   }

   public static void attackTarget(EntityPlayer var0, Entity var1) {
      PlayerAttackEvent var2 = new PlayerAttackEvent(var0, var1);
      EventBus.post(var2);
      if (!var2.isCanceled() && var1.canAttackWithItem() && !var1.hitByEntity(var0)) {
         PlayerAttackHandler$0 var3 = computeAttackAttributes(var0, var1);
         if (!(var3.damage <= 0.0F)) {
            boolean var4 = applyFireAspect(var1, var3.fireAspectLevel);
            PlayerAttackHandler$1 var5 = new PlayerAttackHandler$1(var1);
            boolean var6 = var1.attackEntityFrom(DamageSource.causePlayerDamage(var0), var3.damage);
            if (!var6) {
               if (var4) {
                  var1.extinguish();
               }
            } else {
               jxBb(var0, var1, var3.knockbackLevel);
               zYdjn0(var1, var5);
               applyPostHitEffects(var0, var1, var3);
               LATnh(var0, var1);
               NUZaI(var0, var1, var3.damage, var3.fireAspectLevel);
               var0.addExhaustion(0.3F);
            }
         }
      }
   }

   private static PlayerAttackHandler$0 computeAttackAttributes(EntityPlayer var0, Entity var1) {
      float var2 = (float)var0.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
      EnumCreatureAttribute var3 = var1 instanceof EntityLivingBase ? ((EntityLivingBase)var1).getCreatureAttribute() : EnumCreatureAttribute.UNDEFINED;
      float var4 = EnchantmentHelper.getModifierForCreature(var0.getHeldItem(), var3);
      int var5 = EnchantmentHelper.getKnockbackModifier(var0) + (var0.isSprinting() ? 1 : 0);
      boolean var6 = AttackDamageMath.isCriticalHit(
         var0.fallDistance,
         var0.onGround,
         var0.isOnLadder(),
         var0.isInWater(),
         var0.isPotionActive(Potion.blindness),
         var0.ridingEntity != null,
         var1 instanceof EntityLivingBase
      );
      float var7 = !(var2 > 0.0F) && !(var4 > 0.0F) ? 0.0F : AttackDamageMath.applyCriticalDamageBonus(var2, var4, var6);
      return new PlayerAttackHandler$0(var7, var4, var5, EnchantmentHelper.getFireAspectModifier(var0), var6);
   }

   private static boolean applyFireAspect(Entity var0, int var1) {
      boolean var2 = AttackDamageMath.shouldIgniteTarget(var0 instanceof EntityLivingBase, var1, var0.isBurning());
      if (var2) {
         var0.setFire(1);
      }

      return var2;
   }

   private static void jxBb(EntityPlayer var0, Entity var1, int var2) {
      if (var2 > 0) {
         AttackDamageMath$1 var3 = AttackDamageMath.computeKnockbackVector(var0.rotationYaw, var2);
         var1.addVelocity(var3.x, 0.1, var3.xkyL);
         KeepSprint var4 = Jade.getModuleManager().getModule(KeepSprint.class);
         if (var4 != null && var4.isEnabled() && var0 == Minecraft.getMinecraft().thePlayer) {
            var4.applySprintSlowdown(var0);
         } else {
            var0.motionX *= 0.6;
            var0.motionZ *= 0.6;
            var0.setSprinting(false);
         }
      }
   }

   private static void zYdjn0(Entity var0, PlayerAttackHandler$1 var1) {
      if (var0 instanceof EntityPlayerMP && var0.velocityChanged) {
         ((EntityPlayerMP)var0).playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(var0));
         var0.velocityChanged = false;
         var1.restoreMotion(var0);
      }
   }

   private static void applyPostHitEffects(EntityPlayer var0, Entity var1, PlayerAttackHandler$0 var2) {
      if (var2.critical) {
         var0.onCriticalHit(var1);
      }

      if (var2.enchantmentBonus > 0.0F) {
         var0.onEnchantmentCritical(var1);
      }

      if (AttackDamageMath.isOverkillDamage(var2.damage)) {
         var0.triggerAchievement(AchievementList.overkill);
      }

      var0.setLastAttacker(var1);
   }

   private static void LATnh(EntityPlayer var0, Entity var1) {
      if (var1 instanceof EntityLivingBase) {
         EnchantmentHelper.applyThornEnchantments((EntityLivingBase)var1, var0);
      }

      EnchantmentHelper.applyArthropodEnchantments(var0, var1);
      Entity var2 = resolveAttackEntity(var1);
      ItemStack var3 = var0.getCurrentEquippedItem();
      if (var3 != null && var2 instanceof EntityLivingBase) {
         var3.hitEntity((EntityLivingBase)var2, var0);
         if (var3.stackSize <= 0) {
            var0.destroyCurrentEquippedItem();
         }
      }
   }

   private static Entity resolveAttackEntity(Entity var0) {
      if (!(var0 instanceof EntityDragonPart)) {
         return var0;
      } else {
         IEntityMultiPart var1 = ((EntityDragonPart)var0).entityDragonObj;
         return (Entity)(var1 instanceof EntityLivingBase ? (EntityLivingBase)var1 : var0);
      }
   }

   private static void NUZaI(EntityPlayer var0, Entity var1, float var2, int var3) {
      if (var1 instanceof EntityLivingBase) {
         var0.addStat(StatList.damageDealtStat, AttackDamageMath.toDamageStatAmount(var2));
         if (var3 > 0) {
            var1.setFire(AttackDamageMath.Azpa(var3));
         }
      }
   }
}
