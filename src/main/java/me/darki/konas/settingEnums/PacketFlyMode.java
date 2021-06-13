package me.darki.konas.settingEnums;

public enum PacketFlyMode {
    UP("UP", 0),
    PRESERVE("PRESERVE", 1),
    DOWN("DOWN", 2),
    LIMITJITTER("LIMITJITTER", 3),
    BYPASS("BYPASS", 4),
    OBSCURE("OBSCURE", 5);
    public static PacketFlyMode[] Field2050;

    static {
        Field2050 = new PacketFlyMode[]{UP, PRESERVE, DOWN, LIMITJITTER, BYPASS, OBSCURE};
    }

    public static PacketFlyMode[] Method1883() {
        return Field2050.clone();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    PacketFlyMode(String a, int b) {
    }

    public static PacketFlyMode Method1884(String string) {
        return Enum.valueOf(PacketFlyMode.class, string);
    }
}