package jade.deps.loader107;

public enum HypixelPlayerStats$0 {
   SOLO("1s", new String[]{"eight_one"}),
   DOUBLES("2s", new String[]{"eight_two"}),
   THREES("3s", new String[]{"four_three"}),
   FOURS("4s", new String[]{"four_four"}),
   FOUR_V_FOUR("4v4", new String[]{"two_four"}),
   OVERALL("overall", new String[]{"eight_one", "eight_two", "four_three", "four_four", "two_four"}),
   CORE("core", new String[]{"eight_one", "eight_two", "four_three", "four_four"});

   private final String label;
   private final String[] prefixes;

   HypixelPlayerStats$0(String var3, String[] var4) {
      this.label = var3;
      this.prefixes = var4;
   }

   public String getLabel() {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return this.label;
         }
      }
   }

   static {
   }

   static java.lang.String[] access$000(jade.deps.loader107.HypixelPlayerStats$0 arg0) {
      return arg0.prefixes;
   }
}
