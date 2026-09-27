// Jade recovery: recovered class name: PlayerSprintUpdate; original class: jade.mixin.feature.player.M91ae0504dd563c9087120f5ce82c99dc$1
package jade.mixin.feature.player;

public final class PlayerSprintUpdate$1 {
   public final boolean jumpBefore;
   public final boolean sneakBefore;
   private final boolean forwardBefore;
   private final boolean allowsItemSprint;
   private final boolean itemMovement;

   PlayerSprintUpdate$1(boolean jumpBefore, boolean sneakBefore, boolean forwardBefore, boolean allowsItemSprint, boolean itemMovement) {
      this.jumpBefore = jumpBefore;
      this.sneakBefore = sneakBefore;
      this.forwardBefore = forwardBefore;
      this.allowsItemSprint = allowsItemSprint;
      this.itemMovement = itemMovement;
   }

   static boolean access$100(jade.mixin.feature.player.PlayerSprintUpdate$1 arg0) {
      return arg0.allowsItemSprint;
   }

   static boolean access$200(jade.mixin.feature.player.PlayerSprintUpdate$1 arg0) {
      return arg0.itemMovement;
   }

   static boolean access$300(jade.mixin.feature.player.PlayerSprintUpdate$1 arg0) {
      return arg0.forwardBefore;
   }
}
