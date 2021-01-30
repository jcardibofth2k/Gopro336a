package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import me.darki.konas.module.Module;
import me.darki.konas.module.client.NewGui;
import net.minecraft.entity.Entity;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

public class Class407
extends Module {
    public double Field1182 = 0.0;
    public double Field1183 = 0.0;
    public boolean Field1184 = false;
    public int Field1185 = 4;

    @Subscriber
    public void Method503(MoveEvent moveEvent) {
        double d;
        if (Class407.mc.player.onGround || Class407.mc.player.isElytraFlying() || Class407.mc.player.capabilities.isFlying) {
            return;
        }
        if (this.Field1185 != 1 || Class407.mc.player.moveForward == 0.0f || Class407.mc.player.moveStrafing == 0.0f) {
            if (this.Field1185 == 2 && (Class407.mc.player.moveForward != 0.0f || Class407.mc.player.moveStrafing != 0.0f)) {
                this.Field1182 *= this.Field1184 ? 1.6835 : 1.395;
            } else if (this.Field1185 == 3) {
                d = 0.66 * (this.Field1183 - this.Method505());
                this.Field1182 = this.Field1183 - d;
                this.Field1184 = !this.Field1184;
            } else {
                List list = Class407.mc.world.getCollisionBoxes((Entity)Class407.mc.player, Class407.mc.player.getEntityBoundingBox().offset(0.0, Class407.mc.player.motionY, 0.0));
                if ((list.size() > 0 || Class407.mc.player.collidedVertically) && this.Field1185 > 0) {
                    this.Field1185 = Class407.mc.player.moveForward == 0.0f && Class407.mc.player.moveStrafing == 0.0f ? 0 : 1;
                }
                this.Field1182 = this.Field1183 - this.Field1183 / 159.0;
            }
        } else {
            this.Field1182 = 1.35 * this.Method505() - 0.01;
        }
        this.Field1182 = Math.max(this.Field1182, this.Method505());
        d = Class407.mc.player.movementInput.moveForward;
        double d2 = Class407.mc.player.movementInput.moveStrafe;
        float f = Class407.mc.player.rotationYaw;
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
            moveEvent.setX(d * this.Field1182 * Math.cos(Math.toRadians(f + 90.0f)) + d2 * this.Field1182 * Math.sin(Math.toRadians(f + 90.0f)));
            moveEvent.setZ(d * this.Field1182 * Math.sin(Math.toRadians(f + 90.0f)) - d2 * this.Field1182 * Math.cos(Math.toRadians(f + 90.0f)));
        }
        if (Class407.mc.player.moveForward == 0.0f && Class407.mc.player.moveStrafing == 0.0f) {
            return;
        }
        ++this.Field1185;
    }

    public double Method505() {
        double d = 0.2873;
        if (Class407.mc.player.isPotionActive(MobEffects.SPEED)) {
            int n = Class407.mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
            d *= 1.0 + 0.2 * ((double)n + 1.0);
        }
        return d;
    }

    @Subscriber
    public void Method135(UpdateEvent updateEvent) {
        if (!MathUtil.Method1080()) {
            Class407.mc.player.motionX = 0.0;
            Class407.mc.player.motionZ = 0.0;
            this.Field1182 = 0.0;
            return;
        }
        double d = Class407.mc.player.posX - Class407.mc.player.prevPosX;
        double d2 = Class407.mc.player.posZ - Class407.mc.player.prevPosZ;
        this.Field1183 = Math.sqrt(d * d + d2 * d2);
    }

    @Override
    public void onEnable() {
        if (Class407.mc.player == null || Class407.mc.world == null) {
            this.toggle();
            return;
        }
        this.Field1185 = 4;
        this.Field1182 = this.Method505();
        this.Field1183 = 0.0;
    }

    public double Method1166(double d) {
        BigDecimal bigDecimal = new BigDecimal(d);
        bigDecimal = bigDecimal.setScale(3, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        if (packetEvent.getPacket() instanceof SPacketPlayerPosLook) {
            NewGui.INSTANCE.Field1134.Method749(this);
            this.Field1182 = 0.0;
            this.Field1185 = 4;
            this.Field1183 = 0.0;
        }
    }

    public Class407() {
        super("AirStrafe", "Strafe, but only works in the air", Category.MOVEMENT, "AirMove");
    }
}
