package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class327
extends Enum {
    public static Class327 NONE = new Class327("NONE", 0);
    public static Class327 NORMAL = new Class327("NORMAL", 1);
    public static Class327 MIN = new Class327("MIN", 2);
    public static Class327 LATEST = new Class327("LATEST", 3);
    public static Class327[] Field614;

    static {
        Field614 = new Class327[]{NONE, NORMAL, MIN, LATEST};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class327() {
        void var2_-1;
        void var1_-1;
    }

    public static Class327[] Method660() {
        return (Class327[])Field614.clone();
    }

    public static Class327 Method661(String string) {
        return Enum.valueOf(Class327.class, string);
    }
}