// Jade recovery: original class: jade.deps.eLz.LWOXjn1$7
package jade.client.gui;

import jade.client.common.Account;
import jade.client.common.ConfigFolder;

public final class ProxyAltsScreen$7 {
   private final Account account;
   private final ConfigFolder configFolder;

   ProxyAltsScreen$7(Account var1, ConfigFolder var2) {
      this.account = var1;
      this.configFolder = var2;
   }

   private static ProxyAltsScreen$7 ZXeX(Account var0) {
      return new ProxyAltsScreen$7(var0, null);
   }

   private static ProxyAltsScreen$7 EfpdG(ConfigFolder var0) {
      return new ProxyAltsScreen$7(null, var0);
   }

   public static ConfigFolder getConfigFolder(ProxyAltsScreen$7 var0) {
      return var0.configFolder;
   }

   public static Account getAccount(ProxyAltsScreen$7 var0) {
      return var0.account;
   }

   public static ProxyAltsScreen$7 ofAccount(Account var0) {
      return ZXeX(var0);
   }

   public static ProxyAltsScreen$7 ofFolder(ConfigFolder var0) {
      return EfpdG(var0);
   }
}
