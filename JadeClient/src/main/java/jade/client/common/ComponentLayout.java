// Jade recovery: original class: jade.deps.eLz.cfMFJBK3s
package jade.client.common;

import jade.client.gui.BindComponent;
import jade.client.gui.Component;
import jade.client.setting.ItemListComponent;
import jade.client.setting.SettingComponent;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public final class ComponentLayout {
   private static final float Pth = 6.0F;
   private final Map<Component, GroupComponent> mfXvz = new IdentityHashMap<>();

   public void rebuildGroupMap(List<Component> var1) {
      this.mfXvz.clear();
      HashMap var2 = new HashMap();

      for (Component var4 : var1) {
         if (var4 instanceof GroupComponent) {
            GroupComponent var5 = (GroupComponent)var4;
            var2.put(var5.groupSetting.getName(), var5);
         }
      }

      for (Component var8 : var1) {
         String var9 = ksLnd(var8);
         GroupComponent var6 = (GroupComponent)var2.get(var9);
         if (var6 != null) {
            this.mfXvz.put(var8, var6);
         }
      }
   }

   public GroupComponent uiyP(Component var1) {
      return this.mfXvz.get(var1);
   }

   public boolean isComponentVisible(Component var1) {
      if (!var1.ejbAn()) {
         return false;
      } else {
         GroupComponent var2 = this.uiyP(var1);
         return var2 == null || var2.YVkqQ() > 0.0F;
      }
   }

   public float fBnyl(Component var1) {
      if (var1 instanceof SliderComponent) {
         return 16.0F;
      } else if (var1 instanceof ColorComponent) {
         ColorComponent var2 = (ColorComponent)var1;
         return 12.0F + (var2.getExpandedHeight() - 12.0F) * var2.hDqm53();
      } else {
         return !(var1 instanceof ItemListComponent)
               && !(var1 instanceof TextComponent)
               && !(var1 instanceof PlayerListComponent)
               && !(var1 instanceof StringListComponent)
               && !(var1 instanceof CategoryListComponent)
            ? 12.0F
            : var1.getHeight();
      }
   }

   public float getVisibleHeight(Component var1) {
      if (!var1.ejbAn()) {
         return 0.0F;
      } else {
         GroupComponent var2 = this.uiyP(var1);
         return this.fBnyl(var1) * (var2 == null ? 1.0F : var2.YVkqQ());
      }
   }

   public void applyGroupOffset(Component var1) {
      float var2 = this.uiyP(var1) == null ? 0.0F : 6.0F;
      if (var1 instanceof SliderComponent) {
         ((SliderComponent)var1).indentX = var2;
      } else if (var1 instanceof ToggleComponent) {
         ((ToggleComponent)var1).labelOffsetX = var2;
      } else if (var1 instanceof BindComponent && ((BindComponent)var1).keySetting != null) {
         ((BindComponent)var1).ecma6 = var2;
      } else if (var1 instanceof ColorComponent) {
         ((ColorComponent)var1).previewWidth = var2;
      } else if (var1 instanceof SettingComponent) {
         ((SettingComponent)var1).setGroupIndent(var2);
      }
   }

   public float computeTotalHeight(List<Component> var1, float var2, boolean var3) {
      float var4 = var2;

      for (Component var6 : var1) {
         if (!var3) {
            var4 += this.getVisibleHeight(var6);
         } else if (var6.ejbAn()) {
            GroupComponent var7 = this.uiyP(var6);
            float var8 = var7 == null ? 1.0F : var7.YVkqQ();
            if (var7 != null && var7.zeF) {
               var8 = Math.max(var8, 1.0F);
            }

            var4 += this.fBnyl(var6) * var8;
         }
      }

      return var4;
   }

   public void nd404(List<Component> var1, float var2) {
      float[] var3 = new float[]{var2};
      GroupedListWalker.forEachGroup(var1, ComponentLayout::isComponentEnabled, ComponentLayout::SBAHmS, this::uiyP, (recoveredArg0, recoveredArg1) -> this.layoutComponent(var3, recoveredArg0, recoveredArg1));
   }

   private static String ksLnd(Component var0) {
      if (var0 instanceof SliderComponent) {
         SliderComponent var4 = (SliderComponent)var0;
         return var4.sliderSetting.group == null ? "" : var4.sliderSetting.group.getName();
      } else if (var0 instanceof ToggleComponent) {
         ToggleComponent var3 = (ToggleComponent)var0;
         return var3.booleanSetting.group == null ? "" : var3.booleanSetting.group.getName();
      } else if (!(var0 instanceof BindComponent)) {
         if (var0 instanceof ColorComponent) {
            ColorComponent var2 = (ColorComponent)var0;
            return var2.colorSetting.groupSetting == null ? "" : var2.colorSetting.groupSetting.getName();
         } else {
            return var0 instanceof SettingComponent ? ((SettingComponent)var0).getGroupName() : "";
         }
      } else {
         BindComponent var1 = (BindComponent)var0;
         return var1.keySetting != null && var1.keySetting.groupSetting != null ? var1.keySetting.groupSetting.getName() : "";
      }
   }

   private void layoutComponent(float[] var1, Component var2, List var3) {
      if (!(var2 instanceof GroupComponent)) {
         var2.setY(var1[0]);
         this.applyGroupOffset(var2);
         var1[0] += this.fBnyl(var2);
      } else {
         float var4 = ((GroupComponent)var2).YVkqQ();
         var2.setY(var1[0]);
         float var5 = var1[0];
         float var6 = var5 + this.fBnyl(var2);
         float var7 = 0.0F;

         for (Component var9 : (java.lang.Iterable<Component>) (java.lang.Iterable<?>) (var3)) {
            if (var9.ejbAn()) {
               var9.setY(var6);
               float var10 = this.fBnyl(var9);
               var6 += var10;
               var7 += var10;
               this.applyGroupOffset(var9);
            }
         }

         var1[0] = var5 + this.fBnyl(var2) + var7 * var4;
      }
   }

   private static boolean SBAHmS(Component var0) {
      return var0 instanceof GroupComponent;
   }

   private static boolean isComponentEnabled(Object var0) {
      return ((Component)var0).ejbAn();
   }
}
