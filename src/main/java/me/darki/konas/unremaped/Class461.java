package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class461
extends Enum {
    public static Class461 OFF = new Class461("OFF", 0);
    public static Class461 VANILLA = new Class461("VANILLA", 1);
    public static Class461 TIMER = new Class461("TIMER", 2);
    public static Class461[] Field229;

    static {
        Field229 = new Class461[]{OFF, VANILLA, TIMER};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class461() {
        void var2_-1;
        void var1_-1;
    }

    public static Class461[] Method379() {
        return (Class461[])Field229.clone();
    }

    public static Class461 Method380(String string) {
        return Enum.valueOf(Class461.class, string);
    }
}