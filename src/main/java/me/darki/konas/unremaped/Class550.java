package me.darki.konas;

import me.darki.konas.mixin.mixins.IEntityPlayerSP;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;

public class Class550 {
    public static Minecraft Field833 = Minecraft.getMinecraft();

    public static void Method883(float f, float f2) {
        block17: {
            boolean bl;
            boolean bl2;
            boolean bl3 = Class550.Field833.player.isSprinting();
            if (bl3 != ((IEntityPlayerSP)Class550.Field833.player).Method226()) {
                if (bl3) {
                    Class550.Field833.player.connection.sendPacket(new CPacketEntityAction(Class550.Field833.player, CPacketEntityAction.Action.START_SPRINTING));
                } else {
                    Class550.Field833.player.connection.sendPacket(new CPacketEntityAction(Class550.Field833.player, CPacketEntityAction.Action.STOP_SPRINTING));
                }
                ((IEntityPlayerSP)Class550.Field833.player).Method225(bl3);
            }
            if ((bl2 = Class550.Field833.player.isSneaking()) != ((IEntityPlayerSP)Class550.Field833.player).Method224()) {
                if (bl2) {
                    Class550.Field833.player.connection.sendPacket(new CPacketEntityAction(Class550.Field833.player, CPacketEntityAction.Action.START_SNEAKING));
                } else {
                    Class550.Field833.player.connection.sendPacket(new CPacketEntityAction(Class550.Field833.player, CPacketEntityAction.Action.STOP_SNEAKING));
                }
                ((IEntityPlayerSP)Class550.Field833.player).Method223(bl2);
            }
            if (Class550.Field833.player != Field833.getRenderViewEntity()) break block17;
            AxisAlignedBB axisAlignedBB = Class550.Field833.player.getEntityBoundingBox();
            double d = Class550.Field833.player.posX - ((IEntityPlayerSP)Class550.Field833.player).Method232();
            double d2 = axisAlignedBB.minY - ((IEntityPlayerSP)Class550.Field833.player).Method234();
            double d3 = Class550.Field833.player.posZ - ((IEntityPlayerSP)Class550.Field833.player).Method236();
            double d4 = f - ((IEntityPlayerSP)Class550.Field833.player).Method238();
            double d5 = f2 - ((IEntityPlayerSP)Class550.Field833.player).Method240();
            ((IEntityPlayerSP)Class550.Field833.player).Method241(((IEntityPlayerSP)Class550.Field833.player).Method242() + 1);
            boolean bl4 = d * d + d2 * d2 + d3 * d3 > 9.0E-4 || ((IEntityPlayerSP)Class550.Field833.player).Method242() >= 20;
            boolean bl5 = bl = d4 != 0.0 || d5 != 0.0;
            if (Class550.Field833.player.isRiding()) {
                Class550.Field833.player.connection.sendPacket(new CPacketPlayer.PositionRotation(Class550.Field833.player.motionX, -999.0, Class550.Field833.player.motionZ, f, f2, Class550.Field833.player.onGround));
                bl4 = false;
            } else if (bl4 && bl) {
                Class550.Field833.player.connection.sendPacket(new CPacketPlayer.PositionRotation(Class550.Field833.player.posX, axisAlignedBB.minY, Class550.Field833.player.posZ, f, f2, Class550.Field833.player.onGround));
            } else if (bl4) {
                Class550.Field833.player.connection.sendPacket(new CPacketPlayer.Position(Class550.Field833.player.posX, axisAlignedBB.minY, Class550.Field833.player.posZ, Class550.Field833.player.onGround));
            } else if (bl) {
                Class550.Field833.player.connection.sendPacket(new CPacketPlayer.Rotation(f, f2, Class550.Field833.player.onGround));
            } else if (((IEntityPlayerSP)Class550.Field833.player).Method228() != Class550.Field833.player.onGround) {
                Class550.Field833.player.connection.sendPacket(new CPacketPlayer(Class550.Field833.player.onGround));
            }
            if (bl4) {
                ((IEntityPlayerSP)Class550.Field833.player).Method231(Class550.Field833.player.posX);
                ((IEntityPlayerSP)Class550.Field833.player).Method233(axisAlignedBB.minY);
                ((IEntityPlayerSP)Class550.Field833.player).Method235(Class550.Field833.player.posZ);
                ((IEntityPlayerSP)Class550.Field833.player).Method241(0);
            }
            if (bl) {
                ((IEntityPlayerSP)Class550.Field833.player).Method237(f);
                ((IEntityPlayerSP)Class550.Field833.player).Method239(f2);
            }
            ((IEntityPlayerSP)Class550.Field833.player).Method227(Class550.Field833.player.onGround);
            ((IEntityPlayerSP)Class550.Field833.player).Method229(Class550.Field833.gameSettings.autoJump);
        }
    }
}
