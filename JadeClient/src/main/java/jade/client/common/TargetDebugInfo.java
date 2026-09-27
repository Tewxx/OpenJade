// Jade recovery: original class: jade.deps.eLz.LNoFwClq3
package jade.client.common;

import jade.client.module.other.AntiBot;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.IChatComponent;

public final class TargetDebugInfo {
   private TargetDebugInfo() {
   }

   public static void dumpTargetDebugInfo(
      Minecraft var0,
      EntityLivingBase var1,
      boolean var2,
      Consumer<String> var3,
      Function<Double, Double> var4,
      Function<EntityLivingBase, Double> var5,
      Function<EntityLivingBase, Double> var6,
      Function<EntityPlayer, Boolean> var7,
      Function<EntityPlayer, Boolean> var8
   ) {
      if (var1 != null) {
         var3.accept("&7&m-------------------------");
         var3.accept("&eattacking: &r" + var1.getName());
         var3.accept("&7type: &b" + var1.getClass().getSimpleName());
         var3.accept("&7bot: &r" + (var2 ? AntiBot.shouldHideEntity(var1) : "&cantibot disabled"));
         boolean var9 = var1 instanceof EntityPlayer;
         var3.accept("&7player: &r" + var9);
         var3.accept("&7dist eye: &d" + var4.apply(var5.apply(var1)));
         var3.accept("&7min dist: &d" + var4.apply(Math.sqrt((Double)var6.apply(var1))));
         IChatComponent var10 = var1.getDisplayName();
         if (var9) {
            dumpPlayerDebugInfo(var0, (EntityPlayer)var1, var3, var7, var8);
         }

         var3.accept("&7display unformatted: &r" + (var10 == null ? "&cnull" : var10.getUnformattedText()));
         var3.accept("&7insertion: &r" + (var10 == null ? "&cnull" : var10.getChatStyle().getInsertion()));
         var3.accept("&7health: &r" + var1.getHealth());
         var3.accept("&7ht: &d" + var1.hurtTime + " &7mht: &d" + var1.maxHurtTime);
         var3.accept("&7ticks existed: &r" + var1.ticksExisted);
         var3.accept("&7invisible: &r" + var1.isInvisible());
         var3.accept("&7dead: &r" + var1.isDead);
      }
   }

   private static void dumpPlayerDebugInfo(
      Minecraft var0, EntityPlayer var1, Consumer<String> var2, Function<EntityPlayer, Boolean> var3, Function<EntityPlayer, Boolean> var4
   ) {
      UUID var5 = var1.getUniqueID();
      var2.accept("&7uuid: &d" + var5 + " &b" + var5.variant() + " " + var5.version());
      NetworkPlayerInfo var6 = var0.getNetHandler().getPlayerInfo(var5);
      var2.accept("&7ping: &d" + (var6 == null ? "&cnot found" : var6.getResponseTime()));
      var2.accept("&7teammate: &r" + var3.apply(var1));
      var2.accept("&7tablist: &r" + var4.apply(var1));
      if (var1.getTeam() instanceof ScorePlayerTeam) {
         ScorePlayerTeam var7 = (ScorePlayerTeam)var1.getTeam();
         var2.accept("&7team name: &r" + var7.getTeamName());
         var2.accept("&7team prefix: &r" + var7.getColorPrefix());
         var2.accept("&7team suffix: &r" + var7.getColorSuffix());
      }
   }
}
