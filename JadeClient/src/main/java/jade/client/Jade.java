// Jade recovery: original class: jade.deps.eLz.xXaz2k4tV
package jade.client;

import jade.client.common.AccountStore;
import jade.client.common.ClientUtils;
import jade.client.common.DangerousModules;
import jade.client.common.EventBus;
import jade.client.common.EventPhase;
import jade.client.common.ExternalRenderer;
import jade.client.common.FontManager;
import jade.client.common.JadeClickGui;
import jade.client.common.JengaGame;
import jade.client.common.MiddleClickFriend;
import jade.client.common.ModuleManager;
import jade.client.common.PingChecker;
import jade.client.common.PlayerListTracker;
import jade.client.common.RelationManager;
import jade.client.common.RotationHandler;
import jade.client.common.InputHookManager;
import jade.client.common.SkinCache;
import jade.client.common.SkywarsGameState;
import jade.client.common.Subscribe;
import jade.client.common.WhisperShortcuts;
import jade.client.common.WindowIcon;
import jade.client.common.ConnectionStatsOverlay;
import jade.client.common.ConfigEntry;
import jade.client.common.PlayerPacketStateTracker;
import jade.client.core.BanTracker;
import jade.client.core.CommandManager;
import jade.client.core.ConfigManager;
import jade.client.core.ConfigProfile;
import jade.client.core.SwvyvpD;
import jade.client.core.ThemeManager;
import jade.client.core.PacketReplayController;
import jade.client.core.KeystrokesHud;
import jade.client.core.QueuedPacketDispatcher;
import jade.client.event.EntityJoinWorldEvent;
import jade.client.event.ProfileLoadEvent;
import jade.client.event.SliderChangeEvent;
import jade.client.event.TickEndEvent;
import jade.client.gui.ClickGui;
import jade.client.gui.JadeScreen;
import jade.client.module.Module;
import jade.client.module.minigames.BedwarsUtils$2;
import jade.client.module.movement.Timer;
import jade.deps.loader107.RecoveredMethodInterpreter;
import jade.deps.loader107.IrcMessageBus;
import jade.deps.loader107.PendingChatQueue;
import jade.inject.InjectionAgent;
import java.io.File;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class Jade {
   public static boolean profilingEnabled = false;
   public static Minecraft mc = Minecraft.getMinecraft();
   private static KeystrokesHud hudRenderer;
   private static boolean YuysAp;
   private static boolean fqS;
   private static final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
   private static final ExecutorService executorService = Executors.newCachedThreadPool();
   private static Thread schedulerShutdownHook;
   private static Thread Ag9;
   private static ModuleManager moduleManager;
   public static ClickGui clickGui;
   public static ConfigManager configManager;
   public static ThemeManager themeManager;
   public static CommandManager commandManager;
   public static RelationManager relationManager;
   public static ConfigEntry Grq;
   public static PlayerListTracker playerListTracker;
   public static QueuedPacketDispatcher nbT;
   private static boolean JMvKkc;
   private static final Set<String> LbegRj = Collections.newSetFromMap(new ConcurrentHashMap<>());
   private static Jade$0 initializationState = Jade$0.NEW;
   private static String pcw = "not_started";
   private static final AtomicBoolean removalInProgress = new AtomicBoolean(false);

   public Jade() {
      moduleManager = new ModuleManager();
   }

   public static synchronized void bbKvwi() {
      RecoveredMethodInterpreter.invokeRecovered(
         "SlZNAWzXBCAAAAABAAAABAAAAMoAAAABAAAABWzXBSAAAAAAAAAAAAAAAAEAAAAAAAAABWzXBSAAAAABAAAAAAAAAAEAAAAAAAAAAmzXBIYAAAAEAAAAAWzXBJEAAAAFbNcFIAAAAAIAAAAAAAAAAQAAAAAAAAAFbNcFIAAAAAMAAAAAAAAAAQAAAAAAAAACbNcEhgAAABMAAAAFbNcFIAAAAAQAAAAAAAAAAQAAAAAAAAABbNcEeQAAAAVs1wUgAAAABQAAAAAAAAABAAAAAAAAAAFs1wR5AAAABWzXBSAAAAAGAAAAAQAAAAEAAAABAAAABWzXBSAAAAAHAAAAAAAAAAEAAAAAAAAABWzXBSAAAAAIAAAAAgAAAAEAAAAAAAAABWzXBSAAAAAJAAAAAAAAAAEAAAAAAAAABWzXBSAAAAAKAAAAAgAAAAEAAAAAAAAABWzXBSAAAAALAAAAAQAAAAEAAAAAAAAABWzXBSAAAAAMAAAAAgAAAAEAAAABAAAAAWzXBJ8AAAAFbNcFIAAAAA0AAAAAAAAAAQAAAAAAAAAFbNcFIAAAAA4AAAAAAAAAAQAAAAAAAAACbNcEhgAAACIAAAAFbNcFIAAAAA8AAAAAAAAAAQAAAAAAAAABbNcEeQAAAAVs1wUgAAAAEAAAAAAAAAABAAAAAAAAAAFs1wR5AAAABWzXBSAAAAARAAAAAQAAAAEAAAABAAAABWzXBSAAAAASAAAAAAAAAAEAAAAAAAAABWzXBSAAAAATAAAAAgAAAAEAAAAAAAAABWzXBSAAAAAUAAAAAAAAAAEAAAAAAAAABWzXBSAAAAAVAAAAAgAAAAEAAAAAAAAABWzXBSAAAAAWAAAAAQAAAAEAAAAAAAAABWzXBSAAAAAXAAAAAgAAAAEAAAABAAAAAWzXBJ8AAAAFbNcFIAAAABgAAAAAAAAAAQAAAAAAAAAFbNcFIAAAABkAAAABAAAAAAAAAAAAAAAFbNcFIAAAABoAAAAAAAAAAQAAAAAAAAAFbNcFIAAAABsAAAABAAAAAAAAAAAAAAAFbNcFIAAAABwAAAAAAAAAAQAAAAAAAAABbNcEeQAAAAVs1wUgAAAAHQAAAAAAAAABAAAAAAAAAAFs1wR5AAAABWzXBSAAAAAeAAAAAQAAAAEAAAAAAAAAAWzXBHcAAAAFbNcFIAAAAB8AAAABAAAAAQAAAAAAAAAFbNcFIAAAACAAAAAAAAAAAQAAAAAAAAAFbNcFIAAAACEAAAADAAAAAQAAAAEAAAAFbNcFIAAAACIAAAABAAAAAAAAAAAAAAAFbNcFIAAAACMAAAAAAAAAAQAAAAAAAAABbNcEeQAAAAVs1wUgAAAAJAAAAAAAAAABAAAAAAAAAAFs1wR5AAAABWzXBSAAAAAlAAAAAQAAAAEAAAAAAAAAAWzXBHcAAAAFbNcFIAAAACYAAAABAAAAAQAAAAAAAAAFbNcFIAAAACcAAAAAAAAAAQAAAAAAAAAFbNcFIAAAACgAAAADAAAAAQAAAAEAAAAFbNcFIAAAACkAAAABAAAAAAAAAAAAAAAFbNcFIAAAACoAAAAAAAAAAQAAAAAAAAAFbNcFIAAAACsAAAAAAAAAAQAAAAAAAAAFbNcFIAAAACwAAAACAAAAAAAAAAAAAAAFbNcFIAAAAC0AAAAAAAAAAQAAAAAAAAAFbNcFIAAAAC4AAAAAAAAAAQAAAAAAAAAFbNcFIAAAAC8AAAACAAAAAAAAAAAAAAAFbNcFIAAAADAAAAAAAAAAAQAAAAAAAAAFbNcFIAAAADEAAAABAAAAAAAAAAAAAAAFbNcFIAAAADIAAAAAAAAAAQAAAAAAAAABbNcEeQAAAAVs1wUgAAAAMwAAAAEAAAABAAAAAQAAAAJs1wQaAAAAAAAAAAVs1wUgAAAANAAAAAAAAAABAAAAAAAAAAVs1wUgAAAANQAAAAEAAAAAAAAAAAAAAAVs1wUgAAAANgAAAAAAAAABAAAAAAAAAAFs1wR5AAAABWzXBSAAAAA3AAAAAQAAAAEAAAABAAAABWzXBSAAAAA4AAAAAQAAAAAAAAAAAAAAAmzXBDkAAAAAAAAABWzXBSAAAAA5AAAAAQAAAAAAAAAAAAAABWzXBSAAAAA6AAAAAAAAAAEAAAAAAAAAAWzXBHkAAAAFbNcFIAAAADsAAAABAAAAAQAAAAEAAAAFbNcFIAAAADwAAAABAAAAAAAAAAAAAAAFbNcFIAAAAD0AAAAAAAAAAQAAAAAAAAABbNcEeQAAAAVs1wUgAAAAPgAAAAEAAAABAAAAAQAAAAVs1wUgAAAAPwAAAAEAAAAAAAAAAAAAAAVs1wUgAAAAQAAAAAAAAAABAAAAAAAAAAVs1wUgAAAAQQAAAAEAAAAAAAAAAAAAAAVs1wUgAAAAQgAAAAAAAAABAAAAAAAAAAVs1wUgAAAAQwAAAAEAAAAAAAAAAAAAAAVs1wUgAAAARAAAAAAAAAABAAAAAAAAAAFs1wR5AAAABWzXBSAAAABFAAAAAQAAAAEAAAABAAAABWzXBSAAAABGAAAAAQAAAAAAAAAAAAAABWzXBSAAAABHAAAAAAAAAAEAAAAAAAAAAWzXBHkAAAAFbNcFIAAAAEgAAAABAAAAAQAAAAEAAAAFbNcFIAAAAEkAAAABAAAAAAAAAAAAAAAFbNcFIAAAAEoAAAAAAAAAAQAAAAAAAAABbNcEeQAAAAVs1wUgAAAASwAAAAEAAAABAAAAAQAAAAFs1wR5AAAABWzXBSAAAABMAAAAAQAAAAAAAAAAAAAABWzXBSAAAABNAAAAAQAAAAAAAAAAAAAABWzXBSAAAABOAAAAAAAAAAEAAAAAAAAAAWzXBHkAAAAFbNcFIAAAAE8AAAABAAAAAQAAAAEAAAAFbNcFIAAAAFAAAAABAAAAAAAAAAAAAAAFbNcFIAAAAFEAAAAAAAAAAQAAAAAAAAABbNcEeQAAAAVs1wUgAAAAUgAAAAEAAAABAAAAAQAAAAVs1wUgAAAAUwAAAAEAAAAAAAAAAAAAAAVs1wUgAAAAVAAAAAAAAAABAAAAAAAAAAFs1wR5AAAABWzXBSAAAABVAAAAAQAAAAEAAAABAAAAAWzXBHkAAAAFbNcFIAAAAFYAAAABAAAAAAAAAAAAAAAFbNcFIAAAAFcAAAABAAAAAAAAAAAAAAAFbNcFIAAAAFgAAAAAAAAAAQAAAAAAAAABbNcEeQAAAAVs1wUgAAAAWQAAAAEAAAABAAAAAQAAAAVs1wUgAAAAWgAAAAEAAAAAAAAAAAAAAAVs1wUgAAAAWwAAAAAAAAABAAAAAAAAAAVs1wUgAAAAXAAAAAEAAAAAAAAAAAAAAAVs1wUgAAAAXQAAAAAAAAABAAAAAAAAAAVs1wUgAAAAXgAAAAEAAAAAAAAAAAAAAAVs1wUgAAAAXwAAAAAAAAABAAAAAAAAAAVs1wUgAAAAYAAAAAEAAAAAAAAAAAAAAAVs1wUgAAAAYQAAAAAAAAAAAAAAAAAAAAVs1wUgAAAAYgAAAAAAAAAAAAAAAAAAAAVs1wUgAAAAYwAAAAAAAAABAAAAAAAAAAVs1wUg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",
         jade.build.RecoveredHandles.resolve(Jade.class, "runRecoveredInitializationStep"),
         new Object[0]
      );
   }

   @Subscribe
   public void onTickEnd(TickEndEvent var1) {
      if (var1.eventPhase == EventPhase.END) {
         if (ClientUtils.isInWorld()) {
            FontManager.tick();
            SkinCache.processPendingLookup();
            if (InputHookManager.initializationFailed) {
               ClientUtils.sendColoredMessage("&cThere was an error, relaunch the game.");
               InputHookManager.initializationFailed = false;
            }

            MiddleClickFriend.Mz55();
            DangerousModules.checkLoadedModules();

            for (Module var3 : getModuleManager().getModules()) {
               Tlel(var3);
            }

            if (mc.currentScreen instanceof ClickGui && mc.thePlayer.getHealth() <= 0.0F) {
               mc.displayGuiScreen(null);
            }
         }

         if (YuysAp) {
            YuysAp = false;
            mc.displayGuiScreen(new JadeScreen());
         }

         if (fqS) {
            fqS = false;
            if (clickGui != null) {
               mc.displayGuiScreen(clickGui);
               clickGui.initMain();
            }
         }
      } else {
         MiddleClickFriend.IIroW8();
         if (mc.currentScreen == null && ClientUtils.isInWorld()) {
            for (ConfigEntry var7 : configManager.profiles) {
               var7.getProfile().pollKeybind();
            }
         } else if (ClientUtils.isInWorld()) {
            for (ConfigEntry var6 : configManager.profiles) {
               var6.getProfile().syncKeybind();
            }
         }
      }
   }

   @Subscribe
   public void onProfileLoad(ProfileLoadEvent var1) {
      clickGui.onSliderChange();
   }

   @Subscribe
   public void onSliderChange(SliderChangeEvent var1) {
      clickGui.onSliderChange();
   }

   @Subscribe
   public void onEntityJoinWorld(EntityJoinWorldEvent var1) {
      if (var1.entity == mc.thePlayer) {
         if (!JMvKkc) {
            JMvKkc = true;
         }

         PacketReplayController.getInstance().resetBuffer();
      }
   }

   public static ModuleManager getModuleManager() {
      return moduleManager;
   }

   public static ScheduledExecutorService getScheduler() {
      return scheduledExecutorService;
   }

   public static ExecutorService getExecutor() {
      return executorService;
   }

   public static void ddBk(BiConsumer<Boolean, String> var0) {
      if (!removalInProgress.compareAndSet(false, true)) {
         if (var0 != null) {
            var0.accept(Boolean.FALSE, "Jade removal is already in progress");
         }
      } else {
         try {
            if (moduleManager != null) {
               moduleManager.disableAll();
            }

            ExternalRenderer.shutdown();

            try {
            } catch (Throwable var10) {
            }

            try {
               SkinCache.shutdown();
            } catch (Throwable var9) {
            }

            try {
               PendingChatQueue.clearQueue();
            } catch (Throwable var8) {
            }

            try {
               IrcMessageBus.clearAllProperties();
            } catch (Throwable var7) {
            }

            try {
               WindowIcon.restoreDefaultIcon();
            } catch (Throwable var6) {
            }

            try {
               ClientUtils.eqyXnoq();
            } catch (Throwable var5) {
            }

            try {
               if (mc != null && mc.gameSettings != null && mc.gameSettings.keyBindings != null) {
                  for (KeyBinding var4 : mc.gameSettings.keyBindings) {
                     if (var4 != null) {
                        KeyBinding.setKeyBindState(var4.getKeyCode(), false);
                     }
                  }
               }
            } catch (Throwable var11) {
            }

            EventBus.clear();
            removeShutdownHookQuietly(schedulerShutdownHook);
            removeShutdownHookQuietly(Ag9);
            schedulerShutdownHook = null;
            Ag9 = null;
            scheduledExecutorService.shutdownNow();
            executorService.shutdownNow();
            InjectionAgent.detach(var0);
         } catch (Throwable var12) {
            if (var0 != null) {
               var0.accept(Boolean.FALSE, "Jade cleanup failed; restart Minecraft: " + InjectionAgent.describeFailure(var12));
            }
         }
      }
   }

   private static void removeShutdownHookQuietly(Thread var0) {
      if (var0 != null) {
         try {
            Runtime.getRuntime().removeShutdownHook(var0);
         } catch (IllegalStateException var2) {
         }
      }
   }

   public static KeystrokesHud getHudRenderer() {
      return hudRenderer;
   }

   public static void requestOpenJadeScreen() {
      YuysAp = true;
   }

   public static void requestOpenClickGui() {
      fqS = true;
   }

   public static void openPendingScreen() {
      if (YuysAp) {
         YuysAp = false;
         mc.displayGuiScreen(new JadeScreen());
      }

      if (fqS) {
         fqS = false;
         if (clickGui != null) {
            mc.displayGuiScreen(clickGui);
            clickGui.initMain();
         }
      }
   }

   public static void refreshTimerModuleKeybind() {
      if (moduleManager != null && ClientUtils.isInWorld() && getModuleManager().getModule(Timer.class) != null && getModuleManager().getModule(Timer.class).isEnabled()) {
         updateModuleKeybindState(getModuleManager().getModule(Timer.class), mc.currentScreen instanceof ClickGui);
      }
   }

   public static void WTxmo(int var0, boolean var1) {
      if (var0 != 0) {
         dispatchKeyEvent(var0, var1);
      }
   }

   public static void oMds(int var0, boolean var1) {
      if (var0 >= 0) {
         dispatchKeyEvent(var0 + 1000, var1);
      }
   }

   public static void handleMouseWheel(int var0) {
      if (var0 != 0) {
         int var1 = var0 > 0 ? 1069 : 1070;
         dispatchKeyEvent(var1, true);
         dispatchKeyEvent(var1, false);
      }
   }

   public static void syncKeybindsForKey(int var0) {
      if (moduleManager != null) {
         for (Module var2 : moduleManager.getModules()) {
            if (var2.getKeycode() == var0) {
               var2.syncKeybind();
            }
         }

         if (configManager != null && configManager.profiles != null) {
            for (ConfigEntry var5 : configManager.profiles) {
               ConfigProfile var3 = var5.getProfile();
               if (var3.getKeycode() == var0) {
                  var3.syncKeybind();
               }
            }
         }
      }
   }

   private static void dispatchKeyEvent(int var0, boolean var1) {
      if (moduleManager != null) {
         boolean var2 = !var1 || mc.currentScreen == null && ClientUtils.isInWorld();

         for (Module var4 : moduleManager.getModules()) {
            if (!var1 || var2 && var4.canBeEnabled()) {
               var4.onKey(var0, var1);
            }
         }

         if (configManager != null && configManager.profiles != null) {
            for (ConfigEntry var6 : configManager.profiles) {
               if (!var1 || var2) {
                  var6.getProfile().onKey(var0, var1);
               }
            }
         }
      }
   }

   private static void Tlel(Module var0) {
      try {
         if (mc.currentScreen == null && var0.canBeEnabled()) {
            var0.pollKeybind();
         } else if (mc.currentScreen instanceof ClickGui) {
            var0.guiUpdate();
            var0.syncKeybind();
         } else {
            var0.syncKeybind();
         }
      } catch (Throwable var3) {
         logModuleLoopFailure(var0, "keybind", var3);
      }

      if (var0.isEnabled()) {
         try {
            var0.onUpdate();
         } catch (Throwable var2) {
            logModuleLoopFailure(var0, "update", var2);
         }
      }
   }

   private static void updateModuleKeybindState(Module var0, boolean var1) {
      try {
         if (mc.currentScreen == null && var0.canBeEnabled()) {
            var0.pollKeybind();
         } else {
            if (var1) {
               var0.guiUpdate();
            }

            var0.syncKeybind();
         }
      } catch (Throwable var3) {
         logModuleLoopFailure(var0, "keybind", var3);
      }
   }

   private static void logModuleLoopFailure(Module var0, String var1, Throwable var2) {
      String var3 = var0.getName() + ":" + var1 + ":" + var2.getClass().getName();
      if (LbegRj.add(var3)) {
         ClientUtils.logger.error("Module {} {} loop failed", new Object[]{var0.getName(), var1, var2});
      }
   }

   private static Object runRecoveredInitializationStep(int var0, Object[] var1) throws Throwable {
      switch (var0) {
         case 0:
            return initializationState;
         case 1:
            return Jade$0.INITIALIZED;
         case 2:
            return initializationState;
         case 3:
            return Jade$0.INITIALIZING;
         case 4:
            return RecoveredMethodInterpreter.initializeClass(IllegalStateException.class);
         case 5:
            return RecoveredMethodInterpreter.initializeClass(StringBuilder.class);
         case 6:
            return new StringBuilder();
         case 7:
            return "recursive Jade initialization at ";
         case 8:
            return ((StringBuilder)var1[0]).append((String)var1[1]);
         case 9:
            return pcw;
         case 10:
            return ((StringBuilder)var1[0]).append((String)var1[1]);
         case 11:
            return ((StringBuilder)var1[0]).toString();
         case 12:
            return new IllegalStateException((String)var1[1]);
         case 13:
            return initializationState;
         case 14:
            return Jade$0.FAILED;
         case 15:
            return RecoveredMethodInterpreter.initializeClass(IllegalStateException.class);
         case 16:
            return RecoveredMethodInterpreter.initializeClass(StringBuilder.class);
         case 17:
            return new StringBuilder();
         case 18:
            return "Jade initialization previously failed at ";
         case 19:
            return ((StringBuilder)var1[0]).append((String)var1[1]);
         case 20:
            return pcw;
         case 21:
            return ((StringBuilder)var1[0]).append((String)var1[1]);
         case 22:
            return ((StringBuilder)var1[0]).toString();
         case 23:
            return new IllegalStateException((String)var1[1]);
         case 24:
            return Jade$0.INITIALIZING;
         case 25:
            initializationState = (Jade$0)var1[0];
            return null;
         case 26:
            return "shutdown_hooks";
         case 27:
            pcw = (String)var1[0];
            return null;
         case 28:
            return RecoveredMethodInterpreter.initializeClass(Thread.class);
         case 29:
            return scheduledExecutorService;
         case 30:
            return var1[0].getClass();
         case 31:
            return (Runnable) ((ScheduledExecutorService)var1[0])::shutdown;
         case 32:
            return "jade-scheduled-shutdown";
         case 33:
            return new Thread((Runnable)var1[1], (String)var1[2]);
         case 34:
            schedulerShutdownHook = (Thread)var1[0];
            return null;
         case 35:
            return RecoveredMethodInterpreter.initializeClass(Thread.class);
         case 36:
            return executorService;
         case 37:
            return var1[0].getClass();
         case 38:
            return (Runnable) ((ExecutorService)var1[0])::shutdown;
         case 39:
            return "jade-cached-shutdown";
         case 40:
            return new Thread((Runnable)var1[1], (String)var1[2]);
         case 41:
            Ag9 = (Thread)var1[0];
            return null;
         case 42:
            return Runtime.getRuntime();
         case 43:
            return schedulerShutdownHook;
         case 44:
            ((Runtime)var1[0]).addShutdownHook((Thread)var1[1]);
            return null;
         case 45:
            return Runtime.getRuntime();
         case 46:
            return Ag9;
         case 47:
            ((Runtime)var1[0]).addShutdownHook((Thread)var1[1]);
            return null;
         case 48:
            return "module_manager";
         case 49:
            pcw = (String)var1[0];
            return null;
         case 50:
            return RecoveredMethodInterpreter.initializeClass(Jade.class);
         case 51:
            return new Jade();
         case 52:
            return "event_handlers";
         case 53:
            pcw = (String)var1[0];
            return null;
         case 54:
            return RecoveredMethodInterpreter.initializeClass(KeystrokesHud.class);
         case 55:
            return new KeystrokesHud();
         case 56:
            hudRenderer = (KeystrokesHud)var1[0];
            return null;
         case 57:
            EventBus.register(var1[0]);
            return null;
         case 58:
            return RecoveredMethodInterpreter.initializeClass(ConnectionStatsOverlay.class);
         case 59:
            return new ConnectionStatsOverlay();
         case 60:
            EventBus.register(var1[0]);
            return null;
         case 61:
            return RecoveredMethodInterpreter.initializeClass(MiddleClickFriend.class);
         case 62:
            return new MiddleClickFriend();
         case 63:
            EventBus.register(var1[0]);
            return null;
         case 64:
            return RotationHandler.getInstance();
         case 65:
            EventBus.register(var1[0]);
            return null;
         case 66:
            return hudRenderer;
         case 67:
            EventBus.register(var1[0]);
            return null;
         case 68:
            return RecoveredMethodInterpreter.initializeClass(PingChecker.class);
         case 69:
            return new PingChecker();
         case 70:
            EventBus.register(var1[0]);
            return null;
         case 71:
            return RecoveredMethodInterpreter.initializeClass(BanTracker.class);
         case 72:
            return new BanTracker();
         case 73:
            EventBus.register(var1[0]);
            return null;
         case 74:
            return RecoveredMethodInterpreter.initializeClass(PlayerListTracker.class);
         case 75:
            return new PlayerListTracker();
         case 76:
            playerListTracker = (PlayerListTracker)var1[0];
            return null;
         case 77:
            EventBus.register(var1[0]);
            return null;
         case 78:
            return RecoveredMethodInterpreter.initializeClass(PlayerPacketStateTracker.class);
         case 79:
            return new PlayerPacketStateTracker();
         case 80:
            EventBus.register(var1[0]);
            return null;
         case 81:
            return RecoveredMethodInterpreter.initializeClass(WhisperShortcuts.class);
         case 82:
            return new WhisperShortcuts();
         case 83:
            EventBus.register(var1[0]);
            return null;
         case 84:
            return RecoveredMethodInterpreter.initializeClass(QueuedPacketDispatcher.class);
         case 85:
            return new QueuedPacketDispatcher();
         case 86:
            nbT = (QueuedPacketDispatcher)var1[0];
            return null;
         case 87:
            EventBus.register(var1[0]);
            return null;
         case 88:
            return RecoveredMethodInterpreter.initializeClass(BedwarsUtils$2.class);
         case 89:
            return new BedwarsUtils$2();
         case 90:
            EventBus.register(var1[0]);
            return null;
         case 91:
            return SkywarsGameState.YFaX();
         case 92:
            EventBus.register(var1[0]);
            return null;
         case 93:
            return JengaGame.getInstance();
         case 94:
            EventBus.register(var1[0]);
            return null;
         case 95:
            return "reflection_and_fonts";
         case 96:
            pcw = (String)var1[0];
            return null;
         case 97:
            InputHookManager.initializeReflection();
            return null;
         case 98:
            FontManager.init();
            return null;
         case 99:
            return "relations_and_modules";
         case 100:
            pcw = (String)var1[0];
            return null;
         case 101:
            return RecoveredMethodInterpreter.initializeClass(RelationManager.class);
         case 102:
            return new RelationManager();
         case 103:
            relationManager = (RelationManager)var1[0];
            return null;
         case 104:
            return relationManager;
         case 105:
            ((RelationManager)var1[0]).load();
            return null;
         case 106:
            return moduleManager;
         case 107:
            ((ModuleManager)var1[0]).registerModules();
            return null;
         case 108:
            return RecoveredMethodInterpreter.initializeClass(SwvyvpD.class);
         case 109:
            return new SwvyvpD();
         case 110:
            EventBus.register(var1[0]);
            return null;
         case 111:
            return RecoveredMethodInterpreter.initializeClass(ExternalRenderer.class);
         case 112:
            return new ExternalRenderer();
         case 113:
            EventBus.register(var1[0]);
            return null;
         case 114:
            return "gui";
         case 115:
            pcw = (String)var1[0];
            return null;
         case 116:
            return RecoveredMethodInterpreter.initializeClass(JadeClickGui.class);
         case 117:
            return new JadeClickGui();
         case 118:
            clickGui = (ClickGui)var1[0];
            return null;
         case 119:
            return RecoveredMethodInterpreter.initializeClass(ThemeManager.class);
         case 120:
            return Minecraft.getMinecraft();
         case 121:
            return ((Minecraft)var1[0]).mcDataDir;
         case 122:
            return new ThemeManager((File)var1[1]);
         case 123:
            themeManager = (ThemeManager)var1[0];
            return null;
         case 124:
            return RecoveredMethodInterpreter.initializeClass(ConfigManager.class);
         case 125:
            return new ConfigManager();
         case 126:
            configManager = (ConfigManager)var1[0];
            return null;
         case 127:
            return "configs";
         case 128:
            pcw = (String)var1[0];
            return null;
         case 129:
            return configManager;
         case 130:
            ((ConfigManager)var1[0]).loadProfiles();
            return null;
         case 131:
            return configManager;
         case 132:
            ((ConfigManager)var1[0]).loadStartupProfile();
            return null;
         case 133:
            return "keybindings";
         case 134:
            pcw = (String)var1[0];
            return null;
         case 135:
            InputHookManager.registerKeybindings();
            return null;
         case 136:
            return "commands_and_accounts";
         case 137:
            pcw = (String)var1[0];
            return null;
         case 138:
            return RecoveredMethodInterpreter.initializeClass(CommandManager.class);
         case 139:
            return new CommandManager();
         case 140:
            commandManager = (CommandManager)var1[0];
            return null;
         case 141:
            AccountStore.loadFromDisk();
            return null;
         case 142:
            return "complete";
         case 143:
            pcw = (String)var1[0];
            return null;
         case 144:
            return Jade$0.INITIALIZED;
         case 145:
            initializationState = (Jade$0)var1[0];
            return null;
         case 146:
            return Jade$0.FAILED;
         case 147:
            initializationState = (Jade$0)var1[0];
            return null;
         case 148:
            return RecoveredMethodInterpreter.initializeClass(IllegalStateException.class);
         case 149:
            return RecoveredMethodInterpreter.initializeClass(StringBuilder.class);
         case 150:
            return new StringBuilder();
         case 151:
            return "Jade initialization failed at stage ";
         case 152:
            return ((StringBuilder)var1[0]).append((String)var1[1]);
         case 153:
            return pcw;
         case 154:
            return ((StringBuilder)var1[0]).append((String)var1[1]);
         case 155:
            return ((StringBuilder)var1[0]).toString();
         case 156:
            return new IllegalStateException((String)var1[1], (Throwable)var1[2]);
         case 157:
            return Integer.valueOf((var1[0] instanceof Throwable) ? 1 : 0);
         default:
            throw new IllegalArgumentException();
      }
   }
}
