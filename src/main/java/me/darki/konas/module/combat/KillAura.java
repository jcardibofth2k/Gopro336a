package me.darki.konas.module.combat;

import cookiedragon.eventsystem.Subscriber;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import me.darki.konas.module.Category;
import me.darki.konas.unremaped.Class167;
import me.darki.konas.unremaped.Class24;
import me.darki.konas.unremaped.Class308;
import me.darki.konas.unremaped.Class311;
import me.darki.konas.unremaped.Class327;
import me.darki.konas.unremaped.Class330;
import me.darki.konas.module.misc.AntiBot;
import me.darki.konas.ColorValue;
import me.darki.konas.UpdateEvent;
import me.darki.konas.unremaped.Class473;
import me.darki.konas.unremaped.Class492;
import me.darki.konas.unremaped.Class496;
import me.darki.konas.unremaped.Class507;
import me.darki.konas.ParentSetting;
import me.darki.konas.module.client.NewGui;
import me.darki.konas.unremaped.Class545;
import me.darki.konas.unremaped.Class566;
import me.darki.konas.TickEvent;
import me.darki.konas.unremaped.Class89;
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
    public static Setting<Float> Field439 = new Setting<>("Range", Float.valueOf(4.3f), Float.valueOf(6.0f), Float.valueOf(1.0f), Float.valueOf(0.5f));
    public static Setting<ParentSetting> Field440 = new Setting<>("Targeting", new ParentSetting(false));
    public static Setting<Boolean> Field441 = new Setting<>("Animals", false).setParentSetting(Field440);
    public static Setting<Boolean> Field442 = new Setting<>("Mobs", true).setParentSetting(Field440);
    public static Setting<Boolean> Field443 = new Setting<>("Bullets", false).setParentSetting(Field440);
    public static Setting<Boolean> Field444 = new Setting<>("Players", true).setParentSetting(Field440);
    public static Setting<Boolean> Field445 = new Setting<>("AttackFriends", false).setParentSetting(Field440);
    public static Setting<ParentSetting> Field446 = new Setting<>("AntiCheat", new ParentSetting(false));
    public static Setting<Class330> Field447 = new Setting<>("Timing", Class330.SEQUENTIAL).setParentSetting(Field446);
    public static Setting<Class308> Field448 = new Setting<>("Rotate", Class308.TRACK).setParentSetting(Field446);
    public static Setting<Float> Field449 = new Setting<>("WallsRange", Float.valueOf(3.0f), Float.valueOf(6.0f), Float.valueOf(0.5f), Float.valueOf(0.5f)).setParentSetting(Field446);
    public static Setting<Boolean> Field450 = new Setting<>("Strict", false).setParentSetting(Field446);
    public static Setting<Float> Field451 = new Setting<>("YawAngle", Float.valueOf(1.0f), Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).setParentSetting(Field446);
    public static Setting<Class327> Field452 = new Setting<>("TPSSync", Class327.NORMAL).setParentSetting(Field446);
    public static Setting<ParentSetting> Field453 = new Setting<>("Speed", new ParentSetting(false));
    public static Setting<Class311> Field454 = new Setting<>("Mode", Class311.DYNAMIC).setParentSetting(Field453);
    public static Setting<Integer> Field455 = new Setting<>("TickDelay", 12, 20, 0, 1).Method1191(KillAura::Method519).setParentSetting(Field453);
    public static Setting<ParentSetting> Field456 = new Setting<>("Misc", new ParentSetting(false));
    public static Setting<Boolean> Field457 = new Setting<>("AutoSwitch", true).setParentSetting(Field456);
    public static Setting<Boolean> Field458 = new Setting<>("SwitchBack", false).setParentSetting(Field456);
    public static Setting<Boolean> Field459 = new Setting<>("NoGapSwitch", true).setParentSetting(Field456);
    public static Setting<Boolean> Field460 = new Setting<>("AutoBlock", false).setParentSetting(Field456);
    public static Setting<Boolean> Field461 = new Setting<>("SwordOnly", false).setParentSetting(Field456);
    public static Setting<Boolean> Field462 = new Setting<>("OnlyInHoles", false).setParentSetting(Field456);
    public static Setting<Boolean> Field463 = new Setting<>("OnlyWhenFalling", false).setParentSetting(Field456);
    public static Setting<Boolean> Field464 = new Setting<>("OnlyInAir", false).setParentSetting(Field456);
    public static Setting<Boolean> Field465 = new Setting<>("DisableWhenCA", false).setParentSetting(Field456);
    public static Setting<Boolean> Field466 = new Setting<>("OnlyWhenNoTargets", true).Method1191(Field465::getValue).setParentSetting(Field456);
    public static Setting<Boolean> Field467 = new Setting<>("Check32k", false).Method1191(KillAura::Method393).setParentSetting(Field456);
    public static Setting<ParentSetting> Field468 = new Setting<>("Render", new ParentSetting(false));
    public static Setting<Boolean> Field469 = new Setting<>("TargetRender", true).setParentSetting(Field468);
    public static Setting<Boolean> Field470 = new Setting<>("OnlyWhenHitting", true).setParentSetting(Field468);
    public static Setting<Boolean> Field471 = new Setting<>("Depth", true).setParentSetting(Field468);
    public static Setting<Boolean> Field472 = new Setting<>("Fill", false).setParentSetting(Field468);
    public static Setting<Boolean> Field473 = new Setting<>("Orbit", true).setParentSetting(Field468);
    public static Setting<Boolean> Field474 = new Setting<>("Trail", true).setParentSetting(Field468);
    public static Setting<Float> Field475 = new Setting<>("OrbitSpeed", Float.valueOf(1.0f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).setParentSetting(Field468);
    public static Setting<Float> Field476 = new Setting<>("AnimSpeed", Float.valueOf(1.0f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).setParentSetting(Field468);
    public static Setting<Float> Field477 = new Setting<>("Width", Float.valueOf(2.5f), Float.valueOf(5.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).setParentSetting(Field468);
    public static Setting<ColorValue> Field478 = new Setting<>("Color", new ColorValue(869950564, true)).setParentSetting(Field468);
    public int Field479 = 0;
    public static double Field480;
    public static double Field481;
    public long Field482 = 0L;
    public int Field483 = -1;
    public Entity Field484 = null;
    public Class566 Field485 = new Class566();

    @Override
    public void onDisable() {
        if (KillAura.mc.player != null && Field458.getValue().booleanValue() && this.Field483 != -1) {
            KillAura.mc.player.inventory.currentItem = this.Field483;
            KillAura.mc.player.connection.sendPacket(new CPacketHeldItemChange(this.Field483));
            this.Field483 = -1;
        }
    }

    public KillAura() {
        super("KillAura", 0, Category.COMBAT, "Aura", "SwordAura", "HitAura");
    }

    @Subscriber
    public void Method536(Class24 class24) {
        block2: {
            if (KillAura.mc.world == null || KillAura.mc.player == null) {
                return;
            }
            if (!(class24.getPacket() instanceof CPacketPlayer) || Field448.getValue() == Class308.NONE || this.Field484 == null || Field447.getValue() != Class330.VANILLA) break block2;
            this.Method561(this.Field484);
            CPacketPlayer cPacketPlayer = (CPacketPlayer)class24.getPacket();
            if (class24.getPacket() instanceof CPacketPlayer.Position) {
                class24.setCanceled(true);
                KillAura.mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(cPacketPlayer.getX(KillAura.mc.player.posX), cPacketPlayer.getY(KillAura.mc.player.posY), cPacketPlayer.getZ(KillAura.mc.player.posZ), (float)Field480, (float)Field481, cPacketPlayer.isOnGround()));
            }
        }
    }

    public boolean Method395(Entity entity) {
        if (Field441.getValue().booleanValue() && entity instanceof EntityAnimal) {
            return true;
        }
        if (Field442.getValue().booleanValue() && entity instanceof IMob) {
            return true;
        }
        if (Field444.getValue().booleanValue() && entity instanceof EntityPlayer) {
            if (!Field445.getValue().booleanValue()) {
                return !Class492.Method1989(entity.getName());
            }
            return true;
        }
        return false;
    }

    @Subscriber(priority=1)
    public void Method135(UpdateEvent updateEvent) {
        block15: {
            if (updateEvent.isCanceled() || !Class496.Method1959(Field448.getValue() != Class308.NONE) || Field447.getValue() == Class330.VANILLA) {
                return;
            }
            if (KillAura.mc.world == null || KillAura.mc.player == null) {
                return;
            }
            if (!this.Method388()) {
                return;
            }
            boolean bl = true;
            if (Field448.getValue() != Class308.NONE && this.Field484 != null) {
                float f;
                if (Field448.getValue() == Class308.HIT) {
                    f = 0.0f;
                    if (Field452.getValue() == Class327.NORMAL) {
                        f = 20.0f - Class473.Field2557.Method2193();
                    } else if (Field452.getValue() == Class327.MIN) {
                        f = 20.0f - Class473.Field2557.Method2192();
                    } else if (Field452.getValue() == Class327.LATEST) {
                        f = 20.0f - Class473.Field2557.Method2190();
                    }
                    if (Field454.getValue() == Class311.STATIC && this.Field479 < Field455.getValue()) {
                        ++this.Field479;
                    }
                    float f2 = 1.0f;
                    if (this.Field484 != null && this.Field484 instanceof EntityShulkerBullet) {
                        f2 = 0.0f;
                    }
                    if (this.Field485.Method737(5000.0) || Field480 == 0.0 || Field454.getValue() == Class311.DYNAMIC && KillAura.mc.player.getCooledAttackStrength(Field452.getValue() != Class327.NONE ? -f : 0.0f) >= f2 || Field454.getValue() == Class311.STATIC && this.Field479 >= Field455.getValue()) {
                        this.Method561(this.Field484);
                    }
                } else {
                    this.Method561(this.Field484);
                }
                if (Field451.getValue().floatValue() < 1.0f && Math.abs(f = (float)MathHelper.wrapDegrees(Field480 - (double)((IEntityPlayerSP)KillAura.mc.player).Method238())) > 180.0f * Field451.getValue().floatValue()) {
                    Field480 = ((IEntityPlayerSP)KillAura.mc.player).Method238() + f * (180.0f * Field451.getValue().floatValue() / Math.abs(f));
                    bl = false;
                }
                NewGui.INSTANCE.Field1139.Method1937((float)Field480, (float)Field481);
            }
            if (!bl) break block15;
            this.Method557();
        }
    }

    public boolean Method556(Entity entity, float f) {
        if (entity == KillAura.mc.player || entity == mc.getRenderViewEntity()) {
            return false;
        }
        if (Field443.getValue().booleanValue() && entity instanceof EntityShulkerBullet && !entity.isDead && this.Method560(entity, f) && KillAura.Method384(entity)) {
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
        if (Field459.getValue().booleanValue() && KillAura.mc.player.getActiveItemStack().getItem() instanceof ItemFood) {
            return;
        }
        float f = 0.0f;
        if (Field452.getValue() == Class327.NORMAL) {
            f = 20.0f - Class473.Field2557.Method2193();
        } else if (Field452.getValue() == Class327.MIN) {
            f = 20.0f - Class473.Field2557.Method2192();
        } else if (Field452.getValue() == Class327.LATEST) {
            f = 20.0f - Class473.Field2557.Method2190();
        }
        if (Field454.getValue() == Class311.STATIC && this.Field479 < Field455.getValue()) {
            ++this.Field479;
        }
        float f2 = 1.0f;
        if (this.Field484 != null && this.Field484 instanceof EntityShulkerBullet) {
            f2 = 0.0f;
        }
        if (Field454.getValue() == Class311.DYNAMIC && KillAura.mc.player.getCooledAttackStrength(Field452.getValue() != Class327.NONE ? -f : 0.0f) >= f2 || Field454.getValue() == Class311.STATIC && this.Field479 >= Field455.getValue()) {
            if (!this.Method556(this.Field484, Field439.getValue().floatValue())) {
                this.Field484 = null;
            }
        } else if (Field454.getValue() == Class311.STATIC && this.Field479 < Field455.getValue()) {
            ++this.Field479;
        }
        if ((!Field463.getValue().booleanValue() || KillAura.mc.player.motionY < 0.0) && (!Field464.getValue().booleanValue() || KillAura.mc.world.getBlockState(new BlockPos(KillAura.mc.player)).getBlock() instanceof BlockAir) && (Field454.getValue() == Class311.DYNAMIC && KillAura.mc.player.getCooledAttackStrength(Field452.getValue() != Class327.NONE ? -f : 0.0f) >= f2 || Field454.getValue() == Class311.STATIC && this.Field479 >= Field455.getValue())) {
            if (this.Field484 != null) {
                if (Field457.getValue().booleanValue()) {
                    this.Method562(this.Method464());
                }
                boolean bl = KillAura.mc.player.isSneaking();
                boolean bl2 = Field450.getValue() != false && KillAura.mc.player.isSprinting();
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
                this.Field485.Method739();
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
            } else if (Field458.getValue().booleanValue() && this.Field483 != -1) {
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
        if (Field447.getValue() == Class330.SEQUENTIAL) {
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
        return KillAura.mc.world.rayTraceBlocks(vec3d2, vec3d) == null || vec3d.distanceTo(KillAura.mc.player.getPositionEyes(1.0f)) <= (double) Field449.getValue().floatValue();
    }

    public boolean Method388() {
        List list;
        if (Field465.getValue().booleanValue() && (list = Class167.Method1610(AutoCrystal.class)) != null) {
            AutoCrystal autoCrystal = (AutoCrystal) list;
            if (((Module) list).isEnabled()) {
                if (Field466.getValue().booleanValue()) {
                    if (autoCrystal.Method1554() != null || autoCrystal.Method1573() != null) {
                        this.Field484 = null;
                        if (Field458.getValue().booleanValue() && this.Field483 != -1) {
                            KillAura.mc.player.inventory.currentItem = this.Field483;
                            KillAura.mc.player.connection.sendPacket(new CPacketHeldItemChange(this.Field483));
                            this.Field483 = -1;
                        }
                        return false;
                    }
                } else {
                    this.Field484 = null;
                    if (Field458.getValue().booleanValue() && this.Field483 != -1) {
                        KillAura.mc.player.inventory.currentItem = this.Field483;
                        KillAura.mc.player.connection.sendPacket(new CPacketHeldItemChange(this.Field483));
                        this.Field483 = -1;
                    }
                    return false;
                }
            }
        }
        if (Field461.getValue().booleanValue()) {
            if (!(KillAura.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword)) {
                return false;
            }
            if (Field467.getValue().booleanValue() && EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, KillAura.mc.player.getHeldItemMainhand()) < 6) {
                this.Field484 = null;
                if (Field458.getValue().booleanValue() && this.Field483 != -1) {
                    KillAura.mc.player.inventory.currentItem = this.Field483;
                    KillAura.mc.player.connection.sendPacket(new CPacketHeldItemChange(this.Field483));
                    this.Field483 = -1;
                }
                return false;
            }
        }
        if (Field462.getValue().booleanValue() && !Class545.Method1009((BlockPos)(list = new BlockPos(KillAura.mc.player)))) {
            this.Field484 = null;
            if (Field458.getValue().booleanValue() && this.Field483 != -1) {
                KillAura.mc.player.inventory.currentItem = this.Field483;
                KillAura.mc.player.connection.sendPacket(new CPacketHeldItemChange(this.Field483));
                this.Field483 = -1;
            }
            return false;
        }
        list = KillAura.mc.world.loadedEntityList.stream().filter(this::Method513).sorted(Comparator.comparing(KillAura::Method558)).collect(Collectors.toList());
        if (!list.isEmpty()) {
            if (this.Field484 == null || !this.Field484.equals(list.get(0))) {
                this.Field482 = System.currentTimeMillis();
            }
            this.Field484 = (Entity)list.get(0);
        } else {
            this.Field484 = null;
        }
        if (Field460.getValue().booleanValue() && this.Field484 != null && !KillAura.mc.player.isActiveItemStackBlocking() && KillAura.mc.player.getHeldItemOffhand().getItem() instanceof ItemShield) {
            KillAura.mc.playerController.processRightClick(KillAura.mc.player, KillAura.mc.world, EnumHand.OFF_HAND);
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
        return Field461.getValue();
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
        return this.Method556(entity, Field439.getValue().floatValue());
    }

    public static boolean Method519() {
        return Field454.getValue() == Class311.STATIC;
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
                            if (!(Math.hypot(((dArray2[0] - (double)((IEntityPlayerSP)KillAura.mc.player).Method238()) % 360.0 + 540.0) % 360.0 - 180.0, dArray2[1] - (double)((IEntityPlayerSP)KillAura.mc.player).Method240()) < Math.hypot(((dArray[0] - (double)((IEntityPlayerSP)KillAura.mc.player).Method238()) % 360.0 + 540.0) % 360.0 - 180.0, dArray[1] - (double)((IEntityPlayerSP)KillAura.mc.player).Method240()))) continue;
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
            d2 = ((dArray[0] - (double)((IEntityPlayerSP)KillAura.mc.player).Method238()) % 360.0 + 540.0) % 360.0 - 180.0;
            d = ((dArray[1] - (double)((IEntityPlayerSP)KillAura.mc.player).Method240()) % 360.0 + 540.0) % 360.0 - 180.0;
            double[] dArray3 = new double[]{(double)((IEntityPlayerSP)KillAura.mc.player).Method238() + (d2 > 180.0 ? 180.0 : Math.max(d2, -180.0)), (double)((IEntityPlayerSP)KillAura.mc.player).Method240() + (d > 180.0 ? 180.0 : Math.max(d, -180.0))};
            KillAura.Method559((float)dArray3[0], (float)dArray3[1]);
        }
    }

    public void Method562(int n) {
        block1: {
            if (KillAura.mc.player.inventory.currentItem == n || n == -1) break block1;
            if (Field458.getValue().booleanValue()) {
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
    public void Method139(Class89 class89) {
        block10: {
            int n;
            int n2;
            int n3;
            float f;
            if (KillAura.mc.player == null || KillAura.mc.world == null) {
                return;
            }
            if (!Field469.getValue().booleanValue() || this.Field484 == null || Field470.getValue().booleanValue() && this.Field485.Method737(3500.0)) break block10;
            GlStateManager.pushMatrix();
            Class507.Method1386();
            if (Field471.getValue().booleanValue()) {
                GlStateManager.enableDepth();
            }
            IRenderManager iRenderManager = (IRenderManager)mc.getRenderManager();
            float[] fArray = Color.RGBtoHSB(Field478.getValue().Method769(), Field478.getValue().Method770(), Field478.getValue().Method779(), null);
            float f2 = f = (float)(System.currentTimeMillis() % 7200L) / 7200.0f;
            int n4 = Color.getHSBColor(f2, fArray[1], fArray[2]).getRGB();
            ArrayList<Vec3d> arrayList = new ArrayList<Vec3d>();
            double d = this.Field484.lastTickPosX + (this.Field484.posX - this.Field484.lastTickPosX) * (double)class89.Method436() - iRenderManager.Method69();
            double d2 = this.Field484.lastTickPosY + (this.Field484.posY - this.Field484.lastTickPosY) * (double)class89.Method436() - iRenderManager.Method70();
            double d3 = this.Field484.lastTickPosZ + (this.Field484.posZ - this.Field484.lastTickPosZ) * (double)class89.Method436() - iRenderManager.Method71();
            double d4 = -Math.cos((double)(System.currentTimeMillis() - this.Field482) / 1000.0 * (double) Field476.getValue().floatValue()) * ((double)this.Field484.height / 2.0) + (double)this.Field484.height / 2.0;
            GL11.glLineWidth(Field477.getValue().floatValue());
            GL11.glBegin(1);
            for (n3 = 0; n3 <= 360; ++n3) {
                Vec3d vec3d = new Vec3d(d + Math.sin((double)n3 * Math.PI / 180.0) * 0.5, d2 + d4 + 0.01, d3 + Math.cos((double)n3 * Math.PI / 180.0) * 0.5);
                arrayList.add(vec3d);
            }
            for (n3 = 0; n3 < arrayList.size() - 1; ++n3) {
                float f3;
                int n5 = n4 >> 16 & 0xFF;
                n2 = n4 >> 8 & 0xFF;
                n = n4 & 0xFF;
                float f4 = Field473.getValue().booleanValue() ? (Field474.getValue().booleanValue() ? (float)Math.max(0.0, -0.3183098861837907 * Math.atan(Math.tan(Math.PI * (double)((float)n3 + 1.0f) / (double)arrayList.size() + (double)System.currentTimeMillis() / 1000.0 * (double) Field475.getValue().floatValue()))) : (float)Math.max(0.0, Math.abs(Math.sin((double)(((float)n3 + 1.0f) / (float)arrayList.size()) * Math.PI + (double)System.currentTimeMillis() / 1000.0 * (double) Field475.getValue().floatValue())) * 2.0 - 1.0)) : (f3 = Field472.getValue() != false ? 1.0f : (float) Field478.getValue().Method782() / 255.0f);
                if (Field478.getValue().Method783()) {
                    GL11.glColor4f((float)n5 / 255.0f, (float)n2 / 255.0f, (float)n / 255.0f, f3);
                } else {
                    GL11.glColor4f((float) Field478.getValue().Method769() / 255.0f, (float) Field478.getValue().Method770() / 255.0f, (float) Field478.getValue().Method779() / 255.0f, f3);
                }
                GL11.glVertex3d(arrayList.get(n3).x, arrayList.get(n3).y, arrayList.get(n3).z);
                GL11.glVertex3d(arrayList.get(n3 + 1).x, arrayList.get(n3 + 1).y, arrayList.get(n3 + 1).z);
                n4 = Color.getHSBColor(f2 += 0.0027777778f, fArray[1], fArray[2]).getRGB();
            }
            GL11.glEnd();
            if (Field472.getValue().booleanValue()) {
                f2 = f;
                GL11.glBegin(9);
                for (n3 = 0; n3 < arrayList.size() - 1; ++n3) {
                    int n6 = n4 >> 16 & 0xFF;
                    n2 = n4 >> 8 & 0xFF;
                    n = n4 & 0xFF;
                    if (Field478.getValue().Method783()) {
                        GL11.glColor4f((float)n6 / 255.0f, (float)n2 / 255.0f, (float)n / 255.0f, (float) Field478.getValue().Method782() / 255.0f);
                    } else {
                        GL11.glColor4f((float) Field478.getValue().Method769() / 255.0f, (float) Field478.getValue().Method770() / 255.0f, (float) Field478.getValue().Method779() / 255.0f, (float) Field478.getValue().Method782() / 255.0f);
                    }
                    GL11.glVertex3d(arrayList.get(n3).x, arrayList.get(n3).y, arrayList.get(n3).z);
                    GL11.glVertex3d(arrayList.get(n3 + 1).x, arrayList.get(n3 + 1).y, arrayList.get(n3 + 1).z);
                    n4 = Color.getHSBColor(f2 += 0.0027777778f, fArray[1], fArray[2]).getRGB();
                }
                GL11.glEnd();
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            Class507.Method1385();
            GlStateManager.popMatrix();
        }
    }
}
