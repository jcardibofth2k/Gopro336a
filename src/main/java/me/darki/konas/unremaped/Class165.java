package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class165
extends Enum {
    public static Class165 NONE = new Class165("NONE", 0);
    public static Class165 VANILLA = new Class165("VANILLA", 1);
    public static Class165 KONAS = new Class165("KONAS", 2);
    public static Class165 BOTH = new Class165("BOTH", 3);
    public static Class165[] Field1747;

    public static Class165[] Method1663() {
        return (Class165[])Field1747.clone();
    }

    static {
        Field1747 = new Class165[]{NONE, VANILLA, KONAS, BOTH};
    }

    public static Class165 Method1664(String string) {
        return Enum.valueOf(Class165.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class165() {
        void var2_-1;
        void var1_-1;
    }
}