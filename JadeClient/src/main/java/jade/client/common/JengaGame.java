// Jade recovery: original class: jade.deps.eLz.GKN4OSkfju
package jade.client.common;

import jade.client.event.ChatReceivedEvent;
import jade.client.event.DisconnectEvent;
import jade.client.event.LoadWorldEvent;
import jade.client.event.MouseEvent;
import jade.client.event.RenderWorldLastEvent;
import jade.client.event.TickEndEvent;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public final class JengaGame {
   private static final JengaGame jengaGame = new JengaGame();
   private static final int TOWER_LAYER_COUNT = 18;
   private static final double LOU = 0.3;
   private static final double BLOCK_WIDTH = 0.095;
   private static final double GmB4 = 0.055;
   private static final double BLOCK_GAP = 0.002;
   private static final double BASE_PLATFORM_HALF_X = 0.72;
   private static final double BASE_PLATFORM_HALF_Z = 0.52;
   private static final double SUzrvv = 0.07;
   private static final double PILLAR_HEIGHT = 0.65;
   private static final double MAX_REACH_DISTANCE = 4.5;
   private final Minecraft mc = Minecraft.getMinecraft();
   private final JengaPhysics jengaPhysics = new JengaPhysics();
   private final JengaRenderer jengaRenderer = new JengaRenderer();
   private final JengaDuelManager jengaDuelManager = new JengaDuelManager(this);
   private final List<JengaBlock> UFG = new ArrayList<>();
   private final List<JengaBlock> baseBlocks = new ArrayList<>();
   private boolean boardActive;
   private boolean physicsActive;
   private boolean instabilityReported;
   private int unstableTicks;
   private int TDsZ;
   private double YXY;
   private double ojT;
   private Vector3 gtHf = Vector3.vector3;
   private Quaternion tefDy = Quaternion.DGw;
   private JengaBlock heldBlock;
   private JengaBlock KgC;
   private double grabDistance;
   private Vector3 grabOffset = Vector3.vector3;
   private Vector3 pzEz = Vector3.vector3;
   private Quaternion YqksgA = Quaternion.DGw;

   private JengaGame() {
   }

   public static JengaGame getInstance() {
      return jengaGame;
   }

   public boolean isBoardActive() {
      return this.boardActive;
   }

   public int LeOo() {
      return this.TDsZ;
   }

   public boolean requestDuel(String var1) {
      return this.jengaDuelManager.requestDuel(var1);
   }

   public boolean qtqybRw() {
      return this.jengaDuelManager.Di55();
   }

   public boolean hasPendingDuel() {
      return this.jengaDuelManager.hasPendingDuel();
   }

   public boolean vjcD() {
      if (this.mc.thePlayer != null && this.mc.theWorld != null) {
         EntityPlayerSP var1 = this.mc.thePlayer;
         Vec3 var2 = var1.getLook(1.0F);
         Vector3 var3 = new Vector3(var2.xCoord, 0.0, var2.zCoord).normalize();
         if (var3.lengthSquared() < 1.0E-8) {
            var3 = new Vector3(0.0, 0.0, 1.0);
         }

         double var4 = var1.posX + var3.x * 2.2;
         double var6 = var1.posZ + var3.z * 2.2;
         double var8 = var1.posY;
         MovingObjectPosition var10 = this.mc.objectMouseOver;
         if (var10 != null && var10.typeOfHit == MovingObjectType.BLOCK && var10.hitVec != null && var1.getPositionEyes(1.0F).distanceTo(var10.hitVec) <= 6.0) {
            var4 = var10.hitVec.xCoord;
            var6 = var10.hitVec.zCoord;
            var8 = var10.hitVec.yCoord;
         }

         this.YXY = this.goc7(var4, var6, var8);
         double var11 = Math.round(var1.rotationYaw / 90.0F) * 90.0;
         this.tefDy = Quaternion.fromAxisAngle(new Vector3(0.0, 1.0, 0.0), Math.toRadians(-var11));
         this.ojT = this.YXY + 0.65 + 0.07;
         this.gtHf = new Vector3(var4, this.ojT, var6);
         this.buildTower();
         this.boardActive = true;
         return true;
      } else {
         return false;
      }
   }

   public boolean ensureBoardReady() {
      if (!this.boardActive) {
         return this.vjcD();
      } else {
         this.buildTower();
         return true;
      }
   }

   public void endDuel() {
      this.jengaDuelManager.endDuel();
      this.resetBoardState();
   }

   private void resetBoardState() {
      this.boardActive = false;
      this.physicsActive = false;
      this.heldBlock = null;
      this.KgC = null;
      this.UFG.clear();
      this.baseBlocks.clear();
      this.TDsZ = 0;
      this.unstableTicks = 0;
   }

   private void buildTower() {
      this.UFG.clear();
      this.baseBlocks.clear();
      this.heldBlock = null;
      this.KgC = null;
      this.physicsActive = false;
      this.instabilityReported = false;
      this.unstableTicks = 0;
      this.TDsZ = 0;
      Vector3 var1 = new Vector3(0.72, 0.035, 0.52);
      this.baseBlocks.add(new JengaBlock(-1, new Vector3(this.gtHf.x, this.ojT - 0.035, this.gtHf.z), var1, this.tefDy, -9091286, false));
      double var2 = 0.59;
      double var4 = 0.39;
      Vector3 var6 = new Vector3(0.055, 0.325, 0.055);

      for (byte var7 = -1; var7 <= 1; var7 += 2) {
         for (byte var8 = -1; var8 <= 1; var8 += 2) {
            Vector3 var9 = this.tefDy.rotateVector(new Vector3(var2 * var7, 0.0, var4 * var8));
            this.baseBlocks
               .add(
                  new JengaBlock(-1, new Vector3(this.gtHf.x + var9.x, this.YXY + 0.325, this.gtHf.z + var9.z), var6, this.tefDy, -10471137, false)
               );
         }
      }

      Vector3 var20 = new Vector3(0.15, 0.0275, 0.0475);
      Quaternion var21 = Quaternion.fromAxisAngle(new Vector3(0.0, 1.0, 0.0), Math.PI / 2);
      int var22 = 0;

      for (int var10 = 0; var10 < 18; var10++) {
         boolean var11 = (var10 & 1) != 0;
         Quaternion var12 = var11 ? this.tefDy.SITfh(var21).normalize() : this.tefDy;
         double var13 = this.ojT + 0.0275 + var10 * 0.057;

         for (int var15 = -1; var15 <= 1; var15++) {
            Vector3 var16 = var11 ? new Vector3(var15 * 0.097, 0.0, 0.0) : new Vector3(0.0, 0.0, var15 * 0.097);
            Vector3 var17 = this.tefDy.rotateVector(var16);
            int var18 = var22 % 3;
            int var19 = var18 == 0 ? -2579361 : (var18 == 1 ? -1919633 : -3501746);
            this.UFG.add(new JengaBlock(var22++, new Vector3(this.gtHf.x + var17.x, var13, this.gtHf.z + var17.z), var20, var12, var19, true));
         }
      }
   }

   public boolean createDuelBoard() {
      return this.vjcD();
   }

   public void clearBoard() {
      this.resetBoardState();
   }

   public int applyRemoteMove(int var1, int var2) {
      JengaBlock var3 = this.WZopg(var1);
      if (var3 != null && !var3.removed && this.isBlockRemovable(var3)) {
         this.markBlockRemoved(var3);
         double var4 = (var1 * 73L + var2 & 255L) / 256.0 * Math.PI * 2.0;
         Vector3 var6 = new Vector3(Math.cos(var4), 0.0, Math.sin(var4));
         var3.KmyP = new Vector3(this.gtHf.x + var6.x * 0.94, this.ojT + 0.18, this.gtHf.z + var6.z * 0.74);
         var3.previousPosition = var3.KmyP;
         var3.velocity = new Vector3(var6.x * 0.18, 0.05, var6.z * 0.18);
         var3.bN0 = new Vector3(1.2 + var1 % 3 * 0.3, 0.5, -1.0 - var1 % 5 * 0.2);
         this.physicsActive = true;
         this.TDsZ++;
         boolean var7 = this.isTowerUnstable();
         if (var7) {
            this.applyCollapseImpulse();
         }

         return var7 ? 1 : 0;
      } else {
         return -1;
      }
   }

   private JengaBlock WZopg(int var1) {
      if (var1 >= 0 && var1 < this.UFG.size()) {
         JengaBlock var2 = this.UFG.get(var1);
         return var2.cLsk == var1 ? var2 : null;
      } else {
         return null;
      }
   }

   private boolean isBlockRemovable(JengaBlock var1) {
      return JengaTowerValidator.isBlockRemovable(this.UFG, var1);
   }

   private boolean isPulledClear(JengaBlock var1) {
      Vector3 var2 = var1.KmyP.yiK5(this.pzEz);
      double var3 = Math.sqrt(var2.x * var2.x + var2.z * var2.z);
      return var3 >= 0.186 || Math.abs(var2.y) >= 0.22499999999999998;
   }

   private void gnGv6(JengaBlock var1) {
      var1.KmyP = this.pzEz;
      var1.previousPosition = this.pzEz;
      var1.rotation = this.YqksgA;
      var1.previousRotation = this.YqksgA;
      var1.velocity = Vector3.vector3;
      var1.bN0 = Vector3.vector3;
      var1.EDVnA4 = true;
      var1.settledTicks = 0;
   }

   private void markBlockRemoved(JengaBlock var1) {
      var1.removed = true;
      var1.EDVnA4 = false;
      var1.settledTicks = 0;
      this.physicsActive = true;
   }

   private boolean isTowerUnstable() {
      return JengaTowerValidator.isTowerUnstable(this.UFG);
   }

   private void applyCollapseImpulse() {
      for (JengaBlock var2 : this.UFG) {
         if (!var2.removed) {
            var2.EDVnA4 = false;
            var2.settledTicks = 0;
            double var3 = (var2.cLsk & 1) == 0 ? 1.0 : -1.0;
            var2.bN0 = var2.bN0.add(new Vector3(0.0, var3 * 0.08, var3 * 0.16));
         }
      }

      this.physicsActive = true;
   }

   private void announceInstability() {
      if (!this.instabilityReported) {
         this.instabilityReported = true;
         ClientUtils.sendJadeMessage("Jenga", "&cthe tower became structurally unstable after &f" + this.TDsZ + " &cmove" + (this.TDsZ == 1 ? "" : "s") + "!");
      }
   }

   private double goc7(double var1, double var3, double var5) {
      int var7 = (int)Math.floor(var1);
      int var8 = (int)Math.floor(var3);
      int var9 = (int)Math.floor(var5) + 3;

      for (int var10 = var9; var10 >= var9 - 20; var10--) {
         BlockPos var11 = new BlockPos(var7, var10, var8);
         IBlockState var12 = this.mc.theWorld.getBlockState(var11);
         AxisAlignedBB var13 = var12.getBlock().getCollisionBoundingBox(this.mc.theWorld, var11, var12);
         if (var13 != null) {
            return var13.maxY;
         }
      }

      return Math.floor(this.mc.thePlayer.posY) - 1.0;
   }

   @Subscribe
   public void onTickEnd(TickEndEvent var1) {
      if (var1.eventPhase == EventPhase.END) {
         this.jengaDuelManager.checkRequestTimeout();
         if (this.boardActive) {
            if (this.mc.thePlayer != null && this.mc.theWorld != null) {
               for (JengaBlock var3 : this.UFG) {
                  var3.DlCxwUo();
               }

               this.KgC = this.findTargetedBlock(1.0F);
               if (this.heldBlock != null) {
                  this.dP516();
               }

               if (this.physicsActive) {
                  this.jengaPhysics.Agxt(this.UFG, 0.05, this.YXY, this.ojT, this.gtHf, this.tefDy, 0.72, 0.52);
                  this.checkTowerStability();
               }
            } else {
               this.jengaDuelManager.resetDuelState();
               this.resetBoardState();
            }
         }
      }
   }

   private void checkTowerStability() {
      if (this.instabilityReported) {
         this.unstableTicks = 0;
      } else {
         if (JengaTowerValidator.isTowerToppled(this.UFG, this.ojT)) {
            this.unstableTicks++;
            if (this.unstableTicks >= 12) {
               if (this.jengaDuelManager.isDuelActive()) {
                  if (this.jengaDuelManager.reportTowerCollapse()) {
                     this.instabilityReported = true;
                  }
               } else {
                  this.announceInstability();
               }
            }
         } else {
            this.unstableTicks = 0;
         }
      }
   }

   private void dP516() {
      Vec3 var1 = this.mc.thePlayer.getPositionEyes(1.0F);
      Vec3 var2 = this.mc.thePlayer.getLook(1.0F);
      Vector3 var3 = new Vector3(var1.xCoord + var2.xCoord * this.grabDistance, var1.yCoord + var2.yCoord * this.grabDistance, var1.zCoord + var2.zCoord * this.grabDistance)
         .add(this.grabOffset);
      Vector3 var4 = this.heldBlock.KmyP;
      Vector3 var5 = var4.DAZXLG(var3, 0.48);
      this.jengaPhysics.dragHeldBlock(this.UFG, this.heldBlock, var5, 0.05, this.YXY, this.ojT, this.gtHf, this.tefDy, 0.72, 0.52);
      Vector3 var6 = this.heldBlock.KmyP.yiK5(var4).MMGcz7(20.0);
      double var7 = var6.length();
      this.heldBlock.velocity = var7 > 6.0 ? var6.MMGcz7(6.0 / var7) : var6;
      this.heldBlock.bN0 = Vector3.vector3;
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onMouse(MouseEvent var1) {
      if (this.boardActive && var1.raW && this.mc.currentScreen == null && this.mc.thePlayer != null) {
         if (var1.button == 0) {
            if (this.heldBlock != null) {
               this.releaseHeldBlock();
               var1.setCanceled(true);
               return;
            }

            JengaBlock var2 = this.findTargetedBlock(1.0F);
            if (var2 != null) {
               if (this.jengaDuelManager.isDuelActive() && !this.jengaDuelManager.kDwvQ()) {
                  this.jengaDuelManager.iulQn();
                  var1.setCanceled(true);
                  return;
               }

               if (this.jengaDuelManager.isDuelActive() && !this.isBlockRemovable(var2)) {
                  ClientUtils.sendJadeMessage("Jenga", "&cthe highest occupied layer cannot be removed.");
                  var1.setCanceled(true);
                  return;
               }

               this.grabBlock(var2);
               var1.setCanceled(true);
            }
         } else if (var1.button == 1 && this.heldBlock != null) {
            Quaternion var3 = Quaternion.fromAxisAngle(new Vector3(0.0, 1.0, 0.0), Math.PI / 2);
            this.heldBlock.rotation = var3.SITfh(this.heldBlock.rotation).normalize();
            this.heldBlock.previousRotation = this.heldBlock.rotation;
            var1.setCanceled(true);
         }
      }
   }

   private void grabBlock(JengaBlock var1) {
      Vec3 var2 = this.mc.thePlayer.getPositionEyes(1.0F);
      Vec3 var3 = this.mc.thePlayer.getLook(1.0F);
      Vector3 var4 = new Vector3(var2.xCoord, var2.yCoord, var2.zCoord);
      Vector3 var5 = new Vector3(var3.xCoord, var3.yCoord, var3.zCoord).normalize();
      double var6 = this.getBlockHitDistance(var1, var4, var5);
      this.grabDistance = Vector3.clamp(var6, 1.0, 4.5);
      Vector3 var8 = var4.add(var5.MMGcz7(this.grabDistance));
      this.grabOffset = var1.KmyP.yiK5(var8);
      this.heldBlock = var1;
      this.pzEz = var1.KmyP;
      this.YqksgA = var1.rotation;
      this.heldBlock.held = true;
      this.heldBlock.EDVnA4 = false;
      this.heldBlock.velocity = Vector3.vector3;
      this.heldBlock.bN0 = Vector3.vector3;
      this.physicsActive = true;
      this.KgC = var1;
   }

   private void releaseHeldBlock() {
      if (this.heldBlock != null) {
         JengaBlock var1 = this.heldBlock;
         boolean var2 = this.isPulledClear(var1);
         var1.held = false;
         this.heldBlock = null;
         if (this.jengaDuelManager.isDuelActive()) {
            if (!var2) {
               this.gnGv6(var1);
               ClientUtils.sendJadeMessage("Jenga", "&7move cancelled—the block must be pulled fully clear of the tower.");
            } else {
               this.markBlockRemoved(var1);
               this.TDsZ++;
               boolean var3 = this.isTowerUnstable();
               if (var3) {
                  this.applyCollapseImpulse();
               }

               this.jengaDuelManager.submitLocalMove(var1.cLsk, var3);
            }
         } else {
            this.TDsZ++;
            if (var2) {
               this.markBlockRemoved(var1);
               if (this.isTowerUnstable()) {
                  this.applyCollapseImpulse();
                  this.announceInstability();
               }
            }
         }
      }
   }

   private JengaBlock findTargetedBlock(float var1) {
      if (this.boardActive && this.mc.thePlayer != null) {
         Vec3 var2 = this.mc.thePlayer.getPositionEyes(var1);
         Vec3 var3 = this.mc.thePlayer.getLook(var1);
         Vector3 var4 = new Vector3(var2.xCoord, var2.yCoord, var2.zCoord);
         Vector3 var5 = new Vector3(var3.xCoord, var3.yCoord, var3.zCoord).normalize();
         JengaBlock var6 = null;
         double var7 = 4.5;
         Vec3 var9 = var2.addVector(var3.xCoord * 4.5, var3.yCoord * 4.5, var3.zCoord * 4.5);
         MovingObjectPosition var10 = this.mc.theWorld.rayTraceBlocks(var2, var9, false, false, false);
         if (var10 != null && var10.hitVec != null) {
            var7 = Math.min(var7, var2.distanceTo(var10.hitVec));
         }

         for (JengaBlock var12 : this.UFG) {
            if (!var12.removed) {
               double var13 = this.getBlockHitDistance(var12, var4, var5);
               if (var13 >= 0.0 && var13 < var7) {
                  var7 = var13;
                  var6 = var12;
               }
            }
         }

         return var6;
      } else {
         return null;
      }
   }

   private double getBlockHitDistance(JengaBlock var1, Vector3 var2, Vector3 var3) {
      Quaternion var4 = var1.rotation.LZAUFqA();
      Vector3 var5 = var4.rotateVector(var2.yiK5(var1.KmyP));
      Vector3 var6 = var4.rotateVector(var3);
      double var7 = 0.0;
      double var9 = 4.5;
      double[] var11 = new double[]{var5.x, var5.y, var5.z};
      double[] var12 = new double[]{var6.x, var6.y, var6.z};
      double[] var13 = new double[]{var1.halfExtents.x, var1.halfExtents.y, var1.halfExtents.z};

      for (int var14 = 0; var14 < 3; var14++) {
         if (Math.abs(var12[var14]) < 1.0E-9) {
            if (var11[var14] < -var13[var14] || var11[var14] > var13[var14]) {
               return -1.0;
            }
         } else {
            double var15 = (-var13[var14] - var11[var14]) / var12[var14];
            double var17 = (var13[var14] - var11[var14]) / var12[var14];
            if (var15 > var17) {
               double var19 = var15;
               var15 = var17;
               var17 = var19;
            }

            var7 = Math.max(var7, var15);
            var9 = Math.min(var9, var17);
            if (var7 > var9) {
               return -1.0;
            }
         }
      }

      return var7;
   }

   @Subscribe
   public void onRenderWorldLast(RenderWorldLastEvent var1) {
      if (this.boardActive && this.mc.thePlayer != null && this.mc.theWorld != null) {
         this.KgC = this.heldBlock != null ? this.heldBlock : this.findTargetedBlock(var1.YDn0);
         this.jengaRenderer
            .gih9(
               this.baseBlocks,
               this.UFG,
               this.KgC,
               this.heldBlock,
               var1.YDn0,
               this.mc.getRenderManager().viewerPosX,
               this.mc.getRenderManager().viewerPosY,
               this.mc.getRenderManager().viewerPosZ
            );
      }
   }

   @Subscribe
   public void onLoadWorld(LoadWorldEvent var1) {
      this.jengaDuelManager.resetDuelState();
      this.resetBoardState();
   }

   @Subscribe
   public void onDisconnect(DisconnectEvent var1) {
      this.jengaDuelManager.resetDuelState();
      this.resetBoardState();
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onChatReceived(ChatReceivedEvent var1) {
      this.jengaDuelManager.puuozu(var1);
   }
}
