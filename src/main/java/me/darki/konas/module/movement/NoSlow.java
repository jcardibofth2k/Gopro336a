package me.darki.konas.module.movement;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.event.events.*;
import me.darki.konas.mixin.mixins.ICPacketPlayer;
import me.darki.konas.mixin.mixins.IEntity;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.Class461;
import me.darki.konas.unremaped.Class50;
import me.darki.konas.unremaped.Class550;
import me.darki.konas.unremaped.SendPacketEvent;
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

public class NoSlow
extends Module {
    public static Setting<Boolean> glide = new Setting<>("Glide", false);
    public static Setting<Boolean> strict = new Setting<>("Strict", false);
    public static Setting<Boolean> airStrict = new Setting<>("AirStrict", false);
    public static Setting<Boolean> nCP = new Setting<>("NCP", false).Method1177(4, 47);
    public static Setting<Class461> webs = new Setting<>("Webs", Class461.OFF);
    public static Setting<Integer> shiftTicks = new Setting<>("ShiftTicks", 1, 10, 1, 1).visibleIf(NoSlow::Method394);
    public boolean Field2669;
    public boolean Field2670;
    public boolean Field2671;

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        if (NoSlow.mc.player == null || NoSlow.mc.world == null) {
            return;
        }
        Item item = NoSlow.mc.player.getActiveItemStack().getItem();
        if (!NoSlow.mc.player.isHandActive() && item instanceof ItemFood || item instanceof ItemBow || item instanceof ItemPotion || !(item instanceof ItemFood) || !(item instanceof ItemBow) || !(item instanceof ItemPotion)) {
            if (this.Field2669 && ((Boolean)strict.getValue()).booleanValue()) {
                NoSlow.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity) NoSlow.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
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
            if (NoSlow.mc.world == null || NoSlow.mc.player == null) {
                return;
            }
            if (NoSlow.mc.player.getActiveItemStack().getItem() instanceof ItemBow) {
                return;
            }
            if (!((Boolean)nCP.getValue()).booleanValue() || !(NoSlow.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemSword) || !nCP.Method1178()) break block2;
            NoSlow.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
    }

    @Subscriber
    public void Method1709(InputUpdateEvent inputUpdateEvent) {
        if (NoSlow.mc.player.isHandActive() && !NoSlow.mc.player.isRiding()) {
            inputUpdateEvent.Method81().moveStrafe *= 5.0f;
            inputUpdateEvent.Method81().moveForward *= 5.0f;
        }
    }

    @Subscriber
    public void Method536(SendPacketEvent sendPacketEvent) {
        block0: {
            if (!(sendPacketEvent.getPacket() instanceof CPacketPlayer) || !this.Field2670) break block0;
            ((ICPacketPlayer) sendPacketEvent.getPacket()).setOnGround(false);
        }
    }

    public NoSlow() {
        super("NoSlow", "Makes you not slow down while i. e. eating", Category.MOVEMENT, new String[0]);
    }

    public static boolean Method394() {
        return webs.getValue() == Class461.TIMER;
    }

    @Subscriber
    public void Method503(MoveEvent moveEvent) {
        block2: {
            block3: {
                if (this.Field2671) {
                    return;
                }
                if (webs.getValue() == Class461.OFF) break block2;
                if (webs.getValue() != Class461.TIMER || NoSlow.mc.world.getBlockState(new BlockPos((Entity) NoSlow.mc.player)).getBlock() != Blocks.WEB) break block3;
                if (!(moveEvent.getY() < 0.0)) break block2;
                for (int i = 0; i < (Integer)shiftTicks.getValue(); ++i) {
                    this.Field2671 = true;
                    NoSlow.mc.player.move(moveEvent.getMoverType(), 0.0, moveEvent.getY(), 0.0);
                    Class550.Method883(NoSlow.mc.player.rotationYaw, NoSlow.mc.player.rotationPitch);
                    this.Field2671 = false;
                }
                break block2;
            }
            ((IEntity) NoSlow.mc.player).setInWeb(false);
        }
    }

    @Subscriber
    public void Method540(EntityUseItemEvent entityUseItemEvent) {
        block6: {
            block7: {
                if (NoSlow.mc.player.getActiveItemStack().getItem() instanceof ItemBow) {
                    return;
                }
                if (((Boolean)glide.getValue()).booleanValue()) {
                    if (!this.Field2670) {
                        NoSlow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(NoSlow.mc.player.rotationYaw, NoSlow.mc.player.rotationPitch, false));
                    }
                    this.Field2670 = true;
                }
                if (this.Field2669 || !((Boolean)strict.getValue()).booleanValue()) break block6;
                if (!((Boolean)airStrict.getValue()).booleanValue() || !NoSlow.mc.player.onGround) break block7;
                if (!((Boolean)glide.getValue()).booleanValue()) break block6;
            }
            NoSlow.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity) NoSlow.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            this.Field2669 = true;
        }
    }

    @Subscriber
    public void Method123(Class50 class50) {
        block2: {
            if (NoSlow.mc.world == null || NoSlow.mc.player == null) {
                return;
            }
            if (NoSlow.mc.player.getActiveItemStack().getItem() instanceof ItemBow) {
                return;
            }
            if (!((Boolean)nCP.getValue()).booleanValue() || !(NoSlow.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemSword) || !nCP.Method1178()) break block2;
            NoSlow.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(new BlockPos(-1, -1, -1), EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
        }
    }
}