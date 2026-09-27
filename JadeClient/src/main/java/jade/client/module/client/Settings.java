// Jade recovery: module: Settings (client); original class: jade.deps.eLz.U1TM3B
package jade.client.module.client;

import jade.client.common.IrcChatHandler;
import jade.client.common.WindowIcon;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.setting.BooleanSetting;
import jade.client.setting.MultiSelectSetting;
import jade.client.setting.SliderSetting;
import jade.client.setting.TextSetting;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;

@ModuleInfo
public class Settings extends Module {
   public static BooleanSetting customWindowIcon;
   public static BooleanSetting addBracketsToDistance;
   public static BooleanSetting hideFirstPersonSelfEsp;
   public static BooleanSetting setChatAsInventory;
   public static BooleanSetting showHealthAsHearts;
   public static BooleanSetting showHeartSymbol;
   public static BooleanSetting irc;
   public static BooleanSetting systemNotificationSounds;
   public static BooleanSetting ircSounds;
   public static BooleanSetting defaultChatToIrc;
   public static TextSetting ircPrefix;
   public static BooleanSetting sword;
   public static BooleanSetting axe;
   public static BooleanSetting bow;
   public static BooleanSetting fist;
   public static BooleanSetting hoe;
   public static BooleanSetting rod;
   public static BooleanSetting shovel;
   public static BooleanSetting stick;
   public static MultiSelectSetting multiSelectSetting;
   public static BooleanSetting rotateBody;
   public static BooleanSetting fullBody;
   public static BooleanSetting loadGuiState;
   public static BooleanSetting sendMessageOnEnable;
   public static SliderSetting offset;
   public static SliderSetting timeMultiplier;

   public Settings() {
      super("Settings", Category.client, 0);
      customWindowIcon = new BooleanSetting(
            "Custom window icon", false
         )
         .onChange(Settings::onCustomWindowIconChange);
      addBracketsToDistance = new BooleanSetting(
         "Add brackets to distance", true
      );
      hideFirstPersonSelfEsp = new BooleanSetting(
         "Hide first person self ESP",
         true
      );
      setChatAsInventory = new BooleanSetting(
         "Set chat as inventory",
         true
      );
      showHealthAsHearts = new BooleanSetting(
         "Show health as hearts",
         true
      );
      showHeartSymbol = new BooleanSetting(
         "Show heart symbol", true
      );
      String var10003 = "Weapon selecting";
      BooleanSetting[] var10004 = new BooleanSetting[8];
      var10004[0] = sword = new BooleanSetting(
         "Sword", true
      );
      var10004[1] = axe = new BooleanSetting(
         "Axe", false
      );
      var10004[2] = bow = new BooleanSetting(
         "Bow", false
      );
      var10004[3] = fist = new BooleanSetting(
         "Fist", false
      );
      var10004[4] = hoe = new BooleanSetting(
         "Hoe", false
      );
      var10004[5] = rod = new BooleanSetting(
         "Rod", false
      );
      var10004[6] = shovel = new BooleanSetting(
         "Shovel", false
      );
      var10004[7] = stick = new BooleanSetting(
         "Stick", true
      );
      this.registerSetting(multiSelectSetting = new MultiSelectSetting(var10003, var10004));
      sword.visible = false;
      axe.visible = false;
      bow.visible = false;
      fist.visible = false;
      hoe.visible = false;
      rod.visible = false;
      shovel.visible = false;
      stick.visible = false;
      this.registerSetting(sword);
      this.registerSetting(axe);
      this.registerSetting(bow);
      this.registerSetting(fist);
      this.registerSetting(hoe);
      this.registerSetting(rod);
      this.registerSetting(shovel);
      this.registerSetting(stick);
      rotateBody = new BooleanSetting(
         "Rotate body", true
      );
      fullBody = new BooleanSetting("Full body", false);
      loadGuiState = new BooleanSetting(
         "Load gui state", true
      );
      sendMessageOnEnable = new BooleanSetting(
         "Send message on enable",
         false
      );
      offset = new SliderSetting("Offset", 0.5, -3.0, 3.0, 0.1);
      timeMultiplier = new SliderSetting("Time multiplier", 0.5, 0.1, 4.0, 0.1);
      this.registerSetting(
         systemNotificationSounds = new BooleanSetting(
            "System notification sounds",
            true
         )
      );
      this.registerSetting(
         ircSounds = new BooleanSetting(
            "IRC sounds", true
         )
      );
      this.registerSetting(irc = new BooleanSetting("IRC", true));
      this.registerSetting(defaultChatToIrc = new BooleanSetting("Default chat to IRC", false));
      this.registerSetting(
         ircPrefix = new TextSetting(
            "IRC prefix",
            "#",
            "Type one character...",
            1
         ) {
            @Override
            public void setValue(String var1) {
               if (IrcChatHandler.isValidPrefix(var1)) {
                  super.setValue(var1);
               }
            }
         }
      );
      this.canBeEnabled = false;
   }

   public static boolean isInventoryScreenOpen() {
      return mc.currentScreen instanceof GuiInventory ? true : mc.currentScreen instanceof GuiChat && setChatAsInventory.isToggled();
   }

   private static void onCustomWindowIconChange() {
      if (customWindowIcon.isToggled()) {
         WindowIcon.applyCustomIcon();
      } else {
         WindowIcon.restoreDefaultIcon();
      }
   }
}
