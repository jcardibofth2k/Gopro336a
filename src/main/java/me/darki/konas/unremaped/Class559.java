package me.darki.konas.unremaped;

import me.darki.konas.*;
import java.util.Objects;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;

public class Class559 {
    public String Field767;
    public double Field768;
    public double Field769;
    public double Field770;
    public int Field771;
    public String Field772;
    public Class561 Field773;

    public int hashCode() {
        return Objects.hash(this.Field768, this.Field769, this.Field770, this.Field771, this.Field772);
    }

    public Class559(String string, double d, double d2, double d3, int n, Class561 class561) {
        this.Field767 = string;
        this.Field768 = d;
        this.Field769 = d2;
        this.Field770 = d3;
        this.Field771 = n;
        this.Field772 = "";
        try {
            if (Minecraft.getMinecraft().isSingleplayer()) {
                this.Field772 = Minecraft.getMinecraft().player.getEntityWorld().getWorldInfo().getWorldName();
            } else if (Minecraft.getMinecraft().getCurrentServerData() != null) {
                this.Field772 = Minecraft.getMinecraft().getCurrentServerData().serverIP.replaceAll(":", "_");
            }
        }
        catch (NullPointerException nullPointerException) {
            this.Field772 = "";
        }
        this.Field773 = class561;
    }

    public Class559(String string, double d, double d2, double d3, int n, String string2, Class561 class561) {
        this.Field767 = string;
        this.Field768 = d;
        this.Field769 = d2;
        this.Field770 = d3;
        this.Field771 = n;
        this.Field772 = string2;
        this.Field773 = class561;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) return false;
        if (this.getClass() != object.getClass()) {
            return false;
        }
        Class559 class559 = (Class559)object;
        if ((double)Double.compare(class559.Field768, this.Field768) != 0.0) return false;
        if ((double)Double.compare(class559.Field769, this.Field769) != 0.0) return false;
        if ((double)Double.compare(class559.Field770, this.Field770) != 0.0) return false;
        if (this.Field771 != class559.Field771) return false;
        if (this.Field773 != class559.Field773) return false;
        if (!this.Field772.equalsIgnoreCase(class559.Method813())) return false;
        return true;
    }

    public String toString() {
        return "Waypoint: {name=" + this.Field767 + ", x=" + this.Field768 + ", y=" + this.Field769 + ", z=" + this.Field770 + ", dimension=" + this.Field771 + '}';
    }

    public String Method813() {
        return this.Field772;
    }

    public int Method814() {
        return this.Field771;
    }

    public Class561 Method815() {
        return this.Field773;
    }

    public boolean Method816() {
        block28: {
            block27: {
                Minecraft minecraft = Minecraft.getMinecraft();
                boolean bl = minecraft.isSingleplayer();
                if (!bl) break block27;
                String string = this.Field772;
                Minecraft minecraft2 = Minecraft.getMinecraft();
                EntityPlayerSP entityPlayerSP = minecraft2.player;
                World world = entityPlayerSP.getEntityWorld();
                WorldInfo worldInfo = world.getWorldInfo();
                String string2 = worldInfo.getWorldName();
                return string.equalsIgnoreCase(string2);
            }
            Minecraft minecraft = Minecraft.getMinecraft();
            ServerData serverData = minecraft.getCurrentServerData();
            if (serverData == null) break block28;
            String string = this.Field772;
            Minecraft minecraft3 = Minecraft.getMinecraft();
            ServerData serverData2 = minecraft3.getCurrentServerData();
            String string3 = serverData2.serverIP;
            String string4 = ":";
            String string5 = "_";
            String string6 = string3.replaceAll(string4, string5);
            try {
                return string.equalsIgnoreCase(string6);
            }
            catch (NullPointerException nullPointerException) {
                this.Field772 = "";
            }
        }
        return false;
    }

    public double Method817(Class559 class559) {
        double d = 1.0;
        if (this.Field771 == -1 && class559.Method814() != -1) {
            d = 8.0;
        }
        double d2 = class559.Method821() - this.Field768 * d;
        double d3 = class559.Method820() - this.Field769 * d;
        double d4 = class559.Method818() - this.Field770 * d;
        return MathHelper.sqrt((double)(d2 * d2 + d3 * d3 + d4 * d4));
    }

    public double Method818() {
        return this.Field770;
    }

    public String Method819() {
        return this.Field767;
    }

    public double Method820() {
        return this.Field769;
    }

    public double Method821() {
        return this.Field768;
    }
}