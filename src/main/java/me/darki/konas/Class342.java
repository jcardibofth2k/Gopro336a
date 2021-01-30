package me.darki.konas;

public class Class342
extends Enum {
    public static Class342 LEGIT = new Class342("LEGIT", 0);
    public static Class342 RAGE = new Class342("RAGE", 1);
    public static Class342[] Field251;

    public static Class342 Method397(String string) {
        return Enum.valueOf(Class342.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class342() {
        void var2_-1;
        void var1_-1;
    }

    static {
        Field251 = new Class342[]{LEGIT, RAGE};
    }

    public static Class342[] Method398() {
        return (Class342[])Field251.clone();
    }
}
