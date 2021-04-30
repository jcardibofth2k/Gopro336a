package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class268
extends Enum {
    public static Class268 PACKET = new Class268("PACKET", 0);
    public static Class268 ANTI = new Class268("ANTI", 1);
    public static Class268 VANILLA = new Class268("VANILLA", 2);
    public static Class268 TP = new Class268("TP", 3);
    public static Class268[] Field2002;

    public static Class268 Method1829(String string) {
        return Enum.valueOf(Class268.class, string);
    }

    static {
        Field2002 = new Class268[]{PACKET, ANTI, VANILLA, TP};
    }

    public static Class268[] Method1830() {
        return (Class268[])Field2002.clone();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class268() {
        void var2_-1;
        void var1_-1;
    }
}