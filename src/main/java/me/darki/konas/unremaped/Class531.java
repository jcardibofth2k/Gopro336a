package me.darki.konas.unremaped;

import me.darki.konas.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Class531 {
    public List<String> Field1143 = new ArrayList<String>();

    public Class531(ArrayList<String> arrayList) {
        for (String string : arrayList) {
            if (this.Field1143.contains(string.toLowerCase(Locale.ENGLISH))) continue;
            this.Field1143.add(string.toLowerCase(Locale.ENGLISH));
        }
    }

    public void Method1144(String string) {
        this.Field1143.remove(string.toLowerCase(Locale.ENGLISH));
    }

    public void Method1145(ArrayList<String> arrayList) {
        for (String string : arrayList) {
            if (this.Field1143.contains(string.toLowerCase(Locale.ENGLISH))) continue;
            this.Field1143.add(string.toLowerCase(Locale.ENGLISH));
        }
    }

    public List<String> Method1146() {
        return this.Field1143;
    }

    public void Method1147(String string) {
        if (!this.Field1143.contains(string.toLowerCase(Locale.ENGLISH))) {
            this.Field1143.add(string.toLowerCase(Locale.ENGLISH));
        }
    }

    public Class531(String ... stringArray) {
        for (String string : this.Field1143) {
            if (this.Field1143.contains(string.toLowerCase(Locale.ENGLISH))) continue;
            this.Field1143.add(string.toLowerCase(Locale.ENGLISH));
        }
    }
}