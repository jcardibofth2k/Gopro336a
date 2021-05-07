package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class427
extends Enum {
    public static Class427 VANILLA = new Class427("VANILLA", 0);
    public static Class427 CUSTOM = new Class427("CUSTOM", 1);
    public static Class427 HIGHRES = new Class427("HIGHRES", 2);
    public static Class427[] Field1014;

    public static Class427 Method1014(String string) {
        return Enum.valueOf(Class427.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class427() {
        void var2_-1;
        void var1_-1;
    }

    static {
        Field1014 = new Class427[]{VANILLA, CUSTOM, HIGHRES};
    }

    public static Class427[] Method1015() {
        return (Class427[])Field1014.clone();
    }
}