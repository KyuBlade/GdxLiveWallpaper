package com.gdx.wallpaper.wallpaper;

import com.badlogic.gdx.math.Vector2;

public class WallpaperHomeInfo {

    private boolean scrollingEnabled;
    private boolean landscape;
    private boolean preview;
    private Vector2 screenSize;
    private Vector2 oldPercentOffsets;
    private Vector2 percentOffsets;
    private Vector2 oldPixelOffsets;
    private Vector2 pixelOffsets;
    private Vector2 stepOffsets;

    public WallpaperHomeInfo() {
        screenSize = new Vector2();
        oldPercentOffsets = new Vector2();
        percentOffsets = new Vector2();
        oldPixelOffsets = new Vector2();
        pixelOffsets = new Vector2();
        stepOffsets = new Vector2(1f, 1f);
    }

    public Vector2 getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(int width, int height) {
        screenSize.x = width;
        screenSize.y = height;
        landscape = width > height;
    }

    public Vector2 getOldPercentOffsets() {
        return oldPercentOffsets;
    }

    public Vector2 getPercentOffsets() {
        return percentOffsets;
    }

    public void setPercentOffsets(float x, float y) {
        oldPercentOffsets.set(percentOffsets);

        percentOffsets.x = x;
        percentOffsets.y = y;
    }

    public Vector2 getOldPixelOffsets() {
        return oldPixelOffsets;
    }

    public Vector2 getPixelOffsets() {
        return pixelOffsets;
    }

    public void setPixelOffsets(float x, float y) {
        oldPixelOffsets.set(pixelOffsets);

        pixelOffsets.x = x;
        pixelOffsets.y = y;
    }

    public Vector2 getStepOffsets() {
        return stepOffsets;
    }

    public void setStepOffsets(float x, float y) {
        stepOffsets.x = x;
        stepOffsets.y = y;
    }

    public boolean isScrollingEnabled() {
        return scrollingEnabled;
    }

    public void setScrollingEnabled(boolean scrollingEnabled) {
        this.scrollingEnabled = scrollingEnabled;
    }

    public boolean isLandscape() {
        return landscape;
    }

    public void setPreview(boolean preview) {
        this.preview = preview;
    }

    public boolean isPreview() {
        return preview;
    }

    public int getScreenCount() {
        return Math.round(1f / stepOffsets.x + 1);
    }

    @Override
    public String toString() {
        return new StringBuilder("[screenSize=").append(screenSize).append(", percentOffsets=")
                .append(percentOffsets).append(", pixelOffsets=").append(pixelOffsets)
                .append(", stepOffsets=").append(stepOffsets).append("]").toString();
    }
}
