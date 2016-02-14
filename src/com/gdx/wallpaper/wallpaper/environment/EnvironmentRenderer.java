package com.gdx.wallpaper.wallpaper.environment;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.wallpaper.image.ImageManager;
import com.gdx.wallpaper.transition.Transition;
import com.gdx.wallpaper.transition.renderer.TransitionRendererManager;
import com.gdx.wallpaper.wallpaper.WallpaperHomeInfo;

import aurelienribon.tweenengine.TweenManager;

public abstract class EnvironmentRenderer {

    protected TransitionRendererManager manager;
    protected final Viewport viewport;

    public EnvironmentRenderer(ImageManager imageManager, TweenManager tweenManager,
                               Transition transition, Batch batch) {
        manager = new TransitionRendererManager(imageManager, tweenManager, transition, batch);
        this.viewport = new ScreenViewport();
    }

    public void render(float delta) {
        manager.update(delta);
    }

    public void resize(int width, int height) {
        manager.resize(width, height);
    }

    public void offsetChange(WallpaperHomeInfo homeInfo) {
    }

    public void dispose() {
    }
}