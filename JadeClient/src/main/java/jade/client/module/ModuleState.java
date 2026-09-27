// Jade recovery: original class: jade.deps.eLz.yZDI6UK
package jade.client.module;

import jade.client.common.DangerousModules;
import jade.client.common.EventBus;
import jade.client.common.ModuleManager;

public final class ModuleState {
   private final Module module;
   private final String moduleName;
   private ModuleManager manager;

   public ModuleState(Module var1, String var2) {
      this.module = var1;
      this.moduleName = var2;
   }

   public void attach(ModuleManager var1) {
      if (this.manager != null && this.manager != var1) {
         throw new IllegalStateException("Module is already attached to a manager: " + this.moduleName);
      } else {
         this.manager = var1;
      }
   }

   private void notifyManager(boolean var1) {
      if (this.manager != null) {
         if (var1) {
            this.manager.onModuleEnabled(this.module);
         } else {
            this.manager.onModuleDisabled(this.module);
         }
      }
   }

   private void notifyToggled(boolean var1) {
      if (this.manager != null) {
         this.manager.onModuleToggled(this.module, var1);
      }
   }

   public void enable() {
      if (this.module.canBeEnabled()) {
         if (!this.module.isEnabled()) {
            if (!DangerousModules.ZLqGjyW(this.module)) {
               this.module.setEnabled(true);
               this.notifyManager(true);
               boolean var1 = false;

               try {
                  if (!this.module.skipEventRegistration) {
                     EventBus.register(this.module);
                     var1 = true;
                  }

                  this.module.onEnable();
               } catch (Throwable var3) {
                  this.rollbackEnable(var1);
                  throw this.failure("enable", var3);
               }

               this.notifyToggled(true);
            }
         }
      }
   }

   private void rollbackEnable(boolean var1) {
      if (var1) {
         try {
            EventBus.unregister(this.module);
         } catch (Throwable var3) {
         }
      }

      this.module.setEnabled(false);
      this.notifyManager(false);
   }

   public void disable() {
      if (this.module.isEnabled()) {
         this.module.setEnabled(false);
         this.notifyManager(false);

         try {
            if (!this.module.skipEventRegistration) {
               EventBus.unregister(this.module);
            }

            this.module.onDisable();
         } catch (Throwable var2) {
            throw this.failure("disable", var2);
         }

         this.notifyToggled(false);
      }
   }

   private IllegalStateException failure(String var1, Throwable var2) {
      return new IllegalStateException("Module '" + this.moduleName + "' failed to " + var1, var2);
   }
}
