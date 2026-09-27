// Jade recovery: module: Notifications (render); original class: jade.deps.eLz.n7HXWJlFWs
package jade.client.module.render;

import jade.client.common.ClientUtils;
import jade.client.common.EventPhase;
import jade.client.common.ExternalRenderBuffer;
import jade.client.common.ExternalRenderer;
import jade.client.common.FontManager;
import jade.client.common.IFont;
import jade.client.common.RenderUtils;
import jade.client.common.RoundedRect;
import jade.client.common.Subscribe;
import jade.client.common.ExternalRenderableModule;
import jade.client.event.RenderTickEvent;
import jade.client.event.AnticheatFlagEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.client.Gui;
import jade.client.module.render.notifications.NotificationStyle;
import jade.client.module.shared.FormattedTextRenderer;
import jade.client.module.shared.ModuleToggleListener;
import jade.client.setting.BooleanSetting;
import jade.client.setting.FontSetting;
import jade.client.setting.MultiSelectSetting;
import jade.client.setting.SliderSetting;
import jade.deps.gson.JsonObject;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

@ModuleInfo
public class Notifications extends Module implements ModuleToggleListener, ExternalRenderableModule {
   private static final int iezct = 5;
   private static final float cpd = 1.2F;
   private static final float DEFAULT_ANCHOR_X_RATIO = 0.985F;
   private static final float DEFAULT_ANCHOR_Y_RATIO = 0.985F;
   private static final float SLIDE_DISTANCE = 22.0F;
   private static final float MIN_VISIBLE_ALPHA = 0.02F;
   private static final long ALERT_COOLDOWN_MS = 10000L;
   private static final int CLASSIC_BACKGROUND_COLOR = new Color(
         12, 12, 14, 185
      )
      .getRGB();
   private static final int MODERN_BACKGROUND_COLOR = new Color(
         9,
         10,
         13,
         205
      )
      .getRGB();
   private static final int HtU = 42;
   private static final int ZAd = new Color(
         248, 248, 248, 255
      )
      .getRGB();
   private static final int UiR = new Color(
         188,
         194,
         206,
         255
      )
      .getRGB();
   private static final int PSYs7 = new Color(
         76,
         215,
         120
      )
      .getRGB();
   private static final int ERROR_COLOR = new Color(
         239,
         83,
         80
      )
      .getRGB();
   private final LinkedList<Notifications$3> linkedList = new LinkedList<>();
   private final Map<String, Long> alertCooldowns = new HashMap<>();
   private long lastFrameTime = System.currentTimeMillis();
   private static boolean suppressed;
   private SliderSetting style;
   private SliderSetting scale;
   private SliderSetting backgroundTransparency;
   private SliderSetting rounding;
   private FontSetting font;
   private MultiSelectSetting multiSelectSetting;
   private BooleanSetting moduleToggles;
   private BooleanSetting configToggles;
   private BooleanSetting anticheatAlerts;
   private BooleanSetting lowercase;
   private static float qawFl = 0.985F;
   private static float anchorRatioY = 0.985F;
   private static float anchorX;
   private static float OGVld;

   public Notifications() {
      super("Notifications", Category.render);
      this.registerSetting(this.style = new SliderSetting("Style", NotificationStyle.CLASSIC.ordinal(), NotificationStyle.labels()));
      this.registerSetting(this.scale = new SliderSetting("Scale", 1.0, 0.5, 2.15, 0.05));
      this.scale.visible = false;
      this.registerSetting(this.rounding = new SliderSetting("Rounding", 5.0, 0.0, 12.0, 0.5));
      this.registerSetting(this.font = new FontSetting("Font", "Modern"));
      this.moduleToggles = new BooleanSetting(
         "Module Toggles", true
      );
      this.configToggles = new BooleanSetting(
         "Config Toggles", true
      );
      this.anticheatAlerts = new BooleanSetting(
         "Anticheat Alerts", true
      );
      String var10004 = "Notify On";
      BooleanSetting[] var10005 = new BooleanSetting[3];
      var10005[0] = this.moduleToggles;
      var10005[1] = this.configToggles;
      var10005[2] = this.anticheatAlerts;
      this.registerSetting(this.multiSelectSetting = new MultiSelectSetting(var10004, var10005));
      this.moduleToggles.visible = false;
      this.configToggles.visible = false;
      this.anticheatAlerts.visible = false;
      this.registerSetting(this.moduleToggles);
      this.registerSetting(this.configToggles);
      this.registerSetting(this.anticheatAlerts);
      this.registerSetting(
         this.lowercase = new BooleanSetting(
            "Lowercase", false
         )
      );
      this.registerSetting(this.backgroundTransparency = new SliderSetting("Background transparency", 185.0, 0.0, 255.0, 5.0));
   }

   public SliderSetting getScale() {
      return this.scale;
   }

   private IFont resolveNotificationFont() {
      return FontManager.getHudRenderer(this.font.getResolvedFontName(), 1.0F);
   }

