// Jade recovery: original class: jade.deps.eLz.jWwxLaB8
package jade.client.common;

import jade.deps.gson.Gson;
import jade.deps.gson.GsonBuilder;
import jade.deps.gson.JsonArray;
import jade.deps.gson.JsonElement;
import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonParser;
import jade.deps.loader107.InjectionPaths;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.Minecraft;

public final class OpsecStore {
   private static final int OtX = 1;
   private final Map<String, OpsecStore$0> MWt = new LinkedHashMap<>();
   private final File file;

   public OpsecStore() {
      this(
         new File(
            InjectionPaths.dataDirectory(Minecraft.getMinecraft().mcDataDir),
            "opsec.json"
         )
      );
   }

   public OpsecStore(File var1) {
      this.file = var1;
      this.loadFromDisk();
   }

   public synchronized OpsecStore$0 getAccount(UUID var1) {
      OpsecStore$0 var2 = this.MWt.get(hdGtt(var1));
      return var2 == null ? null : OpsecStore$0.sijyN(var2);
   }

   public synchronized boolean tBxg(UUID var1, List<String> var2) {
      if (var1 == null) {
         return false;
      } else {
         String var3 = hdGtt(var1);
         OpsecStore$0 var4 = this.MWt.get(var3);
         OpsecStore$0 var5 = var4 == null ? null : OpsecStore$0.sijyN(var4);
         if (var5 == null) {
            var5 = new OpsecStore$0();
         }

         OpsecStore$0.setDirty(var5, true);
         OpsecStore$0.setRecoveryLayout(var5, immutableListCopy(var2));
         this.MWt.put(var3, var5);
         return this.persistOrRestorePrevious(var3, var4);
      }
   }

   public synchronized boolean jeDf6(UUID var1) {
      String var2 = hdGtt(var1);
      OpsecStore$0 var3 = this.MWt.get(var2);
      if (var3 == null) {
         return false;
      } else {
         OpsecStore$0 var4 = OpsecStore$0.sijyN(var3);
         OpsecStore$0.setDirty(var4, false);
         OpsecStore$0.setRecoveryLayout(var4, Collections.emptyList());
         this.MWt.put(var2, var4);
         return this.persistOrRestorePrevious(var2, var3);
      }
   }

   public synchronized boolean markAccountVerified(UUID var1, String var2, List<String> var3, List<String> var4) {
      if (var1 == null) {
         return false;
      } else {
         String var5 = hdGtt(var1);
         OpsecStore$0 var6 = this.MWt.get(var5);
         OpsecStore$0 var7 = var6 == null ? null : OpsecStore$0.sijyN(var6);
         if (var7 == null) {
            var7 = new OpsecStore$0();
         }

         OpsecStore$0.setSourceUsername(var7, var2 == null ? "" : var2.trim());
         OpsecStore$0.fZvbNv(var7, immutableListCopy(var3));
         OpsecStore$0.ZECG(var7, immutableListCopy(var4));
         OpsecStore$0.setVerifiedAt(var7, System.currentTimeMillis());
         OpsecStore$0.setDirty(var7, false);
         OpsecStore$0.setRecoveryLayout(var7, Collections.emptyList());
         this.MWt.put(var5, var7);
         return this.persistOrRestorePrevious(var5, var6);
      }
   }

   private void loadFromDisk() {
      if (this.file.isFile()) {
         try (FileReader var1 = new FileReader(this.file)) {
            JsonObject var3 = new JsonParser().parse(var1).getAsJsonObject();
            if (var3.has("schemaVersion") && var3.get("schemaVersion").getAsInt() == 1 && var3.has("accounts") && var3.get("accounts").isJsonObject()) {
               for (Entry var5 : var3.getAsJsonObject("accounts").entrySet()) {
                  if (((JsonElement)var5.getValue()).isJsonObject()) {
                     JsonObject var6 = ((JsonElement)var5.getValue()).getAsJsonObject();
                     OpsecStore$0 var7 = new OpsecStore$0();
                     OpsecStore$0.setSourceUsername(var7, readStringOrEmpty(var6, "sourceUsername"));
                     OpsecStore$0.fZvbNv(var7, readStringListFromJson(var6, "desired"));
                     OpsecStore$0.ZECG(var7, readStringListFromJson(var6, "randomized"));
                     OpsecStore$0.setRecoveryLayout(var7, readStringListFromJson(var6, "recoveryLayout"));
                     OpsecStore$0.setDirty(var7, hD75(var6, "dirty"));
                     OpsecStore$0.setVerifiedAt(var7, readLongOrZero(var6, "verifiedAt"));
                     this.MWt.put((String)var5.getKey(), var7);
                  }
               }

               return;
            } else {
               return;
            }
         } catch (Exception var19) {
         }
      }
   }

