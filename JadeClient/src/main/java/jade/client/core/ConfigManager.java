// Jade recovery: original class: jade.deps.eLz.abaYcs
package jade.client.core;

import jade.client.Jade;
import jade.client.common.CategoryComponent;
import jade.client.common.ClientUtils;
import jade.client.common.DangerousModules;
import jade.client.common.EventBus;
import jade.client.common.IMinecraft;
import jade.client.common.JadeClickGui;
import jade.client.common.ConfigEntry;
import jade.client.event.ProfileLoadEvent;
import jade.client.gui.ClickGui;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.client.ChatCommands;
import jade.client.module.client.Gui;
import jade.client.module.client.Relationships;
import jade.client.module.client.Rendering;
import jade.client.module.client.Settings;
import jade.client.module.minigames.BedwarsUtils;
import jade.client.module.other.LagDetect;
import jade.client.module.player.BridgeAssist;
import jade.client.module.player.HideWindow;
import jade.client.module.render.Arraylist;
import jade.client.module.render.ESP;
import jade.client.module.render.Notifications;
import jade.client.module.render.TargetHUD;
import jade.client.module.render.Watermark;
import jade.client.setting.BlockListSetting;
import jade.client.setting.BooleanSetting;
import jade.client.setting.CategoryListSetting;
import jade.client.setting.ColorSetting;
import jade.client.setting.FontSetting;
import jade.client.setting.KeySetting;
import jade.client.setting.Setting;
import jade.client.setting.SliderSetting;
import jade.client.setting.StringListSetting;
import jade.client.setting.TextSetting;
import jade.deps.gson.GsonBuilder;
import jade.deps.gson.JsonArray;
import jade.deps.gson.JsonElement;
import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonParser;

import jade.deps.loader107.InjectionPaths;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Set;

public class ConfigManager implements IMinecraft {
   private boolean qhUtvV;
   private static final String DEFAULT_PROFILE_NAME = "default";
   private static final int DEFAULT_CONFIG_REVISION = 1;
   private static final char[] INVALID_NAME_CHARS;
   public File file;
   public List<ConfigEntry> profiles = new ArrayList<>();

   public ConfigManager() {
      this.file = new File(InjectionPaths.dataDirectory(mc.mcDataDir), "profiles");
      if (!this.file.exists()) {
         boolean var1 = this.file.mkdirs();
         if (!var1) {
            System.out.println("There was an issue creating profiles directory.");
            return;
         }
      }

      File var2 = new File(this.file, "default.json");
      if (this.YbkO(var2)) {
         this.writeProfile(new ConfigEntry("default", 0));
      }
   }

   public synchronized void saveProfile(ConfigEntry var1) {
      this.writeProfile(var1);
      this.saveGlobalState();
   }

   private void writeProfile(ConfigEntry var1) {
      JsonObject var2 = new JsonObject();
      if ("default".equalsIgnoreCase(var1.getName())) {
         var2.addProperty("defaultConfigRevision", 1);
      }

      var2.addProperty("keybind", var1.getProfile().getKeycode());
      var2.addProperty("theme", var1.getThemeName());
      var2.add("dropdownAppearance", ThemeConfig.serialize());
      JsonArray var3 = new JsonArray();

      for (Module var5 : Jade.getModuleManager().getModules()) {
         if (!this.isGlobalStateModule(var5) && (!var5.skipSettingsPersistence || isRelationshipsModule(var5))) {
            JsonObject var6 = var5.skipSettingsPersistence ? serializeModuleSummary(var5) : VUVhC(var5);
            var3.add(var6);
         }
      }

      var2.add("modules", var3);

      try {
         writeJsonFile(new File(this.file, var1.getName() + ".json"), var2);
      } catch (Exception var7) {
         this.UYPXwrI("save", var1.getName());
         var7.printStackTrace();
      }
   }

   private boolean YbkO(File var1) {
      if (!var1.isFile()) {
         return true;
      } else {
         try (FileReader var2 = new FileReader(var1)) {
            JsonObject var4 = new JsonParser().parse(var2).getAsJsonObject();
            return var4 == null || !var4.has("defaultConfigRevision") || var4.get("defaultConfigRevision").getAsInt() < 1;
         } catch (Exception var17) {
            return true;
         }
      }
   }

   public ConfigEntry dvuld(String var1, int var2) {
      String var3 = this.normalizeProfileName(var1);
      String var4 = this.validateProfileName(var3, null);
      if (var4 != null) {
         ClientUtils.sendJadeMessage("Jade", "&c" + var4);
         return null;
      } else {
         ConfigEntry var5 = new ConfigEntry(var3, var2);
         if (Jade.Grq != null) {
            var5.odjy(Jade.Grq.getThemeName());
         }

         this.saveProfile(var5);
         this.profiles.add(var5);
         this.refreshProfileList();
         return var5;
      }
   }

