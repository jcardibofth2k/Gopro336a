package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import java.util.Comparator;

import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.client.NewGui;
import me.darki.konas.module.combat.AutoCrystal;
import me.darki.konas.setting.Setting;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Class285
extends Module {
    public static Setting<Integer> Field1728 = new Setting<>("Range", 2, 3, 1, 1);
    public Class566 Field1729 = new Class566();
    public Class566 Field1730 = new Class566();
    public Class490 Field1731 = null;

    public boolean Method394() {
        for (int i = 0; i < 9; ++i) {
            ItemBlock itemBlock;
            ItemStack itemStack = Class285.mc.player.inventory.getStackInSlot(i);
            if (itemStack.isEmpty() || !(itemStack.getItem() instanceof ItemBlock) || (itemBlock = (ItemBlock)itemStack.getItem()).getBlock() != Blocks.ENDER_CHEST) continue;
            Class285.mc.player.inventory.currentItem = i;
            Class285.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(i));
            return true;
        }
        return false;
    }

    @Subscriber(priority=5)
    public void Method135(UpdateEvent updateEvent) {
        this.Field1731 = null;
        if (updateEvent.isCanceled() || !Class496.Method1966()) {
            return;
        }
        BlockPos blockPos = AutoCrystal.Method1578(new BlockPos((Entity)Class285.mc.player), ((Integer)Field1728.getValue()).intValue(), (Integer)Field1728.getValue(), false, true, 0).stream().filter(Class285::Method512).min(Comparator.comparing(Class285::Method1653)).orElse(null);
        if (blockPos != null) {
            if (this.Field1730.Method737(4000.0)) {
                boolean bl;
                boolean bl2 = bl = Class285.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_PICKAXE;
                if (!bl) {
                    for (int i = 0; i < 9; ++i) {
                        ItemStack itemStack = Class285.mc.player.inventory.getStackInSlot(i);
                        if (itemStack.isEmpty() || itemStack.getItem() != Items.DIAMOND_PICKAXE) continue;
                        bl = true;
                        Class285.mc.player.inventory.currentItem = i;
                        Class285.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(i));
                        break;
                    }
                }
                if (!bl) {
                    return;
                }
                EnumFacing enumFacing = Class285.mc.player.getHorizontalFacing().getOpposite();
                NewGui.INSTANCE.Field1139.Method1942(new Vec3d((double)blockPos.getX() + 0.5 + (double)enumFacing.getDirectionVec().getX() * 0.5, (double)blockPos.getY() + 0.5 + (double)enumFacing.getDirectionVec().getY() * 0.5, (double)blockPos.getZ() + 0.5 + (double)enumFacing.getDirectionVec().getZ() * 0.5));
                Class285.mc.player.swingArm(EnumHand.MAIN_HAND);
                Class285.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, enumFacing));
                Class285.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, enumFacing));
                this.Field1730.Method739();
            }
        } else if (this.Field1729.Method737(350.0)) {
            Object object;
            this.Field1729.Method739();
            if (Class285.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock ? (object = (ItemBlock)Class285.mc.player.getHeldItemMainhand().getItem()).getBlock() != Blocks.ENDER_CHEST && !this.Method394() : !this.Method394()) {
                return;
            }
            for (BlockPos blockPos2 : AutoCrystal.Method1578(new BlockPos((Entity)Class285.mc.player), ((Integer)Field1728.getValue()).intValue(), (Integer)Field1728.getValue(), false, true, 0)) {
                Class490 class490 = Class496.Method1962(blockPos2, true);
                if (class490 == null) continue;
                this.Field1731 = class490;
            }
        }
    }

    @Override
    public void onEnable() {
        this.Field1731 = null;
        this.Field1730.Method738(0L);
    }

    public static Double Method1653(BlockPos blockPos) {
        return Class285.mc.player.getDistance((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5);
    }

    public Class285() {
        super("AutoObsidian", "Automatically placed EChests and mines them", Category.MISC, new String[0]);
    }

    @Subscriber(priority=5)
    public void Method123(Class50 class50) {
        block0: {
            if (this.Field1731 == null) break block0;
            Class496.Method1958(this.Field1731, EnumHand.MAIN_HAND, false);
            this.Field1730.Method738(0L);
        }
    }

    public static boolean Method512(BlockPos blockPos) {
        return Class285.mc.world.getBlockState(blockPos).getBlock() instanceof BlockEnderChest;
    }
}
