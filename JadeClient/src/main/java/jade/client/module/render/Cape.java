// Jade recovery: module: Cape (render); original class: jade.deps.eLz.pj2uL42
package jade.client.module.render;

import jade.client.Jade;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.setting.SliderSetting;

import jade.deps.loader107.CoreResourceIndex;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javax.imageio.ImageIO;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

@ModuleInfo
public class Cape extends Module {
   private static final String JADE_CAPE_NAME = "Jade";
   private static final int aJwc = 24;
   private static final int ANIMATION_BRIGHTNESS_THRESHOLD = 26;
   private static final double COLOR_SHIFT_PER_PIXEL = 0.175;
   private static final long UPDATE_INTERVAL_MILLIS = 100L;
   private static Cape instance;
   public static SliderSetting cape;
   private String[] capeModes;
   private List<String> capeEntries = new ArrayList<>();
   public static List<List<ResourceLocation>> capeFrames = new ArrayList<>();
   private int vnO = -1;
   private DynamicTexture dynamicTexture;
   private int[] capePixels;
   private int[] animatedPixelIndices;
   private int[] ru4;
   private int capeTextureWidth;
   private long lastAnimationMillis = -1L;

   public Cape() {
      super("Cape", Category.render, 0);
      instance = this;
      this.KBIxnC();
      this.registerSetting(
         cape = new SliderSetting(
            "Cape",
            0,
            this.capeModes,
            new String[]{"Custom cape"}
         )
      );
   }

   public static boolean isCapeEnabled() {
      return instance != null && instance.isEnabled() && cape != null && cape.getInput() > 0.0;
   }

   private void KBIxnC() {
      ArrayList var1 = new ArrayList();
      String var2 = "assets/jade/textures/capes/";

      try {
         InputStream var3 = CoreResourceIndex.openResource("/" + var2 + "capes.txt");
         if (var3 != null) {
            BufferedReader var4 = new BufferedReader(new InputStreamReader(var3, StandardCharsets.UTF_8));

            String var5;
            while ((var5 = var4.readLine()) != null) {
               var5 = var5.trim();
               if (!var5.isEmpty()) {
                  var1.add(var5);
               }
            }

            var4.close();
            var3.close();
         }
      } catch (Exception var17) {
      }

      if (var1.isEmpty()) {
         ArrayList var18 = new ArrayList();
         TreeMap var22 = new TreeMap();

         for (String var6 : CoreResourceIndex.listResources(var2)) {
            if (var6.toLowerCase().endsWith(".png")) {
               String var7 = var6.substring(var2.length());
               int var8 = var7.indexOf(47);
               if (var8 == -1) {
                  var18.add(var7);
               } else {
                  String var9 = var7.substring(0, var8);
                  String var10 = var7.substring(var8 + 1);
                  if (!var10.isEmpty()) {
                     if (!var22.containsKey(var9)) {
                        var22.put(var9, new ArrayList());
                     }

                     ((List)var22.get(var9)).add(var10);
                  }
               }
            }
         }

         Collections.sort(var18);
         var1.addAll(var18);

         for (Entry var32 : (java.lang.Iterable<Entry>) (java.lang.Iterable<?>) (var22.entrySet())) {
            Collections.sort((List)var32.getValue());
            StringBuilder var36 = new StringBuilder((String)var32.getKey()).append("/");

            for (int var40 = 0; var40 < ((List)var32.getValue()).size(); var40++) {
               if (var40 > 0) {
                  var36.append(",");
               }

               var36.append((String)((List)var32.getValue()).get(var40));
            }

            var1.add(var36.toString());
         }
      }

      if (var1.isEmpty()) {
         try {
            URL var19 = Jade.class.getResource("/" + var2);
            if (var19 != null && var19.getProtocol().equals("file")) {
               File var23 = new File(var19.toURI());
               File[] var29 = var23.listFiles();
               if (var29 != null) {
                  Arrays.sort((Object[])var29);

                  for (File var43 : var29) {
                     if (var43.isFile() && var43.getName().toLowerCase().endsWith(".png")) {
                        var1.add(var43.getName());
                     } else if (var43.isDirectory()) {
                        File[] var45 = var43.listFiles(Cape::LSoFd);
                        if (var45 != null && var45.length > 0) {
                           Arrays.sort((Object[])var45);
                           StringBuilder var11 = new StringBuilder(var43.getName()).append("/");

                           for (int var12 = 0; var12 < var45.length; var12++) {
                              if (var12 > 0) {
                                 var11.append(",");
                              }

                              var11.append(var45[var12].getName());
                           }

                           var1.add(var11.toString());
                        }
                     }
                  }
               }
            }
         } catch (Exception var16) {
         }
      }

      if (var1.isEmpty()) {
         try {
            String var20 = Jade.class.getName().replace('.', '/') + ".class";
            URL var24 = Jade.class.getClassLoader().getResource(var20);
            if (var24 != null && var24.getProtocol().equals("jar")) {
               JarURLConnection var30 = (JarURLConnection)var24.openConnection();
               JarFile var34 = var30.getJarFile();
               Enumeration var38 = var34.entries();
               ArrayList var42 = new ArrayList();
               TreeMap var44 = new TreeMap();

               while (var38.hasMoreElements()) {
                  String var46 = ((JarEntry)var38.nextElement()).getName();
                  if (var46.startsWith(var2) && var46.toLowerCase().endsWith(".png")) {
                     String var48 = var46.substring(var2.length());
                     int var50 = var48.indexOf(47);
                     if (var50 == -1) {
                        var42.add(var48);
                     } else {
                        String var13 = var48.substring(0, var50);
                        String var14 = var48.substring(var50 + 1);
                        if (!var14.isEmpty()) {
                           if (!var44.containsKey(var13)) {
                              var44.put(var13, new ArrayList());
                           }

                           ((List)var44.get(var13)).add(var14);
                        }
                     }
                  }
               }

               Collections.sort(var42);
               var1.addAll(var42);

               for (Entry var49 : (java.lang.Iterable<Entry>) (java.lang.Iterable<?>) (var44.entrySet())) {
                  Collections.sort((List)var49.getValue());
                  StringBuilder var51 = new StringBuilder((String)var49.getKey()).append("/");

                  for (int var52 = 0; var52 < ((List)var49.getValue()).size(); var52++) {
                     if (var52 > 0) {
                        var51.append(",");
                     }

                     var51.append((String)((List)var49.getValue()).get(var52));
                  }

                  var1.add(var51.toString());
               }
            }
         } catch (Exception var15) {
         }
      }

      this.capeEntries = var1;
      ArrayList var21 = new ArrayList();
      var21.add("None");

      for (String var31 : this.capeEntries) {
         int var35 = var31.indexOf(47);
         if (var35 != -1) {
            var21.add(var31.substring(0, var35));
         } else {
            int var39 = var31.lastIndexOf(46);
            var21.add(var39 > 0 ? var31.substring(0, var39) : var31);
         }
      }

      this.capeModes = (String[]) var21.toArray(new String[0]);
   }

