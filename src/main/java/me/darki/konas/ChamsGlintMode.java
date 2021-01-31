package me.darki.konas;

public class ChamsGlintMode
extends Enum {
    public static ChamsGlintMode NONE = new ChamsGlintMode("NONE", 0);
    public static ChamsGlintMode VANILLA = new ChamsGlintMode("VANILLA", 1);
    public static ChamsGlintMode CUSTOM = new ChamsGlintMode("CUSTOM", 2);
    public static ChamsGlintMode[] Field2178;

    static {
        Field2178 = new ChamsGlintMode[]{NONE, VANILLA, CUSTOM};
    }

    public static ChamsGlintMode[] Method1976() {
        return (ChamsGlintMode[])Field2178.clone();
    }

    public static ChamsGlintMode Method1977(String string) {
        return Enum.valueOf(ChamsGlintMode.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public ChamsGlintMode() {
        void var2_-1;
        void var1_-1;
    }
}
