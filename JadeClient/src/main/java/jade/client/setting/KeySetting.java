// Jade recovery: original class: jade.deps.eLz.r9mWdsH
package jade.client.setting;

import jade.client.common.MiddleClickFriend;
import jade.deps.gson.JsonArray;
import jade.deps.gson.JsonElement;
import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonPrimitive;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class KeySetting extends Setting {
   private int[] RkY66;
   public GroupSetting groupSetting;

   public KeySetting(String var1, int var2) {
      super(var1);
      int[] var10001 = new int[1];
      var10001[0] = var2;
      this.RkY66 = sanitizeKeyCodes(var10001);
   }

   public KeySetting(GroupSetting var1, String var2, int var3) {
      super(var2);
      this.groupSetting = var1;
      int[] var10001 = new int[1];
      var10001[0] = var3;
      this.RkY66 = sanitizeKeyCodes(var10001);
   }

   public int getKeyCode() {
      return this.RkY66.length == 0 ? 0 : this.RkY66[this.RkY66.length - 1];
   }

   public int[] getKeyCodes() {
      int[] var1 = new int[this.RkY66.length];
      System.arraycopy(this.RkY66, 0, var1, 0, this.RkY66.length);
      return var1;
   }

   @Override
   public String getName() {
      return super.getName();
   }

   @Override
   public String getPath() {
      return this.groupSetting == null ? this.getName() : this.groupSetting.getName() + "." + this.getName();
   }

   public void setKeyCode(int var1) {
      this.RkY66 = sanitizeKeyCodes(new int[]{var1});
   }

   public void CvUpqo(int[] var1) {
      this.RkY66 = sanitizeKeyCodes(var1);
   }

   public boolean isHeldDown() {
      if (this.RkY66.length == 0) {
         return false;
      } else {
         for (int var1 = 0; var1 < this.RkY66.length; var1++) {
            if (!isBoundKeyDown(this.RkY66[var1])) {
               return false;
            }
         }

         return true;
      }
   }

   public JsonElement MtlQ() {
      if (this.RkY66.length <= 1) {
         return new JsonPrimitive(this.getKeyCode());
      } else {
         JsonArray var1 = new JsonArray();

         for (int var2 = 0; var2 < this.RkY66.length; var2++) {
            var1.add(this.RkY66[var2]);
         }

         return var1;
      }
   }

   public String ukYzs() {
      if (this.RkY66.length == 0) {
         return "NONE";
      } else {
         StringBuilder var1 = new StringBuilder();

         for (int var2 = 0; var2 < this.RkY66.length; var2++) {
            if (var2 > 0) {
               var1.append(" + ");
            }

            var1.append(getKeyDisplayName(this.RkY66[var2]));
         }

         return var1.toString();
      }
   }

   @Override
   public void loadConfig(JsonObject var1) {
      String var2 = this.getPath();
      String var3 = this.getName();
      String var4 = var1.has(var2) ? var2 : var3;
      if (var1.has(var4)) {
         JsonElement var5 = var1.get(var4);
         if (var5.isJsonArray()) {
            JsonArray var6 = var5.getAsJsonArray();
            int[] var7 = new int[var6.size()];

            for (int var8 = 0; var8 < var6.size(); var8++) {
               try {
                  var7[var8] = var6.get(var8).getAsInt();
               } catch (Exception var11) {
                  var7[var8] = 0;
               }
            }

            this.RkY66 = sanitizeKeyCodes(var7);
         } else if (var5.isJsonPrimitive()) {
            int var12 = this.getKeyCode();

            try {
               var12 = var5.getAsInt();
            } catch (Exception var10) {
            }

            this.RkY66 = sanitizeKeyCodes(new int[]{var12});
         }
      }
   }

   public static int[] SDwL(int var0) {
      if (var0 != 0 && var0 != 0) {
         int[] var1 = pmdu(var0);
         int[] var2 = new int[var1.length + (isModifierCode(var0) ? 0 : 1)];
         System.arraycopy(var1, 0, var2, 0, var1.length);
         if (!isModifierCode(var0)) {
            var2[var2.length - 1] = var0;
         }

         return var2.length == 0 ? new int[]{var0} : sanitizeKeyCodes(var2);
      } else {
         return new int[0];
      }
   }

   public static int[] combineWithHeldModifiers(int var0) {
      int[] var1 = pmdu(0);
      int[] var2 = new int[var1.length + 1];
      System.arraycopy(var1, 0, var2, 0, var1.length);
      var2[var2.length - 1] = var0;
      return sanitizeKeyCodes(var2);
   }

   public static String SBJv(int var0) {
      return var0 != 0 && var0 != 0 ? getKeyDisplayName(var0) : "NONE";
   }

   public static boolean isModifierKey(int var0) {
      return isModifierCode(var0);
   }

   private static int[] pmdu(int var0) {
      int[] var1 = new int[]{29, 157, 42, 54, 56, 184};
      int[] var2 = new int[var1.length];
      int var3 = 0;

      for (int var4 = 0; var4 < var1.length; var4++) {
         int var5 = var1[var4];
         if (var5 != var0 && Keyboard.isKeyDown(var5)) {
            var2[var3++] = var5;
         }
      }

      int[] var6 = new int[var3];
      System.arraycopy(var2, 0, var6, 0, var3);
      return var6;
   }

   private static boolean isBoundKeyDown(int var0) {
      if (var0 < 1000) {
         return Keyboard.isKeyDown(var0);
      } else {
         return var0 != 1069 && var0 != 1070 ? Mouse.isButtonDown(var0 - 1000) : MiddleClickFriend.isScrollKeyPressed(var0);
      }
   }

   private static boolean isModifierCode(int var0) {
      return var0 == 29 || var0 == 157 || var0 == 42 || var0 == 54 || var0 == 56 || var0 == 184;
   }

   private static int[] sanitizeKeyCodes(int[] var0) {
      if (var0 == null) {
         return new int[0];
      } else {
         int[] var1 = new int[var0.length];
         int var2 = 0;

         for (int var3 = 0; var3 < var0.length; var3++) {
            int var4 = var0[var3];
            if (var4 != 0 && var4 != 0 && !wSpnO(var1, var2, var4)) {
               var1[var2++] = var4;
            }
         }

         int[] var5 = new int[var2];
         System.arraycopy(var1, 0, var5, 0, var2);
         return var5;
      }
   }

   private static boolean wSpnO(int[] var0, int var1, int var2) {
      for (int var3 = 0; var3 < var1; var3++) {
         if (var0[var3] == var2) {
            return true;
         }
      }

      return false;
   }

   private static String getKeyDisplayName(int var0) {
      if (var0 == 1069) {
         return "MWU";
      } else if (var0 == 1070) {
         return "MWD";
      } else if (var0 >= 1000) {
         return "M" + (var0 - 1000);
      } else if (var0 == 29 || var0 == 157) {
         return "CTRL";
      } else if (var0 == 42 || var0 == 54) {
         return "SHIFT";
      } else if (var0 != 56 && var0 != 184) {
         String var1 = Keyboard.getKeyName(var0);
         return var1 == null ? String.valueOf(var0) : var1;
      } else {
         return "ALT";
      }
   }
}
