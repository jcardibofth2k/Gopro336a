package me.darki.konas.module.player;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.*;
import me.darki.konas.mixin.mixins.IMinecraft;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBoat;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemFood;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.math.BlockPos;

public class FastUse
extends Module {
    public static Setting<Boolean> fastXP = new Setting<>("FastXP", true);
    public static Setting<Boolean> ghostFix = new Setting<>("GhostFix", false);
    public static Setting<Boolean> strict = new Setting<>("Strict", false);
    public static Setting<Boolean> packetEat = new Setting<>("PacketEat", false);
    public static Setting<Boolean> fastBow = new Setting<>("FastBow", false);
    public static Setting<Boolean> bowBomb = new Setting<>("BowBomb", false).Method1191(fastBow::getValue);
    public static Setting<Boolean> fastPlace = new Setting<>("FastPlace", false);
    public static Setting<Boolean> noCrystalPlace = new Setting<>("NoCrystalPlace", false);
    public static Setting<Boolean> placeWhitelist = new Setting<>("PlaceWhitelist", false);
    public static Setting<Class443> whitelist = new Setting<>("Whitelist", new Class443(new String[0]));
    public static boolean Field1871 = false;

    public boolean Method394() {
        Item item = FastUse.mc.player.getHeldItemMainhand().getItem();
        Item item2 = FastUse.mc.player.getHeldItemOffhand().getItem();
        if (((Boolean) fastXP.getValue()).booleanValue() && (item instanceof ItemExpBottle || item2 instanceof ItemExpBottle)) {
            return true;
        }
        if (((Boolean) fastPlace.getValue()).booleanValue()) {
            if (item instanceof ItemBlock && (((Class443) whitelist.getValue()).Method682().contains(((ItemBlock)item).getBlock()) || !((Boolean) placeWhitelist.getValue()).booleanValue())) {
                ((IMinecraft)mc).Method57(0);
                return true;
            }
            if (item2 instanceof ItemBlock && (((Class443) whitelist.getValue()).Method682().contains(((ItemBlock)item2).getBlock()) || !((Boolean) placeWhitelist.getValue()).booleanValue())) {
                ((IMinecraft)mc).Method57(0);
                return true;
            }
        }
        if (item instanceof ItemFood) {
            ((IMinecraft)mc).Method57(0);
            return true;
        }
        return false;
    }

    @Subscriber
    public void Method536(Class24 class24) {
        block4: {
            block3: {
                if (FastUse.mc.player == null || FastUse.mc.world == null) {
                    return;
                }
                if (!((Boolean) ghostFix.getValue()).booleanValue() || !(FastUse.mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle) && !(FastUse.mc.player.getHeldItemMainhand().getItem() instanceof ItemBoat)) break block3;
                if (!(class24.getPacket() instanceof CPacketPlayerTryUseItemOnBlock)) break block4;
                class24.Cancel();
                break block4;
            }
            if (!((Boolean) noCrystalPlace.getValue()).booleanValue() || !(class24.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) || !(FastUse.mc.player.getHeldItem(((CPacketPlayerTryUseItemOnBlock)class24.getPacket()).getHand()).getItem() instanceof ItemEndCrystal)) break block4;
            if (Field1871) {
                Field1871 = false;
            } else {
                class24.setCanceled(true);
            }
        }
    }

    public FastUse() {
        super("FastUse", "Removes item use delay", Category.PLAYER, "FastXP", "FastBow", "FastPlace");
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        block4: {
            if (FastUse.mc.world == null || FastUse.mc.player == null) {
                return;
            }
            if (((Boolean) strict.getValue()).booleanValue() && FastUse.mc.player.ticksExisted % 2 == 0) {
                return;
            }
            if (((Boolean) fastBow.getValue()).booleanValue() && FastUse.mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow && FastUse.mc.player.isHandActive() && FastUse.mc.player.getItemInUseMaxCount() >= 3) {
                FastUse.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, FastUse.mc.player.getHorizontalFacing()));
                if (((Boolean) bowBomb.getValue()).booleanValue() && FastUse.mc.gameSettings.keyBindUseItem.isKeyDown()) {
                    FastUse.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(FastUse.mc.player.posX, FastUse.mc.player.posY - 0.1, FastUse.mc.player.posZ, FastUse.mc.player.rotationYaw, FastUse.mc.player.rotationPitch, false));
                    FastUse.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(FastUse.mc.player.posX, FastUse.mc.player.posY - 999.0, FastUse.mc.player.posZ, FastUse.mc.player.rotationYaw, FastUse.mc.player.rotationPitch, true));
                }
                FastUse.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(FastUse.mc.player.getActiveHand()));
                FastUse.mc.player.stopActiveHand();
                return;
            }
            if (!this.Method394() || ((IMinecraft)mc).Method58() == 0) break block4;
            ((IMinecraft)mc).Method57(0);
        }
    }

    @Subscriber
    public void Method1762(Class41 class41) {
        block0: {
            if (!(FastUse.mc.player.getHeldItem(FastUse.mc.player.getActiveHand()).getItem() instanceof ItemFood) || !((Boolean) packetEat.getValue()).booleanValue()) break block0;
            class41.setCanceled(true);
        }
    }
}