   private static JsonObject VUVhC(Module var0) {
      JsonObject var1 = new JsonObject();
      var1.addProperty("name", var0.getName());
      if (var0.canBeEnabled) {
         var1.addProperty("enabled", var0.isEnabled());
         var1.addProperty("hidden", var0.isHidden());
         var1.addProperty("keybind", var0.getKeycode());
      }

      if (var0 instanceof Arraylist) {
         var1.addProperty("posX", Arraylist.anchorX);
         var1.addProperty("posY", Arraylist.riwe);
         var1.addProperty("relPosX", Arraylist.getAnchorXRatio());
         var1.addProperty("relPosY", Arraylist.IRZw());
      } else if (var0 instanceof TargetHUD) {
         var1.addProperty("posX", Jade.getModuleManager().getModule(TargetHUD.class).Vilxx);
         var1.addProperty("posY", Jade.getModuleManager().getModule(TargetHUD.class).offsetY);
      } else if (var0 instanceof Watermark) {
         Watermark var2 = (Watermark)var0;
         var1.addProperty("posX", var2.getRenderX());
         var1.addProperty("posY", var2.getRenderY());
         var1.addProperty("relPosX", var2.PhZ0());
         var1.addProperty("relPosY", var2.getOffsetFractionY());
      } else if (var0 instanceof BedwarsUtils) {
         BedwarsUtils var5 = (BedwarsUtils)var0;
         var1.addProperty("finalKillsPosX", var5.getFinalKillHudX());
         var1.addProperty("finalKillsPosY", var5.iskppX4());
         var1.addProperty("finalKillsRelPosX", var5.getFinalKillHudXRatio());
         var1.addProperty("finalKillsRelPosY", var5.UFKv());
         var1.addProperty("dragonHudPosX", var5.getDragonHudX());
         var1.addProperty("dragonHudPosY", var5.getDragonHudY());
         var1.addProperty("dragonHudRelPosX", var5.getDragonHudXRatio());
         var1.addProperty("dragonHudRelPosY", var5.getDragonHudYRatio());
         var1.addProperty("buildLimitHudPosX", var5.getBuildLimitHudX());
         var1.addProperty("buildLimitHudPosY", var5.getBuildLimitHudY());
         var1.addProperty("buildLimitHudRelPosX", var5.faOoa());
         var1.addProperty("buildLimitHudRelPosY", var5.uwJfi());
      } else if (var0 instanceof HideWindow) {
         HideWindow var6 = (HideWindow)var0;
         var1.addProperty("posX", var6.getWindowPixelX());
         var1.addProperty("posY", var6.getWindowPixelY());
         var1.addProperty("relPosX", var6.yyhw2());
         var1.addProperty("relPosY", var6.FJkOz());
      } else if (var0 instanceof Notifications) {
         var1.addProperty("posX", Notifications.getScreenAnchorX());
         var1.addProperty("posY", Notifications.getScreenAnchorY());
         var1.addProperty("relPosX", Notifications.DvQm());
         var1.addProperty("relPosY", Notifications.getAnchorRatioY());
      } else if (var0 instanceof LagDetect) {
         var1.addProperty("posX", LagDetect.getAlertX());
         var1.addProperty("posY", LagDetect.unh25());
         var1.addProperty("relPosX", LagDetect.getPositionXFraction());
         var1.addProperty("relPosY", LagDetect.getPositionYFraction());
      } else if (var0 instanceof BridgeAssist) {
         BridgeAssist var7 = (BridgeAssist)var0;
         var1.addProperty("blocksHudRelPosX", var7.getHudFractionX());
         var1.addProperty("blocksHudRelPosY", var7.getHudFractionY());
      } else if (var0 instanceof ESP) {
         var1.addProperty("independentColorOpacity", true);
      } else if (var0 instanceof Gui) {
         for (CategoryComponent var3 : ClickGui.categoryPanels) {
            var1.addProperty(var3.category.name(), var3.panelX + "," + var3.TFmJ1 + "," + var3.expanded);
         }
      }

      for (Setting var10 : var0.getSettings()) {
         if (!(var0 instanceof Gui) || !ThemeConfig.isDropdownAppearanceSetting(var10)) {
            if (var10 instanceof BooleanSetting && !((BooleanSetting)var10).isButton) {
               var1.addProperty(var10.getPath(), ((BooleanSetting)var10).isToggled());
            } else if (var10 instanceof FontSetting) {
               var1.addProperty(var10.getPath(), ((FontSetting)var10).getFontName());
            } else if (var10 instanceof SliderSetting) {
               var1.addProperty(var10.getPath(), ((SliderSetting)var10).getInput());
            } else if (var10 instanceof KeySetting) {
               var1.add(var10.getPath(), ((KeySetting)var10).MtlQ());
            } else if (var10 instanceof TextSetting) {
               var1.addProperty(var10.getPath(), ((TextSetting)var10).getValue());
            } else if (var10 instanceof ColorSetting) {
               ColorSetting var4 = (ColorSetting)var10;
               var1.addProperty(var10.getPath(), var4.getRed() + "," + var4.getGreen() + "," + var4.getBlue() + "," + var4.JIjrD());
            } else if (var10 instanceof BlockListSetting) {
               var1.add(var10.getPath(), ((BlockListSetting)var10).toJsonArray());
            } else if (var10 instanceof StringListSetting) {
               var1.add(var10.getPath(), ((StringListSetting)var10).toJsonArray());
            } else if (var10 instanceof CategoryListSetting) {
               var1.add(var10.getPath(), ((CategoryListSetting)var10).OIXIqfO());
            }
         }
      }

      return var1;
   }

   private static JsonObject serializeModuleSummary(Module var0) {
      JsonObject var1 = new JsonObject();
      var1.addProperty("name", var0.getName());
      if (var0.canBeEnabled) {
         var1.addProperty("enabled", var0.isEnabled());
         var1.addProperty("hidden", var0.isHidden());
         var1.addProperty("keybind", var0.getKeycode());
      }

      return var1;
   }

   private static boolean isRelationshipsModule(Module var0) {
      return var0 instanceof Relationships;
   }

