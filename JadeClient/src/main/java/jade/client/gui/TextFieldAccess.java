// Jade recovery: original class: jade.deps.eLz.m2d8Y8n
package jade.client.gui;

import jade.inject.RuntimeAccess;
import java.lang.reflect.Field;
import net.minecraft.client.gui.GuiTextField;

public final class TextFieldAccess {
   private static final Field isEnabledField = resolveMappedField("isEnabled", "field_146226_p");
   private static final Field SlqyFo = resolveMappedField("disabledColor", "field_146221_u");
   private static final Field enabledColorField = resolveMappedField("enabledColor", "field_146222_t");
   private static final Field lFnhUr = resolveMappedField("selectionEnd", "field_146223_s");
   private static final Field HCioa = resolveMappedField("cursorPosition", "field_146224_r");
   private static final Field lineScrollOffsetField = resolveMappedField("lineScrollOffset", "field_146225_q");

   private TextFieldAccess() {
   }

   public static int qZn0(GuiTextField var0, int var1) {
      return readInt(var0, lineScrollOffsetField, var1);
   }

   public static int getCursorPosition(GuiTextField var0, int var1) {
      return readInt(var0, HCioa, var1);
   }

   public static int getSelectionEnd(GuiTextField var0, int var1) {
      return readInt(var0, lFnhUr, var1);
   }

   public static int getTextColor(GuiTextField var0) {
      return readBoolean(var0, isEnabledField, true) ? readInt(var0, enabledColorField, 14737632) : readInt(var0, SlqyFo, 7368816);
   }

   private static Field resolveMappedField(String var0, String var1) {
      return RuntimeAccess.resolveMappedField(GuiTextField.class, var0, var1);
   }

   private static int readInt(GuiTextField var0, Field var1, int var2) {
      try {
         return var1.getInt(var0);
      } catch (IllegalAccessException var4) {
         return var2;
      }
   }

   private static boolean readBoolean(GuiTextField var0, Field var1, boolean var2) {
      try {
         return var1.getBoolean(var0);
      } catch (IllegalAccessException var4) {
         return var2;
      }
   }
}
