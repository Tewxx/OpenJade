// Jade recovery: original class: jade.deps.eLz.zyojmoh5
package jade.client.common;

import jade.client.setting.BlockListSetting;

public final class zyojmoh5 {
   public final BlockListSetting blockListSetting;
   public final String Bqn;
   public final boolean LAUhk9;

   public zyojmoh5(BlockListSetting var1, String var2, boolean var3) {
      this.blockListSetting = var1;
      this.Bqn = var2;
      this.LAUhk9 = var3;
   }

   public boolean matches(BlockListSetting var1, String var2, boolean var3) {
      return this.blockListSetting == var1 && this.LAUhk9 == var3 && this.Bqn != null && this.Bqn.equals(var2);
   }

   @Override
   public boolean equals(Object var1) {
      if (!(var1 instanceof zyojmoh5)) {
         return false;
      } else {
         zyojmoh5 var2 = (zyojmoh5)var1;
         return this.blockListSetting == var2.blockListSetting && this.LAUhk9 == var2.LAUhk9 && this.Bqn != null && this.Bqn.equals(var2.Bqn);
      }
   }

   @Override
   public int hashCode() {
      int var1 = System.identityHashCode(this.blockListSetting);
      var1 = 31 * var1 + (this.Bqn != null ? this.Bqn.hashCode() : 0);
      return 31 * var1 + (this.LAUhk9 ? 1 : 0);
   }
}
