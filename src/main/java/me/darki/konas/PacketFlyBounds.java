package me.darki.konas;

public class PacketFlyBounds
extends Enum {
    public static PacketFlyBounds NONE = new PacketFlyBounds("NONE", 0);
    public static PacketFlyBounds WORLD = new PacketFlyBounds("WORLD", 1);
    public static PacketFlyBounds STRICT = new PacketFlyBounds("STRICT", 2);
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
    public PacketFlyBounds() {
        void var2_-1;
        void var1_-1;
    }
}
