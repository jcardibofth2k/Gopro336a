package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class324
extends Enum {
    public static Class324 ROT13 = new Class324("ROT13", 0);
    public static Class324 PROTOCOL = new Class324("PROTOCOL", 1);
    public static Class324[] Field661;

    public static Class324 Method729(String string) {
        return Enum.valueOf(Class324.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class324() {
        void var2_-1;
        void var1_-1;
    }

    public static Class324[] Method730() {
        return (Class324[])Field661.clone();
    }

    static {
        Field661 = new Class324[]{ROT13, PROTOCOL};
    }
}