// Jade recovery: original class: jade.deps.eLz.U1AxNNVN9u
package jade.client.common;

import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.setting.BooleanSetting;
import jade.client.setting.DescriptionSetting;
import jade.client.setting.GroupSetting;
import jade.client.setting.MultiSelectSetting;
import jade.client.setting.Setting;
import java.util.ArrayList;
import java.util.List;

public final class ConditionSettings {
   private ConditionSettings() {
   }

   public static List<Setting> buildSettings(Module var0, ConditionSettings$0 var1) {
      List var2 = buildAliasedSettings(var0, var1);
      if (var2 != null) {
         return var2;
      } else {
         ArrayList var3 = new ArrayList();
         ArrayList var4 = var0.getSettings();

         for (int var5 = 0; var5 < var4.size(); var5++) {
            Setting var6 = (Setting)var4.get(var5);
            if (var6.visible) {
               if (var6 instanceof GroupSetting) {
                  List var11 = FCdfGwo(var4, var5, var6);
                  if (var11.size() >= 2) {
                     var3.add(var1.createMultiSelectSetting(var0, var6.getName(), var11, mrqLp(var11)));
                     var5 = findGroupEndIndex(var4, var5, var6);
                  }
               } else if (isConditionGroup(var6)) {
                  List var10 = FCdfGwo(var4, var5, var6);
                  if (var10.size() >= 2) {
                     var3.add(var1.createMultiSelectSetting(var0, var6.getName(), var10, mrqLp(var10)));
                     var5 = findGroupEndIndex(var4, var5, var6);
                  }
               } else if (!(var6 instanceof DescriptionSetting)) {
                  if (var6 instanceof BooleanSetting && looksLikeConditionSetting((BooleanSetting)var6)) {
                     ArrayList var7 = new ArrayList();

                     int var8;
                     for (var8 = var5; var8 < var4.size(); var8++) {
                        Setting var9 = (Setting)var4.get(var8);
                        if (!(var9 instanceof BooleanSetting) || !var9.visible || !looksLikeConditionSetting((BooleanSetting)var9)) {
                           break;
                        }

                        var7.add((BooleanSetting)var9);
                     }

                     if (var7.size() >= 2) {
                        var3.add(var1.createMultiSelectSetting(var0, "Conditions", var7, mrqLp(var7)));
                        var5 = var8 - 1;
                        continue;
                     }
                  }

                  var3.add(var6);
               }
            }
         }

         return var3;
      }
   }

   private static List<Setting> buildAliasedSettings(Module var0, ConditionSettings$0 var1) {
      List var2 = var0.getSettingAliases();
      return var2.isEmpty() ? null : mergeAliasedConditions(var0, var2, var1);
   }

   private static List<Setting> mergeAliasedConditions(Module var0, List<Module$2> var1, ConditionSettings$0 var2) {
      ArrayList var3 = new ArrayList();

      for (Module$2 var5 : var1) {
         if (countVisibleConditions(var0, var5.fte) >= 2) {
            for (String var9 : var5.fte) {
               var3.add(var9);
            }
         }
      }

      ArrayList var10 = new ArrayList();
      MCIgrek(var10, var0, (String[]) var3.toArray(new String[0]));

      for (Module$2 var12 : var1) {
         insertAfter(var10, var12.CsUjut, createConditionSetting(var0, var12.sourceSettingName, var12.fte, var12.conditionLabels, var2));
      }

      return var10;
   }

   private static int countVisibleConditions(Module var0, String[] var1) {
      int var2 = 0;

      for (String var6 : var1) {
         BooleanSetting var7 = KLDa(var0, var6);
         if (var7 != null && var7.visible) {
            var2++;
         }
      }

      return var2;
   }

   private static void MCIgrek(List<Setting> var0, Module var1, String... var2) {
      for (Setting var4 : var1.getSettings()) {
         if (var4.visible
            && !(var4 instanceof DescriptionSetting)
            && !(var4 instanceof GroupSetting)
            && (!(var4 instanceof BooleanSetting) || !containsNormalizedName(var2, var4.getName()) && !containsNormalizedName(var2, var4.getPath()))) {
            var0.add(var4);
         }
      }
   }

