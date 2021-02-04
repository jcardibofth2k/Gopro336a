package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.mixin.mixins.IEntityPlayerSP;
import me.darki.konas.mixin.mixins.ISPacketPlayerPosLook;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class Class337
extends Module {
    public Setting<Boolean> Field288 = new Setting<>("Rotate", true);
    public Setting<Boolean> Field289 = new Setting<>("Swing", true);
    public Setting<Boolean> Field290 = new Setting<>("Strict", false);
    public Setting<Boolean> Field291 = new Setting<>("Skulls", true);
    public static Setting<Class443> Field292 = new Setting<>("CustomBlocks", new Class443());
    public static Setting<Class348> Field293 = new Setting<>("Filter", Class348.NONE);
    public Class346 Field294 = Class346.WAITING;
    public Class566 Field295 = new Class566();

    @Subscriber
    public void Method461(Class572 class572) {
        class572.setCanceled(true);
    }

    @Override
    public void onEnable() {
        if (Class337.mc.player == null || Class337.mc.world == null) {
            this.toggle();
            return;
        }
        if (!Class337.mc.player.onGround) {
            this.toggle();
            return;
        }
        this.Field294 = Class346.WAITING;
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        block1: {
            if (Class337.mc.currentScreen instanceof GuiDownloadTerrain) {
                this.toggle();
                return;
            }
            if (!(packetEvent.getPacket() instanceof SPacketPlayerPosLook) || this.Field290.getValue().booleanValue()) break block1;
            ((ISPacketPlayerPosLook) packetEvent.getPacket()).Method40(Class337.mc.player.rotationYaw);
            ((ISPacketPlayerPosLook) packetEvent.getPacket()).Method41(Class337.mc.player.rotationPitch);
        }
    }

    public Class337() {
        super("RubberFill", "Fills your own hole", Category.EXPLOIT, "Burrow", "SelfFill");
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        if (Class337.mc.player == null || Class337.mc.world == null) {
            return;
        }
        if (this.Field294 == Class346.DISABLING) {
            if (this.Field295.Method737(500.0)) {
                this.toggle();
            }
            return;
        }
        if (!Class337.mc.player.onGround) {
            this.toggle();
            return;
        }
        if (Class337.mc.world.getBlockState(new BlockPos(Class337.mc.player)).getBlock() == Blocks.AIR) {
            if (this.Field291.getValue().booleanValue() && Class337.mc.world.getBlockState(new BlockPos(Class337.mc.player).up(2)).getBlock() != Blocks.AIR) {
                if (this.getBlockInHotbar() == -1) {
                    this.toggle();
                    return;
                }
                BlockPos blockPos = new BlockPos(Class337.mc.player.posX, Class337.mc.player.posY, Class337.mc.player.posZ);
                BlockPos blockPos2 = blockPos.down();
                EnumFacing enumFacing = EnumFacing.UP;
                Vec3d vec3d = new Vec3d(blockPos2).add(0.5, 0.5, 0.5).add(new Vec3d(enumFacing.getDirectionVec()).scale(0.5));
                if (this.Field288.getValue().booleanValue()) {
                    if (((IEntityPlayerSP)Class337.mc.player).Method240() < 0.0f) {
                        Class337.mc.player.connection.sendPacket(new CPacketPlayer.Rotation(Class337.mc.player.rotationYaw, 0.0f, true));
                    }
                    Class337.mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(Class337.mc.player.posX, Class337.mc.player.posY, Class337.mc.player.posZ, Class337.mc.player.rotationYaw, 90.0f, true));
                    ((IEntityPlayerSP)Class337.mc.player).Method233(Class337.mc.player.posY + 1.16);
                    ((IEntityPlayerSP)Class337.mc.player).Method239(90.0f);
                }
                float f = (float)(vec3d.x - (double)blockPos.getX());
                float f2 = (float)(vec3d.y - (double)blockPos.getY());
                float f3 = (float)(vec3d.z - (double)blockPos.getZ());
                boolean bl = Class337.mc.player.inventory.currentItem != this.getBlockInHotbar();
                int n = Class337.mc.player.inventory.currentItem;
                if (bl) {
                    Class337.mc.player.inventory.currentItem = this.getBlockInHotbar();
                    Class337.mc.player.connection.sendPacket(new CPacketHeldItemChange(this.getBlockInHotbar()));
                }
                Class337.mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(blockPos2, enumFacing, EnumHand.MAIN_HAND, f, f2, f3));
                if (this.Field289.getValue().booleanValue()) {
                    Class337.mc.player.connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
                }
                if (bl) {
                    Class337.mc.player.inventory.currentItem = n;
                    Class337.mc.player.connection.sendPacket(new CPacketHeldItemChange(n));
                }
                this.Field295.Method739();
                this.Field294 = Class346.DISABLING;
                return;
            }
            if (this.Method464() == -1) {
                this.toggle();
                return;
            }
            BlockPos blockPos = new BlockPos(Class337.mc.player.posX, Class337.mc.player.posY, Class337.mc.player.posZ);
            BlockPos blockPos3 = blockPos.down();
            EnumFacing enumFacing = EnumFacing.UP;
            Vec3d vec3d = new Vec3d(blockPos3).add(0.5, 0.5, 0.5).add(new Vec3d(enumFacing.getDirectionVec()).scale(0.5));
            if (this.Field288.getValue().booleanValue()) {
                if (((IEntityPlayerSP)Class337.mc.player).Method240() < 0.0f) {
                    Class337.mc.player.connection.sendPacket(new CPacketPlayer.Rotation(Class337.mc.player.rotationYaw, 0.0f, true));
                }
                Class337.mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(Class337.mc.player.posX, Class337.mc.player.posY, Class337.mc.player.posZ, Class337.mc.player.rotationYaw, 90.0f, true));
                ((IEntityPlayerSP)Class337.mc.player).Method233(Class337.mc.player.posY + 1.16);
                ((IEntityPlayerSP)Class337.mc.player).Method239(90.0f);
            }
            Class337.mc.player.connection.sendPacket(new CPacketPlayer.Position(Class337.mc.player.posX, Class337.mc.player.posY + 0.42, Class337.mc.player.posZ, false));
            Class337.mc.player.connection.sendPacket(new CPacketPlayer.Position(Class337.mc.player.posX, Class337.mc.player.posY + 0.75, Class337.mc.player.posZ, false));
            Class337.mc.player.connection.sendPacket(new CPacketPlayer.Position(Class337.mc.player.posX, Class337.mc.player.posY + 1.01, Class337.mc.player.posZ, false));
            Class337.mc.player.connection.sendPacket(new CPacketPlayer.Position(Class337.mc.player.posX, Class337.mc.player.posY + 1.16, Class337.mc.player.posZ, false));
            if (mc.getCurrentServerData() != null && Class337.mc.getCurrentServerData().serverIP.toLowerCase().contains("crystalpvp")) {
                Class337.mc.player.connection.sendPacket(new CPacketPlayer.Position(Class337.mc.player.posX, Class337.mc.player.posY + 1.16, Class337.mc.player.posZ, false));
                Class337.mc.player.connection.sendPacket(new CPacketPlayer.Position(Class337.mc.player.posX, Class337.mc.player.posY + 1.24, Class337.mc.player.posZ, false));
            }
            float f = (float)(vec3d.x - (double)blockPos.getX());
            float f4 = (float)(vec3d.y - (double)blockPos.getY());
            float f5 = (float)(vec3d.z - (double)blockPos.getZ());
            boolean bl = Class337.mc.player.inventory.currentItem != this.Method464();
            int n = Class337.mc.player.inventory.currentItem;
            if (bl) {
                Class337.mc.player.inventory.currentItem = this.Method464();
                Class337.mc.player.connection.sendPacket(new CPacketHeldItemChange(this.Method464()));
            }
            Class337.mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(blockPos3, enumFacing, EnumHand.MAIN_HAND, f, f4, f5));
            if (this.Field289.getValue().booleanValue()) {
                Class337.mc.player.connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
            }
            if (bl) {
                Class337.mc.player.inventory.currentItem = n;
                Class337.mc.player.connection.sendPacket(new CPacketHeldItemChange(n));
            }
            Class337.mc.player.connection.sendPacket(new CPacketPlayer.Position(Class337.mc.player.posX, this.Method463(), Class337.mc.player.posZ, false));
            this.Field295.Method739();
            this.Field294 = Class346.DISABLING;
        } else {
            this.toggle();
        }
    }

    public double Method463() {
        BlockPos blockPos;
        if (mc.getCurrentServerData() != null) {
            if (Class337.mc.getCurrentServerData().serverIP.toLowerCase().contains("crystalpvp") || Class337.mc.getCurrentServerData().serverIP.toLowerCase().contains("2b2t")) {
                blockPos = new BlockPos(Class337.mc.player.posX, Class337.mc.player.posY + 2.34, Class337.mc.player.posZ);
                if (Class337.mc.world.getBlockState(blockPos).getBlock() instanceof BlockAir && Class337.mc.world.getBlockState(blockPos.up()).getBlock() instanceof BlockAir) {
                    return Class337.mc.player.posY + 2.34;
                }
            } else {
                if (Class337.mc.getCurrentServerData().serverIP.toLowerCase().contains("endcrystal")) {
                    if (Class337.mc.world.getBlockState(new BlockPos(Class337.mc.player.posX, Class337.mc.player.posY + 4.0, Class337.mc.player.posZ)).getBlock() instanceof BlockAir) {
                        return Class337.mc.player.posY + 4.0;
                    }
                    return Class337.mc.player.posY + 3.0;
                }
                if (Class337.mc.getCurrentServerData().serverIP.toLowerCase().contains("netheranarchy")) {
                    if (Class337.mc.world.getBlockState(new BlockPos(Class337.mc.player.posX, Class337.mc.player.posY + 8.5, Class337.mc.player.posZ)).getBlock() instanceof BlockAir) {
                        return Class337.mc.player.posY + 8.5;
                    }
                    return Class337.mc.player.posY + 9.5;
                }
                if (Class337.mc.getCurrentServerData().serverIP.toLowerCase().contains("9b9t")) {
                    BlockPos blockPos2 = new BlockPos(Class337.mc.player.posX, Class337.mc.player.posY + 9.0, Class337.mc.player.posZ);
                    if (Class337.mc.world.getBlockState(blockPos2).getBlock() instanceof BlockAir && Class337.mc.world.getBlockState(blockPos2.up()).getBlock() instanceof BlockAir) {
                        return Class337.mc.player.posY + 9.0;
                    }
                    for (int i = 10; i < 20; ++i) {
                        BlockPos blockPos3 = new BlockPos(Class337.mc.player.posX, Class337.mc.player.posY + (double)i, Class337.mc.player.posZ);
                        if (!(Class337.mc.world.getBlockState(blockPos3).getBlock() instanceof BlockAir) || !(Class337.mc.world.getBlockState(blockPos3.up()).getBlock() instanceof BlockAir)) continue;
                        return Class337.mc.player.posY + (double)i;
                    }
                    return Class337.mc.player.posY + 20.0;
                }
            }
        }
        if (Class337.mc.world.getBlockState(blockPos = new BlockPos(Class337.mc.player.posX, Class337.mc.player.posY - 9.0, Class337.mc.player.posZ)).getBlock() instanceof BlockAir && Class337.mc.world.getBlockState(blockPos.up()).getBlock() instanceof BlockAir) {
            return Class337.mc.player.posY - 9.0;
        }
        for (int i = -10; i > -20; --i) {
            BlockPos blockPos4 = new BlockPos(Class337.mc.player.posX, Class337.mc.player.posY - (double)i, Class337.mc.player.posZ);
            if (!(Class337.mc.world.getBlockState(blockPos4).getBlock() instanceof BlockAir) || !(Class337.mc.world.getBlockState(blockPos4.up()).getBlock() instanceof BlockAir)) continue;
            return Class337.mc.player.posY - (double)i;
        }
        return Class337.mc.player.posY - 24.0;
    }

    public int Method464() {
        int n = -1;
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = Class337.mc.player.inventory.getStackInSlot(i);
            if (itemStack == ItemStack.EMPTY || !(itemStack.getItem() instanceof ItemBlock)) continue;
            Block block = ((ItemBlock)itemStack.getItem()).getBlock();
            if (Field293.getValue() == Class348.BLACKLIST ? Field292.getValue().Method682().contains(block) : Field293.getValue() == Class348.WHITELIST && !Field292.getValue().Method682().contains(block)) continue;
            n = i;
            break;
        }
        return n;
    }

    public int getBlockInHotbar() {
        int n = -1;
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = Class337.mc.player.inventory.getStackInSlot(i);
            if (!(itemStack.getItem() instanceof ItemSkull)) continue;
            n = i;
            break;
        }
        return n;
    }
}
