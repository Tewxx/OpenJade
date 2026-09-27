// Jade recovery: original class: jade.deps.eLz.AZaEmxC
package jade.client.command;

import jade.client.common.CommandInput;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import org.lwjgl.input.Keyboard;

public class BindsCommand extends Command {
   private final KeybindListBuilder huxy = new KeybindListBuilder();

   public BindsCommand() {
      super("binds");
   }

   @Override
   public void execute(CommandInput var1) {
      if (var1.getArgumentCount() == 0) {
         this.sendKeybindList(this.huxy.buildKeybindMap(0), null);
      } else if (var1.getArgumentCount() != 1) {
         this.ahGlioN();
      } else {
         int var2 = Keyboard.getKeyIndex(var1.getArgument(0).toUpperCase());
         if (var2 == 0) {
            this.sendChatMessage("&7Invalid key.");
         } else {
            this.sendKeybindList(this.huxy.buildKeybindMap(var2), var1.getArgument(0).toUpperCase());
         }
      }
   }

   private void sendKeybindList(Map<String, List<String>> var1, String var2) {
      int var3 = this.huxy.yMmw(var1);
      String var4 = "&7 module" + (var3 == 1 ? "" : "s");
      if (var2 == null) {
         this.sendChatMessage("&f" + var3 + var4 + " have keybinds.");
      } else {
         this.sendChatMessage("&f" + var3 + var4 + " on keybind &b" + var2 + "&7.");
      }

      for (Entry var6 : var1.entrySet()) {
         for (String var8 : (java.lang.Iterable<String>) (java.lang.Iterable<?>) ((List)var6.getValue())) {
            this.sendChatMessage(" &7" + (String)var6.getKey() + " &b" + var8);
         }
      }
   }

   @Override
   public void ahGlioN() {
      this.sendChatMessage("&7List binds: &b" + this.withCommandPrefix("binds") + " &7or &b" + this.withCommandPrefix("binds") + " [key]");
   }
}
