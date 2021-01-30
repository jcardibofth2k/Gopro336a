package me.darki.konas.gui.hud.elements;

public class arrowType
extends Enum {
    public static arrowType ARROW = new arrowType("ARROW", 0);
    public static arrowType COMPASS = new arrowType("COMPASS", 1);
    public static arrowType[] Field2058;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public arrowType() {
        void var2_-1;
        void var1_-1;
    }

    public static arrowType[] Method1918() {
        return (arrowType[])Field2058.clone();
    }

    static {
        Field2058 = new arrowType[]{ARROW, COMPASS};
    }

    public static arrowType Method1919(String string) {
        return Enum.valueOf(arrowType.class, string);
    }
}
