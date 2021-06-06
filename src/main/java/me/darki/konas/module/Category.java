package me.darki.konas.module;

public enum Category {
    COMBAT("COMBAT", 0),
    MOVEMENT("MOVEMENT", 1),
    PLAYER("PLAYER", 2),
    RENDER("RENDER", 3),
    MISC("MISC", 4),
    EXPLOIT("EXPLOIT", 5),
    CLIENT("CLIENT", 6);
    public static Category[] Field1872;

    public static Category Method1763(String string) {
        return Enum.valueOf(Category.class, string);
    }

    public static Category[] Method1764() {
        return Field1872.clone();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    Category(String a, int b) {
    }

    static {
        Field1872 = new Category[]{COMBAT, MOVEMENT, PLAYER, RENDER, MISC, EXPLOIT, CLIENT};
    }
}