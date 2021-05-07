package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class400
extends Enum {
    public static Class400 GROW = new Class400("GROW", 0);
    public static Class400 SHRINK = new Class400("SHRINK", 1);
    public static Class400 CROSS = new Class400("CROSS", 2);
    public static Class400 STATIC = new Class400("STATIC", 3);
    public static Class400[] Field1268;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class400() {
        void var2_-1;
        void var1_-1;
    }

    static {
        Field1268 = new Class400[]{GROW, SHRINK, CROSS, STATIC};
    }

    public static Class400 Method1246(String string) {
        return Enum.valueOf(Class400.class, string);
    }

    public static Class400[] Method1247() {
        return (Class400[])Field1268.clone();
    }
}