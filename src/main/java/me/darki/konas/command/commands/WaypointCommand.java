package me.darki.konas.command.commands;

import java.text.DecimalFormat;
import java.util.Map;

import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.module.render.Waypoints;
import me.darki.konas.unremaped.Class559;
import me.darki.konas.unremaped.Class561;
import me.darki.konas.unremaped.Class562;
import me.darki.konas.util.SyntaxChunk;
import net.minecraft.entity.player.EntityPlayer;

public class WaypointCommand
extends Command {
    public DecimalFormat Field2540 = new DecimalFormat("#.##");

    @Override
    public void Method174(String[] stringArray) {
        Waypoints waypoints = (Waypoints) ModuleManager.getModuleByClass(Waypoints.class);
        if (stringArray.length == 2) {
            if (stringArray[1].equalsIgnoreCase("list")) {
                StringBuilder stringBuilder = new StringBuilder();
                for (Class559 class559 : KonasGlobals.INSTANCE.Field1138.Method759()) {
                    stringBuilder.append(" ").append(class559.Method819());
                }
                for (Map.Entry<EntityPlayer, Long> entry : waypoints.Method1799().entrySet()) {
                    EntityPlayer entityPlayer = entry.getKey();
                    if (entityPlayer == WaypointCommand.Field123.player) continue;
                    stringBuilder.append(" ").append(entityPlayer.getName());
                }
                Logger.Method1118(stringBuilder.toString());
            } else {
                Logger.Method1119(this.Method191());
            }
        } else if (stringArray.length == 3) {
            if (stringArray[1].equalsIgnoreCase("get")) {
                for (Class559 class559 : KonasGlobals.INSTANCE.Field1138.Method759()) {
                    if (!class559.Method819().equalsIgnoreCase(stringArray[2])) continue;
                    Logger.Method1118(class559.toString());
                }
                for (Map.Entry entry : waypoints.Method1799().entrySet()) {
                    EntityPlayer entityPlayer = (EntityPlayer)entry.getKey();
                    if (entityPlayer == WaypointCommand.Field123.player || !entityPlayer.getName().equalsIgnoreCase(stringArray[2])) continue;
                    Logger.Method1118("Waypoint: {name=" + entityPlayer.getName() + ", x=" + entityPlayer.posX + ", y=" + entityPlayer.posY + ", z=" + entityPlayer.posZ + ", dimension =" + WaypointCommand.Field123.player.dimension + '}');
                }
            } else if (stringArray[1].equalsIgnoreCase("del")) {
                for (Class559 class559 : KonasGlobals.INSTANCE.Field1138.Method759()) {
                    if (!class559.Method819().equalsIgnoreCase(stringArray[2])) continue;
                    KonasGlobals.INSTANCE.Field1138.Method763(stringArray[2]);
                    Logger.Method1118("Deleted Waypoint &b" + stringArray[2]);
                }
                waypoints.Method1799().forEach((arg_0, arg_1) -> WaypointCommand.Method2178(stringArray, waypoints, arg_0, arg_1));
            } else if (stringArray[1].equalsIgnoreCase("add")) {
                if (KonasGlobals.INSTANCE.Field1138.Method761(stringArray[2]) != null) {
                    Logger.Method1119("A Waypoint with this name already exists on this server!");
                    return;
                }
                Class559 class559 = new Class559(stringArray[2], Double.parseDouble(this.Field2540.format(WaypointCommand.Field123.player.posX)), Double.parseDouble(this.Field2540.format(WaypointCommand.Field123.player.posY)), Double.parseDouble(this.Field2540.format(WaypointCommand.Field123.player.posZ)), WaypointCommand.Field123.player.dimension, Class561.CUSTOM);
                KonasGlobals.INSTANCE.Field1138.Method760(class559);
                Logger.Method1118("Added Waypoint " + class559.Method819() + " at " + class559.Method821() + ", " + class559.Method820() + ", " + class559.Method818());
            } else {
                Logger.Method1119(this.Method191());
            }
        } else if (stringArray.length == 6) {
            if (stringArray[1].equalsIgnoreCase("add")) {
                StringBuilder stringBuilder;
                Class559 class559;
                block77: {
                    Class562 class562 = KonasGlobals.INSTANCE.Field1138;
                    String string = stringArray[2];
                    Class559 class5592 = class562.Method761(string);
                    if (class5592 == null) break block77;
                    String string2 = "A Waypoint with this name already exists on this server!";
                    Logger.Method1119(string2);
                    return;
                }
                Class559 class5593 = class559;
                Class559 class5594 = class559;
                String string = stringArray[2];
                String string3 = stringArray[3];
                double d = Double.parseDouble(string3);
                String string4 = stringArray[4];
                double d2 = Double.parseDouble(string4);
                String string5 = stringArray[5];
                double d3 = Double.parseDouble(string5);
                int n = WaypointCommand.Field123.player.dimension;
                Class561 class561 = Class561.CUSTOM;
                class5593(string, d, d2, d3, n, class561);
                Class559 class5595 = class5594;
                Class562 class562 = KonasGlobals.INSTANCE.Field1138;
                Class559 class5596 = class5595;
                class562.Method760(class5596);
                StringBuilder stringBuilder2 = stringBuilder;
                StringBuilder stringBuilder3 = stringBuilder;
                stringBuilder2();
                String string6 = "Added Waypoint ";
                StringBuilder stringBuilder4 = stringBuilder3.append(string6);
                Class559 class5597 = class5595;
                String string7 = class5597.Method819();
                StringBuilder stringBuilder5 = stringBuilder4.append(string7);
                String string8 = " at ";
                StringBuilder stringBuilder6 = stringBuilder5.append(string8);
                Class559 class5598 = class5595;
                double d4 = class5598.Method821();
                StringBuilder stringBuilder7 = stringBuilder6.append(d4);
                String string9 = ", ";
                StringBuilder stringBuilder8 = stringBuilder7.append(string9);
                Class559 class5599 = class5595;
                double d5 = class5599.Method820();
                StringBuilder stringBuilder9 = stringBuilder8.append(d5);
                String string10 = ", ";
                StringBuilder stringBuilder10 = stringBuilder9.append(string10);
                Class559 class55910 = class5595;
                double d6 = class55910.Method818();
                StringBuilder stringBuilder11 = stringBuilder10.append(d6);
                String string11 = stringBuilder11.toString();
                try {
                    Logger.Method1118(string11);
                }
                catch (Exception exception) {
                    Logger.Method1118("Please provide a valid coordinate value");
                }
            } else {
                Logger.Method1119(this.Method191());
            }
        } else {
            Logger.Method1119(this.Method191());
        }
    }

    public static void Method2178(String[] stringArray, Waypoints waypoints, EntityPlayer entityPlayer, Long l) {
        block0: {
            if (!entityPlayer.getName().equalsIgnoreCase(stringArray[2])) break block0;
            waypoints.Method1799().remove(entityPlayer);
            Logger.Method1118("Deleted Waypoint &b" + stringArray[2]);
        }
    }

    public WaypointCommand() {
        super("waypoint", "Add, delete, and list waypoints", new SyntaxChunk("<add/del/get/list>"), new SyntaxChunk("<name>"), new SyntaxChunk("[x]"), new SyntaxChunk("[y]"), new SyntaxChunk("[z]"));
    }
}
