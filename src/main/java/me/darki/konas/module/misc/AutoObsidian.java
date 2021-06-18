package me.darki.konas.module.misc;

import cookiedragon.eventsystem.Subscriber;
import java.util.Comparator;

import me.darki.konas.event.events.UpdateEvent;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.module.combat.AutoCrystal;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.Class490;
import me.darki.konas.util.rotation.Rotation;
import me.darki.konas.unremaped.Class50;
import me.darki.konas.util.TimerUtil;
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

public class AutoObsidian
extends Module {
    public static Setting<Integer> range = new Setting<>("Range", 2, 3, 1, 1);
    public TimerUtil Field1729 = new TimerUtil();
    public TimerUtil Field1730 = new TimerUtil();
    public Class490 Field1731 = null;

    public boolean Method394() {
        for (int i = 0; i < 9; ++i) {
            ItemBlock itemBlock;
            ItemStack itemStack = AutoObsidian.mc.player.inventory.getStackInSlot(i);
            if (itemStack.isEmpty() || !(itemStack.getItem() instanceof ItemBlock) || (itemBlock = (ItemBlock)itemStack.getItem()).getBlock() != Blocks.ENDER_CHEST) continue;
            AutoObsidian.mc.player.inventory.currentItem = i;
            AutoObsidian.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(i));
            return true;
        }
        return false;
    }

    @Subscriber(priority=5)
    public void Method135(UpdateEvent updateEvent) {
        this.Field1731 = null;
        if (updateEvent.isCanceled() || !Rotation.Method1966()) {
            return;
        }
        BlockPos blockPos = AutoCrystal.getPossiblePlacement(new BlockPos((Entity) AutoObsidian.mc.player), ((Integer) range.getValue()).intValue(), (Integer) range.getValue(), false, true, 0).stream().filter(AutoObsidian::Method512).min(Comparator.comparing(AutoObsidian::Method1653)).orElse(null);
        if (blockPos != null) {
            if (this.Field1730.GetDifferenceTiming(4000.0)) {
                boolean bl;
                boolean bl2 = bl = AutoObsidian.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_PICKAXE;
                if (!bl) {
                    for (int i = 0; i < 9; ++i) {
                        ItemStack itemStack = AutoObsidian.mc.player.inventory.getStackInSlot(i);
                        if (itemStack.isEmpty() || itemStack.getItem() != Items.DIAMOND_PICKAXE) continue;
                        bl = true;
                        AutoObsidian.mc.player.inventory.currentItem = i;
                        AutoObsidian.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(i));
                        break;
                    }
                }
                if (!bl) {
                    return;
                }
                EnumFacing enumFacing = AutoObsidian.mc.player.getHorizontalFacing().getOpposite();
                KonasGlobals.INSTANCE.Field1139.Method1942(new Vec3d((double)blockPos.getX() + 0.5 + (double)enumFacing.getDirectionVec().getX() * 0.5, (double)blockPos.getY() + 0.5 + (double)enumFacing.getDirectionVec().getY() * 0.5, (double)blockPos.getZ() + 0.5 + (double)enumFacing.getDirectionVec().getZ() * 0.5));
                AutoObsidian.mc.player.swingArm(EnumHand.MAIN_HAND);
                AutoObsidian.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, enumFacing));
                AutoObsidian.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, enumFacing));
                this.Field1730.UpdateCurrentTime();
            }
        } else if (this.Field1729.GetDifferenceTiming(350.0)) {
            this.Field1729.UpdateCurrentTime();
            if (AutoObsidian.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock ? ((ItemBlock)AutoObsidian.mc.player.getHeldItemMainhand().getItem()).getBlock() != Blocks.ENDER_CHEST && !this.Method394() : !this.Method394()) {
                return;
            }
            for (BlockPos blockPos2 : AutoCrystal.getPossiblePlacement(new BlockPos((Entity) AutoObsidian.mc.player), ((Integer) range.getValue()).intValue(), (Integer) range.getValue(), false, true, 0)) {
                Class490 class490 = Rotation.Method1962(blockPos2, true);
                if (class490 == null) continue;
                this.Field1731 = class490;
            }
        }
    }

    @Override
    public void onEnable() {
        this.Field1731 = null;
        this.Field1730.SetCurrentTime(0L);
    }

    public static Double Method1653(BlockPos blockPos) {
        return AutoObsidian.mc.player.getDistance((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5);
    }

    public AutoObsidian() {
        super("AutoObsidian", "Automatically placed EChests and mines them", Category.MISC, new String[0]);
    }

    @Subscriber(priority=5)
    public void Method123(Class50 class50) {
        block0: {
            if (this.Field1731 == null) break block0;
            Rotation.Method1958(this.Field1731, EnumHand.MAIN_HAND, false);
            this.Field1730.SetCurrentTime(0L);
        }
    }

    public static boolean Method512(BlockPos blockPos) {
        return AutoObsidian.mc.world.getBlockState(blockPos).getBlock() instanceof BlockEnderChest;
    }
}
