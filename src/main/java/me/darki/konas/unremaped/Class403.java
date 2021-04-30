package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class403
extends Enum {
    public static Class403 BOOST = new Class403("BOOST", 0);
    public static Class403 CONTROL = new Class403("CONTROL", 1);
    public static Class403 FIREWORK = new Class403("FIREWORK", 2);
    public static Class403 PACKET = new Class403("PACKET", 3);
    public static Class403[] Field1225;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class403() {
        void var2_-1;
        void var1_-1;
    }

    static {
        Field1225 = new Class403[]{BOOST, CONTROL, FIREWORK, PACKET};
    }

    public static Class403[] Method1206() {
        return (Class403[])Field1225.clone();
    }

    public static Class403 Method1207(String string) {
        return Enum.valueOf(Class403.class, string);
    }
}