   public void applyConfigDefaults(JsonObject var1) {
      if (var1 != null) {
         if (this.style != null && !var1.has(this.style.getPath()) && !var1.has(this.style.getName())) {
            this.style.setValueClamped(0.0);
         }

         if (this.moduleToggles != null && !var1.has(this.moduleToggles.getPath()) && !var1.has(this.moduleToggles.getName())) {
            this.moduleToggles.setToggled(true);
         }

         if (this.configToggles != null && !var1.has(this.configToggles.getPath()) && !var1.has(this.configToggles.getName())) {
            this.configToggles.setToggled(true);
         }

         if (this.anticheatAlerts != null && !var1.has(this.anticheatAlerts.getPath()) && !var1.has(this.anticheatAlerts.getName())) {
            this.anticheatAlerts.setToggled(true);
         }
      }
   }

   private String HjeQg(Module var1) {
      String var2 = var1.getNameInHud();
      if (this.lowercase != null && this.lowercase.isToggled()) {
         var2 = var2.toLowerCase();
      }

      return var2;
   }

   private String formatToggleState(boolean var1) {
      String var2 = var1 ? "Enabled" : "Disabled";
      if (this.lowercase != null && this.lowercase.isToggled()) {
         var2 = var2.toLowerCase();
      }

      return var2;
   }

   private String formatModuleToggleText(boolean var1) {
      String var2 = "Module " + (var1 ? "Enabled" : "Disabled");
      if (this.lowercase != null && this.lowercase.isToggled()) {
         var2 = var2.toLowerCase();
      }

      return var2;
   }

   public void notifyModuleToggle(Module var1, boolean var2) {
      if (!suppressed && this.isModuleNotificationEnabled() && this.isEnabled() && var1 != this && !(var1 instanceof Arraylist) && !(var1 instanceof Gui)) {
         if (mc != null && mc.thePlayer != null && mc.theWorld != null) {
            applyAnchorRatios(new ScaledResolution(mc));
            this.pushNotification(new Notifications$3(this.HjeQg(var1), var2, System.currentTimeMillis(), OGVld));
         }
      }
   }

   @Override
   public void onModuleToggled(Module var1, boolean var2) {
      this.notifyModuleToggle(var1, var2);
   }

   public void Xybu(String var1) {
      if (this.Xij9() && this.isEnabled()) {
         if (mc != null && mc.thePlayer != null && mc.theWorld != null) {
            String var2 = var1 != null && !var1.trim().isEmpty() ? var1.trim() : "Config";
            if (this.lowercase != null && this.lowercase.isToggled()) {
               var2 = var2.toLowerCase();
            }

            applyAnchorRatios(new ScaledResolution(mc));
            this.pushNotification(Notifications$3.NCpF(var2, System.currentTimeMillis(), OGVld));
         }
      }
   }

   @Subscribe
   public void onAnticheatFlag(AnticheatFlagEvent var1) {
      if (this.isEnabled() && this.isAnticheatAlertEnabled() && var1 != null && var1.entity != null) {
         if (mc != null && mc.thePlayer != null && mc.theWorld != null) {
            long var2 = System.currentTimeMillis();
            String var4 = var1.checkName != null && !var1.checkName.trim().isEmpty() ? var1.checkName.trim() : "Unknown";
            String var5 = var1.entity.getName() != null && !var1.entity.getName().trim().isEmpty() ? var1.entity.getName().trim() : "Player";
            String var6 = var1.entity.getDisplayName() == null ? var5 : var1.entity.getDisplayName().getFormattedText();
            UUID var7 = var1.entity.getUniqueID();
            String var8 = var7 + "|" + var4.toLowerCase();
            Long var9 = this.alertCooldowns.get(var8);
            if (var9 == null || var2 - var9 >= 10000L) {
               this.alertCooldowns.put(var8, var2);
               applyAnchorRatios(new ScaledResolution(mc));
               this.pushNotification(Notifications$3.createCheatAlert(var6, var4, var1.wnsTt, var2, OGVld));
            }
         }
      }
   }

   public static void setNotificationsSuppressed(boolean var0) {
      suppressed = var0;
   }

   private void pushNotification(Notifications$3 var1) {
      synchronized (this.linkedList) {
         this.linkedList.addFirst(var1);

         while (this.linkedList.size() > 5) {
            this.linkedList.removeLast();
         }
      }
   }

   private boolean isModuleNotificationEnabled() {
      return this.moduleToggles == null || this.moduleToggles.isToggled();
   }

   private boolean Xij9() {
      return this.configToggles == null || this.configToggles.isToggled();
   }

   private boolean isAnticheatAlertEnabled() {
      return this.anticheatAlerts == null || this.anticheatAlerts.isToggled();
   }

   @Override
   public void onDisable() {
      ExternalRenderer.invalidateExternalFrame();
      synchronized (this.linkedList) {
         this.linkedList.clear();
      }

      this.alertCooldowns.clear();
   }

