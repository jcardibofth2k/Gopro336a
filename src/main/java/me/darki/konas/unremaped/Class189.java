package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class189
extends Enum {
    public static Class189 REAL = new Class189("REAL", 0);
    public static Class189 PERCENTAGE = new Class189("PERCENTAGE", 1);
    public static Class189[] Field115;

    static {
        Field115 = new Class189[]{REAL, PERCENTAGE};
    }

    public static Class189[] Method149() {
        return (Class189[])Field115.clone();
    }

    public static Class189 Method150(String string) {
        return Enum.valueOf(Class189.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class189() {
        void var2_-1;
        void var1_-1;
    }
}