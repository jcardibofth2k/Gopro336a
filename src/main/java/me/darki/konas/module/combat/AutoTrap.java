package me.darki.konas.module.combat;

import cookiedragon.eventsystem.Subscriber;
import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import me.darki.konas.module.ModuleManager;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.module.movement.PacketFly;
import me.darki.konas.module.render.Waypoints;
import me.darki.konas.unremaped.*;
import me.darki.konas.util.CrystalUtils;
import me.darki.konas.util.PlayerUtil;
import me.darki.konas.event.events.UpdateEvent;
import me.darki.konas.command.Logger;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.util.TimerUtil;
import me.darki.konas.util.rotation.Rotation;
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

public class AutoTrap
extends Module {
    public static Setting<Float> targetRange = new Setting<>("TargetRange", 4.5f, 16.0f, 1.0f, 0.1f);
    public static Setting<Integer> actionShift = new Setting<>("ActionShift", 3, 8, 1, 1);
    public static Setting<Integer> actionInterval = new Setting<>("ActionInterval", 0, 10, 0, 1);
    public static Setting<Boolean> top = new Setting<>("Top", true);
    public static Setting<Boolean> piston = new Setting<>("Piston", false);
    public static Setting<Class537> self = new Setting<>("Self", new Class537(0));
    public static Setting<Boolean> strict = new Setting<>("Strict", false);
    public static Setting<Boolean> rotate = new Setting<>("Rotate", true);
    public static Setting<Boolean> disableWhenDone = new Setting<>("DisableWhenDone", false);
    public static Setting<Boolean> logoutSpots = new Setting<>("LogoutSpots", false);
    public int Field1703;
    public TimerUtil Field1704 = new TimerUtil();
    public BlockPos Field1705;
    public int Field1706 = 0;
    public BlockPos Field1707 = null;
    public Class490 Field1708;
    public Class490 Field1709;
    public TimerUtil Field1710 = new TimerUtil();
    public static ConcurrentHashMap<BlockPos, Long> Field1711 = new ConcurrentHashMap();

    public int Method464() {
        int n = -1;
        for (int i = 0; i < 9; ++i) {
            Block block;
            ItemStack itemStack = AutoTrap.mc.player.inventory.getStackInSlot(i);
            if (itemStack == ItemStack.EMPTY || !(itemStack.getItem() instanceof ItemBlock) || !((block = ((ItemBlock)itemStack.getItem()).getBlock()) instanceof BlockObsidian)) continue;
            n = i;
            break;
        }
        return n;
    }

    @Subscriber
    public void Method139(Render3DEvent render3DEvent) {
        block1: {
            if (AutoTrap.mc.world == null || AutoTrap.mc.player == null) {
                return;
            }
            if (this.Field1705 == null || this.Field1704.GetDifferenceTiming(500.0)) break block1;
            Class523.Method1218(this.Field1705);
        }
    }

    public boolean Method132(EntityPlayer entityPlayer) {
        BlockPos blockPos = new BlockPos(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ).down();
        Block block = AutoTrap.mc.world.getBlockState(blockPos).getBlock();
        return !(block instanceof BlockAir) && !(block instanceof BlockLiquid);
    }

    @Override
    public void onEnable() {
        if (AutoTrap.mc.player == null || AutoTrap.mc.world == null) {
            this.toggle();
            return;
        }
        this.Field1705 = null;
        this.Field1707 = null;
        this.Field1708 = null;
        this.Field1709 = null;
        this.Field1706 = (Integer)actionInterval.getValue();
    }

    @Subscriber(priority=70)
    public void Method135(UpdateEvent updateEvent) {
        int n;
        if (this.Field1708 != null) {
            this.Field1709 = this.Field1708;
            this.Field1710.UpdateCurrentTime();
        }
        this.Field1708 = null;
        this.Field1707 = null;
        int n2 = CrystalUtils.Method2142();
        Field1711.forEach((arg_0, arg_1) -> AutoTrap.Method1052(n2, arg_0, arg_1));
        if (updateEvent.isCanceled() || !Rotation.Method1959((Boolean)rotate.getValue())) {
            return;
        }
        if (!(!((Boolean)strict.getValue()).booleanValue() || AutoTrap.mc.player.onGround && AutoTrap.mc.player.collidedVertically)) {
            return;
        }
        if (ModuleManager.getModuleByClass(PacketFly.class).isEnabled()) {
            return;
        }
        if (this.Field1706 < (Integer)actionInterval.getValue()) {
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
        if (this.Field1706 < (Integer)actionInterval.getValue()) {
            if (this.Field1709 != null && !this.Field1710.GetDifferenceTiming(650.0)) {
                KonasGlobals.INSTANCE.Field1139.Method1937(this.Field1709.Method1979(), this.Field1709.Method1978());
            }
            return;
        }
        this.Field1707 = new BlockPos(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ);
        BlockPos blockPos = this.Method1626(this.Field1707);
        if (blockPos != null) {
            this.Field1708 = Rotation.Method1962(blockPos, rotate.getValue());
            if (this.Field1708 != null) {
                Field1711.put(blockPos, System.currentTimeMillis());
                this.Field1706 = 0;
                this.Field1705 = blockPos;
                this.Field1704.UpdateCurrentTime();
            }
        } else if ((Boolean) disableWhenDone.getValue()) {
            this.toggle();
            return;
        }
    }

    public boolean Method1572(BlockPos blockPos, EnumFacing enumFacing) {
        block3: {
            PistonAura pistonAura;
            block2: {
                pistonAura = (PistonAura) ModuleManager.getModuleByClass(PistonAura.class);
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
        return Float.valueOf(AutoTrap.mc.player.getDistance((Entity)entityPlayer));
    }

    public EntityPlayer Method1623() {
        Waypoints waypoints = (Waypoints) ModuleManager.getModuleByClass(Waypoints.class);
        Stream<EntityPlayer> stream = AutoTrap.mc.world.playerEntities.stream();
        if (((Boolean)logoutSpots.getValue()).booleanValue()) {
            stream = Stream.concat(AutoTrap.mc.world.playerEntities.stream(), waypoints.Method1799().keySet().stream());
        }
        return stream.filter(AutoTrap::Method128).filter(AutoTrap::Method122).filter(AutoTrap::Method141).filter(AutoTrap::Method126).filter(this::Method132).min(Comparator.comparing(AutoTrap::Method1622)).orElse((EntityPlayer)(PlayerUtil.Method1087(((Class537)self.getValue()).Method851()) ? AutoTrap.mc.player : null));
    }

    public AutoTrap() {
        super("AutoTrap", 0, Category.COMBAT, "Trapper", "AutoTrapper");
    }

    public static boolean Method122(EntityPlayer entityPlayer) {
        return entityPlayer != AutoTrap.mc.player && entityPlayer != mc.getRenderViewEntity();
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
        return AutoTrap.mc.player.getDistance((Entity)entityPlayer) < Math.max(((Float)targetRange.getValue()).floatValue() - 1.0f, 1.0f);
    }

    public boolean Method1624(BlockPos blockPos, boolean bl) {
        return Rotation.Method1967(blockPos, bl) && !Field1711.containsKey(blockPos);
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
            boolean bl = AutoTrap.mc.player.inventory.currentItem != this.Field1703;
            int n = AutoTrap.mc.player.inventory.currentItem;
            if (bl) {
                AutoTrap.mc.player.inventory.currentItem = this.Field1703;
                AutoTrap.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.Field1703));
            }
            boolean bl2 = AutoTrap.mc.player.isSprinting();
            boolean bl3 = Class545.Method1004(this.Field1708.Method1982());
            if (bl2) {
                AutoTrap.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity) AutoTrap.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
            }
            if (bl3) {
                AutoTrap.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity) AutoTrap.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            Rotation.Method1957(this.Field1708, EnumHand.MAIN_HAND, true);
            for (int i = 0; i < (Integer)actionShift.getValue() - 1; ++i) {
                BlockPos blockPos = this.Method1626(this.Field1707);
                if (blockPos != null) {
                    Class490 class490 = Rotation.Method1961(blockPos, (Boolean)rotate.getValue(), true);
                    if (class490 == null) break;
                    this.Field1708 = class490;
                    Field1711.put(blockPos, System.currentTimeMillis());
                    Rotation.Method1957(this.Field1708, EnumHand.MAIN_HAND, true);
                    this.Field1705 = blockPos;
                    this.Field1704.UpdateCurrentTime();
                    continue;
                }
                if (!((Boolean)disableWhenDone.getValue()).booleanValue()) break;
                this.toggle();
                if (bl) {
                    AutoTrap.mc.player.inventory.currentItem = n;
                    AutoTrap.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(n));
                }
                return;
            }
            if (bl3) {
                AutoTrap.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity) AutoTrap.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            if (bl2) {
                AutoTrap.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity) AutoTrap.mc.player, CPacketEntityAction.Action.START_SPRINTING));
            }
            if (bl) {
                AutoTrap.mc.player.inventory.currentItem = n;
                AutoTrap.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(n));
            }
        }
    }

    public BlockPos Method1626(final BlockPos blockPos) {
        for (final EnumFacing enumFacing : EnumFacing.HORIZONTALS) {
            BlockPos blockPos2 = null;
            final double n = 0.0;
            if (this.Method1624(blockPos.offset(enumFacing).down(), true)) {
                final BlockPos down = blockPos.offset(enumFacing).down();
                if (mc.player.getDistance(down.getX() + 0.5, down.getY() + 0.5, down.getZ() + 0.5) >= n) {
                    blockPos2 = down;
                }
            }
            if (blockPos2 != null) {
                return blockPos2;
            }
        }
        for (final EnumFacing enumFacing2 : EnumFacing.HORIZONTALS) {
            BlockPos blockPos3 = null;
            final double n2 = 0.0;
            if (this.Method1624(blockPos.offset(enumFacing2), false)) {
                final BlockPos offset = blockPos.offset(enumFacing2);
                if (mc.player.getDistance(offset.getX() + 0.5, offset.getY() + 0.5, offset.getZ() + 0.5) >= n2) {
                    blockPos3 = offset;
                }
            }
            if (blockPos3 != null) {
                return blockPos3;
            }
        }
        for (final EnumFacing enumFacing3 : EnumFacing.HORIZONTALS) {
            BlockPos blockPos4 = null;
            final double n3 = 0.0;
            if (this.Method1624(blockPos.up().offset(enumFacing3), false) && (!(boolean)piston.getValue() || this.Method1572(blockPos.up(), enumFacing3))) {
                final BlockPos offset2 = blockPos.up().offset(enumFacing3);
                if (mc.player.getDistance(offset2.getX() + 0.5, offset2.getY() + 0.5, offset2.getZ() + 0.5) >= n3) {
                    blockPos4 = offset2;
                }
            }
            if (blockPos4 != null) {
                return blockPos4;
            }
        }
        if (top.getValue()) {
            final Block block = mc.world.getBlockState(blockPos.up().up()).getBlock();
            if (block instanceof BlockAir || block instanceof BlockLiquid) {
                if (this.Method1624(blockPos.up().up(), false)) {
                    return blockPos.up().up();
                }
                final BlockPos offset3 = blockPos.up().up().offset(EnumFacing.getHorizontal(MathHelper.floor(mc.player.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3));
                if (this.Method1624(offset3, false)) {
                    return offset3;
                }
            }
        }
        return null;
    }
}