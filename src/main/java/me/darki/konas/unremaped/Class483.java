package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class483
extends Enum {
    public static Class483 LINE = new Class483("LINE", 0);
    public static Class483 OUTLINE = new Class483("OUTLINE", 1);
    public static Class483 FULL = new Class483("FULL", 2);
    public static Class483[] Field2319;

    static {
        Field2319 = new Class483[]{LINE, OUTLINE, FULL};
    }

    public static Class483 Method2051(String string) {
        return Enum.valueOf(Class483.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class483() {
        void var2_-1;
        void var1_-1;
    }

    public static Class483[] Method2052() {
        return (Class483[])Field2319.clone();
    }
}