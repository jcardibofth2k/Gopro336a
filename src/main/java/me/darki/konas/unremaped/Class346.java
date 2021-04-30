package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class346
extends Enum {
    public static Class346 WAITING = new Class346("WAITING", 0);
    public static Class346 DISABLING = new Class346("DISABLING", 1);
    public static Class346[] Field2643;

    static {
        Field2643 = new Class346[]{WAITING, DISABLING};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class346() {
        void var2_-1;
        void var1_-1;
    }

    public static Class346[] Method2309() {
        return (Class346[])Field2643.clone();
    }

    public static Class346 Method2310(String string) {
        return Enum.valueOf(Class346.class, string);
    }
}