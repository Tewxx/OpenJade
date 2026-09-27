// Jade recovery: original class: jade.deps.eLz.S7sFsG
package jade.client.event;

public class MouseEvent extends Event {
   public final int button;
   public final boolean raW;
   public final int mouseX;
   public final int mouseY;
   public final int vnd;
   public final int deltaY;
   public final int wheelDelta;

   public MouseEvent(int var1, boolean var2, int var3, int var4, int var5, int var6, int var7) {
      this.button = var1;
      this.raW = var2;
      this.mouseX = var3;
      this.mouseY = var4;
      this.vnd = var5;
      this.deltaY = var6;
      this.wheelDelta = var7;
   }
}