   public void loadProfile(String var1) {
      ConfigEntry var2 = this.jnkYch(var1);
      String var3 = var2 != null ? var2.getName() : this.normalizeProfileName(var1);
      boolean var4 = false;

      for (File var6 : this.listProfileFiles()) {
         if (!var6.exists()) {
            this.UYPXwrI("load", var3);
            return;
         }

         if (var6.getName().equals(var3 + ".json")) {
            var4 = true;

            try (FileReader var7 = new FileReader(var6)) {
               JsonParser var9 = new JsonParser();
               JsonObject var10 = var9.parse(var7).getAsJsonObject();
               if (var10 == null) {
                  this.UYPXwrI("load", var3);
                  return;
               }

               JsonArray var11 = var10.getAsJsonArray("modules");
               if (var11 != null) {
                  DangerousModules.LKncO();
                  List var12 = this.GENF();
                  Map var13 = this.QfD61(var12);
                  LinkedHashMap var14 = new LinkedHashMap();
                  LinkedHashMap var15 = new LinkedHashMap();
                  JsonObject var16 = this.loadGlobalState();
                  boolean var17 = false;
                  boolean var18 = false;
                  HashMap var19 = new HashMap();
                  LinkedHashSet var20 = new LinkedHashSet();
                  boolean var21 = false;
                  boolean var22 = false;

                  for (JsonElement var24 : var11) {
                     if (var24 != null && var24.isJsonObject()) {
                        JsonObject var25 = var24.getAsJsonObject();
                        if (var25.has("name") && var25.get("name").isJsonPrimitive()) {
                           String var26 = var25.get("name").getAsString();
                           if (var26 != null && !var26.isEmpty()) {
                              if ("Rendering".equalsIgnoreCase(var26)) {
                                 var21 = true;
                              } else if (var25.has("Render Output")) {
                                 try {
                                    var22 |= var25.get("Render Output").getAsDouble() == 1.0;
                                 } catch (RuntimeException var64) {
                                 }
                              }

                              Module var27 = Jade.getModuleManager().getModuleByName(var26);
                              if (var27 != null) {
                                 if (this.isGlobalStateModule(var27)) {
                                    var15.put(var27, var25);
                                 } else {
                                    var14.put(var27, var25);
                                    if (var27 instanceof Relationships) {
                                       var18 = true;
                                    }

                                    if (var27.canBeEnabled()) {
                                       ConfigManager$0 var28 = (ConfigManager$0)var13.get(var27);
                                       if (var28 == null) {
                                          var28 = new ConfigManager$0(false, 0);
                                          var13.put(var27, var28);
                                       }

                                       try {
                                          if (var25.has("enabled")) {
                                             var28.enabled = var25.get("enabled").getAsBoolean();
                                          }

                                          if (var25.has("hidden")) {
                                             var28.hidden = var25.get("hidden").getAsBoolean();
                                          }

                                          if (var25.has("keybind")) {
                                             var28.keybind = var25.get("keybind").getAsInt();
                                          }
                                       } catch (RuntimeException var63) {
                                          var20.add(var27.getName());
                                       }
                                    }
                                 }
                              }
                           }
                        } else {
                           var20.add("unnamed module entry");
                        }
                     } else {
                        var20.add("invalid module entry");
                     }
                  }

                  if (!var18 && Jade.getModuleManager().getModule(Relationships.class) != null && Jade.relationManager != null) {
                     ConfigManager$0 var71 = (ConfigManager$0)var13.get(Jade.getModuleManager().getModule(Relationships.class));
                     if (var71 != null) {
                        var71.enabled = Jade.relationManager.isActive();
                     }
                  }

                  Notifications.setNotificationsSuppressed(true);

                  for (Module var76 : (java.lang.Iterable<Module>) (java.lang.Iterable<?>) (var12)) {
                     ConfigManager$0 var80 = (ConfigManager$0)var13.get(var76);
                     if (var80 != null && !var80.enabled && var76.isEnabled()) {
                        try {
                           var76.disable();
                        } catch (RuntimeException var62) {
                           var20.add(var76.getName());
                        }
                     }
                  }

                  for (Module var77 : (java.lang.Iterable<Module>) (java.lang.Iterable<?>) (var12)) {
                     ConfigManager$0 var81 = (ConfigManager$0)var13.get(var77);
                     if (var81 != null) {
                        try {
                           var77.setKeycode(var81.keybind);
                           if (var81.hidden != null) {
                              var77.setHidden(var81.hidden);
                           }
                        } catch (RuntimeException var61) {
                           var20.add(var77.getName());
                        }
                     }
                  }

                  for (Entry var78 : (java.lang.Iterable<Entry>) (java.lang.Iterable<?>) (var14.entrySet())) {
                     Module var82 = (Module)var78.getKey();
                     JsonObject var84 = (JsonObject)var78.getValue();

                     try {
                        if (var82 instanceof Arraylist) {
                           if (var84.has("relPosX") && var84.has("relPosY")) {
                              Arraylist.setAnchorRatios(var84.get("relPosX").getAsFloat(), var84.get("relPosY").getAsFloat());
                           } else if (var84.has("posX") || var84.has("posY")) {
                              float var89 = var84.has("posX") ? var84.get("posX").getAsFloat() : Arraylist.anchorX;
                              float var102 = var84.has("posY") ? var84.get("posY").getAsFloat() : Arraylist.riwe;
                              Arraylist.setAbsolutePosition(var89, var102);
                           }
                        } else if (var82 instanceof TargetHUD) {
                           if (var84.has("posX")) {
                              int var90 = var84.get("posX").getAsInt();
                              Jade.getModuleManager().getModule(TargetHUD.class).Vilxx = var90;
                           }

                           if (var84.has("posY")) {
                              int var91 = var84.get("posY").getAsInt();
                              Jade.getModuleManager().getModule(TargetHUD.class).offsetY = var91;
                           }
                        } else if (var82 instanceof Watermark) {
                           Watermark var92 = (Watermark)var82;
                           if (var84.has("relPosX") && var84.has("relPosY")) {
                              var92.XkC0(var84.get("relPosX").getAsFloat(), var84.get("relPosY").getAsFloat());
                           } else if (var84.has("posX") || var84.has("posY")) {
                              float var103 = var84.has("posX") ? var84.get("posX").getAsFloat() : var92.getRenderX();
                              float var29 = var84.has("posY") ? var84.get("posY").getAsFloat() : var92.getRenderY();
                              var92.setScreenPosition(var103, var29);
                           }
                        } else if (var82 instanceof BedwarsUtils) {
                           BedwarsUtils var93 = (BedwarsUtils)var82;
                           if (var84.has("finalKillsRelPosX") && var84.has("finalKillsRelPosY")) {
                              var93.applyFinalKillHudDrag(var84.get("finalKillsRelPosX").getAsFloat(), var84.get("finalKillsRelPosY").getAsFloat());
                           } else if (var84.has("finalKillsPosX") || var84.has("finalKillsPosY")) {
                              float var104 = var84.has("finalKillsPosX") ? var84.get("finalKillsPosX").getAsFloat() : var93.getFinalKillHudX();
                              float var115 = var84.has("finalKillsPosY") ? var84.get("finalKillsPosY").getAsFloat() : var93.iskppX4();
                              var93.setFinalKillHudPosition(var104, var115);
                           }

                           if (var84.has("dragonHudRelPosX") && var84.has("dragonHudRelPosY")) {
                              var93.applyDragonHudDrag(var84.get("dragonHudRelPosX").getAsFloat(), var84.get("dragonHudRelPosY").getAsFloat());
                           } else if (var84.has("dragonHudPosX") || var84.has("dragonHudPosY")) {
                              float var105 = var84.has("dragonHudPosX") ? var84.get("dragonHudPosX").getAsFloat() : var93.getDragonHudX();
                              float var116 = var84.has("dragonHudPosY") ? var84.get("dragonHudPosY").getAsFloat() : var93.getDragonHudY();
                              var93.setDragonHudPosition(var105, var116);
                           }

                           if (var84.has("buildLimitHudRelPosX") && var84.has("buildLimitHudRelPosY")) {
                              var93.applyBuildLimitHudDrag(var84.get("buildLimitHudRelPosX").getAsFloat(), var84.get("buildLimitHudRelPosY").getAsFloat());
                           } else if (var84.has("buildLimitHudPosX") || var84.has("buildLimitHudPosY")) {
                              float var106 = var84.has("buildLimitHudPosX") ? var84.get("buildLimitHudPosX").getAsFloat() : var93.getBuildLimitHudX();
                              float var117 = var84.has("buildLimitHudPosY") ? var84.get("buildLimitHudPosY").getAsFloat() : var93.getBuildLimitHudY();
                              var93.setBuildLimitHudPosition(var106, var117);
                           }
                        } else if (var82.getName().equals("Hide Window")) {
                           HideWindow var94 = (HideWindow)var82;
                           if (var84.has("relPosX") && var84.has("relPosY")) {
                              var94.YYiq8(var84.get("relPosX").getAsFloat(), var84.get("relPosY").getAsFloat());
                           } else if (var84.has("posX") || var84.has("posY")) {
                              float var107 = var84.has("posX") ? var84.get("posX").getAsFloat() : var94.getWindowPixelX();
                              float var118 = var84.has("posY") ? var84.get("posY").getAsFloat() : var94.getWindowPixelY();
                              var94.setWindowFractionPosition(var107, var118);
                           }
                        } else if (var82.getName().equals("Notifications")) {
                           if (var84.has("relPosX") && var84.has("relPosY")) {
                              Notifications.setAnchorRatio(var84.get("relPosX").getAsFloat(), var84.get("relPosY").getAsFloat());
                           } else if (var84.has("posX") || var84.has("posY")) {
                              float var95 = var84.has("posX") ? var84.get("posX").getAsFloat() : Notifications.getScreenAnchorX();
                              float var108 = var84.has("posY") ? var84.get("posY").getAsFloat() : Notifications.getScreenAnchorY();
                              Notifications.setAnchorPositionForCurrentScreen(var95, var108);
                           }
                        } else if (!(var82 instanceof LagDetect) && !var82.getName().equals("Lag Detect") && !var82.getName().equals("Latency Alerts")) {
                           if (var82 instanceof BridgeAssist) {
                              BridgeAssist var97 = (BridgeAssist)var82;
                              if (var84.has("blocksHudRelPosX") && var84.has("blocksHudRelPosY")) {
                                 var97.setHudFraction(var84.get("blocksHudRelPosX").getAsFloat(), var84.get("blocksHudRelPosY").getAsFloat());
                              }
                           } else if (var82.getName().equals("Gui")) {
                              for (Entry var110 : var84.entrySet()) {
                                 String var119 = (String)var110.getKey();
                                 if (Module.categoryNames.contains(var119)) {
                                    String var30 = ((JsonElement)var110.getValue()).getAsString();
                                    String[] var31 = var30.split(",");
                                    float var32 = Float.parseFloat(var31[0]);
                                    float var33 = Float.parseFloat(var31[1]);
                                    boolean var34 = var31.length > 2 && Boolean.parseBoolean(var31[2]);
                                    var19.put(var119, new ConfigManager$1(var32, var33, var34));
                                 }
                              }
                           }
                        } else if (var84.has("relPosX") && var84.has("relPosY")) {
                           LagDetect.setPositionFractions(var84.get("relPosX").getAsFloat(), var84.get("relPosY").getAsFloat());
                        } else if (var84.has("posX") || var84.has("posY")) {
                           float var96 = var84.has("posX") ? var84.get("posX").getAsFloat() : LagDetect.getAlertX();
                           float var109 = var84.has("posY") ? var84.get("posY").getAsFloat() : LagDetect.unh25();
                           LagDetect.EVsgL(var96, var109);
                        }
                     } catch (RuntimeException var66) {
                        var20.add(var82.getName());
                     }

                     if (var82 instanceof Notifications) {
                        try {
                           ((Notifications)var82).applyConfigDefaults(var84);
                        } catch (RuntimeException var60) {
                           var20.add(var82.getName());
                        }
                     }

                     if (var82 instanceof ESP && var84.has("Color") && !var84.has("Default colour")) {
                        var84.add("Default colour", var84.get("Color"));
                     }

                     for (Setting var111 : var82.getSettings()) {
                        try {
                           var111.loadConfig(var84);
                        } catch (RuntimeException var59) {
                           var20.add(var82.getName() + "/" + var111.getName());
                        }
                     }

                     if (var82 instanceof ESP && !var84.has("independentColorOpacity")) {
                        try {
                           ((ESP)var82).applyOpacityToColors();
                        } catch (RuntimeException var58) {
                           var20.add(var82.getName());
                        }
                     }
                  }

                  Rendering var75 = Jade.getModuleManager().getModule(Rendering.class);
                  if (!var21 && var75 != null) {
                     var75.setExternalOutput(var22);
                  } else if (var75 != null) {
                     var75.refreshExternalRenderer();
                  }

                  try {
                     if (var16 != null) {
                        this.applyGlobalState(var16);
                     } else {
                        this.applyLegacyGlobalModules(var15);
                        var17 = true;
                     }
                  } catch (RuntimeException var57) {
                     var20.add("global state");
                  }

                  JsonObject var79 = var16 != null && var16.has("gui") && var16.get("gui").isJsonObject() ? var16.getAsJsonObject("gui") : null;
                  JsonObject var83 = (JsonObject)var15.get(Jade.getModuleManager().getModule(Gui.class));
                  if (Gui.guiStyle != null
                     && (var79 == null || !var79.has(Gui.guiStyle.getPath()))
                     && (var16 != null || var83 == null || !var83.has(Gui.guiStyle.getPath()))) {
                     JsonObject var85 = this.extractContainerState(var16);
                     if (var85 == null && var16 == null && var10.has("container") && var10.get("container").isJsonObject()) {
                        var85 = var10.getAsJsonObject("container");
                     }

                     if (var85 != null && var85.has("style")) {
                        Gui.guiStyle.setValueClamped("Dropdown".equals(var85.get("style").getAsString()) ? 1.0 : 0.0);
                        var17 = true;
                     }
                  }

                  ThemeConfig.LRTFvu(var10);

                  for (Module var100 : (java.lang.Iterable<Module>) (java.lang.Iterable<?>) (var12)) {
                     ConfigManager$0 var112 = (ConfigManager$0)var13.get(var100);
                     if (var112 != null && (!var112.enabled || var100.isEnabled() || !DangerousModules.markPendingDanger(var100)) && var112.enabled && !var100.isEnabled()) {
                        try {
                           var100.enable();
                        } catch (RuntimeException var56) {
                           var20.add(var100.getName());
                        }
                     }
                  }

                  Notifications.setNotificationsSuppressed(false);
                  DangerousModules.warnPendingDanger();
                  if (Jade.getModuleManager().getModule(Notifications.class) != null) {
                     Jade.getModuleManager().getModule(Notifications.class).Xybu(var3);
                  }

                  Jade.Grq = this.jnkYch(var3);
                  if (Jade.Grq != null) {
                     String var87 = var10.has("theme") ? var10.get("theme").getAsString() : "jade";
                     Jade.Grq.odjy(var87);
                  }

                  boolean var88 = Settings.loadGuiState.isToggled();
                  JsonObject var101 = this.extractContainerState(var16);

                  try {
                     Jade.clickGui.refreshAfterProfileLoad();
                     if (Jade.clickGui instanceof JadeClickGui) {
                        JadeClickGui var113 = (JadeClickGui)Jade.clickGui;
                        if (var88) {
                           if (var101 != null) {
                              var113.importProfileState(var101);
                           } else if (var10.has("container")) {
                              var113.importProfileState(var10.getAsJsonObject("container"));
                           } else if (var10.has("clickGuiV3")) {
                              var113.importProfileState(var10.getAsJsonObject("clickGuiV3"));
                           } else {
                              var113.applyDefaultLayout();
                           }
                        } else {
                           var113.applyDefaultLayout();
                        }
                     }

                     if (var88 && var101 == null) {
                        for (CategoryComponent var120 : ClickGui.categoryPanels) {
                           ConfigManager$1 var121 = (ConfigManager$1)var19.get(var120.category.name());
                           if (var121 != null) {
                              var120.restoreState(var121.DyClmS, var121.positionY, var121.expanded, true);
                           }
                        }
                     }
                  } catch (RuntimeException var65) {
                     var20.add("GUI layout");
                  }

                  if (Jade.Grq != null && Jade.themeManager != null) {
                     Jade.themeManager.fHn0(Jade.themeManager.dwDhpA(Jade.Grq.getThemeName()));
                  }

                  if (Jade.clickGui instanceof JadeClickGui) {
                     ((JadeClickGui)Jade.clickGui).showCategoryAfterProfileLoad(this.qhUtvV ? Category.combat : Category.profiles);
                  }

                  if (var17) {
                     this.saveGlobalState();
                  }

                  if (Jade.Grq != null) {
                     EventBus.post(new ProfileLoadEvent(Jade.Grq.getName()));
                  }

                  if (!var20.isEmpty()) {
                     ClientUtils.sendJadeMessage("Jade", "&eLoaded &f" + var3 + " &ewith skipped incompatible data: &f" + this.ZcV1(var20));
                  }

                  return;
               }

               this.UYPXwrI("load", var3);
               return;
            } catch (Exception var69) {
               Notifications.setNotificationsSuppressed(false);
               DangerousModules.LKncO();
               this.UYPXwrI("load", var3);
               return;
            }
         }
      }

      if (!var4) {
         this.UYPXwrI("load", var3);
      }
   }

