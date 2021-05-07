package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class376
extends Enum {
    public static Class376 BOUNCE = new Class376("BOUNCE", 0);
    public static Class376 SPLASH = new Class376("SPLASH", 1);
    public static Class376 BOTH = new Class376("BOTH", 2);
    public static Class376[] Field2059;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class376() {
        void var2_-1;
        void var1_-1;
    }

    public static Class376[] Method1921() {
        return (Class376[])Field2059.clone();
    }

    public static Class376 Method1922(String string) {
        return Enum.valueOf(Class376.class, string);
    }

    static {
        Field2059 = new Class376[]{BOUNCE, SPLASH, BOTH};
    }
}