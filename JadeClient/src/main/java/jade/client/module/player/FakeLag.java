// Jade recovery: module: Fake Lag (player); original class: jade.deps.eLz.Rrf6TV
package jade.client.module.player;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.EventPhase;
import jade.client.common.FakePlayerRenderer$1;
import jade.client.common.FakePlayerRenderer$2;
import jade.client.common.FakePlayerRenderer;
import jade.client.common.PacketDirection;
import jade.client.common.Subscribe;
import jade.client.common.PacketListenerRegistration;
import jade.client.event.PlayerAttackEvent;
import jade.client.event.RenderWorldLastEvent;
import jade.client.event.TickEndEvent;
import jade.client.event.TickStartEvent;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.shared.DisabledOrNestedCondition;
import jade.client.module.shared.ProgressBarSource;
import jade.client.setting.BooleanSetting;
import jade.client.setting.DescriptionSetting;
import jade.client.setting.SliderSetting;

import java.util.List;
import java.util.Set;
import org.jetbrains.annotations.Nullable;

@ModuleInfo
public class FakeLag extends Module implements ProgressBarSource {
   private static final String[] wagB = new String[]{"Inbound", "Outbound", "Both"};
   private final SliderSetting mode;
   private final SliderSetting packetDelay;
   private final BooleanSetting disableLagrange;
   private final BooleanSetting attack;
   private final BooleanSetting showServerPosition;
   private int appliedMode = -1;
   private long appliedPacketDelay = -1L;
   @Nullable
   private PacketListenerRegistration XBz;
   private long lagStartTime;
   private boolean UuG;
   private final FakePlayerRenderer$1 serverPositionTracker = new FakePlayerRenderer$1();

   public FakeLag() {
      super("Fake Lag", Category.player, 0);
      this.registerSetting(
         this.mode = new SliderSetting(
            "Mode", 1, wagB
         )
      );
      this.registerSetting(
         this.packetDelay = new SliderSetting(
            "Packet delay", "ms", 0.0, 0.0, 1500.0, 20.0
         )
      );
      this.registerSetting(
         this.disableLagrange = new BooleanSetting(
            "Disable Lagrange", false
         )
      );
      this.registerSetting(new DescriptionSetting("Disable on"));
      this.registerSetting(
         this.attack = new BooleanSetting(
            "Attack", false
         )
      );
      this.registerSetting(this.showServerPosition = new BooleanSetting("Show server position", false));
   }

   @Override
   public String getInfo() {
      return (int)this.packetDelay.getInput() + "ms";
   }

   @Override
   public boolean isProgressActive() {
      return this.isEnabled() && this.packetDelay.getInput() > 0.0;
   }

   @Override
   public float getProgressFraction() {
      if (this.UuG) {
         return 1.0F;
      } else {
         long var1 = Math.max(1L, (long)this.packetDelay.getInput());
         if (this.lagStartTime <= 0L) {
            return 0.0F;
         } else {
            long var3 = System.currentTimeMillis() - this.lagStartTime;
            return Math.max(0.0F, Math.min(1.0F, (float)var3 / (float)var1));
         }
      }
   }

   @Override
   public String getProgressLabel() {
      return "Fake Lag";
   }

   @Override
   public void guiUpdate() {
      this.disableLagrange.setVisible(this.isOutboundMode(), this);
      if (this.isEnabled()) {
         if (this.packetDelay.getInput() <= 0.0) {
            this.disable();
         } else {
            int var1 = (int)this.mode.getInput();
            long var2 = (long)this.packetDelay.getInput();
            if (var1 != this.appliedMode || var2 != this.appliedPacketDelay) {
               this.appliedMode = var1;
               this.appliedPacketDelay = var2;
               this.rebuildPacketQueue();
            }
         }
      }
   }

   private void rebuildPacketQueue() {
      if (this.XBz != null) {
         this.XBz.getPacketHandler().forceOpen();
      }

      this.XBz = new PacketListenerRegistration(this.getSelectedDirections(), new DisabledOrNestedCondition(this));
      Jade.nbT.JUlwlNu(this.XBz);
   }

