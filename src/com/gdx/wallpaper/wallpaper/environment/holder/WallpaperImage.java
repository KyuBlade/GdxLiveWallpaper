package com.gdx.wallpaper.wallpaper.environment.holder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class WallpaperImage extends Actor {

    private Texture texture;

    public WallpaperImage() {

    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a);
        batch.draw(texture, getX(), getY(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}