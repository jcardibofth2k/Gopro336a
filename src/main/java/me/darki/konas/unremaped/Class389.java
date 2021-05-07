package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class389
extends Enum {
    public static Class389 NONE = new Class389("NONE", 0);
    public static Class389 NORMAL = new Class389("NORMAL", 1);
    public static Class389 SILENT = new Class389("SILENT", 2);
    public static Class389 RETURN = new Class389("RETURN", 3);
    public static Class389[] Field2003;

    public static Class389[] Method1831() {
        return (Class389[])Field2003.clone();
    }

    static {
        Field2003 = new Class389[]{NONE, NORMAL, SILENT, RETURN};
    }

    public static Class389 Method1832(String string) {
        return Enum.valueOf(Class389.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class389() {
        void var2_-1;
        void var1_-1;
    }
}