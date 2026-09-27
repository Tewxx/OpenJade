// Jade recovery: original class: jade.deps.eLz.zJtasScxVk
package jade.client.common;

import jade.client.Jade;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.render.arraylist.ModuleSorter;
import jade.client.module.shared.ModuleToggleListener;

import jade.deps.loader107.CoreResourceIndex;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Map;

public final class ModuleManager {
   private static final String NqRyg = "META-INF/jade-modules";
   private final List<Module> allModules = new ArrayList<>();
   private final List<Module> sortedModules = new ArrayList<>();
   private final Map<Category, List<Module>> IWXX = new EnumMap<>(Category.class);
   private final Map<Class<? extends Module>, Module> BIz = new LinkedHashMap<>();
   private final Map<String, Module> modulesByName = new HashMap<>();
   private final Map<String, Module> modulesByNormalizedName = new HashMap<>();
   private final Object SDez = new Object();
   private final List<Class<? extends Module>> kQrgeK;
   private volatile List<Module> sortedEnabledModules = Collections.emptyList();
   private boolean modulesRegistered;

   public ModuleManager() {
      this.kQrgeK = null;
      Category[] var1 = Category.values();
      int var2 = var1.length;

      for (int var3 = 0; var3 < var2; var3++) {
         Category var4 = var1[var3];
         this.IWXX.put(var4, new ArrayList<>());
      }
   }

   public ModuleManager(List<Class<? extends Module>> var1) {
      this.kQrgeK = new ArrayList<>(var1);
      Category[] var2 = Category.values();
      int var3 = var2.length;

      for (int var4 = 0; var4 < var3; var4++) {
         Category var5 = var2[var4];
         this.IWXX.put(var5, new ArrayList<>());
      }
   }

   public synchronized void registerModules() {
      if (this.modulesRegistered) {
         throw new IllegalStateException("Modules have already been registered");
      } else {
         for (Class var3 : this.kQrgeK == null ? this.readModuleIndex() : this.kQrgeK) {
            this.registerModule(var3);
         }

         Collections.sort(this.sortedModules, Comparator.comparing(Module::getName));

         for (List var6 : this.IWXX.values()) {
            Collections.sort(var6, Comparator.comparing(Module::getName));
         }

         this.modulesRegistered = true;

         for (Module var7 : new ArrayList<>(this.allModules)) {
            if (var7.isEnabledByDefault()) {
               var7.enable();
            }
         }
      }
   }

   private List<Class<? extends Module>> readModuleIndex() {
      InputStream var1 = CoreResourceIndex.openResource("META-INF/jade-modules");
      if (var1 == null) {
         throw new IllegalStateException("Missing generated module index: META-INF/jade-modules");
      } else {
         ArrayList var2 = new ArrayList();

         try {
            BufferedReader var3 = new BufferedReader(new InputStreamReader(var1, StandardCharsets.UTF_8));

            String var4;
            while ((var4 = var3.readLine()) != null) {
               String var5 = var4.trim();
               if (!var5.isEmpty() && !var5.startsWith("#")) {
                  Class var6 = Class.forName(var5, false, ModuleManager.class.getClassLoader());
                  if (!Module.class.isAssignableFrom(var6)) {
                     throw new IllegalStateException("Indexed class is not a module: " + var5);
                  }

                  var2.add(var6);
               }
            }
         } catch (IOException var15) {
            throw new IllegalStateException("Could not read generated module index", var15);
         } catch (ClassNotFoundException var16) {
            throw new IllegalStateException("Could not load indexed module", var16);
         } finally {
            try {
               var1.close();
            } catch (IOException var14) {
            }
         }

         if (var2.isEmpty()) {
            throw new IllegalStateException("Generated module index is empty");
         } else {
            return var2;
         }
      }
   }

   private void registerModule(Class<? extends Module> var1) {
      if (var1 == null || Modifier.isAbstract(var1.getModifiers()) || var1.isInterface()) {
         throw new IllegalStateException("Invalid module index entry: " + var1);
      } else if (this.BIz.containsKey(var1)) {
         throw new IllegalStateException("Duplicate module class: " + var1.getName());
      } else {
         ModuleInfo var2 = var1.getAnnotation(ModuleInfo.class);
         if (var2 == null) {
            throw new IllegalStateException("Indexed module is missing @ModuleEntry: " + var1.getName());
         } else {
            Module var3;
            try {
               Constructor var4 = var1.getDeclaredConstructor();
               if (!Modifier.isPublic(var4.getModifiers()) || !Modifier.isPublic(var1.getModifiers())) {
                  throw new IllegalStateException("Module entry must have a public no-arg constructor: " + var1.getName());
               }

               var3 = (Module)var4.newInstance();
            } catch (ReflectiveOperationException var8) {
               throw new IllegalStateException("Could not construct module: " + var1.getName(), var8);
            }

            var3.attachManager(this);
            this.allModules.add(var3);
            this.BIz.put(var1, var3);
            this.registerNameOrAlias(var3.getName(), var3);

            for (String var7 : var2.aliases()) {
               this.registerNameOrAlias(var7, var3);
            }

            if (var2.listed()) {
               this.sortedModules.add(var3);
               this.IWXX.get(var3.getCategory()).add(var3);
            }
         }
      }
   }

