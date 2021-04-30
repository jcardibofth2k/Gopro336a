package me.darki.konas.unremaped;

import me.darki.konas.*;
import net.minecraft.client.Minecraft;

public class Class152 {
    public String Field1852;
    public String Field1853;
    public int Field1854;

    public Class152(String string, String string2, int n) {
        this.Field1852 = string;
        this.Field1853 = string2;
        this.Field1854 = n;
    }

    public String Method1756() {
        return this.Field1853;
    }

    public int Method1757() {
        return this.Field1854;
    }

    public void Method1758(String string) {
        this.Field1853 = string;
    }

    public boolean equals(Object object) {
        if (object instanceof Class152) {
            return this.Method1760().equalsIgnoreCase(((Class152)object).Method1760());
        }
        return false;
    }

    public void Method1759() {
        Minecraft.getMinecraft().player.sendChatMessage(this.Field1853);
    }

    public String Method1760() {
        return this.Field1852;
    }

    public void Method1761(int n) {
        this.Field1854 = n;
    }
}