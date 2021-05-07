package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class499 {
    public double[][] Field2076 = new double[10][2];
    public int Field2077 = 0;
    public double Field2078;
    public double Field2079;
    public double Field2080;
    public double Field2081;
    public float Field2082;

    public Class499(double d, double d2, double d3, double d4, float f) {
        this.Field2080 = d;
        this.Field2081 = d2;
        this.Field2078 = d3;
        this.Field2079 = d4;
        this.Field2082 = f;
    }

    public void Method1933(double d, double d2) {
        double d3 = (d -= this.Field2078) * Math.cos(this.Field2082) - (d2 -= this.Field2079) * Math.sin(this.Field2082);
        double d4 = d * Math.sin(this.Field2082) + d2 * Math.cos(this.Field2082);
        this.Field2076[this.Field2077++] = new double[]{d3 += this.Field2078, d4 += this.Field2079};
    }

    public double[] Method1934(int n) {
        return this.Field2076[n];
    }
}