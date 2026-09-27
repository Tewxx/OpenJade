package jade.deps.loader107;

public final class PendingChatQueue {
   private static final String QUEUE_PROPERTY = "jade.chat.queue";
   private static final char ENTRY_SEPARATOR = '\u0001';
   private static final Object QUEUE_LOCK = new Object();
   private static final int MAX_QUEUED_LENGTH = 16000;

   private PendingChatQueue() {
   }

   public static void enqueueMessage(String var0) {
      if (var0 != null && !var0.isEmpty()) {
         String var1 = var0.indexOf(1) >= 0 ? var0.replace('\u0001', ' ') : var0;
         synchronized (QUEUE_LOCK) {
            String var3 = System.getProperty("jade.chat.queue", "");
            String var4 = var3.isEmpty() ? var1 : var3 + '\u0001' + var1;
            if (var4.length() > 16000) {
               while (var4.length() > 16000) {
                  int var5 = var4.indexOf(1);
                  if (var5 < 0) {
                     var4 = var4.substring(var4.length() - 16000);
                     break;
                  }

                  var4 = var4.substring(var5 + 1);
               }
            }

            System.setProperty("jade.chat.queue", var4);
         }
      }
   }

   public static String pollMessage() {
      synchronized (QUEUE_LOCK) {
         String var1 = System.getProperty("jade.chat.queue", "");
         if (var1.isEmpty()) {
            return null;
         } else {
            int var2 = var1.indexOf(1);
            String var3;
            String var4;
            if (var2 < 0) {
               var3 = var1;
               var4 = "";
            } else {
               var3 = var1.substring(0, var2);
               var4 = var1.substring(var2 + 1);
            }

            if (var4.isEmpty()) {
               System.clearProperty("jade.chat.queue");
            } else {
               System.setProperty("jade.chat.queue", var4);
            }

            return var3;
         }
      }
   }

   public static void clearQueue() {
      synchronized (QUEUE_LOCK) {
         System.clearProperty("jade.chat.queue");
      }
   }
}
