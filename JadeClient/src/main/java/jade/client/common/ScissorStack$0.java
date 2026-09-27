// Jade recovery: original class: jade.deps.eLz.UW89UQ5E$0
package jade.client.common;

public final class ScissorStack$0 {
   public final int x;
   public final int Eatd7;
   public final int pfi;
   public final int KYt;

   public ScissorStack$0(int var1, int var2, int var3, int var4) {
      this.x = var1;
      this.Eatd7 = var2;
      this.pfi = Math.max(0, var3);
      this.KYt = Math.max(0, var4);
   }

   public ScissorStack$0 intersect(ScissorStack$0 var1) {
      int var2 = Math.max(this.x, var1.x);
      int var3 = Math.max(this.Eatd7, var1.Eatd7);
      int var4 = Math.min(this.x + this.pfi, var1.x + var1.pfi);
      int var5 = Math.min(this.Eatd7 + this.KYt, var1.Eatd7 + var1.KYt);
      return new ScissorStack$0(var2, var3, var4 - var2, var5 - var3);
   }
}
