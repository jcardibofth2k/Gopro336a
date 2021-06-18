package me.darki.konas.module.combat;

import cookiedragon.eventsystem.Subscriber;
import java.util.Comparator;

import me.darki.konas.event.events.UpdateEvent;
import me.darki.konas.command.Logger;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.module.movement.PacketFly;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.*;
import me.darki.konas.util.TimerUtil;
import me.darki.konas.util.rotation.Rotation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockWeb;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class AutoWeb
extends Module {
    public static Setting<Float> targetRange = new Setting<>("TargetRange", Float.valueOf(4.5f), Float.valueOf(16.0f), Float.valueOf(1.0f), Float.valueOf(0.1f));
    public static Setting<Integer> actionShift = new Setting<>("ActionShift", 2, 2, 1, 1);
    public static Setting<Integer> actionInterval = new Setting<>("ActionInterval", 0, 10, 0, 1);
    public static Setting<Boolean> top = new Setting<>("Top", true);
    public static Setting<Boolean> self = new Setting<>("Self", false);
    public static Setting<Boolean> strict = new Setting<>("Strict", false);
    public static Setting<Boolean> rotate = new Setting<>("Rotate", true);
    public static Setting<Boolean> switchBack = new Setting<>("SwitchBack", false);
    public int Field1756;
    public TimerUtil Field1757 = new TimerUtil();
    public BlockPos Field1758;
    public int Field1759 = 0;
    public BlockPos Field1760 = null;
    public Class490 Field1761;
    public Class490 Field1762;
    public TimerUtil Field1763 = new TimerUtil();

    public static boolean Method128(EntityPlayer entityPlayer) {
        return AutoWeb.mc.player.getDistance((Entity)entityPlayer) < Math.max(((Float)targetRange.getValue()).floatValue() - 1.0f, 1.0f);
    }

    public static boolean Method138(EntityPlayer entityPlayer) {
        return entityPlayer != AutoWeb.mc.player && entityPlayer != mc.getRenderViewEntity();
    }

    public static Float Method137(EntityPlayer entityPlayer) {
        return Float.valueOf(AutoWeb.mc.player.getDistance((Entity)entityPlayer));
    }

    public static boolean Method1665(EntityPlayer entityPlayer) {
        return entityPlayer.getHealth() > 0.0f;
    }

    public static boolean Method141(EntityPlayer entityPlayer) {
        return !Class546.Method963((Entity)entityPlayer);
    }

    @Override
    public void onEnable() {
        if (AutoWeb.mc.player == null || AutoWeb.mc.world == null) {
            this.toggle();
            return;
        }
        this.Field1758 = null;
        this.Field1760 = null;
        this.Field1761 = null;
        this.Field1762 = null;
        this.Field1759 = (Integer)actionInterval.getValue();
    }

    public static boolean Method122(EntityPlayer entityPlayer) {
        return !Class492.Method1988(entityPlayer.getUniqueID().toString());
    }

    public int Method464() {
        int n = -1;
        for (int i = 0; i < 9; ++i) {
            Block block;
            ItemStack itemStack = AutoWeb.mc.player.inventory.getStackInSlot(i);
            if (itemStack == ItemStack.EMPTY || !(itemStack.getItem() instanceof ItemBlock) || !((block = ((ItemBlock)itemStack.getItem()).getBlock()) instanceof BlockWeb)) continue;
            n = i;
            break;
        }
        return n;
    }

    public EntityPlayer Method1623() {
        return AutoWeb.mc.world.playerEntities.stream().filter(AutoWeb::Method141).filter(AutoWeb::Method138).filter(AutoWeb::Method132).filter(AutoWeb::Method122).filter(AutoWeb::Method1665).filter(AutoWeb::Method128).filter(this::Method126).min(Comparator.comparing(AutoWeb::Method137)).orElse((EntityPlayer)(((Boolean)self.getValue()).booleanValue() ? AutoWeb.mc.player : null));
    }

    public boolean Method126(EntityPlayer entityPlayer) {
        BlockPos blockPos = new BlockPos(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ).down();
        Block block = AutoWeb.mc.world.getBlockState(blockPos).getBlock();
        return !(block instanceof BlockAir) && !(block instanceof BlockLiquid);
    }

    public AutoWeb() {
        super("AutoWeb", 0, Category.COMBAT, "WebAura", "AutoWebber");
    }

    @Subscriber(priority=12)
    public void Method123(Class50 class50) {
        block8: {
            if (this.Field1761 == null || this.Field1760 == null || this.Field1756 == -1) break block8;
            boolean bl = AutoWeb.mc.player.inventory.currentItem != this.Field1756;
            int n = AutoWeb.mc.player.inventory.currentItem;
            if (bl) {
                AutoWeb.mc.player.inventory.currentItem = this.Field1756;
                AutoWeb.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.Field1756));
            }
            boolean bl2 = AutoWeb.mc.player.isSprinting();
            boolean bl3 = Class545.Method1004(this.Field1761.Method1982());
            if (bl2) {
                AutoWeb.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity) AutoWeb.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
            }
            if (bl3) {
                AutoWeb.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity) AutoWeb.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            Rotation.Method1957(this.Field1761, EnumHand.MAIN_HAND, false);
            if ((Integer)actionShift.getValue() == 2 && ((Boolean)top.getValue()).booleanValue() && Rotation.Method1965(this.Field1760.up(), false, false)) {
                this.Field1761 = Rotation.Method1961(this.Field1760.up(), (Boolean)rotate.getValue(), true);
                if (this.Field1761 != null) {
                    this.Field1758 = this.Field1760;
                    this.Field1757.UpdateCurrentTime();
                    Rotation.Method1957(this.Field1761, EnumHand.MAIN_HAND, false);
                }
            }
            if (bl3) {
                AutoWeb.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity) AutoWeb.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            if (bl2) {
                AutoWeb.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity) AutoWeb.mc.player, CPacketEntityAction.Action.START_SPRINTING));
            }
            if (bl && ((Boolean)switchBack.getValue()).booleanValue()) {
                AutoWeb.mc.player.inventory.currentItem = n;
                AutoWeb.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(n));
            }
        }
    }

    public static boolean Method132(EntityPlayer entityPlayer) {
        return !entityPlayer.isDead;
    }

    @Subscriber
    public void Method139(Render3DEvent render3DEvent) {
        block1: {
            if (AutoWeb.mc.world == null || AutoWeb.mc.player == null) {
                return;
            }
            if (this.Field1758 == null) break block1;
            Class523.Method1218(this.Field1758);
        }
    }

    @Subscriber(priority=71)
    public void Method135(UpdateEvent updateEvent) {
        block13: {
            EntityPlayer entityPlayer;
            if (this.Field1761 != null) {
                this.Field1762 = this.Field1761;
                this.Field1763.UpdateCurrentTime();
            }
            this.Field1760 = null;
            this.Field1761 = null;
            if (this.Field1757.GetDifferenceTiming(350.0)) {
                this.Field1758 = null;
            }
            if (updateEvent.isCanceled() || !Rotation.Method1959((Boolean)rotate.getValue())) {
                return;
            }
            if (ModuleManager.getModuleByClass(PacketFly.class).isEnabled()) {
                return;
            }
            if (!(!((Boolean)strict.getValue()).booleanValue() || AutoWeb.mc.player.onGround && AutoWeb.mc.player.collidedVertically)) {
                return;
            }
            if (this.Field1759 < (Integer)actionInterval.getValue()) {
                ++this.Field1759;
            }
            if ((entityPlayer = this.Method1623()) == null) {
                return;
            }
            if (this.Field1759 < (Integer)actionInterval.getValue()) {
                if (this.Field1762 != null && !this.Field1763.GetDifferenceTiming(650.0)) {
                    KonasGlobals.INSTANCE.Field1139.Method1937(this.Field1762.Method1979(), this.Field1762.Method1978());
                }
                return;
            }
            this.Field1760 = new BlockPos(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ);
            int n = this.Method464();
            if (n == -1) {
                Logger.Method1119("No Webs Found!");
                this.toggle();
                return;
            }
            this.Field1756 = n;
            if (Rotation.Method1965(this.Field1760, false, false)) {
                this.Field1761 = Rotation.Method1962(this.Field1760, (Boolean)rotate.getValue());
            } else if (((Boolean)top.getValue()).booleanValue() && Rotation.Method1965(this.Field1760.up(), false, false)) {
                this.Field1761 = Rotation.Method1962(this.Field1760.up(), (Boolean)rotate.getValue());
            }
            if (this.Field1761 == null) break block13;
            this.Field1759 = 0;
            this.Field1758 = this.Field1760;
            this.Field1757.UpdateCurrentTime();
        }
    }
}