package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class408
extends Enum {
    public static Class408 BOTTOM = new Class408("BOTTOM", 0);
    public static Class408 OUTLINE = new Class408("OUTLINE", 1);
    public static Class408 FULL = new Class408("FULL", 2);
    public static Class408 WIREFRAME = new Class408("WIREFRAME", 3);
    public static Class408 FADE = new Class408("FADE", 4);
    public static Class408[] Field1224;

    static {
        Field1224 = new Class408[]{BOTTOM, OUTLINE, FULL, WIREFRAME, FADE};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class408() {
        void var2_-1;
        void var1_-1;
    }

    public static Class408 Method1204(String string) {
        return Enum.valueOf(Class408.class, string);
    }

    public static Class408[] Method1205() {
        return (Class408[])Field1224.clone();
    }
}