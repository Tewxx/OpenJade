// Jade recovery: original class: jade.deps.eLz.SUlXxnwqU
package jade.client.common;

import jade.client.event.MouseEvent;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.client.gui.inventory.GuiBrewingStand;
import net.minecraft.client.gui.inventory.GuiDispenser;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.inventory.ContainerWorkbench;
import org.lwjgl.input.Mouse;

public class InputHookManager {
   public static Field field;
   public static HashMap<Class, Field> qwL = new HashMap<>();
   private static final List<Class<?>> lIyydS = Arrays.asList(
      GuiFurnace.class,
      GuiBrewingStand.class,
      GuiEnchantment.class,
      ContainerHopper.class,
      GuiDispenser.class,
      ContainerWorkbench.class,
      ContainerMerchant.class,
      ContainerHorseInventory.class
   );
   public static boolean initializationFailed = false;
   public static Map<String, KeyBinding> fTcl = new HashMap<>();

   public static void initializeReflection() {
      try {
         field = Mouse.class.getDeclaredField("buttons");

         for (Class var1 : lIyydS) {
            InventoryFieldLocator.findInventoryField(var1, qwL);
         }
      } catch (Exception var2) {
         ClientUtils.logger.error("There was an error, relaunch the game.", var2);
         initializationFailed = true;
      }
   }

   public static void registerKeybindings() {
      KeybindRegistry.registerKeybindings(Minecraft.getMinecraft().gameSettings.keyBindings, fTcl);
   }

   public static void simulateMouseButton(int var0, boolean var1) {
      EventBus.post(new MouseEvent(var0, var1, Mouse.getX(), Mouse.getY(), 0, 0, 0));
      setMouseButtonState(var0, var1);
   }

   public static void setMouseButtonState(int var0, boolean var1) {
      ByteBufferFieldWriter.writeByte(field, var0, var1);
   }

   public static boolean setRenderItemInUse(boolean var0) {
      return ItemRendererStateHelper.setRenderItemInUse(var0);
   }
}