   private static MultiSelectSetting createConditionSetting(Module var0, String var1, String[] var2, String[] var3, ConditionSettings$0 var4) {
      ArrayList var5 = new ArrayList();
      ArrayList var6 = new ArrayList();

      for (int var7 = 0; var7 < var2.length; var7++) {
         String var8 = var2[var7];
         BooleanSetting var9 = KLDa(var0, var8);
         if (var9 != null && var9.visible) {
            var5.add(var9);
            var6.add(var3 != null && var7 < var3.length ? var3[var7] : null);
         }
      }

      return var4.createMultiSelectSetting(var0, var1, var5, (String[]) var6.toArray(new String[0]));
   }

   private static BooleanSetting KLDa(Module var0, String var1) {
      for (Setting var3 : var0.getSettings()) {
         if (var3 instanceof BooleanSetting && (EWgh(var3.getName()).equals(var1) || EWgh(var3.getPath()).equals(var1))) {
            return (BooleanSetting)var3;
         }
      }

      return null;
   }

   private static boolean containsNormalizedName(String[] var0, String var1) {
      String var2 = EWgh(var1);

      for (String var6 : var0) {
         if (var2.equals(var6)) {
            return true;
         }
      }

      return false;
   }

   private static void insertAfter(List<Setting> var0, String var1, Setting var2) {
      if (var2 != null) {
         int var3 = indexOfSettingByName(var0, var1);
         var0.add(var3 < 0 ? var0.size() : var3 + 1, var2);
      }
   }

   private static int indexOfSettingByName(List<Setting> var0, String var1) {
      String var2 = EWgh(var1);

      for (int var3 = 0; var3 < var0.size(); var3++) {
         if (EWgh(((Setting)var0.get(var3)).getName()).equals(var2)) {
            return var3;
         }
      }

      return -1;
   }

   private static boolean isConditionGroup(Setting var0) {
      if (!(var0 instanceof DescriptionSetting) && !(var0 instanceof GroupSetting)) {
         return false;
      } else {
         String var1 = EWgh(var0.getName());
         return var1.equals("conditions")
            || var1.equals("item conditions")
            || var1.equals("disable on")
            || var1.equals("allow while")
            || var1.equals("allow while using")
            || var1.equals("flush on");
      }
   }

   private static List<BooleanSetting> FCdfGwo(List<Setting> var0, int var1, Setting var2) {
      ArrayList var3 = new ArrayList();
      if (var2 instanceof GroupSetting) {
         GroupSetting var7 = (GroupSetting)var2;

         for (int var8 = var1 + 1; var8 < var0.size(); var8++) {
            Setting var6 = (Setting)var0.get(var8);
            if (var6 instanceof GroupSetting || var6 instanceof DescriptionSetting) {
               break;
            }

            if (var6 instanceof BooleanSetting && var6.visible && ((BooleanSetting)var6).group == var7) {
               var3.add((BooleanSetting)var6);
            }
         }

         return var3;
      } else {
         for (int var4 = var1 + 1; var4 < var0.size(); var4++) {
            Setting var5 = (Setting)var0.get(var4);
            if (var5 instanceof GroupSetting || var5 instanceof DescriptionSetting || !(var5 instanceof BooleanSetting) || !var5.visible) {
               break;
            }

            var3.add((BooleanSetting)var5);
         }

         return var3;
      }
   }