   @Subscribe
   public void onRenderTick(RenderTickEvent var1) {
      if (var1.eventPhase == EventPhase.END && mc != null) {
         long var2 = System.currentTimeMillis();
         float var4 = Math.min(0.05F, Math.max(0.0F, (float)(var2 - this.lastFrameTime) / 1000.0F));
         this.lastFrameTime = var2;
         if (!mc.gameSettings.showDebugInfo) {
            ArrayList var5;
            synchronized (this.linkedList) {
               if (this.linkedList.isEmpty()) {
                  return;
               }

               var5 = new ArrayList<>(this.linkedList);
            }

            ScaledResolution var31 = new ScaledResolution(mc);
            applyAnchorRatios(var31);
            IFont var7 = this.resolveNotificationFont();
            boolean var8 = this.isTopHalf(var31);
            float var9 = OGVld;
            long var10 = 1200L;

            for (Notifications$3 var13 : (java.lang.Iterable<Notifications$3>) (java.lang.Iterable<?>) (var5)) {
               boolean var14 = var2 - Notifications$3.dL21(var13) >= var10;
               float var15 = var14 ? 0.0F : 1.0F;
               Notifications$3.QERbH(var13, exponentialSmooth(Notifications$3.getVisibilityProgress(var13), var15, var4, 14.0F));
               if (Notifications$3.getVisibilityProgress(var13) <= 0.02F && var14) {
                  synchronized (this.linkedList) {
                     this.linkedList.remove(var13);
                  }
               } else {
                  Notifications$2 var16 = this.computeCardLayout(var7, var13);
                  float var17 = this.getRenderScale();
                  float var18 = Notifications$2.getWidth(var16) * var17;
                  float var19 = Notifications$2.getHeight(var16) * var17;
                  boolean var20 = this.isRightHalf(var31);
                  float var21 = easeOutCubic(Notifications$3.getVisibilityProgress(var13));
                  float var22 = var8 ? var9 : var9 - var19;
                  Notifications$3.setPositionY(var13, exponentialSmooth(Notifications$3.getPositionY(var13), var22, var4, 12.0F));
                  float var23 = this.TWdW(var18, var31, var20);
                  float var24 = (1.0F - var21) * this.scaleForStyle(22.0F) * var17;
                  float var25 = var20 ? var23 + var24 : var23 - var24;
                  int var26 = Math.min(255, Math.max(0, Math.round(255.0F * var21)));
                  float var27 = 1.0F - Math.min(1.0F, Math.max(0.0F, (float)(var2 - Notifications$3.dL21(var13)) / (float)Math.max(1L, var10)));
                  if (this.VIuQ()) {
                     this.XYuOp(var7, var13, var16, var25, Notifications$3.getPositionY(var13), var26, var27);
                  } else {
                     this.LLIMkA6(var7, var13, var16, var25, Notifications$3.getPositionY(var13), var26, var27);
                  }

                  float var28 = (var19 + Notifications$2.getStackSpacing(var16) * var17) * var21;
                  var9 += var8 ? var28 : -var28;
               }
            }
         }
      }
   }

   @Override
   public void guiUpdate() {
      this.font.setVisible(true, this);
   }

