package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class36
extends Enum {
    public static Class36 HORIZONTAL = new Class36("HORIZONTAL", 0);
    public static Class36 VERTICAL = new Class36("VERTICAL", 1);
    public static Class36[] Field175;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class36() {
        void var2_-1;
        void var1_-1;
    }

    public static Class36[] Method288() {
        return (Class36[])Field175.clone();
    }

    public static Class36 Method289(String string) {
        return Enum.valueOf(Class36.class, string);
    }

    static {
        Field175 = new Class36[]{HORIZONTAL, VERTICAL};
    }
}