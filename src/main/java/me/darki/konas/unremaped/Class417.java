package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class417
extends Enum {
    public static Class417 NONE = new Class417("NONE", 0);
    public static Class417 ALL = new Class417("ALL", 1);
    public static Class417 SELECT = new Class417("SELECT", 2);
    public static Class417[] Field1072;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class417() {
        void var2_-1;
        void var1_-1;
    }

    public static Class417 Method1100(String string) {
        return Enum.valueOf(Class417.class, string);
    }

    static {
        Field1072 = new Class417[]{NONE, ALL, SELECT};
    }

    public static Class417[] Method1101() {
        return (Class417[])Field1072.clone();
    }
}