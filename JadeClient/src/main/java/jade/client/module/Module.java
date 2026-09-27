// Jade recovery: original class: jade.deps.eLz.QNtzFjlH
package jade.client.module;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.ModuleManager;
import jade.client.module.client.Gui;
import jade.client.setting.BooleanSetting;
import jade.client.setting.Setting;
import jade.client.setting.SliderSetting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.Minecraft;

public class Module {
   protected static Minecraft mc;
   protected ArrayList<Setting> settings;
   public boolean canBeEnabled = true;
   public boolean skipSettingsPersistence;
   public boolean hidden;
   public boolean initialized;
   public boolean skipEventRegistration;
   public String lastInfo;
   public static boolean infoChanged;
   public static List<String> categoryNames = new ArrayList<>();
   private final String name;
   private final Category category;
   private final ModuleState state;
   private final Keybind keybind;
   private volatile boolean enabled;

   public Module(String var1, Category var2) {
      this(var1, var2, 0);
   }

   public Module(String var1, Category var2, int var3) {
      this.name = var1;
      this.category = var2;
      this.state = new ModuleState(this, var1);
      this.keybind = new Keybind(var3);
      mc = Minecraft.getMinecraft();
      this.settings = new ArrayList<>();
   }

   public final void attachManager(ModuleManager var1) {
      this.state.attach(var1);
   }

   public boolean isEnabledByDefault() {
      return false;
   }

   public boolean canBeEnabled() {
      return this.canBeEnabled;
   }

   public boolean isHidden() {
      return this.hidden;
   }

   public void setHidden(boolean var1) {
      this.hidden = var1;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void setEnabled(boolean var1) {
      this.enabled = var1;
   }

   public String getName() {
      return this.name;
   }

   public String getNameInHud() {
      return this.name;
   }

   public Category getCategory() {
      return this.category;
   }

   public int getKeycode() {
      return this.keybind.getKeycode();
   }

   public void setKeycode(int var1) {
      this.keybind.setKeycode(var1);
   }

   public ArrayList<Setting> getSettings() {
      return this.settings;
   }

   public void registerSetting(Setting var1) {
      this.settings.add(var1);
   }

   public void enable() {
      this.state.enable();
   }

   public void disable() {
      this.state.disable();
   }

   public void toggle() {
      if (!this.isEnabled()) {
         this.enable();
      } else {
         this.disable();
      }

      if (Jade.Grq != null && !(this instanceof Gui)) {
         Jade.Grq.getProfile().unmodified = false;
      }
   }

   public void pollKeybind() {
      try {
         this.keybind.poll(this);
      } catch (Exception var2) {
         var2.printStackTrace();
         ClientUtils.sendJadeMessage("Jade", "&cfailed to check keybinding, setting to none");
         this.keybind.setKeycode(0);
      }
   }

   public void syncKeybind() {
      try {
         this.keybind.sync();
      } catch (Exception var2) {
         var2.printStackTrace();
         ClientUtils.sendColoredMessage("&cFailed to check keybinding. Setting to none");
         this.keybind.clear();
      }
   }

   public void onKey(int var1, boolean var2) {
      this.keybind.onKey(this, var1, var2);
   }

   public String getInfo() {
      return "";
   }

   public String updateInfo() {
      String var1 = this.getInfo();
      if (!Objects.equals(var1, this.lastInfo)) {
         infoChanged = true;
      }

      this.lastInfo = var1;
      return var1;
   }

   public void onEnable() {
   }

   public void onDisable() {
   }

   public void onUpdate() {
   }

   public void guiUpdate() {
   }

   public void guiButtonToggled(BooleanSetting var1) {
   }

   public void guiSliderChanged(SliderSetting var1) {
   }

   public List<Module$2> getSettingAliases() {
      return Collections.emptyList();
   }

   protected static Module$2 buildSettingAlias(String var0, String var1, String[] var2, String[] var3) {
      return new Module$2(var0, var1, var2, var3);
   }

   protected static Module$2 DEgN(String var0, String var1, String... var2) {
      return buildSettingAlias(var0, var1, var2, null);
   }

   protected static List<Module$2> BIqm(Module$2... var0) {
      return var0 == null ? new ArrayList<>() : new ArrayList<>(Arrays.asList(var0));
   }

   static {
      Arrays.stream(Category.values()).map(Enum::name).forEach(categoryNames::add);
   }
}
