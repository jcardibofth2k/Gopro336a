package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class312
extends Enum {
    public static Class312 ALL = new Class312("ALL", 0);
    public static Class312 FOOD = new Class312("FOOD", 1);
    public static Class312 WHITELIST = new Class312("WHITELIST", 2);
    public static Class312[] Field816;

    static {
        Field816 = new Class312[]{ALL, FOOD, WHITELIST};
    }

    public static Class312 Method870(String string) {
        return Enum.valueOf(Class312.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class312() {
        void var2_-1;
        void var1_-1;
    }

    public static Class312[] Method871() {
        return (Class312[])Field816.clone();
    }
}