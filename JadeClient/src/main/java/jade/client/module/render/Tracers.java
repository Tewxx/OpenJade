// Jade recovery: module: Tracers (render); original class: jade.deps.eLz.MW505Wn17i
package jade.client.module.render;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.EventPhase;
import jade.client.common.ExternalRenderBuffer;
import jade.client.common.ExternalRenderer;
import jade.client.common.RenderUtils;
import jade.client.common.ScreenProjector;
import jade.client.common.Subscribe;
import jade.client.common.ExternalRenderableModule;
import jade.client.event.RenderWorldLastEvent;
import jade.client.event.TickEndEvent;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.other.AntiBot;
import jade.client.setting.BooleanSetting;
import jade.client.setting.ColorSetting;
import jade.client.setting.SliderSetting;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

@ModuleInfo
public class Tracers extends Module implements ExternalRenderableModule {
   public SliderSetting lineWidth;
   public SliderSetting maxDistance;
   public ColorSetting color;
   public BooleanSetting teamColor;
   public BooleanSetting offScreenOnly;
   public BooleanSetting hideTeammates;
   public BooleanSetting onlyInGame;
   private boolean rVdeD;
   private final ArrayList<Entity> entitys = new ArrayList<>();
   private int entityCount = 0;

   public Tracers() {
      super("Tracers", Category.render);
      this.registerSetting(this.lineWidth = new SliderSetting("Line Width", 1.0, 1.0, 5.0, 1.0));
      this.registerSetting(
         this.maxDistance = new SliderSetting(
            "Max distance", "m", 128.0, 10.0, 512.0, 10.0
         )
      );
      this.registerSetting(
         this.color = new ColorSetting(
            "Color",
            0,
            255,
            0
         )
      );
      this.registerSetting(this.teamColor = new BooleanSetting("Team color", false));
      this.registerSetting(
         this.offScreenOnly = new BooleanSetting(
            "Off-screen only", false
         )
      );
      this.registerSetting(
         this.hideTeammates = new BooleanSetting(
            "Hide teammates", false
         )
      );
      this.registerSetting(this.onlyInGame = new BooleanSetting("Only in-game", false));
   }

   @Override
   public void onEnable() {
      this.rVdeD = mc.gameSettings.viewBobbing;
      if (this.rVdeD) {
         mc.gameSettings.viewBobbing = false;
      }
   }

   @Override
   public void onDisable() {
      mc.gameSettings.viewBobbing = this.rVdeD;
   }

   @Override
   public void onUpdate() {
      if (mc.gameSettings.viewBobbing) {
         mc.gameSettings.viewBobbing = false;
      }
   }

   @Subscribe
   public void onTickEnd(TickEndEvent var1) {
      if (var1.eventPhase == EventPhase.END) {
         this.abeK();
      }
   }

   @Subscribe
   public void onRenderWorldLast(RenderWorldLastEvent var1) {
      if (ClientUtils.isInWorld() && this.entityCount != 0) {
         int var2 = this.color.getArgb();

         for (int var3 = 0; var3 < this.entityCount; var3++) {
            Entity var4 = this.entitys.get(var3);
            if (var4 != null && (!this.offScreenOnly.isToggled() || !RenderUtils.isEntityInView(var4))) {
               int var5 = this.applyTeamColor(var4, var2);
               if (this.VIuQ()) {
                  this.drawExternalTracer(var4, var5, var1.YDn0);
               } else {
                  RenderUtils.drawTracerToEntity(var4, var5, (float)this.lineWidth.getInput(), var1.YDn0);
               }
            }
         }
      }
   }

   private void drawExternalTracer(Entity var1, int var2, float var3) {
      ExternalRenderBuffer var4 = ExternalRenderer.getActiveRenderBuffer();
      ScreenProjector var5 = ExternalRenderer.getActiveScreenProjector();
      if (var4 != null && var5 != null) {
         double var6 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * var3 - mc.getRenderManager().viewerPosX;
         double var8 = var1.lastTickPosY
            + (var1.posY - var1.lastTickPosY) * var3
            - mc.getRenderManager().viewerPosY
            + var1.getEyeHeight()
            + (var1.isSneaking() ? -0.125 : 0.0);
         double var10 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * var3 - mc.getRenderManager().viewerPosZ;
         if (var5.projectPoint(var6, var8, var10)) {
            var4.drawLine(mc.displayWidth * 0.5, mc.displayHeight * 0.5, var5.projectedPoint[0], var5.projectedPoint[1], var2, (float)this.lineWidth.getInput());
         }
      }
   }

   private int applyTeamColor(Entity var1, int var2) {
      if (this.teamColor.isToggled() && var1 instanceof EntityPlayer) {
         int var3 = ClientUtils.oCoqd(var1);
         if (var3 != -1) {
            int var4 = var2 >> 24 & 0xFF;
            return var4 << 24 | var3 & 16777215;
         }
      }

      return var2;
   }

   private void abeK() {
      this.entityCount = 0;
      if (ClientUtils.isInWorld() && mc.theWorld != null) {
         if (!this.onlyInGame.isToggled() || isInBedwarsGame()) {
            double var1 = this.maxDistance.getInput();
            double var3 = var1 * var1;
            if (Jade.profilingEnabled) {
               for (Entity var8 : mc.theWorld.loadedEntityList) {
                  if (var8 instanceof EntityLivingBase && var8 != mc.thePlayer && mc.thePlayer.getDistanceSqToEntity(var8) <= var3) {
                     this.addEntity(var8);
                  }
               }
            } else {
               for (EntityPlayer var6 : mc.theWorld.playerEntities) {
                  if (var6 != mc.thePlayer
                     && var6.deathTime == 0
                     && !AntiBot.shouldHideEntity(var6)
                     && (!this.hideTeammates.isToggled() || !ClientUtils.isTeammate(var6))
                     && !(mc.thePlayer.getDistanceSqToEntity(var6) > var3)) {
                     this.addEntity(var6);
                  }
               }
            }
         }
      }
   }

   private void addEntity(Entity var1) {
      if (this.entityCount >= this.entitys.size()) {
         this.entitys.add(var1);
      } else {
         this.entitys.set(this.entityCount, var1);
      }

      this.entityCount++;
   }

   private static boolean isInBedwarsGame() {
      List var0 = ClientUtils.PxSw4();
      if (var0.size() < 7) {
         return false;
      } else if (!ClientUtils.zaUnpz((String)var0.get(0)).startsWith("BED WARS")) {
         return false;
      } else {
         String var1 = ClientUtils.zaUnpz((String)var0.get(1));
         String[] var2 = var1.split("  ");
         if (var2.length > 1) {
            String var3 = var2[1];
            if (var3.endsWith("]")) {
               var3 = var3.split(" ")[0];
            }

            if (var3.startsWith("L")) {
               return false;
            }
         }

         if (ClientUtils.zaUnpz((String)var0.get(5)).startsWith("R Red:") && ClientUtils.zaUnpz((String)var0.get(6)).startsWith("B Blue:")) {
            return true;
         } else {
            String var4 = ClientUtils.zaUnpz((String)var0.get(6));
            return !var4.equals("Waiting...") && !var4.startsWith("Starting in") ? false : false;
         }
      }
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(
         buildSettingAlias("Target", "Width", new String[]{"hide teammates", "only in-game"}, new String[]{"Hide teammates", "In game only"}),
         buildSettingAlias(
            "Visuals",
            "Target",
            new String[]{"team color", "off-screen only", "render offscreen"},
            new String[]{"Team color", "Off-screen only", "Render off-screen"}
         )
      );
   }
}
