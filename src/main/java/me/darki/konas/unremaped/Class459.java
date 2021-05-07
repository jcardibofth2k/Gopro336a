package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class459
extends Enum {
    public static Class459 NORMAL = new Class459("NORMAL", 0);
    public static Class459 BYPASS = new Class459("BYPASS", 1);
    public static Class459[] Field428;

    public static Class459 Method542(String string) {
        return Enum.valueOf(Class459.class, string);
    }

    static {
        Field428 = new Class459[]{NORMAL, BYPASS};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class459() {
        void var2_-1;
        void var1_-1;
    }

    public static Class459[] Method543() {
        return (Class459[])Field428.clone();
    }
}