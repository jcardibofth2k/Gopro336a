package me.darki.konas.settingEnums;

public enum PacketFlyPhase {
    NONE("NONE", 0),
    SLOW("SLOW", 1),
    FAST("FAST", 2);
    public static PacketFlyPhase[] Field2074;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    PacketFlyPhase(String a, int b) {
    }

    public static PacketFlyPhase Method1929(String string) {
        return Enum.valueOf(PacketFlyPhase.class, string);
    }

    public static PacketFlyPhase[] Method1930() {
        return Field2074.clone();
    }

    static {
        Field2074 = new PacketFlyPhase[]{NONE, SLOW, FAST};
    }
}