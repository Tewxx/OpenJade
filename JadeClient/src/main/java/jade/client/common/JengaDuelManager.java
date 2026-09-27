// Jade recovery: original class: jade.deps.eLz.Vbr2Xg1I
package jade.client.common;

import jade.client.event.ChatReceivedEvent;
import java.security.SecureRandom;
import net.minecraft.client.Minecraft;

public final class JengaDuelManager {
   private static final long REQUEST_TIMEOUT_MS = 60000L;
   private static final long lFv = 2000L;
   private final JengaGame jengaGame;
   private final Minecraft mc = Minecraft.getMinecraft();
   private final SecureRandom secureRandom = new SecureRandom();
   private JengaDuelManager$0 duelState = JengaDuelManager$0.IDLE;
   private String YJwU = "";
   private int Gig7;
   private int nextSequenceNumber = 1;
   private int QRr = -1;
   private long duelDeadlineMillis;
   private long lastPromptTimeMillis;
   private boolean OnA;
   private boolean MNTA;

   public JengaDuelManager(JengaGame var1) {
      this.jengaGame = var1;
   }

   public boolean requestDuel(String var1) {
      String var2 = this.sanitizeUsername(var1);
      String var3 = this.BKLdWch();
      if (var2.isEmpty()) {
         this.sendStatusMessage("&cuse a valid Minecraft username.");
         return false;
      } else if (var3.isEmpty() || this.mc.thePlayer == null) {
         this.sendStatusMessage("&cjoin Hypixel before requesting a Jenga duel.");
         return false;
      } else if (var2.equalsIgnoreCase(var3)) {
         this.sendStatusMessage("&cyou cannot duel yourself.");
         return false;
      } else if (this.duelState != JengaDuelManager$0.IDLE) {
         this.sendStatusMessage("&cfinish or remove the current Jenga duel first.");
         return false;
      } else {
         this.YJwU = var2;
         this.Gig7 = this.secureRandom.nextInt();
         if (this.Gig7 == 0) {
            this.Gig7 = 1;
         }

         this.nextSequenceNumber = 1;
         this.QRr = -1;
         this.duelDeadlineMillis = System.currentTimeMillis() + 60000L;
         this.duelState = JengaDuelManager$0.REQUEST_SENT;
         this.lPnf(JengaPartyPacket.MHnBdjo(1, this.Gig7, this.FTNqoh(), var2));
         this.sendStatusMessage("&7sent &f" + var2 + " &7a duel request. Waiting for &b.jenga accept&7.");
         return true;
      }
   }

   public boolean Di55() {
      if (this.duelState == JengaDuelManager$0.REQUEST_RECEIVED && System.currentTimeMillis() <= this.duelDeadlineMillis) {
         this.lPnf(JengaPartyPacket.MHnBdjo(2, this.Gig7, this.FTNqoh(), this.YJwU));
         this.duelState = JengaDuelManager$0.WAITING_START;
         this.duelDeadlineMillis = System.currentTimeMillis() + 60000L;
         this.sendStatusMessage("&aaccepted &f" + this.YJwU + "&a's duel request. Preparing the board...");
         return true;
      } else {
         if (this.duelState == JengaDuelManager$0.REQUEST_RECEIVED) {
            this.resetDuelState();
         }

         this.sendStatusMessage("&7there is no pending Jenga duel request.");
         return false;
      }
   }

   public boolean hasPendingDuel() {
      return this.duelState != JengaDuelManager$0.IDLE;
   }

   public boolean isDuelActive() {
      return this.duelState == JengaDuelManager$0.ACTIVE;
   }

   public boolean kDwvQ() {
      return this.duelState == JengaDuelManager$0.ACTIVE && !this.MNTA && this.OnA;
   }

   public void iulQn() {
      long var1 = System.currentTimeMillis();
      if (var1 - this.lastPromptTimeMillis >= 2000L) {
         this.lastPromptTimeMillis = var1;
         this.sendStatusMessage(this.MNTA ? "&7this duel has finished. Use &b.jenga remove &7to clear the board." : "&7wait for &f" + this.YJwU + "&7 to take their turn.");
      }
   }

