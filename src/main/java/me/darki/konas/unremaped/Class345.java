package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.event.events.*;
import me.darki.konas.mixin.mixins.ICPacketPlayer;
import me.darki.konas.mixin.mixins.IEntity;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class Class345
extends Module {
    public static Setting<Boolean> Field2663 = new Setting<>("Glide", false);
    public static Setting<Boolean> Field2664 = new Setting<>("Strict", false);
    public static Setting<Boolean> Field2665 = new Setting<>("AirStrict", false);
    public static Setting<Boolean> Field2666 = new Setting<>("NCP", false).Method1177(4, 47);
    public static Setting<Class461> Field2667 = new Setting<>("Webs", Class461.OFF);
    public static Setting<Integer> Field2668 = new Setting<>("ShiftTicks", 1, 10, 1, 1).visibleIf(Class345::Method394);
    public boolean Field2669;
    public boolean Field2670;
    public boolean Field2671;

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        if (Class345.mc.player == null || Class345.mc.world == null) {
            return;
        }
        Item item = Class345.mc.player.getActiveItemStack().getItem();
        if (!Class345.mc.player.isHandActive() && item instanceof ItemFood || item instanceof ItemBow || item instanceof ItemPotion || !(item instanceof ItemFood) || !(item instanceof ItemBow) || !(item instanceof ItemPotion)) {
            if (this.Field2669 && ((Boolean)Field2664.getValue()).booleanValue()) {
                Class345.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Class345.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                this.Field2669 = false;
            }
            if (this.Field2670) {
                this.Field2670 = false;
            }
        }
    }

    @Subscriber
    public void Method135(UpdateEvent updateEvent) {
        block2: {
            if (Class345.mc.world == null || Class345.mc.player == null) {
                return;
            }
            if (Class345.mc.player.getActiveItemStack().getItem() instanceof ItemBow) {
                return;
            }
            if (!((Boolean)Field2666.getValue()).booleanValue() || !(Class345.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemSword) || !Field2666.Method1178()) break block2;
            Class345.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
    }

    @Subscriber
    public void Method1709(InputUpdateEvent inputUpdateEvent) {
        if (Class345.mc.player.isHandActive() && !Class345.mc.player.isRiding()) {
            inputUpdateEvent.Method81().moveStrafe *= 5.0f;
            inputUpdateEvent.Method81().moveForward *= 5.0f;
        }
    }

    @Subscriber
    public void Method536(Class24 class24) {
        block0: {
            if (!(class24.getPacket() instanceof CPacketPlayer) || !this.Field2670) break block0;
            ((ICPacketPlayer)class24.getPacket()).Method1700(false);
        }
    }

    public Class345() {
        super("NoSlow", "Makes you not slow down while i. e. eating", Category.MOVEMENT, new String[0]);
    }

    public static boolean Method394() {
        return Field2667.getValue() == Class461.TIMER;
    }

    @Subscriber
    public void Method503(MoveEvent moveEvent) {
        block2: {
            block3: {
                if (this.Field2671) {
                    return;
                }
                if (Field2667.getValue() == Class461.OFF) break block2;
                if (Field2667.getValue() != Class461.TIMER || Class345.mc.world.getBlockState(new BlockPos((Entity)Class345.mc.player)).getBlock() != Blocks.WEB) break block3;
                if (!(moveEvent.getY() < 0.0)) break block2;
                for (int i = 0; i < (Integer)Field2668.getValue(); ++i) {
                    this.Field2671 = true;
                    Class345.mc.player.move(moveEvent.getMoverType(), 0.0, moveEvent.getY(), 0.0);
                    Class550.Method883(Class345.mc.player.rotationYaw, Class345.mc.player.rotationPitch);
                    this.Field2671 = false;
                }
                break block2;
            }
            ((IEntity)Class345.mc.player).Method45(false);
        }
    }

    @Subscriber
    public void Method540(EntityUseItemEvent entityUseItemEvent) {
        block6: {
            block7: {
                if (Class345.mc.player.getActiveItemStack().getItem() instanceof ItemBow) {
                    return;
                }
                if (((Boolean)Field2663.getValue()).booleanValue()) {
                    if (!this.Field2670) {
                        Class345.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(Class345.mc.player.rotationYaw, Class345.mc.player.rotationPitch, false));
                    }
                    this.Field2670 = true;
                }
                if (this.Field2669 || !((Boolean)Field2664.getValue()).booleanValue()) break block6;
                if (!((Boolean)Field2665.getValue()).booleanValue() || !Class345.mc.player.onGround) break block7;
                if (!((Boolean)Field2663.getValue()).booleanValue()) break block6;
            }
            Class345.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Class345.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            this.Field2669 = true;
        }
    }

    @Subscriber
    public void Method123(Class50 class50) {
        block2: {
            if (Class345.mc.world == null || Class345.mc.player == null) {
                return;
            }
            if (Class345.mc.player.getActiveItemStack().getItem() instanceof ItemBow) {
                return;
            }
            if (!((Boolean)Field2666.getValue()).booleanValue() || !(Class345.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemSword) || !Field2666.Method1178()) break block2;
            Class345.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(new BlockPos(-1, -1, -1), EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
        }
    }
}
