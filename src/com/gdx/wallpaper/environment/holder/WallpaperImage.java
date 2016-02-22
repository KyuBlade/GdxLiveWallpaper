package com.gdx.wallpaper.environment.holder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

public class WallpaperImage extends Widget {

    private Texture texture;

    public WallpaperImage() {
        setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    protected void sizeChanged() {
        invalidateHierarchy();
    }

    @Override
    public float getPrefWidth() {
        return getWidth();
    }

    @Override
    public float getPrefHeight() {
        return getHeight();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (texture == null) {
            return;
        }

        final Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a);

        final float width = getWidth();
        final float height = getHeight();
        batch.draw(texture, getX(), getY(), width, height, 0, 0, (int) width, (int) height, false,
                   true);
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}