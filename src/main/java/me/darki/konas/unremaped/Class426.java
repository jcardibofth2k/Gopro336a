package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class426
extends Enum {
    public static Class426 NONE = new Class426("NONE", 0);
    public static Class426 NORMAL = new Class426("NORMAL", 1);
    public static Class426 NCP = new Class426("NCP", 2);
    public static Class426 GLIDE = new Class426("GLIDE", 3);
    public static Class426[] Field1019;

    public static Class426[] Method1026() {
        return (Class426[])Field1019.clone();
    }

    public static Class426 Method1027(String string) {
        return Enum.valueOf(Class426.class, string);
    }

    static {
        Field1019 = new Class426[]{NONE, NORMAL, NCP, GLIDE};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class426() {
        void var2_-1;
        void var1_-1;
    }
}