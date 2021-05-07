package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class460
extends Enum {
    public static Class460 LINES = new Class460("LINES", 0);
    public static Class460 ARROWS = new Class460("ARROWS", 1);
    public static Class460[] Field231;

    public static Class460 Method381(String string) {
        return Enum.valueOf(Class460.class, string);
    }

    public static Class460[] Method382() {
        return (Class460[])Field231.clone();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class460() {
        void var2_-1;
        void var1_-1;
    }

    static {
        Field231 = new Class460[]{LINES, ARROWS};
    }
}