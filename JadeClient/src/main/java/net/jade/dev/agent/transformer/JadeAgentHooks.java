// Jade recovery: recovered class name: JadeAgentHooks
package net.jade.dev.agent.transformer;

import io.netty.channel.Channel;
import jade.client.Jade;
import jade.client.common.ChatUtils;
import jade.client.common.ClientUtils;
import jade.client.common.EventBus;
import jade.client.common.EventPhase;
import jade.client.common.FakePlayerRenderer;
import jade.client.common.IrcChatHandler;
import jade.client.common.PacketUtils;
import jade.client.common.ProxyManager;
import jade.client.common.RotationHandler;
import jade.client.common.RotationUtils;
import jade.client.common.PlayerPacketStateTracker;
import jade.client.event.AttackEntityEvent;
import jade.client.event.ChangeCurrentItemEvent;
import jade.client.event.ChatReceivedEvent;
import jade.client.event.ClickMouseEvent;
import jade.client.event.CollisionBoxesEvent;
import jade.client.event.DisconnectEvent;
import jade.client.event.DrawSelectionBoxEvent;
import jade.client.event.EntityJoinWorldEvent;
import jade.client.event.ExplosionEvent;
import jade.client.event.GameLoopEvent;
import jade.client.event.GuiDisplayEvent;
import jade.client.event.GuiKeyboardInputEvent;
import jade.client.event.GuiOpenEvent;
import jade.client.event.JumpEvent;
import jade.client.event.LeftClickEvent;
import jade.client.event.LoadWorldEvent;
import jade.client.event.MouseEvent;
import jade.client.event.MouseOverEvent;
import jade.client.event.MoveEntityWithHeadingEvent;
import jade.client.event.MoveFlyingEvent;
import jade.client.event.MoveInputEvent;
import jade.client.event.MoveStateUpdateEvent;
import jade.client.event.PacketDispatchEvent;
import jade.client.event.PacketReceiveEvent;
import jade.client.event.PacketSendEvent;
import jade.client.event.PlayerAttackEvent;
import jade.client.event.PlayerMoveEvent;
import jade.client.event.PostUpdateEvent;
import jade.client.event.PrePlayerInteractEvent;
import jade.client.event.PreUpdateEvent;
import jade.client.event.RenderLivingPostEvent;
import jade.client.event.RenderLivingPreEvent;
import jade.client.event.RenderPlayerPostEvent;
import jade.client.event.RenderPlayerPreEvent;
import jade.client.event.RenderTickEvent;
import jade.client.event.RenderWorldLastEvent;
import jade.client.event.RightClickDelayEvent;
import jade.client.event.RightClickEvent;
import jade.client.event.SetCurrentItemEvent;
import jade.client.event.SilentPacketSendEvent;
import jade.client.event.StepHeightEvent;
import jade.client.event.TickEndEvent;
import jade.client.event.TickEvent;
import jade.client.event.TickStartEvent;
import jade.client.event.UpdateWalkingPlayerEvent;
import jade.client.event.UseItemEvent;
import jade.client.event.VectorForRotationEvent;
import jade.client.event.VelocityEvent;
import jade.client.event.PostWalkingUpdateEvent;
import jade.client.hook.ItemUseHelper;
import jade.client.hook.ClassLoaderAssetCache;
import jade.client.hook.ProxyAltsButton;
import jade.client.hook.UpdateNotification;
import jade.client.module.Module;
import jade.client.module.client.Gui;
import jade.client.module.client.Settings;
import jade.client.module.combat.AutoClicker;
import jade.client.module.combat.KeepSprint;
import jade.client.module.combat.Piercing;
import jade.client.module.combat.SprintReset;
import jade.client.module.minigames.AutoChest;
import jade.client.module.minigames.BedwarsUtils;
import jade.client.module.minigames.Opsec;
import jade.client.module.minigames.TabStats;
import jade.client.module.movement.NoSlow;
import jade.client.module.movement.Sprint;
import jade.client.module.other.Anticheat;
import jade.client.module.other.Denick;
import jade.client.module.player.BedNuker;
import jade.client.module.player.BridgeNuker;
import jade.client.module.player.DelayRemover;
import jade.client.module.player.FastBreak;
import jade.client.module.player.Freecam;
import jade.client.module.player.GhostHand;
import jade.client.module.player.InventoryManager;
import jade.client.module.player.Scaffold;
import jade.client.module.render.AntiDebuff;
import jade.client.module.render.AntiInvis;
import jade.client.module.render.Cape;
import jade.client.module.render.ESP;
import jade.client.module.render.FreeLook;
import jade.client.module.render.Nametags;
import jade.client.module.render.NoCameraClip;


import jade.deps.loader107.StartupAnnouncement$0;
import jade.deps.loader107.StartupAnnouncement;

import jade.deps.loader107.LocalCoreLoader;

import jade.deps.loader107.IrcMessageBus$1;
import jade.deps.loader107.IrcMessageBus;

import jade.deps.loader107.SubscriptionState;

