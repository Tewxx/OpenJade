// Jade recovery: original class: jade.deps.eLz.bBVm2SZcSa
package jade.client.setting;

import jade.client.common.IrcChatHandler;
import jade.client.module.client.ChatCommands;
import jade.deps.gson.JsonElement;
import jade.deps.gson.JsonObject;

import java.util.Locale;
import org.lwjgl.input.Keyboard;

public final class PrefixSetting extends TextSetting {
   public PrefixSetting() {
      super(
         "Prefix",
         ".",
         "Type one character...",
         1
      );
   }

   @Override
   public void setValue(String var1) {
      if (ChatCommands.isValidPrefix(var1)) {
         if (!IrcChatHandler.matchesConfiguredPrefix(var1)) {
            super.setValue(var1);
         }
      }
   }

   @Override
   public void loadConfig(JsonObject var1) {
      if (var1 != null) {
         JsonElement var2 = var1.get(this.getPath());
         if (var2 != null && var2.isJsonPrimitive()) {
            String var8 = var2.getAsString();
            if (ChatCommands.isValidPrefix(var8)) {
               this.setValue(var8);
            }
         } else {
            JsonElement var3 = var1.get("Prefix key");
            if (var3 != null && var3.isJsonPrimitive()) {
               try {
                  int var4 = var3.getAsInt();
                  if (var4 <= 0 || var4 >= 256) {
                     return;
                  }

                  String var5 = Keyboard.getKeyName(var4);
                  if (!ChatCommands.isValidPrefix(var5)) {
                     return;
                  }

                  String var6 = var5.toLowerCase(Locale.ROOT);
                  if (ChatCommands.isValidPrefix(var6)) {
                     this.setValue(var6);
                  }
               } catch (Exception var7) {
               }
            }
         }
      }
   }
}
