package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class333
extends Enum {
    public static Class333 NONE = new Class333("NONE", 0);
    public static Class333 DAMAGE = new Class333("DAMAGE", 1);
    public static Class333 RANGE = new Class333("RANGE", 2);
    public static Class333[] Field430;

    public static Class333[] Method546() {
        return (Class333[])Field430.clone();
    }

    public static Class333 Method547(String string) {
        return Enum.valueOf(Class333.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class333() {
        void var2_-1;
        void var1_-1;
    }

    static {
        Field430 = new Class333[]{NONE, DAMAGE, RANGE};
    }
}