   private static int findGroupEndIndex(List<Setting> var0, int var1, Setting var2) {
      int var3 = var1;
      if (var2 instanceof GroupSetting) {
         GroupSetting var7 = (GroupSetting)var2;

         for (int var8 = var1 + 1; var8 < var0.size(); var8++) {
            Setting var6 = (Setting)var0.get(var8);
            if (var6 instanceof GroupSetting || var6 instanceof DescriptionSetting) {
               break;
            }

            if (var6 instanceof BooleanSetting && ((BooleanSetting)var6).group == var7) {
               var3 = var8;
            }
         }

         return var3;
      } else {
         for (int var4 = var1 + 1; var4 < var0.size(); var3 = var4++) {
            Setting var5 = (Setting)var0.get(var4);
            if (var5 instanceof GroupSetting || var5 instanceof DescriptionSetting || !(var5 instanceof BooleanSetting)) {
               break;
            }
         }

         return var3;
      }
   }

   private static boolean looksLikeConditionSetting(BooleanSetting var0) {
      String var1 = EWgh(var0.getName());
      return var1.contains("weapon only")
         || var1.contains("not using item")
         || var1.contains("break blocks")
         || var1.contains("breaking blocks")
         || var1.contains("creative")
         || var1.contains("inventory")
         || var1.startsWith("require ")
         || var1.startsWith("only ")
         || var1.startsWith("not ")
         || var1.startsWith("ignore ")
         || var1.startsWith("allow ")
         || var1.startsWith("disable ")
         || var1.startsWith("stop when")
         || var1.startsWith("flush on")
         || var1.endsWith(" key pressed")
         || var1.startsWith("holding ")
         || var1.startsWith("looking ")
         || var1.startsWith("not moving")
         || var1.startsWith("whilst ")
         || var1.startsWith("while ")
         || var1.startsWith("show ")
         || var1.startsWith("render ")
         || var1.endsWith(" only");
   }

   private static String[] mrqLp(List<BooleanSetting> var0) {
      String[] var1 = new String[var0.size()];

      for (int var2 = 0; var2 < var0.size(); var2++) {
         var1[var2] = ZXlS((BooleanSetting)var0.get(var2));
      }

      return var1;
   }

   private static String ZXlS(BooleanSetting var0) {
      String var1 = var0.getName();
      String var2 = EWgh(var1);
      if (var2.equals("weapon only")) {
         return "Holding weapon";
      } else if (var2.equals("not using item")) {
         return "Not using item";
      } else if (var2.equals("break blocks") || var2.equals("breaking blocks")) {
         return "Breaking blocks";
      } else if (var2.equals("disable in creative") || var2.equals("in creative")) {
         return "Not in creative";
      } else if (var2.equals("inventory") || var2.equals("in inventory")) {
         return "Inventory open";
      } else if (var2.equals("ignore teammates")) {
         return "Ignore teammates";
      } else if (var2.equals("hide teammates")) {
         return "Hide teammates";
      } else if (var2.equals("require left mouse")) {
         return "Left mouse held";
      } else if (var2.equals("require right mouse")) {
         return "Right mouse held";
      } else if (var2.equals("require mouse")) {
         return "Mouse held";
      } else if (var2.startsWith("require ")) {
         return var1.substring(8);
      } else if (var2.startsWith("flush on ")) {
         return var1.substring("Flush on ".length());
      } else if (var2.startsWith("disable on ")) {
         return var1.substring("Disable on ".length());
      } else if (var2.startsWith("stop when ")) {
         return "Not " + var1.substring("Stop when ".length());
      } else if (var2.startsWith("only while ")) {
         return var1.substring(11);
      } else if (var2.startsWith("only whilst ")) {
         return var1.substring(12);
      } else if (var2.startsWith("only ")) {
         return var1.substring(5);
      } else if (var2.startsWith("not ")) {
         return var1.substring(4);
      } else if (var2.startsWith("ignore ")) {
         return var1.substring(7);
      } else if (var2.startsWith("allow ")) {
         return var1.substring(6);
      } else if (var2.startsWith("disable ")) {
         return var1.substring(8);
      } else {
         return var2.endsWith(" only") && var1.length() > 5 ? var1.substring(0, var1.length() - 5) : var1;
      }
   }

   private static String EWgh(String var0) {
      return var0 == null ? "" : var0.trim().toLowerCase();
   }
}
