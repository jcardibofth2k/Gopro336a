package me.darki.konas.module;

public class Category
extends Enum {
    public static Category COMBAT = new Category("COMBAT", 0);
    public static Category MOVEMENT = new Category("MOVEMENT", 1);
    public static Category PLAYER = new Category("PLAYER", 2);
    public static Category RENDER = new Category("RENDER", 3);
    public static Category MISC = new Category("MISC", 4);
    public static Category EXPLOIT = new Category("EXPLOIT", 5);
    public static Category CLIENT = new Category("CLIENT", 6);
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
    public Category(String s, int n) {
        super(s, n);
        void var2_-1;
        void var1_-1;
    }

    static {
        Field1872 = new Category[]{COMBAT, MOVEMENT, PLAYER, RENDER, MISC, EXPLOIT, CLIENT};
    }
}