   public void loadCapeTextures() {
      capeFrames.clear();
      this.vnO = -1;
      this.dynamicTexture = null;
      this.capePixels = null;
      this.animatedPixelIndices = null;
      this.ru4 = null;
      this.capeTextureWidth = 0;
      this.lastAnimationMillis = -1L;
      String var1 = "/assets/jade/textures/capes/";

      try {
         for (String var3 : this.capeEntries) {
            int var4 = var3.indexOf(47);
            if (var4 != -1) {
               String var16 = var3.substring(0, var4);
               String var17 = var3.substring(var4 + 1);
               String[] var18 = var17.split(",");
               ArrayList var19 = new ArrayList();

               for (String var23 : var18) {
                  var23 = var23.trim();
                  if (!var23.isEmpty()) {
                     InputStream var25 = CoreResourceIndex.openResource(var1 + var16 + "/" + var23);
                     if (var25 != null) {
                        BufferedImage var26 = ImageIO.read(var25);
                        var25.close();
                        if (var26 != null) {
                           var19.add(mc.getTextureManager().getDynamicTextureLocation(var16 + "_" + var23, new DynamicTexture(var26)));
                        }
                     }
                  }
               }

               capeFrames.add(var19);
            } else {
               String var5 = var3.substring(0, var3.lastIndexOf(46));
               InputStream var6 = CoreResourceIndex.openResource(var1 + var3);
               if (var6 == null) {
                  capeFrames.add(new ArrayList<>());
               } else {
                  BufferedImage var7 = ImageIO.read(var6);
                  var6.close();
                  if (var7 == null) {
                     capeFrames.add(new ArrayList<>());
                  } else {
                     int var8 = var7.getWidth();
                     int var9 = var7.getHeight();
                     int var10 = Math.max(1, var8 / 2);
                     ArrayList var11 = new ArrayList();
                     if ("Jade".equals(var5)) {
                        this.vnO = capeFrames.size();
                        var11.add(this.createJadeCapeTexture(var7));
                     } else if (var9 > var10 && var9 % var10 == 0) {
                        int var12 = var9 / var10;

                        for (int var13 = 0; var13 < var12; var13++) {
                           BufferedImage var14 = var7.getSubimage(0, var13 * var10, var8, var10);
                           var11.add(mc.getTextureManager().getDynamicTextureLocation(var5 + "_f" + var13, new DynamicTexture(var14)));
                        }
                     } else {
                        var11.add(mc.getTextureManager().getDynamicTextureLocation(var5, new DynamicTexture(var7)));
                     }

                     capeFrames.add(var11);
                  }
               }
            }
         }
      } catch (Exception var15) {
         var15.printStackTrace();
      }
   }

