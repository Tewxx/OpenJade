// Jade recovery: original class: jade.deps.eLz.TyoMFkV
package jade.client.common;

import jade.client.Jade;
import jade.client.module.Module;
import jade.client.setting.BooleanSetting;
import jade.client.setting.MultiSelectSetting;
import jade.client.setting.Setting;
import jade.client.setting.SliderSetting;
import jade.deps.gson.JsonArray;
import jade.deps.gson.JsonElement;
import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonParser;
import jade.deps.loader107.InjectionPaths;
import jade.deps.loader107.PendingChatQueue;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;

public final class DangerousModules {
   private static final Object afX = new Object();
   private static volatile List<xoHFmOLKl> hNg = Collections.emptyList();
   private static final Set<Long> acknowledgedIds = new LinkedHashSet<>();
   private static final Set<Long> VIA = new LinkedHashSet<>();
   private static final Map<Module, xoHFmOLKl> pendingModules = new LinkedHashMap<>();
   private static boolean acknowledgementsLoaded;
   private static volatile String lastRegistryJson;

   private DangerousModules() {
   }

   public static void loadFromRegistryJson(JsonObject var0) {
      loadFromJson(var0 == null ? null : var0.get("dangerous_modules"));
   }

   public static void loadFromJson(JsonElement var0) {
      ArrayList var1 = new ArrayList();
      if (var0 != null && var0.isJsonArray()) {
         for (JsonElement var3 : var0.getAsJsonArray()) {
            if (var3 != null && var3.isJsonObject()) {
               JsonObject var4 = var3.getAsJsonObject();

               try {
                  long var5 = var4.get("id").getAsLong();
                  String var7 = normalizeName(var4.get("module").getAsString());
                  String var8 = var4.has("mode") && !var4.get("mode").isJsonNull() ? normalizeName(var4.get("mode").getAsString()) : null;
                  if (var5 > 0L && !var7.isEmpty() && (var8 == null || !var8.isEmpty())) {
                     var1.add(new xoHFmOLKl(var5, var7, var8));
                  }
               } catch (RuntimeException var11) {
               }
            }
         }
      }

      synchronized (afX) {
         loadAcknowledgements();
         hNg = Collections.unmodifiableList(var1);
         pendingModules.entrySet().removeIf((recoveredArg0) -> DangerousModules.isMissingEntry(var1, (java.util.Map.Entry) recoveredArg0));
         VIA.removeIf((recoveredArg0) -> DangerousModules.isIdMissing(var1, (java.lang.Long) recoveredArg0));
      }

      CTWLqFe(var1);
   }

   public static xoHFmOLKl findUnconditionalDanger(Module var0) {
      refreshRegistry();
      if (var0 == null) {
         return null;
      } else {
         String var1 = normalizeName(var0.getName());

         for (xoHFmOLKl var3 : hNg) {
            if (var3.hasNoMode() && var3.getModuleName().equals(var1)) {
               return var3;
            }
         }

         return null;
      }
   }

   public static boolean isListedDangerous(Module var0) {
      refreshRegistry();
      if (var0 == null) {
         return false;
      } else {
         String var1 = normalizeName(var0.getName());

         for (xoHFmOLKl var3 : hNg) {
            if (var3.getModuleName().equals(var1)) {
               return true;
            }
         }

         return false;
      }
   }

   public static xoHFmOLKl findActiveDanger(Module var0) {
      xoHFmOLKl var1 = findUnconditionalDanger(var0);
      if (var1 != null) {
         return var1;
      } else if (var0 == null) {
         return null;
      } else {
         String var2 = normalizeName(var0.getName());

         for (xoHFmOLKl var4 : hNg) {
            if (!var4.hasNoMode() && var4.getModuleName().equals(var2) && isConditionActive(var0, var4.getMode())) {
               return var4;
            }
         }

         return null;
      }
   }

