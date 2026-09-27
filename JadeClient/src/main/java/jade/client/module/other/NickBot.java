// Jade recovery: module: Nick Bot (other); original class: jade.deps.eLz.uNk4tFipJ
package jade.client.module.other;

import jade.client.common.ClientUtils;
import jade.client.common.Subscribe;
import jade.client.event.ChatReceivedEvent;
import jade.client.event.PreUpdateEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.setting.BooleanSetting;
import jade.client.setting.DescriptionSetting;
import jade.client.setting.MultiSelectSetting;
import jade.client.setting.SliderSetting;
import jade.deps.gson.GsonBuilder;
import jade.deps.gson.JsonArray;
import jade.deps.gson.JsonElement;
import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonParser;

import jade.deps.loader107.InjectionPaths;

import jade.mixin.impl.accessor.IAccessorGuiScreenBook;
import jade.mixin.impl.accessor.IAccessorMinecraft;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IChatComponent;

@ModuleInfo(aliases = {"Nick Bot", "NickBot"})
public class NickBot extends Module {
   private static final String elzcO = "/nick actuallyset ";
   private static final String ubldX = "Nick Bot";
   private static final String[] DEFAULT_LOBBIES = new String[]{
         "mw",
         "blitz",
         "sw",
         "bw",
         "mm",
         "bb",
         "duels",
         "s",
         "classic",
         "arcade",
         "uhc",
         "tnt",
         "ww",
         "prototype"
      };
   private final SliderSetting nickInterval;
   private final SliderSetting resetInterval;
   private final BooleanSetting autoClaim;
   private final BooleanSetting vowelRepeaters;
   private final BooleanSetting n4Letters;
   private final BooleanSetting underscoreEdges;
   private final BooleanSetting customChecks;
   private final List<String> Ofo0 = new ArrayList<>();
   private final List<String> pIriM = new ArrayList<>();
   private final List<String> endsWithChecks = new ArrayList<>();
   private final List<String> KUqW = new ArrayList<>();
   private final List<String> lobbyList = new ArrayList<>();
   private boolean lzL;
   private boolean awaitingNickResponse;
   private boolean mmat;
   private int tickCounter;
   private int NLsuf;
   private int attemptsSinceReset;
   private int generatedNickCount;
   private long lastLobbyCommandMillis;
   private long lobbySelectorOpenedAtMillis;
   private long nickRequestStartMillis;
   private String xm8 = "";

   public NickBot() {
      super("Nick Bot", Category.other);
      this.registerSetting(new DescriptionSetting("Claims matching random nicknames from JSON rules."));
      this.registerSetting(
         this.nickInterval = new SliderSetting(
            "Nick Interval", "ticks", 20.0, 1.0, 50.0, 1.0
         )
      );
      this.registerSetting(
         this.resetInterval = new SliderSetting(
            "Reset Interval",
            true,
            0.0,
            0.0,
            100.0,
            1.0
         )
      );
      this.registerSetting(
         this.autoClaim = new BooleanSetting(
            "Auto Claim", true
         )
      );
      this.vowelRepeaters = new BooleanSetting(
         "Vowel Repeaters", true
      );
      this.n4Letters = new BooleanSetting("4 Letters", true);
      this.underscoreEdges = new BooleanSetting(
         "Underscore Edges", true
      );
      this.customChecks = new BooleanSetting("Custom Checks", true);
      String var10003 = "Wanted Nicks";
      BooleanSetting[] var10004 = new BooleanSetting[4];
      var10004[0] = this.vowelRepeaters;
      var10004[1] = this.n4Letters;
      var10004[2] = this.underscoreEdges;
      var10004[3] = this.customChecks;
      this.registerSetting(new MultiSelectSetting(var10003, var10004));
      this.registerHiddenSetting(this.vowelRepeaters);
      this.registerHiddenSetting(this.n4Letters);
      this.registerHiddenSetting(this.underscoreEdges);
      this.registerHiddenSetting(this.customChecks);
      this.registerSetting(new BooleanSetting("Reload Custom Options", new Runnable() {
         @Override
         public void run() {
            NickBot.UQFF(NickBot.this, true);
         }
      }));
   }

   private void registerHiddenSetting(BooleanSetting var1) {
      var1.visible = false;
      this.registerSetting(var1);
   }

   @Override
   public void onEnable() {
      this.resetState();
      this.loadCustomChecks(true);
      this.awaitingNickResponse = true;
      this.nickRequestStartMillis = System.currentTimeMillis();
      this.sendCommand("/nick reuse");
      ClientUtils.sendJadeMessage("Nick Bot", "&7fetching current nick.");
   }

