// Jade recovery: recovered class name: MixinGuiChat; mixin target: net.minecraft.client.gui.GuiChat; original class: jade.mixin.impl.render.Mc5ea0e71de71660f8137d0db7db37cf0
package jade.mixin.impl.render;

import jade.client.Jade;
import jade.mixin.impl.accessor.IAccessorGuiTextField;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiChat.class)
public abstract class MixinGuiChat extends MixinGuiScreen {
   @Unique
   private static final int GHOST_SUGGESTION_COLOR = -2005434998;
   @Unique
   private String[] jade$commandCompletions = new String[0];
   @Unique
   private int jade$commandCompletionIndex = -1;
   @Shadow
   protected GuiTextField field_146415_a;
   @Shadow
   private boolean field_146414_r;

   @Shadow(aliases = "func_146406_a")
   public abstract void onAutocompleteResponse(String[] var1);

   @Inject(method = "keyTyped", at = @At("RETURN"))
   private void updateLength(CallbackInfo callbackInfo) {
      if (Jade.commandManager != null && Jade.commandManager.isCommandMessage(this.field_146415_a.getText())) {
         this.field_146415_a.setMaxStringLength(256);
      } else {
         this.field_146415_a.setMaxStringLength(100);
      }
   }

   @Inject(method = "keyTyped", at = @At("HEAD"))
   private void clearCommandCompletionState(char typedChar, int keyCode, CallbackInfo callbackInfo) {
      if (keyCode != 15) {
         this.jade$resetCommandCompletions();
      }
   }

   @Inject(method = "drawScreen", at = @At("RETURN"))
   private void drawLiveCommandSuggestion(int mouseX, int mouseY, float partialTicks, CallbackInfo callbackInfo) {
      if (Jade.commandManager != null) {
         String input = this.field_146415_a.getText();
         if (Jade.commandManager.isCommandMessage(input)) {
            String suffix = this.jade$getGhostSuggestionSuffix(input);
            if (!suffix.isEmpty()) {
               IAccessorGuiTextField field = (IAccessorGuiTextField)(Object)this.field_146415_a;
               if (field.getCursorPosition() == input.length() && field.getSelectionEnd() == field.getCursorPosition()) {
                  int inset = field.isEnableBackgroundDrawing() ? 4 : 0;
                  int textLeft = this.field_146415_a.xPosition + inset;
                  int textTop = this.field_146415_a.yPosition + (field.isEnableBackgroundDrawing() ? (field.getHeight() - 8) / 2 : 0);
                  int textRight = this.field_146415_a.xPosition + field.getWidth() - inset;
                  int visibleWidth = Math.max(0, textRight - textLeft);
                  int scroll = Math.max(0, Math.min(field.getLineScrollOffset(), input.length()));
                  String visibleInput = this.field_146297_k.fontRendererObj.trimStringToWidth(input.substring(scroll), visibleWidth);
                  int ghostX = textLeft + this.field_146297_k.fontRendererObj.getStringWidth(visibleInput) + 1;
                  int remainingWidth = textRight - ghostX;
                  if (remainingWidth > 0) {
                     String visibleSuffix = this.field_146297_k.fontRendererObj.trimStringToWidth(suffix, remainingWidth);
                     GlStateManager.enableBlend();
                     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                     this.field_146297_k.fontRendererObj.drawString(visibleSuffix, ghostX, textTop, -2005434998);
                     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                  }
               }
            }
         }
      }
   }

   @Inject(method = "sendAutocompleteRequest", at = @At("HEAD"), cancellable = true)
   private void handleClientCommandCompletion(String full, String ignored, CallbackInfo callbackInfo) {
      if (Jade.commandManager != null && Jade.commandManager.isCommandMessage(full)) {
         String[] suggestions = Jade.commandManager.getCompletions(full);
         if (suggestions.length != 0) {
            if (suggestions.length != 1 || !full.equalsIgnoreCase(suggestions[0])) {
               this.field_146414_r = true;
               this.onAutocompleteResponse(suggestions);
               callbackInfo.cancel();
            }
         }
      }
   }

   @Inject(method = "autocompletePlayerNames", at = @At("HEAD"), cancellable = true)
   private void handleCommandAutocomplete(CallbackInfo callbackInfo) {
      if (Jade.commandManager != null) {
         String input = this.field_146415_a.getText();
         if (!Jade.commandManager.isCommandMessage(input)) {
            this.jade$resetCommandCompletions();
         } else {
            String[] suggestions;
            if (this.jade$canCycleCurrentCompletion(input)) {
               suggestions = this.jade$commandCompletions;
               this.jade$commandCompletionIndex = (this.jade$commandCompletionIndex + 1) % suggestions.length;
            } else {
               suggestions = Jade.commandManager.getCompletions(input);
               if (suggestions.length == 0) {
                  this.jade$resetCommandCompletions();
                  callbackInfo.cancel();
                  return;
               }

               this.jade$commandCompletions = suggestions;
               this.jade$commandCompletionIndex = 0;
            }

            this.field_146415_a.setText(suggestions[this.jade$commandCompletionIndex]);
            this.field_146415_a.setCursorPositionEnd();
            callbackInfo.cancel();
         }
      }
   }

   @Unique
   private boolean jade$canCycleCurrentCompletion(String input) {
      if (this.jade$commandCompletions.length != 0 && this.jade$commandCompletionIndex >= 0) {
         for (String suggestion : this.jade$commandCompletions) {
            if (suggestion.equals(input)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   @Unique
   private void jade$resetCommandCompletions() {
      this.jade$commandCompletions = new String[0];
      this.jade$commandCompletionIndex = -1;
   }

   @Unique
   private String jade$getGhostSuggestionSuffix(String input) {
      String suggestion = Jade.commandManager.getFirstCompletion(input);
      if (!suggestion.isEmpty() && !suggestion.equalsIgnoreCase(input)) {
         return !suggestion.toLowerCase().startsWith(input.toLowerCase()) ? "" : suggestion.substring(input.length());
      } else {
         return "";
      }
   }
}
