// Jade recovery: original class: jade.deps.eLz.XzkQGEq
package jade.client.common;

import jade.client.module.render.arraylist.ColorTheme;
import jade.inject.InjectionAgent;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

public final class ChatUtils {
   private static final Pattern FLOW_TAG_PATTERN = Pattern.compile("\\{flow:([^}]+)\\}(.*?)\\{/flow\\}");
   private static final Pattern FLOW_OPEN_PATTERN = Pattern.compile("\\{flow:([^}]+)\\}");
   private static final long FLOW_PERIOD_MILLIS = 1600L;
   private static final double GRADIENT_PHASE_OFFSET = 0.12;
   private static boolean DYgS6;
   private static boolean customDrawActive = false;
   private static int chatRenderDepth = 0;
   private static final int CACHE_MAX_SIZE = 100;
   private static final int xxn = 200;
   private static final Map<String, String> flowTextCache = new LinkedHashMap<String, String>(16, 0.75F, true) {
      @Override
      protected boolean removeEldestEntry(Entry<String, String> var1) {
         return this.size() > 100;
      }
   };
   private static final Map<String, String> QnyE = new LinkedHashMap<String, String>(16, 0.75F, true) {
      @Override
      protected boolean removeEldestEntry(Entry<String, String> var1) {
         return this.size() > 200;
      }
   };

   private ChatUtils() {
   }

   public static String applyFlowGradient(String var0, String... var1) {
      if (var0 != null && !var0.isEmpty() && var1 != null && var1.length >= 2) {
         StringBuilder var2 = new StringBuilder();
         var2.append("{flow:");

         for (int var3 = 0; var3 < var1.length; var3++) {
            if (var3 > 0) {
               var2.append(',');
            }

            var2.append(normalizeHexColor(var1[var3]));
         }

         var2.append('}').append(var0).append("{/flow}");
         return var2.toString();
      } else {
         return var0;
      }
   }

   public static boolean hasFlowTag(String var0) {
      return var0 != null && var0.contains("{flow:") && var0.contains("{/flow}");
   }

   public static List<ChatUtils$2> splitFlowSegments(String var0) {
      if (var0 != null && !var0.isEmpty()) {
         ArrayList var1 = new ArrayList();
         Matcher var2 = FLOW_TAG_PATTERN.matcher(var0);

         int var3;
         for (var3 = 0; var2.find(); var3 = var2.end()) {
            if (var2.start() > var3) {
               var1.add(ChatUtils$2.createPlain(var0.substring(var3, var2.start())));
            }

            List var4 = parseColorList(var2.group(1));
            String var5 = var2.group(2);
            if (var4.size() >= 2 && var5 != null && !var5.isEmpty()) {
               var1.add(ChatUtils$2.createGradient(var5, var4));
            } else {
               var1.add(ChatUtils$2.createPlain(var2.group()));
            }
         }

         if (var3 < var0.length()) {
            var1.add(ChatUtils$2.createPlain(var0.substring(var3)));
         }

         return var1;
      } else {
         return Collections.emptyList();
      }
   }

   public static String identityText(String var0) {
      return var0;
   }

   public static String passthroughText(String var0) {
      return var0;
   }

   public static String resolveFlowText(String var0) {
      if (!hasFlowTag(var0)) {
         return var0;
      } else {
         String var1 = kWr1(var0);
         String var2 = mfX4(var1);
         synchronized (flowTextCache) {
            flowTextCache.put(var2, var0);
         }

         if (var2.startsWith("Jade")) {
            InjectionAgent.recordBadlionGradient("register", "keyLength=" + var2.length() + " rawLength=" + var0.length());

            try {
               Minecraft var7 = Minecraft.getMinecraft();
               GuiNewChat var4 = var7.ingameGUI == null ? null : var7.ingameGUI.getChatGUI();
               InjectionAgent.recordBadlionGradient(
                  "live.classes",
                  "chat="
                     + (var4 == null ? "null" : var4.getClass().getName())
                     + " font="
                     + (var7.fontRendererObj == null ? "null" : var7.fontRendererObj.getClass().getName())
               );
            } catch (Throwable var5) {
               InjectionAgent.recordBadlionGradient("live.classes", "failure=" + var5.getClass().getName());
            }
         }

         InjectionAgent.setHookEnabled("onFontDraw", "(Ljava/lang/Object;Ljava/lang/Object;FFIZ)Ljava/lang/Object;", true);
         InjectionAgent.setHookEnabled("onChatDrawString", "(Ljava/lang/Object;Ljava/lang/Object;FFI)Ljava/lang/Object;", true);
         return var1;
      }
   }

