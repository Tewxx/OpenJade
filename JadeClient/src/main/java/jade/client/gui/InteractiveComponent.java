// Jade recovery: original class: jade.deps.eLz.zMxvGyvab
package jade.client.gui;

public interface InteractiveComponent {
   default boolean mouseClicked(int var1, int var2, int var3) {
      return false;
   }

   default void mouseReleased(int var1, int var2, int var3) {
   }

   default void keyTyped(char var1, int var2) {
   }

   default void keyPressed(int var1) {
   }
}
