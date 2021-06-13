package me.darki.konas.unremaped;

import me.darki.konas.*;
public enum Class107 {
    TOP_RIGHT("TOP_RIGHT", 0),
    TOP_LEFT("TOP_LEFT", 1),
    BOTTOM_RIGHT("BOTTOM_RIGHT", 2),
    BOTTOM_LEFT("BOTTOM_LEFT", 3);
    public static Class107[] Field2623;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    Class107(String a, int b) {
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