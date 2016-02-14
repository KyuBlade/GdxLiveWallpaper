package com.gdx.wallpaper.wallpaper.environment;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.gdx.wallpaper.image.ImageManager;
import com.gdx.wallpaper.transition.Transition;

import aurelienribon.tweenengine.TweenManager;

public class Scene3DEnvironmentRenderer extends EnvironmentRenderer {

    public Scene3DEnvironmentRenderer(ImageManager imageManager, TweenManager tweenManager,
                                      Transition transition, Batch batch) {
        super(imageManager, tweenManager, transition, batch);
    }

    @Override
    public void render(float delta) {

    }
}