   private void XYuOp(IFont var1, Notifications$3 var2, Notifications$2 var3, float var4, float var5, int var6, float var7) {
      ExternalRenderBuffer var8 = ExternalRenderer.getActiveRenderBuffer();
      if (var8 != null) {
         float var9 = new ScaledResolution(mc).getScaleFactor();
         float var10 = this.getRenderScale() * var9;
         var4 *= var9;
         var5 *= var9;
         boolean var11 = this.isModernCardStyle(var2);
         var8.fillRoundedRect(
            var4,
            var5,
            var4 + Notifications$2.getWidth(var3) * var10,
            var5 + Notifications$2.getHeight(var3) * var10,
            ClientUtils.YVVZ(var11 ? MODERN_BACKGROUND_COLOR : CLASSIC_BACKGROUND_COLOR, Math.min(this.getBackgroundAlpha(), var6)),
            ExternalRenderBuffer.XNfyt(Notifications$2.nbvZi(var3), var10)
         );
         int var12 = ClientUtils.YVVZ(ZAd, var6);
         if (!var11) {
            String var23 = Notifications$3.isCompact(var2)
               ? Notifications$3.getTitle(var2)
               : (Notifications$3.pokb5(var2) ? Notifications$3.getAlertTitle(var2) : Notifications$3.getTitle(var2));
            FormattedTextRenderer.drawTextAtHeight(
               var8,
               var1,
               var23,
               var4 + Notifications$2.JVPFY(var3) * var10,
               var5 + Notifications$2.getTextOffsetY(var3) * var10,
               var1.getFontHeight() * var10,
               var12,
               true,
               var1.getStringWidth(var23) * var10
            );
            if (!Notifications$3.isCompact(var2)) {
               String var24 = Notifications$3.pokb5(var2) ? Notifications$3.getStatusText(var2) : this.formatToggleState(Notifications$3.isModuleEnabled(var2));
               FormattedTextRenderer.drawTextAtHeight(
                  var8,
                  var1,
                  var24,
                  var4 + (Notifications$2.JVPFY(var3) + Notifications$2.getPrimaryTextWidth(var3) + Notifications$2.getStatusGap(var3)) * var10,
                  var5 + Notifications$2.getTextOffsetY(var3) * var10,
                  var1.getFontHeight() * var10,
                  ClientUtils.YVVZ(Notifications$3.isModuleEnabled(var2) ? PSYs7 : ERROR_COLOR, var6),
                  true,
                  var1.getStringWidth(var24) * var10
               );
            }
         } else {
            int var13 = Arraylist.xQec0(Notifications$3.isModuleEnabled(var2) ? 0.0 : 24.0);
            int var14 = ClientUtils.YVVZ(var13, var6);
            float var15 = var4 + Notifications$2.JVPFY(var3) * var10;
            float var16 = var5 + (Notifications$2.getHeight(var3) - Notifications$2.getIconSize(var3)) * var10 * 0.5F;
            float var17 = Notifications$2.getIconSize(var3) * var10;
            var8.fillRoundedRect(
               var15, var16, var15 + var17, var16 + var17, ClientUtils.YVVZ(var13, Math.min(42, var6)), ExternalRenderBuffer.XNfyt(Notifications$2.getIconSize(var3) * 0.5F, var10)
            );
            float var18 = Math.max(1.35F, this.scaleForStyle(1.9F)) * var10;
            if (Notifications$3.isCompact(var2)) {
               var8.drawLine(var15 + var17 * 0.5, var16 + var17 * 0.25, var15 + var17 * 0.5, var16 + var17 * 0.57, var14, var18);
               var8.fillRoundedRect(var15 + var17 * 0.45, var16 + var17 * 0.7, var15 + var17 * 0.55, var16 + var17 * 0.8, var14, var18);
            } else if (!Notifications$3.isModuleEnabled(var2) && !Notifications$3.pokb5(var2)) {
               var8.drawLine(var15 + var17 * 0.3, var16 + var17 * 0.3, var15 + var17 * 0.7, var16 + var17 * 0.7, var14, var18);
               var8.drawLine(var15 + var17 * 0.7, var16 + var17 * 0.3, var15 + var17 * 0.3, var16 + var17 * 0.7, var14, var18);
            } else {
               var8.drawLine(var15 + var17 * 0.25, var16 + var17 * 0.5, var15 + var17 * 0.43, var16 + var17 * 0.68, var14, var18);
               var8.drawLine(var15 + var17 * 0.43, var16 + var17 * 0.68, var15 + var17 * 0.76, var16 + var17 * 0.32, var14, var18);
            }

            float var19 = var15 + var17 + Notifications$2.getStatusGap(var3) * var10;
            String var20 = this.getNotificationTitle(var2);
            FormattedTextRenderer.drawTextAtHeight(
               var8,
               var1,
               var20,
               var19,
               var5 + Notifications$2.FMvPq6(var3) * var10,
               var1.getFontHeight() * var10,
               var12,
               true,
               var1.getStringWidth(var20) * var10
            );
            FormattedTextRenderer.drawTextAtHeight(
               var8,
               var1,
               Notifications$3.getStatusText(var2),
               var19,
               var5 + Notifications$2.getStatusTextOffsetY(var3) * var10,
               var1.getFontHeight() * var10,
               ClientUtils.YVVZ(UiR, var6),
               true,
               var1.getStringWidth(Notifications$3.getStatusText(var2)) * var10
            );
            var8.fillRoundedRect(
               var4 + Notifications$2.JVPFY(var3) * var10,
               var5 + (Notifications$2.getHeight(var3) - 3.0F) * var10,
               var4 + (Notifications$2.JVPFY(var3) + (Notifications$2.getWidth(var3) - 2.0F * Notifications$2.JVPFY(var3)) * var7) * var10,
               var5 + (Notifications$2.getHeight(var3) - 2.0F) * var10,
               var14,
               0.5F * var10
            );
         }
      }
   }

   private void LLIMkA6(IFont var1, Notifications$3 var2, Notifications$2 var3, float var4, float var5, int var6, float var7) {
      float var8 = this.getRenderScale();
      GL11.glPushMatrix();
      GL11.glTranslatef(var4, var5, 0.0F);
      GL11.glScalef(var8, var8, 1.0F);
      this.renderNotificationCard(var1, var2, var3, 0.0F, 0.0F, var6, var7);
      GL11.glPopMatrix();
   }

   private void renderNotificationCard(IFont var1, Notifications$3 var2, Notifications$2 var3, float var4, float var5, int var6, float var7) {
      if (this.isModernCardStyle(var2)) {
         this.renderModernStyleCard(var1, var2, var3, var4, var5, var6, var7);
      } else {
         int var8 = Notifications$3.isModuleEnabled(var2) ? PSYs7 : ERROR_COLOR;
         int var9 = ClientUtils.YVVZ(CLASSIC_BACKGROUND_COLOR, Math.min(this.getBackgroundAlpha(), var6));
         int var10 = ClientUtils.YVVZ(var8, var6);
         if (Notifications$3.isCompact(var2)) {
            RenderUtils.jxyoE(var4, var5, var4 + Notifications$2.getWidth(var3), var5 + Notifications$2.getHeight(var3), Notifications$2.nbvZi(var3), var9);
            this.JYHc(var1, Notifications$3.getTitle(var2), var4 + Notifications$2.JVPFY(var3), var5 + Notifications$2.getTextOffsetY(var3), ClientUtils.YVVZ(ZAd, var6), true);
         } else {
            String var11 = Notifications$3.pokb5(var2) ? Notifications$3.getStatusText(var2) : this.formatToggleState(Notifications$3.isModuleEnabled(var2));
            RenderUtils.jxyoE(var4, var5, var4 + Notifications$2.getWidth(var3), var5 + Notifications$2.getHeight(var3), Notifications$2.nbvZi(var3), var9);
            float var12 = var4 + Notifications$2.JVPFY(var3);
            float var13 = var12 + Notifications$2.getPrimaryTextWidth(var3) + Notifications$2.getStatusGap(var3);
            this.JYHc(
               var1,
               Notifications$3.pokb5(var2) ? Notifications$3.getAlertTitle(var2) : Notifications$3.getTitle(var2),
               var12,
               var5 + Notifications$2.getTextOffsetY(var3),
               ClientUtils.YVVZ(ZAd, var6),
               true
            );
            this.JYHc(var1, var11, var13, var5 + Notifications$2.getTextOffsetY(var3), var10, true);
         }
      }
   }

