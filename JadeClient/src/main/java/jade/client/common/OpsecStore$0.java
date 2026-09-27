// Jade recovery: original class: jade.deps.eLz.jWwxLaB8$0
package jade.client.common;

import java.util.Collections;
import java.util.List;

public final class OpsecStore$0 {
   private String sourceUsername = "";
   private List<String> Afq1 = Collections.emptyList();
   private List<String> randomizedNames = Collections.emptyList();
   private long verifiedAt;
   private boolean dirty;
   private List<String> recoveryLayout = Collections.emptyList();

   public String RDYtfh() {
      return this.sourceUsername;
   }

   public List<String> gsyNw() {
      return this.Afq1;
   }

   public List<String> wksF() {
      return this.randomizedNames;
   }

   public long getVerifiedAt() {
      return this.verifiedAt;
   }

   public boolean isDirty() {
      return this.dirty;
   }

   public List<String> getRecoveryLayout() {
      return this.recoveryLayout;
   }

   private OpsecStore$0 copy() {
      OpsecStore$0 var1 = new OpsecStore$0();
      var1.sourceUsername = this.sourceUsername;
      var1.Afq1 = OpsecStore.immutableCopy(this.Afq1);
      var1.randomizedNames = OpsecStore.immutableCopy(this.randomizedNames);
      var1.verifiedAt = this.verifiedAt;
      var1.dirty = this.dirty;
      var1.recoveryLayout = OpsecStore.immutableCopy(this.recoveryLayout);
      return var1;
   }

   public static OpsecStore$0 sijyN(OpsecStore$0 var0) {
      return var0.copy();
   }

   public static boolean setDirty(OpsecStore$0 var0, boolean var1) {
      return var0.dirty = var1;
   }

   public static List setRecoveryLayout(OpsecStore$0 var0, List var1) {
      return var0.recoveryLayout = var1;
   }

   public static String setSourceUsername(OpsecStore$0 var0, String var1) {
      return var0.sourceUsername = var1;
   }

   public static List fZvbNv(OpsecStore$0 var0, List var1) {
      return var0.Afq1 = var1;
   }

   public static List ZECG(OpsecStore$0 var0, List var1) {
      return var0.randomizedNames = var1;
   }

   public static long setVerifiedAt(OpsecStore$0 var0, long var1) {
      return var0.verifiedAt = var1;
   }

   public static String readSourceUsername(OpsecStore$0 var0) {
      return var0.sourceUsername;
   }

   public static List readDesiredNames(OpsecStore$0 var0) {
      return var0.Afq1;
   }

   public static List readRandomizedNames(OpsecStore$0 var0) {
      return var0.randomizedNames;
   }

   public static long HAmSrgD(OpsecStore$0 var0) {
      return var0.verifiedAt;
   }

   public static boolean ELSzqSb(OpsecStore$0 var0) {
      return var0.dirty;
   }

   public static List readRecoveryLayout(OpsecStore$0 var0) {
      return var0.recoveryLayout;
   }
}
