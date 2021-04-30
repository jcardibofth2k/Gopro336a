package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class447
extends Enum {
    public static Class447 OFF = new Class447("OFF", 0);
    public static Class447 CONSTANT = new Class447("CONSTANT", 1);
    public static Class447 DYNAMIC = new Class447("DYNAMIC", 2);
    public static Class447[] Field561;

    static {
        Field561 = new Class447[]{OFF, CONSTANT, DYNAMIC};
    }

    public static Class447[] Method634() {
        return (Class447[])Field561.clone();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class447() {
        void var2_-1;
        void var1_-1;
    }

    public static Class447 Method635(String string) {
        return Enum.valueOf(Class447.class, string);
    }
}