   private void renderModernStyleCard(IFont var1, Notifications$3 var2, Notifications$2 var3, float var4, float var5, int var6, float var7) {
      int var8 = ClientUtils.YVVZ(MODERN_BACKGROUND_COLOR, Math.min(this.getBackgroundAlpha(), var6));
      int var9 = Arraylist.xQec0(var5 * 0.45 + (Notifications$3.isModuleEnabled(var2) ? 0.0 : 24.0));
      int var10 = ClientUtils.YVVZ(var9, Math.min(42, var6));
      int var11 = ClientUtils.YVVZ(var9, var6);
      RenderUtils.jxyoE(var4, var5, var4 + Notifications$2.getWidth(var3), var5 + Notifications$2.getHeight(var3), Notifications$2.nbvZi(var3), var8);
      float var12 = Notifications$2.getIconSize(var3);
      float var13 = var4 + Notifications$2.JVPFY(var3);
      float var14 = var5 + (Notifications$2.getHeight(var3) - var12) * 0.5F;
      RenderUtils.jxyoE(var13, var14, var13 + var12, var14 + var12, var12 * 0.5F, var10);
      int var15 = ClientUtils.YVVZ(scaleRgb(var9, 0.38F), Math.min(var6, 175));
      float var16 = Math.max(1.35F, this.scaleForStyle(1.9F));
      drawNotificationIcon(var2, var13 + this.scaleForStyle(0.8F), var14 + this.scaleForStyle(0.8F), var12, var15, var16 + this.scaleForStyle(1.1F));
      drawNotificationIcon(var2, var13, var14, var12, var11, var16);
      float var17 = var13 + var12 + Notifications$2.getStatusGap(var3);
      int var18 = ClientUtils.YVVZ(ZAd, var6);
      if (Notifications$3.isCompact(var2)) {
         this.Oa27(var1, this.getNotificationTitle(var2), var17, var5 + Notifications$2.FMvPq6(var3), var18);
      } else {
         this.JYHc(var1, this.getNotificationTitle(var2), var17, var5 + Notifications$2.FMvPq6(var3), var18, true);
      }

      this.JYHc(var1, Notifications$3.getStatusText(var2), var17, var5 + Notifications$2.getStatusTextOffsetY(var3), ClientUtils.YVVZ(UiR, var6), true);
      this.drawProgressBar(var4, var5, var3, var6, var7, Notifications$3.isModuleEnabled(var2));
   }

   private Notifications$2 computeCardLayout(IFont var1, Notifications$3 var2) {
      int var3 = Math.max(8, var1.getFontHeight());
      if (this.isModernCardStyle(var2)) {
         float var17 = this.scaleForStyle(10.0F);
         float var18 = this.scaleForStyle(6.0F);
         float var19 = this.scaleForStyle(2.0F);
         float var20 = var3 * 2.0F + var19;
         float var21 = Math.max(this.scaleForStyle(19.0F), var20 * 0.72F);
         float var22 = this.scaleForStyle(8.0F);
         float var23 = Notifications$3.pokb5(var2)
            ? var1.getStringWidth(this.getNotificationTitle(var2))
            : Math.max(var1.getStringWidth(this.formatModuleToggleText(true)), var1.getStringWidth(this.formatModuleToggleText(false)));
         float var11 = var1.getStringWidth(Notifications$3.getStatusText(var2));
         float var12 = Math.max(var23, var11);
         float var13 = Math.max(this.scaleForStyle(164.0F), var17 * 2.0F + var21 + var22 + var12);
         float var14 = Math.max(this.scaleForStyle(42.0F), Math.max(var18 * 2.0F + var20, var21 + var18 * 2.0F));
         float var15 = (var14 - var20) * 0.5F;
         float var16 = var15 + var3 + var19;
         return new Notifications$2(
            var13, var14, this.scaleForStyle(5.0F), this.scaleForStyle((float)Math.max(0.0, this.rounding.getInput())), var17, var18, var11, var22, var21, var15, var16
         );
      } else {
         float var4 = this.scaleForStyle(10.0F);
         float var5 = this.scaleForStyle(5.0F);
         float var6 = var1.getStringWidth(
            Notifications$3.isCompact(var2) ? Notifications$3.getTitle(var2) : (Notifications$3.pokb5(var2) ? Notifications$3.getAlertTitle(var2) : Notifications$3.getTitle(var2))
         );
         float var7 = this.scaleForStyle(6.0F);
         float var8 = Notifications$3.isCompact(var2) ? 0.0F : var1.getStringWidth(Notifications$3.pokb5(var2) ? Notifications$3.getStatusText(var2) : this.formatToggleState(false));
         float var9 = Math.max(this.scaleForStyle(150.0F), var4 * 2.0F + var6 + (Notifications$3.isCompact(var2) ? 0.0F : var7) + var8);
         float var10 = var5 * 2.0F + var3;
         return new Notifications$2(var9, var10, this.scaleForStyle(5.0F), this.scaleForStyle((float)Math.max(0.0, this.rounding.getInput())), var4, var5, var6, var7, 0.0F, var5, var5);
      }
   }

