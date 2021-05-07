package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class308
extends Enum {
    public static Class308 NONE = new Class308("NONE", 0);
    public static Class308 TRACK = new Class308("TRACK", 1);
    public static Class308 HIT = new Class308("HIT", 2);
    public static Class308[] Field862;

    static {
        Field862 = new Class308[]{NONE, TRACK, HIT};
    }

    public static Class308[] Method924() {
        return (Class308[])Field862.clone();
    }

    public static Class308 Method925(String string) {
        return Enum.valueOf(Class308.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class308() {
        void var2_-1;
        void var1_-1;
    }
}