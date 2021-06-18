package me.darki.konas.util;

public class TimerUtil {
    public long timeSaved = System.currentTimeMillis();

    public long GetCurrentTime() {
        return this.timeSaved;
    }

    public boolean GetDifferenceTiming(double d) {
        return (double)(System.currentTimeMillis() - this.timeSaved) >= d;
    }

    public void SetCurrentTime(long l) {
        this.timeSaved = l;
    }

    public void UpdateCurrentTime() {
        this.timeSaved = System.currentTimeMillis();
    }
}