   public static ResourceLocation FOsFcam() {
      if (!isCapeEnabled()) {
         return null;
      } else {
         if (capeFrames.isEmpty() && instance != null) {
            instance.loadCapeTextures();
         }

         int var0 = (int)(cape.getInput() - 1.0);
         if (var0 >= 0 && var0 < capeFrames.size()) {
            if (instance != null && var0 == instance.vnO) {
               instance.updateJadeCapeAnimation();
            }

            List var1 = capeFrames.get(var0);
            if (var1 != null && !var1.isEmpty()) {
               if (var1.size() == 1) {
                  return (ResourceLocation)var1.get(0);
               } else {
                  int var2 = (int)(System.currentTimeMillis() / 67L % var1.size());
                  return (ResourceLocation)var1.get(var2);
               }
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   private ResourceLocation createJadeCapeTexture(BufferedImage var1) {
      BufferedImage var2 = new BufferedImage(var1.getWidth(), var1.getHeight(), 2);
      Graphics var3 = var2.getGraphics();
      var3.drawImage(var1, 0, 0, null);
      var3.dispose();
      this.capeTextureWidth = var2.getWidth();
      int var4 = var2.getHeight();
      this.capePixels = new int[this.capeTextureWidth * var4];
      ArrayList var5 = new ArrayList();
      ArrayList var6 = new ArrayList();
      var2.getRGB(0, 0, this.capeTextureWidth, var4, this.capePixels, 0, this.capeTextureWidth);

      for (int var7 = 0; var7 < this.capePixels.length; var7++) {
         int var8 = this.capePixels[var7];
         int var9 = var8 >>> 24 & 0xFF;
         int var10 = var8 >>> 16 & 0xFF;
         int var11 = var8 >>> 8 & 0xFF;
         int var12 = var8 & 0xFF;
         int var13 = Math.max(var10, Math.max(var11, var12));
         if (var9 > 0 && var13 > 26) {
            int var14 = Math.min(255, Math.max(0, (var13 - 24) * 255 / 231));
            var5.add(var7);
            var6.add(var14);
            this.capePixels[var7] = var9 << 24 | 1572864 | 6144 | 24;
         }
      }

      this.animatedPixelIndices = new int[var5.size()];
      this.ru4 = new int[var6.size()];

      for (int var15 = 0; var15 < var5.size(); var15++) {
         this.animatedPixelIndices[var15] = (Integer)var5.get(var15);
         this.ru4[var15] = (Integer)var6.get(var15);
      }

      this.dynamicTexture = new DynamicTexture(var2);
      return mc.getTextureManager().getDynamicTextureLocation("jade_dynamic_cape", this.dynamicTexture);
   }

   private void updateJadeCapeAnimation() {
      if (this.dynamicTexture != null && this.capePixels != null && this.animatedPixelIndices != null && this.ru4 != null && this.capeTextureWidth > 0) {
         long var1 = System.currentTimeMillis();
         if (this.lastAnimationMillis < 0L || var1 - this.lastAnimationMillis >= 100L) {
            this.lastAnimationMillis = var1;
            int[] var3 = this.dynamicTexture.getTextureData();
            if (var3.length == this.capePixels.length) {
               for (int var4 = 0; var4 < this.animatedPixelIndices.length; var4++) {
                  int var5 = this.ru4[var4];
                  int var6 = this.animatedPixelIndices[var4];
                  int var7 = this.capePixels[var6];
                  int var8 = var7 >>> 24 & 0xFF;
                  int var9 = var6 % this.capeTextureWidth;
                  int var10 = Arraylist.xQec0(var9 * 0.175);
                  int var11 = 255 - var5;
                  int var12 = ((var7 >>> 16 & 0xFF) * var11 + (var10 >>> 16 & 0xFF) * var5) / 255;
                  int var13 = ((var7 >>> 8 & 0xFF) * var11 + (var10 >>> 8 & 0xFF) * var5) / 255;
                  int var14 = ((var7 & 0xFF) * var11 + (var10 & 0xFF) * var5) / 255;
                  var3[var6] = var8 << 24 | var12 << 16 | var13 << 8 | var14;
               }

               this.dynamicTexture.updateDynamicTexture();
            }
         }
      }
   }

   private static boolean LSoFd(File var0, String var1) {
      return var1.toLowerCase().endsWith(".png");
   }
}