   private boolean isModernStyle() {
      return this.style != null && NotificationStyle.fromSetting(this.style.getInput()) == NotificationStyle.MODERN;
   }

   private boolean isModernCardStyle(Notifications$3 var1) {
      return this.isModernStyle();
   }

   private boolean isRightHalf(ScaledResolution var1) {
      return anchorX >= var1.getScaledWidth() * 0.5F;
   }

   private float TWdW(float var1, ScaledResolution var2, boolean var3) {
      float var4 = Math.max(0.0F, Math.min(var2.getScaledWidth() - 1.0F, anchorX));
      return var3 ? Math.max(0.0F, var4 - var1) : Math.min(var4, var2.getScaledWidth() - var1);
   }

   private boolean isTopHalf(ScaledResolution var1) {
      return OGVld <= var1.getScaledHeight() * 0.5F;
   }

   private float dPht2() {
      return 0.63F;
   }

   private float getRenderScale() {
      return this.scale == null ? 1.0F : (float)Math.max(0.5, Math.min(2.15, this.scale.getInput()));
   }

   private float scaleForStyle(float var1) {
      return var1 * this.dPht2();
   }

   private float getShadowOffset() {
      return 0.5F;
   }

   private int getBackgroundAlpha() {
      return this.backgroundTransparency == null ? 185 : (int)Math.max(0.0, Math.min(255.0, this.backgroundTransparency.getInput()));
   }

   public static void setAnchorRatio(float var0, float var1) {
      qawFl = var0;
      anchorRatioY = var1;
      applyAnchorRatios(new ScaledResolution(mc));
   }

   private static void applyAnchorRatios(ScaledResolution var0) {
      anchorX = qawFl * var0.getScaledWidth();
      OGVld = anchorRatioY * var0.getScaledHeight();
   }

   public static void setAnchorPositionForCurrentScreen(float var0, float var1) {
      setAnchorPosition(var0, var1, new ScaledResolution(mc));
   }

   public static float getScreenAnchorX() {
      applyAnchorRatios(new ScaledResolution(mc));
      return anchorX;
   }

   public static float getScreenAnchorY() {
      applyAnchorRatios(new ScaledResolution(mc));
      return OGVld;
   }

   public static float DvQm() {
      return qawFl;
   }

   public static float getAnchorRatioY() {
      return anchorRatioY;
   }

   public static void resetAnchorPosition() {
      setAnchorRatio(0.985F, 0.985F);
   }

   public float[] VWNVw(float var1, float var2) {
      ScaledResolution var3 = new ScaledResolution(mc);
      IFont var4 = this.resolveNotificationFont();
      Notifications$3 var5 = new Notifications$3("Example", true, System.currentTimeMillis(), var2);
      Notifications$2 var6 = this.computeCardLayout(var4, var5);
      float var7 = this.getRenderScale();
      float var8 = Notifications$2.getWidth(var6) * var7;
      float var9 = Notifications$2.getHeight(var6) * var7;
      boolean var10 = var1 >= var3.getScaledWidth() * 0.5F;
      float var11 = var10 ? var1 - var8 : var1;
      this.LLIMkA6(var4, var5, var6, var11, var2, 255, 0.75F);
      return new float[]{var11, var2, var11 + var8, var2 + var9};
   }

   private static void setAnchorPosition(float var0, float var1, ScaledResolution var2) {
      anchorX = var0;
      OGVld = var1;
      qawFl = var0 / Math.max(1.0F, (float)var2.getScaledWidth());
      anchorRatioY = var1 / Math.max(1.0F, (float)var2.getScaledHeight());
   }

   private static float exponentialSmooth(float var0, float var1, float var2, float var3) {
      if (Math.abs(var1 - var0) < 1.0E-4F) {
         return var1;
      } else {
         float var4 = 1.0F - (float)Math.pow(2.0, -var3 * var2);
         return var0 + (var1 - var0) * var4;
      }
   }

   private static float easeOutCubic(float var0) {
      float var1 = 1.0F - Math.max(0.0F, Math.min(1.0F, var0));
      return 1.0F - var1 * var1 * var1;
   }

