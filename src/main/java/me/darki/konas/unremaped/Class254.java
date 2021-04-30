package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class254
extends Enum {
    public static Class254 VANILLA = new Class254("VANILLA", 0);
    public static Class254 SPIGOT = new Class254("SPIGOT", 1);
    public static Class254[] Field2149;

    public static Class254 Method1955(String string) {
        return Enum.valueOf(Class254.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class254() {
        void var2_-1;
        void var1_-1;
    }

    public static Class254[] Method1956() {
        return (Class254[])Field2149.clone();
    }

    static {
        Field2149 = new Class254[]{VANILLA, SPIGOT};
    }
}