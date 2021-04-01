package me.darki.konas.settingEnums;

public class SprintMode
extends Enum {
    public static SprintMode LEGIT = new SprintMode("LEGIT", 0);
    public static SprintMode RAGE = new SprintMode("RAGE", 1);
    public static SprintMode[] Field251;

    public static SprintMode Method397(String string) {
        return Enum.valueOf(SprintMode.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public SprintMode() {
        void var2_-1;
        void var1_-1;
    }

    static {
        Field251 = new SprintMode[]{LEGIT, RAGE};
    }

    public static SprintMode[] Method398() {
        return Field251.clone();
    }
}