   @Override
   public void onDisable() {
      boolean var1 = this.lzL || this.awaitingNickResponse || this.mmat;
      this.resetState();
      if (var1 && ClientUtils.isInWorld()) {
         ClientUtils.sendJadeMessage("Nick Bot", "&7disabled.");
      }
   }

   @Override
   public String getInfo() {
      return this.lzL ? String.valueOf(this.generatedNickCount) : "";
   }

   @Subscribe
   public void onChatReceived(ChatReceivedEvent var1) {
      if (var1 != null && var1.iChatComponent != null && (this.lzL || this.awaitingNickResponse)) {
         String var2 = ClientUtils.zaUnpz(var1.iChatComponent.getUnformattedText());
         if (var2 != null) {
            if (this.awaitingNickResponse && var2.startsWith("You are now nicked as ")) {
               this.xm8 = var2.substring("You are now nicked as ".length());
               if (this.xm8.endsWith(".")) {
                  this.xm8 = this.xm8.substring(0, this.xm8.length() - 1);
               }

               this.awaitingNickResponse = false;
               this.lzL = true;
               ClientUtils.sendJadeMessage("Nick Bot", "&7current nick: &f" + this.xm8 + "&7.");
               ClientUtils.sendJadeMessage("Nick Bot", "&7enabled.");
               var1.setCanceled(true);
            } else if (this.awaitingNickResponse && var2.equals("Processing request. Please wait...")) {
               var1.setCanceled(true);
            } else {
               if (this.lzL && var2.equals("Generating a unique random name. Please wait...")) {
                  var1.setCanceled(true);
               }
            }
         }
      }
   }

   @Subscribe
   public void onPreUpdate(PreUpdateEvent var1) {
      if (ClientUtils.isInWorld()) {
         if (!this.lzL) {
            if (!this.awaitingNickResponse || System.currentTimeMillis() - this.nickRequestStartMillis <= 5000L) {
               return;
            }

            this.awaitingNickResponse = false;
            this.lzL = true;
            ClientUtils.sendJadeMessage("Nick Bot", "&7enabled without a current nick response.");
         }

         this.tickCounter++;
         if (!this.handleBookScreen()) {
            int var2 = this.LemhnP();
            if (var2 != 1) {
               if (var2 == 0 && System.currentTimeMillis() - this.lastLobbyCommandMillis > 5000L) {
                  this.sendCommand("/l " + this.getRandomLobby());
                  this.lastLobbyCommandMillis = System.currentTimeMillis();
               }
            } else if (!this.Lbpboe()) {
               int var3 = Math.max(1, (int)this.nickInterval.getInput());
               if (this.tickCounter - this.NLsuf >= var3) {
                  this.NLsuf = this.tickCounter;
                  int var4 = (int)this.resetInterval.getInput();
                  if (var4 > 0 && var4 < 100 && ++this.attemptsSinceReset > var4) {
                     this.openLobbySelector();
                     this.attemptsSinceReset = 0;
                  } else {
                     this.sendCommand("/nick help setrandom");
                  }
               }
            }
         }
      }
   }

   private boolean handleBookScreen() {
      if (!(mc.currentScreen instanceof GuiScreenBook)) {
         return false;
      } else {
         String var1 = this.readNickFromBook();
         if (var1.isEmpty()) {
            return false;
         } else if (this.isWantedNick(var1)) {
            if (this.autoClaim.isToggled()) {
               this.sendCommand("/nick actuallyset " + var1);
               ClientUtils.sendJadeMessage("Nick Bot", "&7claimed nick &f" + var1 + "&7.");
            } else {
               ClientUtils.sendJadeMessage("Nick Bot", "&7found wanted nick &f" + var1 + "&7.");
            }

            this.disable();
            return true;
         } else {
            this.generatedNickCount++;
            ClientUtils.sendJadeMessage("Nick Bot", "&7new nick #" + this.generatedNickCount + ": &f" + var1 + "&7.");
            mc.thePlayer.closeScreen();
            return true;
         }
      }
   }

   private String readNickFromBook() {
      List var1 = ((IAccessorGuiScreenBook)mc.currentScreen).getBookContents();
      if (var1 == null) {
         return "";
      } else {
         int var2 = Math.min(128 / mc.fontRendererObj.FONT_HEIGHT, var1.size());

         for (int var3 = 0; var3 + 1 < var2; var3++) {
            IChatComponent var4 = (IChatComponent)var1.get(var3);
            if (var4 != null && "you:".equalsIgnoreCase(ClientUtils.zaUnpz(var4.getUnformattedText()))) {
               IChatComponent var5 = (IChatComponent)var1.get(var3 + 1);
               return var5 == null ? "" : ClientUtils.zaUnpz(var5.getUnformattedText()).trim();
            }
         }

         return "";
      }
   }

