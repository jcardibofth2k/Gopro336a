package me.darki.konas.settingEnums;

public enum ACInteractMode {
    VANILLA("VANILLA", 0),
    NORMAL("NORMAL", 1),
    STRICT("STRICT", 2);
    public static ACInteractMode[] Field613;

    public static ACInteractMode Method658(String string) {
        return Enum.valueOf(ACInteractMode.class, string);
    }

    public static ACInteractMode[] Method659() {
        return Field613.clone();
    }

    static {
        Field613 = new ACInteractMode[]{VANILLA, NORMAL, STRICT};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    ACInteractMode(String a, int b) {
    }
}