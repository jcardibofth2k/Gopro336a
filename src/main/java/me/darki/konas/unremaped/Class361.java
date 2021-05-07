package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class361
extends Enum {
    public static Class361 VANILLA = new Class361("VANILLA", 0);
    public static Class361 PACKET = new Class361("PACKET", 1);
    public static Class361 INSTANT = new Class361("INSTANT", 2);
    public static Class361[] Field2348;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class361() {
        void var2_-1;
        void var1_-1;
    }

    static {
        Field2348 = new Class361[]{VANILLA, PACKET, INSTANT};
    }

    public static Class361[] Method2059() {
        return (Class361[])Field2348.clone();
    }

    public static Class361 Method2060(String string) {
        return Enum.valueOf(Class361.class, string);
    }
}