   private List<Module> GENF() {
      return new ArrayList<>(Jade.getModuleManager().getModules());
   }

   private String ZcV1(Set<String> var1) {
      StringBuilder var2 = new StringBuilder();
      int var3 = 0;

      for (String var5 : var1) {
         if (var3 > 0) {
            var2.append(", ");
         }

         var2.append(var5);
         var3++;
         if (var3 == 4 && var1.size() > var3) {
            var2.append(" +").append(var1.size() - var3).append(" more");
            break;
         }
      }

      return var2.toString();
   }

   private Map<Module, ConfigManager$0> QfD61(List<Module> var1) {
      HashMap var2 = new HashMap();

      for (Module var4 : var1) {
         if (var4.canBeEnabled() && !this.isGlobalStateModule(var4)) {
            var2.put(var4, new ConfigManager$0(false, 0));
         }
      }

      return var2;
   }

   public boolean deleteProfile(String var1) {
      ConfigEntry var2 = this.jnkYch(var1);
      String var3 = var2 != null ? var2.getName() : this.normalizeProfileName(var1);
      File var4 = new File(this.file, var3 + ".json");
      if (var4.exists() && !var4.delete() && var4.exists()) {
         return false;
      } else {
         boolean var5 = var2 != null && Jade.Grq == var2;
         if (var2 != null) {
            this.profiles.remove(var2);
         }

         if (var5) {
            Jade.Grq = null;
         }

         if (this.profiles.isEmpty()) {
            ConfigEntry var6 = this.dvuld("default", 0);
            if (var6 == null) {
               return false;
            }
         } else {
            this.refreshProfileList();
            if (var5) {
               ConfigEntry var7 = this.getDefaultProfile();
               if (var7 != null) {
                  this.loadProfile(var7.getName());
               }
            }
         }

         return var2 != null;
      }
   }

