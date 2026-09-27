// Jade recovery: mixin target: net.minecraft.client.Minecraft
package net.jade.dev.agent.transformer;

import jade.client.hook.ModLifecycleInvoker;


import jade.deps.loader107.LocalCoreLoader;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.client.Minecraft", remap = false)
public class MixinMinecraftModBootstrap {
   private static boolean modsInitialized = false;
   private static final String[] Jeslq = new String[]{
         "net.minecraft.entity.Entity",
         "net.minecraft.client.entity.AbstractClientPlayer",
         "net.minecraft.client.gui.FontRenderer",
         "net.minecraft.client.entity.EntityPlayerSP",
         "net.minecraft.entity.EntityLivingBase",
         "net.minecraft.network.NetworkManager",
         "net.minecraft.network.NetworkManager$5",
         "net.minecraft.entity.player.EntityPlayer",
         "net.minecraft.client.renderer.entity.RendererLivingEntity",
         "net.minecraft.client.renderer.ActiveRenderInfo",
         "net.minecraft.client.renderer.EntityRenderer",
         "net.minecraft.util.MovementInputFromOptions",
         "net.minecraft.client.Minecraft",
         "net.minecraft.client.renderer.ItemRenderer",
         "net.minecraft.client.renderer.RenderGlobal",
         "net.minecraft.client.renderer.entity.layers.LayerArmorBase",
         "net.minecraft.world.World",
         "net.minecraft.client.gui.GuiChat",
         "net.minecraft.client.gui.GuiNewChat",
         "net.minecraft.client.gui.GuiPlayerTabOverlay",
         "net.minecraft.client.gui.inventory.GuiContainer",
         "net.minecraft.client.gui.GuiIngame",
         "net.minecraft.client.gui.GuiScreen",
         "net.minecraft.client.network.NetHandlerPlayClient",
         "net.minecraft.client.renderer.entity.RenderManager",
         "net.minecraft.client.multiplayer.PlayerControllerMP",
         "net.minecraft.block.Block",
         "net.minecraft.client.renderer.entity.RenderPlayer",
         "net.minecraft.item.ItemStack",
         "net.minecraft.entity.projectile.EntityArrow",
         "net.minecraft.client.gui.GuiScreenBook",
         "net.minecraft.client.gui.GuiTextField",
         "net.minecraft.item.ItemFood",
         "net.minecraft.network.play.server.S02PacketChat",
         "net.minecraft.network.play.server.S30PacketWindowItems",
         "net.minecraft.network.play.server.S14PacketEntity",
         "net.minecraft.network.play.client.C0DPacketCloseWindow",
         "net.minecraft.util.MouseHelper"
      };

   @Inject(method = "startGame", at = @At("RETURN"), remap = false, require = 0)
   private void onStartGame(CallbackInfo var1) {
      this.Nefz();
   }

   @Inject(method = "func_71384_a", at = @At("RETURN"), remap = false, require = 0)
   private void onStartGameSrg(CallbackInfo var1) {
      this.Nefz();
   }

   private void Nefz() {
      if (!modsInitialized) {
         modsInitialized = true;
         {
            try {
               ClassLoader var1 = this.getClass().getClassLoader();
               boolean var2 = isForgeEnvironment(var1);
               if (var2) {
                  LocalCoreLoader.ensureLocalPayloadPrepared();
               }

               LocalCoreLoader.bootLocalCore(var1);
               if (var2) {
                  IsPy(var1);
               }

               System.setProperty(
                  "jade.local.ichor.detached",
                  "true"
               );
            } catch (Throwable var14) {
               return;
            }

            String var15 = System.getProperty("lunar.agent.bootstrap.mods");
            if (!isBlank(var15)) {
               ClassLoader var16 = this.getClass().getClassLoader();
               ClassLoader var3 = ItUt(var16);
               File var4 = resolveModConfigDirectory();

               for (String var8 : var15.split(",")) {
                  String[] var9 = var8.split("\\|", -1);
                  if (var9.length >= 1 && !isBlank(var9[0])) {
                     String var10 = var9[0];
                     String var11 = var9.length > 1 ? var9[1] : "";

                     try {
                        rAj3(var10, var11, var3, var4);
                     } catch (Throwable var13) {
                     }
                  }
               }
            }
         }
      }
   }

   private static void IsPy(ClassLoader var0) throws ClassNotFoundException {
      if (isForgeEnvironment(var0)) {
         for (String var4 : Jeslq) {
            Class.forName(var4, false, var0);
         }
      }
   }

   private static boolean isForgeEnvironment(ClassLoader var0) {
      try {
         Class.forName(
            "net.minecraftforge.fml.relauncher.FMLLaunchHandler",
            false,
            var0
         );
         return true;
      } catch (ClassNotFoundException var2) {
         return false;
      }
   }

   private static ClassLoader ItUt(ClassLoader var0) {
      String var1 = System.getProperty("lunar.agent.bootstrap.jar.paths");
      if (isBlank(var1)) {
         return var0;
      } else {
         try {
            String[] var2 = var1.split("::");
            URL[] var3 = new URL[var2.length];

            for (int var4 = 0; var4 < var2.length; var4++) {
               var3[var4] = new File(var2[var4]).toURI().toURL();
            }

            return new URLClassLoader(var3, var0);
         } catch (Exception var5) {
            return var0;
         }
      }
   }

   private static boolean isBlank(String var0) {
      return var0 == null || var0.trim().isEmpty();
   }

   private static File resolveModConfigDirectory() {
      String var0 = System.getProperty("lunar.agent.bootstrap.jar.paths");
      if (!isBlank(var0)) {
         File var1 = new File(var0.split("::")[0]).getParentFile();
         if (var1 != null) {
            File var2 = new File(var1, "config");
            var2.mkdirs();
            return var2;
         }
      }

      File var3 = new File(
         System.getProperty("user.home"),
         ".lunarclient"
            + File.separator
            + "offline"
            + File.separator
            + "multiver"
            + File.separator
            + "config"
      );
      var3.mkdirs();
      return var3;
   }

   private static void rAj3(String var0, String var1, ClassLoader var2, File var3) throws Exception {
      if (isBlank(var1) || Boolean.getBoolean(var1)) {
         String var4 = var0.replace('/', '.');
         Class var5 = Class.forName(var4, true, var2);
         Object var6 = var5.getDeclaredConstructor().newInstance();
         ModLifecycleInvoker.invokeLifecycleMethods(var6, var3, var2);
      }
   }
}
