package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import java.util.Comparator;
import java.util.stream.Stream;

import me.darki.konas.event.events.OpenGuiEvent;
import me.darki.konas.event.events.UpdateEvent;
import me.darki.konas.command.Logger;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.module.movement.PacketFly;
import me.darki.konas.setting.Setting;
import me.darki.konas.util.rotation.Rotation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class Class185
extends Module {
    public static Setting<Boolean> pressurePlates = new Setting<>("PressurePlates", true);
    public static Setting<Integer> minHeight = new Setting<>("MinHeight", 2, 5, 2, 1);
    public static Setting<Integer> range = new Setting<>("Range", 2, 5, 1, 1);
    public static Setting<Integer> interval = new Setting<>("Interval", 0, 20, 0, 1);
    public static Setting<Double> range2 = new Setting<>("Range", 4.0, 6.0, 1.0, 0.1);
    public int Field1385 = 0;
    public Class490 Field1386 = null;
    public int Field1387 = -1;

    public Class185() {
        super("AutoAnvil", "Automatically places anvils above people's heads to break their helmet", Category.COMBAT, new String[0]);
    }

    @Subscriber(priority=8)
    public void Method123(Class50 class50) {
        block2: {
            if (this.Field1387 == -1 || this.Field1386 == null) break block2;
            boolean bl = Class185.mc.player.inventory.currentItem != this.Field1387;
            int n = Class185.mc.player.inventory.currentItem;
            if (bl) {
                Class185.mc.player.inventory.currentItem = this.Field1387;
                Class185.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.Field1387));
            }
            Rotation.Method1958(this.Field1386, EnumHand.MAIN_HAND, true);
            this.Field1385 = 0;
            if (bl) {
                Class185.mc.player.inventory.currentItem = n;
                Class185.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(n));
            }
        }
    }

    @Override
    public void onEnable() {
        this.Field1386 = null;
        this.Field1387 = -1;
        this.Field1385 = (Integer)interval.getValue();
    }

    public static boolean Method122(EntityPlayer entityPlayer) {
        return !Class546.Method963((Entity)entityPlayer);
    }

    @Subscriber(priority=40)
    public void Method135(UpdateEvent updateEvent) {
        this.Field1386 = null;
        this.Field1387 = -1;
        if (updateEvent.isCanceled() || !Rotation.Method1966()) {
            return;
        }
        if (ModuleManager.getModuleByClass(PacketFly.class).isEnabled()) {
            return;
        }
        if (this.Field1385 < (Integer)interval.getValue()) {
            ++this.Field1385;
        }
        if (this.Field1385 < (Integer)interval.getValue()) {
            return;
        }
        EntityPlayer entityPlayer = this.Method1453();
        if (entityPlayer == null) {
            return;
        }
        if (((Boolean)pressurePlates.getValue()).booleanValue() && !(Class185.mc.world.getBlockState(new BlockPos((Entity)entityPlayer)).getBlock() instanceof BlockPressurePlate)) {
            this.Field1386 = Rotation.Method1962(new BlockPos((Entity)entityPlayer), true);
            if (this.Field1386 != null) {
                int n = this.Method464();
                if (n == -1) {
                    this.Field1386 = null;
                    this.toggle();
                    Logger.Method1118("No pressure plates found!");
                    return;
                }
                this.Field1387 = n;
                return;
            }
        }
        for (int i = 0; i < (Integer)range.getValue(); ++i) {
            this.Field1386 = Rotation.Method1962(new BlockPos((Entity)entityPlayer).up((Integer)minHeight.getValue() + i), true);
            if (this.Field1386 == null) continue;
            int n = this.Method524();
            if (n == -1) {
                this.Field1386 = null;
                this.toggle();
                Logger.Method1118("No anvils found!");
                return;
            }
            this.Field1387 = n;
            return;
        }
    }

    public int Method464() {
        int n = -1;
        for (int i = 0; i < 9; ++i) {
            Item item = Class185.mc.player.inventory.getStackInSlot(i).getItem();
            Block block = Block.getBlockFromItem((Item)item);
            if (!(block instanceof BlockPressurePlate)) continue;
            n = i;
            break;
        }
        return n;
    }

    public boolean Method138(EntityPlayer entityPlayer) {
        BlockPos blockPos = new BlockPos(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ).down();
        Block block = Class185.mc.world.getBlockState(blockPos).getBlock();
        return !(block instanceof BlockAir) && !(block instanceof BlockLiquid);
    }

    public static boolean Method126(EntityPlayer entityPlayer) {
        return (double)Class185.mc.player.getDistance((Entity)entityPlayer) < (Double)range2.getValue();
    }

    public static boolean Method128(EntityPlayer entityPlayer) {
        return !Class492.Method1988(entityPlayer.getUniqueID().toString());
    }

    public static boolean Method141(EntityPlayer entityPlayer) {
        return entityPlayer != Class185.mc.player && entityPlayer != mc.getRenderViewEntity();
    }

    public int Method524() {
        int n = -1;
        for (int i = 0; i < 9; ++i) {
            Item item = Class185.mc.player.inventory.getStackInSlot(i).getItem();
            Block block = Block.getBlockFromItem((Item)item);
            if (!(block instanceof BlockAnvil)) continue;
            n = i;
            break;
        }
        return n;
    }

    @Subscriber
    public void Method1451(OpenGuiEvent openGuiEvent) {
        block0: {
            if (!(openGuiEvent.Method1161() instanceof GuiRepair)) break block0;
            openGuiEvent.setCanceled(true);
        }
    }

    public static Float Method1452(EntityPlayer entityPlayer) {
        return Float.valueOf(Class185.mc.player.getDistance((Entity)entityPlayer));
    }

    public EntityPlayer Method1453() {
        Stream<EntityPlayer> stream = Class185.mc.world.playerEntities.stream();
        return stream.filter(Class185::Method122).filter(Class185::Method141).filter(Class185::Method128).filter(Class185::Method126).filter(this::Method138).min(Comparator.comparing(Class185::Method1452)).orElse(null);
    }
}