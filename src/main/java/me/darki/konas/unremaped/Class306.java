package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class306
extends Enum {
    public static Class306 MENU = new Class306("MENU", 0);
    public static Class306 FRIEND = new Class306("FRIEND", 1);
    public static Class306 MISC = new Class306("MISC", 2);
    public static Class306[] Field1013;

    public static Class306[] Method1012() {
        return (Class306[])Field1013.clone();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class306() {
        void var2_-1;
        void var1_-1;
    }

    static {
        Field1013 = new Class306[]{MENU, FRIEND, MISC};
    }

    public static Class306 Method1013(String string) {
        return Enum.valueOf(Class306.class, string);
    }
}