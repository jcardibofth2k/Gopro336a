package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class561
extends Enum {
    public static Class561 DEATH = new Class561("DEATH", 0);
    public static Class561 CUSTOM = new Class561("CUSTOM", 1);
    public static Class561[] Field728;

    static {
        Field728 = new Class561[]{DEATH, CUSTOM};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class561() {
        void var2_-1;
        void var1_-1;
    }

    public static Class561[] Method785() {
        return (Class561[])Field728.clone();
    }

    public static Class561 Method786(String string) {
        return Enum.valueOf(Class561.class, string);
    }
}