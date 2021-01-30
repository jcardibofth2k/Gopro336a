package me.darki.konas;

public class ACDamageMode
extends Enum {
    public static ACDamageMode NONE = new ACDamageMode("NONE", 0);
    public static ACDamageMode FLAT = new ACDamageMode("FLAT", 1);
    public static ACDamageMode SHADED = new ACDamageMode("SHADED", 2);
    public static ACDamageMode[] Field429;

    public static ACDamageMode[] Method544() {
        return (ACDamageMode[])Field429.clone();
    }

    public static ACDamageMode Method545(String string) {
        return Enum.valueOf(ACDamageMode.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public ACDamageMode() {
        void var2_-1;
        void var1_-1;
    }

    static {
        Field429 = new ACDamageMode[]{NONE, FLAT, SHADED};
    }
}
