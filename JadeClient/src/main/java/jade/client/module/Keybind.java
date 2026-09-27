// Jade recovery: original class: jade.deps.eLz.vfbKmn
package jade.client.module;

import jade.client.common.MiddleClickFriend;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public final class Keybind {
   private int keycode;
   private boolean wasDown;

   public Keybind(int var1) {
      this.keycode = var1;
   }

   public int getKeycode() {
      return this.keycode;
   }

   public void setKeycode(int var1) {
      this.keycode = var1;
   }

   private boolean isDown() {
      if (this.keycode < 1000) {
         return Keyboard.isKeyDown(this.keycode);
      } else {
         return this.keycode != 1069 && this.keycode != 1070 ? Mouse.isButtonDown(this.keycode - 1000) : MiddleClickFriend.isScrollKeyPressed(this.keycode);
      }
   }

   public void poll(Module var1) {
      if (this.keycode != 0) {
         if (!this.wasDown && this.isDown()) {
            var1.toggle();
            this.wasDown = true;
         } else {
            if (!this.isDown()) {
               this.wasDown = false;
            }
         }
      }
   }

   public void sync() {
      if (this.keycode != 0) {
         this.wasDown = this.isDown();
      }
   }

   public void clear() {
      this.keycode = 0;
      this.wasDown = false;
   }

   public void onKey(Module var1, int var2, boolean var3) {
      if (this.keycode != 0 && var2 == this.keycode) {
         if (!var3) {
            this.wasDown = false;
         } else if (!this.wasDown) {
            var1.toggle();
            this.wasDown = true;
         }
      }
   }
}
