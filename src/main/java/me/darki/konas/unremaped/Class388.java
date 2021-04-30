package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class388
extends Enum {
    public static Class388 NONE = new Class388("NONE", 0);
    public static Class388 SPLASH = new Class388("SPLASH", 1);
    public static Class388 LINGERING = new Class388("LINGERING", 2);
    public static Class388[] Field1992;

    public static Class388[] Method1821() {
        return (Class388[])Field1992.clone();
    }

    public static Class388 Method1822(String string) {
        return Enum.valueOf(Class388.class, string);
    }

    static {
        Field1992 = new Class388[]{NONE, SPLASH, LINGERING};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class388() {
        void var2_-1;
        void var1_-1;
    }
}