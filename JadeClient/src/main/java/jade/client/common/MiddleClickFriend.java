// Jade recovery: original class: jade.deps.eLz.oiLUHON
package jade.client.common;

import jade.client.Jade;
import jade.client.event.MouseEvent;
import jade.client.event.TickEndEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;

public class MiddleClickFriend {
   private static final Minecraft mc = Minecraft.getMinecraft();
   private static final RecentEventCounter leftCpsTracker = new RecentEventCounter();
   private static final RecentEventCounter BEmR = new RecentEventCounter();
   private static final MouseScrollState scrollState = new MouseScrollState();
   private static final EdgeTrigger middleClickEdgeDetector = new EdgeTrigger();
   public static long Qbel = 0L;
   public static long moxQ = 0L;

   public static void Mz55() {
      scrollState.setScrollDelta(Mouse.getDWheel());
   }

   public static boolean isScrollKeyPressed(int var0) {
      return scrollState.matchesScrollDirection(var0, Mouse::getDWheel);
   }

   public static void IIroW8() {
      scrollState.QhLg();
   }

   @Subscribe
   public void onMouse(MouseEvent var1) {
      if (var1.raW) {
         if (var1.button == 0) {
            gadF();
            if (Jade.profilingEnabled && mc.objectMouseOver != null) {
               Entity var2 = mc.objectMouseOver.entityHit;
               if (var2 == null || !(var2 instanceof EntityLivingBase)) {
                  return;
               }

               ClientUtils.QBUrnV((EntityLivingBase)var2);
            }
         } else if (var1.button == 1) {
            ZnihnJ();
         }
      }
   }

   @Subscribe
   public void onTickEnd(TickEndEvent var1) {
      if (var1.eventPhase == EventPhase.START) {
         if (middleClickEdgeDetector.TLhG(Mouse.isButtonDown(2))) {
            if (Jade.relationManager != null && Jade.relationManager.isMiddleClickFriends() && ClientUtils.isInWorld() && mc.currentScreen == null) {
               MovingObjectPosition var2 = mc.objectMouseOver;
               if (var2 != null && var2.entityHit instanceof EntityPlayer) {
                  EntityPlayer var3 = (EntityPlayer)var2.entityHit;
                  String var4 = var3.getName();
                  if (ClientUtils.addFriend(var4)) {
                     ClientUtils.sendJadeMessage("Jade", "&aadded &7friend: &f" + var4);
                  } else if (ClientUtils.removeFriend(var4)) {
                     ClientUtils.sendJadeMessage("Jade", "&cremoved &7friend: &f" + var4);
                  }
               }
            }
         }
      }
   }

   public static void gadF() {
      leftCpsTracker.record(Qbel = System.currentTimeMillis());
   }

   public static void ZnihnJ() {
      BEmR.record(moxQ = System.currentTimeMillis());
   }

   public static int getLeftCps() {
      return leftCpsTracker.pruneAndCount(System::currentTimeMillis);
   }

   public static int getRightCps() {
      return BEmR.pruneAndCount(System::currentTimeMillis);
   }
}
