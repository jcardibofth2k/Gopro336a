package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class220
extends Enum {
    public static Class220 SEARCHING = new Class220("SEARCHING", 0);
    public static Class220 CRYSTAL = new Class220("CRYSTAL", 1);
    public static Class220 REDSTONE = new Class220("REDSTONE", 2);
    public static Class220 BREAKING = new Class220("BREAKING", 3);
    public static Class220 EXPLOSION = new Class220("EXPLOSION", 4);
    public static Class220[] Field257;

    static {
        Field257 = new Class220[]{SEARCHING, CRYSTAL, REDSTONE, BREAKING, EXPLOSION};
    }

    public static Class220[] Method409() {
        return (Class220[])Field257.clone();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class220() {
        void var2_-1;
        void var1_-1;
    }

    public static Class220 Method410(String string) {
        return Enum.valueOf(Class220.class, string);
    }
}