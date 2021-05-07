package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class469 {
    public String Field2573;
    public String Field2574;

    public String Method2205() {
        return this.Field2573;
    }

    public Class469(String string, String string2) {
        this.Field2573 = string;
        this.Field2574 = string2;
    }

    public String Method2206() {
        return this.Field2574;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Class469)) {
            return false;
        }
        Class469 class469 = (Class469)object;
        return class469.Field2573.equals(this.Field2573) && class469.Field2574.equals(this.Field2574);
    }
}