package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class436
extends Enum {
    public static Class436 NONE = new Class436("NONE", 0);
    public static Class436 STATIC = new Class436("STATIC", 1);
    public static Class436 IRL = new Class436("IRL", 2);
    public static Class436[] Field759;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class436() {
        void var2_-1;
        void var1_-1;
    }

    static {
        Field759 = new Class436[]{NONE, STATIC, IRL};
    }

    public static Class436 Method793(String string) {
        return Enum.valueOf(Class436.class, string);
    }

    public static Class436[] Method794() {
        return (Class436[])Field759.clone();
    }
}