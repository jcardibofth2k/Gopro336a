package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class295
extends Enum {
    public static Class295 MSG = new Class295("MSG", 0);
    public static Class295 WHISPER = new Class295("WHISPER", 1);
    public static Class295[] Field1483;

    public static Class295 Method1512(String string) {
        return Enum.valueOf(Class295.class, string);
    }

    public static Class295[] Method1513() {
        return (Class295[])Field1483.clone();
    }

    static {
        Field1483 = new Class295[]{MSG, WHISPER};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class295() {
        void var2_-1;
        void var1_-1;
    }
}