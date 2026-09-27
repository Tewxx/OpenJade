// Jade recovery: module: Packet Debug (other); original class: jade.deps.eLz.JG0qzlGhQo
package jade.client.module.other;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.EventPriority;
import jade.client.common.Subscribe;
import jade.client.event.PacketReceiveEvent;
import jade.client.event.PacketSendEvent;
import jade.client.event.SilentPacketSendEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.setting.BooleanSetting;
import jade.client.setting.DescriptionSetting;
import jade.client.setting.Setting;
import jade.client.setting.SliderSetting;
import jade.client.setting.TextSetting;

import jade.deps.loader107.InjectionPaths;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.TimeZone;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;

@ModuleInfo
public class PacketDebug extends Module {
   private static final String[] cqtgiD = new String[]{"Outgoing", "Incoming", "Both"};
   private static final int EVj = 0;
   private static final int WUUkge = 1;
   private static final int DIRECTION_BOTH = 2;
   private static final int FtaM = 2;
   private static final int MAX_COLLECTION_ELEMENTS = 16;
   private final SliderSetting direction;
   private final SliderSetting maxRawBytes;
   private final SliderSetting flushEvery;
   private final BooleanSetting autoBlockPacketsOnly;
   private final BooleanSetting rawPacketBytes;
   private final BooleanSetting packetFields;
   private final BooleanSetting playerContext;
   private final BooleanSetting enabledModuleSettings;
   private final TextSetting label;
   private final TextSetting packetFilter;
   private BufferedWriter bufferedWriter;
   private File file;
   private long captureStartMillis;
   private int capturedPacketCount;
   private int aQo;

   public PacketDebug() {
      super("Packet Debug", Category.other);
      this.registerSetting(new DescriptionSetting("Capture"));
      this.registerSetting(this.direction = new SliderSetting("Direction", 0, cqtgiD));
      this.registerSetting(
         this.label = new TextSetting(
            "Label",
            "autoblock-test",
            "label",
            64
         )
      );
      this.registerSetting(
         this.packetFilter = new TextSetting(
            "Packet Filter",
            "",
            "C08,C07,C02",
            128
         )
      );
      this.registerSetting(
         this.autoBlockPacketsOnly = new BooleanSetting(
            "AutoBlock packets only",
            true
         )
      );
      this.registerSetting(new DescriptionSetting("Data"));
      this.registerSetting(
         this.rawPacketBytes = new BooleanSetting(
            "Raw packet bytes",
            true
         )
      );
      this.registerSetting(
         this.maxRawBytes = new SliderSetting(
            "Max raw bytes", "bytes", 4096.0, 0.0, 32768.0, 256.0
         )
      );
      this.registerSetting(this.packetFields = new BooleanSetting("Packet fields", true));
      this.registerSetting(this.playerContext = new BooleanSetting("Player context", true));
      this.registerSetting(this.enabledModuleSettings = new BooleanSetting("Enabled module settings", true));
      this.registerSetting(
         this.flushEvery = new SliderSetting(
            "Flush every", "packets", 1.0, 1.0, 100.0, 1.0
         )
      );
      this.registerSetting(new BooleanSetting("Write marker", new Runnable() {
         @Override
         public void run() {
            PacketDebug.writeMarker(PacketDebug.this, "manual");
         }
      }).setButtonText("Mark"));
   }

   @Override
   public void onEnable() {
      this.capturedPacketCount = 0;
      this.aQo = 0;
      this.captureStartMillis = System.currentTimeMillis();
      this.openCaptureFile();
      this.writeSessionMarker("session_start");
      ClientUtils.sendJadeMessage("Packet Debug", "&7capture started: &f" + (this.file == null ? "failed" : this.file.getName()));
   }

   @Override
   public void onDisable() {
      this.writeSessionMarker("session_stop");
      this.closeCaptureFile();
      ClientUtils.sendJadeMessage("Packet Debug", "&7capture stopped. packets=&f" + this.capturedPacketCount + " &7skipped=&f" + this.aQo);
   }

