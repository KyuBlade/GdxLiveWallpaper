package com.gdx.wallpaper.wallpaper.environment.holder;

import com.badlogic.gdx.graphics.Texture;

/**
 * Represent a surface when a texture can be rendered.
 * Also provide the implementation to render it to the surface
 *
 * @param <T> The type of the surface
 */
public abstract class AbstractSurfaceHolder<T> {

    protected T surface;

    public AbstractSurfaceHolder(T surface) {
        this.surface = surface;
    }

    public abstract void updateTexture(Texture texture);

    public abstract void updateProgress(float newProgress);

    public abstract void resize(int width, int height);

    public abstract void remove();
}