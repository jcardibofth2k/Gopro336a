package me.darki.konas.settingEnums;

public enum ACRotateMode {
    OFF("OFF", 0), TRACK("TRACK", 1), INTERACT("INTERACT", 2);

    public static ACRotateMode[] Field536;

    public static ACRotateMode Method611(String string) {
        return Enum.valueOf(ACRotateMode.class, string);
    }

    public static ACRotateMode[] Method612() {
        return Field536.clone();
    }

    static {
        Field536 = new ACRotateMode[]{OFF, TRACK, INTERACT};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    ACRotateMode(String a, int b) {
    }
}