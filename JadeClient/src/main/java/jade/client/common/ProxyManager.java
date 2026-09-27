// Jade recovery: original class: jade.deps.eLz.AX12y0n5
package jade.client.common;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import jade.client.gui.ProxySettings$0;
import jade.client.gui.ProxySettings;
import jade.deps.loader107.InjectionPaths;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import net.minecraft.client.Minecraft;

public final class ProxyManager {
   private static final String PROPERTIES_FILE_NAME = "JadeProxy.properties";
   private static ProxySettings configuredSettings = new ProxySettings();
   private static ProxySettings aow = new ProxySettings();
   private static boolean loaded;
   private static String Scgdu = "none";

   private ProxyManager() {
   }

   public static synchronized ProxySettings rfB4() {
      ensureLoaded();
      return configuredSettings.copy();
   }

   public static synchronized void KOvZb(ProxySettings var0, boolean var1) {
      ensureLoaded();
      configuredSettings = var0 == null ? new ProxySettings() : var0.copy();
      ProxySettings var2 = kak4();
      Scgdu = var2.XAiikGo() ? var2.getHost() : "none";
      if (var1) {
         saveToDisk();
      }
   }

   public static synchronized void setFallbackSettings(ProxySettings var0) {
      ensureLoaded();
      aow = var0 == null ? new ProxySettings() : var0.copy();
      ProxySettings var1 = kak4();
      Scgdu = var1.XAiikGo() ? var1.getHost() : "none";
   }

   public static synchronized ProxySettings getActiveSettings() {
      ensureLoaded();
      return kak4().copy();
   }

   public static synchronized String getStatusLabel() {
      ensureLoaded();
      ProxySettings var0 = kak4();
      return var0.XAiikGo() ? "Proxy:" + var0.getHost() : "Proxy";
   }

   public static synchronized String getActiveProxyLabel() {
      ensureLoaded();
      return Scgdu;
   }

   public static void installProxyHandler(Channel var0) {
      ProxySettings var1 = getActiveSettings();
      if (var0 != null && var1.XAiikGo()) {
         synchronized (ProxyManager.class) {
            Scgdu = var1.getHost();
         }

         if (var0.pipeline().get("jade_proxy") == null) {
            var0.pipeline().addFirst("jade_proxy", new ProxyHandler(var1));
         }
      } else {
         synchronized (ProxyManager.class) {
            Scgdu = "none";
         }
      }
   }

   public static Object wrapChannelHandler(Object var0) {
      return var0 instanceof ChannelHandler && !(var0 instanceof ProxyChannelInitializer) && getActiveSettings().XAiikGo() ? new ProxyChannelInitializer((ChannelHandler)var0) : var0;
   }

   private static synchronized void ensureLoaded() {
      if (!loaded) {
         loaded = true;
         File var0 = getSettingsFile();
         if (!var0.isFile()) {
            configuredSettings = new ProxySettings();
            aow = new ProxySettings();
            Scgdu = "none";
         } else {
            Properties var1 = new Properties();
            FileInputStream var2 = null;

            try {
               var2 = new FileInputStream(var0);
               var1.load(var2);
               ProxySettings$0 var3 = ProxySettings$0.SOCKS5;
               String var4 = var1.getProperty("host", "");
               int var5 = lGgmU(var1.getProperty("port", "0"));
               String var6 = var1.getProperty("userId", "");
               String var7 = var1.getProperty("username", "");
               String var8 = var1.getProperty("password", "");
               boolean var9 = Boolean.parseBoolean(var1.getProperty("enabled", "false"));
               configuredSettings = new ProxySettings(var9, var3, var4, var5, var6, var7, var8);
               aow = new ProxySettings();
               ProxySettings var10 = kak4();
               Scgdu = var10.XAiikGo() ? var10.getHost() : "none";
            } catch (Exception var19) {
               configuredSettings = new ProxySettings();
               aow = new ProxySettings();
               Scgdu = "none";
            } finally {
               if (var2 != null) {
                  try {
                     var2.close();
                  } catch (Exception var18) {
                  }
               }
            }
         }
      }
   }

   private static synchronized void saveToDisk() {
      File var0 = getSettingsFile();
      File var1 = var0.getParentFile();
      if (var1 != null && !var1.isDirectory()) {
         var1.mkdirs();
      }

      Properties var2 = new Properties();
      var2.setProperty("enabled", Boolean.toString(configuredSettings.XAiikGo()));
      var2.setProperty("type", configuredSettings.getProxyType().name());
      var2.setProperty("host", configuredSettings.getHost());
      var2.setProperty("port", Integer.toString(configuredSettings.getPort()));
      var2.setProperty("userId", configuredSettings.getUserId());
      var2.setProperty("username", configuredSettings.getUsername());
      var2.setProperty("password", configuredSettings.getPassword());
      FileOutputStream var3 = null;

      try {
         var3 = new FileOutputStream(var0);
         var2.store(var3, "Jade proxy settings");
      } catch (Exception var13) {
      } finally {
         if (var3 != null) {
            try {
               var3.close();
            } catch (Exception var12) {
            }
         }
      }
   }

   private static File getSettingsFile() {
      Minecraft var0 = Minecraft.getMinecraft();
      File var1 = var0 != null && var0.mcDataDir != null ? var0.mcDataDir : new File(".");
      return new File(InjectionPaths.dataDirectory(var1), "JadeProxy.properties");
   }

   private static ProxySettings kak4() {
      return configuredSettings.XAiikGo() ? configuredSettings : aow;
   }

   private static int lGgmU(String var0) {
      try {
         return Integer.parseInt(var0);
      } catch (Exception var2) {
         return 0;
      }
   }
}
