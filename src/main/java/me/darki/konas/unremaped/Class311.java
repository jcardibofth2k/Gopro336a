package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class311
extends Enum {
    public static Class311 DYNAMIC = new Class311("DYNAMIC", 0);
    public static Class311 STATIC = new Class311("STATIC", 1);
    public static Class311[] Field780;

    public static Class311 Method832(String string) {
        return Enum.valueOf(Class311.class, string);
    }

    static {
        Field780 = new Class311[]{DYNAMIC, STATIC};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class311() {
        void var2_-1;
        void var1_-1;
    }

    public static Class311[] Method833() {
        return (Class311[])Field780.clone();
    }
}