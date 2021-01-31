package me.darki.konas.module.movement;

import cookiedragon.eventsystem.Subscriber;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import me.darki.konas.module.Category;
import me.darki.konas.Class167;
import me.darki.konas.PacketEvent;
import me.darki.konas.Class19;
import me.darki.konas.Class24;
import me.darki.konas.Class25;
import me.darki.konas.StrafeMode;
import me.darki.konas.Class347;
import me.darki.konas.UpdateEvent;
import me.darki.konas.ParentSetting;
import me.darki.konas.module.client.NewGui;
import me.darki.konas.Class537;
import me.darki.konas.MathUtil;
import me.darki.konas.Class566;
import me.darki.konas.MoveEvent;
import me.darki.konas.TickEvent;
import me.darki.konas.mixin.mixins.ICPacketPlayer;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.entity.Entity;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class Speed
extends Module {
    public Setting<Double> Field391 = new Setting<>("Speed", 1.0, 10.0, 0.0, 0.1);
    public Setting<Boolean> Field392 = new Setting<>("UseTimer", true);
    public Setting<Float> Field393 = new Setting<>("Factor", Float.valueOf(1.0f), Float.valueOf(10.0f), Float.valueOf(0.0f), Float.valueOf(0.1f)).Method1191(this.Field392::getValue);
    public Setting<StrafeMode> Field394 = new Setting<>("Mode", StrafeMode.STRAFE);
    public Setting<Boolean> Field395 = new Setting<>("Bypass", false).Method1191(this::Method396);
    public Setting<Boolean> Field396 = new Setting<>("Hypixel", false).Method1191(this::Method393);
    public Setting<Boolean> Field397 = new Setting<>("AllowEat", false).Method1191(this::Method539);
    public Setting<Boolean> Field398 = new Setting<>("Strict", false).Method1191(this::Method538);
    public Setting<Boolean> Field399 = new Setting<>("DisableOnSneak", false);
    public Setting<Boolean> Field400 = new Setting<>("ForceSprint", false);
    public Setting<Boolean> Field401 = new Setting<>("Boost", false).Method1191(this::Method535);
    public Setting<Double> Field402 = new Setting<>("BoostFactor", 1.0, 10.0, 0.0, 0.1).Method1191(this::Method519);
    public Setting<ParentSetting> Field403 = new Setting<>("Alternative", new ParentSetting(false));
    public Setting<StrafeMode> Field404 = new Setting<>("AltMode", StrafeMode.ONGROUND).setParentSetting(this.Field403);
    public Setting<Class537> Field405 = new Setting<>("AltBind", new Class537(0)).setParentSetting(this.Field403);
    public int Field406 = 1;
    public int Field407;
    public double Field408 = 0.0;
    public double Field409;
    public double Field410 = 0.0;
    public double Field411 = 0.0;
    public boolean Field412 = false;
    public int Field413 = 4;
    public double Field414 = 0.2873;
    public int Field415;
    public int Field416 = 4;
    public int Field417 = 0;
    public boolean Field418;
    public double Field419 = 0.0;
    public Class566 Field420 = new Class566();
    public Class566 Field421 = new Class566();
    public int Field422;
    public double Field423;
    public boolean Field424;
    public int Field425;
    public double Field426;
    public boolean Field427;

    @Override
    public boolean Method396() {
        return this.Method537() == StrafeMode.ONGROUND;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean Method535() {
        if (this.Method537() == StrafeMode.SMALLHOP) return true;
        if (this.Method537() == StrafeMode.STRAFE) return true;
        if (this.Method537() == StrafeMode.STRAFEOLD) return true;
        if (this.Method537() != StrafeMode.STRAFESTRICT) return false;
        return true;
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        boolean bl;
        if (Speed.mc.player == null || Speed.mc.world == null) {
            return;
        }
        if (tickEvent.Method324() == net.minecraftforge.fml.common.gameevent.TickEvent.Phase.START && this.Method537() == StrafeMode.ONGROUND) {
            double d = Speed.mc.player.posX - Speed.mc.player.prevPosX;
            double d2 = Speed.mc.player.posZ - Speed.mc.player.prevPosZ;
            this.Field411 = Math.sqrt(d * d + d2 * d2);
        }
        if (Class167.Method1610(ElytraFly.class).isEnabled() && ElytraFly.Method976()) {
            NewGui.INSTANCE.Field1134.Method749(this);
            return;
        }
        int n = MathHelper.floor((double)(Speed.mc.player.getEntityBoundingBox().minY - 0.2));
        boolean bl2 = bl = Jesus.Method834(BlockLiquid.class, n) != null;
        if (Class167.Method1610(Jesus.class).isEnabled() && (Speed.mc.player.isInWater() || Speed.mc.player.isInLava() || bl)) {
            return;
        }
        this.Method1645(this.Method537().name().substring(0, 1).toUpperCase() + this.Method537().name().substring(1).toLowerCase());
        if (((Boolean)this.Field399.getValue()).booleanValue() && Speed.mc.player.isSneaking()) {
            return;
        }
        if ((this.Method537() == StrafeMode.STRAFEOLD || this.Method537() == StrafeMode.STRAFE || this.Method537() == StrafeMode.LOWHOP) && ((Boolean)this.Field392.getValue()).booleanValue()) {
            NewGui.INSTANCE.Field1134.Method746(this, 10, 1.08f + 0.008f * ((Float)this.Field393.getValue()).floatValue());
        } else if (this.Method537() != StrafeMode.STRAFESTRICT && this.Method537() != StrafeMode.SMALLHOP) {
            NewGui.INSTANCE.Field1134.Method749(this);
        }
        switch (Class347.Field2591[this.Method537().ordinal()]) {
            case 1: {
                if (Class167.Method1612("LongJump").isEnabled() && ((Boolean) LongJump.disableStrafe.getValue()).booleanValue()) {
                    return;
                }
                if (Class167.Method1612("ElytraFly").isEnabled()) {
                    return;
                }
                if (((Boolean)this.Field400.getValue()).booleanValue() && !Speed.mc.player.isSprinting() && MathUtil.Method1080()) {
                    Speed.mc.player.setSprinting(true);
                    Speed.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Speed.mc.player, CPacketEntityAction.Action.START_SPRINTING));
                }
                this.Field408 = Math.sqrt((Speed.mc.player.posX - Speed.mc.player.prevPosX) * (Speed.mc.player.posX - Speed.mc.player.prevPosX) + (Speed.mc.player.posZ - Speed.mc.player.prevPosZ) * (Speed.mc.player.posZ - Speed.mc.player.prevPosZ));
                break;
            }
            case 2: {
                if (!MathUtil.Method1080() || Speed.mc.player.collidedHorizontally) {
                    NewGui.INSTANCE.Field1134.Method749(this);
                    return;
                }
                if (Speed.mc.player.onGround) {
                    NewGui.INSTANCE.Field1134.Method746(this, 10, 1.15f);
                    Speed.mc.player.jump();
                    boolean bl3 = Speed.mc.world.getBlockState(new BlockPos(Speed.mc.player.posX, Speed.mc.player.posY - 1.0, Speed.mc.player.posZ)).getBlock() instanceof BlockIce || Speed.mc.world.getBlockState(new BlockPos(Speed.mc.player.posX, Speed.mc.player.posY - 1.0, Speed.mc.player.posZ)).getBlock() instanceof BlockPackedIce;
                    double[] dArray = MathUtil.Method1086(this.Method541() * (Double)this.Field391.getValue() + (((Boolean)this.Field401.getValue()).booleanValue() ? (bl3 ? 0.3 : 0.06 * (Double)this.Field402.getValue()) : 0.0));
                    Speed.mc.player.motionX = dArray[0];
                    Speed.mc.player.motionZ = dArray[1];
                    break;
                }
                Speed.mc.player.motionY = -1.0;
                NewGui.INSTANCE.Field1134.Method749(this);
                break;
            }
        }
        Item item = Speed.mc.player.getActiveItemStack().getItem();
        if (this.Method537() == StrafeMode.STRAFESTRICT && ((Boolean)this.Field397.getValue()).booleanValue() && this.Field418 && (!Speed.mc.player.isHandActive() && item instanceof ItemFood || item instanceof ItemBow || item instanceof ItemPotion || !(item instanceof ItemFood) || !(item instanceof ItemBow) || !(item instanceof ItemPotion))) {
            Speed.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Speed.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.Field418 = false;
        }
    }

    @Subscriber(priority=1000)
    public void Method135(UpdateEvent updateEvent) {
        if (Class167.Method1610(ElytraFly.class).isEnabled() && ElytraFly.Method976()) {
            return;
        }
        if (!MathUtil.Method1080()) {
            this.Field410 = 0.0;
            if (this.Method537() != StrafeMode.SMALLHOP) {
                Speed.mc.player.motionX = 0.0;
                Speed.mc.player.motionZ = 0.0;
                return;
            }
        }
        if (this.Method537() == StrafeMode.STRAFE || this.Method537() == StrafeMode.STRAFESTRICT || this.Method537() == StrafeMode.LOWHOP) {
            double d = Speed.mc.player.posX - Speed.mc.player.prevPosX;
            double d2 = Speed.mc.player.posZ - Speed.mc.player.prevPosZ;
            this.Field411 = Math.sqrt(d * d + d2 * d2);
        }
    }

    public Speed() {
        super("Speed", Category.MOVEMENT, new String[0]);
    }

    @Subscriber
    public void Method536(Class24 class24) {
        block0: {
            if (!(class24.getPacket() instanceof CPacketPlayer) || !this.Field427) break block0;
            this.Field427 = false;
            ((ICPacketPlayer)class24.getPacket()).Method1700(true);
        }
    }

    public StrafeMode Method537() {
        if (MathUtil.Method1087(((Class537)this.Field405.getValue()).Method851())) {
            return (StrafeMode)((Object)this.Field404.getValue());
        }
        return (StrafeMode)((Object)this.Field394.getValue());
    }

    public double Method504(double d, int n) {
        BigDecimal bigDecimal = new BigDecimal(d);
        bigDecimal = bigDecimal.setScale(n, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    @Override
    public void onDisable() {
        if (Speed.mc.player == null || Speed.mc.world == null) {
            return;
        }
        if (this.Method537() == StrafeMode.SMALLHOP) {
            Speed.mc.player.setVelocity(0.0, 0.0, 0.0);
        }
        NewGui.INSTANCE.Field1134.Method749(this);
    }

    public boolean Method538() {
        return this.Method537() == StrafeMode.STRAFE;
    }

    @Subscriber
    public void Method503(MoveEvent moveEvent) {
        boolean bl;
        if (Speed.mc.player == null || Speed.mc.world == null) {
            return;
        }
        if (((Boolean)this.Field399.getValue()).booleanValue() && Speed.mc.player.isSneaking()) {
            return;
        }
        if (Class167.Method1610(ElytraFly.class).isEnabled() && ElytraFly.Method976()) {
            return;
        }
        int n = MathHelper.floor((double)(Speed.mc.player.getEntityBoundingBox().minY - 0.2));
        boolean bl2 = bl = Jesus.Method834(BlockLiquid.class, n) != null;
        if (Class167.Method1610(Jesus.class).isEnabled() && (Speed.mc.player.isInWater() || Speed.mc.player.isInLava() || bl)) {
            return;
        }
        switch (Class347.Field2591[this.Method537().ordinal()]) {
            case 4: {
                if (this.Field413 != 1 || Speed.mc.player.moveForward == 0.0f || Speed.mc.player.moveStrafing == 0.0f) {
                    if (this.Field413 == 2 && (Speed.mc.player.moveForward != 0.0f || Speed.mc.player.moveStrafing != 0.0f)) {
                        double d = 0.0;
                        if (Speed.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
                            d += (double)((float)(Speed.mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1f);
                        }
                        Speed.mc.player.motionY = ((Boolean)this.Field396.getValue() != false ? 0.3999999463558197 : 0.3999) + d;
                        moveEvent.setY(Speed.mc.player.motionY);
                        this.Field410 *= this.Field412 ? 1.6835 : 1.395;
                    } else if (this.Field413 == 3) {
                        double d = 0.66 * (this.Field411 - this.Method541());
                        this.Field410 = this.Field411 - d;
                        this.Field412 = !this.Field412;
                    } else {
                        List list = Speed.mc.world.getCollisionBoxes((Entity)Speed.mc.player, Speed.mc.player.getEntityBoundingBox().offset(0.0, Speed.mc.player.motionY, 0.0));
                        if (list.size() > 0 || Speed.mc.player.collidedVertically) {
                            if (this.Field413 > 0) {
                                this.Field413 = Speed.mc.player.moveForward == 0.0f && Speed.mc.player.moveStrafing == 0.0f ? 0 : 1;
                            }
                        }
                        this.Field410 = this.Field411 - this.Field411 / 159.0;
                    }
                } else {
                    this.Field410 = 1.35 * this.Method541() - 0.01;
                }
                this.Field410 = Math.max(this.Field410, this.Method541());
                if (this.Field419 > 0.0 && ((Boolean)this.Field401.getValue()).booleanValue() && !this.Field420.Method737(75.0) && !Speed.mc.player.collidedHorizontally) {
                    this.Field410 = Math.max(this.Field410, this.Field419);
                } else if (((Boolean)this.Field398.getValue()).booleanValue()) {
                    this.Field410 = Math.min(this.Field410, 0.433);
                }
                double d = Speed.mc.player.movementInput.moveForward;
                double d2 = Speed.mc.player.movementInput.moveStrafe;
                float f = Speed.mc.player.rotationYaw;
                if (d == 0.0 && d2 == 0.0) {
                    moveEvent.setX(0.0);
                    moveEvent.setZ(0.0);
                } else {
                    if (d != 0.0) {
                        if (d2 > 0.0) {
                            f += (float)(d > 0.0 ? -45 : 45);
                        } else if (d2 < 0.0) {
                            f += (float)(d > 0.0 ? 45 : -45);
                        }
                        d2 = 0.0;
                        if (d > 0.0) {
                            d = 1.0;
                        } else if (d < 0.0) {
                            d = -1.0;
                        }
                    }
                    moveEvent.setX(d * this.Field410 * Math.cos(Math.toRadians(f + 90.0f)) + d2 * this.Field410 * Math.sin(Math.toRadians(f + 90.0f)));
                    moveEvent.setZ(d * this.Field410 * Math.sin(Math.toRadians(f + 90.0f)) - d2 * this.Field410 * Math.cos(Math.toRadians(f + 90.0f)));
                }
                if (Speed.mc.player.moveForward == 0.0f && Speed.mc.player.moveStrafing == 0.0f) {
                    return;
                }
                ++this.Field413;
                break;
            }
            case 1: {
                if (this.Method537() == StrafeMode.STRAFEOLD && Class167.Method1612("LongJump").isEnabled() && ((Boolean) LongJump.disableStrafe.getValue()).booleanValue()) {
                    return;
                }
                if (this.Method537() == StrafeMode.STRAFEOLD && Class167.Method1612("ElytraFly").isEnabled()) {
                    return;
                }
                if (!Speed.mc.player.isSprinting()) {
                    Speed.mc.player.setSprinting(true);
                    Speed.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Speed.mc.player, CPacketEntityAction.Action.START_SPRINTING));
                }
                double d = (Double)this.Field391.getValue() * 0.99;
                switch (this.Field406) {
                    case 0: {
                        ++this.Field406;
                        this.Field408 = 0.0;
                        break;
                    }
                    case 2: {
                        double d3;
                        double d4 = d3 = (Boolean)this.Field396.getValue() != false ? 0.3999999463558197 : 0.40123128;
                        if (Speed.mc.player.moveForward == 0.0f && Speed.mc.player.moveStrafing == 0.0f || !Speed.mc.player.onGround) break;
                        Speed.mc.player.motionY = d3;
                        moveEvent.setY(Speed.mc.player.motionY);
                        this.Field409 *= 2.149;
                        break;
                    }
                    case 3: {
                        this.Field409 = this.Field408 - 0.76 * (this.Field408 - this.Method541());
                        break;
                    }
                    default: {
                        this.Field409 = this.Field408 - this.Field408 / 159.0;
                        if (Speed.mc.world.getCollisionBoxes((Entity)Speed.mc.player, Speed.mc.player.getEntityBoundingBox().offset(0.0, Speed.mc.player.motionY, 0.0)).size() <= 0) {
                            if (!Speed.mc.player.collidedVertically) break;
                        }
                        if (this.Field406 <= 0) break;
                        this.Field406 = Speed.mc.player.moveForward != 0.0f || Speed.mc.player.moveStrafing != 0.0f ? 1 : 0;
                        break;
                    }
                }
                this.Field409 = Math.max(this.Field409, this.Method541());
                if (this.Field419 > 0.0 && ((Boolean)this.Field401.getValue()).booleanValue() && !this.Field420.Method737(75.0) && !Speed.mc.player.collidedHorizontally) {
                    this.Field409 = Math.max(this.Field409, this.Field419);
                }
                float f = Speed.mc.player.movementInput.moveForward;
                float f2 = Speed.mc.player.movementInput.moveStrafe;
                if (f == 0.0f && f2 == 0.0f) {
                    moveEvent.setX(0.0);
                    moveEvent.setZ(0.0);
                } else if ((double)f != 0.0 && (double)f2 != 0.0) {
                    f = (float)((double)f * Math.sin(0.7853981633974483));
                    f2 = (float)((double)f2 * Math.cos(0.7853981633974483));
                }
                moveEvent.setX(((double)f * this.Field409 * -Math.sin(Math.toRadians(Speed.mc.player.rotationYaw)) + (double)f2 * this.Field409 * Math.cos(Math.toRadians(Speed.mc.player.rotationYaw))) * d);
                moveEvent.setZ(((double)f * this.Field409 * Math.cos(Math.toRadians(Speed.mc.player.rotationYaw)) - (double)f2 * this.Field409 * -Math.sin(Math.toRadians(Speed.mc.player.rotationYaw))) * d);
                ++this.Field406;
                break;
            }
            case 5: {
                ++this.Field415;
                this.Field415 %= 5;
                if (this.Field415 != 0) {
                    NewGui.INSTANCE.Field1134.Method749(this);
                } else if (MathUtil.Method1080()) {
                    NewGui.INSTANCE.Field1134.Method746(this, 10, 1.3f);
                    Speed.mc.player.motionX *= (double)1.02f;
                    Speed.mc.player.motionZ *= (double)1.02f;
                }
                if (Speed.mc.player.onGround && MathUtil.Method1080()) {
                    this.Field416 = 2;
                }
                if (this.Method504(Speed.mc.player.posY - (double)((int)Speed.mc.player.posY), 3) == this.Method504(0.138, 3)) {
                    Speed.mc.player.motionY -= 0.08;
                    moveEvent.setY(moveEvent.getY() - 0.09316090325960147);
                    Speed.mc.player.posY -= 0.09316090325960147;
                }
                if (this.Field416 == 1 && (Speed.mc.player.moveForward != 0.0f || Speed.mc.player.moveStrafing != 0.0f)) {
                    this.Field416 = 2;
                    this.Field414 = 1.38 * this.Method541() - 0.01;
                } else if (this.Field416 == 2) {
                    this.Field416 = 3;
                    Speed.mc.player.motionY = 0.3994f;
                    moveEvent.setY(0.3994f);
                    this.Field414 *= 2.149;
                } else if (this.Field416 == 3) {
                    this.Field416 = 4;
                    double d = 0.66 * (this.Field411 - this.Method541());
                    this.Field414 = this.Field411 - d;
                } else {
                    if (Speed.mc.world.getCollisionBoxes((Entity)Speed.mc.player, Speed.mc.player.getEntityBoundingBox().offset(0.0, Speed.mc.player.motionY, 0.0)).size() > 0 || Speed.mc.player.collidedVertically) {
                        this.Field416 = 1;
                    }
                    this.Field414 = this.Field411 - this.Field411 / 159.0;
                }
                this.Field414 = Math.max(this.Field414, this.Method541());
                this.Field414 = this.Field419 > 0.0 && (Boolean)this.Field401.getValue() != false && !this.Field420.Method737(75.0) && !Speed.mc.player.collidedHorizontally ? Math.max(this.Field414, this.Field419) : Math.min(this.Field414, this.Field417 > 25 ? 0.449 : 0.433);
                float f = Speed.mc.player.movementInput.moveForward;
                float f3 = Speed.mc.player.movementInput.moveStrafe;
                float f4 = Speed.mc.player.rotationYaw;
                ++this.Field417;
                if (this.Field417 > 50) {
                    this.Field417 = 0;
                }
                if (f == 0.0f && f3 == 0.0f) {
                    moveEvent.setX(0.0);
                    moveEvent.setZ(0.0);
                } else if (f != 0.0f) {
                    if (f3 >= 1.0f) {
                        f4 += (float)(f > 0.0f ? -45 : 45);
                        f3 = 0.0f;
                    } else if (f3 <= -1.0f) {
                        f4 += (float)(f > 0.0f ? 45 : -45);
                        f3 = 0.0f;
                    }
                    if (f > 0.0f) {
                        f = 1.0f;
                    } else if (f < 0.0f) {
                        f = -1.0f;
                    }
                }
                double d = Math.cos(Math.toRadians(f4 + 90.0f));
                double d5 = Math.sin(Math.toRadians(f4 + 90.0f));
                moveEvent.setX((double)f * this.Field414 * d + (double)f3 * this.Field414 * d5);
                moveEvent.setZ((double)f * this.Field414 * d5 - (double)f3 * this.Field414 * d);
                if (f != 0.0f || f3 != 0.0f) break;
                moveEvent.setX(0.0);
                moveEvent.setZ(0.0);
                break;
            }
            case 6: {
                if (!this.Field421.Method737(100.0)) {
                    return;
                }
                double d = 0.0;
                if (Speed.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
                    d += (double)((float)(Speed.mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1f);
                }
                if (this.Method504(Speed.mc.player.posY - (double)((int)Speed.mc.player.posY), 3) == this.Method504(0.4, 3)) {
                    Speed.mc.player.motionY = 0.31 + d;
                    moveEvent.setY(Speed.mc.player.motionY);
                } else if (this.Method504(Speed.mc.player.posY - (double)((int)Speed.mc.player.posY), 3) == this.Method504(0.71, 3)) {
                    Speed.mc.player.motionY = 0.04 + d;
                    moveEvent.setY(Speed.mc.player.motionY);
                } else if (this.Method504(Speed.mc.player.posY - (double)((int)Speed.mc.player.posY), 3) == this.Method504(0.75, 3)) {
                    Speed.mc.player.motionY = -0.2 - d;
                    moveEvent.setY(Speed.mc.player.motionY);
                } else if (this.Method504(Speed.mc.player.posY - (double)((int)Speed.mc.player.posY), 3) == this.Method504(0.55, 3)) {
                    Speed.mc.player.motionY = -0.14 + d;
                    moveEvent.setY(Speed.mc.player.motionY);
                } else if (this.Method504(Speed.mc.player.posY - (double)((int)Speed.mc.player.posY), 3) == this.Method504(0.41, 3)) {
                    Speed.mc.player.motionY = -0.2 + d;
                    moveEvent.setY(Speed.mc.player.motionY);
                }
                if (this.Field422 == 1 && (Speed.mc.player.moveForward != 0.0f || Speed.mc.player.moveStrafing != 0.0f)) {
                    this.Field423 = 1.35 * this.Method541() - 0.01;
                } else if (this.Field422 == 2 && (Speed.mc.player.moveForward != 0.0f || Speed.mc.player.moveStrafing != 0.0f)) {
                    Speed.mc.player.motionY = (this.Method388() ? 0.2 : 0.3999) + d;
                    moveEvent.setY(Speed.mc.player.motionY);
                    this.Field423 *= this.Field424 ? 1.5685 : 1.3445;
                } else if (this.Field422 == 3) {
                    double d6 = 0.66 * (this.Field411 - this.Method541());
                    this.Field423 = this.Field411 - d6;
                    this.Field424 = !this.Field424;
                } else {
                    if (Speed.mc.player.onGround && this.Field422 > 0) {
                        this.Field422 = Speed.mc.player.moveForward != 0.0f || Speed.mc.player.moveStrafing != 0.0f ? 1 : 0;
                    }
                    this.Field423 = this.Field411 - this.Field411 / 159.0;
                }
                this.Field423 = Math.max(this.Field423, this.Method541());
                float f = Speed.mc.player.movementInput.moveForward;
                float f5 = Speed.mc.player.movementInput.moveStrafe;
                if (f == 0.0f && f5 == 0.0f) {
                    moveEvent.setX(0.0);
                    moveEvent.setZ(0.0);
                } else if ((double)f != 0.0 && (double)f5 != 0.0) {
                    f = (float)((double)f * Math.sin(0.7853981633974483));
                    f5 = (float)((double)f5 * Math.cos(0.7853981633974483));
                }
                moveEvent.setX((double)f * this.Field423 * -Math.sin(Math.toRadians(Speed.mc.player.rotationYaw)) + (double)f5 * this.Field423 * Math.cos(Math.toRadians(Speed.mc.player.rotationYaw)));
                moveEvent.setZ((double)f * this.Field423 * Math.cos(Math.toRadians(Speed.mc.player.rotationYaw)) - (double)f5 * this.Field423 * -Math.sin(Math.toRadians(Speed.mc.player.rotationYaw)));
                if (Speed.mc.player.moveForward == 0.0f && Speed.mc.player.moveStrafing == 0.0f) {
                    return;
                }
                ++this.Field422;
                break;
            }
            case 7: {
                if (!Speed.mc.player.onGround && this.Field425 != 3) {
                    return;
                }
                if (!Speed.mc.player.collidedHorizontally && Speed.mc.player.moveForward != 0.0f || Speed.mc.player.moveStrafing != 0.0f) {
                    if (this.Field425 == 2) {
                        Speed.mc.player.motionY = -0.5;
                        moveEvent.setY(this.Method388() ? 0.2 : 0.4);
                        this.Field426 *= 2.149;
                        this.Field425 = 3;
                        if (((Boolean)this.Field395.getValue()).booleanValue()) {
                            this.Field427 = true;
                        }
                    } else if (this.Field425 == 3) {
                        double d = 0.66 * (this.Field411 - this.Method541());
                        this.Field426 = this.Field411 - d;
                        this.Field425 = 2;
                    } else if (this.Method388() || Speed.mc.player.collidedVertically) {
                        this.Field425 = 1;
                    }
                }
                this.Field426 = Math.min(Math.max(this.Field426, this.Method541()), (Double)this.Field391.getValue());
                float f = Speed.mc.player.movementInput.moveForward;
                float f6 = Speed.mc.player.movementInput.moveStrafe;
                if (f == 0.0f && f6 == 0.0f) {
                    moveEvent.setX(0.0);
                    moveEvent.setZ(0.0);
                } else if ((double)f != 0.0 && (double)f6 != 0.0) {
                    f = (float)((double)f * Math.sin(0.7853981633974483));
                    f6 = (float)((double)f6 * Math.cos(0.7853981633974483));
                }
                moveEvent.setX((double)f * this.Field426 * -Math.sin(Math.toRadians(Speed.mc.player.rotationYaw)) + (double)f6 * this.Field426 * Math.cos(Math.toRadians(Speed.mc.player.rotationYaw)));
                moveEvent.setZ((double)f * this.Field426 * Math.cos(Math.toRadians(Speed.mc.player.rotationYaw)) - (double)f6 * this.Field426 * -Math.sin(Math.toRadians(Speed.mc.player.rotationYaw)));
                break;
            }
        }
    }

    @Override
    public void onEnable() {
        if (Speed.mc.player == null || Speed.mc.world == null) {
            this.toggle();
            return;
        }
        this.Field419 = 0.0;
        this.Field407 = 1;
        this.Field422 = 4;
        this.Field425 = 2;
        if (this.Method537() == StrafeMode.STRAFEOLD && Class167.Method1612("LongJump").isEnabled() && ((Boolean) LongJump.disableStrafe.getValue()).booleanValue()) {
            return;
        }
        switch (Class347.Field2591[this.Method537().ordinal()]) {
            case 1: {
                if (!Speed.mc.player.isSprinting() && MathUtil.Method1080()) {
                    Speed.mc.player.setSprinting(true);
                    Speed.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Speed.mc.player, CPacketEntityAction.Action.START_SPRINTING));
                }
            }
            case 4: {
                this.Field413 = 4;
                this.Field410 = this.Method541();
                this.Field411 = 0.0;
            }
        }
    }

    public boolean Method539() {
        return this.Method537() == StrafeMode.STRAFESTRICT;
    }

    public boolean Method519() {
        return this.Method537() == StrafeMode.SMALLHOP && (Boolean)this.Field401.getValue() != false;
    }

    @Subscriber
    public void Method130(Class19 class19) {
        if (Speed.mc.player == null || Speed.mc.world == null) {
            return;
        }
        if (Class167.Method1610(ElytraFly.class).isEnabled() && ElytraFly.Method976()) {
            return;
        }
        switch (Class347.Field2591[this.Method537().ordinal()]) {
            case 3: {
                for (double d = 0.0625; d < (Double)this.Field391.getValue(); d += 0.262) {
                    double[] dArray = MathUtil.Method1086(d);
                    Speed.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Speed.mc.player.posX + dArray[0], Speed.mc.player.posY, Speed.mc.player.posZ + dArray[1], Speed.mc.player.onGround));
                }
                Speed.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Speed.mc.player.posX + Speed.mc.player.motionX, Speed.mc.player.posY <= 10.0 ? 255.0 : 1.0, Speed.mc.player.posZ + Speed.mc.player.motionZ, Speed.mc.player.onGround));
                break;
            }
        }
    }

    public boolean Method388() {
        return Speed.mc.world.getCollisionBoxes((Entity)Speed.mc.player, Speed.mc.player.getEntityBoundingBox().offset(0.0, 0.21, 0.0)).size() > 0;
    }

    @Subscriber
    public void Method540(Class25 class25) {
        if (!this.Field418 && this.Method537() == StrafeMode.STRAFESTRICT && ((Boolean)this.Field397.getValue()).booleanValue()) {
            Speed.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Speed.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            this.Field418 = true;
        }
    }

    public boolean Method393() {
        return this.Method537() == StrafeMode.STRAFEOLD;
    }

    @Subscriber(priority=69)
    public void Method131(PacketEvent packetEvent) {
        block2: {
            block1: {
                if (Class167.Method1610(ElytraFly.class).isEnabled() && ElytraFly.Method976()) {
                    return;
                }
                if (!(packetEvent.getPacket() instanceof SPacketPlayerPosLook)) break block1;
                NewGui.INSTANCE.Field1134.Method749(this);
                this.Field408 = 0.0;
                this.Field410 = 0.0;
                this.Field409 = 0.0;
                this.Field413 = 4;
                this.Field414 = 0.2873;
                this.Field416 = 4;
                this.Field411 = 0.0;
                this.Field415 = 0;
                this.Field419 = 0.0;
                this.Field421.Method739();
                this.Field422 = 4;
                this.Field425 = 2;
                this.Field426 = 0.0;
                break block2;
            }
            if (!(packetEvent.getPacket() instanceof SPacketExplosion)) break block2;
            SPacketExplosion sPacketExplosion = (SPacketExplosion) packetEvent.getPacket();
            this.Field419 = Math.sqrt(sPacketExplosion.getMotionX() * sPacketExplosion.getMotionX() + sPacketExplosion.getMotionZ() * sPacketExplosion.getMotionZ());
            this.Field420.Method739();
        }
    }

    public double Method541() {
        double d;
        double d2 = d = this.Method537() == StrafeMode.STRAFE || this.Method537() == StrafeMode.STRAFESTRICT || this.Method537() == StrafeMode.SMALLHOP || this.Method537() == StrafeMode.ONGROUND || this.Method537() == StrafeMode.LOWHOP ? 0.2873 : 0.272;
        if (Speed.mc.player.isPotionActive(MobEffects.SPEED)) {
            int n = Speed.mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
            d *= 1.0 + 0.2 * ((double)n + 1.0);
        }
        return d;
    }
}
