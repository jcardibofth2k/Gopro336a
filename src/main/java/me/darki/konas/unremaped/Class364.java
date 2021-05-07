package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class364
extends Enum {
    public static Class364 SPIN = new Class364("SPIN", 0);
    public static Class364 JITTER = new Class364("JITTER", 1);
    public static Class364 STARE = new Class364("STARE", 2);
    public static Class364[] Field2352;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class364() {
        void var2_-1;
        void var1_-1;
    }

    static {
        Field2352 = new Class364[]{SPIN, JITTER, STARE};
    }

    public static Class364 Method2063(String string) {
        return Enum.valueOf(Class364.class, string);
    }

    public static Class364[] Method2064() {
        return (Class364[])Field2352.clone();
    }
}