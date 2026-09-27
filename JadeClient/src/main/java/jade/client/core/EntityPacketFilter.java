// Jade recovery: original class: jade.deps.eLz.ml3Ek1
package jade.client.core;

import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.network.play.server.S0APacketUseBed;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S0DPacketCollectItem;
import net.minecraft.network.play.server.S0EPacketSpawnObject;
import net.minecraft.network.play.server.S0FPacketSpawnMob;
import net.minecraft.network.play.server.S10PacketSpawnPainting;
import net.minecraft.network.play.server.S11PacketSpawnExperienceOrb;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S19PacketEntityHeadLook;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.network.play.server.S1CPacketEntityMetadata;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;

public final class EntityPacketFilter {
   private static final Class<?>[] ENTITY_PACKET_CLASSES = new Class[]{
      S1CPacketEntityMetadata.class,
      S12PacketEntityVelocity.class,
      S0CPacketSpawnPlayer.class,
      S19PacketEntityStatus.class,
      S04PacketEntityEquipment.class,
      S14PacketEntity.class,
      S0BPacketAnimation.class,
      S1DPacketEntityEffect.class,
      S0FPacketSpawnMob.class,
      S13PacketDestroyEntities.class,
      S1BPacketEntityAttach.class,
      S10PacketSpawnPainting.class,
      S18PacketEntityTeleport.class,
      S0DPacketCollectItem.class,
      S2CPacketSpawnGlobalEntity.class,
      S19PacketEntityHeadLook.class,
      S0APacketUseBed.class,
      S11PacketSpawnExperienceOrb.class,
      S1EPacketRemoveEntityEffect.class,
      S0EPacketSpawnObject.class
   };

   private EntityPacketFilter() {
   }

   public static boolean isEntityPacket(Packet<?> var0) {
      for (Class var4 : ENTITY_PACKET_CLASSES) {
         if (var4.isInstance(var0)) {
            return true;
         }
      }

      return false;
   }
}
