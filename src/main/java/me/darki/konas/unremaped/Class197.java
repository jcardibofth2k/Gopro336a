package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class197
extends Enum {
    public static Class197 CUSTOM = new Class197("CUSTOM", 0);
    public static Class197 LOGOUT = new Class197("LOGOUT", 1);
    public static Class197 DEATH = new Class197("DEATH", 2);
    public static Class197[] Field46;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class197() {
        void var2_-1;
        void var1_-1;
    }

    static {
        Field46 = new Class197[]{CUSTOM, LOGOUT, DEATH};
    }

    public static Class197 Method98(String string) {
        return Enum.valueOf(Class197.class, string);
    }

    public static Class197[] Method99() {
        return (Class197[])Field46.clone();
    }
}