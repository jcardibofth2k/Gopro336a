package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.mixin.mixins.ICPacketPlayerTryUseItemOnBlock;
import me.darki.konas.module.Module;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;

public class WorldBorder
extends Module {
    public WorldBorder() {
        super("WorldBorder", "Bypass World Border, only works on some servers", Category.EXPLOIT, new String[0]);
    }

    @Subscriber
    public void Method536(Class24 class24) {
        if (class24.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
            CPacketPlayerTryUseItemOnBlock cPacketPlayerTryUseItemOnBlock = (CPacketPlayerTryUseItemOnBlock)class24.getPacket();
            if (cPacketPlayerTryUseItemOnBlock.getPos().getY() >= 255 && cPacketPlayerTryUseItemOnBlock.getDirection() == EnumFacing.UP) {
                ((ICPacketPlayerTryUseItemOnBlock)cPacketPlayerTryUseItemOnBlock).Method30(EnumFacing.DOWN);
            } else if (!WorldBorder.mc.world.getWorldBorder().contains(cPacketPlayerTryUseItemOnBlock.getPos())) {
                switch (cPacketPlayerTryUseItemOnBlock.getDirection()) {
                    case EAST: {
                        ((ICPacketPlayerTryUseItemOnBlock)cPacketPlayerTryUseItemOnBlock).Method30(EnumFacing.WEST);
                        break;
                    }
                    case NORTH: {
                        ((ICPacketPlayerTryUseItemOnBlock)cPacketPlayerTryUseItemOnBlock).Method30(EnumFacing.SOUTH);
                        break;
                    }
                    case WEST: {
                        ((ICPacketPlayerTryUseItemOnBlock)cPacketPlayerTryUseItemOnBlock).Method30(EnumFacing.EAST);
                        break;
                    }
                    case SOUTH: {
                        ((ICPacketPlayerTryUseItemOnBlock)cPacketPlayerTryUseItemOnBlock).Method30(EnumFacing.NORTH);
                        break;
                    }
                }
            }
        }
    }
}
