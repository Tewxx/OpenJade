package jade.deps.loader107;

import java.util.function.BooleanSupplier;

public final class StartupProgramRunner {
   public static final int OPCODE_CALL = 49;
   public static final int OPCODE_BRANCH = 87;
   public static final int OPCODE_RETURN = 109;

   private StartupProgramRunner() {
   }

   public static void runStartupProgram(int[] var0, Runnable[] var1, BooleanSupplier[] var2) {
      if (var0 != null && var1 != null && var2 != null) {
         int[] var3 = (int[])var0.clone();
         Runnable[] var4 = (Runnable[])var1.clone();
         BooleanSupplier[] var5 = (BooleanSupplier[])var2.clone();
         validateStartupProgram(var3, var4, var5);
         int var6 = 0;

         while (true) {
            int var7 = var6 * 3;
            int var8 = var3[var7 + 1];
            switch (var3[var7]) {
               case 49:
                  var4[var8].run();
                  var6++;
                  break;
               case 87:
                  var6 = var5[var8].getAsBoolean() ? var6 + 1 : var3[var7 + 2];
                  break;
               case 109:
                  return;
               default:
                  throw new IllegalStateException("invalid validated startup opcode");
            }
         }
      } else {
         throw new IllegalArgumentException("missing startup program input");
      }
   }

   private static void validateStartupProgram(int[] var0, Runnable[] var1, BooleanSupplier[] var2) {
      if (var0.length != 0 && var0.length % 3 == 0) {
         int var3 = var0.length / 3;

         for (int var4 = 0; var4 < var3; var4++) {
            int var5 = var4 * 3;
            int var6 = var0[var5];
            int var7 = var0[var5 + 1];
            int var8 = var0[var5 + 2];
            if (var6 == 49) {
               if (var7 < 0 || var7 >= var1.length || var1[var7] == null || var8 != 0 || var4 == var3 - 1) {
                  throw new IllegalArgumentException("invalid startup call");
               }
            } else if (var6 == 87) {
               if (var7 < 0 || var7 >= var2.length || var2[var7] == null || var8 <= var4 || var8 >= var3 || var4 == var3 - 1) {
                  throw new IllegalArgumentException("invalid startup branch");
               }
            } else {
               if (var6 != 109) {
                  throw new IllegalArgumentException("unknown startup opcode");
               }

               if (var7 != 0 || var8 != 0) {
                  throw new IllegalArgumentException("invalid startup return");
               }
            }
         }
      } else {
         throw new IllegalArgumentException("invalid startup program length");
      }
   }
}