   public void loadProfiles() {
      String var1 = Jade.Grq != null ? Jade.Grq.getName() : null;
      boolean var2 = Jade.Grq == null || Jade.Grq.getProfile().unmodified;
      this.profiles.clear();
      if (!this.file.exists() && !this.file.mkdirs()) {
         ClientUtils.sendJadeMessage("Jade", "&cfailed to load configs.");
      } else if (this.file.isDirectory() && this.file.canRead()) {
         List var3 = this.listProfileFiles();
         if (var3.isEmpty()) {
            this.saveProfile(new ConfigEntry("default", 0));
            var3 = this.listProfileFiles();
         }

         for (File var5 : (java.lang.Iterable<File>) (java.lang.Iterable<?>) (var3)) {
            try (FileReader var6 = new FileReader(var5)) {
               JsonParser var8 = new JsonParser();
               JsonObject var9 = var8.parse(var6).getAsJsonObject();
               String var10 = var5.getName().replace(".json", "");
               if (var9 == null) {
                  this.UYPXwrI("load", var10);
                  return;
               }

               int var11 = 0;
               if (var9.has("keybind")) {
                  var11 = var9.get("keybind").getAsInt();
               }

               ConfigEntry var12 = new ConfigEntry(var10, var11);
               String var13 = var9.has("theme") ? var9.get("theme").getAsString() : null;
               if (var13 == null || var13.trim().isEmpty()) {
                  var13 = this.resolveThemeName(var10, var9);
                  var9.addProperty("theme", var13);

                  try {
                     writeJsonFile(var5, var9);
                  } catch (Exception var25) {
                  }
               }

               var12.odjy(var13);
               this.profiles.add(var12);
            } catch (Exception var28) {
               ClientUtils.sendJadeMessage("Jade", "&cfailed to load configs.");
               var28.printStackTrace();
            }
         }

         if (var1 != null) {
            Jade.Grq = this.jnkYch(var1);
            if (Jade.Grq != null) {
               Jade.Grq.getProfile().unmodified = var2;
            }
         }

         this.refreshProfileList();
         ClientUtils.sendJadeMessage("Jade", "&f" + var3.size() + "&7 configs loaded.");
      } else {
         ClientUtils.sendJadeMessage("Jade", "&cfailed to read configs directory.");
      }
   }

