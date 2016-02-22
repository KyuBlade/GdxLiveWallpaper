package com.gdx.wallpaper.environment.renderer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.image.ImageManager;
import com.gdx.wallpaper.transition.Transition;
import com.gdx.wallpaper.transition.renderer.TransitionRendererManager;
import com.gdx.wallpaper.wallpaper.WallpaperHomeInfo;

import aurelienribon.tweenengine.TweenManager;

public class EnvironmentRenderer {

    protected final TransitionRendererManager manager;
    protected final Viewport viewport;
    protected final Environment environment;

    public EnvironmentRenderer(Environment environment, ImageManager imageManager,
                               TweenManager tweenManager,
                               Transition transition, Batch batch, Viewport viewport) {
        this.environment = environment;
        this.viewport = viewport;
        manager =
                new TransitionRendererManager(environment, imageManager, tweenManager, transition,
                                              batch);
    }

    public void render(float delta) {
        manager.update(delta);
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
        manager.resize(width, height);
    }

    public void offsetChange(WallpaperHomeInfo homeInfo) {
    }

    public void dispose() {
    }
}