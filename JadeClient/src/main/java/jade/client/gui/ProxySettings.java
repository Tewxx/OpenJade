// Jade recovery: original class: jade.deps.eLz.B1AyuUsc
package jade.client.gui;

public class ProxySettings {
   private boolean enabled;
   private ProxySettings$0 MQc;
   private String host;
   private int port;
   private String PsI;
   private String username;
   private String password;

   public ProxySettings() {
      this(false, ProxySettings$0.SOCKS5, "", 0, "", "", "");
   }

   public ProxySettings(boolean var1, ProxySettings$0 var2, String var3, int var4, String var5, String var6, String var7) {
      this.enabled = var1;
      this.MQc = ProxySettings$0.SOCKS5;
      this.host = normalize(var3);
      this.port = var4;
      this.PsI = normalize(var5);
      this.username = normalize(var6);
      this.password = normalize(var7);
   }

   public ProxySettings copy() {
      return new ProxySettings(this.enabled, this.MQc, this.host, this.port, this.PsI, this.username, this.password);
   }

   public boolean XAiikGo() {
      return this.enabled && this.host.length() > 0 && this.port > 0;
   }

   public ProxySettings$0 getProxyType() {
      return this.MQc;
   }

   public String getHost() {
      return this.host;
   }

   public int getPort() {
      return this.port;
   }

   public String getUserId() {
      return this.PsI;
   }

   public String getUsername() {
      return this.username;
   }

   public String getPassword() {
      return this.password;
   }

   public String gtItl() {
      return this.XAiikGo() ? this.host + ":" + this.port : "";
   }

   public String getHostOrNone() {
      return this.XAiikGo() ? this.host : "none";
   }

   public String UNCjvL() {
      return !this.XAiikGo() ? "" : this.host + ":" + this.port + ":" + this.username + ":" + this.password;
   }

   public static ProxySettings createDisabledSettings(ProxySettings$0 var0, String var1, String var2, String var3) {
      return new ProxySettings(false, var0, "", 0, var1, var2, var3);
   }

   public static ProxySettings parse(String var0) {
      String var1 = normalize(var0);
      if (var1.length() == 0) {
         return new ProxySettings();
      } else {
         String[] var2 = var1.split(":", 4);
         if (var2.length != 4) {
            return null;
         } else {
            String var3 = normalize(var2[0]);

            int var4;
            try {
               var4 = Integer.parseInt(normalize(var2[1]));
            } catch (NumberFormatException var6) {
               return null;
            }

            return var3.length() != 0 && var4 >= 1 && var4 <= 65535 ? new ProxySettings(true, ProxySettings$0.SOCKS5, var3, var4, "", var2[2], var2[3]) : null;
         }
      }
   }

   private static String normalize(String var0) {
      return var0 == null ? "" : var0.trim();
   }
}
