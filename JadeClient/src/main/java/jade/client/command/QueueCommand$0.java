// Jade recovery: original class: jade.deps.eLz.BY8PzakIB$0
package jade.client.command;

public enum QueueCommand$0 {
   SOLOS("bedwars_eight_one", "solos", "&b"),
   DOUBLES("bedwars_eight_two", "doubles", "&d"),
   THREES("bedwars_four_three", "threes", "&a"),
   FOURS("bedwars_four_four", "fours", "&e"),
   FOUR_V_FOUR("bedwars_two_four", "4v4", "&c");

   private final String playMode;
   private final String displayName;
   private final String chatColor;

   QueueCommand$0(String var3, String var4, String var5) {
      this.playMode = var3;
      this.displayName = var4;
      this.chatColor = var5;
   }

   static java.lang.String access$000(jade.client.command.QueueCommand$0 arg0) {
      return arg0.playMode;
   }

   static java.lang.String access$100(jade.client.command.QueueCommand$0 arg0) {
      return arg0.chatColor;
   }

   static java.lang.String access$200(jade.client.command.QueueCommand$0 arg0) {
      return arg0.displayName;
   }
}
