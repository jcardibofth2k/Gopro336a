package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class522
extends Enum {
    public static Class522 MOUSE = new Class522("MOUSE", 0);
    public static Class522 PACKET = new Class522("PACKET", 1);
    public static Class522[] Field1261;

    public static Class522 Method1237(String string) {
        return Enum.valueOf(Class522.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class522() {
        void var2_-1;
        void var1_-1;
    }

    static {
        Field1261 = new Class522[]{MOUSE, PACKET};
    }

    public static Class522[] Method1238() {
        return (Class522[])Field1261.clone();
    }
}