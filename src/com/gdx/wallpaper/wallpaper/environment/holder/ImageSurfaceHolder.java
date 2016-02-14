package com.gdx.wallpaper.wallpaper.environment.holder;

import com.badlogic.gdx.graphics.Texture;

public class ImageSurfaceHolder extends AbstractSurfaceHolder<ImageSurface> {

    public ImageSurfaceHolder(ImageSurface surface) {
        super(surface);
    }

    @Override
    public void updateTexture(Texture texture) {
        surface.setTexture(texture);
    }

    @Override
    public void updateProgress(float newProgress) {
        surface.indicator.setProgress(newProgress);
    }

    @Override
    public void onRemove() {
        surface.remove();
    }
}