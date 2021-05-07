package me.darki.konas.unremaped;

import me.darki.konas.*;
import java.util.ArrayList;
import net.minecraft.entity.Entity;

public class Class546 {
    public static ArrayList<Integer> Field959 = new ArrayList();

    public static void Method959(int n) {
        Field959.remove((Object)n);
    }

    public static void Method960(Entity entity) {
        Field959.remove((Object)entity.getEntityId());
    }

    public static boolean Method961(int n) {
        return Field959.contains(n);
    }

    public static void Method962(int n) {
        Field959.add(n);
    }

    public static boolean Method963(Entity entity) {
        return Field959.contains(entity.getEntityId());
    }

    public static void Method964() {
        Field959.clear();
    }

    public static void Method965(Entity entity) {
        Field959.add(entity.getEntityId());
    }
}