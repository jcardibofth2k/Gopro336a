package me.darki.konas.settingEnums;

public enum PacketFlyType {
    FACTOR("FACTOR", 0),
    SETBACK("SETBACK", 1),
    FAST("FAST", 2),
    SLOW("SLOW", 3),
    DESYNC("DESYNC", 4);
    public static PacketFlyType[] Field2351;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    PacketFlyType(String a, int b) {
    }

    static {
        Field2351 = new PacketFlyType[]{FACTOR, SETBACK, FAST, SLOW, DESYNC};
    }

    public static PacketFlyType[] Method2061() {
        return Field2351.clone();
    }

    public static PacketFlyType Method2062(String string) {
        return Enum.valueOf(PacketFlyType.class, string);
    }
}