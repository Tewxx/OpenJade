// Jade recovery: original class: jade.deps.eLz.IWmaZDm
package jade.client.common;

import jade.client.Jade;
import jade.client.hook.MovementMathHelper;
import jade.client.module.Module;
import jade.client.module.client.Settings;
import jade.client.module.combat.AutoClicker;
import jade.client.module.combat.attributeswap.ItemScorer;
import jade.client.module.other.AntiBot;
import jade.client.module.player.Freecam;
import jade.client.module.render.FreeLook;
import jade.client.module.render.NoCameraClip;
import jade.client.setting.SliderSetting;
import jade.deps.gson.JsonObject;
import jade.deps.loader107.InjectionPaths;
import jade.mixin.impl.accessor.IAccessorMinecraft;
import jade.mixin.impl.accessor.IAccessorPlayerControllerMP;
import java.awt.Color;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFireball;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class ClientUtils implements IMinecraft {
   private static final Random RANDOM = new Random();
   private static final ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(ClientUtils::initialNestedUpdateDepth);
   public static HashSet<String> friends = new HashSet<>();
   public static HashSet<String> enemies = new HashSet<>();
   public static final Logger logger = LogManager.getLogger();

   public static boolean canRiderInteract(Entity var0) {
      return AttackHelper.invokeCanRiderInteract(var0);
   }

   public static boolean addEnemy(String var0) {
      return Jade.relationManager != null ? Jade.relationManager.addEnemy(var0) : RelationListHelper.BJi26(enemies, var0, true, "enemy", ClientUtils::sendEnemyAddedMessage, ClientUtils::afterEnemyAdded);
   }

   public static boolean removeEnemy(String var0) {
      return Jade.relationManager != null ? Jade.relationManager.RLDdY(var0) : RelationListHelper.BJi26(enemies, var0, false, "enemy", ClientUtils::sendEnemyRemovedMessage, ClientUtils::afterEnemyRemoved);
   }

   public static float getRenderYaw() {
      return Jade.getModuleManager().getModule(FreeLook.class) != null && Jade.getModuleManager().getModule(FreeLook.class).isEnabled() && FreeLook.DnH
         ? FreeLook.savedYaw
         : (float)Math.toDegrees(Math.atan2(ActiveRenderInfo.getRotationZ(), ActiveRenderInfo.getRotationX()));
   }

   public static float getRenderPitch() {
      return Jade.getModuleManager().getModule(FreeLook.class) != null && Jade.getModuleManager().getModule(FreeLook.class).isEnabled() && FreeLook.DnH
         ? FreeLook.savedPitch
         : (float)Math.toDegrees(Math.acos(ActiveRenderInfo.getRotationXZ()));
   }

   public static Vec3 getCameraLookVector(double var0) {
      NoCameraClip var2 = Jade.getModuleManager().getModule(NoCameraClip.class);
      return LookUtils.computeCameraPosition(mc, var0, getRenderYaw(), getRenderPitch(), var2 != null && var2.isEnabled());
   }

   public static void QBUrnV(EntityLivingBase var0) {
      TargetDebugInfo.dumpTargetDebugInfo(
         mc,
         var0,
         Jade.getModuleManager().getModule(AntiBot.class).isEnabled(),
         ClientUtils::sendColoredMessage,
         ClientUtils::Ujlt,
         ClientUtils::getEyeDistance,
         ClientUtils::getTargetHitDistance,
         ClientUtils::isTeammate,
         ClientUtils::isRealPlayer
      );
   }

   public static double getEntityHitDistanceSq(Entity var0, double var1, boolean var3) {
      float[] var4 = var3 ? RotationUtils.GURsr(var0) : new float[]{mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch};
      return RotationUtils.KooOx(var0, var1, var4[0], var4[1]);
   }

   public static double getEyeDistance(Entity var0) {
      return mc.thePlayer.getPositionEyes(1.0F).distanceTo(var0.getPositionEyes(1.0F));
   }

   public static boolean isRealPlayer(EntityPlayer var0) {
      return kaspqOdZw.pwB0(mc, var0);
   }

   public static String getPlayerName() {
      return mc.thePlayer.getName();
   }

   public static boolean vAxywR() {
      return GameStateUtils.isInGameWithFocus(mc);
   }

   public static boolean isEating(Entity var0) {
      return !(var0 instanceof EntityPlayer) ? false : ((EntityPlayer)var0).isUsingItem() && isHoldingFood((EntityPlayer)var0);
   }

   public static boolean isHoldingFood(EntityLivingBase var0) {
      return jWyVAePxit.isHoldingItemType(var0, ItemFood.class);
   }

   public static int oCoqd(Entity var0) {
      return TeamColors.getEntityTeamColor(var0);
   }

   public static boolean isAirColumnBelow(double var0, double var2, double var4) {
      return VerticalBlockScan.isColumnBlockedUpTo(var2, (recoveredArg0) -> ClientUtils.isAirAt(var0, var4, recoveredArg0));
   }

   public static Block UHFZ(String var0) {
      return (Block)Block.blockRegistry.getObject(new ResourceLocation("minecraft:" + var0));
   }

   public static boolean canSeeEntity(EntityLivingBase var0) {
      return LineOfSightUtils.canSeeEntity(mc, var0);
   }

   public static boolean isHoldingFireball() {
      return jWyVAePxit.isHoldingItemType(mc.thePlayer, ItemFireball.class);
   }

   public static boolean hasLineOfSight(Vec3 var0, Vec3 var1) {
      return LineOfSightUtils.isPathClear(mc, var0, var1);
   }

   public static List<NetworkPlayerInfo> ibtl3(boolean var0) {
      return kaspqOdZw.getUniquePlayerInfos(mc, var0);
   }

   public static void BKUuuL(ArrayList var0) {
      AQKBeof5.FPet(var0);
   }

   public static boolean removeFriend(String var0) {
      return Jade.relationManager != null ? Jade.relationManager.removeFriend(var0) : RelationListHelper.BJi26(friends, var0, false, "friend", ClientUtils::hCxpaA, ClientUtils::afterFriendRemoved);
   }

   public static String cWls() {
      String var0 = System.getProperty("java.io.tmpdir") + "amFkZV9zY3JpcHRz";
      if (System.getProperty("os.name").toLowerCase().contains("linux")) {
         File var1 = new File(new File(InjectionPaths.dataDirectory(mc.mcDataDir), "scripts"), "compiler_temp");
         return !var1.exists() && !var1.mkdirs() ? var0 : var1.getAbsolutePath();
      } else {
         return var0;
      }
   }

   public static boolean addFriend(String var0) {
      return Jade.relationManager != null ? Jade.relationManager.addFriend(var0) : RelationListHelper.BJi26(friends, var0, true, "friend", ClientUtils::BqvM, () -> ClientUtils.omrG(var0));
   }

   public static boolean OGQeRb(double var0) {
      return TextUtils.isWholeNumber(var0);
   }

   public static String formatNumberAsString(double var0) {
      return TextUtils.ASXEvc(var0);
   }

   public static int randomInt(int var0, int var1) {
      return TextUtils.randomIntInclusive(RANDOM, var0, var1);
   }

   public static double randomDouble(double var0, double var2) {
      return TextUtils.randomDoubleInRange(RANDOM, var0, var2);
   }

   public static boolean AggrRe(float var0, BlockPos var1) {
      return Bhr95(var0, var1.getX(), var1.getZ());
   }

   public static boolean isLookingAtEntity(float var0, Entity var1) {
      return dtpL7(var0, var1.posX, var1.posZ);
   }

   public static boolean dtpL7(float var0, double var1, double var3) {
      return isFacingCoordinates(mc.thePlayer, var0, var1, var3);
   }

   public static boolean isFacingCoordinates(Entity var0, float var1, double var2, double var4) {
      return FovUtils.isAngleWithinFov(var0.rotationYaw, var1, RotationUtils.ilaZ(var2, var4));
   }

   public static boolean Bhr95(float var0, float var1, float var2) {
      return FovUtils.isAngleWithinFov(var0, var1, var2);
   }

   public static Vec3 getLookVector(float var0, float var1) {
      return AngleUtils.getLookVector(var0, var1);
   }

   public static boolean isHoldingBow() {
      return jWyVAePxit.isHoldingItemType(mc.thePlayer, ItemBow.class);
   }

   public static boolean zhzO() {
      return isHoldingBow() && mc.thePlayer.moveStrafing == 0.0F && mc.thePlayer.moveForward <= 0.0F && uUwk();
   }

   public static boolean isAlwaysFalse() {
      return false;
   }

   public static void sendColoredMessage(String var0) {
      ChatSender.HARd(mc, var0, true);
   }

   public static boolean LCAP(String var0) {
      return ExternalChatOverlay.WBGA(var0);
   }

   public static String[] getThemeGradientColors() {
      return ChatSender.getGradientColors();
   }

   public static String formatGradientPrefix(String var0) {
      return ChatSender.formatGradientTitle(var0);
   }

   public static void sendJadeMessage(String var0, String var1) {
      ChatSender.daPdo(mc, var0, " " + var1);
   }

   public static void sendJadeMessageNoSpace(String var0, String var1) {
      ChatSender.daPdo(mc, var0, var1);
   }

   public static void ojsz9(String var0) {
      ChatSender.HARd(mc, var0, true);
   }

   public static void sendObjectMessage(Object var0) {
      String var1 = String.valueOf(var0);
      sendColoredMessage(var1);
   }

   public static void sendDebugMessage(String var0) {
      ChatSender.sendDebugMessage(mc, var0);
   }

   public static void aidx(Entity var0, boolean var1, boolean var2) {
      AttackHelper.performAttack(mc, var0, var1, var2);
   }

   public static void wvAt(String var0) {
      ChatSender.HARd(mc, var0, false);
   }

   public static float getTotalHealth(EntityLivingBase var0) {
      return var0.getHealth() + var0.getAbsorptionAmount();
   }

   public static String getHealthText(EntityLivingBase var0, boolean var1) {
      float var2 = getTotalHealth(var0);
      if (var1 && var0.isDead) {
         var2 = 0.0F;
      }

      return formatHealth(var0.getHealth() / var0.getMaxHealth(), var2);
   }

   public static boolean xusXfhC(KeyBinding var0) {
      return Wlo914.GKjTw(var0.getKeyCode(), Keyboard::isKeyDown, Mouse::isButtonDown);
   }

   public static int AHvb(Block var0) {
      return ToolUtils.XJUbym(mc.thePlayer.inventory, var0);
   }

   public static boolean isOnLadder(Entity var0) {
      return MovementChecks.isOnLadder(mc, var0);
   }

   public static float getToolDigSpeed(ItemStack var0, Block var1) {
      return ToolUtils.getEffectiveBlockSpeed(var0, var1);
   }

   public static boolean isEnemy(EntityPlayer var0) {
      return var0 != null && isEnemyName(var0.getName());
   }

   public static boolean isEnemyName(String var0) {
      return Jade.relationManager != null ? Jade.relationManager.mHh2(var0) : var0 != null && !enemies.isEmpty() && enemies.contains(var0.toLowerCase());
   }

   public static String formatHealth(double var0, double var2) {
      return TextUtils.formatHealthDisplay(var0, var2, Settings.showHealthAsHearts.isToggled(), Settings.showHeartSymbol.isToggled());
   }

   public static int asfu(double var0) {
      return TextUtils.getHealthColor(var0);
   }

   public static String translateColorCodes(String var0) {
      return TextUtils.QJdK(var0);
   }

   public static String getFirstColorCode(String var0) {
      return TextUtils.NTfs(var0);
   }

   public static int getVisibleTextLength(String var0) {
      return TextUtils.countBoldCharacters(var0);
   }

   public static void normalizeSliderRange(SliderSetting var0, SliderSetting var1) {
      AQKBeof5.SUej(var0, var1);
   }

   public static String randomString(int var0) {
      return TextUtils.randomAlphanumeric(RANDOM, var0);
   }

   public static boolean isFriend(EntityPlayer var0) {
      return var0 != null && isFriendName(var0.getName());
   }

   public static boolean isFriendName(String var0) {
      return Jade.relationManager != null ? Jade.relationManager.isFriend(var0) : var0 != null && !friends.isEmpty() && friends.contains(var0.toLowerCase());
   }

   public static double randomBetweenSliders(SliderSetting var0, SliderSetting var1, Random var2) {
      return AQKBeof5.randomBetweenSliders(var0, var1, var2);
   }

   public static boolean isInWorld() {
      return GameStateUtils.isInWorld(mc);
   }

   public static boolean isOnHypixel() {
      return GameStateUtils.isOnHypixel(mc);
   }

   public static String getHitsToKillText(EntityPlayer var0, ItemStack var1) {
      return HitsToKillCalculator.formatHitsToKill(var0, var1);
   }

   public static double getHitsToKill(EntityPlayer var0, ItemStack var1) {
      return HitsToKillCalculator.getHitsToKill(var0, var1);
   }

   public static float getMovementYaw() {
      return computeMovementYaw(mc.thePlayer.rotationYaw, mc.thePlayer.movementInput.moveForward, mc.thePlayer.movementInput.moveStrafe);
   }

   public static String OOFUl(String var0) {
      return TextUtils.extractMiddleUnderscoreSegment(var0);
   }

   public static int YVVZ(int var0, int var1) {
      return TextUtils.withAlpha(var0, var1);
   }

   public static int clampColorComponent(int var0) {
      return TextUtils.clampAlpha(var0);
   }

   public static boolean hasArrowAmmo(ItemStack var0) {
      boolean var1 = mc.thePlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, var0) > 0;
      return var1 || mc.thePlayer.inventory.hasItem(Items.arrow);
   }

   public static int dimColor(int var0, double var1) {
      return TextUtils.SxrM(var0, var1);
   }

   public static boolean isTeammate(Entity var0) {
      return TeamUtils.isTeammate(mc, var0);
   }

   public static String RcNd() {
      return TeamUtils.getOwnFormattedName(mc);
   }

   public static void ajmX(double var0) {
      MotionUtils.setMotionFromYaw(mc.thePlayer, var0, ClientUtils::getMovementYaw);
   }

   public static void eqyXnoq() {
      ((IAccessorMinecraft)mc).getTimer().timerSpeed = 1.0F;
   }

   public static void beginNestedUpdate() {
      threadLocal.set(threadLocal.get() + 1);
   }

   public static void EnMd() {
      int var0 = threadLocal.get() - 1;
      threadLocal.set(Math.max(0, var0));
   }

   public static boolean isInNestedUpdate() {
      return threadLocal.get() > 0;
   }

   public static boolean YDGRCC() {
      return GameStateUtils.isPlayerInventoryOpen(mc);
   }

   public static int getSkyWarsBoardType() {
      return ScoreboardUtils.getSkyWarsBoardType(mc);
   }

   public static String getJsonString(JsonObject var0, String var1) {
      return AQKBeof5.getJsonString(var0, var1);
   }

   public static int getBedWarsBoardType() {
      return ScoreboardUtils.getBedWarsBoardType(mc);
   }

   public static String zaUnpz(String var0) {
      return ScoreboardUtils.IwugSbg(var0);
   }

   public static List<String> PxSw4() {
      return ScoreboardUtils.dj854(mc);
   }

   public static Random getRandom() {
      return RANDOM;
   }

   public static boolean uUwk() {
      return mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F;
   }

   public static void aimAtEntity(Entity var0, float var1, boolean var2) {
      EntityAimHelper.lookAtEntity(mc, var0, var1, var2);
   }

   public static float[] NyEsnD(Entity var0) {
      return EntityAngles.getAnglesTo(mc.thePlayer, var0);
   }

   public static double getYawDifferenceToEntity(Entity var0, boolean var1) {
      float var2 = var1 ? RotationUtils.lastSentRotation[0] : mc.thePlayer.rotationYaw;
      return EntityAngles.wrapAngleDifference(var2, VrOe(var0));
   }

   public static double coPw(Entity var0, boolean var1) {
      float var2 = var1 ? RotationUtils.lastSentRotation[1] : mc.thePlayer.rotationPitch;
      return EntityAngles.wrapAngleDifference(var2, getPitchToEntity(var0));
   }

   public static float VrOe(Entity var0) {
      return EntityAngles.getYawTo(mc.thePlayer, var0);
   }

   public static float getPitchToEntity(Entity var0) {
      return EntityAngles.getPitchTo(mc.thePlayer, var0);
   }

   public static void setHeldSlot(int var0, boolean var1) {
      mc.thePlayer.inventory.currentItem = var0;
      if (var1) {
         ((IAccessorPlayerControllerMP)mc.playerController).callSyncCurrentPlayItem();
      }
   }

   public static MovingObjectPosition rayTraceBlocksWithRotations(double var0, float var2, float var3) {
      return RotationUtils.traceBlockHit(var0, var2, var3);
   }

   public static boolean canReachBlock(BlockPos var0, double var1) {
      float[] var3 = RotationUtils.anglesToBlockCenter(var0);
      AxisAlignedBB var4 = BlockUtils.iepjdt(var0).getCollisionBoundingBox(mc.theWorld, var0, BlockUtils.getBlockState(var0));
      return RotationUtils.doesBoxIntersectRay(var4, var1, var3[0], var3[1]);
   }

   public static void setMotionSpeed(double var0, boolean var2) {
      MotionUtils.setMotionFromYawIfEnabled(mc.thePlayer, var0, !var2 || uUwk(), ClientUtils::getMovementAngle);
   }

   public static boolean LLglN() {
      return GameStateUtils.isMovementKeyDown(mc);
   }

   public static boolean isJumpKeyDown() {
      return GameStateUtils.yrvB(mc);
   }

   public static double wHu5(Entity var0) {
      return VerticalBlockScan.measureDistanceToGround(var0.posY, var0.onGround, (recoveredArg0) -> ClientUtils.isPassableAtHeight(var0, recoveredArg0));
   }

   public static float getMovementAngle() {
      return (float)MovementFix.getMovementYaw(mc.thePlayer.rotationYaw, mc.thePlayer.moveForward, mc.thePlayer.moveStrafing);
   }

   public static float computeMovementYaw(float var0, float var1, float var2) {
      return (float)MovementFix.getMovementYaw(var0, var1, var2);
   }

   public static double getHorizontalSpeed() {
      return getEntityHorizontalSpeed(mc.thePlayer);
   }

   public static double getEntityHorizontalSpeed(Entity var0) {
      return MotionUtils.getHorizontalSpeed(var0);
   }

   public static List<String> getScriptStatements(String var0) {
      return VDCQaDdI09.jwaDz(var0);
   }

   public static boolean canEat(ItemStack var0) {
      return jWyVAePxit.Osqz7(var0, mc.thePlayer);
   }

   public static boolean isBlockAboveHead() {
      return MovementChecks.hasBlockAboveHead(mc.thePlayer);
   }

   public static boolean isPlayerOverVoid() {
      return hasNoGroundBelow(mc.thePlayer);
   }

   public static boolean hasNoGroundBelow(Entity var0) {
      return MovementChecks.isOverVoid(mc, var0);
   }

   public static boolean isLookingAtBlock() {
      return GameStateUtils.isLookingAtBlock(mc);
   }

   public static boolean isDiagonalMovement(boolean var0) {
      boolean var1 = MovementMathHelper.isYawOffAxis(mc.thePlayer.rotationYaw, var0);
      boolean var2 = Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode()) || Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode());
      return var1 || var2;
   }

   public static double getSpeedBlocksPerSecond(Entity var0, int var1) {
      double var2 = MotionUtils.GaGpp(var0);
      return var1 == 0 ? var2 : WXYd(var2, var1);
   }

   public static boolean isWithinRange(double var0, double var2, double var4) {
      return var4 >= var0 && var4 <= var2;
   }

   public static String stripObfuscatedCodes(String var0) {
      return TextUtils.UNOSfz(var0);
   }

   public static boolean isAttackKeyDown() {
      AutoClicker var0 = Jade.getModuleManager().getModule(AutoClicker.class);
      return GameStateUtils.isPrimaryClickHeld(var0 != null && var0.isEnabled());
   }

   public static boolean isMiningBlock() {
      return MiningTargetCheck.isMiningBlock(mc);
   }

   public static boolean isFeetInAir() {
      return MovementChecks.isAirAtFeet(mc, mc.thePlayer);
   }

   public static long LvhY(long var0, long var2) {
      return Math.abs(var2 - var0);
   }

   public static void sendModuleMessage(Module var0, String var1) {
      sendJadeMessage("Jade", "&f" + var0.getName() + "&7: " + var1);
   }

   public static EntityLivingBase getEntityInCrosshair(int var0) {
      Object var1 = Freecam.cameraEntity == null ? mc.thePlayer : Freecam.cameraEntity;
      return EntityRaycast.ZdwP(mc, (EntityPlayer)var1, var0, ClientUtils::canRiderInteract);
   }

   public static int AIowEv(long var0, long... var2) {
      long var3 = System.currentTimeMillis() + (var2.length > 0 ? var2[0] : 0L);
      return Color.getHSBColor((float)(var3 % (15000L / var0)) / (15000.0F / (float)var0), 1.0F, 1.0F).getRGB();
   }

   public static double WXYd(double var0, int var2) {
      if (var2 == 0) {
         return Math.round(var0);
      } else {
         double var3 = Math.pow(10.0, var2);
         return Math.round(var0 * var3) / var3;
      }
   }

   public static String AOAtn(String var0) {
      return var0.isEmpty() ? var0 : ScoreboardUtils.IwugSbg(var0);
   }

   public static List<String> getSidebarLines() {
      return ScoreboardLines.getSidebarLines(mc);
   }

   public static void startSwing() {
      AttackHelper.forceSwing(mc);
   }

   public static String capitalize(String var0) {
      return var0.substring(0, 1).toUpperCase() + var0.substring(1);
   }

   public static boolean isPassableAt(BlockPos var0) {
      return BlockUtils.isReplaceableAt(var0) || BlockUtils.isLiquid(BlockUtils.iepjdt(var0));
   }

   public static boolean isOnDeathScreen() {
      return GameStateUtils.isDeathStateOrReturnItem(mc);
   }

   public static boolean isHoldingWeapon() {
      return PIRHgte(mc.thePlayer);
   }

   public static boolean PIRHgte(EntityLivingBase var0) {
      return WeaponCheck.isHoldingEnabledWeapon(var0);
   }

   public static boolean CqWuiK() {
      return jWyVAePxit.isHoldingItemType(mc.thePlayer, ItemSword.class);
   }

   public static double getItemDamageScore(ItemStack var0) {
      return ItemScorer.getMeleeDamage(var0);
   }

   public static float computeInputMovementAngle() {
      return lgSi(mc.thePlayer.rotationYaw, mc.thePlayer.movementInput.moveForward, mc.thePlayer.movementInput.moveStrafe);
   }

   public static boolean isMovementInputActive() {
      return mc.thePlayer.movementInput.moveForward != 0.0F || mc.thePlayer.movementInput.moveStrafe != 0.0F;
   }

   public static float lgSi(float var0, float var1, float var2) {
      return MovementMathHelper.computeMoveDirectionRadians(var0, var1, var2);
   }

   public static boolean isPassableBlock(ItemBlock var0) {
      return PassableBlocks.isPassable(var0.getBlock());
   }

   public static <E extends Enum<E>> E findEnumByName(Class<E> var0, String var1) {
      return AQKBeof5.findEnumByName(var0, var1);
   }

   public static int getSpeedAmplifier() {
      return mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 1 + mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() : 0;
   }

   public static ItemStack resolveRenderedItemStack(ItemStack var0) {
      return var0;
   }

   public static boolean isSideCorrectionEnabled(boolean var0) {
      return false;
   }

   public static String readStreamText(InputStream var0) {
      return AQKBeof5.readStreamText(var0);
   }

   public static boolean isInLobby() {
      return isOnHypixel() && ScoreboardUtils.isInLobby(mc);
   }

   public static boolean mdeQ() {
      return isOnHypixel() && ScoreboardUtils.ATIk(mc);
   }

   public static Vec3 LHYz(double var0) {
      return kaspqOdZw.YOOxcA0(mc, var0);
   }

   private static boolean isPassableAtHeight(Entity var0, int var1) {
      return isPassableAt(new BlockPos(var0.posX, var1, var0.posZ));
   }

   private static void omrG(String var0) {
      if (enemies.contains(var0.toLowerCase())) {
         enemies.remove(var0.toLowerCase());
      }
   }

   private static void BqvM(String var0) {
      sendJadeMessage("Jade", var0);
   }

   private static void afterFriendRemoved() {
   }

   private static void hCxpaA(String var0) {
      sendJadeMessage("Jade", var0);
   }

   private static boolean isAirAt(double var0, double var2, int var4) {
      return mc.theWorld.getBlockState(new BlockPos(var0, var4, var2)).getBlock() instanceof BlockAir;
   }

   private static Double getTargetHitDistance(EntityLivingBase var0) {
      return getEntityHitDistanceSq(var0, 12.0, false);
   }

   private static Double Ujlt(Double var0) {
      return WXYd(var0, 2);
   }

   private static void afterEnemyRemoved() {
   }

   private static void sendEnemyRemovedMessage(String var0) {
      sendJadeMessage("Jade", var0);
   }

   private static void afterEnemyAdded() {
   }

   private static void sendEnemyAddedMessage(String var0) {
      sendJadeMessage("Jade", var0);
   }

   private static Integer initialNestedUpdateDepth() {
      return 0;
   }
}
