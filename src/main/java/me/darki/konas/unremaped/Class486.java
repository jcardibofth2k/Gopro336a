package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class486
extends Enum {
    public static Class486 NONE = new Class486("NONE", 0);
    public static Class486 GLOW = new Class486("GLOW", 1);
    public static Class486 BOX = new Class486("BOX", 2);
    public static Class486 SHADER = new Class486("SHADER", 3);
    public static Class486 OUTLINE = new Class486("OUTLINE", 4);
    public static Class486[] Field2252;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class486() {
        void var2_-1;
        void var1_-1;
    }

    public static Class486 Method2038(String string) {
        return Enum.valueOf(Class486.class, string);
    }

    public static Class486[] Method2039() {
        return (Class486[])Field2252.clone();
    }

    static {
        Field2252 = new Class486[]{NONE, GLOW, BOX, SHADER, OUTLINE};
    }
}