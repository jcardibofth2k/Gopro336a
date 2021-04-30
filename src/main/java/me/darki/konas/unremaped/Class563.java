package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class563 {
    public long Field658 = System.nanoTime();

    public boolean Method723(double d) {
        return (double)(System.nanoTime() - this.Field658) >= d;
    }

    public long Method724() {
        return this.Field658;
    }

    public void Method725(long l) {
        this.Field658 = l;
    }

    public void Method726() {
        this.Field658 = System.nanoTime();
    }
}