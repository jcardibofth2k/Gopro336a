package me.darki.konas.unremaped;

import me.darki.konas.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Class157 {
    public static CopyOnWriteArrayList<Class152> Field1807 = new CopyOnWriteArrayList();

    public static void Method1703(Class152 class152) {
        Field1807.remove(class152);
    }

    public static CopyOnWriteArrayList<Class152> Method1704() {
        return Field1807;
    }

    public static void Method1705(Class152 class152) {
        if (!Field1807.contains(class152)) {
            Field1807.add(class152);
        }
    }

    public static void Method1706(CopyOnWriteArrayList<Class152> copyOnWriteArrayList) {
        Field1807 = copyOnWriteArrayList;
    }

    public static void Method1707() {
        Field1807.clear();
    }

    public static Class152 Method1708(String string) {
        for (Class152 class152 : Class157.Method1704()) {
            if (!class152.Method1760().equalsIgnoreCase(string)) continue;
            return class152;
        }
        return null;
    }
}