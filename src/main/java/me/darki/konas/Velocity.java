package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.mixin.mixins.ISPacketEntityVelocity;
import me.darki.konas.mixin.mixins.ISPacketExplosion;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.world.World;

public class Velocity
extends Module {
    public static Setting<Float> horizontal = new Setting<>("Horizontal", Float.valueOf(0.0f), Float.valueOf(10.0f), Float.valueOf(0.0f), Float.valueOf(1.0f));
    public static Setting<Float> vertical = new Setting<>("Vertical", Float.valueOf(0.0f), Float.valueOf(10.0f), Float.valueOf(0.0f), Float.valueOf(1.0f));
    public static Setting<Boolean> noPush = new Setting<>("NoPush", true);
    public static Setting<Boolean> noHook = new Setting<>("NoHook", true);
    public static Setting<Boolean> noPiston = new Setting<>("NoPiston", false);

    @Subscriber
    public void Method503(MoveEvent moveEvent) {
        block0: {
            if (moveEvent.getMoverType() != MoverType.PISTON && moveEvent.getMoverType() != MoverType.SHULKER_BOX || !((Boolean) noPiston.getValue()).booleanValue()) break block0;
            moveEvent.setCanceled(true);
        }
    }

    @Subscriber
    public void Method461(Class572 class572) {
        block0: {
            if (!((Boolean) noPush.getValue()).booleanValue()) break block0;
            class572.setCanceled(true);
        }
    }

    @Subscriber
    public void Method2072(Class34 class34) {
        block1: {
            block2: {
                if (Velocity.mc.world == null || Velocity.mc.player == null) {
                    return;
                }
                if (!((Boolean) noPush.getValue()).booleanValue() || class34.Method286() != Velocity.mc.player) break block1;
                if (class34.Method285() != Class36.HORIZONTAL) break block2;
                class34.Method89(class34.Method88() * (double)((Float) horizontal.getValue()).floatValue());
                break block1;
            }
            if (class34.Method285() != Class36.VERTICAL) break block1;
            class34.Method89(class34.Method88() * (double)((Float) vertical.getValue()).floatValue());
        }
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        block5: {
            Entity entity;
            SPacketEntityStatus sPacketEntityStatus;
            block6: {
                block4: {
                    if (Velocity.mc.world == null || Velocity.mc.player == null) {
                        return;
                    }
                    if (!(packetEvent.getPacket() instanceof SPacketEntityVelocity)) break block4;
                    SPacketEntityVelocity sPacketEntityVelocity = (SPacketEntityVelocity) packetEvent.getPacket();
                    if (sPacketEntityVelocity.getEntityID() != Velocity.mc.player.getEntityId()) break block5;
                    if (((Float) horizontal.getValue()).floatValue() == 0.0f && ((Float) vertical.getValue()).floatValue() == 0.0f) {
                        packetEvent.setCanceled(true);
                    } else {
                        ((ISPacketEntityVelocity)sPacketEntityVelocity).Method76((int)((float)sPacketEntityVelocity.getMotionX() * ((Float) horizontal.getValue()).floatValue()));
                        ((ISPacketEntityVelocity)sPacketEntityVelocity).Method78((int)((float)sPacketEntityVelocity.getMotionY() * ((Float) vertical.getValue()).floatValue()));
                        ((ISPacketEntityVelocity)sPacketEntityVelocity).Method80((int)((float)sPacketEntityVelocity.getMotionZ() * ((Float) horizontal.getValue()).floatValue()));
                    }
                    break block5;
                }
                if (!(packetEvent.getPacket() instanceof SPacketExplosion)) break block6;
                SPacketExplosion sPacketExplosion = (SPacketExplosion) packetEvent.getPacket();
                ((ISPacketExplosion)sPacketExplosion).Method471((int)(sPacketExplosion.getMotionX() * ((Float) horizontal.getValue()).floatValue()));
                ((ISPacketExplosion)sPacketExplosion).Method473((int)(sPacketExplosion.getMotionY() * ((Float) vertical.getValue()).floatValue()));
                ((ISPacketExplosion)sPacketExplosion).Method475((int)(sPacketExplosion.getMotionZ() * ((Float) horizontal.getValue()).floatValue()));
                break block5;
            }
            if (!(packetEvent.getPacket() instanceof SPacketEntityStatus) || !((Boolean) noHook.getValue()).booleanValue() || (sPacketEntityStatus = (SPacketEntityStatus) packetEvent.getPacket()).getOpCode() != 31 || (entity = sPacketEntityStatus.getEntity((World) Velocity.mc.world)) == null || !(entity instanceof EntityFishHook)) break block5;
            EntityFishHook entityFishHook = (EntityFishHook)entity;
            if (entityFishHook.caughtEntity == Velocity.mc.player) {
                packetEvent.Cancel();
            }
        }
    }

    @Subscriber
    public void Method2073(Class4 class4) {
        block0: {
            if (!((Boolean) noPush.getValue()).booleanValue()) break block0;
            class4.setCanceled(true);
        }
    }

    public Velocity() {
        super("Velocity", Category.MOVEMENT, "AntiKnockback");
    }
}
