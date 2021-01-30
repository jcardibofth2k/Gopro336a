package me.darki.konas;

public class Class495
extends Enum {
    public static Class495 NONE = new Class495("NONE", 0);
    public static Class495 VANILLA = new Class495("VANILLA", 1);
    public static Class495 CUSTOM = new Class495("CUSTOM", 2);
    public static Class495[] Field2178;

    static {
        Field2178 = new Class495[]{NONE, VANILLA, CUSTOM};
    }

    public static Class495[] Method1976() {
        return (Class495[])Field2178.clone();
    }

    public static Class495 Method1977(String string) {
        return Enum.valueOf(Class495.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class495() {
        void var2_-1;
        void var1_-1;
    }
}
