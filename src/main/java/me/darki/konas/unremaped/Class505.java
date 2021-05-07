package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class505
extends Enum {
    public static Class505 NORMAL = new Class505("NORMAL", 0);
    public static Class505 GAMMA = new Class505("GAMMA", 1);
    public static Class505 POTION = new Class505("POTION", 2);
    public static Class505[] Field1352;

    public static Class505[] Method1360() {
        return (Class505[])Field1352.clone();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class505() {
        void var2_-1;
        void var1_-1;
    }

    static {
        Field1352 = new Class505[]{NORMAL, GAMMA, POTION};
    }

    public static Class505 Method1361(String string) {
        return Enum.valueOf(Class505.class, string);
    }
}