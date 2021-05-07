package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class323
extends Enum {
    public static Class323 PACKET = new Class323("PACKET", 0);
    public static Class323 BYPASS = new Class323("BYPASS", 1);
    public static Class323 JUMP = new Class323("JUMP", 2);
    public static Class323 SMALLJUMP = new Class323("SMALLJUMP", 3);
    public static Class323[] Field681;

    public static Class323[] Method743() {
        return (Class323[])Field681.clone();
    }

    static {
        Field681 = new Class323[]{PACKET, BYPASS, JUMP, SMALLJUMP};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class323() {
        void var2_-1;
        void var1_-1;
    }

    public static Class323 Method744(String string) {
        return Enum.valueOf(Class323.class, string);
    }
}