   private int LemhnP() {
      if (mc.theWorld != null && mc.thePlayer != null) {
         ItemStack var1 = mc.thePlayer.inventory.getStackInSlot(4);
         if (var1 != null && var1.getItem() == Item.getItemFromBlock(Blocks.trapped_chest)) {
            return 1;
         } else {
            return mc.theWorld.provider != null && "The End".equals(mc.theWorld.provider.getDimensionName()) ? 0 : -1;
         }
      } else {
         return -1;
      }
   }

   private boolean Lbpboe() {
      if (!this.mmat) {
         return false;
      } else if (System.currentTimeMillis() - this.lobbySelectorOpenedAtMillis > 5000L) {
         this.mmat = false;
         return false;
      } else if (mc.currentScreen instanceof GuiChest && mc.thePlayer.openContainer instanceof ContainerChest) {
         ContainerChest var1 = (ContainerChest)mc.thePlayer.openContainer;
         String var2 = var1.getLowerChestInventory().getDisplayName().getUnformattedText();
         if (!var2.endsWith("Lobby Selector")) {
            return true;
         } else {
            int var3 = var1.getLowerChestInventory().getSizeInventory();

            for (int var4 = var3 - 1; var4 >= 0; var4--) {
               ItemStack var5 = var1.getLowerChestInventory().getStackInSlot(var4);
               if (var5 != null && var5.hasDisplayName()) {
                  String var6 = var5.getDisplayName();
                  String var7 = ClientUtils.zaUnpz(var6);
                  if (var6.startsWith("§a") && var7.contains("Lobby #")) {
                     mc.playerController.windowClick(var1.windowId, var4, 0, 0, mc.thePlayer);
                     this.mmat = false;
                     return true;
                  }
               }
            }

            return true;
         }
      } else {
         return true;
      }
   }

   private void openLobbySelector() {
      if (mc.thePlayer.inventory.currentItem != 8) {
         mc.thePlayer.inventory.currentItem = 8;
      }

      ((IAccessorMinecraft)mc).callRightClickMouse();
      this.lobbySelectorOpenedAtMillis = System.currentTimeMillis();
      this.mmat = true;
   }

   private boolean isWantedNick(String var1) {
      if (var1 != null && !var1.isEmpty()) {
         String var2 = var1.toLowerCase(Locale.ROOT);
         if (!this.xm8.isEmpty() && this.xm8.toLowerCase(Locale.ROOT).equals(var2)) {
            return false;
         } else if (this.n4Letters.isToggled() && var1.length() == 4) {
            return true;
         } else {
            if (this.vowelRepeaters.isToggled()) {
               String[] var3 = new String[]{"a", "e", "i", "o", "u"};

               for (String var7 : var3) {
                  if (var2.contains(var7 + var7 + var7)) {
                     return true;
                  }
               }
            }

            if (!this.underscoreEdges.isToggled() || !var1.startsWith("_") && !var1.endsWith("_")) {
               if (!this.customChecks.isToggled()) {
                  return false;
               } else if (this.listContains(this.Ofo0, var2)) {
                  return true;
               } else {
                  for (String var11 : this.pIriM) {
                     if (var2.startsWith(var11)) {
                        return true;
                     }
                  }

                  for (String var12 : this.endsWithChecks) {
                     if (var2.endsWith(var12)) {
                        return true;
                     }
                  }

                  for (String var13 : this.KUqW) {
                     if (var2.contains(var13)) {
                        return true;
                     }
                  }

                  return false;
               }
            } else {
               return true;
            }
         }
      } else {
         return false;
      }
   }

   private boolean listContains(List<String> var1, String var2) {
      for (String var4 : var1) {
         if (var4.equals(var2)) {
            return true;
         }
      }

      return false;
   }

