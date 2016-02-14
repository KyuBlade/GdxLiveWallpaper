package com.gdx.wallpaper.wallpaper.environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.gdx.wallpaper.image.ImageManager;
import com.gdx.wallpaper.transition.Transition;
import com.gdx.wallpaper.transition.renderer.TransitionRendererInstance;
import com.gdx.wallpaper.wallpaper.WallpaperHomeInfo;
import com.gdx.wallpaper.wallpaper.environment.holder.ImageSurface;
import com.gdx.wallpaper.wallpaper.environment.holder.ImageSurfaceHolder;

import aurelienribon.tweenengine.TweenManager;

public class SlideEnvironmentRenderer extends Scene2DEnvironmentRenderer {

    private int screenCount;
    private final HorizontalGroup group;

    public SlideEnvironmentRenderer(ImageManager imageManager, TweenManager tweenManager,
                                    Transition transition, Skin skin, Batch batch) {
        super(imageManager, tweenManager, transition, skin, batch);

        group = new HorizontalGroup();
        stage.addActor(group);
    }

    @Override
    public void resize(int width, int height) {
        group.setSize(width, height);
    }

    @Override
    public void offsetChange(WallpaperHomeInfo homeInfo) {
        int screenCount = homeInfo.getScreenCount();
        int screenCountDiff = screenCount - this.screenCount;
        if (screenCountDiff > 0) { // Add
            for (int i = 0; i < screenCountDiff; i++) {
                ImageSurface surface = new ImageSurface(skin);
                surface.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                group.addActor(surface);
                manager.registerSurface(new ImageSurfaceHolder(surface));
            }
        } else if (screenCountDiff < 0) { // Remove
            for (int i = 0; i < screenCountDiff; i++) {
                Array<TransitionRendererInstance> instances = manager.getInstances();
                TransitionRendererInstance instance = instances.peek();
                manager.unregisterSurface(instance.getSurface());
            }
        }

        this.screenCount = screenCount;

        group.setX(
                -(homeInfo.getPercentOffsets().x * (screenCount - 1) * Gdx.graphics.getWidth()) -
                        Gdx.graphics.getWidth() * 0.5f);
    }
}
