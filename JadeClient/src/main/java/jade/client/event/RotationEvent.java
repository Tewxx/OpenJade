// Jade recovery: original class: jade.deps.eLz.BfFBsn5OQv
package jade.client.event;

public class RotationEvent extends Event {
   public static final int PRIORITY_NONE = 0;
   public static final int TBb = 10;
   public static final int ihT = 20;
   public static final int PRIORITY_MEDIUM = 30;
   public static final int Xu91 = 40;
   public static final int PRIORITY_HIGH = 45;
   public static final int jBj = 50;
   public static final int PRIORITY_CRITICAL = 55;
   public static final int Lyk = 60;
   public Float MGzP2;
   public Float pitch;
   public boolean suppressRotations;
   private int appliedPriority = Integer.MIN_VALUE;

   public RotationEvent(Float var1, Float var2) {
      this.MGzP2 = var1;
      this.pitch = var2;
   }

   public void setYaw(Float var1) {
      this.PrQmu(var1, 0);
   }

   public void setPitch(Float var1) {
      this.FWJBv(var1, 0);
   }

   public boolean PrQmu(Float var1, int var2) {
      if (!this.Rzezn(var2)) {
         return false;
      } else {
         this.MGzP2 = var1;
         this.appliedPriority = var2;
         return true;
      }
   }

   public boolean FWJBv(Float var1, int var2) {
      if (!this.Rzezn(var2)) {
         return false;
      } else {
         this.pitch = var1;
         this.appliedPriority = var2;
         return true;
      }
   }

   public boolean setRotation(Float var1, Float var2, int var3) {
      if (!this.Rzezn(var3)) {
         return false;
      } else {
         this.MGzP2 = var1;
         this.pitch = var2;
         this.appliedPriority = var3;
         return true;
      }
   }

   public int Jxhy() {
      return this.appliedPriority;
   }

   private boolean Rzezn(int var1) {
      return var1 >= this.appliedPriority;
   }
}
