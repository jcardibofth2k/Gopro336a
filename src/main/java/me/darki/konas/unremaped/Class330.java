package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class330
extends Enum {
    public static Class330 SEQUENTIAL = new Class330("SEQUENTIAL", 0);
    public static Class330 VANILLA = new Class330("VANILLA", 1);
    public static Class330[] Field527;

    static {
        Field527 = new Class330[]{SEQUENTIAL, VANILLA};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class330() {
        void var2_-1;
        void var1_-1;
    }

    public static Class330[] Method595() {
        return (Class330[])Field527.clone();
    }

    public static Class330 Method596(String string) {
        return Enum.valueOf(Class330.class, string);
    }
}