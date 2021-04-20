package me.darki.konas.setting;

import me.darki.konas.unremaped.Class520;

public class Keybind
implements Class520 {
    public int Field806;

    public Keybind(int n) {
        this.Field806 = n;
    }

    @Override
    public void Method850(int n) {
        this.Field806 = n;
    }

    @Override
    public int Method851() {
        return this.Field806;
    }
}