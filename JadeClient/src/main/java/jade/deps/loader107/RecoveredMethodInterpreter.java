package jade.deps.loader107;

import java.lang.invoke.MethodHandle;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public final class RecoveredMethodInterpreter {
   private static final ConcurrentHashMap<String, MethodVmProgram> concurrentHashMap = new ConcurrentHashMap<>();

   private RecoveredMethodInterpreter() {
   }

   public static Object invokeRecovered(String program, MethodHandle callback, Object[] arguments) {
      try {
         return interpretProgram(program, callback, arguments);
      } catch (Throwable failure) {
         return jade.build.RecoveredHandles.raise(failure);
      }
   }

   public static Object interpretProgram(String var0, MethodHandle var1, Object[] var2) throws Throwable {
      MethodVmProgram var3 = concurrentHashMap.get(var0);
      if (var3 == null) {
         MethodVmProgram var4 = new MethodVmProgram(var0);
         MethodVmProgram var5 = concurrentHashMap.putIfAbsent(var0, var4);
         var3 = var5 == null ? var4 : var5;
      }

      MethodVmFrame var9 = new MethodVmFrame(
         var3.localsCount, var3.stackSize, var2
      );
      Arrays.fill(var2, null);

      Object var10;
      try {
         executeProgram(var3, var1, var9, null);
         var10 = var9.result;
      } finally {
         var9.reset();
      }

      return var10;
   }

   public static Object initializeClass(Class<?> var0) throws ClassNotFoundException {
      Class.forName(var0.getName(), true, var0.getClassLoader());
      return new Object();
   }

   public static int readArrayInt(Object var0, int var1) {
      return var0 instanceof boolean[] ? (((boolean[])var0)[var1] ? 1 : 0) : ((byte[])var0)[var1];
   }

   public static void writeArrayInt(Object var0, int var1, int var2) {
      if (var0 instanceof boolean[]) {
         ((boolean[])var0)[var1] = (var2 & 1) != 0;
      } else {
         ((byte[])var0)[var1] = (byte)var2;
      }
   }

   private static void executeProgram(
      MethodVmProgram var0, MethodHandle var1, MethodVmFrame var2, Object var3
   ) throws Throwable {
      while (true) {
         int var4 = var2.programCounter;
         int[] var5 = var0.instructions[var2.programCounter++];

         try {
            switch (var5[0]) {
               case 0:
                  break;
               case 1:
                  var2.push(null);
                  break;
               case 2:
               case 3:
               case 4:
               case 5:
               case 6:
               case 7:
               case 8:
                  var2.push(var5[0] - 3);
                  break;
               case 9:
               case 10:
                  var2.push((long)(var5[0] - 9));
                  break;
               case 11:
               case 12:
               case 13:
                  var2.push((float)(var5[0] - 11));
                  break;
               case 14:
               case 15:
                  var2.push((double)(var5[0] - 14));
                  break;
               case 16:
               case 17:
                  var2.push(var5[1]);
                  break;
               case 18:
               case 19:
               case 20:
               case 26:
               case 27:
               case 28:
               case 29:
               case 30:
               case 31:
               case 32:
               case 33:
               case 34:
               case 35:
               case 36:
               case 37:
               case 38:
               case 39:
               case 40:
               case 41:
               case 42:
               case 43:
               case 44:
               case 45:
               case 46:
               case 47:
               case 48:
               case 49:
               case 50:
               case 51:
               case 52:
               case 53:
               case 59:
               case 60:
               case 61:
               case 62:
               case 63:
               case 64:
               case 65:
               case 66:
               case 67:
               case 68:
               case 69:
               case 70:
               case 71:
               case 72:
               case 73:
               case 74:
               case 75:
               case 76:
               case 77:
               case 78:
               case 79:
               case 80:
               case 81:
               case 82:
               case 83:
               case 84:
               case 85:
               case 86:
               case 168:
               case 169:
               case 178:
               case 179:
               case 180:
               case 181:
               case 182:
               case 183:
               case 184:
               case 185:
               case 186:
               case 187:
               case 188:
               case 189:
               case 190:
               case 192:
               case 193:
               case 196:
               case 197:
               case 200:
               case 201:
               case 202:
               case 203:
               case 204:
               case 205:
               case 206:
               case 207:
               case 208:
               case 209:
               case 210:
               case 211:
               case 212:
               case 213:
               case 214:
               case 215:
               case 216:
               case 217:
               case 218:
               case 219:
               case 220:
               case 221:
               case 222:
               case 223:
               case 224:
               case 225:
               case 226:
               case 227:
               case 228:
               case 229:
               case 230:
               case 231:
               case 232:
               case 233:
               case 234:
               case 235:
               case 236:
               case 237:
               case 238:
               case 239:
               case 240:
               case 241:
               case 242:
               case 243:
               case 244:
               case 245:
               case 246:
               case 247:
               case 248:
               case 249:
               case 250:
               case 251:
               case 252:
               case 253:
               case 254:
               case 255:
               default:
                  throw new IllegalArgumentException(
                     "unsupported method VM instruction"
                  );
               case 21:
               case 22:
               case 23:
               case 24:
               case 25:
                  var2.push(var2.locals[var5[1]]);
                  break;
               case 54:
               case 55:
               case 56:
               case 57:
               case 58:
                  var2.locals[var5[1]] = var2.pop();
                  break;
               case 87:
                  var2.pop();
                  break;
               case 88:
                  var2.pop();
                  if (var5[1] != 2) {
                     var2.pop();
                  }
                  break;
               case 89:
                  Object var71 = var2.pop();
                  var2.push(var71);
                  var2.push(var71);
                  break;
               case 90:
                  Object var70 = var2.pop();
                  Object var81 = var2.pop();
                  var2.push(var70);
                  var2.push(var81);
                  var2.push(var70);
                  break;
               case 91:
                  Object var69 = var2.pop();
                  Object var80 = var2.pop();
                  if (var5[2] == 2) {
                     var2.push(var69);
                     var2.push(var80);
                     var2.push(var69);
                     break;
                  }

                  Object var84 = var2.pop();
                  var2.push(var69);
                  var2.push(var84);
                  var2.push(var80);
                  var2.push(var69);
                  break;
               case 92:
                  Object var68 = var2.pop();
                  if (var5[1] == 2) {
                     var2.push(var68);
                     var2.push(var68);
                     break;
                  }

                  Object var79 = var2.pop();
                  var2.push(var79);
                  var2.push(var68);
                  var2.push(var79);
                  var2.push(var68);
                  break;
               case 93:
               case 94:
                  applyStackPermutation(var2, var5[1] != 2, var5[0] == 94 && var5[2] != 2);
                  break;
               case 95:
                  Object var67 = var2.pop();
                  Object var78 = var2.pop();
                  var2.push(var67);
                  var2.push(var78);
                  break;
               case 96:
                  int var66 = var2.popInt();
                  var2.push(var2.popInt() + var66);
                  break;
               case 97:
                  long var65 = var2.popLong();
                  var2.push(var2.popLong() + var65);
                  break;
               case 98:
                  float var64 = var2.popFloat();
                  var2.push(var2.popFloat() + var64);
                  break;
               case 99:
                  double var63 = var2.popDouble();
                  var2.push(var2.popDouble() + var63);
                  break;
               case 100:
                  int var62 = var2.popInt();
                  var2.push(var2.popInt() - var62);
                  break;
               case 101:
                  long var61 = var2.popLong();
                  var2.push(var2.popLong() - var61);
                  break;
               case 102:
                  float var60 = var2.popFloat();
                  var2.push(var2.popFloat() - var60);
                  break;
               case 103:
                  double var59 = var2.popDouble();
                  var2.push(var2.popDouble() - var59);
                  break;
               case 104:
                  int var58 = var2.popInt();
                  var2.push(var2.popInt() * var58);
                  break;
               case 105:
                  long var57 = var2.popLong();
                  var2.push(var2.popLong() * var57);
                  break;
               case 106:
                  float var56 = var2.popFloat();
                  var2.push(var2.popFloat() * var56);
                  break;
               case 107:
                  double var55 = var2.popDouble();
                  var2.push(var2.popDouble() * var55);
                  break;
               case 108:
                  int var54 = var2.popInt();
                  var2.push(var2.popInt() / var54);
                  break;
               case 109:
                  long var53 = var2.popLong();
                  var2.push(var2.popLong() / var53);
                  break;
               case 110:
                  float var52 = var2.popFloat();
                  var2.push(var2.popFloat() / var52);
                  break;
               case 111:
                  double var51 = var2.popDouble();
                  var2.push(var2.popDouble() / var51);
                  break;
               case 112:
                  int var50 = var2.popInt();
                  var2.push(var2.popInt() % var50);
                  break;
               case 113:
                  long var49 = var2.popLong();
                  var2.push(var2.popLong() % var49);
                  break;
               case 114:
                  float var48 = var2.popFloat();
                  var2.push(var2.popFloat() % var48);
                  break;
               case 115:
                  double var47 = var2.popDouble();
                  var2.push(var2.popDouble() % var47);
                  break;
               case 116:
                  var2.push(-var2.popInt());
                  break;
               case 117:
                  var2.push(-var2.popLong());
                  break;
               case 118:
                  var2.push(-var2.popFloat());
                  break;
               case 119:
                  var2.push(-var2.popDouble());
                  break;
               case 120:
                  int var46 = var2.popInt();
                  var2.push(var2.popInt() << var46);
                  break;
               case 121:
                  int var45 = var2.popInt();
                  var2.push(var2.popLong() << var45);
                  break;
               case 122:
                  int var44 = var2.popInt();
                  var2.push(var2.popInt() >> var44);
                  break;
               case 123:
                  int var43 = var2.popInt();
                  var2.push(var2.popLong() >> var43);
                  break;
               case 124:
                  int var42 = var2.popInt();
                  var2.push(var2.popInt() >>> var42);
                  break;
               case 125:
                  int var41 = var2.popInt();
                  var2.push(var2.popLong() >>> var41);
                  break;
               case 126:
                  int var40 = var2.popInt();
                  var2.push(var2.popInt() & var40);
                  break;
               case 127:
                  long var39 = var2.popLong();
                  var2.push(var2.popLong() & var39);
                  break;
               case 128:
                  int var38 = var2.popInt();
                  var2.push(var2.popInt() | var38);
                  break;
               case 129:
                  long var37 = var2.popLong();
                  var2.push(var2.popLong() | var37);
                  break;
               case 130:
                  int var36 = var2.popInt();
                  var2.push(var2.popInt() ^ var36);
                  break;
               case 131:
                  long var35 = var2.popLong();
                  var2.push(var2.popLong() ^ var35);
                  break;
               case 132:
                  var2.locals[var5[1]] = ((Number)var2.locals[var5[1]]).intValue() + var5[2];
                  break;
               case 133:
                  var2.push((long)var2.popInt());
                  break;
               case 134:
                  var2.push((float)var2.popInt());
                  break;
               case 135:
                  var2.push((double)var2.popInt());
                  break;
               case 136:
                  var2.push((int)var2.popLong());
                  break;
               case 137:
                  var2.push((float)var2.popLong());
                  break;
               case 138:
                  var2.push((double)var2.popLong());
                  break;
               case 139:
                  var2.push((int)var2.popFloat());
                  break;
               case 140:
                  var2.push((long)var2.popFloat());
                  break;
               case 141:
                  var2.push((double)var2.popFloat());
                  break;
               case 142:
                  var2.push((int)var2.popDouble());
                  break;
               case 143:
                  var2.push((long)var2.popDouble());
                  break;
               case 144:
                  var2.push((float)var2.popDouble());
                  break;
               case 145:
                  var2.push(Integer.valueOf((byte)var2.popInt()));
                  break;
               case 146:
                  var2.push(Integer.valueOf((char)var2.popInt()));
                  break;
               case 147:
                  var2.push(Integer.valueOf((short)var2.popInt()));
                  break;
               case 148:
                  long var34 = var2.popLong();
                  long var83 = var2.popLong();
                  var2.push(var83 > var34 ? 1 : (var83 == var34 ? 0 : -1));
                  break;
               case 149:
               case 150:
                  float var33 = var2.popFloat();
                  float var77 = var2.popFloat();
                  var2.push(
                     !Float.isNaN(var77) && !Float.isNaN(var33) ? (var77 > var33 ? 1 : (var77 == var33 ? 0 : -1)) : (var5[0] == 149 ? -1 : 1)
                  );
                  break;
               case 151:
               case 152:
                  double var32 = var2.popDouble();
                  double var82 = var2.popDouble();
                  var2.push(
                     !Double.isNaN(var82) && !Double.isNaN(var32) ? (var82 > var32 ? 1 : (var82 == var32 ? 0 : -1)) : (var5[0] == 151 ? -1 : 1)
                  );
                  break;
               case 153:
               case 154:
               case 155:
               case 156:
               case 157:
               case 158:
                  if (compareInts(var2.popInt(), 0, var5[0] - 153)) {
                     var2.programCounter = var5[1];
                  }
                  break;
               case 159:
               case 160:
               case 161:
               case 162:
               case 163:
               case 164:
                  int var31 = var2.popInt();
                  int var76 = var2.popInt();
                  if (compareInts(var76, var31, var5[0] - 159)) {
                     var2.programCounter = var5[1];
                  }
                  break;
               case 165:
               case 166:
                  Object var30 = var2.pop();
                  Object var75 = var2.pop();
                  if (var75 == var30 == (var5[0] == 165)) {
                     var2.programCounter = var5[1];
                  }
                  break;
               case 167:
                  var2.programCounter = var5[1];
                  break;
               case 170:
               case 171:
                  int var29 = var2.popInt();
                  var2.programCounter = var5[1];

                  for (byte var74 = 2; var74 < var5.length; var74 += 2) {
                     if (var5[var74] == var29) {
                        var2.programCounter = var5[var74 + 1];
                        break;
                     }
                  }
                  break;
               case 172:
               case 173:
               case 174:
               case 175:
               case 176:
                  var2.result = var2.pop();
                  return;
               case 177:
                  return;
               case 191:
                  throw (Throwable)var2.pop();
               case 194:
                  Object var28 = var2.pop();
                  synchronized (var28) {
                     executeProgram(var0, var1, var2, var28);
                     break;
                  }
               case 195:
                  if (var3 != null && var2.pop() == var3) {
                     return;
                  }

                  throw new IllegalMonitorStateException();
               case 198:
               case 199:
                  if (var2.pop() == null == (var5[0] == 198)) {
                     var2.programCounter = var5[1];
                  }
                  break;
               case 256:
                  Object[] var27 = new Object[var5[2]];

                  for (int var72 = var27.length - 1; var72 >= 0; var72--) {
                     var27[var72] = var2.pop();
                  }

                  try {
                     Object var73 = (Object)var1.invokeExact((int)var5[1], (Object[])var27);
                     if (var5[4] != 0) {
                        var2.replaceReferences(var27[0], var73);
                     } else if (var5[3] != 0) {
                        var2.push(var73);
                     }
                  } finally {
                     Arrays.fill(var27, null);
                  }
            }
         } catch (Throwable var26) {
            Throwable var6 = var26;
            boolean var7 = false;

            for (int[] var11 : var0.handlers) {
               if (var4 >= var11[0] && var4 < var11[1]) {
                  boolean var12 = var11[3] < 0;
                  if (!var12) {
                     Object[] var13 = new Object[]{var6};

                     try {
                        var12 = ((Number)(Object)var1.invokeExact((int)var11[3], (Object[])var13)).intValue() != 0;
                     } finally {
                        var13[0] = null;
                     }
                  }

                  if (var12) {
                     var2.clearStack();
                     var2.push(var6);
                     var2.programCounter = var11[2];
                     var7 = true;
                     break;
                  }
               }
            }

            if (!var7) {
               throw var6;
            }
         }
      }
   }

   private static boolean compareInts(int var0, int var1, int var2) {
      switch (var2) {
         case 0:
            return var0 == var1;
         case 1:
            return var0 != var1;
         case 2:
            return var0 < var1;
         case 3:
            return var0 >= var1;
         case 4:
            return var0 > var1;
         case 5:
            return var0 <= var1;
         default:
            throw new IllegalArgumentException("invalid method VM comparison");
      }
   }

   private static void applyStackPermutation(MethodVmFrame var0, boolean var1, boolean var2) {
      Object var3 = var0.pop();
      Object var4 = var1 ? var0.pop() : null;
      Object var5 = var0.pop();
      Object var6 = var2 ? var0.pop() : null;
      if (var1) {
         var0.push(var4);
      }

      var0.push(var3);
      if (var2) {
         var0.push(var6);
      }

      var0.push(var5);
      if (var1) {
         var0.push(var4);
      }

      var0.push(var3);
   }
}
