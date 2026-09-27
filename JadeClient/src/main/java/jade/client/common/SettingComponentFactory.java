// Jade recovery: original class: jade.deps.eLz.KmpLY4o
package jade.client.common;

import jade.client.gui.BindComponent;
import jade.client.gui.Component;
import jade.client.module.Module;
import jade.client.setting.ItemSlotListComponent;
import jade.client.setting.BlockListSetting;
import jade.client.setting.BooleanSetting;
import jade.client.setting.CategoryListSetting;
import jade.client.setting.ColorSetting;
import jade.client.setting.DescriptionSetting;
import jade.client.setting.GroupSetting;
import jade.client.setting.ItemListEditor;
import jade.client.setting.ItemListSetting;
import jade.client.setting.ItemSlotListSetting;
import jade.client.setting.KeySetting;
import jade.client.setting.Setting;
import jade.client.setting.SliderSetting;
import jade.client.setting.StringListSetting;
import jade.client.setting.NameListSetting;
import jade.client.setting.TextSetting;
import jade.client.setting.RelationListSetting;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Map;

public final class SettingComponentFactory {
   private static final Map<Class<?>, SettingComponentFactory$1> SETTING_COMPONENT_BUILDERS = PVplN();

   private SettingComponentFactory() {
   }

   public static ArrayList<Component> ExtHpb(Module var0, ModuleComponent var1, float var2, boolean var3) {
      ArrayList var4 = new ArrayList();
      float var5 = var2;
      if (var0 != null) {
         for (Setting var7 : var0.getSettings()) {
            if (var7.visible) {
               SettingComponentFactory$0 var8 = createSettingComponent(var7, var0, var1, var5);
               if (var8 != null) {
                  var4.add(var8.component);
                  var5 += var8.rowHeight;
               }
            }
         }
      }

      if (var3) {
         var4.add(new BindComponent(var1, var5));
      }

      return var4;
   }

   public static SettingComponentFactory$2 KSMlIkm(Iterable<Component> var0) {
      SettingComponentFactory$2 var1 = new SettingComponentFactory$2();

      for (Component var3 : var0) {
         if (var3 instanceof SliderComponent) {
            SliderComponent var4 = (SliderComponent)var3;
            SettingComponentFactory$2.cddesI(var1).put(var4.sliderSetting, var4.draggingHandle);
         } else if (var3 instanceof ColorComponent) {
            ColorComponent var5 = (ColorComponent)var3;
            SettingComponentFactory$2.KrPi(var1).put(var5.colorSetting, var5.jvJ);
         }
      }

      return var1;
   }

   public static void WTEB(Iterable<Component> var0, SettingComponentFactory$2 var1) {
      for (Component var3 : var0) {
         if (var3 instanceof SliderComponent) {
            SliderComponent var4 = (SliderComponent)var3;
            Boolean var5 = (Boolean)SettingComponentFactory$2.cddesI(var1).get(var4.sliderSetting);
            if (var5 != null) {
               var4.draggingHandle = var5;
            }
         } else if (var3 instanceof ColorComponent) {
            ColorComponent var6 = (ColorComponent)var3;
            Boolean var7 = (Boolean)SettingComponentFactory$2.KrPi(var1).get(var6.colorSetting);
            if (var7 != null) {
               var6.setExpandedImmediate(var7);
            }
         }
      }
   }

   private static SettingComponentFactory$0 createSettingComponent(Setting var0, Module var1, ModuleComponent var2, float var3) {
      for (Entry var5 : SETTING_COMPONENT_BUILDERS.entrySet()) {
         if (((Class)var5.getKey()).isInstance(var0)) {
            return ((SettingComponentFactory$1)var5.getValue()).ncnAak8(var0, var1, var2, var3);
         }
      }

      return null;
   }