   private void registerNameOrAlias(String var1, Module var2) {
      if (var1 != null && !var1.trim().isEmpty()) {
         Module var3 = this.modulesByName.put(var1, var2);
         if (var3 != null && var3 != var2) {
            throw this.createDuplicateNameException(var1, var3, var2);
         } else {
            String var4 = normalizeName(var1);
            Module var5 = this.modulesByNormalizedName.put(var4, var2);
            if (var5 != null && var5 != var2) {
               throw this.createDuplicateNameException(var1, var5, var2);
            }
         }
      } else {
         throw new IllegalStateException("Module names and aliases must not be blank: " + var2.getClass().getName());
      }
   }

   private IllegalStateException createDuplicateNameException(String var1, Module var2, Module var3) {
      return new IllegalStateException("Duplicate module name or alias '" + var1 + "': " + var2.getClass().getName() + " and " + var3.getClass().getName());
   }

   public List<Module> getModules() {
      return Collections.unmodifiableList(this.sortedModules);
   }

   public List<Module> getModulesInCategory(Category var1) {
      List var2 = this.IWXX.get(var1);
      return var2 == null ? Collections.emptyList() : Collections.unmodifiableList(var2);
   }

   public List<Module> getSortedModules() {
      return this.sortedEnabledModules;
   }

   public <T extends Module> T getModule(Class<T> var1) {
      Module var2 = this.BIz.get(var1);
      return (T)(var2 == null ? null : var1.cast(var2));
   }

   public Module getModuleByName(String var1) {
      if (var1 == null) {
         return null;
      } else {
         Module var2 = this.modulesByName.get(var1);
         return var2 != null ? var2 : this.modulesByNormalizedName.get(normalizeName(var1));
      }
   }

   public boolean matchesName(Module var1, String var2) {
      String var3 = normalizeName(var2);
      if (var1 != null && !var3.isEmpty()) {
         for (Entry var5 : this.modulesByNormalizedName.entrySet()) {
            if (var5.getValue() == var1 && ((String)var5.getKey()).contains(var3)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public void disableAll() {
      for (Module var2 : new ArrayList<>(this.allModules)) {
         try {
            if (var2.isEnabled()) {
               var2.disable();
            }
         } catch (Throwable var4) {
         }
      }
   }

   public void onModuleEnabled(Module var1) {
      synchronized (this.SDez) {
         ArrayList var3 = new ArrayList<>(this.sortedEnabledModules);
         if (!var3.contains(var1)) {
            var3.add(var1);
         }

         this.sortModules(var3);
         this.sortedEnabledModules = Collections.unmodifiableList(var3);
      }
   }

   public void onModuleDisabled(Module var1) {
      boolean var2;
      synchronized (this.SDez) {
         ArrayList var4 = new ArrayList<>(this.sortedEnabledModules);
         var4.remove(var1);
         this.sortedEnabledModules = Collections.unmodifiableList(var4);
         var2 = var4.isEmpty();
      }

      if (var2 && Jade.nbT != null) {
         Jade.nbT.FfRco();
      }
   }

   public void onModuleToggled(Module var1, boolean var2) {
      for (Module var4 : new ArrayList<>(this.allModules)) {
         if (var4 instanceof ModuleToggleListener) {
            ((ModuleToggleListener)var4).onModuleToggled(var1, var2);
         }
      }
   }

   public void resortModules() {
      synchronized (this.SDez) {
         ArrayList var2 = new ArrayList<>(this.sortedEnabledModules);
         this.sortModules(var2);
         this.sortedEnabledModules = Collections.unmodifiableList(var2);
      }
   }

   private void sortModules(List<Module> var1) {
      Comparator var2 = null;

      for (Module var4 : this.allModules) {
         if (var4 instanceof ModuleSorter) {
            var2 = ((ModuleSorter)var4).createModuleComparator();
            break;
         }
      }

      if (var2 == null) {
         var2 = Comparator.comparing(Module::getName);
      }

      Collections.sort(var1, var2);
   }

   private static String normalizeName(String var0) {
      if (var0 == null) {
         return "";
      } else {
         String var1 = var0.toLowerCase(Locale.ROOT);
         StringBuilder var2 = new StringBuilder(var1.length());

         for (int var3 = 0; var3 < var1.length(); var3++) {
            char var4 = var1.charAt(var3);
            if (Character.isLetterOrDigit(var4)) {
               var2.append(var4);
            }
         }

         return var2.toString();
      }
   }
}
