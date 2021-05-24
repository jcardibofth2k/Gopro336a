package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class305
extends Enum {
    public static Class305 NONE = new Class305("NONE", 0);
    public static Class305 ALWAYS = new Class305("ALWAYS", 1);
    public static Class305 TARGET = new Class305("TARGET", 2);
    public static Class305[] Field1018;


    public static Class305[] Method1024() {
        return (Class305[])Field1018.clone();
    }

    public static Class305 Method1025(String string) {
        return Enum.valueOf(Class305.class, string);
    }

    static {
        Field1018 = new Class305[]{NONE, ALWAYS, TARGET};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class305() {
        void var2_-1;
        void var1_-1;
    }
}