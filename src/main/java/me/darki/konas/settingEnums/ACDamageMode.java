package me.darki.konas.settingEnums;

public enum ACDamageMode {
    NONE("NONE", 0),
    FLAT("FLAT", 1),
    SHADED("SHADED", 2);
    public static ACDamageMode[] Field429;

    public static ACDamageMode[] Method544() {
        return Field429.clone();
    }

    public static ACDamageMode Method545(String string) {
        return Enum.valueOf(ACDamageMode.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    ACDamageMode(String a, int b) {
    }

    static {
        Field429 = new ACDamageMode[]{NONE, FLAT, SHADED};
    }
}