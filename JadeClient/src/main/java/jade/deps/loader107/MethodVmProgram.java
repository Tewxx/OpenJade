package jade.deps.loader107;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

final class MethodVmProgram {
   final int localsCount;
   final int stackSize;
   final int[][] instructions;
   final int[][] handlers;

   MethodVmProgram(String var1) {
      byte[] var2 = Base64.getDecoder().decode(var1);

      try {
         DataInputStream var3 = new DataInputStream(new ByteArrayInputStream(var2));
         if (var3.readInt() != 1247169793) {
            throw new IllegalArgumentException("unsupported method VM version");
         }

         int var4 = var3.readInt();
         this.localsCount = validateLimit(var3.readInt(), 65535);
         this.stackSize = validateLimit(var3.readInt(), 65535);
         this.instructions = new int[validateLimit(var3.readInt(), 65535)][];
         this.handlers = new int[validateLimit(var3.readInt(), 65535)][4];

         for (int var5 = 0; var5 < this.instructions.length; var5++) {
            int var6 = validateLimit(var3.readInt(), 65535);
            if (var6 == 0) {
               throw new IllegalArgumentException("empty method VM instruction");
            }

            this.instructions[var5] = new int[var6];

            for (int var7 = 0; var7 < var6; var7++) {
               this.instructions[var5][var7] = var3.readInt();
            }

            this.instructions[var5][0] = this.instructions[var5][0] ^ var4;
         }

         for (int[] var8 : this.handlers) {
            for (int var9 = 0; var9 < 4; var9++) {
               var8[var9] = var3.readInt();
            }

            if (var8[0] < 0
               || var8[0] >= var8[1]
               || var8[1] > this.instructions.length
               || var8[2] < 0
               || var8[2] >= this.instructions.length) {
               throw new IllegalArgumentException("invalid method VM handler");
            }
         }

         if (this.instructions.length == 0 || var3.read() != -1) {
            throw new IllegalArgumentException("invalid method VM program");
         }
      } catch (IOException var13) {
         throw new IllegalArgumentException("truncated method VM program", var13);
      } finally {
         Arrays.fill(var2, (byte)0);
      }
   }

   private static int validateLimit(int var0, int var1) {
      if (var0 >= 0 && var0 <= var1) {
         return var0;
      } else {
         throw new IllegalArgumentException("method VM limit exceeded");
      }
   }
}