   public static void registerComponentText(IChatComponent var0) {
      if (var0 != null) {
         if (hasFlowTag(var0.getFormattedText())) {
            resolveFlowText(var0.getFormattedText());
         }

         if (var0.getSiblings() != null) {
            for (IChatComponent var2 : var0.getSiblings()) {
               registerComponentText(var2);
            }
         }
      }
   }

   public static IChatComponent copyWithoutFlow(IChatComponent var0) {
      if (var0 == null) {
         return null;
      } else {
         ChatComponentText var1 = new ChatComponentText(kWr1(var0.getUnformattedTextForChat()));
         ChatStyle var2 = var0.getChatStyle();
         if (var2 != null) {
            var1.setChatStyle(var2.createShallowCopy());
         }

         if (var0.getSiblings() != null) {
            for (IChatComponent var4 : var0.getSiblings()) {
               IChatComponent var5 = copyWithoutFlow(var4);
               if (var5 != null) {
                  var1.appendSibling(var5);
               }
            }
         }

         return var1;
      }
   }

   public static int measureStrippedWidth(FontRenderer var0, String var1) {
      DYgS6 = true;

      int var2;
      try {
         var2 = var0.getStringWidth(kWr1(var1));
      } finally {
         DYgS6 = false;
      }

      return var2;
   }

   public static int UCYxg(FontRenderer var0, String var1, float var2, float var3, int var4, boolean var5) {
      if (var1 != null && !var1.isEmpty()) {
         if ((var4 & -67108864) == 0) {
            var4 |= -16777216;
         }

         List var6 = splitFlowSegments(var1);
         long var7 = System.currentTimeMillis();
         float var9 = var2;
         String var10 = null;

         for (ChatUtils$2 var12 : (java.lang.Iterable<ChatUtils$2>) (java.lang.Iterable<?>) (var6)) {
            if (!var12.CREFU()) {
               String var13 = var12.getText();
               if (var10 != null && !startsWithColorCode(var13)) {
                  var13 = var10 + var13;
               }

               var9 = var0.drawString(var13, var9, var3, var4, var5);
               var10 = HtpR(var13);
            } else {
               var9 += drawFlowSegment(var0, var12.getText(), var12.getGradientColors(), var9, var3, var4, var5, var7);
            }
         }

         return Math.round(var9);
      } else {
         return 0;
      }
   }

   private static boolean startsWithColorCode(String var0) {
      return var0.length() >= 2 && var0.charAt(0) == 167;
   }

   private static String HtpR(String var0) {
      String var1 = null;

      for (int var2 = 0; var2 + 1 < var0.length(); var2++) {
         if (var0.charAt(var2) == 167) {
            char var3 = Character.toLowerCase(var0.charAt(var2 + 1));
            if ("0123456789abcdef".indexOf(var3) >= 0) {
               var1 = "§" + var3;
            } else if (var3 == 'r') {
               var1 = null;
            }

            var2++;
         }
      }

      return var1;
   }

   public static boolean isMeasuringText() {
      return DYgS6;
   }

   public static boolean uDc8() {
      return customDrawActive;
   }

   public static void setCustomDrawActive(boolean var0) {
      customDrawActive = var0;
   }

