package com.gdx.wallpaper.wallpaper.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class Scene2DTexture extends Image {

    @Override
    public void draw(Batch batch, float parentAlpha) {
        validate();

        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        Drawable drawable = getDrawable();
        if (drawable != null) {
            float width = getWidth();
            float height = getHeight();
            drawable.draw(batch, getX(), getY(), getWidth(), getHeight());
        }
    }
}