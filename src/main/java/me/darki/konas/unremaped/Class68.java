package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class68
implements Comparable {
    public String Field187;
    public String Field188;
    public String Field189;
    public String Field190 = "";
    public String Field191 = "";
    public boolean Field192;
    public boolean Field193;
    public boolean Field194;
    public long Field195;

    public Class68(String string, String string2, boolean bl, boolean bl2) {
        this.Field188 = string;
        this.Field187 = string;
        this.Field189 = string2;
        this.Field192 = bl;
        this.Field194 = bl2;
    }

    public boolean Method302() {
        return this.Field194;
    }

    public String Method303() {
        return this.Field188;
    }

    public void Method304(String string) {
        this.Field187 = string;
    }

    public String Method305() {
        return this.Field190;
    }

    public void Method306(String string) {
        this.Field190 = string;
    }

    public long Method307() {
        return this.Field195;
    }

    public void Method308(boolean bl) {
        this.Field193 = bl;
    }

    public String Method309() {
        return this.Field187;
    }

    public void Method310(String string) {
        this.Field191 = string;
    }

    public boolean Method311() {
        return this.Field193;
    }

    public String Method312() {
        return this.Field191;
    }

    public void Method313(boolean bl) {
        this.Field192 = bl;
    }

    public int Method314(Class68 class68) {
        if (this.Field195 < class68.Field195) {
            return 1;
        }
        return this.Field195 > class68.Field195 ? -1 : this.Field187.compareTo(class68.Field187);
    }

    public boolean Method315() {
        return this.Field192;
    }

    public void Method316(String string) {
        this.Field189 = string;
    }

    public String Method317() {
        return this.Field189;
    }

    public void Method318(long l) {
        this.Field195 = l;
    }

    public int compareTo(Object object) {
        return this.Method314((Class68)object);
    }
}