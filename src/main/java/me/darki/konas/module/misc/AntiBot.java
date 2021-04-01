package me.darki.konas.module.misc;

import cookiedragon.eventsystem.Subscriber;
import java.util.ArrayList;
import java.util.List;

import me.darki.konas.module.Category;
import me.darki.konas.unremaped.Class19;
import me.darki.konas.module.Module;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class AntiBot
extends Module {
    public static ArrayList<EntityPlayer> Field2049 = new ArrayList();

    public static boolean Method513(Entity entity) {
        return entity instanceof EntityPlayer;
    }

    public static List<EntityPlayer> Method1519() {
        return Field2049;
    }

    public AntiBot() {
        super("AntiBot", Category.MISC);
    }

    public static void Method1318(Entity entity) {
        if (!Field2049.contains(entity)) {
            Field2049.add((EntityPlayer)entity);
        }
    }

    public boolean Method138(EntityPlayer entityPlayer) {
        NetworkPlayerInfo networkPlayerInfo = mc.getConnection().getPlayerInfo(entityPlayer.getGameProfile().getId());
        return networkPlayerInfo == null || networkPlayerInfo.getResponseTime() <= 0 && !entityPlayer.equals(AntiBot.mc.player) && networkPlayerInfo.getGameProfile() == null && entityPlayer.hasCustomName();
    }

    public static boolean Method395(Entity entity) {
        return entity != AntiBot.mc.player && entity != null;
    }

    @Subscriber
    public void Method130(Class19 class19) {
        block1: {
            this.Method1645(Field2049.size() + "");
            if (AntiBot.mc.currentScreen instanceof GuiDownloadTerrain && !Field2049.isEmpty()) {
                Field2049.clear();
            }
            if (AntiBot.mc.world == null) break block1;
            AntiBot.mc.world.loadedEntityList.stream().filter(AntiBot::Method513).filter(AntiBot::Method395).filter(this::Method386).forEach(AntiBot::Method1318);
        }
    }

    public boolean Method386(Entity entity) {
        return this.Method138((EntityPlayer)entity);
    }

    @Override
    public void onDisable() {
        Field2049.clear();
    }
}