   private String resolveThemeName(String var1, JsonObject var2) {
      if ("default".equalsIgnoreCase(var1)) {
         return "jade";
      } else if (Jade.themeManager != null && var2 != null && var2.has("modules")) {
         for (JsonElement var5 : var2.getAsJsonArray("modules")) {
            if (var5 != null && var5.isJsonObject()) {
               JsonObject var6 = var5.getAsJsonObject();
               if (var6.has("name") && "Arraylist".equalsIgnoreCase(var6.get("name").getAsString())) {
                  ArrayList var7 = new ArrayList();
                  Integer var8 = parseColorValue(var6, "Color");
                  Integer var9 = parseColorValue(var6, "Color 2");
                  int var10 = var6.has("Color mode") ? var6.get("Color mode").getAsInt() : 0;
                  if (var8 != null) {
                     var7.add(var8);
                  }

                  if (var10 != 0 && var9 != null && !var9.equals(var8)) {
                     var7.add(var9);
                  }

                  if (var7.isEmpty()) {
                     return "jade";
                  }

                  Theme var11 = Jade.themeManager.cNph(var7);
                  if (var11 != null) {
                     return var11.getId();
                  }

                  try {
                     return Jade.themeManager.createTheme(var1, var7).getId();
                  } catch (IOException var13) {
                     return "jade";
                  }
               }
            }
         }

         return "jade";
      } else {
         return "jade";
      }
   }

