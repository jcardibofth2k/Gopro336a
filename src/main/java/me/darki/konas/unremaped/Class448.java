package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class448 {
    public double Field615;
    public double Field616;
    public double Field617;

    public Class448(double d, double d2, double d3) {
        this.Field615 = d;
        this.Field616 = d2;
        this.Field617 = d3;
    }

    public double Method662(Class448 class448) {
        double d = this.Field615 - class448.Field615;
        double d2 = this.Field616 - class448.Field616;
        double d3 = this.Field617 - class448.Field617;
        return Math.sqrt(d * d + d2 * d2 + d3 * d3);
    }

    public boolean equals(Object object) {
        if (object instanceof Class448) {
            Class448 class448 = (Class448)object;
            return Double.compare(this.Field615, class448.Field615) == 0 && Double.compare(this.Field616, class448.Field616) == 0 && Double.compare(this.Field617, class448.Field617) == 0;
        }
        return super.equals(object);
    }
}