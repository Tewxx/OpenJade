// Jade recovery: original class: jade.deps.eLz.wPe9Mn
package jade.client.common;

public final class ExternalRendererErrors {
   public static final int STATUS_CONNECTED = 1;
   public static final int STATUS_TEXTURE_CACHE_RESET = 2;
   public static final int WuU = 4;
   public static final int STATUS_FRAME_REJECTED = 8;
   public static final int STATUS_START_FAILED = 16;
   public static final int STATUS_NO_MINECRAFT = 32;
   public static final int rmqJl = 64;
   public static final int STATUS_DRAW_FAILED = 128;

   private ExternalRendererErrors() {
   }

   public static String describeStatus(int var0) {
      if ((var0 & 4) != 0) {
         return "External renderer version mismatch.";
      } else if ((var0 & 8) != 0) {
         return "External renderer rejected a frame.";
      } else if ((var0 & 16) != 0) {
         return "External renderer failed to start.";
      } else if ((var0 & 128) != 0) {
         return "External renderer failed to draw.";
      } else if ((var0 & 32) != 0) {
         return "External renderer cannot find Minecraft.";
      } else if ((var0 & 64) != 0) {
         return "";
      } else {
         return (var0 & 1) != 0 ? "" : "";
      }
   }
}
