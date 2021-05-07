package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class435
extends Enum {
    public static Class435 SOLID = new Class435("SOLID", 0);
    public static Class435 TRAMPOLINE = new Class435("TRAMPOLINE", 1);
    public static Class435[] Field762;

    public static Class435[] Method803() {
        return (Class435[])Field762.clone();
    }

    static {
        Field762 = new Class435[]{SOLID, TRAMPOLINE};
    }

    public static Class435 Method804(String string) {
        return Enum.valueOf(Class435.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class435() {
        void var2_-1;
        void var1_-1;
    }
}