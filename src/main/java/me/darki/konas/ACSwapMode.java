package me.darki.konas;

public class ACSwapMode
extends Enum {
    public static ACSwapMode OFF = new ACSwapMode("OFF", 0);
    public static ACSwapMode NORMAL = new ACSwapMode("NORMAL", 1);
    public static ACSwapMode SILENT = new ACSwapMode("SILENT", 2);
    public static ACSwapMode[] Field1791;

    public static ACSwapMode Method1689(String string) {
        return Enum.valueOf(ACSwapMode.class, string);
    }

    static {
        Field1791 = new ACSwapMode[]{OFF, NORMAL, SILENT};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public ACSwapMode() {
        void var2_-1;
        void var1_-1;
    }

    public static ACSwapMode[] Method1690() {
        return (ACSwapMode[])Field1791.clone();
    }
}
