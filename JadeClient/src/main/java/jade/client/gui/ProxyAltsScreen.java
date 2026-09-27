// Jade recovery: original class: jade.deps.eLz.LWOXjn1
package jade.client.gui;

import jade.client.Jade;
import jade.client.common.Account;
import jade.client.common.AccountStore;
import jade.client.common.AccountType;
import jade.client.common.ConfigFolder;
import jade.client.common.FontManager;
import jade.client.common.IFont;
import jade.client.common.LoginMethod;
import jade.client.common.ProxyManager;
import jade.client.common.RenderUtils;
import jade.client.common.RoundedRect;
import jade.client.common.SessionAccessor;
import jade.client.common.SkinCache;
import jade.client.module.client.Gui;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class ProxyAltsScreen extends GuiScreen {
   private static final int MAX_TOKEN_LENGTH = 500;
   private static final int EvnWbp = 12;
   private static final int TEXT_FIELD_HEIGHT = 20;
   private static final int qxN70 = 20;
   private static final int NXe = 112;
   private static final int CARD_HEIGHT = 136;
   private static final int PANEL_PADDING = 12;
   private static final int SECTION_GAP = 10;
   private static final int HEADER_HEIGHT = 38;
   private static final int FOOTER_HEIGHT = 36;
   private static final int PANEL_MAX_WIDTH = 480;
   private static final float qrju = 4.0F;
   private static final int WHITE_COLOR = -1;
   private static final int HINT_TEXT_COLOR = -6250336;
   private static final int MUTED_TEXT_COLOR = -9408400;
   private static final int FIELD_BACKGROUND_COLOR = -15658735;
   private static final int KfP = -15066598;
   private static final int LIST_BACKGROUND_COLOR = -15658735;
   private static final int NPn = 1711276032;
   private static final int OUTLINE_COLOR = 352321535;
   private static final int CARD_COLOR = -15197921;
   private static final int CARD_PROXY_COLOR = -14868956;
   private static final int CARD_HOVER_COLOR = -14539990;
   private static final int IkN = -15648481;
   private final GuiScreen parentScreen;
   private final List<ProxyAltsScreen$7> visibleEntries = new ArrayList<>();
   private final AtomicReference<String> statusMessage = new AtomicReference<>("");
   private GuiTextField proxyOverrideField;
   private GuiTextField tokenField;
   private GuiTextField dialogField;
   private GuiTextField skinField;
   private GuiTextField searchField;
   private final AnimatedFloat VUcj = new AnimatedFloat(480L);
   private int Tma;
   private int dragStartY;
   private Account GYH;
   private ConfigFolder asC;
   private boolean dragging;
   private long lastFolderClickMs;
   private ConfigFolder lastClickedFolder;
   private ConfigFolder HQSgE;
   private boolean loginInProgress;
   private String hintText = "";
   private CompletableFuture<Account> Jy1;
   private Account pendingAccount;
   private ProxyAltsScreen$8 lvyn = ProxyAltsScreen$8.NONE;
   private Account JQTFl;
   private ConfigFolder editingFolder;
   private boolean zcK;
   private ProxyAltsScreen$6 ZepH;
   private Component awtCanvas;
   private DropTarget FVWvM8;

   public ProxyAltsScreen() {
      this(null);
   }

   public ProxyAltsScreen(GuiScreen var1) {
      this.parentScreen = var1;
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      this.createTextFields();
      this.installDropTarget();
   }

   private void createTextFields() {
      int var1 = this.getPanelLeft();
      int var2 = this.getPanelWidth();
      this.proxyOverrideField = new GuiTextField(1, this.mc.fontRendererObj, var1 + 118, 36, var2 - 118 - 130, 20);
      this.proxyOverrideField.setMaxStringLength(255);
      this.proxyOverrideField.setText(ProxyManager.rfB4().UNCjvL());
      this.proxyOverrideField.setEnableBackgroundDrawing(false);
      this.tokenField = new GuiTextField(2, this.mc.fontRendererObj, var1, this.height - 62, var2, 20);
      this.tokenField.setMaxStringLength(50000);
      this.tokenField.setEnableBackgroundDrawing(false);
      this.dialogField = new GuiTextField(3, this.mc.fontRendererObj, 0, 0, 0, 20);
      this.dialogField.setMaxStringLength(255);
      this.dialogField.setEnableBackgroundDrawing(false);
      this.skinField = new GuiTextField(4, this.mc.fontRendererObj, 0, 0, 0, 20);
      this.skinField.setMaxStringLength(255);
      this.skinField.setEnableBackgroundDrawing(false);
      this.searchField = new GuiTextField(5, this.mc.fontRendererObj, 0, 0, 0, 20);
      this.searchField.setMaxStringLength(64);
      this.searchField.setEnableBackgroundDrawing(false);
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.updateLoginState();
      if (this.parentScreen != null) {
         this.parentScreen.drawScreen(var1, var2, var3);
      } else {
         this.drawDefaultBackground();
      }

      drawRect(0, 0, this.width, this.height, -1342177280);
      int var4 = this.getPanelLeft();
      int var5 = this.getPanelWidth();
      int var6 = this.getPanelTop();
      int var7 = var4 - 12;
      int var8 = this.getPanelHeight();
      int var9 = var5 + 24;
      this.fillRoundedRect(var7, var6, var9, var8, 10.0F, -15066598);
      this.drawPanelOutline(var7, var6, var9, var8, 10.0F);
      AltManagerIcons.drawContainerLogoIcon(var4, var6 + 9, 18, this.QUPa());
      this.drawString(this.fontRendererObj, EnumChatFormatting.BOLD.toString() + "Proxy & Alts", var4 + 26, var6 + 14, -1);
      this.drawIconButton(ProxyAltsScreen$5.CLOSE, var4 + var5 - 24, var6 + 8, this.CURbqW(var1, var2, var4 + var5 - 24, var6 + 8, 20, 20), false);
      this.drawAccountsHeader(var1, var2, var4, var5);
      this.drawBottomBar(var1, var2, var4, var5);
      if (this.dragging && this.GYH != null) {
         this.drawString(this.fontRendererObj, this.GYH.zYgb(), var1 + 8, var2 + 8, -1);
      }

      if (this.dragging && this.asC != null) {
         this.drawString(this.fontRendererObj, EnumChatFormatting.BOLD.toString() + this.asC.getTitle(), var1 + 8, var2 + 8, -1);
      }

      if (this.lvyn != ProxyAltsScreen$8.NONE) {
         this.drawDialog(var1, var2);
      }
   }

   private void drawAccountsHeader(int var1, int var2, int var3, int var4) {
      int var5 = this.getPanelTop() + 38 + 10;
      int var6 = var3 + var4 - 104;
      if (this.HQSgE == null) {
         Object var7 = "Your Accounts (" + AccountStore.getAccounts().size() + ")";
         this.drawString(this.fontRendererObj, EnumChatFormatting.BOLD.toString() + var7, var3, var5, -1);
         int var8 = this.getHeaderFont().getStringWidth((String)var7);
         int var9 = var3 + var8 + 10;
         int var10 = Math.max(82, var6 - var9 - 8);
         this.searchField.xPosition = var9;
         this.searchField.yPosition = var5 - 5;
         jade.build.TextFieldAccess.setWidth(this.searchField, var10);
         this.drawTextField(this.searchField, "Search", 0);
      } else {
         AltManagerIcons.drawFolderIcon(var3, var5 - 2, 14, -1);
         this.drawString(this.fontRendererObj, EnumChatFormatting.BOLD.toString() + this.HQSgE.getTitle(), var3 + 20, var5, -1);
         this.drawIconButton(ProxyAltsScreen$5.CLOSE, var3 + var4 - 24, var5 - 6, this.CURbqW(var1, var2, var3 + var4 - 24, var5 - 6, 20, 20), false);
      }

      if (this.HQSgE == null) {
         this.drawActionButton("Create Folder", var6, var5 - 5, 104, 20, this.CURbqW(var1, var2, var6, var5 - 5, 104, 20), false);
      }

      int var16 = this.getContentTop();
      int var17 = this.getListBottom();
      this.fillRoundedRect(var3, var16, var4, var17 - var16, 8.0F, -15658735);
      this.DiAf();
      int var18 = this.EanExk(var4);
      int var19 = this.getMaxScroll(var18, var17 - var16);
      float var11 = this.WQsJb7(var19);
      if (this.visibleEntries.isEmpty()) {
         this.drawCenteredString(this.fontRendererObj, this.isSearching() ? "No matching accounts" : "No accounts saved", this.width / 2, var16 + 24, -9408400);
      } else {
         RenderUtils.pushScissorRect(var3, var16, var4, var17 - var16);

         for (int var12 = 0; var12 < this.visibleEntries.size(); var12++) {
            int var13 = this.nnnOb(var12, var11);
            if (var13 <= var17 && var13 + 136 >= var16) {
               ProxyAltsScreen$7 var14 = this.visibleEntries.get(var12);
               int var15 = this.qkQeguI(var12, var3, var4);
               if (ProxyAltsScreen$7.getConfigFolder(var14) != null) {
                  this.drawFolderCard(ProxyAltsScreen$7.getConfigFolder(var14), var1, var2, var15, var13);
               } else if (ProxyAltsScreen$7.getAccount(var14) != null) {
                  this.FCuC5(ProxyAltsScreen$7.getAccount(var14), var1, var2, var15, var13);
               }
            }
         }

         RenderUtils.restoreScissorState();
      }
   }

   private void drawFolderCard(ConfigFolder var1, int var2, int var3, int var4, int var5) {
      boolean var6 = this.CURbqW(var2, var3, var4, var5, 112, 136);
      this.fillRoundedRect(var4, var5, 112.0F, 136.0F, 8.0F, var6 ? -14408404 : -15197921);
      AltManagerIcons.drawFolderIcon(var4 + 38, var5 + 34, 36, var6 ? -1 : -6250336);
      this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.BOLD.toString() + this.lgWg(var1.getTitle(), 94, this.fontRendererObj), var4 + 56, var5 + 92, -1);
      this.drawCenteredString(this.fontRendererObj, this.countAccountsInFolder(var1.getId()) + " accounts", var4 + 56, var5 + 110, -6250336);
      if (var6) {
         int var7 = var4 + 112 - 25;
         this.drawIconButton(ProxyAltsScreen$5.DELETE, var7, var5 + 4, this.CURbqW(var2, var3, var7, var5 + 4, 20, 20), false);
      }
   }

   private void FCuC5(Account var1, int var2, int var3, int var4, int var5) {
      boolean var6 = this.dcRifP(var1);
      boolean var7 = this.CURbqW(var2, var3, var4, var5, 112, 136);
      this.fillRoundedRect(var4, var5, 112.0F, 136.0F, 8.0F, var6 ? -15648481 : (var7 ? -14539990 : (var1.getFolderId().length() > 0 ? -14868956 : -15197921)));
      this.drawAccountHead(var1, var4 + 33, var5 + 18, 46);
      Object var8 = this.lgWg(var1.zYgb(), 98, this.fontRendererObj);
      int var9 = var5 + 92;
      this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.BOLD.toString() + var8, var4 + 56, var9, var1.hasStatusMessage() ? -28528 : -1);
      if (var6) {
         this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.BOLD.toString() + "Active", var4 + 56, var5 + 110, -13041800);
      } else {
         String var10 = this.getBanText(var1);
         if (var10.length() > 0) {
            this.drawCenteredString(this.fontRendererObj, this.lgWg(var10, 100, this.fontRendererObj), var4 + 56, var5 + 110, this.getBanTextColor(var1));
         }
      }

      if (var1.hasStatusMessage()) {
         this.drawCenteredString(this.fontRendererObj, this.lgWg(var1.getStatusMessage(), 100, this.fontRendererObj), var4 + 56, var5 + 122, -34953);
      }

      int var13 = var4 + 112 - 23;
      int var11 = var13 - 18;
      int var12 = var11 - 18;
      if (var7 || var1.gDhkZ5()) {
         this.drawIconButton(ProxyAltsScreen$5.PROXY, var11, var5 + 4, this.CURbqW(var2, var3, var11, var5 + 4, 20, 20), var1.gDhkZ5());
      }

      if (var7) {
         this.drawIconButton(ProxyAltsScreen$5.MORE, var12, var5 + 4, this.CURbqW(var2, var3, var12, var5 + 4, 20, 20), false);
         this.drawIconButton(ProxyAltsScreen$5.DELETE, var13, var5 + 4, this.CURbqW(var2, var3, var13, var5 + 4, 20, 20), false);
      }
   }

   private void drawAccountHead(Account var1, int var2, int var3, int var4) {
      ResourceLocation var5 = SkinCache.getPlayerSkin(var1.zYgb(), null);
      this.mc.getTextureManager().bindTexture(var5);
      float var6 = Math.min(4.0F, var4 / 2.0F);
      RoundedRect.drawRoundedTextureRegion(var2, var3, var4, var4, var6, 1.0F, 0.125F, 0.125F, 0.25F, 0.25F);
      RoundedRect.drawRoundedTextureRegion(var2, var3, var4, var4, var6, 1.0F, 0.625F, 0.125F, 0.75F, 0.25F);
   }

   private void ndBn(String var1, int var2, int var3) {
      String var4 = "Active: ";
      this.getHeaderFont().drawString(var4, var2, var3, -13041800, false);
      this.getHeaderFont().drawString(var1, var2 + this.getHeaderFont().getStringWidth(var4), var3, -1, false);
   }

   private void drawBottomBar(int var1, int var2, int var3, int var4) {
      int var5 = this.getPanelBottom() - 36;
      Session var6 = SessionAccessor.getSession();
      String var7 = var6 == null ? "none" : var6.getUsername();
      short var8 = 136;
      int var9 = var3 + var4 - var8;
      byte var10 = 72;
      int var11 = var9 - var10 - 8;
      boolean var12 = this.CURbqW(var1, var2, var9, var5, var8, 28);
      this.fillRoundedRect(var9, var5, var8, 28.0F, 7.0F, var12 ? -16294258 : -16364685);
      String var13 = "Add New Account";
      int var14 = this.getHeaderFont().getStringWidth(var13);
      int var15 = 20 + var14;
      int var16 = var9 + (var8 - var15) / 2;
      AltManagerIcons.drawPlusIcon(var16, var5 + 8, 12, -1);
      this.getHeaderFont().drawString(var13, var16 + 20, var5 + (28 - this.getHeaderFont().getFontHeight()) / 2.0F + 1.0F, -1, false);
      byte var17 = 56;
      if (this.loginInProgress) {
         this.drawActionButton("Cancel", var11 - var17 - 8, var5 + 4, var17, 20, this.CURbqW(var1, var2, var11 - var17 - 8, var5 + 4, var17, 20), true);
      }

      this.drawActionButton("Proxy", var11, var5, var10, 28, this.CURbqW(var1, var2, var11, var5, var10, 28), false);
      if (this.isErrorMessage(this.hintText)) {
         this.drawString(this.fontRendererObj, this.lgWg(this.hintText, var11 - var3 - 12, this.fontRendererObj), var3, var5 - 13, -34953);
      }

      this.ndBn(var7, var3, var5 + (28 - this.getHeaderFont().getFontHeight()) / 2 + 1);
   }

   private void drawDialog(int var1, int var2) {
      drawRect(0, 0, this.width, this.height, -1442840576);
      int var3 = Math.min(360, this.width - 40);
      int var4 = this.lvyn == ProxyAltsScreen$8.ADD_ACCOUNT ? (this.ZepH == ProxyAltsScreen$6.TOKEN ? 190 : 128) : (this.lvyn == ProxyAltsScreen$8.EDIT_ACCOUNT ? 168 : 118);
      int var5 = (this.width - var3) / 2;
      int var6 = (this.height - var4) / 2;
      this.fillRoundedRect(var5, var6, var3, var4, 9.0F, -15066598);
      this.drawPanelOutline(var5, var6, var3, var4, 9.0F);
      if (this.lvyn == ProxyAltsScreen$8.ADD_ACCOUNT) {
         this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.BOLD.toString() + "Add Account", this.width / 2, var6 + 14, -1);
         this.drawIconButton(ProxyAltsScreen$5.CLOSE, var5 + var3 - 30, var6 + 8, this.CURbqW(var1, var2, var5 + var3 - 30, var6 + 8, 20, 20), false);
         byte var15 = 98;
         byte var17 = 58;
         byte var19 = 10;
         int var20 = var5 + (var3 - var15 * 3 - var19 * 2) / 2;
         int var21 = var6 + 42;
         this.drawLoginMethodCard(var1, var2, var20, var21, var15, var17, "Token", ProxyAltsScreen$6.TOKEN);
         this.drawLoginMethodCard(var1, var2, var20 + var15 + var19, var21, var15, var17, "Cookie", ProxyAltsScreen$6.COOKIE);
         this.drawLoginMethodCard(var1, var2, var20 + (var15 + var19) * 2, var21, var15, var17, "Microsoft", ProxyAltsScreen$6.MICROSOFT);
         if (this.ZepH == ProxyAltsScreen$6.TOKEN) {
            this.drawString(this.fontRendererObj, "Token", var5 + 14, var6 + 112, -6250336);
            this.tokenField.xPosition = var5 + 14;
            this.tokenField.yPosition = var6 + 126;
            jade.build.TextFieldAccess.setWidth(this.tokenField, var3 - 28);
            this.drawTextField(this.tokenField, "Paste Minecraft or Microsoft Refresh Token", 0);
            int var22 = var3 - 28;
            int var13 = var6 + var4 - 32;
            this.drawActionButton("Save", var5 + 14, var13, var22, 20, this.CURbqW(var1, var2, var5 + 14, var13, var22, 20), false);
         }
      } else if (this.lvyn == ProxyAltsScreen$8.EDIT_ACCOUNT) {
         this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.BOLD.toString() + "Account Options", this.width / 2, var6 + 12, -1);
         this.drawString(this.fontRendererObj, "New Name", var5 + 14, var6 + 34, -6250336);
         this.dialogField.xPosition = var5 + 14;
         this.dialogField.yPosition = var6 + 48;
         jade.build.TextFieldAccess.setWidth(this.dialogField, var3 - 28);
         this.drawTextField(this.dialogField, "New Profile Name", 0);
         this.drawString(this.fontRendererObj, "Skin Username", var5 + 14, var6 + 74, -6250336);
         this.skinField.xPosition = var5 + 14;
         this.skinField.yPosition = var6 + 88;
         jade.build.TextFieldAccess.setWidth(this.skinField, var3 - 28);
         this.drawTextField(this.skinField, "Skin To Copy", 0);
         byte var14 = 6;
         int var16 = (var3 - 28 - var14) / 2;
         int var18 = var6 + 128;
         this.drawActionButton("Save", var5 + 14, var18, var16, 20, this.CURbqW(var1, var2, var5 + 14, var18, var16, 20), false);
         this.drawActionButton("Cancel", var5 + 14 + var16 + var14, var18, var16, 20, this.CURbqW(var1, var2, var5 + 14 + var16 + var14, var18, var16, 20), false);
      } else {
         String var7 = this.lvyn == ProxyAltsScreen$8.PROXY ? "Account Proxy" : (this.lvyn == ProxyAltsScreen$8.PROXY_OVERRIDE ? "Proxy Override" : "Folder");
         this.drawCenteredString(this.fontRendererObj, var7, this.width / 2, var6 + 12, -1);
         String var8 = this.lvyn != ProxyAltsScreen$8.PROXY && this.lvyn != ProxyAltsScreen$8.PROXY_OVERRIDE ? "Folder Title" : "ip:port:user:pass";
         this.drawString(this.fontRendererObj, var8, var5 + 14, var6 + 34, -6250336);
         this.dialogField.xPosition = var5 + 14;
         this.dialogField.yPosition = var6 + 48;
         jade.build.TextFieldAccess.setWidth(this.dialogField, var3 - 28);
         this.drawTextField(this.dialogField, var8, 0);
         byte var9 = 6;
         int var10 = (var3 - 28 - var9 * 2) / 3;
         int var11 = var6 + 82;
         if (this.lvyn == ProxyAltsScreen$8.RENAME_FOLDER && this.zcK) {
            int var12 = (var3 - 28 - var9) / 2;
            this.drawActionButton("Save", var5 + 14, var11, var12, 20, this.CURbqW(var1, var2, var5 + 14, var11, var12, 20), false);
            this.drawActionButton("Cancel", var5 + 14 + var12 + var9, var11, var12, 20, this.CURbqW(var1, var2, var5 + 14 + var12 + var9, var11, var12, 20), false);
         } else {
            this.drawActionButton("Save", var5 + 14, var11, var10, 20, this.CURbqW(var1, var2, var5 + 14, var11, var10, 20), false);
            this.drawActionButton(
               this.lvyn != ProxyAltsScreen$8.PROXY && this.lvyn != ProxyAltsScreen$8.PROXY_OVERRIDE ? "Delete" : "Clear",
               var5 + 14 + var10 + var9,
               var11,
               var10,
               20,
               this.CURbqW(var1, var2, var5 + 14 + var10 + var9, var11, var10, 20),
               true
            );
            this.drawActionButton(
               "Cancel", var5 + 14 + (var10 + var9) * 2, var11, var10, 20, this.CURbqW(var1, var2, var5 + 14 + (var10 + var9) * 2, var11, var10, 20), false
            );
         }
      }
   }

   private void drawLoginMethodCard(int var1, int var2, int var3, int var4, int var5, int var6, String var7, ProxyAltsScreen$6 var8) {
      boolean var9 = this.CURbqW(var1, var2, var3, var4, var5, var6);
      boolean var10 = this.ZepH == var8;
      this.fillRoundedRect(var3, var4, var5, var6, 8.0F, var10 ? -14272710 : (var9 ? -14408404 : -14671576));
      int var11 = var9 ? -1 : -2039584;
      byte var12 = 20;
      int var13 = var3 + (var5 - var12) / 2;
      int var14 = var4 + 9;
      if (var8 == ProxyAltsScreen$6.TOKEN) {
         AltManagerIcons.drawTokenIcon(var13, var14, var12, var11);
      } else if (var8 == ProxyAltsScreen$6.COOKIE) {
         AltManagerIcons.drawCookieIcon(var13, var14, var12, var11);
      } else {
         AltManagerIcons.ZEce(var13, var14, var12, var11);
      }

      this.drawCenteredString(this.fontRendererObj, var7, var3 + var5 / 2, var4 + 38, var10 ? -1 : -6250336);
   }

   private void drawTextField(GuiTextField var1, String var2, int var3) {
      this.fillRoundedRect(var1.xPosition, var1.yPosition - 1, jade.build.TextFieldAccess.getWidth(var1), 22.0F, 6.0F, -15658735);
      String var4 = var1.getText();
      int var5 = jade.build.TextFieldAccess.getWidth(var1) - 12 - var3;
      int var6 = -1;
      if (var4 == null || var4.length() == 0) {
         var4 = var1.isFocused() ? "" : var2;
         var6 = var1.isFocused() ? -1 : -9408400;
      }

      String var7 = this.lgWg(var4, Math.max(8, var5), this.fontRendererObj);
      int var8 = var1.yPosition + (20 - this.getBodyFont().getFontHeight()) / 2 + 1;
      this.getBodyFont().drawString(var7, var1.xPosition + 6, var8, var6, false);
      if (var1.isFocused() && System.currentTimeMillis() / 500L % 2L == 0L) {
         int var9 = var1.xPosition + 7 + this.getBodyFont().getStringWidth(var7);
         if (var9 < var1.xPosition + jade.build.TextFieldAccess.getWidth(var1) - var3 - 4) {
            this.getBodyFont().drawString("|", var9, var8, -6250336, false);
         }
      }
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      if (var3 == 1 && this.lvyn == ProxyAltsScreen$8.NONE) {
         ProxyAltsScreen$7 var9 = this.hIt3(var1, var2);
         if (var9 != null && ProxyAltsScreen$7.getConfigFolder(var9) != null) {
            this.HQSgE = ProxyAltsScreen$7.getConfigFolder(var9);
            this.resetScroll();
            this.hintText = "Opened " + ProxyAltsScreen$7.getConfigFolder(var9).getTitle();
         }
      } else if (var3 == 0) {
         if (this.lvyn != ProxyAltsScreen$8.NONE) {
            this.OEID(var1, var2);
         } else {
            if (this.HQSgE == null) {
               this.searchField.mouseClicked(var1, var2, var3);
               if (this.searchField.isFocused()) {
                  return;
               }
            } else {
               this.searchField.setFocused(false);
            }

            int var4 = this.getPanelLeft();
            int var5 = this.getPanelWidth();
            if (this.CURbqW(var1, var2, var4 + var5 - 24, this.getPanelTop() + 8, 20, 20)) {
               this.srDm();
            } else {
               int var6 = this.getPanelTop() + 38 + 10;
               if (this.HQSgE != null && this.CURbqW(var1, var2, var4 + var5 - 24, var6 - 6, 20, 20)) {
                  this.HQSgE = null;
                  this.resetScroll();
               } else {
                  int var7 = var4 + var5 - 104;
                  if (this.HQSgE == null && this.CURbqW(var1, var2, var7, var6 - 5, 104, 20)) {
                     this.createFolder();
                  } else {
                     ProxyAltsScreen$7 var8 = this.hIt3(var1, var2);
                     if (var8 != null) {
                        if (ProxyAltsScreen$7.getConfigFolder(var8) != null) {
                           this.BREifJ(ProxyAltsScreen$7.getConfigFolder(var8), var1, var2);
                           return;
                        }

                        if (ProxyAltsScreen$7.getAccount(var8) != null) {
                           if (this.handleCardButtonClick(ProxyAltsScreen$7.getAccount(var8), var1, var2, var4, var5)) {
                              return;
                           }

                           this.GYH = ProxyAltsScreen$7.getAccount(var8);
                           this.Tma = var1;
                           this.dragStartY = var2;
                           this.dragging = false;
                        }
                     }

                     this.handleBottomBarClick(var1, var2, var4, var5);
                  }
               }
            }
         }
      }
   }

   private void BREifJ(ConfigFolder var1, int var2, int var3) {
      int var4 = this.getPanelLeft();
      int var5 = this.MmxHba(var1);
      int var6 = this.getPanelWidth();
      int var7 = this.indexOfFolderEntry(var1);
      int var8 = var7 < 0 ? var4 : this.qkQeguI(var7, var4, var6);
      int var9 = var8 + 112 - 25;
      int var10 = var5 + 4;
      if (this.CURbqW(var2, var3, var9, var10, 20, 20)) {
         AccountStore.removeFolder(var1);
         if (this.HQSgE == var1) {
            this.HQSgE = null;
         }

         this.hintText = "Folder deleted";
      } else {
         long var11 = System.currentTimeMillis();
         if (this.lastClickedFolder == var1 && var11 - this.lastFolderClickMs < 350L) {
            this.openRenameFolderDialog(var1);
            this.lastClickedFolder = null;
            this.lastFolderClickMs = 0L;
         } else {
            this.lastClickedFolder = var1;
            this.lastFolderClickMs = var11;
            this.asC = var1;
            this.Tma = var2;
            this.dragStartY = var3;
            this.dragging = false;
         }
      }
   }

   private boolean handleCardButtonClick(Account var1, int var2, int var3, int var4, int var5) {
      int var6 = this.DUtlu(var1);
      if (var6 < 0) {
         return false;
      } else {
         int var7 = this.indexOfAccountEntry(var1);
         int var8 = var7 < 0 ? var4 : this.qkQeguI(var7, var4, var5);
         int var9 = var8 + 112 - 23;
         int var10 = var9 - 18;
         int var11 = var10 - 18;
         if (this.CURbqW(var2, var3, var11, var6 + 4, 20, 20)) {
            this.openEditAccountDialog(var1);
            return true;
         } else if (this.CURbqW(var2, var3, var10, var6 + 4, 20, 20)) {
            this.openProxyDialog(var1);
            return true;
         } else if (this.CURbqW(var2, var3, var9, var6 + 4, 20, 20)) {
            AccountStore.removeAccount(var1);
            this.hintText = "Removed " + var1.zYgb();
            return true;
         } else {
            return false;
         }
      }
   }

   private void handleBottomBarClick(int var1, int var2, int var3, int var4) {
      int var5 = this.getPanelBottom() - 36;
      short var6 = 136;
      int var7 = var3 + var4 - var6;
      byte var8 = 72;
      int var9 = var7 - var8 - 8;
      byte var10 = 56;
      if (this.HQSgE != null && this.CURbqW(var1, var2, this.getFolderCloseX(), this.getFolderCloseY(), 20, 20)) {
         this.HQSgE = null;
         this.resetScroll();
      } else if (this.CURbqW(var1, var2, var7, var5, var6, 28)) {
         this.openAddAccountDialog();
      } else if (this.CURbqW(var1, var2, var9, var5, var8, 28)) {
         this.openProxyOverrideDialog();
      } else if (this.loginInProgress && this.CURbqW(var1, var2, var9 - var10 - 8, var5 + 4, var10, 20)) {
         this.cancelLogin();
      }
   }

   private void OEID(int var1, int var2) {
      this.dialogField.mouseClicked(var1, var2, 0);
      this.skinField.mouseClicked(var1, var2, 0);
      this.tokenField.mouseClicked(var1, var2, 0);
      int var3 = Math.min(360, this.width - 40);
      int var4 = this.lvyn == ProxyAltsScreen$8.ADD_ACCOUNT ? (this.ZepH == ProxyAltsScreen$6.TOKEN ? 190 : 128) : (this.lvyn == ProxyAltsScreen$8.EDIT_ACCOUNT ? 168 : 118);
      int var5 = (this.width - var3) / 2;
      int var6 = (this.height - var4) / 2;
      if (this.lvyn == ProxyAltsScreen$8.ADD_ACCOUNT) {
         if (this.CURbqW(var1, var2, var5 + var3 - 30, var6 + 8, 20, 20)) {
            this.cancelDialog();
         } else {
            byte var15 = 98;
            byte var17 = 58;
            byte var19 = 10;
            int var20 = var5 + (var3 - var15 * 3 - var19 * 2) / 2;
            int var11 = var6 + 42;
            if (this.CURbqW(var1, var2, var20, var11, var15, var17)) {
               this.ZepH = ProxyAltsScreen$6.TOKEN;
               this.tokenField.setFocused(true);
            } else if (this.CURbqW(var1, var2, var20 + var15 + var19, var11, var15, var17)) {
               this.closeDialog();
               this.tjhZ();
            } else if (this.CURbqW(var1, var2, var20 + (var15 + var19) * 2, var11, var15, var17)) {
               this.closeDialog();
               this.startMicrosoftLogin();
            } else {
               if (this.ZepH == ProxyAltsScreen$6.TOKEN) {
                  int var12 = var3 - 28;
                  int var13 = var6 + var4 - 32;
                  if (this.CURbqW(var1, var2, var5 + 14, var13, var12, 20)) {
                     this.fve26();
                     this.closeDialog();
                  }
               }
            }
         }
      } else if (this.lvyn == ProxyAltsScreen$8.EDIT_ACCOUNT) {
         byte var14 = 6;
         int var16 = (var3 - 28 - var14) / 2;
         int var18 = var6 + 128;
         if (this.CURbqW(var1, var2, var5 + 14, var18, var16, 20)) {
            this.MZLPe();
         } else if (this.CURbqW(var1, var2, var5 + 14 + var16 + var14, var18, var16, 20)) {
            this.cancelDialog();
         }
      } else {
         byte var7 = 6;
         int var8 = (var3 - 28 - var7 * 2) / 3;
         int var9 = var6 + 82;
         if (this.lvyn == ProxyAltsScreen$8.RENAME_FOLDER && this.zcK) {
            int var10 = (var3 - 28 - var7) / 2;
            if (this.CURbqW(var1, var2, var5 + 14, var9, var10, 20)) {
               this.MZLPe();
            } else if (this.CURbqW(var1, var2, var5 + 14 + var10 + var7, var9, var10, 20)) {
               this.cancelDialog();
            }
         } else if (this.CURbqW(var1, var2, var5 + 14, var9, var8, 20)) {
            this.MZLPe();
         } else if (this.CURbqW(var1, var2, var5 + 14 + var8 + var7, var9, var8, 20)) {
            if (this.lvyn == ProxyAltsScreen$8.PROXY && this.JQTFl != null) {
               this.JQTFl.setProxy("");
               if (this.dcRifP(this.JQTFl)) {
                  this.applyAccountProxy(this.JQTFl);
               }

               AccountStore.saveToDisk();
               this.hintText = "Account proxy cleared";
            } else if (this.lvyn == ProxyAltsScreen$8.PROXY_OVERRIDE) {
               this.dialogField.setText("");
               this.proxyOverrideField.setText("");
               ProxyManager.KOvZb(new ProxySettings(), true);
               this.hintText = "";
            } else if (this.lvyn == ProxyAltsScreen$8.RENAME_FOLDER && this.editingFolder != null) {
               AccountStore.removeFolder(this.editingFolder);
               this.hintText = "Folder deleted";
            }

            this.closeDialog();
         } else if (this.CURbqW(var1, var2, var5 + 14 + (var8 + var7) * 2, var9, var8, 20)) {
            this.cancelDialog();
         }
      }
   }

   protected void mouseClickMove(int var1, int var2, int var3, long var4) {
      if (var3 == 0 && this.GYH != null && (Math.abs(var1 - this.Tma) > 3 || Math.abs(var2 - this.dragStartY) > 3)) {
         this.dragging = true;
      }

      if (var3 == 0 && this.asC != null && (Math.abs(var1 - this.Tma) > 3 || Math.abs(var2 - this.dragStartY) > 3)) {
         this.dragging = true;
      }
   }

   protected void mouseReleased(int var1, int var2, int var3) {
      if (var3 == 0 && this.GYH != null) {
         if (this.dragging) {
            this.LLbs(var1, var2);
         } else {
            this.loginWithAccount(this.GYH);
         }

         this.GYH = null;
         this.dragging = false;
      }

      if (var3 == 0 && this.asC != null) {
         if (this.dragging) {
            this.dropFolder(var1, var2);
         } else {
            this.HQSgE = this.asC;
            this.resetScroll();
            this.hintText = "Opened " + this.asC.getTitle();
         }

         this.asC = null;
         this.dragging = false;
      }
   }

   private void LLbs(int var1, int var2) {
      ProxyAltsScreen$7 var3 = this.hIt3(var1, var2);
      if (var3 == null) {
         if (this.GYH.getFolderId().length() > 0 && this.CURbqW(var1, var2, this.getFolderCloseX(), this.getFolderCloseY(), 20, 20)) {
            AccountStore.NEuj8(this.GYH, AccountStore.getAccounts().size(), "");
            this.hintText = "Moved out of folder";
         }
      } else if (ProxyAltsScreen$7.getConfigFolder(var3) != null) {
         int var5 = this.indexOfFolderInAccountStore(ProxyAltsScreen$7.getConfigFolder(var3).getId());
         AccountStore.NEuj8(this.GYH, var5, ProxyAltsScreen$7.getConfigFolder(var3).getId());
         this.hintText = "Moved to " + ProxyAltsScreen$7.getConfigFolder(var3).getTitle();
      } else {
         if (ProxyAltsScreen$7.getAccount(var3) != null && ProxyAltsScreen$7.getAccount(var3) != this.GYH) {
            int var4 = AccountStore.getAccounts().indexOf(ProxyAltsScreen$7.getAccount(var3));
            if (var2 > this.DUtlu(ProxyAltsScreen$7.getAccount(var3)) + 68) {
               var4++;
            }

            AccountStore.NEuj8(this.GYH, var4, ProxyAltsScreen$7.getAccount(var3).getFolderId());
            this.hintText = "Account reordered";
         }
      }
   }

   private void dropFolder(int var1, int var2) {
      ProxyAltsScreen$7 var3 = this.hIt3(var1, var2);
      if (var3 != null && ProxyAltsScreen$7.getConfigFolder(var3) != null && ProxyAltsScreen$7.getConfigFolder(var3) != this.asC) {
         int var4 = AccountStore.getFolders().indexOf(ProxyAltsScreen$7.getConfigFolder(var3));
         if (var2 > this.MmxHba(ProxyAltsScreen$7.getConfigFolder(var3)) + 68) {
            var4++;
         }

         AccountStore.kjgiw(this.asC, var4);
         this.hintText = "Folder reordered";
      }
   }

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      int var1 = Mouse.getDWheel();
      if (var1 != 0) {
         int var2 = this.EanExk(this.getPanelWidth());
         int var3 = this.getMaxScroll(var2, this.getListBottom() - this.getContentTop());
         int var4 = Math.max(1, Math.abs(var1) / 120);
         float var5 = this.VUcj.getTargetValue() + (var1 < 0 ? 36 : -36) * var4;
         this.VUcj.setTargetValue(this.clampValue(var5, 0.0F, var3));
      }
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if (this.lvyn != ProxyAltsScreen$8.NONE) {
         if (var2 == 1) {
            this.closeDialog();
         } else if (var2 == 28) {
            this.MZLPe();
         } else {
            if (this.dialogField.isFocused()) {
               this.dialogField.textboxKeyTyped(var1, var2);
            } else if (this.skinField.isFocused()) {
               this.skinField.textboxKeyTyped(var1, var2);
            } else if (this.tokenField.isFocused()) {
               this.tokenField.textboxKeyTyped(var1, var2);
            }
         }
      } else if (var2 == 1) {
         if (this.HQSgE != null) {
            this.HQSgE = null;
            this.resetScroll();
         } else {
            this.srDm();
         }
      } else if (this.searchField.isFocused()) {
         String var3 = this.searchField.getText();
         this.searchField.textboxKeyTyped(var1, var2);
         if (!var3.equals(this.searchField.getText())) {
            this.resetScroll();
         }
      } else if (this.proxyOverrideField.isFocused()) {
         this.proxyOverrideField.textboxKeyTyped(var1, var2);
      } else if (this.tokenField.isFocused()) {
         this.tokenField.textboxKeyTyped(var1, var2);
         if (var2 == 28) {
            this.fve26();
         }
      }
   }

   public void updateScreen() {
      if (this.proxyOverrideField != null) {
         this.proxyOverrideField.updateCursorCounter();
      }

      if (this.tokenField != null) {
         this.tokenField.updateCursorCounter();
      }

      if (this.dialogField != null) {
         this.dialogField.updateCursorCounter();
      }

      if (this.skinField != null) {
         this.skinField.updateCursorCounter();
      }

      if (this.searchField != null) {
         this.searchField.updateCursorCounter();
      }
   }

   private void ZVz9() {
      ProxySettings var1 = ProxySettings.parse(this.proxyOverrideField.getText());
      if (var1 == null) {
         this.hintText = "Proxy must be ip:port:user:pass";
         this.proxyOverrideField.setFocused(true);
      } else {
         ProxyManager.KOvZb(var1, true);
         this.hintText = var1.XAiikGo() ? "Proxy override saved" : "Proxy override cleared";
      }
   }

   private void createFolder() {
      ConfigFolder var1 = new ConfigFolder("folder-" + System.currentTimeMillis(), "New Folder");
      this.openFolderDialog(var1, true);
   }

   private void openProxyDialog(Account var1) {
      this.lvyn = ProxyAltsScreen$8.PROXY;
      this.JQTFl = var1;
      this.editingFolder = null;
      this.dialogField.setText(var1.getProxy());
      this.skinField.setText("");
      this.dialogField.setFocused(true);
   }

   private void openAddAccountDialog() {
      this.lvyn = ProxyAltsScreen$8.ADD_ACCOUNT;
      this.ZepH = null;
      this.JQTFl = null;
      this.editingFolder = null;
      this.dialogField.setText("");
      this.skinField.setText("");
      this.dialogField.setFocused(false);
      this.skinField.setFocused(false);
   }

   private void openProxyOverrideDialog() {
      this.lvyn = ProxyAltsScreen$8.PROXY_OVERRIDE;
      this.JQTFl = null;
      this.editingFolder = null;
      this.dialogField.setText(ProxyManager.rfB4().UNCjvL());
      this.skinField.setText("");
      this.dialogField.setFocused(true);
      this.skinField.setFocused(false);
   }

   private void openEditAccountDialog(Account var1) {
      this.lvyn = ProxyAltsScreen$8.EDIT_ACCOUNT;
      this.JQTFl = var1;
      this.editingFolder = null;
      this.dialogField.setText(var1.zYgb());
      this.skinField.setText("");
      this.dialogField.setFocused(true);
      this.skinField.setFocused(false);
   }

   private void openRenameFolderDialog(ConfigFolder var1) {
      this.openFolderDialog(var1, false);
   }

   private void openFolderDialog(ConfigFolder var1, boolean var2) {
      this.lvyn = ProxyAltsScreen$8.RENAME_FOLDER;
      this.editingFolder = var1;
      this.zcK = var2;
      this.JQTFl = null;
      this.dialogField.setText(var1.getTitle());
      this.skinField.setText("");
      this.dialogField.setFocused(true);
   }

   private void MZLPe() {
      if (this.lvyn == ProxyAltsScreen$8.PROXY && this.JQTFl != null) {
         String var4 = this.dialogField.getText().trim();
         ProxySettings var5 = ProxySettings.parse(var4);
         if (var5 == null) {
            this.hintText = "Account proxy must be ip:port:user:pass";
            this.dialogField.setFocused(true);
            return;
         }

         this.JQTFl.setProxy(var5.UNCjvL());
         if (this.dcRifP(this.JQTFl)) {
            this.applyAccountProxy(this.JQTFl);
         }

         AccountStore.saveToDisk();
         this.hintText = "Account proxy saved";
      } else if (this.lvyn == ProxyAltsScreen$8.PROXY_OVERRIDE) {
         ProxySettings var1 = ProxySettings.parse(this.dialogField.getText().trim());
         if (var1 == null) {
            this.hintText = "Error: Proxy must be ip:port:user:pass";
            this.dialogField.setFocused(true);
            return;
         }

         ProxyManager.KOvZb(var1, true);
         this.proxyOverrideField.setText(var1.UNCjvL());
         this.hintText = "";
      } else if (this.lvyn == ProxyAltsScreen$8.RENAME_FOLDER && this.editingFolder != null) {
         this.editingFolder.setTitle(this.dialogField.getText());
         if (this.zcK) {
            AccountStore.addFolder(this.editingFolder);
         } else {
            AccountStore.saveToDisk();
         }

         this.hintText = "Folder renamed";
      } else if (this.lvyn == ProxyAltsScreen$8.EDIT_ACCOUNT && this.JQTFl != null) {
         String var3 = this.dialogField.getText().trim();
         String var2 = this.skinField.getText().trim();
         if (var3.length() == 0 && var2.length() == 0) {
            this.hintText = "Enter a name or skin username";
            return;
         }

         this.hintText = "Profile update started";
         this.oYrl(this.JQTFl, var3, var2);
      } else if (this.lvyn == ProxyAltsScreen$8.ADD_ACCOUNT) {
         if (this.tokenField.getText().trim().length() == 0) {
            this.hintText = "Choose Microsoft, Cookie, or paste a token";
            return;
         }

         this.fve26();
      }

      this.closeDialog();
   }

   private void cancelDialog() {
      if (this.lvyn == ProxyAltsScreen$8.RENAME_FOLDER && this.zcK) {
         this.hintText = "Folder creation cancelled";
      }

      this.closeDialog();
   }

   private void closeDialog() {
      this.lvyn = ProxyAltsScreen$8.NONE;
      this.JQTFl = null;
      this.editingFolder = null;
      this.ZepH = null;
      this.zcK = false;
      this.dialogField.setFocused(false);
      this.skinField.setFocused(false);
      this.tokenField.setFocused(false);
   }

   private void oYrl(final Account var1, final String var2, final String var3) {
      if (this.loginInProgress) {
         this.hintText = "Initialization is already running";
      } else {
         SkinCache.RJcjhaH(var1.zYgb());
         this.loginInProgress = true;
         this.pendingAccount = var1;
         this.statusMessage.set("Updating profile...");
         this.Jy1 = CompletableFuture.supplyAsync(new Supplier<Account>() {
            public Account get() {
               try {
                  return MicrosoftLogin.HJJh(var1, var2, var3, ProxyAltsScreen.JazgzF(ProxyAltsScreen.this));
               } catch (Exception var2x) {
                  throw new RuntimeException(var2x);
               }
            }
         }, Jade.getExecutor());
      }
   }

   private void fve26() {
      if (!this.loginInProgress) {
         String var1 = this.tokenField.getText().trim();
         if (var1.length() == 0) {
            this.hintText = "Paste a token first";
         } else {
            this.loginInProgress = true;
            this.pendingAccount = null;
            if (MicrosoftLogin.kNp3(var1)) {
               this.statusMessage.set("Authenticating refresh token...");
               this.Jy1 = MicrosoftLogin.startPastedRefreshTokenLogin(var1, Jade.getExecutor(), this.statusMessage);
            } else {
               this.statusMessage.set("Checking Minecraft token...");
               this.Jy1 = MicrosoftLogin.loginWithMicrosoftToken(var1, Jade.getExecutor(), this.statusMessage);
            }

            this.tokenField.setText("");
         }
      }
   }

   private void startMicrosoftLogin() {
      if (!this.loginInProgress) {
         this.loginInProgress = true;
         this.pendingAccount = null;
         this.statusMessage.set("Starting Microsoft login...");
         this.Jy1 = MicrosoftLogin.loginWithBrowser(Jade.getExecutor(), this.statusMessage);
      }
   }

   private void tjhZ() {
      if (!this.loginInProgress) {
         this.hintText = "Opening cookie file picker...";
         Thread var1 = new Thread(new Runnable() {
            @Override
            public void run() {
               FileDialog var1x = new FileDialog((Frame)null, "Select Cookie File", 0);
               var1x.setDirectory(new File(System.getProperty("user.home"), "Downloads").getAbsolutePath());
               var1x.setFile("*.txt");
               var1x.setModal(true);
               var1x.setVisible(true);
               String var2 = var1x.getFile();
               if (var2 == null) {
                  ProxyAltsScreen.EvrNba(ProxyAltsScreen.this, "Cookie file selection cancelled");
               } else {
                  final File var3 = new File(var1x.getDirectory(), var2);
                  ProxyAltsScreen.this.mc.addScheduledTask(new Runnable() {
                     @Override
                     public void run() {
                        ProxyAltsScreen.handleDroppedCookieFile(ProxyAltsScreen.this, var3);
                     }
                  });
               }
            }
         }, "Jade-Cookie-Picker");
         var1.setDaemon(true);
         var1.start();
      }
   }

   private void startCookieFileLogin(File var1) {
      if (this.loginInProgress) {
         this.hintText = "Already logging in";
      } else if (var1 != null && var1.exists() && var1.isFile()) {
         if (!var1.getName().toLowerCase().endsWith(".txt")) {
            this.hintText = "Drop a .txt cookie file";
         } else {
            this.loginInProgress = true;
            this.pendingAccount = null;
            this.statusMessage.set("Reading cookie file...");
            this.Jy1 = BrowserLogin.loginWithCookieFileAsync(var1, Jade.getExecutor(), this.statusMessage);
         }
      } else {
         this.hintText = "Cookie file not found";
      }
   }

   private void loginWithAccount(Account var1) {
      if (this.loginInProgress) {
         if (this.pendingAccount == null) {
            this.hintText = "Initialization is already running";
            return;
         }

         if (this.Jy1 != null) {
            this.Jy1.cancel(true);
         }

         this.Jy1 = null;
         this.pendingAccount = null;
         this.loginInProgress = false;
      }

      var1.SxxXv("");
      this.applyAccountProxy(var1);
      if (var1.FZa4() == AccountType.MICROSOFT) {
         String var2 = var1.getRefreshToken();
         if (var1.getLoginMethod() == LoginMethod.MICROSOFT_V2 && var2 != null && var2.length() > 0) {
            this.loginInProgress = true;
            this.pendingAccount = var1;
            this.statusMessage.set("Refreshing Microsoft v2 session...");
            this.Jy1 = MicrosoftLogin.sawbe(var2, Jade.getExecutor(), this.statusMessage);
            return;
         }

         if (var1.getLoginMethod() == LoginMethod.MSA_ARTIFACT && var2 != null && var2.length() > 0) {
            this.loginInProgress = true;
            this.pendingAccount = var1;
            this.statusMessage.set("Authenticating MSA token...");
            this.Jy1 = MicrosoftLogin.startMsaArtifactLogin(var2, Jade.getExecutor(), this.statusMessage);
            return;
         }

         if (var1.getLoginMethod() == LoginMethod.MICROSOFT_REFRESH && var2 != null && var2.length() > 0) {
            this.loginInProgress = true;
            this.pendingAccount = var1;
            this.statusMessage.set("Authenticating refresh token...");
            this.Jy1 = MicrosoftLogin.loginWithSavedRefreshToken(var2, Jade.getExecutor(), this.statusMessage);
            return;
         }

         if (var2 != null && var2.length() > 0) {
            this.loginInProgress = true;
            this.pendingAccount = var1;
            this.statusMessage.set("Refreshing session...");
            this.Jy1 = MicrosoftLogin.refreshLiveSession(var2, Jade.getExecutor(), this.statusMessage);
            return;
         }

         String var3 = var1.qRa8673();
         if (var3 == null || var3.length() == 0) {
            this.hintText = "No saved access token - add account again";
            var1.SxxXv(this.hintText);
            return;
         }

         this.loginInProgress = true;
         this.pendingAccount = var1;
         this.statusMessage.set("Validating saved token...");
         this.Jy1 = MicrosoftLogin.startSavedTokenValidation(var3, Jade.getExecutor(), this.statusMessage);
      } else {
         this.hintText = "Cannot re-login legacy account";
         var1.SxxXv(this.hintText);
      }
   }

   private void applyAccountProxy(Account var1) {
      ProxySettings var2 = ProxySettings.parse(var1.getProxy());
      ProxyManager.setFallbackSettings(var2 == null ? new ProxySettings() : var2);
   }

   private void updateLoginState() {
      if (this.Jy1 != null) {
         if (!this.Jy1.isDone()) {
            this.hintText = this.statusMessage.get();
         } else {
            try {
               Account var1 = this.Jy1.get();
               if (var1 != null) {
                  if (this.pendingAccount != null) {
                     this.pendingAccount.setUsername(var1.zYgb());
                     this.pendingAccount.setUuid(var1.getUuid());
                     this.pendingAccount.setAccessToken(var1.qRa8673());
                     if (var1.getRefreshToken() != null) {
                        this.pendingAccount.setRefreshToken(var1.getRefreshToken());
                     }

                     this.pendingAccount.setLoginMethod(var1.getLoginMethod());
                     this.pendingAccount.SxxXv("");
                     this.applyAccountProxy(this.pendingAccount);
                     SkinCache.RJcjhaH(var1.zYgb());
                     SessionAccessor.setSession(new Session(var1.zYgb(), var1.getUuid(), var1.qRa8673(), "mojang"));
                     AccountStore.saveToDisk();
                     this.hintText = "Logged in as " + var1.zYgb() + this.getLoginMethodSuffix(var1);
                  } else {
                     var1.SxxXv("");
                     AccountStore.wqNcm(var1);
                     this.applyAccountProxy(var1);
                     this.hintText = "Added: " + var1.zYgb() + this.getLoginMethodSuffix(var1);
                  }
               } else {
                  this.hintText = this.statusMessage.get();
                  if (this.pendingAccount != null) {
                     this.pendingAccount.SxxXv(this.hintText);
                  }
               }
            } catch (InterruptedException var3) {
               this.hintText = "Interrupted";
               if (this.pendingAccount != null) {
                  this.pendingAccount.SxxXv(this.hintText);
               }
            } catch (CancellationException var4) {
               this.hintText = "Cancelled";
               if (this.pendingAccount != null) {
                  this.pendingAccount.SxxXv(this.hintText);
               }
            } catch (ExecutionException var5) {
               Throwable var2 = var5.getCause();
               if (var2 instanceof RuntimeException && var2.getCause() != null) {
                  var2 = var2.getCause();
               }

               this.hintText = "Error: " + this.describeThrowable((Throwable)(var2 == null ? var5 : var2));
               if (this.pendingAccount != null) {
                  this.pendingAccount.SxxXv(this.hintText);
               }
            }

            this.Jy1 = null;
            this.pendingAccount = null;
            this.loginInProgress = false;
         }
      }
   }

   private String getLoginMethodSuffix(Account var1) {
      if (var1 != null && (var1.getLoginMethod() == LoginMethod.MSA_ARTIFACT || var1.getLoginMethod() == LoginMethod.MICROSOFT_V2 || var1.getLoginMethod() == LoginMethod.MICROSOFT_REFRESH)) {
         String var2 = this.statusMessage.get();
         if (var2 != null && var2.startsWith("MSA token worked: ")) {
            return " (" + var2 + ")";
         } else if (var2 != null && var2.startsWith("Microsoft refresh worked: ")) {
            return " (" + var2 + ")";
         } else if (var1.getLoginMethod() == LoginMethod.MICROSOFT_V2) {
            return " (Microsoft v2)";
         } else {
            return var1.getLoginMethod() == LoginMethod.MICROSOFT_REFRESH ? " (Refresh token)" : " (MSA token)";
         }
      } else {
         return "";
      }
   }

   private String describeThrowable(Throwable var1) {
      if (var1 == null) {
         return "unknown error";
      } else {
         String var2 = var1.getMessage();
         if (var2 == null || var2.trim().length() == 0) {
            var2 = var1.getClass().getSimpleName();
         }

         return var2 != null && var2.trim().length() != 0 ? var2.trim() : "unknown error";
      }
   }

   private void cancelLogin() {
      if (this.Jy1 != null) {
         this.Jy1.cancel(true);
         this.Jy1 = null;
      }

      MicrosoftLogin.shutdownCallbackServer();
      this.pendingAccount = null;
      this.loginInProgress = false;
      this.hintText = "Cancelled";
   }

   private void srDm() {
      this.cancelLogin();
      this.mc.displayGuiScreen(this.parentScreen);
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
      this.hayP45();
   }

   private void DiAf() {
      this.visibleEntries.clear();
      if (this.isSearching()) {
         String var6 = this.searchField.getText().trim().toLowerCase(Locale.ROOT);

         for (Account var3 : AccountStore.getAccounts()) {
            if (var3.zYgb().toLowerCase(Locale.ROOT).contains(var6)) {
               this.visibleEntries.add(ProxyAltsScreen$7.ofAccount(var3));
            }
         }
      } else if (this.HQSgE != null) {
         for (Account var8 : AccountStore.getAccounts()) {
            if (this.HQSgE.getId().equals(var8.getFolderId())) {
               this.visibleEntries.add(ProxyAltsScreen$7.ofAccount(var8));
            }
         }
      } else {
         for (ConfigFolder var2 : AccountStore.getFolders()) {
            this.visibleEntries.add(ProxyAltsScreen$7.ofFolder(var2));
         }

         for (Account var7 : AccountStore.getAccounts()) {
            if (var7.getFolderId().length() == 0 || AccountStore.eOnjkC(var7.getFolderId()) == null) {
               this.visibleEntries.add(ProxyAltsScreen$7.ofAccount(var7));
            }
         }
      }
   }

   private boolean isSearching() {
      return this.searchField != null && this.searchField.getText().trim().length() > 0;
   }

   private void resetScroll() {
      this.VUcj.snapTo(0.0F);
   }

   private int getMaxScroll(int var1, int var2) {
      int var3 = (this.visibleEntries.size() + var1 - 1) / var1;
      short var4 = 148;
      int var5 = var3 <= 0 ? 0 : var3 * var4 - 12;
      int var6 = Math.max(0, var2 - 10);
      return Math.max(0, var5 - var6);
   }

   private float WQsJb7(int var1) {
      float var2 = this.clampValue(this.VUcj.getTargetValue(), 0.0F, var1);
      if (var2 != this.VUcj.getTargetValue()) {
         this.VUcj.setTargetValue(var2);
      }

      return this.clampValue(this.VUcj.ORMWO(), 0.0F, var1);
   }

   private ProxyAltsScreen$7 hIt3(int var1, int var2) {
      int var3 = this.getPanelLeft();
      int var4 = this.getPanelWidth();
      if (!this.CURbqW(var1, var2, var3, this.getContentTop(), var4, this.getListBottom() - this.getContentTop())) {
         return null;
      } else {
         int var5 = this.EanExk(var4);
         int var6 = this.getGridLeft(var3, var4, var5);
         if (var1 < var6) {
            return null;
         } else {
            int var7 = (var1 - var6) / 124;
            int var8 = var6 + var7 * 124;
            if (var7 >= 0 && var7 < var5 && var1 < var8 + 112) {
               int var9 = this.getContentTop() + 10;
               if (var2 < var9) {
                  return null;
               } else {
                  float var10 = this.WQsJb7(this.getMaxScroll(var5, this.getListBottom() - this.getContentTop()));
                  int var11 = (int)((var2 - var9 + var10) / 148.0F);
                  int var12 = Math.round(var9 + var11 * 148 - var10);
                  if (var2 >= var12 + 136) {
                     return null;
                  } else {
                     int var13 = var11 * var5 + var7;
                     return var13 >= 0 && var13 < this.visibleEntries.size() ? this.visibleEntries.get(var13) : null;
                  }
               }
            } else {
               return null;
            }
         }
      }
   }

   private int DUtlu(Account var1) {
      for (int var2 = 0; var2 < this.visibleEntries.size(); var2++) {
         if (ProxyAltsScreen$7.getAccount(this.visibleEntries.get(var2)) == var1) {
            return this.getCardY(var2);
         }
      }

      return -1;
   }

   private int MmxHba(ConfigFolder var1) {
      for (int var2 = 0; var2 < this.visibleEntries.size(); var2++) {
         if (ProxyAltsScreen$7.getConfigFolder(this.visibleEntries.get(var2)) == var1) {
            return this.getCardY(var2);
         }
      }

      return -1;
   }

   private int BOExh0(int var1) {
      return this.getCardY(var1);
   }

   private int indexOfFolderInAccountStore(String var1) {
      List var2 = AccountStore.getAccounts();

      for (int var3 = 0; var3 < var2.size(); var3++) {
         if (var1.equals(((Account)var2.get(var3)).getFolderId())) {
            return var3;
         }
      }

      return var2.size();
   }

   private int countAccountsInFolder(String var1) {
      int var2 = 0;

      for (Account var4 : AccountStore.getAccounts()) {
         if (var1.equals(var4.getFolderId())) {
            var2++;
         }
      }

      return var2;
   }

   private boolean dcRifP(Account var1) {
      Session var2 = SessionAccessor.getSession();
      return var2 != null && var2.getUsername().equalsIgnoreCase(var1.zYgb());
   }

   private String getBanText(Account var1) {
      long var2 = var1.Kk50();
      if (var2 < 0L) {
         return "Banned";
      } else {
         long var4 = var2 - System.currentTimeMillis();
         return var4 <= 0L ? "" : "Ban: " + this.formatDuration(var4);
      }
   }

   private int getBanTextColor(Account var1) {
      return var1.Kk50() < 0L ? -34953 : -19641;
   }

   private String formatDuration(long var1) {
      long var3 = Math.max(1L, (var1 + 999L) / 1000L);
      long var5 = var3 / 86400L;
      var3 %= 86400L;
      long var7 = var3 / 3600L;
      var3 %= 3600L;
      long var9 = var3 / 60L;
      var3 %= 60L;
      StringBuilder var11 = new StringBuilder();
      this.appendTimeUnit(var11, var5, "d");
      this.appendTimeUnit(var11, var7, "h");
      this.appendTimeUnit(var11, var9, "m");
      if (var11.length() == 0 || var5 == 0L) {
         this.appendTimeUnit(var11, var3, "s");
      }

      return var11.toString();
   }

   private void appendTimeUnit(StringBuilder var1, long var2, String var4) {
      if (var2 > 0L) {
         if (var1.length() > 0) {
            var1.append(' ');
         }

         var1.append(var2).append(var4);
      }
   }

   private int getPanelWidth() {
      return Math.min(500, Math.max(300, this.width - 140));
   }

   private int getPanelLeft() {
      return (this.width - this.getPanelWidth()) / 2;
   }

   private int getPanelHeight() {
      return Math.min(480, Math.max(330, this.height - 120));
   }

   private int getPanelTop() {
      return Math.max(18, (this.height - this.getPanelHeight()) / 2);
   }

   private int getPanelBottom() {
      return this.getPanelTop() + this.getPanelHeight();
   }

   private int getFolderCloseX() {
      return this.getPanelLeft() + this.getPanelWidth() - 24;
   }

   private int getFolderCloseY() {
      return this.getPanelTop() + 38 + 4;
   }

   private int getContentTop() {
      return this.getPanelTop() + 38 + 34;
   }

   private int getListBottom() {
      return Math.max(this.getContentTop() + 136 + 10, this.getPanelBottom() - 50);
   }

   private int EanExk(int var1) {
      return Math.max(1, Math.min(4, (var1 + 12) / 124));
   }

   private int getGridLeft(int var1, int var2, int var3) {
      int var4 = var3 * 112 + (var3 - 1) * 12;
      return var1 + Math.max(0, (var2 - var4) / 2);
   }

   private int qkQeguI(int var1, int var2, int var3) {
      int var4 = this.EanExk(var3);
      int var5 = var1 % var4;
      return this.getGridLeft(var2, var3, var4) + var5 * 124;
   }

   private int getCardY(int var1) {
      return this.nnnOb(var1, this.WQsJb7(this.getMaxScroll(this.EanExk(this.getPanelWidth()), this.getListBottom() - this.getContentTop())));
   }

   private int nnnOb(int var1, float var2) {
      int var3 = this.EanExk(this.getPanelWidth());
      int var4 = var1 / var3;
      return Math.round(this.getContentTop() + 10 + var4 * 148 - var2);
   }

   private int indexOfAccountEntry(Account var1) {
      for (int var2 = 0; var2 < this.visibleEntries.size(); var2++) {
         if (ProxyAltsScreen$7.getAccount(this.visibleEntries.get(var2)) == var1) {
            return var2;
         }
      }

      return -1;
   }

   private int indexOfFolderEntry(ConfigFolder var1) {
      for (int var2 = 0; var2 < this.visibleEntries.size(); var2++) {
         if (ProxyAltsScreen$7.getConfigFolder(this.visibleEntries.get(var2)) == var1) {
            return var2;
         }
      }

      return -1;
   }

   private void drawActionButton(String var1, int var2, int var3, int var4, int var5, boolean var6, boolean var7) {
      int var8;
      if (var7) {
         var8 = var6 ? -8708064 : -10872808;
      } else {
         var8 = var6 ? -14408404 : -14803162;
      }

      this.fillRoundedRect(var2, var3, var4, var5, 7.0F, var8);
      IFont var9 = this.getHeaderFont();
      var9.drawString(var1, var2 + (var4 - var9.getStringWidth(var1)) / 2.0F, var3 + (var5 - var9.getFontHeight()) / 2.0F + 1.0F, -1, false);
   }

   private void drawIconButton(ProxyAltsScreen$5 var1, int var2, int var3, boolean var4, boolean var5) {
      int var6 = var1 == ProxyAltsScreen$5.DELETE ? (var4 ? -49088 : -3137504) : (var5 ? (var4 ? -11141291 : -13252299) : (var4 ? -1 : -1644826));
      int var7 = var1 != ProxyAltsScreen$5.APPLY && var1 != ProxyAltsScreen$5.CLOSE ? 15 : 16;
      int var8 = var2 + (20 - var7) / 2;
      int var9 = var3 + (20 - var7) / 2;
      if (var1 != ProxyAltsScreen$5.APPLY && var1 != ProxyAltsScreen$5.CLOSE) {
         this.fillRoundedRect(var8, var9, var7, var7, var7 / 2.0F, var4 ? -14013645 : -14671576);
      } else if (var4) {
         this.fillRoundedRect(var8, var9, var7, var7, var7 / 2.0F, -14013645);
      }

      int var10 = var1 != ProxyAltsScreen$5.APPLY && var1 != ProxyAltsScreen$5.CLOSE ? 10 : 11;
      int var11 = var2 + (20 - var10) / 2;
      int var12 = var3 + (20 - var10) / 2;
      if (var1 == ProxyAltsScreen$5.PROXY) {
         AltManagerIcons.drawProxyIcon(var11, var12, var10, var6);
      } else if (var1 == ProxyAltsScreen$5.DELETE) {
         AltManagerIcons.drawDeleteIcon(var11, var12, var10, var6);
      } else if (var1 == ProxyAltsScreen$5.APPLY) {
         AltManagerIcons.drawCheckIcon(var11, var12, var10, var4 ? -11141291 : -1644826);
      } else if (var1 == ProxyAltsScreen$5.CLOSE) {
         AltManagerIcons.drawXMarkIcon(var11, var12, var10, var4 ? -39322 : -1644826);
      } else {
         AltManagerIcons.drawMoreIcon(var11, var12, var10, var6);
      }
   }

   public void drawString(FontRenderer var1, String var2, int var3, int var4, int var5) {
      if (var2 != null) {
         boolean var6 = var2.indexOf(EnumChatFormatting.BOLD.toString()) >= 0;
         String var7 = EnumChatFormatting.getTextWithoutFormattingCodes(var2);
         IFont var8 = var6 ? this.getHeaderFont() : this.getBodyFont();
         var8.drawString(var7 == null ? var2 : var7, var3, var4, var5, false);
      }
   }

   public void drawCenteredString(FontRenderer var1, String var2, int var3, int var4, int var5) {
      if (var2 != null) {
         boolean var6 = var2.indexOf(EnumChatFormatting.BOLD.toString()) >= 0;
         String var7 = EnumChatFormatting.getTextWithoutFormattingCodes(var2);
         if (var7 == null) {
            var7 = var2;
         }

         IFont var8 = var6 ? this.getHeaderFont() : this.getBodyFont();
         var8.drawString(var7, var3 - var8.getStringWidth(var7) / 2.0F, var4, var5, false);
      }
   }

   private IFont getHeaderFont() {
      return FontManager.getClickGuiHeaderRenderer("Bold");
   }

   private IFont getBodyFont() {
      return FontManager.getClickGuiSettingRenderer("Modern");
   }

   private int QUPa() {
      return Gui.accent == null ? -15030151 : Gui.accent.getArgb() | 0xFF000000;
   }

   private boolean isErrorMessage(String var1) {
      if (var1 != null && var1.length() != 0) {
         String var2 = var1.toLowerCase();
         return var2.startsWith("error")
            || var2.indexOf("failed") >= 0
            || var2.indexOf("invalid") >= 0
            || var2.indexOf("must be") >= 0
            || var2.indexOf("not found") >= 0
            || var2.indexOf("no saved") >= 0
            || var2.indexOf("cannot") >= 0;
      } else {
         return false;
      }
   }

   private void fillRoundedRect(float var1, float var2, float var3, float var4, float var5, int var6) {
      RoundedRect.drawRoundedRect(var1, var2, var3, var4, var5, new Color(var6, true));
   }

   private void drawPanelOutline(float var1, float var2, float var3, float var4, float var5) {
      RoundedRect.drawRoundedOutline(var1, var2, var3, var4, var5, 0.22F, new Color(0, 0, 0, 0), new Color(1711276032, true));
      RoundedRect.drawRoundedOutline(var1 + 0.5F, var2 + 0.5F, var3 - 1.0F, var4 - 1.0F, Math.max(0.0F, var5 - 0.5F), 0.22F, new Color(0, 0, 0, 0), new Color(352321535, true));
   }

   private boolean CURbqW(int var1, int var2, int var3, int var4, int var5, int var6) {
      return var1 >= var3 && var1 < var3 + var5 && var2 >= var4 && var2 < var4 + var6;
   }

   private float clampValue(float var1, float var2, float var3) {
      return Math.max(var2, Math.min(var3, var1));
   }

   private String lgWg(String var1, int var2, FontRenderer var3) {
      if (var3.getStringWidth(var1) <= var2) {
         return var1;
      } else {
         String var4 = var1;

         while (var4.length() > 1 && var3.getStringWidth(var4 + "..") > var2) {
            var4 = var4.substring(0, var4.length() - 1);
         }

         return var4 + "..";
      }
   }

   private void installDropTarget() {
      try {
         Canvas var1 = Display.getParent();
         if (var1 == null || var1 == this.awtCanvas) {
            return;
         }

         this.awtCanvas = var1;
         this.FVWvM8 = var1.getDropTarget();
         new DropTarget(var1, 1, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent var1) {
               ProxyAltsScreen.JEAy(ProxyAltsScreen.this, var1);
            }
         }, true);
      } catch (Throwable var2) {
      }
   }

   private void hayP45() {
      try {
         if (this.awtCanvas != null) {
            this.awtCanvas.setDropTarget(this.FVWvM8);
         }
      } catch (Throwable var5) {
      } finally {
         this.awtCanvas = null;
         this.FVWvM8 = null;
      }
   }

   private void NRJSbyp(DropTargetDropEvent var1) {
      try {
         if (!var1.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            var1.rejectDrop();
            return;
         }

         var1.acceptDrop(1);
         List var2 = (List)var1.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
         File var3 = null;

         for (File var5 : (java.lang.Iterable<File>) (java.lang.Iterable<?>) (var2)) {
            if (var5 != null && var5.getName().toLowerCase().endsWith(".txt")) {
               var3 = var5;
               break;
            }
         }

         if (var3 == null) {
            this.hintText = "Drop a .txt cookie file";
            var1.dropComplete(false);
            return;
         }

         final File cookieFile = var3;
         this.mc.addScheduledTask(new Runnable() {
            @Override
            public void run() {
               ProxyAltsScreen.handleDroppedCookieFile(ProxyAltsScreen.this, cookieFile);
            }
         });
         var1.dropComplete(true);
      } catch (Exception var6) {
         this.hintText = "Drop failed: " + var6.getMessage();
         var1.dropComplete(false);
      }
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public static AtomicReference JazgzF(ProxyAltsScreen var0) {
      return var0.statusMessage;
   }

   public static String EvrNba(ProxyAltsScreen var0, String var1) {
      return var0.hintText = var1;
   }

   public static void handleDroppedCookieFile(ProxyAltsScreen var0, File var1) {
      var0.startCookieFileLogin(var1);
   }

   public static void JEAy(ProxyAltsScreen var0, DropTargetDropEvent var1) {
      var0.NRJSbyp(var1);
   }
}
