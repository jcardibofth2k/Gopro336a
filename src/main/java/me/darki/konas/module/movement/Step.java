package me.darki.konas.module.movement;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.event.events.MoveEvent;
import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.event.events.UpdateEvent;
import me.darki.konas.module.Category;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.setting.Setting;
import me.darki.konas.settingEnums.StepMode;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.module.exploit.RubberFill;
import me.darki.konas.util.TimerUtil;
import me.darki.konas.util.PlayerUtil;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import me.darki.konas.module.Module;

public class Step
extends Module {
    public static Setting<StepMode> mode = new Setting<>("Mode", StepMode.NORMAL);
    public static Setting<Float> stepHeight = new Setting<>("StepHeight", 1.0f, 7.0f, 0.5f, 0.5f);
    public static Setting<Boolean> upwards = new Setting<>("Upwards", true);
    public static Setting<Boolean> reverse = new Setting<>("Reverse", true);
    public static Setting<Boolean> useTimer = new Setting<>("UseTimer", false);
    public static Setting<Boolean> speedDisable = new Setting<>("SpeedDisable", true);
    public static Setting<Boolean> autoDisable = new Setting<>("AutoDisable", false);
    public boolean Field2157 = false;
    public int Field2158 = 0;
    public int Field2159 = 0;
    public TimerUtil Field2160 = new TimerUtil();
    public double Field2161 = 0.0;
    public static double[] Field2162 = new double[]{0.42, 0.753};
    public static double[] Field2163 = new double[]{0.42, 0.75, 1.0, 1.16, 1.23, 1.2};
    public static double[] Field2164 = new double[]{0.42, 0.78, 0.63, 0.51, 0.9, 1.21, 1.45, 1.43};
    public static double[] Field2165 = new double[]{0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869, 2.019, 1.907};

    @Subscriber
    public void tickEvent(TickEvent tickEvent) {
        if (Step.mc.world == null || Step.mc.player == null) {
            return;
        }
        Module module = ModuleManager.Method1612("Speed");
        if (module != null && module.isEnabled() && speedDisable.getValue().booleanValue()) {
            this.toggle();
        }
        if (Step.mc.player.getRidingEntity() != null) {
            this.Field2160.UpdateCurrentTime();
        }
        if (useTimer.getValue().booleanValue()) {
            if (this.Field2158 == 0) {
                KonasGlobals.INSTANCE.Field1134.Method749(this);
            } else {
                --this.Field2158;
            }
        } else {
            KonasGlobals.INSTANCE.Field1134.Method749(this);
        }
    }

    public Step() {
        super("Step", "Instantly steps up blocks", 0, Category.PLAYER);
    }

    public boolean Method394() {
        float f = Step.mc.player.rotationYaw;
        if (Step.mc.player.moveForward < 0.0f) {
            f += 180.0f;
        }
        float f2 = 1.0f;
        if (Step.mc.player.moveForward < 0.0f) {
            f2 = -0.5f;
        } else if (Step.mc.player.moveForward > 0.0f) {
            f2 = 0.5f;
        }
        if (Step.mc.player.moveStrafing > 0.0f) {
            f -= 90.0f * f2;
        }
        if (Step.mc.player.moveStrafing < 0.0f) {
            f += 90.0f * f2;
        }
        float f3 = (float)Math.toRadians(f);
        double d = (double)(-MathHelper.sin(f3)) * 0.4;
        double d2 = (double)MathHelper.cos(f3) * 0.4;
        return Step.mc.world.getCollisionBoxes(Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(d, 1.001335979112147, d2)).isEmpty();
    }

    @Override
    public void onEnable() {
        this.Field2157 = false;
    }

    @Override
    public void onDisable() {
        Step.mc.player.stepHeight = 0.5f;
        KonasGlobals.INSTANCE.Field1134.Method749(this);
    }

    public double Method463() {
        boolean bl;
        boolean bl2 = bl = Step.mc.player.onGround && Step.mc.player.collidedHorizontally;
        if (!bl) {
            return 0.0;
        }
        double d = -1.0;
        float f = Step.mc.player.rotationYaw;
        if (Step.mc.player.moveForward < 0.0f) {
            f += 180.0f;
        }
        float f2 = 1.0f;
        if (Step.mc.player.moveForward < 0.0f) {
            f2 = -0.5f;
        } else if (Step.mc.player.moveForward > 0.0f) {
            f2 = 0.5f;
        }
        if (Step.mc.player.moveStrafing > 0.0f) {
            f -= 90.0f * f2;
        }
        if (Step.mc.player.moveStrafing < 0.0f) {
            f += 90.0f * f2;
        }
        float f3 = (float)Math.toRadians(f);
        double d2 = (double)(-MathHelper.sin(f3)) * 0.4;
        double d3 = (double)MathHelper.cos(f3) * 0.4;
        AxisAlignedBB axisAlignedBB = Step.mc.player.getEntityBoundingBox().offset(0.0, 0.05, 0.0).grow(0.05);
        axisAlignedBB = axisAlignedBB.setMaxY(axisAlignedBB.maxY + (double) stepHeight.getValue().floatValue());
        for (AxisAlignedBB axisAlignedBB2 : Step.mc.world.getCollisionBoxes(Step.mc.player, axisAlignedBB)) {
            if (!(axisAlignedBB2.maxY > d)) continue;
            d = axisAlignedBB2.maxY;
        }
        return (d -= Step.mc.player.posY) > 0.0 && d <= (double) stepHeight.getValue().floatValue() ? d : 0.0;
    }

    @Subscriber
    public void moveEvent(MoveEvent moveEvent) {
        if (mode.getValue() != StepMode.MOTION || !upwards.getValue().booleanValue()) {
            return;
        }
        if (!Step.mc.player.collidedHorizontally) {
            return;
        }
        if (Step.mc.player.onGround && this.Method394()) {
            Step.mc.player.motionY = 0.0;
            moveEvent.setY(0.41999998688698);
            this.Field2159 = 1;
        } else if (this.Field2159 == 1) {
            moveEvent.setY(0.33319999363422);
            this.Field2159 = 2;
        } else if (this.Field2159 == 2) {
            float f = Step.mc.player.rotationYaw;
            if (Step.mc.player.moveForward < 0.0f) {
                f += 180.0f;
            }
            float f2 = 1.0f;
            if (Step.mc.player.moveForward < 0.0f) {
                f2 = -0.5f;
            } else if (Step.mc.player.moveForward > 0.0f) {
                f2 = 0.5f;
            }
            if (Step.mc.player.moveStrafing > 0.0f) {
                f -= 90.0f * f2;
            }
            if (Step.mc.player.moveStrafing < 0.0f) {
                f += 90.0f * f2;
            }
            float f3 = (float)Math.toRadians(f);
            moveEvent.setY(0.24813599859094704);
            moveEvent.setX((double)(-MathHelper.sin(f3)) * 0.7);
            moveEvent.setZ((double)MathHelper.cos(f3) * 0.7);
            this.Field2159 = 0;
        }
    }

    @Subscriber
    public void onUpdate(UpdateEvent updateEvent) {
        Module module = ModuleManager.Method1612("Speed");
        if (module != null && module.isEnabled() && speedDisable.getValue().booleanValue()) {
            this.toggle();
        }
        if (reverse.getValue().booleanValue() && !ModuleManager.getModuleByClass(RubberFill.class).isEnabled() && this.Field2157 && !Step.mc.player.onGround && Step.mc.player.motionY <= 0.0) {
            if (!Step.mc.player.world.getCollisionBoxes(Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(0.0, -3.01, 0.0)).isEmpty() && !Step.mc.player.isInWater() && this.Field2160.GetDifferenceTiming(1000.0)) {
                Step.mc.player.motionY = -3.0;
            }
        }
        this.Field2157 = Step.mc.player.onGround;
        if (upwards.getValue().booleanValue() && !Step.mc.player.isInWater() && Step.mc.player.onGround && !Step.mc.player.isOnLadder() && !Step.mc.player.movementInput.jump && Step.mc.player.collidedVertically && (double) Step.mc.player.fallDistance < 0.1) {
            if (mode.getValue() == StepMode.VANILLA) {
                Step.mc.player.stepHeight = stepHeight.getValue().floatValue();
            } else if (mode.getValue() == StepMode.NORMAL) {
                if (!this.Field2160.GetDifferenceTiming(320.0)) {
                    return;
                }
                this.Field2161 = this.Method463();
                if (this.Field2161 == 0.0) {
                    return;
                }
                if (this.Field2161 <= 1.0) {
                    Step.mc.player.connection.sendPacket(new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 0.42, Step.mc.player.posZ, Step.mc.player.onGround));
                    Step.mc.player.connection.sendPacket(new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 0.75, Step.mc.player.posZ, Step.mc.player.onGround));
                    Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + 1.0, Step.mc.player.posZ);
                    return;
                }
                if (this.Field2161 <= (double) stepHeight.getValue().floatValue() && this.Field2161 <= 1.5) {
                    updateEvent.setCanceled(true);
                    Step.mc.player.connection.sendPacket(new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 0.42, Step.mc.player.posZ, Step.mc.player.onGround));
                    Step.mc.player.connection.sendPacket(new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 0.75, Step.mc.player.posZ, Step.mc.player.onGround));
                    Step.mc.player.connection.sendPacket(new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 1.0, Step.mc.player.posZ, Step.mc.player.onGround));
                    Step.mc.player.connection.sendPacket(new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 1.16, Step.mc.player.posZ, Step.mc.player.onGround));
                    Step.mc.player.connection.sendPacket(new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 1.23, Step.mc.player.posZ, Step.mc.player.onGround));
                    Step.mc.player.connection.sendPacket(new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 1.2, Step.mc.player.posZ, Step.mc.player.onGround));
                    Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + this.Field2161, Step.mc.player.posZ);
                    return;
                }
                if (this.Field2161 <= (double) stepHeight.getValue().floatValue()) {
                    updateEvent.setCanceled(true);
                    Step.mc.player.connection.sendPacket(new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 0.42, Step.mc.player.posZ, Step.mc.player.onGround));
                    Step.mc.player.connection.sendPacket(new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 0.7800000000000002, Step.mc.player.posZ, Step.mc.player.onGround));
                    Step.mc.player.connection.sendPacket(new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 0.63, Step.mc.player.posZ, Step.mc.player.onGround));
                    Step.mc.player.connection.sendPacket(new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 0.51, Step.mc.player.posZ, Step.mc.player.onGround));
                    Step.mc.player.connection.sendPacket(new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 0.9, Step.mc.player.posZ, Step.mc.player.onGround));
                    Step.mc.player.connection.sendPacket(new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 1.21, Step.mc.player.posZ, Step.mc.player.onGround));
                    Step.mc.player.connection.sendPacket(new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 1.45, Step.mc.player.posZ, Step.mc.player.onGround));
                    Step.mc.player.connection.sendPacket(new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 1.43, Step.mc.player.posZ, Step.mc.player.onGround));
                    Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + this.Field2161, Step.mc.player.posZ);
                }
            } else if (mode.getValue() == StepMode.NCP) {
                double[] dArray = PlayerUtil.Method1086(0.1);
                if (Step.mc.world.getCollisionBoxes(Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dArray[0], 1.0, dArray[1])).isEmpty() && !Step.mc.world.getCollisionBoxes(Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dArray[0], 0.6, dArray[1])).isEmpty() && (double) stepHeight.getValue().floatValue() >= 1.0) {
                    for (double d : Field2162) {
                        Step.mc.player.connection.sendPacket(new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + d, Step.mc.player.posZ, Step.mc.player.onGround));
                    }
                    if (useTimer.getValue().booleanValue()) {
                        KonasGlobals.INSTANCE.Field1134.Method746(this, 15, 0.6f);
                    }
                    Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + 1.0, Step.mc.player.posZ);
                    this.Field2158 = 1;
                }
                if (Step.mc.world.getCollisionBoxes(Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dArray[0], 1.6, dArray[1])).isEmpty() && !Step.mc.world.getCollisionBoxes(Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dArray[0], 1.4, dArray[1])).isEmpty() && (double) stepHeight.getValue().floatValue() >= 1.5) {
                    for (double d : Field2163) {
                        Step.mc.player.connection.sendPacket(new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + d, Step.mc.player.posZ, Step.mc.player.onGround));
                    }
                    if (useTimer.getValue().booleanValue()) {
                        KonasGlobals.INSTANCE.Field1134.Method746(this, 15, 0.35f);
                    }
                    Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + 1.5, Step.mc.player.posZ);
                    this.Field2158 = 1;
                }
                if (Step.mc.world.getCollisionBoxes(Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dArray[0], 2.1, dArray[1])).isEmpty() && !Step.mc.world.getCollisionBoxes(Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dArray[0], 1.9, dArray[1])).isEmpty() && (double) stepHeight.getValue().floatValue() >= 2.0) {
                    for (double d : Field2164) {
                        Step.mc.player.connection.sendPacket(new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + d, Step.mc.player.posZ, Step.mc.player.onGround));
                    }
                    if (useTimer.getValue().booleanValue()) {
                        KonasGlobals.INSTANCE.Field1134.Method746(this, 15, 0.25f);
                    }
                    Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + 2.0, Step.mc.player.posZ);
                    this.Field2158 = 2;
                }
                if (Step.mc.world.getCollisionBoxes(Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dArray[0], 2.6, dArray[1])).isEmpty() && !Step.mc.world.getCollisionBoxes(Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dArray[0], 2.4, dArray[1])).isEmpty() && (double) stepHeight.getValue().floatValue() >= 2.5) {
                    for (double d : Field2165) {
                        Step.mc.player.connection.sendPacket(new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + d, Step.mc.player.posZ, Step.mc.player.onGround));
                    }
                    if (useTimer.getValue().booleanValue()) {
                        KonasGlobals.INSTANCE.Field1134.Method746(this, 15, 0.15f);
                    }
                    Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + 2.5, Step.mc.player.posZ);
                    this.Field2158 = 2;
                }
            }
        } else if (mode.getValue() == StepMode.VANILLA) {
            Step.mc.player.stepHeight = 0.5f;
        }
    }

    @Subscriber
    public void onPacket(PacketEvent packetEvent) {
        block1: {
            if (!(packetEvent.getPacket() instanceof SPacketPlayerPosLook)) break block1;
            this.Field2160.UpdateCurrentTime();
            if (autoDisable.getValue().booleanValue()) {
                this.toggle();
            }
        }
    }
}
