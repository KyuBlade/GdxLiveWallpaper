package com.gdx.wallpaper.environment.renderer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.image.ImageManager;
import com.gdx.wallpaper.transition.Transition;

import aurelienribon.tweenengine.TweenManager;

public class NullEnvironmentRenderer extends EnvironmentRenderer {

    public NullEnvironmentRenderer(Environment environment,
                                   ImageManager imageManager,
                                   TweenManager tweenManager,
                                   Transition transition,
                                   Batch batch, String shader) {
        super(environment, imageManager, tweenManager, transition, batch, shader);
    }
}
