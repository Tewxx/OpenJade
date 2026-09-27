// Jade recovery: original class: jade.deps.eLz.BHs3TBy
package jade.client.runtime;

import jade.deps.asm.ClassReader;
import jade.deps.asm.ClassVisitor;
import jade.deps.asm.ClassWriter;
import jade.deps.asm.Label;
import jade.deps.asm.MethodVisitor;
import jade.deps.asm.Type;
import jade.deps.asm.tree.ClassNode;
import jade.deps.asm.tree.MethodNode;

import jade.inject.HookIds;
import jade.inject.InjectionAgent;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class ClientClassTransformer implements ClassFileTransformer {
   public static final int NCuHh = 589824;
   public static final int HOOK_SCRATCH_SLOT_BASE = 1000;
   private static final String Pof = "jade/inject/BootstrapHookBridge";
   private static final String MINECRAFT_CLASS_NAME = "net/minecraft/client/Minecraft";
   private static final String MOVEMENT_INPUT_FROM_OPTIONS = "net/minecraft/util/MovementInputFromOptions";
   private static final String NETWORK_MANAGER = "net/minecraft/network/NetworkManager";
   private static final String hwU = "net/minecraft/client/gui/GuiIngame";
   private static final String ENTITY_RENDERER = "net/minecraft/client/renderer/EntityRenderer";
   private static final String ENTITY_PLAYER_SP = "net/minecraft/client/entity/EntityPlayerSP";
   private static final String OZKt = "net/minecraft/client/multiplayer/PlayerControllerMP";
   private static final String NET_HANDLER_PLAY_CLIENT = "net/minecraft/client/network/NetHandlerPlayClient";
   private static final Set<String> CORE_HOOK_TARGETS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(new String[]{
         "net/minecraft/client/Minecraft",
         "net/minecraft/util/MovementInputFromOptions",
         "net/minecraft/network/NetworkManager",
         "net/minecraft/client/gui/GuiIngame",
         "net/minecraft/client/renderer/EntityRenderer",
         "net/minecraft/client/entity/EntityPlayerSP",
         "net/minecraft/client/multiplayer/PlayerControllerMP",
         "net/minecraft/client/network/NetHandlerPlayClient"
      })));
   private static final Set<String> REQUIRED_TARGETS = Collections.unmodifiableSet(new HashSet<>(Collections.singletonList("net/minecraft/client/Minecraft")));
   private static final Set<String> ALL_HOOK_TARGETS = TpKy();
   private static final Set<String> LQvJ = Collections.newSetFromMap(new ConcurrentHashMap<>());
   private static final Set<String> SKIPPED_TARGETS = Collections.newSetFromMap(new ConcurrentHashMap<>());
   private static final ConcurrentHashMap<String, String> UVvm = new ConcurrentHashMap<>();
   private static final ConcurrentHashMap<String, String> WTqQo = new ConcurrentHashMap<>();
   private final boolean badlionRenderPass;
   private final boolean forgeHudPass;
   private final Set<String> activeTargetClasses;
   private final AdditionalHooksTransformer Ifu;

   public ClientClassTransformer() {
      this(false, false);
   }

   public ClientClassTransformer(boolean var1) {
      this(var1, false);
   }

   public ClientClassTransformer(boolean var1, boolean var2) {
      this.badlionRenderPass = var1;
      this.forgeHudPass = var2;
      this.activeTargetClasses = var1 ? badlionTargetClasses() : (var2 ? EfYf() : vanillaTargetClasses());
      this.Ifu = new AdditionalHooksTransformer(var1, var2);
   }

   public static boolean VDbv(String var0) {
      return var0 != null && ALL_HOOK_TARGETS.contains(var0.replace('.', '/'));
   }

   public static Set<String> vanillaTargetClasses() {
      HashSet var0 = new HashSet<>(ALL_HOOK_TARGETS);
      var0.remove("net/minecraft/client/multiplayer/WorldClient");
      var0.remove("net/minecraft/world/World");
      return Collections.unmodifiableSet(var0);
   }

   public static Set<String> badlionTargetClasses() {
      HashSet var0 = new HashSet<>(ALL_HOOK_TARGETS);
      var0.remove("net/minecraft/client/multiplayer/WorldClient");
      var0.remove("net/minecraft/world/World");
      return Collections.unmodifiableSet(var0);
   }

   public static Set<String> EfYf() {
      HashSet var0 = new HashSet<>(vanillaTargetClasses());
      var0.remove("net/minecraft/entity/player/EntityPlayer");
      return Collections.unmodifiableSet(var0);
   }

   public boolean isConfiguredTarget(String var1) {
      return var1 != null && this.activeTargetClasses.contains(var1.replace('.', '/'));
   }

   public static boolean isCoreTargetClass(String var0) {
      return var0 != null && CORE_HOOK_TARGETS.contains(var0.replace('.', '/'));
   }

   public static boolean isOptionalTargetClass(String var0) {
      if (var0 == null) {
         return false;
      } else {
         String var1 = var0.replace('.', '/');
         return ALL_HOOK_TARGETS.contains(var1) && !REQUIRED_TARGETS.contains(var1);
      }
   }

   public static void markTargetSkipped(String var0) {
      if (var0 != null) {
         String var1 = var0.replace('.', '/');
         SKIPPED_TARGETS.add(var1);
         LQvJ.remove(var1);
      }
   }

   public static int getSkippedTargetCount() {
      return SKIPPED_TARGETS.size();
   }

   public static String sKg03() {
      ArrayList var0 = new ArrayList<>(SKIPPED_TARGETS);
      Collections.sort(var0);
      StringBuilder var1 = new StringBuilder();

      for (String var3 : (java.lang.Iterable<String>) (java.lang.Iterable<?>) (var0)) {
         if (var1.length() != 0) {
            var1.append(", ");
         }

         int var4 = var3.lastIndexOf(47);
         var1.append(var4 < 0 ? var3 : var3.substring(var4 + 1));
      }

      return var1.toString();
   }

   public static int getDegradedTargetCount() {
      return WTqQo.size();
   }

   public static String wnx69() {
      ArrayList var0 = new ArrayList<>(WTqQo.keySet());
      Collections.sort(var0);
      StringBuilder var1 = new StringBuilder();

      for (String var3 : (java.lang.Iterable<String>) (java.lang.Iterable<?>) (var0)) {
         if (var1.length() != 0) {
            var1.append(", ");
         }

         int var4 = var3.lastIndexOf(47);
         var1.append(var4 < 0 ? var3 : var3.substring(var4 + 1));
         var1.append('[').append(WTqQo.get(var3)).append(']');
      }

      return var1.toString();
   }

   private static Set<String> TpKy() {
      HashSet var0 = new HashSet<>(CORE_HOOK_TARGETS);
      var0.addAll(AdditionalHooksTransformer.getHookedClasses());
      return Collections.unmodifiableSet(var0);
   }

   public static void resetTracking() {
      LQvJ.clear();
      SKIPPED_TARGETS.clear();
      UVvm.clear();
      WTqQo.clear();
   }

   public static boolean gGlv(String var0) {
      return LQvJ.contains(var0);
   }

   public static String sqi5(String var0) {
      return UVvm.get(var0);
   }

   @Override
   public byte[] transform(ClassLoader var1, String var2, Class<?> var3, ProtectionDomain var4, byte[] var5) {
      return var2 != null && var5 != null && this.isConfiguredTarget(var2) ? this.transformCanonical(var2, var5, var1) : null;
   }

   public byte[] transformCanonical(String var1, byte[] var2) {
      return this.transformCanonical(var1, var2, null);
   }

   public byte[] transformCanonical(String var1, byte[] var2, ClassLoader var3) {
      return this.transformClassBytes(var1, var2, var3);
   }

   private byte[] transformClassBytes(String var1, byte[] var2, final ClassLoader var3) {
      if (!this.isConfiguredTarget(var1)) {
         return null;
      } else if (SKIPPED_TARGETS.contains(var1)) {
         InjectionAgent.reportDiagnostic("transform skipped class=" + var1);
         return null;
      } else {
         InjectionAgent.reportDiagnostic(
            "transform entered class="
               + var1
               + " bytes="
               + (var2 == null ? -1 : var2.length)
               + " loader="
               + (var3 == null ? "null" : var3.getClass().getName())
         );
         if (AdditionalHooksTransformer.isHookedClass(var1)) {
            try {
               byte[] var11 = this.Ifu.transformClass(var1, var2, var3);
               LQvJ.add(var1);
               UVvm.remove(var1);
               InjectionAgent.reportDiagnostic(
                  "transform completed additional class="
                     + var1
                     + " outputBytes="
                     + (var11 == null ? -1 : var11.length)
               );
               return var11;
            } catch (Throwable var9) {
               UVvm.put(var1, mMyrqLf(var9));
               InjectionAgent.reportDiagnostic(
                  "Hook transformer "
                     + var1
                     + " failed: "
                     + var9
               );
               return null;
            }
         } else {
            try {
               ClassReader var4 = new ClassReader(var2);
               if (containsInjectedHooks(var4)) {
                  LQvJ.add(var1);
                  InjectionAgent.reportDiagnostic(
                     "transform already present class=" + var1
                  );
                  return var2;
               } else {
                  int var5 = expectedAnchorCount(var1);
                  int var6 = EVisWy1(var1, var4);
                  if (var6 == 0) {
                     throw new IllegalStateException(
                        "no compatible hook anchors found (expected up to "
                           + var5
                           + ")"
                     );
                  } else {
                     ClassWriter var7 = new ClassWriter(var4, 3) {
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
                     var4.accept(KjfC(var1, var7, this.badlionRenderPass, this.forgeHudPass), 4);
                     byte[] var8 = var7.toByteArray();
                     if (this.forgeHudPass) {
                        var8 = mergeNonHookMethods(var1, var2, var8, false);
                     }

                     this.acAfeX(var1, var8);
                     LQvJ.add(var1);
                     UVvm.remove(var1);
                     InjectionAgent.reportDiagnostic(
                        "transform completed class="
                           + var1
                           + " anchors="
                           + var6
                           + "/"
                           + var5
                           + " outputBytes="
                           + var8.length
                     );
                     return var8;
                  }
               }
            } catch (Throwable var10) {
               UVvm.put(var1, mMyrqLf(var10));
               InjectionAgent.reportDiagnostic(
                  "Hook transformer "
                     + var1
                     + " failed: "
                     + var10
               );
               return null;
            }
         }
      }
   }

   private static String mMyrqLf(Throwable var0) {
      StringBuilder var1 = new StringBuilder();
      Throwable var2 = var0;

      for (int var3 = 0; var2 != null && var3++ < 6; var2 = var2.getCause()) {
         if (var1.length() != 0) {
            var1.append(" caused by ");
         }

         var1.append(var2.getClass().getName());
         if (var2.getMessage() != null) {
            var1.append(": ").append(var2.getMessage());
         }

         StackTraceElement[] var4 = var2.getStackTrace();
         int var5 = Math.min(var4.length, 8);

         for (int var6 = 0; var6 < var5; var6++) {
            var1.append(" at ").append(var4[var6]);
         }
      }

      return var1.toString();
   }

   private void acAfeX(String var1, byte[] var2) {
      final HashSet var3 = new HashSet();
      new ClassReader(var2)
         .accept(
            new ClassVisitor(589824) {
               @Override
               public MethodVisitor visitMethod(int var1, String var2x, String var3x, String var4, String[] var5) {
                  return new MethodVisitor(589824) {
                     private Integer pendingHookId;

                     @Override
                     public void visitLdcInsn(Object var1) {
                        if (var1 instanceof Integer) {
                           this.pendingHookId = (Integer)var1;
                        }
                     }

                     @Override
                     public void visitInsn(int var1) {
                        if (var1 >= 2 && var1 <= 8) {
                           this.pendingHookId = var1 - 3;
                        }
                     }

                     @Override
                     public void visitIntInsn(int var1, int var2x) {
                        if (var1 == 16 || var1 == 17) {
                           this.pendingHookId = var2x;
                        }
                     }

                     @Override
                     public void visitMethodInsn(int var1, String var2x, String var3xx, String var4x, boolean var5x) {
                        if ("jade/inject/BootstrapHookBridge".equals(var2x)
                           && var3xx.startsWith("invoke")
                           && this.pendingHookId != null) {
                           var3.add(this.pendingHookId);
                        }
                     }
                  };
               }
            },
            6
         );
      String[] var4 = this.requiredCallbacksFor(var1);
      Map var5 = HookIds.snapshot();
      ArrayList var6 = new ArrayList();

      for (String var10 : var4) {
         boolean var11 = false;

         for (Entry var13 : (java.lang.Iterable<Entry>) (java.lang.Iterable<?>) (var5.entrySet())) {
            if (((String)var13.getKey()).startsWith(var10 + "(") && var3.contains(var13.getValue())) {
               var11 = true;
               break;
            }
         }

         if (!var11) {
            var6.add(var10);
         }
      }

      for (String var20 : this.minimumCallbacksFor(var1)) {
         if (var6.contains(var20)) {
            throw new IllegalStateException(
               "essential callback was not injected into "
                  + var1
                  + ": "
                  + var20
            );
         }
      }

      if (var6.isEmpty()) {
         WTqQo.remove(var1);
      } else {
         StringBuilder var15 = new StringBuilder();

         for (String var19 : (java.lang.Iterable<String>) (java.lang.Iterable<?>) (var6)) {
            if (var15.length() != 0) {
               var15.append('|');
            }

            var15.append(var19);
         }

         WTqQo.put(var1, var15.toString());
         InjectionAgent.reportDiagnostic(
            "partial hook capability class="
               + var1
               + " missing="
               + var15
         );
      }
   }

   public String[] minimumCallbacksFor(String var1) {
      return "net/minecraft/client/Minecraft".equals(var1) ? new String[]{"onRunTickStart", "onRunTickEnd"} : new String[0];
   }

   public String[] requiredCallbacksFor(String var1) {
      if ("net/minecraft/client/Minecraft".equals(var1)) {
         if (this.badlionRenderPass) {
            return new String[]{
         "onRunTickStart",
         "onRunTickEnd",
         "onClickMouse",
         "onRightClickMouse",
         "onRunGameLoopStart",
         "onDisplayGuiScreen",
         "onPrePlayerInteract",
         "onBadlionThirdPersonTick",
         "onBadlionWorldTick"
      };
         } else {
            return this.forgeHudPass ? new String[]{"onRunTickStart", "onRunTickEnd", "onClickMouse", "onRightClickMouse", "onRunGameLoopStart", "onDisplayGuiScreen"} : new String[]{
         "onRunTickStart",
         "onRunTickEnd",
         "onClickMouse",
         "onRightClickMouse",
         "onRunGameLoopStart",
         "onDisplayGuiScreen",
         "onSetThirdPersonView"
      };
         }
      } else if ("net/minecraft/client/gui/GuiIngame".equals(var1)) {
         return this.forgeHudPass ? new String[0] : new String[]{"onRenderHud"};
      } else if ("net/minecraft/client/renderer/EntityRenderer".equals(var1)) {
         if (this.badlionRenderPass) {
            return new String[]{"onRenderWorldLast", "onGetMouseOverPre", "onGetMouseOverPost", "onFreelookMouseFocus"};
         } else {
            return this.forgeHudPass ? new String[]{"onRenderWorldLast", "onGetMouseOverPre", "onGetMouseOverPost", "onFreelookMouseFocus", "onRenderHud"} : new String[]{"onRenderWorldLast", "onGetMouseOverPre", "onGetMouseOverPost", "onFreelookMouseFocus"};
         }
      } else if ("net/minecraft/client/entity/EntityPlayerSP".equals(var1)) {
         return new String[]{
         "onPlayerUpdateHead", "onPlayerUpdateReturn", "onUpdateWalkingPlayer", "onPlayerLivingUpdateBeforeSuper", "onPlayerLivingUpdateAfterSuper", "onFreecamCurrentView"
      };
      } else if ("net/minecraft/client/multiplayer/PlayerControllerMP".equals(var1)) {
         return new String[]{"onSendUseItem", "onAttackEntity", "onPlayerDamageBlockHead", "onPlayerDamageBlockReturn", "onFastMineBlockHitDelay"};
      } else if ("net/minecraft/client/network/NetHandlerPlayClient".equals(var1)) {
         return this.badlionRenderPass ? new String[]{"onHandleChat", "onHandleDisconnect", "onHandleEntityVelocity", "onHandleExplosion", "onEntityJoinWorld"} : new String[]{"onHandleChat", "onHandleDisconnect", "onHandleEntityVelocity", "onHandleExplosion"};
      } else if ("net/minecraft/network/NetworkManager".equals(var1)) {
         return this.badlionRenderPass ? new String[]{"onNetworkSend", "onNetworkDispatch", "onNetworkReceive", "onNetworkBootstrapHandler"} : new String[]{"onNetworkSend", "onNetworkDispatch", "onNetworkReceive"};
      } else if ("net/minecraft/client/gui/GuiScreen".equals(var1)) {
         return new String[]{"onGuiScreenDraw", "onGuiKeyboardInput", "onGuiMouseInput"};
      } else if ("net/minecraft/client/gui/FontRenderer".equals(var1)) {
         return this.badlionRenderPass ? new String[]{"onFontDraw", "onChatDrawString"} : new String[]{"onFontDraw"};
      } else if ("net/minecraft/client/gui/GuiNewChat".equals(var1)) {
         return this.badlionRenderPass ? new String[]{"onChatRenderBegin", "onChatRenderEnd", "onChatDrawString", "onBadlionCachedChatDraw", "onChatLineSet"} : new String[]{"onChatRenderBegin", "onChatRenderEnd", "onChatLineSet"};
      } else if ("net/minecraft/entity/EntityLivingBase".equals(var1)) {
         return this.badlionRenderPass ? new String[]{"onUpdateDistance", "onLivingJump", "onMoveEntityWithHeading", "onShouldRemoveBadlionJumpDelay"} : new String[]{"onUpdateDistance", "onLivingJump", "onMoveEntityWithHeading"};
      } else if ("net/minecraft/entity/player/EntityPlayer".equals(var1)) {
         return this.badlionRenderPass ? new String[]{"onPlayerAttack", "onKeepSprintAttackSlowdown", "onPlayerBlocking", "onCapeWearState"} : new String[]{
         "onPlayerAttack", "onKeepSprintAttackSlowdown", "onPlayerBlocking", "onItemUseCount", "onIsUsingItem", "onCapeWearState"
      };
      } else if ("net/minecraft/world/World".equals(var1)) {
         return new String[]{"onEntityJoinWorld"};
      } else if ("net/minecraft/client/multiplayer/WorldClient".equals(var1)) {
         return new String[]{"onEntityJoinWorld"};
      } else if ("net/minecraft/client/renderer/ItemRenderer".equals(var1)) {
         return this.badlionRenderPass ? new String[]{"onItemRenderPre", "onItemRenderPost", "onBadlionFirstPersonUseCount", "onItemRendererUpdate", "onItemRendererReset"} : new String[]{"onItemRenderPre", "onItemRenderPost", "onItemRendererUpdate", "onItemRendererReset"};
      } else if ("net/minecraft/client/renderer/entity/RendererLivingEntity".equals(var1)) {
         return new String[]{"onRenderLivingPre", "onRenderLivingPost", "onSuppressLivingName"};
      } else {
         return "net/minecraft/client/renderer/entity/RenderPlayer".equals(var1) ? new String[]{"onRenderPlayerPre", "onRenderPlayerPost"} : new String[0];
      }
   }

   public static String resolveCommonSuperClass(String var0, String var1, ClassLoader var2) throws ClassNotFoundException {
      if (var2 == null) {
         throw new ClassNotFoundException("target classloader is unavailable");
      } else {
         String var3 = tryResolveCommonSuperClass(var0, var1, var2);
         if (var3 != null) {
            return var3;
         } else {
            Class var4 = Class.forName(var0.replace('/', '.'), false, var2);
            Class var5 = Class.forName(var1.replace('/', '.'), false, var2);
            if (var4.isAssignableFrom(var5)) {
               return var0;
            } else if (var5.isAssignableFrom(var4)) {
               return var1;
            } else if (!var4.isInterface() && !var5.isInterface()) {
               do {
                  var4 = var4.getSuperclass();
               } while (var4 != null && !var4.isAssignableFrom(var5));

               return var4 == null ? "java/lang/Object" : Type.getInternalName(var4);
            } else {
               return "java/lang/Object";
            }
         }
      }
   }

   private static String tryResolveCommonSuperClass(String var0, String var1, ClassLoader var2) {
      if (var0.equals(var1)) {
         return var0;
      } else {
         HashMap var3 = new HashMap();
         if (isSupertypeOf(var0, var1, var2, var3, new HashSet<>())) {
            return var0;
         } else if (isSupertypeOf(var1, var0, var2, var3, new HashSet<>())) {
            return var1;
         } else {
            ClientClassTransformer$12 var4 = HBDj(var0, var2, var3);
            ClientClassTransformer$12 var5 = HBDj(var1, var2, var3);
            if (var4 != null && var5 != null) {
               if (!var4.osQd && !var5.osQd) {
                  String var6 = var4.qWw;

                  while (var6 != null) {
                     if (isSupertypeOf(var6, var1, var2, var3, new HashSet<>())) {
                        return var6;
                     }

                     ClientClassTransformer$12 var7 = HBDj(var6, var2, var3);
                     var6 = var7 == null ? null : var7.qWw;
                  }

                  return "java/lang/Object";
               } else {
                  return "java/lang/Object";
               }
            } else {
               return null;
            }
         }
      }
   }

   private static boolean isSupertypeOf(String var0, String var1, ClassLoader var2, Map<String, ClientClassTransformer$12> var3, Set<String> var4) {
      if (!var0.equals(var1) && !"java/lang/Object".equals(var0)) {
         if (!var4.add(var1)) {
            return false;
         } else {
            ClientClassTransformer$12 var5 = HBDj(var1, var2, var3);
            if (var5 == null) {
               return false;
            } else if (var5.qWw != null && isSupertypeOf(var0, var5.qWw, var2, var3, var4)) {
               return true;
            } else {
               for (String var9 : var5.interfaceNames) {
                  if (isSupertypeOf(var0, var9, var2, var3, var4)) {
                     return true;
                  }
               }

               return false;
            }
         }
      } else {
         return true;
      }
   }

   private static ClientClassTransformer$12 HBDj(String var0, ClassLoader var1, Map<String, ClientClassTransformer$12> var2) {
      if (var2.containsKey(var0)) {
         return (ClientClassTransformer$12)var2.get(var0);
      } else {
         InputStream var3 = var1.getResourceAsStream(var0 + ".class");
         if (var3 == null) {
            var2.put(var0, null);
            return null;
         } else {
            ClientClassTransformer$12 var19;
            try {
               ClassReader var4 = new ClassReader(var3);
               if (var0.equals(var4.getClassName())) {
                  var19 = new ClientClassTransformer$12(var4.getSuperName(), var4.getInterfaces(), (var4.getAccess() & 512) != 0);
                  var2.put(var0, var19);
                  return var19;
               }

               var2.put(var0, null);
               var19 = null;
            } catch (IOException var17) {
               var2.put(var0, null);
               return null;
            } finally {
               try {
                  var3.close();
               } catch (IOException var16) {
               }
            }

            return var19;
         }
      }
   }

   private static boolean containsInjectedHooks(ClassReader var0) {
      final boolean[] var1 = new boolean[1];
      var0.accept(
         new ClassVisitor(589824) {
            @Override
            public MethodVisitor visitMethod(int var1x, String var2, String var3, String var4, String[] var5) {
               return new MethodVisitor(589824) {
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

   public static boolean bytesContainInjectedHooks(byte[] var0) {
      return var0 != null && containsInjectedHooks(new ClassReader(var0));
   }

   private static int expectedAnchorCount(String var0) {
      if ("net/minecraft/client/Minecraft".equals(var0)) {
         return 7;
      } else if ("net/minecraft/util/MovementInputFromOptions".equals(var0)) {
         return 1;
      } else if ("net/minecraft/network/NetworkManager".equals(var0)) {
         return 3;
      } else if ("net/minecraft/client/gui/GuiIngame".equals(var0)) {
         return 1;
      } else if ("net/minecraft/client/renderer/EntityRenderer".equals(var0)) {
         return 8;
      } else if ("net/minecraft/client/entity/EntityPlayerSP".equals(var0)) {
         return 5;
      } else if ("net/minecraft/client/multiplayer/PlayerControllerMP".equals(var0)) {
         return 4;
      } else {
         return "net/minecraft/client/network/NetHandlerPlayClient".equals(var0) ? 4 : 0;
      }
   }

   private static int EVisWy1(final String var0, ClassReader var1) {
      final int[] var2 = new int[1];
      var1.accept(new ClassVisitor(589824) {
         @Override
         public MethodVisitor visitMethod(int var1, String var2x, String var3, String var4, String[] var5) {
            if (ClientClassTransformer.Mshco(var0, var2x, var3)) {
               var2[0]++;
            }

            return null;
         }
      }, 7);
      return var2[0];
   }

   public static boolean Mshco(String var0, String var1, String var2) {
      if ("net/minecraft/client/Minecraft".equals(var0)) {
         return GVIslF(
               var1,
               var2,
               "startGame",
               "func_71384_a",
               "()V"
            )
            || GVIslF(
               var1,
               var2,
               "runTick",
               "func_71407_l",
               "()V"
            )
            || GVIslF(
               var1,
               var2,
               "runGameLoop",
               "func_71411_J",
               "()V"
            )
            || GVIslF(
               var1,
               var2,
               "clickMouse",
               "func_147116_af",
               "()V"
            )
            || GVIslF(
               var1,
               var2,
               "rightClickMouse",
               "func_147121_ag",
               "()V"
            )
            || GVIslF(
               var1,
               var2,
               "loadWorld",
               "func_71353_a",
               "(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V"
            )
            || GVIslF(
               var1,
               var2,
               "displayGuiScreen",
               "func_147108_a",
               "(Lnet/minecraft/client/gui/GuiScreen;)V"
            );
      } else if ("net/minecraft/util/MovementInputFromOptions".equals(var0)) {
         return GVIslF(
            var1,
            var2,
            "updatePlayerMoveState",
            "func_78898_a",
            "()V"
         );
      } else if ("net/minecraft/network/NetworkManager".equals(var0)) {
         return GVIslF(
               var1,
               var2,
               "sendPacket",
               "func_179290_a",
               "(Lnet/minecraft/network/Packet;)V"
            )
            || GVIslF(
               var1,
               var2,
               "dispatchPacket",
               "func_150732_b",
               "(Lnet/minecraft/network/Packet;[Lio/netty/util/concurrent/GenericFutureListener;)V"
            )
            || "channelRead0".equals(var1)
               && "(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/Packet;)V".equals(var2);
      } else if ("net/minecraft/client/gui/GuiIngame".equals(var0)) {
         return GVIslF(
            var1,
            var2,
            "renderGameOverlay",
            "func_175180_a",
            "(F)V"
         );
      } else if ("net/minecraft/client/renderer/EntityRenderer".equals(var0)) {
         return GVIslF(
               var1,
               var2,
               "orientCamera",
               "func_78467_g",
               "(F)V"
            )
            || GVIslF(
               var1,
               var2,
               "renderWorldPass",
               "func_175068_a",
               "(IFJ)V"
            )
            || GVIslF(
               var1,
               var2,
               "getMouseOver",
               "func_78473_a",
               "(F)V"
            )
            || GVIslF(
               var1,
               var2,
               "updateCameraAndRender",
               "func_181560_a",
               "(FJ)V"
            )
            || GVIslF(
               var1,
               var2,
               "setupFog",
               "func_78468_a",
               "(IF)V"
            )
            || GVIslF(
               var1,
               var2,
               "updateFogColor",
               "func_78466_h",
               "(F)V"
            )
            || GVIslF(
               var1,
               var2,
               "setupCameraTransform",
               "func_78479_a",
               "(FI)V"
            )
            || GVIslF(
               var1,
               var2,
               "renderWorld",
               "func_78471_a",
               "(FJ)V"
            );
      } else if ("net/minecraft/client/entity/EntityPlayerSP".equals(var0)) {
         return GVIslF(
               var1,
               var2,
               "onUpdate",
               "func_70071_h_",
               "()V"
            )
            || GVIslF(
               var1,
               var2,
               "closeScreen",
               "func_71053_j",
               "()V"
            )
            || GVIslF(
               var1,
               var2,
               "onUpdateWalkingPlayer",
               "func_175161_p",
               "()V"
            )
            || GVIslF(
               var1,
               var2,
               "onLivingUpdate",
               "func_70636_d",
               "()V"
            )
            || GVIslF(
               var1,
               var2,
               "updateEntityActionState",
               "func_70626_be",
               "()V"
            );
      } else if ("net/minecraft/client/multiplayer/PlayerControllerMP".equals(var0)) {
         return GVIslF(
               var1,
               var2,
               "sendUseItem",
               "func_78769_a",
               "(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;)Z"
            )
            || GVIslF(
               var1,
               var2,
               "attackEntity",
               "func_78764_a",
               "(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/entity/Entity;)V"
            )
            || GVIslF(
               var1,
               var2,
               "onPlayerDamageBlock",
               "func_180512_c",
               "(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)Z"
            )
            || GVIslF(
               var1,
               var2,
               "clickBlock",
               "func_180511_b",
               "(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)Z"
            );
      } else {
         return !"net/minecraft/client/network/NetHandlerPlayClient".equals(var0)
            ? false
            : GVIslF(
                  var1,
                  var2,
                  "handleChat",
                  "func_147251_a",
                  "(Lnet/minecraft/network/play/server/S02PacketChat;)V"
               )
               || GVIslF(
                  var1,
                  var2,
                  "handleDisconnect",
                  "func_147253_a",
                  "(Lnet/minecraft/network/play/server/S40PacketDisconnect;)V"
               )
               || GVIslF(
                  var1,
                  var2,
                  "handleEntityVelocity",
                  "func_147244_a",
                  "(Lnet/minecraft/network/play/server/S12PacketEntityVelocity;)V"
               )
               || GVIslF(
                  var1,
                  var2,
                  "handleExplosion",
                  "func_147283_a",
                  "(Lnet/minecraft/network/play/server/S27PacketExplosion;)V"
               );
      }
   }

   public static byte[] mergeNonHookMethods(String var0, byte[] var1, byte[] var2, boolean var3) {
      ClassNode var4 = new ClassNode(589824);
      new ClassReader(var1).accept(var4, 0);
      ClassNode var5 = new ClassNode(589824);
      new ClassReader(var2).accept(var5, 0);
      HashMap var6 = new HashMap();

      for (MethodNode var8 : var4.methods) {
         var6.put(var8.name + var8.desc, var8);
      }

      for (int var11 = 0; var11 < var5.methods.size(); var11++) {
         MethodNode var13 = var5.methods.get(var11);
         boolean var9 = !var3 && "net/minecraft/client/network/NetHandlerPlayClient".equals(var0)
            || (var3 ? AdditionalHooksTransformer.isHookTargetMethod(var0, var13.name, var13.desc) : Mshco(var0, var13.name, var13.desc));
         if (!var9) {
            MethodNode var10 = (MethodNode)var6.get(var13.name + var13.desc);
            if (var10 != null) {
               var5.methods.set(var11, var10);
            }
         }
      }

      ClassWriter var12 = new ClassWriter(0);
      var5.accept(var12);
      return var12.toByteArray();
   }

   private static ClassVisitor KjfC(String var0, ClassVisitor var1, boolean var2, boolean var3) {
      if ("net/minecraft/client/Minecraft".equals(var0)) {
         return new ClientClassTransformer$21(var1, var2);
      } else if ("net/minecraft/util/MovementInputFromOptions".equals(var0)) {
         return new ClientClassTransformer$22(var1);
      } else if ("net/minecraft/network/NetworkManager".equals(var0)) {
         return new ClientClassTransformer$24(var1, var2);
      } else if ("net/minecraft/client/gui/GuiIngame".equals(var0)) {
         return new ClientClassTransformer$20(var1, var3);
      } else if ("net/minecraft/client/renderer/EntityRenderer".equals(var0)) {
         return new ClientClassTransformer$16(var1, var2, var3);
      } else if ("net/minecraft/client/entity/EntityPlayerSP".equals(var0)) {
         return new ClientClassTransformer$15(var1);
      } else if ("net/minecraft/client/multiplayer/PlayerControllerMP".equals(var0)) {
         return new ClientClassTransformer$28(var1);
      } else {
         return (ClassVisitor)("net/minecraft/client/network/NetHandlerPlayClient".equals(var0) ? new ClientClassTransformer$23(var1, var2) : var1);
      }
   }

   private static boolean GVIslF(String var0, String var1, String var2, String var3, String var4) {
      return var4.equals(var1) && (var2.equals(var0) || var3.equals(var0));
   }

   public static void emitReturnIfFalse(MethodVisitor var0) {
      Label var1 = new Label();
      var0.visitJumpInsn(153, var1);
      var0.visitInsn(177);
      var0.visitLabel(var1);
   }

   public static void emitHookCall(MethodVisitor var0, String var1, String var2) {
      Type[] var3 = Type.getArgumentTypes(var2);
      Type var4 = Type.getReturnType(var2);
      storeHookArguments(var0, var3);
      Label var5 = new Label();
      Label var6 = new Label();
      Label var7 = new Label();
      Label var8 = new Label();
      var0.visitTryCatchBlock(var5, var6, var7, "java/lang/Throwable");
      var0.visitLabel(var5);
      emitIntConstant(var0, HookIds.id(var1, var2));
      loadHookArguments(var0, var3);
      Type[] var9 = new Type[var3.length + 1];
      var9[0] = Type.INT_TYPE;
      System.arraycopy(var3, 0, var9, 1, var3.length);
      var0.visitMethodInsn(184, "jade/inject/BootstrapHookBridge", RPa2(var4), Type.getMethodDescriptor(var4, var9), false);
      var0.visitLabel(var6);
      var0.visitJumpInsn(167, var8);
      var0.visitLabel(var7);
      var0.visitInsn(87);
      emitDefaultValue(var0, var4);
      var0.visitLabel(var8);
   }

   private static String RPa2(Type var0) {
      switch (var0.getSort()) {
         case 0:
            return "invoke";
         case 1:
            return "invokeBoolean";
         case 2:
         case 3:
         case 4:
         case 7:
         default:
            throw new IllegalArgumentException(
               "unsupported hook return type: " + var0
            );
         case 5:
            return "invokeInt";
         case 6:
            return "invokeFloat";
         case 8:
            return "invokeDouble";
         case 9:
         case 10:
            return "invokeObject";
      }
   }

   private static void loadHookArguments(MethodVisitor var0, Type[] var1) {
      int[] var2 = computeHookArgumentSlots(var1);

      for (int var3 = 0; var3 < var1.length; var3++) {
         var0.visitVarInsn(var1[var3].getOpcode(21), var2[var3]);
      }
   }

   private static void storeHookArguments(MethodVisitor var0, Type[] var1) {
      int[] var2 = computeHookArgumentSlots(var1);

      for (int var3 = var1.length - 1; var3 >= 0; var3--) {
         Type var4 = var1[var3];
         var0.visitVarInsn(var4.getOpcode(54), var2[var3]);
      }
   }

   private static int[] computeHookArgumentSlots(Type[] var0) {
      int[] var1 = new int[var0.length];
      int var2 = 1000;

      for (int var3 = 0; var3 < var0.length; var3++) {
         var1[var3] = var2;
         var2 += var0[var3].getSize();
      }

      return var1;
   }

   private static void emitDefaultValue(MethodVisitor var0, Type var1) {
      switch (var1.getSort()) {
         case 0:
            return;
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
            var0.visitInsn(3);
            return;
         case 6:
            var0.visitInsn(11);
            return;
         case 7:
            var0.visitInsn(9);
            return;
         case 8:
            var0.visitInsn(14);
            return;
         default:
            var0.visitInsn(1);
      }
   }

   private static void emitIntConstant(MethodVisitor var0, int var1) {
      if (var1 >= -1 && var1 <= 5) {
         var0.visitInsn(3 + var1);
      } else if (var1 >= -128 && var1 <= 127) {
         var0.visitIntInsn(16, var1);
      } else if (var1 >= -32768 && var1 <= 32767) {
         var0.visitIntInsn(17, var1);
      } else {
         var0.visitLdcInsn(var1);
      }
   }

   public static boolean OSIs(String var0, String var1, String var2, String var3, String var4) {
      return GVIslF(var0, var1, var2, var3, var4);
   }
}
