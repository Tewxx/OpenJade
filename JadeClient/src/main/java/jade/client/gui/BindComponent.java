// Jade recovery: original class: jade.deps.eLz.bYDQTJxZ6h
package jade.client.gui;

import jade.client.Jade;
import jade.client.common.IFont;
import jade.client.common.ModuleComponent;
import jade.client.module.Category;
import jade.client.module.client.Gui;
import jade.client.setting.KeySetting;

public class BindComponent extends Component {
   private static final String NUrB = "/assets/jade/textures/gui/eye.png";
   private static final String ooC = "/assets/jade/textures/gui/eye_off.png";
   private static final int EYE_ICON_MARGIN = 2;
   public boolean listening;
   public ModuleComponent moduleComponent;
   public float Luxscs;
   public float panelX;
   private float rowY;
   public KeySetting keySetting;
   public float ecma6;
   private final KeyBindState PRGw = new KeyBindState(0);

   public BindComponent(ModuleComponent var1, float var2) {
      this.moduleComponent = var1;
      this.panelX = var1.categoryComponent.EWZxNc() + var1.categoryComponent.getPanelWidth();
      this.rowY = var1.categoryComponent.getPanelY() + var1.HPu;
      this.Luxscs = var2;
   }

   public BindComponent(ModuleComponent var1, KeySetting var2, float var3) {
      this.moduleComponent = var1;
      this.panelX = var1.categoryComponent.EWZxNc() + var1.categoryComponent.getPanelWidth();
      this.rowY = var1.categoryComponent.getPanelY() + var1.HPu;
      this.keySetting = var2;
      this.Luxscs = var3;
   }

   @Override
   public void setY(float var1) {
      this.Luxscs = var1;
   }

   @Override
   public float getWidth() {
      return this.Luxscs;
   }

   @Override
   public boolean ejbAn() {
      return this.keySetting == null || this.keySetting.visible;
   }

   @Override
   public void renderComponent() {
      BindLabelRenderer.drawBindLabel(this.moduleComponent, this.keySetting, this.listening, this.Luxscs, this.ecma6);
   }

   @Override
   public void updateLayout(int var1, int var2) {
      this.rowY = this.moduleComponent.categoryComponent.WHmq() + this.Luxscs;
      this.panelX = this.moduleComponent.categoryComponent.EWZxNc();
   }

