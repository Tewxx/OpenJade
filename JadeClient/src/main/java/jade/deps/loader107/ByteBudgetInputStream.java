package jade.deps.loader107;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

final class ByteBudgetInputStream extends FilterInputStream {
   private long remainingBudget;

   ByteBudgetInputStream(InputStream var1, long var2) {
      super(var1);
      if (var1 != null && var2 >= 0L) {
         this.remainingBudget = var2;
      } else {
         throw new IllegalArgumentException("invalid input budget");
      }
   }

   @Override
   public int read() throws IOException {
      byte var2 = 0;

      while (true) {
         switch (var2) {
            case 0:

               var2 = 1;
               break;
            case 1:
               var2 = 2;
               break;
            default:
               int var1 = this.in.read();
               if (var1 >= 0) {
                  if (this.remainingBudget == 0L) {
                     throw new IOException("response exceeds byte budget");
                  }

                  this.remainingBudget--;
               }

               return var1;
         }
      }
   }

   @Override
   public int read(byte[] var1, int var2, int var3) throws IOException {
      byte var5 = 0;

      while (true) {
         switch (var5) {
            case 0:

               var5 = 1;
               break;
            case 1:
               var5 = 2;
               break;
            default:
               if (var1 == null) {
                  throw new NullPointerException("buffer");
               } else if (var2 < 0 || var3 < 0 || var3 > var1.length - var2) {
                  throw new IndexOutOfBoundsException();
               } else if (var3 == 0) {
                  return 0;
               } else if (this.remainingBudget == 0L) {
                  return this.read();
               } else {
                  int var4 = this.in.read(var1, var2, (int)Math.min((long)var3, this.remainingBudget));
                  if (var4 > 0) {
                     this.remainingBudget -= var4;
                  }

                  return var4;
               }
         }
      }
   }

   @Override
   public long skip(long var1) throws IOException {
      byte var5 = 0;

      while (true) {
         switch (var5) {
            case 0:

               var5 = 1;
               break;
            case 1:
               var5 = 2;
               break;
            default:
               if (var1 <= 0L) {
                  return 0L;
               } else {
                  long var3 = this.in.skip(Math.min(var1, this.remainingBudget));
                  this.remainingBudget -= var3;
                  return var3;
               }
         }
      }
   }

   @Override
   public int available() throws IOException {
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
               return (int)Math.min((long)this.in.available(), this.remainingBudget);
         }
      }
   }

   @Override
   public boolean markSupported() {
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
               return false;
         }
      }
   }

   @Override
   public synchronized void mark(int var1) {
   }

   @Override
   public synchronized void reset() throws IOException {
      throw new IOException("bounded input cannot be reset");
   }
}
