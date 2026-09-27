// Jade recovery: original class: jade.deps.eLz.TxiKTcc3
package jade.client.common;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class ExternalRenderBuffer {
   public static final int MAGIC = 1480868170;
   public static final int VERSION = 4;
   public static final int jBa = 1048576;
   public static final int MAX_COMMANDS = 8192;
   private static final float COORDINATE_LIMIT = 1000000.0F;
   private static final float sNyo = 256.0F;
   private static final float cstC = 4096.0F;
   private static final float powRv = 4096.0F;
   public final byte[] AkJmu = new byte[1048576];
   private final ByteBuffer byteBuffer = ByteBuffer.wrap(this.AkJmu).order(ByteOrder.LITTLE_ENDIAN);
   private int commandCount;
   public int jfwSfx;
   public final int[] referencedTextureIds = new int[256];
   public final int[] uploadedTextureIds = new int[64];
   public int referencedTextureCount;
   public int zNc3;
   public int IVr5;

   public void TCsFvxT(long var1, int var3, int var4, int var5) {
      ((Buffer)this.byteBuffer).clear();
      this.byteBuffer.putInt(0).putInt(1480868170).putInt(4).putLong(var1).putInt(var3).putInt(var4).putInt(var5).putInt(0);
      this.commandCount = 0;
      this.referencedTextureCount = this.zNc3 = 0;
   }

   public void drawLine(double var1, double var3, double var5, double var7, int var9, float var10) {
      this.writeRectCommand(1, var1, var3, var5, var7, var9, var10, 0.0F);
   }

   public void fillRoundedRect(double var1, double var3, double var5, double var7, int var9, float var10) {
      this.writeRectCommand(2, var1, var3, var5, var7, var9, 0.0F, var10);
   }

   public static float XNfyt(float var0, float var1) {
      return Math.max(0.0F, var0) * var1 * 0.5F;
   }

   public void strokeRoundedRect(double var1, double var3, double var5, double var7, int var9, float var10, float var11) {
      this.writeRectCommand(2, var1, var3, var5, var7, var9, var11, var10);
   }

   public void drawTexture(int var1, double var2, double var4, float var6, float var7, float var8) {
      this.drawTextureQuad(var1, var2, var4, var6, var7, var8, false);
   }

   public void drawTextureQuad(int var1, double var2, double var4, float var6, float var7, float var8, boolean var9) {
      if (var1 != 0 && this.canAppendBytes(48) && areFiniteCoordinates(var2, var4, var6, var8) && !(var6 <= 0.0F) && !(var6 > 4096.0F) && !(Math.abs(var8) > 360.0F)) {
         boolean var10 = false;

         for (int var11 = 0; var11 < this.referencedTextureCount; var11++) {
            if (this.referencedTextureIds[var11] == var1) {
               var10 = true;
               break;
            }
         }

         if (var10 || this.referencedTextureCount != this.referencedTextureIds.length) {
            this.writeCommand(
               6,
               Math.round(Math.max(0.0F, Math.min(1.0F, var7)) * 255.0F) << 24,
               var1,
               var9 ? 2 : 0,
               (float)var2,
               (float)var4,
               var6,
               var6,
               var8,
               0.0F,
               0.0F,
               0.0F
            );
            if (!var10) {
               this.referencedTextureIds[this.referencedTextureCount++] = var1;
            }
         }
      }
   }

   public boolean FnkMa(int var1, byte[] var2) {
      return this.qqPw(var1, 64, 64, var2);
   }

   public boolean qqPw(int var1, int var2, int var3, byte[] var4) {
      if (var1 != 0
         && var2 > 0
         && var3 > 0
         && var2 <= 2048
         && var3 <= 2048
         && (long)var2 * var3 * 4L == var4.length
         && var4.length <= 262144
         && this.zNc3 != this.uploadedTextureIds.length
         && this.canAppendBytes(52 + var4.length)) {
         this.writeCommand(7, var2, var1, var3, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
         this.byteBuffer.putInt(var4.length).put(var4);
         this.uploadedTextureIds[this.zNc3++] = var1;
         return true;
      } else {
         return false;
      }
   }

   public boolean uploadAlphaTexture(int var1, int var2, int var3, byte[] var4) {
      if (var1 != 0
         && var2 > 0
         && var3 > 0
         && var2 <= 2048
         && var3 <= 2048
         && (long)var2 * var3 == var4.length
         && var4.length <= 262144
         && this.zNc3 != this.uploadedTextureIds.length
         && this.canAppendBytes(52 + var4.length)) {
         this.writeCommand(9, var2, var1, var3, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
         this.byteBuffer.putInt(var4.length).put(var4);
         this.uploadedTextureIds[this.zNc3++] = var1;
         return true;
      } else {
         return false;
      }
   }

   public void drawTextureRect(int var1, double var2, double var4, float var6, float var7, int var8, boolean var9) {
      if (var1 != 0 && areFiniteCoordinates(var2, var4, var6, var7) && !(var6 <= 0.0F) && !(var7 <= 0.0F) && !(var6 > 4096.0F) && !(var7 > 4096.0F) && this.canAppendBytes(48)) {
         boolean var10 = false;

         for (int var11 = 0; var11 < this.referencedTextureCount; var11++) {
            if (this.referencedTextureIds[var11] == var1) {
               var10 = true;
               break;
            }
         }

         if (var10 || this.referencedTextureCount != this.referencedTextureIds.length) {
            this.writeCommand(6, var8, var1, var9 ? 3 : 1, (float)var2, (float)var4, var6, var7, 0.0F, 0.0F, 0.0F, 0.0F);
            if (!var10) {
               this.referencedTextureIds[this.referencedTextureCount++] = var1;
            }
         }
      }
   }

   public void fillPerCornerRoundedRect(double var1, double var3, double var5, double var7, int var9, float var10, float var11, float var12, float var13) {
      if (areFiniteCoordinates(var1, var3, var5, var7) && !(var5 <= var1) && !(var7 <= var3) && areValidSizes(var10, var11, var12, var13) && this.canAppendBytes(48)) {
         this.writeCommand(8, var9, 0, 0, (float)var1, (float)var3, (float)var5, (float)var7, var10, var11, var12, var13);
      }
   }

   public void VogZb(double var1, double var3, double var5, double var7, double var9, double var11, int var13) {
      if (this.canAppendBytes(48) && areFiniteCoordinates(var1, var3, var5, var7) && areFiniteCoordinates(var9, var11, 0.0, 0.0)) {
         this.writeCommand(5, var13, 0, 0, (float)var1, (float)var3, (float)var5, (float)var7, (float)var9, (float)var11, 0.0F, 0.0F);
      }
   }

   public void drawText(String var1, double var2, double var4, float var6, int var7, boolean var8, float var9) {
      int var10 = getUtf8ByteLength(var1);
      if (var10 >= 0 && this.canAppendBytes(60 + var10) && areFiniteCoordinates(var2, var4, var6, var9) && !(var6 < 1.0F) && !(var6 > 256.0F)) {
         this.writeCommand(4, var7, 0, var8 ? 4 : 0, (float)var2, (float)var4, var9, 0.0F, 0.0F, 0.0F, 0.0F, Math.max(1.0F, Math.min(256.0F, var6)));
         this.writeString(var1, var10);
         this.writeString("", 0);
         this.writeString("", 0);
      }
   }

   private void writeRectCommand(int var1, double var2, double var4, double var6, double var8, int var10, float var11, float var12) {
      if (this.canAppendBytes(48) && areFiniteCoordinates(var2, var4, var6, var8) && !(var11 < 0.0F) && !(var11 > 256.0F) && !(var12 < 0.0F) && !(var12 > 4096.0F)) {
         if (var1 != 2 || !(var6 < var2) && !(var8 < var4)) {
            this.writeCommand(var1, var10, 0, 0, (float)var2, (float)var4, (float)var6, (float)var8, var11, var12, 0.0F, 0.0F);
         }
      }
   }

   public void GEDQ(
      double var1, double var3, float var5, float var6, float var7, float var8, int var9, int var10, int var11, String var12, String var13, String var14
   ) {
      int var15 = getUtf8ByteLength(var12);
      int var16 = getUtf8ByteLength(var13);
      int var17 = getUtf8ByteLength(var14);
      if (var15 >= 0
         && var16 >= 0
         && var17 >= 0
         && areFiniteCoordinates(var1, var3, var5, var6)
         && areFiniteCoordinates(var7, var8, 0.0, 0.0)
         && !(var5 < 1.0F)
         && !(var5 > 256.0F)
         && !(var6 < 0.0F)
         && !(var6 > 1.0F)
         && !(var7 < 0.0F)
         && !(var7 > 4096.0F)
         && !(var8 < 0.0F)
         && !(var8 > 1.0F)
         && (var11 & -64) == 0
         && this.canAppendBytes(60 + var15 + var16 + var17)) {
         this.writeCommand(3, var9, var10, var11, (float)var1, (float)var3, 0.0F, var8, 0.0F, var7, var6, var5);
         this.writeString(var12, var15);
         this.writeString(var13, var16);
         this.writeString(var14, var17);
      }
   }

   private static int getUtf8ByteLength(String var0) {
      if (var0 != null && var0.length() <= 2048) {
         int var1 = 0;
         int var2 = 0;

         while (var2 < var0.length()) {
            int var3 = var0.codePointAt(var2);
            var2 += Character.charCount(var3);
            if (var3 >= 55296 && var3 <= 57343) {
               return -1;
            }

            var1 += var3 < 128 ? 1 : (var3 < 2048 ? 2 : (var3 < 65536 ? 3 : 4));
         }

         return var1 <= 4096 ? var1 : -1;
      } else {
         return -1;
      }
   }

   private void writeString(String var1, int var2) {
      this.byteBuffer.putInt(var2);
      int var3 = 0;

      while (var3 < var1.length()) {
         int var4 = var1.codePointAt(var3);
         var3 += Character.charCount(var4);
         if (var4 < 128) {
            this.byteBuffer.put((byte)var4);
         } else if (var4 < 2048) {
            this.byteBuffer.put((byte)(192 | var4 >> 6)).put((byte)(128 | var4 & 63));
         } else if (var4 < 65536) {
            this.byteBuffer.put((byte)(224 | var4 >> 12)).put((byte)(128 | var4 >> 6 & 63)).put((byte)(128 | var4 & 63));
         } else {
            this.byteBuffer.put((byte)(240 | var4 >> 18)).put((byte)(128 | var4 >> 12 & 63)).put((byte)(128 | var4 >> 6 & 63)).put((byte)(128 | var4 & 63));
         }
      }
   }

   private boolean canAppendBytes(int var1) {
      return this.commandCount < 8192 && this.byteBuffer.remaining() >= var1;
   }

   private static boolean areFiniteCoordinates(double var0, double var2, double var4, double var6) {
      return Double.isFinite(var0)
         && Double.isFinite(var2)
         && Double.isFinite(var4)
         && Double.isFinite(var6)
         && Math.abs(var0) <= 1000000.0
         && Math.abs(var2) <= 1000000.0
         && Math.abs(var4) <= 1000000.0
         && Math.abs(var6) <= 1000000.0;
   }

   private static boolean areValidSizes(float var0, float var1, float var2, float var3) {
      return areFiniteCoordinates(var0, var1, var2, var3)
         && var0 >= 0.0F
         && var1 >= 0.0F
         && var2 >= 0.0F
         && var3 >= 0.0F
         && var0 <= 4096.0F
         && var1 <= 4096.0F
         && var2 <= 4096.0F
         && var3 <= 4096.0F;
   }

   private void writeCommand(int var1, int var2, int var3, int var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, float var12) {
      this.byteBuffer
         .putInt(var1)
         .putInt(var2)
         .putInt(var3)
         .putInt(var4)
         .putFloat(var5)
         .putFloat(var6)
         .putFloat(var7)
         .putFloat(var8)
         .putFloat(var9)
         .putFloat(var10)
         .putFloat(var11)
         .putFloat(var12);
      this.commandCount++;
   }

   public void seHtuu4() {
      this.jfwSfx = this.byteBuffer.position();
      this.byteBuffer.putInt(0, this.jfwSfx - 4);
      this.byteBuffer.putInt(32, this.commandCount);
   }

   public int getRemainingCommands() {
      return 8192 - this.commandCount;
   }
}
