package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class163
extends Enum {
    public static Class163 STATIC = new Class163("STATIC", 0);
    public static Class163 DYNAMIC = new Class163("DYNAMIC", 1);
    public static Class163[] Field1732;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class163() {
        void var2_-1;
        void var1_-1;
    }

    static {
        Field1732 = new Class163[]{STATIC, DYNAMIC};
    }

    public static Class163[] Method1654() {
        return (Class163[])Field1732.clone();
    }

    public static Class163 Method1655(String string) {
        return Enum.valueOf(Class163.class, string);
    }
}