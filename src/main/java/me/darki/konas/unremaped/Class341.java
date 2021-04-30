package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class341
extends Enum {
    public static Class341 MANUAL = new Class341("MANUAL", 0);
    public static Class341 AUTOMATIC = new Class341("AUTOMATIC", 1);
    public static Class341[] Field258;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class341() {
        void var2_-1;
        void var1_-1;
    }

    public static Class341 Method411(String string) {
        return Enum.valueOf(Class341.class, string);
    }

    static {
        Field258 = new Class341[]{MANUAL, AUTOMATIC};
    }

    public static Class341[] Method412() {
        return (Class341[])Field258.clone();
    }
}