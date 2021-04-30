package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class144
extends Enum {
    public static Class144 TEXT = new Class144("TEXT", 0);
    public static Class144 IMAGE = new Class144("IMAGE", 1);
    public static Class144[] Field2016;

    static {
        Field2016 = new Class144[]{TEXT, IMAGE};
    }

    public static Class144 Method1843(String string) {
        return Enum.valueOf(Class144.class, string);
    }

    public static Class144[] Method1844() {
        return (Class144[])Field2016.clone();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class144() {
        void var2_-1;
        void var1_-1;
    }
}