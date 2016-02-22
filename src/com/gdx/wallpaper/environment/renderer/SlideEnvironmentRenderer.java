package com.gdx.wallpaper.environment.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.environment.holder.ImageSurface;
import com.gdx.wallpaper.environment.holder.ImageSurfaceHolder;
import com.gdx.wallpaper.image.ImageManager;
import com.gdx.wallpaper.transition.Transition;
import com.gdx.wallpaper.wallpaper.WallpaperHomeInfo;

import aurelienribon.tweenengine.TweenManager;

public class SlideEnvironmentRenderer extends Scene2DEnvironmentRenderer {

    private final HorizontalGroup group;

    public SlideEnvironmentRenderer(Environment environment, ImageManager imageManager,
                                    TweenManager tweenManager,
                                    Transition transition, Skin skin, Batch batch) {
        super(environment, imageManager, tweenManager, transition, skin, batch);

        group = new HorizontalGroup();
        stage.addActor(group);

        for (int i = 0; i < environment.getScreenCount(); i++) {
            ImageSurface surface = new ImageSurface(skin);
            group.addActor(surface);
            group.pack();
            manager.registerSurface(new ImageSurfaceHolder(surface));
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        group.pack();
    }

    @Override
    public void offsetChange(WallpaperHomeInfo homeInfo) {
        float x = -(homeInfo.getPercentOffsets().x * (environment.getScreenCount() - 1) *
                Gdx.graphics.getWidth());
        group.setX(x);
    }
}