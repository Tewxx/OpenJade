// Jade recovery: module: Auto Deflect (player); original class: jade.deps.eLz.Bd4BrB0
package jade.client.module.player;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.RotationUtils;
import jade.client.common.InputHookManager;
import jade.client.common.Subscribe;
import jade.client.event.EntityJoinWorldEvent;
import jade.client.event.MoveInputEvent;
import jade.client.event.PrePlayerInteractEvent;
import jade.client.event.RotationEvent;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.player.autodeflect.AutoDeflectSettings;
import jade.client.module.player.autodeflect.FireballFinder;
import jade.client.module.player.autodeflect.DeflectConditions;
import jade.client.module.player.autodeflect.FireballAim;
import jade.client.module.player.autodeflect.ClickScheduler;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.MovingObjectPosition;

@ModuleInfo(aliases = {"Anti Fireball", "AntiFireball"})
public class AutoDeflect extends Module {
   private final AutoDeflectSettings autoDeflectSettings;
   public EntityFireball entityFireball;
   private final HashSet<Entity> entitys = new HashSet<>();
   private final ClickScheduler TGLfo = new ClickScheduler();
   private final Random random = new Random();

   public AutoDeflect() {
      super("Auto Deflect", Category.player);
      this.autoDeflectSettings = new AutoDeflectSettings(this);
   }

   @Override
   public void onDisable() {
      this.entitys.clear();
      this.entityFireball = null;
      this.TGLfo.resetSchedule();
   }

   @Override
   public void onUpdate() {
      if (ClientUtils.isInWorld() && mc.currentScreen == null) {
         this.entityFireball = this.BrlC();
      } else {
         this.entityFireball = null;
      }
   }

   @Subscribe
   public void onMoveInput(MoveInputEvent var1) {
      if (ClientUtils.isInWorld()) {
         if (DeflectConditions.shouldHoldSneak(this.entityFireball != null, this.autoDeflectSettings.sneak.isToggled(), mc.thePlayer.isRiding(), mc.thePlayer.capabilities.isFlying)) {
            var1.setSneaking(true);
         }
      }
   }

   @Subscribe
   public void onRotation(RotationEvent var1) {
      if (ClientUtils.isInWorld() && mc.currentScreen == null) {
         if (Jade.getModuleManager().getModule(BedNuker.class) == null || !Jade.getModuleManager().getModule(BedNuker.class).shouldOverridePointedObject()) {
            if (Jade.getModuleManager().getModule(BridgeNuker.class) == null || !Jade.getModuleManager().getModule(BridgeNuker.class).shouldOverridePointedObject()) {
               if (DeflectConditions.canAimAtFireball(true, true, this.autoDeflectSettings.onGround.isToggled(), mc.thePlayer.onGround, this.entityFireball != null)) {
                  float var2 = var1.MGzP2 != null ? var1.MGzP2 : RotationUtils.lastSentRotation[0];
                  float var3 = var1.pitch != null ? var1.pitch : RotationUtils.lastSentRotation[1];
                  float[] var4 = FireballAim.computeAimAngles(mc, this.entityFireball, var2, var3);
                  if (var4 != null) {
                     float[] var5 = RotationUtils.smoothAngles(var2, var3, var4[0], var4[1], (int)this.autoDeflectSettings.aimSpeed.getInput());
                     var1.setRotation(var5[0], var5[1], 45);
                  }
               }
            }
         }
      }
   }

   @Subscribe
   public void onPrePlayerInteract(PrePlayerInteractEvent var1) {
      if (ClientUtils.isInWorld() && mc.currentScreen == null) {
         if (DeflectConditions.canAimAtFireball(true, true, this.autoDeflectSettings.onGround.isToggled(), mc.thePlayer.onGround, this.entityFireball != null)) {
            MovingObjectPosition var2 = mc.objectMouseOver;
            if (var2 != null && var2.typeOfHit == MovingObjectType.ENTITY && var2.entityHit == this.entityFireball) {
               long var3 = System.currentTimeMillis();
               int var5 = mc.gameSettings.keyBindAttack.getKeyCode();
               int var6 = this.TGLfo.consumeDueClicks(var3, this::nextClickDelayMillis);

               for (int var7 = 0; var7 < var6; var7++) {
                  KeyBinding.setKeyBindState(var5, true);
                  KeyBinding.onTick(var5);
                  InputHookManager.simulateMouseButton(0, true);
               }
            } else {
               this.TGLfo.resetSchedule();
               InputHookManager.simulateMouseButton(0, false);
            }
         }
      }
   }

   private EntityFireball BrlC() {
      return FireballFinder.findClosestFireball(mc, this.entitys, this.autoDeflectSettings.range.getInput(), (float)this.autoDeflectSettings.fov.getInput(), this.autoDeflectSettings.onGround.isToggled());
   }

   @Subscribe
   public void onEntityJoinWorld(EntityJoinWorldEvent var1) {
      if (ClientUtils.isInWorld()) {
         if (var1.entity == mc.thePlayer) {
            this.entitys.clear();
         } else if (DeflectConditions.isTrackableEntity(false, var1.entity instanceof EntityFireball, mc.thePlayer.getDistanceSqToEntity(var1.entity))) {
            this.entitys.add(var1.entity);
         }
      }
   }

   private long nextClickDelayMillis() {
      double var1 = this.autoDeflectSettings.cps.getInput();
      return ClickScheduler.getDelayMillis(var1, this.random.nextInt(ClickScheduler.getJitterBound(var1)));
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(buildSettingAlias("Conditions", "Aim speed", new String[]{"on ground", "sneak"}, new String[]{"On ground", "Sneak"}));
   }
}
