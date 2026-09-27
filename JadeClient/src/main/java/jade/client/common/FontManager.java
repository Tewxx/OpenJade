// Jade recovery: original class: jade.deps.eLz.h8r705KPe
package jade.client.common;

import jade.client.module.render.nametags.MinecraftFont;
import jade.deps.loader107.InjectionPaths;
import jade.deps.loader107.CoreResourceIndex;
import java.awt.Font;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public final class FontManager {
   private static final String MINECRAFT = "Minecraft";
   private static final String RESOURCE_ROOT = "/assets/jade/fonts/";
   private static final int MAX_CACHED_RENDERERS = 512;
   private static final long FONT_SCAN_INTERVAL_MS = 2000L;
   private static final float DEFAULT_HUD_FONT_SIZE = 10.0F;
   private static final float DEFAULT_CLICK_GUI_HEADER_HEIGHT = 9.0F;
   private static final float DEFAULT_CLICK_GUI_SETTING_HEIGHT = 8.0F;
   private static final float DEFAULT_NAMETAG_FONT_SIZE = 9.0F;
   private static final FontManager$7[] BUNDLED_FONTS = new FontManager$7[]{
      new FontManager$7("Modern", "ProductSans-Regular.ttf", 0),
      new FontManager$7("Bold", "ProductSans-Bold.ttf", 1),
      new FontManager$7("Smooth", "Smooth.ttf", 0),
      new FontManager$7("Pixel", "Doto-Black.ttf", 0),
      new FontManager$7("Tahoma", "tahoma.ttf", 0)
   };
   private static final Map<String, FontManager$7> BUNDLED_FONT_MAP = buildBundledFontMap();
   private static final Map<String, Font> BASE_FONT_CACHE = new ConcurrentHashMap<>();
   private static final Map<String, IFont> FONT_CACHE = new LinkedHashMap<>(16, 0.75F, true);
   private static final Map<String, FontManager$8> CUSTOM_FONT_MAP = new LinkedHashMap<>();
   private static final Map<String, String> BOLD_FAMILY_MAP = new LinkedHashMap<>();
   private static String[] hudFontOptions = buildBundledHudFontOptions();
   private static File fontDirectory;
   private static long nextFontScanMs;
   private static String lastDirectoryFingerprint = "";

   private FontManager() {
   }

   public static void init() {
      getFontDirectory();
      refreshCustomFonts(true);
   }

   public static void tick() {
      refreshCustomFonts(false);
   }

   public static File getFontDirectory() {
      if (fontDirectory == null) {
         fontDirectory = new File(InjectionPaths.dataDirectory(Minecraft.getMinecraft().mcDataDir), "fonts");
      }

      if (!fontDirectory.exists()) {
         fontDirectory.mkdirs();
      }

      return fontDirectory;
   }

   public static String[] getHudFontOptions() {
      refreshCustomFonts(false);
      return (String[])hudFontOptions.clone();
   }

   public static String getDefaultHudFontName() {
      String[] var0 = getHudFontOptions();
      return var0.length == 0 ? "Minecraft" : var0[0];
   }

   public static String resolveHudFontName(String var0) {
      refreshCustomFonts(false);
      if (var0 == null || var0.trim().isEmpty()) {
         return getDefaultHudFontName();
      } else {
         return isAvailableFont(var0) ? var0 : getDefaultHudFontName();
      }
   }

   public static boolean isAvailableFont(String var0) {
      if (var0 == null) {
         return false;
      } else if (!isMinecraftFont(var0) && !BUNDLED_FONT_MAP.containsKey(var0)) {
         refreshCustomFonts(false);
         return CUSTOM_FONT_MAP.containsKey(var0);
      } else {
         return true;
      }
   }

   public static String getBoldFamily(String var0) {
      if (isMinecraftFont(var0)) {
         return var0;
      } else {
         refreshCustomFonts(false);
         String var1 = resolveHudFontName(var0);
         String var2 = BOLD_FAMILY_MAP.get(var1);
         if (var2 != null && isAvailableFont(var2)) {
            return var2;
         } else {
            return "Modern".equals(var1) ? "Bold" : var1;
         }
      }
   }

   public static IFont getHudRenderer(String var0, float var1) {
      float var2 = Math.max(0.5F, Math.min(2.0F, var1));
      return getRenderer(var0, 10.0F * var2);
   }

   public static IFont getClickGuiHeaderRenderer(String var0) {
      return getRendererForPixelHeight(var0, 9.0F);
   }

   public static IFont getClickGuiSettingRenderer(String var0) {
      return getRendererForPixelHeight(var0, 8.0F);
   }

   public static IFont getNametagRenderer(String var0) {
      return getRenderer(var0, 9.0F);
   }

   public static IFont getPixelHeightRenderer(String var0, float var1) {
      return getRendererForPixelHeight(var0, var1);
   }

   private static IFont getRenderer(String var0, float var1) {
      final float var2 = Math.max(1.0F, var1);
      if (var0 != null && !isMinecraftFont(var0)) {
         final FontManager$7 var3 = BUNDLED_FONT_MAP.get(var0);
         if (var3 != null) {
            String var6 = var0 + "#" + quantizeForCacheKey(var2) + "#" + getUiScale();
            return getCachedRenderer(
               var6,
               new Supplier<IFont>() {
                  public IFont get() {
                     Font var1x = FontManager.BASE_FONT_CACHE.computeIfAbsent(FontManager$7.access$100(var3), var0x -> FontManager.loadBaseFont(var0x));
                     return (IFont)(var1x == null
                        ? FontManager.getMinecraftRenderer(var2)
                        : new TrueTypeFont(var1x.deriveFont(FontManager$7.access$400(var3), var2), true));
                  }
               }
            );
         } else {
            final FontManager$8 var4 = getCustomFont(var0);
            if (var4 == null) {
               return getMinecraftRenderer(var2);
            } else {
               String var5 = "custom:" + FontManager$8.access$600(var4) + "#" + quantizeForCacheKey(var2) + "#" + getUiScale();
               return getCachedRenderer(
                  var5,
                  new Supplier<IFont>() {
                     public IFont get() {
                        Font var1 = FontManager.BASE_FONT_CACHE.computeIfAbsent(FontManager$8.access$600(var4), new Function<String, Font>() {
                           public Font apply(String var1) {
                              return FontManager.loadCustomBaseFont(FontManager$8.access$700(var4));
                           }
                        });
                        return (IFont)(var1 == null
                           ? FontManager.getMinecraftRenderer(var2)
                           : new TrueTypeFont(var1.deriveFont(FontManager$8.access$900(var4), var2), true));
                     }
                  }
               );
            }
         }
      } else {
         return getMinecraftRenderer(var2);
      }
   }

   private static IFont getRendererForPixelHeight(String var0, float var1) {
      final float var2 = Math.max(1.0F, var1);
      if (var0 != null && !isMinecraftFont(var0)) {
         final FontManager$7 var3 = BUNDLED_FONT_MAP.get(var0);
         if (var3 != null) {
            String var6 = var0 + "#height#" + quantizeForCacheKey(var2) + "#" + getUiScale();
            return getCachedRenderer(
               var6,
               new Supplier<IFont>() {
                  public IFont get() {
                     Font var1x = FontManager.BASE_FONT_CACHE.computeIfAbsent(FontManager$7.access$100(var3), var0x -> FontManager.loadBaseFont(var0x));
                     return var1x == null
                        ? FontManager.getMinecraftRenderer(var2)
                        : FontManager.createHeightMatchedRenderer(var1x, FontManager$7.access$400(var3), var2);
                  }
               }
            );
         } else {
            final FontManager$8 var4 = getCustomFont(var0);
            if (var4 == null) {
               return getMinecraftRenderer(var2);
            } else {
               String var5 = "custom:" + FontManager$8.access$600(var4) + "#height#" + quantizeForCacheKey(var2) + "#" + getUiScale();
               return getCachedRenderer(
                  var5,
                  new Supplier<IFont>() {
                     public IFont get() {
                        Font var1 = FontManager.BASE_FONT_CACHE.computeIfAbsent(FontManager$8.access$600(var4), new Function<String, Font>() {
                           public Font apply(String var1) {
                              return FontManager.loadCustomBaseFont(FontManager$8.access$700(var4));
                           }
                        });
                        return var1 == null
                           ? FontManager.getMinecraftRenderer(var2)
                           : FontManager.createHeightMatchedRenderer(var1, FontManager$8.access$900(var4), var2);
                     }
                  }
               );
            }
         }
      } else {
         return getMinecraftRenderer(var2);
      }
   }

   private static IFont createHeightMatchedRenderer(Font var0, int var1, float var2) {
      float var3 = var2;
      TrueTypeFont var4 = new TrueTypeFont(var0.deriveFont(var1, var2), true);

      for (int var5 = 0; var5 < 2; var5++) {
         float var6 = Math.max(1.0F, (float)var4.getFontHeight());
         float var7 = Math.abs(var6 - var2);
         if (var7 <= 0.5F) {
            break;
         }

         TrueTypeFont var8 = var4;
         var3 = Math.max(1.0F, var3 * (var2 / var6));
         var4 = new TrueTypeFont(var0.deriveFont(var1, var3), true);
         var8.destroy();
      }

      return var4;
   }

   private static IFont getMinecraftRenderer(float var0) {
      float var1 = Math.max(1.0F, (float)Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT);
      final float var2 = Math.max(0.5F, Math.min(2.0F, var0 / var1));
      String var3 = "Minecraft#" + quantizeForCacheKey(var2);
      return getCachedRenderer(var3, new Supplier<IFont>() {
         public IFont get() {
            return new MinecraftFont(Minecraft.getMinecraft().fontRendererObj, var2);
         }
      });
   }

   public static boolean isMinecraftFont(String var0) {
      return var0 == null || "Minecraft".equalsIgnoreCase(var0);
   }

   private static String[] buildBundledHudFontOptions() {
      String[] var0 = new String[BUNDLED_FONTS.length + 1];

      for (int var1 = 0; var1 < BUNDLED_FONTS.length; var1++) {
         var0[var1] = FontManager$7.access$1100(BUNDLED_FONTS[var1]);
      }

      var0[BUNDLED_FONTS.length] = "Minecraft";
      return var0;
   }

   private static Map<String, FontManager$7> buildBundledFontMap() {
      LinkedHashMap var0 = new LinkedHashMap();

      for (FontManager$7 var4 : BUNDLED_FONTS) {
         var0.put(FontManager$7.access$1100(var4), var4);
      }

      var0.put("Sf-Regular", var0.get("Modern"));
      var0.put("Sf-Ui", var0.get("Modern"));
      var0.put("Sf-Bold", var0.get("Bold"));
      return var0;
   }

   private static synchronized FontManager$8 getCustomFont(String var0) {
      refreshCustomFonts(false);
      return CUSTOM_FONT_MAP.get(var0);
   }

   private static synchronized void refreshCustomFonts(boolean var0) {
      long var1 = System.currentTimeMillis();
      if (var0 || var1 >= nextFontScanMs) {
         nextFontScanMs = var1 + 2000L;
         File var3 = getFontDirectory();
         File[] var4 = var3.listFiles();
         String var5 = buildDirectoryFingerprint(var4);
         if (var0 || !var5.equals(lastDirectoryFingerprint)) {
            lastDirectoryFingerprint = var5;
            LinkedHashMap var6 = new LinkedHashMap();
            LinkedHashMap var7 = new LinkedHashMap();
            if (var4 != null) {
               Arrays.sort(var4, (var0x, var1x) -> var0x.getName().compareToIgnoreCase(var1x.getName()));

               for (File var11 : var4) {
                  if (var11.isFile() && isSupportedFontFile(var11)) {
                     String var12 = displayNameForFile(var11);
                     if (var12.length() != 0 && !isReservedDisplayName(var12)) {
                        int var13 = isBoldDisplayName(var12) ? 1 : 0;
                        var6.put(var12, new FontManager$8(var12, var11, var13));
                     }
                  }
               }
            }

            for (String var15 : (java.lang.Iterable<String>) (java.lang.Iterable<?>) (var6.keySet())) {
               if (isBoldDisplayName(var15)) {
                  String var16 = regularNameForBoldDisplay(var15);
                  if (var6.containsKey(var16)) {
                     var7.put(var16, var15);
                  }
               }
            }

            var7.put("Modern", "Bold");
            var7.put("Sf-Regular", "Bold");
            var7.put("Sf-Ui", "Bold");
            CUSTOM_FONT_MAP.clear();
            CUSTOM_FONT_MAP.putAll(var6);
            BOLD_FAMILY_MAP.clear();
            BOLD_FAMILY_MAP.putAll(var7);
            hudFontOptions = buildHudFontOptions(var6.keySet());
         }
      }
   }

   private static String[] buildHudFontOptions(Iterable<String> var0) {
      ArrayList var1 = new ArrayList();

      for (String var5 : buildBundledHudFontOptions()) {
         var1.add(var5);
      }

      for (String var7 : var0) {
         var1.add(var7);
      }

      return (String[]) var1.toArray(new String[0]);
   }

   private static String buildDirectoryFingerprint(File[] var0) {
      if (var0 != null && var0.length != 0) {
         Arrays.sort(var0, (var0x, var1x) -> var0x.getName().compareToIgnoreCase(var1x.getName()));
         StringBuilder var1 = new StringBuilder();

         for (File var5 : var0) {
            if (var5.isFile() && isSupportedFontFile(var5)) {
               var1.append(var5.getName()).append(':').append(var5.length()).append(':').append(var5.lastModified()).append(';');
            }
         }

         return var1.toString();
      } else {
         return "";
      }
   }

   private static boolean isSupportedFontFile(File var0) {
      String var1 = var0.getName().toLowerCase();
      return var1.endsWith(".ttf") || var1.endsWith(".otf");
   }

   private static boolean isReservedDisplayName(String var0) {
      return "Minecraft".equalsIgnoreCase(var0) || BUNDLED_FONT_MAP.containsKey(var0);
   }

   private static String displayNameForFile(File var0) {
      String var1 = var0.getName();
      int var2 = var1.lastIndexOf(46);
      if (var2 > 0) {
         var1 = var1.substring(0, var2);
      }

      var1 = var1.replace('_', ' ').replace('-', ' ').trim();
      var1 = var1.replaceAll("\\s+", " ");
      if (var1.toLowerCase().endsWith("bold") && !var1.toLowerCase().endsWith(" bold")) {
         var1 = var1.substring(0, var1.length() - 4).trim() + " Bold";
      }

      return var1;
   }

   private static boolean isBoldDisplayName(String var0) {
      return var0 != null && var0.toLowerCase().endsWith(" bold");
   }

   private static String regularNameForBoldDisplay(String var0) {
      return var0.substring(0, var0.length() - " Bold".length()).trim();
   }

   private static Font loadBaseFont(String var0) {
      byte[] var1 = readFontData(var0);
      if (var1 == null) {
         return null;
      } else {
         try {
            return Font.createFont(0, new ByteArrayInputStream(var1));
         } catch (Exception var5) {
            try {
               return Font.createFont(1, new ByteArrayInputStream(var1));
            } catch (Exception var4) {
               return null;
            }
         }
      }
   }

   private static Font loadCustomBaseFont(File var0) {
      if (var0 != null && var0.isFile()) {
         try (FileInputStream var1 = new FileInputStream(var0)) {
            return Font.createFont(0, var1);
         } catch (Exception var15) {
            return null;
         }
      } else {
         return null;
      }
   }

   private static int getUiScale() {
      try {
         return Math.max(1, new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor());
      } catch (Exception var1) {
         return 1;
      }
   }

   private static float quantizeForCacheKey(float var0) {
      return Math.round(var0 * 100.0F) / 100.0F;
   }

   private static byte[] readFontData(String var0) {
      try (InputStream var1 = CoreResourceIndex.openResource("/assets/jade/fonts/" + var0)) {
         if (var1 != null) {
            ByteArrayOutputStream var20 = new ByteArrayOutputStream();
            byte[] var4 = new byte[4096];

            int var5;
            while ((var5 = var1.read(var4)) != -1) {
               var20.write(var4, 0, var5);
            }

            return var20.toByteArray();
         } else {
            return null;
         }
      } catch (IOException var19) {
         return null;
      }
   }

   private static synchronized IFont getCachedRenderer(String var0, Supplier<IFont> var1) {
      IFont var2 = FONT_CACHE.get(var0);
      if (var2 != null) {
         return var2;
      } else {
         var2 = (IFont)var1.get();
         FONT_CACHE.put(var0, var2);
         trimFontCache();
         return var2;
      }
   }

   private static void trimFontCache() {
      while (FONT_CACHE.size() > 512) {
         Iterator var0 = FONT_CACHE.entrySet().iterator();
         if (!var0.hasNext()) {
            return;
         }

         Entry var1 = (Entry)var0.next();
         var0.remove();
         if (var1.getValue() != null) {
            ((IFont)var1.getValue()).destroy();
         }
      }
   }

   static java.util.Map access$200() {
      return jade.client.common.FontManager.BASE_FONT_CACHE;
   }

   static jade.client.common.IFont access$300(float arg0) {
      return jade.client.common.FontManager.getMinecraftRenderer(arg0);
   }

   static java.awt.Font access$500(java.lang.String arg0) {
      return jade.client.common.FontManager.loadBaseFont(arg0);
   }

   static java.awt.Font access$800(java.io.File arg0) {
      return jade.client.common.FontManager.loadCustomBaseFont(arg0);
   }

   static jade.client.common.IFont access$1000(java.awt.Font arg0, int arg1, float arg2) {
      return jade.client.common.FontManager.createHeightMatchedRenderer(arg0, arg1, arg2);
   }
}
