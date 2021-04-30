package me.darki.konas.unremaped;

import me.darki.konas.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Class562 {
    public ArrayList<Class559> Field724 = new ArrayList();

    public static boolean Method757(Class559 class559) {
        return class559.Method815() == Class561.DEATH;
    }

    public ArrayList<Class559> Method758() {
        return this.Field724;
    }

    public List<Class559> Method759() {
        return this.Field724.stream().filter(Class559::Method816).collect(Collectors.toList());
    }

    public boolean Method760(Class559 class559) {
        for (Class559 class5592 : this.Field724) {
            if (!class559.equals(class5592)) continue;
            return false;
        }
        this.Field724.add(class559);
        return true;
    }

    public Class559 Method761(String string) {
        for (Class559 class559 : this.Field724) {
            if (!class559.Method819().equalsIgnoreCase(string) || !class559.Method816()) continue;
            return class559;
        }
        return null;
    }

    public static boolean Method762(String string, Class559 class559) {
        return class559.Method819().equalsIgnoreCase(string) && class559.Method816();
    }

    public void Method763(String string) {
        this.Field724.removeIf(arg_0 -> Class562.Method762(string, arg_0));
    }

    public void Method764() {
        this.Field724.removeIf(Class562::Method757);
    }

    public void Method765() {
        this.Field724.clear();
    }
}