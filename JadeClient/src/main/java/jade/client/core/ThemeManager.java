// Jade recovery: original class: jade.deps.eLz.PuJkYd3fP
package jade.client.core;

import jade.client.Jade;
import jade.client.module.client.Gui;
import jade.client.module.render.Arraylist;
import jade.deps.gson.Gson;
import jade.deps.gson.GsonBuilder;
import jade.deps.gson.JsonArray;
import jade.deps.gson.JsonElement;
import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonParser;
import jade.deps.loader107.InjectionPaths;
import java.awt.Desktop;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public final class ThemeManager {
   public static final String DEFAULT_THEME_ID = "jade";
   private final File file;
   private final List<Theme> uZjo = new ArrayList<>();
   private final List<Theme> customThemes = new ArrayList<>();
   private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

   public ThemeManager(File var1) {
      this.file = new File(InjectionPaths.dataDirectory(var1), "themes");
      this.file.mkdirs();
      this.uZjo.add(LAg1("jade", "Jade", -12326778, -15630261));
      this.uZjo.add(LAg1("ocean", "Ocean", -14632462, -11832631, -12334640));
      this.uZjo.add(LAg1("sunset", "Sunset", -1028241, -19154, -38891));
      this.uZjo.add(LAg1("forest", "Forest", -8206003, -13337786, -15628932));
      this.uZjo.add(LAg1("purple", "Purple", -7256088, -4679192, -5944431));
      this.uZjo.add(LAg1("midnight", "Midnight", -12623416, -13354343, -11916912));
      this.uZjo.add(LAg1("cyber", "Cyber", -13253144, -9018126, -6149714));
      this.uZjo.add(LAg1("aurora", "Aurora", -12331582, -12807727, -9420569));
      this.reloadCustomThemes();
   }

   private static Theme LAg1(String var0, String var1, Integer... var2) {
      return new Theme(var0, var1, Arrays.asList(var2), false);
   }

   public File getThemesDirectory() {
      return this.file;
   }

   public List<Theme> getAllThemes() {
      ArrayList var1 = new ArrayList<>(this.uZjo);
      var1.addAll(this.customThemes);
      return Collections.unmodifiableList(var1);
   }

   public Theme dwDhpA(String var1) {
      if (var1 != null) {
         for (Theme var3 : this.getAllThemes()) {
            if (var3.getId().equalsIgnoreCase(var1)) {
               return var3;
            }
         }
      }

      return this.uZjo.get(0);
   }

   public Theme cNph(List<Integer> var1) {
      if (var1 != null && !var1.isEmpty()) {
         for (Theme var3 : this.getAllThemes()) {
            if (var3.mKwci3().equals(var1)) {
               return var3;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public synchronized void reloadCustomThemes() {
      this.customThemes.clear();
      File[] var1 = this.file.listFiles();
      if (var1 != null) {
         Arrays.sort(var1, new Comparator<File>() {
            public int compare(File var1, File var2) {
               return var1.getName().compareToIgnoreCase(var2.getName());
            }
         });

         for (File var5 : var1) {
            if (var5.isFile() && var5.getName().toLowerCase().endsWith(".json")) {
               try (FileReader var6 = new FileReader(var5)) {
                  JsonObject var8 = new JsonParser().parse(var6).getAsJsonObject();
                  String var9 = var5.getName().substring(0, var5.getName().length() - 5);
                  String var10 = var8.has("id") ? normalizeThemeId(var8.get("id").getAsString()) : normalizeThemeId(var9);
                  String var11 = var8.has("name") ? var8.get("name").getAsString().trim() : var9;
                  if (var11.toLowerCase(Locale.ROOT).startsWith("imported ") && var11.length() > 9) {
                     var11 = var11.substring(9).trim();
                     var8.addProperty("name", var11);

                     try (FileWriter var12 = new FileWriter(var5)) {
                        this.gson.toJson((JsonElement)var8, var12);
                     }
                  }

                  List var43 = parseColors(var8.getAsJsonArray("colors"));
                  if (!var10.isEmpty() && !var11.isEmpty() && !var43.isEmpty() && !this.themeIdExists(var10)) {
                     this.customThemes.add(new Theme(var10, var11, var43, true));
                  }
               } catch (Exception var42) {
               }
            }
         }
      }
   }

   public synchronized Theme createTheme(String var1, List<Integer> var2) throws IOException {
      String var3 = var1 == null ? "" : var1.trim();
      if (var3.isEmpty()) {
         throw new IOException("Theme name cannot be empty.");
      } else {
         List var4 = wMyh2(var2);
         if (var4.isEmpty()) {
            throw new IOException("Choose at least one color.");
         } else {
            Theme var5 = this.cNph(var4);
            if (var5 != null) {
               return var5;
            } else {
               String var6 = normalizeThemeId(var3);
               if (var6.isEmpty()) {
                  var6 = "theme";
               }

               String var7 = var6;
               int var8 = 2;

               while (this.themeIdExists(var7)) {
                  var7 = var6 + "-" + var8++;
               }

               JsonObject var9 = new JsonObject();
               var9.addProperty("id", var7);
               var9.addProperty("name", var3);
               JsonArray var10 = new JsonArray();

               for (int var12 : (java.lang.Iterable<Integer>) (java.lang.Iterable<?>) (var4)) {
                  var10.add(String.format("#%06X", var12 & 16777215));
               }

               var9.add("colors", var10);
               File var24 = new File(this.file, var7 + ".json");

               try (FileWriter var25 = new FileWriter(var24)) {
                  this.gson.toJson((JsonElement)var9, var25);
               }

               Theme var26 = new Theme(var7, var3, var4, true);
               this.customThemes.add(var26);
               return var26;
            }
         }
      }
   }

   public synchronized Theme updateTheme(Theme var1, String var2, List<Integer> var3) throws IOException {
      if (var1 != null && var1.isCustom()) {
         String var4 = var2 == null ? "" : var2.trim();
         if (var4.isEmpty()) {
            throw new IOException("Theme name cannot be empty.");
         } else {
            List var5 = wMyh2(var3);
            if (var5.isEmpty()) {
               throw new IOException("Choose at least one color.");
            } else {
               JsonObject var6 = new JsonObject();
               var6.addProperty("id", var1.getId());
               var6.addProperty("name", var4);
               JsonArray var7 = new JsonArray();

               for (int var9 : (java.lang.Iterable<Integer>) (java.lang.Iterable<?>) (var5)) {
                  var7.add(String.format("#%06X", var9 & 16777215));
               }

               var6.add("colors", var7);

               try (FileWriter var20 = new FileWriter(new File(this.file, var1.getId() + ".json"))) {
                  this.gson.toJson((JsonElement)var6, var20);
               }

               Theme var21 = new Theme(var1.getId(), var4, var5, true);
               int var23 = this.customThemes.indexOf(var1);
               if (var23 >= 0) {
                  this.customThemes.set(var23, var21);
               } else {
                  this.customThemes.add(var21);
               }

               return var21;
            }
         }
      } else {
         return this.createTheme(var2, var3);
      }
   }

   public synchronized boolean deleteTheme(Theme var1) {
      if (var1 != null && var1.isCustom()) {
         File var2 = new File(this.file, var1.getId() + ".json");
         boolean var3 = !var2.exists() || var2.delete();
         if (var3) {
            this.customThemes.remove(var1);
         }

         return var3;
      } else {
         return false;
      }
   }

   public void openThemesFolder() throws IOException {
      if (!this.file.exists()) {
         this.file.mkdirs();
      }

      Desktop.getDesktop().open(this.file);
   }

   public void fHn0(Theme var1) {
      if (var1 != null && !var1.mKwci3().isEmpty()) {
         List var2 = var1.mKwci3();
         int var3 = (Integer)var2.get(0);
         if (Gui.accent != null) {
            Gui.accent.setRgb(var3 >> 16 & 0xFF, var3 >> 8 & 0xFF, var3 & 0xFF);
         }

         Arraylist.setGradientColors(var2);
         Arraylist var4 = Jade.getModuleManager() == null ? null : Jade.getModuleManager().getModule(Arraylist.class);
         if (var4 != null) {
            var4.guiUpdate();
         }
      }
   }

   private Theme IdSyw(String var1) {
      for (Theme var3 : this.customThemes) {
         if (var3.getId().equalsIgnoreCase(var1)) {
            return var3;
         }
      }

      return null;
   }

   private boolean themeIdExists(String var1) {
      for (Theme var3 : this.getAllThemes()) {
         if (var3.getId().equalsIgnoreCase(var1)) {
            return true;
         }
      }

      return false;
   }

   private static List<Integer> parseColors(JsonArray var0) {
      ArrayList var1 = new ArrayList();
      if (var0 == null) {
         return var1;
      } else {
         for (JsonElement var3 : var0) {
            if (var1.size() == 4) {
               break;
            }

            try {
               String var4 = var3.getAsString().trim();
               if (var4.startsWith("#")) {
                  var4 = var4.substring(1);
               }

               var1.add(0xFF000000 | Integer.parseInt(var4, 16));
            } catch (Exception var5) {
            }
         }

         return var1;
      }
   }

   private static List<Integer> wMyh2(List<Integer> var0) {
      ArrayList var1 = new ArrayList();
      if (var0 == null) {
         return var1;
      } else {
         for (Integer var3 : var0) {
            if (var3 != null && var1.size() < 4) {
               var1.add(0xFF000000 | var3 & 16777215);
            }
         }

         return var1;
      }
   }

   public static String normalizeThemeId(String var0) {
      if (var0 == null) {
         return "";
      } else {
         String var1 = var0.trim().toLowerCase().replaceAll("[^a-z0-9_-]+", "-");
         return var1.replaceAll("^-+|-+$", "");
      }
   }
}
