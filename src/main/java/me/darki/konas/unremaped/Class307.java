package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class307
extends Enum {
    public static Class307 NONE = new Class307("NONE", 0);
    public static Class307 NORMAL = new Class307("NORMAL", 1);
    public static Class307 FAST = new Class307("FAST", 2);
    public static Class307[] Field864;

    static {
        Field864 = new Class307[]{NONE, NORMAL, FAST};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class307() {
        void var2_-1;
        void var1_-1;
    }

    public static Class307 Method926(String string) {
        return Enum.valueOf(Class307.class, string);
    }

    public static Class307[] Method927() {
        return (Class307[])Field864.clone();
    }
}