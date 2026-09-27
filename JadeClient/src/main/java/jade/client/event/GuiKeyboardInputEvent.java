// Jade recovery: original class: jade.deps.eLz.Y5QOB5
package jade.client.event;

public class GuiKeyboardInputEvent extends Event {
   public char typedChar;
   public int keyCode;

   public GuiKeyboardInputEvent(char var1, int var2) {
      this.keyCode = var2;
      this.typedChar = var1;
   }
}
