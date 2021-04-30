package me.darki.konas.unremaped;

import me.darki.konas.*;
import java.util.ArrayList;

public class Class484 {
    public static ArrayList<String> Field2362 = new ArrayList();

    public static void Method2067(String string) {
        Field2362.add(string);
    }

    public static ArrayList<String> Method2068() {
        return Field2362;
    }

    public static void Method2069(String string) {
        Field2362.remove(string);
    }

    public static void Method2070() {
        Field2362.clear();
    }

    public static boolean Method2071(String string) {
        for (String string2 : Field2362) {
            if (!string2.equalsIgnoreCase(string)) continue;
            return true;
        }
        return false;
    }
}