   @Override
   public boolean mouseClicked(int var1, int var2, int var3) {
      if (this.isRowHovered(var1, var2) && this.moduleComponent.Nqe && this.moduleComponent.isComponentVisible(this)) {
         if (var3 == 0 && this.moduleComponent.module.getCategory() != Category.profiles && this.PaVobS(var1, var2)) {
            this.moduleComponent.module.setHidden(!this.moduleComponent.module.isHidden());
            if (Jade.Grq != null) {
               Jade.Grq.getProfile().unmodified = false;
            }

            return true;
         } else if (this.moduleComponent.module.canBeEnabled() && var3 == 0 && this.txP7(var1, var2)) {
            this.PRGw.setListening(this.listening);
            this.listening = this.PRGw.gduzp();
            return true;
         } else if (this.moduleComponent.module.canBeEnabled() && var3 > 1 && this.listening) {
            int var4 = KeyBindState.toScrollKeycode(var3);
            if (this.keySetting != null) {
               this.keySetting.CvUpqo(KeySetting.combineWithHeldModifiers(var4));
            } else {
               this.moduleComponent.module.setKeycode(var4);
            }

            if (Jade.Grq != null) {
               Jade.Grq.getProfile().unmodified = false;
            }

            this.resetListeningState();
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean PaVobS(int var1, int var2) {
      int var3 = this.getIconHalfHeight();
      float var4 = this.computeIconX(var3);
      float var5 = this.computeIconY(var3);
      return YXWjGcywLT.isPointInRect(var1, var2, var4, var5, var3, var3);
   }

   private float computeLabelX() {
      return YXWjGcywLT.textCenterX(this.moduleComponent.categoryComponent.EWZxNc(), this.ecma6);
   }

   private float computeBindTextY() {
      return YXWjGcywLT.textBaselineY(this.moduleComponent.categoryComponent.WHmq(), this.Luxscs, this.keySetting == null);
   }

   private float computeHeaderTextY() {
      return YXWjGcywLT.textBaselineY(this.moduleComponent.categoryComponent.getPanelY(), this.Luxscs, this.keySetting == null);
   }

   private String getBindLabel() {
      return KeybindText.getBindDisplayText(this.moduleComponent.module, this.keySetting, this.listening);
   }

   private boolean txP7(int var1, int var2) {
      String var3 = this.getBindLabel();
      IFont var4 = Gui.getSettingFont();
      float var5 = this.computeLabelX();
      float var6 = this.computeBindTextY();
      float var7 = var4.getStringWidth(var3) * 0.5F;
      float var8 = var4.getFontHeight() * 0.5F;
      return YXWjGcywLT.isPointInRect(var1, var2, var5, var6, var7, var8);
   }

   private int getIconHalfHeight() {
      return YXWjGcywLT.LyqqeVh(Gui.getSettingFont().getFontHeight() * 0.5F);
   }

   private float computeIconX(int var1) {
      return YXWjGcywLT.rightAlignedX(this.moduleComponent.categoryComponent.EWZxNc(), this.moduleComponent.categoryComponent.getPanelWidth(), var1, 2);
   }

   private float computeIconY(int var1) {
      float var2 = this.computeBindTextY();
      float var3 = Gui.getSettingFont().getFontHeight() * 0.5F;
      return YXWjGcywLT.centeredY(var2, var3, var1);
   }

   @Override
   public void keyPressed(int var1) {
      if (this.listening && var1 != 0) {
         int var2 = KeyBindState.scrollDeltaToKeycode(var1);
         if (this.keySetting != null) {
            this.keySetting.CvUpqo(KeySetting.combineWithHeldModifiers(var2));
         } else {
            this.moduleComponent.module.setKeycode(var2);
         }

         if (Jade.Grq != null) {
            Jade.Grq.getProfile().unmodified = false;
         }

         this.resetListeningState();
      }
   }

   @Override
   public void keyTyped(char var1, int var2) {
      if (this.listening) {
         this.PRGw.setListening(true);
         if (var2 != 11 && var2 != 1) {
            if (this.keySetting != null && KeySetting.isModifierKey(var2)) {
               this.PRGw.setCapturedKey(var2);
               return;
            }

            if (this.keySetting != null) {
               this.keySetting.CvUpqo(KeySetting.SDwL(var2));
            } else {
               this.moduleComponent.module.setKeycode(var2);
            }
         } else if (this.moduleComponent.module instanceof Gui) {
            this.moduleComponent.module.setKeycode(25);
         } else if (this.keySetting != null) {
            this.keySetting.setKeyCode(0);
         } else {
            this.moduleComponent.module.setKeycode(0);
         }

         if (Jade.Grq != null) {
            Jade.Grq.getProfile().unmodified = false;
         }

         this.resetListeningState();
      }
   }

   public void assignScrollKey(int var1) {
      if (this.listening && this.keySetting != null && this.PRGw.matchesCapturedKey(var1)) {
         this.keySetting.setKeyCode(var1);
         if (Jade.Grq != null) {
            Jade.Grq.getProfile().unmodified = false;
         }

         this.resetListeningState();
      }
   }

   public boolean isRowHovered(int var1, int var2) {
      float var3 = this.moduleComponent.categoryComponent.EWZxNc();
      float var4 = this.moduleComponent.categoryComponent.WHmq() + this.Luxscs;
      float var5 = this.moduleComponent.categoryComponent.getPanelWidth();
      return YXWjGcywLT.IGGd(var1, var2, var3, var4, var5);
   }

   public String CRyJ(boolean var1) {
      return var1 ? this.keySetting.ukYzs() : KeybindText.getKeyName(this.moduleComponent.module.getKeycode());
   }

   public String getScrollKeyName(int var1) {
      return KeyBindState.getScrollKeyName(var1);
   }

   @Override
   public float getHeight() {
      return this.keySetting != null ? 0.0F : 16.0F;
   }

   @Override
   public int getHeightRounded() {
      return Math.round(this.getHeight());
   }

   @Override
   public void resetEditingState() {
      this.resetListeningState();
   }

   private void resetListeningState() {
      this.PRGw.NcyUqh();
      this.listening = false;
   }
}
