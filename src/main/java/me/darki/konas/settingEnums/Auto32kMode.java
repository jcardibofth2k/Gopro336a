package me.darki.konas.settingEnums;

public class Auto32kMode
extends Enum {
    public static Auto32kMode TICK = new Auto32kMode("TICK", 0);
    public static Auto32kMode ALWAYS = new Auto32kMode("ALWAYS", 1);
    public static Auto32kMode[] Field1438;

    static {
        Field1438 = new Auto32kMode[]{TICK, ALWAYS};
    }

    public static Auto32kMode[] Method1496() {
        return Field1438.clone();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Auto32kMode() {
        void var2_-1;
        void var1_-1;
    }

    public static Auto32kMode Method1497(String string) {
        return Enum.valueOf(Auto32kMode.class, string);
    }
}