   public void submitLocalMove(int var1, boolean var2) {
      if (this.kDwvQ()) {
         this.lPnf(JengaPartyPacket.GQWq(this.Gig7, this.FTNqoh(), var1));
         if (var2) {
            this.MNTA = true;
            this.OnA = false;
            this.sendStatusMessage("&cthat move collapsed the tower. You lost to &f" + this.YJwU + "&c.");
         } else {
            this.OnA = false;
            this.sendStatusMessage("&amove accepted. &7Waiting for &f" + this.YJwU + "&7...");
         }
      }
   }

   public boolean reportTowerCollapse() {
      if (this.duelState == JengaDuelManager$0.ACTIVE && !this.MNTA) {
         this.lPnf(JengaPartyPacket.createTowerCollapsePacket(this.Gig7, this.FTNqoh()));
         this.MNTA = true;
         this.OnA = false;
         this.sendStatusMessage("&cthe tower fell from your collision. You lost to &f" + this.YJwU + "&c.");
         return true;
      } else {
         return false;
      }
   }

   public void endDuel() {
      if (this.duelState != JengaDuelManager$0.IDLE && this.mc.thePlayer != null) {
         this.lPnf(JengaPartyPacket.edayOj(this.Gig7, this.FTNqoh()));
      }

      this.resetDuelState();
   }

   public void resetDuelState() {
      this.duelState = JengaDuelManager$0.IDLE;
      this.YJwU = "";
      this.Gig7 = 0;
      this.nextSequenceNumber = 1;
      this.QRr = -1;
      this.duelDeadlineMillis = 0L;
      this.OnA = false;
      this.MNTA = false;
   }

   public void checkRequestTimeout() {
      if ((this.duelState == JengaDuelManager$0.REQUEST_SENT || this.duelState == JengaDuelManager$0.REQUEST_RECEIVED || this.duelState == JengaDuelManager$0.WAITING_START)
         && System.currentTimeMillis() > this.duelDeadlineMillis) {
         this.sendStatusMessage("&cJenga duel request timed out.");
         this.resetDuelState();
      }
   }

   public void puuozu(ChatReceivedEvent var1) {
      if (var1 != null && var1.messageType != 2 && var1.iChatComponent != null) {
         PartyChatParser$0 var2 = PartyChatParser.parsePartyMessage(ClientUtils.AOAtn(var1.iChatComponent.getUnformattedText()));
         if (var2 != null && JengaPartyCodec.isEncodedPacket(var2.partyCode)) {
            var1.setCanceled(true);
            JengaPartyPacket var3 = JengaPartyCodec.decodePacket(var2.partyCode);
            if (var3 != null) {
               this.handlePartyPacket(var2.playerName, var3);
            }
         }
      }
   }

