package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class410
extends Enum {
    public static Class410 SEQUENTIAL = new Class410("SEQUENTIAL", 0);
    public static Class410 VANILLA = new Class410("VANILLA", 1);
    public static Class410[] Field1142;

    static {
        Field1142 = new Class410[]{SEQUENTIAL, VANILLA};
    }

    public static Class410 Method1142(String string) {
        return Enum.valueOf(Class410.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class410() {
        void var2_-1;
        void var1_-1;
    }

    public static Class410[] Method1143() {
        return (Class410[])Field1142.clone();
    }
}