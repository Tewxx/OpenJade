// Jade recovery: original class: jade.deps.eLz.lMIe3zTKN
package jade.client.common;

import jade.deps.gson.GsonBuilder;
import jade.deps.gson.JsonArray;
import jade.deps.gson.JsonElement;
import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonParser;
import jade.deps.loader107.InjectionPaths;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;

public class AccountStore {
   private static final List<Account> accounts = new ArrayList<>();
   private static final List<ConfigFolder> folders = new ArrayList<>();
   private static File file;

   private static File getAccountsFile() {
      if (file == null) {
         File var0 = InjectionPaths.dataDirectory(Minecraft.getMinecraft().mcDataDir);
         var0.mkdirs();
         file = new File(var0, "accounts.json");
      }

      return file;
   }

   public static List<Account> getAccounts() {
      return accounts;
   }

   public static List<ConfigFolder> getFolders() {
      return folders;
   }

   public static void wqNcm(Account var0) {
      for (int var1 = accounts.size() - 1; var1 >= 0; var1--) {
         if (accounts.get(var1).zYgb().equalsIgnoreCase(var0.zYgb())) {
            if (var0.getFolderId().length() == 0) {
               var0.setFolderId(accounts.get(var1).getFolderId());
            }

            if (var0.getProxy().length() == 0) {
               var0.setProxy(accounts.get(var1).getProxy());
            }

            accounts.remove(var1);
         }
      }

      accounts.add(var0);
      saveToDisk();
   }

   public static void kjgiw(ConfigFolder var0, int var1) {
      if (var0 != null) {
         int var2 = folders.indexOf(var0);
         folders.remove(var0);
         if (var2 >= 0 && var2 < var1) {
            var1--;
         }

         if (var1 < 0) {
            var1 = 0;
         }

         if (var1 > folders.size()) {
            var1 = folders.size();
         }

         folders.add(var1, var0);
         saveToDisk();
      }
   }

   public static void removeAccount(Account var0) {
      accounts.remove(var0);
      saveToDisk();
   }

   public static void addFolder(ConfigFolder var0) {
      if (var0 != null && var0.getId().length() != 0) {
         folders.add(var0);
         saveToDisk();
      }
   }

   public static void removeFolder(ConfigFolder var0) {
      if (var0 != null) {
         folders.remove(var0);

         for (Account var2 : accounts) {
            if (var0.getId().equals(var2.getFolderId())) {
               var2.setFolderId("");
            }
         }

         saveToDisk();
      }
   }