   private Set<PacketDirection> getSelectedDirections() {
      switch ((int)this.mode.getInput()) {
         case 0:
            return PacketDirection.ONLY_INBOUND;
         case 1:
         default:
            return PacketDirection.ONLY_OUTBOUND;
         case 2:
            return PacketDirection.BIDIRECTIONAL;
      }
   }

   public boolean isOutboundMode() {
      int var1 = (int)this.mode.getInput();
      return var1 == 1 || var1 == 2;
   }

   public boolean isLagrangeDisableActive() {
      return this.isEnabled() && this.disableLagrange.isToggled() && this.XBz != null && this.packetDelay.getInput() > 0.0 && this.isOutboundMode();
   }

   @Override
   public void onEnable() {
      if (mc.isSingleplayer()) {
         ClientUtils.sendJadeMessage("Jade", "&fFake Lag &7cannot be enabled in singleplayer.");
         this.disable();
      } else if (Jade.getModuleManager().getModule(Blink.class).isEnabled()) {
         ClientUtils.sendColoredMessage("&cCannot use Fake Lag with Blink!");
         this.disable();
      } else {
         this.appliedMode = (int)this.mode.getInput();
         this.appliedPacketDelay = (long)this.packetDelay.getInput();
         this.lagStartTime = System.currentTimeMillis();
         this.UuG = false;
         this.resetServerPositionTracker();
         this.rebuildPacketQueue();
      }
   }

   @Override
   public void onDisable() {
      if (this.XBz != null) {
         this.XBz.getPacketHandler().forceOpen();
         this.XBz = null;
      }

      this.appliedMode = -1;
      this.appliedPacketDelay = -1L;
      this.lagStartTime = 0L;
      this.UuG = false;
      this.resetServerPositionTracker();
   }

   @Subscribe
   public void onTickStart(TickStartEvent var1) {
      if (this.isEnabled()) {
         if (ClientUtils.isInWorld() && mc.theWorld != null) {
            long var2 = (long)this.packetDelay.getInput();
            if (var2 > 0L) {
               Set var4 = this.getSelectedDirections();
               if (var4.contains(PacketDirection.INBOUND)) {
                  Jade.nbT.Cvuz(PacketDirection.INBOUND, var2);
               }

               if (var4.contains(PacketDirection.OUTBOUND)) {
                  Jade.nbT.Cvuz(PacketDirection.OUTBOUND, var2);
               }

               long var5 = System.currentTimeMillis();
               if (this.lagStartTime <= 0L) {
                  this.lagStartTime = var5;
               } else if (var5 - this.lagStartTime >= var2) {
                  this.UuG = true;
               }
            }
         } else {
            this.disable();
         }
      }
   }

   @Subscribe
   public void onPlayerAttack(PlayerAttackEvent var1) {
      if (this.isEnabled() && this.attack.isToggled() && ClientUtils.isInWorld()) {
         if (var1.entityPlayer == mc.thePlayer) {
            this.disable();
         }
      }
   }

   @Subscribe
   public void onRenderWorldLast(RenderWorldLastEvent var1) {
      if (!this.isEnabled() || !this.showServerPosition.isToggled() || !ClientUtils.isInWorld()) {
         this.resetServerPositionTracker();
      } else if (!this.isOutboundMode()) {
         this.resetServerPositionTracker();
      } else {
         long var2 = (long)this.packetDelay.getInput();
         this.serverPositionTracker.recordSnapshot(mc.thePlayer, var1.YDn0);
         FakePlayerRenderer$2 var4 = this.serverPositionTracker.YJfvaQ(var2);
         FakePlayerRenderer.renderFakePlayerWithDefaultAlpha(mc.thePlayer, var4, var1.YDn0);
      }
   }

   @Subscribe
   public void onTickEnd(TickEndEvent var1) {
      if (var1.eventPhase == EventPhase.END) {
         if (mc.theWorld == null && this.isEnabled()) {
            this.disable();
         }
      }
   }

   private void resetServerPositionTracker() {
      this.serverPositionTracker.clearHistory();
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(
         buildSettingAlias("Disable on", "Maximum duration", new String[]{"attack"}, new String[]{"Attack"}),
         buildSettingAlias("Visuals", "Disable on", new String[]{"show initial position", "show server position"}, new String[]{"Initial position", "Server position"})
      );
   }
}
