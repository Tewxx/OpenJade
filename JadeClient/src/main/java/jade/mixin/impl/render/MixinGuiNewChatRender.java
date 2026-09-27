// Jade recovery: recovered class name: MixinGuiNewChatRender; mixin target: net.minecraft.client.gui.GuiNewChat; original class: jade.mixin.impl.render.Mc3c8035658a51a1de0817e7e0792fe9a
package jade.mixin.impl.render;

import jade.client.common.ChatUtils;
import java.util.List;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiNewChat.class)
public class MixinGuiNewChatRender {
   @Shadow
   @Final
   private List<ChatLine> field_146253_i;

   @Inject(method = "drawChat", at = @At("HEAD"))
   private void jade$beginGradientChatRender(int updateCounter, CallbackInfo ci) {
      ChatUtils.enterChatRender();
   }

   @Inject(method = "drawChat", at = @At("RETURN"))
   private void jade$endGradientChatRender(int updateCounter, CallbackInfo ci) {
      ChatUtils.exitChatRender();
   }

   @Inject(method = "setChatLine", at = @At("RETURN"))
   private void jade$registerWrappedGradientLines(IChatComponent chatComponent, int chatLineId, int updateCounter, boolean displayOnly, CallbackInfo ci) {
      String rawText = ChatUtils.getOriginalFormattedText(chatComponent.getFormattedText());
      if (rawText != null) {
         for (ChatLine line : this.field_146253_i) {
            if (line.getUpdatedCounter() != updateCounter
               || chatLineId != 0 && line.getChatLineID() != chatLineId
               || !ChatUtils.registerFormatting(line.getChatComponent().getFormattedText(), rawText)) {
               break;
            }
         }
      }
   }
}
