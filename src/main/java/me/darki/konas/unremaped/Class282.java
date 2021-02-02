package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import java.util.List;

import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.combat.AutoCrystal;
import me.darki.konas.setting.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class Class282
extends Module {
    public Setting<Boolean> Field1793 = new Setting<>("Rotate", true);
    public Setting<Boolean> Field1794 = new Setting<>("Triggerable", true);
    public static Setting<Integer> Field1795 = new Setting<>("Range", 4, 10, 2, 1);
    public static Setting<Integer> Field1796 = new Setting<>("ActionShift", 1, 5, 1, 1);
    public static Setting<Integer> Field1797 = new Setting<>("ActionInterval", 15, 30, 5, 1);
    public BlockPos Field1798;
    public boolean Field1799;
    public boolean Field1800;
    public int Field1801;
    public int Field1802;
    public int Field1803;
    public int Field1804;
    public boolean Field1805 = false;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean Method393() {
        boolean bl = true;
        this.Field1799 = true;
        this.Field1800 = true;
        boolean bl2 = false;
        if (Class282.mc.world.getBlockState(this.Field1798) == null) {
            return false;
        }
        Block block = Class282.mc.world.getBlockState(this.Field1798).getBlock();
        if (block instanceof BlockTallGrass || block instanceof BlockDeadBush) {
            bl2 = true;
        }
        if (Class282.Method1520(this.Field1798.up()) == null) {
            return false;
        }
        for (BlockPos blockPos : Class276.Method1765()) {
            if (!this.Method515(this.Field1798.add((Vec3i)blockPos))) continue;
            bl = false;
        }
        for (BlockPos blockPos : Class276.Method1766()) {
            if (!this.Method515(this.Field1798.add((Vec3i)blockPos)) && !this.Method515(this.Field1798.add((Vec3i)blockPos.down()))) continue;
            this.Field1799 = false;
        }
        for (BlockPos blockPos : Class276.Method1767()) {
            if (!this.Method515(this.Field1798.add((Vec3i)blockPos)) && !this.Method515(this.Field1798.add((Vec3i)blockPos.down()))) continue;
            this.Field1800 = false;
        }
        for (BlockPos blockPos : Class276.Method1769()) {
            if (!this.Method515(this.Field1798.add((Vec3i)blockPos))) continue;
            this.Field1799 = false;
        }
        for (BlockPos blockPos : Class276.Method1768()) {
            if (!this.Method515(this.Field1798.add((Vec3i)blockPos))) continue;
            this.Field1800 = false;
        }
        if (bl2) return false;
        if (!bl) return false;
        if (this.Field1799) return true;
        if (!this.Field1800) return false;
        return true;
    }

    public Class282() {
        super("AutoWither", "Automatically places and spawns wither", Category.MISC, new String[0]);
    }

    @Subscriber(priority=40)
    public void Method135(UpdateEvent updateEvent) {
        if (this.Field1803 == 1) {
            Class490 class490;
            if (this.Field1805) {
                this.Field1805 = false;
            } else {
                this.Field1799 = false;
                this.Field1800 = false;
                if (!this.Method394()) {
                    this.toggle();
                    return;
                }
                List<BlockPos> list = AutoCrystal.Method1578(Class282.mc.player.getPosition().down(), ((Integer)Field1795.getValue()).intValue(), (Integer)Field1795.getValue(), false, true, 0);
                boolean bl = true;
                for (BlockPos blockPos : list) {
                    this.Field1798 = blockPos.down();
                    if (!this.Method393()) continue;
                    bl = false;
                    break;
                }
                if (bl) {
                    if (((Boolean)this.Field1794.getValue()).booleanValue()) {
                        this.toggle();
                    }
                    return;
                }
            }
            if (Class282.mc.player.inventory.currentItem != this.Field1801) {
                Class282.mc.player.inventory.currentItem = this.Field1801;
                Class282.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.Field1801));
            }
            int n = 0;
            for (BlockPos blockPos : Class276.Method1765()) {
                if (!Class496.Method1965(this.Field1798.add((Vec3i)blockPos), false, true) || (class490 = Class496.Method1961(this.Field1798.add((Vec3i)blockPos), (Boolean)this.Field1793.getValue(), true)) == null) continue;
                Class496.Method1957(class490, EnumHand.MAIN_HAND, false);
                if (++n < (Integer)Field1796.getValue()) continue;
                this.Field1805 = true;
                return;
            }
            if (this.Field1799) {
                for (BlockPos blockPos : Class276.Method1766()) {
                    if (!Class496.Method1965(this.Field1798.add((Vec3i)blockPos), false, true) || (class490 = Class496.Method1961(this.Field1798.add((Vec3i)blockPos), (Boolean)this.Field1793.getValue(), true)) == null) continue;
                    Class496.Method1957(class490, EnumHand.MAIN_HAND, false);
                    if (++n < (Integer)Field1796.getValue()) continue;
                    this.Field1805 = true;
                    return;
                }
            } else if (this.Field1800) {
                for (BlockPos blockPos : Class276.Method1767()) {
                    if (!Class496.Method1965(this.Field1798.add((Vec3i)blockPos), false, true) || (class490 = Class496.Method1961(this.Field1798.add((Vec3i)blockPos), (Boolean)this.Field1793.getValue(), true)) == null) continue;
                    Class496.Method1957(class490, EnumHand.MAIN_HAND, false);
                    if (++n < (Integer)Field1796.getValue()) continue;
                    this.Field1805 = true;
                    return;
                }
            }
            this.Field1803 = 2;
        } else if (this.Field1803 == 2) {
            if (Class282.mc.player.inventory.currentItem != this.Field1802) {
                Class282.mc.player.inventory.currentItem = this.Field1802;
                Class282.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.Field1802));
            }
            int n = 0;
            if (this.Field1799) {
                for (BlockPos blockPos : Class276.Method1769()) {
                    Class490 class490;
                    if (!Class496.Method1965(this.Field1798.add((Vec3i)blockPos), false, true) || (class490 = Class496.Method1961(this.Field1798.add((Vec3i)blockPos), (Boolean)this.Field1793.getValue(), true)) == null) continue;
                    Class496.Method1957(class490, EnumHand.MAIN_HAND, false);
                    if (++n < (Integer)Field1796.getValue()) continue;
                    return;
                }
            } else if (this.Field1800) {
                for (BlockPos blockPos : Class276.Method1768()) {
                    Class490 class490;
                    if (!Class496.Method1965(this.Field1798.add((Vec3i)blockPos), false, true) || (class490 = Class496.Method1961(this.Field1798.add((Vec3i)blockPos), (Boolean)this.Field1793.getValue(), true)) == null) continue;
                    Class496.Method1957(class490, EnumHand.MAIN_HAND, false);
                    if (++n < (Integer)Field1796.getValue()) continue;
                    return;
                }
            }
            if (((Boolean)this.Field1794.getValue()).booleanValue()) {
                this.toggle();
            }
            this.Field1803 = 3;
        } else if (this.Field1803 == 3) {
            if (this.Field1804 < (Integer)Field1797.getValue()) {
                ++this.Field1804;
            } else {
                this.Field1804 = 1;
                this.Field1803 = 1;
            }
        }
    }

    public boolean Method515(BlockPos blockPos) {
        Block block = Class282.mc.world.getBlockState(blockPos).getBlock();
        if (!(block instanceof BlockAir)) {
            return true;
        }
        for (Entity entity : Class282.mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(blockPos))) {
            if (entity instanceof EntityItem || entity instanceof EntityXPOrb) continue;
            return true;
        }
        return false;
    }

    @Override
    public void onEnable() {
        if (Class282.mc.player == null) {
            this.toggle();
            return;
        }
        this.Field1803 = 1;
        this.Field1804 = 1;
        this.Field1805 = false;
    }

    public boolean Method394() {
        this.Field1802 = -1;
        this.Field1801 = -1;
        for (int i = 0; i < 9; ++i) {
            Block block;
            ItemStack itemStack = Class282.mc.player.inventory.getStackInSlot(i);
            if (itemStack == ItemStack.EMPTY) continue;
            if (itemStack.getItem() == Items.SKULL && itemStack.getItemDamage() == 1) {
                if (Class282.mc.player.inventory.getStackInSlot(i).getCount() < 3) continue;
                this.Field1802 = i;
                continue;
            }
            if (!(itemStack.getItem() instanceof ItemBlock) || !((block = ((ItemBlock)itemStack.getItem()).getBlock()) instanceof BlockSoulSand) || Class282.mc.player.inventory.getStackInSlot(i).getCount() < 4) continue;
            this.Field1801 = i;
        }
        return this.Field1801 != -1 && this.Field1802 != -1;
    }

    public static EnumFacing Method1520(BlockPos blockPos) {
        for (EnumFacing enumFacing : EnumFacing.values()) {
            IBlockState iBlockState;
            BlockPos blockPos2 = blockPos.offset(enumFacing);
            if (!Class282.mc.world.getBlockState(blockPos2).getBlock().canCollideCheck(Class282.mc.world.getBlockState(blockPos2), false) || (iBlockState = Class282.mc.world.getBlockState(blockPos2)).getMaterial().isReplaceable() || iBlockState.getBlock() instanceof BlockTallGrass || iBlockState.getBlock() instanceof BlockDeadBush) continue;
            return enumFacing;
        }
        return null;
    }
}
