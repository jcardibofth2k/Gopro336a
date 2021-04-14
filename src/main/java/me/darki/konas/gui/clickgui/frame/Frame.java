package me.darki.konas.gui.clickgui.frame;

public abstract class Frame {
    public String name;
    public float posX;
    public float posY;
    public float previousPosX;
    public float previousPosY;
    public float width;
    public float height;
    public boolean dragging;
    public boolean extended;

    public void setPosX(float f) {
        this.posX = f;
    }
   
    public void Method712() {
    }
    
    public void Method704(int n, int n2, int n3, long l) {
    }

    public void onMove(float f, float f2) {
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public void onKeyTyped(char c, int n) {
    }

    public void setPreviousY(float f) {
        this.previousPosY = f;
    }

    public boolean onMouseClicked(int n, int n2, int n3) {
        return false;
    }

    public float getPreviousX() {
        return this.previousPosX;
    }

    public boolean isExtended() {
        return this.extended;
    }

    public void setDragging(boolean bl) {
        this.dragging = bl;
    }

    public float getPreviousY() {
        return this.previousPosY;
    }

    public static boolean isMouseWithinBounds(int n, int n2, double d, double d2, double d3, double d4) {
        return (double)n >= d && (double)n <= d + d3 && (double)n2 >= d2 && (double)n2 <= d2 + d4;
    }

    public void setExtended(boolean bl) {
        this.extended = bl;
    }

    public boolean isDragging() {
        return this.dragging;
    }

    public void Method918(float f) {
        this.width = f;
    }

    public void onMouseReleased(int n, int n2, int n3) {
    }

    public void Method919(float f) {
        this.height = f;
    }

    public String Method920() {
        return this.name;
    }

    public float Method921() {
        return this.posY;
    }

    public void Method442(float f) {
        this.posY = f;
    }

    public void onRender(int n, int n2, float f) {
    }

    public float getPosX() {
        return this.posX;
    }

    public void setPreviousX(float f) {
        this.previousPosX = f;
    }

    public Frame(String string, float f, float f2, float f3, float f4) {
        this.name = string;
        this.posX = f;
        this.posY = f2;
        this.width = f3;
        this.height = f4;
    }
}