   private void handlePartyPacket(String var1, JengaPartyPacket var2) {
      String var3 = this.BKLdWch();
      if (!var1.isEmpty() && !var3.isEmpty() && !var1.equalsIgnoreCase(var3)) {
         if (var2.ciZe == 1) {
            if (var2.EUX.equalsIgnoreCase(var3)) {
               if (this.duelState != JengaDuelManager$0.IDLE) {
                  this.sendStatusMessage("&7ignored a Jenga request from &f" + var1 + " &7because you are already busy.");
               } else {
                  this.YJwU = var1;
                  this.Gig7 = var2.duelId;
                  this.nextSequenceNumber = 1;
                  this.QRr = var2.sequenceNumber;
                  this.duelDeadlineMillis = System.currentTimeMillis() + 60000L;
                  this.duelState = JengaDuelManager$0.REQUEST_RECEIVED;
                  this.sendStatusMessage("&f" + var1 + " &7challenged you to Jenga. Type &b.jenga accept &7within 60 seconds.");
               }
            }
         } else if (var2.ciZe == 2) {
            if (this.duelState == JengaDuelManager$0.REQUEST_SENT && var2.duelId == this.Gig7 && var1.equalsIgnoreCase(this.YJwU) && var2.EUX.equalsIgnoreCase(var3)) {
               this.QRr = var2.sequenceNumber;
               this.lPnf(JengaPartyPacket.MHnBdjo(3, this.Gig7, this.FTNqoh(), this.YJwU));
               this.startDuel(true);
            }
         } else if (var2.ciZe == 3) {
            if (this.duelState == JengaDuelManager$0.WAITING_START && var2.duelId == this.Gig7 && var1.equalsIgnoreCase(this.YJwU) && var2.EUX.equalsIgnoreCase(var3)) {
               this.QRr = var2.sequenceNumber;
               this.startDuel(false);
            }
         } else if (var2.ciZe == 4) {
            if (this.duelState == JengaDuelManager$0.ACTIVE
               && !this.MNTA
               && var2.duelId == this.Gig7
               && var1.equalsIgnoreCase(this.YJwU)
               && !this.OnA
               && this.isNewerSequence(var2.sequenceNumber, this.QRr)) {
               this.QRr = var2.sequenceNumber;
               int var4 = this.jengaGame.applyRemoteMove(var2.blockId, this.Gig7);
               if (var4 < 0) {
                  this.MNTA = true;
                  this.sendStatusMessage("&cduel desynchronized after an invalid move. Remove the board and retry.");
               } else if (var4 > 0) {
                  this.MNTA = true;
                  this.OnA = false;
                  this.sendStatusMessage("&a" + this.YJwU + " collapsed the tower. You win!");
               } else {
                  this.OnA = true;
                  this.sendStatusMessage("&a" + this.YJwU + " completed their move. Your turn!");
               }
            }
         } else if (var2.ciZe == 5 && var2.duelId == this.Gig7 && var1.equalsIgnoreCase(this.YJwU) && this.duelState != JengaDuelManager$0.IDLE) {
            this.sendStatusMessage("&7" + this.YJwU + " ended the Jenga duel.");
            this.resetDuelState();
            this.jengaGame.clearBoard();
         } else {
            if (var2.ciZe == 6
               && this.duelState == JengaDuelManager$0.ACTIVE
               && !this.MNTA
               && var2.duelId == this.Gig7
               && var1.equalsIgnoreCase(this.YJwU)
               && this.isNewerSequence(var2.sequenceNumber, this.QRr)) {
               this.QRr = var2.sequenceNumber;
               this.MNTA = true;
               this.OnA = false;
               this.sendStatusMessage("&a" + this.YJwU + " knocked the tower over. You win!");
            }
         }
      }
   }

   private void startDuel(boolean var1) {
      if (!this.jengaGame.createDuelBoard()) {
         this.lPnf(JengaPartyPacket.edayOj(this.Gig7, this.FTNqoh()));
         this.sendStatusMessage("&ccould not create the duel board in this world.");
         this.resetDuelState();
      } else {
         this.duelState = JengaDuelManager$0.ACTIVE;
         this.MNTA = false;
         this.OnA = var1;
         this.sendStatusMessage("&aJenga duel started against &f" + this.YJwU + "&a. " + (this.OnA ? "&aYour turn!" : "&7They have the first turn."));
      }
   }

   private void lPnf(JengaPartyPacket var1) {
      if (this.mc.thePlayer != null) {
         String var2 = JengaPartyCodec.encodePacket(var1);
         this.mc.thePlayer.sendChatMessage("/pc " + var2);
      }
   }

   private int FTNqoh() {
      int var1 = this.nextSequenceNumber & 65535;
      this.nextSequenceNumber = this.nextSequenceNumber + 1 & 65535;
      return var1;
   }

   private boolean isNewerSequence(int var1, int var2) {
      if (var2 < 0) {
         return true;
      } else {
         int var3 = var1 - var2 & 65535;
         return var3 != 0 && var3 < 32768;
      }
   }

   private String BKLdWch() {
      return this.mc.getSession() != null && this.mc.getSession().getUsername() != null ? this.sanitizeUsername(this.mc.getSession().getUsername()) : "";
   }

   private String sanitizeUsername(String var1) {
      if (var1 == null) {
         return "";
      } else {
         String var2 = var1.trim();
         if (var2.length() >= 1 && var2.length() <= 16) {
            for (int var3 = 0; var3 < var2.length(); var3++) {
               char var4 = var2.charAt(var3);
               if (!Character.isLetterOrDigit(var4) && var4 != '_') {
                  return "";
               }
            }

            return var2;
         } else {
            return "";
         }
      }
   }

   private void sendStatusMessage(String var1) {
      ClientUtils.sendJadeMessage("Jenga", var1);
   }
}