   private void drawProgressBar(float var1, float var2, Notifications$2 var3, int var4, float var5, boolean var6) {
      float var7 = Math.max(0.0F, Math.min(1.0F, var5));
      if (!(var7 <= 0.01F)) {
         float var8 = Math.max(0.45F, this.scaleForStyle(0.7F));
         float var9 = Math.max(0.0F, Notifications$2.getWidth(var3));
         float var10 = var9 * var7;
         if (!(var10 <= 0.5F)) {
            float var11 = var1 + var9 - var10;
            float var12 = var2 + Notifications$2.getHeight(var3) - var8;
            double var13 = System.currentTimeMillis() * 0.018;
            double var15 = var6 ? 0.0 : 24.0;
            int var17 = Math.min(var4, 255);
            int var18 = ClientUtils.YVVZ(Arraylist.xQec0(var13 + var1 * 0.5 + var15), var17);
            int var19 = ClientUtils.YVVZ(Arraylist.xQec0(var13 + (var1 + var9) * 0.5 + 38.0 + var15), var17);
            RoundedRect.drawFourCornerGradientArgb(var11, var12, var10, var8, var8 * 0.5F, var18, var18, var19, var19);
         }
      }
   }

   private static int scaleRgb(int var0, float var1) {
      int var2 = Math.round((var0 >> 16 & 0xFF) * var1);
      int var3 = Math.round((var0 >> 8 & 0xFF) * var1);
      int var4 = Math.round((var0 & 0xFF) * var1);
      return var2 << 16 | var3 << 8 | var4;
   }

   private static void xexr(float var0, float var1, float var2, boolean var3, int var4, float var5) {
      float var6 = var2 * 0.28F;
      if (var3) {
         drawOutlineLine(var0 + var6, var1 + var2 * 0.54F, var0 + var2 * 0.44F, var1 + var2 - var6, var4, var5);
         drawOutlineLine(var0 + var2 * 0.44F, var1 + var2 - var6, var0 + var2 - var6, var1 + var6, var4, var5);
      } else {
         drawOutlineLine(var0 + var6, var1 + var6, var0 + var2 - var6, var1 + var2 - var6, var4, var5);
         drawOutlineLine(var0 + var2 - var6, var1 + var6, var0 + var6, var1 + var2 - var6, var4, var5);
      }
   }

   private String getNotificationTitle(Notifications$3 var1) {
      return Notifications$3.pokb5(var1) ? Notifications$3.getAlertTitle(var1) : this.formatModuleToggleText(Notifications$3.isModuleEnabled(var1));
   }

   private static void drawNotificationIcon(Notifications$3 var0, float var1, float var2, float var3, int var4, float var5) {
      if (Notifications$3.isCompact(var0)) {
         keB02(var1, var2, var3, var4, var5);
      } else if (Notifications$3.pokb5(var0)) {
         drawSunIcon(var1, var2, var3, var4, var5);
      } else {
         xexr(var1, var2, var3, Notifications$3.isModuleEnabled(var0), var4, var5);
      }
   }

   private static void keB02(float var0, float var1, float var2, int var3, float var4) {
      float var5 = var0 + var2 * 0.22F;
      float var6 = var0 + var2 * 0.78F;
      float var7 = var1 + var2 * 0.2F;
      float var8 = var1 + var2 * 0.43F;
      float var9 = var1 + var2 * 0.54F;
      float var10 = var1 + var2 * 0.78F;
      drawOutlineLine(var5, var8, var6, var8, var3, var4);
      drawOutlineLine(var0 + var2 * 0.31F, var8, var0 + var2 * 0.36F, var7, var3, var4);
      drawOutlineLine(var0 + var2 * 0.36F, var7, var0 + var2 * 0.47F, var7, var3, var4);
      drawOutlineLine(var0 + var2 * 0.47F, var7, var0 + var2 * 0.5F, var1 + var2 * 0.29F, var3, var4);
      drawOutlineLine(var0 + var2 * 0.5F, var1 + var2 * 0.29F, var0 + var2 * 0.53F, var7, var3, var4);
      drawOutlineLine(var0 + var2 * 0.53F, var7, var0 + var2 * 0.64F, var7, var3, var4);
      drawOutlineLine(var0 + var2 * 0.64F, var7, var0 + var2 * 0.69F, var8, var3, var4);
      drawOutlineLine(var0 + var2 * 0.28F, var9, var0 + var2 * 0.28F, var10, var3, var4);
      drawOutlineLine(var0 + var2 * 0.72F, var9, var0 + var2 * 0.72F, var10, var3, var4);
      drawOutlineLine(var0 + var2 * 0.28F, var10, var0 + var2 * 0.43F, var1 + var2 * 0.82F, var3, var4);
      drawOutlineLine(var0 + var2 * 0.43F, var1 + var2 * 0.82F, var0 + var2 * 0.5F, var1 + var2 * 0.75F, var3, var4);
      drawOutlineLine(var0 + var2 * 0.5F, var1 + var2 * 0.75F, var0 + var2 * 0.57F, var1 + var2 * 0.82F, var3, var4);
      drawOutlineLine(var0 + var2 * 0.57F, var1 + var2 * 0.82F, var0 + var2 * 0.72F, var10, var3, var4);
      drawOutlineLine(var0 + var2 * 0.34F, var1 + var2 * 0.64F, var0 + var2 * 0.43F, var1 + var2 * 0.67F, var3, var4 * 1.25F);
      drawOutlineLine(var0 + var2 * 0.57F, var1 + var2 * 0.67F, var0 + var2 * 0.66F, var1 + var2 * 0.64F, var3, var4 * 1.25F);
   }