import jade.deps.loader107.PendingChatQueue;
import jade.inject.InjectionAgent;
import jade.inject.RuntimeAccess;
import jade.mixin.impl.accessor.IAccessorGuiTextField;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.zip.CRC32;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.potion.Potion;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public final class JadeAgentHooks {
   private static final float ANNOUNCEMENT_OPEN_PITCH = 1.75F;
   private static final float ANNOUNCEMENT_CLOSE_PITCH = 0.55F;
   private static final float CHAT_MESSAGE_PITCH = 1.15F;
   private static boolean shownFirstWorldAnnouncement;
   private static boolean announcementSoundActive;
   private static long announcementSoundStartedAt = -1L;
   private static Entity freelookEntity;
   private static float freelookRotationYaw;
   private static float freelookPrevRotationYaw;
   private static float freelookRotationPitch;
   private static float freelookPrevRotationPitch;
   private static final Map<Object, Float> PRE_DAMAGE_PROGRESS = new WeakHashMap<>();
   private static final Map<Object, float[]> RENDER_PITCH_STATE = new WeakHashMap<>();
   private static final Map<Object, Object[]> ITEM_RENDER_STATE = new WeakHashMap<>();
   private static final Map<Object, Object> CONTAINER_DRAW_SLOT = new WeakHashMap<>();
   private static final Map<Object, Object[]> CHAT_COMPLETIONS = new WeakHashMap<>();
   private static final Map<Object, Map<ResourceLocation, Boolean>> RESOURCE_EXISTENCE = new WeakHashMap<>();
   private static final Map<Object, Map<ResourceLocation, byte[]>> RESOURCE_CONTENTS = new WeakHashMap<>();
   private static final Map<Object, Set<ResourceLocation>> RESOURCE_MISSING = new WeakHashMap<>();
   private static final Map<Object, Set<ResourceLocation>> PENDING_TEXTURES = new WeakHashMap<>();
   private static final Map<Object, String> SOUND_FINGERPRINTS = new WeakHashMap<>();
   private static final Map<Object, Boolean> SOUND_RESTARTS = new WeakHashMap<>();
   private static final Set<String> LOGGED_FAILURES = Collections.newSetFromMap(new ConcurrentHashMap<>());
   private static final ConcurrentLinkedQueue<Object[]> BADLION_ENTITY_JOINS = new ConcurrentLinkedQueue<>();
   private static volatile Object badlionWorld;
   private static volatile Object badlionPlayer;
   private static volatile boolean mixinEventBridgeActive;
   private static volatile boolean startupAttempted;
   private static volatile boolean prePlayerInteractPostedThisTick;

   private JadeAgentHooks() {
   }

   public static void markMixinEventBridgeActive() {
      mixinEventBridgeActive = InjectionAgent.isForgeRuntime();
   }

   public static void onStartGame() {
      ensureJadeStarted();
   }

   public static boolean onCapeWearState(Object playerObj, Object partObj, boolean original) {
      if (mixinEventBridgeActive) {
         return original;
      } else {
         try {
            if (partObj == EnumPlayerModelParts.CAPE && playerObj == minecraft().thePlayer && Cape.isCapeEnabled()) {
               return true;
            }
         } catch (Throwable var4) {
            log("onCapeWearState", var4);
         }

         return original;
      }
   }

   public static Object onCapeLocation(Object playerObj, Object original) {
      if (mixinEventBridgeActive) {
         return original;
      } else {
         try {
            if (playerObj == minecraft().thePlayer && Cape.isCapeEnabled()) {
               ResourceLocation cape = Cape.FOsFcam();
               if (cape != null) {
                  return cape;
               }
            }
         } catch (Throwable var3) {
            log("onCapeLocation", var3);
         }

         return original;
      }
   }

   public static void onEntityJoinWorld(Object worldObj, Object entityObj) {
      if (!mixinEventBridgeActive) {
         try {
            World world = (World)worldObj;
            if (!world.isRemote) {
               return;
            }

            if (InjectionAgent.isBadlionRuntime()) {
               BADLION_ENTITY_JOINS.add(new Object[]{world, entityObj});
            } else {
               EventBus.post(new EntityJoinWorldEvent((Entity)entityObj, world));
            }
         } catch (Throwable var3) {
            log("onEntityJoinWorld", var3);
         }
      }
   }

   public static void onBadlionWorldTick(Object minecraftObj) {
      if (!mixinEventBridgeActive) {
         try {
            Minecraft mc = (Minecraft)minecraftObj;
            World world = mc.theWorld;
            Entity player = mc.thePlayer;
            if (world == null || player == null) {
               badlionWorld = null;
               badlionPlayer = null;
               BADLION_ENTITY_JOINS.clear();
               return;
            }

            if (world != badlionWorld || player != badlionPlayer) {
               badlionWorld = world;
               badlionPlayer = player;
               EventBus.post(new EntityJoinWorldEvent(player, world));
            }

            Object[] joined;
            while ((joined = BADLION_ENTITY_JOINS.poll()) != null) {
               if (joined[0] == world && joined[1] != player) {
                  EventBus.post(new EntityJoinWorldEvent((Entity)joined[1], world));
               }
            }
         } catch (Throwable var5) {
            log("onBadlionWorldTick", var5);
         }
      }
   }

   public static boolean onItemHasEffect(Object ignored) {
      return !mixinEventBridgeActive && ESP.overlayPassActive;
   }

   public static Object onFontDraw(Object rendererObj, Object textObj, float x, float y, int color, boolean shadow) {
      if (mixinEventBridgeActive && !InjectionAgent.isBadlionRuntime()) {
         return null;
      } else {
         try {
            String text = (String)textObj;
            if (text != null && text.length() >= 4 && ChatUtils.hasCachedText()) {
               InjectionAgent.recordBadlionGradient(
                  "font.candidate",
                  "renderer="
                     + (
                        rendererObj == null
                           ? "null"
                           : rendererObj.getClass().getName()
                     )
                     + " mixin="
                     + mixinEventBridgeActive
                     + " chatDepth="
                     + ChatUtils.isRenderingChat()
                     + " gradient="
                     + ChatUtils.uDc8()
                     + " text="
                     + summarizeGradientText(text)
               );
            }

            if (text != null && text.indexOf("Jade") >= 0) {
               InjectionAgent.recordBadlionGradient(
                  "font.entry",
                  "renderer="
                     + rendererObj.getClass().getName()
                     + " length="
                     + text.length()
                     + " color="
                     + color
               );
            }

            if (text != null && text.length() >= 4 && ChatUtils.hasCachedText() && !ChatUtils.uDc8()) {
               String raw = ChatUtils.getOriginalFormattedText(text);
               if (raw == null && ChatUtils.isRenderingChat()) {
                  raw = ChatUtils.getRegisteredFormatting(text);
               }

               if (raw == null && InjectionAgent.isBadlionRuntime()) {
                  raw = ChatUtils.restoreFormattedText(text);
               }

               if (raw != null && !ChatUtils.hasObfuscatedCode(raw)) {
                  InjectionAgent.recordBadlionGradient(
                     "font.match",
                     "rawLength="
                        + raw.length()
                        + " shadow="
                        + shadow
                  );
                  ChatUtils.setCustomDrawActive(true);

                  Integer var8;
                  try {
                     var8 = ChatUtils.UCYxg((FontRenderer)rendererObj, raw, x, y, color, shadow);
                  } finally {
                     ChatUtils.setCustomDrawActive(false);
                  }

                  return var8;
               } else {
                  return null;
               }
            } else {
               return null;
            }
         } catch (Throwable var13) {
            log("onFontDraw", var13);
            return null;
         }
      }
   }

   public static Object onChatDrawString(Object rendererObj, Object textObj, float x, float y, int color) {
      if (mixinEventBridgeActive && !InjectionAgent.isBadlionRuntime()) {
         return null;
      } else {
         try {
            String text = (String)textObj;
            if (text != null && text.length() >= 4 && ChatUtils.hasCachedText()) {
               InjectionAgent.recordBadlionGradient(
                  "chat.candidate",
                  "renderer="
                     + (
                        rendererObj == null
                           ? "static"
                           : rendererObj.getClass().getName()
                     )
                     + " chatDepth="
                     + ChatUtils.isRenderingChat()
                     + " text="
                     + summarizeGradientText(text)
               );
            }

            if (text != null && text.indexOf("Jade") >= 0) {
               InjectionAgent.recordBadlionGradient(
                  "chat.entry",
                  "renderer="
                     + (rendererObj == null ? "static" : rendererObj.getClass().getName())
                     + " length="
                     + text.length()
                     + " color="
                     + color
               );
            }

            String raw = ChatUtils.restoreFormattedText(text);
            if (text != null && text.indexOf("Jade") >= 0) {
               InjectionAgent.recordBadlionGradient(
                  "chat.lookup",
                  raw == null
                     ? "miss"
                     : "match rawLength=" + raw.length()
               );
            }

            if (raw != null && !ChatUtils.hasObfuscatedCode(raw) && !ChatUtils.uDc8()) {
               FontRenderer renderer = rendererObj instanceof FontRenderer ? (FontRenderer)rendererObj : minecraft().fontRendererObj;
               if (renderer == null) {
                  return null;
               } else {
                  InjectionAgent.recordBadlionGradient(
                     "chat.renderer",
                     "selected=" + renderer.getClass().getName()
                  );
                  ChatUtils.setCustomDrawActive(true);

                  Integer var8;
                  try {
                     var8 = ChatUtils.UCYxg(renderer, raw, x, y, color, true);
                  } finally {
                     ChatUtils.setCustomDrawActive(false);
                  }

                  return var8;
               }
            } else {
               return null;
            }
         } catch (Throwable var13) {
            log("onChatDrawString", var13);
            return null;
         }
      }
   }

   public static boolean onBadlionCachedChatDraw(
      Object cacheObj, Object layerObj, Object renderIdObj, Object wrapperObj, int x, int y, int color, boolean shadow
   ) {
      if (InjectionAgent.isBadlionRuntime() && wrapperObj != null && !ChatUtils.uDc8() && ChatUtils.hasCachedText()) {
         try {
            Object componentObj = invokeAny(wrapperObj, new String[]{"bJI"});
            if (!(componentObj instanceof IChatComponent)) {
               return false;
            } else {
               String displayed = ((IChatComponent)componentObj).getFormattedText();
               String raw = ChatUtils.restoreFormattedText(displayed);
               if (raw != null && !ChatUtils.hasObfuscatedCode(raw)) {
                  Minecraft mc = minecraft();
                  if (mc != null && mc.fontRendererObj != null) {
                     ChatUtils.setCustomDrawActive(true);

                     try {
                        ChatUtils.UCYxg(mc.fontRendererObj, raw, x, y, color, shadow);
                     } finally {
                        ChatUtils.setCustomDrawActive(false);
                     }

                     InjectionAgent.recordBadlionGradient(
                        "cached.match",
                        "wrapper="
                           + wrapperObj.getClass().getName()
                           + " x="
                           + x
                           + " y="
                           + y
                           + " shadow="
                           + shadow
                     );
                     return true;
                  } else {
                     return false;
                  }
               } else {
                  return false;
               }
            }
         } catch (Throwable var16) {
            log("onBadlionCachedChatDraw", var16);
            InjectionAgent.recordBadlionGradient(
               "cached.failure",
               var16.getClass().getName() + ":" + var16.getMessage()
            );
            return false;
         }
      } else {
         return false;
      }
   }

   public static void onNetworkChannelInitialized(Object channelObj) {
      if (!mixinEventBridgeActive) {
         try {
            ProxyManager.installProxyHandler((Channel)channelObj);
         } catch (Throwable var2) {
            log("onNetworkChannelInitialized", var2);
         }
      }
   }

   public static Object onNetworkBootstrapHandler(Object handlerObj) {
      if (!mixinEventBridgeActive && InjectionAgent.isBadlionRuntime()) {
         try {
            return ProxyManager.wrapChannelHandler(handlerObj);
         } catch (Throwable var2) {
            log("onNetworkBootstrapHandler", var2);
            return handlerObj;
         }
      } else {
         return handlerObj;
      }
   }

   private static String summarizeGradientText(String text) {
      StringBuilder summary = new StringBuilder();
      int limit = Math.min(text.length(), 96);

      for (int i = 0; i < limit; i++) {
         char character = text.charAt(i);
         if (character == 167) {
            summary.append('&');
         } else if (character >= ' ' && character != 127) {
            summary.append(character);
         } else {
            summary.append('?');
         }
      }

      if (text.length() > limit) {
         summary.append("...");
      }

      return summary.toString();
   }

   public static void onChatRenderBegin(Object ignored) {
      if (!mixinEventBridgeActive || InjectionAgent.isBadlionRuntime()) {
         ChatUtils.enterChatRender();
      }
   }

   public static void onChatRenderEnd(Object ignored) {
      if (!mixinEventBridgeActive || InjectionAgent.isBadlionRuntime()) {
         ChatUtils.exitChatRender();
      }
   }

   public static void onChatLineSet(Object chatObj, Object componentObj, int chatLineId, int updateCounter, boolean displayOnly) {
      if (!mixinEventBridgeActive || InjectionAgent.isBadlionRuntime()) {
         try {
            IChatComponent component = (IChatComponent)componentObj;
            String raw = ChatUtils.getOriginalFormattedText(component.getFormattedText());
            if (raw == null) {
               return;
            }

            Object value = getField(chatObj, new String[]{"drawnChatLines", "field_146253_i"});
            if (!(value instanceof List)) {
               return;
            }

            for (Object item : (List)value) {
               ChatLine line = (ChatLine)item;
               if (line.getUpdatedCounter() != updateCounter
                  || chatLineId != 0 && line.getChatLineID() != chatLineId
                  || !ChatUtils.registerFormatting(line.getChatComponent().getFormattedText(), raw)) {
                  break;
               }
            }
         } catch (Throwable var11) {
            log("onChatLineSet", var11);
         }
      }
   }

   public static boolean onTabRender() {
      if (mixinEventBridgeActive) {
         return false;
      } else {
         try {
            Minecraft mc = minecraft();
            if (mc != null && mc.theWorld != null) {
               Scoreboard scoreboard = mc.theWorld.getScoreboard();
               return onTabRender(new ScaledResolution(mc).getScaledWidth(), scoreboard, scoreboard.getObjectiveInDisplaySlot(0));
            } else {
               return false;
            }
         } catch (Throwable var2) {
            log("onTabRender", var2);
            return false;
         }
      }
   }

   public static boolean onTabRender(int screenWidth, Object scoreboardObj, Object objectiveObj) {
      if (mixinEventBridgeActive) {
         return false;
      } else {
         try {
            TabStats tabStats = Jade.getModuleManager().getModule(TabStats.class);
            return tabStats != null && tabStats.renderTabStats(screenWidth, (Scoreboard)scoreboardObj, (ScoreObjective)objectiveObj);
         } catch (Throwable var4) {
            log("onTabRender", var4);
            return false;
         }
      }
   }

   public static Object onTabPlayerName(Object infoObj, Object originalObj) {
      if (mixinEventBridgeActive) {
         return originalObj;
      } else {
         try {
            NetworkPlayerInfo info = (NetworkPlayerInfo)infoObj;
            if (info != null && info.getGameProfile() != null) {
               Denick denick = Jade.getModuleManager().getModule(Denick.class);
               String marker = denick == null ? "" : denick.getTabMarker(info);
               Anticheat anticheat = Jade.getModuleManager().getModule(Anticheat.class);
               if (anticheat != null && anticheat.WfMt(info.getGameProfile().getId(), info.getGameProfile().getName())) {
                  marker = marker + " \u00a76\u26a0\u00a7r";
               }

               String original = (String)originalObj;
               return !marker.isEmpty() && original != null ? original + marker : original;
            } else {
               return originalObj;
            }
         } catch (Throwable var7) {
            log("onTabPlayerName", var7);
            return originalObj;
         }
      }
   }

   public static void onGuiScreenDraw(Object screenObj) {
      if (!mixinEventBridgeActive) {
         try {
            ProxyAltsButton.WXs6((Minecraft)getField(screenObj, new String[]{"mc", "field_146297_k"}));
         } catch (Throwable var2) {
            log("onGuiScreenDraw", var2);
         }
      }
   }

   public static boolean onGuiChatSend(Object screenObj, Object messageObj, boolean addToChat) {
      if (!mixinEventBridgeActive && addToChat) {
         try {
            String message = (String)messageObj;
            Minecraft mc = (Minecraft)getField(screenObj, new String[]{"mc", "field_146297_k"});
            if (handleLocalChatMessage(mc, message)) {
               InjectionAgent.recordRuntimeSignal(
                  "chat",
                  "outgoing.local_handled_at_gui"
               );
               return true;
            }
         } catch (Throwable var5) {
            log("onGuiChatSend", var5);
         }

         return false;
      } else {
         return false;
      }
   }

   private static boolean handleLocalChatMessage(Minecraft mc, String message) {
      if (mc == null || message == null) {
         return false;
      } else if (IrcChatHandler.handleChatInput(message)) {
         mc.ingameGUI.getChatGUI().addToSentMessages(message);
         return true;
      } else if (Jade.commandManager != null && Jade.commandManager.DCXw(message)) {
         mc.ingameGUI.getChatGUI().addToSentMessages(message);
         return true;
      } else {
         return false;
      }
   }

   public static boolean onGuiKeyboardInput(Object ignored) {
      if (mixinEventBridgeActive) {
         return false;
      } else {
         try {
            GuiKeyboardInputEvent event = new GuiKeyboardInputEvent(Keyboard.getEventCharacter(), Keyboard.getEventKey());
            EventBus.post(event);
            return event.isCanceled();
         } catch (Throwable var2) {
            log("onGuiKeyboardInput", var2);
            return false;
         }
      }
   }

   public static void onGuiMouseInput(Object screenObj) {
      if (!mixinEventBridgeActive) {
         try {
            if (InjectionAgent.isBadlionRuntime() && !(screenObj instanceof GuiContainer)) {
               return;
            }

            int wheel = Mouse.getEventDWheel();
            if (wheel != 0 && Jade.getModuleManager().getModule(AutoChest.class) != null && Jade.getModuleManager().getModule(AutoChest.class).isEnabled()) {
               Jade.getModuleManager().getModule(AutoChest.class).handleScrollTransfer(wheel);
            }
         } catch (Throwable var2) {
            log("onGuiMouseInput", var2);
         }
      }
   }

   public static boolean onDrawSelectionBox(Object playerObj, Object hitObj, float partialTicks) {
      if (mixinEventBridgeActive) {
         return false;
      } else {
         try {
            DrawSelectionBoxEvent event = new DrawSelectionBoxEvent((EntityPlayer)playerObj, (MovingObjectPosition)hitObj, partialTicks);
            EventBus.post(event);
            return event.isCanceled();
         } catch (Throwable var4) {
            log("onDrawSelectionBox", var4);
            return false;
         }
      }
   }

   public static void onRenderEntityPre(Object managerObj, Object entityObj) {
      if (!mixinEventBridgeActive && entityObj instanceof EntityPlayerSP && UpdateWalkingPlayerEvent.isYawOverrideRequested()) {
         try {
            EntityPlayerSP player = (EntityPlayerSP)entityObj;
            synchronized (RENDER_PITCH_STATE) {
               RENDER_PITCH_STATE.put(managerObj, new float[]{player.rotationPitch, player.prevRotationPitch});
            }

            player.prevRotationPitch = RotationUtils.previousPitch;
            player.rotationPitch = RotationUtils.pN0;
         } catch (Throwable var6) {
            log("onRenderEntityPre", var6);
         }
      }
   }

   public static void onRenderEntityPost(Object managerObj, Object entityObj) {
      if (!mixinEventBridgeActive && entityObj instanceof EntityPlayerSP) {
         try {
            float[] state;
            synchronized (RENDER_PITCH_STATE) {
               state = RENDER_PITCH_STATE.remove(managerObj);
            }

            if (state != null) {
               EntityPlayerSP player = (EntityPlayerSP)entityObj;
               player.rotationPitch = state[0];
               player.prevRotationPitch = state[1];
            }
         } catch (Throwable var6) {
            log("onRenderEntityPost", var6);
         }
      }
   }

   public static void onCacheActiveRenderInfo(Object managerObj) {
      if (!mixinEventBridgeActive) {
         try {
            if (Jade.getModuleManager().getModule(FreeLook.class) != null && Jade.getModuleManager().getModule(FreeLook.class).isEnabled() && FreeLook.DnH) {
               setFloatField(managerObj, FreeLook.savedPitch, new String[]{"playerViewX", "field_78732_j"});
               setFloatField(managerObj, FreeLook.savedYaw, new String[]{"playerViewY", "field_78735_i"});
            }
         } catch (Throwable var2) {
            log("onCacheActiveRenderInfo", var2);
         }
      }
   }

   public static boolean onAddCollisionBoxes(Object blockObj, Object worldObj, Object posObj, Object stateObj, Object maskObj, Object listObj, Object entityObj) {
      if (mixinEventBridgeActive) {
         return false;
      } else {
         try {
            Block block = (Block)blockObj;
            BlockPos pos = (BlockPos)posObj;
            AxisAlignedBB box = block.getCollisionBoundingBox((World)worldObj, pos, (IBlockState)stateObj);
            CollisionBoxesEvent event = new CollisionBoxesEvent(pos, block, box);
            EventBus.post(event);
            box = event.axisAlignedBB;
            if (box != null && ((AxisAlignedBB)maskObj).intersectsWith(box)) {
               ((List)listObj).add(box);
            }

            return true;
         } catch (Throwable var11) {
            log("onAddCollisionBoxes", var11);
            return false;
         }
      }
   }

   public static void onEntityMove(Object entityObj, double x, double y, double z) {
      if (!mixinEventBridgeActive) {
         try {
            if (entityObj instanceof EntityPlayerSP) {
               EventBus.post(new PlayerMoveEvent(x, y, z));
            }
         } catch (Throwable var8) {
            log("onEntityMove", var8);
         }
      }
   }

   public static boolean onSafeWalk(Object entityObj, boolean original) {
      if (mixinEventBridgeActive) {
         return original;
      } else {
         try {
            Entity entity = (Entity)entityObj;
            Scaffold scaffold = Jade.getModuleManager().getModule(Scaffold.class);
            if (entity == minecraft().thePlayer && entity.onGround && scaffold != null && scaffold.isSafeWalkRequested()) {
               return true;
            }
         } catch (Throwable var4) {
            log("onSafeWalk", var4);
         }

         return original;
      }
   }

   public static float onStepHeight(Object entityObj, float original) {
      if (mixinEventBridgeActive) {
         return original;
      } else {
         try {
            StepHeightEvent event = new StepHeightEvent((Entity)entityObj, original);
            EventBus.post(event);
            return event.stepHeight;
         } catch (Throwable var3) {
            log("onStepHeight", var3);
            return original;
         }
      }
   }

   public static void onMoveFlying(Object entityObj, float strafe, float forward, float friction) {
      if (!mixinEventBridgeActive) {
         try {
            Entity entity = (Entity)entityObj;
            MoveFlyingEvent event = new MoveFlyingEvent(strafe, forward, friction, entity.rotationYaw);
            if (entity == minecraft().thePlayer) {
               EventBus.post(event);
            }

            strafe = event.getStrafe();
            forward = event.getForward();
            friction = event.getFriction();
            float yaw = event.osuUy7();
            float length = strafe * strafe + forward * forward;
            if (length >= 1.0E-4F) {
               length = MathHelper.sqrt_float(length);
               if (length < 1.0F) {
                  length = 1.0F;
               }

               length = friction / length;
               strafe *= length;
               forward *= length;
               float sin = MathHelper.sin(yaw * (float) Math.PI / 180.0F);
               float cos = MathHelper.cos(yaw * (float) Math.PI / 180.0F);
               entity.motionX += strafe * cos - forward * sin;
               entity.motionZ += forward * cos + strafe * sin;
            }
         } catch (Throwable var10) {
            log("onMoveFlying", var10);
         }
      }
   }

   public static Object onVectorForRotation(float pitch, float yaw) {
      try {
         VectorForRotationEvent event = new VectorForRotationEvent(yaw, pitch);
         if (!mixinEventBridgeActive) {
            EventBus.post(event);
         }

         pitch = event.MHWu;
         yaw = event.yaw;
         float cosYaw = MathHelper.cos(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
         float sinYaw = MathHelper.sin(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
         float cosPitch = -MathHelper.cos(-pitch * (float) (Math.PI / 180.0));
         float sinPitch = MathHelper.sin(-pitch * (float) (Math.PI / 180.0));
         return new Vec3(sinYaw * cosPitch, sinPitch, cosYaw * cosPitch);
      } catch (Throwable var7) {
         log("onVectorForRotation", var7);
         return new Vec3(0.0, 0.0, 0.0);
      }
   }

   public static float onUpdateDistance(Object livingObj, float yaw, float distance) {
      if (mixinEventBridgeActive) {
         return distance;
      } else {
         try {
            EntityLivingBase living = (EntityLivingBase)livingObj;
            float rotationYaw = living.rotationYaw;
            if (Settings.fullBody != null
               && Settings.rotateBody != null
               && !Settings.fullBody.isToggled()
               && Settings.rotateBody.isToggled()
               && living instanceof EntityPlayerSP
               && UpdateWalkingPlayerEvent.isYawOverrideRequested()) {
               if (living.swingProgress > 0.0F) {
                  yaw = RotationUtils.outgoingYaw;
               }

               rotationYaw = RotationUtils.outgoingYaw;
               living.rotationYawHead = RotationUtils.outgoingYaw;
            }

            float delta = MathHelper.wrapAngleTo180_float(yaw - living.renderYawOffset);
            living.renderYawOffset += delta * 0.3F;
            float headDelta = MathHelper.wrapAngleTo180_float(rotationYaw - living.renderYawOffset);
            if (headDelta < -75.0F) {
               headDelta = -75.0F;
            }

            if (headDelta >= 75.0F) {
               headDelta = 75.0F;
            }

            living.renderYawOffset = rotationYaw - headDelta;
            if (headDelta * headDelta > 2500.0F) {
               living.renderYawOffset += headDelta * 0.2F;
            }

            return -distance;
         } catch (Throwable var7) {
            log("onUpdateDistance", var7);
            return distance;
         }
      }
   }

   public static void onLivingJump(Object livingObj) {
      if (!mixinEventBridgeActive) {
         try {
            EntityLivingBase living = (EntityLivingBase)livingObj;
            Object base = invokeAny(living, new String[]{"getJumpUpwardsMotion", "func_175134_bD"});
            float motion = base instanceof Number ? ((Number)base).floatValue() : 0.42F;
            JumpEvent event = new JumpEvent(living, motion, living.rotationYaw, living.isSprinting());
            EventBus.post(event);
            if (event.isCanceled()) {
               return;
            }

            living.motionY = event.MCqU();
            if (living.isPotionActive(Potion.jump)) {
               living.motionY = living.motionY + (living.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F;
            }

            if (event.isSprinting()) {
               float radians = event.glMe() * (float) (Math.PI / 180.0);
               living.motionX = living.motionX - MathHelper.sin(radians) * 0.2F;
               living.motionZ = living.motionZ + MathHelper.cos(radians) * 0.2F;
            }

            living.isAirBorne = true;
         } catch (Throwable var6) {
            log("onLivingJump", var6);
         }
      }
   }

   public static int onItemUseCount(Object livingObj, int original) {
      if (!mixinEventBridgeActive && !InjectionAgent.isBadlionRuntime() && original == 0) {
         try {
            EntityLivingBase living = (EntityLivingBase)livingObj;
            ItemStack held = living.getHeldItem();
            return forcedItemUseCount(original, held, ItemUseHelper.Ujb4(living, held));
         } catch (Throwable var4) {
            log("onItemUseCount", var4);
            return original;
         }
      } else {
         return original;
      }
   }

   public static boolean onIsUsingItem(Object livingObj, boolean original) {
      if (!mixinEventBridgeActive && !InjectionAgent.isBadlionRuntime() && !original) {
         try {
            EntityLivingBase living = (EntityLivingBase)livingObj;
            return ItemUseHelper.Ujb4(living, living.getHeldItem());
         } catch (Throwable var3) {
            log("onIsUsingItem", var3);
            return original;
         }
      } else {
         return original;
      }
   }

   public static void onMoveEntityWithHeading(Object livingObj, float strafe, float forward) {
      try {
         EntityLivingBase living = (EntityLivingBase)livingObj;
         if (!mixinEventBridgeActive && living instanceof EntityPlayerSP) {
            MoveEntityWithHeadingEvent event = new MoveEntityWithHeadingEvent(forward, strafe);
            EventBus.post(event);
            living.moveEntityWithHeading(event.vjoL8, event.ljWi1);
         } else {
            living.moveEntityWithHeading(strafe, forward);
         }
      } catch (Throwable var5) {
         log("onMoveEntityWithHeading", var5);
      }
   }

   public static boolean onPlayerAttack(Object playerObj, Object targetObj) {
      if (mixinEventBridgeActive) {
         return false;
      } else {
         try {
            PlayerAttackEvent event = new PlayerAttackEvent((EntityPlayer)playerObj, (Entity)targetObj);
            EventBus.post(event);
            return event.isCanceled();
         } catch (Throwable var3) {
            log("onPlayerAttack", var3);
            return false;
         }
      }
   }

   public static boolean onKeepSprintAttackSlowdown(Object playerObj) {
      if (mixinEventBridgeActive) {
         return false;
      } else {
         try {
            Minecraft mc = minecraft();
            if (playerObj instanceof EntityPlayer && mc != null && playerObj == minecraftPlayer(mc)) {
               KeepSprint keepSprint = Jade.getModuleManager().getModule(KeepSprint.class);
               if (keepSprint != null && keepSprint.isEnabled()) {
                  keepSprint.applySprintSlowdown((EntityPlayer)playerObj);
                  return true;
               } else {
                  return false;
               }
            } else {
               return false;
            }
         } catch (Throwable var3) {
            log("onKeepSprintAttackSlowdown", var3);
            return false;
         }
      }
   }

   public static boolean onPlayerBlocking(Object playerObj, boolean original) {
      if (!mixinEventBridgeActive && !original) {
         try {
            EntityPlayer player = (EntityPlayer)playerObj;
            return ItemUseHelper.shouldForceItemUse(player, player.getHeldItem());
         } catch (Throwable var3) {
            log("onPlayerBlocking", var3);
            return original;
         }
      } else {
         return original;
      }
   }

   public static boolean onLivingInvisible(Object entityObj, boolean original) {
      if (!mixinEventBridgeActive && original) {
         try {
            AntiInvis antiInvis = Jade.getModuleManager().getModule(AntiInvis.class);
            return antiInvis != null && antiInvis.isForceVisibleTarget((EntityLivingBase)entityObj) ? false : original;
         } catch (Throwable var3) {
            log("onLivingInvisible", var3);
            return original;
         }
      } else {
         return original;
      }
   }

   public static boolean onLivingInvisibleToPlayer(Object entityObj, boolean original) {
      return onLivingInvisible(entityObj, original);
   }

   public static boolean onSuppressLivingName(Object entityObj) {
      if (mixinEventBridgeActive) {
         return false;
      } else {
         try {
            if (ESP.overlayPassActive || FakePlayerRenderer.isRendering()) {
               return true;
            }

            if (entityObj instanceof EntityPlayer) {
               Nametags nametags = Jade.getModuleManager().getModule(Nametags.class);
               return nametags != null && nametags.shouldHideVanillaNametag((EntityPlayer)entityObj);
            }
         } catch (Throwable var2) {
            log("onSuppressLivingName", var2);
         }

         return false;
      }
   }

   public static void onRenderLivingPre(Object entityObj, double x, double y, double z, float partialTicks) {
      if (!mixinEventBridgeActive && !FakePlayerRenderer.isRendering() && EventBus.hasListeners(RenderLivingPreEvent.class)) {
         try {
            EventBus.post(new RenderLivingPreEvent((EntityLivingBase)entityObj, x, y, z, partialTicks));
         } catch (Throwable var9) {
            log("onRenderLivingPre", var9);
         }
      }
   }

   public static void onRenderLivingPost(Object entityObj, double x, double y, double z, float partialTicks) {
      if (!mixinEventBridgeActive && !FakePlayerRenderer.isRendering() && EventBus.hasListeners(RenderLivingPostEvent.class)) {
         try {
            EventBus.post(new RenderLivingPostEvent((EntityLivingBase)entityObj, x, y, z, partialTicks));
         } catch (Throwable var9) {
            log("onRenderLivingPost", var9);
         }
      }
   }

   public static void onRenderPlayerPre(Object rendererObj, Object playerObj, double x, double y, double z, float partialTicks) {
      if (!mixinEventBridgeActive && !FakePlayerRenderer.isRendering()) {
         try {
            AbstractClientPlayer player = (AbstractClientPlayer)playerObj;
            ItemUseHelper.beginRenderTracking(player);
            if (EventBus.hasListeners(RenderPlayerPreEvent.class)) {
               EventBus.post(new RenderPlayerPreEvent(player, x, y, z, partialTicks));
            }
         } catch (Throwable var10) {
            log("onRenderPlayerPre", var10);
         }
      }
   }

   public static boolean onRenderPlayerIsUser(Object playerObj) {
      try {
         AbstractClientPlayer player = (AbstractClientPlayer)playerObj;
         return player.isUser() && !Freecam.isHiddenLocalPlayer(player);
      } catch (Throwable var2) {
         log("onRenderPlayerIsUser", var2);
         return false;
      }
   }

   public static boolean onFreecamCurrentView(Object playerObj) {
      try {
         return invokeBoolean(playerObj, new String[]{"isCurrentViewEntity", "func_175160_A"}) || Freecam.pqos();
      } catch (Throwable var2) {
         log("onFreecamCurrentView", var2);
         return Freecam.pqos();
      }
   }

   public static void onRenderPlayerPost(Object rendererObj, Object playerObj, double x, double y, double z, float partialTicks) {
      if (!mixinEventBridgeActive && !FakePlayerRenderer.isRendering()) {
         try {
            AbstractClientPlayer player = (AbstractClientPlayer)playerObj;
            ItemUseHelper.endRenderTracking(player);
            if (EventBus.hasListeners(RenderPlayerPostEvent.class)) {
               ModelBase model = ((RenderPlayer)rendererObj).getMainModel();
               EventBus.post(new RenderPlayerPostEvent(player, x, y, z, partialTicks, model instanceof ModelBiped ? (ModelBiped)model : null));
            }
         } catch (Throwable var11) {
            log("onRenderPlayerPost", var11);
         }
      }
   }

   public static Object onRenderPlayerCurrentItem(Object inventoryObj) {
      try {
         InventoryPlayer inventory = (InventoryPlayer)inventoryObj;
         ItemStack item = inventory.getCurrentItem();
         return minecraft().gameSettings.thirdPersonView == 0 && inventory.player == minecraft().thePlayer ? ClientUtils.resolveRenderedItemStack(item) : item;
      } catch (Throwable var3) {
         log("onRenderPlayerCurrentItem", var3);
         return null;
      }
   }

   public static int onRenderPlayerUseCount(Object playerObj) {
      try {
         AbstractClientPlayer player = (AbstractClientPlayer)playerObj;
         int actual = player.getItemInUseCount();
         if (actual > 0) {
            return actual;
         } else {
            ItemStack item = minecraft().gameSettings.thirdPersonView == 0
               ? ClientUtils.resolveRenderedItemStack(player.inventory.getCurrentItem())
               : player.inventory.getCurrentItem();
            return forcedItemUseCount(actual, item, ItemUseHelper.shouldForceItemUse(player, item));
         }
      } catch (Throwable var4) {
         log("onRenderPlayerUseCount", var4);
         return 0;
      }
   }

   public static int onBadlionFirstPersonUseCount(Object playerObj) {
      try {
         AbstractClientPlayer player = (AbstractClientPlayer)playerObj;
         int actual = player.getItemInUseCount();
         if (actual > 0) {
            return actual;
         } else {
            ItemStack displayed = ClientUtils.resolveRenderedItemStack(player.inventory.getCurrentItem());
            return ItemUseHelper.Ujb4(player, displayed) ? 1 : actual;
         }
      } catch (Throwable var4) {
         log("onBadlionFirstPersonUseCount", var4);
         return 0;
      }
   }

   public static Object onArmorAlpha(float alpha) {
      if (!mixinEventBridgeActive && FakePlayerRenderer.isRendering()) {
         alpha *= FakePlayerRenderer.xu60();
      }

      return alpha;
   }

   public static void onItemRenderPre(Object rendererObj) {
      if (!mixinEventBridgeActive) {
         try {
            ItemStack original = (ItemStack)getField(rendererObj, new String[]{"itemToRender", "field_78453_b"});
            ItemStack displayed = ClientUtils.resolveRenderedItemStack(original);
            setField(
               rendererObj,
               "itemToRender",
               "field_78453_b",
               displayed
            );
            ItemUseHelper.Ziacy();
            EntityPlayerSP player = minecraft().thePlayer;
            int originalUseCount = getIntField(player, new String[]{"itemInUseCount", "field_71072_f"});
            boolean forced = ItemUseHelper.Ujb4(player, displayed);
            boolean writeUseCount = forced && !InjectionAgent.isBadlionRuntime();
            if (writeUseCount) {
               setIntField(player, forcedItemUseCount(originalUseCount, displayed, true), new String[]{"itemInUseCount", "field_71072_f"});
            }

            float[] freecamRotation = beginFreecamHandRotation(player);
            synchronized (ITEM_RENDER_STATE) {
               ITEM_RENDER_STATE.put(rendererObj, new Object[]{original, originalUseCount, writeUseCount, player, freecamRotation});
            }
         } catch (Throwable var11) {
            log("onItemRenderPre", var11);
         }
      }
   }

   public static void onItemRenderPost(Object rendererObj) {
      if (!mixinEventBridgeActive) {
         try {
            Object[] state;
            synchronized (ITEM_RENDER_STATE) {
               state = ITEM_RENDER_STATE.remove(rendererObj);
            }

            if (state != null) {
               restoreItemRenderState(rendererObj, state);
            }
         } catch (Throwable var9) {
            log("onItemRenderPost", var9);
         } finally {
            ItemUseHelper.Npqilu9();
         }
      }
   }

   private static void restoreItemRenderState(Object rendererObj, Object[] state) throws Exception {
      if ((Boolean)state[2]) {
         int count = (Integer)state[1];
         setIntField(state[3], count, new String[]{"itemInUseCount", "field_71072_f"});
      }

      if (state.length > 4 && state[3] instanceof EntityPlayerSP) {
         restoreFreecamHandRotation((EntityPlayerSP)state[3], (float[])state[4]);
      }

      setField(
         rendererObj,
         "itemToRender",
         "field_78453_b",
         state[0]
      );
   }

   private static float[] beginFreecamHandRotation(EntityPlayerSP player) {
      Entity camera = Freecam.cameraEntity;
      if (camera != null && player != null) {
         float[] state = new float[]{
            player.rotationYaw,
            player.prevRotationYaw,
            player.rotationPitch,
            player.prevRotationPitch,
            player.renderArmYaw,
            player.prevRenderArmYaw,
            player.renderArmPitch,
            player.prevRenderArmPitch
         };
         player.rotationYaw = player.renderArmYaw = camera.rotationYaw;
         player.prevRotationYaw = player.prevRenderArmYaw = camera.prevRotationYaw;
         player.rotationPitch = player.renderArmPitch = camera.rotationPitch;
         player.prevRotationPitch = player.prevRenderArmPitch = camera.prevRotationPitch;
         return state;
      } else {
         return null;
      }
   }

   private static void restoreFreecamHandRotation(EntityPlayerSP player, float[] state) {
      if (player != null && state != null) {
         player.rotationYaw = state[0];
         player.prevRotationYaw = state[1];
         player.rotationPitch = state[2];
         player.prevRotationPitch = state[3];
         player.renderArmYaw = state[4];
         player.prevRenderArmYaw = state[5];
         player.renderArmPitch = state[6];
         player.prevRenderArmPitch = state[7];
      }
   }

   private static int forcedItemUseCount(int original, ItemStack stack, boolean forced) {
      return resolveForcedItemUseCount(original, forced);
   }

   private static int resolveForcedItemUseCount(int original, boolean forced) {
      return original <= 0 && forced ? 1 : original;
   }

   public static boolean onItemRendererUpdate(Object rendererObj) {
      return cancelItemRendererOperation(rendererObj, "cancelUpdate");
   }

   public static boolean onItemRendererReset(Object rendererObj) {
      return cancelItemRendererOperation(rendererObj, "cancelReset");
   }

   private static boolean cancelItemRendererOperation(Object rendererObj, String flag) {
      if (!mixinEventBridgeActive && RuntimeAccess.consumeSidecarFlag(rendererObj, flag)) {
         try {
            setFloatField(rendererObj, 1.0F, new String[]{"equippedProgress", "field_78454_c"});
            setFloatField(rendererObj, 1.0F, new String[]{"prevEquippedProgress", "field_78451_d"});
            return true;
         } catch (Throwable var3) {
            log("cancelItemRendererOperation", var3);
            return false;
         }
      } else {
         return false;
      }
   }

   public static void onContainerDrawSlotBegin(Object containerObj, Object slotObj) {
      if (!mixinEventBridgeActive) {
         synchronized (CONTAINER_DRAW_SLOT) {
            CONTAINER_DRAW_SLOT.put(containerObj, slotObj);
         }
      }
   }

   public static void onContainerDrawSlotEnd(Object containerObj) {
      synchronized (CONTAINER_DRAW_SLOT) {
         CONTAINER_DRAW_SLOT.remove(containerObj);
      }
   }

   public static Object onContainerDisplayedStack(Object containerObj, Object originalObj) {
      if (!mixinEventBridgeActive && Jade.getModuleManager().getModule(Opsec.class) != null) {
         try {
            Object slot;
            synchronized (CONTAINER_DRAW_SLOT) {
               slot = CONTAINER_DRAW_SLOT.get(containerObj);
            }

            return Jade.getModuleManager().getModule(Opsec.class).Bxn6((Slot)slot, (ItemStack)originalObj);
         } catch (Throwable var6) {
            log("onContainerDisplayedStack", var6);
            return originalObj;
         }
      } else {
         return originalObj;
      }
   }

   public static Object onContainerTooltipStack(Object containerObj, Object originalObj) {
      if (!mixinEventBridgeActive && Jade.getModuleManager().getModule(Opsec.class) != null) {
         try {
            Object rawSlot = getField(containerObj, new String[]{"theSlot", "field_147006_u"});
            if (rawSlot != null && !(rawSlot instanceof Slot)) {
               InjectionAgent.recordRuntimeSignal(
                  "container",
                  "tooltip_slot_type_mismatch"
               );
               return originalObj;
            } else if (originalObj != null && !(originalObj instanceof ItemStack)) {
               InjectionAgent.recordRuntimeSignal(
                  "container",
                  "tooltip_stack_type_mismatch"
               );
               return originalObj;
            } else {
               return Jade.getModuleManager().getModule(Opsec.class).Bxn6((Slot)rawSlot, (ItemStack)originalObj);
            }
         } catch (Throwable var3) {
            log("onContainerTooltipStack", var3);
            return originalObj;
         }
      } else {
         return originalObj;
      }
   }

   public static void onContainerDrawScreen(Object containerObj, int mouseX, int mouseY) {
      if (!mixinEventBridgeActive) {
         if (!(containerObj instanceof GuiContainer)) {
            InjectionAgent.recordRuntimeSignal(
               "container",
               "screen_type_mismatch"
            );
         } else {
            AutoClicker autoClicker = Jade.getModuleManager().getModule(AutoClicker.class);
            if (autoClicker != null && autoClicker.isEnabled() && autoClicker.whilstShiftingInInventory.isToggled()) {
               try {
                  autoClicker.setInventoryTarget((GuiContainer)containerObj, resolveHoveredContainerSlot((GuiContainer)containerObj, mouseX, mouseY));
               } catch (Throwable var6) {
                  log("onContainerDrawScreen.autoClicker", var6);
               }
            }

            if (Jade.getModuleManager().getModule(Opsec.class) != null) {
               try {
                  Jade.getModuleManager().getModule(Opsec.class).drawQuickBuySetupText();
               } catch (Throwable var5) {
                  log("onContainerDrawScreen.opsec", var5);
               }
            }
         }
      }
   }

   private static Slot resolveHoveredContainerSlot(GuiContainer container, int mouseX, int mouseY) {
      try {
         Method isMouseOverSlot = findMethod(container.getClass(), new String[]{"isMouseOverSlot", "func_146981_a"}, new Class[]{Slot.class, int.class, int.class});
         if (isMouseOverSlot != null) {
            if (container.inventorySlots != null && container.inventorySlots.inventorySlots != null) {
               for (Object candidate : container.inventorySlots.inventorySlots) {
                  if (candidate instanceof Slot) {
                     Object hovered = isMouseOverSlot.invoke(container, candidate, mouseX, mouseY);
                     if (Boolean.TRUE.equals(hovered)) {
                        return (Slot)candidate;
                     }
                  }
               }

               return null;
            }

            return null;
         }
      } catch (Throwable var7) {
         log("onContainerDrawScreen.hoveredSlot", var7);
      }

      try {
         Object rawSlot = getField(container, new String[]{"theSlot", "field_147006_u"});
         if (rawSlot == null || rawSlot instanceof Slot) {
            return (Slot)rawSlot;
         }

         InjectionAgent.recordRuntimeSignal(
            "container",
            "screen_slot_type_mismatch"
         );
      } catch (Throwable var8) {
         log("onContainerDrawScreen.slotFallback", var8);
      }

      return null;
   }

   public static boolean onContainerManualInput(Object ignored) {
      return mixinEventBridgeActive
         ? false
         : Jade.getModuleManager().getModule(InventoryManager.class) != null && Jade.getModuleManager().getModule(InventoryManager.class).isExecutingPlan()
            || Jade.getModuleManager().getModule(Opsec.class) != null && Jade.getModuleManager().getModule(Opsec.class).isQuickBuySetupActive();
   }

   public static boolean onContainerClick(Object containerObj, Object slotObj, int slotId, int clickedButton, int clickType) {
      if (mixinEventBridgeActive) {
         return false;
      } else {
         try {
            if (onContainerManualInput(containerObj)) {
               return true;
            } else {
               Slot slot = (Slot)slotObj;
               return Jade.getModuleManager().getModule(Opsec.class) != null
                     && Jade.getModuleManager().getModule(Opsec.class).axClaj(slot, slotId, clickedButton, clickType)
                  ? true
                  : Jade.getModuleManager().getModule(BedwarsUtils.class) != null && Jade.getModuleManager().getModule(BedwarsUtils.class).handleQuickShopSlotClick(slot, slotId, clickedButton, clickType);
            }
         } catch (Throwable var6) {
            log("onContainerClick", var6);
            return false;
         }
      }
   }

   public static void onGuiChatKeyPre(Object chatObj, int keyCode) {
      if (!mixinEventBridgeActive && keyCode != 15) {
         synchronized (CHAT_COMPLETIONS) {
            CHAT_COMPLETIONS.remove(chatObj);
         }
      }
   }

   public static void onGuiChatKeyPost(Object chatObj) {
      if (!mixinEventBridgeActive) {
         try {
            GuiTextField input = (GuiTextField)getField(chatObj, new String[]{"inputField", "field_146415_a"});
            input.setMaxStringLength(Jade.commandManager != null && Jade.commandManager.isCommandMessage(input.getText()) ? 256 : 100);
         } catch (Throwable var2) {
            log("onGuiChatKeyPost", var2);
         }
      }
   }

   public static void onGuiChatDraw(Object chatObj) {
      if (!mixinEventBridgeActive && Jade.commandManager != null) {
         try {
            GuiTextField input = (GuiTextField)getField(chatObj, new String[]{"inputField", "field_146415_a"});
            String text = input.getText();
            if (!Jade.commandManager.isCommandMessage(text)) {
               return;
            }

            String suggestion = Jade.commandManager.getFirstCompletion(text);
            if (suggestion.isEmpty() || suggestion.equalsIgnoreCase(text) || !suggestion.toLowerCase().startsWith(text.toLowerCase())) {
               return;
            }

            IAccessorGuiTextField field = (IAccessorGuiTextField)input;
            if (field.getCursorPosition() != text.length() || field.getSelectionEnd() != field.getCursorPosition()) {
               return;
            }

            Minecraft mc = (Minecraft)getField(chatObj, new String[]{"mc", "field_146297_k"});
            int inset = field.isEnableBackgroundDrawing() ? 4 : 0;
            int left = input.xPosition + inset;
            int top = input.yPosition + (field.isEnableBackgroundDrawing() ? (field.getHeight() - 8) / 2 : 0);
            int right = input.xPosition + field.getWidth() - inset;
            int scroll = Math.max(0, Math.min(field.getLineScrollOffset(), text.length()));
            String visible = mc.fontRendererObj.trimStringToWidth(text.substring(scroll), Math.max(0, right - left));
            int ghostX = left + mc.fontRendererObj.getStringWidth(visible) + 1;
            if (right <= ghostX) {
               return;
            }

            String suffix = mc.fontRendererObj.trimStringToWidth(suggestion.substring(text.length()), right - ghostX);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            mc.fontRendererObj.drawString(suffix, ghostX, top, -2005434998);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         } catch (Throwable var14) {
            log("onGuiChatDraw", var14);
         }
      }
   }

   public static boolean onGuiChatRequest(Object chatObj, Object fullObj) {
      if (!mixinEventBridgeActive && Jade.commandManager != null) {
         try {
            String full = (String)fullObj;
            if (!Jade.commandManager.isCommandMessage(full)) {
               return false;
            } else {
               String[] suggestions = Jade.commandManager.getCompletions(full);
               if (suggestions.length != 0 && (suggestions.length != 1 || !full.equalsIgnoreCase(suggestions[0]))) {
                  setBooleanField(chatObj, true, new String[]{"waitingOnAutocomplete", "field_146414_r"});
                  ((GuiChat)chatObj).onAutocompleteResponse(suggestions);
                  return true;
               } else {
                  return false;
               }
            }
         } catch (Throwable var4) {
            log("onGuiChatRequest", var4);
            return false;
         }
      } else {
         return false;
      }
   }

   public static boolean onGuiChatAutocomplete(Object chatObj) {
      if (!mixinEventBridgeActive && Jade.commandManager != null) {
         try {
            GuiTextField input = (GuiTextField)getField(chatObj, new String[]{"inputField", "field_146415_a"});
            String text = input.getText();
            if (!Jade.commandManager.isCommandMessage(text)) {
               synchronized (CHAT_COMPLETIONS) {
                  CHAT_COMPLETIONS.remove(chatObj);
               }

               return false;
            } else {
               Object[] state;
               synchronized (CHAT_COMPLETIONS) {
                  state = CHAT_COMPLETIONS.get(chatObj);
               }

               String[] values = state == null ? null : (String[])state[0];
               if (values != null && contains(values, text)) {
                  state[1] = ((Integer)state[1] + 1) % values.length;
               } else {
                  values = Jade.commandManager.getCompletions(text);
                  if (values.length == 0) {
                     synchronized (CHAT_COMPLETIONS) {
                        CHAT_COMPLETIONS.remove(chatObj);
                     }

                     return true;
                  }

                  state = new Object[]{values, 0};
                  synchronized (CHAT_COMPLETIONS) {
                     CHAT_COMPLETIONS.put(chatObj, state);
                  }
               }

               input.setText(values[(Integer)state[1]]);
               input.setCursorPositionEnd();
               return true;
            }
         } catch (Throwable var12) {
            log("onGuiChatAutocomplete", var12);
            return false;
         }
      } else {
         return false;
      }
   }

   public static boolean onResourceStreamHead(Object packObj, Object locationObj) {
      if (mixinEventBridgeActive) {
         return false;
      } else {
         ResourceLocation location = (ResourceLocation)locationObj;
         if (location == null) {
            return false;
         } else if ((
               !"minecraft".equals(location.getResourceDomain())
                  || !location.getResourcePath().startsWith("skins/")
            )
            && !resourceMissing(packObj).contains(location)) {
            if (InjectionAgent.isLunarRuntime()
               && "minecraft".equals(location.getResourceDomain())) {
               Boolean present = ClassLoaderAssetCache.gZe4(
                  packObj.getClass().getClassLoader(),
                  "assets/minecraft/" + location.getResourcePath()
               );
               return Boolean.FALSE.equals(present);
            } else {
               return false;
            }
         } else {
            return true;
         }
      }
   }

   public static Object onResourceStreamReturn(Object packObj, Object locationObj, Object streamObj) {
      ResourceLocation location = (ResourceLocation)locationObj;
      if (location != null && streamObj == null) {
         resourceMissing(packObj).add(location);
      }

      return streamObj;
   }

   public static Object onResourceInputHead(Object packObj, Object locationObj) {
      ResourceLocation location = (ResourceLocation)locationObj;
      byte[] cached = location == null ? null : resourceContents(packObj).get(location);
      return cached == null ? null : new ByteArrayInputStream(cached);
   }

   public static Object onResourceInputReturn(Object packObj, Object locationObj, Object streamObj) {
      ResourceLocation location = (ResourceLocation)locationObj;
      InputStream stream = (InputStream)streamObj;
      if (location != null
         && stream != null
         && location.getResourcePath().endsWith(".json")) {
         try {
            ByteArrayOutputStream output = new ByteArrayOutputStream(4096);
            byte[] buffer = new byte[4096];

            int read;
            while ((read = stream.read(buffer)) != -1) {
               output.write(buffer, 0, read);
            }

            stream.close();
            byte[] contents = output.toByteArray();
            byte[] existing = resourceContents(packObj).putIfAbsent(location, contents);
            return new ByteArrayInputStream(existing == null ? contents : existing);
         } catch (Throwable var10) {
            log("onResourceInputReturn", var10);
            return stream;
         }
      } else {
         return stream;
      }
   }

   public static boolean onResourceExists(Object packObj, Object locationObj, boolean original) {
      ResourceLocation location = (ResourceLocation)locationObj;
      if (location != null) {
         resourceExistence(packObj).put(location, original);
      }

      return original;
   }

   public static Object onResourceExistsHead(Object packObj, Object locationObj) {
      return !mixinEventBridgeActive && locationObj != null ? resourceExistence(packObj).get((ResourceLocation)locationObj) : null;
   }

   private static Map<ResourceLocation, Boolean> resourceExistence(Object owner) {
      synchronized (RESOURCE_EXISTENCE) {
         Map<ResourceLocation, Boolean> cache = RESOURCE_EXISTENCE.get(owner);
         if (cache == null) {
            cache = new ConcurrentHashMap<>();
            RESOURCE_EXISTENCE.put(owner, cache);
         }

         return cache;
      }
   }

   private static Map<ResourceLocation, byte[]> resourceContents(Object owner) {
      synchronized (RESOURCE_CONTENTS) {
         Map<ResourceLocation, byte[]> cache = RESOURCE_CONTENTS.get(owner);
         if (cache == null) {
            cache = new ConcurrentHashMap<>();
            RESOURCE_CONTENTS.put(owner, cache);
         }

         return cache;
      }
   }

   private static Set<ResourceLocation> resourceMissing(Object owner) {
      synchronized (RESOURCE_MISSING) {
         Set<ResourceLocation> cache = RESOURCE_MISSING.get(owner);
         if (cache == null) {
            cache = Collections.newSetFromMap(new ConcurrentHashMap<>());
            RESOURCE_MISSING.put(owner, cache);
         }

         return cache;
      }
   }

   public static boolean onTextureReload(Object managerObj, Object locationObj, Object textureObj) {
      try {
         TextureManager manager = (TextureManager)managerObj;
         ResourceLocation location = (ResourceLocation)locationObj;
         ITextureObject texture = (ITextureObject)textureObj;
         if (texture instanceof ThreadDownloadImageData || texture instanceof DynamicTexture) {
            return true;
         } else if (texture instanceof SimpleTexture) {
            synchronized (PENDING_TEXTURES) {
               Set<ResourceLocation> pending = PENDING_TEXTURES.get(managerObj);
               if (pending == null) {
                  pending = new HashSet<>();
                  PENDING_TEXTURES.put(managerObj, pending);
               }

               pending.add(location);
            }

            return true;
         } else {
            return manager.loadTexture(location, texture);
         }
      } catch (Throwable var10) {
         log("onTextureReload", var10);
         return false;
      }
   }

   public static void onTextureBind(Object managerObj, Object locationObj) {
      ResourceLocation location = (ResourceLocation)locationObj;

      try {
         boolean pending;
         synchronized (PENDING_TEXTURES) {
            Set<ResourceLocation> set = PENDING_TEXTURES.get(managerObj);
            pending = set != null && set.remove(location);
         }

         if (!pending) {
            return;
         }

         Object mapObj = getField(managerObj, new String[]{"mapTextureObjects", "field_110585_a"});
         ITextureObject texture = (ITextureObject)((Map)mapObj).get(location);
         if (texture instanceof SimpleTexture && !(texture instanceof ThreadDownloadImageData)) {
            ((TextureManager)managerObj).loadTexture(location, texture);
         }
      } catch (Throwable var8) {
         log("onTextureBind", var8);
      }
   }

   public static void onTextureLoad(Object managerObj, Object locationObj) {
      synchronized (PENDING_TEXTURES) {
         Set<ResourceLocation> set = PENDING_TEXTURES.get(managerObj);
         if (set != null) {
            set.remove((ResourceLocation)locationObj);
         }
      }
   }

   public static void onSoundReloadHead(Object handlerObj, Object resourceManagerObj) {
      try {
         String fingerprint = soundFingerprint((IResourceManager)resourceManagerObj);
         Object manager = getField(handlerObj, new String[]{"sndManager", "field_147694_f"});
         synchronized (SOUND_FINGERPRINTS) {
            String previous = SOUND_FINGERPRINTS.put(handlerObj, fingerprint);
            SOUND_RESTARTS.put(manager, fingerprint == null || previous == null || !fingerprint.equals(previous));
         }
      } catch (Throwable var8) {
         log("onSoundReloadHead", var8);
      }
   }

   public static void onSoundManagerReload(Object managerObj) {
      try {
         Boolean restart;
         synchronized (SOUND_FINGERPRINTS) {
            restart = SOUND_RESTARTS.remove(managerObj);
         }

         if (restart == null || restart) {
            ((SoundManager)managerObj).reloadSoundSystem();
         }
      } catch (Throwable var5) {
         log("onSoundManagerReload", var5);
      }
   }

   private static String soundFingerprint(IResourceManager resourceManager) {
      CRC32 crc = new CRC32();
      int count = 0;

      try {
         List<String> domains = new ArrayList<>(resourceManager.getResourceDomains());
         Collections.sort(domains);
         byte[] buffer = new byte[4096];

         for (String domain : domains) {
            updateCrc(crc, domain);

            List<IResource> resources;
            try {
               resources = resourceManager.getAllResources(
                  new ResourceLocation(domain, "sounds.json")
               );
            } catch (IOException var16) {
               continue;
            }

            for (IResource resource : resources) {
               count++;
               updateCrc(crc, resource.getResourcePackName());
               InputStream input = resource.getInputStream();

               int read;
               try {
                  while ((read = input.read(buffer)) != -1) {
                     crc.update(buffer, 0, read);
                  }
               } finally {
                  input.close();
               }
            }
         }

         return count + ":" + Long.toHexString(crc.getValue());
      } catch (Throwable var18) {
         return null;
      }
   }

   private static void updateCrc(CRC32 crc, String value) {
      byte[] bytes = (value == null ? "" : value).getBytes(StandardCharsets.UTF_8);
      crc.update(bytes, 0, bytes.length);
      crc.update(0);
   }

   private static boolean contains(String[] values, String value) {
      for (String candidate : values) {
         if (candidate.equals(value)) {
            return true;
         }
      }

      return false;
   }

   public static void ensureJadeStarted() {
      if (!startupAttempted) {
         startupAttempted = true;

         try {
            InjectionAgent.reportDiagnostic(
               "client-thread Jade initialization started"
            );
            ClassLoader loader = JadeAgentHooks.class.getClassLoader();
            LocalCoreLoader.bootLocalCore(loader);
            InjectionAgent.markReady();
            InjectionAgent.reportDiagnostic(
               "client-thread Jade initialization completed"
            );
            log(
               "startup",
               "Core initialized"
            );
         } catch (Throwable var1) {
            InjectionAgent.markFailed(
               "Jade.init failed: " + InjectionAgent.describeFailure(var1)
            );
            log("onStartGame", var1);
         }
      }
   }

   public static void onRunTickStart() {
      if (!mixinEventBridgeActive) {
         try {
            prePlayerInteractPostedThisTick = false;
            ensureJadeStarted();
            EventBus.post(new TickStartEvent());
            EventBus.post(new TickEndEvent(EventPhase.START));
            RotationHandler.getInstance().updateTargetRotation();
            EventBus.post(new TickEvent());
            EventBus.post(new RightClickDelayEvent());
         } catch (Throwable var1) {
            log("onRunTickStart", var1);
         }
      }
   }

   public static void onRunTickEnd() {
      if (!mixinEventBridgeActive) {
         try {
            EventBus.post(new TickEndEvent(EventPhase.END));
            DelayRemover delayRemover = Jade.getModuleManager().getModule(DelayRemover.class);
            Minecraft mc = minecraft();
            if (delayRemover != null && delayRemover.isEnabled() && mc != null && mc.inGameHasFocus && minecraftPlayer(mc) != null && delayRemover.legacyHitreg.isToggled()) {
               setIntField(mc, 0, new String[]{"leftClickCounter", "field_71429_W"});
               InjectionAgent.recordRuntimeSignal(
                  "mutation",
                  "DelayRemover.leftClickCounter.write"
               );
               if (getIntField(mc, new String[]{"leftClickCounter", "field_71429_W"}) == 0) {
                  InjectionAgent.recordRuntimeSignal(
                     "mutation",
                     "DelayRemover.leftClickCounter.verified"
                  );
               }
            }
         } catch (Throwable var2) {
            log("onRunTickEnd", var2);
         }
      }
   }

   public static void onKeyboardEventAvailable(boolean hasEvent) {
      if (!mixinEventBridgeActive) {
         try {
            if (hasEvent) {
               Jade.WTxmo(Keyboard.getEventKey(), Keyboard.getEventKeyState());
            }
         } catch (Throwable var2) {
            log("onKeyboardEventAvailable", var2);
         }
      }
   }

   public static void onMouseEventAvailable(boolean hasEvent) {
      if (!mixinEventBridgeActive) {
         try {
            if (!hasEvent) {
               return;
            }

            Minecraft mc = minecraft();
            if (!shouldObserveMouseEvent(InjectionAgent.isBadlionRuntime(), mc == null ? null : mc.currentScreen)) {
               return;
            }

            int button = Mouse.getEventButton();
            if (button >= 0) {
               boolean pressed = Mouse.getEventButtonState();
               Jade.oMds(button, pressed);
               if (button == 2 && pressed) {
                  EventBus.post(new MouseEvent(2, true, Mouse.getX(), Mouse.getY(), 0, 0, 0));
               }
            }

            Jade.handleMouseWheel(Mouse.getEventDWheel());
         } catch (Throwable var4) {
            log("onMouseEventAvailable", var4);
         }
      }
   }

   private static boolean shouldObserveMouseEvent(boolean badlion, Object currentScreen) {
      return !badlion || currentScreen == null;
   }

   public static void onKeyBindingState(int keyCode, boolean pressed) {
      if (!mixinEventBridgeActive) {
         try {
            if (keyCode < 0) {
               Jade.syncKeybindsForKey(keyCode + 1100);
            } else {
               Jade.syncKeybindsForKey(keyCode);
            }
         } catch (Throwable var3) {
            log("onKeyBindingState", var3);
         }
      }
   }

   public static void onAfterControllerUpdate() {
      if (!mixinEventBridgeActive) {
         try {
            if (Jade.getModuleManager().getModule(BedNuker.class) != null) {
               Jade.getModuleManager().getModule(BedNuker.class).processPendingPlacement();
            }
         } catch (Throwable var1) {
            log("onAfterControllerUpdate", var1);
         }
      }
   }

   public static void onPrePlayerInteract() {
      if (!mixinEventBridgeActive) {
         if (!prePlayerInteractPostedThisTick) {
            prePlayerInteractPostedThisTick = true;

            try {
               EventBus.post(new PrePlayerInteractEvent());
            } catch (Throwable var1) {
               log("onPrePlayerInteract", var1);
            }
         }
      }
   }

   public static void onChangeCurrentItem(Object inventoryObj, int direction) {
      try {
         InventoryPlayer inventory = (InventoryPlayer)inventoryObj;
         if (!mixinEventBridgeActive) {
            ChangeCurrentItemEvent event = new ChangeCurrentItemEvent(direction, inventory.currentItem);
            EventBus.post(event);
            if (event.isCanceled()) {
               return;
            }
         }

         inventory.changeCurrentItem(direction);
      } catch (Throwable var4) {
         log("onChangeCurrentItem", var4);
      }
   }

   public static void onSetThirdPersonView(Object settingsObj, int value) {
      try {
         GameSettings settings = (GameSettings)settingsObj;
         if (!mixinEventBridgeActive && Jade.getModuleManager().getModule(FreeLook.class) != null && FreeLook.DnH) {
            Jade.getModuleManager().getModule(FreeLook.class).disableFreeLook();
         } else {
            settings.thirdPersonView = value;
         }
      } catch (Throwable var3) {
         log("onSetThirdPersonView", var3);
      }
   }

   public static boolean onSetThirdPersonView(Object settingsObj, int value, boolean redirected) {
      try {
         if (redirected && !mixinEventBridgeActive && Jade.getModuleManager().getModule(FreeLook.class) != null && FreeLook.DnH) {
            Jade.getModuleManager().getModule(FreeLook.class).disableFreeLook();
            return true;
         }
      } catch (Throwable var4) {
         log("onSetThirdPersonView", var4);
      }

      return false;
   }

   public static void onBadlionThirdPersonTick(Object minecraftObj) {
      if (!mixinEventBridgeActive) {
         try {
            Minecraft mc = (Minecraft)minecraftObj;
            if (Jade.getModuleManager().getModule(FreeLook.class) != null && FreeLook.DnH && mc.gameSettings != null && mc.gameSettings.thirdPersonView != 1) {
               Jade.getModuleManager().getModule(FreeLook.class).disableFreeLook();
            }
         } catch (Throwable var2) {
            log("onBadlionThirdPersonTick", var2);
         }
      }
   }

   public static void onSetCurrentItem(Object inventoryObj, int slot) {
      try {
         InventoryPlayer inventory = (InventoryPlayer)inventoryObj;
         if (!mixinEventBridgeActive) {
            SetCurrentItemEvent event = new SetCurrentItemEvent(slot);
            EventBus.post(event);
            if (event.isCanceled()) {
               return;
            }
         }

         inventory.currentItem = slot;
      } catch (Throwable var4) {
         log("onSetCurrentItem", var4);
      }
   }

   public static void onRunGameLoopStart(Object minecraft) {
      if (!mixinEventBridgeActive) {
         try {
            ensureJadeStarted();
            Jade.refreshTimerModuleKeybind();
            Jade.openPendingScreen();
            EventBus.post(new GameLoopEvent());
            Object timer = getField(minecraft, new String[]{"timer", "field_71428_T"});
            float partialTicks = timer == null ? 0.0F : getFloatField(timer, new String[]{"renderPartialTicks", "field_74281_c"});
            EventBus.post(new RenderTickEvent(EventPhase.START, partialTicks));
         } catch (Throwable var3) {
            log("onRunGameLoopStart", var3);
         }
      }
   }

   public static boolean onClickMouse(Object minecraft) {
      if (mixinEventBridgeActive) {
         return false;
      } else {
         try {
            Minecraft mc = (Minecraft)minecraft;
            DelayRemover delayRemover = Jade.getModuleManager().getModule(DelayRemover.class);
            if (delayRemover != null && delayRemover.isEnabled() && delayRemover.legacyHitreg.isToggled()) {
               setIntField(mc, 0, new String[]{"leftClickCounter", "field_71429_W"});
               InjectionAgent.recordRuntimeSignal(
                  "mutation",
                  "DelayRemover.leftClickCounter.at_click"
               );
            }

            MovingObjectPosition mop = (MovingObjectPosition)getField(mc, new String[]{"objectMouseOver", "field_71476_x"});
            MouseEvent mouseEvent = new MouseEvent(0, true, Mouse.getX(), Mouse.getY(), 0, 0, 0);
            EventBus.post(mouseEvent);
            if (mouseEvent.isCanceled()) {
               return true;
            }

            ClickMouseEvent preAttack = new ClickMouseEvent(mop);
            EventBus.post(preAttack);
            if (preAttack.isCanceled()) {
               return true;
            }

            EventBus.post(new LeftClickEvent());
         } catch (Throwable var6) {
            log("onClickMouse", var6);
         }

         return false;
      }
   }

   public static boolean onRightClickMouse() {
      if (mixinEventBridgeActive) {
         return false;
      } else {
         try {
            if (Mouse.isButtonDown(1)) {
               MouseEvent mouseEvent = new MouseEvent(1, true, Mouse.getX(), Mouse.getY(), 0, 0, 0);
               EventBus.post(mouseEvent);
               if (mouseEvent.isCanceled()) {
                  return true;
               }
            }

            RightClickEvent event = new RightClickEvent();
            EventBus.post(event);
            return event.isCanceled();
         } catch (Throwable var1) {
            log("onRightClickMouse", var1);
            return false;
         }
      }
   }

   public static void onLoadWorld(Object minecraft) {
      if (!mixinEventBridgeActive) {
         try {
            EventBus.post(new LoadWorldEvent((World)getField(minecraft, new String[]{"theWorld", "field_71441_e"})));
         } catch (Throwable var2) {
            log("onLoadWorld", var2);
         }
      }
   }

   public static boolean onDisplayGuiScreen(Object minecraft, Object guiScreen) {
      if (mixinEventBridgeActive) {
         return false;
      } else {
         try {
            Minecraft mc = (Minecraft)minecraft;
            GuiScreen previousGui = (GuiScreen)getField(mc, new String[]{"currentScreen", "field_71462_r"});
            GuiScreen setGui = (GuiScreen)guiScreen;
            boolean opened = setGui != null;
            if (!opened) {
               setGui = previousGui;
            }

            EventBus.post(new GuiDisplayEvent(setGui, opened));
            GuiOpenEvent event = new GuiOpenEvent((GuiScreen)guiScreen);
            EventBus.post(event);
            return event.isCanceled();
         } catch (Throwable var7) {
            log("onDisplayGuiScreen", var7);
            return false;
         }
      }
   }

   public static void onUpdatePlayerMoveState(Object inputObj) {
      if (!mixinEventBridgeActive && inputObj instanceof MovementInput) {
         try {
            MovementInput input = (MovementInput)inputObj;
            float forward = input.moveForward;
            float strafe = input.moveStrafe;
            if (input.sneak) {
               forward /= 0.3F;
               strafe /= 0.3F;
            }

            MoveInputEvent event = new MoveInputEvent(forward, strafe, input.jump, input.sneak, 0.3);
            EventBus.post(event);
            double sneakMultiplier = event.BFAN();
            input.moveForward = event.getMoveForward();
            input.moveStrafe = event.AfWugH();
            input.jump = event.isJumping();
            input.sneak = event.isSneaking();
            if (input.sneak) {
               input.moveStrafe = (float)(input.moveStrafe * sneakMultiplier);
               input.moveForward = (float)(input.moveForward * sneakMultiplier);
            }

            EventBus.post(new MoveStateUpdateEvent());
         } catch (Throwable var7) {
            log("onUpdatePlayerMoveState", var7);
         }
      }
   }

   public static boolean onNetworkSend(Object packetObj) {
      if (mixinEventBridgeActive) {
         return false;
      } else {
         try {
            Packet packet = (Packet)packetObj;
            if (InjectionAgent.isBadlionRuntime()
               && packet instanceof C01PacketChatMessage
               && handleLocalChatMessage(minecraft(), ((C01PacketChatMessage)packet).getMessage())) {
               InjectionAgent.recordRuntimeSignal(
                  "chat",
                  "outgoing.local_handled_at_packet"
               );
               return true;
            } else if (PacketUtils.consumeSilentlySent(packet)) {
               EventBus.post(new SilentPacketSendEvent(packet));
               return false;
            } else {
               PacketSendEvent event = new PacketSendEvent(packet);
               EventBus.post(event);
               return event.isCanceled();
            }
         } catch (Throwable var3) {
            log("onNetworkSend", var3);
            return false;
         }
      }
   }

   public static void onNetworkDispatch(Object packetObj) {
      if (!mixinEventBridgeActive) {
         try {
            EventBus.post(new PacketDispatchEvent((Packet<?>)packetObj));
         } catch (Throwable var2) {
            log("onNetworkDispatch", var2);
         }
      }
   }

   public static boolean onNetworkReceive(Object packetObj) {
      if (mixinEventBridgeActive) {
         return false;
      } else {
         try {
            Packet packet = (Packet)packetObj;
            if (PacketUtils.NQazztK(packet)) {
               return false;
            } else {
               PacketReceiveEvent event = new PacketReceiveEvent(packet);
               EventBus.post(event);
               return event.isCanceled();
            }
         } catch (Throwable var3) {
            log("onNetworkReceive", var3);
            return false;
         }
      }
   }

   public static void onPlayerUpdateHead(Object playerObj) {
      if (!mixinEventBridgeActive) {
         try {
            EntityPlayerSP player = (EntityPlayerSP)playerObj;
            if (ClientUtils.isInNestedUpdate()) {
               return;
            }

            if (player.worldObj.isBlockLoaded(new BlockPos(player.posX, 0.0, player.posZ))) {
               RotationUtils.previousPitch = RotationUtils.pN0;
               RotationUtils.IuZ = RotationUtils.outgoingYaw;
               EventBus.post(new PreUpdateEvent());
            }
         } catch (Throwable var2) {
            log("onPlayerUpdateHead", var2);
         }
      }
   }

   public static void onPlayerUpdateReturn(Object playerObj) {
      if (!mixinEventBridgeActive) {
         try {
            EntityPlayerSP player = (EntityPlayerSP)playerObj;
            if (!ClientUtils.isInNestedUpdate() && player.worldObj.isBlockLoaded(new BlockPos(player.posX, 0.0, player.posZ))) {
               EventBus.post(new PostUpdateEvent());
            }
         } catch (Throwable var2) {
            log("onPlayerUpdateReturn", var2);
         }
      }
   }

   public static boolean onUpdateWalkingPlayer(Object playerObj) {
      if (mixinEventBridgeActive) {
         return false;
      } else {
         try {
            EntityPlayerSP player = (EntityPlayerSP)playerObj;
            UpdateWalkingPlayerEvent.QhtJiq = false;
            UpdateWalkingPlayerEvent.setYawOverrideRequested(false);
            RotationUtils.hasPendingRotationOverride = false;
            UpdateWalkingPlayerEvent event = new UpdateWalkingPlayerEvent(
               player.posX,
               player.getEntityBoundingBox().minY,
               player.posZ,
               player.rotationYaw,
               player.rotationPitch,
               player.onGround,
               player.isSprinting(),
               player.isSneaking()
            );
            EventBus.post(event);
            RotationUtils.lastSentRotation = new float[]{event.getYaw(), event.getPitch()};
            boolean sprinting = event.isSprinting();
            boolean serverSprint = getBooleanField(player, new String[]{"serverSprintState", "field_175171_bO"});
            if (sprinting != serverSprint) {
               player.sendQueue.addToSendQueue(new C0BPacketEntityAction(player, sprinting ? Action.START_SPRINTING : Action.STOP_SPRINTING));
               setBooleanField(player, sprinting, new String[]{"serverSprintState", "field_175171_bO"});
            }

            boolean sneaking = event.isSneaking();
            boolean serverSneak = getBooleanField(player, new String[]{"serverSneakState", "field_175170_bN"});
            if (sneaking != serverSneak) {
               player.sendQueue.addToSendQueue(new C0BPacketEntityAction(player, sneaking ? Action.START_SNEAKING : Action.STOP_SNEAKING));
               setBooleanField(player, sneaking, new String[]{"serverSneakState", "field_175170_bN"});
            }

            if (invokeBoolean(player, new String[]{"isCurrentViewEntity", "func_175160_A"}) || Freecam.pqos()) {
               if (UpdateWalkingPlayerEvent.isYawOverrideRequested()) {
                  RotationUtils.applyRenderedYaw(event.getYaw());
               }

               RotationUtils.pN0 = event.getPitch();
               RotationUtils.outgoingYaw = event.getYaw();
               if (RotationUtils.hasPendingRotationOverride) {
                  RotationUtils.pN0 = RotationUtils.gib[1];
                  RotationUtils.outgoingYaw = RotationUtils.gib[0];
                  RotationUtils.applyRenderedYaw(RotationUtils.outgoingYaw);
               }

               RotationUtils.hasPendingRotationOverride = false;
               double lastX = getDoubleField(player, new String[]{"lastReportedPosX", "field_175172_bI"});
               double lastY = getDoubleField(player, new String[]{"lastReportedPosY", "field_175166_bJ"});
               double lastZ = getDoubleField(player, new String[]{"lastReportedPosZ", "field_175167_bK"});
               float lastYaw = getFloatField(player, new String[]{"lastReportedYaw", "field_175164_bL"});
               float lastPitch = getFloatField(player, new String[]{"lastReportedPitch", "field_175165_bM"});
               int updateTicks = getIntField(player, new String[]{"positionUpdateTicks", "field_175168_bP"});
               double dx = event.getX() - lastX;
               double dy = event.getY() - lastY;
               double dz = event.getZ() - lastZ;
               double yawDelta = event.getYaw() - lastYaw;
               double pitchDelta = event.getPitch() - lastPitch;
               boolean moved = dx * dx + dy * dy + dz * dz > 9.0E-4 || updateTicks >= 20;
               boolean rotated = yawDelta != 0.0 || pitchDelta != 0.0;
               if (player.ridingEntity == null) {
                  if (moved && rotated) {
                     player.sendQueue
                        .addToSendQueue(
                           new C06PacketPlayerPosLook(event.getX(), event.getY(), event.getZ(), event.getYaw(), event.getPitch(), event.isOnGround())
                        );
                  } else if (moved) {
                     player.sendQueue.addToSendQueue(new C04PacketPlayerPosition(event.getX(), event.getY(), event.getZ(), event.isOnGround()));
                  } else if (rotated) {
                     player.sendQueue.addToSendQueue(new C05PacketPlayerLook(event.getYaw(), event.getPitch(), event.isOnGround()));
                  } else {
                     player.sendQueue.addToSendQueue(new C03PacketPlayer(event.isOnGround()));
                  }
               } else {
                  player.sendQueue
                     .addToSendQueue(new C06PacketPlayerPosLook(player.motionX, -999.0, player.motionZ, event.getYaw(), event.getPitch(), event.isOnGround()));
                  moved = false;
               }

               setIntField(player, updateTicks + 1, new String[]{"positionUpdateTicks", "field_175168_bP"});
               if (moved) {
                  setDoubleField(player, event.getX(), new String[]{"lastReportedPosX", "field_175172_bI"});
                  setDoubleField(player, event.getY(), new String[]{"lastReportedPosY", "field_175166_bJ"});
                  setDoubleField(player, event.getZ(), new String[]{"lastReportedPosZ", "field_175167_bK"});
                  setIntField(player, 0, new String[]{"positionUpdateTicks", "field_175168_bP"});
               }

               if (rotated) {
                  setFloatField(player, event.getYaw(), new String[]{"lastReportedYaw", "field_175164_bL"});
                  setFloatField(player, event.getPitch(), new String[]{"lastReportedPitch", "field_175165_bM"});
               }
            }

            EventBus.post(new PostWalkingUpdateEvent());
            return true;
         } catch (Throwable var28) {
            log("onUpdateWalkingPlayer", var28);
            return false;
         }
      }
   }

   public static boolean onPlayerLivingUpdateBeforeSuper(Object playerObj) {
      if (mixinEventBridgeActive) {
         return false;
      } else {
         try {
            EntityPlayerSP player = (EntityPlayerSP)playerObj;
            if (applyNoJumpDelay(player)) {
               InjectionAgent.recordRuntimeSignal(
                  "mutation",
                  "DelayRemover.jumpTicks.before_living_super"
               );
            }

            RuntimeAccess.setSidecarFlag(
               player, "wasGroundBeforeLivingUpdate", player.onGround
            );
            Minecraft mc = (Minecraft)getField(player, new String[]{"mc", "field_71159_c"});
            MovementInput input = player.movementInput;
            int sprintTicks = getIntField(player, new String[]{"sprintingTicksLeft", "field_71157_e"});
            if (sprintTicks > 0) {
               setIntField(player, --sprintTicks, new String[]{"sprintingTicksLeft", "field_71157_e"});
               if (sprintTicks == 0) {
                  player.setSprinting(false);
               }
            }

            int sprintToggle = getIntField(player, new String[]{"sprintToggleTimer", "field_71156_d"});
            if (sprintToggle > 0) {
               setIntField(player, sprintToggle - 1, new String[]{"sprintToggleTimer", "field_71156_d"});
            }

            player.prevTimeInPortal = player.timeInPortal;
            if (getBooleanField(player, new String[]{"inPortal", "field_71087_bX"})) {
               if (mc.currentScreen != null && !mc.currentScreen.doesGuiPauseGame()) {
                  mc.displayGuiScreen(null);
               }

               if (player.timeInPortal == 0.0F) {
                  mc.getSoundHandler()
                     .playSound(
                        PositionedSoundRecord.create(
                           new ResourceLocation("portal.trigger"),
                           player.getRNG().nextFloat() * 0.4F + 0.8F
                        )
                     );
               }

               player.timeInPortal = Math.min(1.0F, player.timeInPortal + 0.0125F);
               setBooleanField(player, false, new String[]{"inPortal", "field_71087_bX"});
            } else if (!player.isPotionActive(Potion.confusion)
               || player.getActivePotionEffect(Potion.confusion).getDuration() <= 60
               || Jade.getModuleManager().getModule(AntiDebuff.class) != null && Jade.getModuleManager().getModule(AntiDebuff.class).blocksConfusion(Potion.confusion)) {
               player.timeInPortal = Math.max(0.0F, player.timeInPortal - 0.05F);
            } else {
               player.timeInPortal = Math.min(1.0F, player.timeInPortal + 0.006666667F);
            }

            if (player.timeUntilPortal > 0) {
               player.timeUntilPortal--;
            }

            boolean wasJumping = input.jump;
            boolean wasSneaking = input.sneak;
            float sprintThreshold = 0.8F;
            boolean hadForwardInput = input.moveForward >= sprintThreshold;
            input.updatePlayerMoveState();
            boolean naturalForward = input.moveForward >= sprintThreshold;
            boolean keepSprint = NoSlow.CjyFd(naturalForward);
            boolean stopSprint = !keepSprint;
            boolean bypassSlowdown = NoSlow.isVanillaModeActive();
            boolean usingItem = player.isUsingItem() && !bypassSlowdown;
            if (usingItem && !player.isRiding()) {
               input.moveStrafe *= 0.2F;
               input.moveForward *= 0.2F;
               if (stopSprint) {
                  setIntField(player, 0, new String[]{"sprintToggleTimer", "field_71156_d"});
               }
            }

            if (bypassSlowdown && !player.isRiding()) {
               float multiplier = NoSlow.getMovementSpeedMultiplier();
               input.moveStrafe *= multiplier;
               input.moveForward *= multiplier;
            }

            double width = player.width * 0.35;
            double minY = player.getEntityBoundingBox().minY + 0.5;
            invokeMethod(player, new String[]{"pushOutOfBlocks", "func_145771_j"}, new Class[]{double.class, double.class, double.class}, player.posX - width, minY, player.posZ + width);
            invokeMethod(player, new String[]{"pushOutOfBlocks", "func_145771_j"}, new Class[]{double.class, double.class, double.class}, player.posX - width, minY, player.posZ - width);
            invokeMethod(player, new String[]{"pushOutOfBlocks", "func_145771_j"}, new Class[]{double.class, double.class, double.class}, player.posX + width, minY, player.posZ - width);
            invokeMethod(player, new String[]{"pushOutOfBlocks", "func_145771_j"}, new Class[]{double.class, double.class, double.class}, player.posX + width, minY, player.posZ + width);
            boolean canSprint = player.getFoodStats().getFoodLevel() > 6 || player.capabilities.allowFlying;
            Sprint sprint = Jade.getModuleManager().getModule(Sprint.class);
            boolean suppressAirSprint = sprint != null && sprint.shouldSuppressSprint();
            if (suppressAirSprint && player.isSprinting()) {
               player.setSprinting(false);
            }

            boolean sprintKey = (mc.gameSettings.keyBindSprint.isKeyDown() || keepSprint) && !suppressAirSprint;
            boolean usingStopsSprint = (usingItem || player.isBlocking()) && !keepSprint;
            boolean wTap = Jade.getModuleManager().getModule(SprintReset.class) != null && Jade.getModuleManager().getModule(SprintReset.class).isEnabled();
            boolean awaitingRestart = wTap && SprintReset.isAwaitingRestart();
            boolean canRestart = input.moveForward >= sprintThreshold
               && !wasSneaking
               && canSprint
               && !player.isPotionActive(Potion.blindness)
               && (!usingItem || !stopSprint || keepSprint)
               && !player.isCollidedHorizontally
               && !PlayerPacketStateTracker.airFrictionApplied;
            boolean suppressStart = suppressAirSprint || wTap && (SprintReset.isRestartSuppressed() || awaitingRestart && !canRestart);
            boolean forward = input.moveForward >= sprintThreshold || keepSprint;
            if (!suppressStart
               && player.onGround
               && !wasSneaking
               && !hadForwardInput
               && forward
               && !player.isSprinting()
               && canSprint
               && (!usingItem || !stopSprint || keepSprint)
               && !player.isPotionActive(Potion.blindness)) {
               int toggle = getIntField(player, new String[]{"sprintToggleTimer", "field_71156_d"});
               if (toggle <= 0 && !sprintKey) {
                  setIntField(player, 7, new String[]{"sprintToggleTimer", "field_71156_d"});
               } else {
                  player.setSprinting(true);
               }
            }

            if (!suppressStart
               && !player.isSprinting()
               && sprintKey
               && (input.moveForward != 0.0F || input.moveStrafe != 0.0F)
               && forward
               && canSprint
               && (!usingStopsSprint || !stopSprint)
               && !player.isPotionActive(Potion.blindness)) {
               player.setSprinting(true);
            }

            boolean sprintReset = wTap && (SprintReset.resetRequested || SprintReset.isResetInProgress());
            boolean lacksInput = input.moveForward < sprintThreshold && !keepSprint;
            if (!player.isSprinting()
               || !lacksInput
                  && canSprint
                  && !player.isCollidedHorizontally
                  && !PlayerPacketStateTracker.airFrictionApplied
                  && !usingStopsSprint
                  && (input.moveForward != 0.0F || input.moveStrafe != 0.0F)
                  && !mc.gameSettings.keyBindSneak.isKeyDown()
                  && !sprintReset) {
               if (sprintReset) {
                  SprintReset.clearResetInProgress();
               }
            } else {
               player.setSprinting(false);
               SprintReset.resetRequested = false;
               SprintReset.clearResetInProgress();
            }

            if (awaitingRestart && player.isSprinting()) {
               SprintReset.clearAwaitingRestart();
            }

            SprintReset.decrementRestartSuppression();
            int flyToggle = getIntField(player, new String[]{"flyToggleTimer", "field_71101_bC"});
            if (player.capabilities.allowFlying) {
               if (mc.playerController.isSpectatorMode()) {
                  if (!player.capabilities.isFlying) {
                     player.capabilities.isFlying = true;
                     player.sendPlayerAbilities();
                  }
               } else if (!wasJumping && input.jump) {
                  if (flyToggle == 0) {
                     setIntField(player, 7, new String[]{"flyToggleTimer", "field_71101_bC"});
                  } else {
                     player.capabilities.isFlying = !player.capabilities.isFlying;
                     player.sendPlayerAbilities();
                     setIntField(player, 0, new String[]{"flyToggleTimer", "field_71101_bC"});
                  }
               }
            }

            if (player.capabilities.isFlying && invokeBoolean(player, new String[]{"isCurrentViewEntity", "func_175160_A"})) {
               if (input.sneak) {
                  player.motionY = player.motionY - player.capabilities.getFlySpeed() * 3.0F;
               }

               if (input.jump) {
                  player.motionY = player.motionY + player.capabilities.getFlySpeed() * 3.0F;
               }
            }

            if (invokeBoolean(player, new String[]{"isRidingHorse", "func_110317_t"})) {
               int counter = getIntField(player, new String[]{"horseJumpPowerCounter", "field_110320_a"});
               float power = getFloatField(player, new String[]{"horseJumpPower", "field_110321_bQ"});
               if (counter < 0) {
                  if (++counter == 0) {
                     power = 0.0F;
                  }
               }

               if (wasJumping && !input.jump) {
                  counter = -10;
                  invokeMethod(player, new String[]{"sendHorseJump", "func_110318_g"}, new Class[0]);
               } else if (!wasJumping && input.jump) {
                  counter = 0;
                  power = 0.0F;
               } else if (wasJumping) {
                  counter++;
                  power = counter < 10 ? counter * 0.1F : 0.8F + 2.0F / (counter - 9) * 0.1F;
               }

               setIntField(player, counter, new String[]{"horseJumpPowerCounter", "field_110320_a"});
               setFloatField(player, power, new String[]{"horseJumpPower", "field_110321_bQ"});
            } else {
               setFloatField(player, 0.0F, new String[]{"horseJumpPower", "field_110321_bQ"});
            }

            KeepSprint keepSprintModule = Jade.getModuleManager().getModule(KeepSprint.class);
            if (keepSprintModule != null && keepSprintModule.isEnabled()) {
               keepSprintModule.applyPredictedSprintState(player);
            }

            return true;
         } catch (Throwable var34) {
            log("onPlayerLivingUpdateBeforeSuper", var34);
            return false;
         }
      }
   }

   public static boolean onShouldRemoveBadlionJumpDelay(Object entityObj) {
      if (!mixinEventBridgeActive && InjectionAgent.isBadlionRuntime()) {
         try {
            Minecraft mc = minecraft();
            Object localPlayer = mc == null ? null : minecraftPlayer(mc);
            DelayRemover delayRemover = Jade.getModuleManager().getModule(DelayRemover.class);
            boolean remove = entityObj == localPlayer
               && entityObj instanceof EntityPlayerSP
               && delayRemover != null
               && delayRemover.shouldRemoveJumpTicks((EntityPlayerSP)entityObj);
            if (remove) {
               InjectionAgent.recordRuntimeSignal(
                  "mutation",
                  "DelayRemover.jumpTicks.badlion_direct_field"
               );
            }

            return remove;
         } catch (Throwable var5) {
            log("onShouldRemoveBadlionJumpDelay", var5);
            return false;
         }
      } else {
         return false;
      }
   }

   public static void onPlayerLivingUpdateAfterSuper(Object playerObj) {
      if (!mixinEventBridgeActive) {
         try {
            EntityPlayerSP player = (EntityPlayerSP)playerObj;
            Minecraft mc = (Minecraft)getField(player, new String[]{"mc", "field_71159_c"});
            if (player.onGround && player.capabilities.isFlying && !mc.playerController.isSpectatorMode()) {
               player.capabilities.isFlying = false;
               player.sendPlayerAbilities();
            }
         } catch (Throwable var3) {
            log("onPlayerLivingUpdateAfterSuper", var3);
         }
      }
   }

   public static void onBeforeCloseScreen() {
      if (!mixinEventBridgeActive) {
         try {
            if (Jade.getModuleManager().getModule(InventoryManager.class) != null) {
               Jade.getModuleManager()
                  .getModule(InventoryManager.class)
                  .recoverCursorOnScreenClose("EntityPlayerSP.closeScreen");
            }
         } catch (Throwable var1) {
            log("onBeforeCloseScreen", var1);
         }
      }
   }

   public static void onOrientCameraPre() {
      if (!mixinEventBridgeActive) {
         try {
            freelookEntity = null;
            if (Jade.getModuleManager().getModule(FreeLook.class) == null || !Jade.getModuleManager().getModule(FreeLook.class).isEnabled() || !FreeLook.DnH) {
               return;
            }

            Entity view = getRenderViewEntity(minecraft());
            if (view == null) {
               return;
            }

            freelookEntity = view;
            freelookRotationYaw = getFloatField(view, new String[]{"rotationYaw", "field_70177_z"});
            freelookPrevRotationYaw = getFloatField(view, new String[]{"prevRotationYaw", "field_70126_B"});
            freelookRotationPitch = getFloatField(view, new String[]{"rotationPitch", "field_70125_A"});
            freelookPrevRotationPitch = getFloatField(view, new String[]{"prevRotationPitch", "field_70127_C"});
            setFloatField(view, FreeLook.savedYaw, new String[]{"rotationYaw", "field_70177_z"});
            setFloatField(view, FreeLook.savedYaw, new String[]{"prevRotationYaw", "field_70126_B"});
            setFloatField(view, FreeLook.savedPitch, new String[]{"rotationPitch", "field_70125_A"});
            setFloatField(view, FreeLook.savedPitch, new String[]{"prevRotationPitch", "field_70127_C"});
         } catch (Throwable var1) {
            log("onOrientCameraPre", var1);
         }
      }
   }

   public static void onOrientCameraPost() {
      if (!mixinEventBridgeActive) {
         try {
            if (freelookEntity == null) {
               return;
            }

            setFloatField(freelookEntity, freelookRotationYaw, new String[]{"rotationYaw", "field_70177_z"});
            setFloatField(freelookEntity, freelookPrevRotationYaw, new String[]{"prevRotationYaw", "field_70126_B"});
            setFloatField(freelookEntity, freelookRotationPitch, new String[]{"rotationPitch", "field_70125_A"});
            setFloatField(freelookEntity, freelookPrevRotationPitch, new String[]{"prevRotationPitch", "field_70127_C"});
            freelookEntity = null;
         } catch (Throwable var1) {
            log("onOrientCameraPost", var1);
         }
      }
   }

   public static double onCameraClipDistance(Object fromObj, Object toObj) {
      try {
         return !mixinEventBridgeActive && Jade.getModuleManager().getModule(NoCameraClip.class) != null && Jade.getModuleManager().getModule(NoCameraClip.class).isEnabled()
            ? 4.0
            : ((Vec3)fromObj).distanceTo((Vec3)toObj);
      } catch (Throwable var3) {
         log("onCameraClipDistance", var3);
         return 4.0;
      }
   }

   public static boolean onFreelookMouseFocus(Object minecraftObj, boolean original) {
      try {
         Minecraft mc = (Minecraft)minecraftObj;
         return !mixinEventBridgeActive
               && Jade.getModuleManager().getModule(FreeLook.class) != null
               && Jade.getModuleManager().getModule(FreeLook.class).isEnabled()
               && FreeLook.DnH
            ? FreeLook.updateFreeLookCamera(mc)
            : original;
      } catch (Throwable var3) {
         log("onFreelookMouseFocus", var3);
         return original;
      }
   }

   public static void onFreecamSetAngles(Object playerObj, float yawDelta, float pitchDelta) {
      try {
         if (!onFreecamEntitySetAngles(playerObj, yawDelta, pitchDelta)) {
            ((Entity)playerObj).setAngles(yawDelta, pitchDelta);
         }
      } catch (Throwable var6) {
         log("onFreecamSetAngles", var6);

         try {
            ((Entity)playerObj).setAngles(yawDelta, pitchDelta);
         } catch (Throwable var5) {
         }
      }
   }

   public static boolean onFreecamEntitySetAngles(Object entityObj, float yawDelta, float pitchDelta) {
      try {
         Minecraft mc = minecraft();
         return mc != null && entityObj == mc.thePlayer && Freecam.applyCameraAngles(yawDelta, pitchDelta);
      } catch (Throwable var4) {
         log("onFreecamEntitySetAngles", var4);
         return false;
      }
   }

   public static boolean onBlindnessPotionCheck(Object entityObj, Object potionObj) {
      try {
         Potion potion = (Potion)potionObj;
         return !mixinEventBridgeActive && Jade.getModuleManager().getModule(AntiDebuff.class) != null && Jade.getModuleManager().getModule(AntiDebuff.class).blocksBlindness(potion)
            ? false
            : ((EntityLivingBase)entityObj).isPotionActive(potion);
      } catch (Throwable var3) {
         log("onBlindnessPotionCheck", var3);
         return false;
      }
   }

   public static boolean onNauseaPotionCheck(Object entityObj, Object potionObj) {
      try {
         Potion potion = (Potion)potionObj;
         return !mixinEventBridgeActive && Jade.getModuleManager().getModule(AntiDebuff.class) != null && Jade.getModuleManager().getModule(AntiDebuff.class).blocksConfusion(potion)
            ? false
            : ((EntityPlayerSP)entityObj).isPotionActive(potion);
      } catch (Throwable var3) {
         log("onNauseaPotionCheck", var3);
         return false;
      }
   }

   public static void onRenderWorldMouseOver() {
      if (!mixinEventBridgeActive) {
         try {
            EventBus.post(new MouseOverEvent());
         } catch (Throwable var1) {
            log("onRenderWorldMouseOver", var1);
         }
      }
   }

   public static void onRenderWorldLast(float partialTicks) {
      if (!mixinEventBridgeActive && EventBus.hasListeners(RenderWorldLastEvent.class)) {
         try {
            EventBus.post(new RenderWorldLastEvent(partialTicks));
         } catch (Throwable var2) {
            log("onRenderWorldLast", var2);
         }
      }
   }

   public static void onRenderWorldLast(float partialTicks, boolean isolatedCamera) {
      if (EventBus.hasListeners(RenderWorldLastEvent.class)) {
         if (!isolatedCamera) {
            onRenderWorldLast(partialTicks);
         } else if (!mixinEventBridgeActive) {
            int previousMatrixMode = 5888;
            boolean projectionPushed = false;
            boolean modelViewPushed = false;

            try {
               Minecraft mc = minecraft();
               Object renderer = mc == null ? null : mc.entityRenderer;
               if (renderer != null && mc.displayWidth > 0 && mc.displayHeight > 0) {
                  previousMatrixMode = GL11.glGetInteger(2976);
                  GlStateManager.matrixMode(5889);
                  GL11.glPushMatrix();
                  projectionPushed = true;
                  GlStateManager.matrixMode(5888);
                  GL11.glPushMatrix();
                  modelViewPushed = true;
                  Method setupCamera = findMethod(renderer.getClass(), new String[]{"setupCameraTransform", "func_78479_a"}, new Class[]{float.class, int.class});
                  if (setupCamera == null) {
                     throw new NoSuchMethodException(
                        "EntityRenderer.setupCameraTransform(float,int)"
                     );
                  }

                  try {
                     setupCamera.invoke(renderer, partialTicks, 0);
                  } catch (Throwable var13) {
                     log("onRenderWorldLast camera", var13);
                  }

                  GlStateManager.enableDepth();
                  EventBus.post(new RenderWorldLastEvent(partialTicks));
                  return;
               }

               EventBus.post(new RenderWorldLastEvent(partialTicks));
            } catch (Throwable var14) {
               log("onRenderWorldLast", var14);
               return;
            } finally {
               if (modelViewPushed) {
                  GlStateManager.matrixMode(5888);
                  GL11.glPopMatrix();
               }

               if (projectionPushed) {
                  GlStateManager.matrixMode(5889);
                  GL11.glPopMatrix();
               }

               GlStateManager.matrixMode(previousMatrixMode);
            }
         }
      }
   }

   public static void onGetMouseOverPre() {
      if (!mixinEventBridgeActive) {
         try {
            RotationHandler rh = RotationHandler.getInstance();
            rh.updateTargetRotation();
            if (rh.renderRotationApplied) {
               return;
            }

            Entity view = getRenderViewEntity(minecraft());
            if (view != null && rh.abxpJn()) {
               Float yaw = rh.getTargetYaw();
               Float pitch = rh.cvZx();
               if (yaw != null && !yaw.isNaN() && pitch != null && !pitch.isNaN()) {
                  rh.mNwrQ(view, yaw, pitch, true);
                  rh.renderRotationApplied = true;
               }
            }
         } catch (Throwable var4) {
            log("onGetMouseOverPre", var4);
         }
      }
   }

   public static void onGetMouseOverPost(float partialTicks) {
      if (!mixinEventBridgeActive) {
         try {
            if (Jade.getModuleManager().getModule(BedNuker.class) != null && Jade.getModuleManager().getModule(BedNuker.class).shouldOverridePointedObject()) {
               Jade.getModuleManager().getModule(BedNuker.class).applyPointedObjectOverride(partialTicks);
            } else if (Jade.getModuleManager().getModule(BridgeNuker.class) != null && Jade.getModuleManager().getModule(BridgeNuker.class).shouldOverridePointedObject()) {
               Jade.getModuleManager().getModule(BridgeNuker.class).applyPointedObjectOverride(partialTicks);
            } else if (Jade.getModuleManager().getModule(GhostHand.class) != null && Jade.getModuleManager().getModule(GhostHand.class).shouldOverridePointedObject()) {
               Jade.getModuleManager().getModule(GhostHand.class).applyPointedObjectOverride(partialTicks);
            } else if (Jade.getModuleManager().getModule(Piercing.class) != null && Jade.getModuleManager().getModule(Piercing.class).shouldOverridePointedObject()) {
               Jade.getModuleManager().getModule(Piercing.class).applyPointedObjectOverride(partialTicks);
            }

            RotationHandler rh = RotationHandler.getInstance();
            if (rh.renderRotationApplied) {
               Entity view = getRenderViewEntity(minecraft());
               if (view != null) {
                  rh.restoreEntityRotation(view);
               }

               rh.renderRotationApplied = false;
            }

            EventBus.post(new MouseOverEvent());
            onPrePlayerInteract();
         } catch (Throwable var3) {
            log("onGetMouseOverPost", var3);
         }
      }
   }

   public static void onRenderHud(float partialTicks) {
      if (!mixinEventBridgeActive) {
         try {
            ensureJadeStarted();
            Minecraft mc = minecraft();
            EventBus.post(new RenderTickEvent(EventPhase.END, partialTicks));
            if (mc != null && minecraftPlayer(mc) != null) {
               publishFirstWorldAnnouncement();
               drainServerChat(mc);
               drainIrcChat(mc);
            }

            renderAnnouncement(mc);
         } catch (Throwable var2) {
            log("onRenderHud", var2);
         }
      }
   }

   public static boolean onSendUseItem(Object itemStackObj) {
      if (mixinEventBridgeActive) {
         return false;
      } else if (Freecam.pqos()) {
         return true;
      } else {
         try {
            UseItemEvent event = new UseItemEvent((ItemStack)itemStackObj);
            EventBus.post(event);
            return event.isCanceled();
         } catch (Throwable var2) {
            log("onSendUseItem", var2);
            return false;
         }
      }
   }

   public static boolean onAttackEntity(Object playerObj, Object targetObj) {
      if (mixinEventBridgeActive) {
         return false;
      } else if (Freecam.pqos()) {
         return true;
      } else {
         try {
            KeepSprint keepSprint = Jade.getModuleManager().getModule(KeepSprint.class);
            if (keepSprint != null && keepSprint.isEnabled() && keepSprint.shouldCancelAttack((EntityPlayer)playerObj, (Entity)targetObj)) {
               return true;
            } else {
               AttackEntityEvent event = new AttackEntityEvent((Entity)targetObj, (EntityPlayer)playerObj, true);
               EventBus.post(event);
               return event.isCanceled();
            }
         } catch (Throwable var4) {
            log("onAttackEntity", var4);
            return false;
         }
      }
   }

   private static boolean applyNoJumpDelay(Object player) throws Exception {
      DelayRemover delayRemover = Jade.getModuleManager().getModule(DelayRemover.class);
      if (delayRemover != null && player instanceof EntityPlayerSP && delayRemover.shouldRemoveJumpTicks((EntityPlayerSP)player)) {
         setIntField(player, 0, new String[]{"jumpTicks", "field_70773_bE"});
         return true;
      } else {
         return false;
      }
   }

   public static void onPlayerDamageBlockHead(Object controllerObj) {
      if (!mixinEventBridgeActive) {
         try {
            synchronized (PRE_DAMAGE_PROGRESS) {
               PRE_DAMAGE_PROGRESS.put(controllerObj, getFloatField(controllerObj, new String[]{"curBlockDamageMP", "field_78770_f"}));
            }
         } catch (Throwable var4) {
            log("onPlayerDamageBlockHead", var4);
         }
      }
   }

   public static boolean onCancelFreecamBlockInteraction() {
      return Freecam.pqos();
   }

   public static void onPlayerDamageBlockReturn(Object controllerObj) {
      if (!mixinEventBridgeActive) {
         try {
            Float boxed;
            synchronized (PRE_DAMAGE_PROGRESS) {
               boxed = PRE_DAMAGE_PROGRESS.remove(controllerObj);
            }

            if (boxed == null) {
               return;
            }

            float before = boxed;
            float current = getFloatField(controllerObj, new String[]{"curBlockDamageMP", "field_78770_f"});
            float delta = current - before;
            if (delta <= 0.0F) {
               return;
            }

            float multiplier = getActiveMiningIncrementMultiplier();
            if (multiplier <= 1.0F) {
               return;
            }

            setFloatField(controllerObj, Math.min(1.0F, before + delta * multiplier), new String[]{"curBlockDamageMP", "field_78770_f"});
         } catch (Throwable var7) {
            log("onPlayerDamageBlockReturn", var7);
         }
      }
   }

   public static void onFastMineBlockHitDelay(Object controllerObj) {
      if (!mixinEventBridgeActive) {
         try {
            int delay = -1;
            BedNuker bedNuker = Jade.getModuleManager().getModule(BedNuker.class);
            if (bedNuker != null && bedNuker.isNuking()) {
               delay = bedNuker.getBlockHitDelay();
            } else {
               BridgeNuker bridgeNuker = Jade.getModuleManager().getModule(BridgeNuker.class);
               if (bridgeNuker != null && bridgeNuker.PfjH()) {
                  delay = bridgeNuker.getBreakDelayTicks();
               } else {
                  FastBreak fm = Jade.getModuleManager().getModule(FastBreak.class);
                  if (fm != null) {
                     delay = fm.getBlockHitDelayOverride();
                  }
               }
            }

            if (delay >= 0 && delay < 5) {
               setIntField(controllerObj, delay, new String[]{"blockHitDelay", "field_78781_i"});
            }
         } catch (Throwable var5) {
            log("onFastMineBlockHitDelay", var5);
         }
      }
   }

   public static boolean onHandleChat(Object packetObj) {
      if (mixinEventBridgeActive) {
         return false;
      } else {
         try {
            S02PacketChat packet = (S02PacketChat)packetObj;
            IChatComponent component = packet.getChatComponent();
            if (ChatUtils.hasFlowTag(component.getFormattedText())) {
               ChatUtils.registerComponentText(component);
               setField(
                  packet,
                  "chatComponent",
                  "field_148919_a",
                  ChatUtils.copyWithoutFlow(component)
               );
               component = packet.getChatComponent();
            }

            ChatReceivedEvent event = new ChatReceivedEvent(packet.getType(), component);
            InjectionAgent.recordRuntimeSignal(
               "chat",
               "incoming.type_" + packet.getType()
            );
            EventBus.post(event);
            InjectionAgent.recordRuntimeSignal(
               "chat",
               event.isCanceled()
                  ? "incoming.canceled"
                  : "incoming.delivered"
            );
            return event.isCanceled();
         } catch (Throwable var4) {
            log("onHandleChat", var4);
            return false;
         }
      }
   }

   public static void onHandleDisconnect() {
      if (!mixinEventBridgeActive) {
         try {
            EventBus.post(new DisconnectEvent());
         } catch (Throwable var1) {
            log("onHandleDisconnect", var1);
         }
      }
   }

   public static boolean onHandleEntityVelocity(Object packetObj) {
      if (mixinEventBridgeActive) {
         return false;
      } else {
         try {
            VelocityEvent event = new VelocityEvent((S12PacketEntityVelocity)packetObj);
            EventBus.post(event);
            return event.isCanceled();
         } catch (Throwable var2) {
            log("onHandleEntityVelocity", var2);
            return false;
         }
      }
   }

   public static boolean onHandleExplosion(Object packetObj) {
      if (mixinEventBridgeActive) {
         return false;
      } else {
         try {
            ExplosionEvent event = new ExplosionEvent((S27PacketExplosion)packetObj);
            EventBus.post(event);
            return event.isCanceled();
         } catch (Throwable var2) {
            log("onHandleExplosion", var2);
            return false;
         }
      }
   }

   public static void onPassiveFastMine(Object minecraftObj) {
      if (!mixinEventBridgeActive) {
         try {
            Minecraft mc = (Minecraft)minecraftObj;
            BedNuker bedNuker = Jade.getModuleManager().getModule(BedNuker.class);
            if (bedNuker != null && bedNuker.isNuking()) {
               bedNuker.QLkR();
               return;
            }

            BridgeNuker bridgeNuker = Jade.getModuleManager().getModule(BridgeNuker.class);
            if (bridgeNuker != null && bridgeNuker.PfjH()) {
               return;
            }

            FastBreak fm = Jade.getModuleManager().getModule(FastBreak.class);
            if (fm != null) {
               fm.decreaseBlockHitDelay(mc);
               fm.boostBlockDamageProgress(mc);
            }
         } catch (Throwable var5) {
            log("onPassiveFastMine", var5);
         }
      }
   }

   private static float getActiveMiningIncrementMultiplier() {
      BedNuker bedNuker = Jade.getModuleManager().getModule(BedNuker.class);
      if (bedNuker != null && bedNuker.isNuking()) {
         return bedNuker.TnokwE();
      } else {
         BridgeNuker bridgeNuker = Jade.getModuleManager().getModule(BridgeNuker.class);
         return bridgeNuker != null && bridgeNuker.PfjH() ? bridgeNuker.getBreakSpeedMultiplier() : 1.0F;
      }
   }

   private static boolean isDown(KeyBinding key) {
      if (key == null) {
         return false;
      } else {
         try {
            Object value = invokeAny(key, new String[]{"isKeyDown", "func_151470_d"});
            return value instanceof Boolean && (Boolean)value;
         } catch (Throwable var2) {
            return false;
         }
      }
   }

   private static void drainServerChat(Minecraft mc) {
      String text;
      while ((text = PendingChatQueue.pollMessage()) != null) {
         if (!text.isEmpty()) {
            try {
               Object player = minecraftPlayer(mc);
               if (player != null) {
                  String line = formatServerChat(text);
                  if (!ClientUtils.LCAP(line)) {
                     invokeMethod(player, new String[]{"addChatMessage", "func_145747_a"}, new Class[]{IChatComponent.class}, new ChatComponentText(ChatUtils.resolveFlowText(line)));
                  }

                  playPling(mc, 1.15F);
                  continue;
               }
            } catch (Throwable var4) {
            }
            break;
         }
      }
   }

   private static void drainIrcChat(Minecraft mc) {
      String notice;
      while ((notice = IrcMessageBus.pollNotice()) != null) {
         if (!notice.isEmpty()) {
            ClientUtils.sendJadeMessage("Jade", notice);
         }
      }

      IrcMessageBus$1 message;
      while ((message = IrcMessageBus.pollIncomingMessage()) != null) {
         if (Settings.irc == null || Settings.irc.isToggled()) {
            try {
               Object player = minecraftPlayer(mc);
               if (player == null) {
                  break;
               }

               String line = formatIrcChat(message);
               if (!ClientUtils.LCAP(line)) {
                  invokeMethod(player, new String[]{"addChatMessage", "func_145747_a"}, new Class[]{IChatComponent.class}, new ChatComponentText(ChatUtils.resolveFlowText(line)));
               }

               if (Settings.ircSounds == null || Settings.ircSounds.isToggled()) {
                  playPling(mc, 1.15F);
               }
            } catch (Throwable var5) {
               break;
            }
         }
      }
   }

   private static String formatServerChat(String text) {
      String formattedText = text.replace('&', '§');
      return ClientUtils.formatGradientPrefix("Jade")
         + " "
         + formattedText;
   }

   private static String formatIrcChat(IrcMessageBus$1 message) {
      String[] colors = ClientUtils.getThemeGradientColors();
      String username = IrcMessageBus.sanitizeSenderName(message.senderName);
      String uid = message.timestamp >= 0L
         ? String.valueOf(message.timestamp)
         : "?";
      String text = IrcMessageBus.sanitizeMessage(message.message);
      String bracketedUid = ChatUtils.applyFlowGradient(
         "\u00a78[\u00a7r"
            + uid
            + "\u00a78]",
         colors[0],
         colors[1]
      );
      return ChatUtils.applyFlowGradient("\u00a7lIRC", colors[0], colors[1])
         + " \u00a7b"
         + username
         + " "
         + bracketedUid
         + "\u00a78: \u00a7f"
         + text;
   }

   private static void publishFirstWorldAnnouncement() {
      if (!shownFirstWorldAnnouncement) {
         shownFirstWorldAnnouncement = true;
         String username = SubscriptionState.getDiscordUsername();
         if (username == null || username.isEmpty()) {
            try {
               Minecraft mc = minecraft();
               Object session = mc == null ? null : invokeAny(mc, new String[]{"getSession", "func_110432_I"});
               Object sessionUsername = session == null ? null : invokeAny(session, new String[]{"getUsername", "func_111285_a"});
               if (sessionUsername instanceof String) {
                  username = (String)sessionUsername;
               }
            } catch (Throwable var4) {
            }
         }

         if (username == null || username.isEmpty()) {
            username = "player";
         }

         StartupAnnouncement.publishAnnouncement(
            System.currentTimeMillis(),
            "Hi |"
               + username
               + "|",
            "Open GUI with |"
               + getGuiBindName()
               + "|"
         );
      }
   }

   private static String getGuiBindName() {
      Module gui = Jade.getModuleManager().getModule(Gui.class);
      int key = gui == null ? 25 : gui.getKeycode();
      if (key == 1069) {
         return "MScrollUp";
      } else if (key == 1070) {
         return "MScrollDown";
      } else if (key >= 1000) {
         return "M" + (key - 1000);
      } else {
         String keyName = Keyboard.getKeyName(key);
         return keyName != null && !keyName.isEmpty() ? keyName : "P";
      }
   }

   private static void renderAnnouncement(Minecraft mc) {
      StartupAnnouncement$0 pending = StartupAnnouncement.getActiveAnnouncement();
      if (pending != null) {
         if (!announcementSoundActive || announcementSoundStartedAt != pending.startedAt) {
            playPling(mc, 1.75F);
            announcementSoundActive = true;
            announcementSoundStartedAt = pending.startedAt;
         }

         UpdateNotification.LkDv(pending.startedAt, pending.firstText, pending.secondText);
      } else if (announcementSoundActive) {
         playPling(mc, 0.55F);
         announcementSoundActive = false;
         announcementSoundStartedAt = -1L;
      }
   }

   private static void playPling(Minecraft mc, float pitch) {
      Object player = mc == null ? null : minecraftPlayer(mc);
      if (player != null) {
         try {
            invokeMethod(
               player,
               new String[]{"playSound", "func_85030_a"},
               new Class[]{String.class, float.class, float.class},
               "note.pling",
               1.0F,
               pitch
            );
         } catch (Throwable var4) {
         }
      }
   }

   private static Minecraft minecraft() {
      try {
         Object mc = invokeStatic(Minecraft.class, new String[]{"getMinecraft", "func_71410_x"});
         return mc instanceof Minecraft ? (Minecraft)mc : null;
      } catch (Throwable var1) {
         return null;
      }
   }

   private static EntityPlayerSP minecraftPlayer(Minecraft mc) {
      if (mc == null) {
         return null;
      } else {
         try {
            Object player = getField(mc, new String[]{"thePlayer", "field_71439_g"});
            return player instanceof EntityPlayerSP ? (EntityPlayerSP)player : null;
         } catch (Throwable var2) {
            return null;
         }
      }
   }

   private static Entity getRenderViewEntity(Minecraft mc) {
      if (mc == null) {
         return null;
      } else {
         try {
            Object view = invokeAny(mc, new String[]{"getRenderViewEntity", "func_175606_aa"});
            return view instanceof Entity ? (Entity)view : null;
         } catch (Throwable var2) {
            return null;
         }
      }
   }

   private static Object getField(Object owner, String... names) throws Exception {
      Field field = findField(owner.getClass(), names);
      return field == null ? null : field.get(owner);
   }

   private static float getFloatField(Object owner, String... names) throws Exception {
      Object value = getField(owner, names);
      return value instanceof Number ? ((Number)value).floatValue() : 0.0F;
   }

   private static double getDoubleField(Object owner, String... names) throws Exception {
      Object value = getField(owner, names);
      return value instanceof Number ? ((Number)value).doubleValue() : 0.0;
   }

   private static int getIntField(Object owner, String... names) throws Exception {
      Object value = getField(owner, names);
      return value instanceof Number ? ((Number)value).intValue() : 0;
   }

   private static boolean getBooleanField(Object owner, String... names) throws Exception {
      Object value = getField(owner, names);
      return value instanceof Boolean && (Boolean)value;
   }

   private static void setFloatField(Object owner, float value, String... names) throws Exception {
      Field field = findField(owner.getClass(), names);
      if (field != null) {
         field.setFloat(owner, value);
      }
   }

   private static void setDoubleField(Object owner, double value, String... names) throws Exception {
      Field field = findField(owner.getClass(), names);
      if (field != null) {
         field.setDouble(owner, value);
      }
   }

   private static void setBooleanField(Object owner, boolean value, String... names) throws Exception {
      Field field = findField(owner.getClass(), names);
      if (field != null) {
         field.setBoolean(owner, value);
      }
   }

   private static void setIntField(Object owner, int value, String... names) throws Exception {
      Field field = findField(owner.getClass(), names);
      if (field != null) {
         field.setInt(owner, value);
      }
   }

   private static void setField(Object owner, String fieldName, Object value) throws Exception {
      Field field = findField(owner.getClass(), fieldName);
      if (field != null) {
         field.set(owner, value);
      }
   }

   private static void setField(Object owner, String nameA, String nameB, Object value) throws Exception {
      Field field = findField(owner.getClass(), nameA, nameB);
      if (field != null) {
         field.set(owner, value);
      }
   }

   private static Field findField(Class<?> type, String... names) {
      return RuntimeAccess.resolveMappedField(type, names);
   }

   private static Object invoke(Object owner, String name, Class<?>[] types, Object... args) throws Exception {
      return invokeMethod(owner, new String[]{name}, types, args);
   }

   private static Object invokeAny(Object owner, String... names) throws Exception {
      return invokeMethod(owner, names, new Class[0]);
   }

   private static boolean invokeBoolean(Object owner, String... names) throws Exception {
      Object value = invokeMethod(owner, names, new Class[0]);
      return value instanceof Boolean && (Boolean)value;
   }

   private static Object invokeStatic(Class<?> owner, String... names) throws Exception {
      Method method = findMethod(owner, names, new Class[0]);
      return method == null ? null : method.invoke(null);
   }

   private static Object invokeMethod(Object owner, String[] names, Class<?>[] types, Object... args) throws Exception {
      Method method = findMethod(owner.getClass(), names, types);
      return method == null ? null : method.invoke(owner, args);
   }

   private static Method findMethod(Class<?> type, String[] names, Class<?>[] types) {
      return RuntimeAccess.resolveMappedMethod(type, types, names);
   }

   private static void log(String where, Throwable t) {
      try {
         String key = where
            + ":"
            + t.getClass().getName()
            + ":"
            + t.getMessage();
         if (!LOGGED_FAILURES.add(key)) {
            return;
         }

         InjectionAgent.recordRuntimeFailure("hook.internal", where, t);
         InjectionAgent.reportDiagnostic(where + " failed: " + t);
      } catch (Throwable var3) {
      }
   }

   private static void log(String where, String message) {
      try {
         InjectionAgent.reportDiagnostic(where + ": " + message);
      } catch (Throwable var3) {
      }
   }
}
