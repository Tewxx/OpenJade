package jade.deps.loader107;

public final class StartupAnnouncement {
   private static final String STARTED_AT_PROPERTY = "jade.announce.startedAt";
   private static final String FIRST_LINE_PROPERTY = "jade.announce.first";
   private static final String SECOND_LINE_PROPERTY = "jade.announce.second";
   private static final long ANNOUNCE_SLIDE_MILLIS = 180L;
   private static final long ANNOUNCE_LINE_DELAY_MILLIS = 100L;
   private static final long ANNOUNCE_FADE_MILLIS = 260L;
   private static final long ANNOUNCE_HOLD_MILLIS = 1800L;
   private static final long ANNOUNCE_SECOND_EXTRA_MILLIS = 200L;
   private static final long ANNOUNCE_SECOND_HOLD_MILLIS = 1800L;
   private static final long ANNOUNCE_SECOND_FADE_MILLIS = 260L;
   private static final long ANNOUNCE_SECOND_SLIDE_MILLIS = 180L;

   private StartupAnnouncement() {
   }

   public static void publishAnnouncement(long var0, String var2, String var3) {
      if (var2 != null && !var2.isEmpty()) {
         System.setProperty("jade.announce.first", var2);
         System.setProperty("jade.announce.second", var3 != null ? var3 : "");
         System.setProperty(
            "jade.announce.startedAt", Long.toString(System.currentTimeMillis())
         );
      }
   }

   public static StartupAnnouncement$0 getActiveAnnouncement() {
      String var0 = System.getProperty("jade.announce.startedAt");
      if (var0 == null) {
         return null;
      } else {
         long var1;
         try {
            var1 = Long.parseLong(var0);
         } catch (NumberFormatException var8) {
            return null;
         }

         long var3 = System.currentTimeMillis() - var1;
         String var5 = System.getProperty("jade.announce.second");
         if (var3 >= 0L && var3 <= computeLifetimeMillis(var5)) {
            String var6 = System.getProperty("jade.announce.first");
            if (var6 != null && !var6.isEmpty()) {
               String var7 = var5 != null && !var5.isEmpty() ? var5 : null;
               return new StartupAnnouncement$0(var1, var6, var7);
            } else {
               return null;
            }
         } else {
            System.clearProperty("jade.announce.startedAt");
            System.clearProperty("jade.announce.first");
            System.clearProperty("jade.announce.second");
            return null;
         }
      }
   }

   private static long computeLifetimeMillis(String var0) {
      long var1 = 2340L;
      if (var0 != null && !var0.isEmpty()) {
         var1 += 2000L;
      }

      return var1 + 260L + 180L;
   }
}