   public static void enterChatRender() {
      chatRenderDepth++;
   }

   public static void exitChatRender() {
      if (chatRenderDepth > 0) {
         chatRenderDepth--;
      }
   }

   public static boolean isRenderingChat() {
      return chatRenderDepth > 0;
   }

   public static boolean hasObfuscatedCode(String var0) {
      if (var0 != null && !var0.isEmpty()) {
         for (int var1 = 0; var1 + 1 < var0.length(); var1++) {
            if (var0.charAt(var1) == 167) {
               char var2 = Character.toLowerCase(var0.charAt(++var1));
               if (var2 == 'k') {
                  return true;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static boolean hasCachedText() {
      synchronized (flowTextCache) {
         return !flowTextCache.isEmpty();
      }
   }

   public static String kWr1(String var0) {
      if (var0 != null && !var0.isEmpty()) {
         String var1 = FLOW_TAG_PATTERN.matcher(var0).replaceAll("$2");
         var1 = FLOW_OPEN_PATTERN.matcher(var1).replaceAll("");
         return var1.replace("{/flow}", "");
      } else {
         return var0;
      }
   }

   public static int getGradientColor(List<Integer> var0, double var1, long var3) {
      if (var0 != null && !var0.isEmpty()) {
         if (var0.size() == 1) {
            return (Integer)var0.get(0);
         } else {
            int var5 = var0.size() - 1;
            double var6 = (double)var3 / (1600L * var5) + var1 * 0.12 / ((Math.PI * 2) * var5);
            var6 -= Math.floor(var6);
            double var8 = var6 * var5 * 2.0;
            double var10 = var8 <= var5 ? var8 : var5 * 2.0 - var8;
            int var12 = Math.min(var5 - 1, (int)Math.floor(var10));
            int var13 = var12 + 1;
            float var14 = (float)(var10 - var12);
            return interpolateColor((Integer)var0.get(var12), (Integer)var0.get(var13), var14);
         }
      } else {
         return 16777215;
      }
   }

   private static double iskk(double var0, long var2) {
      return var2 / 1600.0 * (Math.PI * 2) + var0 * 0.12;
   }

   private static int interpolateColor(int var0, int var1, float var2) {
      Color var3 = new Color(var0 & 16777215);
      Color var4 = new Color(var1 & 16777215);
      return ColorTheme.convert(var4, var3, var2).getRGB() & 16777215;
   }

   public static int TZwn(int var0, int var1, boolean var2) {
      int var3 = var0 >>> 24 & 0xFF;
      if (var3 == 0) {
         var3 = 255;
      }

      int var4 = var1 >> 16 & 0xFF;
      int var5 = var1 >> 8 & 0xFF;
      int var6 = var1 & 0xFF;
      if (var2) {
         var4 = Math.max(0, Math.round(var4 * 0.25F));
         var5 = Math.max(0, Math.round(var5 * 0.25F));
         var6 = Math.max(0, Math.round(var6 * 0.25F));
      }

      return var3 << 24 | var4 << 16 | var5 << 8 | var6;
   }

   public static int applyAlphaToRgb(int var0, int var1) {
      int var2 = var0 >>> 24 & 0xFF;
      if (var2 == 0) {
         var2 = 255;
      }

      return var2 << 24 | var1 & 16777215;
   }

   private static List<Integer> parseColorList(String var0) {
      ArrayList var1 = new ArrayList();
      if (var0 != null && !var0.isEmpty()) {
         String[] var2 = var0.split(",");

         for (String var6 : var2) {
            Integer var7 = parseHexColor(var6);
            if (var7 != null) {
               var1.add(var7);
            }
         }

         return var1;
      } else {
         return var1;
      }
   }

   private static Integer parseHexColor(String var0) {
      if (var0 == null) {
         return null;
      } else {
         String var1 = normalizeHexColor(var0);
         if (var1.length() == 7 && var1.charAt(0) == '#') {
            try {
               return Integer.parseInt(var1.substring(1), 16);
            } catch (NumberFormatException var3) {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   private static String normalizeHexColor(String var0) {
      if (var0 == null) {
         return "#FFFFFF";
      } else {
         String var1 = var0.trim().toUpperCase(Locale.ROOT);
         if (!var1.startsWith("#")) {
            var1 = "#" + var1;
         }

         if (var1.length() == 4) {
            char var2 = var1.charAt(1);
            char var3 = var1.charAt(2);
            char var4 = var1.charAt(3);
            var1 = "#" + var2 + var2 + var3 + var3 + var4 + var4;
         }

         if (var1.length() != 7) {
            return "#FFFFFF";
         } else {
            try {
               Color.decode(var1);
               return var1;
            } catch (NumberFormatException var5) {
               return "#FFFFFF";
            }
         }
      }
   }

   private static int drawFlowSegment(FontRenderer var0, String var1, List<Integer> var2, float var3, float var4, int var5, boolean var6, long var7) {
      String var9 = "0123456789abcdef";
      short var10 = 167;
      int var11 = 0;
      Integer var12 = null;
      StringBuilder var13 = new StringBuilder();

      for (int var14 = 0; var14 < var1.length(); var14++) {
         char var15 = var1.charAt(var14);
         if (var15 == 167 && var14 + 1 < var1.length()) {
            char var20 = Character.toLowerCase(var1.charAt(++var14));
            int var21 = "0123456789abcdef".indexOf(var20);
            if (var21 >= 0) {
               var12 = var0.getColorCode(var20) & 16777215;
               var13.setLength(0);
            } else if (var20 == 'r') {
               var12 = null;
               var13.setLength(0);
            } else if (var20 >= 'k' && var20 <= 'o') {
               appendFormatCode(var13, var20);
            }
         } else if (var15 != '\n') {
            int var16 = var0.getCharWidth(var15);
            if (var16 >= 0) {
               if (hasFormatCode(var13, 'l') && var16 > 0) {
                  var16++;
               }

               float var17 = var11;
               float var18 = var16;
               int var19 = var12 != null ? applyAlphaToRgb(var5, var12) : applyAlphaToRgb(var5, getGradientColor(var2, var3 + var17 + var18 * 0.5F, var7));
               drawFormattedChar(var0, var15, var13, var3 + var17, var4, var19, var6);
               var11 += var16;
            }
         }
      }

      return var11;
   }

   private static void drawFormattedChar(FontRenderer var0, char var1, StringBuilder var2, float var3, float var4, int var5, boolean var6) {
      if (var2.length() == 0) {
         var0.drawString(String.valueOf(var1), var3, var4, var5, var6);
      } else {
         StringBuilder var7 = new StringBuilder(var2.length() + 1);
         var7.append((CharSequence)var2).append(var1);
         var0.drawString(var7.toString(), var3, var4, var5, var6);
      }
   }

   private static boolean hasFormatCode(StringBuilder var0, char var1) {
      for (byte var2 = 1; var2 < var0.length(); var2 += 2) {
         if (var0.charAt(var2) == var1) {
            return true;
         }
      }

      return false;
   }

   private static void appendFormatCode(StringBuilder var0, char var1) {
      if (!hasFormatCode(var0, var1)) {
         var0.append('§').append(var1);
      }
   }

   public static String getOriginalFormattedText(String var0) {
      if (var0 == null) {
         return null;
      } else {
         String var1 = mfX4(var0);
         synchronized (flowTextCache) {
            return flowTextCache.get(var1);
         }
      }
   }

   public static String getRegisteredFormatting(String var0) {
      if (var0 == null) {
         return null;
      } else {
         String var1 = mfX4(var0);
         if (var1.length() < 3) {
            return null;
         } else {
            synchronized (QnyE) {
               return QnyE.get(var1);
            }
         }
      }
   }

   public static String getCachedFormattedText(String var0) {
      if (var0 != null && !hasObfuscatedCode(var0)) {
         String var1 = mfX4(var0);
         if (var1.length() < 3) {
            return null;
         } else {
            String var2 = getOriginalFormattedText(var0);
            return var2 != null ? var2 : getRegisteredFormatting(var0);
         }
      } else {
         return null;
      }
   }

   public static String restoreFormattedText(String var0) {
      String var1 = translateAmpersandCodes(var0);
      String var2 = getCachedFormattedText(var1);
      if (var2 == null && var0 != null) {
         String var3 = ClientUtils.AOAtn(var1);
         synchronized (flowTextCache) {
            for (Entry var6 : flowTextCache.entrySet()) {
               String var7 = (String)var6.getKey();
               int var8 = var3.lastIndexOf(var7);
               if (var8 >= 0 && var8 + var7.length() == var3.length()) {
                  return SeGi6(var1, var8) + (String)var6.getValue();
               }
            }
         }

         int var11 = var3.lastIndexOf("Jade");
         if (var11 < 0) {
            return null;
         } else {
            String[] var12 = ClientUtils.getThemeGradientColors();
            return SeGi6(var1, var11) + applyFlowGradient("§lJade", var12) + skipVisibleCharacters(var1, var11 + 4);
         }
      } else {
         return var2;
      }
   }

   private static String SeGi6(String var0, int var1) {
      if (var1 <= 0) {
         return "";
      } else {
         StringBuilder var2 = new StringBuilder();
         int var3 = 0;

         for (int var4 = 0; var4 < var0.length() && var3 < var1; var4++) {
            char var5 = var0.charAt(var4);
            var2.append(var5);
            if (var5 == 167 && var4 + 1 < var0.length()) {
               var2.append(var0.charAt(++var4));
            } else if (var5 != '\n') {
               var3++;
            }
         }

         return var2.toString();
      }
   }

   private static String skipVisibleCharacters(String var0, int var1) {
      StringBuilder var2 = new StringBuilder();
      int var3 = 0;
      boolean var4 = false;

      for (int var5 = 0; var5 < var0.length(); var5++) {
         char var6 = var0.charAt(var5);
         if (var6 == 167 && var5 + 1 < var0.length()) {
            if (var4) {
               var2.append(var6).append(var0.charAt(++var5));
            } else {
               var5++;
            }
         } else if (!var4) {
            if (++var3 >= var1) {
               var4 = true;
            }
         } else {
            var2.append(var6);
         }
      }

      return var2.toString();
   }

   public static boolean registerFormatting(String var0, String var1) {
      if (var0 != null && var1 != null) {
         String var2 = mfX4(var0);
         if (var2.length() < 3) {
            return false;
         } else {
            String var3 = yvUn(var1, var2);
            if (var3 != null && !var3.isEmpty()) {
               synchronized (QnyE) {
                  QnyE.put(var2, var3);
                  return true;
               }
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   private static String mfX4(String var0) {
      return ClientUtils.AOAtn(translateAmpersandCodes(var0)).trim();
   }

   private static String translateAmpersandCodes(String var0) {
      if (var0 != null && var0.indexOf(38) >= 0) {
         StringBuilder var1 = new StringBuilder(var0.length());

         for (int var2 = 0; var2 < var0.length(); var2++) {
            char var3 = var0.charAt(var2);
            if (var3 == '&' && var2 + 1 < var0.length()) {
               char var4 = Character.toLowerCase(var0.charAt(var2 + 1));
               if ("0123456789abcdefklmnor".indexOf(var4) >= 0) {
                  var1.append('§').append(var4);
                  var2++;
                  continue;
               }
            }

            var1.append(var3);
         }

         return var1.toString();
      } else {
         return var0 == null ? "" : var0;
      }
   }

   private static String yvUn(String var0, String var1) {
      String var2 = ClientUtils.AOAtn(kWr1(var0));
      int var3 = var2.indexOf(var1);
      if (var3 < 0) {
         return null;
      } else {
         int var4 = var3 + var1.length();
         StringBuilder var5 = new StringBuilder();
         int var6 = 0;

         for (ChatUtils$2 var9 : splitFlowSegments(var0)) {
            String var10 = var9.getText();
            int var11 = getVisibleLength(var10);
            int var13 = var6 + var11;
            if (var13 > var3 && var6 < var4) {
               int var14 = Math.max(0, var3 - var6);
               int var15 = Math.min(var11, var4 - var6);
               String var16 = substringVisibleRange(var10, var14, var15);
               if (!var16.isEmpty()) {
                  if (var9.CREFU()) {
                     var5.append("{flow:").append(formatColorList(var9.getGradientColors())).append('}').append(var16).append("{/flow}");
                  } else {
                     var5.append(var16);
                  }
               }
            }

            var6 = var13;
            if (var13 >= var4) {
               break;
            }
         }

         return var5.toString();
      }
   }

   private static int getVisibleLength(String var0) {
      int var1 = 0;
      short var2 = 167;

      for (int var3 = 0; var3 < var0.length(); var3++) {
         char var4 = var0.charAt(var3);
         if (var4 == 167 && var3 + 1 < var0.length()) {
            var3++;
         } else if (var4 != '\n') {
            var1++;
         }
      }

      return var1;
   }

   private static String substringVisibleRange(String var0, int var1, int var2) {
      if (var1 >= var2) {
         return "";
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append(getFormattingPrefixAt(var0, var1));
         int var4 = 0;
         short var5 = 167;

         for (int var6 = 0; var6 < var0.length(); var6++) {
            char var7 = var0.charAt(var6);
            if (var7 == 167 && var6 + 1 < var0.length()) {
               char var8 = var0.charAt(++var6);
               if (var4 >= var1 && var4 < var2) {
                  var3.append('§').append(var8);
               }
            } else if (var7 != '\n') {
               if (var4 >= var1 && var4 < var2) {
                  var3.append(var7);
               }

               if (++var4 >= var2) {
                  break;
               }
            }
         }

         return var3.toString();
      }
   }

   private static String getFormattingPrefixAt(String var0, int var1) {
      Character var2 = null;
      StringBuilder var3 = new StringBuilder();
      int var4 = 0;
      short var5 = 167;
      String var6 = "0123456789abcdef";

      for (int var7 = 0; var7 < var0.length() && var4 < var1; var7++) {
         char var8 = var0.charAt(var7);
         if (var8 == 167 && var7 + 1 < var0.length()) {
            char var9 = Character.toLowerCase(var0.charAt(++var7));
            if ("0123456789abcdef".indexOf(var9) >= 0) {
               var2 = var9;
               var3.setLength(0);
            } else if (var9 == 'r') {
               var2 = null;
               var3.setLength(0);
            } else if (var9 >= 'k' && var9 <= 'o' && !containsFormatCode(var3, var9)) {
               var3.append('§').append(var9);
            }
         } else if (var8 != '\n') {
            var4++;
         }
      }

      StringBuilder var10 = new StringBuilder();
      if (var2 != null) {
         var10.append('§').append(var2.charValue());
      }

      var10.append((CharSequence)var3);
      return var10.toString();
   }

   private static boolean containsFormatCode(StringBuilder var0, char var1) {
      for (int var2 = 1; var2 < var0.length(); var2 += 2) {
         if (var0.charAt(var2) == var1) {
            return true;
         }
      }

      return false;
   }

   private static String formatColorList(List<Integer> var0) {
      StringBuilder var1 = new StringBuilder();

      for (int var2 = 0; var2 < var0.size(); var2++) {
         if (var2 > 0) {
            var1.append(',');
         }

         var1.append(String.format(Locale.ROOT, "#%06X", (Integer)var0.get(var2) & 16777215));
      }

      return var1.toString();
   }
}
