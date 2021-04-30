package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class395
extends Enum {
    public static Class395 CANCEL = new Class395("CANCEL", 0);
    public static Class395 OFFHAND = new Class395("OFFHAND", 1);
    public static Class395 MAINHAND = new Class395("MAINHAND", 2);
    public static Class395 OPPOSITE = new Class395("OPPOSITE", 3);
    public static Class395 NONE = new Class395("NONE", 4);
    public static Class395[] Field1851;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class395() {
        void var2_-1;
        void var1_-1;
    }

    public static Class395 Method1754(String string) {
        return Enum.valueOf(Class395.class, string);
    }

    public static Class395[] Method1755() {
        return (Class395[])Field1851.clone();
    }

    static {
        Field1851 = new Class395[]{CANCEL, OFFHAND, MAINHAND, OPPOSITE, NONE};
    }
}