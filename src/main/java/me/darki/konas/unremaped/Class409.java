package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class409
extends Enum {
    public static Class409 FULL = new Class409("FULL", 0);
    public static Class409 BOTTOM = new Class409("BOTTOM", 1);
    public static Class409 TOP = new Class409("TOP", 2);
    public static Class409[] Field1223;

    public static Class409[] Method1202() {
        return (Class409[])Field1223.clone();
    }

    public static Class409 Method1203(String string) {
        return Enum.valueOf(Class409.class, string);
    }

    static {
        Field1223 = new Class409[]{FULL, BOTTOM, TOP};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class409() {
        void var2_-1;
        void var1_-1;
    }
}