   private static Integer parseColorValue(JsonObject var0, String var1) {
      if (!var0.has(var1)) {
         return null;
      } else {
         try {
            String[] var2 = var0.get(var1).getAsString().split(",");
            if (var2.length < 3) {
               return null;
            } else {
               int var3 = Math.max(0, Math.min(255, Integer.parseInt(var2[0].trim())));
               int var4 = Math.max(0, Math.min(255, Integer.parseInt(var2[1].trim())));
               int var5 = Math.max(0, Math.min(255, Integer.parseInt(var2[2].trim())));
               return 0xFF000000 | var3 << 16 | var4 << 8 | var5;
            }
         } catch (Exception var6) {
            return null;
         }
      }
   }

   public List<File> listProfileFiles() {
      ArrayList var1 = new ArrayList();
      if (this.file.exists()) {
         File[] var2 = this.file.listFiles();
         if (var2 == null) {
            return var1;
         }

         for (File var6 : var2) {
            if (var6.isFile() && var6.getName().endsWith(".json")) {
               var1.add(var6);
            }
         }
      }

      return var1;
   }

   public ConfigEntry jnkYch(String var1) {
      for (ConfigEntry var3 : this.profiles) {
         if (var3.getName().equalsIgnoreCase(var1)) {
            return var3;
         }
      }

      return null;
   }

   public void clearActiveProfile() {
      Jade.Grq = null;
   }

   public File getProfilePrefsFile() {
      return new File(this.file.getParent(), "profile_prefs.json");
   }

   public File getGlobalStateFile() {
      return new File(this.file.getParent(), "global_state.json");
   }

   private String readStartupProfileName() {
      try {
         File var1 = this.getProfilePrefsFile();
         if (!var1.exists()) {
            return "default";
         } else {
            String var5;
            try (FileReader var2 = new FileReader(var1)) {
               JsonObject var4 = new JsonParser().parse(var2).getAsJsonObject();
               if (var4 == null || !var4.has("startupProfile")) {
                  return "default";
               }

               var5 = var4.get("startupProfile").getAsString();
            }

            return var5;
         }
      } catch (Exception var18) {
         return "default";
      }
   }

   public String yrtW() {
      return this.readStartupProfileName();
   }

   public void setStartupProfile(String var1) {
      String var2 = this.normalizeProfileName(var1);
      if (var2.isEmpty()) {
         ClientUtils.sendJadeMessage("Jade", "&cinvalid config name.");
      } else if (this.jnkYch(var2) == null) {
         ClientUtils.sendJadeMessage("Jade", "&cconfig not found: &f" + var2);
      } else {
         try {
            JsonObject var3 = new JsonObject();
            var3.addProperty("startupProfile", var2);
            writeJsonFile(this.getProfilePrefsFile(), var3);
            ClientUtils.sendJadeMessage("Jade", "&7startup config set to &f" + var2);
         } catch (Exception var4) {
            ClientUtils.sendJadeMessage("Jade", "&cfailed to save startup config preference.");
            var4.printStackTrace();
         }
      }
   }

   public void loadStartupProfile() {
      String var1 = this.readStartupProfileName();
      if (var1 == null || var1.isEmpty()) {
         var1 = "default";
      }

      this.qhUtvV = true;

      try {
         ConfigEntry var2 = this.jnkYch(var1);
         if (var2 != null) {
            this.loadProfile(var2.getName());
         } else if (!this.profiles.isEmpty()) {
            this.loadProfile(this.profiles.get(0).getName());
         }
      } finally {
         this.qhUtvV = false;
      }
   }

   public void UYPXwrI(String var1, String var2) {
      ClientUtils.sendJadeMessage("Jade", "&cfailed to " + var1 + ": &f" + var2);
   }

   public synchronized void saveGlobalState() {
      JsonObject var1 = new JsonObject();
      Module var2 = Jade.getModuleManager().getModule(Gui.class);
      Module var3 = Jade.getModuleManager().getModule(Settings.class);
      Module var4 = Jade.getModuleManager().getModule(ChatCommands.class);
      if (var2 != null) {
         var1.add("gui", VUVhC(var2));
      }

      if (var3 != null) {
         var1.add("settings", VUVhC(var3));
      }

      if (var4 != null) {
         var1.add("chatCommands", VUVhC(var4));
      }

      if (Jade.clickGui instanceof JadeClickGui) {
         var1.add("container", ((JadeClickGui)Jade.clickGui).exportProfileState());
      }

      try {
         writeJsonFile(this.getGlobalStateFile(), var1);
      } catch (Exception var6) {
         ClientUtils.sendJadeMessage("Jade", "&cfailed to save global settings.");
         var6.printStackTrace();
      }
   }

