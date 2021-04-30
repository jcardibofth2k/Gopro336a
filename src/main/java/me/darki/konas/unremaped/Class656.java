package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class656
extends Enum {
    public static Class656 MAINHAND = new Class656("MAINHAND", 0);
    public static Class656 OFFHAND = new Class656("OFFHAND", 1);
    public static Class656[] Field1086;

    static {
        Field1086 = new Class656[]{MAINHAND, OFFHAND};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class656() {
        void var2_-1;
        void var1_-1;
    }

    public static Class656[] Method1121() {
        return (Class656[])Field1086.clone();
    }

    public static Class656 Method1122(String string) {
        return Enum.valueOf(Class656.class, string);
    }
}