   private static Map<Class<?>, SettingComponentFactory$1> PVplN() {
      LinkedHashMap<Class<?>, SettingComponentFactory$1> var0 = new LinkedHashMap<>();
      var0.put(SliderSetting.class, SettingComponentFactory::tsTuosn);
      var0.put(BooleanSetting.class, SettingComponentFactory::createBooleanComponent);
      var0.put(DescriptionSetting.class, SettingComponentFactory::GUFo);
      var0.put(KeySetting.class, SettingComponentFactory::createKeyBindComponent);
      var0.put(GroupSetting.class, SettingComponentFactory::dddI);
      var0.put(ColorSetting.class, SettingComponentFactory::createColorComponent);
      var0.put(NameListSetting.class, SettingComponentFactory::createPotionListComponent);
      var0.put(ItemSlotListSetting.class, SettingComponentFactory::createItemSlotListComponent);
      var0.put(ItemListSetting.class, SettingComponentFactory::createItemListComponent);
      var0.put(RelationListSetting.class, SettingComponentFactory::createPlayerListComponent);
      var0.put(StringListSetting.class, SettingComponentFactory::YtCt7);
      var0.put(BlockListSetting.class, SettingComponentFactory::lnzK);
      var0.put(TextSetting.class, SettingComponentFactory::DDObba);
      var0.put(CategoryListSetting.class, SettingComponentFactory::tzofFj);
      return var0;
   }

   private static SettingComponentFactory$0 wrapWithFixedRowHeight(Component var0) {
      return new SettingComponentFactory$0(var0, 12.0F);
   }

   private static SettingComponentFactory$0 Ujlwl(Component var0) {
      return new SettingComponentFactory$0(var0, var0.getHeight());
   }

   private static SettingComponentFactory$0 tzofFj(Setting var0, Module var1, ModuleComponent var2, float var3) {
      return Ujlwl(new CategoryListComponent((CategoryListSetting)var0, var2, var3));
   }

   private static SettingComponentFactory$0 DDObba(Setting var0, Module var1, ModuleComponent var2, float var3) {
      return Ujlwl(new TextComponent((TextSetting)var0, var2, var3));
   }

   private static SettingComponentFactory$0 lnzK(Setting var0, Module var1, ModuleComponent var2, float var3) {
      return wrapWithFixedRowHeight(new BlockListComponent((BlockListSetting)var0, var2, var3));
   }

   private static SettingComponentFactory$0 YtCt7(Setting var0, Module var1, ModuleComponent var2, float var3) {
      return Ujlwl(new StringListComponent((StringListSetting)var0, var2, var3));
   }

   private static SettingComponentFactory$0 createPlayerListComponent(Setting var0, Module var1, ModuleComponent var2, float var3) {
      return Ujlwl(new PlayerListComponent((RelationListSetting)var0, var2, var3));
   }

   private static SettingComponentFactory$0 createItemListComponent(Setting var0, Module var1, ModuleComponent var2, float var3) {
      return wrapWithFixedRowHeight(new ItemListEditor((ItemListSetting)var0, var2, var3));
   }

   private static SettingComponentFactory$0 createItemSlotListComponent(Setting var0, Module var1, ModuleComponent var2, float var3) {
      return wrapWithFixedRowHeight(new ItemSlotListComponent((ItemSlotListSetting)var0, var2, var3));
   }

   private static SettingComponentFactory$0 createPotionListComponent(Setting var0, Module var1, ModuleComponent var2, float var3) {
      return wrapWithFixedRowHeight(new PotionListComponent((NameListSetting)var0, var2, var3));
   }

   private static SettingComponentFactory$0 createColorComponent(Setting var0, Module var1, ModuleComponent var2, float var3) {
      return wrapWithFixedRowHeight(new ColorComponent((ColorSetting)var0, var2, var3));
   }

   private static SettingComponentFactory$0 dddI(Setting var0, Module var1, ModuleComponent var2, float var3) {
      return wrapWithFixedRowHeight(new GroupComponent((GroupSetting)var0, var2, var3));
   }

   private static SettingComponentFactory$0 createKeyBindComponent(Setting var0, Module var1, ModuleComponent var2, float var3) {
      return wrapWithFixedRowHeight(new BindComponent(var2, (KeySetting)var0, var3));
   }

   private static SettingComponentFactory$0 GUFo(Setting var0, Module var1, ModuleComponent var2, float var3) {
      return wrapWithFixedRowHeight(new DescriptionComponent((DescriptionSetting)var0, var2, var3));
   }

   private static SettingComponentFactory$0 createBooleanComponent(Setting var0, Module var1, ModuleComponent var2, float var3) {
      return wrapWithFixedRowHeight(new ToggleComponent(var1, (BooleanSetting)var0, var2, var3));
   }

   private static SettingComponentFactory$0 tsTuosn(Setting var0, Module var1, ModuleComponent var2, float var3) {
      return wrapWithFixedRowHeight(new SliderComponent((SliderSetting)var0, var2, var3));
   }
}
