package com.gdx.wallpaper.setting.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class ProgressBarIndicator extends Actor {

    private ProgressBarIndicatorStyle style;

    private float currentProgress;
    private float maxProgress;

    public ProgressBarIndicator(Skin skin) {
        this(skin, "default");
    }

    public ProgressBarIndicator(Skin skin, String styleName) {
        if (skin == null) {
            throw new IllegalArgumentException("Skin must not be null");
        }
        if (styleName == null) {
            throw new IllegalArgumentException("Style name must not be null");
        }

        setStyle(skin.get(styleName, ProgressBarIndicatorStyle.class));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        style.indicatorDrawable.draw(batch, getX(),
                                     getY(),
                                     (getWidth() * (currentProgress / maxProgress)),
                                     getHeight());
    }

    public void setStyle(ProgressBarIndicatorStyle style) {
        this.style = style;

        setColor(style.color);
    }

    public float getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(float maxProgress) {
        this.maxProgress = maxProgress;
    }

    public void setProgress(float currentProgress) {
        this.currentProgress = currentProgress;
    }

    public float getProgress() {
        return currentProgress;
    }

    public static class ProgressBarIndicatorStyle {

        public Color color;
        public Drawable indicatorDrawable;

        public ProgressBarIndicatorStyle() {

        }
    }
}
