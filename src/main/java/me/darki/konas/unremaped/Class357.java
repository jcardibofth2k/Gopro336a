package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class357
extends Enum {
    public static Class357 WALK = new Class357("WALK", 0);
    public static Class357 PLACE = new Class357("PLACE", 1);
    public static Class357 JUMP = new Class357("JUMP", 2);
    public static Class357 DROP = new Class357("DROP", 3);
    public static Class357 BREAK = new Class357("BREAK", 4);
    public static Class357 EAT = new Class357("EAT", 5);
    public static Class357 CRAFT = new Class357("CRAFT", 6);
    public static Class357 PICKUP = new Class357("PICKUP", 7);
    public static Class357[] Field2507;

    static {
        Field2507 = new Class357[]{WALK, PLACE, JUMP, DROP, BREAK, EAT, CRAFT, PICKUP};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class357() {
        void var2_-1;
        void var1_-1;
    }

    public static Class357 Method2165(String string) {
        return Enum.valueOf(Class357.class, string);
    }

    public static Class357[] Method2166() {
        return (Class357[])Field2507.clone();
    }
}