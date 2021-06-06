package me.darki.konas.settingEnums;

public enum ViewModelMode {
    MAINHAND("MAINHAND", 0),
    OFFHAND("OFFHAND", 1);
    public static ViewModelMode[] Field1086;

    static {
        Field1086 = new ViewModelMode[]{MAINHAND, OFFHAND};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    ViewModelMode(String a, int b) {
    }

    public static ViewModelMode[] Method1121() {
        return Field1086.clone();
    }

    public static
    ViewModelMode Method1122(String string) {
        return Enum.valueOf(ViewModelMode.class, string);
    }
}