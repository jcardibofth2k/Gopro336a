package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class214
extends Enum {
    public static Class214 DAMAGE = new Class214("DAMAGE", 0);
    public static Class214 PUSH = new Class214("PUSH", 1);
    public static Class214[] Field438;

    public static Class214[] Method554() {
        return (Class214[])Field438.clone();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class214() {
        void var2_-1;
        void var1_-1;
    }

    static {
        Field438 = new Class214[]{DAMAGE, PUSH};
    }

    public static Class214 Method555(String string) {
        return Enum.valueOf(Class214.class, string);
    }
}