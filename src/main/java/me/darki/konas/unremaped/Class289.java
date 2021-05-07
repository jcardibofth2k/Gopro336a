package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class289
extends Enum {
    public static Class289 SINGLE = new Class289("SINGLE", 0);
    public static Class289 MULTI = new Class289("MULTI", 1);
    public static Class289[] Field1689;

    public static Class289 Method1605(String string) {
        return Enum.valueOf(Class289.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class289() {
        void var2_-1;
        void var1_-1;
    }

    public static Class289[] Method1606() {
        return (Class289[])Field1689.clone();
    }

    static {
        Field1689 = new Class289[]{SINGLE, MULTI};
    }
}