   private void loadCustomChecks(boolean var1) {
      this.Ofo0.clear();
      this.pIriM.clear();
      this.endsWithChecks.clear();
      this.KUqW.clear();
      this.lobbyList.clear();
      this.lobbyList.addAll(Arrays.asList(DEFAULT_LOBBIES));
      File var2 = this.getConfigFile();
      this.createDefaultConfig(var2);
      FileReader var3 = null;

      JsonObject var4;
      boolean var5;
      label99: {
         try {
            var3 = new FileReader(var2);
            var4 = new JsonParser().parse(var3).getAsJsonObject();
            var5 = this.ensureInstructions(var4);
            break label99;
         } catch (Exception var18) {
            if (var1) {
               ClientUtils.sendJadeMessage("Nick Bot", "&cfailed to load JSON rules. Using defaults.");
            }
         } finally {
            if (var3 != null) {
               try {
                  var3.close();
               } catch (Exception var16) {
               }
            }
         }

         return;
      }

      if (var5) {
         this.writeJsonFile(var2, var4);
      }

      try {
         this.readStringList(var4, "names", this.Ofo0);
         this.readStringList(var4, "startsWith", this.pIriM);
         this.readStringList(var4, "endsWith", this.endsWithChecks);
         this.readStringList(var4, "contains", this.KUqW);
         ArrayList var6 = new ArrayList();
         this.readStringList(var4, "lobbies", var6);
         if (!var6.isEmpty()) {
            this.lobbyList.clear();
            this.lobbyList.addAll(var6);
         }

         if (var1) {
            ClientUtils.sendJadeMessage("Nick Bot", "&7loaded custom checks from &fnickbot.json&7.");
         }
      } catch (Exception var17) {
         if (var1) {
            ClientUtils.sendJadeMessage("Nick Bot", "&cfailed to load JSON rules. Using defaults.");
         }
      }
   }

   private void readStringList(JsonObject var1, String var2, List<String> var3) {
      if (var1 != null && var1.has(var2) && var1.get(var2).isJsonArray()) {
         for (JsonElement var6 : var1.getAsJsonArray(var2)) {
            if (var6.isJsonPrimitive()) {
               String var7 = var6.getAsString();
               if (var7 != null && !var7.trim().isEmpty()) {
                  String var8 = var7.trim().toLowerCase(Locale.ROOT);
                  if (!var3.contains(var8)) {
                     var3.add(var8);
                  }
               }
            }
         }
      }
   }

   private void createDefaultConfig(File var1) {
      if (!var1.exists()) {
         File var2 = var1.getParentFile();
         if (var2 != null && !var2.exists()) {
            var2.mkdirs();
         }

         JsonObject var3 = new JsonObject();
         var3.add("_instructions", this.HXTPU());
         var3.add("names", new JsonArray());
         var3.add("startsWith", new JsonArray());
         var3.add("endsWith", new JsonArray());
         var3.add("contains", new JsonArray());
         JsonArray var4 = new JsonArray();

         for (String var8 : DEFAULT_LOBBIES) {
            var4.add(var8);
         }

         var3.add("lobbies", var4);
         this.writeJsonFile(var1, var3);
      }
   }

   private JsonArray HXTPU() {
      JsonArray var1 = new JsonArray();
      var1.add("press nick bots reload button after saving.");
      var1.add("names: exact nicknames to claim, not case sensitive");
      var1.add("startsWith, endsWith, contains: - not case sensitive .");
      var1.add("lobbies: what lobbies to go to?.");
      var1.add("select custom in wanted nicks box");
      return var1;
   }

   private boolean ensureInstructions(JsonObject var1) {
      JsonArray var2 = this.HXTPU();
      if (var1.has("_instructions") && var1.get("_instructions").isJsonArray() && var1.getAsJsonArray("_instructions").toString().equals(var2.toString())) {
         return false;
      } else {
         var1.add("_instructions", var2);
         return true;
      }
   }

   private void writeJsonFile(File var1, JsonObject var2) {
      FileWriter var3 = null;

      try {
         var3 = new FileWriter(var1);
         new GsonBuilder().setPrettyPrinting().create().toJson((JsonElement)var2, var3);
      } catch (Exception var13) {
      } finally {
         if (var3 != null) {
            try {
               var3.close();
            } catch (Exception var12) {
            }
         }
      }
   }

   private File getConfigFile() {
      return new File(InjectionPaths.dataDirectory(mc.mcDataDir), "nickbot.json");
   }

   private String getRandomLobby() {
      return this.lobbyList.isEmpty() ? DEFAULT_LOBBIES[ClientUtils.randomInt(0, DEFAULT_LOBBIES.length - 1)] : this.lobbyList.get(ClientUtils.randomInt(0, this.lobbyList.size() - 1));
   }

   private void sendCommand(String var1) {
      if (mc.thePlayer != null) {
         mc.thePlayer.sendChatMessage(var1);
      }
   }

   private void resetState() {
      this.lzL = false;
      this.awaitingNickResponse = false;
      this.mmat = false;
      this.tickCounter = 0;
      this.NLsuf = 0;
      this.attemptsSinceReset = 0;
      this.generatedNickCount = 0;
      this.lastLobbyCommandMillis = 0L;
      this.lobbySelectorOpenedAtMillis = 0L;
      this.nickRequestStartMillis = 0L;
      this.xm8 = "";
   }

   public static void UQFF(NickBot var0, boolean var1) {
      var0.loadCustomChecks(var1);
   }
}
