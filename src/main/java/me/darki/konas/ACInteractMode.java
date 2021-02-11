package me.darki.konas;

public class ACInteractMode
extends Enum {
    public static ACInteractMode VANILLA = new ACInteractMode("VANILLA", 0);
    public static ACInteractMode NORMAL = new ACInteractMode("NORMAL", 1);
    public static ACInteractMode STRICT = new ACInteractMode("STRICT", 2);
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
    public ACInteractMode() {
        void var2_-1;
        void var1_-1;
    }
}