   private static void writeJsonFile(File var0, JsonElement var1) throws IOException {
      File var2 = var0.getAbsoluteFile().getParentFile();
      if (var2 != null && (var2.exists() || var2.mkdirs())) {
         File var3 = File.createTempFile(var0.getName(), ".tmp", var2);
         boolean var4 = false;

         try {
            try (FileWriter var5 = new FileWriter(var3)) {
               new GsonBuilder().setPrettyPrinting().create().toJson(var1, var5);
            }

            try {
               Files.move(var3.toPath(), var0.toPath(), StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException var24) {
               Files.move(var3.toPath(), var0.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            var4 = true;
         } finally {
            if (!var4 && var3.exists()) {
               var3.delete();
            }
         }
      } else {
         throw new IOException("cannot create config directory");
      }
   }

   private JsonObject loadGlobalState() {
      File var1 = this.getGlobalStateFile();
      if (!var1.exists()) {
         return null;
      } else {
         try (FileReader var2 = new FileReader(var1)) {
            JsonObject var4 = new JsonParser().parse(var2).getAsJsonObject();
            return var4 == null ? null : var4;
         } catch (Exception var17) {
            ClientUtils.sendJadeMessage("Jade", "&cfailed to load global settings.");
            var17.printStackTrace();
            return null;
         }
      }
   }

   private void applyGlobalState(JsonObject var1) {
      if (var1 != null) {
         this.applyModuleJsonSection(Jade.getModuleManager().getModule(Gui.class), var1, "gui");
         this.applyModuleJsonSection(Jade.getModuleManager().getModule(Settings.class), var1, "settings");
         this.applyModuleJsonSection(Jade.getModuleManager().getModule(ChatCommands.class), var1, "chatCommands");
      }
   }

   private void applyLegacyGlobalModules(Map<Module, JsonObject> var1) {
      this.applyModuleJsonFromMap(Jade.getModuleManager().getModule(Gui.class), var1, false);
      this.applyModuleJsonFromMap(Jade.getModuleManager().getModule(Settings.class), var1, true);
      this.applyModuleJsonFromMap(Jade.getModuleManager().getModule(ChatCommands.class), var1, false);
   }

   private void applyModuleJsonFromMap(Module var1, Map<Module, JsonObject> var2, boolean var3) {
      if (var1 != null) {
         JsonObject var4 = (JsonObject)var2.get(var1);
         if (var4 != null) {
            this.applyModuleSettings(var1, var4, var3);
         }
      }
   }

   private void applyModuleJsonSection(Module var1, JsonObject var2, String var3) {
      if (var1 != null && var2.has(var3) && var2.get(var3).isJsonObject()) {
         this.applyModuleSettings(var1, var2.getAsJsonObject(var3), true);
      }
   }

   private void applyModuleSettings(Module var1, JsonObject var2, boolean var3) {
      if (var1.canBeEnabled()) {
         if (var3 && var2.has("keybind")) {
            var1.setKeycode(var2.get("keybind").getAsInt());
         }

         if (var3 && var2.has("hidden")) {
            var1.setHidden(var2.get("hidden").getAsBoolean());
         }
      }

      for (Setting var5 : var1.getSettings()) {
         if (!(var1 instanceof Gui) || !ThemeConfig.isDropdownAppearanceSetting(var5)) {
            var5.loadConfig(var2);
         }
      }
   }

   private JsonObject extractContainerState(JsonObject var1) {
      if (var1 == null) {
         return null;
      } else {
         if (Jade.clickGui instanceof JadeClickGui) {
            if (var1.has("container") && var1.get("container").isJsonObject()) {
               return var1.getAsJsonObject("container");
            }

            if (var1.has("clickGuiV3") && var1.get("clickGuiV3").isJsonObject()) {
               return var1.getAsJsonObject("clickGuiV3");
            }
         }

         return null;
      }
   }

   private boolean isGlobalStateModule(Module var1) {
      return var1 instanceof Gui || var1 instanceof Settings || var1 instanceof ChatCommands;
   }

   public boolean renameProfile(ConfigEntry var1, String var2) {
      if (var1 == null) {
         ClientUtils.sendJadeMessage("Jade", "&cfailed to rename config.");
         return false;
      } else {
         String var3 = var1.getName();
         String var4 = this.normalizeProfileName(var2);
         String var5 = this.validateProfileName(var4, var3);
         if (var5 != null) {
            ClientUtils.sendJadeMessage("Jade", "&c" + var5);
            return false;
         } else if (var3.equals(var4)) {
            var1.rename(var4);
            return true;
         } else {
            File var6 = new File(this.file, var3 + ".json");
            File var7 = new File(this.file, var4 + ".json");
            if (!var6.exists()) {
               this.UYPXwrI("rename", var3);
               return false;
            } else {
               try {
                  Files.move(var6.toPath(), var7.toPath());
                  var1.rename(var4);
                  return true;
               } catch (Exception var9) {
                  this.UYPXwrI("rename", var3);
                  var9.printStackTrace();
                  return false;
               }
            }
         }
      }
   }

   private void refreshProfileList() {
      if (Jade.clickGui != null && ClickGui.categoryPanels != null) {
         for (CategoryComponent var2 : ClickGui.categoryPanels) {
            if (var2.category == Category.profiles) {
               var2.close(true);
               break;
            }
         }
      }
   }

   private ConfigEntry getDefaultProfile() {
      ConfigEntry var1 = this.jnkYch("default");
      if (var1 != null) {
         return var1;
      } else {
         return this.profiles.isEmpty() ? null : this.profiles.get(0);
      }
   }

   private String validateProfileName(String var1, String var2) {
      if (var1.isEmpty()) {
         return "Config name cannot be empty.";
      } else if (!var1.endsWith(".") && !var1.endsWith(" ")) {
         for (char var6 : var1.toCharArray()) {
            if (var6 < ' ' || this.isInvalidNameChar(var6)) {
               return "Config name contains invalid characters.";
            }
         }

         for (ConfigEntry var8 : this.profiles) {
            if (var8.getName().equalsIgnoreCase(var1) && (var2 == null || !var8.getName().equalsIgnoreCase(var2))) {
               return "Config already exists: " + var1;
            }
         }

         return null;
      } else {
         return "Config name cannot end with a space or period.";
      }
   }

   private boolean isInvalidNameChar(char var1) {
      for (char var5 : INVALID_NAME_CHARS) {
         if (var5 == var1) {
            return true;
         }
      }

      return false;
   }

   private String normalizeProfileName(String var1) {
      return var1 == null ? "" : var1.trim();
   }

   static {
      char[] var10000 = new char[9];
      var10000[0] = '\\';
      var10000[1] = '/';
      var10000[2] = ':';
      var10000[3] = '*';
      var10000[4] = '?';
      var10000[5] = (char)34;
      var10000[6] = '<';
      var10000[7] = (char)62;
      var10000[8] = (char)124;
      INVALID_NAME_CHARS = var10000;
   }
}
