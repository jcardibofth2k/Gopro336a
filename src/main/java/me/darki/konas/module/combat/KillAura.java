package me.darki.konas.module.combat;

import cookiedragon.eventsystem.Subscriber;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import me.darki.konas.module.Category;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.unremaped.SendPacketEvent;
import me.darki.konas.unremaped.Class308;
import me.darki.konas.unremaped.Class311;
import me.darki.konas.unremaped.Class327;
import me.darki.konas.unremaped.Class330;
import me.darki.konas.module.misc.AntiBot;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.event.events.UpdateEvent;
import me.darki.konas.unremaped.Class473;
import me.darki.konas.unremaped.Class492;
import me.darki.konas.util.rotation.Rotation;
import me.darki.konas.unremaped.EspRenderUtil;
import me.darki.konas.setting.ParentSetting;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.unremaped.Class545;
import me.darki.konas.util.TimerUtil;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.unremaped.Render3DEvent;
import me.darki.konas.mixin.mixins.IEntityPlayerSP;
import me.darki.konas.mixin.mixins.IRenderManager;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.block.BlockAir;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class KillAura
extends Module {
    public static Setting<Float> range = new Setting<>("Range", 4.3f, 6.0f, 1.0f, 0.5f);
    
    public static Setting<ParentSetting> targeting = new Setting<>("Targeting", new ParentSetting(false));
    public static Setting<Boolean> animals = new Setting<>("Animals", false).setParentSetting(targeting);
    public static Setting<Boolean> mobs = new Setting<>("Mobs", true).setParentSetting(targeting);
    public static Setting<Boolean> bullets = new Setting<>("Bullets", false).setParentSetting(targeting);
    public static Setting<Boolean> players = new Setting<>("Players", true).setParentSetting(targeting);
    public static Setting<Boolean> attackFriends = new Setting<>("AttackFriends", false).setParentSetting(targeting);
    
    public static Setting<ParentSetting> antiCheat = new Setting<>("AntiCheat", new ParentSetting(false));
    public static Setting<Class330> timing = new Setting<>("Timing", Class330.SEQUENTIAL).setParentSetting(antiCheat);
    public static Setting<Class308> rotate = new Setting<>("Rotate", Class308.TRACK).setParentSetting(antiCheat);
    public static Setting<Float> wallsRange = new Setting<>("WallsRange", 3.0f, 6.0f, 0.5f, 0.5f).setParentSetting(antiCheat);
    public static Setting<Boolean> strict = new Setting<>("Strict", false).setParentSetting(antiCheat);
    public static Setting<Float> yawAngle = new Setting<>("YawAngle", 1.0f, 1.0f, 0.1f, 0.1f).setParentSetting(antiCheat);
    public static Setting<Class327> tPSSync = new Setting<>("TPSSync", Class327.NORMAL).setParentSetting(antiCheat);
    public static Setting<ParentSetting> speed = new Setting<>("Speed", new ParentSetting(false));
    public static Setting<Class311> mode = new Setting<>("Mode", Class311.DYNAMIC).setParentSetting(speed);
    public static Setting<Integer> tickDelay = new Setting<>("TickDelay", 12, 20, 0, 1).visibleIf(KillAura::Method519).setParentSetting(speed);
    
    public static Setting<ParentSetting> misc = new Setting<>("Misc", new ParentSetting(false));
    public static Setting<Boolean> autoSwitch = new Setting<>("AutoSwitch", true).setParentSetting(misc);
    public static Setting<Boolean> switchBack = new Setting<>("SwitchBack", false).setParentSetting(misc);
    public static Setting<Boolean> noGapSwitch = new Setting<>("NoGapSwitch", true).setParentSetting(misc);
    public static Setting<Boolean> autoBlock = new Setting<>("AutoBlock", false).setParentSetting(misc);
    public static Setting<Boolean> swordOnly = new Setting<>("SwordOnly", false).setParentSetting(misc);
    public static Setting<Boolean> onlyInHoles = new Setting<>("OnlyInHoles", false).setParentSetting(misc);
    public static Setting<Boolean> onlyWhenFalling = new Setting<>("OnlyWhenFalling", false).setParentSetting(misc);
    public static Setting<Boolean> onlyInAir = new Setting<>("OnlyInAir", false).setParentSetting(misc);
    public static Setting<Boolean> disableWhenCA = new Setting<>("DisableWhenCA", false).setParentSetting(misc);
    public static Setting<Boolean> onlyWhenNoTargets = new Setting<>("OnlyWhenNoTargets", true).visibleIf(disableWhenCA::getValue).setParentSetting(misc);
    public static Setting<Boolean> check32k = new Setting<>("Check32k", false).visibleIf(KillAura::Method393).setParentSetting(misc);
    
    public static Setting<ParentSetting> render = new Setting<>("Render", new ParentSetting(false));
    public static Setting<Boolean> targetRender = new Setting<>("TargetRender", true).setParentSetting(render);
    public static Setting<Boolean> onlyWhenHitting = new Setting<>("OnlyWhenHitting", true).setParentSetting(render);
    public static Setting<Boolean> depth = new Setting<>("Depth", true).setParentSetting(render);
    public static Setting<Boolean> fill = new Setting<>("Fill", false).setParentSetting(render);
    public static Setting<Boolean> orbit = new Setting<>("Orbit", true).setParentSetting(render);
    public static Setting<Boolean> trail = new Setting<>("Trail", true).setParentSetting(render);
    public static Setting<Float> orbitSpeed = new Setting<>("OrbitSpeed", 1.0f, 10.0f, 0.1f, 0.1f).setParentSetting(render);
    public static Setting<Float> animSpeed = new Setting<>("AnimSpeed", 1.0f, 10.0f, 0.1f, 0.1f).setParentSetting(render);
    public static Setting<Float> width = new Setting<>("Width", 2.5f, 5.0f, 0.1f, 0.1f).setParentSetting(render);
    public static Setting<ColorValue> color = new Setting<>("Color", new ColorValue(869950564, true)).setParentSetting(render);
    public int Field479 = 0;
    public static double Field480;
    public static double Field481;
    public long Field482 = 0L;
    public int Field483 = -1;
    public Entity Field484 = null;
    public TimerUtil Field485 = new TimerUtil();

    @Override
    public void onDisable() {
        if (KillAura.mc.player != null && switchBack.getValue().booleanValue() && this.Field483 != -1) {
            KillAura.mc.player.inventory.currentItem = this.Field483;
            KillAura.mc.player.connection.sendPacket(new CPacketHeldItemChange(this.Field483));
            this.Field483 = -1;
        }
    }

    public KillAura() {
        super("KillAura", 0, Category.COMBAT, "Aura", "SwordAura", "HitAura");
    }

    @Subscriber
    public void Method536(SendPacketEvent sendPacketEvent) {
        block2: {
            if (KillAura.mc.world == null || KillAura.mc.player == null) {
                return;
            }
            if (!(sendPacketEvent.getPacket() instanceof CPacketPlayer) || rotate.getValue() == Class308.NONE || this.Field484 == null || timing.getValue() != Class330.VANILLA) break block2;
            this.Method561(this.Field484);
            CPacketPlayer cPacketPlayer = (CPacketPlayer) sendPacketEvent.getPacket();
            if (sendPacketEvent.getPacket() instanceof CPacketPlayer.Position) {
                sendPacketEvent.setCanceled(true);
                KillAura.mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(cPacketPlayer.getX(KillAura.mc.player.posX), cPacketPlayer.getY(KillAura.mc.player.posY), cPacketPlayer.getZ(KillAura.mc.player.posZ), (float)Field480, (float)Field481, cPacketPlayer.isOnGround()));
            }
        }
    }

    public boolean Method395(Entity entity) {
        if (animals.getValue().booleanValue() && entity instanceof EntityAnimal) {
            return true;
        }
        if (mobs.getValue().booleanValue() && entity instanceof IMob) {
            return true;
        }
        if (players.getValue().booleanValue() && entity instanceof EntityPlayer) {
            if (!attackFriends.getValue().booleanValue()) {
                return !Class492.Method1989(entity.getName());
            }
            return true;
        }
        return false;
    }

    @Subscriber(priority=1)
    public void Method135(UpdateEvent updateEvent) {
        block15: {
            if (updateEvent.isCanceled() || !Rotation.Method1959(rotate.getValue() != Class308.NONE) || timing.getValue() == Class330.VANILLA) {
                return;
            }
            if (KillAura.mc.world == null || KillAura.mc.player == null) {
                return;
            }
            if (!this.Method388()) {
                return;
            }
            boolean bl = true;
            if (rotate.getValue() != Class308.NONE && this.Field484 != null) {
                float f;
                if (rotate.getValue() == Class308.HIT) {
                    f = 0.0f;
                    if (tPSSync.getValue() == Class327.NORMAL) {
                        f = 20.0f - Class473.Field2557.Method2193();
                    } else if (tPSSync.getValue() == Class327.MIN) {
                        f = 20.0f - Class473.Field2557.Method2192();
                    } else if (tPSSync.getValue() == Class327.LATEST) {
                        f = 20.0f - Class473.Field2557.Method2190();
                    }
                    if (mode.getValue() == Class311.STATIC && this.Field479 < tickDelay.getValue()) {
                        ++this.Field479;
                    }
                    float f2 = 1.0f;
                    if (this.Field484 != null && this.Field484 instanceof EntityShulkerBullet) {
                        f2 = 0.0f;
                    }
                    if (this.Field485.GetDifferenceTiming(5000.0) || Field480 == 0.0 || mode.getValue() == Class311.DYNAMIC && KillAura.mc.player.getCooledAttackStrength(tPSSync.getValue() != Class327.NONE ? -f : 0.0f) >= f2 || mode.getValue() == Class311.STATIC && this.Field479 >= tickDelay.getValue()) {
                        this.Method561(this.Field484);
                    }
                } else {
                    this.Method561(this.Field484);
                }
                if (yawAngle.getValue().floatValue() < 1.0f && Math.abs(f = (float)MathHelper.wrapDegrees(Field480 - (double)((IEntityPlayerSP)KillAura.mc.player).getLastReportedYaw())) > 180.0f * yawAngle.getValue().floatValue()) {
                    Field480 = ((IEntityPlayerSP)KillAura.mc.player).getLastReportedYaw() + f * (180.0f * yawAngle.getValue().floatValue() / Math.abs(f));
                    bl = false;
                }
                KonasGlobals.INSTANCE.Field1139.Method1937((float)Field480, (float)Field481);
            }
            if (!bl) break block15;
            this.Method557();
        }
    }

    public boolean Method556(Entity entity, float f) {
        if (entity == KillAura.mc.player || entity == mc.getRenderViewEntity()) {
            return false;
        }
        if (bullets.getValue().booleanValue() && entity instanceof EntityShulkerBullet && !entity.isDead && this.Method560(entity, f) && KillAura.Method384(entity)) {
            return true;
        }
        if (!(entity instanceof EntityLivingBase)) {
            return false;
        }
        if (!this.Method395(entity)) {
            return false;
        }
        if (entity.isDead) {
            return false;
        }
        if (((EntityLivingBase)entity).getHealth() <= 0.0f) {
            return false;
        }
        if (!this.Method560(entity, f)) {
            return false;
        }
        if (!KillAura.Method384(entity)) {
            return false;
        }
        return !AntiBot.Method1519().contains(entity);
    }

    public void Method557() {
        if (noGapSwitch.getValue().booleanValue() && KillAura.mc.player.getActiveItemStack().getItem() instanceof ItemFood) {
            return;
        }
        float f = 0.0f;
        if (tPSSync.getValue() == Class327.NORMAL) {
            f = 20.0f - Class473.Field2557.Method2193();
        } else if (tPSSync.getValue() == Class327.MIN) {
            f = 20.0f - Class473.Field2557.Method2192();
        } else if (tPSSync.getValue() == Class327.LATEST) {
            f = 20.0f - Class473.Field2557.Method2190();
        }
        if (mode.getValue() == Class311.STATIC && this.Field479 < tickDelay.getValue()) {
            ++this.Field479;
        }
        float f2 = 1.0f;
        if (this.Field484 != null && this.Field484 instanceof EntityShulkerBullet) {
            f2 = 0.0f;
        }
        if (mode.getValue() == Class311.DYNAMIC && KillAura.mc.player.getCooledAttackStrength(tPSSync.getValue() != Class327.NONE ? -f : 0.0f) >= f2 || mode.getValue() == Class311.STATIC && this.Field479 >= tickDelay.getValue()) {
            if (!this.Method556(this.Field484, range.getValue().floatValue())) {
                this.Field484 = null;
            }
        } else if (mode.getValue() == Class311.STATIC && this.Field479 < tickDelay.getValue()) {
            ++this.Field479;
        }
        if ((!onlyWhenFalling.getValue().booleanValue() || KillAura.mc.player.motionY < 0.0) && (!onlyInAir.getValue().booleanValue() || KillAura.mc.world.getBlockState(new BlockPos(KillAura.mc.player)).getBlock() instanceof BlockAir) && (mode.getValue() == Class311.DYNAMIC && KillAura.mc.player.getCooledAttackStrength(tPSSync.getValue() != Class327.NONE ? -f : 0.0f) >= f2 || mode.getValue() == Class311.STATIC && this.Field479 >= tickDelay.getValue())) {
            if (this.Field484 != null) {
                if (autoSwitch.getValue().booleanValue()) {
                    this.Method562(this.Method464());
                }
                boolean bl = KillAura.mc.player.isSneaking();
                boolean bl2 = strict.getValue() != false && KillAura.mc.player.isSprinting();
                boolean bl3 = KillAura.mc.player.isActiveItemStackBlocking();
                if (bl) {
                    KillAura.mc.player.connection.sendPacket(new CPacketEntityAction(KillAura.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                }
                if (bl2) {
                    KillAura.mc.player.connection.sendPacket(new CPacketEntityAction(KillAura.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
                }
                if (bl3 && KillAura.mc.player.getHeldItemOffhand().getItem() instanceof ItemShield) {
                    KillAura.mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(KillAura.mc.player), EnumFacing.getFacingFromVector((float)((int)KillAura.mc.player.posX), (float)((int)KillAura.mc.player.posY), (float)((int)KillAura.mc.player.posZ))));
                }
                KillAura.mc.playerController.attackEntity(KillAura.mc.player, this.Field484);
                KillAura.mc.player.swingArm(EnumHand.MAIN_HAND);
                this.Field485.UpdateCurrentTime();
                this.Field479 = 0;
                if (bl) {
                    KillAura.mc.player.connection.sendPacket(new CPacketEntityAction(KillAura.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                }
                if (bl2) {
                    KillAura.mc.player.connection.sendPacket(new CPacketEntityAction(KillAura.mc.player, CPacketEntityAction.Action.START_SPRINTING));
                }
                if (bl3 && KillAura.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword && KillAura.mc.player.getHeldItemOffhand().getItem() instanceof ItemShield) {
                    KillAura.mc.playerController.processRightClick(KillAura.mc.player, KillAura.mc.world, EnumHand.OFF_HAND);
                }
            } else if (switchBack.getValue().booleanValue() && this.Field483 != -1) {
                KillAura.mc.player.inventory.currentItem = this.Field483;
                KillAura.mc.player.connection.sendPacket(new CPacketHeldItemChange(this.Field483));
                this.Field483 = -1;
            }
        }
    }

    public static Float Method558(Entity entity) {
        return Float.valueOf(KillAura.mc.player.getDistance(entity));
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        if (timing.getValue() == Class330.SEQUENTIAL) {
            return;
        }
        if (KillAura.mc.player == null || KillAura.mc.world == null) {
            return;
        }
        if (!this.Method388()) {
            this.Field484 = null;
            return;
        }
        this.Method557();
    }

    public static boolean Method467(Vec3d vec3d) {
        Vec3d vec3d2 = new Vec3d(KillAura.mc.player.posX, KillAura.mc.player.getEntityBoundingBox().minY + (double)KillAura.mc.player.getEyeHeight(), KillAura.mc.player.posZ);
        return KillAura.mc.world.rayTraceBlocks(vec3d2, vec3d) == null || vec3d.distanceTo(KillAura.mc.player.getPositionEyes(1.0f)) <= (double) wallsRange.getValue().floatValue();
    }

    public boolean Method388() {
        if (disableWhenCA.getValue()) {
            final Module method1610 = ModuleManager.getModuleByClass(AutoCrystal.class);
            if (method1610 != null) {
                final AutoCrystal autoCrystal = (AutoCrystal)method1610;
                if (method1610.isEnabled()) {
                    if (!(boolean)onlyWhenNoTargets.getValue()) {
                        this.Field484 = null;
                        if ((boolean)switchBack.getValue() && this.Field483 != -1) {
                            mc.player.inventory.currentItem = this.Field483;
                            mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.Field483));
                            this.Field483 = -1;
                        }
                        return false;
                    }
                    if (autoCrystal.getBestBreak() != null || autoCrystal.getBestPlace() != null) {
                        this.Field484 = null;
                        if ((boolean)switchBack.getValue() && this.Field483 != -1) {
                            mc.player.inventory.currentItem = this.Field483;
                            mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.Field483));
                            this.Field483 = -1;
                        }
                        return false;
                    }
                }
            }
        }
        if (swordOnly.getValue()) {
            if (!(mc.player.getHeldItemMainhand().getItem() instanceof ItemSword)) {
                return false;
            }
            if (check32k.getValue() && EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, mc.player.getHeldItemMainhand()) < 6) {
                this.Field484 = null;
                if ((boolean)switchBack.getValue() && this.Field483 != -1) {
                    mc.player.inventory.currentItem = this.Field483;
                    mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.Field483));
                    this.Field483 = -1;
                }
                return false;
            }
        }
        if ((boolean)onlyInHoles.getValue() && !Class545.Method1009(new BlockPos((Entity)mc.player))) {
            this.Field484 = null;
            if (switchBack.getValue()) {
                if (this.Field483 != -1) {
                    mc.player.inventory.currentItem = this.Field483;
                    mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.Field483));
                    this.Field483 = -1;
                }
            }
            return false;
        }
        final List<Entity> list = mc.world.loadedEntityList.stream().filter(this::Method513).sorted(Comparator.comparing(KillAura::Method558)).collect(Collectors.toList());
        if (!list.isEmpty()) {
            if (this.Field484 == null || !this.Field484.equals(list.get(0))) {
                this.Field482 = System.currentTimeMillis();
            }
            this.Field484 = list.get(0);
        }
        else {
            this.Field484 = null;
        }
        if ((boolean)autoBlock.getValue() && this.Field484 != null && !mc.player.isActiveItemStackBlocking() && mc.player.getHeldItemOffhand().getItem() instanceof ItemShield) {
            mc.playerController.processRightClick((EntityPlayer)mc.player, mc.world, EnumHand.OFF_HAND);
        }
        return true;
    }

    public static void Method559(float f, float f2) {
        Field480 = f;
        Field481 = f2;
    }

    public int Method464() {
        int n = -1;
        if (KillAura.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_SWORD) {
            n = KillAura.mc.player.inventory.currentItem;
        }
        if (n == -1) {
            for (int i = 0; i < 9; ++i) {
                if (KillAura.mc.player.inventory.getStackInSlot(i).getItem() != Items.DIAMOND_SWORD) continue;
                n = i;
                break;
            }
        }
        return n;
    }

    public static boolean Method393() {
        return swordOnly.getValue();
    }

    public boolean Method560(Entity entity, float f) {
        AxisAlignedBB axisAlignedBB = entity.getEntityBoundingBox();
        for (double d = 0.15; d < 0.85; d += 0.1) {
            for (double d2 = 0.15; d2 < 0.85; d2 += 0.1) {
                for (double d3 = 0.15; d3 < 0.85; d3 += 0.1) {
                    Vec3d vec3d = new Vec3d(axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) * d, axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) * d2, axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) * d3);
                    if (!(KillAura.mc.player.getDistance(vec3d.x, vec3d.y, vec3d.z) < (double)f)) continue;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onEnable() {
        this.Field484 = null;
        this.Field483 = -1;
    }

    public boolean Method513(Entity entity) {
        return this.Method556(entity, range.getValue().floatValue());
    }

    public static boolean Method519() {
        return mode.getValue() == Class311.STATIC;
    }

    public void Method561(Entity entity) {
        block4: {
            double d;
            double d2;
            AxisAlignedBB axisAlignedBB = entity.getEntityBoundingBox();
            Vec3d vec3d = new Vec3d(KillAura.mc.player.posX, KillAura.mc.player.getEntityBoundingBox().minY + (double)KillAura.mc.player.getEyeHeight(), KillAura.mc.player.posZ);
            Vec3d vec3d2 = null;
            double[] dArray = null;
            for (d2 = 0.15; d2 < 0.85; d2 += 0.1) {
                for (d = 0.15; d < 0.85; d += 0.1) {
                    for (double d3 = 0.15; d3 < 0.85; d3 += 0.1) {
                        Vec3d vec3d3 = new Vec3d(axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) * d2, axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) * d, axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) * d3);
                        if (!KillAura.Method467(vec3d3)) continue;
                        double d4 = vec3d3.x - vec3d.x;
                        double d5 = vec3d3.y - vec3d.y;
                        double d6 = vec3d3.z - vec3d.z;
                        double[] dArray2 = new double[]{MathHelper.wrapDegrees((float)Math.toDegrees(Math.atan2(d6, d4)) - 90.0f), MathHelper.wrapDegrees((float)(-Math.toDegrees(Math.atan2(d5, Math.sqrt(d4 * d4 + d6 * d6)))))};
                        if (vec3d2 != null && dArray != null) {
                            if (!(Math.hypot(((dArray2[0] - (double)((IEntityPlayerSP)KillAura.mc.player).getLastReportedYaw()) % 360.0 + 540.0) % 360.0 - 180.0, dArray2[1] - (double)((IEntityPlayerSP)KillAura.mc.player).getLastReportedPitch()) < Math.hypot(((dArray[0] - (double)((IEntityPlayerSP)KillAura.mc.player).getLastReportedYaw()) % 360.0 + 540.0) % 360.0 - 180.0, dArray[1] - (double)((IEntityPlayerSP)KillAura.mc.player).getLastReportedPitch()))) continue;
                            vec3d2 = vec3d3;
                            dArray = dArray2;
                            continue;
                        }
                        vec3d2 = vec3d3;
                        dArray = dArray2;
                    }
                }
            }
            if (vec3d2 == null || dArray == null) break block4;
            d2 = ((dArray[0] - (double)((IEntityPlayerSP)KillAura.mc.player).getLastReportedYaw()) % 360.0 + 540.0) % 360.0 - 180.0;
            d = ((dArray[1] - (double)((IEntityPlayerSP)KillAura.mc.player).getLastReportedPitch()) % 360.0 + 540.0) % 360.0 - 180.0;
            double[] dArray3 = new double[]{(double)((IEntityPlayerSP)KillAura.mc.player).getLastReportedYaw() + (d2 > 180.0 ? 180.0 : Math.max(d2, -180.0)), (double)((IEntityPlayerSP)KillAura.mc.player).getLastReportedPitch() + (d > 180.0 ? 180.0 : Math.max(d, -180.0))};
            KillAura.Method559((float)dArray3[0], (float)dArray3[1]);
        }
    }

    public void Method562(int n) {
        block1: {
            if (KillAura.mc.player.inventory.currentItem == n || n == -1) break block1;
            if (switchBack.getValue().booleanValue()) {
                this.Field483 = KillAura.mc.player.inventory.currentItem;
            }
            KillAura.mc.player.inventory.currentItem = n;
            KillAura.mc.player.connection.sendPacket(new CPacketHeldItemChange(n));
        }
    }

    public static boolean Method384(Entity entity) {
        AxisAlignedBB axisAlignedBB = entity.getEntityBoundingBox();
        for (double d = 0.15; d < 0.85; d += 0.1) {
            for (double d2 = 0.15; d2 < 0.85; d2 += 0.1) {
                for (double d3 = 0.15; d3 < 0.85; d3 += 0.1) {
                    Vec3d vec3d = new Vec3d(axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) * d, axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) * d2, axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) * d3);
                    if (!KillAura.Method467(vec3d)) continue;
                    return true;
                }
            }
        }
        return false;
    }

    @Subscriber
    public void Method139(final Render3DEvent render3DEvent) {
        if (KillAura.mc.player == null || KillAura.mc.world == null) {
            return;
        }
        if (KillAura.targetRender.getValue() && this.Field484 != null && (!(boolean)KillAura.onlyWhenHitting.getValue() || !this.Field485.GetDifferenceTiming(3500.0))) {
            GlStateManager.pushMatrix();
            EspRenderUtil.Method1386();
            if (KillAura.depth.getValue()) {
                GlStateManager.enableDepth();
            }
            final IRenderManager renderManager = (IRenderManager)KillAura.mc.getRenderManager();
            final float[] rgBtoHSB = Color.RGBtoHSB((KillAura.color.getValue()).Method769(), (KillAura.color.getValue()).Method770(), (KillAura.color.getValue()).Method779(), null);
            float n2;
            final float n = n2 = System.currentTimeMillis() % 7200L / 7200.0f;
            int n3 = Color.getHSBColor(n2, rgBtoHSB[1], rgBtoHSB[2]).getRGB();
            final ArrayList<Vec3d> list = new ArrayList<Vec3d>();
            final double n4 = this.Field484.lastTickPosX + (this.Field484.posX - this.Field484.lastTickPosX) * render3DEvent.Method436() - renderManager.getRenderPosX();
            final double n5 = this.Field484.lastTickPosY + (this.Field484.posY - this.Field484.lastTickPosY) * render3DEvent.Method436() - renderManager.getRenderPosY();
            final double n6 = this.Field484.lastTickPosZ + (this.Field484.posZ - this.Field484.lastTickPosZ) * render3DEvent.Method436() - renderManager.getRenderPosZ();
            final double n7 = -Math.cos((System.currentTimeMillis() - this.Field482) / 1000.0 * (float)KillAura.animSpeed.getValue()) * (this.Field484.height / 2.0) + this.Field484.height / 2.0;
            GL11.glLineWidth((float)KillAura.width.getValue());
            GL11.glBegin(1);
            for (int i = 0; i <= 360; ++i) {
                list.add(new Vec3d(n4 + Math.sin(i * 3.141592653589793 / 180.0) * 0.5, n5 + n7 + 0.01, n6 + Math.cos(i * 3.141592653589793 / 180.0) * 0.5));
            }
            for (int j = 0; j < list.size() - 1; ++j) {
                final int n8 = n3 >> 16 & 0xFF;
                final int n9 = n3 >> 8 & 0xFF;
                final int n10 = n3 & 0xFF;
                final float n11 = KillAura.orbit.getValue() ? (KillAura.trail.getValue() ? ((float)Math.max(0.0, -0.3183098861837907 * Math.atan(Math.tan(3.141592653589793 * (j + 1.0f) / (float)list.size() + System.currentTimeMillis() / 1000.0 * (float)KillAura.orbitSpeed.getValue())))) : ((float)Math.max(0.0, Math.abs(Math.sin((j + 1.0f) / list.size() * 3.141592653589793 + System.currentTimeMillis() / 1000.0 * (float)KillAura.orbitSpeed.getValue())) * 2.0 - 1.0))) : (KillAura.fill.getValue() ? 1.0f : ((KillAura.color.getValue()).Method782() / 255.0f));
                if ((KillAura.color.getValue()).Method783()) {
                    GL11.glColor4f(n8 / 255.0f, n9 / 255.0f, n10 / 255.0f, n11);
                }
                else {
                    GL11.glColor4f((KillAura.color.getValue()).Method769() / 255.0f, (KillAura.color.getValue()).Method770() / 255.0f, (KillAura.color.getValue()).Method779() / 255.0f, n11);
                }
                GL11.glVertex3d(list.get(j).x, list.get(j).y, list.get(j).z);
                GL11.glVertex3d(list.get(j + 1).x, list.get(j + 1).y, list.get(j + 1).z);
                n2 += 0.0027777778f;
                n3 = Color.getHSBColor(n2, rgBtoHSB[1], rgBtoHSB[2]).getRGB();
            }
            GL11.glEnd();
            if (KillAura.fill.getValue()) {
                float h = n;
                GL11.glBegin(9);
                for (int k = 0; k < list.size() - 1; ++k) {
                    final int n12 = n3 >> 16 & 0xFF;
                    final int n13 = n3 >> 8 & 0xFF;
                    final int n14 = n3 & 0xFF;
                    if ((KillAura.color.getValue()).Method783()) {
                        GL11.glColor4f(n12 / 255.0f, n13 / 255.0f, n14 / 255.0f, (KillAura.color.getValue()).Method782() / 255.0f);
                    }
                    else {
                        GL11.glColor4f((KillAura.color.getValue()).Method769() / 255.0f, (KillAura.color.getValue()).Method770() / 255.0f, (KillAura.color.getValue()).Method779() / 255.0f, (KillAura.color.getValue()).Method782() / 255.0f);
                    }
                    GL11.glVertex3d(list.get(k).x, list.get(k).y, list.get(k).z);
                    GL11.glVertex3d(list.get(k + 1).x, list.get(k + 1).y, list.get(k + 1).z);
                    h += 0.0027777778f;
                    n3 = Color.getHSBColor(h, rgBtoHSB[1], rgBtoHSB[2]).getRGB();
                }
                GL11.glEnd();
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            EspRenderUtil.Method1385();
            GlStateManager.popMatrix();
        }
    }
}