   public static boolean Vupme(Module var0, String var1) {
      refreshRegistry();
      if (var0 != null && var1 != null) {
         String var2 = normalizeName(var0.getName());
         String var3 = normalizeName(var1);

         for (xoHFmOLKl var5 : hNg) {
            if (!var5.hasNoMode() && var5.getModuleName().equals(var2) && var5.getMode().equals(var3)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static void checkLoadedModules() {
      refreshRegistry();
      CTWLqFe(hNg);
   }

   public static void LKncO() {
      synchronized (afX) {
         pendingModules.clear();
      }
   }

   public static boolean markPendingDanger(Module var0) {
      refreshRegistry();
      xoHFmOLKl var1 = findActiveDanger(var0);
      if (var1 == null) {
         return false;
      } else {
         synchronized (afX) {
            loadAcknowledgements();
            if (acknowledgedIds.contains(var1.getId())) {
               return false;
            } else {
               pendingModules.put(var0, var1);
               return true;
            }
         }
      }
   }

   public static boolean ZLqGjyW(Module var0) {
      boolean var1 = markPendingDanger(var0);
      if (var1) {
         warnPendingDanger();
      }

      return var1;
   }

   public static void warnPendingDanger() {
      ArrayList var0;
      synchronized (afX) {
         if (pendingModules.isEmpty()) {
            return;
         }

         var0 = new ArrayList<>(new LinkedHashSet<>(pendingModules.values()));
      }

      StringBuilder var5 = new StringBuilder();

      for (xoHFmOLKl var3 : (java.lang.Iterable<xoHFmOLKl>) (java.lang.Iterable<?>) (var0)) {
         if (var5.length() > 0) {
            var5.append(", ");
         }

         var5.append(var3.getDisplayName());
      }

      ClientUtils.sendJadeMessage(
         "Jade",
         "&6"
            + var5
            + (
               var0.size() == 1
                  ? " &fis now marked as dangerous. It may have been patched or may be unsafe to use."
                  : " &fare now marked as dangerous. They may have been patched or may be unsafe to use."
            )
      );
      ClientUtils.sendJadeMessage("Jade", "&fTo acknowledge the risk and enable " + (var0.size() == 1 ? "it" : "them") + ", run &6.danger confirm&f.");
   }

   public static int nPtc() {
      LinkedHashMap var0;
      synchronized (afX) {
         loadAcknowledgements();
         if (pendingModules.isEmpty()) {
            return 0;
         }

         var0 = new LinkedHashMap<>(pendingModules);

         for (xoHFmOLKl var3 : (java.lang.Iterable<xoHFmOLKl>) (java.lang.Iterable<?>) (var0.values())) {
            acknowledgedIds.add(var3.getId());
         }

         pendingModules.clear();
         saveAcknowledgements();
      }

      int var6 = 0;

      for (Module var8 : (java.lang.Iterable<Module>) (java.lang.Iterable<?>) (var0.keySet())) {
         if (!var8.isEnabled() && var8.canBeEnabled()) {
            var8.enable();
            var6++;
         }
      }

      return var6;
   }

   public static int JrDne() {
      synchronized (afX) {
         return pendingModules.size();
      }
   }

   private static void CTWLqFe(List<xoHFmOLKl> var0) {
      ModuleManager var1 = Jade.getModuleManager();
      if (var1 != null && var0 != null && !var0.isEmpty()) {
         for (xoHFmOLKl var3 : var0) {
            synchronized (afX) {
               loadAcknowledgements();
               if (acknowledgedIds.contains(var3.getId()) || VIA.contains(var3.getId())) {
                  continue;
               }
            }

            Module var9 = var1.getModuleByName(var3.getModuleName());
            if (var9 != null && var9.isEnabled() && (var3.hasNoMode() || isConditionActive(var9, var3.getMode()))) {
               synchronized (afX) {
                  if (!VIA.add(var3.getId())) {
                     continue;
                  }
               }

               PendingChatQueue.enqueueMessage(
                  "&6Warning: &f" + var3.getDisplayName() + " &fis marked as dangerous and remains enabled. Continued use may be unsafe."
               );
            }
         }
      }
   }

   private static boolean isConditionActive(Module var0, String var1) {
      for (Setting var3 : var0.getSettings()) {
         if (var3 instanceof SliderSetting) {
            SliderSetting var4 = (SliderSetting)var3;
            String[] var5 = var4.getOptions();
            int var6 = (int)Math.round(var4.getInput());
            if (var5 != null && var6 >= 0 && var6 < var5.length && normalizeName(var5[var6]).equals(var1)) {
               return true;
            }
         } else if (var3 instanceof BooleanSetting) {
            BooleanSetting var9 = (BooleanSetting)var3;
            if (!var9.isButton && var9.isToggled() && normalizeName(var9.getName()).equals(var1)) {
               return true;
            }
         } else if (var3 instanceof MultiSelectSetting) {
            MultiSelectSetting var10 = (MultiSelectSetting)var3;

            for (BooleanSetting var8 : var10.awwHd()) {
               if (var8 != null && var8.isToggled() && (normalizeName(var8.getName()).equals(var1) || normalizeName(var10.getLabelFor(var8)).equals(var1))) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   private static boolean containsId(List<xoHFmOLKl> var0, long var1) {
      for (xoHFmOLKl var4 : var0) {
         if (var4.getId() == var1) {
            return true;
         }
      }

      return false;
   }

   private static void refreshRegistry() {
      String var0 = System.getProperty("jade.deps.k.registry", "[]");
      if (!var0.equals(lastRegistryJson)) {
         try {
            loadFromJson(new JsonParser().parse(var0));
            lastRegistryJson = var0;
         } catch (RuntimeException var2) {
         }
      }
   }

   private static String normalizeName(String var0) {
      return var0 == null ? "" : var0.trim().toLowerCase(Locale.ROOT);
   }

   private static File getAcknowledgementsFile() {
      File var0 = InjectionPaths.dataDirectory(Minecraft.getMinecraft().mcDataDir);
      if (!var0.exists()) {
         var0.mkdirs();
      }

      return new File(var0, "danger-acknowledgements.json");
   }

   private static void loadAcknowledgements() {
      if (!acknowledgementsLoaded) {
         acknowledgementsLoaded = true;
         File var0 = getAcknowledgementsFile();
         if (var0.isFile()) {
            try (FileReader var1 = new FileReader(var0)) {
               JsonElement var3 = new JsonParser().parse(var1);
               if (var3.isJsonArray()) {
                  for (JsonElement var5 : var3.getAsJsonArray()) {
                     if (var5 != null && var5.isJsonPrimitive()) {
                        acknowledgedIds.add(var5.getAsLong());
                     }
                  }

                  return;
               } else {
                  return;
               }
            } catch (Exception var17) {
            }
         }
      }
   }

   private static void saveAcknowledgements() {
      File var0 = getAcknowledgementsFile();
      File var1 = new File(var0.getParentFile(), var0.getName() + ".tmp");
      JsonArray var2 = new JsonArray();

      for (Long var4 : acknowledgedIds) {
         var2.add(var4);
      }

      try (FileWriter var19 = new FileWriter(var1)) {
         var19.write(var2.toString());
      } catch (IOException var18) {
         var1.delete();
         return;
      }

      try {
         Files.move(var1.toPath(), var0.toPath(), StandardCopyOption.REPLACE_EXISTING);
      } catch (IOException var15) {
         var1.delete();
      }
   }

   private static boolean isIdMissing(ArrayList var0, Long var1) {
      return !containsId(var0, var1);
   }

   private static boolean isMissingEntry(ArrayList var0, Entry var1) {
      return !containsId(var0, ((xoHFmOLKl)var1.getValue()).getId());
   }
}
