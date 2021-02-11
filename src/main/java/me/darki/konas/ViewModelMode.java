package me.darki.konas;

public class ViewModelMode
extends Enum {
    public static ViewModelMode MAINHAND = new ViewModelMode("MAINHAND", 0);
    public static ViewModelMode OFFHAND = new ViewModelMode("OFFHAND", 1);
    public static ViewModelMode[] Field1086;

    static {
        Field1086 = new ViewModelMode[]{MAINHAND, OFFHAND};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public
    ViewModelMode() {
        void var2_-1;
        void var1_-1;
    }

    public static ViewModelMode[] Method1121() {
        return Field1086.clone();
    }

    public static
    ViewModelMode Method1122(String string) {
        return Enum.valueOf(ViewModelMode.class, string);
    }
}
