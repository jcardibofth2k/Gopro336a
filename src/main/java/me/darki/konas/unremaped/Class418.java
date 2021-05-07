package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class418
extends Enum {
    public static Class418 NONE = new Class418("NONE", 0);
    public static Class418 NORMAL = new Class418("NORMAL", 1);
    public static Class418 TOGGLE = new Class418("TOGGLE", 2);
    public static Class418[] Field1071;

    static {
        Field1071 = new Class418[]{NONE, NORMAL, TOGGLE};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class418() {
        void var2_-1;
        void var1_-1;
    }

    public static Class418[] Method1098() {
        return (Class418[])Field1071.clone();
    }

    public static Class418 Method1099(String string) {
        return Enum.valueOf(Class418.class, string);
    }
}