package me.darki.konas.gui.clickgui.component;

public abstract class Component {
    public String name;
    public float Field1424;
    public float Field1425;
    public float Field1426;
    public float Field1427;
    public float offsetX;
    public float offsetY;
    public float Field1430;
    public float Field1431;
    public float width;
    public float height;
    public boolean dragging;
    public boolean extended;

    public float getOffsetY() {
        return this.offsetY;
    }

    public void Method1471(float f) {
        this.Field1431 = f;
    }

    public void setYOffset(float f) {
        this.offsetY = f;
    }

    public boolean Method106(int n, int n2, int n3) {
        return false;
    }

    public float getXOffset() {
        return this.offsetX;
    }

    public void Method102(char c, int n) {
    }

    public Component(String string, float f, float f2, float f3, float f4, float f5, float f6) {
        this.name = string;
        this.Field1424 = f;
        this.Field1425 = f2;
        this.offsetX = f3;
        this.offsetY = f4;
        this.Field1430 = f + f3;
        this.Field1431 = f2 + f4;
        this.width = f5;
        this.height = f6;
    }

    public void Method1474(float f) {
        this.Field1424 = f;
    }

    public float Method1475() {
        return this.Field1430;
    }

    public float Method1476() {
        return this.Field1431;
    }

    public float Method1477() {
        return this.Field1427;
    }

    public void Method1478(float f) {
        this.Field1427 = f;
    }

    public void Method647(int n, int n2, int n3) {
    }

    public float Method1479() {
        return this.width;
    }

    public void Method105(int n, int n2, float f) {
    }

    public String Method1480() {
        return this.name;
    }

    public float Method1481() {
        return this.height;
    }

    public float Method1482() {
        return this.Field1426;
    }

    public void Method649(int n, int n2, int n3, long l) {
    }

    public void setExtended(boolean bl) {
        this.extended = bl;
    }

    public void Method1484(float f) {
        this.offsetX = f;
    }

    public void Method1485(float f) {
        this.Field1425 = f;
    }

    public boolean Method1486() {
        return this.extended;
    }

    public boolean Method1487() {
        return this.dragging;
    }

    public float Method1488() {
        return this.Field1424;
    }

    public void Method667() {
    }

    public float Method1489() {
        return this.Field1425;
    }

    public void Method1490(float f) {
        this.Field1426 = f;
    }

    public void onMove(float f, float f2) {
        this.Method1474(f);
        this.Method1485(f2);
        this.Method1491(this.Method1488() + this.getXOffset());
        this.Method1471(this.Method1489() + this.getOffsetY());
    }

    public void Method1491(float f) {
        this.Field1430 = f;
    }

    public void setDragging(boolean bl) {
        this.dragging = bl;
    }

    public static boolean isMouseWithinBounds(int n, int n2, double d, double d2, double d3, double d4) {
        return (double)n >= d && (double)n <= d + d3 && (double)n2 >= d2 && (double)n2 <= d2 + d4;
    }
}