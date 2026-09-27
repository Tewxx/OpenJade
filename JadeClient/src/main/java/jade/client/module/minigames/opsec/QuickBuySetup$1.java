// Jade recovery: original class: jade.deps.eLz.fGXd54Wf$1
package jade.client.module.minigames.opsec;

public enum QuickBuySetup$1 {
   IDLE("idle"),
   WAIT_SETTINGS("waiting for Settings"),
   WAIT_BEDWARS_SETTINGS("opening Bed Wars Settings"),
   WAIT_INITIAL_GRID("opening Edit Quick Buy"),
   WAIT_PREFLIGHT_CATALOGUE("opening the item catalogue"),
   CRAWL_CATALOGUE("indexing the item catalogue"),
   WAIT_PREFLIGHT_GRID("returning to the editor"),
   PLACE_AT_GRID("selecting a destination"),
   WAIT_PLACE_CATALOGUE("opening a destination catalogue"),
   NAVIGATE_CATALOGUE("navigating the item catalogue"),
   WAIT_PLACED_GRID("verifying a replacement"),
   WAIT_FINAL_CATALOGUE("reopening the catalogue for final verification"),
   WAIT_FINAL_GRID("verifying the complete Quick Buy layout");

   private final String label;

   QuickBuySetup$1(String var3) {
      this.label = var3;
   }

   static {
      QuickBuySetup$1[] var10000 = new QuickBuySetup$1[13];
      var10000[0] = IDLE;
      var10000[1] = WAIT_SETTINGS;
      var10000[2] = WAIT_BEDWARS_SETTINGS;
      var10000[3] = WAIT_INITIAL_GRID;
      var10000[4] = WAIT_PREFLIGHT_CATALOGUE;
      var10000[5] = CRAWL_CATALOGUE;
      var10000[6] = WAIT_PREFLIGHT_GRID;
      var10000[7] = PLACE_AT_GRID;
      var10000[8] = WAIT_PLACE_CATALOGUE;
      var10000[9] = NAVIGATE_CATALOGUE;
      var10000[10] = WAIT_PLACED_GRID;
      var10000[11] = WAIT_FINAL_CATALOGUE;
      var10000[12] = WAIT_FINAL_GRID;
   }

   static java.lang.String access$000(jade.client.module.minigames.opsec.QuickBuySetup$1 arg0) {
      return arg0.label;
   }
}