   @Override
   public String getInfo() {
      return this.capturedPacketCount == 0 ? "0" : String.valueOf(this.capturedPacketCount);
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void onPacketSend(PacketSendEvent var1) {
      if (this.matchesDirection(0)) {
         this.zaq7("outbound", EnumPacketDirection.SERVERBOUND, var1.ys98(), var1.isCanceled(), false);
      }
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void onSilentPacketSend(SilentPacketSendEvent var1) {
      if (this.matchesDirection(0)) {
         this.zaq7("outbound_no_event", EnumPacketDirection.SERVERBOUND, var1.ys98(), false, true);
      }
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void onPacketReceive(PacketReceiveEvent var1) {
      if (this.matchesDirection(1)) {
         this.zaq7("inbound", EnumPacketDirection.CLIENTBOUND, var1.ys98(), var1.isCanceled(), false);
      }
   }

   private boolean matchesDirection(int var1) {
      int var2 = (int)this.direction.getInput();
      return var2 == 2 || var2 == var1;
   }

   private void openCaptureFile() {
      try {
         File var1 = new File(InjectionPaths.dataDirectory(mc.mcDataDir), "packet-debug");
         if (!var1.exists() && !var1.mkdirs()) {
            return;
         }

         String var2 = this.sanitizeLabel(this.label.getValue());
         String var3 = this.formatFileTimestamp(this.captureStartMillis) + "-" + var2 + ".jsonl";
         this.file = new File(var1, var3);
         this.bufferedWriter = new BufferedWriter(new FileWriter(this.file, true));
      } catch (IOException var4) {
         this.bufferedWriter = null;
         this.file = null;
         ClientUtils.sendJadeMessage("Packet Debug", "&cfailed to open capture file: " + var4.getMessage());
      }
   }

   private void closeCaptureFile() {
      if (this.bufferedWriter != null) {
         try {
            this.bufferedWriter.flush();
            this.bufferedWriter.close();
         } catch (IOException var5) {
            ClientUtils.sendJadeMessage("Packet Debug", "&cfailed to close capture file: " + var5.getMessage());
         } finally {
            this.bufferedWriter = null;
         }
      }
   }

   private void zaq7(String var1, EnumPacketDirection var2, Packet<?> var3, boolean var4, boolean var5) {
      if (this.bufferedWriter != null && var3 != null) {
         if (!this.shouldCapturePacket(var3)) {
            this.aQo++;
         } else {
            long var6 = System.currentTimeMillis();
            StringBuilder var8 = new StringBuilder(4096);
            var8.append('{');
            this.DOFS(var8, "packet", var6);
            this.appendStringField(var8, "direction", var1);
            this.EEGn(var8, "canceled", var4);
            this.EEGn(var8, "noEvent", var5);
            this.appendLongField(var8, "relativeMs", var6 - this.captureStartMillis);
            this.appendLongField(var8, "sequence", this.capturedPacketCount + 1);
            this.appendPacketInfo(var8, var3, var2);
            if (this.playerContext.isToggled()) {
               this.appendPlayerContext(var8);
            }

            var8.append('}');
            this.JWHg(var8.toString());
            this.capturedPacketCount++;
            if (this.capturedPacketCount % Math.max(1, (int)this.flushEvery.getInput()) == 0) {
               this.flushWriter();
            }
         }
      }
   }

   private boolean shouldCapturePacket(Packet<?> var1) {
      String var2 = var1.getClass().getSimpleName();
      if (this.autoBlockPacketsOnly.isToggled() && !this.isAutoBlockPacket(var2)) {
         return false;
      } else {
         String var3 = this.packetFilter.getValue();
         if (var3 != null && !var3.trim().isEmpty()) {
            String var4 = var1.getClass().getName().toLowerCase();
            String[] var5 = var3.split(",");

            for (String var9 : var5) {
               String var10 = var9.trim().toLowerCase();
               if (!var10.isEmpty() && var4.contains(var10)) {
                  return true;
               }
            }

            return false;
         } else {
            return true;
         }
      }
   }

   private boolean isAutoBlockPacket(String var1) {
      return var1.startsWith("C02")
         || var1.startsWith("C03")
         || var1.startsWith("C07")
         || var1.startsWith("C08")
         || var1.startsWith("C09")
         || var1.startsWith("C0A")
         || var1.startsWith("C0B")
         || var1.startsWith("C0F");
   }

   private void appendPacketInfo(StringBuilder var1, Packet<?> var2, EnumPacketDirection var3) {
      this.openJsonObject(var1, "packet");
      this.appendStringField(var1, "class", var2.getClass().getName());
      this.appendStringField(var1, "simpleClass", var2.getClass().getSimpleName());
      Integer var4 = this.getPlayPacketId(var2, var3);
      if (var4 == null) {
         this.BqlaH8(var1, "playStateId");
      } else {
         this.appendLongField(var1, "playStateId", var4.intValue());
      }

      if (this.rawPacketBytes.isToggled()) {
         this.hAov(var1, var2, var4);
      }

      if (this.packetFields.isToggled()) {
         this.openJsonObject(var1, "fields");
         this.faJq8(var1, var2, new IdentityHashMap<>());
         var1.append('}');
      }

      var1.append('}');
   }

   private Integer getPlayPacketId(Packet<?> var1, EnumPacketDirection var2) {
      try {
         return EnumConnectionState.PLAY.getPacketId(var2, var1);
      } catch (Throwable var4) {
         return null;
      }
   }

   private void hAov(StringBuilder var1, Packet<?> var2, Integer var3) {
      ByteBuf var4 = Unpooled.buffer();
      ByteBuf var5 = Unpooled.buffer();

      try {
         PacketBuffer var6 = new PacketBuffer(var4);
         var2.writePacketData(var6);
         int var7 = var4.readableBytes();
         this.appendLongField(var1, "rawPayloadByteLength", var7);
         int var8 = Math.max(0, (int)this.maxRawBytes.getInput());
         int var9 = var8 == 0 ? 0 : Math.min(var7, var8);
         this.appendStringField(var1, "rawPayloadHex", this.UqqZjr(var4, var9));
         this.EEGn(var1, "rawPayloadTruncated", var9 < var7);
         if (var3 != null) {
            this.writeVarInt(var5, var3);
            var5.writeBytes(var4, var4.readerIndex(), var7);
            int var10 = var5.readableBytes();
            int var11 = var8 == 0 ? 0 : Math.min(var10, var8);
            this.appendLongField(var1, "rawFrameByteLength", var10);
            this.appendStringField(var1, "rawFrameHex", this.UqqZjr(var5, var11));
            this.EEGn(var1, "rawFrameTruncated", var11 < var10);
         }
      } catch (Throwable var15) {
         this.appendStringField(var1, "rawError", var15.getClass().getSimpleName() + ": " + this.getThrowableMessage(var15));
      } finally {
         var4.release();
         var5.release();
      }
   }

   private void faJq8(StringBuilder var1, Object var2, IdentityHashMap<Object, Boolean> var3) {
      Class var4 = var2.getClass();

      for (boolean var5 = false; var4 != null && var4 != Object.class; var4 = var4.getSuperclass()) {
         Field[] var6 = var4.getDeclaredFields();

         for (Field var10 : var6) {
            if (!Modifier.isStatic(var10.getModifiers())) {
               try {
                  var10.setAccessible(true);
                  Object var11 = var10.get(var2);
                  if (var5) {
                     var1.append(',');
                  }

                  this.WxsW(var1, var10.getName());
                  this.appendJsonValue(var1, var11, var3, 0);
                  var5 = true;
               } catch (Throwable var12) {
               }
            }
         }
      }
   }

   private void appendPlayerContext(StringBuilder var1) {
      this.openJsonObject(var1, "context");
      if (mc.thePlayer == null) {
         this.BqlaH8(var1, "player");
         var1.append('}');
      } else {
         this.openJsonObject(var1, "player");
         this.appendStringField(var1, "name", mc.thePlayer.getName());
         this.appendLongField(var1, "entityId", mc.thePlayer.getEntityId());
         this.appendLongField(var1, "ticksExisted", mc.thePlayer.ticksExisted);
         this.appendDoubleField(var1, "posX", mc.thePlayer.posX);
         this.appendDoubleField(var1, "posY", mc.thePlayer.posY);
         this.appendDoubleField(var1, "posZ", mc.thePlayer.posZ);
         this.appendDoubleField(var1, "lastTickPosX", mc.thePlayer.lastTickPosX);
         this.appendDoubleField(var1, "lastTickPosY", mc.thePlayer.lastTickPosY);
         this.appendDoubleField(var1, "lastTickPosZ", mc.thePlayer.lastTickPosZ);
         this.appendDoubleField(var1, "motionX", mc.thePlayer.motionX);
         this.appendDoubleField(var1, "motionY", mc.thePlayer.motionY);
         this.appendDoubleField(var1, "motionZ", mc.thePlayer.motionZ);
         this.appendDoubleField(var1, "rotationYaw", mc.thePlayer.rotationYaw);
         this.appendDoubleField(var1, "rotationPitch", mc.thePlayer.rotationPitch);
         this.appendDoubleField(var1, "prevRotationYaw", mc.thePlayer.prevRotationYaw);
         this.appendDoubleField(var1, "prevRotationPitch", mc.thePlayer.prevRotationPitch);
         this.EEGn(var1, "onGround", mc.thePlayer.onGround);
         this.EEGn(var1, "isSprinting", mc.thePlayer.isSprinting());
         this.EEGn(var1, "isSneaking", mc.thePlayer.isSneaking());
         this.EEGn(var1, "isBlocking", mc.thePlayer.isBlocking());
         this.appendLongField(var1, "hurtTime", mc.thePlayer.hurtTime);
         this.appendLongField(var1, "hurtResistantTime", mc.thePlayer.hurtResistantTime);
         this.appendDoubleField(var1, "fallDistance", mc.thePlayer.fallDistance);
         this.appendDoubleField(var1, "health", mc.thePlayer.getHealth());
         this.appendDoubleField(var1, "absorption", mc.thePlayer.getAbsorptionAmount());
         this.appendLongField(var1, "heldSlot", mc.thePlayer.inventory.currentItem);
         this.appendItemStackField(var1, "heldItem", mc.thePlayer.getHeldItem());
         var1.append('}');
         this.openJsonObject(var1, "input");
         this.EEGn(var1, "mouseLeft", Mouse.isButtonDown(0));
         this.EEGn(var1, "mouseRight", Mouse.isButtonDown(1));
         this.EEGn(var1, "forward", this.isKeyPressed(mc.gameSettings.keyBindForward));
         this.EEGn(var1, "back", this.isKeyPressed(mc.gameSettings.keyBindBack));
         this.EEGn(var1, "left", this.isKeyPressed(mc.gameSettings.keyBindLeft));
         this.EEGn(var1, "right", this.isKeyPressed(mc.gameSettings.keyBindRight));
         this.EEGn(var1, "jump", this.isKeyPressed(mc.gameSettings.keyBindJump));
         this.EEGn(var1, "sneak", this.isKeyPressed(mc.gameSettings.keyBindSneak));
         this.EEGn(var1, "use", this.isKeyPressed(mc.gameSettings.keyBindUseItem));
         this.EEGn(var1, "attack", this.isKeyPressed(mc.gameSettings.keyBindAttack));
         var1.append('}');
         this.openJsonObject(var1, "mouseOver");
         this.appendMouseOver(var1);
         var1.append('}');
         this.appendStringField(var1, "screen", mc.currentScreen == null ? "none" : mc.currentScreen.getClass().getName());
         ServerData var2 = mc.getCurrentServerData();
         this.appendStringField(var1, "server", var2 == null ? "singleplayer_or_unknown" : var2.serverIP);
         if (this.enabledModuleSettings.isToggled()) {
            this.appendEnabledModules(var1);
         }

         var1.append('}');
      }
   }

   private void appendMouseOver(StringBuilder var1) {
      MovingObjectPosition var2 = mc.objectMouseOver;
      if (var2 == null) {
         this.BqlaH8(var1, "type");
      } else {
         this.appendStringField(var1, "type", String.valueOf(var2.typeOfHit));
         if (var2.entityHit != null) {
            Entity var3 = var2.entityHit;
            this.appendLongField(var1, "entityId", var3.getEntityId());
            this.appendStringField(var1, "entityName", var3.getName());
            this.appendStringField(var1, "entityClass", var3.getClass().getName());
            this.appendDoubleField(var1, "entityDistance", mc.thePlayer == null ? 0.0 : mc.thePlayer.getDistanceToEntity(var3));
         }

         if (var2.getBlockPos() != null) {
            this.appendLongField(var1, "blockX", var2.getBlockPos().getX());
            this.appendLongField(var1, "blockY", var2.getBlockPos().getY());
            this.appendLongField(var1, "blockZ", var2.getBlockPos().getZ());
         }

         if (var2.sideHit != null) {
            this.appendStringField(var1, "sideHit", String.valueOf(var2.sideHit));
         }
      }
   }

   private boolean isKeyPressed(KeyBinding var1) {
      return var1 != null && ClientUtils.xusXfhC(var1);
   }

   private void appendItemStackField(StringBuilder var1, String var2, ItemStack var3) {
      this.openJsonObject(var1, var2);
      if (var3 == null) {
         this.BqlaH8(var1, "item");
         var1.append('}');
      } else {
         Item var4 = var3.getItem();
         this.appendStringField(var1, "displayName", var3.getDisplayName());
         this.appendStringField(var1, "registryName", var4 == null ? "null" : String.valueOf(Item.itemRegistry.getNameForObject(var4)));
         this.appendLongField(var1, "itemId", var4 == null ? -1L : Item.getIdFromItem(var4));
         this.appendLongField(var1, "stackSize", var3.stackSize);
         this.appendLongField(var1, "metadata", var3.getMetadata());
         this.appendLongField(var1, "damage", var3.getItemDamage());
         this.EEGn(var1, "hasTag", var3.hasTagCompound());
         if (var3.hasTagCompound()) {
            this.appendStringField(var1, "tag", var3.getTagCompound().toString());
         }

         var1.append('}');
      }
   }

   private void appendEnabledModules(StringBuilder var1) {
      this.openJsonArray(var1, "enabledModules");
      boolean var2 = false;

      for (Module var4 : Jade.getModuleManager().getModules()) {
         if (var4 != null && var4.isEnabled()) {
            if (var2) {
               var1.append(',');
            }

            var1.append('{');
            this.appendStringField(var1, "name", var4.getName());
            this.appendStringField(var1, "category", var4.getCategory().name());
            this.openJsonArray(var1, "settings");
            boolean var5 = false;

            for (Setting var7 : var4.getSettings()) {
               if (var7 != null) {
                  if (var5) {
                     var1.append(',');
                  }

                  var1.append('{');
                  this.appendStringField(var1, "name", var7.getName());
                  this.appendStringField(var1, "value", this.getSettingValueString(var7));
                  var1.append('}');
                  var5 = true;
               }
            }

            var1.append(']');
            var1.append('}');
            var2 = true;
         }
      }

      var1.append(']');
   }

   private String getSettingValueString(Setting var1) {
      if (var1 instanceof BooleanSetting) {
         return String.valueOf(((BooleanSetting)var1).isToggled());
      } else if (var1 instanceof SliderSetting) {
         SliderSetting var2 = (SliderSetting)var1;
         if (var2.isMode && var2.getOptions() != null) {
            int var3 = (int)var2.getInput();
            String[] var4 = var2.getOptions();
            if (var3 >= 0 && var3 < var4.length) {
               return var4[var3];
            }
         }

         return String.valueOf(var2.getInput());
      } else {
         return var1 instanceof TextSetting ? ((TextSetting)var1).getValue() : var1.getClass().getSimpleName();
      }
   }

   private void writeSessionMarker(String var1) {
      if (this.bufferedWriter != null) {
         long var2 = System.currentTimeMillis();
         StringBuilder var4 = new StringBuilder(2048);
         var4.append('{');
         this.DOFS(var4, var1, var2);
         this.appendLongField(var4, "relativeMs", var2 - this.captureStartMillis);
         this.appendStringField(var4, "label", this.label.getValue());
         this.appendStringField(var4, "file", this.file == null ? "" : this.file.getAbsolutePath());
         this.appendStringField(
            var4,
            "note",
            "Client mods can record local outbound packets and inbound server packets. They cannot directly observe another player's serverbound packets unless run on that client or a controlled proxy/server."
         );
         if (this.enabledModuleSettings.isToggled()) {
            this.appendEnabledModules(var4);
         }

         var4.append('}');
         this.JWHg(var4.toString());
         this.flushWriter();
      }
   }

   private void ixvEs(String var1) {
      if (this.isEnabled() && this.bufferedWriter != null) {
         long var2 = System.currentTimeMillis();
         StringBuilder var4 = new StringBuilder(512);
         var4.append('{');
         this.DOFS(var4, "marker", var2);
         this.appendStringField(var4, "reason", var1);
         this.appendLongField(var4, "relativeMs", var2 - this.captureStartMillis);
         var4.append('}');
         this.JWHg(var4.toString());
         this.flushWriter();
         ClientUtils.sendJadeMessage("Packet Debug", "&7marker written");
      } else {
         ClientUtils.sendJadeMessage("Packet Debug", "&cnot capturing");
      }
   }

   private void JWHg(String var1) {
      if (this.bufferedWriter != null) {
         try {
            this.bufferedWriter.write(var1);
            this.bufferedWriter.newLine();
         } catch (IOException var3) {
            ClientUtils.sendJadeMessage("Packet Debug", "&cwrite failed: " + var3.getMessage());
            this.closeCaptureFile();
         }
      }
   }

   private void flushWriter() {
      if (this.bufferedWriter != null) {
         try {
            this.bufferedWriter.flush();
         } catch (IOException var2) {
         }
      }
   }

   private void DOFS(StringBuilder var1, String var2, long var3) {
      this.appendStringField(var1, "type", var2);
      this.appendLongField(var1, "unixMs", var3);
      this.appendStringField(var1, "utcTime", this.aF31(var3));
   }

   private void appendJsonValue(StringBuilder var1, Object var2, IdentityHashMap<Object, Boolean> var3, int var4) {
      if (var2 == null) {
         var1.append("null");
      } else if (var2 instanceof Number || var2 instanceof Boolean) {
         var1.append(String.valueOf(var2));
      } else if (var2 instanceof Character || var2 instanceof CharSequence || var2.getClass().isEnum()) {
         this.appendQuotedString(var1, String.valueOf(var2));
      } else if (var2 instanceof ItemStack) {
         this.appendQuotedString(var1, this.formatItemStack((ItemStack)var2));
      } else if (var2.getClass().isArray()) {
         this.PdVvn(var1, var2, var3, var4);
      } else if (var2 instanceof Collection) {
         this.appendCollection(var1, (Collection<?>)var2, var3, var4);
      } else if (var4 < 2 && !var3.containsKey(var2)) {
         var3.put(var2, Boolean.TRUE);
         var1.append('{');
         this.appendStringField(var1, "class", var2.getClass().getName());
         this.appendStringField(var1, "value", String.valueOf(var2));
         var1.append('}');
      } else {
         this.appendQuotedString(var1, String.valueOf(var2));
      }
   }

   private void PdVvn(StringBuilder var1, Object var2, IdentityHashMap<Object, Boolean> var3, int var4) {
      int var5 = Array.getLength(var2);
      var1.append('[');
      int var6 = Math.min(var5, 16);

      for (int var7 = 0; var7 < var6; var7++) {
         if (var7 > 0) {
            var1.append(',');
         }

         this.appendJsonValue(var1, Array.get(var2, var7), var3, var4 + 1);
      }

      var1.append(']');
   }

   private void appendCollection(StringBuilder var1, Collection<?> var2, IdentityHashMap<Object, Boolean> var3, int var4) {
      var1.append('[');
      int var5 = 0;

      for (Object var7 : var2) {
         if (var5 >= 16) {
            break;
         }

         if (var5 > 0) {
            var1.append(',');
         }

         this.appendJsonValue(var1, var7, var3, var4 + 1);
         var5++;
      }

      var1.append(']');
   }

   private String formatItemStack(ItemStack var1) {
      if (var1 == null) {
         return "null";
      } else {
         Item var2 = var1.getItem();
         String var3 = var2 == null ? "null" : String.valueOf(Item.itemRegistry.getNameForObject(var2));
         return var3 + " x" + var1.stackSize + " meta=" + var1.getMetadata();
      }
   }

   private void openJsonObject(StringBuilder var1, String var2) {
      this.appendFieldName(var1, var2);
      var1.append('{');
   }

   private void openJsonArray(StringBuilder var1, String var2) {
      this.appendFieldName(var1, var2);
      var1.append('[');
   }

   private void appendStringField(StringBuilder var1, String var2, String var3) {
      this.appendFieldName(var1, var2);
      this.appendQuotedString(var1, var3);
   }

   private void appendDoubleField(StringBuilder var1, String var2, double var3) {
      this.appendFieldName(var1, var2);
      if (!Double.isNaN(var3) && !Double.isInfinite(var3)) {
         var1.append(var3);
      } else {
         var1.append("null");
      }
   }

   private void appendLongField(StringBuilder var1, String var2, long var3) {
      this.appendFieldName(var1, var2);
      var1.append(var3);
   }

   private void EEGn(StringBuilder var1, String var2, boolean var3) {
      this.appendFieldName(var1, var2);
      var1.append(var3);
   }

   private void BqlaH8(StringBuilder var1, String var2) {
      this.appendFieldName(var1, var2);
      var1.append("null");
   }

   private void appendFieldName(StringBuilder var1, String var2) {
      if (var1.length() > 0) {
         char var3 = var1.charAt(var1.length() - 1);
         if (var3 != '{' && var3 != '[' && var3 != ',') {
            var1.append(',');
         }
      }

      this.WxsW(var1, var2);
   }

   private void WxsW(StringBuilder var1, String var2) {
      this.appendQuotedString(var1, var2);
      var1.append(':');
   }

   private void appendQuotedString(StringBuilder var1, String var2) {
      var1.append('"');
      String var3 = var2 == null ? "" : var2;

      for (int var4 = 0; var4 < var3.length(); var4++) {
         char var5 = var3.charAt(var4);
         switch (var5) {
            case '\b':
               var1.append("\\b");
               break;
            case '\t':
               var1.append("\\t");
               break;
            case '\n':
               var1.append("\\n");
               break;
            case '\f':
               var1.append("\\f");
               break;
            case '\r':
               var1.append("\\r");
               break;
            case '"':
               var1.append("\\\"");
               break;
            case '\\':
               var1.append("\\\\");
               break;
            default:
               if (var5 < ' ') {
                  var1.append(String.format("\\u%04x", Integer.valueOf(var5)));
               } else {
                  var1.append(var5);
               }
         }
      }

      var1.append('"');
   }

   private String UqqZjr(ByteBuf var1, int var2) {
      StringBuilder var3 = new StringBuilder(var2 * 2);
      int var4 = var1.readerIndex();

      for (int var5 = 0; var5 < var2; var5++) {
         short var6 = var1.getUnsignedByte(var4 + var5);
         if (var6 < 16) {
            var3.append('0');
         }

         var3.append(Integer.toHexString(var6));
      }

      return var3.toString();
   }

   private void writeVarInt(ByteBuf var1, int var2) {
      while ((var2 & -128) != 0) {
         var1.writeByte(var2 & 127 | 128);
         var2 >>>= 7;
      }

      var1.writeByte(var2);
   }

   private String getThrowableMessage(Throwable var1) {
      String var2 = var1.getMessage();
      return var2 == null ? "" : var2;
   }

   private String sanitizeLabel(String var1) {
      String var2 = var1 != null && !var1.trim().isEmpty() ? var1.trim() : "capture";
      StringBuilder var3 = new StringBuilder(var2.length());

      for (int var4 = 0; var4 < var2.length(); var4++) {
         char var5 = var2.charAt(var4);
         if (!Character.isLetterOrDigit(var5) && var5 != '-' && var5 != '_') {
            var3.append('_');
         } else {
            var3.append(var5);
         }
      }

      return var3.length() == 0 ? "capture" : var3.toString();
   }

   private String formatFileTimestamp(long var1) {
      SimpleDateFormat var3 = new SimpleDateFormat("yyyyMMdd-HHmmss-SSS");
      return var3.format(new Date(var1));
   }

   private String aF31(long var1) {
      SimpleDateFormat var3 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
      var3.setTimeZone(TimeZone.getTimeZone("UTC"));
      return var3.format(new Date(var1));
   }

   public static void writeMarker(PacketDebug var0, String var1) {
      var0.ixvEs(var1);
   }
}
