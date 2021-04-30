package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class462
extends Enum {
    public static Class462 FULL = new Class462("FULL", 0);
    public static Class462 OUTLINE = new Class462("OUTLINE", 1);
    public static Class462 VECTOR = new Class462("VECTOR", 2);
    public static Class462 BASED = new Class462("BASED", 3);
    public static Class462[] Field259;

    static {
        Field259 = new Class462[]{FULL, OUTLINE, VECTOR, BASED};
    }

    public static Class462[] Method413() {
        return (Class462[])Field259.clone();
    }

    public static Class462 Method414(String string) {
        return Enum.valueOf(Class462.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class462() {
        void var2_-1;
        void var1_-1;
    }
}