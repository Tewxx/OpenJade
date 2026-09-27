package jade.deps.loader107;

final class PlayerTrackerClient$1 {
   private final String uuid;
   private final String username;

   PlayerTrackerClient$1(String var1, String var2) {
      this.uuid = var1;
      this.username = var2 == null ? "" : var2;
   }

   static String getUuid(PlayerTrackerClient$1 var0) {
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
               return var0.uuid;
         }
      }
   }

   static String getUsername(PlayerTrackerClient$1 var0) {
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
               return var0.username;
         }
      }
   }
}
