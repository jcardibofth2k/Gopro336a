package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class334
extends Enum {
    public static Class334 NORMAL = new Class334("NORMAL", 0);
    public static Class334 RIGHTCLICK = new Class334("RIGHTCLICK", 1);
    public static Class334[] Field495;

    public static Class334[] Method567() {
        return (Class334[])Field495.clone();
    }

    static {
        Field495 = new Class334[]{NORMAL, RIGHTCLICK};
    }

    public static Class334 Method568(String string) {
        return Enum.valueOf(Class334.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class334() {
        void var2_-1;
        void var1_-1;
    }
}