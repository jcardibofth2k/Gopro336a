package me.darki.konas.unremaped;

import me.darki.konas.*;
public class TimerUtil {
    public long Field676 = System.currentTimeMillis();

    public long Method736() {
        return this.Field676;
    }

    public boolean Method737(double d) {
        return (double)(System.currentTimeMillis() - this.Field676) >= d;
    }

    public void Method738(long l) {
        this.Field676 = l;
    }

    public void Method739() {
        this.Field676 = System.currentTimeMillis();
    }
}