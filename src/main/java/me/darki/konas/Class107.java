package me.darki.konas;

public class Class107
extends Enum {
    public static Class107 TOP_RIGHT = new Class107("TOP_RIGHT", 0);
    public static Class107 TOP_LEFT = new Class107("TOP_LEFT", 1);
    public static Class107 BOTTOM_RIGHT = new Class107("BOTTOM_RIGHT", 2);
    public static Class107 BOTTOM_LEFT = new Class107("BOTTOM_LEFT", 3);
    public static Class107[] Field2623;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class107() {
        void var2_-1;
        void var1_-1;
    }

    public static Class107[] Method2280() {
        return (Class107[])Field2623.clone();
    }

    static {
        Field2623 = new Class107[]{TOP_RIGHT, TOP_LEFT, BOTTOM_RIGHT, BOTTOM_LEFT};
    }

    public static Class107 Method2281(String string) {
        return Enum.valueOf(Class107.class, string);
    }
}
