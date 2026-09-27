// Jade recovery: original class: jade.deps.eLz.VFEUvGmi
package jade.client.module.movement.inventorywalk;

public enum InventoryWalkMode {
   NONE("None", false),
   VANILLA("Vanilla", false),
   SLOW_DOWN("Slow Down", true);

   private final String label;
   private final boolean hypixel;

   private InventoryWalkMode(String var3, boolean var4) {
      this.label = var3;
      this.hypixel = var4;
   }

   public String getLabel() {
      return this.label;
   }

   public boolean isHypixel() {
      return this.hypixel;
   }

   public static String[] labels() {
      InventoryWalkMode[] var0 = values();
      String[] var1 = new String[var0.length];

      for (int var2 = 0; var2 < var0.length; var2++) {
         var1[var2] = var0[var2].label;
      }

      return var1;
   }

   public static InventoryWalkMode fromSetting(double var0) {
      int var2 = (int)var0;
      return var2 >= 0 && var2 < values().length ? values()[var2] : NONE;
   }

   static {
      InventoryWalkMode[] var10000 = new InventoryWalkMode[3];
      var10000[0] = NONE;
      var10000[1] = VANILLA;
      var10000[2] = SLOW_DOWN;
   }
}
