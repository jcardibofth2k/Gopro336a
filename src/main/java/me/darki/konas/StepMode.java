package me.darki.konas;

public class StepMode
extends Enum {
    public static StepMode VANILLA = new StepMode("VANILLA", 0);
    public static StepMode NORMAL = new StepMode("NORMAL", 1);
    public static StepMode NCP = new StepMode("NCP", 2);
    public static StepMode MOTION = new StepMode("MOTION", 3);
    public static StepMode[] Field2075;

    static {
        Field2075 = new StepMode[]{VANILLA, NORMAL, NCP, MOTION};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public StepMode() {
        void var2_-1;
        void var1_-1;
    }

    public static StepMode[] Method1931() {
        return Field2075.clone();
    }

    public static StepMode Method1932(String string) {
        return Enum.valueOf(StepMode.class, string);
    }
}
