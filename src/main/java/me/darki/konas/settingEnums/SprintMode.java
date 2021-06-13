package me.darki.konas.settingEnums;

public enum SprintMode {
    LEGIT("LEGIT", 0),
    RAGE("RAGE", 1);
    public static SprintMode[] Field251;

    public static SprintMode Method397(String string) {
        return Enum.valueOf(SprintMode.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    SprintMode(String a, int b) {
    }

    static {
        Field251 = new SprintMode[]{LEGIT, RAGE};
    }

    public static SprintMode[] Method398() {
        return Field251.clone();
    }
}