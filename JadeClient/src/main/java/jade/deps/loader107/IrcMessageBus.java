package jade.deps.loader107;

public final class IrcMessageBus {
   private static final String OUTGOING_MESSAGE_PROPERTY = "jade.irc.outgoing";
   private static final String INCOMING_MESSAGE_PROPERTY = "jade.irc.incoming";
   private static final String NOTICE_PROPERTY = "jade.irc.notices";
   private static final String ONLINE_REQUEST_PROPERTY = "jade.irc.onlineRequests";
   private static final char ENTRY_SEPARATOR = '\u0001';
   private static final char FIELD_SEPARATOR = '\u0002';
   private static final int MAX_PROPERTY_LENGTH = 16000;
   private static final int MAX_TEXT_LENGTH = 255;

   private IrcMessageBus() {
   }

   public static void publishOutgoingMessage(String var0) {
      appendToProperty(
         "jade.irc.outgoing", sanitizeMessage(var0)
      );
   }

   public static String pollOutgoingMessage() {
      return pollProperty("jade.irc.outgoing");
   }

   public static void incrementOnlineRequests() {
      synchronized (System.getProperties()) {
         int var1 = 0;
         String var2 = System.getProperty(
            "jade.irc.onlineRequests",
            "0"
         );

         try {
            var1 = Integer.parseInt(var2);
         } catch (NumberFormatException var5) {
         }

         System.setProperty(
            "jade.irc.onlineRequests", String.valueOf(Math.min(32, var1 + 1))
         );
      }
   }

   public static boolean consumeOnlineRequest() {
      synchronized (System.getProperties()) {
         int var1 = 0;
         String var2 = System.getProperty(
            "jade.irc.onlineRequests",
            "0"
         );

         try {
            var1 = Integer.parseInt(var2);
         } catch (NumberFormatException var5) {
         }

         if (var1 <= 0) {
            System.clearProperty("jade.irc.onlineRequests");
            return false;
         } else {
            if (var1 == 1) {
               System.clearProperty("jade.irc.onlineRequests");
            } else {
               System.setProperty(
                  "jade.irc.onlineRequests", String.valueOf(var1 - 1)
               );
            }

            return true;
         }
      }
   }

   public static void publishIncomingMessage(String var0, long var1, String var3) {
      String var4 = sanitizeSenderName(var0) + '\u0002' + var1 + '\u0002' + sanitizeMessage(var3);
      appendToProperty("jade.irc.incoming", var4);
   }

   public static IrcMessageBus$1 pollIncomingMessage() {
      String var0 = pollProperty("jade.irc.incoming");
      if (var0 != null && !var0.isEmpty()) {
         String[] var1 = var0.split(String.valueOf('\u0002'), 3);
         if (var1.length != 3) {
            return null;
         } else {
            long var2 = -1L;

            try {
               var2 = Long.parseLong(var1[1]);
            } catch (NumberFormatException var5) {
            }

            return new IrcMessageBus$1(var1[0], var2, var1[2]);
         }
      } else {
         return null;
      }
   }

   public static void publishNotice(String var0) {
      appendToProperty(
         "jade.irc.notices", sanitizeNoticeText(var0)
      );
   }

   public static String pollNotice() {
      return pollProperty("jade.irc.notices");
   }

   public static void clearAllProperties() {
      synchronized (System.getProperties()) {
         System.clearProperty("jade.irc.outgoing");
         System.clearProperty("jade.irc.incoming");
         System.clearProperty("jade.irc.notices");
         System.clearProperty("jade.irc.onlineRequests");
      }
   }

   public static String sanitizeMessage(String var0) {
      if (var0 == null) {
         return "";
      } else {
         String var1 = var0.replaceAll("(?i)§[0-9A-FK-OR]", "")
            .replaceAll("(?i)&[0-9A-FK-OR]", "")
            .replace('\r', ' ')
            .replace('\n', ' ')
            .replace('\t', ' ')
            .trim();
         StringBuilder var2 = new StringBuilder(Math.min(var1.length(), 255));

         for (int var3 = 0; var3 < var1.length() && var2.length() < 255; var3++) {
            char var4 = var1.charAt(var3);
            if (var4 != 1 && var4 != 2 && !Character.isISOControl(var4)) {
               var2.append(var4);
            } else {
               var2.append(' ');
            }
         }

         return var2.toString().trim();
      }
   }

   public static String sanitizeSenderName(String var0) {
      String var1 = sanitizeMessage(var0);
      if (var1.length() > 64) {
         var1 = var1.substring(0, 64).trim();
      }

      return var1.isEmpty() ? "Unknown" : var1;
   }

   private static String sanitizeNoticeText(String var0) {
      if (var0 == null) {
         return "";
      } else {
         String var1 = var0.replace('\r', ' ').replace('\n', ' ').replace('\t', ' ').trim();
         StringBuilder var2 = new StringBuilder(Math.min(var1.length(), 255));

         for (int var3 = 0; var3 < var1.length() && var2.length() < 255; var3++) {
            char var4 = var1.charAt(var3);
            if (var4 != 1 && var4 != 2 && !Character.isISOControl(var4)) {
               var2.append(var4);
            } else {
               var2.append(' ');
            }
         }

         return var2.toString().trim();
      }
   }

   private static void appendToProperty(String var0, String var1) {
      if (var1 != null && !var1.isEmpty()) {
         synchronized (System.getProperties()) {
            String var3 = System.getProperty(var0, "");
            String var4 = var3.isEmpty() ? var1 : var3 + '\u0001' + var1;

            while (var4.length() > 16000) {
               int var5 = var4.indexOf(1);
               if (var5 < 0) {
                  var4 = var4.substring(var4.length() - 16000);
                  break;
               }

               var4 = var4.substring(var5 + 1);
            }

            System.setProperty(var0, var4);
         }
      }
   }

   private static String pollProperty(String var0) {
      synchronized (System.getProperties()) {
         String var2 = System.getProperty(var0, "");
         if (var2.isEmpty()) {
            return null;
         } else {
            int var3 = var2.indexOf(1);
            String var4;
            String var5;
            if (var3 < 0) {
               var4 = var2;
               var5 = "";
            } else {
               var4 = var2.substring(0, var3);
               var5 = var2.substring(var3 + 1);
            }

            if (var5.isEmpty()) {
               System.clearProperty(var0);
            } else {
               System.setProperty(var0, var5);
            }

            return var4;
         }
      }
   }
}
