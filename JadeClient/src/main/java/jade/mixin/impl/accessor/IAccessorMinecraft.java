// Jade recovery: recovered class name: IAccessorMinecraft; mixin target: net.minecraft.client.Minecraft
package jade.mixin.impl.accessor;

import net.minecraft.client.Minecraft;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Minecraft.class)
public interface IAccessorMinecraft {
   @Invoker("dispatchKeypresses")
   void invokeDispatchKeypresses();

   @Invoker("clickMouse")
   void callClickMouse();

   @Invoker("rightClickMouse")
   void callRightClickMouse();

   @Accessor("leftClickCounter")
   void setLeftClickCounter(int var1);

   @Accessor("leftClickCounter")
   int getLeftClickCounter();

   @Accessor("rightClickDelayTimer")
   void setRightClickDelayTimer(int var1);

   @Accessor("rightClickDelayTimer")
   int getRightClickDelayTimer();

   @Accessor("myNetworkManager")
   NetworkManager getMyNetworkManager();

   @Accessor("timer")
   Timer getTimer();
}
