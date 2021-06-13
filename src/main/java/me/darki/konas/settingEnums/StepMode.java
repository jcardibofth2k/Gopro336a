package me.darki.konas.settingEnums;

public enum StepMode {
    VANILLA("VANILLA", 0),
    NORMAL("NORMAL", 1),
    NCP("NCP", 2),
    MOTION("MOTION", 3);
    public static StepMode[] Field2075;

    static {
        Field2075 = new StepMode[]{VANILLA, NORMAL, NCP, MOTION};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    StepMode(String a, int b) {
    }

    public static StepMode[] Method1931() {
        return Field2075.clone();
    }

    public static StepMode Method1932(String string) {
        return Enum.valueOf(StepMode.class, string);
    }
}