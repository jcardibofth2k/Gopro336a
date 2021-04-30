package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class195
extends Enum {
    public static Class195 OFF = new Class195("OFF", 0);
    public static Class195 NORMAL = new Class195("NORMAL", 1);
    public static Class195 SILENT = new Class195("SILENT", 2);
    public static Class195[] Field79;

    public static Class195 Method143(String string) {
        return Enum.valueOf(Class195.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class195() {
        void var2_-1;
        void var1_-1;
    }

    public static Class195[] Method144() {
        return (Class195[])Field79.clone();
    }

    static {
        Field79 = new Class195[]{OFF, NORMAL, SILENT};
    }
}