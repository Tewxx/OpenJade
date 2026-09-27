// Jade recovery: original class: jade.deps.eLz.qQ8P7JMy3
package jade.client.common;

import jade.client.Jade;
import jade.client.core.ConfigProfile;
import jade.client.core.ThemeConfig;
import jade.client.core.Theme;
import jade.client.event.RenderTickEvent;
import jade.client.gui.AnimatedFloat;
import jade.client.gui.HudEditorScreen;
import jade.client.gui.TextField;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.client.ChatCommands;
import jade.client.module.client.Gui;
import jade.client.module.client.Relationships;
import jade.client.module.client.Rendering;
import jade.client.module.client.Settings;
import jade.client.module.profiles.ProfileManager;
import jade.client.module.render.ItemESP;
import jade.client.module.render.blockesp.BlockEspParser$1;
import jade.client.module.render.blockesp.BlockEspParser;
import jade.client.module.render.nametags.WarningIconRenderer;
import jade.client.setting.BlockColorListSetting;
import jade.client.setting.BlockListSetting;
import jade.client.setting.BooleanSetting;
import jade.client.setting.CategoryListSetting;
import jade.client.setting.ColorSetting;
import jade.client.setting.DescriptionSetting;
import jade.client.setting.GroupSetting;
import jade.client.setting.ItemColorListSetting;
import jade.client.setting.ItemListSetting;
import jade.client.setting.ItemSlotListSetting;
import jade.client.setting.KeySetting;
import jade.client.setting.MultiSelectSetting;
import jade.client.setting.Setting;
import jade.client.setting.SliderSetting;
import jade.client.setting.StringListSetting;
import jade.client.setting.NameListSetting;
import jade.client.setting.TextSetting;
import jade.client.setting.RelationListSetting;
import jade.deps.gson.Gson;
import jade.deps.gson.GsonBuilder;
import jade.deps.gson.JsonElement;
import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonParser;
import jade.deps.loader107.InjectionPaths;
import jade.deps.loader107.SubscriptionState;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class JadeClickGui extends RelationsClickGui {
   private final DropdownPanelManager dropdownLayoutState = new DropdownPanelManager();
   private final BlurFramebufferCapture dropdownFadeRenderer = new BlurFramebufferCapture();
   private final GuiAnimationRegistry themesDropdownAnimation = new GuiAnimationRegistry();
   private final SettingHoverAnimations hoverAnimations = new SettingHoverAnimations();
   private final Map<Object, SmoothedFloat> transitionCache = new IdentityHashMap<>();
   private final Map<Module, GuiRect> moduleExpandButtonRects = new IdentityHashMap<>();
   private boolean Hto;
   private boolean fadeOutPending;
   private long FgL;
   private float openFadeProgress;
   public final LGHsOECU7 gvH = new LGHsOECU7();
   private GuiRect activeSliderTrackRect;
   private float Osi = 1.35F;
   private DropdownLayoutManager dropdownClientRenderer;
   private ResourceLocation checkerboardTexture;
   private ResourceLocation hueBarTexture;
   private final Map<ColorSetting, JadeClickGui$3> colorPreviewTextures = new IdentityHashMap<>();
   private static final int DEFAULT_CONTAINER_WIDTH = 540;
   private static final int UKOizn = 410;
   private static final int DEFAULT_SIDEBAR_WIDTH = 116;
   private static final int MIN_CONTAINER_WIDTH = 430;
   private static final int MIN_CONTAINER_HEIGHT = 270;
   private static final int MIN_SIDEBAR_WIDTH = 92;
   private static final int MAX_SIDEBAR_WIDTH = 170;
   private static final int RESIZE_HANDLE_MARGIN = 6;
   private static final int DRAG_MOVE = 10;
   private static final int xBkt = 11;
   private static final int DRAG_TOP = 12;
   private static final int DRAG_BOTTOM = 13;
   private static final int DRAG_TOP_RIGHT = 14;
   private static final int DRAG_BOTTOM_RIGHT = 15;
   private static final int DRAG_TOP_LEFT = 16;
   private static final int DRAG_BOTTOM_LEFT = 17;
   private static final int DRAG_LEFT_EDGE = 18;
   private static final int PAD = 8;
   private static final int MODULE_ROW_GAP = 5;
   private static final int MODULE_ROW_HEIGHT = 31;
   private static final int CATEGORY_ROW_PITCH = 27;
   private static final int SETTING_ROW_GAP = 2;
   private static final int DROPDOWN_RESERVED_HEIGHT = 32;
   private static final float TEXT_SCALE = 1.1F;
   private static final float PKic = 1.0F;
   private static final float ANIMATION_STEP = 0.11F;
   private static final int MIN_STATUS_PILL_WIDTH = 140;
   private static final int IdT = 24;
   private static final int STATUS_PILL_Y = 9;
   private static final String[] GREETINGS = new String[]{"Hey", "Hello", "Hi", "Welcome", "Shalom", "Hola", "Bonjour", "Ciao", "Ni Hao"};
   private static final int vawbh = -15658735;
   private static final int BG_WINDOW = -15066598;
   private static final int BG_PILL = -15066598;
   private static final int BG_PILL_HOVER = -14671840;
   private static final int BG_INPUT = -15658735;
   private static final int BG_ROW_DISABLED = -16053493;
   private static final int UQoa7 = 1711276032;
   private static final int DIVIDER_COLOR = 352321535;
   private static final int TXT = -986896;
   private static final int TEXT_ON_ACCENT = -16448251;
   private static final int TXT_DIM = -6645094;
   private static final int TXT_MUTE = -9803158;
   private static final int ICON_MUTED = -4671304;
   private static final String CZvN = "/assets/jade/textures/gui/edit.png";
   private static final int CONFIG_DIALOG_NONE = 0;
   private static final int CONFIG_DIALOG_SAVE = 1;
   private static final int vetn9 = 2;
   private static final int vovx0 = 3;
   private Category currentCategory = Category.combat;
   private final Set<Module> IyqlY = Collections.newSetFromMap(new IdentityHashMap<>());
   private final Set<SliderSetting> expandedModeSliders = Collections.newSetFromMap(new IdentityHashMap<>());
   private final Set<ColorSetting> colorPickerOpenSettings = Collections.newSetFromMap(new IdentityHashMap<>());
   private final Map<TextSetting, TextField> textSettingFields = new IdentityHashMap<>();
   private final Map<StringListSetting, TextField> stringListFields = new IdentityHashMap<>();
   private final Map<BlockListSetting, TextField> Mj7 = new IdentityHashMap<>();
   private final Map<Category, AnimatedFloat> categoryScrollAnimations = new EnumMap<>(Category.class);
   private final Map<Module, Float> moduleExpandProgress = new IdentityHashMap<>();
   private final Map<Object, Float> animationCache = new IdentityHashMap<>();
   private final Map<SliderSetting, Float> YnY6 = new IdentityHashMap<>();
   private final Map<SliderSetting, Double> animatedSliderValues = new IdentityHashMap<>();
   private final Map<Module, Object> IeoW = new IdentityHashMap<>();
   private final Map<Category, Object> topScrollFadeKeys = new EnumMap<>(Category.class);
   private final Map<Category, Object> elmSla = new EnumMap<>(Category.class);
   private final Map<Category, Object> WVkE = new EnumMap<>(Category.class);
   private final Object WHb = new Object();
   private final VectorIcons kwGva = new VectorIcons();
   private final Object pgpJ = new Object();
   private final Object powerButtonHoverKey = new Object();
   private final Object searchFocusAnimationKey = new Object();
   private final Object Aej = new Object();
   private final AnimatedFloat RClfTp = new AnimatedFloat(160L);
   private final AnimatedFloat configListScroll = new AnimatedFloat(160L);
   private final AnimatedFloat themesScroll = new AnimatedFloat(160L);
   private final AnimatedFloat sidebarScrollAnimation = new AnimatedFloat(160L);
   private String searchText = "";
   private boolean searchFocused;
   private boolean searchScopedToCategory;
   private boolean Namm5;
   private int trwid = 540;
   private int fAxth0 = 410;
   private int sidebarWidth = 116;
   private int ISaB;
   private int dragStartX;
   private int OQmx;
   private int dragStartWidth;
   private int dragStartHeight;
   private int enX;
   private int Evqe;
   private int Vbm;
   private boolean movingWindow;
   private boolean layoutFileLoaded;
   private boolean AMrCa;
   private int moveStartX;
   private int wx4;
   private int containerOffsetAtDragStart;
   private int moveStartY;
   private final Map<Module, GuiRect> umR = new IdentityHashMap<>();
   private final Map<Module, GuiRect> moduleToggleRects = new IdentityHashMap<>();
   private final Map<Module, GuiRect> moduleBindButtons = new IdentityHashMap<>();
   private final Map<Module, GuiRect> moduleHiddenToggleRects = new IdentityHashMap<>();
   private final Map<SliderSetting, GuiRect> UKsT = new IdentityHashMap<>();
   private final Map<SliderSetting, GuiRect> Ei2 = new IdentityHashMap<>();
   private final Map<SliderSetting, TextField> IDSVr = new IdentityHashMap<>();
   private final Map<SliderSetting, List<OptionRowHitbox>> modeSliderOptionRects = new IdentityHashMap<>();
   private final Map<BooleanSetting, GuiRect> NCOdbl = new IdentityHashMap<>();
   private final Map<MultiSelectSetting, GuiRect> multiSelectHeaderRects = new IdentityHashMap<>();
   private final Map<BooleanSetting, GuiRect> eJ7 = new IdentityHashMap<>();
   private final Map<String, MultiSelectSetting> multiSelectCache = new LinkedHashMap<>();
   private final Map<KeySetting, GuiRect> keySettingButtons = new IdentityHashMap<>();
   private final Map<ColorSetting, ColorPickerBounds> colorPickerRects = new IdentityHashMap<>();
   private final Map<ColorSetting, GuiRect> cr2 = new IdentityHashMap<>();
   private final Map<ColorSetting, GuiRect> colorValueFieldRects = new IdentityHashMap<>();
   private final Map<ColorSetting, TextField> tvH = new IdentityHashMap<>();
   private GuiRect XYIJhz;
   private final Map<TextSetting, GuiRect> textSettingFieldRects = new IdentityHashMap<>();
   private final Map<StringListSetting, GuiRect> ofU = new IdentityHashMap<>();
   private final Map<BlockListSetting, GuiRect> CNc = new IdentityHashMap<>();
   private final Map<udUfwO, GuiRect> listEntryRemoveButtons = new LinkedHashMap<>();
   private final Map<SettingSearchEntry, GuiRect> ONuyuP = new LinkedHashMap<>();
   private final Map<zyojmoh5, GuiRect> cgw = new LinkedHashMap<>();
   private final Map<zyojmoh5, GuiRect> colorChipRightClickRects = new LinkedHashMap<>();
   private final Map<ConfigProfile, GuiRect> configRowRects = new IdentityHashMap<>();
   private final Map<ConfigProfile, GuiRect> nam = new IdentityHashMap<>();
   private final Map<ConfigProfile, GuiRect> configSaveButtons = new IdentityHashMap<>();
   private final Map<ConfigProfile, GuiRect> PeGs9 = new IdentityHashMap<>();
   private final Map<ConfigProfile, GuiRect> configBindButtons = new IdentityHashMap<>();
   private final Map<CategoryEntryKey, GuiRect> categorySlotAddButtons = new LinkedHashMap<>();
   private final Map<CategoryListItemKey, GuiRect> categorySlotChipRects = new LinkedHashMap<>();
   private final Map<CategoryListOptionKey, GuiRect> categoryPickerOptionRects = new LinkedHashMap<>();
   private CategoryListSetting OmaV;
   private int openCategorySlotIndex = -1;
   private Module bindingModule;
   private ConfigProfile bindingConfigProfile;
   private KeySetting bindingKeySetting;
   private int yKrqJ9 = 0;
   private SliderSetting draggingSlider;
   private ColorSetting BvMq;
   private final ProfileManager profilesModule = new ProfileManager();
   private int colorPickerDragMode;
   private SliderSetting editingSlider;
   private ColorSetting gdj;
   private TextSetting editingTextSetting;
   private StringListSetting editingStringList;
   private BlockListSetting activeBlockList;
   private long openTimeMillis = -1L;
   private boolean WGlw = true;
   private String greeting = GREETINGS[0];
   private GuiRect createConfigButtonRect;
   private GuiRect VwaR;
   private GuiRect openFolderButton;
   private GuiRect Tkzu;
   private GuiRect createConfigNameFieldRect;
   private GuiRect createConfigConfirmButton;
   private GuiRect createConfigCancelButton;
   private GuiRect configActionDialogRect;
   private GuiRect dAg;
   private GuiRect MeGm;
   private GuiRect uninjectDialogRect;
   private GuiRect uninjectCloseButton;
   private GuiRect uninjectConfirmButton;
   private boolean createConfigDialogOpen;
   private int configDialogMode;
   private ConfigProfile EVHD;
   private boolean uninjectDialogOpen;
   private boolean uninjectInProgress;
   private boolean zzd;
   private long uninjectHoldStart;
   private String uninjectErrorMessage;
   private static final long UNINJECT_HOLD_MILLIS = 2000L;
   private String duhp5 = "";
   private ConfigProfile yzh;
   private String configRenameInput = "";
   private ConfigProfile lastClickedConfig;
   private long lastConfigClickTime;
   private GuiRect QLh;
   private GuiRect GKCt;
   private GuiRect Jiwn40;
   private final AnimatedFloat blockSearchScroll = new AnimatedFloat(160L);
   private BlockListSetting NSq4;
   private String editingBlockEntry;
   private boolean editingSecondaryColor;
   private int fHvex;
   private float entryColorHue;
   private float entryColorSaturation;
   private float entryColorBrightness;
   private GuiRect NSTby6;
   private GuiRect SaV;
   private GuiRect entryColorHueBarRect;
   private GuiRect entryColorHexFieldRect;
   private final TextField entryColorHexField = new TextField("#RRGGBB", 9, 1.1F);
   private boolean editingEntryColorHex;
   private final Map<Theme, GuiRect> HOf = new LinkedHashMap<>();
   private final Map<Theme, GuiRect> themeEditButtons = new LinkedHashMap<>();
   private final Map<Theme, GuiRect> themeDeleteButtons = new LinkedHashMap<>();
   private final Map<Theme, Float> Er2 = new IdentityHashMap<>();
   private final Map<Theme, Long> UNVY = new IdentityHashMap<>();
   private final List<GuiRect> Uginwv = new ArrayList<>();
   private GuiRect themesNewButton;
   private GuiRect themesFolderButton;
   private final List<GuiRect> addPaletteColorButtons = new ArrayList<>();
   private GuiRect zeL;
   private GuiRect paletteEditorPanelRect;
   private GuiRect paletteEditorCloseButton;
   private GuiRect mV1;
   private GuiRect MAf;
   private GuiRect DVRCt;
   private GuiRect hexInputButtonRect;
   private GuiRect themeNameDialogRect;
   private GuiRect themeNameFieldRect;
   private GuiRect themeNameConfirmButton;
   private GuiRect dQuikX;
   private boolean themeEditorOpen;
   private boolean themeNameDialogOpen;
   private Theme editingTheme;
   private final List<Integer> paletteColors = new ArrayList<>();
   private int selectedPaletteIndex;
   private int ABCZe;
   private boolean nwE5;
   private boolean editingHexColor;
   private boolean Zrv;
   private String hexColorInput = "#FFFFFF";
   private boolean paletteDirty;
   private Theme pendingThemeEdit;
   private GuiRect IVYgm;
   private GuiRect discardThemeConfirmButton;
   private GuiRect discardThemeCancelButton;
   private float paletteHue;
   private float iOc3;
   private float paletteBrightness;
   private String themeNameInput = "";
   private Theme Metj;
   private GuiRect deleteThemeDialogRect;
   private GuiRect vgrxy;
   private GuiRect Vul;
   private static final int SETTING_ROW_HEIGHT = 22;
   private static final int ttiz = 2;
   private static final int CATEGORY_OPTION_ROW_HEIGHT = 18;
   private static final int CATEGORY_OPTION_ROWS = (CategoryListSetting.keM.length + 2 - 1) / 2 + 1;
   private static final int ovbT = CATEGORY_OPTION_ROWS * 18 + 6;
   private static final int BYJR = 36;
   private static final int SMALL_ICON_SIZE = 14;
   private static final int OZoRi = 18;
   private static final int coHt8 = 4;

   @Override
   public boolean isTypingInTextInput() {
      return this.themeNameDialogOpen
         || this.editingHexColor
         || this.searchFocused
         || this.editingSlider != null
         || this.gdj != null
         || this.editingEntryColorHex
         || this.editingTextSetting != null
         || this.editingStringList != null
         || this.activeBlockList != null
         || this.isTypingInRelationshipInput()
         || super.isTypingInTextInput();
   }

   public JsonObject exportProfileState() {
      JsonObject var1 = new JsonObject();
      var1.addProperty("selectedCategory", this.currentCategory.name());
      var1.addProperty("dropdownScaleBaseline", 135);
      var1.add("dropdownLayout", this.dropdownLayoutState.ZZYR());
      var1.addProperty("accentHue", Gui.getAccentHue());
      var1.addProperty("accentSat", Gui.zFsde8());
      var1.addProperty("accentBri", Gui.getAccentBrightness());
      var1.addProperty("roundingPct", Gui.getRoundingPercent());
      var1.addProperty("gradientFill", Gui.GWPe());
      var1.addProperty("capitalized", Gui.isCapitalizeTextEnabled());
      var1.addProperty("clickGuiFont", Gui.ISjhxoi());

      for (Category var5 : Category.values()) {
         var1.addProperty("scroll_" + var5.name(), this.LYepv7(this.categoryScrollAnimations, var5).getTargetValue());
      }

      for (Module var7 : this.getAllModules()) {
         var1.addProperty("expanded_" + var7.getCategory().name() + "_" + var7.getName(), this.IyqlY.contains(var7));
      }

      return var1;
   }

   public void importProfileState(JsonObject var1) {
      this.TBHHa8();
      if (var1 == null) {
         this.applyDefaultLayout();
      } else {
         if (var1.has("selectedCategory")) {
            try {
               this.currentCategory = Category.valueOf(var1.get("selectedCategory").getAsString());
            } catch (Exception var9) {
               this.currentCategory = Category.combat;
            }
         }

         this.refreshProfileDependentState();
         if (Gui.JzraV3() && var1.has("style") && !var1.has("dropdownScaleBaseline") && Gui.guiScale != null) {
            Gui.guiScale.setValueClamped(Gui.getGuiScale() / 1.35F);
         }

         if (var1.has("dropdownLayout") && var1.get("dropdownLayout").isJsonObject()) {
            this.dropdownLayoutState.WoR0(var1.getAsJsonObject("dropdownLayout"));
         }

         if (var1.has("accentHue") && Gui.accent != null) {
            Gui.accent.oAej(var1.get("accentHue").getAsInt());
         }

         if (var1.has("accentSat") && Gui.accent != null) {
            Gui.accent.setSaturation(var1.get("accentSat").getAsFloat());
         }

         if (var1.has("accentBri") && Gui.accent != null) {
            Gui.accent.setBrightness(var1.get("accentBri").getAsFloat());
         }

         if (var1.has("roundingPct") && Gui.rounding != null) {
            Gui.rounding.setValueClamped(var1.get("roundingPct").getAsFloat());
         }

         if (var1.has("gradientFill") && Gui.gradientTopbar != null && Gui.gradientTopbar.isToggled() != var1.get("gradientFill").getAsBoolean()) {
            Gui.gradientTopbar.toggle();
         }

         if (var1.has("capitalized") && Gui.capitalizeText != null && Gui.capitalizeText.isToggled() != var1.get("capitalized").getAsBoolean()) {
            Gui.capitalizeText.toggle();
         }

         if (var1.has("clickGuiFont")) {
            Gui.selectClickGuiFont(var1.get("clickGuiFont").getAsString());
         }

         if (!this.AMrCa && this.eAypV(var1)) {
            this.MFFFyaw(var1);
            this.WQeLm();
         }

         this.IyqlY.clear();

         for (Module var3 : this.getAllModules()) {
            String var4 = "expanded_" + var3.getCategory().name() + "_" + var3.getName();
            if (var1.has(var4) && var1.get(var4).getAsBoolean()) {
               this.IyqlY.add(var3);
            }
         }

         for (Category var5 : Category.values()) {
            String var6 = "scroll_" + var5.name();
            String var7 = "colOneScroll_" + var5.name();
            float var8 = var1.has(var6) ? var1.get(var6).getAsFloat() : (var1.has(var7) ? var1.get(var7).getAsFloat() : 0.0F);
            this.LYepv7(this.categoryScrollAnimations, var5).snapTo(var8);
         }
      }
   }

   private boolean eAypV(JsonObject var1) {
      return var1 != null
         && (
            var1.has("containerW")
               || var1.has("containerH")
               || var1.has("containerX")
               || var1.has("containerY")
               || var1.has("sidebarW")
               || var1.has("sidebarScroll")
         );
   }

   private void MFFFyaw(JsonObject var1) {
      if (var1 != null) {
         if (var1.has("containerW")) {
            this.trwid = Math.max(430, var1.get("containerW").getAsInt());
         }

         if (var1.has("containerH")) {
            this.fAxth0 = Math.max(270, var1.get("containerH").getAsInt());
         }

         if (var1.has("containerX")) {
            this.Evqe = var1.get("containerX").getAsInt();
         }

         if (var1.has("containerY")) {
            this.Vbm = var1.get("containerY").getAsInt();
         }

         if (var1.has("sidebarW")) {
            this.sidebarWidth = Math.max(92, Math.min(170, var1.get("sidebarW").getAsInt()));
         }

         if (var1.has("sidebarScroll")) {
            this.sidebarScrollAnimation.snapTo(var1.get("sidebarScroll").getAsFloat());
         }

         if (var1.has("dropdownLayout") && var1.get("dropdownLayout").isJsonObject()) {
            this.dropdownLayoutState.WoR0(var1.getAsJsonObject("dropdownLayout"));
         }
      }
   }

   private JsonObject serializeContainerLayout() {
      JsonObject var1 = new JsonObject();
      var1.addProperty("containerW", this.trwid);
      var1.addProperty("containerH", this.fAxth0);
      var1.addProperty("containerX", this.Evqe);
      var1.addProperty("containerY", this.Vbm);
      var1.addProperty("sidebarW", this.sidebarWidth);
      var1.addProperty("sidebarScroll", this.sidebarScrollAnimation.getTargetValue());
      var1.add("dropdownLayout", this.dropdownLayoutState.ZZYR());
      return var1;
   }

   private void TBHHa8() {
      if (!this.layoutFileLoaded) {
         this.layoutFileLoaded = true;
         File var1 = this.VAjSin9();
         if (var1 != null && var1.isFile()) {
            try (FileReader var2 = new FileReader(var1)) {
               JsonObject var4 = new JsonParser().parse(var2).getAsJsonObject();
               this.MFFFyaw(var4);
               this.AMrCa = true;
            } catch (Exception var15) {
               this.AMrCa = false;
            }
         }
      }
   }

   private void WQeLm() {
      this.layoutFileLoaded = true;
      File var1 = this.VAjSin9();
      if (var1 != null) {
         try (FileWriter var2 = new FileWriter(var1)) {
            Gson var4 = new GsonBuilder().setPrettyPrinting().create();
            var4.toJson((JsonElement)this.serializeContainerLayout(), var2);
            this.AMrCa = true;
         } catch (Exception var15) {
         }
      }
   }

   private File VAjSin9() {
      Minecraft var1 = Minecraft.getMinecraft();
      if (var1 != null && var1.mcDataDir != null) {
         File var2 = InjectionPaths.dataDirectory(var1.mcDataDir);
         return !var2.exists() && !var2.mkdirs() ? null : new File(var2, "container_layout.json");
      } else {
         return null;
      }
   }

   public void applyDefaultLayout() {
      this.currentCategory = Category.combat;
      this.IyqlY.clear();
      this.expandedModeSliders.clear();
      this.colorPickerOpenSettings.clear();
      this.categoryScrollAnimations.clear();
      this.moduleExpandProgress.clear();
      this.YnY6.clear();
      this.animatedSliderValues.clear();
      if (Jade.themeManager != null && Jade.Grq != null) {
         Jade.themeManager.fHn0(Jade.themeManager.dwDhpA(Jade.Grq.getThemeName()));
      } else if (Gui.accent != null) {
         Gui.accent.setRgb(26, 168, 121);
      }

      if (Gui.rounding != null) {
         Gui.rounding.setValueClamped(70.0);
      }

      if (Gui.gradientTopbar != null && Gui.gradientTopbar.isToggled()) {
         Gui.gradientTopbar.toggle();
      }

      if (Gui.capitalizeText != null && Gui.capitalizeText.isToggled()) {
         Gui.capitalizeText.toggle();
      }

      Gui.selectClickGuiFont("Modern");
      this.searchText = "";
      this.searchFocused = false;
      this.searchScopedToCategory = false;
      this.Namm5 = false;
      this.configListScroll.snapTo(0.0F);
      this.themesScroll.snapTo(0.0F);
   }

   private void refreshProfileDependentState() {
   }

   @Override
   public void initGui() {
      if (this.fadeOutPending) {
         this.finishClosingFade();
      }

      this.TBHHa8();
      this.refreshAfterProfileLoad();
      this.Hto = Gui.JzraV3();
      Keyboard.enableRepeatEvents(true);
      if (this.WGlw) {
         this.fadeOutPending = false;
         this.openTimeMillis = System.currentTimeMillis();
         this.ESTnlo();
         this.WGlw = false;
      }
   }

   @Override
   public void setWorldAndResolution(Minecraft var1, int var2, int var3) {
      this.mc = var1;
      this.itemRender = var1.getRenderItem();
      this.fontRendererObj = var1.fontRendererObj;
      ScaledResolution var4 = new ScaledResolution(var1);
      this.width = var4.getScaledWidth();
      this.height = var4.getScaledHeight();
      this.buttonList.clear();
      this.initGui();
   }

   @Override
   public void refreshAfterProfileLoad() {
      ScaledResolution var1 = new ScaledResolution(Minecraft.getMinecraft());
      if (this.draggingSlider != Gui.guiScale && this.editingSlider != Gui.guiScale) {
         this.Osi = Gui.getGuiScale() * 1.35F;
      }

      this.width = Gui.JzraV3() ? (int)Math.ceil((double)this.mc.displayWidth / this.Osi) : var1.getScaledWidth();
      this.height = Gui.JzraV3() ? (int)Math.ceil((double)this.mc.displayHeight / this.Osi) : var1.getScaledHeight();
   }

   @Override
   protected double getRenderScale() {
      return Gui.JzraV3() ? this.Osi / new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor() : 1.0;
   }

   public void showCategoryAfterProfileLoad(Category var1) {
      this.currentCategory = var1 == null ? Category.combat : var1;
      this.searchText = "";
      this.searchFocused = false;
      this.searchScopedToCategory = false;
      this.Namm5 = false;
      if (this.currentCategory == Category.profiles) {
         this.configListScroll.snapTo(0.0F);
      }

      if (this.currentCategory != Category.themes && this.themeEditorOpen) {
         this.closeThemeEditor();
      }
   }

   @Override
   public void requestScaleRefresh() {
      this.refreshAfterProfileLoad();
   }

   @Override
   public void drawScreen(int var1, int var2, float var3) {
      boolean var4 = Gui.JzraV3();
      if (var4 != this.Hto) {
         this.MldK();
         this.searchFocused = false;
         this.expandedModeSliders.clear();
         this.dropdownLayoutState.resetState();
         this.openTimeMillis = System.currentTimeMillis();
         this.Hto = var4;
      }

      this.refreshAfterProfileLoad();
      if (!var4) {
         this.renderClickGuiContent(var1, var2, var3);
      } else {
         double var5 = this.getRenderScale();
         var1 = (int)Math.floor(var1 / var5);
         var2 = (int)Math.floor(var2 / var5);
         float var7 = GuiTheme.ANLBFU((float)(System.currentTimeMillis() - this.openTimeMillis) / 900.0F);
         if (this.fadeOutPending) {
            float var8 = (float)(System.currentTimeMillis() - this.FgL) / 220.0F;
            if (var8 >= 1.0F) {
               this.finishClosingFade();
               return;
            }

            var7 = this.openFadeProgress * (1.0F - GuiTheme.ANLBFU(var8));
            var2 = -10000;
            var1 = -10000;
         }

         GlStateManager.pushMatrix();
         GlStateManager.scale(var5, var5, 1.0);

         try {
            GlStateManager.disableDepth();
            GlStateManager.depthMask(false);
            GlStateManager.disableAlpha();
            RenderUtils.XNRNki(0.0, 0.0, this.width, this.height, Math.round(209.09999F * var7) << 24);
            this.dropdownFadeRenderer.beginBlurCapture(var7);

            try {
               this.renderClickGuiContent(var1, var2, var3);
               SubscriptionStatus.drawStartupStatus(this, this.height);
            } finally {
               this.endDropdownFade(this.dropdownFadeRenderer, var7);
            }
         } finally {
            GlStateManager.depthMask(true);
            GlStateManager.enableAlpha();
            GlStateManager.enableDepth();
            GlStateManager.popMatrix();
         }
      }
   }

   private void renderClickGuiContent(int var1, int var2, float var3) {
      this.refreshProfileDependentState();
      this.gvH.HUBGZjr();
      this.clearDropdownGeometryCaches();
      if (!Gui.JzraV3()) {
         this.NieD();
         this.updateResizeDrag(var1, var2);
         this.dragWindow(var1, var2);
      }

      GuiRect var4 = this.windowRect();
      if (Gui.JzraV3()) {
         this.dropdownLayoutState.VJvs2(this, this.width, this.height, var1, var2);
         var4 = this.windowRect();
      } else {
         this.lxzOnq();
         this.nhut(var4, var1, var2);
         this.drawStatusPill();
      }

      if (this.nwE5 && this.themeEditorOpen) {
         this.drawPaletteEditor();
      }

      if (!this.createConfigDialogOpen && this.configDialogMode == 0 && !this.searchModalOpen && !this.uninjectDialogOpen && this.activeBlockList == null && this.NSq4 == null) {
         this.drawOpenColorPicker();
      }

      if (this.createConfigDialogOpen) {
         this.drawCreateConfigDialog(var4, var1, var2);
      }

      if (this.themeNameDialogOpen) {
         this.eycl(var4, var1, var2);
      }

      if (this.Metj != null) {
         this.drawDeleteThemeDialog(var4, var1, var2);
      }

      if (this.pendingThemeEdit != null) {
         this.drawUnsavedThemeDialog(var4, var1, var2);
      }

      if (this.configDialogMode != 0) {
         this.AzetQ(var4, var1, var2);
      }

      if (this.uninjectDialogOpen) {
         this.drawUninjectDialog(var4, var1, var2);
      }

      if (this.searchModalOpen) {
         this.drawRelationshipSearchModal(var4, var1, var2);
      }

      if (this.activeBlockList != null && this.supportsEntrySearchDialog(this.activeBlockList)) {
         this.drawEntrySearchDialog(var4, var1, var2);
      }

      if (this.NSq4 != null && this.activeBlockList == null) {
         this.XGsbyK1(var1, var2);
      }

      if (this.draggingSlider != null) {
         this.PbPmkE4(var1);
      }

      if (this.BvMq != null) {
         this.dragColorPicker(var1, var2);
      }

      if (this.fHvex != 0) {
         this.dragEntryColorPicker(var1, var2);
      }

      if (this.ABCZe != 0) {
         this.dragPaletteColorPicker(var1, var2);
      }
   }

   private void drawStatusPill() {
      float var1 = this.getStatusPillAlpha();
      if (!(var1 <= 0.01F)) {
         byte var2 = 24;
         IFont var3 = this.settingFont();
         int var4 = this.getStatusPillWidth(var3);
         int var5 = (this.width - var4) / 2;
         byte var6 = 9;
         var5 = Math.max(8, Math.min(this.width - var4 - 8, var5));
         int var7 = Math.round(212.0F * var1);
         int var8 = Math.round(38.0F * var1);
         int var9 = this.withAlphaValue(352321535, Math.round(20.0F * var1));
         this.round(var5 + 1, var6 + 1, var4, var2, 7.0F, this.withAlphaValue(-16777216, var8));
         this.round(var5, var6, var4, var2, 7.0F, this.withAlphaValue(-15066598, var7));
         this.drawSoftOutline(var5, var6, var4, var2, 7.0F, var9);
         this.eAswnvv(var5, this.centeredTextY(var6, var2, var3), var4, var1, var3);
      }
   }

   private void eAswnvv(int var1, float var2, int var3, float var4, IFont var5) {
      String var6 = this.greeting + ", ";
      String var7 = this.PznYc();
      String var8 = "  |  ";
      String var9 = "Expires in ";
      String var10 = this.MDmWpaS();
      int var12 = this.textWidth(var6, var5);
      int var13 = this.textWidth(var7, var5);
      int var14 = this.textWidth(var8, var5);
      int var15 = this.textWidth(var9, var5);
      int var16 = this.textWidth(var10, var5);
      int var17 = var12 + var13 + var14 + var15 + var16;
      float var18 = var1 + (var3 - var17) / 2.0F;
      this.drawText(var6, var18, var2, this.withAlphaValue(-986896, Math.round(242.0F * var4)), var5);
      var18 += var12;
      this.drawText(var7, var18, var2, this.withAlphaValue(this.getAccentColor(), Math.round(255.0F * var4)), var5);
      var18 += var13;
      this.drawText(var8, var18, var2, this.withAlphaValue(-9803158, Math.round(190.0F * var4)), var5);
      var18 += var14;
      this.drawText(var9, var18, var2, this.withAlphaValue(-986896, Math.round(242.0F * var4)), var5);
      var18 += var15;
      this.drawText(var10, var18, var2, this.withAlphaValue(this.getAccentColor(), Math.round(255.0F * var4)), var5);
   }

   private float getStatusPillAlpha() {
      if (this.openTimeMillis <= 0L) {
         return 1.0F;
      } else {
         long var1 = System.currentTimeMillis() - this.openTimeMillis;
         if (var1 <= 190L) {
            return 0.0F;
         } else {
            float var3 = Math.min(1.0F, (float)(var1 - 190L) / 260.0F);
            return 1.0F - (float)Math.pow(1.0F - var3, 3.0);
         }
      }
   }

   private int getStatusPillWidth(IFont var1) {
      String var2 = this.greeting + ", " + this.PznYc() + "  |  Expires in " + this.MDmWpaS();
      int var3 = this.textWidth(var2, var1) + 16;
      return Math.max(140, var3);
   }

   private void ESTnlo() {
      this.greeting = GREETINGS[(int)(Math.random() * GREETINGS.length)];
   }

   public void drawDropdownGreeting(int var1, int var2) {
      String var3 = this.greeting + ", ";
      this.drawSmall(var3, var1, var2, -1182988);
      this.drawSmall(this.PznYc(), var1 + this.textWidth(var3, this.settingFont()), var2, GuiTheme.xGoxa());
   }

   private String PznYc() {
      String var1 = SubscriptionState.getDiscordUsername();
      if (var1 != null && !var1.isEmpty()) {
         return var1;
      } else {
         try {
            if (this.mc != null && this.mc.getSession() != null) {
               return this.mc.getSession().getUsername();
            }
         } catch (Throwable var3) {
         }

         return "player";
      }
   }

   private String MDmWpaS() {
      return SubscriptionState.formatRemainingTime();
   }

   private void nhut(GuiRect var1, int var2, int var3) {
      float var4 = this.getWindowOpenScale();
      this.round(var1.x + 3, var1.ufe + 4, var1.busF - 1, var1.HfS - 1, 12.0F, 1375731712);
      this.round(var1.x, var1.ufe, var1.busF, var1.HfS, 10.0F, -15658735);
      RoundedRect.drawHorizontalGradient(var1.x, var1.ufe, var1.busF, var1.HfS, 10.0F, new Color(-1610612736, true), new Color(301989888, true));
      int var5 = this.sidebarW();
      RenderUtils.pushScissorRect(var1.x, var1.ufe, var5 + 2, var1.HfS);
      this.round(var1.x, var1.ufe, var1.busF, var1.HfS, 10.0F, -15066598);
      RenderUtils.restoreScissorState();
      this.edgeOutline(var1.x, var1.ufe, var1.busF, var1.HfS, 10.0F);
      RenderUtils.XNRNki(var1.x + var5, var1.ufe + 1, var1.x + var5 + 1, var1.ufe + var1.HfS - 1, 1711276032);
      RenderUtils.XNRNki(var1.x + var5 + 1, var1.ufe + 1, var1.x + var5 + 2, var1.ufe + var1.HfS - 1, 352321535);
      this.AYz5(var1, var2, var3);
      this.drawCategoryContent(var1, var2, var3);
      if (var4 < 0.999F) {
         this.round(var1.x, var1.ufe, var1.busF, var1.HfS, 10.0F, this.withAlphaValue(-15658735, Math.round((1.0F - var4) * 175.0F)));
      }
   }

   private void AYz5(GuiRect var1, int var2, int var3) {
      int var4 = var1.ufe + 12;
      this.drawLogo(var1.x + 14, var4, 32.0F, this.getAccentColor());
      this.WZTcG("jaded.cc", var1.x + 50, var4 + 12, this.getAccentColor(), this.nqA2());
      this.drawSearchBox(var1, var2, var3);
      int var5 = this.getSidebarListTop(var1);
      int var6 = this.aIrlh6(var1).ufe - 8;
      int var7 = Math.max(20, var6 - var5);
      int var8 = this.getSidebarContentHeight();
      this.clampScroll(this.sidebarScrollAnimation, var8, var7);
      int var9 = var5 - Math.round(this.sidebarScrollAnimation.ORMWO());
      RenderUtils.pushScissorRect(var1.x, var5, this.sidebarW(), var7);
      this.drawPlainText("Modules", var1.x + 18, var9 + 5, -9803158, this.settingFont());
      var9 += 20;

      for (Category var13 : CategoryNames.getOrderedCategories()) {
         if (var13 == Category.friends) {
            this.drawPlainText("Client", var1.x + 18, var9 + 5, -9803158, this.settingFont());
            var9 += 20;
         }

         GuiRect var14 = this.getCategoryRowRect(var1, var9);
         boolean var15 = var13 == this.currentCategory;
         boolean var16 = var14.contains(var2, var3);
         float var17 = this.animateTowards(this.ghEaz(var13), var15 ? 1.0F : 0.0F);
         if (var17 > 0.01F) {
            this.drawSidebarHighlight(var14, var17);
         }

         if (!var15 && var16) {
            this.round(var14.x, var14.ufe + 2, var14.busF, var14.HfS - 4, 7.0F, -14079703);
         }

         int var18 = var15 ? this.getAccentContrastText() : -6645094;
         byte var19 = 18;
         this.drawCategoryIcon(var13, var14.x + 10, var14.ufe + (var14.HfS - var19) / 2, var19, var18);
         this.drawPlainText(CategoryNames.getDisplayName(var13), var14.x + 38, this.centeredFontTextY(var14.ufe, var14.HfS, this.getTitleFont()), var18, this.getTitleFont());
         var9 += 27;
      }

      RenderUtils.restoreScissorState();
      this.drawSidebarScrollFade(var1, var5, var7, var8, Math.round(this.sidebarScrollAnimation.ORMWO()));
      this.drawClientCategoryButton(var1, var2, var3);
   }

   private void drawSearchBox(GuiRect var1, int var2, int var3) {
      GuiRect var4 = this.getSearchBoxRect(var1);
      boolean var5 = var4.contains(var2, var3);
      float var6 = this.animateTowards(this.searchFocusAnimationKey, this.searchFocused ? 1.0F : 0.0F);
      int var7 = this.lerpColor(-15658735, !var5 && !this.searchFocused ? -15658735 : -15395563, Math.max(var5 ? 1.0F : 0.0F, var6));
      this.round(var4.x, var4.ufe, var4.busF, var4.HfS, 7.0F, var7);
      if (this.searchFocused) {
         RoundedRect.drawRoundedOutline(var4.x, var4.ufe, var4.busF, var4.HfS, 7.0F, 0.45F, new Color(0, 0, 0, 0), new Color(-1761607681, true));
      }

      IFont var8 = this.settingFont();
      byte var9 = 12;
      int var10 = var4.x + var4.busF - var9 - 9;
      int var11 = var4.ufe + (var4.HfS - var9) / 2;
      int var12 = var10 - var4.x - 17;
      float var13 = this.centeredFontTextY(var4.ufe, var4.HfS, var8);
      float var14 = this.animateTowards(this.Aej, this.searchText.isEmpty() && !this.searchFocused ? 1.0F : 0.0F);
      if (var14 > 0.01F) {
         this.drawPlainText("Search...", var4.x + 10, var13, this.withAlphaValue(-12961222, Math.round(var14 * 210.0F)), var8);
      }

      if (!this.searchText.isEmpty()) {
         String var15 = this.OKJT(this.searchText, var12, var8);
         int var16 = var4.x + 10;
         int var17 = Math.min(var12, this.textWidth(var15, var8));
         if (this.searchFocused && this.Namm5) {
            RenderUtils.XNRNki(var16 - 2, var4.ufe + 6, var16 + var17 + 2, var4.ufe + var4.HfS - 6, 1442840575);
         }

         this.drawPlainText(var15, var16, var13, this.getSearchTextColor(), var8);
         if (this.searchFocused && !this.Namm5 && System.currentTimeMillis() / 500L % 2L == 0L) {
            int var18 = var16 + var17 + 2;
            RenderUtils.XNRNki(var18, var4.ufe + 7, var18 + 1, var4.ufe + var4.HfS - 7, -6645094);
         }
      } else if (this.searchFocused && System.currentTimeMillis() / 500L % 2L == 0L) {
         int var19 = var4.x + 10;
         RenderUtils.XNRNki(var19, var4.ufe + 7, var19 + 1, var4.ufe + var4.HfS - 7, -6645094);
      }

      this.drawSearchIcon(var10, var11, var9, this.searchFocused ? -6645094 : -9803158);
   }

   private int getSearchTextColor() {
      if (!this.searchFocused) {
         return -986896;
      } else {
         float var1 = (float)((Math.sin(System.currentTimeMillis() / 210.0) + 1.0) * 0.5);
         return this.withAlphaValue(-986896, Math.round(150.0F + var1 * 90.0F));
      }
   }

   private void drawCategoryContent(GuiRect var1, int var2, int var3) {
      if (this.currentCategory != Category.friends || !this.normalizedSearch().isEmpty() && !this.searchScopedToCategory) {
         if (this.currentCategory != Category.profiles || !this.normalizedSearch().isEmpty() && !this.searchScopedToCategory) {
            if (this.currentCategory != Category.themes || !this.normalizedSearch().isEmpty() && !this.searchScopedToCategory) {
               if (this.currentCategory != Category.client || !this.normalizedSearch().isEmpty() && !this.searchScopedToCategory) {
                  int var4 = var1.x + this.sidebarW();
                  int var5 = var1.ufe;
                  int var6 = var1.HfS;
                  List var7 = this.getVisibleModules();
                  int var8 = var5 + 8;
                  int var9 = var6 - 16;
                  int var10 = var4 + 8;
                  int var11 = var1.busF - this.sidebarW() - 16;
                  int var12 = this.getModuleListHeight(var7, var11);
                  AnimatedFloat var13 = this.getActiveContentScroll();
                  this.clampScroll(var13, var12, var9);
                  int var14 = Math.round(var13.ORMWO());
                  this.drawModuleList(var7, var10, var8, var11, var9, var14, var2, var3);
                  this.drawScrollFade(this.getScrollFadeCategory(), var10, var8, var11, var9, var12, var14);
               } else {
                  this.drawClientSettingsPage(var1, var2, var3);
               }
            } else {
               this.drawThemesPage(var1, var2, var3);
            }
         } else {
            this.ymtt(var1, var2, var3);
         }
      } else {
         this.drawRelationshipsManager(var1, var2, var3);
      }
   }

   private void drawClientSettingsPage(GuiRect var1, int var2, int var3) {
      int var4 = var1.x + this.sidebarW();
      int var5 = var4 + 8;
      int var6 = var1.ufe + 8;
      int var7 = var1.busF - this.sidebarW() - 16;
      int var8 = var1.HfS - 16;
      List var9 = this.getClientSettingEntries();
      int var10 = this.getClientSettingsHeight(var9, var7);
      AnimatedFloat var11 = this.getActiveContentScroll();
      this.clampScroll(var11, var10, var8);
      int var12 = Math.round(var11.ORMWO());
      int var13 = var5 + 2;
      int var14 = var6 + 2 - var12;
      int var15 = var7 - 4;
      int var16 = Math.max(30, var10 + 16);
      RenderUtils.pushScissorRect(var5 - 1, var6 - 1, var7 + 2, var8 + 2);
      this.round(var13, var14, var15, var16, 6.0F, 150994943);
      this.drawLightOverlay(var13, var14, var15, var16, 6.0F, this.withAccentAlpha(28));
      RenderUtils.restoreScissorState();
      RenderUtils.pushScissorRect(var5 - 1, var6 - 1, var7 + 2, var8 + 2);
      int var17 = var6 + 10 - var12;

      for (DropdownEntry var19 : (java.lang.Iterable<DropdownEntry>) (java.lang.Iterable<?>) (var9)) {
         if (var17 > var6 + var8) {
            break;
         }

         int var20 = this.bqhY(var19, var7);
         if (var17 + var20 >= var6 && var19.mzi == null) {
            var17 += this.drawSetting(var19.module, var19.setting, var5 + 12, var17, var7 - 24, var2, var3);
            var17 += 2;
         } else {
            var17 += var20;
         }
      }

      RenderUtils.restoreScissorState();
      this.drawScrollFade(Category.client, var5, var6, var7, var8, var10, var12);
   }

   private List<DropdownEntry> getClientSettingEntries() {
      ArrayList var1 = new ArrayList();
      Module var2 = Jade.getModuleManager().getModule(Gui.class);
      Module var3 = Jade.getModuleManager().getModule(Rendering.class);
      Module var4 = Jade.getModuleManager().getModule(Settings.class);
      Module var5 = Jade.getModuleManager().getModule(ChatCommands.class);
      if (var2 != null) {
         this.collectVisibleSettings(var1, var2);
      }

      if (var3 != null) {
         this.collectVisibleSettings(var1, var3);
      }

      if (var4 != null) {
         this.collectVisibleSettings(var1, var4);
      }

      if (var5 != null) {
         this.collectVisibleSettings(var1, var5);
      }

      return var1;
   }

   private void collectVisibleSettings(List<DropdownEntry> var1, Module var2) {
      for (Setting var4 : var2.getSettings()) {
         if ((Gui.JzraV3() || !ThemeConfig.isDropdownAppearanceSetting(var4)) && var4.visible && !(var4 instanceof DescriptionSetting)) {
            var1.add(new DropdownEntry(var2, var4));
         }
      }
   }

   private int getClientSettingsHeight(List<DropdownEntry> var1, int var2) {
      int var3 = 0;

      for (DropdownEntry var5 : var1) {
         var3 += this.bqhY(var5, var2);
      }

      return Math.max(0, var3 + 18);
   }

   private int bqhY(DropdownEntry var1, int var2) {
      return var1.HpgN() ? 30 : this.settingHeight(var1.setting, var2) + 2;
   }

   private void drawConfigsPanel(GuiRect var1, int var2, int var3) {
      int var4 = var1.x + 8;
      int var5 = var1.ufe + 4;
      int var6 = var1.busF - 16;
      List var7 = this.getFilteredConfigProfiles();
      this.createConfigButtonRect = new GuiRect(var4 + var6 - 66, var5, 22, 22);
      this.VwaR = new GuiRect(var4 + var6 - 44, var5, 22, 22);
      this.openFolderButton = new GuiRect(var4 + var6 - 22, var5, 22, 22);
      GuiIcons.drawIconButton(this, this.createConfigButtonRect, "+", "New config", var2, var3);
      GuiIcons.drawIconButton(this, this.VwaR, "refresh", "Refresh", var2, var3);
      GuiIcons.drawIconButton(this, this.openFolderButton, "folder", "Open folder", var2, var3);
      int var8 = var5 + 27;
      int var9 = Math.max(20, var1.HfS - 39);
      byte var10 = 26;
      this.clampScroll(this.configListScroll, var7.size() * var10, var9);
      RenderUtils.pushScissorRect(var4, var8, var6, var9);
      int var11 = var8 - Math.round(this.configListScroll.ORMWO());

      for (ConfigProfile var13 : (java.lang.Iterable<ConfigProfile>) (java.lang.Iterable<?>) (var7)) {
         GuiRect var14 = new GuiRect(var4, var11, var6, var10);
         if (var11 + var10 > var8 && var11 < var8 + var9) {
            boolean var15 = var13.isEnabled();
            boolean var16 = !this.isDefaultConfigProfile(var13);
            boolean var17 = var14.contains(var2, var3);
            RenderUtils.XNRNki(var4, var11, var4 + var6, var11 + var10, var15 ? GuiTheme.withAlpha(65) : -1945499126);
            if (var17) {
               RenderUtils.XNRNki(var4, var11, var4 + var6, var11 + var10, 822083583);
            }

            RenderUtils.XNRNki(var4, var11 + var10 - 1, var4 + var6, var11 + var10, 613258909);
            GuiRect var18 = new GuiRect(var4 + var6 - 44, var11, 22, var10);
            GuiRect var19 = new GuiRect(var4 + var6 - 22, var11, 22, var10);
            String var20 = this.bindingConfigProfile == var13 ? "..." : (var13.getKeycode() == 0 ? "" : this.getKeyDisplayName(var13.getKeycode()));
            int var21 = Math.max(22, this.dropdownBindWidth(var20) + 6);
            GuiRect var22 = new GuiRect(var18.x - var21, var11, var21, var10);
            GuiRect var23 = new GuiRect(var4 + 4, var11 + 2, (var16 ? var22.x : var4 + var6) - var4 - 8, var10 - 4);
            if (this.yzh == var13) {
               this.drawConfigTextInput(var23, this.configRenameInput, "Config name", true);
            } else {
               this.drawDropdownTitle(
                  this.trimToWidth(var13.getName(), var23.busF, this.settingFont()), var23.x, this.centeredTextY(var11, var10, this.settingFont()), -1182988
               );
            }

            if (var16) {
               if (!var20.isEmpty()) {
                  this.drawDropdownBind(var20, var22);
               } else if (var17) {
                  GuiIcons.drawIconButton(this, var22, "edit", null, var2, var3);
               }

               GuiIcons.drawIconButton(this, var18, "save", null, var2, var3);
               GuiIcons.drawIconButton(this, var19, "x", null, var2, var3);
            }

            if (var3 >= var8 && var3 < var8 + var9) {
               this.configRowRects.put(var13, var14);
               this.nam.put(var13, var23);
               if (var16) {
                  this.configBindButtons.put(var13, var22);
                  this.configSaveButtons.put(var13, var18);
                  this.PeGs9.put(var13, var19);
               }
            }
         }

         var11 += var10;
      }

      RenderUtils.restoreScissorState();
      GuiIcons.drawRectBorder(var4, var8, var6, var9, 613258909);
   }

   public void beginDropdownThemes() {
      this.themesDropdownAnimation.resetAnimations();
   }

   public float dropdownThemesEntrance() {
      return this.themesDropdownAnimation.MUePi();
   }

   public int[] dropdownManagerSize(Category var1) {
      if (var1 == Category.friends) {
         return new int[]{460, this.dropdownRelationshipsHeight()};
      } else if (var1 == Category.themes) {
         int var2 = Jade.themeManager == null ? 0 : Jade.themeManager.getAllThemes().size();
         float var3 = this.themesDropdownAnimation.getGlobalToggleValue(this.themeEditorOpen);
         int var4 = Math.min(420, 44 + Math.max(1, (var2 + 1) / 2) * 34);
         return new int[]{480 + Math.round(240.0F * var3), Math.round(var4 + (360 - var4) * var3)};
      } else {
         return new int[]{480, Math.min(440, 42 + Math.max(2, this.getFilteredConfigProfiles().size()) * 26)};
      }
   }

   private void drawThemesPanel(GuiRect var1, int var2, int var3) {
      this.HOf.clear();
      this.themeEditButtons.clear();
      this.themeDeleteButtons.clear();
      this.Uginwv.clear();
      this.addPaletteColorButtons.clear();
      this.paletteEditorPanelRect = null;
      this.paletteEditorCloseButton = null;
      int var4 = var1.x + 8;
      int var5 = var1.ufe + 4;
      int var6 = var1.busF - 16;
      this.themesNewButton = new GuiRect(var4 + var6 - 42, var5, 21, 21);
      this.themesFolderButton = new GuiRect(var4 + var6 - 21, var5, 21, 21);
      GuiIcons.drawIconButton(this, this.themesNewButton, "+", "New theme", var2, var3);
      GuiIcons.drawIconButton(this, this.themesFolderButton, "folder", "Open folder", var2, var3);
      int var7 = var5 + 26;
      int var8 = var1.HfS - 38;
      float var9 = this.themesDropdownAnimation.getGlobalToggleValue(this.themeEditorOpen);
      short var10 = 230;
      int var11 = Math.round(240.0F * var9);
      int var12 = var6 - var11;
      List var13 = Jade.themeManager == null ? Collections.emptyList() : Jade.themeManager.getAllThemes();
      int var14 = Math.max(1, var12 / 200);
      byte var15 = 8;
      int var16 = (var12 - (var14 - 1) * var15) / var14;
      byte var17 = 30;
      this.clampScroll(this.themesScroll, (var13.size() + var14 - 1) / var14 * 34, var8);
      RenderUtils.pushScissorRect(var4, var7, var12, var8);

      for (int var18 = 0; var18 < var13.size(); var18++) {
         Theme var19 = (Theme)var13.get(var18);
         GuiRect var20 = new GuiRect(var4 + var18 % var14 * (var16 + var15), var7 + var18 / var14 * 34 - Math.round(this.themesScroll.ORMWO()), var16, var17);
         if (var20.ufe + var20.HfS > var7 && var20.ufe < var7 + var8) {
            boolean var21 = Jade.Grq != null && var19.getId().equalsIgnoreCase(Jade.Grq.getThemeName());
            float var22 = this.themesDropdownAnimation.QHqA(var19.getId(), var21);
            float var23 = this.themesDropdownAnimation.Xfa0(var19.getId(), var20.contains(var2, var3));
            RenderUtils.XNRNki(var20.x, var20.ufe, var20.x + var20.busF, var20.ufe + 23, -1945499126);
            if (var22 > 0.0F) {
               RenderUtils.XNRNki(var20.x, var20.ufe, var20.x + var20.busF, var20.ufe + 23, GuiTheme.withAlpha(Math.round(124.0F * var22)));
            }

            if (var23 > 0.0F) {
               RenderUtils.XNRNki(var20.x, var20.ufe, var20.x + var20.busF, var20.ufe + 23, Math.round(25.0F * var23) << 24 | 16777215);
            }

            int var24 = var19.isCustom() ? 46 : 8;
            this.drawDropdownTitle(
               this.trimToWidth(var19.getName(), var20.busF - var24 - 8, this.settingFont()),
               var20.x + 4,
               this.centeredTextY(var20.ufe, 23.0F, this.settingFont()),
               -1182988
            );
            List var25 = var19.mKwci3();

            for (int var26 = 0; var26 < var25.size(); var26++) {
               RenderUtils.XNRNki(
                  var20.x + var26 * var20.busF / var25.size(),
                  var20.ufe + 24,
                  var20.x + (var26 + 1) * var20.busF / var25.size(),
                  var20.ufe + 29,
                  (Integer)var25.get(var26)
               );
            }

            boolean var30 = var3 >= var7 && var3 < var7 + var8;
            if (var30) {
               this.HOf.put(var19, var20);
            }

            if (var19.isCustom()) {
               GuiRect var27 = new GuiRect(var20.x + var20.busF - 44, var20.ufe, 22, 23);
               GuiRect var28 = new GuiRect(var20.x + var20.busF - 22, var20.ufe, 22, 23);
               GuiIcons.drawIconButton(this, var27, "edit", null, var2, var3);
               GuiIcons.drawIconButton(this, var28, "x", null, var2, var3);
               if (var30) {
                  this.themeEditButtons.put(var19, var27);
                  this.themeDeleteButtons.put(var19, var28);
               }
            }

            GuiIcons.drawRectBorder(var20.x, var20.ufe, var20.busF, var20.HfS, 613258909);
         }
      }

      RenderUtils.restoreScissorState();
      if (var11 > 10) {
         int var29 = var4 + var12 + 10;
         RenderUtils.pushScissorRect(var29, var7, var11 - 10, var8);
         if (this.themeEditorOpen) {
            this.drawPalettePanel(new GuiRect(var29, var7, var10, var8), var2, var3);
         } else {
            RenderUtils.XNRNki(var29, var7, var29 + var10, var7 + var8, -1509357303);
         }

         RenderUtils.restoreScissorState();
      }
   }

   private void drawPalettePanel(GuiRect var1, int var2, int var3) {
      this.paletteEditorPanelRect = var1;
      RenderUtils.XNRNki(var1.x, var1.ufe, var1.x + var1.busF, var1.ufe + var1.HfS, -1509357303);
      GuiIcons.drawRectBorder(var1.x, var1.ufe, var1.busF, var1.HfS, 613258909);
      this.drawDropdownTitle(this.editingTheme == null ? "Create palette" : "Edit palette", var1.x + 12, var1.ufe + 14, -1182988);
      this.paletteEditorCloseButton = new GuiRect(var1.x + var1.busF - 32, var1.ufe + 8, 24, 24);
      GuiIcons.drawHoverableButton(this, this.paletteEditorCloseButton, "x", var2, var3, false);
      this.drawSmall("Click to edit. Right-click to remove.", var1.x + 12, var1.ufe + 43, -4208434);
      int var4 = var1.ufe + 69;

      for (int var5 = 0; var5 < this.paletteColors.size(); var5++) {
         GuiRect var6 = new GuiRect(var1.x + 12, var4, var1.busF - 24, 40);
         this.Uginwv.add(var6);
         RenderUtils.XNRNki(var6.x, var6.ufe, var6.x + 40, var6.ufe + 40, this.paletteColors.get(var5));
         this.drawSmall(String.format("#%06X", this.paletteColors.get(var5) & 16777215), var6.x + 52, var4 + 14, -1182988);
         GuiIcons.drawRectBorder(var6.x, var6.ufe, var6.busF, var6.HfS, var6.contains(var2, var3) ? GuiTheme.xGoxa() : 613258909);
         var4 += 50;
      }

      if (this.paletteColors.size() < 4) {
         GuiRect var7 = new GuiRect(var1.x + 12, var4, var1.busF - 24, 30);
         this.addPaletteColorButtons.add(var7);
         GuiIcons.drawHoverableButton(this, var7, "+ Add color", var2, var3, false);
      }

      this.zeL = new GuiRect(var1.x + 12, var1.ufe + var1.HfS - 42, var1.busF - 24, 30);
      GuiIcons.drawHoverableButton(this, this.zeL, this.editingTheme == null ? "Create theme" : "Save / Rename", var2, var3, true);
   }

   private void ymtt(GuiRect var1, int var2, int var3) {
      this.configRowRects.clear();
      this.nam.clear();
      this.configSaveButtons.clear();
      this.PeGs9.clear();
      this.configBindButtons.clear();
      int var4 = var1.x + this.sidebarW();
      int var5 = var4 + 8;
      int var6 = var1.ufe + 8;
      int var7 = var1.busF - this.sidebarW() - 16;
      int var8 = var1.HfS - 16;
      this.cnP26(var5, var6, var7, var2, var3);
      int var9 = var6 + 39;
      int var10 = var8 - 39;
      List var11 = this.getFilteredConfigProfiles();
      int var12 = Math.max(0, var11.size() * 36 - 5);
      this.clampScroll(this.configListScroll, var12, var10);
      int var13 = Math.round(this.configListScroll.ORMWO());
      RenderUtils.pushScissorRect(var5 - 1, var9 - 1, var7 + 2, var10 + 2);
      int var14 = var9 - var13;

      for (ConfigProfile var16 : (java.lang.Iterable<ConfigProfile>) (java.lang.Iterable<?>) (var11)) {
         this.drawConfigProfileRow(var16, var5, var14, var7, var2, var3);
         var14 += 36;
      }

      if (var11.isEmpty()) {
         this.drawText("No configs", var5 + 12, var9 + 14, -9803158, this.settingFont());
      }

      RenderUtils.restoreScissorState();
      this.drawScrollFade(Category.profiles, var5, var9, var7, var10, var12, var13);
   }

   private void drawThemesPage(GuiRect var1, int var2, int var3) {
      this.HOf.clear();
      this.themeEditButtons.clear();
      this.themeDeleteButtons.clear();
      this.Uginwv.clear();
      this.addPaletteColorButtons.clear();
      this.paletteEditorPanelRect = null;
      this.paletteEditorCloseButton = null;
      int var4 = var1.x + this.sidebarW() + 8;
      int var5 = var1.ufe + 8;
      int var6 = var1.busF - this.sidebarW() - 16;
      int var7 = var1.HfS - 16;
      this.drawText("Select Theme", var4 + 2, var5 + 6, -986896, this.nqA2());
      this.drawSmall("Choose a preset or one of your custom themes.", var4 + 2, var5 + 27, -9803158);
      this.themesFolderButton = new GuiRect(var4 + var6 - 30, var5 + 3, 30, 25);
      this.themesNewButton = new GuiRect(var4 + var6 - 105, var5 + 3, 70, 25);
      this.drawIconButton(this.themesNewButton, "New", IconType.CREATE, var2, var3, true, true);
      this.drawIconButton(this.themesFolderButton, "", IconType.FOLDER, var2, var3, true, false);
      int var8 = this.themeEditorOpen ? Math.min(154, Math.max(124, var7 / 2)) : 0;
      int var9 = var5 + 42;
      int var10 = var7 - 42;
      List var11 = Jade.themeManager == null ? Collections.emptyList() : Jade.themeManager.getAllThemes();
      byte var12 = 6;
      int var13 = Math.max(2, Math.min(4, (var6 + var12) / 88));
      int var14 = (var6 - var12 * (var13 - 1)) / var13;
      byte var15 = 68;
      int var16 = (var11.size() + var13 - 1) / var13;
      int var17 = Math.max(0, var16 * (var15 + var12) - var12 + (this.themeEditorOpen ? var8 + var12 : 0));
      this.clampScroll(this.themesScroll, var17, var10);
      int var18 = Math.round(this.themesScroll.ORMWO());
      RenderUtils.pushScissorRect(var4 - 1, var9 - 1, var6 + 2, var10 + 2);

      for (int var19 = 0; var19 < var11.size(); var19++) {
         Theme var20 = (Theme)var11.get(var19);
         int var21 = var4 + var19 % var13 * (var14 + var12);
         int var22 = var9 + var19 / var13 * (var15 + var12) - var18;
         this.drawThemeCard(var20, new GuiRect(var21, var22, var14, var15), var2, var3);
      }

      RenderUtils.restoreScissorState();
      this.drawScrollFade(Category.themes, var4, var9, var6, var10, var17, var18);
      if (this.themeEditorOpen) {
         this.drawPaletteDialog(var4, var5 + var7 - var8, var6, var8, var2, var3);
      }
   }

   private void drawThemeCard(Theme var1, GuiRect var2, int var3, int var4) {
      this.HOf.put(var1, var2);
      boolean var5 = Jade.Grq != null && var1.getId().equalsIgnoreCase(Jade.Grq.getThemeName());
      boolean var6 = var2.contains(var3, var4);
      this.round(var2.x, var2.ufe, var2.busF, var2.HfS, 6.0F, var6 ? -14671840 : -15066598);
      int var7 = Math.max(11, Math.min(16, (var2.busF - 28) / Math.max(1, var1.mKwci3().size()) - 3));
      int var8 = var1.mKwci3().size() * var7 + Math.max(0, var1.mKwci3().size() - 1) * 5;
      int var9 = var2.x + (var2.busF - var8) / 2;

      for (int var11 : var1.mKwci3()) {
         this.round(var9, var2.ufe + 11, var7, var7, var7 / 2.0F, var11);
         var9 += var7 + 5;
      }

      this.drawCenteredText(
         this.trimToWidth(var1.getName(), var2.busF - 12, this.getTitleFont()), var2.getCenterX(), var2.ufe + 42, var5 ? -986896 : -6645094, this.getTitleFont()
      );
      if (var5) {
         byte var32 = 16;
         int var34 = var2.x + var2.busF - var32 - 1;
         RenderUtils.pushScissorRect(var2.x, var2.ufe, var2.busF, var2.HfS);
         this.round(var34, var2.ufe + 1, var32, var32, 5.0F, this.getAccentColor());
         this.drawVectorIcon(IconType.TICK, var34 + 3, var2.ufe + 4, 10, this.getAccentContrastText());
         RenderUtils.restoreScissorState();
      }

      if (var1.isCustom()) {
         int var33 = Math.max(10, Math.min(13, var2.busF / 9));
         GuiRect var35 = new GuiRect(var2.x + 2, var2.ufe + 4, 12, var33 + 4);
         byte var12 = 18;
         byte var13 = 14;
         GuiRect var14 = new GuiRect(var35.x + var35.busF + 2, var2.ufe + 3, var13 * 2 + 7, var12);
         float var15 = this.Er2.containsKey(var1) ? this.Er2.get(var1) : 0.0F;
         long var16 = System.currentTimeMillis();
         boolean var18 = var35.contains(var3, var4) || var15 > 0.05F && var14.contains(var3, var4);
         if (var18) {
            this.UNVY.put(var1, var16);
         }

         long var19 = this.UNVY.containsKey(var1) ? this.UNVY.get(var1) : 0L;
         boolean var21 = var6 && (var18 || var16 - var19 < 1000L);
         if (!var6) {
            this.UNVY.remove(var1);
         }

         float var22 = var21 ? 1.0F : 0.0F;
         float var23 = var15 + (var22 - var15) * 0.24F;
         if (Math.abs(var23 - var22) < 0.015F) {
            var23 = var22;
         }

         this.Er2.put(var1, var23);
         int var24 = var18 ? -986896 : -9803158;
         int var25 = Math.max(2, var33 / 5);
         int var26 = Math.max(1, (var33 - var25 * 3) / 2);
         int var27 = var35.ufe + 2;

         for (int var28 = 0; var28 < 3; var28++) {
            this.round(var35.x + (var35.busF - var25) / 2, var27 + var28 * (var25 + var26), var25, var25, var25 / 2.0F, var24);
         }

         int var36 = var14.x - Math.round((1.0F - var23) * 3.0F);
         GuiRect var29 = new GuiRect(var36, var14.ufe, var14.busF, var14.HfS);
         GuiRect var30 = new GuiRect(var36 + 3, var14.ufe + 2, var13, var13);
         GuiRect var31 = new GuiRect(var30.x + var13 + 1, var30.ufe, var13, var13);
         if (var23 > 0.02F) {
            this.round(var29.x, var29.ufe, var29.busF, var29.HfS, 5.0F, this.withAlphaValue(-14408668, Math.round(238.0F * var23)));
            RoundedRect.drawRoundedOutline(
               var29.x, var29.ufe, var29.busF, var29.HfS, 5.0F, 0.1F, new Color(0, 0, 0, 0), new Color(this.withAlphaValue(-1, Math.round(45.0F * var23)), true)
            );
            RenderUtils.drawIconTexture(
               RenderUtils.getIconTexture("/assets/jade/textures/gui/edit.png"),
               var30.x + 2,
               var30.ufe + 2,
               10,
               this.withAlphaValue(var30.contains(var3, var4) ? -986896 : -9803158, Math.round(255.0F * var23))
            );
            this.drawCloseCross(var31, this.withAlphaValue(var31.contains(var3, var4) ? -38037 : -9803158, Math.round(255.0F * var23)), 9.0F, 1.5F);
         }

         if (var23 > 0.45F) {
            this.themeEditButtons.put(var1, var30);
            this.themeDeleteButtons.put(var1, var31);
         }
      }

      RoundedRect.drawRoundedOutline(
         var2.x, var2.ufe, var2.busF, var2.HfS, 6.0F, var5 ? 0.18F : 0.08F, new Color(0, 0, 0, 0), new Color(var5 ? this.getAccentColor() : 889192447, true)
      );
   }

   private void drawPaletteDialog(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.paletteEditorPanelRect = new GuiRect(var1, var2, var3, var4);
      this.round(var1, var2, var3, var4, 7.0F, -15263977);
      RoundedRect.drawRoundedOutline(var1, var2, var3, var4, 7.0F, 0.12F, new Color(0, 0, 0, 0), new Color(1124073471, true));
      RoundedRect.drawRoundedOutline(var1 + 0.45F, var2 + 0.45F, var3 - 0.9F, var4 - 0.9F, 6.55F, 0.08F, new Color(0, 0, 0, 0), new Color(-1342177280, true));
      this.drawHeader(this.editingTheme == null ? "Create Theme" : "Editing Theme \"" + this.editingTheme.getName() + "\"", var1 + 10, var2 + 10, -986896);
      this.paletteEditorCloseButton = new GuiRect(var1 + var3 - 27, var2 + 6, 21, 21);
      this.drawCenteredText("x", this.paletteEditorCloseButton.getCenterX(), this.paletteEditorCloseButton.ufe + 5, this.paletteEditorCloseButton.contains(var5, var6) ? -986896 : -9803158, this.getTitleFont());
      byte var7 = 44;
      int var8 = Math.min(4, this.paletteColors.size() + (this.paletteColors.size() < 4 ? 1 : 0));
      byte var9 = 57;
      int var10 = Math.round(var1 + var3 / 2.0F - ((var8 - 1) * var9 + var7) / 2.0F);
      int var11 = var2 + (var4 - var7) / 2 - 3;

      for (int var12 = 0; var12 < this.paletteColors.size(); var12++) {
         GuiRect var13 = new GuiRect(var10 + var12 * var9, var11, var7, var7);
         this.Uginwv.add(var13);
         boolean var14 = var13.contains(var5, var6);
         if (var14 || this.nwE5 && var12 == this.selectedPaletteIndex) {
            RoundedRect.drawRoundedOutline(var13.x - 2, var13.ufe - 2, var13.busF + 4, var13.HfS + 4, 24.0F, 0.28F, new Color(0, 0, 0, 0), new Color(-1, true));
         }

         this.round(var13.x, var13.ufe, var13.busF, var13.HfS, var7 / 2.0F, this.paletteColors.get(var12));
      }

      if (this.paletteColors.size() < 4) {
         int var15 = this.paletteColors.size();
         GuiRect var16 = new GuiRect(var10 + var15 * var9, var11, var7, var7);
         this.addPaletteColorButtons.add(var16);
         this.drawColorWheelRing(var16.getCenterX(), var16.getCenterY(), var7 / 2.0F - 1.0F, var16.contains(var5, var6) ? -986896 : -9803158);
         this.drawCenteredText(
            "+", var16.getCenterX(), this.centeredTextY(var16.ufe, var16.HfS, this.getTitleFont()), var16.contains(var5, var6) ? -986896 : -6645094, this.getTitleFont()
         );
      }

      this.zeL = new GuiRect(var1 + var3 - 120, var2 + var4 - 31, 110, 23);
      this.drawDialogButton(this.zeL, this.editingTheme == null ? "Create Theme" : "Save / Rename", var5, var6, !this.paletteColors.isEmpty(), true);
      this.drawSmall("Right-click a color to remove it.", var1 + 10, this.centeredTextY(this.zeL.ufe, this.zeL.HfS, this.settingFont()), -9803158);
   }

   private void eycl(GuiRect var1, int var2, int var3) {
      RenderUtils.XNRNki(0.0, 0.0, this.width, this.height, -2046820352);
      int var4 = Math.min(286, var1.busF - 36);
      byte var5 = 116;
      int var6 = var1.x + (var1.busF - var4) / 2;
      int var7 = var1.ufe + (var1.HfS - var5) / 2;
      this.themeNameDialogRect = new GuiRect(var6, var7, var4, var5);
      this.round(var6, var7, var4, var5, 8.0F, -15132391);
      this.edgeOutline(var6, var7, var4, var5, 8.0F);
      this.drawHeader(this.editingTheme == null ? "Name your theme" : "Rename theme", var6 + 14, var7 + 13, -986896);
      this.themeNameFieldRect = new GuiRect(var6 + 14, var7 + 40, var4 - 28, 25);
      this.drawConfigTextInput(this.themeNameFieldRect, this.themeNameInput, "Theme name...", true);
      this.dQuikX = new GuiRect(var6 + 14, var7 + var5 - 34, 72, 23);
      this.themeNameConfirmButton = new GuiRect(var6 + var4 - 82, var7 + var5 - 34, 68, 23);
      this.drawDialogButton(this.dQuikX, "Cancel", var2, var3, true, false);
      this.drawDialogButton(this.themeNameConfirmButton, this.editingTheme == null ? "Create" : "Save", var2, var3, !this.themeNameInput.trim().isEmpty(), true);
   }

   private void drawColorWheelRing(int var1, int var2, float var3, int var4) {
      for (int var5 = 0; var5 < 360; var5 += 36) {
         RenderUtils.drawArc(var1, var2, var3, var5, var5 + 22, 1.25F, var4);
      }
   }

   private void drawCloseCross(GuiRect var1, int var2, float var3, float var4) {
      float var5 = (var2 >> 24 & 0xFF) / 255.0F;
      float var6 = (var2 >> 16 & 0xFF) / 255.0F;
      float var7 = (var2 >> 8 & 0xFF) / 255.0F;
      float var8 = (var2 & 0xFF) / 255.0F;
      GL11.glPushAttrib(1048575);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glLineWidth(var4);
      GL11.glColor4f(var6, var7, var8, var5);
      float var9 = (var1.busF - var3) / 2.0F;
      float var10 = (var1.HfS - var3) / 2.0F;
      GL11.glBegin(1);
      GL11.glVertex2f(var1.x + var9, var1.ufe + var10);
      GL11.glVertex2f(var1.x + var1.busF - var9, var1.ufe + var1.HfS - var10);
      GL11.glVertex2f(var1.x + var1.busF - var9, var1.ufe + var10);
      GL11.glVertex2f(var1.x + var9, var1.ufe + var1.HfS - var10);
      GL11.glEnd();
      GL11.glPopAttrib();
   }

   private void drawPaletteEditor() {
      if (this.selectedPaletteIndex >= 0 && this.selectedPaletteIndex < this.Uginwv.size()) {
         GuiRect var1 = this.Uginwv.get(this.selectedPaletteIndex);
         if (Gui.JzraV3()) {
            ColorPickerPopup var19 = new ColorPickerPopup(var1, this.width, this.height, false);
            var19.EoZct(this, "Palette color", this.paletteHue, this.iOc3, this.paletteBrightness, this.paletteColors.get(this.selectedPaletteIndex) | 0xFF000000);
            this.DVRCt = var19.tnIwm;
            this.mV1 = var19.DSvS;
            this.MAf = var19.UmD;
            this.hexInputButtonRect = var19.confirmButtonBounds;
            this.drawSmall(this.hexColorInput == null ? "" : this.hexColorInput.toUpperCase(Locale.ROOT), var19.confirmButtonBounds.x + 5, var19.confirmButtonBounds.ufe + 4, -1182988);
            if (this.editingHexColor) {
               GuiIcons.drawRectBorder(var19.confirmButtonBounds.x, var19.confirmButtonBounds.ufe, var19.confirmButtonBounds.busF, var19.confirmButtonBounds.HfS, GuiTheme.xGoxa());
            }
         } else {
            short var2 = 168;
            byte var3 = 86;
            int var4 = Math.max(5, Math.min(this.width - var2 - 5, var1.x + var1.busF + 8));
            int var5 = Math.max(5, Math.min(this.height - var3 - 5, var1.ufe - 24));
            this.DVRCt = new GuiRect(var4, var5, var2, var3);
            this.round(var4 + 1, var5 + 2, var2, var3, 9.0F, 1375731712);
            this.drawPopupFrame(var4, var5, var2, var3, 9.0F);
            int var6 = var4 + 10;
            int var7 = var5 + 10;
            byte var8 = 112;
            byte var9 = 66;
            int var10 = var4 + 128;
            byte var11 = 8;
            int var12 = Color.HSBtoRGB(this.paletteHue, 1.0F, 1.0F) | 0xFF000000;
            this.round(var6 - 1, var7 - 1, var8 + 2, var9 + 2, 6.0F, -16185079);
            RoundedRect.drawFourCornerGradientArgb(var6, var7, var8, var9, Gui.JzraV3() ? 0.0F : 5.0F, -16777216, -1, -16777216, var12);
            int var13 = var6 + Math.round(this.iOc3 * var8);
            int var14 = var7 + Math.round((1.0F - this.paletteBrightness) * var9);
            this.drawPickerCursor(var13, var14, this.paletteColors.get(this.selectedPaletteIndex));
            this.drawHueBar(var10, var7, var11, var9);
            this.drawPickerHandle(var10 + var11 / 2, var7 + Math.round(this.paletteHue * var9), true);
            this.hexInputButtonRect = new GuiRect(var4 + 142, var7, 16, var9);
            this.round(this.hexInputButtonRect.x, this.hexInputButtonRect.ufe, this.hexInputButtonRect.busF, this.hexInputButtonRect.HfS, 5.0F, this.editingHexColor ? -14408668 : -15066598);
            String var15 = this.hexColorInput == null ? "" : this.hexColorInput.toUpperCase(Locale.ROOT);
            byte var16 = 8;
            int var17 = this.hexInputButtonRect.ufe + (this.hexInputButtonRect.HfS - var15.length() * var16) / 2;

            for (int var18 = 0; var18 < var15.length(); var18++) {
               this.drawCenteredText(String.valueOf(var15.charAt(var18)), this.hexInputButtonRect.getCenterX(), var17 + var18 * var16, -986896, this.getValueFont());
            }

            this.mV1 = new GuiRect(var6, var7, var8, var9);
            this.MAf = new GuiRect(var10 - 3, var7, var11 + 6, var9);
         }
      }
   }

   private void drawUnsavedThemeDialog(GuiRect var1, int var2, int var3) {
      RenderUtils.XNRNki(0.0, 0.0, this.width, this.height, -2046820352);
      short var4 = 260;
      byte var5 = 104;
      int var6 = var1.x + (var1.busF - var4) / 2;
      int var7 = var1.ufe + (var1.HfS - var5) / 2;
      this.IVYgm = new GuiRect(var6, var7, var4, var5);
      this.round(var6, var7, var4, var5, 9.0F, -15066598);
      this.edgeOutline(var6, var7, var4, var5, 9.0F);
      this.drawHeader("Unsaved theme changes", var6 + 14, var7 + 13, -986896);
      this.drawSmall("Discard them and edit " + this.trimToWidth(this.pendingThemeEdit.getName(), 105, this.settingFont()) + "?", var6 + 14, var7 + 39, -6645094);
      this.discardThemeCancelButton = new GuiRect(var6 + 14, var7 + var5 - 33, 72, 22);
      this.discardThemeConfirmButton = new GuiRect(var6 + var4 - 92, var7 + var5 - 33, 78, 22);
      this.drawDialogButton(this.discardThemeCancelButton, "Cancel", var2, var3, true, false);
      this.UDSOQ(this.discardThemeConfirmButton, "Discard", var2, var3, true, true, true);
   }

   private void drawDeleteThemeDialog(GuiRect var1, int var2, int var3) {
      RenderUtils.XNRNki(0.0, 0.0, this.width, this.height, -2046820352);
      short var4 = 230;
      byte var5 = 94;
      int var6 = var1.x + (var1.busF - var4) / 2;
      int var7 = var1.ufe + (var1.HfS - var5) / 2;
      this.deleteThemeDialogRect = new GuiRect(var6, var7, var4, var5);
      this.round(var6, var7, var4, var5, 9.0F, -15066598);
      this.edgeOutline(var6, var7, var4, var5, 9.0F);
      this.drawHeader("Delete theme?", var6 + 14, var7 + 13, -986896);
      this.drawSmall(this.trimToWidth(this.Metj.getName(), var4 - 28, this.settingFont()), var6 + 14, var7 + 35, -6645094);
      this.Vul = new GuiRect(var6 + 14, var7 + var5 - 32, 72, 22);
      this.vgrxy = new GuiRect(var6 + var4 - 86, var7 + var5 - 32, 72, 22);
      this.drawDialogButton(this.Vul, "Cancel", var2, var3, true, false);
      this.UDSOQ(this.vgrxy, "Delete", var2, var3, true, true, true);
   }

   private void cnP26(int var1, int var2, int var3, int var4, int var5) {
      byte var6 = 5;
      byte var7 = 30;
      byte var8 = 74;
      int var9 = var3 - var7 - var8 - var6 * 2;
      this.createConfigButtonRect = new GuiRect(var1, var2, var9, 28);
      this.VwaR = new GuiRect(var1 + var9 + var6, var2, var8, 28);
      this.openFolderButton = new GuiRect(var1 + var9 + var6 + var8 + var6, var2, var7, 28);
      this.drawIconButton(this.createConfigButtonRect, "Create Config", IconType.CREATE, var4, var5, true, true);
      this.drawIconButton(this.VwaR, "Refresh", IconType.REFRESH, var4, var5, true, true);
      this.drawIconButton(this.openFolderButton, "", IconType.FOLDER, var4, var5, true, false);
   }

   private void drawIconButton(GuiRect var1, String var2, IconType var3, int var4, int var5, boolean var6, boolean var7) {
      boolean var8 = var6 && var1.contains(var4, var5);
      int var9 = var8 ? -14408668 : -15066598;
      this.round(var1.x, var1.ufe, var1.busF, var1.HfS, 7.0F, var9);
      int var10 = var6 ? (var8 ? -986896 : -6645094) : -9803158;
      byte var11 = 14;
      int var12 = var7 ? var1.x + 10 : var1.x + (var1.busF - var11) / 2;
      int var13 = var1.ufe + (var1.HfS - var11) / 2;
      this.drawVectorIcon(var3, var12, var13, var11, var10);
      if (var7) {
         this.drawText(var2, var1.x + 30, this.centeredTextY(var1.ufe, var1.HfS, this.settingFont()), var6 ? -6645094 : -9803158, this.settingFont());
      }
   }

   private void drawConfigProfileRow(ConfigProfile var1, int var2, int var3, int var4, int var5, int var6) {
      boolean var7 = var1.isEnabled();
      boolean var8 = this.isDefaultConfigProfile(var1);
      GuiRect var9 = new GuiRect(var2, var3, var4, 31);
      this.configRowRects.put(var1, var9);
      boolean var10 = var9.contains(var5, var6);
      this.round(var9.x, var9.ufe, var9.busF, var9.HfS, 5.0F, var10 ? -14671840 : -15066598);
      if (var7) {
         RoundedRect.drawFourCornerGradientArgb(var9.x, var9.ufe, Math.min(var9.busF, 180), var9.HfS, 5.0F, this.withAccentAlpha(92), this.withAccentAlpha(70), 0, 0);
      }

      GuiRect var11 = new GuiRect(var9.x + 9, var9.ufe + 8, 15, 15);
      if (var7) {
         this.round(var11.x, var11.ufe, var11.busF, var11.HfS, 4.0F, this.getAccentColor());
         this.drawVectorIcon(IconType.TICK, var11.x + 2, var11.ufe + 2, 11, this.getAccentContrastText());
      }

      GuiRect var12 = new GuiRect(var9.x + 33, var9.ufe + 4, var9.busF - 124, 23);
      this.nam.put(var1, var12);
      if (this.yzh == var1) {
         this.drawConfigTextInput(var12, this.configRenameInput, "Config name...", true);
      } else {
         String var13 = this.trimToWidth(var1.getName(), var12.busF, this.getTitleFont());
         this.drawHeader(this.applyTextCapitalization(var13), var12.x, this.centeredTextY(var9.ufe, var9.HfS, this.getTitleFont()), var7 ? this.getAccentColor() : -6645094);
      }

      byte var21 = 22;
      byte var14 = 4;
      GuiRect var15 = new GuiRect(var9.x + var9.busF - var21 - 8, var9.ufe + 5, var21, var21);
      GuiRect var16 = new GuiRect(var15.x - var14 - var21, var9.ufe + 5, var21, var21);
      boolean var17 = this.bindingConfigProfile == var1 || var1.getKeycode() != 0;
      String var18 = this.bindingConfigProfile == var1 ? "..." : this.getKeyDisplayName(var1.getKeycode());
      int var19 = var17 ? Math.max(22, this.textWidth(var18, this.getValueFont()) + 12) : 22;
      GuiRect var20 = new GuiRect(var16.x - var14 - var19, var9.ufe + 7, var19, 18);
      this.configSaveButtons.put(var1, var16);
      this.PeGs9.put(var1, var15);
      this.configBindButtons.put(var1, var20);
      this.drawConfigBindButton(var20, var1, var5, var6, !var8);
      this.cAtlzz(var16, IconType.SAVE, var5, var6, !var8);
      this.cAtlzz(var15, IconType.DELETE, var5, var6, !var8);
   }

   private void cAtlzz(GuiRect var1, IconType var2, int var3, int var4, boolean var5) {
      boolean var6 = var5 && var1.contains(var3, var4);
      int var7 = var6 ? -14079703 : -15658735;
      this.round(var1.x, var1.ufe, var1.busF, var1.HfS, 5.0F, var7);
      this.drawVectorIcon(var2, var1.x + 5, var1.ufe + 5, 12, var5 ? (var6 ? -986896 : -6645094) : -11776948);
   }

   private void drawConfigBindButton(GuiRect var1, ConfigProfile var2, int var3, int var4, boolean var5) {
      boolean var6 = this.bindingConfigProfile == var2 || var2.getKeycode() != 0;
      boolean var7 = var5 && var1.contains(var3, var4);
      if (var6) {
         String var8 = this.bindingConfigProfile == var2 ? "..." : this.getKeyDisplayName(var2.getKeycode());
         this.drawRoundedPanel(var1.x, var1.ufe, var1.busF, var1.HfS, 4.0F, -15658735);
         this.drawCenteredText(var8, var1.getCenterX(), var1.ufe + 5, var5 ? -9803158 : -11776948, this.getValueFont());
      } else if (var7) {
         byte var9 = 13;
         RenderUtils.drawIconTexture(
            RenderUtils.getIconTexture("/assets/jade/textures/gui/edit.png"),
            var1.x + (var1.busF - var9) / 2,
            var1.ufe + (var1.HfS - var9) / 2,
            var9,
            var5 ? -6645094 : -11776948
         );
      }
   }

   private void drawCreateConfigDialog(GuiRect var1, int var2, int var3) {
      RenderUtils.XNRNki(0.0, 0.0, this.width, this.height, -2046820352);
      short var4 = 230;
      byte var5 = 118;
      int var6 = var1.x + (var1.busF - var4) / 2;
      int var7 = var1.ufe + (var1.HfS - var5) / 2;
      this.Tkzu = new GuiRect(var6, var7, var4, var5);
      this.round(var6 + 2, var7 + 3, var4, var5, 10.0F, 1426063360);
      this.round(var6, var7, var4, var5, 9.0F, -15066598);
      this.edgeOutline(var6, var7, var4, var5, 9.0F);
      this.drawHeader("Create Config", var6 + 14, var7 + 14, -986896);
      this.createConfigNameFieldRect = new GuiRect(var6 + 14, var7 + 42, var4 - 28, 26);
      this.drawConfigTextInput(this.createConfigNameFieldRect, this.duhp5, "Config name...", true);
      this.createConfigCancelButton = new GuiRect(var6 + 14, var7 + var5 - 34, 78, 23);
      this.createConfigConfirmButton = new GuiRect(var6 + var4 - 96, var7 + var5 - 34, 82, 23);
      this.drawDialogButton(this.createConfigCancelButton, "Cancel", var2, var3, true, false);
      this.drawDialogButton(this.createConfigConfirmButton, "Create", var2, var3, true, true);
   }

   private void AzetQ(GuiRect var1, int var2, int var3) {
      RenderUtils.XNRNki(0.0, 0.0, this.width, this.height, -2046820352);
      short var4 = 218;
      byte var5 = 98;
      int var6 = var1.x + (var1.busF - var4) / 2;
      int var7 = var1.ufe + (var1.HfS - var5) / 2;
      this.configActionDialogRect = new GuiRect(var6, var7, var4, var5);
      this.round(var6 + 2, var7 + 3, var4, var5, 10.0F, 1426063360);
      this.round(var6, var7, var4, var5, 9.0F, -15066598);
      this.edgeOutline(var6, var7, var4, var5, 9.0F);
      this.drawHeader(this.getConfigDialogTitle(), var6 + 14, var7 + 12, -986896);
      this.drawConfigDialogMessage(var6 + 14, var7 + 37);
      byte var8 = 3;
      int var9 = (var4 - 28 - var8) / 2;
      this.MeGm = new GuiRect(var6 + 14, var7 + var5 - 30, var9, 22);
      this.dAg = new GuiRect(this.MeGm.x + this.MeGm.busF + var8, var7 + var5 - 30, var4 - 28 - var9 - var8, 22);
      this.drawDialogButton(this.MeGm, "Cancel", var2, var3, true, false);
      this.UDSOQ(this.dAg, this.getConfigDialogConfirmLabel(), var2, var3, true, true, this.configDialogMode == 2);
   }

   private void drawUninjectDialog(GuiRect var1, int var2, int var3) {
      RenderUtils.XNRNki(0.0, 0.0, this.width, this.height, -1610612736);
      short var4 = 306;
      boolean var5 = Gui.JzraV3();
      int var6 = var5 ? (this.uninjectErrorMessage == null && !this.uninjectInProgress ? 98 : 116) : (this.uninjectErrorMessage == null ? 142 : 158);
      int var7 = var1.x + (var1.busF - var4) / 2;
      int var8 = var1.ufe + (var1.HfS - var6) / 2;
      this.uninjectDialogRect = new GuiRect(var7, var8, var4, var6);
      this.round(var7 + 2, var8 + 3, var4, var6, 10.0F, 1426063360);
      this.round(var7, var8, var4, var6, 9.0F, -15066598);
      this.edgeOutline(var7, var8, var4, var6, 9.0F);
      this.nRtk0(this.uninjectInProgress ? "Removing Jade" : "Uninject Jade", var7 + 14, var8 + 12, this.uninjectErrorMessage == null ? -986896 : -33925);
      this.drawSmall(
         this.uninjectInProgress ? "Restoring Minecraft and releasing runtime resources..." : "This removes Jade from the running Minecraft session.",
         var7 + 14,
         var8 + (var5 ? 40 : 45),
         -6645094
      );
      if (this.uninjectInProgress) {
         this.drawSmall("Do not close Minecraft while this completes.", var7 + 14, var8 + (var5 ? 57 : 62), -9803158);
      }

      if (this.uninjectErrorMessage != null) {
         this.drawSmall(this.trimToWidth(this.uninjectErrorMessage, var4 - 28, this.settingFont()), var7 + 14, var8 + (var5 ? 57 : 78), -33925);
      }

      int var9 = var8 + var6 - 35;
      this.uninjectCloseButton = new GuiRect(var7 + 14, var9, 82, 24);
      this.uninjectConfirmButton = new GuiRect(var7 + 102, var9, var4 - 116, 24);
      this.drawDialogButton(this.uninjectCloseButton, this.uninjectErrorMessage == null ? "Cancel" : "Close", var2, var3, !this.uninjectInProgress, false);
      boolean var10 = !this.uninjectInProgress && this.uninjectErrorMessage == null && this.uninjectHoldStart > 0L && this.zzd && this.uninjectConfirmButton.contains(var2, var3);
      if (!var10 && !this.uninjectInProgress) {
         this.uninjectHoldStart = 0L;
         if (this.zzd && !this.uninjectConfirmButton.contains(var2, var3)) {
            this.zzd = false;
         }
      }

      float var11 = var10 ? Math.min(1.0F, (float)(System.currentTimeMillis() - this.uninjectHoldStart) / 2000.0F) : 0.0F;
      this.drawHoldButton(this.uninjectConfirmButton, this.uninjectInProgress ? "Removing..." : "Hold to uninject", var2, var3, !this.uninjectInProgress && this.uninjectErrorMessage == null, this.uninjectInProgress ? 1.0F : var11);
      if (var11 >= 1.0F) {
         this.startUninject();
      }
   }

   private void startUninject() {
      if (!this.uninjectInProgress) {
         this.uninjectInProgress = true;
         this.zzd = false;
         this.uninjectHoldStart = 0L;
         final Minecraft var1 = Minecraft.getMinecraft();
         if (var1 != null) {
            var1.displayGuiScreen(null);
         }

         Jade.ddBk(new BiConsumer<Boolean, String>() {
            public void accept(Boolean var1x, final String var2) {
               if (!Boolean.TRUE.equals(var1x) && var1 != null) {
                  var1.addScheduledTask(new Runnable() {
                     @Override
                     public void run() {
                        JadeClickGui.UgeWb(JadeClickGui.this, false);
                        JadeClickGui.KSnu(JadeClickGui.this, var2);
                        var1.displayGuiScreen(JadeClickGui.this);
                     }
                  });
               }
            }
         });
      }
   }

   private void drawConfigDialogMessage(int var1, int var2) {
      String var3 = this.EVHD == null ? "this config" : this.EVHD.getName();
      String var4 = this.configDialogMode == 2 ? "Delete " : (this.configDialogMode == 3 ? "Load " : "Overwrite ");
      String var5 = this.configDialogMode == 3 ? " and discard changes?" : "?";
      String var6 = this.trimToWidth(var3, 82, this.settingFont());
      this.drawSmall(var4, var1, var2, -6645094);
      int var7 = this.textWidth(var4, this.settingFont());
      this.drawSmall(var6, var1 + var7, var2, this.getAccentColor());
      this.drawSmall(var5, var1 + var7 + this.textWidth(var6, this.settingFont()), var2, -6645094);
   }

   private String getConfigDialogTitle() {
      if (this.configDialogMode == 2) {
         return "Delete Config";
      } else {
         return this.configDialogMode == 3 ? "Unsaved Config" : "Save Config";
      }
   }

   private String QBLAR() {
      String var1 = this.EVHD == null ? "this config" : this.EVHD.getName();
      if (this.configDialogMode == 2) {
         return "Delete " + var1 + "?";
      } else {
         return this.configDialogMode == 3 ? "Load " + var1 + " and discard unsaved changes?" : "Overwrite " + var1 + " with current settings?";
      }
   }

   private String getConfigDialogConfirmLabel() {
      if (this.configDialogMode == 2) {
         return "Delete";
      } else {
         return this.configDialogMode == 3 ? "Load" : "Save";
      }
   }

   private void drawEntrySearchDialog(GuiRect var1, int var2, int var3) {
      RenderUtils.XNRNki(0.0, 0.0, this.width, this.height, -2046820352);
      this.ONuyuP.clear();
      short var4 = 280;
      short var5 = 244;
      int var6 = var1.x + (var1.busF - var4) / 2;
      int var7 = var1.ufe + (var1.HfS - var5) / 2;
      this.QLh = new GuiRect(var6, var7, var4, var5);
      this.round(var6 + 2, var7 + 3, var4, var5, 10.0F, 1426063360);
      this.round(var6, var7, var4, var5, 9.0F, -15066598);
      this.edgeOutline(var6, var7, var4, var5, 9.0F);
      this.drawHeader("Add " + this.activeBlockList.getName(), var6 + 14, var7 + 13, -986896);
      this.Jiwn40 = new GuiRect(var6 + var4 - 30, var7 + 10, 18, 18);
      this.round(this.Jiwn40.x, this.Jiwn40.ufe, this.Jiwn40.busF, this.Jiwn40.HfS, 5.0F, this.Jiwn40.contains(var2, var3) ? -14079703 : -15658735);
      this.drawCenteredText("x", this.Jiwn40.getCenterX(), this.Jiwn40.ufe + 4, -6645094, this.settingFont());
      TextField var8 = this.LGba(this.activeBlockList);
      this.GKCt = new GuiRect(var6 + 14, var7 + 42, var4 - 28, 26);
      var8.kqao7();
      var8.render(this.GKCt.x, this.GKCt.ufe, this.GKCt.x + this.GKCt.busF, this.GKCt.ufe + this.GKCt.HfS);
      int var9 = var7 + 76;
      String var10 = var8.getText();
      if (!var10.trim().isEmpty()) {
         List var11 = this.searchBlockEntries(this.activeBlockList, var10);
         int var12 = var5 - 90;
         int var13 = var11.size() * 22;
         this.clampScroll(this.blockSearchScroll, var13, var12);
         int var14 = Math.round(this.blockSearchScroll.ORMWO());
         boolean var15 = var2 >= var6 + 14 && var2 < var6 + var4 - 14 && var3 >= var9 && var3 < var9 + var12;
         RenderUtils.pushScissorRect(var6 + 14, var9, var4 - 28, var12);

         for (int var16 = 0; var16 < var11.size(); var16++) {
            int var17 = var9 + var16 * 22 - var14;
            if (var17 + 20 >= var9 && var17 < var9 + var12) {
               ItemListEntry var18 = (ItemListEntry)var11.get(var16);
               this.drawItemSearchResultRow(
                  new SettingSearchEntry(this.activeBlockList, var18.ZYWlS, var18.selected),
                  var18.displayName,
                  var18.itemStack,
                  var6 + 14,
                  var17,
                  var4 - 28,
                  var15 ? var2 : -1,
                  var15 ? var3 : -1
               );
            }
         }

         RenderUtils.restoreScissorState();
         this.drawListScrollFade(var6 + 14, var9, var4 - 28, var12, var13, var14);
         if (var11.isEmpty()) {
            this.drawSmall("No matches", var6 + 18, var9 + 5, -9803158);
         }
      }
   }

   private void drawDialogButton(GuiRect var1, String var2, int var3, int var4, boolean var5, boolean var6) {
      this.UDSOQ(var1, var2, var3, var4, var5, var6, false);
   }

   private void UDSOQ(GuiRect var1, String var2, int var3, int var4, boolean var5, boolean var6, boolean var7) {
      boolean var8 = var5 && var1.contains(var3, var4);
      int var9 = var7 ? (var8 ? -2734008 : -4705742) : (var6 ? (var8 ? this.withAccentAlpha(190) : this.withAccentAlpha(150)) : (var8 ? -14079703 : -15066598));
      this.round(var1.x, var1.ufe, var1.busF, var1.HfS, 6.0F, var9);
      int var10 = !var6 && !var7 ? -6645094 : this.getAccentContrastText();
      this.drawCenteredText(var2, var1.getCenterX(), this.centeredTextY(var1.ufe, var1.HfS, this.settingFont()), var5 ? var10 : -9803158, this.settingFont());
   }

   private void drawHoldButton(GuiRect var1, String var2, int var3, int var4, boolean var5, float var6) {
      boolean var7 = var5 && var1.contains(var3, var4);
      this.round(var1.x, var1.ufe, var1.busF, var1.HfS, 6.0F, var7 ? -12377052 : -14017508);
      int var8 = Math.round(var1.busF * Math.max(0.0F, Math.min(1.0F, var6)));
      if (var8 > 0) {
         RenderUtils.pushScissorRect(var1.x, var1.ufe, var8, var1.HfS);
         this.round(var1.x, var1.ufe, var1.busF, var1.HfS, 6.0F, var7 ? -1549734 : -3260095);
         RenderUtils.restoreScissorState();
      }

      this.drawCenteredText(
         var2,
         var1.getCenterX(),
         this.centeredTextY(var1.ufe, var1.HfS, this.settingFont()),
         !var5 && !(var6 > 0.0F) ? -9803158 : this.getAccentContrastText(),
         this.settingFont()
      );
   }

   private void drawListScrollFade(int var1, int var2, int var3, int var4, int var5, int var6) {
      if (var5 > var4) {
         int var7 = Math.min(36, Math.max(18, var4 / 4));
         int var8 = Math.max(0, var5 - var4);
         if (var6 > 1) {
            RenderUtils.drawVerticalGradient(var1 - 1, var2 - 1, var1 + var3 + 1, var2 + var7, this.withAlphaValue(-15066598, 190), this.withAlphaValue(-15066598, 0));
         }

         if (var6 < var8 - 1) {
            RenderUtils.drawVerticalGradient(var1 - 1, var2 + var4 - var7, var1 + var3 + 1, var2 + var4 + 1, this.withAlphaValue(-15066598, 0), this.withAlphaValue(-15066598, 190));
         }
      }
   }

   @Override
   protected void drawConfigTextInput(GuiRect var1, String var2, String var3, boolean var4) {
      this.round(var1.x, var1.ufe, var1.busF, var1.HfS, 7.0F, var4 ? -15395563 : -15658735);
      if (var4) {
         if (Gui.JzraV3()) {
            GuiIcons.drawRectBorder(var1.x, var1.ufe, var1.busF, var1.HfS, GuiTheme.xGoxa());
         } else {
            RoundedRect.drawRoundedOutline(var1.x, var1.ufe, var1.busF, var1.HfS, 7.0F, 0.12F, new Color(0, 0, 0, 0), new Color(-1761607681, true));
         }
      }

      String var5 = var2 == null ? "" : var2;
      String var6 = var5.isEmpty() ? var3 : var5;
      int var7 = var5.isEmpty() ? -11184811 : -986896;
      int var8 = var1.busF - 16;
      this.drawText(
         this.trimToWidth(var6, var8, this.settingFont()), var1.x + 8, this.centeredTextY(var1.ufe, var1.HfS, this.settingFont()), var7, this.settingFont()
      );
      if (var4 && System.currentTimeMillis() / 500L % 2L == 0L) {
         int var9 = var1.x + 8 + Math.min(var8, this.textWidth(this.trimToWidth(var5, var8, this.settingFont()), this.settingFont())) + 2;
         RenderUtils.XNRNki(var9, var1.ufe + 7, var9 + 1, var1.ufe + var1.HfS - 7, -6645094);
      }
   }

   private List<ConfigProfile> getFilteredConfigProfiles() {
      ArrayList var1 = new ArrayList();
      if (Jade.configManager != null && Jade.configManager.profiles != null) {
         for (ConfigEntry var3 : Jade.configManager.profiles) {
            if (var3 != null && var3.getProfile() != null) {
               ConfigProfile var4 = var3.getProfile();
               if (this.normalizedSearch().isEmpty() || this.searchScopedToCategory || this.matchesText(var4.getName(), this.normalizedSearch())) {
                  var1.add(var4);
               }
            }
         }

         Collections.sort(var1, this::compareConfigProfiles);
         return var1;
      } else {
         return var1;
      }
   }

   private boolean isDefaultConfigProfile(ConfigProfile var1) {
      return var1 != null && "default".equalsIgnoreCase(var1.getName());
   }

   private void drawModuleList(List<Module> var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      RenderUtils.pushScissorRect(var2 - 1, var3 - 1, var4 + 2, var5 + 2);
      int var9 = var3 - var6;

      for (Module var11 : var1) {
         int var12 = this.getAnimatedModuleHeight(var11, var4);
         this.drawModuleRow(var11, var2, var9, var4, var12, var7, var8);
         var9 += var12 + 5;
      }

      RenderUtils.restoreScissorState();
   }

   @Override
   protected void drawScrollFade(Category var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      if (var6 > var5) {
         int var8 = Math.min(58, Math.max(34, var5 / 4));
         int var9 = Math.max(0, var6 - var5);
         float var10 = this.animateTowards(this.getCategoryAnimationKey(this.topScrollFadeKeys, var1), var7 > 1 ? 1.0F : 0.0F);
         float var11 = this.animateTowards(this.getCategoryAnimationKey(this.elmSla, var1), var7 < var9 - 1 ? 1.0F : 0.0F);
         if (var10 > 0.01F) {
            int var12 = Math.round(224.0F * var10);
            RenderUtils.drawVerticalGradient(var2 - 1, var3 - 1, var2 + var4 + 1, var3 + var8, this.withAlphaValue(-15658735, var12), this.withAlphaValue(-15658735, 0));
         }

         if (var11 > 0.01F) {
            int var13 = Math.round(224.0F * var11);
            RenderUtils.drawVerticalGradient(var2 - 1, var3 + var5 - var8, var2 + var4 + 1, var3 + var5 + 1, this.withAlphaValue(-15658735, 0), this.withAlphaValue(-15658735, var13));
         }
      }
   }

   private void drawSidebarScrollFade(GuiRect var1, int var2, int var3, int var4, int var5) {
      if (var4 > var3) {
         int var6 = Math.min(24, Math.max(16, var3 / 5));
         int var7 = Math.max(0, var4 - var3);
         if (var5 > 1) {
            RenderUtils.drawVerticalGradient(var1.x, var2, var1.x + this.sidebarW(), var2 + var6, -15066598, this.withAlphaValue(-15066598, 0));
         }

         if (var5 < var7 - 1) {
            RenderUtils.drawVerticalGradient(var1.x, var2 + var3 - var6, var1.x + this.sidebarW(), var2 + var3, this.withAlphaValue(-15066598, 0), -15066598);
         }
      }
   }

   private void drawClientCategoryButton(GuiRect var1, int var2, int var3) {
      GuiRect var4 = this.aIrlh6(var1);
      boolean var5 = this.currentCategory == Category.client;
      boolean var6 = var4.contains(var2, var3);
      float var7 = this.animateTowards(this.WHb, var5 ? 1.0F : 0.0F);
      if (var7 > 0.01F) {
         this.drawSidebarHighlight(var4, var7);
      }

      if (!var5 && var6) {
         this.round(var4.x, var4.ufe, var4.busF, var4.HfS, 7.0F, -14079703);
      }

      byte var8 = 18;
      int var9 = var5 ? this.getAccentContrastText() : -6645094;
      this.drawCategoryIcon(Category.client, var4.x + (var4.busF - var8) / 2, var4.ufe + (var4.HfS - var8) / 2, var8, var9);
      this.drawHudButton(var1, var2, var3);
      this.drawPowerButton(var1, var2, var3);
   }

   private void drawHudButton(GuiRect var1, int var2, int var3) {
      GuiRect var4 = this.getHudButtonRect(var1);
      float var5 = this.animateTowards(this.pgpJ, var4.contains(var2, var3) ? 1.0F : 0.0F);
      if (var5 > 0.01F) {
         this.round(var4.x, var4.ufe, var4.busF, var4.HfS, 7.0F, this.withAlphaValue(2697513, Math.round(var5 * 255.0F)));
      }

      byte var6 = 17;
      this.drawVectorIcon(IconType.HUD, var4.x + (var4.busF - var6) / 2, var4.ufe + (var4.HfS - var6) / 2, var6, var5 > 0.55F ? -986896 : -6645094);
   }

   private void drawPowerButton(GuiRect var1, int var2, int var3) {
      GuiRect var4 = this.FHLMmH(var1);
      float var5 = this.animateTowards(this.powerButtonHoverKey, var4.contains(var2, var3) ? 1.0F : 0.0F);
      if (var5 > 0.01F) {
         this.round(var4.x, var4.ufe, var4.busF, var4.HfS, 7.0F, this.withAlphaValue(5906464, Math.round(var5 * 255.0F)));
      }

      byte var6 = 11;
      this.drawVectorIcon(IconType.POWER, var4.x + (var4.busF - var6) / 2, var4.ufe + (var4.HfS - var6) / 2, var6, var5 > 0.55F ? -30070 : -6645094);
   }

   private void drawSidebarHighlight(GuiRect var1, float var2) {
      int var3 = Math.round(138.0F * Math.max(0.0F, Math.min(1.0F, var2)));
      this.round(var1.x, var1.ufe + 2, var1.busF, var1.HfS - 4, 7.0F, this.withAccentAlpha(var3));
   }

   public void clearDropdownGeometry() {
      this.clearDropdownGeometryCaches();
   }

   private DropdownLayoutManager Byvzw7() {
      if (this.dropdownClientRenderer == null) {
         this.dropdownClientRenderer = new DropdownLayoutManager();
      }

      return this.dropdownClientRenderer;
   }

   public int dropdownClientHeight(int var1) {
      return this.Byvzw7().measureColumnHeight(this, var1);
   }

   public void drawDropdownClient(int var1, int var2, int var3, int var4, int var5) {
      this.Byvzw7().drawDropdownColumn(this, var1, var2, var3, var4, var5);
   }

   public void bindDropdownGui(Module var1) {
      this.bindingModule = var1;
   }

   public List<Module> dropdownModules(Category var1) {
      return this.KsF6(var1);
   }

   public boolean dropdownMatchesSearch(Module var1) {
      String var2 = this.normalizedSearch();
      return var2.isEmpty() || this.moduleMatchesSearch(var1, var2) || var1.getCategory() == Category.movement && this.matchesText("Movement", var2);
   }

   public void focusDropdownSearch() {
      this.MldK();
      this.dropdownLayoutState.closeOpenCategory();
      this.searchFocused = true;
   }

   public void drawDropdownSearch(GuiRect var1) {
      byte var2 = 12;
      int var3 = var1.x + 9;
      int var4 = var1.ufe + (var1.HfS - var2) / 2;
      this.drawSearchIcon(var3, var4, var2, this.searchFocused ? -1182988 : -4208434);
      IFont var5 = this.settingFont();
      int var6 = var1.x + 27;
      float var7 = this.centeredTextY(var1.ufe, var1.HfS, var5);
      int var8 = var1.busF - 34;
      if (!this.searchText.isEmpty()) {
         String var9 = this.trimToWidth(this.searchText, var8, var5);
         int var10 = Math.min(var8, this.textWidth(var9, var5));
         if (this.searchFocused && this.Namm5) {
            RenderUtils.XNRNki(var6 - 1, var1.ufe + 5, var6 + var10 + 1, var1.ufe + var1.HfS - 5, 1442840575);
         }

         this.drawSmall(var9, var6, var7, -986896);
         if (this.searchFocused && !this.Namm5 && System.currentTimeMillis() / 500L % 2L == 0L) {
            RenderUtils.XNRNki(var6 + var10 + 1, var1.ufe + 6, var6 + var10 + 2, var1.ufe + var1.HfS - 6, -1182988);
         }
      } else if (this.searchFocused) {
         if (System.currentTimeMillis() / 500L % 2L == 0L) {
            RenderUtils.XNRNki(var6, var1.ufe + 6, var6 + 1, var1.ufe + var1.HfS - 6, -1182988);
         }
      } else {
         this.drawSmall("Search", var6, var7, -6645094);
      }

      if (this.searchFocused) {
         RenderUtils.XNRNki(var1.x, var1.ufe + var1.HfS - 1, var1.x + var1.busF, var1.ufe + var1.HfS, GuiTheme.xGoxa());
      }
   }

   public void drawDropdownCategoryIcon(Category var1, int var2, int var3, int var4, int var5) {
      this.kwGva.drawCategoryToolbarIcon(var1, var2, var3, var4, var5);
   }

   public void drawDropdownManager(Category var1, GuiRect var2, int var3, int var4) {
      this.currentCategory = var1;
      if (var1 == Category.profiles) {
         this.drawConfigsPanel(var2, var3, var4);
      } else if (var1 == Category.themes) {
         this.drawThemesPanel(var2, var3, var4);
      } else if (var1 == Category.friends) {
         this.drawDropdownRelationshipsManager(var2, var3, var4);
      }
   }

   public void openDropdownHud() {
      this.MldK();
      this.searchFocused = false;
      this.mc.displayGuiScreen(new HudEditorScreen(this));
   }

   public void openDropdownDisconnect() {
      this.MldK();
      this.searchFocused = false;
      this.uninjectDialogOpen = true;
      this.uninjectErrorMessage = null;
      this.zzd = false;
      this.uninjectHoldStart = 0L;
   }

   public float dropdownTransition(Object var1, float var2) {
      SmoothedFloat var3 = this.transitionCache.get(var1);
      if (var3 == null) {
         var3 = new SmoothedFloat(var2);
         this.transitionCache.put(var1, var3);
      }

      return var3.smoothTowards(var2);
   }

   public void endDropdownFade(BlurFramebufferCapture var1, float var2) {
      var1.drawCapturedFrame(var2, this.mc.displayWidth / this.Osi, this.mc.displayHeight / this.Osi);
   }

   public void drawDropdownTitle(String var1, float var2, float var3, int var4) {
      this.drawText(var1, var2, var3, var4, this.settingFont());
   }

   public void drawDropdownExpand(GuiRect var1, boolean var2) {
      VectorIconPainter.drawExpandIndicator(var1.getCenterX(), var1.getCenterY(), var2, -4208434);
   }

   public int dropdownBindWidth(String var1) {
      return Math.round(this.settingFont().getStringWidth(var1) * 1.1F * 0.85F);
   }

   public void drawDropdownBind(String var1, GuiRect var2) {
      float var3 = 0.93500006F;
      IFont var4 = this.settingFont();
      float var5 = (var4.getTextBottomOffset() - var4.getTextTopOffset()) * var3;
      float var6 = var2.ufe + (var2.HfS - var5) / 2.0F - var4.getTextTopOffset() * var3;
      GL11.glPushMatrix();
      GL11.glTranslatef(var2.getCenterX() - this.dropdownBindWidth(var1) / 2.0F, var6, 0.0F);
      GL11.glScalef(var3, var3, 1.0F);
      var4.drawString(var1, 0.0F, 0.0F, -1182988, true);
      GL11.glPopMatrix();
   }

   public int dropdownModuleHeight(Module var1, int var2) {
      int var3 = 4;

      for (Setting var5 : this.getVisibleModuleSettings(var1)) {
         var3 += this.settingHeight(var5, var2) + 2;
      }

      return 21 + Math.round(var3 * this.dropdownTransition(var1, this.IyqlY.contains(var1) ? 1.0F : 0.0F));
   }

   public int drawDropdownSettingRow(Module var1, Setting var2, int var3, int var4, int var5, int var6, int var7) {
      return this.drawSetting(var1, var2, var3, var4, var5, var6, var7) + 2;
   }

   public String dropdownBindLabel(Module var1) {
      return this.bindingModule == var1 ? "..." : (var1.getKeycode() == 0 ? "" : this.getKeyDisplayName(var1.getKeycode()));
   }

   public void drawDropdownModule(Module var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      byte var8 = 21;
      GuiRect var9 = new GuiRect(var2, var3, var4, var8);
      this.umR.put(var1, var9);
      if (var1.isEnabled()) {
         RenderUtils.XNRNki(var2, var3, var2 + var4, var3 + var8, GuiTheme.withAlpha(124));
      }

      if (var9.contains(var6, var7)) {
         RenderUtils.XNRNki(var2, var3, var2 + var4, var3 + var8, 822083583);
      }

      ZIdxbY.drawGlowLine(var2 + 3, var3 + var8 - 0.65F, var4 - 6, 950974140);
      GuiRect var10 = new GuiRect(var2 + var4 - 19, var3, 19, var8);
      boolean var11 = var9.contains(var6, var7);
      String var12 = this.dropdownBindLabel(var1);
      int var13 = var12.isEmpty() ? 0 : Math.max(16, this.dropdownBindWidth(var12) + 6);
      GuiRect var14 = new GuiRect(var10.x - var13, var3, var13, var8);
      if (!var12.isEmpty()) {
         this.moduleBindButtons.put(var1, var14);
         this.drawDropdownBind(var12, var14);
      }

      int var15 = var14.x;
      if (var11) {
         GuiRect var16 = new GuiRect(var15 - 17, var3, 17, var8);
         this.moduleHiddenToggleRects.put(var1, var16);
         VectorIconPainter.YRSmn(var16.getCenterX(), var16.getCenterY(), var1.isHidden(), -1182988);
         var15 = var16.x;
      }

      this.drawDropdownTitle(
         this.trimToWidth(var1.getName(), var15 - var2 - 20, this.settingFont()),
         var2 + 4,
         this.centeredTextY(var3, var8, this.settingFont()),
         var1.isEnabled() ? -986896 : -4208434
      );
      if (DangerousModules.isListedDangerous(var1)) {
         WarningIconRenderer.VyqlhA(Math.min(var15 - 12, var2 + 8 + this.textWidth(var1.getName(), this.settingFont())), var3 + (var8 - 8) / 2.0F);
      }

      this.moduleExpandButtonRects.put(var1, var10);
      this.drawDropdownExpand(var10, this.IyqlY.contains(var1));
      if (var5 > var8) {
         this.gvH.MeQu(new GuiRect(var2, var3 + var8, var4, var5 - var8), this.IyqlY.contains(var1) && this.dropdownMatchesSearch(var1));
         RenderUtils.pushScissorRect(var2, var3 + var8, var4, var5 - var8);
         RenderUtils.XNRNki(var2, var3 + var8, var2 + var4, var3 + var5, -1509357303);
         int var19 = var3 + var8 + 2;

         for (Setting var18 : this.getVisibleModuleSettings(var1)) {
            var19 += this.drawDropdownSettingRow(var1, var18, var2, var19, var4, var6, var7);
         }

         RenderUtils.restoreScissorState();
         this.gvH.s172();
      }
   }

   private void drawModuleRow(Module var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      float var8 = this.getModuleExpandProgress(var1);
      boolean var9 = var8 > 0.02F;
      boolean var10 = var1.isEnabled();
      boolean var11 = new GuiRect(var2, var3, var4, 31).contains(var6, var7);
      int var12 = var11 && !var9 ? -14671840 : -15066598;
      float var13 = var9 ? 5.0F : 4.0F;
      this.round(var2, var3, var4, var5, var13, var12);
      float var14 = this.animateTowards(this.getModuleAnimationKey(var1), var10 ? 1.0F : 0.0F);
      if (var14 > 0.01F) {
         int var15 = Math.max(0, Math.min(31, var5));
         int var16 = Math.min(var4, Math.max(250, Math.round(var4 * 0.84F)));
         int var17 = Math.round(138.0F * var14);
         RenderUtils.pushScissorRect(var2, var3, var4, var15);
         RoundedRect.drawFourCornerGradientArgb(var2, var3, var16, var5, var13, this.withAccentAlpha(var17), this.withAccentAlpha(var17), 0, 0);
         RenderUtils.restoreScissorState();
      }

      if (var9) {
         RenderUtils.XNRNki(var2 + 14, var3 + 31 + 4, var2 + var4 - 14, var3 + 31 + 5, 352321535);
      }

      GuiRect var22 = new GuiRect(var2, var3, var4, 31);
      this.umR.put(var1, var22);
      this.drawHeader(this.applyTextCapitalization(var1.getName()), var2 + 13, var3 + 11, var10 ? -986896 : -6645094);
      if (DangerousModules.isListedDangerous(var1)) {
         WarningIconRenderer.VyqlhA(var2 + 17 + this.textWidth(this.applyTextCapitalization(var1.getName()), this.getTitleFont()), var3 + 11);
      }

      GuiRect var23 = new GuiRect(var2 + var4 - 68, var3 + 6, 22, 18);
      if (this.bindingModule != var1 && var1.getKeycode() == 0) {
         if (var11) {
            RenderUtils.drawIconTexture(RenderUtils.getIconTexture("/assets/jade/textures/gui/edit.png"), var23.x + 4, var23.ufe + 2, 13, -4671304);
         }
      } else {
         String var24 = this.bindingModule == var1 ? "..." : this.getKeyDisplayName(var1.getKeycode());
         int var18 = Math.max(22, this.textWidth(var24, this.getValueFont()) + 12);
         var23 = new GuiRect(var2 + var4 - 50 - var18, var3 + 6, var18, 18);
         this.drawRoundedPanel(var23.x, var23.ufe, var23.busF, var23.HfS, 4.0F, -15658735);
         this.drawCenteredText(var24, var23.getCenterX(), var23.ufe + 5, -9803158, this.getValueFont());
      }

      if (this.bindingModule == var1 || var1.getKeycode() != 0 || var11) {
         this.moduleBindButtons.put(var1, var23);
      }

      if (var11 && var1.getCategory() != Category.profiles && var1.getCategory() != Category.client) {
         GuiRect var25 = new GuiRect(var23.x - 22, var3 + 6, 18, 18);
         this.moduleHiddenToggleRects.put(var1, var25);
         RenderUtils.drawIconTexture(var1.isHidden() ? this.getHiddenEyeIcon() : this.Xwui(), var25.x + 3, var25.ufe + 3, 12, -11842741);
      }

      GuiRect var26 = new GuiRect(var2 + var4 - 38, var3 + 9, 22, 12);
      this.moduleToggleRects.put(var1, var26);
      this.IKGFu(var26.x, var26.ufe, var10, var1.canBeEnabled(), var26.busF, var26.HfS, var1);
      if (var9) {
         RenderUtils.pushScissorRect(var2, var3 + 31, var4, Math.max(0, var5 - 31));
         int var27 = var3 + 31 + 8;
         List var19 = this.getVisibleModuleSettings(var1);
         if (var19.isEmpty()) {
            this.drawSmall("No settings", var2 + 18, var27, -9803158);
            RenderUtils.restoreScissorState();
         } else {
            for (Setting var21 : (java.lang.Iterable<Setting>) (java.lang.Iterable<?>) (var19)) {
               var27 += this.drawSetting(var1, var21, var2 + 10, var27, var4 - 20, var6, var7);
               var27 += 2;
            }

            RenderUtils.restoreScissorState();
         }
      }
   }

   public int drawSetting(Module var1, Setting var2, int var3, int var4, int var5, int var6, int var7) {
      if (Gui.JzraV3()) {
         var3 += 7;
         var5 -= 14;
         int var8 = this.drawModernSettingRow(var1, var2, var3, var4, var5, var6, var7);
         if (var8 >= 0) {
            return var8;
         }
      }

      if (var2 instanceof GroupSetting) {
         this.drawSmall(var2.getName().toUpperCase(), var3, var4 + 4, Gui.JzraV3() ? -1 : (var1.isEnabled() ? -6645094 : -9803158));
         RenderUtils.XNRNki(var3, var4 + 19, var3 + var5, var4 + 20, 352321535);
         return 22;
      } else if (var2 instanceof DescriptionSetting) {
         return 0;
      } else if (var2 instanceof BooleanSetting) {
         return this.drawBooleanSetting(var1, (BooleanSetting)var2, var3, var4, var5);
      } else if (var2 instanceof MultiSelectSetting) {
         return this.Udv2(var1, (MultiSelectSetting)var2, var3, var4, var5);
      } else if (var2 instanceof SliderSetting) {
         return this.drawSliderSetting(var1, (SliderSetting)var2, var3, var4, var5);
      } else if (var2 instanceof ColorSetting) {
         return this.drawColorSetting(var1, (ColorSetting)var2, var3, var4, var5);
      } else if (var2 instanceof KeySetting) {
         return this.drawKeySetting(var1, (KeySetting)var2, var3, var4, var5);
      } else if (var2 instanceof TextSetting) {
         return this.drawTextSetting((TextSetting)var2, var3, var4, var5);
      } else if (var2 instanceof StringListSetting) {
         return this.drawStringListSetting((StringListSetting)var2, var3, var4, var5, var6, var7);
      } else if (var2 instanceof RelationListSetting) {
         return this.drawRelationshipListSetting((RelationListSetting)var2, var3, var4, var5, var6, var7);
      } else if (var2 instanceof BlockListSetting) {
         return this.drawBlockListSetting((BlockListSetting)var2, var3, var4, var5, var6, var7);
      } else if (var2 instanceof CategoryListSetting) {
         return this.drawCategoryListSetting((CategoryListSetting)var2, var3, var4, var5);
      } else {
         this.drawSmall(var2.getName(), var3, var4 + 5, -6645094);
         return 24;
      }
   }

   private int drawModernSettingRow(Module var1, Setting var2, int var3, int var4, int var5, int var6, int var7) {
      byte var8 = 21;
      if (var2 instanceof BooleanSetting) {
         BooleanSetting var23 = (BooleanSetting)var2;
         GuiRect var26 = new GuiRect(var3 - 4, var4, var5 + 8, var8);
         this.NCOdbl.put(var23, var26);
         if (var26.contains(var6, var7)) {
            GuiIcons.fillRect(var26.x, var4, var26.busF, var8, 822083583);
         }

         String var29 = var23.isButton ? var23.getButtonText() : "";
         int var32 = this.textWidth(var29, this.settingFont());
         this.drawSmall(
            this.trimToWidth(var2.getName(), var5 - var32 - 12, this.settingFont()), var3, var4 + 5, var23.isToggled() && !var23.isButton ? this.getAccentColor() : -6645094
         );
         if (DangerousModules.Vupme(var1, var2.getName())) {
            WarningIconRenderer.VyqlhA(var3 + Math.min(var5 - var32 - 24, this.textWidth(var2.getName(), this.settingFont()) + 4), var4 + (var8 - 8) / 2.0F);
         }

         this.drawSmall(var29, var3 + var5 - var32, var4 + 5, !var23.isButton && !var23.isToggled() ? -6645094 : this.getAccentColor());
         return var8;
      } else if (var2 instanceof MultiSelectSetting) {
         MultiSelectSetting var22 = (MultiSelectSetting)var2;
         BooleanSetting[] var25 = var22.awwHd();
         int var28 = var25 == null ? 0 : var25.length;
         this.multiSelectHeaderRects.put(var22, new GuiRect(var3 - 4, var4, var5 + 8, var8));
         GuiIcons.fillRect(var3 - 4, var4, var5 + 8, var8, -435154928);
         this.drawSmall(this.trimToWidth(var2.getName(), var5 - 56, this.settingFont()), var3, var4 + 5, -1);
         int var31 = 0;
         if (var25 != null) {
            for (BooleanSetting var40 : var25) {
               if (var40 != null && var40.isToggled()) {
                  var31++;
               }
            }
         }

         String var35 = var22.isExpanded() ? "-" : var31 + "/" + var28 + " +";
         this.drawSmall(var35, var3 + var5 - this.textWidth(var35, this.settingFont()), var4 + 5, this.getAccentColor());
         int var37 = Math.round(var28 * var8 * this.dropdownTransition(var22, var22.isExpanded() ? 1.0F : 0.0F));
         if (var37 <= 0) {
            return var8;
         } else {
            RenderUtils.pushScissorRect(var3 - 4, var4 + var8, var5 + 8, var37);
            GuiIcons.fillRect(var3 - 4, var4 + var8, var5 + 8, var37, -435154928);
            int var39 = var4 + var8;
            if (var25 != null) {
               for (BooleanSetting var44 : var25) {
                  if (var44 != null) {
                     GuiRect var20 = new GuiRect(var3 - 4, var39, var5 + 8, Math.max(0, Math.min(var8, var4 + var8 + var37 - var39)));
                     if (var20.HfS > 0) {
                        this.eJ7.put(var44, var20);
                     }

                     if (var20.contains(var6, var7)) {
                        GuiIcons.fillRect(var3 - 4, var39, var5 + 8, var8, 822083583);
                     }

                     this.drawSmall(
                        this.trimToWidth(var22.getLabelFor(var44), var5 - 14, this.settingFont()), var3 + 4, var39 + 5, var44.isToggled() ? this.getAccentColor() : -6645094
                     );
                     if (DangerousModules.Vupme(var1, var22.getLabelFor(var44)) || DangerousModules.Vupme(var1, var44.getName())) {
                        WarningIconRenderer.VyqlhA(var3 + var5 - 14, var39 + (var8 - 8) / 2.0F);
                     }
                  }

                  var39 += var8;
               }
            }

            RenderUtils.restoreScissorState();
            return var8 + var37;
         }
      } else if (var2 instanceof SliderSetting && ((SliderSetting)var2).isMode) {
         SliderSetting var21 = (SliderSetting)var2;
         String[] var24 = var21.getOptions();
         int var27 = (int)var21.getInput();
         int var30 = var24 == null ? 0 : Math.max(0, var24.length - 1);
         int var33 = Math.round(var30 * var8 * this.getModeSliderExpandProgress(var21));
         this.UKsT.put(var21, new GuiRect(var3 - 4, var4, var5 + 8, var8));
         GuiIcons.fillRect(var3 - 4, var4, var5 + 8, var8, -435154928);
         String var14 = this.trimToWidth(this.getModeOptionLabel(var21), var5 / 2 - 10, this.settingFont());
         int var15 = this.textWidth(var14, this.settingFont());
         this.drawSmall(this.trimToWidth(var21.getName(), var5 - var15 - 20, this.settingFont()), var3, var4 + 5, -1);
         this.drawSmall(var14, var3 + var5 - var15 - 10, var4 + 5, this.getAccentColor());
         if (DangerousModules.Vupme(var1, this.getModeOptionLabel(var21))) {
            WarningIconRenderer.VyqlhA(var3 + var5 - var15 - 22, var4 + (var8 - 8) / 2.0F);
         }

         this.drawSmall(this.expandedModeSliders.contains(var21) ? "-" : "+", var3 + var5 - 6, var4 + 5, this.getAccentColor());
         ArrayList var16 = new ArrayList();
         RenderUtils.pushScissorRect(var3 - 4, var4 + var8, var5 + 8, var33);
         GuiIcons.fillRect(var3 - 4, var4 + var8, var5 + 8, var33, -435154928);
         int var17 = var4 + var8;
         if (var24 != null && var33 > 0) {
            for (int var18 = 0; var18 < var24.length; var18++) {
               if (var18 != var27) {
                  GuiRect var19 = new GuiRect(var3 - 4, var17, var5 + 8, Math.max(0, Math.min(var8, var4 + var8 + var33 - var17)));
                  if (var19.HfS > 0) {
                     var16.add(new OptionRowHitbox(var19, var18));
                  }

                  if (var19.contains(var6, var7)) {
                     GuiIcons.fillRect(var3 - 4, var17, var5 + 8, var8, 822083583);
                  }

                  this.drawSmall(this.trimToWidth(var24[var18], var5 - 12, this.settingFont()), var3 + 4, var17 + 5, -6645094);
                  if (DangerousModules.Vupme(var1, var24[var18])) {
                     WarningIconRenderer.VyqlhA(var3 + var5 - 14, var17 + (var8 - 8) / 2.0F);
                  }

                  var17 += var8;
               }
            }
         }

         RenderUtils.restoreScissorState();
         this.modeSliderOptionRects.put(var21, var16);
         return var8 + var33;
      } else if (var2 instanceof ColorSetting) {
         ColorSetting var9 = (ColorSetting)var2;
         GuiRect var10 = new GuiRect(var3, var4, var5, var8);
         this.cr2.put(var9, var10);
         this.gvH.registerComponentBounds(var9, var10);
         this.drawSmall(this.trimToWidth(var2.getName(), var5 - 83, this.settingFont()), var3, var4 + 5, -6645094);
         String var11 = this.formatColorHex(var9);
         GuiRect var12 = new GuiRect(var3 + var5 - 80, var4, 59, var8);
         this.colorValueFieldRects.put(var9, var12);
         if (this.gdj == var9) {
            this.YEXZj(var9).render(var12.x, var12.ufe, var12.x + var12.busF, var12.ufe + var8);
         } else {
            this.drawSmall(var11, var12.x + 1, var4 + 5, this.getAccentColor());
         }

         GuiRect var13 = new GuiRect(var3 + var5 - 16, var4 + 5, 16, 11);
         RenderUtils.XNRNki(var13.x, var13.ufe, var13.x + var13.busF, var13.ufe + var13.HfS, var9.getArgb() | 0xFF000000);
         this.colorPickerRects.put(var9, new ColorPickerBounds(var13));
         return var8;
      } else {
         return -1;
      }
   }

   private int drawCategoryListSetting(CategoryListSetting var1, int var2, int var3, int var4) {
      int var5 = var3;

      for (int var6 = 0; var6 < 9; var6++) {
         this.drawSmall("Slot " + (var6 + 1), var2 + 4, this.centeredTextY(var5, 22.0F, this.settingFont()), -6645094);
         int var8 = var2 + 36;
         int var9 = var2 + var4 - 4;
         int var10 = var5 + 4;
         int var11 = var8;
         List var12 = var1.getCategoriesForSlot(var6);

         for (int var13 = 0; var13 < var12.size(); var13++) {
            String var14 = (String)var12.get(var13);
            String var15 = CategoryListSetting.getCategoryDisplayName(var14);
            int var16 = this.textWidth(var15, this.settingFont()) + 16;
            if (var11 + var16 > var9) {
               break;
            }

            GuiRect var17 = new GuiRect(var11, var10, var16, 14);
            this.categorySlotChipRects.put(new CategoryListItemKey(var1, var6, var13), var17);
            this.drawRoundedPanel(var17.x, var17.ufe, var17.busF, var17.HfS, 4.0F, -15658735);
            this.drawLightOverlay(var17.x, var17.ufe, var17.busF, var17.HfS, 4.0F, this.withAccentAlpha(50));
            this.drawCenteredText(var15, var17.getCenterX(), var17.ufe + 3, this.getAccentColor(), this.settingFont());
            var11 += var16 + 4;
         }

         if (!var1.MCweS(var6) && var11 + 18 <= var9) {
            boolean var25 = this.OmaV == var1 && this.openCategorySlotIndex == var6;
            GuiRect var27 = new GuiRect(var11, var10, 18, 14);
            this.categorySlotAddButtons.put(new CategoryEntryKey(var1, var6), var27);
            this.drawRoundedPanel(var27.x, var27.ufe, var27.busF, var27.HfS, 4.0F, -15658735);
            if (var25) {
               this.drawLightOverlay(var27.x, var27.ufe, var27.busF, var27.HfS, 4.0F, this.withAccentAlpha(80));
            }

            this.drawCenteredText(var25 ? "x" : "+", var27.getCenterX(), var27.ufe + 3, var25 ? this.getAccentColor() : -6645094, this.settingFont());
         }

         var5 += 22;
         if (this.OmaV == var1 && this.openCategorySlotIndex == var6) {
            int var26 = var5;
            int var28 = ovbT;
            this.drawRoundedPanel(var2 + 4, var5, var4 - 8, var28, 5.0F, -15658735);
            int var29 = CategoryListSetting.keM.length;
            int var30 = (var4 - 16) / 2;

            for (int var31 = 0; var31 < var29; var31++) {
               int var18 = var31 % 2;
               int var19 = var31 / 2;
               int var20 = var2 + 8 + var18 * var30;
               int var21 = var26 + 3 + var19 * 18;
               GuiRect var22 = new GuiRect(var20, var21, var30 - 2, 17);
               this.categoryPickerOptionRects.put(new CategoryListOptionKey(var1, var6, var31), var22);
               boolean var23 = var1.getCategoriesForSlot(var6).contains(CategoryListSetting.keM[var31]);
               int var24 = var23 ? -9803158 : -6645094;
               if (var23) {
                  this.drawLightOverlay(var22.x, var22.ufe, var22.busF, var22.HfS, 3.0F, this.withAccentAlpha(20));
               }

               this.drawSmall(CategoryListSetting.JQZs[var31], var22.x + 6, this.centeredTextY(var22.ufe, var22.HfS, this.settingFont()), var24);
            }

            int var32 = (var29 + 2 - 1) / 2;
            int var33 = var26 + 3 + var32 * 18;
            GuiRect var34 = new GuiRect(var2 + 8, var33, var4 - 16, 17);
            this.categoryPickerOptionRects.put(new CategoryListOptionKey(var1, var6, var29), var34);
            this.drawSmall("Clear", var34.x + 6, this.centeredTextY(var34.ufe, var34.HfS, this.settingFont()), -36752);
            var5 += var28;
         }
      }

      return var5 - var3 + 4;
   }

   private int drawBooleanSetting(Module var1, BooleanSetting var2, int var3, int var4, int var5) {
      GuiRect var6 = new GuiRect(var3, var4, var5, 22);
      this.NCOdbl.put(var2, var6);
      boolean var7 = this.isModuleSettingsEnabled(var1);
      int var8 = !var7 ? -9803158 : (!var2.isButton && !var2.isToggled() ? -6645094 : -986896);
      this.drawSmall(var2.getName(), var3, var4 + 5, var8);
      if (DangerousModules.Vupme(var1, var2.getName())) {
         WarningIconRenderer.VyqlhA(var3 + 4 + this.textWidth(var2.getName(), this.settingFont()), var4 + 5);
      }

      if (var2.isButton) {
         String var9 = var2.getButtonText();
         int var10 = Math.max(36, this.textWidth(var9, this.settingFont()) + 14);
         this.drawRoundedPanel(var3 + var5 - var10, var4 + 3, var10, 16.0F, 5.0F, -15658735);
         this.drawFaintOverlay(var3 + var5 - var10, var4 + 3, var10, 16.0F, 5.0F, 685431514);
         this.drawCenteredText(var9, var3 + var5 - var10 / 2, var4 + 7, -6645094, this.settingFont());
      } else {
         this.IKGFu(var3 + var5 - 24, var4 + 6, var2.isToggled(), var7, 20, 11, var2);
      }

      return 22;
   }

   private int Udv2(Module var1, MultiSelectSetting var2, int var3, int var4, int var5) {
      boolean var6 = this.isModuleSettingsEnabled(var1);
      BooleanSetting[] var7 = var2.awwHd();
      int var8 = var7 == null ? 0 : var7.length;
      int var9 = var8 > 6 ? 2 : 1;
      int var10 = var9 == 2 ? (var8 + 1) / 2 : var8;
      int var11 = var2.isExpanded() ? 34 + var10 * 20 : 24;
      GuiRect var12 = new GuiRect(var3, var4, var5, var11);
      this.multiSelectHeaderRects.put(var2, new GuiRect(var3, var4, var5, 24));
      this.drawRoundedPanel(var12.x, var12.ufe, var12.busF, var12.HfS, 5.0F, var6 ? -15658735 : -16053493);
      this.drawLightOverlay(var12.x, var12.ufe, var12.busF, var12.HfS, 5.0F, var6 ? this.withAccentAlpha(26) : this.yVsd(36));
      this.drawSmall(var2.getName(), var3 + 8, this.centeredTextY(var4, 24.0F, this.settingFont()), var6 ? -986896 : -6645094);
      String var13 = this.trimToWidth(var2.getSummaryText(), Math.max(30, var5 - this.textWidth(var2.getName(), this.settingFont()) - 36), this.settingFont());
      this.drawText(
         var13,
         var3 + var5 - 10 - this.textWidth(var13, this.settingFont()),
         this.centeredTextY(var4, 24.0F, this.settingFont()),
         var6 ? -6645094 : -9803158,
         this.settingFont()
      );
      if (!var2.isExpanded()) {
         return var11 + 2;
      } else {
         int var14 = var4 + 30;
         int var15 = 0;

         for (BooleanSetting var19 : var7) {
            if (var19 == null) {
               var15++;
            } else {
               int var20 = var9 == 2 ? var15 % 2 : 0;
               int var21 = var9 == 2 ? var15 / 2 : var15;
               int var22 = var9 == 2 ? 8 : 0;
               int var23 = var9 == 2 ? (var5 - 12 - var22) / 2 : var5 - 12;
               int var24 = var3 + 6 + var20 * (var23 + var22);
               GuiRect var25 = new GuiRect(var24, var14 + var21 * 20, var23, 18);
               this.eJ7.put(var19, var25);
               this.drawSmall(
                  var2.getLabelFor(var19), var25.x + 22, this.centeredTextY(var25.ufe, var25.HfS, this.settingFont()), var19.isToggled() ? -986896 : -6645094
               );
               if (DangerousModules.Vupme(var1, var2.getLabelFor(var19)) || DangerousModules.Vupme(var1, var19.getName())) {
                  WarningIconRenderer.VyqlhA(var25.x + var25.busF - 14, var25.ufe + (var25.HfS - 8) / 2.0F);
               }

               GuiRect var26 = new GuiRect(var25.x + 4, var25.ufe + 3, 12, 12);
               this.round(var26.x, var26.ufe, var26.busF, var26.HfS, 3.0F, var19.isToggled() ? this.withAccentAlpha(210) : -15066598);
               if (var19.isToggled()) {
                  this.drawVectorIcon(IconType.TICK, var26.x + 2, var26.ufe + 2, 8, -1);
               }

               var15++;
            }
         }

         return var11 + 2;
      }
   }

   private int drawSliderSetting(Module var1, SliderSetting var2, int var3, int var4, int var5) {
      boolean var6 = this.isModuleSettingsEnabled(var1);
      int var7 = var6 ? -986896 : -6645094;
      if (var2.isMode) {
         this.drawSmall(var2.getName(), var3, var4 + 1, var6 ? -6645094 : -9803158);
         String[] var22 = var2.getOptions();
         int var9 = var22 == null ? 0 : Math.max(0, Math.min(var22.length - 1, (int)Math.round(var2.getInput())));
         int var23 = var22 == null ? 0 : Math.max(0, var22.length - 1);
         float var24 = this.getModeSliderExpandProgress(var2);
         int var25 = var23 * 17 + 5;
         int var27 = 22 + Math.round(var25 * var24);
         GuiRect var29 = new GuiRect(var3, var4 + 16, var5, var27);
         this.drawRoundedPanel(var29.x, var29.ufe, var29.busF, var29.HfS, 5.0F, var6 ? -15658735 : -16053493);
         this.drawLightOverlay(var29.x, var29.ufe, var29.busF, var29.HfS, 5.0F, var6 ? this.withAccentAlpha(36) : this.yVsd(44));
         String var15 = this.getModeOptionLabel(var2);
         this.drawText(var15, var29.x + 8, this.centeredTextY(var29.ufe, 22.0F, this.settingFont()), var6 ? -986896 : -6645094, this.settingFont());
         if (DangerousModules.Vupme(var1, var15)) {
            WarningIconRenderer.VyqlhA(var29.x + var29.busF - 16, var29.ufe + (var29.HfS - 8) / 2.0F);
         }

         this.UKsT.put(var2, var29);
         int var30 = var27 + 17;
         if (var24 > 0.02F && var22 != null) {
            ArrayList var31 = new ArrayList();
            int var18 = var29.ufe + 22;
            RenderUtils.pushScissorRect(var29.x, var18, var29.busF, Math.max(0, var29.HfS - 22));
            RenderUtils.XNRNki(var29.x + 6, var18 - 1, var29.x + var29.busF - 6, var18, 285212671);
            int var19 = 0;

            for (int var20 = 0; var20 < var22.length; var20++) {
               if (var20 != var9) {
                  GuiRect var21 = new GuiRect(var3 + 3, var18 + 2 + var19 * 17, var5 - 6, 15);
                  var31.add(new OptionRowHitbox(var21, var20));
                  this.drawSmall(var22[var20], var21.x + 6, this.centeredTextY(var21.ufe, var21.HfS, this.settingFont()), var6 ? -6645094 : -9803158);
                  if (DangerousModules.Vupme(var1, var22[var20])) {
                     WarningIconRenderer.VyqlhA(var21.x + var21.busF - 14, var21.ufe + (var21.HfS - 8) / 2.0F);
                  }

                  var19++;
               }
            }

            RenderUtils.restoreScissorState();
            this.modeSliderOptionRects.put(var2, var31);
         }

         return var30;
      } else {
         double var8 = this.getAnimatedSliderValue(var2);
         String var10 = this.formatSliderValue(var2.applyValueCurve(var8), var2) + DegreeSymbols.applyDegreeSymbol(var2.getSuffix());
         int var11 = this.textWidth(var10, this.settingFont());
         this.drawSmall(Gui.JzraV3() ? this.trimToWidth(var2.getName(), var5 - var11 - 12, this.settingFont()) : var2.getName(), var3, var4 + 8, var7);
         GuiRect var12 = new GuiRect(var3 + var5 - var11 - 4, var4 + 4, var11 + 8, 20);
         this.Ei2.put(var2, var12);
         if (this.editingSlider == var2) {
            TextField var13 = this.getSliderTextField(var2);
            var13.render(var12.x, var12.ufe, var12.x + var12.busF, var12.ufe + var12.HfS);
         } else {
            this.drawText(var10, var3 + var5 - var11, var4 + 8, var6 ? -6645094 : -9803158, this.settingFont());
         }

         GuiRect var26 = new GuiRect(var3, var4 + 22, var5, 7);
         this.UKsT.put(var2, var26);
         if (Gui.JzraV3() && this.draggingSlider == var2) {
            this.activeSliderTrackRect = var26;
         }

         this.round(var26.x, var26.ufe + 2, var26.busF, 3.0F, 1.5F, var6 ? -16053493 : -16316665);
         double var14 = var2.toFraction(var8);
         var14 = Math.max(0.0, Math.min(1.0, var14));
         int var16 = Gui.JzraV3() ? SliderMath.fractionToSliderOffset(var26.busF, var14) : (int)Math.round(var26.busF * var14);
         this.round(var26.x, var26.ufe + 2, var16, 3.0F, 1.5F, var6 ? this.getAccentColor() : this.bgZk());
         int var17 = var26.x + var16;
         if (Gui.JzraV3()) {
            RenderUtils.XNRNki(var17 - 2, var26.ufe + 1, var17 + 3, var26.ufe + 6, -986896);
         } else {
            this.round(var17 - 4, var26.ufe - 1, 9.0F, 9.0F, 4.5F, var6 ? -986896 : -11908534);
         }

         return 32;
      }
   }

   private boolean isModuleSettingsEnabled(Module var1) {
      return true;
   }

   private int drawColorSetting(Module var1, ColorSetting var2, int var3, int var4, int var5) {
      boolean var6 = this.isModuleSettingsEnabled(var1);
      this.cr2.put(var2, new GuiRect(var3, var4, var5, 30));
      this.drawSmall(var2.getName(), var3, this.centeredTextY(var4, 30.0F, this.settingFont()), var6 ? -986896 : -6645094);
      GuiRect var7 = new GuiRect(var3 + var5 - 20, var4 + 5, 18, 18);
      this.BSdoOnk(var2, var7.x, var7.ufe, var7.busF, var7.HfS, 4.0F);
      String var8 = this.formatColorHex(var2);
      int var9 = this.textWidth(var8, this.getValueFont());
      GuiRect var10 = new GuiRect(var7.x - var9 - 12, var4 + 4, var9 + 8, 20);
      this.colorValueFieldRects.put(var2, var10);
      if (this.gdj == var2) {
         TextField var11 = this.YEXZj(var2);
         var11.render(var10.x, var10.ufe, var10.x + var10.busF, var10.ufe + var10.HfS);
      } else {
         this.drawText(var8, var7.x - var9 - 8, this.centeredTextY(var4, 30.0F, this.getValueFont()), -6645094, this.getValueFont());
      }

      this.colorPickerRects.put(var2, new ColorPickerBounds(var7));
      return 30;
   }

   private void drawOpenColorPicker() {
      ColorSetting var1 = this.getOpenPickerColorSetting();
      if (var1 == null) {
         this.XYIJhz = null;
      } else {
         GuiRect var2 = this.cr2.get(var1);
         ColorPickerBounds var3 = this.colorPickerRects.get(var1);
         if (Gui.JzraV3()) {
            var2 = this.gvH.hOzmx(var1);
            if (var2 == null) {
               this.closeColorPickerPopup();
               return;
            }

            var3 = new ColorPickerBounds(new GuiRect(var2.x + var2.busF - 16, var2.ufe + 5, 16, 11));
            this.colorPickerRects.put(var1, var3);
         }

         if (var2 == null || var3 == null) {
            this.XYIJhz = null;
         } else if (Gui.JzraV3()) {
            ColorPickerPopup var23 = new ColorPickerPopup(var2, this.width, this.height, var1.supportsAlpha());
            var23.EoZct(this, var1.getName(), var1.getHue() / 360.0F, var1.getSaturation(), var1.pBf3(), var1.getArgb());
            this.XYIJhz = var23.tnIwm;
            var3.dobk = var23.DSvS;
            var3.hueSliderBounds = var23.UmD;
            var3.alphaSliderBounds = var23.uKl;
            this.colorValueFieldRects.put(var1, var23.confirmButtonBounds);
            if (this.gdj == var1) {
               this.YEXZj(var1).render(var23.confirmButtonBounds.x, var23.confirmButtonBounds.ufe, var23.confirmButtonBounds.x + var23.confirmButtonBounds.busF, var23.confirmButtonBounds.ufe + var23.confirmButtonBounds.HfS);
            } else {
               this.drawSmall(this.formatColorHex(var1), var23.confirmButtonBounds.x + 5, var23.confirmButtonBounds.ufe + 4, -1182988);
            }
         } else {
            short var4 = 152;
            int var5 = var1.supportsAlpha() ? 114 : 86;
            int var6 = var2.x + var2.busF + 7;
            if (var6 + var4 > this.width - 5) {
               var6 = var2.x - var4 - 7;
            }

            var6 = Math.max(5, Math.min(this.width - var4 - 5, var6));
            int var7 = Math.max(5, Math.min(this.height - var5 - 5, var2.ufe - 8));
            this.XYIJhz = new GuiRect(var6, var7, var4, var5);
            this.round(var6 + 1, var7 + 2, var4, var5, 9.0F, 1375731712);
            this.drawPopupFrame(var6, var7, var4, var5, 9.0F);
            int var8 = var6 + 10;
            int var9 = var7 + 10;
            int var10 = var4 - 40;
            byte var11 = 66;
            int var12 = var6 + var4 - 20;
            byte var13 = 8;
            int var14 = Color.HSBtoRGB(var1.getHue() / 360.0F, 1.0F, 1.0F) | 0xFF000000;
            this.round(var8 - 1, var9 - 1, var10 + 2, var11 + 2, 6.0F, -16185079);
            RoundedRect.drawFourCornerGradientArgb(var8, var9, var10, var11, Gui.JzraV3() ? 0.0F : 5.0F, -16777216, -1, -16777216, var14);
            int var15 = var8 + Math.round(var1.getSaturation() * var10);
            int var16 = var9 + Math.round((1.0F - var1.pBf3()) * var11);
            this.drawPickerCursor(var15, var16, var1.getArgb());
            this.drawHueBar(var12, var9, var13, var11);
            int var17 = var9 + Math.round(var1.getHue() / 360.0F * var11);
            this.drawPickerHandle(var12 + var13 / 2, var17, true);
            var3.dobk = new GuiRect(var8, var9, var10, var11);
            var3.hueSliderBounds = new GuiRect(var12 - 3, var9, var13 + 6, var11);
            if (var1.supportsAlpha()) {
               int var18 = var9 + var11 + 6;
               this.drawSmall("Opacity", var8, var18, -6645094);
               String var19 = Math.round(var1.JIjrD() / 255.0F * 100.0F) + "%";
               this.drawText(var19, var6 + var4 - 10 - this.textWidth(var19, this.settingFont()), var18, -6645094, this.settingFont());
               int var20 = var18 + 12;
               int var21 = var4 - 20;
               this.drawCheckerboardTexture(var8, var20, var21, 8.0F, 4.0F);
               RoundedRect.drawVerticalGradient(var8, var20, var21, 8.0F, 4.0F, new Color(var1.getRgb(), true), new Color(0xFF000000 | var1.getRgb(), true));
               int var22 = var8 + Math.round(var1.JIjrD() / 255.0F * var21);
               this.drawPickerHandle(var22, var20 + 4, false);
               var3.alphaSliderBounds = new GuiRect(var8, var20 - 4, var21, 16);
            }
         }
      }
   }

   private void drawPickerCursor(int var1, int var2, int var3) {
      this.round(var1 - 4, var2 - 4, 8.0F, 8.0F, 4.0F, -1);
      this.round(var1 - 2, var2 - 2, 4.0F, 4.0F, 2.0F, var3 | 0xFF000000);
   }

   private void drawPickerHandle(int var1, int var2, boolean var3) {
      if (var3) {
         RoundedRect.drawRoundedOutline(var1 - 7, var2 - 3, 14.0F, 6.0F, 3.0F, 0.7F, new Color(0, 0, 0, 0), Color.WHITE);
      } else {
         RoundedRect.drawRoundedOutline(var1 - 3, var2 - 6, 6.0F, 12.0F, 3.0F, 0.7F, new Color(0, 0, 0, 0), Color.WHITE);
      }
   }

   private void drawCheckerboardTexture(float var1, float var2, float var3, float var4, float var5) {
      this.mc.getTextureManager().bindTexture(this.SaopcJt());
      GL11.glTexParameteri(3553, 10241, 9728);
      GL11.glTexParameteri(3553, 10240, 9728);
      GL11.glTexParameteri(3553, 10242, 10497);
      GL11.glTexParameteri(3553, 10243, 10497);
      RoundedRect.drawRoundedTextureRegion(var1, var2, var3, var4, var5, 1.0F, 0.0F, 0.0F, var3 / 8.0F, var4 / 8.0F);
   }

   private void BSdoOnk(ColorSetting var1, float var2, float var3, float var4, float var5, float var6) {
      JadeClickGui$3 var7 = this.colorPreviewTextures.get(var1);
      if (var7 == null) {
         DynamicTexture var8 = new DynamicTexture(8, 8);
         ResourceLocation var9 = this.mc.getTextureManager().getDynamicTextureLocation("jade_color_preview", var8);
         var7 = new JadeClickGui$3(var8, var9);
         this.colorPreviewTextures.put(var1, var7);
      }

      int var18 = var1.getArgb();
      if (!JadeClickGui$3.isTextureUploaded(var7) || JadeClickGui$3.getCachedColor(var7) != var18) {
         int var19 = var1.JIjrD();
         int var10 = 255 - var19;
         int[] var11 = JadeClickGui$3.GNUb(var7).getTextureData();

         for (int var12 = 0; var12 < 8; var12++) {
            for (int var13 = 0; var13 < 8; var13++) {
               int var14 = (var13 / 4 + var12 / 4 & 1) == 0 ? 208 : 146;
               int var15 = (var14 * var10 + var1.getRed() * var19) / 255;
               int var16 = (var14 * var10 + var1.getGreen() * var19) / 255;
               int var17 = (var14 * var10 + var1.getBlue() * var19) / 255;
               var11[var12 * 8 + var13] = 0xFF000000 | var15 << 16 | var16 << 8 | var17;
            }
         }

         JadeClickGui$3.GNUb(var7).updateDynamicTexture();
         JadeClickGui$3.setCachedColor(var7, var18);
         JadeClickGui$3.setTextureUploaded(var7, true);
      }

      this.mc.getTextureManager().bindTexture(JadeClickGui$3.getResourceLocation(var7));
      GL11.glTexParameteri(3553, 10241, 9728);
      GL11.glTexParameteri(3553, 10240, 9728);
      GL11.glTexParameteri(3553, 10242, 10497);
      GL11.glTexParameteri(3553, 10243, 10497);
      RoundedRect.drawRoundedTextureRegion(var2, var3, var4, var5, var6, 1.0F, 0.0F, 0.0F, var4 / 8.0F, var5 / 8.0F);
   }

   private void drawPopupFrame(float var1, float var2, float var3, float var4, float var5) {
      float var6 = 0.28F;
      this.round(var1, var2, var3, var4, var5, 889192447);
      this.round(var1 + var6, var2 + var6, var3 - var6 * 2.0F, var4 - var6 * 2.0F, var5 - var6, -536344568);
      this.round(var1 + var6 * 2.0F, var2 + var6 * 2.0F, var3 - var6 * 4.0F, var4 - var6 * 4.0F, var5 - var6 * 2.0F, -15066598);
   }

   private ResourceLocation SaopcJt() {
      if (this.checkerboardTexture == null) {
         BufferedImage var1 = new BufferedImage(8, 8, 2);

         for (int var2 = 0; var2 < 8; var2++) {
            for (int var3 = 0; var3 < 8; var3++) {
               var1.setRGB(var2, var3, (var2 / 4 + var3 / 4 & 1) == 0 ? -3092272 : -7171438);
            }
         }

         this.checkerboardTexture = this.mc.getTextureManager().getDynamicTextureLocation("jade_color_checkerboard", new DynamicTexture(var1));
      }

      return this.checkerboardTexture;
   }

   private int drawKeySetting(Module var1, KeySetting var2, int var3, int var4, int var5) {
      this.drawSmall(var2.getName(), var3, var4 + 8, this.isModuleSettingsEnabled(var1) ? -986896 : -6645094);
      String var6 = this.bindingKeySetting == var2 ? "..." : var2.ukYzs();
      int var7 = Math.max(48, this.textWidth(var6, this.getValueFont()) + 16);
      GuiRect var8 = new GuiRect(var3 + var5 - var7, var4 + 4, var7, 20);
      this.keySettingButtons.put(var2, var8);
      this.drawRoundedPanel(var8.x, var8.ufe, var8.busF, var8.HfS, 5.0F, -15658735);
      this.drawCenteredText(var6, var8.getCenterX(), var8.ufe + 6, -6645094, this.getValueFont());
      return 30;
   }

   private int drawTextSetting(TextSetting var1, int var2, int var3, int var4) {
      this.drawSmall(var1.getName(), var2, var3 + 2, -6645094);
      TextField var5 = this.textSettingFields.get(var1);
      if (var5 == null) {
         var5 = new TextField(var1.getPlaceholder(), var1.xmB5(), 1.1F);
         var5.setText(var1.getValue());
         this.textSettingFields.put(var1, var5);
      }

      var5.kqao7();
      GuiRect var6 = new GuiRect(var2, var3 + 20, var4, 24);
      this.textSettingFieldRects.put(var1, var6);
      var5.render(var6.x, var6.ufe, var6.x + var6.busF, var6.ufe + var6.HfS);
      return 50;
   }

   private int drawStringListSetting(StringListSetting var1, int var2, int var3, int var4, int var5, int var6) {
      this.drawSmall(var1.getName(), var2, var3 + 2, -6645094);
      TextField var7 = this.stringListFields.get(var1);
      if (var7 == null) {
         var7 = new TextField(var1.getInputHint(), var1.getMaxEntryLength(), 1.1F);
         this.stringListFields.put(var1, var7);
      }

      var7.kqao7();
      GuiRect var8 = new GuiRect(var2, var3 + 20, var4, 24);
      this.ofU.put(var1, var8);
      var7.render(var8.x, var8.ufe, var8.x + var8.busF, var8.ufe + var8.HfS);
      int var9 = var3 + 50;
      int var10 = 0;

      for (String var12 : var1.getEntries()) {
         if (var10 >= 4) {
            break;
         }

         this.MEHocve(new udUfwO(var1, var12), var12, var2, var9, var4, var5, var6);
         var9 += 22;
         var10++;
      }

      return Math.max(54, var9 - var3 + 2);
   }

   private int drawRelationshipListSetting(RelationListSetting var1, int var2, int var3, int var4, int var5, int var6) {
      this.drawSmall(var1.getName(), var2, var3 + 2, -6645094);
      int var7 = var3 + 22;
      int var8 = 0;

      for (RelationManager$0 var10 : var1.getRelations()) {
         if (var8 >= 5) {
            break;
         }

         this.MEHocve(new udUfwO(var1, var10.ZNKxZ()), var10.ZNKxZ(), var2, var7, var4, var5, var6);
         var7 += 22;
         var8++;
      }

      if (var8 == 0) {
         this.drawSmall("Empty", var2, var7 + 4, -9803158);
         var7 += 22;
      }

      return var7 - var3 + 4;
   }

   private int drawBlockListSetting(BlockListSetting var1, int var2, int var3, int var4, int var5, int var6) {
      boolean var7 = Gui.JzraV3() && this.supportsEntrySearchDialog(var1);
      this.drawSmall(
         var7 ? this.trimToWidth(var1.getName(), var4 - 25, this.settingFont()) : var1.getName(),
         var2,
         var7 ? this.centeredTextY(var3, 21.0F, this.settingFont()) : var3 + 2,
         var7 ? -1 : -6645094
      );
      if (this.supportsEntrySearchDialog(var1)) {
         int var16 = this.textWidth(this.trimToWidth(var1.getName(), var4 - 25, this.settingFont()), this.settingFont());
         GuiRect var17 = var7 ? new GuiRect(var2 + var16 + 4, var3, 21, 21) : new GuiRect(var2, var3 + 20, var4, 24);
         this.CNc.put(var1, var17);
         if (var7) {
            float var18 = this.hoverAnimations.getHoverProgress(var1, "add", var17.contains(var5, var6));
            this.drawCenteredText(
               "+",
               var17.getCenterX(),
               this.centeredTextY(var17.ufe, var17.HfS, this.settingFont()),
               var18 > 0.01F ? this.withAlphaValue(this.getAccentColor(), Math.round(180.0F + 75.0F * var18)) : -6645094,
               this.settingFont()
            );
         } else {
            this.round(var17.x, var17.ufe, var17.busF, var17.HfS, 5.0F, -15658735);
            this.drawSmall("+ Add", var17.x + 8, this.centeredTextY(var17.ufe, var17.HfS, this.settingFont()), -6645094);
         }

         List var19 = var1 instanceof ItemSlotListSetting ? ((ItemSlotListSetting)var1).getItems() : (var1 instanceof ItemListSetting ? ((ItemListSetting)var1).getItems() : var1.getEntries());
         int var21 = var3 + (var7 ? 23 : 50);
         int var23 = 0;
         int var25 = var1 instanceof ItemColorListSetting ? Integer.MAX_VALUE : 4;

         for (String var29 : (java.lang.Iterable<String>) (java.lang.Iterable<?>) (var19)) {
            if (var23 >= var25) {
               break;
            }

            this.drawBlockListEntryRow(var1, var29, var2, var21, var4, var5, var6);
            var21 += 22;
            var23++;
         }

         if (var23 == 0) {
            this.drawSmall("Empty", var2, var21 + 4, -9803158);
            var21 += 22;
         }

         return var7 ? var21 - var3 + 2 : Math.max(54, var21 - var3 + 2);
      } else {
         TextField var8 = this.Mj7.get(var1);
         if (var8 == null) {
            var8 = new TextField(this.ENmj(var1), 96, 1.1F);
            this.Mj7.put(var1, var8);
         }

         var8.kqao7();
         GuiRect var9 = new GuiRect(var2, var3 + 20, var4, 24);
         this.CNc.put(var1, var9);
         var8.render(var9.x, var9.ufe, var9.x + var9.busF, var9.ufe + var9.HfS);
         int var10 = var3 + 50;
         if (this.activeBlockList == var1 && var1 instanceof ItemListSetting && !var8.getText().trim().isEmpty()) {
            int var11 = 0;

            for (ItemMatcher$0 var13 : ItemMatcher.getSuggestions(var8.getText(), (ItemListSetting)var1)) {
               if (var11 >= 4) {
                  break;
               }

               String var14 = var13.isSingleItem() ? var13.variants.get(0).hvDbs : var13.getWildcardId();
               String var15 = var13.isSingleItem() ? var13.variants.get(0).Qwi : var13.getDisplayNameWithCount();
               this.endu5(new SettingSearchEntry(var1, var14), var15, var2, var10, var4);
               var10 += 22;
               var11++;
            }
         }

         List var20;
         if (var1 instanceof ItemSlotListSetting) {
            var20 = ((ItemSlotListSetting)var1).getItems();
         } else if (var1 instanceof ItemListSetting) {
            var20 = ((ItemListSetting)var1).getItems();
         } else if (var1 instanceof NameListSetting) {
            var20 = ((NameListSetting)var1).getEntryList();
         } else {
            var20 = var1.getEntries();
         }

         int var22 = 0;
         int var24 = var1 instanceof ItemColorListSetting ? Integer.MAX_VALUE : 4;

         for (String var28 : (java.lang.Iterable<String>) (java.lang.Iterable<?>) (var20)) {
            if (var22 >= var24) {
               break;
            }

            this.drawBlockListEntryRow(var1, var28, var2, var10, var4, var5, var6);
            var10 += 22;
            var22++;
         }

         if (var22 == 0) {
            this.drawSmall("Empty", var2, var10 + 4, -9803158);
            var10 += 22;
         }

         return Math.max(54, var10 - var3 + 2);
      }
   }

   private String ENmj(BlockListSetting var1) {
      if (var1 instanceof NameListSetting) {
         return "e.g. speed";
      } else {
         return var1 instanceof ItemListSetting ? "Search items..." : "Search blocks...";
      }
   }

   private void MEHocve(udUfwO var1, String var2, int var3, int var4, int var5, int var6, int var7) {
      GuiRect var8 = new GuiRect(var3, var4, var5, 20);
      this.drawRoundedPanel(var8.x, var8.ufe, var8.busF, var8.HfS, 4.0F, -15658735);
      GuiRect var9 = Gui.JzraV3() ? new GuiRect(var3 + var5 - 22, var4, 22, var8.HfS) : new GuiRect(var3 + var5 - 20, var4 + 3, 14, 14);
      int var10 = Gui.JzraV3() ? this.hoverAnimations.renderHoverRow(var1.setting, var1.qakJ0, var8, var9, var6, var7) : -9803158;
      String var11 = this.trimToWidth(var2, var5 - 32, this.settingFont());
      this.drawSmall(var11, var3 + 7, var4 + 6, -6645094);
      this.listEntryRemoveButtons.put(var1, var9);
      this.drawCenteredText(
         "x", var9.getCenterX(), Gui.JzraV3() ? this.centeredTextY(var9.ufe, var9.HfS, this.settingFont()) : var9.ufe + 3, var10, this.settingFont()
      );
   }

   private void endu5(SettingSearchEntry var1, String var2, int var3, int var4, int var5) {
      GuiRect var6 = new GuiRect(var3, var4, var5, 20);
      this.drawRoundedPanel(var6.x, var6.ufe, var6.busF, var6.HfS, 4.0F, -14408668);
      String var7 = this.trimToWidth(var2, var5 - 32, this.settingFont());
      this.drawSmall(var7, var3 + 7, var4 + 6, -6645094);
      this.ONuyuP.put(var1, var6);
      GuiRect var8 = new GuiRect(var3 + var5 - 20, var4 + 3, 14, 14);
      this.drawCenteredText("+", var8.getCenterX(), var8.ufe + 3, -9803158, this.settingFont());
   }

   private void drawBlockListEntryRow(BlockListSetting var1, String var2, int var3, int var4, int var5, int var6, int var7) {
      GuiRect var8 = new GuiRect(var3, var4, var5, 20);
      if (this.hasEntryColors(var1)) {
         this.colorChipRightClickRects.put(new zyojmoh5(var1, var2, false), var8);
      }

      this.drawRoundedPanel(var8.x, var8.ufe, var8.busF, var8.HfS, 4.0F, -15658735);
      GuiRect var9 = Gui.JzraV3() ? new GuiRect(var3 + var5 - 22, var4, 22, var8.HfS) : new GuiRect(var3 + var5 - 20, var4 + 3, 14, 14);
      int var10 = Gui.JzraV3() ? this.hoverAnimations.renderHoverRow(var1, var2, var8, var9, var6, var7) : -9803158;
      int var11 = this.hasEntryColors(var1) ? (var1 instanceof ItemColorListSetting ? 52 : 39) : 32;
      if (var1 instanceof ItemColorListSetting && !this.isNametagColorList(var1)) {
         var11 = 39;
      }

      String var12 = this.trimToWidth(this.MyUrzHe(var1, var2), var5 - var11, this.settingFont());
      this.drawSmall(var12, var3 + 7, var4 + 6, -6645094);
      if (var1 instanceof ItemColorListSetting) {
         ItemColorListSetting var13 = (ItemColorListSetting)var1;
         if (this.isNametagColorList(var1)) {
            this.drawEntryColorChip(var1, var2, false, var13.getPrimaryColor(var2), var3 + var5 - 52, var4 + 6);
            this.drawEntryColorChip(var1, var2, true, var13.jaqpR(var2), var3 + var5 - 39, var4 + 6);
         } else {
            this.drawEntryColorChip(var1, var2, false, var13.getPrimaryColor(var2), var3 + var5 - 39, var4 + 6);
         }
      } else if (var1 instanceof BlockColorListSetting) {
         this.drawEntryColorChip(var1, var2, false, ((BlockColorListSetting)var1).getBlockColor(var2), var3 + var5 - 39, var4 + 6);
      }

      this.listEntryRemoveButtons.put(new udUfwO(var1, var2), var9);
      this.drawCenteredText(
         "x", var9.getCenterX(), Gui.JzraV3() ? this.centeredTextY(var9.ufe, var9.HfS, this.settingFont()) : var9.ufe + 3, var10, this.settingFont()
      );
   }

   private void drawEntryColorChip(BlockListSetting var1, String var2, boolean var3, int var4, int var5, int var6) {
      GuiRect var7 = new GuiRect(var5, var6, 8, 8);
      zyojmoh5 var8 = new zyojmoh5(var1, var2, var3);
      this.cgw.put(var8, var7);
      if (Gui.JzraV3()) {
         this.gvH.registerComponentBounds(var8, var7);
      }

      this.round(var7.x, var7.ufe, var7.busF, var7.HfS, 2.5F, 0xFF000000 | var4 & 16777215);
   }

   private void drawItemSearchResultRow(SettingSearchEntry var1, String var2, ItemStack var3, int var4, int var5, int var6, int var7, int var8) {
      GuiRect var9 = new GuiRect(var4, var5, var6, 20);
      boolean var10 = var9.contains(var7, var8);
      boolean var11 = var1.Jwp;
      int var12 = var11 ? -13619152 : -14408668;
      if (var10 && !Gui.JzraV3()) {
         var12 = var11 ? this.withAccentAlpha(140) : this.withAccentAlpha(120);
      }

      this.drawRoundedPanel(var9.x, var9.ufe, var9.busF, var9.HfS, 4.0F, var12);
      GuiRect var13 = new GuiRect(var4 + var6 - 20, var5 + 3, 14, 14);
      int var14 = var10 ? -986896 : -9803158;
      if (Gui.JzraV3()) {
         var14 = this.hoverAnimations.renderHoverRow(var1.setting, "picker:" + var1.displayName, var9, var11 ? var13 : new GuiRect(0, 0, 0, 0), var7, var8);
      }

      this.drawItemStack(var3, var4 + 4, var5 + 2);
      String var15 = this.trimToWidth(var2, var6 - 48, this.settingFont());
      int var16 = var10 ? -986896 : (var11 ? -3618616 : -6645094);
      this.drawSmall(var15, var4 + 24, var5 + 6, var16);
      this.ONuyuP.put(var1, var9);
      this.drawCenteredText(var11 ? "x" : "+", var13.getCenterX(), var13.ufe + 3, var14, this.settingFont());
   }

   private void drawItemStack(ItemStack var1, int var2, int var3) {
      if (var1 != null && this.itemRender != null) {
         GlStateManager.pushMatrix();
         GlStateManager.scale(0.8F, 0.8F, 0.8F);
         RenderHelper.enableGUIStandardItemLighting();
         this.itemRender.renderItemAndEffectIntoGUI(var1, Math.round(var2 / 0.8F), Math.round(var3 / 0.8F));
         RenderHelper.disableStandardItemLighting();
         GlStateManager.popMatrix();
      }
   }

   private List<ItemListEntry> searchBlockEntries(BlockListSetting var1, String var2) {
      ArrayList var3 = new ArrayList();
      if (var1 instanceof ItemListSetting) {
         for (ItemMatcher$0 var10 : ItemMatcher.getSuggestionsIncludingSelected(var2, (ItemListSetting)var1, true)) {
            String var11 = var10.isSingleItem() ? var10.variants.get(0).hvDbs : var10.getWildcardId();
            String var12 = var10.isSingleItem() ? var10.variants.get(0).Qwi : var10.getDisplayNameWithCount();
            ItemStack var13 = var10.isSingleItem() ? var10.variants.get(0).copyItemStack() : var10.NQcs();
            var3.add(new ItemListEntry(var11, var12, var13, this.isItemEntryListed((ItemListSetting)var1, var11)));
         }

         return var3;
      } else {
         for (BlockEspParser$1 var5 : BlockEspParser.parseGroupedEntriesIncludingUnlisted(var2, var1, true)) {
            String var6 = var5.isSingleMatch() ? var5.nh7.get(0).registryKey : var5.baseRegistryName + ":*";
            String var7 = var5.isSingleMatch() ? var5.nh7.get(0).displayName : var5.buildGroupLabel();
            ItemStack var8 = var5.isSingleMatch() ? var5.nh7.get(0).createItemStack() : var5.getRepresentativeStack();
            var3.add(new ItemListEntry(var6, var7, var8, var1.containsEntry(var6)));
         }

         return var3;
      }
   }

   private TextField LGba(BlockListSetting var1) {
      TextField var2 = this.Mj7.get(var1);
      if (var2 == null) {
         var2 = new TextField(this.ENmj(var1), 96, 1.1F);
         this.Mj7.put(var1, var2);
      }

      return var2;
   }

   private boolean supportsEntrySearchDialog(BlockListSetting var1) {
      return var1 != null && !(var1 instanceof NameListSetting);
   }

   private boolean hasEntryColors(BlockListSetting var1) {
      return var1 instanceof ItemColorListSetting || var1 instanceof BlockColorListSetting;
   }

   private boolean isNametagColorList(BlockListSetting var1) {
      Module var2 = this.ZZPUa7(var1);
      return var2 instanceof ItemESP && "Nametag".equalsIgnoreCase(var2.getInfo());
   }

   private boolean isItemEntryListed(ItemListSetting var1, String var2) {
      if (var1.containsItem(var2)) {
         return true;
      } else {
         String var3 = ItemMatcher.toCanonicalItemId(var2);
         return var3 != null && var1.containsItem(var3 + ":*");
      }
   }

   private String MyUrzHe(BlockListSetting var1, String var2) {
      if (var1 instanceof ItemListSetting) {
         return ItemMatcher.getDisplayName(var2);
      } else {
         return var1 instanceof NameListSetting ? var2 : BlockEspParser.getIconDisplayName(var2);
      }
   }

   private void openEntryColorPicker(zyojmoh5 var1) {
      this.MldK();
      this.closeColorPickerPopup();
      this.NSq4 = var1.blockListSetting;
      this.editingBlockEntry = var1.Bqn;
      this.editingSecondaryColor = var1.LAUhk9;
      this.fHvex = 0;
      int var2 = this.getEntryColor();
      float[] var3 = Color.RGBtoHSB(var2 >> 16 & 0xFF, var2 >> 8 & 0xFF, var2 & 0xFF, null);
      this.entryColorHue = var3[0];
      this.entryColorSaturation = var3[1];
      this.entryColorBrightness = var3[2];
      this.entryColorHexField.setText(this.formatRgbHex(var2));
   }

   private void XGsbyK1(int var1, int var2) {
      if (this.NSq4 != null && this.editingBlockEntry != null) {
         GuiRect var3 = this.cgw.get(new zyojmoh5(this.NSq4, this.editingBlockEntry, this.editingSecondaryColor));
         if (Gui.JzraV3()) {
            var3 = this.gvH.hOzmx(new zyojmoh5(this.NSq4, this.editingBlockEntry, this.editingSecondaryColor));
            if (var3 == null) {
               this.closeEntryColorPicker();
            } else {
               ColorPickerPopup var18 = new ColorPickerPopup(var3, this.width, this.height, false);
               var18.EoZct(this, "Entry color", this.entryColorHue, this.entryColorSaturation, this.entryColorBrightness, this.getEntryColor() | 0xFF000000);
               this.NSTby6 = var18.tnIwm;
               this.SaV = var18.DSvS;
               this.entryColorHueBarRect = var18.UmD;
               this.entryColorHexFieldRect = var18.confirmButtonBounds;
               if (!this.editingEntryColorHex) {
                  this.entryColorHexField.setText(this.formatRgbHex(this.getEntryColor()));
               }

               this.entryColorHexField.kqao7();
               this.entryColorHexField.render(var18.confirmButtonBounds.x, var18.confirmButtonBounds.ufe, var18.confirmButtonBounds.x + var18.confirmButtonBounds.busF, var18.confirmButtonBounds.ufe + var18.confirmButtonBounds.HfS);
            }
         } else {
            int var4 = var3 != null ? var3.x - 64 : this.windowRect().x + this.windowRect().busF - 92;
            int var5 = var3 != null ? var3.ufe + 14 : this.windowRect().ufe + 64;
            byte var6 = 82;
            byte var7 = 102;
            var4 = Math.max(4, Math.min(this.width - var6 - 4, var4));
            var5 = Math.max(4, Math.min(this.height - var7 - 4, var5));
            this.NSTby6 = new GuiRect(var4, var5, var6, var7);
            this.round(var4, var5, var6, var7, 7.0F, -15066598);
            this.edgeOutline(var4, var5, var6, var7, 7.0F);
            this.SaV = new GuiRect(var4 + 8, var5 + 8, 54, 54);
            this.entryColorHueBarRect = new GuiRect(var4 + 67, var5 + 8, 7, 54);
            int var8 = Color.HSBtoRGB(this.entryColorHue, 1.0F, 1.0F) | 0xFF000000;
            RenderUtils.XNRNki(this.SaV.x, this.SaV.ufe, this.SaV.x + this.SaV.busF, this.SaV.ufe + this.SaV.HfS, var8);
            RenderUtils.drawHorizontalGradient(this.SaV.x, this.SaV.ufe, this.SaV.x + this.SaV.busF, this.SaV.ufe + this.SaV.HfS, -1, 16777215);
            RenderUtils.drawVerticalGradient(this.SaV.x, this.SaV.ufe, this.SaV.x + this.SaV.busF, this.SaV.ufe + this.SaV.HfS, 0, -16777216);
            RenderUtils.drawRectOutline(this.SaV.x - 1, this.SaV.ufe - 1, this.SaV.x + this.SaV.busF + 1, this.SaV.ufe + this.SaV.HfS + 1, 1.0F, -12237499);
            int var9 = this.SaV.x + Math.round(this.entryColorSaturation * this.SaV.busF);
            int var10 = this.SaV.ufe + Math.round((1.0F - this.entryColorBrightness) * this.SaV.HfS);
            RenderUtils.XNRNki(var9 - 2, var10, var9 + 3, var10 + 1, -1);
            RenderUtils.XNRNki(var9, var10 - 2, var9 + 1, var10 + 3, -1);

            for (int var11 = 0; var11 < 18; var11++) {
               int var12 = this.entryColorHueBarRect.ufe + Math.round(var11 * this.entryColorHueBarRect.HfS / 18.0F);
               int var13 = this.entryColorHueBarRect.ufe + Math.round((var11 + 1) * this.entryColorHueBarRect.HfS / 18.0F);
               int var14 = Color.HSBtoRGB(var11 / 18.0F, 1.0F, 1.0F) | 0xFF000000;
               int var15 = Color.HSBtoRGB((var11 + 1) / 18.0F, 1.0F, 1.0F) | 0xFF000000;
               RenderUtils.drawVerticalGradient(this.entryColorHueBarRect.x, var12, this.entryColorHueBarRect.x + this.entryColorHueBarRect.busF, var13, var14, var15);
            }

            RenderUtils.drawRectOutline(
               this.entryColorHueBarRect.x - 1, this.entryColorHueBarRect.ufe - 1, this.entryColorHueBarRect.x + this.entryColorHueBarRect.busF + 1, this.entryColorHueBarRect.ufe + this.entryColorHueBarRect.HfS + 1, 1.0F, -12237499
            );
            int var20 = this.entryColorHueBarRect.ufe + Math.round(this.entryColorHue * this.entryColorHueBarRect.HfS);
            RenderUtils.XNRNki(this.entryColorHueBarRect.x - 1, var20 - 1, this.entryColorHueBarRect.x + this.entryColorHueBarRect.busF + 1, var20 + 2, -1);
            this.entryColorHexFieldRect = new GuiRect(var4 + 8, var5 + 72, var6 - 16, 22);
            if (!this.editingEntryColorHex) {
               this.entryColorHexField.setText(this.formatRgbHex(this.getEntryColor()));
            }

            this.entryColorHexField.kqao7();
            this.entryColorHexField.render(this.entryColorHexFieldRect.x, this.entryColorHexFieldRect.ufe, this.entryColorHexFieldRect.x + this.entryColorHexFieldRect.busF, this.entryColorHexFieldRect.ufe + this.entryColorHexFieldRect.HfS);
         }
      }
   }

   private boolean handleEntryColorPickerClick(int var1, int var2, int var3) {
      if (this.NSq4 == null) {
         return false;
      } else if (var3 == 0 && this.entryColorHexFieldRect != null && this.entryColorHexFieldRect.contains(var1, var2)) {
         this.MldK();
         this.editingEntryColorHex = true;
         this.entryColorHexField.setText(this.formatRgbHex(this.getEntryColor()));
         this.entryColorHexField.setFocused(true);
         this.entryColorHexField.selectAll();
         return true;
      } else if (var3 == 0 && this.SaV != null && this.SaV.contains(var1, var2)) {
         this.MldK();
         this.fHvex = 1;
         this.dragEntryColorPicker(var1, var2);
         return true;
      } else if (var3 == 0 && this.entryColorHueBarRect != null && this.entryColorHueBarRect.contains(var1, var2)) {
         this.MldK();
         this.fHvex = 2;
         this.dragEntryColorPicker(var1, var2);
         return true;
      } else {
         GuiRect var4 = this.cgw.get(new zyojmoh5(this.NSq4, this.editingBlockEntry, this.editingSecondaryColor));
         if (var4 != null && var4.contains(var1, var2)) {
            return false;
         } else if (this.NSTby6 != null && this.NSTby6.contains(var1, var2)) {
            return true;
         } else {
            this.closeEntryColorPicker();
            return false;
         }
      }
   }

   private void closeEntryColorPicker() {
      if (this.fHvex != 0) {
         this.markUnsaved(this.ZZPUa7(this.NSq4));
      }

      this.fHvex = 0;
      this.NSq4 = null;
      this.editingBlockEntry = null;
      this.NSTby6 = null;
      this.SaV = null;
      this.entryColorHueBarRect = null;
      this.entryColorHexFieldRect = null;
      if (this.editingEntryColorHex) {
         this.MldK();
      }
   }

   private void dragEntryColorPicker(int var1, int var2) {
      if (this.NSq4 != null && this.fHvex != 0) {
         if (this.fHvex == 1 && this.SaV != null) {
            this.entryColorSaturation = XTpl((float)(var1 - this.SaV.x) / this.SaV.busF);
            this.entryColorBrightness = XTpl(1.0F - (float)(var2 - this.SaV.ufe) / this.SaV.HfS);
         } else if (this.fHvex == 2 && this.entryColorHueBarRect != null) {
            this.entryColorHue = XTpl((float)(var2 - this.entryColorHueBarRect.ufe) / this.entryColorHueBarRect.HfS);
         }

         this.WOvsh(0xFF000000 | Color.HSBtoRGB(this.entryColorHue, this.entryColorSaturation, this.entryColorBrightness) & 16777215);
      }
   }

   private int getEntryColor() {
      if (this.NSq4 instanceof ItemColorListSetting) {
         ItemColorListSetting var1 = (ItemColorListSetting)this.NSq4;
         return this.editingSecondaryColor ? var1.jaqpR(this.editingBlockEntry) : var1.getPrimaryColor(this.editingBlockEntry);
      } else {
         return this.NSq4 instanceof BlockColorListSetting ? ((BlockColorListSetting)this.NSq4).getBlockColor(this.editingBlockEntry) : -1;
      }
   }

   private void WOvsh(int var1) {
      if (this.NSq4 instanceof ItemColorListSetting) {
         ItemColorListSetting var2 = (ItemColorListSetting)this.NSq4;
         if (this.editingSecondaryColor) {
            var2.setSecondaryColor(this.editingBlockEntry, var1);
         } else {
            var2.setPrimaryColor(this.editingBlockEntry, var1);
         }
      } else if (this.NSq4 instanceof BlockColorListSetting) {
         ((BlockColorListSetting)this.NSq4).setBlockColor(this.editingBlockEntry, var1);
      }
   }

   private static float XTpl(float var0) {
      return Math.max(0.0F, Math.min(1.0F, var0));
   }

   @Override
   public void mouseClicked(int var1, int var2, int var3) throws IOException {
      if (!this.fadeOutPending) {
         int[] var4 = this.wcL7(var1, var2);
         var1 = var4[0];
         var2 = var4[1];
         if (this.bindingConfigProfile != null && var3 > 1) {
            this.bindingConfigProfile.setKeycode(var3 + 1000);
            ConfigEntry var12 = this.BDRrbgu(this.bindingConfigProfile);
            if (var12 != null && Jade.configManager != null) {
               Jade.configManager.saveProfile(var12);
            }

            this.bindingConfigProfile = null;
         } else if (this.bindingModule != null && var3 > 1) {
            this.bindingModule.setKeycode(var3 + 1000);
            this.bindingModule = null;
            this.markUnsaved(null);
         } else if (this.bindingKeySetting != null && var3 > 1) {
            this.bindingKeySetting.CvUpqo(KeySetting.combineWithHeldModifiers(var3 + 1000));
            this.bindingKeySetting = null;
            this.yKrqJ9 = 0;
            this.markUnsaved(null);
         } else {
            GuiRect var5 = this.windowRect();
            if (var3 == 0) {
               this.searchFocused = this.getSearchBoxRect(var5).contains(var1, var2);
               if (this.searchFocused && !this.normalizedSearch().isEmpty()) {
                  this.searchScopedToCategory = false;
                  this.RClfTp.snapTo(0.0F);
               }

               if (!this.searchFocused) {
                  this.Namm5 = false;
               }

               if (!this.isRelationshipInputAt(var1, var2)) {
                  this.IAq = null;
               }
            }

            if (this.searchModalOpen) {
               this.handleRelationshipSearchModalClick(var1, var2, var3);
            } else if (this.uninjectDialogOpen) {
               this.handleUninjectDialogClick(var1, var2, var3);
            } else if (this.configDialogMode != 0) {
               this.Drp6(var1, var2, var3);
            } else if (this.createConfigDialogOpen) {
               this.handleCreateConfigDialogClick(var1, var2, var3);
            } else if (this.themeNameDialogOpen) {
               this.handleThemeNameDialogClick(var1, var2, var3);
            } else if (this.Metj != null) {
               this.handleDeleteThemeDialogClick(var1, var2, var3);
            } else if (this.pendingThemeEdit != null) {
               this.handleUnsavedThemeDialogClick(var1, var2, var3);
            } else {
               if (this.nwE5 && var3 == 0) {
                  if (this.hexInputButtonRect != null && this.hexInputButtonRect.contains(var1, var2)) {
                     this.editingHexColor = true;
                     this.Zrv = true;
                     return;
                  }

                  if (this.mV1 != null && this.mV1.contains(var1, var2)) {
                     this.editingHexColor = false;
                     this.ABCZe = 1;
                     this.dragPaletteColorPicker(var1, var2);
                     return;
                  }

                  if (this.MAf != null && this.MAf.contains(var1, var2)) {
                     this.editingHexColor = false;
                     this.ABCZe = 2;
                     this.dragPaletteColorPicker(var1, var2);
                     return;
                  }

                  if (this.DVRCt != null && this.DVRCt.contains(var1, var2)) {
                     return;
                  }

                  this.editingHexColor = false;
               }

               if (this.activeBlockList != null && this.supportsEntrySearchDialog(this.activeBlockList)) {
                  this.handleBlockSearchDialogClick(var1, var2, var3);
               } else if (!this.handleEntryColorPickerClick(var1, var2, var3)) {
                  if (!this.handleColorPickerClick(var1, var2, var3)) {
                     if ((var3 == 0 || var3 == 1) && (!Gui.JzraV3() || this.dropdownLayoutState.isMouseOverOpenContent(var1, var2))) {
                        for (Entry var7 : this.cgw.entrySet()) {
                           if (((GuiRect)var7.getValue()).contains(var1, var2)) {
                              this.openEntryColorPicker((zyojmoh5)var7.getKey());
                              return;
                           }
                        }

                        if (var3 == 1) {
                           for (Entry var39 : this.colorChipRightClickRects.entrySet()) {
                              if (((GuiRect)var39.getValue()).contains(var1, var2)) {
                                 this.openEntryColorPicker((zyojmoh5)var39.getKey());
                                 return;
                              }
                           }
                        }
                     }

                     if (Gui.JzraV3()) {
                        if (this.dropdownLayoutState.handleMouseClick(this, var1, var2, var3)) {
                           return;
                        }

                        if (!this.dropdownLayoutState.isMouseOverOpenContent(var1, var2)) {
                           this.MldK();
                           this.finishConfigRename(true);
                           return;
                        }

                        if (this.dropdownClientRenderer != null && this.dropdownClientRenderer.handleDropdownClick(this, var1, var2, var3)) {
                           return;
                        }

                        if (this.dropdownLayoutState.hasOpenCategory()
                           && (this.handleRelationshipsClick(var1, var2, var3) || this.handleConfigsPanelClick(var1, var2, var3) || this.handleThemesPageClick(var1, var2, var3))) {
                           return;
                        }

                        for (Entry var40 : this.moduleExpandButtonRects.entrySet()) {
                           if (((GuiRect)var40.getValue()).contains(var1, var2)) {
                              this.handleModuleClick((Module)var40.getKey(), 1);
                              return;
                           }
                        }
                     } else {
                        if (var3 == 0) {
                           int var15 = this.hitTestResizeHandle(var5, var1, var2);
                           if (var15 != 0) {
                              this.beginResizeDrag(var15, var1, var2);
                              this.searchFocused = false;
                              this.IAq = null;
                              this.MldK();
                              this.finishConfigRename(true);
                              return;
                           }
                        }

                        if (var3 == 0 && this.NQhR(var5, var1, var2)) {
                           this.beginWindowMove(var1, var2);
                           this.searchFocused = false;
                           this.IAq = null;
                           this.MldK();
                           this.finishConfigRename(true);
                           return;
                        }

                        if (!var5.contains(var1, var2)) {
                           this.MldK();
                           this.finishConfigRename(true);
                           return;
                        }

                        if (this.handleRelationshipsClick(var1, var2, var3)) {
                           return;
                        }

                        if (this.handleConfigsPanelClick(var1, var2, var3)) {
                           return;
                        }

                        if (this.handleThemesPageClick(var1, var2, var3)) {
                           return;
                        }

                        if (var3 == 0 && this.getHudButtonRect(var5).contains(var1, var2)) {
                           this.MldK();
                           this.searchFocused = false;
                           this.mc.displayGuiScreen(new HudEditorScreen(this));
                           return;
                        }

                        if (var3 == 0 && this.FHLMmH(var5).contains(var1, var2)) {
                           this.MldK();
                           this.searchFocused = false;
                           this.uninjectDialogOpen = true;
                           this.uninjectErrorMessage = null;
                           this.zzd = false;
                           this.uninjectHoldStart = 0L;
                           return;
                        }

                        if (var3 == 0 && this.getSearchBoxRect(var5).contains(var1, var2)) {
                           this.MldK();
                           return;
                        }

                        Category var16 = this.getCategoryAt(var1, var2);
                        if (var16 != null) {
                           this.currentCategory = var16;
                           if (var16 == Category.themes && Jade.themeManager != null) {
                              Jade.themeManager.reloadCustomThemes();
                           }

                           if (!this.normalizedSearch().isEmpty()) {
                              this.searchScopedToCategory = true;
                           }

                           this.MldK();
                           this.finishConfigRename(true);
                           return;
                        }
                     }

                     if (var3 == 0) {
                        for (Entry var41 : this.categoryPickerOptionRects.entrySet()) {
                           if (((GuiRect)var41.getValue()).contains(var1, var2)) {
                              CategoryListOptionKey var8 = (CategoryListOptionKey)var41.getKey();
                              if (var8.CUuW == CategoryListSetting.keM.length) {
                                 var8.categoryListSetting.clearSlot(var8.Iad);
                              } else {
                                 String var9 = CategoryListSetting.keM[var8.CUuW];
                                 if (!var8.categoryListSetting.getCategoriesForSlot(var8.Iad).contains(var9)) {
                                    var8.categoryListSetting.addCategoryToSlot(var8.Iad, var9);
                                 }
                              }

                              this.openCategorySlotIndex = -1;
                              this.OmaV = null;
                              this.markUnsaved(null);
                              return;
                           }
                        }

                        for (Entry var42 : this.categorySlotChipRects.entrySet()) {
                           if (((GuiRect)var42.getValue()).contains(var1, var2)) {
                              CategoryListItemKey var63 = (CategoryListItemKey)var42.getKey();
                              List var73 = var63.categoryListSetting.getCategoriesForSlot(var63.slotIndex);
                              if (var63.bs5 >= 0 && var63.bs5 < var73.size()) {
                                 var63.categoryListSetting.removeCategoryFromSlot(var63.slotIndex, (String)var73.get(var63.bs5));
                                 this.markUnsaved(null);
                              }

                              return;
                           }
                        }

                        for (Entry var43 : this.categorySlotAddButtons.entrySet()) {
                           if (((GuiRect)var43.getValue()).contains(var1, var2)) {
                              CategoryEntryKey var64 = (CategoryEntryKey)var43.getKey();
                              if (this.OmaV == var64.categoryListSetting && this.openCategorySlotIndex == var64.entryIndex) {
                                 this.openCategorySlotIndex = -1;
                                 this.OmaV = null;
                              } else {
                                 this.OmaV = var64.categoryListSetting;
                                 this.openCategorySlotIndex = var64.entryIndex;
                              }

                              return;
                           }
                        }

                        if (this.openCategorySlotIndex != -1) {
                           this.openCategorySlotIndex = -1;
                           this.OmaV = null;
                        }
                     }

                     for (Entry var44 : this.listEntryRemoveButtons.entrySet()) {
                        if (((GuiRect)var44.getValue()).contains(var1, var2)) {
                           this.removeListEntry((udUfwO)var44.getKey());
                           this.markUnsaved(null);
                           return;
                        }
                     }

                     for (Entry var45 : this.ONuyuP.entrySet()) {
                        if (((GuiRect)var45.getValue()).contains(var1, var2)) {
                           this.addBlockListEntry((BlockListSetting)((SettingSearchEntry)var45.getKey()).setting, ((SettingSearchEntry)var45.getKey()).displayName);
                           TextField var65 = this.Mj7.get(((SettingSearchEntry)var45.getKey()).setting);
                           if (var65 != null) {
                              var65.setFocused(true);
                           }

                           this.markUnsaved(this.ZZPUa7(((SettingSearchEntry)var45.getKey()).setting));
                           return;
                        }
                     }

                     for (Entry var46 : this.textSettingFieldRects.entrySet()) {
                        if (((GuiRect)var46.getValue()).contains(var1, var2)) {
                           this.beginEditingTextSetting((TextSetting)var46.getKey());
                           return;
                        }
                     }

                     for (Entry var47 : this.ofU.entrySet()) {
                        if (((GuiRect)var47.getValue()).contains(var1, var2)) {
                           this.beginEditingStringList((StringListSetting)var47.getKey());
                           return;
                        }
                     }

                     for (Entry var48 : this.CNc.entrySet()) {
                        if (((GuiRect)var48.getValue()).contains(var1, var2)) {
                           this.beginEditingBlockList((BlockListSetting)var48.getKey());
                           return;
                        }
                     }

                     if (var3 == 0) {
                        for (Entry var49 : this.Ei2.entrySet()) {
                           if (((GuiRect)var49.getValue()).contains(var1, var2) && !((SliderSetting)var49.getKey()).isMode) {
                              this.beginEditingSlider((SliderSetting)var49.getKey());
                              return;
                           }
                        }

                        for (Entry var50 : this.colorValueFieldRects.entrySet()) {
                           if (((GuiRect)var50.getValue()).contains(var1, var2)) {
                              this.UipqMn((ColorSetting)var50.getKey());
                              return;
                           }
                        }
                     }

                     this.MldK();
                     if (var3 == 1) {
                        for (Entry var51 : this.cr2.entrySet()) {
                           if (((GuiRect)var51.getValue()).contains(var1, var2)) {
                              this.RcKo((ColorSetting)var51.getKey());
                              return;
                           }
                        }
                     }

                     for (Entry var52 : this.colorPickerRects.entrySet()) {
                        ColorSetting var66 = (ColorSetting)var52.getKey();
                        ColorPickerBounds var74 = (ColorPickerBounds)var52.getValue();
                        if (var74.colorPreviewBounds != null && var74.colorPreviewBounds.contains(var1, var2)) {
                           this.RcKo(var66);
                           return;
                        }

                        if (var74.dobk != null && var74.dobk.contains(var1, var2)) {
                           this.BvMq = var66;
                           this.colorPickerDragMode = 1;
                           this.dragColorPicker(var1, var2);
                           return;
                        }

                        if (var74.hueSliderBounds != null && var74.hueSliderBounds.contains(var1, var2)) {
                           this.BvMq = var66;
                           this.colorPickerDragMode = 2;
                           this.dragColorPicker(var1, var2);
                           return;
                        }

                        if (var74.alphaSliderBounds != null && var74.alphaSliderBounds.contains(var1, var2)) {
                           this.BvMq = var66;
                           this.colorPickerDragMode = 3;
                           this.dragColorPicker(var1, var2);
                           return;
                        }
                     }

                     for (Entry var53 : this.modeSliderOptionRects.entrySet()) {
                        for (OptionRowHitbox var75 : (java.lang.Iterable<OptionRowHitbox>) (java.lang.Iterable<?>) ((List)var53.getValue())) {
                           if (var75.bhEy.contains(var1, var2)) {
                              ((SliderSetting)var53.getKey()).setValueClamped(var75.optionIndex);
                              this.expandedModeSliders.remove(var53.getKey());
                              if (var53.getKey() == Gui.guiStyle) {
                                 if (Jade.configManager != null) {
                                    Jade.configManager.saveGlobalState();
                                 }
                              } else {
                                 if (var53.getKey() == Gui.dropdownColor) {
                                    this.colorPickerOpenSettings.remove(Gui.dropdownAccent);
                                    this.MldK();
                                 }

                                 this.markUnsaved(null);
                              }

                              return;
                           }
                        }
                     }

                     for (Entry var54 : this.eJ7.entrySet()) {
                        if (((GuiRect)var54.getValue()).contains(var1, var2)) {
                           BooleanSetting var68 = (BooleanSetting)var54.getKey();
                           var68.toggle();
                           Module var76 = this.getOwnerModuleOfBoolean(var68);
                           if (var76 != null) {
                              var76.guiButtonToggled(var68);
                           }

                           this.markUnsaved(var76);
                           return;
                        }
                     }

                     for (Entry var55 : this.multiSelectHeaderRects.entrySet()) {
                        if (((GuiRect)var55.getValue()).contains(var1, var2)) {
                           ((MultiSelectSetting)var55.getKey()).Gfaah(!((MultiSelectSetting)var55.getKey()).isExpanded());
                           return;
                        }
                     }

                     for (Entry var56 : this.NCOdbl.entrySet()) {
                        if (((GuiRect)var56.getValue()).contains(var1, var2)) {
                           Module var69 = this.getOwnerModuleOfBoolean((BooleanSetting)var56.getKey());
                           BooleanSetting var77 = (BooleanSetting)var56.getKey();
                           if (var77.isButton) {
                              var77.runAction();
                           } else {
                              var77.toggle();
                              if (var69 != null) {
                                 var69.guiButtonToggled(var77);
                              }
                           }

                           this.markUnsaved(var69);
                           return;
                        }
                     }

                     for (Entry var57 : this.keySettingButtons.entrySet()) {
                        if (((GuiRect)var57.getValue()).contains(var1, var2)) {
                           this.bindingKeySetting = (KeySetting)var57.getKey();
                           this.yKrqJ9 = 0;
                           return;
                        }
                     }

                     for (Entry var58 : this.UKsT.entrySet()) {
                        if (((GuiRect)var58.getValue()).contains(var1, var2)) {
                           SliderSetting var70 = (SliderSetting)var58.getKey();
                           if (var70.isMode) {
                              if (var3 != 0) {
                                 return;
                              }

                              if (this.expandedModeSliders.contains(var70)) {
                                 this.expandedModeSliders.remove(var70);
                              } else {
                                 this.expandedModeSliders.add(var70);
                              }
                           } else if (var3 == 2) {
                              this.beginEditingSlider(var70);
                           } else if (var3 == 0) {
                              this.draggingSlider = var70;
                              if (Gui.JzraV3()) {
                                 this.activeSliderTrackRect = (GuiRect)var58.getValue();
                              }

                              this.PbPmkE4(var1);
                           }

                           return;
                        }
                     }

                     for (Entry var59 : this.moduleHiddenToggleRects.entrySet()) {
                        if (((GuiRect)var59.getValue()).contains(var1, var2)) {
                           Module var71 = (Module)var59.getKey();
                           var71.setHidden(!var71.isHidden());
                           this.markUnsaved(var71);
                           return;
                        }
                     }

                     for (Entry var60 : this.moduleBindButtons.entrySet()) {
                        if (((GuiRect)var60.getValue()).contains(var1, var2)) {
                           this.bindingModule = (Module)var60.getKey();
                           return;
                        }
                     }

                     for (Entry var61 : this.moduleToggleRects.entrySet()) {
                        if (((GuiRect)var61.getValue()).contains(var1, var2)) {
                           Module var72 = (Module)var61.getKey();
                           if (var72.canBeEnabled()) {
                              var72.toggle();
                           }

                           return;
                        }
                     }

                     for (Entry var62 : this.umR.entrySet()) {
                        if (((GuiRect)var62.getValue()).contains(var1, var2)) {
                           this.handleModuleClick((Module)var62.getKey(), var3);
                           return;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public void mouseReleased(int var1, int var2, int var3) {
      if (var3 == 0) {
         this.dropdownLayoutState.endPanelDrag();
      }

      if (var3 == 0) {
         this.ABCZe = 0;
      }

      if (var3 == 0) {
         this.zzd = false;
         if (!this.uninjectInProgress) {
            this.uninjectHoldStart = 0L;
         }
      }

      boolean var4 = this.draggingSlider == Gui.guiScale;
      boolean var5 = this.ISaB != 0;
      boolean var6 = this.movingWindow;
      this.ISaB = 0;
      this.movingWindow = false;
      if (var5 || var6) {
         this.WQeLm();
      }

      if (this.draggingSlider != null) {
         Module var7 = this.getOwnerModuleOfSlider(this.draggingSlider);
         if (var7 != null) {
            var7.guiSliderChanged(this.draggingSlider);
         }

         this.markUnsaved(var7);
      }

      if (this.BvMq != null) {
         this.markUnsaved(null);
      }

      if (this.fHvex != 0) {
         this.markUnsaved(this.ZZPUa7(this.NSq4));
      }

      this.draggingSlider = null;
      this.BvMq = null;
      this.colorPickerDragMode = 0;
      this.fHvex = 0;
      if (var4) {
         this.refreshAfterProfileLoad();
      }
   }

   @Override
   public void handleMouseInput() throws IOException {
      if (!this.fadeOutPending) {
         int var1 = Mouse.getEventDWheel();
         super.handleMouseInput();
         if (var1 != 0) {
            if (this.bindingConfigProfile != null) {
               this.bindingConfigProfile.setKeycode(var1 > 0 ? 1069 : 1070);
               ConfigEntry var12 = this.BDRrbgu(this.bindingConfigProfile);
               if (var12 != null && Jade.configManager != null) {
                  Jade.configManager.saveProfile(var12);
               }

               this.bindingConfigProfile = null;
            } else if (this.bindingModule != null) {
               this.bindingModule.setKeycode(var1 > 0 ? 1069 : 1070);
               this.bindingModule = null;
               this.markUnsaved(null);
            } else if (this.bindingKeySetting != null) {
               this.bindingKeySetting.CvUpqo(KeySetting.combineWithHeldModifiers(var1 > 0 ? 1069 : 1070));
               this.bindingKeySetting = null;
               this.yKrqJ9 = 0;
               this.markUnsaved(null);
            } else {
               int var2 = Mouse.getEventX() * this.width / this.mc.displayWidth;
               int var3 = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
               if (this.activeBlockList != null && this.supportsEntrySearchDialog(this.activeBlockList) && this.QLh != null && this.QLh.contains(var2, var3)) {
                  this.blockSearchScroll.addToTarget(var1 > 0 ? -32.0F : 32.0F);
               } else if (Gui.JzraV3() && !this.dropdownLayoutState.hasOpenCategory()) {
                  if (!this.searchModalOpen && !this.uninjectDialogOpen && !this.createConfigDialogOpen && this.configDialogMode == 0 && this.activeBlockList == null) {
                     this.dropdownLayoutState.handleMouseScroll(var2, var3, var1);
                  }
               } else {
                  GuiRect var4 = this.windowRect();
                  int var5 = this.getSidebarListTop(var4);
                  int var6 = this.aIrlh6(var4).ufe - 8;
                  if (var2 >= var4.x && var2 < var4.x + this.sidebarW() && var3 >= var5 && var3 < var6) {
                     this.sidebarScrollAnimation.addToTarget(var1 > 0 ? -32.0F : 32.0F);
                  } else {
                     int var7 = var4.x + this.sidebarW();
                     int var8 = var7 + 8;
                     int var9 = var4.busF - this.sidebarW() - 16;
                     int var10 = var4.ufe + 8;
                     int var11 = var4.HfS - 16;
                     if (var3 >= var10 && var3 <= var10 + var11) {
                        if (var2 >= var8 && var2 < var8 + var9) {
                           if (this.currentCategory != Category.friends || !this.normalizedSearch().isEmpty() && !this.searchScopedToCategory) {
                              if (this.currentCategory != Category.profiles || !this.normalizedSearch().isEmpty() && !this.searchScopedToCategory) {
                                 if (this.currentCategory == Category.themes && (this.normalizedSearch().isEmpty() || this.searchScopedToCategory)) {
                                    this.themesScroll.addToTarget(var1 > 0 ? -32.0F : 32.0F);
                                 } else {
                                    this.getActiveContentScroll().addToTarget(var1 > 0 ? -32.0F : 32.0F);
                                 }
                              } else {
                                 this.configListScroll.addToTarget(var1 > 0 ? -32.0F : 32.0F);
                              }
                           } else {
                              this.usTe3.addToTarget(var1 > 0 ? -32.0F : 32.0F);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public void keyTyped(char var1, int var2) {
      if (!this.fadeOutPending) {
         if (this.themeNameDialogOpen) {
            if (var2 == 1) {
               this.themeNameDialogOpen = false;
            } else if (var2 != 28 && var2 != 156) {
               this.themeNameInput = this.editConfigText(this.themeNameInput, var1, var2);
            } else {
               this.saveThemeFromDialog();
            }
         } else if (this.Metj != null) {
            if (var2 == 1) {
               this.Metj = null;
            }
         } else if (this.pendingThemeEdit != null) {
            if (var2 == 1) {
               this.pendingThemeEdit = null;
            }
         } else if (this.editingHexColor) {
            if (var2 != 1 && var2 != 28 && var2 != 156) {
               if (var2 == 14) {
                  if (this.Zrv) {
                     this.hexColorInput = "#";
                  } else if (this.hexColorInput.length() > 1) {
                     this.hexColorInput = this.hexColorInput.substring(0, this.hexColorInput.length() - 1);
                  }

                  this.Zrv = false;
               } else if (var2 == 211) {
                  this.hexColorInput = "#";
                  this.Zrv = false;
               } else if (GuiScreen.isCtrlKeyDown() && var2 == 30) {
                  this.Zrv = true;
               } else if (GuiScreen.isCtrlKeyDown() && var2 == 47) {
                  this.hexColorInput = sanitizeHexInput(getClipboardString());
                  this.Zrv = false;
               } else if (tqAt(var1) && (this.Zrv || this.hexColorInput.length() < 7)) {
                  if (this.Zrv) {
                     this.hexColorInput = "#";
                  }

                  this.hexColorInput = this.hexColorInput + Character.toUpperCase(var1);
                  this.Zrv = false;
               }

               this.applyPaletteHexInput();
            } else {
               this.editingHexColor = false;
            }
         } else if (this.currentCategory == Category.themes && this.themeEditorOpen && var2 == 1) {
            if (this.nwE5) {
               this.nwE5 = false;
            } else {
               this.closeThemeEditor();
            }
         } else if (this.bindingConfigProfile != null) {
            if (var2 != 1 && var2 != 211 && var2 != 14) {
               this.bindingConfigProfile.setKeycode(var2);
            } else {
               this.bindingConfigProfile.setKeycode(0);
            }

            ConfigEntry var8 = this.BDRrbgu(this.bindingConfigProfile);
            if (var8 != null && Jade.configManager != null) {
               Jade.configManager.saveProfile(var8);
            }

            this.bindingConfigProfile = null;
         } else if (this.bindingModule != null) {
            if (var2 != 1 && var2 != 211 && var2 != 14) {
               this.bindingModule.setKeycode(var2);
            } else {
               this.bindingModule.setKeycode(0);
            }

            this.bindingModule = null;
            this.markUnsaved(null);
         } else if (this.bindingKeySetting != null) {
            if (var2 != 1 && var2 != 211 && var2 != 14) {
               if (KeySetting.isModifierKey(var2)) {
                  this.yKrqJ9 = var2;
                  return;
               }

               this.bindingKeySetting.CvUpqo(KeySetting.SDwL(var2));
            } else {
               this.bindingKeySetting.setKeyCode(0);
            }

            this.bindingKeySetting = null;
            this.yKrqJ9 = 0;
            this.markUnsaved(null);
         } else if (this.searchModalOpen) {
            this.handleRelationshipSearchModalKey(var1, var2);
         } else if (this.uninjectDialogOpen) {
            if (var2 == 1 && !this.uninjectInProgress) {
               this.closeUninjectDialog();
            }
         } else if (this.configDialogMode != 0) {
            this.handleConfigActionDialogKey(var2);
         } else if (this.IAq != null) {
            this.handleRelationshipInputKey(var1, var2);
         } else if (this.createConfigDialogOpen) {
            this.abadhA(var1, var2);
         } else if (this.yzh != null) {
            this.handleConfigRenameKey(var1, var2);
         } else if (this.activeBlockList != null && this.supportsEntrySearchDialog(this.activeBlockList)) {
            this.NRVAn(var1, var2);
         } else if (this.editingSlider != null) {
            TextField var7 = this.IDSVr.get(this.editingSlider);
            if (var7 != null) {
               if (var2 == 1) {
                  this.MldK();
                  return;
               }

               if (var2 == 28 || var2 == 156) {
                  this.applySliderInput(var7.getText());
                  return;
               }

               var7.keyTyped(var1, var2);
            }
         } else if (this.gdj != null) {
            TextField var6 = this.tvH.get(this.gdj);
            if (var6 != null) {
               if (var2 == 1) {
                  this.MldK();
                  return;
               }

               if (var2 == 28 || var2 == 156) {
                  this.applyColorSettingInput(var6.getText());
                  return;
               }

               var6.keyTyped(var1, var2);
            }
         } else if (this.editingEntryColorHex) {
            if (var2 == 1) {
               this.MldK();
            } else if (var2 != 28 && var2 != 156) {
               this.entryColorHexField.keyTyped(var1, var2);
            } else {
               this.applyEntryColorInput(this.entryColorHexField.getText());
            }
         } else if (this.editingTextSetting != null) {
            TextField var5 = this.textSettingFields.get(this.editingTextSetting);
            if (var5 != null) {
               if (var2 == 1) {
                  this.MldK();
                  return;
               }

               if (var2 == 28 || var2 == 156) {
                  this.editingTextSetting.setValue(var5.getText());
                  this.editingTextSetting.runChangeCallback();
                  this.markUnsaved(this.ZZPUa7(this.editingTextSetting));
                  this.MldK();
                  return;
               }

               if (var5.keyTyped(var1, var2)) {
                  this.editingTextSetting.setValue(var5.getText());
                  this.markUnsaved(this.ZZPUa7(this.editingTextSetting));
               }
            }
         } else if (this.editingStringList != null) {
            TextField var4 = this.stringListFields.get(this.editingStringList);
            if (var4 != null) {
               if (var2 == 1) {
                  this.MldK();
                  return;
               }

               if (var2 == 28 || var2 == 156) {
                  if (this.editingStringList.addEntry(var4.getText())) {
                     var4.setText("");
                     this.markUnsaved(this.ZZPUa7(this.editingStringList));
                  }

                  return;
               }

               var4.keyTyped(var1, var2);
            }
         } else if (this.activeBlockList == null) {
            if (this.searchFocused) {
               this.handleSearchKey(var1, var2);
            } else {
               if (var2 == 1) {
                  if (Gui.JzraV3() && this.dropdownLayoutState.closeOpenCategory()) {
                     return;
                  }

                  if (Gui.JzraV3()) {
                     this.mouseReleased(0, 0, 0);
                     this.FgL = System.currentTimeMillis();
                     this.openFadeProgress = GuiTheme.ANLBFU((float)(this.FgL - this.openTimeMillis) / 900.0F);
                     this.fadeOutPending = true;
                     EventBus.register(this);
                     this.mc.displayGuiScreen(null);
                  } else {
                     this.mc.displayGuiScreen(null);
                  }
               }
            }
         } else {
            TextField var3 = this.Mj7.get(this.activeBlockList);
            if (var3 != null) {
               if (var2 == 1) {
                  this.MldK();
                  return;
               }

               if (var2 == 28 || var2 == 156) {
                  if (this.addBlockListEntry(this.activeBlockList, var3.getText())) {
                     var3.setText("");
                     this.markUnsaved(this.ZZPUa7(this.activeBlockList));
                  }

                  return;
               }

               var3.keyTyped(var1, var2);
            }
         }
      }
   }

   @Override
   protected void modifierKeyReleased(int var1) {
      if (!this.fadeOutPending) {
         if (this.bindingKeySetting != null && this.yKrqJ9 == var1) {
            this.bindingKeySetting.setKeyCode(var1);
            this.bindingKeySetting = null;
            this.yKrqJ9 = 0;
            this.markUnsaved(null);
         } else {
            super.modifierKeyReleased(var1);
         }
      }
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void renderDropdownClose(RenderTickEvent var1) {
      if (this.fadeOutPending) {
         if (this.mc.currentScreen == null && this.mc.theWorld != null && Gui.JzraV3() && System.currentTimeMillis() - this.FgL < 220L) {
            if (var1.eventPhase == EventPhase.END) {
               this.drawScreen(-10000, -10000, var1.partialTicks);
            }
         } else {
            this.finishClosingFade();
         }
      }
   }

   private void finishClosingFade() {
      EventBus.unregister(this);
      this.fadeOutPending = false;
      this.resetGuiState();
   }

   @Override
   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
      if (!this.fadeOutPending) {
         this.resetGuiState();
      }
   }

   private void resetGuiState() {
      this.closeColorPickerPopup();
      this.closeEntryColorPicker();
      this.gvH.HUBGZjr();
      if (this.dropdownClientRenderer != null) {
         this.dropdownClientRenderer.resetRowAnimations();
      }

      this.hoverAnimations.clearAnimations();
      this.dropdownLayoutState.resetState();
      this.dropdownFadeRenderer.jbgV();
      this.bindingModule = null;
      this.bindingConfigProfile = null;
      this.bindingKeySetting = null;
      this.yKrqJ9 = 0;
      this.draggingSlider = null;
      this.BvMq = null;
      this.colorPickerDragMode = 0;
      this.ISaB = 0;
      this.movingWindow = false;
      this.WQeLm();
      this.MldK();
      this.searchFocused = false;
      this.searchText = "";
      this.searchScopedToCategory = false;
      this.Namm5 = false;
      this.RClfTp.snapTo(0.0F);
      this.createConfigDialogOpen = false;
      this.duhp5 = "";
      this.closeConfigActionDialog();
      this.closeUninjectDialog();
      this.searchModalOpen = false;
      this.uay = "";
      this.OVUf = null;
      this.IAq = null;
      this.finishConfigRename(false);
      this.WGlw = true;
   }

   private void PbPmkE4(int var1) {
      if (this.draggingSlider != null) {
         GuiRect var2 = this.UKsT.get(this.draggingSlider);
         if (var2 == null && Gui.JzraV3()) {
            var2 = this.activeSliderTrackRect;
         }

         if (var2 != null && var2.busF > 0) {
            double var3 = Gui.JzraV3()
               ? SliderMath.sliderOffsetToFraction(var2.busF, var1 - var2.x)
               : Math.max(0.0, Math.min(1.0, (double)(var1 - var2.x) / var2.busF));
            this.draggingSlider.setValueClamped(this.draggingSlider.fromFraction(var3));
         }
      }
   }

   private void dragColorPicker(int var1, int var2) {
      if (this.BvMq != null) {
         ColorPickerBounds var3 = this.colorPickerRects.get(this.BvMq);
         if (var3 != null) {
            if (this.colorPickerDragMode == 1 && var3.dobk != null) {
               float var7 = Math.max(0.0F, Math.min(1.0F, (float)(var1 - var3.dobk.x) / var3.dobk.busF));
               float var5 = Math.max(0.0F, Math.min(1.0F, 1.0F - (float)(var2 - var3.dobk.ufe) / var3.dobk.HfS));
               this.BvMq.setSaturation(var7);
               this.BvMq.setBrightness(var5);
            } else if (this.colorPickerDragMode == 2 && var3.hueSliderBounds != null) {
               float var6 = Math.max(0.0F, Math.min(360.0F, (float)(var2 - var3.hueSliderBounds.ufe) / var3.hueSliderBounds.HfS * 360.0F));
               this.BvMq.oAej(var6);
            } else if (this.colorPickerDragMode == 3 && var3.alphaSliderBounds != null) {
               int var4 = Math.round(Math.max(0.0F, Math.min(1.0F, (float)(var1 - var3.alphaSliderBounds.x) / var3.alphaSliderBounds.busF)) * 255.0F);
               this.BvMq.setAlpha(var4);
            }
         }
      }
   }

   private boolean handleColorPickerClick(int var1, int var2, int var3) {
      ColorSetting var4 = this.getOpenPickerColorSetting();
      if (var4 == null) {
         return false;
      } else {
         ColorPickerBounds var5 = this.colorPickerRects.get(var4);
         GuiRect var6 = this.colorValueFieldRects.get(var4);
         if (Gui.JzraV3() && var3 == 0 && var6 != null && var6.contains(var1, var2)) {
            this.UipqMn(var4);
            return true;
         } else {
            if (var5 != null) {
               if (var3 == 0 && var5.dobk != null && var5.dobk.contains(var1, var2)) {
                  this.BvMq = var4;
                  this.colorPickerDragMode = 1;
                  this.dragColorPicker(var1, var2);
                  return true;
               }

               if (var3 == 0 && var5.hueSliderBounds != null && var5.hueSliderBounds.contains(var1, var2)) {
                  this.BvMq = var4;
                  this.colorPickerDragMode = 2;
                  this.dragColorPicker(var1, var2);
                  return true;
               }

               if (var3 == 0 && var5.alphaSliderBounds != null && var5.alphaSliderBounds.contains(var1, var2)) {
                  this.BvMq = var4;
                  this.colorPickerDragMode = 3;
                  this.dragColorPicker(var1, var2);
                  return true;
               }
            }

            if (this.XYIJhz != null && this.XYIJhz.contains(var1, var2)) {
               return true;
            } else {
               GuiRect var7 = this.cr2.get(var4);
               if (var7 == null || !var7.contains(var1, var2)) {
                  this.closeColorPickerPopup();
               }

               return false;
            }
         }
      }
   }

   private void closeColorPickerPopup() {
      if (this.BvMq != null) {
         this.markUnsaved(this.ZZPUa7(this.BvMq));
      }

      this.BvMq = null;
      this.colorPickerDragMode = 0;
      this.colorPickerOpenSettings.clear();
      this.XYIJhz = null;
      if (this.gdj != null) {
         this.MldK();
      }
   }

   private void removeListEntry(udUfwO var1) {
      if (this.NSq4 == var1.setting && this.editingBlockEntry != null && this.editingBlockEntry.equals(var1.qakJ0)) {
         this.closeEntryColorPicker();
      }

      if (var1.setting instanceof StringListSetting) {
         ((StringListSetting)var1.setting).removeEntry(var1.qakJ0);
      } else if (var1.setting instanceof RelationListSetting) {
         ((RelationListSetting)var1.setting).removeRelation(var1.qakJ0);
      } else if (var1.setting instanceof ItemSlotListSetting) {
         ((ItemSlotListSetting)var1.setting).removeItem(var1.qakJ0);
      } else if (var1.setting instanceof ItemListSetting) {
         ItemListSetting var2 = (ItemListSetting)var1.setting;
         var2.removeItem(var1.qakJ0);
         if (!var1.qakJ0.endsWith(":*")) {
            String var3 = ItemMatcher.toCanonicalItemId(var1.qakJ0);
            if (var3 != null) {
               var2.removeItem(var3 + ":*");
            }
         }
      } else if (var1.setting instanceof NameListSetting) {
         ((NameListSetting)var1.setting).WAAq(var1.qakJ0);
      } else if (var1.setting instanceof BlockListSetting) {
         BlockListSetting var4 = (BlockListSetting)var1.setting;
         var4.removeEntry(var1.qakJ0);
         if (!var1.qakJ0.endsWith(":*")) {
            String var5 = BlockEspParser.getBaseRegistryName(var1.qakJ0);
            if (var5 != null) {
               var4.removeEntry(var5 + ":*");
            }
         }
      }
   }

   private void handleModuleClick(Module var1, int var2) {
      if (var2 == 2) {
         this.bindingModule = var1;
      } else if (var2 == 0 && var1.canBeEnabled()) {
         var1.toggle();
      } else {
         if (var2 == 1) {
            if (this.IyqlY.contains(var1)) {
               if (!this.moduleExpandProgress.containsKey(var1)) {
                  this.moduleExpandProgress.put(var1, 1.0F);
               }

               this.IyqlY.remove(var1);
            } else {
               if (!this.moduleExpandProgress.containsKey(var1)) {
                  this.moduleExpandProgress.put(var1, 0.0F);
               }

               this.IyqlY.add(var1);
            }

            this.markUnsaved(var1);
         }
      }
   }

   public void openModuleConfig(Module var1) {
      if (var1 != null) {
         if (Gui.JzraV3()) {
            this.dropdownLayoutState.resetState();
            this.dropdownLayoutState.focusCategoryPanel(var1.getCategory());
            this.IyqlY.add(var1);
            this.setSearchText(var1.getName());
            this.searchFocused = false;
         } else {
            this.currentCategory = var1.getCategory();
            this.setSearchText("");
            this.searchFocused = false;
            this.searchScopedToCategory = false;
            this.IAq = null;
            this.MldK();
            this.finishConfigRename(true);
            this.IyqlY.add(var1);
            this.moduleExpandProgress.put(var1, 1.0F);
            GuiRect var2 = this.windowRect();
            int var3 = var2.HfS - 16;
            int var4 = var2.busF - this.sidebarW() - 16;
            List var5 = this.KsF6(this.currentCategory);
            int var6 = 0;

            for (Module var8 : (java.lang.Iterable<Module>) (java.lang.Iterable<?>) (var5)) {
               if (var8 == var1) {
                  break;
               }

               var6 += this.getAnimatedModuleHeight(var8, var4) + 5;
            }

            int var9 = Math.max(0, this.getModuleListHeight(var5, var4) - var3);
            this.LYepv7(this.categoryScrollAnimations, this.currentCategory).snapTo(Math.max(0, Math.min(var9, var6 - 8)));
         }
      }
   }

   private void handleSearchKey(char var1, int var2) {
      if (var2 == 1) {
         if (!this.searchText.isEmpty()) {
            this.setSearchText("");
         } else {
            this.searchFocused = false;
         }
      } else if (var2 == 28 || var2 == 156) {
         this.searchFocused = false;
         this.Namm5 = false;
      } else if (var2 == 30 && this.isCtrlDown()) {
         this.Namm5 = !this.searchText.isEmpty();
      } else if (var2 == 46 && this.isCtrlDown()) {
         if (this.Namm5) {
            setClipboardString(this.searchText);
         }
      } else if (var2 == 45 && this.isCtrlDown()) {
         if (this.Namm5) {
            setClipboardString(this.searchText);
            this.setSearchText("");
         }
      } else if (var2 == 14) {
         if (this.Namm5) {
            this.setSearchText("");
         } else if (!this.searchText.isEmpty()) {
            this.setSearchText(this.searchText.substring(0, this.searchText.length() - 1));
         }
      } else if (var2 == 211) {
         if (this.Namm5) {
            this.setSearchText("");
         } else if (!this.searchText.isEmpty()) {
            this.setSearchText(this.searchText.substring(0, this.searchText.length() - 1));
         }
      } else if (var2 == 47 && this.isCtrlDown()) {
         String var3 = getClipboardString();
         if (var3 != null && !var3.isEmpty()) {
            String var4 = this.Namm5 ? "" : this.searchText;
            this.setSearchText((var4 + var3).substring(0, Math.min(64, var4.length() + var3.length())));
         }
      } else {
         if (var1 >= ' ' && var1 != 127 && (this.Namm5 || this.searchText.length() < 64)) {
            this.setSearchText(this.Namm5 ? String.valueOf(var1) : this.searchText + var1);
         }
      }
   }

   private void setSearchText(String var1) {
      String var2 = var1 == null ? "" : var1;
      if (!var2.equals(this.searchText)) {
         this.searchScopedToCategory = false;
         this.Namm5 = false;
         this.RClfTp.snapTo(0.0F);
      }

      this.searchText = var2;
   }

   private boolean isCtrlDown() {
      return Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
   }

   private boolean handleConfigsPanelClick(int var1, int var2, int var3) {
      if (this.currentCategory == Category.profiles && (this.normalizedSearch().isEmpty() || this.searchScopedToCategory)) {
         GuiRect var4 = this.windowRect();
         int var5 = var4.x + this.sidebarW();
         if (var1 >= var5 && var1 < var4.x + var4.busF && var2 >= var4.ufe && var2 < var4.ufe + var4.HfS) {
            if (var3 != 0) {
               return true;
            } else if (this.createConfigButtonRect != null && this.createConfigButtonRect.contains(var1, var2)) {
               this.openCreateConfigDialog();
               return true;
            } else if (this.VwaR != null && this.VwaR.contains(var1, var2)) {
               this.finishConfigRename(true);
               if (Jade.configManager != null) {
                  Jade.configManager.loadProfiles();
                  this.configListScroll.snapTo(0.0F);
               }

               return true;
            } else if (this.openFolderButton != null && this.openFolderButton.contains(var1, var2)) {
               this.finishConfigRename(true);
               this.openConfigFolder();
               return true;
            } else {
               for (Entry var7 : this.configBindButtons.entrySet()) {
                  if (((GuiRect)var7.getValue()).contains(var1, var2)) {
                     this.finishConfigRename(true);
                     if (!this.isDefaultConfigProfile((ConfigProfile)var7.getKey())) {
                        this.bindingConfigProfile = this.bindingConfigProfile == var7.getKey() ? null : (ConfigProfile)var7.getKey();
                     }

                     return true;
                  }
               }

               for (Entry var15 : this.configSaveButtons.entrySet()) {
                  if (((GuiRect)var15.getValue()).contains(var1, var2)) {
                     this.finishConfigRename(true);
                     this.openConfigActionDialog(1, (ConfigProfile)var15.getKey());
                     return true;
                  }
               }

               for (Entry var16 : this.PeGs9.entrySet()) {
                  if (((GuiRect)var16.getValue()).contains(var1, var2)) {
                     this.finishConfigRename(true);
                     this.openConfigActionDialog(2, (ConfigProfile)var16.getKey());
                     return true;
                  }
               }

               for (Entry var17 : this.nam.entrySet()) {
                  if (((GuiRect)var17.getValue()).contains(var1, var2)) {
                     ConfigProfile var8 = (ConfigProfile)var17.getKey();
                     long var9 = System.currentTimeMillis();
                     if (this.lastClickedConfig != var8 || var9 - this.lastConfigClickTime >= 340L) {
                        this.finishConfigRename(true);
                        this.activateConfigProfile(var8);
                        this.lastClickedConfig = var8;
                        this.lastConfigClickTime = var9;
                     } else if (!this.isDefaultConfigProfile(var8)) {
                        this.GixZy(var8);
                     }

                     return true;
                  }
               }

               for (Entry var18 : this.configRowRects.entrySet()) {
                  if (((GuiRect)var18.getValue()).contains(var1, var2)) {
                     this.finishConfigRename(true);
                     this.activateConfigProfile((ConfigProfile)var18.getKey());
                     return true;
                  }
               }

               this.finishConfigRename(true);
               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean handleThemesPageClick(int var1, int var2, int var3) {
      if (this.currentCategory == Category.themes && (this.normalizedSearch().isEmpty() || this.searchScopedToCategory)) {
         GuiRect var4 = this.windowRect();
         if (var1 >= var4.x + this.sidebarW() && var4.contains(var1, var2)) {
            if (this.themesFolderButton != null && this.themesFolderButton.contains(var1, var2) && var3 == 0) {
               try {
                  if (Jade.themeManager != null) {
                     Jade.themeManager.openThemesFolder();
                  }
               } catch (IOException var7) {
               }

               return true;
            } else if (this.themesNewButton != null && this.themesNewButton.contains(var1, var2) && var3 == 0) {
               this.openThemeEditor(null);
               return true;
            } else {
               if (this.themeEditorOpen) {
                  if (this.paletteEditorCloseButton != null && this.paletteEditorCloseButton.contains(var1, var2) && var3 == 0) {
                     this.closeThemeEditor();
                     return true;
                  }

                  for (int var5 = 0; var5 < this.Uginwv.size(); var5++) {
                     if (this.Uginwv.get(var5).contains(var1, var2)) {
                        if (var3 == 1 && this.paletteColors.size() > 1) {
                           this.paletteColors.remove(var5);
                           this.selectedPaletteIndex = Math.min(this.selectedPaletteIndex, this.paletteColors.size() - 1);
                           this.nwE5 = false;
                           this.paletteDirty = true;
                        } else if (var3 == 0) {
                           this.selectedPaletteIndex = var5;
                           this.nwE5 = true;
                        }

                        this.syncPaletteColorInputs();
                        return true;
                     }
                  }

                  for (GuiRect var6 : this.addPaletteColorButtons) {
                     if (var6.contains(var1, var2) && var3 == 0) {
                        this.paletteColors.add(this.paletteColors.isEmpty() ? this.getAccentColor() : this.paletteColors.get(this.paletteColors.size() - 1));
                        this.selectedPaletteIndex = this.paletteColors.size() - 1;
                        this.nwE5 = true;
                        this.paletteDirty = true;
                        this.syncPaletteColorInputs();
                        return true;
                     }
                  }

                  if (this.zeL != null && this.zeL.contains(var1, var2) && var3 == 0 && !this.paletteColors.isEmpty()) {
                     this.nwE5 = false;
                     this.themeNameInput = this.editingTheme == null ? "" : this.editingTheme.getName();
                     this.themeNameDialogOpen = true;
                     return true;
                  }

                  if (this.paletteEditorPanelRect != null && this.paletteEditorPanelRect.contains(var1, var2)) {
                     if (var3 == 0) {
                        this.nwE5 = false;
                     }

                     return true;
                  }
               }

               for (Entry var12 : this.themeEditButtons.entrySet()) {
                  if (((GuiRect)var12.getValue()).contains(var1, var2) && var3 == 0) {
                     this.editTheme((Theme)var12.getKey());
                     return true;
                  }
               }

               for (Entry var13 : this.themeDeleteButtons.entrySet()) {
                  if (((GuiRect)var13.getValue()).contains(var1, var2) && var3 == 0) {
                     this.Metj = (Theme)var13.getKey();
                     return true;
                  }
               }

               for (Entry var14 : this.HOf.entrySet()) {
                  if (((GuiRect)var14.getValue()).contains(var1, var2) && var3 == 0) {
                     if (this.themeEditorOpen) {
                        this.editTheme((Theme)var14.getKey());
                     } else {
                        this.applyTheme((Theme)var14.getKey());
                     }

                     return true;
                  }
               }

               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private void openThemeEditor(Theme var1) {
      this.editingTheme = var1;
      this.paletteColors.clear();
      if (var1 == null) {
         this.paletteColors.add(this.getAccentColor());
      } else {
         this.paletteColors.addAll(var1.mKwci3());
      }

      this.selectedPaletteIndex = 0;
      this.themeEditorOpen = true;
      this.nwE5 = false;
      this.editingHexColor = false;
      this.paletteDirty = false;
      this.syncPaletteColorInputs();
   }

   private void closeThemeEditor() {
      this.themeEditorOpen = false;
      this.editingTheme = null;
      this.ABCZe = 0;
      this.nwE5 = false;
      this.editingHexColor = false;
      this.paletteDirty = false;
      this.pendingThemeEdit = null;
      this.paletteColors.clear();
   }

   private void editTheme(Theme var1) {
      if (var1 != null && var1 != this.editingTheme) {
         if (this.themeEditorOpen && this.paletteDirty) {
            this.pendingThemeEdit = var1;
         } else {
            this.openThemeEditor(var1);
         }
      }
   }

   private void syncPaletteColorInputs() {
      if (!this.paletteColors.isEmpty()) {
         int var1 = this.paletteColors.get(Math.max(0, Math.min(this.selectedPaletteIndex, this.paletteColors.size() - 1)));
         float[] var2 = Color.RGBtoHSB(var1 >> 16 & 0xFF, var1 >> 8 & 0xFF, var1 & 0xFF, null);
         this.paletteHue = var2[0];
         this.iOc3 = var2[1];
         this.paletteBrightness = var2[2];
         this.hexColorInput = String.format(Locale.ROOT, "#%06X", var1 & 16777215);
      }
   }

   private void dragPaletteColorPicker(int var1, int var2) {
      if (!this.paletteColors.isEmpty()) {
         if (this.ABCZe == 1 && this.mV1 != null) {
            this.iOc3 = XTpl((float)(var1 - this.mV1.x) / this.mV1.busF);
            this.paletteBrightness = XTpl(1.0F - (float)(var2 - this.mV1.ufe) / this.mV1.HfS);
         } else if (this.ABCZe == 2 && this.MAf != null) {
            this.paletteHue = XTpl((float)(var2 - this.MAf.ufe) / this.MAf.HfS);
         }

         this.paletteColors.set(this.selectedPaletteIndex, Color.HSBtoRGB(this.paletteHue, this.iOc3, this.paletteBrightness) | 0xFF000000);
         this.paletteDirty = true;
         this.hexColorInput = String.format(Locale.ROOT, "#%06X", this.paletteColors.get(this.selectedPaletteIndex) & 16777215);
      }
   }

   private static boolean tqAt(char var0) {
      char var1 = Character.toUpperCase(var0);
      return var1 >= '0' && var1 <= '9' || var1 >= 'A' && var1 <= 'F';
   }

   private static String sanitizeHexInput(String var0) {
      StringBuilder var1 = new StringBuilder("#");
      if (var0 == null) {
         return var1.toString();
      } else {
         for (int var2 = 0; var2 < var0.length() && var1.length() < 7; var2++) {
            char var3 = var0.charAt(var2);
            if (tqAt(var3)) {
               var1.append(Character.toUpperCase(var3));
            }
         }

         return var1.toString();
      }
   }

   private void applyPaletteHexInput() {
      if (this.hexColorInput != null && this.hexColorInput.length() == 7 && !this.paletteColors.isEmpty()) {
         try {
            int var1 = 0xFF000000 | Integer.parseInt(this.hexColorInput.substring(1), 16);
            this.paletteColors.set(this.selectedPaletteIndex, var1);
            float[] var2 = Color.RGBtoHSB(var1 >> 16 & 0xFF, var1 >> 8 & 0xFF, var1 & 0xFF, null);
            this.paletteHue = var2[0];
            this.iOc3 = var2[1];
            this.paletteBrightness = var2[2];
            this.paletteDirty = true;
         } catch (NumberFormatException var3) {
         }
      }
   }

   private void applyTheme(Theme var1) {
      if (var1 != null && Jade.themeManager != null) {
         Jade.themeManager.fHn0(var1);
         if (Jade.Grq != null) {
            Jade.Grq.odjy(var1.getId());
            this.markUnsaved(null);
         }
      }
   }

   private void handleThemeNameDialogClick(int var1, int var2, int var3) {
      if (var3 == 0) {
         if (this.dQuikX != null && this.dQuikX.contains(var1, var2)) {
            this.themeNameDialogOpen = false;
         } else if (this.themeNameConfirmButton != null && this.themeNameConfirmButton.contains(var1, var2)) {
            this.saveThemeFromDialog();
         } else {
            if (this.themeNameDialogRect == null || !this.themeNameDialogRect.contains(var1, var2)) {
               this.themeNameDialogOpen = false;
            }
         }
      }
   }

   private void handleDeleteThemeDialogClick(int var1, int var2, int var3) {
      if (var3 == 0) {
         if (this.Vul != null && this.Vul.contains(var1, var2)) {
            this.Metj = null;
         } else if (this.vgrxy != null && this.vgrxy.contains(var1, var2)) {
            Theme var4 = this.Metj;
            boolean var5 = Jade.Grq != null && var4.getId().equalsIgnoreCase(Jade.Grq.getThemeName());
            if (Jade.themeManager != null && Jade.themeManager.deleteTheme(var4) && var5) {
               this.applyTheme(Jade.themeManager.dwDhpA("jade"));
            }

            if (this.editingTheme == var4) {
               this.closeThemeEditor();
            }

            this.Metj = null;
         } else {
            if (this.deleteThemeDialogRect == null || !this.deleteThemeDialogRect.contains(var1, var2)) {
               this.Metj = null;
            }
         }
      }
   }

   private void handleUnsavedThemeDialogClick(int var1, int var2, int var3) {
      if (var3 == 0) {
         if (this.discardThemeCancelButton != null && this.discardThemeCancelButton.contains(var1, var2)) {
            this.pendingThemeEdit = null;
         } else if (this.discardThemeConfirmButton != null && this.discardThemeConfirmButton.contains(var1, var2)) {
            Theme var4 = this.pendingThemeEdit;
            this.pendingThemeEdit = null;
            this.openThemeEditor(var4);
         } else {
            if (this.IVYgm == null || !this.IVYgm.contains(var1, var2)) {
               this.pendingThemeEdit = null;
            }
         }
      }
   }

   private void saveThemeFromDialog() {
      if (Jade.themeManager != null && !this.themeNameInput.trim().isEmpty()) {
         try {
            Theme var1 = this.editingTheme != null && this.editingTheme.isCustom()
               ? Jade.themeManager.updateTheme(this.editingTheme, this.themeNameInput, this.paletteColors)
               : Jade.themeManager.createTheme(this.themeNameInput, this.paletteColors);
            this.themeNameDialogOpen = false;
            this.closeThemeEditor();
            this.applyTheme(var1);
         } catch (IOException var2) {
         }
      }
   }

   private void handleBlockSearchDialogClick(int var1, int var2, int var3) {
      if (var3 == 0) {
         if (this.QLh != null && this.QLh.contains(var1, var2)) {
            if (this.Jiwn40 != null && this.Jiwn40.contains(var1, var2)) {
               this.LAyfe();
            } else if (this.GKCt != null && this.GKCt.contains(var1, var2)) {
               TextField var7 = this.LGba(this.activeBlockList);
               var7.setFocused(true);
            } else {
               for (Entry var5 : this.ONuyuP.entrySet()) {
                  if (((GuiRect)var5.getValue()).contains(var1, var2)) {
                     if (((SettingSearchEntry)var5.getKey()).Jwp) {
                        this.removeListEntry(new udUfwO(((SettingSearchEntry)var5.getKey()).setting, ((SettingSearchEntry)var5.getKey()).displayName));
                     } else {
                        this.addBlockListEntry((BlockListSetting)((SettingSearchEntry)var5.getKey()).setting, ((SettingSearchEntry)var5.getKey()).displayName);
                     }

                     TextField var6 = this.Mj7.get(((SettingSearchEntry)var5.getKey()).setting);
                     if (var6 != null) {
                        var6.setFocused(true);
                     }

                     this.markUnsaved(this.ZZPUa7(((SettingSearchEntry)var5.getKey()).setting));
                     return;
                  }
               }
            }
         } else {
            this.LAyfe();
         }
      }
   }

   private void handleCreateConfigDialogClick(int var1, int var2, int var3) {
      if (var3 == 0) {
         if (this.Tkzu == null || !this.Tkzu.contains(var1, var2)) {
            this.closeCreateConfigDialog();
         } else if (this.createConfigConfirmButton != null && this.createConfigConfirmButton.contains(var1, var2)) {
            this.createConfigFromInput();
         } else if (this.createConfigCancelButton != null && this.createConfigCancelButton.contains(var1, var2)) {
            this.closeCreateConfigDialog();
         }
      }
   }

   private void Drp6(int var1, int var2, int var3) {
      if (var3 == 0) {
         if (this.configActionDialogRect != null && this.configActionDialogRect.contains(var1, var2) && (this.MeGm == null || !this.MeGm.contains(var1, var2))) {
            if (this.dAg != null && this.dAg.contains(var1, var2)) {
               this.confirmConfigDialogAction();
            }
         } else {
            this.closeConfigActionDialog();
         }
      }
   }

   private void handleUninjectDialogClick(int var1, int var2, int var3) {
      if (var3 == 0 && !this.uninjectInProgress) {
         if (this.uninjectDialogRect != null && this.uninjectDialogRect.contains(var1, var2) && (this.uninjectCloseButton == null || !this.uninjectCloseButton.contains(var1, var2))) {
            if (this.uninjectErrorMessage == null && this.uninjectConfirmButton != null && this.uninjectConfirmButton.contains(var1, var2)) {
               this.zzd = true;
               this.uninjectHoldStart = System.currentTimeMillis();
            }
         } else {
            this.closeUninjectDialog();
         }
      }
   }

   private void handleConfigActionDialogKey(int var1) {
      if (var1 == 1) {
         this.closeConfigActionDialog();
      } else if (var1 == 28 || var1 == 156) {
         this.confirmConfigDialogAction();
      }
   }

   private void abadhA(char var1, int var2) {
      if (var2 == 1) {
         this.closeCreateConfigDialog();
      } else if (var2 != 28 && var2 != 156) {
         this.duhp5 = this.editConfigText(this.duhp5, var1, var2);
      } else {
         this.createConfigFromInput();
      }
   }

   private void NRVAn(char var1, int var2) {
      if (var2 == 1) {
         this.LAyfe();
      } else {
         TextField var3 = this.LGba(this.activeBlockList);
         if (var2 != 28 && var2 != 156) {
            if (var3.keyTyped(var1, var2)) {
               this.blockSearchScroll.snapTo(0.0F);
            }
         } else {
            String var4 = this.resolveBlockEntryId(this.activeBlockList, var3.getText());
            if (var4 != null && this.addBlockListEntry(this.activeBlockList, var4)) {
               this.markUnsaved(this.ZZPUa7(this.activeBlockList));
            }
         }
      }
   }

   private String resolveBlockEntryId(BlockListSetting var1, String var2) {
      if (var1 == null || var2 == null || var2.trim().isEmpty()) {
         return null;
      } else if (var1 instanceof ItemListSetting) {
         List var5 = ItemMatcher.getSuggestions(var2, (ItemListSetting)var1);
         if (var5.isEmpty()) {
            return null;
         } else {
            ItemMatcher$0 var6 = (ItemMatcher$0)var5.get(0);
            return var6.isSingleItem() ? var6.variants.get(0).hvDbs : var6.getWildcardId();
         }
      } else {
         List var3 = BlockEspParser.parseGroupedEntries(var2, var1);
         if (var3.isEmpty()) {
            return null;
         } else {
            BlockEspParser$1 var4 = (BlockEspParser$1)var3.get(0);
            return var4.isSingleMatch() ? var4.nh7.get(0).registryKey : var4.baseRegistryName + ":*";
         }
      }
   }

   private void LAyfe() {
      if (this.activeBlockList != null) {
         TextField var1 = this.Mj7.get(this.activeBlockList);
         if (var1 != null) {
            var1.setFocused(false);
            var1.setText("");
         }
      }

      this.activeBlockList = null;
      this.QLh = null;
      this.GKCt = null;
      this.Jiwn40 = null;
   }

   private void handleConfigRenameKey(char var1, int var2) {
      if (var2 == 1) {
         this.finishConfigRename(false);
      } else if (var2 != 28 && var2 != 156) {
         this.configRenameInput = this.editConfigText(this.configRenameInput, var1, var2);
      } else {
         this.finishConfigRename(true);
      }
   }

   private void openCreateConfigDialog() {
      this.finishConfigRename(true);
      this.MldK();
      this.searchFocused = false;
      this.createConfigDialogOpen = true;
      this.duhp5 = "";
   }

   private void closeCreateConfigDialog() {
      this.createConfigDialogOpen = false;
      this.duhp5 = "";
   }

   private void openConfigActionDialog(int var1, ConfigProfile var2) {
      if (var2 != null && var1 != 0) {
         this.finishConfigRename(true);
         this.MldK();
         this.searchFocused = false;
         this.createConfigDialogOpen = false;
         this.configDialogMode = var1;
         this.EVHD = var2;
      }
   }

   private void closeConfigActionDialog() {
      this.configDialogMode = 0;
      this.EVHD = null;
      this.configActionDialogRect = null;
      this.dAg = null;
      this.MeGm = null;
   }

   private void closeUninjectDialog() {
      if (!this.uninjectInProgress) {
         this.uninjectDialogOpen = false;
         this.zzd = false;
         this.uninjectHoldStart = 0L;
         this.uninjectErrorMessage = null;
         this.uninjectDialogRect = null;
         this.uninjectCloseButton = null;
         this.uninjectConfirmButton = null;
      }
   }

   private void confirmConfigDialogAction() {
      ConfigProfile var1 = this.EVHD;
      int var2 = this.configDialogMode;
      this.closeConfigActionDialog();
      if (var1 != null) {
         if (var2 == 1) {
            this.saveConfigProfile(var1);
         } else if (var2 == 2) {
            this.EaRguvO(var1);
         } else if (var2 == 3) {
            this.toggleConfigProfile(var1);
         }
      }
   }

   private void activateConfigProfile(ConfigProfile var1) {
      if (var1 != null && !var1.isEnabled()) {
         ConfigProfile var2 = Jade.Grq == null ? null : Jade.Grq.getProfile();
         if (var2 != null && !var2.unmodified) {
            this.openConfigActionDialog(3, var1);
         } else {
            this.toggleConfigProfile(var1);
         }
      }
   }

   private void toggleConfigProfile(ConfigProfile var1) {
      if (var1 != null) {
         var1.toggle();
         this.configListScroll.snapTo(0.0F);
      }
   }

   private void createConfigFromInput() {
      if (Jade.configManager != null) {
         ConfigEntry var1 = Jade.configManager.dvuld(this.duhp5, 0);
         if (var1 != null) {
            this.configListScroll.snapTo(0.0F);
            this.closeCreateConfigDialog();
         }
      }
   }

   private void GixZy(ConfigProfile var1) {
      if (var1 != null) {
         this.MldK();
         this.searchFocused = false;
         this.yzh = var1;
         this.configRenameInput = var1.getName();
      }
   }

   private void finishConfigRename(boolean var1) {
      if (this.yzh != null && var1 && Jade.configManager != null) {
         ConfigEntry var2 = this.BDRrbgu(this.yzh);
         if (var2 != null) {
            Jade.configManager.renameProfile(var2, this.configRenameInput);
         }
      }

      this.yzh = null;
      this.configRenameInput = "";
   }

   @Override
   protected String editConfigText(String var1, char var2, int var3) {
      String var4 = var1 == null ? "" : var1;
      if (var3 == 14) {
         return var4.isEmpty() ? var4 : var4.substring(0, var4.length() - 1);
      } else if (var3 == 211) {
         return "";
      } else if (var3 != 47 || !Keyboard.isKeyDown(29) && !Keyboard.isKeyDown(157)) {
         return var2 >= ' ' && var2 != 127 ? this.truncateConfigText(var4 + var2) : var4;
      } else {
         String var5 = getClipboardString();
         return var5 != null && !var5.isEmpty() ? this.truncateConfigText(var4 + var5) : var4;
      }
   }

   private String truncateConfigText(String var1) {
      return var1 == null ? "" : var1.substring(0, Math.min(32, var1.length()));
   }

   private void saveConfigProfile(ConfigProfile var1) {
      if (var1 != null && !this.isDefaultConfigProfile(var1) && Jade.configManager != null) {
         ConfigEntry var2 = this.BDRrbgu(var1);
         if (var2 != null) {
            Jade.configManager.saveProfile(var2);
            var1.unmodified = true;
            ClientUtils.sendJadeMessage("Jade", "&7saved config: &f" + var2.getName());
         }
      }
   }

   private void EaRguvO(ConfigProfile var1) {
      if (var1 != null && !this.isDefaultConfigProfile(var1) && Jade.configManager != null) {
         Jade.configManager.deleteProfile(var1.getName());
         this.configListScroll.snapTo(0.0F);
      }
   }

   private ConfigEntry BDRrbgu(ConfigProfile var1) {
      if (Jade.configManager != null && Jade.configManager.profiles != null && var1 != null) {
         for (ConfigEntry var3 : Jade.configManager.profiles) {
            if (var3 != null && var3.getProfile() == var1) {
               return var3;
            }
         }

         return Jade.configManager.jnkYch(var1.getName());
      } else {
         return null;
      }
   }

   private void openConfigFolder() {
      if (Jade.configManager != null && Jade.configManager.file != null) {
         try {
            if (!Jade.configManager.file.exists()) {
               Jade.configManager.file.mkdirs();
            }

            Desktop.getDesktop().open(Jade.configManager.file);
         } catch (Exception var2) {
         }
      }
   }

   private int[] wcL7(int var1, int var2) {
      if (Gui.JzraV3()) {
         return new int[]{var1, var2};
      } else {
         GuiRect var3 = this.windowRect();
         if (var3.contains(var1, var2)) {
            return new int[]{var1, var2};
         } else {
            int var4 = Mouse.getX() * this.width / Math.max(1, this.mc.displayWidth);
            int var5 = this.height - Mouse.getY() * this.height / Math.max(1, this.mc.displayHeight) - 1;
            if (var3.contains(var4, var5)) {
               return new int[]{var4, var5};
            } else {
               int var6 = this.height - var2 - 1;
               return var3.contains(var1, var6) ? new int[]{var1, var6} : new int[]{var1, var2};
            }
         }
      }
   }

   private Category getCategoryAt(int var1, int var2) {
      GuiRect var3 = this.windowRect();
      if (this.aIrlh6(var3).contains(var1, var2)) {
         return Category.client;
      } else {
         int var4 = this.getSidebarListTop(var3);
         int var5 = this.aIrlh6(var3).ufe - 8;
         if (var1 >= var3.x && var1 < var3.x + this.sidebarW() && var2 >= var4 && var2 < var5) {
            int var6 = var4 + 20 - Math.round(this.sidebarScrollAnimation.ORMWO());

            for (Category var10 : CategoryNames.getOrderedCategories()) {
               if (var10 == Category.friends) {
                  var6 += 20;
               }

               GuiRect var11 = this.getCategoryRowRect(var3, var6);
               if (var11.contains(var1, var2)) {
                  return var10;
               }

               var6 += 27;
            }

            return null;
         } else {
            return null;
         }
      }
   }

   private Module findModuleAtPosition(int var1, int var2) {
      GuiRect var3 = this.windowRect();
      int var4 = var3.x + this.sidebarW();
      int var5 = var3.ufe + 8;
      int var6 = var3.HfS - 16;
      int var7 = var4 + 8;
      int var8 = var3.busF - this.sidebarW() - 16;
      if (var2 >= var5 && var2 < var5 + var6 && var1 >= var7 && var1 < var7 + var8) {
         List var9 = this.getVisibleModules();
         int var10 = Math.round(this.getActiveContentScroll().ORMWO());
         return this.findModuleInList(var9, var7, var5, var8, var10, var1, var2);
      } else {
         return null;
      }
   }

   private Module findModuleInList(List<Module> var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      int var8 = var3 - var5;

      for (Module var10 : var1) {
         GuiRect var11 = new GuiRect(var2, var8, var4, 31);
         if (var11.contains(var6, var7)) {
            return var10;
         }

         var8 += this.getAnimatedModuleHeight(var10, var4) + 5;
      }

      return null;
   }

   private int getModuleListHeight(List<Module> var1, int var2) {
      int var3 = 0;

      for (Module var5 : var1) {
         var3 += this.getAnimatedModuleHeight(var5, var2) + 5;
      }

      return Math.max(0, var3 - 5);
   }

   private int getAnimatedModuleHeight(Module var1, int var2) {
      float var3 = this.getModuleExpandProgress(var1);
      if (var3 <= 0.01F) {
         return 31;
      } else {
         int var4 = this.getExpandedModuleHeight(var1, var2);
         return 31 + Math.round((var4 - 31) * var3);
      }
   }

   private int getExpandedModuleHeight(Module var1, int var2) {
      int var3 = 41;
      List var4 = this.getVisibleModuleSettings(var1);
      if (var4.isEmpty()) {
         return var3 + 18;
      } else {
         int var5 = var2 - 36;

         for (Setting var7 : (java.lang.Iterable<Setting>) (java.lang.Iterable<?>) (var4)) {
            var3 += this.settingHeight(var7, var5) + 2;
         }

         return var3 + 6;
      }
   }

   public int settingHeight(Setting var1, int var2) {
      if (Gui.JzraV3()) {
         if (var1 instanceof BlockListSetting && this.supportsEntrySearchDialog((BlockListSetting)var1)) {
            int var10 = Math.max(1, this.getEntryCount((BlockListSetting)var1));
            if (!(var1 instanceof ItemColorListSetting)) {
               var10 = Math.min(4, var10);
            }

            return 25 + var10 * 22;
         }

         if (var1 instanceof BooleanSetting || var1 instanceof ColorSetting) {
            return 21;
         }

         if (var1 instanceof MultiSelectSetting) {
            MultiSelectSetting var9 = (MultiSelectSetting)var1;
            int var12 = var9.awwHd() == null ? 0 : var9.awwHd().length;
            return 21 + Math.round(var12 * 21 * this.dropdownTransition(var9, var9.isExpanded() ? 1.0F : 0.0F));
         }

         if (var1 instanceof SliderSetting && ((SliderSetting)var1).isMode) {
            SliderSetting var8 = (SliderSetting)var1;
            return 21 + Math.round(Math.max(0, var8.getOptions().length - 1) * 21 * this.getModeSliderExpandProgress(var8));
         }
      }

      if (var1 instanceof GroupSetting) {
         return 22;
      } else if (var1 instanceof DescriptionSetting) {
         return 0;
      } else if (var1 instanceof BooleanSetting) {
         return 22;
      } else if (var1 instanceof MultiSelectSetting) {
         MultiSelectSetting var7 = (MultiSelectSetting)var1;
         int var11 = var7.awwHd() == null ? 0 : var7.awwHd().length;
         int var5 = var11 > 6 ? 2 : 1;
         int var6 = var5 == 2 ? (var11 + 1) / 2 : var11;
         return var7.isExpanded() ? 36 + var6 * 20 : 26;
      } else if (var1 instanceof SliderSetting) {
         SliderSetting var3 = (SliderSetting)var1;
         if (!var3.isMode) {
            return 32;
         } else {
            int var4 = 39;
            if (var3.getOptions() != null) {
               var4 += Math.round((Math.max(0, var3.getOptions().length - 1) * 17 + 5) * this.getModeSliderExpandProgress(var3));
            }

            return var4;
         }
      } else if (var1 instanceof ColorSetting) {
         return 30;
      } else if (var1 instanceof KeySetting) {
         return 30;
      } else if (var1 instanceof TextSetting) {
         return 50;
      } else if (var1 instanceof StringListSetting) {
         return 58 + Math.min(4, ((StringListSetting)var1).getEntries().size()) * 22;
      } else if (var1 instanceof RelationListSetting) {
         return 50 + Math.min(5, Math.max(1, ((RelationListSetting)var1).getRelations().size())) * 22;
      } else if (var1 instanceof ItemColorListSetting) {
         return 50 + Math.max(1, this.getEntryCount((BlockListSetting)var1)) * 22;
      } else if (var1 instanceof BlockListSetting) {
         return 50 + Math.min(5, Math.max(1, this.getEntryCount((BlockListSetting)var1))) * 22;
      } else {
         return var1 instanceof CategoryListSetting ? this.getCategoryListHeight((CategoryListSetting)var1) : 24;
      }
   }

   private int getCategoryListHeight(CategoryListSetting var1) {
      int var2 = 202;
      if (this.OmaV == var1 && this.openCategorySlotIndex >= 0) {
         var2 += ovbT;
      }

      return var2;
   }

   private int getEntryCount(BlockListSetting var1) {
      if (var1 instanceof ItemSlotListSetting) {
         return ((ItemSlotListSetting)var1).getItems().size();
      } else if (var1 instanceof ItemListSetting) {
         return ((ItemListSetting)var1).getItems().size();
      } else {
         return var1 instanceof NameListSetting ? ((NameListSetting)var1).getEntryList().size() : var1.getEntries().size();
      }
   }

   private List<Setting> getVisibleModuleSettings(Module var1) {
      List<Setting> var2 = ConditionSettings.buildSettings(var1, new ConditionSettings$0() {
         @Override
         public MultiSelectSetting createMultiSelectSetting(Module var1, String var2x, List<BooleanSetting> var3, String[] var4) {
            return JadeClickGui.createCachedMultiSelectSetting(JadeClickGui.this, var1, var2x, var3, var4);
         }
      });
      if (!Gui.JzraV3()) {
         var2.removeIf(ThemeConfig::isDropdownAppearanceSetting);
      }

      return var2;
   }

   private MultiSelectSetting getOrCreateMultiSelectSettingSimple(Module var1, String var2, List<BooleanSetting> var3) {
      return this.getOrCreateMultiSelectSetting(var1, var2, var3, null);
   }

   private MultiSelectSetting getOrCreateMultiSelectSetting(Module var1, String var2, List<BooleanSetting> var3, String[] var4) {
      if (var3 != null && var3.size() >= 2) {
         StringBuilder var5 = new StringBuilder(var1.getName()).append(":").append(var2);
         BooleanSetting[] var6 = (BooleanSetting[]) var3.toArray(new BooleanSetting[0]);
         String[] var7 = new String[var6.length];

         for (int var8 = 0; var8 < var6.length; var8++) {
            var5.append(":").append(var6[var8].getPath());
            var7[var8] = var4 != null && var8 < var4.length ? var4[var8] : var6[var8].getName();
         }

         String var11 = var5.toString();
         MultiSelectSetting var9 = this.multiSelectCache.get(var11);
         if (var9 == null || var9.awwHd().length != var6.length || !this.multiSelectLabelsMatch(var9, var7)) {
            boolean var10 = var9 != null && var9.isExpanded();
            var9 = new MultiSelectSetting(var2, var7, var6);
            var9.Gfaah(var10);
            this.multiSelectCache.put(var11, var9);
         }

         return var9;
      } else {
         return null;
      }
   }

   private boolean multiSelectLabelsMatch(MultiSelectSetting var1, String[] var2) {
      if (var2 == null) {
         return true;
      } else {
         for (int var3 = 0; var3 < var2.length; var3++) {
            String var4 = var2[var3] == null ? "" : var2[var3];
            String var5 = var1.getOptionLabel(var3);
            if (!var4.equals(var5)) {
               return false;
            }
         }

         return true;
      }
   }

   private List<Module> KsF6(Category var1) {
      ArrayList var2 = new ArrayList();
      if (var1 != Category.profiles) {
         if (var1 == Category.friends) {
            return var2;
         } else {
            for (Module var6 : this.getAllModules()) {
               if (var6.getCategory() == var1) {
                  var2.add(var6);
               }
            }

            Collections.sort(var2, JadeClickGui::compareModulesByName);
            return var2;
         }
      } else {
         var2.add(this.profilesModule);
         if (Jade.configManager != null && Jade.configManager.profiles != null) {
            for (ConfigEntry var4 : Jade.configManager.profiles) {
               var2.add(var4.getProfile());
            }
         }

         return var2;
      }
   }

   private List<Module> getVisibleModules() {
      String var1 = this.normalizedSearch();
      if (!var1.isEmpty() && !this.searchScopedToCategory) {
         ArrayList var2 = new ArrayList();

         for (Module var4 : this.getAllModules()) {
            if (!this.isClientUtilityModule(var4) && this.moduleMatchesSearch(var4, var1)) {
               var2.add(var4);
            }
         }

         Collections.sort(var2, JadeClickGui::NPlaP);
         return var2;
      } else {
         return this.KsF6(this.currentCategory);
      }
   }

   private boolean isClientUtilityModule(Module var1) {
      return var1 instanceof Relationships || var1 instanceof ChatCommands || var1 instanceof Rendering || var1 instanceof Settings || var1 instanceof ProfileManager;
   }

   private boolean moduleMatchesSearch(Module var1, String var2) {
      if (!this.matchesText(var1.getName(), var2) && !Jade.getModuleManager().matchesName(var1, var2) && !this.matchesText(CategoryNames.getDisplayName(var1.getCategory()), var2)) {
         for (Setting var4 : var1.getSettings()) {
            if (this.settingMatchesSearch(var4, var2)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   private boolean settingMatchesSearch(Setting var1, String var2) {
      if (var1 != null && !(var1 instanceof DescriptionSetting)) {
         if (!this.matchesText(var1.getName(), var2) && !this.matchesText(var1.getPath(), var2)) {
            if (var1 instanceof SliderSetting) {
               SliderSetting var14 = (SliderSetting)var1;
               if (this.matchesText(var14.getSuffix(), var2) || this.matchesText(this.getModeOptionLabel(var14), var2)) {
                  return true;
               }

               String[] var19 = var14.getOptions();
               if (var19 != null) {
                  for (String var8 : var19) {
                     if (this.matchesText(var8, var2)) {
                        return true;
                     }
                  }
               }
            } else if (var1 instanceof MultiSelectSetting) {
               MultiSelectSetting var13 = (MultiSelectSetting)var1;
               if (this.matchesText(var13.getSummaryText(), var2)) {
                  return true;
               }

               for (BooleanSetting var7 : var13.awwHd()) {
                  if (var7 != null && this.matchesText(var7.getName(), var2)) {
                     return true;
                  }
               }
            } else if (var1 instanceof TextSetting) {
               TextSetting var12 = (TextSetting)var1;
               if (this.matchesText(var12.getValue(), var2) || this.matchesText(var12.getPlaceholder(), var2)) {
                  return true;
               }
            } else if (var1 instanceof StringListSetting) {
               StringListSetting var11 = (StringListSetting)var1;
               if (this.matchesText(var11.getInputHint(), var2)) {
                  return true;
               }

               for (String var22 : var11.getEntries()) {
                  if (this.matchesText(var22, var2)) {
                     return true;
                  }
               }
            } else if (var1 instanceof RelationListSetting) {
               RelationListSetting var10 = (RelationListSetting)var1;
               if (this.matchesText(var10.getInputHint(), var2)) {
                  return true;
               }

               for (RelationManager$0 var21 : var10.getRelations()) {
                  if (this.matchesText(var21.ZNKxZ(), var2) || this.matchesText(var21.getPlayerName(), var2)) {
                     return true;
                  }
               }
            } else if (var1 instanceof BlockListSetting) {
               List var3;
               if (var1 instanceof ItemSlotListSetting) {
                  var3 = ((ItemSlotListSetting)var1).getItems();
               } else if (var1 instanceof ItemListSetting) {
                  var3 = ((ItemListSetting)var1).getItems();
               } else if (var1 instanceof NameListSetting) {
                  var3 = ((NameListSetting)var1).getEntryList();
               } else {
                  var3 = ((BlockListSetting)var1).getEntries();
               }

               for (String var5 : (java.lang.Iterable<String>) (java.lang.Iterable<?>) (var3)) {
                  if (this.matchesText(var5, var2)) {
                     return true;
                  }
               }
            } else if (var1 instanceof GroupSetting) {
               GroupSetting var9 = (GroupSetting)var1;
               if (var9.getSettings() != null) {
                  for (Setting var20 : var9.getSettings()) {
                     if (this.settingMatchesSearch(var20, var2)) {
                        return true;
                     }
                  }
               }
            }

            return false;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   @Override
   protected boolean matchesText(String var1, String var2) {
      return var1 != null && this.normalizeSearchToken(var1).contains(var2);
   }

   @Override
   protected String normalizedSearch() {
      return this.normalizeSearchToken(this.searchText);
   }

   @Override
   protected Category selectedCategory() {
      return this.currentCategory;
   }

   @Override
   protected boolean searchCategoryScoped() {
      return this.searchScopedToCategory;
   }

   @Override
   protected void clearSearchFocus() {
      this.searchFocused = false;
   }

   @Override
   protected String normalizeSearchToken(String var1) {
      if (var1 == null) {
         return "";
      } else {
         StringBuilder var2 = new StringBuilder(var1.length());

         for (int var3 = 0; var3 < var1.length(); var3++) {
            char var4 = Character.toLowerCase(var1.charAt(var3));
            if (Character.isLetterOrDigit(var4)) {
               var2.append(var4);
            }
         }

         return var2.toString();
      }
   }

   private List<Module> getAllModules() {
      ArrayList var1 = new ArrayList();
      if (Jade.getModuleManager() != null) {
         var1.addAll(Jade.getModuleManager().getModules());
      }

      return var1;
   }

   private Module ZZPUa7(Setting var1) {
      for (Module var3 : this.getAllModules()) {
         for (Setting var5 : var3.getSettings()) {
            if (var5 == var1) {
               return var3;
            }
         }
      }

      return null;
   }

   private Module getOwnerModuleOfBoolean(BooleanSetting var1) {
      return this.ZZPUa7(var1);
   }

   private Module getOwnerModuleOfSlider(SliderSetting var1) {
      return this.ZZPUa7(var1);
   }

   private float getModuleExpandProgress(Module var1) {
      float var2 = this.IyqlY.contains(var1) ? 1.0F : 0.0F;
      Float var3 = this.moduleExpandProgress.get(var1);
      float var4 = var3 == null ? (var2 > 0.0F ? 0.0F : 1.0F) : var3;
      var4 += (var2 - var4) * 0.11F;
      if (Math.abs(var2 - var4) < 0.003F) {
         var4 = var2;
      }

      this.moduleExpandProgress.put(var1, var4);
      return var4;
   }

   private float animateTowards(Object var1, float var2) {
      Float var3 = this.animationCache.get(var1);
      float var4 = var3 == null ? var2 : var3;
      var4 += (var2 - var4) * 0.11F;
      if (Math.abs(var2 - var4) < 0.003F) {
         var4 = var2;
      }

      this.animationCache.put(var1, var4);
      return var4;
   }

   private float getWindowOpenScale() {
      if (this.openTimeMillis <= 0L) {
         return 1.0F;
      } else {
         float var1 = Math.min(1.0F, (float)(System.currentTimeMillis() - this.openTimeMillis) / 190.0F);
         float var2 = 1.0F - (float)Math.pow(1.0F - var1, 3.0);
         return 0.92F + var2 * 0.08F;
      }
   }

   private Object getModuleAnimationKey(Module var1) {
      Object var2 = this.IeoW.get(var1);
      if (var2 == null) {
         var2 = new Object();
         this.IeoW.put(var1, var2);
      }

      return var2;
   }

   private Object getCategoryAnimationKey(Map<Category, Object> var1, Category var2) {
      Object var3 = var1.get(var2);
      if (var3 == null) {
         var3 = new Object();
         var1.put(var2, var3);
      }

      return var3;
   }

   private Object ghEaz(Category var1) {
      Object var2 = this.WVkE.get(var1);
      if (var2 == null) {
         var2 = new Object();
         this.WVkE.put(var1, var2);
      }

      return var2;
   }

   private float getModeSliderExpandProgress(SliderSetting var1) {
      float var2 = this.expandedModeSliders.contains(var1) ? 1.0F : 0.0F;
      if (Gui.JzraV3()) {
         return this.dropdownTransition(var1, var2);
      } else {
         Float var3 = this.YnY6.get(var1);
         float var4 = var3 == null ? var2 : var3;
         var4 += (var2 - var4) * 0.11F;
         if (Math.abs(var2 - var4) < 0.003F) {
            var4 = var2;
         }

         this.YnY6.put(var1, var4);
         return var4;
      }
   }

   private double getAnimatedSliderValue(SliderSetting var1) {
      Double var2 = this.animatedSliderValues.get(var1);
      double var3 = var1.getInput();
      double var5 = var2 == null ? var3 : var2;
      var5 += (var3 - var5) * 0.24;
      if (Math.abs(var3 - var5) < 0.001) {
         var5 = var3;
      }

      this.animatedSliderValues.put(var1, var5);
      return var5;
   }

   public void markUnsaved(Module var1) {
      if (Jade.Grq != null && (var1 == null || !var1.skipSettingsPersistence)) {
         ConfigProfile var2 = Jade.Grq.getProfile();
         if (var2 != null) {
            var2.unmodified = false;
         }
      }
   }

   private void beginEditingTextSetting(TextSetting var1) {
      this.MldK();
      this.editingTextSetting = var1;
      TextField var2 = this.textSettingFields.get(var1);
      if (var2 != null) {
         var2.setFocused(true);
      }
   }

   private void beginEditingStringList(StringListSetting var1) {
      this.MldK();
      this.editingStringList = var1;
      TextField var2 = this.stringListFields.get(var1);
      if (var2 != null) {
         var2.setFocused(true);
      }
   }

   private void beginEditingBlockList(BlockListSetting var1) {
      this.MldK();
      this.activeBlockList = var1;
      TextField var2 = this.supportsEntrySearchDialog(var1) ? this.LGba(var1) : this.Mj7.get(var1);
      if (var2 != null) {
         var2.setFocused(true);
      }

      this.blockSearchScroll.snapTo(0.0F);
   }

   private void beginEditingSlider(SliderSetting var1) {
      this.MldK();
      this.searchFocused = false;
      this.editingSlider = var1;
      TextField var2 = this.getSliderTextField(var1);
      var2.setText(this.formatSliderValue(var1.getInput(), var1));
      var2.setFocused(true);
      var2.selectAll();
   }

   private void UipqMn(ColorSetting var1) {
      this.MldK();
      this.searchFocused = false;
      this.gdj = var1;
      TextField var2 = this.YEXZj(var1);
      var2.setText(this.formatColorHex(var1));
      var2.setFocused(true);
      var2.selectAll();
   }

   private TextField getSliderTextField(SliderSetting var1) {
      TextField var2 = this.IDSVr.get(var1);
      if (var2 == null) {
         var2 = new TextField("", 64, 1.1F);
         this.IDSVr.put(var1, var2);
      }

      return var2;
   }

   private TextField YEXZj(ColorSetting var1) {
      TextField var2 = this.tvH.get(var1);
      if (var2 == null) {
         var2 = new TextField("", 10, 1.1F);
         this.tvH.put(var1, var2);
      }

      return var2;
   }

   private void applySliderInput(String var1) {
      if (this.editingSlider != null) {
         String var2 = var1 == null ? "" : var1.trim();
         if (!var2.isEmpty()) {
            try {
               double var3 = Double.parseDouble(var2);
               this.editingSlider.setValueClamped(var3);
               this.animatedSliderValues.put(this.editingSlider, this.editingSlider.getInput());
               Module var5 = this.getOwnerModuleOfSlider(this.editingSlider);
               if (var5 != null) {
                  var5.guiSliderChanged(this.editingSlider);
               }

               this.markUnsaved(var5);
               if (this.editingSlider == Gui.guiScale) {
                  this.refreshAfterProfileLoad();
               }
            } catch (NumberFormatException var6) {
            }
         }

         this.MldK();
      }
   }

   private void applyColorSettingInput(String var1) {
      if (this.gdj != null) {
         int[] var2 = this.parseHexColor(var1);
         if (var2 != null) {
            if (this.gdj.supportsAlpha() && var2.length >= 4) {
               this.gdj.setRgba(var2[0], var2[1], var2[2], var2[3]);
            } else {
               this.gdj.setRgb(var2[0], var2[1], var2[2]);
            }

            this.markUnsaved(this.ZZPUa7(this.gdj));
         }

         this.MldK();
      }
   }

   private void applyEntryColorInput(String var1) {
      int[] var2 = this.parseHexColor(var1);
      if (this.NSq4 != null && this.editingBlockEntry != null && var2 != null) {
         int var3 = 0xFF000000 | var2[0] << 16 | var2[1] << 8 | var2[2];
         this.WOvsh(var3);
         float[] var4 = Color.RGBtoHSB(var2[0], var2[1], var2[2], null);
         this.entryColorHue = var4[0];
         this.entryColorSaturation = var4[1];
         this.entryColorBrightness = var4[2];
         this.markUnsaved(this.ZZPUa7(this.NSq4));
      }

      this.MldK();
   }

   private void RcKo(ColorSetting var1) {
      this.closeEntryColorPicker();
      boolean var2 = this.colorPickerOpenSettings.contains(var1);
      this.colorPickerOpenSettings.clear();
      if (!var2) {
         this.colorPickerOpenSettings.add(var1);
      }
   }

   private ColorSetting getOpenPickerColorSetting() {
      Iterator var1 = this.colorPickerOpenSettings.iterator();
      return var1.hasNext() ? (ColorSetting)var1.next() : null;
   }

   private void MldK() {
      if (this.editingSlider != null) {
         TextField var1 = this.IDSVr.get(this.editingSlider);
         if (var1 != null) {
            var1.setFocused(false);
         }
      }

      if (this.gdj != null) {
         TextField var2 = this.tvH.get(this.gdj);
         if (var2 != null) {
            var2.setFocused(false);
         }
      }

      if (this.editingEntryColorHex) {
         this.entryColorHexField.setFocused(false);
      }

      if (this.editingTextSetting != null) {
         TextField var3 = this.textSettingFields.get(this.editingTextSetting);
         if (var3 != null) {
            var3.setFocused(false);
         }
      }

      if (this.editingStringList != null) {
         TextField var4 = this.stringListFields.get(this.editingStringList);
         if (var4 != null) {
            var4.setFocused(false);
         }
      }

      if (this.activeBlockList != null) {
         TextField var5 = this.Mj7.get(this.activeBlockList);
         if (var5 != null) {
            var5.setFocused(false);
         }
      }

      this.editingSlider = null;
      this.gdj = null;
      this.editingEntryColorHex = false;
      this.editingTextSetting = null;
      this.editingStringList = null;
      this.activeBlockList = null;
   }

   private boolean addBlockListEntry(BlockListSetting var1, String var2) {
      String var3 = var2 == null ? "" : var2.trim();
      if (var3.isEmpty()) {
         return false;
      } else {
         if (var1 instanceof ItemSlotListSetting) {
            ((ItemSlotListSetting)var1).addItem(var3);
         } else if (var1 instanceof ItemListSetting) {
            ((ItemListSetting)var1).addItem(var3);
         } else if (var1 instanceof NameListSetting) {
            ((NameListSetting)var1).appendEntry(var3);
         } else {
            var1.addEntry(var3);
         }

         return true;
      }
   }

   private void clearDropdownGeometryCaches() {
      if (this.dropdownClientRenderer != null) {
         this.dropdownClientRenderer.XRfL();
      }

      this.moduleExpandButtonRects.clear();
      this.umR.clear();
      this.moduleToggleRects.clear();
      this.moduleBindButtons.clear();
      this.moduleHiddenToggleRects.clear();
      this.UKsT.clear();
      this.Ei2.clear();
      this.modeSliderOptionRects.clear();
      this.NCOdbl.clear();
      this.multiSelectHeaderRects.clear();
      this.eJ7.clear();
      this.keySettingButtons.clear();
      this.colorPickerRects.clear();
      this.cr2.clear();
      this.colorValueFieldRects.clear();
      this.XYIJhz = null;
      this.textSettingFieldRects.clear();
      this.ofU.clear();
      this.CNc.clear();
      this.listEntryRemoveButtons.clear();
      this.ONuyuP.clear();
      this.cgw.clear();
      this.colorChipRightClickRects.clear();
      this.relationEntryRemoveRects.clear();
      this.categorySlotAddButtons.clear();
      this.categorySlotChipRects.clear();
      this.categoryPickerOptionRects.clear();
      this.nam.clear();
      this.configRowRects.clear();
      this.configSaveButtons.clear();
      this.PeGs9.clear();
      this.configBindButtons.clear();
      this.createConfigButtonRect = null;
      this.VwaR = null;
      this.openFolderButton = null;
      this.Tkzu = null;
      this.createConfigNameFieldRect = null;
      this.createConfigConfirmButton = null;
      this.createConfigCancelButton = null;
      this.DMpe = null;
      this.enemyInputRect = null;
      this.GTnX94 = null;
      this.zj9 = null;
      this.friendShowMoreRect = null;
      this.lB7 = null;
      this.sTwoz = null;
      this.NHv = null;
      this.ZRE = null;
      this.QLh = null;
      this.GKCt = null;
      this.Jiwn40 = null;
   }

   @Override
   protected GuiRect windowRect() {
      if (Gui.JzraV3()) {
         return this.dropdownLayoutState.hasOpenCategory() ? this.dropdownLayoutState.JjDy() : new GuiRect(0, 0, this.width, this.height - 32);
      } else {
         this.trwid = this.clampContainerWidth(this.trwid);
         this.fAxth0 = this.clampContainerHeight(this.fAxth0);
         this.sidebarWidth = this.nEpvsC(this.sidebarWidth);
         this.Evqe = this.clampContainerOffsetX(this.Evqe);
         this.Vbm = this.clampContainerOffsetY(this.Vbm);
         return new GuiRect((this.width - this.trwid) / 2 + this.Evqe, (this.height - this.fAxth0) / 2 + this.Vbm, this.trwid, this.fAxth0);
      }
   }

   @Override
   protected int sidebarW() {
      if (Gui.JzraV3()) {
         return 0;
      } else {
         this.sidebarWidth = this.nEpvsC(this.sidebarWidth);
         return this.sidebarWidth;
      }
   }

   private int clampContainerWidth(int var1) {
      if (this.width <= 0) {
         return Math.max(430, var1);
      } else {
         int var2 = Math.max(430, this.width - 32);
         return Math.max(430, Math.min(var2, var1));
      }
   }

   private int clampContainerHeight(int var1) {
      if (this.height <= 0) {
         return Math.max(270, var1);
      } else {
         int var2 = Math.max(270, this.height - 32);
         return Math.max(270, Math.min(var2, var1));
      }
   }

   private int nEpvsC(int var1) {
      int var2 = Math.max(92, this.trwid - 270);
      int var3 = Math.max(92, Math.min(170, var2));
      return Math.max(92, Math.min(var3, var1));
   }

   private int clampContainerOffsetX(int var1) {
      if (this.width <= 0) {
         return var1;
      } else {
         int var2 = (this.width - this.trwid) / 2;
         int var3 = Math.min(var2, 8);
         int var4 = Math.max(var2, this.width - this.trwid - 8);
         return Math.max(var3 - var2, Math.min(var4 - var2, var1));
      }
   }

   private int clampContainerOffsetY(int var1) {
      if (this.height <= 0) {
         return var1;
      } else {
         int var2 = (this.height - this.fAxth0) / 2;
         int var3 = Math.min(var2, 8);
         int var4 = Math.max(var2, this.height - this.fAxth0 - 8);
         return Math.max(var3 - var2, Math.min(var4 - var2, var1));
      }
   }

   private int getSidebarContentHeight() {
      int var1 = 20;

      for (Category var5 : CategoryNames.getOrderedCategories()) {
         if (var5 == Category.friends) {
            var1 += 20;
         }

         var1 += 27;
      }

      return var1;
   }

   private int hitTestResizeHandle(GuiRect var1, int var2, int var3) {
      if (!this.inflateRect(var1, 6).contains(var2, var3)) {
         return 0;
      } else {
         int var4 = var1.x + this.sidebarW();
         if (var3 >= var1.ufe && var3 < var1.ufe + var1.HfS && Math.abs(var2 - var4) <= 6) {
            return 18;
         } else {
            boolean var5 = Math.abs(var2 - var1.x) <= 6;
            boolean var6 = Math.abs(var2 - (var1.x + var1.busF)) <= 6;
            boolean var7 = Math.abs(var3 - var1.ufe) <= 6;
            boolean var8 = Math.abs(var3 - (var1.ufe + var1.HfS)) <= 6;
            if (var7 && var5) {
               return 16;
            } else if (var7 && var6) {
               return 14;
            } else if (var8 && var5) {
               return 17;
            } else if (var8 && var6) {
               return 15;
            } else if (var7) {
               return 12;
            } else if (var8) {
               return 13;
            } else {
               return var6 ? 11 : 0;
            }
         }
      }
   }

   private GuiRect inflateRect(GuiRect var1, int var2) {
      return new GuiRect(var1.x - var2, var1.ufe - var2, var1.busF + var2 * 2, var1.HfS + var2 * 2);
   }

   private void beginResizeDrag(int var1, int var2, int var3) {
      this.ISaB = var1;
      this.dragStartX = var2;
      this.OQmx = var3;
      this.dragStartWidth = this.trwid;
      this.dragStartHeight = this.fAxth0;
      this.enX = this.sidebarWidth;
   }

   private void updateResizeDrag(int var1, int var2) {
      if (this.ISaB != 0) {
         int var3 = var1 - this.dragStartX;
         int var4 = var2 - this.OQmx;
         if (this.ISaB == 18) {
            this.sidebarWidth = this.nEpvsC(this.enX + var3);
         } else {
            if (this.ISaB == 11 || this.ISaB == 14 || this.ISaB == 15) {
               this.trwid = this.clampContainerWidth(this.dragStartWidth + var3);
            } else if (this.ISaB == 16 || this.ISaB == 17) {
               this.trwid = this.clampContainerWidth(this.dragStartWidth - var3);
            }

            if (this.ISaB == 13 || this.ISaB == 15 || this.ISaB == 17) {
               this.fAxth0 = this.clampContainerHeight(this.dragStartHeight + var4);
            } else if (this.ISaB == 12 || this.ISaB == 14 || this.ISaB == 16) {
               this.fAxth0 = this.clampContainerHeight(this.dragStartHeight - var4);
            }

            this.sidebarWidth = this.nEpvsC(this.sidebarWidth);
         }
      }
   }

   private boolean NQhR(GuiRect var1, int var2, int var3) {
      return var2 >= var1.x && var2 < var1.x + this.sidebarW() && var3 >= var1.ufe && var3 < var1.ufe + var1.HfS
         ? !this.getSearchBoxRect(var1).contains(var2, var3)
            && !this.aIrlh6(var1).contains(var2, var3)
            && !this.getHudButtonRect(var1).contains(var2, var3)
            && !this.FHLMmH(var1).contains(var2, var3)
            && this.getCategoryAt(var2, var3) == null
         : false;
   }

   private void beginWindowMove(int var1, int var2) {
      this.movingWindow = true;
      this.moveStartX = var1;
      this.wx4 = var2;
      this.containerOffsetAtDragStart = this.Evqe;
      this.moveStartY = this.Vbm;
   }

   private void dragWindow(int var1, int var2) {
      if (this.movingWindow) {
         this.Evqe = this.clampContainerOffsetX(this.snapToZero(this.containerOffsetAtDragStart + var1 - this.moveStartX));
         this.Vbm = this.clampContainerOffsetY(this.snapToZero(this.moveStartY + var2 - this.wx4));
      }
   }

   private int snapToZero(int var1) {
      return Math.abs(var1) <= 10 ? 0 : var1;
   }

   private void lxzOnq() {
      if (this.movingWindow) {
         int var1 = this.Evqe == 0 ? 58 : 30;
         int var2 = this.Vbm == 0 ? 58 : 30;
         int var3 = this.width / 2;
         int var4 = this.height / 2;
         RenderUtils.XNRNki(var3, 0.0, var3 + 1, this.height, this.withAlphaValue(16777215, var1));
         RenderUtils.XNRNki(0.0, var4, this.width, var4 + 1, this.withAlphaValue(16777215, var2));
      }
   }

   private GuiRect getSearchBoxRect(GuiRect var1) {
      return Gui.JzraV3() ? this.dropdownLayoutState.getSearchFieldBounds() : new GuiRect(var1.x + 10, var1.ufe + 56, this.sidebarW() - 20, 23);
   }

   private int getSidebarListTop(GuiRect var1) {
      GuiRect var2 = this.getSearchBoxRect(var1);
      return var2.ufe + var2.HfS + 12;
   }

   private GuiRect getCategoryRowRect(GuiRect var1, int var2) {
      return new GuiRect(var1.x + 10, var2, this.sidebarW() - 20, 27);
   }

   private GuiRect aIrlh6(GuiRect var1) {
      byte var2 = 28;
      return new GuiRect(var1.x + 14, var1.ufe + var1.HfS - var2 - 12, var2, var2);
   }

   private GuiRect getHudButtonRect(GuiRect var1) {
      GuiRect var2 = this.aIrlh6(var1);
      byte var3 = 28;
      return new GuiRect(var2.x + var2.busF + 6, var2.ufe, var3, var3);
   }

   private GuiRect FHLMmH(GuiRect var1) {
      GuiRect var2 = this.getHudButtonRect(var1);
      byte var3 = 28;
      return new GuiRect(var2.x + var2.busF + 6, var2.ufe, var3, var3);
   }

   private void NieD() {
      boolean var1 = Gui.darkBackground == null || Gui.darkBackground.isToggled();
      int var2 = var1 ? -436207616 : -1476395008;
      int var3 = var1 ? 1979711488 : 1140850688;
      RenderUtils.drawVerticalGradient(0.0F, 0.0F, this.width, this.height, var2, var3);
   }

   private void drawScrollbarThumb(int var1, int var2, int var3, int var4, int var5) {
      if (var4 > var3 && var3 > 0) {
         int var6 = Math.max(18, Math.round(var3 * ((float)var3 / var4)));
         int var7 = Math.max(1, var4 - var3);
         int var8 = var2 + Math.round((var3 - var6) * ((float)var5 / var7));
         RenderUtils.XNRNki(var1, var2, var1 + 2, var2 + var3, 285212671);
         RenderUtils.XNRNki(var1, var8, var1 + 2, var8 + var6, 822083583);
      }
   }

   private void clampCategoryScroll(Map<Category, AnimatedFloat> var1, Category var2, int var3, int var4) {
      this.clampScroll(this.LYepv7(var1, var2), var3, var4);
   }

   @Override
   protected void clampScroll(AnimatedFloat var1, int var2, int var3) {
      var1.clampTarget(0.0F, Math.max(0, var2 - var3));
      if (!var1.isAnimating()) {
         var1.snapTo(Math.max(0.0F, Math.min(var1.getTargetValue(), (float)Math.max(0, var2 - var3))));
      }
   }

   private AnimatedFloat getActiveContentScroll() {
      return !this.normalizedSearch().isEmpty() && !this.searchScopedToCategory ? this.RClfTp : this.LYepv7(this.categoryScrollAnimations, this.currentCategory);
   }

   private Category getScrollFadeCategory() {
      return !this.normalizedSearch().isEmpty() && !this.searchScopedToCategory ? Category.client : this.currentCategory;
   }

   private AnimatedFloat LYepv7(Map<Category, AnimatedFloat> var1, Category var2) {
      AnimatedFloat var3 = (AnimatedFloat)var1.get(var2);
      if (var3 == null) {
         var3 = new AnimatedFloat(160L);
         var1.put(var2, var3);
      }

      return var3;
   }

   private void drawCompactToggle(int var1, int var2, boolean var3, boolean var4) {
      this.IKGFu(var1, var2, var3, var4, 30, 16, null);
   }

   private void BBDyb(int var1, int var2, boolean var3, boolean var4, int var5, int var6) {
      this.IKGFu(var1, var2, var3, var4, var5, var6, null);
   }

   private void IKGFu(int var1, int var2, boolean var3, boolean var4, int var5, int var6, Object var7) {
      float var8 = this.animateTowards(var7 == null ? var3 : var7, var3 ? 1.0F : 0.0F);
      int var9 = var4 ? -15790321 : -16316665;
      int var10 = var4 ? this.withAccentAlpha(215) : this.bgZk();
      int var11 = this.lerpColor(var9, var10, var8);
      this.round(var1, var2, var5, var6, var6 / 2.0F, var11);
      int var12 = this.lerpColor(var4 ? -11184811 : -13421773, var4 ? -986896 : -11184811, var8);
      int var13 = var6 - 4;
      int var14 = var1 + 2 + Math.round((var5 - var13 - 4) * var8);
      this.round(var14, var2 + 2, var13, var13, var13 / 2.0F, var12);
   }

   @Override
   protected void drawRelationshipToggle(int var1, int var2, boolean var3, int var4, int var5, Object var6) {
      this.IKGFu(var1, var2, var3, true, var4, var5, var6);
   }

   private void drawHueBar(int var1, int var2, int var3, int var4) {
      this.mc.getTextureManager().bindTexture(this.getHueBarTexture());
      GL11.glTexParameteri(3553, 10241, 9729);
      GL11.glTexParameteri(3553, 10240, 9729);
      GL11.glTexParameteri(3553, 10242, 33071);
      GL11.glTexParameteri(3553, 10243, 33071);
      RoundedRect.drawRoundedTexture(var1, var2, var3, var4, var3 / 2.0F, 1.0F);
   }

   private ResourceLocation getHueBarTexture() {
      if (this.hueBarTexture == null) {
         BufferedImage var1 = new BufferedImage(2, 256, 2);

         for (int var2 = 0; var2 < 256; var2++) {
            int var3 = Color.HSBtoRGB(var2 / 255.0F, 1.0F, 1.0F) | 0xFF000000;
            var1.setRGB(0, var2, var3);
            var1.setRGB(1, var2, var3);
         }

         this.hueBarTexture = this.mc.getTextureManager().getDynamicTextureLocation("jade_color_hue_bar", new DynamicTexture(var1));
      }

      return this.hueBarTexture;
   }

   private void drawCategoryIcon(Category var1, int var2, int var3, int var4, int var5) {
      this.kwGva.drawCategoryIcon(var1, var2, var3, var4, var5);
   }

   @Override
   protected void drawSearchIcon(int var1, int var2, int var3, int var4) {
      this.kwGva.HViKr(var1, var2, var3, var4);
   }

   private ResourceLocation Xwui() {
      return this.kwGva.GOXpq();
   }

   private ResourceLocation getHiddenEyeIcon() {
      return this.kwGva.esvk();
   }

   private void drawVectorIcon(IconType var1, int var2, int var3, int var4, int var5) {
      this.kwGva.drawIcon(var1, var2, var3, var4, var5);
   }

   private void drawLogo(float var1, float var2, float var3, int var4) {
      this.kwGva.drawLogo(var1, var2, var3, var4);
   }

   private String applyTextCapitalization(String var1) {
      return Gui.isCapitalizeTextEnabled() ? var1.toUpperCase() : var1;
   }

   private String getModeOptionLabel(SliderSetting var1) {
      String[] var2 = var1.getOptions();
      if (var2 != null && var2.length != 0) {
         int var3 = Math.max(0, Math.min(var2.length - 1, (int)Math.round(var1.getInput())));
         return var2[var3];
      } else {
         return "";
      }
   }

   private String formatTrimmedDecimal(double var1) {
      return Math.abs(var1 - Math.round(var1)) < 1.0E-4
         ? String.valueOf((int)Math.round(var1))
         : String.format("%.2f", var1).replaceAll("0+$", "").replaceAll("\\.$", "");
   }

   private String formatSliderValue(double var1, SliderSetting var3) {
      int var4 = this.getDecimalPlaces(var3.getStep());
      double var5 = Math.pow(10.0, var4);
      double var7 = Math.round(var1 * var5) / var5;
      if (var4 == 0) {
         return String.valueOf((int)Math.round(var7));
      } else {
         String var9 = String.format("%." + var4 + "f", var7);
         return var9.replaceAll("0+$", "").replaceAll("\\.$", "");
      }
   }

   private String formatColorHex(ColorSetting var1) {
      return var1.supportsAlpha()
         ? String.format("#%02X%02X%02X%02X", var1.getRed(), var1.getGreen(), var1.getBlue(), var1.JIjrD())
         : String.format("#%02X%02X%02X", var1.getRed(), var1.getGreen(), var1.getBlue());
   }

   private String formatRgbHex(int var1) {
      return String.format("#%06X", var1 & 16777215);
   }

   private int[] parseHexColor(String var1) {
      String var2 = var1 == null ? "" : var1.trim();
      if (var2.startsWith("#")) {
         var2 = var2.substring(1);
      } else if (var2.startsWith("0x") || var2.startsWith("0X")) {
         var2 = var2.substring(2);
      }

      if (var2.length() == 3) {
         var2 = "" + var2.charAt(0) + var2.charAt(0) + var2.charAt(1) + var2.charAt(1) + var2.charAt(2) + var2.charAt(2);
      }

      if (var2.length() != 6 && var2.length() != 8) {
         return null;
      } else {
         for (int var3 = 0; var3 < var2.length(); var3++) {
            if (Character.digit(var2.charAt(var3), 16) == -1) {
               return null;
            }
         }

         int var7 = Integer.parseInt(var2.substring(0, 2), 16);
         int var4 = Integer.parseInt(var2.substring(2, 4), 16);
         int var5 = Integer.parseInt(var2.substring(4, 6), 16);
         if (var2.length() == 8) {
            int var6 = Integer.parseInt(var2.substring(6, 8), 16);
            return new int[]{var7, var4, var5, var6};
         } else {
            return new int[]{var7, var4, var5};
         }
      }
   }

   private int getDecimalPlaces(double var1) {
      double var3 = Math.abs(var1);
      if (!(var3 <= 0.0) && !(Math.abs(var3 - Math.round(var3)) < 1.0E-6)) {
         int var5 = 0;

         while (var5 < 4 && Math.abs(var3 * Math.pow(10.0, var5) - Math.round(var3 * Math.pow(10.0, var5))) > 1.0E-6) {
            var5++;
         }

         return var5;
      } else {
         return 0;
      }
   }

   private String getKeyDisplayName(int var1) {
      return KeySetting.SBJv(var1);
   }

   private int getAccentColor() {
      if (Gui.JzraV3()) {
         return GuiTheme.xGoxa();
      } else {
         return Gui.accent == null ? -15030151 : Gui.accent.getArgb() | 0xFF000000;
      }
   }

   private int getBrightAccentColor() {
      int var1 = this.getAccentColor();
      int var2 = var1 >> 16 & 0xFF;
      int var3 = var1 >> 8 & 0xFF;
      int var4 = var1 & 0xFF;
      float[] var5 = Color.RGBtoHSB(var2, var3, var4, null);
      return var5[1] < 0.08F ? var1 : Color.HSBtoRGB(var5[0], Math.max(0.58F, var5[1]), Math.max(0.86F, var5[2])) | 0xFF000000;
   }

   private int bgZk() {
      return this.scaleColorRgb(this.getAccentColor(), 0.25F);
   }

   private int withAccentAlpha(int var1) {
      return Math.max(0, Math.min(255, var1)) << 24 | this.getAccentColor() & 16777215;
   }

   private int yVsd(int var1) {
      return Math.max(0, Math.min(255, var1)) << 24 | this.bgZk() & 16777215;
   }

   private int getAccentContrastText() {
      int var1 = this.getAccentColor();
      int var2 = var1 >> 16 & 0xFF;
      int var3 = var1 >> 8 & 0xFF;
      int var4 = var1 & 0xFF;
      int var5 = Math.max(var2, Math.max(var3, var4));
      int var6 = Math.min(var2, Math.min(var3, var4));
      float var7 = 0.2126F * var2 + 0.7152F * var3 + 0.0722F * var4;
      boolean var8 = var6 > 205 && var5 - var6 < 38;
      boolean var9 = var7 > 232.0F;
      return !var8 && !var9 ? -986896 : -16448251;
   }

   private int withAlphaValue(int var1, int var2) {
      return Math.max(0, Math.min(255, var2)) << 24 | var1 & 16777215;
   }

   private int scaleColorRgb(int var1, float var2) {
      int var3 = Math.max(0, Math.min(255, Math.round((var1 >> 16 & 0xFF) * var2)));
      int var4 = Math.max(0, Math.min(255, Math.round((var1 >> 8 & 0xFF) * var2)));
      int var5 = Math.max(0, Math.min(255, Math.round((var1 & 0xFF) * var2)));
      return 0xFF000000 | var3 << 16 | var4 << 8 | var5;
   }

   private int lerpColor(int var1, int var2, float var3) {
      var3 = Math.max(0.0F, Math.min(1.0F, var3));
      int var4 = var1 >> 24 & 0xFF;
      int var5 = var1 >> 16 & 0xFF;
      int var6 = var1 >> 8 & 0xFF;
      int var7 = var1 & 0xFF;
      int var8 = var2 >> 24 & 0xFF;
      int var9 = var2 >> 16 & 0xFF;
      int var10 = var2 >> 8 & 0xFF;
      int var11 = var2 & 0xFF;
      return (int)(var4 + (var8 - var4) * var3) << 24
         | (int)(var5 + (var9 - var5) * var3) << 16
         | (int)(var6 + (var10 - var6) * var3) << 8
         | (int)(var7 + (var11 - var7) * var3);
   }

   @Override
   protected void round(float var1, float var2, float var3, float var4, float var5, int var6) {
      if (!(var3 <= 0.0F) && !(var4 <= 0.0F)) {
         if (Gui.JzraV3()) {
            RenderUtils.XNRNki(var1, var2, var1 + var3, var2 + var4, var6);
         } else {
            RoundedRect.drawRoundedRectArgb(var1, var2, var3, var4, Math.max(0.0F, var5), var6);
         }
      }
   }

   private void drawRoundedPanel(float var1, float var2, float var3, float var4, float var5, int var6) {
      this.round(var1, var2, var3, var4, var5, var6);
   }

   private void drawDoubleOutline(float var1, float var2, float var3, float var4, float var5, int var6, int var7) {
      if (Gui.JzraV3()) {
         GuiIcons.drawRectBorder(var1, var2, var3, var4, var6);
      } else {
         RoundedRect.drawRoundedOutline(var1, var2, var3, var4, var5, 1.0F, new Color(0, 0, 0, 0), new Color(var6, true));
         RoundedRect.drawRoundedOutline(var1 + 1.0F, var2 + 1.0F, var3 - 2.0F, var4 - 2.0F, Math.max(0.0F, var5 - 1.0F), 1.0F, new Color(0, 0, 0, 0), new Color(var7, true));
      }
   }

   private void drawSoftOutline(float var1, float var2, float var3, float var4, float var5, int var6) {
      if (Gui.JzraV3()) {
         GuiIcons.drawRectBorder(var1, var2, var3, var4, var6);
      } else {
         RoundedRect.drawRoundedOutline(var1, var2, var3, var4, var5, 0.08F, new Color(0, 0, 0, 0), new Color(var6, true));
      }
   }

   @Override
   protected void edgeOutline(float var1, float var2, float var3, float var4, float var5) {
      if (Gui.JzraV3()) {
         GuiIcons.drawRectBorder(var1, var2, var3, var4, 613258909);
      } else {
         RoundedRect.drawRoundedOutline(var1, var2, var3, var4, var5, 0.22F, new Color(0, 0, 0, 0), new Color(1711276032, true));
         RoundedRect.drawRoundedOutline(
            var1 + 0.5F, var2 + 0.5F, var3 - 1.0F, var4 - 1.0F, Math.max(0.0F, var5 - 0.5F), 0.22F, new Color(0, 0, 0, 0), new Color(352321535, true)
         );
      }
   }

   private void drawFaintOverlay(float var1, float var2, float var3, float var4, float var5, int var6) {
      if (Gui.JzraV3()) {
         GuiIcons.drawRectBorder(var1, var2, var3, var4, var6);
      } else if (!(var3 <= 2.0F) && !(var4 <= 2.0F)) {
         RoundedRect.drawRoundedOutline(var1, var2, var3, var4, var5, 0.1F, new Color(0, 0, 0, 0), new Color(var6, true));
      }
   }

   private void drawLightOverlay(float var1, float var2, float var3, float var4, float var5, int var6) {
      if (Gui.JzraV3()) {
         GuiIcons.drawRectBorder(var1, var2, var3, var4, var6);
      } else if (!(var3 <= 2.0F) && !(var4 <= 2.0F)) {
         RoundedRect.drawRoundedOutline(var1, var2, var3, var4, var5, 0.12F, new Color(0, 0, 0, 0), new Color(var6, true));
      }
   }

   private IFont getTitleFont() {
      return Gui.getHeaderFont();
   }

   private IFont nqA2() {
      return FontManager.getClickGuiHeaderRenderer("Bold");
   }

   @Override
   protected IFont settingFont() {
      return Gui.getSettingFont();
   }

   private IFont getValueFont() {
      return Gui.JzraV3() ? this.settingFont() : FontManager.getClickGuiSettingRenderer("Modern");
   }

   @Override
   protected void drawHeader(String var1, float var2, float var3, int var4) {
      this.drawText(var1, var2, var3, var4, this.getTitleFont());
   }

   private void nRtk0(String var1, float var2, float var3, int var4) {
      GL11.glPushMatrix();
      GL11.glTranslatef(var2, var3, 0.0F);
      GL11.glScalef(1.28F, 1.28F, 1.0F);
      if (Gui.JzraV3()) {
         this.getTitleFont().drawString(var1 == null ? "" : var1, 0.8F, 0.8F, var4 & 0xFF000000, false);
      }

      this.getTitleFont().drawString(var1 == null ? "" : var1, 0.0F, 0.0F, var4, false);
      GL11.glPopMatrix();
   }

   @Override
   protected void drawSmall(String var1, float var2, float var3, int var4) {
      this.drawText(var1, var2, var3, var4, this.settingFont());
   }

   private void drawPlainText(String var1, float var2, float var3, int var4, IFont var5) {
      GL11.glPushMatrix();
      GL11.glTranslatef(var2, var3, 0.0F);
      GL11.glScalef(1.0F, 1.0F, 1.0F);
      var5.drawString(var1 == null ? "" : var1, 0.0F, 0.0F, var4, false);
      GL11.glPopMatrix();
   }

   private void WZTcG(String var1, float var2, float var3, int var4, IFont var5) {
      GL11.glPushMatrix();
      GL11.glTranslatef(var2, var3, 0.0F);
      GL11.glScalef(1.18F, 1.18F, 1.0F);
      var5.drawString(var1 == null ? "" : var1, 0.0F, 0.0F, var4, false);
      GL11.glPopMatrix();
   }

   private float centeredFontTextY(float var1, float var2, IFont var3) {
      float var4 = Math.max(1.0F, (var3.getTextBottomOffset() - var3.getTextTopOffset()) * 1.0F);
      return var1 + (var2 - var4) / 2.0F - var3.getTextTopOffset() * 1.0F;
   }

   @Override
   protected void drawText(String var1, float var2, float var3, int var4, IFont var5) {
      GL11.glPushMatrix();
      GL11.glTranslatef(var2, var3, 0.0F);
      GL11.glScalef(1.1F, 1.1F, 1.0F);
      if (Gui.JzraV3()) {
         var5.drawString(var1 == null ? "" : var1, 0.72727275F, 0.72727275F, var4 & 0xFF000000, false);
      }

      var5.drawString(var1 == null ? "" : var1, 0.0F, 0.0F, var4, false);
      GL11.glPopMatrix();
   }

   @Override
   protected void drawCenteredText(String var1, float var2, float var3, int var4, IFont var5) {
      this.drawText(var1, var2 - this.textWidth(var1, var5) / 2.0F, var3, var4, var5);
   }

   @Override
   protected float centeredTextY(float var1, float var2, IFont var3) {
      float var4 = Math.max(1.0F, (var3.getTextBottomOffset() - var3.getTextTopOffset()) * 1.1F);
      return var1 + (var2 - var4) / 2.0F - var3.getTextTopOffset() * 1.1F;
   }

   @Override
   protected int textWidth(String var1, IFont var2) {
      return Math.round(var2.getStringWidth(var1 == null ? "" : var1) * 1.1F);
   }

   private int getRawTextWidth(String var1, IFont var2) {
      return Math.round(var2.getStringWidth(var1 == null ? "" : var1) * 1.0F);
   }

   @Override
   protected String trimToWidth(String var1, int var2, IFont var3) {
      if (this.textWidth(var1, var3) <= var2) {
         return var1;
      } else {
         String var4 = "...";
         int var5 = Math.max(0, var2 - this.textWidth(var4, var3));
         StringBuilder var6 = new StringBuilder();

         for (int var7 = 0; var7 < var1.length(); var7++) {
            String var8 = var6.toString() + var1.charAt(var7);
            if (this.textWidth(var8, var3) > var5) {
               break;
            }

            var6.append(var1.charAt(var7));
         }

         return var6.toString() + var4;
      }
   }

   private String OKJT(String var1, int var2, IFont var3) {
      if (this.getRawTextWidth(var1, var3) <= var2) {
         return var1;
      } else {
         String var4 = "...";
         int var5 = Math.max(0, var2 - this.getRawTextWidth(var4, var3));
         StringBuilder var6 = new StringBuilder();

         for (int var7 = 0; var7 < var1.length(); var7++) {
            String var8 = var6.toString() + var1.charAt(var7);
            if (this.getRawTextWidth(var8, var3) > var5) {
               break;
            }

            var6.append(var1.charAt(var7));
         }

         return var6.toString() + var4;
      }
   }

   private static int NPlaP(Module var0, Module var1) {
      return var0.getName().compareToIgnoreCase(var1.getName());
   }

   private static int compareModulesByName(Module var0, Module var1) {
      return var0.getName().compareToIgnoreCase(var1.getName());
   }

   private int compareConfigProfiles(ConfigProfile var1, ConfigProfile var2) {
      if (this.isDefaultConfigProfile(var1)) {
         return -1;
      } else {
         return this.isDefaultConfigProfile(var2) ? 1 : var1.getName().compareToIgnoreCase(var2.getName());
      }
   }

   public static boolean UgeWb(JadeClickGui var0, boolean var1) {
      return var0.uninjectInProgress = var1;
   }

   public static String KSnu(JadeClickGui var0, String var1) {
      return var0.uninjectErrorMessage = var1;
   }

   public static MultiSelectSetting createCachedMultiSelectSetting(JadeClickGui var0, Module var1, String var2, List var3, String[] var4) {
      return var0.getOrCreateMultiSelectSetting(var1, var2, var3, var4);
   }
}
