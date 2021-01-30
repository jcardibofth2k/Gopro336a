package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import me.darki.konas.command.Logger;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class Class288
extends Module {
    public static Setting<Float> Field1693 = new Setting<>("TargetRange", Float.valueOf(4.5f), Float.valueOf(16.0f), Float.valueOf(1.0f), Float.valueOf(0.1f));
    public static Setting<Integer> Field1694 = new Setting<>("ActionShift", 3, 8, 1, 1);
    public static Setting<Integer> Field1695 = new Setting<>("ActionInterval", 0, 10, 0, 1);
    public static Setting<Boolean> Field1696 = new Setting<>("Top", true);
    public static Setting<Boolean> Field1697 = new Setting<>("Piston", false);
    public static Setting<Class537> Field1698 = new Setting<>("Self", new Class537(0));
    public static Setting<Boolean> Field1699 = new Setting<>("Strict", false);
    public static Setting<Boolean> Field1700 = new Setting<>("Rotate", true);
    public static Setting<Boolean> Field1701 = new Setting<>("DisableWhenDone", false);
    public static Setting<Boolean> Field1702 = new Setting<>("LogoutSpots", false);
    public int Field1703;
    public Class566 Field1704 = new Class566();
    public BlockPos Field1705;
    public int Field1706 = 0;
    public BlockPos Field1707 = null;
    public Class490 Field1708;
    public Class490 Field1709;
    public Class566 Field1710 = new Class566();
    public static ConcurrentHashMap<BlockPos, Long> Field1711 = new ConcurrentHashMap();

    public int Method464() {
        int n = -1;
        for (int i = 0; i < 9; ++i) {
            Block block;
            ItemStack itemStack = Class288.mc.player.inventory.getStackInSlot(i);
            if (itemStack == ItemStack.EMPTY || !(itemStack.getItem() instanceof ItemBlock) || !((block = ((ItemBlock)itemStack.getItem()).getBlock()) instanceof BlockObsidian)) continue;
            n = i;
            break;
        }
        return n;
    }

    @Subscriber
    public void Method139(Class89 class89) {
        block1: {
            if (Class288.mc.world == null || Class288.mc.player == null) {
                return;
            }
            if (this.Field1705 == null || this.Field1704.Method737(500.0)) break block1;
            Class523.Method1218(this.Field1705);
        }
    }

    public boolean Method132(EntityPlayer entityPlayer) {
        BlockPos blockPos = new BlockPos(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ).down();
        Block block = Class288.mc.world.getBlockState(blockPos).getBlock();
        return !(block instanceof BlockAir) && !(block instanceof BlockLiquid);
    }

    @Override
    public void onEnable() {
        if (Class288.mc.player == null || Class288.mc.world == null) {
            this.toggle();
            return;
        }
        this.Field1705 = null;
        this.Field1707 = null;
        this.Field1708 = null;
        this.Field1709 = null;
        this.Field1706 = (Integer)Field1695.getValue();
    }

    @Subscriber(priority=70)
    public void Method135(UpdateEvent updateEvent) {
        int n;
        if (this.Field1708 != null) {
            this.Field1709 = this.Field1708;
            this.Field1710.Method739();
        }
        this.Field1708 = null;
        this.Field1707 = null;
        int n2 = Class475.Method2142();
        Field1711.forEach((arg_0, arg_1) -> Class288.Method1052(n2, arg_0, arg_1));
        if (updateEvent.isCanceled() || !Class496.Method1959((Boolean)Field1700.getValue())) {
            return;
        }
        if (!(!((Boolean)Field1699.getValue()).booleanValue() || Class288.mc.player.onGround && Class288.mc.player.collidedVertically)) {
            return;
        }
        if (Class167.Method1610(PacketFly.class).Method1651()) {
            return;
        }
        if (this.Field1706 < (Integer)Field1695.getValue()) {
            ++this.Field1706;
        }
        if ((n = this.Method464()) == -1) {
            Logger.Method1119("No Obby Found!");
            this.toggle();
            return;
        }
        this.Field1703 = n;
        EntityPlayer entityPlayer = this.Method1623();
        if (entityPlayer == null) {
            return;
        }
        if (this.Field1706 < (Integer)Field1695.getValue()) {
            if (this.Field1709 != null && !this.Field1710.Method737(650.0)) {
                NewGui.INSTANCE.Field1139.Method1937(this.Field1709.Method1979(), this.Field1709.Method1978());
            }
            return;
        }
        this.Field1707 = new BlockPos(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ);
        BlockPos blockPos = this.Method1626(this.Field1707);
        if (blockPos != null) {
            this.Field1708 = Class496.Method1962(blockPos, (Boolean)Field1700.getValue());
            if (this.Field1708 != null) {
                Field1711.put(blockPos, System.currentTimeMillis());
                this.Field1706 = 0;
                this.Field1705 = blockPos;
                this.Field1704.Method739();
            }
        } else if (((Boolean)Field1701.getValue()).booleanValue()) {
            this.toggle();
            return;
        }
    }

    public boolean Method1572(BlockPos blockPos, EnumFacing enumFacing) {
        block3: {
            PistonAura pistonAura;
            block2: {
                pistonAura = (PistonAura)Class167.Method1610(PistonAura.class);
                if (pistonAura.Field373 == null) break block2;
                if (pistonAura.Field374.equals((Object)enumFacing)) {
                    return false;
                }
                break block3;
            }
            pistonAura.Method522(blockPos);
            if (pistonAura.Field373 == null) break block3;
            if (pistonAura.Field374.equals((Object)enumFacing)) {
                this.Method1625(pistonAura);
                return false;
            }
            this.Method1625(pistonAura);
        }
        return true;
    }

    public static Float Method1622(EntityPlayer entityPlayer) {
        return Float.valueOf(Class288.mc.player.getDistance((Entity)entityPlayer));
    }

    public EntityPlayer Method1623() {
        Waypoints waypoints = (Waypoints)Class167.Method1610(Waypoints.class);
        Stream stream = Class288.mc.world.playerEntities.stream();
        if (((Boolean)Field1702.getValue()).booleanValue()) {
            stream = Stream.concat(Class288.mc.world.playerEntities.stream(), waypoints.Method1799().keySet().stream());
        }
        return stream.filter(Class288::Method128).filter(Class288::Method122).filter(Class288::Method141).filter(Class288::Method126).filter(this::Method132).min(Comparator.comparing(Class288::Method1622)).orElse((EntityPlayer)(MathUtil.Method1087(((Class537)Field1698.getValue()).Method851()) ? Class288.mc.player : null));
    }

    public Class288() {
        super("AutoTrap", 0, Category.COMBAT, "Trapper", "AutoTrapper");
    }

    public static boolean Method122(EntityPlayer entityPlayer) {
        return entityPlayer != Class288.mc.player && entityPlayer != mc.getRenderViewEntity();
    }

    public static void Method1052(int n, BlockPos blockPos, Long l) {
        if (System.currentTimeMillis() - l > (long)(n + 100)) {
            Field1711.remove(blockPos);
        }
    }

    public static boolean Method128(EntityPlayer entityPlayer) {
        return !Class546.Method963((Entity)entityPlayer);
    }

    public static boolean Method126(EntityPlayer entityPlayer) {
        return Class288.mc.player.getDistance((Entity)entityPlayer) < Math.max(((Float)Field1693.getValue()).floatValue() - 1.0f, 1.0f);
    }

    public boolean Method1624(BlockPos blockPos, boolean bl) {
        return Class496.Method1967(blockPos, bl) && !Field1711.containsKey(blockPos);
    }

    public static boolean Method141(EntityPlayer entityPlayer) {
        return !Class492.Method1988(entityPlayer.getUniqueID().toString());
    }

    public void Method1625(PistonAura pistonAura) {
        pistonAura.Field373 = null;
        pistonAura.Field374 = null;
        pistonAura.Field377 = null;
        pistonAura.Field376 = null;
        pistonAura.Field375 = null;
    }

    @Subscriber(priority=70)
    public void Method123(Class50 class50) {
        block9: {
            if (this.Field1708 == null || this.Field1707 == null || this.Field1703 == -1) break block9;
            boolean bl = Class288.mc.player.inventory.currentItem != this.Field1703;
            int n = Class288.mc.player.inventory.currentItem;
            if (bl) {
                Class288.mc.player.inventory.currentItem = this.Field1703;
                Class288.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.Field1703));
            }
            boolean bl2 = Class288.mc.player.isSprinting();
            boolean bl3 = Class545.Method1004(this.Field1708.Method1982());
            if (bl2) {
                Class288.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Class288.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
            }
            if (bl3) {
                Class288.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Class288.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            Class496.Method1957(this.Field1708, EnumHand.MAIN_HAND, true);
            for (int i = 0; i < (Integer)Field1694.getValue() - 1; ++i) {
                BlockPos blockPos = this.Method1626(this.Field1707);
                if (blockPos != null) {
                    Class490 class490 = Class496.Method1961(blockPos, (Boolean)Field1700.getValue(), true);
                    if (class490 == null) break;
                    this.Field1708 = class490;
                    Field1711.put(blockPos, System.currentTimeMillis());
                    Class496.Method1957(this.Field1708, EnumHand.MAIN_HAND, true);
                    this.Field1705 = blockPos;
                    this.Field1704.Method739();
                    continue;
                }
                if (!((Boolean)Field1701.getValue()).booleanValue()) break;
                this.toggle();
                if (bl) {
                    Class288.mc.player.inventory.currentItem = n;
                    Class288.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(n));
                }
                return;
            }
            if (bl3) {
                Class288.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Class288.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            if (bl2) {
                Class288.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Class288.mc.player, CPacketEntityAction.Action.START_SPRINTING));
            }
            if (bl) {
                Class288.mc.player.inventory.currentItem = n;
                Class288.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(n));
            }
        }
    }

    public BlockPos Method1626(BlockPos blockPos) {
        BlockPos blockPos2;
        double d;
        double d2;
        BlockPos blockPos3;
        for (EnumFacing enumFacing : EnumFacing.HORIZONTALS) {
            blockPos3 = null;
            d2 = 0.0;
            if (this.Method1624(blockPos.offset(enumFacing).down(), true) && (d = Class288.mc.player.getDistance((double)(blockPos2 = blockPos.offset(enumFacing).down()).getX() + 0.5, (double)blockPos2.getY() + 0.5, (double)blockPos2.getZ() + 0.5)) >= d2) {
                blockPos3 = blockPos2;
                d2 = d;
            }
            if (blockPos3 == null) continue;
            return blockPos3;
        }
        for (EnumFacing enumFacing : EnumFacing.HORIZONTALS) {
            blockPos3 = null;
            d2 = 0.0;
            if (this.Method1624(blockPos.offset(enumFacing), false) && (d = Class288.mc.player.getDistance((double)(blockPos2 = blockPos.offset(enumFacing)).getX() + 0.5, (double)blockPos2.getY() + 0.5, (double)blockPos2.getZ() + 0.5)) >= d2) {
                blockPos3 = blockPos2;
                d2 = d;
            }
            if (blockPos3 == null) continue;
            return blockPos3;
        }
        Block block = EnumFacing.HORIZONTALS;
        int n = ((EnumFacing[])block).length;
        for (int i = 0; i < n; ++i) {
            EnumFacing enumFacing;
            enumFacing = block[i];
            blockPos3 = null;
            d2 = 0.0;
            if (this.Method1624(blockPos.up().offset(enumFacing), false) && (!((Boolean)Field1697.getValue()).booleanValue() || this.Method1572(blockPos.up(), enumFacing)) && (d = Class288.mc.player.getDistance((double)(blockPos2 = blockPos.up().offset(enumFacing)).getX() + 0.5, (double)blockPos2.getY() + 0.5, (double)blockPos2.getZ() + 0.5)) >= d2) {
                blockPos3 = blockPos2;
                d2 = d;
            }
            if (blockPos3 == null) continue;
            return blockPos3;
        }
        if (((Boolean)Field1696.getValue()).booleanValue() && ((block = Class288.mc.world.getBlockState(blockPos.up().up()).getBlock()) instanceof BlockAir || block instanceof BlockLiquid)) {
            if (this.Method1624(blockPos.up().up(), false)) {
                return blockPos.up().up();
            }
            BlockPos blockPos4 = blockPos.up().up().offset(EnumFacing.byHorizontalIndex((int)(MathHelper.floor((double)((double)(Class288.mc.player.rotationYaw * 4.0f / 360.0f) + 0.5)) & 3)));
            if (this.Method1624(blockPos4, false)) {
                return blockPos4;
            }
        }
        return null;
    }
}
