package me.darki.konas.settingEnums;

public enum PacketFlyBounds {
    NONE("NONE", 0),
    WORLD("WORLD", 1),
    STRICT("STRICT", 2);
    public static PacketFlyBounds[] Field389;

    static {
        Field389 = new PacketFlyBounds[]{NONE, WORLD, STRICT};
    }

    public static PacketFlyBounds Method532(String string) {
        return Enum.valueOf(PacketFlyBounds.class, string);
    }

    public static PacketFlyBounds[] Method533() {
        return Field389.clone();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    PacketFlyBounds(String a, int b) {
    }
}