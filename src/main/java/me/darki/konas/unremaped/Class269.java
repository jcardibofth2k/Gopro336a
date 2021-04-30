package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class269
extends Enum {
    public static Class269 SAND = new Class269("SAND", 0);
    public static Class269 NOCLIP = new Class269("NOCLIP", 1);
    public static Class269[] Field1999;

    public static Class269 Method1826(String string) {
        return Enum.valueOf(Class269.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class269() {
        void var2_-1;
        void var1_-1;
    }

    public static Class269[] Method1827() {
        return (Class269[])Field1999.clone();
    }

    static {
        Field1999 = new Class269[]{SAND, NOCLIP};
    }
}