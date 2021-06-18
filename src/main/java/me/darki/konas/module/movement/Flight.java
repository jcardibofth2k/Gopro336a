package me.darki.konas.module.movement;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.event.events.MoveEvent;
import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.mixin.mixins.ICPacketPlayer;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.SendPacketEvent;
import me.darki.konas.unremaped.Class418;
import me.darki.konas.unremaped.Class447;
import me.darki.konas.util.TimerUtil;
import me.darki.konas.util.math.Interpolation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class Flight
extends Module {
    public Setting<Boolean> noFall = new Setting<>("NoFall", false);
    public Setting<Boolean> useTimer = new Setting<>("UseTimer", false);
    public Setting<Float> acceleration = new Setting<>("Acceleration", Float.valueOf(0.5f), Float.valueOf(5.0f), Float.valueOf(0.05f), Float.valueOf(0.05f));
    public Setting<Float> vAcceleration = new Setting<>("VAcceleration", Float.valueOf(0.5f), Float.valueOf(5.0f), Float.valueOf(0.05f), Float.valueOf(0.05f));
    public Setting<Float> speed = new Setting<>("Speed", Float.valueOf(1.0f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(0.1f));
    public Setting<Float> vSpeed = new Setting<>("VSpeed", Float.valueOf(1.0f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(0.1f));
    public Setting<Float> upFactor = new Setting<>("UpFactor", Float.valueOf(0.5f), Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(0.1f));
    public Setting<Float> maxSpeed = new Setting<>("MaxSpeed", Float.valueOf(1.0f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(0.1f));
    public Setting<Class447> glide = new Setting<>("Glide", Class447.CONSTANT);
    public Setting<Float> glideSpeed = new Setting<>("GlideSpeed", Float.valueOf(1.0f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).visibleIf(this::Method396);
    public Setting<Integer> glideInterval = new Setting<>("GlideInterval", 3, 20, 1, 1).visibleIf(this::Method393);
    public Setting<Integer> glideTicks = new Setting<>("GlideTicks", 1, 5, 1, 1).visibleIf(this::Method388);
    public Setting<Class418> antiKick = new Setting<>("AntiKick", Class418.NONE);
    public Setting<Integer> antiKickInterval = new Setting<>("AntiKickInterval", 2, 20, 1, 1).visibleIf(this::Method394);
    public Setting<Integer> antiKickTicks = new Setting<>("AntiKickTicks", 1, 5, 1, 1).visibleIf(this::Method519);
    public Setting<Boolean> inAir = new Setting<>("InAir", true);
    public Setting<Boolean> inWater = new Setting<>("InWater", true);
    public Setting<Boolean> inLava = new Setting<>("InLava", true);
    public TimerUtil Field700 = new TimerUtil();
    public long Field701 = -1L;
    public int Field702 = 0;
    public int Field703 = 0;
    public int Field704 = 0;
    public int Field705 = 0;

    public boolean Method393() {
        return this.glide.getValue() == Class447.DYNAMIC;
    }

    @Override
    public void onEnable() {
        this.Field701 = System.currentTimeMillis();
        this.Field702 = 0;
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        if (packetEvent.getPacket() instanceof SPacketPlayerPosLook) {
            this.Field700.UpdateCurrentTime();
            this.Field701 = System.currentTimeMillis();
        }
    }

    public boolean Method394() {
        return this.antiKick.getValue() != Class418.NONE;
    }

    @Subscriber
    public void Method503(MoveEvent moveEvent) {
        AxisAlignedBB axisAlignedBB;
        if (!this.Field700.GetDifferenceTiming(350.0)) {
            return;
        }
        AxisAlignedBB axisAlignedBB2 = axisAlignedBB = Flight.mc.player.getRidingEntity() != null ? Flight.mc.player.getRidingEntity().getEntityBoundingBox() : Flight.mc.player.getEntityBoundingBox();
        if (axisAlignedBB != null) {
            int n = (int)axisAlignedBB.minY;
            for (int i = MathHelper.floor(axisAlignedBB.minX); i < MathHelper.floor(axisAlignedBB.maxX) + 1; ++i) {
                for (int j = MathHelper.floor(axisAlignedBB.minZ); j < MathHelper.floor(axisAlignedBB.maxZ) + 1; ++j) {
                    Block block = Flight.mc.world.getBlockState(new BlockPos(i, n, j)).getBlock();
                    if (block instanceof BlockAir && !this.inAir.getValue().booleanValue()) {
                        return;
                    }
                    if (!(block != Blocks.LAVA && block != Blocks.FLOWING_LAVA || this.inLava.getValue().booleanValue())) {
                        return;
                    }
                    if (block != Blocks.WATER && block != Blocks.FLOWING_WATER || this.inWater.getValue().booleanValue()) continue;
                    return;
                }
            }
        }
        double d = Interpolation.Method362(0.0, (double) this.speed.getValue().floatValue() * 0.2625, Math.min(1.0f, (float)(System.currentTimeMillis() - this.Field701) / (1000.0f * this.acceleration.getValue().floatValue())));
        double d2 = Interpolation.Method362(0.0, (double) this.vSpeed.getValue().floatValue() * 0.4, Math.min(1.0f, (float)(System.currentTimeMillis() - this.Field701) / (1000.0f * this.vAcceleration.getValue().floatValue())));
        boolean bl = true;
        if (this.antiKick.getValue() != Class418.NONE) {
            if (this.Field704 < this.antiKickInterval.getValue()) {
                ++this.Field704;
            } else {
                ++this.Field705;
                if (this.Field705 >= this.antiKickTicks.getValue()) {
                    this.Field704 = 0;
                }
                if (this.antiKick.getValue() == Class418.NORMAL) {
                    d = 0.0;
                    bl = false;
                } else {
                    return;
                }
            }
        }
        double d3 = Math.sqrt(d * d + d2 * d2);
        if (this.Field702 < this.glideInterval.getValue()) {
            ++this.Field702;
            this.Field703 = 0;
        }
        if (this.glide.getValue() == Class447.CONSTANT || this.Field702 >= this.glideInterval.getValue() && this.glide.getValue() == Class447.DYNAMIC) {
            moveEvent.setY((double)(-this.glideSpeed.getValue().floatValue()) * 0.01);
            ++this.Field703;
            if (this.Field703 >= this.glideTicks.getValue()) {
                this.Field702 = 0;
            }
        }
        if (Flight.mc.gameSettings.keyBindJump.isKeyDown() && bl) {
            moveEvent.setY(d2 * (double) this.upFactor.getValue().floatValue());
        } else if (Flight.mc.gameSettings.keyBindSneak.isKeyDown()) {
            moveEvent.setY(-d2);
        }
        if (d3 > (double) this.maxSpeed.getValue().floatValue() * 0.6625) {
            d = Math.min((double) this.speed.getValue().floatValue() * 0.2625, Math.sqrt(d3 * d3 - moveEvent.getY() * moveEvent.getY()));
        }
        double d4 = Flight.mc.player.movementInput.moveForward;
        double d5 = Flight.mc.player.movementInput.moveStrafe;
        float f = Flight.mc.player.rotationYaw;
        if (d4 == 0.0 && d5 == 0.0) {
            moveEvent.setX(0.0);
            moveEvent.setZ(0.0);
            this.Field701 = System.currentTimeMillis();
        } else {
            if (d4 != 0.0) {
                if (d5 > 0.0) {
                    f += (float)(d4 > 0.0 ? -45 : 45);
                } else if (d5 < 0.0) {
                    f += (float)(d4 > 0.0 ? 45 : -45);
                }
                d5 = 0.0;
                if (d4 > 0.0) {
                    d4 = 1.0;
                } else if (d4 < 0.0) {
                    d4 = -1.0;
                }
            }
            moveEvent.setX(d4 * d * Math.cos(Math.toRadians(f + 90.0f)) + d5 * d * Math.sin(Math.toRadians(f + 90.0f)));
            moveEvent.setZ(d4 * d * Math.sin(Math.toRadians(f + 90.0f)) - d5 * d * Math.cos(Math.toRadians(f + 90.0f)));
        }
    }

    //@Override
    public boolean Method396() {
        return this.glide.getValue() != Class447.OFF;
    }

    public Flight() {
        super("Flight", Category.MOVEMENT, "CreativeFly");
    }

    public boolean Method388() {
        return this.glide.getValue() == Class447.DYNAMIC;
    }

    public boolean Method519() {
        return this.glide.getValue() == Class447.DYNAMIC;
    }

    @Override
    public void onDisable() {
        KonasGlobals.INSTANCE.Field1134.Method749(this);
    }

    @Subscriber
    public void Method536(SendPacketEvent sendPacketEvent) {
        block0: {
            if (!(sendPacketEvent.getPacket() instanceof CPacketPlayer) || !this.noFall.getValue().booleanValue()) break block0;
            ((ICPacketPlayer) sendPacketEvent.getPacket()).setOnGround(true);
        }
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        if (this.useTimer.getValue().booleanValue()) {
            KonasGlobals.INSTANCE.Field1134.Method746(this, 10, 1.088f);
        } else {
            KonasGlobals.INSTANCE.Field1134.Method749(this);
        }
    }
}