   private boolean persistOrRestorePrevious(String var1, OpsecStore$0 var2) {
      if (this.saveToDisk()) {
         return true;
      } else {
         if (var2 == null) {
            this.MWt.remove(var1);
         } else {
            this.MWt.put(var1, var2);
         }

         return false;
      }
   }

   private boolean saveToDisk() {
      File var1 = this.file.getParentFile();
      if (var1 != null && !var1.exists() && !var1.mkdirs()) {
         return false;
      } else {
         JsonObject var2 = new JsonObject();
         var2.addProperty("schemaVersion", 1);
         JsonObject var3 = new JsonObject();

         for (Entry var5 : this.MWt.entrySet()) {
            OpsecStore$0 var6 = (OpsecStore$0)var5.getValue();
            JsonObject var7 = new JsonObject();
            var7.addProperty("sourceUsername", OpsecStore$0.readSourceUsername(var6));
            var7.add("desired", toJsonArray(OpsecStore$0.readDesiredNames(var6)));
            var7.add("randomized", toJsonArray(OpsecStore$0.readRandomizedNames(var6)));
            var7.addProperty("verifiedAt", OpsecStore$0.HAmSrgD(var6));
            var7.addProperty("dirty", OpsecStore$0.ELSzqSb(var6));
            var7.add("recoveryLayout", toJsonArray(OpsecStore$0.readRecoveryLayout(var6)));
            var3.add((String)var5.getKey(), var7);
         }

         var2.add("accounts", var3);
         File var23 = new File(this.file.getParentFile(), this.file.getName() + ".tmp");

         try (FileWriter var24 = new FileWriter(var23)) {
            Gson var26 = new GsonBuilder().setPrettyPrinting().create();
            var26.toJson((JsonElement)var2, var24);
         } catch (Exception var22) {
            return false;
         }

         try {
            Files.move(var23.toPath(), this.file.toPath(), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            return true;
         } catch (Exception var19) {
            try {
               Files.move(var23.toPath(), this.file.toPath(), StandardCopyOption.REPLACE_EXISTING);
               return true;
            } catch (Exception var17) {
               return false;
            }
         }
      }
   }

   private static JsonArray toJsonArray(List<String> var0) {
      JsonArray var1 = new JsonArray();
      if (var0 != null) {
         for (String var3 : var0) {
            var1.add(var3);
         }
      }

      return var1;
   }

   private static List<String> readStringListFromJson(JsonObject var0, String var1) {
      ArrayList var2 = new ArrayList();
      if (var0.has(var1) && var0.get(var1).isJsonArray()) {
         for (JsonElement var4 : var0.getAsJsonArray(var1)) {
            if (var4.isJsonPrimitive()) {
               var2.add(QuickBuyLayout.normalizeItemName(var4.getAsString()));
            }
         }
      }

      return immutableListCopy(var2);
   }

   private static String readStringOrEmpty(JsonObject var0, String var1) {
      try {
         return var0.has(var1) ? var0.get(var1).getAsString() : "";
      } catch (Exception var3) {
         return "";
      }
   }

   private static boolean hD75(JsonObject var0, String var1) {
      try {
         return var0.has(var1) && var0.get(var1).getAsBoolean();
      } catch (Exception var3) {
         return false;
      }
   }

   private static long readLongOrZero(JsonObject var0, String var1) {
      try {
         return var0.has(var1) ? var0.get(var1).getAsLong() : 0L;
      } catch (Exception var3) {
         return 0L;
      }
   }

   private static String hdGtt(UUID var0) {
      return var0 == null ? "" : var0.toString().replace("-", "").toLowerCase();
   }

   private static List<String> immutableListCopy(List<String> var0) {
      return var0 == null ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList<>(var0));
   }

   public static List immutableCopy(List var0) {
      return immutableListCopy(var0);
   }
}
