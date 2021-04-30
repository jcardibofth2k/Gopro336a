package me.darki.konas.unremaped;

import me.darki.konas.*;
import com.mojang.authlib.GameProfile;
import cookiedragon.eventsystem.Subscriber;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.darki.konas.event.events.TickEvent;
import me.darki.konas.mixin.mixins.IInventoryPlayer;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class FakePlayer
extends Module {
    public static Setting<Class289> spawnMode = new Setting<>("SpawnMode", Class289.MULTI);
    public static Setting<Boolean> copyInventory = new Setting<>("CopyInventory", false);
    public List<Integer> Field1745 = null;
    public static String[][] Field1746 = new String[][]{{"66666666-6666-6666-6666-666666666600", "soulbond", "-3", "0"}, {"66666666-6666-6666-6666-666666666601", "derp1", "0", "-3"}, {"66666666-6666-6666-6666-666666666602", "derp2", "3", "0"}, {"66666666-6666-6666-6666-666666666603", "derp3", "0", "3"}, {"66666666-6666-6666-6666-666666666604", "derp4", "-6", "0"}, {"66666666-6666-6666-6666-666666666605", "derp5", "0", "-6"}, {"66666666-6666-6666-6666-666666666606", "derp6", "6", "0"}, {"66666666-6666-6666-6666-666666666607", "derp7", "0", "6"}, {"66666666-6666-6666-6666-666666666608", "derp8", "-9", "0"}, {"66666666-6666-6666-6666-666666666609", "derp9", "0", "-9"}, {"66666666-6666-6666-6666-666666666610", "derp10", "9", "0"}, {"66666666-6666-6666-6666-666666666611", "derp11", "0", "9"}};

    public void Method1662(String string, String string2, int n, int n2, int n3) {
        EntityOtherPlayerMP entityOtherPlayerMP = new EntityOtherPlayerMP((World) FakePlayer.mc.world, new GameProfile(UUID.fromString(string), string2));
        entityOtherPlayerMP.copyLocationAndAnglesFrom((Entity) FakePlayer.mc.player);
        entityOtherPlayerMP.posX += (double)n2;
        entityOtherPlayerMP.posZ += (double)n3;
        if (((Boolean)copyInventory.getValue()).booleanValue()) {
            ((IInventoryPlayer)entityOtherPlayerMP.inventory).setArmorInventory((NonNullList<ItemStack>) FakePlayer.mc.player.inventory.armorInventory);
            ((IInventoryPlayer)entityOtherPlayerMP.inventory).setMainInventory((NonNullList<ItemStack>) FakePlayer.mc.player.inventory.mainInventory);
            entityOtherPlayerMP.inventory.currentItem = FakePlayer.mc.player.inventory.currentItem;
            entityOtherPlayerMP.setHeldItem(EnumHand.MAIN_HAND, FakePlayer.mc.player.getHeldItemMainhand());
            entityOtherPlayerMP.setHeldItem(EnumHand.OFF_HAND, FakePlayer.mc.player.getHeldItemOffhand());
        }
        FakePlayer.mc.world.addEntityToWorld(n, (Entity)entityOtherPlayerMP);
        this.Field1745.add(n);
    }

    public FakePlayer() {
        super("FakePlayer", Category.MISC, "Ghosts");
    }

    @Subscriber
    public void onTickEvent(TickEvent tickEvent) {
        block0: {
            if (this.Field1745 != null && !this.Field1745.isEmpty()) break block0;
            this.toggle();
        }
    }

    @Override
    public void onEnable() {
        if (FakePlayer.mc.player == null || FakePlayer.mc.world == null) {
            this.toggle();
            return;
        }
        this.Field1745 = new ArrayList<Integer>();
        int n = -101;
        for (String[] stringArray : Field1746) {
            if (((Class289)((Object)spawnMode.getValue())).equals((Object)Class289.SINGLE)) {
                this.Method1662(stringArray[0], stringArray[1], n, 0, 0);
                break;
            }
            this.Method1662(stringArray[0], stringArray[1], n, Integer.parseInt(stringArray[2]), Integer.parseInt(stringArray[3]));
            --n;
        }
    }

    @Override
    public void onDisable() {
        if (FakePlayer.mc.player == null || FakePlayer.mc.world == null) {
            return;
        }
        if (this.Field1745 != null) {
            for (int n : this.Field1745) {
                FakePlayer.mc.world.removeEntityFromWorld(n);
            }
        }
    }
}