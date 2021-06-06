package me.darki.konas.gui.hud.elements;

public enum arrowType
{
    //public static arrowType ARROW = new arrowType("ARROW", 0);
    //public static arrowType COMPASS = new arrowType("COMPASS", 1);
    ARROW("Arrow", 0),
    COMPASS("Compass", 1);

    public static arrowType[] Field2058;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */

    arrowType(String a, int b)
    {
    }

    public static arrowType[] Method1918() {
        return Field2058.clone();
    }

    static {
        Field2058 = new arrowType[]{ARROW, COMPASS};
    }

    public static arrowType Method1919(String string) {
        return Enum.valueOf(arrowType.class, string);
    }
}