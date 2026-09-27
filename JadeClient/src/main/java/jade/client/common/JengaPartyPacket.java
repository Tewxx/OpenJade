// Jade recovery: original class: jade.deps.eLz.QlCUnLvv
package jade.client.common;

public final class JengaPartyPacket {
   public static final int TYPE_DUEL_REQUEST = 1;
   public static final int fWvq = 2;
   public static final int puwA = 3;
   public static final int IxyX = 4;
   public static final int TYPE_DUEL_ENDED = 5;
   public static final int TYPE_TOWER_FELL = 6;
   public final int ciZe;
   public final int duelId;
   public final int sequenceNumber;
   public final String EUX;
   public final int blockId;

   public JengaPartyPacket(int var1, int var2, int var3, String var4, int var5) {
      this.ciZe = var1;
      this.duelId = var2;
      this.sequenceNumber = var3;
      this.EUX = var4 == null ? "" : var4;
      this.blockId = var5;
   }

   public static JengaPartyPacket MHnBdjo(int var0, int var1, int var2, String var3) {
      return new JengaPartyPacket(var0, var1, var2, var3, -1);
   }

   public static JengaPartyPacket GQWq(int var0, int var1, int var2) {
      return new JengaPartyPacket(4, var0, var1, "", var2);
   }

   public static JengaPartyPacket edayOj(int var0, int var1) {
      return new JengaPartyPacket(5, var0, var1, "", -1);
   }

   public static JengaPartyPacket createTowerCollapsePacket(int var0, int var1) {
      return new JengaPartyPacket(6, var0, var1, "", -1);
   }
}
