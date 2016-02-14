package com.gdx.wallpaper.setting;

public class IntValue {

    private int current;
    private int max;

    public IntValue(int current, int max) {
        this.current = current;
        this.max = max;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if (current > max) {
            current = max;
        }

        this.current = current;
    }

    public int getMax() {
        return max;
    }
}