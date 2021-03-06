package com.gdx.wallpaper.environment.renderer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.image.ImageManager;
import com.gdx.wallpaper.transition.Transition;
import com.gdx.wallpaper.environment.holder.ImageSurface;
import com.gdx.wallpaper.environment.holder.ImageSurfaceHolder;

import aurelienribon.tweenengine.TweenManager;

public class NullEnvironmentRenderer extends Scene2DEnvironmentRenderer {

    public NullEnvironmentRenderer(Environment environment, ImageManager imageManager,
                                   TweenManager tweenManager,
                                   Transition transition, Skin skin, Batch batch) {
        super(environment, imageManager, tweenManager, transition, skin, batch);

        ImageSurface surface = new ImageSurface(skin);
        stage.addActor(surface);
        manager.registerSurface(new ImageSurfaceHolder(surface));
    }
}
