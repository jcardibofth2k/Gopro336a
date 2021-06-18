package me.darki.konas.module.movement;

import cookiedragon.eventsystem.Subscriber;
import java.math.BigDecimal;
import java.math.RoundingMode;

import me.darki.konas.event.events.MoveEvent;
import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.event.events.UpdateEvent;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.Class438;
import me.darki.konas.unremaped.Class441;
import me.darki.konas.unremaped.Class459;
import me.darki.konas.util.TimerUtil;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

public class LongJump
extends Module {
    public static Setting<Class459> mode = new Setting<>("Mode", Class459.BYPASS);
    public static Setting<Float> speed = new Setting<>("Speed", Float.valueOf(4.5f), Float.valueOf(20.0f), Float.valueOf(0.5f), Float.valueOf(0.1f));
    public static Setting<Float> modifier = new Setting<>("Modifier", Float.valueOf(5.0f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(0.1f));
    public static Setting<Float> glide = new Setting<>("Glide", Float.valueOf(1.0f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(0.1f));
    public static Setting<Boolean> shortJump = new Setting<>("ShortJump", false);
    public static Setting<Boolean> disableStrafe = new Setting<>("DisableStrafe", true);
    public static Setting<Class438> groundCheck = new Setting<>("GroundCheck", Class438.NORMAL);
    public static Setting<Boolean> autoDisable = new Setting<>("AutoDisable", false);
    public TimerUtil Field337 = new TimerUtil();
    public boolean Field338;
    public boolean Field339;
    public int Field340 = 0;
    public double Field341;
    public double Field342;
    public int Field343;
    public int Field344;
    public double Field345;
    public boolean Field346;

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        block0: {
            if (!(packetEvent.getPacket() instanceof SPacketPlayerPosLook) || !autoDisable.getValue().booleanValue()) break block0;
            this.toggle();
        }
    }

    @Override
    public void onEnable() {
        if (LongJump.mc.player != null && LongJump.mc.world != null) {
            this.Field345 = LongJump.Method505();
            LongJump.mc.player.onGround = true;
        }
        this.Field346 = groundCheck.getValue() != Class438.NONE;
        this.Field339 = false;
        this.Field338 = true;
        this.Field342 = 0.0;
        this.Field344 = 1;
    }

    @Subscriber
    public void Method503(MoveEvent moveEvent) {
        if (this.Field346) {
            return;
        }
        if (LongJump.mc.player != mc.getRenderViewEntity()) {
            return;
        }
        switch (Class441.Field723[mode.getValue().ordinal()]) {
            case 1: {
                if (LongJump.mc.player.moveStrafing <= 0.0f && LongJump.mc.player.moveForward <= 0.0f) {
                    this.Field344 = 1;
                }
                if (this.Method504(LongJump.mc.player.posY - (double)((int) LongJump.mc.player.posY), 3) == 0.943) {
                    LongJump.mc.player.motionY -= 0.0157 * (double) glide.getValue().floatValue();
                    moveEvent.setY(moveEvent.getY() - 0.0157 * (double) glide.getValue().floatValue());
                }
                if (this.Field344 == 1 && (LongJump.mc.player.moveForward != 0.0f || LongJump.mc.player.moveStrafing != 0.0f)) {
                    this.Field344 = 2;
                    this.Field345 = (double) speed.getValue().floatValue() * LongJump.Method505() - 0.01;
                } else if (this.Field344 == 2) {
                    LongJump.mc.player.motionY = 0.0848 * (double) modifier.getValue().floatValue();
                    moveEvent.setY(0.0848 * (double) modifier.getValue().floatValue());
                    this.Field344 = 3;
                    this.Field345 *= 2.149802;
                } else if (this.Field344 == 3) {
                    this.Field344 = 4;
                    this.Field341 = 0.66 * this.Field342;
                    this.Field345 = this.Field342 - this.Field341;
                } else {
                    if (LongJump.mc.world.getCollisionBoxes(LongJump.mc.player, LongJump.mc.player.getEntityBoundingBox().offset(0.0, LongJump.mc.player.motionY, 0.0)).size() > 0 || LongJump.mc.player.collidedVertically) {
                        this.Field344 = 1;
                    }
                    this.Field345 = this.Field342 - this.Field342 / 159.0;
                }
                this.Field345 = Math.max(this.Field345, LongJump.Method505());
                float f = LongJump.mc.player.movementInput.moveForward;
                float f2 = LongJump.mc.player.movementInput.moveStrafe;
                float f3 = LongJump.mc.player.rotationYaw;
                if (f == 0.0f && f2 == 0.0f) {
                    moveEvent.setX(0.0);
                    moveEvent.setZ(0.0);
                } else if (f != 0.0f) {
                    if (f2 >= 1.0f) {
                        f3 += (float)(f > 0.0f ? -45 : 45);
                        f2 = 0.0f;
                    } else if (f2 <= -1.0f) {
                        f3 += (float)(f > 0.0f ? 45 : -45);
                        f2 = 0.0f;
                    }
                    if (f > 0.0f) {
                        f = 1.0f;
                    } else if (f < 0.0f) {
                        f = -1.0f;
                    }
                }
                double d = Math.cos(Math.toRadians(f3 + 90.0f));
                double d2 = Math.sin(Math.toRadians(f3 + 90.0f));
                moveEvent.setX((double)f * this.Field345 * d + (double)f2 * this.Field345 * d2);
                moveEvent.setZ((double)f * this.Field345 * d2 - (double)f2 * this.Field345 * d);
                return;
            }
            case 2: {
                if (this.Field338) {
                    if (LongJump.mc.player.onGround) {
                        this.Field337.UpdateCurrentTime();
                    }
                    if (this.Method504(LongJump.mc.player.posY - (double)((int) LongJump.mc.player.posY), 3) == 0.41) {
                        LongJump.mc.player.motionY = 0.0;
                    }
                    if (LongJump.mc.player.moveStrafing <= 0.0f && LongJump.mc.player.moveForward <= 0.0f) {
                        this.Field343 = 1;
                    }
                    if (this.Method504(LongJump.mc.player.posY - (double)((int) LongJump.mc.player.posY), 3) == 0.943) {
                        LongJump.mc.player.motionY = 0.0;
                    }
                    if (this.Field343 == 1) {
                        if (LongJump.mc.player.moveForward != 0.0f || LongJump.mc.player.moveStrafing != 0.0f) {
                            this.Field343 = 2;
                            this.Field345 = (double) speed.getValue().floatValue() * LongJump.Method505() - 0.01;
                        }
                    } else if (this.Field343 == 2) {
                        this.Field343 = 3;
                        if (!shortJump.getValue().booleanValue()) {
                            LongJump.mc.player.motionY = 0.424;
                        }
                        moveEvent.setY(0.424);
                        this.Field345 *= 2.149802;
                    } else if (this.Field343 == 3) {
                        this.Field343 = 4;
                        double d = 0.66 * (this.Field342 - LongJump.Method505());
                        this.Field345 = this.Field342 - d;
                    } else {
                        if (LongJump.mc.world.getCollisionBoxes(LongJump.mc.player, LongJump.mc.player.getEntityBoundingBox().offset(0.0, LongJump.mc.player.motionY, 0.0)).size() > 0 || LongJump.mc.player.collidedVertically) {
                            this.Field343 = 1;
                        }
                        this.Field345 = this.Field342 - this.Field342 / 159.0;
                    }
                    this.Field345 = Math.max(this.Field345, LongJump.Method505());
                    float f = LongJump.mc.player.movementInput.moveForward;
                    float f4 = LongJump.mc.player.movementInput.moveStrafe;
                    float f5 = LongJump.mc.player.rotationYaw;
                    if (f == 0.0f || f4 == 0.0f) {
                        moveEvent.setX(0.0);
                        moveEvent.setZ(0.0);
                    } else {
                        if (f4 >= 1.0f) {
                            f5 += (float)(f > 0.0f ? -45 : 45);
                            f4 = 0.0f;
                        } else if (f4 <= -1.0f) {
                            f5 += (float)(f > 0.0f ? 45 : -45);
                            f4 = 0.0f;
                        }
                        if (f > 0.0f) {
                            f = 1.0f;
                        } else if (f < 0.0f) {
                            f = -1.0f;
                        }
                    }
                    double d = Math.cos(Math.toRadians(f5 + 90.0f));
                    double d3 = Math.sin(Math.toRadians(f5 + 90.0f));
                    moveEvent.setX((double)f * this.Field345 * d + (double)f4 * this.Field345 * d3);
                    moveEvent.setZ((double)f * this.Field345 * d3 - (double)f4 * this.Field345 * d);
                    if (f == 0.0f && f4 == 0.0f) {
                        moveEvent.setX(0.0);
                        moveEvent.setZ(0.0);
                    }
                }
                if (LongJump.mc.player.onGround) {
                    ++this.Field340;
                } else if (!LongJump.mc.player.onGround && this.Field340 != 0) {
                    --this.Field340;
                }
                if (shortJump.getValue().booleanValue()) {
                    if (this.Field337.GetDifferenceTiming(35.0)) {
                        this.Field339 = true;
                    }
                    if (this.Field337.GetDifferenceTiming(2490.0)) {
                        this.Field339 = false;
                        this.Field338 = false;
                        LongJump.mc.player.motionX *= 0.0;
                        LongJump.mc.player.motionZ *= 0.0;
                    }
                    if (!this.Field337.GetDifferenceTiming(2820.0)) {
                        return;
                    }
                    this.Field338 = true;
                    LongJump.mc.player.motionX *= 0.0;
                    LongJump.mc.player.motionZ *= 0.0;
                    this.Field337.UpdateCurrentTime();
                    break;
                }
                if (this.Field337.GetDifferenceTiming(480.0)) {
                    LongJump.mc.player.motionX *= 0.0;
                    LongJump.mc.player.motionZ *= 0.0;
                    this.Field338 = false;
                }
                if (!this.Field337.GetDifferenceTiming(780.0)) break;
                LongJump.mc.player.motionX *= 0.0;
                LongJump.mc.player.motionZ *= 0.0;
                this.Field338 = true;
                this.Field337.UpdateCurrentTime();
                break;
            }
        }
    }

    public LongJump() {
        super("LongJump", Category.MOVEMENT);
    }

    public double Method504(double d, int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bigDecimal = new BigDecimal(d);
        bigDecimal = bigDecimal.setScale(n, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    public static double Method505() {
        double d = 0.2873;
        if (LongJump.mc.player.isPotionActive(MobEffects.SPEED)) {
            int n = LongJump.mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
            d *= 1.0 + 0.2 * (double)(n + 1);
        }
        return d;
    }

    @Subscriber
    public void Method135(UpdateEvent updateEvent) {
        if (this.Field346) {
            if (groundCheck.getValue() == Class438.NORMAL) {
                if (LongJump.mc.player.onGround) {
                    this.Field346 = false;
                }
            } else if (groundCheck.getValue() == Class438.EDGEJUMP && LongJump.mc.player.onGround && !LongJump.mc.player.isSneaking() && LongJump.mc.world.getCollisionBoxes(LongJump.mc.player, LongJump.mc.player.getEntityBoundingBox().offset(0.0, 0.0, 0.0).shrink(0.001)).isEmpty()) {
                this.Field346 = false;
            }
        } else if (mode.getValue() == Class459.NORMAL) {
            this.Field341 = LongJump.mc.player.posX - LongJump.mc.player.prevPosX;
            double d = LongJump.mc.player.posZ - LongJump.mc.player.prevPosZ;
            this.Field342 = Math.sqrt(this.Field341 * this.Field341 + d * d);
        } else {
            double d = LongJump.mc.player.posX - LongJump.mc.player.prevPosX;
            double d2 = LongJump.mc.player.posZ - LongJump.mc.player.prevPosZ;
            this.Field342 = Math.sqrt(d * d + d2 * d2);
            if (!this.Field339) {
                return;
            }
            LongJump.mc.player.motionY = 0.005;
        }
    }

    @Override
    public void onDisable() {
        if (LongJump.mc.player != null && LongJump.mc.world != null && mode.getValue() == Class459.BYPASS) {
            LongJump.mc.player.onGround = false;
            LongJump.mc.player.capabilities.isFlying = false;
        }
    }
}