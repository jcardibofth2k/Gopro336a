package me.darki.konas.settingEnums;

public enum ChamsGlintMode {
    NONE("NONE", 0),
    VANILLA("VANILLA", 1),
    CUSTOM("CUSTOM", 2);
    public static ChamsGlintMode[] Field2178;

    static {
        Field2178 = new ChamsGlintMode[]{NONE, VANILLA, CUSTOM};
    }

    public static ChamsGlintMode[] Method1976() {
        return Field2178.clone();
    }

    public static ChamsGlintMode Method1977(String string) {
        return Enum.valueOf(ChamsGlintMode.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    ChamsGlintMode(String a, int b) {
    }
}