package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class353
extends Enum {
    public static Class353 NONE = new Class353("NONE", 0);
    public static Class353 JITTER = new Class353("JITTER", 1);
    public static Class353 STARE = new Class353("STARE", 2);
    public static Class353 DOWN = new Class353("DOWN", 3);
    public static Class353[] Field2546;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class353() {
        void var2_-1;
        void var1_-1;
    }

    public static Class353[] Method2184() {
        return (Class353[])Field2546.clone();
    }

    static {
        Field2546 = new Class353[]{NONE, JITTER, STARE, DOWN};
    }

    public static Class353 Method2185(String string) {
        return Enum.valueOf(Class353.class, string);
    }
}