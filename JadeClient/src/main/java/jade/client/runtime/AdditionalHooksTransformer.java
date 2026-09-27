// Jade recovery: original class: jade.deps.eLz.InnKfaj
package jade.client.runtime;

import jade.deps.asm.ClassReader;
import jade.deps.asm.ClassVisitor;
import jade.deps.asm.ClassWriter;
import jade.deps.asm.MethodVisitor;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class AdditionalHooksTransformer {
   private final boolean Ffj;
   private final boolean TVdFg8;
   private static final String ABSTRACT_CLIENT_PLAYER = "net/minecraft/client/entity/AbstractClientPlayer";
   private static final String WORLD = "net/minecraft/world/World";
   private static final String LUrec = "net/minecraft/client/multiplayer/WorldClient";
   private static final String ITEM_STACK = "net/minecraft/item/ItemStack";
   private static final String FONT_RENDERER = "net/minecraft/client/gui/FontRenderer";
   private static final String NETWORK_MANAGER_INIT_CHANNEL = "net/minecraft/network/NetworkManager$5";
   private static final String GUI_NEW_CHAT = "net/minecraft/client/gui/GuiNewChat";
   private static final String cni = "net/minecraft/client/gui/GuiPlayerTabOverlay";
   private static final String TGpT = "net/minecraft/client/gui/GuiScreen";
   private static final String RENDER_GLOBAL = "net/minecraft/client/renderer/RenderGlobal";
   private static final String RENDER_MANAGER = "net/minecraft/client/renderer/entity/RenderManager";
   private static final String BLOCK = "net/minecraft/block/Block";
   private static final String ENTITY = "net/minecraft/entity/Entity";
   private static final String yj3 = "net/minecraft/entity/EntityLivingBase";
   private static final String ENTITY_PLAYER = "net/minecraft/entity/player/EntityPlayer";
   private static final String RENDERER_LIVING_ENTITY = "net/minecraft/client/renderer/entity/RendererLivingEntity";
   private static final String RENDER_PLAYER = "net/minecraft/client/renderer/entity/RenderPlayer";
   private static final String LAYER_ARMOR_BASE = "net/minecraft/client/renderer/entity/layers/LayerArmorBase";
   private static final String EluEb1 = "net/minecraft/client/renderer/ItemRenderer";
   private static final String GUI_CONTAINER = "net/minecraft/client/gui/inventory/GuiContainer";
   private static final String GUI_CHAT = "net/minecraft/client/gui/GuiChat";
   private static final String UYsbd = "net/minecraft/client/resources/DefaultResourcePack";
   private static final String THREAD_DOWNLOAD_IMAGE_DATA = "net/minecraft/client/renderer/ThreadDownloadImageData";
   private static final String TEXTURE_MANAGER = "net/minecraft/client/renderer/texture/TextureManager";
   private static final String SOUND_HANDLER = "net/minecraft/client/audio/SoundHandler";
   private static final Set<String> Cnt = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(new String[]{
         "net/minecraft/client/entity/AbstractClientPlayer",
         "net/minecraft/world/World",
         "net/minecraft/client/multiplayer/WorldClient",
         "net/minecraft/item/ItemStack",
         "net/minecraft/client/gui/FontRenderer",
         "net/minecraft/network/NetworkManager$5",
         "net/minecraft/client/gui/GuiNewChat",
         "net/minecraft/client/gui/GuiPlayerTabOverlay",
         "net/minecraft/client/gui/GuiScreen",
         "net/minecraft/client/renderer/RenderGlobal",
         "net/minecraft/client/renderer/entity/RenderManager",
         "net/minecraft/block/Block",
         "net/minecraft/entity/Entity",
         "net/minecraft/entity/EntityLivingBase",
         "net/minecraft/entity/player/EntityPlayer",
         "net/minecraft/client/renderer/entity/RendererLivingEntity",
         "net/minecraft/client/renderer/entity/RenderPlayer",
         "net/minecraft/client/renderer/entity/layers/LayerArmorBase",
         "net/minecraft/client/renderer/ItemRenderer",
         "net/minecraft/client/gui/inventory/GuiContainer",
         "net/minecraft/client/gui/GuiChat",
         "net/minecraft/client/resources/DefaultResourcePack",
         "net/minecraft/client/renderer/ThreadDownloadImageData",
         "net/minecraft/client/renderer/texture/TextureManager",
         "net/minecraft/client/audio/SoundHandler"
      })));

   public AdditionalHooksTransformer(boolean var1, boolean var2) {
      this.Ffj = var1;
      this.TVdFg8 = var2;
   }

   public static Set<String> getHookedClasses() {
      return Cnt;
   }

   public static boolean isHookedClass(String var0) {
      return var0 != null && Cnt.contains(var0.replace('.', '/'));
   }

   public byte[] transformClass(String var1, byte[] var2, final ClassLoader var3) {
      try {
         ClassReader var4 = new ClassReader(var2);
         if (isAlreadyInstrumented(var4)) {
            return var2;
         } else {
            boolean var5 = "net/minecraft/world/World".equals(var1);
            int var6 = var5 ? 1 : 3;
            ClassWriter var7 = new ClassWriter(var4, var6) {
               @Override
               protected String getCommonSuperClass(String var1, String var2x) {
                  try {
                     return ClientClassTransformer.resolveCommonSuperClass(var1, var2x, var3);
                  } catch (Throwable var6x) {
                     try {
                        return super.getCommonSuperClass(var1, var2x);
                     } catch (Throwable var5x) {
                        return "java/lang/Object";
                     }
                  }
               }
            };
            var4.accept(this.LROZ(var1, var7), var5 ? 0 : 4);
            byte[] var8 = var7.toByteArray();
            return this.TVdFg8 ? ClientClassTransformer.mergeNonHookMethods(var1, var2, var8, true) : var8;
         }
      } catch (Throwable var9) {
         throw new IllegalStateException(
            "additional hooks failed for "
               + var1
               + ": "
               + var9,
            var9
         );
      }
   }

   private static boolean isAlreadyInstrumented(ClassReader var0) {
      final boolean[] var1 = new boolean[1];
      var0.accept(
         new ClassVisitor(589824) {
            @Override
            public MethodVisitor visitMethod(int var1x, String var2, String var3, String var4, String[] var5) {
               return new MethodVisitor(589824) {
                  @Override
                  public void visitLdcInsn(Object var1x) {
                     if ("jade.inject.InjectionAgent".equals(var1x)
                        || "jade.inject.callback.bridge.v1".equals(var1x)) {
                        var1[0] = true;
                     }
                  }

                  @Override
                  public void visitMethodInsn(int var1x, String var2x, String var3x, String var4x, boolean var5x) {
                     if ("jade/inject/BootstrapHookBridge".equals(var2x)
                           && var3x.startsWith("invoke")
                        || "jade/inject/InjectionAgent".equals(var2x)
                           && "invokeHook".equals(var3x)) {
                        var1[0] = true;
                     }
                  }
               };
            }
         },
         6
      );
      return var1[0];
   }

   private static int getExpectedHookCount(String var0) {
      if ("net/minecraft/client/entity/AbstractClientPlayer".equals(var0)) {
         return 1;
      } else if ("net/minecraft/client/gui/FontRenderer".equals(var0)) {
         return 2;
      } else if ("net/minecraft/client/gui/GuiNewChat".equals(var0)) {
         return 2;
      } else if ("net/minecraft/client/gui/GuiPlayerTabOverlay".equals(var0)) {
         return 2;
      } else if ("net/minecraft/client/gui/GuiScreen".equals(var0)) {
         return 4;
      } else if ("net/minecraft/client/renderer/entity/RenderManager".equals(var0)) {
         return 2;
      } else if ("net/minecraft/entity/Entity".equals(var0)) {
         return 4;
      } else if ("net/minecraft/entity/EntityLivingBase".equals(var0)) {
         return 3;
      } else if ("net/minecraft/entity/player/EntityPlayer".equals(var0)) {
         return 5;
      } else if ("net/minecraft/client/renderer/entity/RendererLivingEntity".equals(var0)) {
         return 4;
      } else if ("net/minecraft/client/renderer/entity/RenderPlayer".equals(var0)) {
         return 2;
      } else if ("net/minecraft/client/renderer/ItemRenderer".equals(var0)) {
         return 4;
      } else if ("net/minecraft/client/gui/inventory/GuiContainer".equals(var0)) {
         return 7;
      } else if ("net/minecraft/client/gui/GuiChat".equals(var0)) {
         return 4;
      } else if ("net/minecraft/client/resources/DefaultResourcePack".equals(var0)) {
         return 3;
      } else {
         return "net/minecraft/client/renderer/texture/TextureManager".equals(var0) ? 3 : 1;
      }
   }

   private static int countHookedMethods(final String var0, ClassReader var1) {
      final int[] var2 = new int[1];
      var1.accept(new ClassVisitor(589824) {
         @Override
         public MethodVisitor visitMethod(int var1, String var2x, String var3, String var4, String[] var5) {
            if (AdditionalHooksTransformer.isHookTargetMethod(var0, var2x, var3)) {
               var2[0]++;
            }

            return null;
         }
      }, 7);
      return var2[0];
   }

   public static boolean isHookTargetMethod(String var0, String var1, String var2) {
      if ("net/minecraft/client/entity/AbstractClientPlayer".equals(var0)) {
         return matchesMethod(
            var1,
            var2,
            "getLocationCape",
            "func_110303_q",
            "()Lnet/minecraft/util/ResourceLocation;"
         );
      } else if ("net/minecraft/world/World".equals(var0)) {
         return matchesMethod(
            var1,
            var2,
            "spawnEntityInWorld",
            "func_72838_d",
            "(Lnet/minecraft/entity/Entity;)Z"
         );
      } else if ("net/minecraft/client/multiplayer/WorldClient".equals(var0)) {
         return matchesMethod(
            var1,
            var2,
            "addEntityToWorld",
            "func_73027_a",
            "(ILnet/minecraft/entity/Entity;)V"
         );
      } else if ("net/minecraft/item/ItemStack".equals(var0)) {
         return matchesMethod(
            var1,
            var2,
            "hasEffect",
            "func_77962_s",
            "()Z"
         );
      } else if ("net/minecraft/client/gui/FontRenderer".equals(var0)) {
         return matchesMethod(
               var1,
               var2,
               "drawString",
               "func_175065_a",
               "(Ljava/lang/String;FFIZ)I"
            )
            || matchesMethod(
               var1,
               var2,
               "drawStringWithShadow",
               "func_175063_a",
               "(Ljava/lang/String;FFI)I"
            );
      } else if ("net/minecraft/network/NetworkManager$5".equals(var0)) {
         return "initChannel".equals(var1)
            && "(Lio/netty/channel/Channel;)V".equals(var2);
      } else if ("net/minecraft/client/gui/GuiNewChat".equals(var0)) {
         return matchesMethod(
               var1,
               var2,
               "drawChat",
               "func_146230_a",
               "(I)V"
            )
            || matchesMethod(
               var1,
               var2,
               "setChatLine",
               "func_146237_a",
               "(Lnet/minecraft/util/IChatComponent;IIZ)V"
            );
      } else if ("net/minecraft/client/gui/GuiPlayerTabOverlay".equals(var0)) {
         return matchesMethod(
               var1,
               var2,
               "getPlayerName",
               "func_175243_a",
               "(Lnet/minecraft/client/network/NetworkPlayerInfo;)Ljava/lang/String;"
            )
            || matchesMethod(
               var1,
               var2,
               "renderPlayerlist",
               "func_175249_a",
               "(ILnet/minecraft/scoreboard/Scoreboard;Lnet/minecraft/scoreboard/ScoreObjective;)V"
            );
      } else if ("net/minecraft/client/gui/GuiScreen".equals(var0)) {
         return matchesMethod(
               var1,
               var2,
               "drawScreen",
               "func_73863_a",
               "(IIF)V"
            )
            || matchesMethod(
               var1,
               var2,
               "sendChatMessage",
               "func_175281_b",
               "(Ljava/lang/String;Z)V"
            )
            || matchesMethod(
               var1,
               var2,
               "handleKeyboardInput",
               "func_146282_l",
               "()V"
            )
            || matchesMethod(
               var1,
               var2,
               "handleMouseInput",
               "func_146274_d",
               "()V"
            );
      } else if ("net/minecraft/client/renderer/RenderGlobal".equals(var0)) {
         return matchesMethod(
            var1,
            var2,
            "drawSelectionBox",
            "func_72731_b",
            "(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/MovingObjectPosition;IF)V"
         );
      } else if ("net/minecraft/client/renderer/entity/RenderManager".equals(var0)) {
         return matchesMethod(
               var1,
               var2,
               "renderEntityStatic",
               "func_147936_a",
               "(Lnet/minecraft/entity/Entity;FZ)Z"
            )
            || matchesMethod(
               var1,
               var2,
               "cacheActiveRenderInfo",
               "func_180597_a",
               "(Lnet/minecraft/world/World;Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;Lnet/minecraft/client/settings/GameSettings;F)V"
            );
      } else if ("net/minecraft/entity/Entity".equals(var0)) {
         return matchesMethod(
               var1,
               var2,
               "moveEntity",
               "func_70091_d",
               "(DDD)V"
            )
            || matchesMethod(
               var1,
               var2,
               "moveFlying",
               "func_70060_a",
               "(FFF)V"
            )
            || matchesMethod(
               var1,
               var2,
               "getVectorForRotation",
               "func_174806_f",
               "(FF)Lnet/minecraft/util/Vec3;"
            )
            || matchesMethod(
               var1,
               var2,
               "setAngles",
               "func_70082_c",
               "(FF)V"
            );
      } else if ("net/minecraft/entity/EntityLivingBase".equals(var0)) {
         return matchesMethod(
               var1,
               var2,
               "updateDistance",
               "func_110146_f",
               "(FF)F"
            )
            || matchesMethod(
               var1,
               var2,
               "jump",
               "func_70664_aZ",
               "()V"
            )
            || matchesMethod(
               var1,
               var2,
               "onLivingUpdate",
               "func_70636_d",
               "()V"
            );
      } else if ("net/minecraft/entity/player/EntityPlayer".equals(var0)) {
         return matchesMethod(
               var1,
               var2,
               "attackTargetEntityWithCurrentItem",
               "func_71059_n",
               "(Lnet/minecraft/entity/Entity;)V"
            )
            || matchesMethod(
               var1,
               var2,
               "isBlocking",
               "func_70632_aY",
               "()Z"
            )
            || matchesMethod(
               var1,
               var2,
               "isWearing",
               "func_175148_a",
               "(Lnet/minecraft/entity/player/EnumPlayerModelParts;)Z"
            )
            || matchesMethod(
               var1,
               var2,
               "getItemInUseCount",
               "func_71052_bv",
               "()I"
            )
            || matchesMethod(
               var1,
               var2,
               "isUsingItem",
               "func_71039_bw",
               "()Z"
            );
      } else if ("net/minecraft/client/renderer/entity/RendererLivingEntity".equals(var0)) {
         return matchesMethod(
               var1,
               var2,
               "renderModel",
               "func_77036_a",
               "(Lnet/minecraft/entity/EntityLivingBase;FFFFFF)V"
            )
            || matchesMethod(
               var1,
               var2,
               "canRenderName",
               "func_177070_b",
               "(Lnet/minecraft/entity/EntityLivingBase;)Z"
            )
            || matchesMethod(
               var1,
               var2,
               "doRender",
               "func_76986_a",
               "(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V"
            )
            || matchesMethod(
               var1,
               var2,
               "renderName",
               "func_177067_a",
               "(Lnet/minecraft/entity/EntityLivingBase;DDD)V"
            );
      } else if ("net/minecraft/client/renderer/entity/RenderPlayer".equals(var0)) {
         return matchesMethod(
               var1,
               var2,
               "doRender",
               "func_76986_a",
               "(Lnet/minecraft/client/entity/AbstractClientPlayer;DDDFF)V"
            )
            || matchesMethod(
               var1,
               var2,
               "setModelVisibilities",
               "func_177137_d",
               "(Lnet/minecraft/client/entity/AbstractClientPlayer;)V"
            );
      } else if ("net/minecraft/client/renderer/entity/layers/LayerArmorBase".equals(var0)) {
         return matchesMethod(
            var1,
            var2,
            "renderLayer",
            "func_177182_a",
            "(Lnet/minecraft/entity/EntityLivingBase;FFFFFFFI)V"
         );
      } else if ("net/minecraft/client/renderer/ItemRenderer".equals(var0)) {
         return matchesMethod(
               var1,
               var2,
               "renderItemInFirstPerson",
               "func_78440_a",
               "(F)V"
            )
            || matchesMethod(
               var1,
               var2,
               "updateEquippedItem",
               "func_78441_a",
               "()V"
            )
            || matchesMethod(
               var1,
               var2,
               "resetEquippedProgress",
               "func_78444_b",
               "()V"
            )
            || matchesMethod(
               var1,
               var2,
               "resetEquippedProgress2",
               "func_78445_c",
               "()V"
            );
      } else if ("net/minecraft/client/gui/inventory/GuiContainer".equals(var0)) {
         return matchesMethod(
               var1,
               var2,
               "drawSlot",
               "func_146977_a",
               "(Lnet/minecraft/inventory/Slot;)V"
            )
            || matchesMethod(
               var1,
               var2,
               "drawScreen",
               "func_73863_a",
               "(IIF)V"
            )
            || matchesMethod(
               var1,
               var2,
               "keyTyped",
               "func_73869_a",
               "(CI)V"
            )
            || matchesMethod(
               var1,
               var2,
               "mouseClicked",
               "func_73864_a",
               "(III)V"
            )
            || matchesMethod(
               var1,
               var2,
               "mouseClickMove",
               "func_146273_a",
               "(IIIJ)V"
            )
            || matchesMethod(
               var1,
               var2,
               "mouseReleased",
               "func_146286_b",
               "(III)V"
            )
            || matchesMethod(
               var1,
               var2,
               "handleMouseClick",
               "func_146984_a",
               "(Lnet/minecraft/inventory/Slot;III)V"
            );
      } else if ("net/minecraft/client/gui/GuiChat".equals(var0)) {
         return matchesMethod(
               var1,
               var2,
               "keyTyped",
               "func_73869_a",
               "(CI)V"
            )
            || matchesMethod(
               var1,
               var2,
               "drawScreen",
               "func_73863_a",
               "(IIF)V"
            )
            || matchesMethod(
               var1,
               var2,
               "sendAutocompleteRequest",
               "func_146405_a",
               "(Ljava/lang/String;Ljava/lang/String;)V"
            )
            || matchesMethod(
               var1,
               var2,
               "autocompletePlayerNames",
               "func_146404_p_",
               "()V"
            );
      } else if ("net/minecraft/client/resources/DefaultResourcePack".equals(var0)) {
         return matchesMethod(
               var1,
               var2,
               "getResourceStream",
               "func_110605_c",
               "(Lnet/minecraft/util/ResourceLocation;)Ljava/io/InputStream;"
            )
            || matchesMethod(
               var1,
               var2,
               "getInputStream",
               "func_110590_a",
               "(Lnet/minecraft/util/ResourceLocation;)Ljava/io/InputStream;"
            )
            || matchesMethod(
               var1,
               var2,
               "resourceExists",
               "func_110589_b",
               "(Lnet/minecraft/util/ResourceLocation;)Z"
            );
      } else if ("net/minecraft/client/renderer/ThreadDownloadImageData".equals(var0)) {
         return matchesMethod(
            var1,
            var2,
            "loadTexture",
            "func_110551_a",
            "(Lnet/minecraft/client/resources/IResourceManager;)V"
         );
      } else if ("net/minecraft/client/renderer/texture/TextureManager".equals(var0)) {
         return matchesMethod(
               var1,
               var2,
               "onResourceManagerReload",
               "func_110549_a",
               "(Lnet/minecraft/client/resources/IResourceManager;)V"
            )
            || matchesMethod(
               var1,
               var2,
               "bindTexture",
               "func_110577_a",
               "(Lnet/minecraft/util/ResourceLocation;)V"
            )
            || matchesMethod(
               var1,
               var2,
               "loadTexture",
               "func_110579_a",
               "(Lnet/minecraft/util/ResourceLocation;Lnet/minecraft/client/renderer/texture/ITextureObject;)Z"
            );
      } else {
         return "net/minecraft/client/audio/SoundHandler".equals(var0)
            ? matchesMethod(
               var1,
               var2,
               "onResourceManagerReload",
               "func_110549_a",
               "(Lnet/minecraft/client/resources/IResourceManager;)V"
            )
            : "net/minecraft/block/Block".equals(var0)
               && matchesMethod(
                  var1,
                  var2,
                  "addCollisionBoxesToList",
                  "func_180638_a",
                  "(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/AxisAlignedBB;Ljava/util/List;Lnet/minecraft/entity/Entity;)V"
               );
      }
   }

   private ClassVisitor LROZ(final String var1, ClassVisitor var2) {
      return new ClassVisitor(589824, var2) {
         @Override
         public MethodVisitor visitMethod(int var1x, String var2x, String var3, String var4, String[] var5) {
            MethodVisitor var6 = super.visitMethod(var1x, var2x, var3, var4, var5);
            if (!AdditionalHooksTransformer.isHookTargetMethod(var1, var2x, var3)) {
               return var6;
            } else if ("net/minecraft/client/entity/AbstractClientPlayer".equals(var1)) {
               return new AdditionalHooksTransformer$8(var6);
            } else if ("net/minecraft/world/World".equals(var1)) {
               return new AdditionalHooksTransformer$18(var6);
            } else if ("net/minecraft/client/multiplayer/WorldClient".equals(var1)) {
               return new AdditionalHooksTransformer$54(var6);
            } else if ("net/minecraft/item/ItemStack".equals(var1)) {
               return new AdditionalHooksTransformer$22(var6, "onItemHasEffect");
            } else if ("net/minecraft/client/gui/FontRenderer".equals(var1)) {
               if (AdditionalHooksTransformer.isHookMethodMatch(
                  var2x,
                  var3,
                  "drawStringWithShadow",
                  "func_175063_a",
                  "(Ljava/lang/String;FFI)I"
               )) {
                  return (MethodVisitor)(AdditionalHooksTransformer.isBadlionRenderPass(AdditionalHooksTransformer.this) ? new AdditionalHooksTransformer$21(var6) : var6);
               } else {
                  return new AdditionalHooksTransformer$20(var6);
               }
            } else if ("net/minecraft/network/NetworkManager$5".equals(var1)) {
               return new AdditionalHooksTransformer$33(var6, 1, "onNetworkChannelInitialized");
            } else if ("net/minecraft/client/gui/GuiNewChat".equals(var1)) {
               return (MethodVisitor)(!var2x.equals("drawChat")
                     && !var2x.equals("func_146230_a")
                  ? new AdditionalHooksTransformer$10(var6)
                  : new AdditionalHooksTransformer$11(var6, AdditionalHooksTransformer.isBadlionRenderPass(AdditionalHooksTransformer.this)));
            } else if ("net/minecraft/client/gui/GuiPlayerTabOverlay".equals(var1)) {
               return (MethodVisitor)(!var2x.equals("renderPlayerlist")
                     && !var2x.equals("func_175249_a")
                  ? new AdditionalHooksTransformer$43(var6)
                  : new AdditionalHooksTransformer$44(var6));
            } else if ("net/minecraft/client/gui/GuiScreen".equals(var1)) {
               if (var2x.equals("drawScreen")
                  || var2x.equals("func_73863_a")) {
                  return new AdditionalHooksTransformer$51(var6, "onGuiScreenDraw");
               } else if (var2x.equals("sendChatMessage")
                  || var2x.equals("func_175281_b")) {
                  return new AdditionalHooksTransformer$12(var6);
               } else {
                  return (MethodVisitor)(!var2x.equals("handleKeyboardInput")
                        && !var2x.equals("func_146282_l")
                     ? new AdditionalHooksTransformer$49(var6, "onGuiMouseInput")
                     : new AdditionalHooksTransformer$48(var6, "onGuiKeyboardInput"));
               }
            } else if ("net/minecraft/client/renderer/RenderGlobal".equals(var1)) {
               return new AdditionalHooksTransformer$41(var6);
            } else if ("net/minecraft/client/renderer/entity/RenderManager".equals(var1)) {
               return (MethodVisitor)(!var2x.equals("renderEntityStatic")
                     && !var2x.equals("func_147936_a")
                  ? new AdditionalHooksTransformer$51(var6, "onCacheActiveRenderInfo")
                  : new AdditionalHooksTransformer$37(var6));
            } else if ("net/minecraft/entity/Entity".equals(var1)) {
               if (var2x.equals("moveEntity")
                  || var2x.equals("func_70091_d")) {
                  return new AdditionalHooksTransformer$19(var6);
               } else if (var2x.equals("moveFlying")
                  || var2x.equals("func_70060_a")) {
                  return new AdditionalHooksTransformer$32(var6);
               } else {
                  return (MethodVisitor)(!var2x.equals("setAngles")
                        && !var2x.equals("func_70082_c")
                     ? new AdditionalHooksTransformer$53(var6)
                     : new AdditionalHooksTransformer$23(var6));
               }
            } else if ("net/minecraft/entity/EntityLivingBase".equals(var1)) {
               if (var2x.equals("updateDistance")
                  || var2x.equals("func_110146_f")) {
                  return new AdditionalHooksTransformer$52(var6);
               } else if (var2x.equals("jump")
                  || var2x.equals("func_70664_aZ")) {
                  return new AdditionalHooksTransformer$50(var6, "onLivingJump");
               } else if (var2x.equals("getItemInUseCount")
                  || var2x.equals("func_71052_bv")) {
                  return (MethodVisitor)(AdditionalHooksTransformer.isBadlionRenderPass(AdditionalHooksTransformer.this)
                     ? var6
                     : new AdditionalHooksTransformer$26(var6, "onItemUseCount"));
               } else if (!var2x.equals("isUsingItem")
                  && !var2x.equals("func_71039_bw")) {
                  return new AdditionalHooksTransformer$31(var6, AdditionalHooksTransformer.isBadlionRenderPass(AdditionalHooksTransformer.this));
               } else {
                  return (MethodVisitor)(AdditionalHooksTransformer.isBadlionRenderPass(AdditionalHooksTransformer.this)
                     ? var6
                     : new AdditionalHooksTransformer$6(var6, "onIsUsingItem"));
               }
            } else if ("net/minecraft/entity/player/EntityPlayer".equals(var1)) {
               if (var2x.equals("isWearing")
                  || var2x.equals("func_175148_a")) {
                  return new AdditionalHooksTransformer$9(var6);
               } else if (var2x.equals("getItemInUseCount")
                  || var2x.equals("func_71052_bv")) {
                  return (MethodVisitor)(AdditionalHooksTransformer.isBadlionRenderPass(AdditionalHooksTransformer.this)
                     ? var6
                     : new AdditionalHooksTransformer$26(var6, "onItemUseCount"));
               } else if (!var2x.equals("isUsingItem")
                  && !var2x.equals("func_71039_bw")) {
                  return (MethodVisitor)(!var2x.equals(
                           "attackTargetEntityWithCurrentItem"
                        )
                        && !var2x.equals("func_71059_n")
                     ? new AdditionalHooksTransformer$6(var6, "onPlayerBlocking")
                     : new AdditionalHooksTransformer$34(var6));
               } else {
                  return (MethodVisitor)(AdditionalHooksTransformer.isBadlionRenderPass(AdditionalHooksTransformer.this)
                     ? var6
                     : new AdditionalHooksTransformer$6(var6, "onIsUsingItem"));
               }
            } else if ("net/minecraft/client/renderer/entity/RendererLivingEntity".equals(var1)) {
               if (var2x.equals("renderModel")
                  || var2x.equals("func_77036_a")) {
                  return new AdditionalHooksTransformer$29(var6);
               } else if (var2x.equals("canRenderName")
                  || var2x.equals("func_177070_b")) {
                  return new AdditionalHooksTransformer$7(var6);
               } else {
                  return (MethodVisitor)(!var2x.equals("doRender")
                        && !var2x.equals("func_76986_a")
                     ? new AdditionalHooksTransformer$30(var6)
                     : new AdditionalHooksTransformer$28(var6));
               }
            } else if (!"net/minecraft/client/renderer/entity/RenderPlayer".equals(var1)) {
               if ("net/minecraft/client/renderer/entity/layers/LayerArmorBase".equals(var1)) {
                  return new AdditionalHooksTransformer$5(var6);
               } else if ("net/minecraft/client/renderer/ItemRenderer".equals(var1)) {
                  if (var2x.equals("renderItemInFirstPerson")
                     || var2x.equals("func_78440_a")) {
                     return new AdditionalHooksTransformer$27(var6, AdditionalHooksTransformer.isBadlionRenderPass(AdditionalHooksTransformer.this));
                  } else {
                     return !var2x.equals("updateEquippedItem")
                           && !var2x.equals("func_78441_a")
                        ? new AdditionalHooksTransformer$48(var6, "onItemRendererReset")
                        : new AdditionalHooksTransformer$48(var6, "onItemRendererUpdate");
                  }
               } else if ("net/minecraft/client/gui/inventory/GuiContainer".equals(var1)) {
                  if (var2x.equals("drawSlot")
                     || var2x.equals("func_146977_a")) {
                     return new AdditionalHooksTransformer$16(var6);
                  } else if (var2x.equals("drawScreen")
                     || var2x.equals("func_73863_a")) {
                     return new AdditionalHooksTransformer$15(var6);
                  } else {
                     return (MethodVisitor)(!var2x.equals("handleMouseClick")
                           && !var2x.equals("func_146984_a")
                        ? new AdditionalHooksTransformer$48(var6, "onContainerManualInput")
                        : new AdditionalHooksTransformer$14(var6));
                  }
               } else if ("net/minecraft/client/gui/GuiChat".equals(var1)) {
                  if (var2x.equals("keyTyped")
                     || var2x.equals("func_73869_a")) {
                     return new AdditionalHooksTransformer$24(var6);
                  } else if (var2x.equals("drawScreen")
                     || var2x.equals("func_73863_a")) {
                     return new AdditionalHooksTransformer$51(var6, "onGuiChatDraw");
                  } else {
                     return (MethodVisitor)(!var2x.equals(
                              "sendAutocompleteRequest"
                           )
                           && !var2x.equals("func_146405_a")
                        ? new AdditionalHooksTransformer$48(var6, "onGuiChatAutocomplete")
                        : new AdditionalHooksTransformer$25(var6));
                  }
               } else if ("net/minecraft/client/resources/DefaultResourcePack".equals(var1)) {
                  if (var2x.equals("getResourceStream")
                     || var2x.equals("func_110605_c")) {
                     return new AdditionalHooksTransformer$40(var6);
                  } else {
                     return (MethodVisitor)(!var2x.equals("getInputStream")
                           && !var2x.equals("func_110590_a")
                        ? new AdditionalHooksTransformer$38(var6)
                        : new AdditionalHooksTransformer$39(var6));
                  }
               } else if ("net/minecraft/client/renderer/ThreadDownloadImageData".equals(var1)) {
                  return new AdditionalHooksTransformer$17(var6);
               } else if ("net/minecraft/client/renderer/texture/TextureManager".equals(var1)) {
                  if (var2x.equals("onResourceManagerReload")
                     || var2x.equals("func_110549_a")) {
                     return new AdditionalHooksTransformer$46(var6);
                  } else {
                     return !var2x.equals("bindTexture")
                           && !var2x.equals("func_110577_a")
                        ? new AdditionalHooksTransformer$45(var6, "onTextureLoad")
                        : new AdditionalHooksTransformer$45(var6, "onTextureBind");
                  }
               } else if ("net/minecraft/client/audio/SoundHandler".equals(var1)) {
                  return new AdditionalHooksTransformer$42(var6);
               } else {
                  return (MethodVisitor)("net/minecraft/block/Block".equals(var1) ? new AdditionalHooksTransformer$13(var6) : var6);
               }
            } else {
               return (MethodVisitor)(!var2x.equals("doRender")
                     && !var2x.equals("func_76986_a")
                  ? new AdditionalHooksTransformer$36(var6)
                  : new AdditionalHooksTransformer$35(var6));
            }
         }
      };
   }

   private static boolean matchesMethod(String var0, String var1, String var2, String var3, String var4) {
      return var4.equals(var1) && (var2.equals(var0) || var3.equals(var0));
   }

   public static boolean isHookMethodMatch(String var0, String var1, String var2, String var3, String var4) {
      return matchesMethod(var0, var1, var2, var3, var4);
   }

   public static boolean isBadlionRenderPass(AdditionalHooksTransformer var0) {
      return var0.Ffj;
   }
}
