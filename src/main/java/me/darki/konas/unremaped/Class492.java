package me.darki.konas.unremaped;

import me.darki.konas.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Class492 {
    public static CopyOnWriteArrayList<Class469> Field2191 = new CopyOnWriteArrayList();

    public static void Method1986() {
        Field2191.clear();
    }

    public Class469 Method1987(String string) {
        for (Class469 class469 : Field2191) {
            if (!class469.Method2206().equalsIgnoreCase(string)) continue;
            return class469;
        }
        return null;
    }

    public static boolean Method1988(String string) {
        if (string == null) {
            return false;
        }
        for (Class469 class469 : Field2191) {
            if (!class469.Method2206().equals(string.replaceAll("-", ""))) continue;
            return true;
        }
        return false;
    }

    public static boolean Method1989(String string) {
        if (string == null) {
            return false;
        }
        for (Class469 class469 : Field2191) {
            if (!class469.Method2205().equalsIgnoreCase(string)) continue;
            return true;
        }
        return false;
    }

    public static void Method1990(String string, String string2) {
        if (!Field2191.contains(new Class469(string, string2.replaceAll("-", "")))) {
            Field2191.add(new Class469(string, string2.replaceAll("-", "")));
        }
    }

    public static boolean Method1991(String string, Class469 class469) {
        return class469.Method2205().equalsIgnoreCase(string);
    }

    public static void Method1992(String string) {
        Field2191.removeIf(arg_0 -> Class492.Method1991(string, arg_0));
    }

    public static CopyOnWriteArrayList<Class469> Method1993() {
        return Field2191;
    }
}