   public static ConfigFolder eOnjkC(String var0) {
      if (var0 != null && var0.length() != 0) {
         for (ConfigFolder var2 : folders) {
            if (var0.equals(var2.getId())) {
               return var2;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public static void NEuj8(Account var0, int var1, String var2) {
      if (var0 != null) {
         int var3 = accounts.indexOf(var0);
         accounts.remove(var0);
         if (var3 >= 0 && var3 < var1) {
            var1--;
         }

         if (var1 < 0) {
            var1 = 0;
         }

         if (var1 > accounts.size()) {
            var1 = accounts.size();
         }

         var0.setFolderId(var2);
         accounts.add(var1, var0);
         saveToDisk();
      }
   }

   public static void loadFromDisk() {
      File var0 = getAccountsFile();
      if (var0.exists()) {
         try {
            BufferedReader var1 = new BufferedReader(new FileReader(var0));
            StringBuilder var2 = new StringBuilder();

            String var3;
            while ((var3 = var1.readLine()) != null) {
               var2.append(var3);
            }

            var1.close();
            JsonElement var4 = new JsonParser().parse(var2.toString());
            accounts.clear();
            folders.clear();
            JsonArray var5;
            if (var4.isJsonObject()) {
               JsonObject var6 = var4.getAsJsonObject();
               var5 = var6.has("accounts") ? var6.get("accounts").getAsJsonArray() : new JsonArray();
               if (var6.has("folders") && var6.get("folders").isJsonArray()) {
                  for (JsonElement var8 : var6.get("folders").getAsJsonArray()) {
                     JsonObject var9 = var8.getAsJsonObject();
                     String var10 = var9.has("id") ? var9.get("id").getAsString() : "";
                     String var11 = var9.has("title") ? var9.get("title").getAsString() : "New Folder";
                     if (var10.length() > 0) {
                        ConfigFolder var12 = new ConfigFolder(var10, var11);
                        if (var9.has("collapsed")) {
                           var12.setCollapsed(var9.get("collapsed").getAsBoolean());
                        }

                        folders.add(var12);
                     }
                  }
               }
            } else {
               var5 = var4.getAsJsonArray();
            }

            for (JsonElement var21 : var5) {
               JsonObject var22 = var21.getAsJsonObject();
               String var23 = var22.get("username").getAsString();
               String var24 = var22.get("uuid").getAsString();
               String var25 = var22.has("accessToken") ? var22.get("accessToken").getAsString() : "0";
               String var26 = var22.has("refreshToken") && !var22.get("refreshToken").isJsonNull() ? var22.get("refreshToken").getAsString() : null;
               AccountType var13 = AccountType.CRACKED;

               try {
                  if (var22.has("type")) {
                     var13 = AccountType.valueOf(var22.get("type").getAsString());
                  }
               } catch (IllegalArgumentException var18) {
               }

               Account var14 = new Account(var23, var24, var25, var26, var13);
               boolean var15 = false;
               if (var22.has("source") && !var22.get("source").isJsonNull()) {
                  try {
                     var14.setLoginMethod(LoginMethod.valueOf(var22.get("source").getAsString()));
                     var15 = true;
                  } catch (IllegalArgumentException var17) {
                  }
               }

               if (!var15 && var13 == AccountType.MICROSOFT && var26 == null) {
                  var14.setLoginMethod(LoginMethod.TOKEN);
               }

               if (var22.has("folderId") && !var22.get("folderId").isJsonNull()) {
                  var14.setFolderId(var22.get("folderId").getAsString());
               }

               if (var22.has("proxy") && !var22.get("proxy").isJsonNull()) {
                  var14.setProxy(var22.get("proxy").getAsString());
               }

               if (var22.has("unbanTime") && !var22.get("unbanTime").isJsonNull()) {
                  var14.setTimestamp(var22.get("unbanTime").getAsLong());
               }

               accounts.add(var14);
            }
         } catch (Exception var19) {
            var19.printStackTrace();
         }
      }
   }

   public static void saveToDisk() {
      try {
         JsonObject var0 = new JsonObject();
         JsonArray var1 = new JsonArray();

         for (ConfigFolder var3 : folders) {
            JsonObject var4 = new JsonObject();
            var4.addProperty("id", var3.getId());
            var4.addProperty("title", var3.getTitle());
            var4.addProperty("collapsed", var3.isCollapsed());
            var1.add(var4);
         }

         JsonArray var7 = new JsonArray();

         for (Account var10 : accounts) {
            JsonObject var5 = new JsonObject();
            var5.addProperty("username", var10.zYgb());
            var5.addProperty("uuid", var10.getUuid());
            var5.addProperty("accessToken", var10.qRa8673());
            if (var10.getRefreshToken() != null) {
               var5.addProperty("refreshToken", var10.getRefreshToken());
            }

            var5.addProperty("type", var10.FZa4().name());
            var5.addProperty("source", var10.getLoginMethod().name());
            if (var10.getFolderId().length() > 0) {
               var5.addProperty("folderId", var10.getFolderId());
            }

            if (var10.getProxy().length() > 0) {
               var5.addProperty("proxy", var10.getProxy());
            }

            if (var10.Kk50() != 0L) {
               var5.addProperty("unbanTime", var10.Kk50());
            }

            var7.add(var5);
         }

         var0.add("folders", var1);
         var0.add("accounts", var7);
         PrintWriter var9 = new PrintWriter(new FileWriter(getAccountsFile()));
         var9.print(new GsonBuilder().setPrettyPrinting().create().toJson((JsonElement)var0));
         var9.close();
      } catch (Exception var6) {
         var6.printStackTrace();
      }
   }
}
