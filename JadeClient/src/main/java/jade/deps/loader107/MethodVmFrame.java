package jade.deps.loader107;

import java.util.Arrays;

final class MethodVmFrame {
   final Object[] locals;
   final Object[] stack;
   int stackPointer;
   int programCounter;
   Object result;

   MethodVmFrame(int var1, int var2, Object[] var3) {
      this.locals = new Object[var1];
      this.stack = new Object[var2];
      System.arraycopy(var3, 0, this.locals, 0, var3.length);
   }

   void push(Object var1) {
      this.stack[this.stackPointer++] = var1;
   }

   Object pop() {
      Object var1 = this.stack[--this.stackPointer];
      this.stack[this.stackPointer] = null;
      return var1;
   }

   int popInt() {
      return ((Number)this.pop()).intValue();
   }

   long popLong() {
      return ((Number)this.pop()).longValue();
   }

   float popFloat() {
      return ((Number)this.pop()).floatValue();
   }

   double popDouble() {
      return ((Number)this.pop()).doubleValue();
   }

   void clearStack() {
      Arrays.fill(this.stack, null);
      this.stackPointer = 0;
   }

   void reset() {
      this.clearStack();
      Arrays.fill(this.locals, null);
      this.result = null;
   }

   void replaceReferences(Object var1, Object var2) {
      for (int var3 = 0; var3 < this.stackPointer; var3++) {
         if (this.stack[var3] == var1) {
            this.stack[var3] = var2;
         }
      }

      for (int var4 = 0; var4 < this.locals.length; var4++) {
         if (this.locals[var4] == var1) {
            this.locals[var4] = var2;
         }
      }
   }
}