   private static void drawSunIcon(float var0, float var1, float var2, int var3, float var4) {
      float var5 = var0 + var2 * 0.5F;
      float var6 = var1 + var2 * 0.5F;
      float var7 = var2 * 0.16F;
      float var8 = var2 * 0.29F;
      float var9 = var2 * 0.39F;
      drawCircleOutline(var5, var6, var7, var3, var4, 16);
      drawCircleOutline(var5, var6, var8, var3, var4, 20);

      for (int var10 = 0; var10 < 8; var10++) {
         double var11 = (Math.PI * 2) * var10 / 8.0;
         float var13 = (float)Math.cos(var11);
         float var14 = (float)Math.sin(var11);
         drawOutlineLine(var5 + var13 * var8, var6 + var14 * var8, var5 + var13 * var9, var6 + var14 * var9, var3, var4 * 1.35F);
      }
   }

   private static void drawCircleOutline(float var0, float var1, float var2, int var3, float var4, int var5) {
      float var6 = (var3 >> 24 & 0xFF) / 255.0F;
      float var7 = (var3 >> 16 & 0xFF) / 255.0F;
      float var8 = (var3 >> 8 & 0xFF) / 255.0F;
      float var9 = (var3 & 0xFF) / 255.0F;
      GL11.glPushAttrib(1048575);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glLineWidth(var4);
      GL11.glColor4f(var7, var8, var9, var6);
      GL11.glBegin(2);

      for (int var10 = 0; var10 < var5; var10++) {
         double var11 = (Math.PI * 2) * var10 / var5;
         GL11.glVertex2f(var0 + (float)Math.cos(var11) * var2, var1 + (float)Math.sin(var11) * var2);
      }

      GL11.glEnd();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopAttrib();
   }

   private static void drawOutlineLine(float var0, float var1, float var2, float var3, int var4, float var5) {
      float var6 = (var4 >> 24 & 0xFF) / 255.0F;
      float var7 = (var4 >> 16 & 0xFF) / 255.0F;
      float var8 = (var4 >> 8 & 0xFF) / 255.0F;
      float var9 = (var4 & 0xFF) / 255.0F;
      GL11.glPushMatrix();
      GL11.glPushAttrib(1048575);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glLineWidth(var5);
      GL11.glColor4f(var7, var8, var9, var6);
      GL11.glBegin(1);
      GL11.glVertex2f(var0, var1);
      GL11.glVertex2f(var2, var3);
      GL11.glEnd();
      GL11.glLineWidth(1.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glPopAttrib();
      GL11.glPopMatrix();
   }

   private void JYHc(IFont var1, String var2, float var3, float var4, int var5, boolean var6) {
      if (!var6) {
         var1.drawString(var2, var3, var4, var5, false);
      } else {
         float var7 = this.getShadowOffset();
         int var8 = ClientUtils.YVVZ(0, Math.min(180, var5 >> 24 & 0xFF));
         var1.drawString(var2, var3 + var7, var4 + var7, var8, false);
         var1.drawString(var2, var3, var4, var5, false);
      }
   }

   private void Oa27(IFont var1, String var2, float var3, float var4, int var5) {
      float var6 = this.getShadowOffset();
      int var7 = ClientUtils.YVVZ(0, Math.min(180, var5 >> 24 & 0xFF));
      var1.drawString(ClientUtils.AOAtn(var2), var3 + var6, var4 + var6, var7, false);
      var1.drawString(var2, var3, var4, var5, false);
   }

   public static void recomputeAnchorPosition(ScaledResolution var0) {
      applyAnchorRatios(var0);
   }

   public static float getAnchorX() {
      return anchorX;
   }

   public static float getAnchorY() {
      return OGVld;
   }

   public static IFont getNotificationFont(Notifications var0) {
      return var0.resolveNotificationFont();
   }

   public static Notifications$2 wEsh(Notifications var0, IFont var1, Notifications$3 var2) {
      return var0.computeCardLayout(var1, var2);
   }

   public static void applyAnchorPosition(float var0, float var1, ScaledResolution var2) {
      setAnchorPosition(var0, var1, var2);
   }

   public static boolean JhSgku(Notifications var0) {
      return var0.isModernStyle();
   }

   public static void renderModernCard(Notifications var0, IFont var1, Notifications$3 var2, Notifications$2 var3, float var4, float var5, int var6, float var7) {
      var0.renderModernStyleCard(var1, var2, var3, var4, var5, var6, var7);
   }

   public static int getSuccessColor() {
      return PSYs7;
   }

   public static int CZqbG() {
      return CLASSIC_BACKGROUND_COLOR;
   }

   public static int getBackgroundTransparency(Notifications var0) {
      return var0.getBackgroundAlpha();
   }

   public static int getPrimaryTextColor() {
      return ZAd;
   }

   public static void drawShadowedText(Notifications var0, IFont var1, String var2, float var3, float var4, int var5, boolean var6) {
      var0.JYHc(var1, var2, var3, var4, var5, var6);
   }

   public static String formatStateText(Notifications var0, boolean var1) {
      return var0.formatToggleState(var1);
   }
}
