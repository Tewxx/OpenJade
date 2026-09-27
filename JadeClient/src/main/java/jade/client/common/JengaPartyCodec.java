// Jade recovery: original class: jade.deps.eLz.A0teTi
package jade.client.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class JengaPartyCodec {
   public static final String GfmJ = "J9";
   public static final int bD3 = 64;
   private static final int oax = 1;
   private static final int NONCE_BYTES = 8;
   private static final int GCM_TAG_BITS = 96;
   private static final byte[] AAD_LABEL = "jade-jenga-party-v1".getBytes(StandardCharsets.US_ASCII);
   private static final byte[] OrO;
   private static final SecureRandom secureRandom = new SecureRandom();

   private JengaPartyCodec() {
   }

   public static String encodePacket(JengaPartyPacket var0) {
      try {
         byte[] var1 = SViRo4(var0);
         byte[] var2 = new byte[8];
         secureRandom.nextBytes(var2);
         Cipher var3 = Cipher.getInstance("AES/GCM/NoPadding");
         var3.init(1, new SecretKeySpec(OrO, "AES"), new GCMParameterSpec(96, var2));
         var3.updateAAD(AAD_LABEL);
         byte[] var4 = var3.doFinal(var1);
         byte[] var5 = new byte[var2.length + var4.length];
         System.arraycopy(var2, 0, var5, 0, var2.length);
         System.arraycopy(var4, 0, var5, var2.length, var4.length);
         String var6 = "J9" + Base64.getUrlEncoder().withoutPadding().encodeToString(var5);
         if (var6.length() > 64) {
            throw new IllegalArgumentException("Jenga party packet exceeds 64 characters");
         } else {
            return var6;
         }
      } catch (GeneralSecurityException var7) {
         throw new IllegalStateException("Jenga party encryption is unavailable", var7);
      }
   }

   public static JengaPartyPacket decodePacket(String var0) {
      if (!isEncodedPacket(var0)) {
         return null;
      } else {
         try {
            byte[] var1 = Base64.getUrlDecoder().decode(var0.substring("J9".length()));
            if (var1.length <= 8) {
               return null;
            } else {
               byte[] var2 = Arrays.copyOfRange(var1, 0, 8);
               byte[] var3 = Arrays.copyOfRange(var1, 8, var1.length);
               Cipher var4 = Cipher.getInstance("AES/GCM/NoPadding");
               var4.init(2, new SecretKeySpec(OrO, "AES"), new GCMParameterSpec(96, var2));
               var4.updateAAD(AAD_LABEL);
               return deserializePacket(var4.doFinal(var3));
            }
         } catch (Exception var5) {
            return null;
         }
      }
   }

   public static boolean isEncodedPacket(String var0) {
      return var0 != null && var0.length() <= 64 && var0.startsWith("J9");
   }

   private static byte[] SViRo4(JengaPartyPacket var0) {
      if (var0 != null && var0.ciZe >= 1 && var0.ciZe <= 6) {
         try {
            ByteArrayOutputStream var1 = new ByteArrayOutputStream();
            DataOutputStream var2 = new DataOutputStream(var1);
            var2.writeByte(1);
            var2.writeByte(var0.ciZe);
            var2.writeInt(var0.duelId);
            var2.writeShort(var0.sequenceNumber & 65535);
            if (packetCarriesTargetName(var0.ciZe)) {
               byte[] var3 = var0.EUX.getBytes(StandardCharsets.US_ASCII);
               if (var3.length < 1 || var3.length > 16) {
                  throw new IllegalArgumentException("Invalid Jenga duel target");
               }

               var2.writeByte(var3.length);
               var2.write(var3);
            } else if (var0.ciZe == 4) {
               if (var0.blockId < 0 || var0.blockId >= 54) {
                  throw new IllegalArgumentException("Invalid Jenga block ID");
               }

               var2.writeByte(var0.blockId);
            }

            var2.flush();
            return var1.toByteArray();
         } catch (IOException var4) {
            throw new IllegalStateException(var4);
         }
      } else {
         throw new IllegalArgumentException("Invalid Jenga party packet");
      }
   }

   private static JengaPartyPacket deserializePacket(byte[] var0) throws IOException {
      DataInputStream var1 = new DataInputStream(new ByteArrayInputStream(var0));
      if (var1.readUnsignedByte() != 1) {
         return null;
      } else {
         int var2 = var1.readUnsignedByte();
         if (var2 >= 1 && var2 <= 6) {
            int var3 = var1.readInt();
            int var4 = var1.readUnsignedShort();
            String var5 = "";
            int var6 = -1;
            if (packetCarriesTargetName(var2)) {
               int var7 = var1.readUnsignedByte();
               if (var7 < 1 || var7 > 16 || var1.available() != var7) {
                  return null;
               }

               byte[] var8 = new byte[var7];
               var1.readFully(var8);
               var5 = new String(var8, StandardCharsets.US_ASCII);
            } else if (var2 == 4) {
               if (var1.available() != 1) {
                  return null;
               }

               var6 = var1.readUnsignedByte();
               if (var6 >= 54) {
                  return null;
               }
            } else if (var1.available() != 0) {
               return null;
            }

            return new JengaPartyPacket(var2, var3, var4, var5, var6);
         } else {
            return null;
         }
      }
   }

   private static boolean packetCarriesTargetName(int var0) {
      return var0 == 1 || var0 == 2 || var0 == 3;
   }

   static {
      byte[] var10000 = new byte[16];
      var10000[0] = 109;
      var10000[1] = (byte)19;
      var10000[2] = (byte)-72;
      var10000[3] = (byte)66;
      var10000[4] = -105;
      var10000[5] = (byte)44;
      var10000[6] = (byte)80;
      var10000[7] = (byte)-82;
      var10000[8] = (byte)-31;
      var10000[9] = 116;
      var10000[10] = (byte)9;
      var10000[11] = (byte)-58;
      var10000[12] = (byte)53;
      var10000[13] = (byte)-6;
      var10000[14] = (byte)-127;
      var10000[15] = 45;
      OrO = var10000;
   }
}
