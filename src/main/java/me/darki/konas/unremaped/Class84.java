package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class84
extends Enum {
    public static Class84 CATEGORY = new Class84("CATEGORY", 0);
    public static Class84 MODULES = new Class84("MODULES", 1);
    public static Class84[] Field285;

    public static Class84 Method459(String string) {
        return Enum.valueOf(Class84.class, string);
    }

    static {
        Field285 = new Class84[]{CATEGORY, MODULES};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class84() {
        void var2_-1;
        void var1_-1;
    }

    public static Class84[] Method460() {
        return (Class84[])Field285.clone();
    }
}