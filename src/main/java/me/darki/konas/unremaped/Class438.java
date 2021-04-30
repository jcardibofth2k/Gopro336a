package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class438
extends Enum {
    public static Class438 NONE = new Class438("NONE", 0);
    public static Class438 NORMAL = new Class438("NORMAL", 1);
    public static Class438 EDGEJUMP = new Class438("EDGEJUMP", 2);
    public static Class438[] Field766;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class438() {
        void var2_-1;
        void var1_-1;
    }

    public static Class438[] Method811() {
        return (Class438[])Field766.clone();
    }

    public static Class438 Method812(String string) {
        return Enum.valueOf(Class438.class, string);
    }

    static {
        Field766 = new Class438[]{NONE, NORMAL, EDGEJUMP};
    }
}