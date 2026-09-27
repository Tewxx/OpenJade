// Jade recovery: recovered class name: MixinFontRenderer; mixin target: net.minecraft.client.gui.FontRenderer; original class: jade.mixin.impl.render.M190f3a94d678fc6d891b3092014141fe
package jade.mixin.impl.render;

import jade.client.common.ChatUtils;
import jade.client.common.EXQhDjf8;
import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FontRenderer.class)
public abstract class MixinFontRenderer {
   @Shadow
   protected float field_78295_j;
   @Shadow
   protected float field_78296_k;
   @Shadow
   private int field_78304_r;

   @ModifyVariable(method = "renderString", at = @At("HEAD"), require = 1, ordinal = 0, argsOnly = true)
   private String renderString(String string) {
      return this.prepareBaseString(string);
   }

   @Inject(method = "drawString(Ljava/lang/String;FFIZ)I", at = @At("HEAD"), cancellable = true)
   private void jade$interceptGradient(String text, float x, float y, int color, boolean shadow, CallbackInfoReturnable<Integer> cir) {
      if (text != null) {
         if (text.length() >= 4 && ChatUtils.hasCachedText() && !ChatUtils.uDc8() && !EXQhDjf8.isRenderingGlyphs()) {
            String rawText = ChatUtils.getOriginalFormattedText(text);
            if (rawText == null && ChatUtils.isRenderingChat()) {
               rawText = ChatUtils.getRegisteredFormatting(text);
            }

            if (rawText != null && !ChatUtils.hasObfuscatedCode(rawText)) {
               ChatUtils.setCustomDrawActive(true);

               try {
                  cir.setReturnValue(ChatUtils.UCYxg((FontRenderer)(Object)this, rawText, x, y, color, shadow));
               } finally {
                  ChatUtils.setCustomDrawActive(false);
               }

               return;
            }
         }

         if (!EXQhDjf8.isRenderingGlyphs() && EXQhDjf8.TQHTc3(text)) {
            cir.setReturnValue(EXQhDjf8.drawString((FontRenderer)(Object)this, text, x, y, color, shadow));
         }
      }
   }

   @Inject(method = "getStringWidth", at = @At("HEAD"), cancellable = true)
   private void jade$measureStarGlyphs(String text, CallbackInfoReturnable<Integer> cir) {
      if (!EXQhDjf8.isRenderingGlyphs() && EXQhDjf8.TQHTc3(text)) {
         cir.setReturnValue(EXQhDjf8.getStringWidth((FontRenderer)(Object)this, text));
      }
   }

   @Inject(method = "renderUnicodeChar", at = @At("HEAD"), cancellable = true)
   private void jade$renderUnicodeStar(char character, boolean italic, CallbackInfoReturnable<Float> cir) {
      if (EXQhDjf8.isSupportedGlyph(character)) {
         cir.setReturnValue(EXQhDjf8.MUMCHwh((FontRenderer)(Object)this, character, this.field_78295_j, this.field_78296_k, this.field_78304_r));
      }
   }

   @Inject(method = "getCharWidth", at = @At("HEAD"), cancellable = true)
   private void jade$measureUnicodeStar(char character, CallbackInfoReturnable<Integer> cir) {
      if (EXQhDjf8.isSupportedGlyph(character)) {
         cir.setReturnValue(EXQhDjf8.getGlyphSize((FontRenderer)(Object)this, false));
      }
   }

   private String prepareBaseString(String string) {
      return string;
   }
}
