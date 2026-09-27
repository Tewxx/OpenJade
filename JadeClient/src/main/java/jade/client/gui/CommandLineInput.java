// Jade recovery: original class: jade.deps.eLz.ZyBhIa8
package jade.client.gui;

import jade.client.common.CommandLineBridge;
import jade.client.module.client.CommandLine;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;

public final class CommandLineInput extends Gui {
   private GuiButton VKta;
   private GuiTextField inputField;

   public void initialize(Minecraft var1, FontRenderer var2, int var3, List<GuiButton> var4) {
      this.inputField = new GuiTextField(1, var2, 22, var3 - 100, 150, 20);
      this.inputField.setMaxStringLength(256);
      this.VKta = new GuiButton(2, 22, var3 - 70, 150, 20, "Send");
      this.VKta.visible = CommandLine.commandLineOpen;
      var4.add(this.VKta);
   }

   public boolean draw(FontRenderer var1, int var2, int var3) {
      if (!CommandLine.commandLineOpen) {
         if (CommandLine.commandLineClosing) {
            CommandLine.commandLineClosing = false;
         }

         return false;
      } else {
         this.VKta.visible = true;
         int var4 = CommandLine.animate.isToggled() ? CommandLine.animation.computeEasedInt(0, 200, 2) : 200;
         if (CommandLine.commandLineClosing) {
            var4 = 200 - var4;
            if (var4 == 0) {
               CommandLine.commandLineClosing = false;
               CommandLine.commandLineOpen = false;
               this.VKta.visible = false;
            }
         }

         drawRect(0, 0, var4, var2, -1089466352);
         this.drawHorizontalLine(0, var4 - 1, var2 - 345, -1);
         this.drawHorizontalLine(0, var4 - 1, var2 - 115, -1);
         drawRect(var4 - 1, 0, var4, var2, -1);
         CommandLineBridge.renderConsole(var1, var2, var4, var3);
         int var5 = var4 - 178;
         this.inputField.xPosition = var5;
         this.VKta.xPosition = var5;
         this.inputField.drawTextBox();
         return true;
      }
   }

   public void click(int var1, int var2, int var3) {
      this.inputField.mouseClicked(var1, var2, var3);
   }

   public boolean keyTyped(char var1, int var2) {
      String var3 = this.inputField.getText();
      if (var2 == 28 && !var3.isEmpty()) {
         this.submit();
         return true;
      } else {
         this.inputField.textboxKeyTyped(var1, var2);
         return false;
      }
   }

   public boolean owns(GuiButton var1) {
      return var1 == this.VKta;
   }

   public GuiTextField input() {
      return this.inputField;
   }

   public void submit() {
      CommandLineBridge.executeCommand(this.inputField.getText());
      this.inputField.setText("");
   }
}
