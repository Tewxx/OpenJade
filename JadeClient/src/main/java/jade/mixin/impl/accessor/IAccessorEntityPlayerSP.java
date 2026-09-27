// Jade recovery: recovered class name: IAccessorEntityPlayerSP; mixin target: net.minecraft.client.entity.EntityPlayerSP
package jade.mixin.impl.accessor;

import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityPlayerSP.class)
public interface IAccessorEntityPlayerSP {
   @Accessor("lastReportedPosX")
   double getLastReportedPosX();

   @Accessor("lastReportedPosX")
   void setLastReportedPosX(double var1);

   @Accessor("lastReportedPosY")
   double getLastReportedPosY();

   @Accessor("lastReportedPosY")
   void setLastReportedPosY(double var1);

   @Accessor("lastReportedPosZ")
   double getLastReportedPosZ();

   @Accessor("lastReportedPosZ")
   void setLastReportedPosZ(double var1);

   @Accessor("lastReportedYaw")
   float getLastReportedYaw();

   @Accessor("lastReportedYaw")
   void setLastReportedYaw(float var1);

   @Accessor("lastReportedPitch")
   float getLastReportedPitch();

   @Accessor("lastReportedPitch")
   void setLastReportedPitch(float var1);

   @Accessor("positionUpdateTicks")
   int getPositionUpdateTicks();

   @Accessor("positionUpdateTicks")
   void setPositionUpdateTicks(int var1);

   @Accessor("serverSprintState")
   boolean getServerSprintState();

   @Accessor("serverSprintState")
   void setServerSprintState(boolean var1);

   @Accessor("serverSneakState")
   boolean getServerSneakState();

   @Accessor("serverSneakState")
   void setServerSneakState(boolean var1);

   @Accessor("sprintToggleTimer")
   int getSprintToggleTimer();

   @Accessor("sprintToggleTimer")
   void setSprintToggleTimer(int var1);

   @Accessor("horseJumpPowerCounter")
   int getHorseJumpPowerCounter();

   @Accessor("horseJumpPowerCounter")
   void setHorseJumpPowerCounter(int var1);

   @Accessor("horseJumpPower")
   float getHorseJumpPower();

   @Accessor("horseJumpPower")
   void setHorseJumpPower(float var1);

   @Invoker("isCurrentViewEntity")
   boolean invokeIsCurrentViewEntity();

   @Invoker("sendHorseJump")
   void invokeSendHorseJump();
}
