package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class300
extends Enum {
    public static Class300 HEALTH = new Class300("HEALTH", 0);
    public static Class300 PLAYER = new Class300("PLAYER", 1);
    public static Class300 CRYSTALDMG = new Class300("CRYSTALDMG", 2);
    public static Class300[] Field1022;

    public static Class300[] Method1037() {
        return (Class300[])Field1022.clone();
    }

    static {
        Field1022 = new Class300[]{HEALTH, PLAYER, CRYSTALDMG};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class300() {
        void var2_-1;
        void var1_-1;
    }

    public static Class300 Method1038(String string) {
        return Enum.valueOf(Class300.class, string);
    }
}