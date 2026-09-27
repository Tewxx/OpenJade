// Jade recovery: module: Ghost Hand (player); original class: jade.deps.eLz.L7o3PgJO
package jade.client.module.player;

import jade.client.common.BlockUtils;
import jade.client.common.ClientUtils;
import jade.client.common.PointedObjectOverrider;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.other.AntiBot;
import jade.client.module.player.ghosthand.GhostHandRaytrace;
import jade.client.module.player.ghosthand.GhostHandSettings;
import jade.client.module.player.ghosthand.GhostHandFilters$2;
import jade.client.module.player.ghosthand.GhostHandFilters;
import jade.mixin.impl.accessor.IAccessorEntityRenderer;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Mouse;

@ModuleInfo
public class GhostHand extends Module implements PointedObjectOverrider {
   private final GhostHandSettings ghostHandSettings = new GhostHandSettings(this);

   public GhostHand() {
      super("Ghost Hand", Category.player);
   }

   @Override
   public boolean shouldOverridePointedObject() {
      boolean var1 = mc != null && mc.theWorld != null && mc.thePlayer != null;
      boolean var2 = var1 && mc.getRenderViewEntity() != null;
      boolean var3 = !this.ghostHandSettings.requireLeftMouse.isToggled() || Mouse.isButtonDown(0);
      boolean var4 = !this.ghostHandSettings.requireRightMouse.isToggled() || Mouse.isButtonDown(1);
      return !GhostHandFilters.YWMt(this.isEnabled(), var1, var2, this.ghostHandSettings.notHoldingASword.isToggled(), var1 && ClientUtils.CqWuiK(), var3, var4) ? false : this.isHeldItemAllowed();
   }

   @Override
   public void applyPointedObjectOverride(float var1) {
      if (this.shouldOverridePointedObject()) {
         Entity var2 = mc.getRenderViewEntity();
         double var3 = mc.playerController.getBlockReachDistance();
         if (mc.playerController.extendedReach()) {
            var3 = 6.0;
         }

         MovingObjectPosition var5 = this.raytraceBlocksThroughWalls(var2, var3, var1);
         if (var5 != null) {
            BlockPos var6 = var5.getBlockPos();
            Block var7 = BlockUtils.iepjdt(var6);
            boolean var8 = var7 instanceof BlockBed;
            boolean var9 = !var8 && BlockUtils.isNextToBed(var6);
            boolean var10 = this.ghostHandSettings.bed.isToggled() && var8 || this.ghostHandSettings.nextToBed.isToggled() && var9;
            if (GhostHandFilters.zLd0782(this.ghostHandSettings.everything.isToggled(), this.ghostHandSettings.bed.isToggled(), this.ghostHandSettings.nextToBed.isToggled(), var8, var9)) {
               if (!var10) {
                  Vec3 var11 = var2.getPositionEyes(var1);
                  Vec3 var12 = var5.hitVec;
                  Entity var13 = GhostHandRaytrace.findEntityOnPath(mc.theWorld, var2, var11, var12);
                  if (var13 == null || !this.isEntityCategoryAllowed(var13)) {
                     return;
                  }
               }

               mc.objectMouseOver = var5;
               mc.pointedEntity = null;
               EntityRenderer var14 = mc.entityRenderer;
               if (var14 instanceof IAccessorEntityRenderer) {
                  ((IAccessorEntityRenderer)var14).setPointedEntity(null);
               }
            }
         }
      }
   }

   private MovingObjectPosition raytraceBlocksThroughWalls(Entity var1, double var2, float var4) {
      Vec3 var5 = var1.getPositionEyes(var4);
      Vec3 var6 = var1.getLook(var4);
      Vec3 var7 = var5.addVector(var6.xCoord * var2, var6.yCoord * var2, var6.zCoord * var2);
      return BlockUtils.rayTraceBlocks(var5, var7, this.ghostHandSettings.bed.isToggled(), this.ghostHandSettings.nextToBed.isToggled());
   }

    private boolean isHeldItemAllowed() {
        net.minecraft.item.ItemStack itemStack = jade.client.module.player.GhostHand.mc.thePlayer.getHeldItem();
        switch (jade.client.module.player.ghosthand.GhostHandFilters.wsedf(itemStack)) {
            case SWORD: {
                return this.ghostHandSettings.sword.isToggled();
            }
            case TOOL: {
                return this.ghostHandSettings.tool.isToggled();
            }
            case FISTS: {
                return this.ghostHandSettings.fists.isToggled();
            }
            case BUCKET: {
                return this.ghostHandSettings.bucket.isToggled();
            }
            case FLINT_STEEL: {
                return this.ghostHandSettings.flintAndSteel.isToggled();
            }
            case COBWEB: {
                return this.ghostHandSettings.cobweb.isToggled();
            }
        }
        return this.ghostHandSettings.other.isToggled();
    }

   private boolean isEntityCategoryAllowed(Entity var1) {
      if (!(var1 instanceof EntityPlayer)) {
         GhostHandFilters$2 var4 = GhostHandFilters$2.NON_PLAYER;
         return GhostHandFilters.isCategoryEnabled(var4, this.ghostHandSettings.nonPlayerEntities.isToggled(), this.ghostHandSettings.bots.isToggled(), this.ghostHandSettings.friendlies.isToggled(), this.ghostHandSettings.enemies.isToggled());
      } else {
         EntityPlayer var3 = (EntityPlayer)var1;
         GhostHandFilters$2 var2;
         if (AntiBot.shouldHideEntity(var3)) {
            var2 = GhostHandFilters$2.BOT;
         } else if (!ClientUtils.isFriend(var3) && !ClientUtils.isTeammate(var3)) {
            var2 = GhostHandFilters$2.ENEMY;
         } else {
            var2 = GhostHandFilters$2.FRIENDLY;
         }

         return GhostHandFilters.isCategoryEnabled(var2, this.ghostHandSettings.nonPlayerEntities.isToggled(), this.ghostHandSettings.bots.isToggled(), this.ghostHandSettings.friendlies.isToggled(), this.ghostHandSettings.enemies.isToggled());
      }
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(
         buildSettingAlias(
            "Target",
            "Range",
            new String[]{"non-player entities", "bots", "friendlies", "enemies"},
            new String[]{"Through non-players", "Through bots", "Through friendlies", "Through enemies"}
         ),
         buildSettingAlias("Preferred targets", "Target", new String[]{"everything", "bed", "next to bed"}, new String[]{"Everything", "Bed", "Next to bed"}),
         buildSettingAlias(
            "Allow while using",
            "Preferred targets",
            new String[]{"sword", "tool", "fists", "bucket", "flint and steel", "cobweb", "other"},
            new String[]{"Sword", "Tool", "Fists", "Bucket", "Flint and steel", "Cobweb", "Other"}
         ),
         buildSettingAlias(
            "Conditions",
            "Allow while using",
            new String[]{"require left mouse", "require right mouse", "not holding a sword"},
            new String[]{"Left mouse held", "Right mouse held", "Not holding sword"}
         )
      );
   }
}

