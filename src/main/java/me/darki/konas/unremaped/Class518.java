package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class518 {
    public Class515 Field1286 = null;

    public boolean Method1301(Class515 class515) {
        if (this.Field1286 == null) {
            this.Field1286 = class515;
            return true;
        }
        return false;
    }

    public boolean Method1302() {
        return this.Field1286 != null;
    }

    public void Method1303() {
        this.Field1286 = null;
    }

    public String Method1304() {
        if (this.Field1286 == null) {
            return "NONE";
        }
        return this.Field1286.Method1311();
    }
}