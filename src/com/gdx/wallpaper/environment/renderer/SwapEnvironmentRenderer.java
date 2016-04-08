package com.gdx.wallpaper.environment.renderer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdx.wallpaper.environment.type.SwapEnvironment;
import com.gdx.wallpaper.image.ImageManager;
import com.gdx.wallpaper.transition.Transition;

public class SwapEnvironmentRenderer extends AbstractEnvironmentRenderer<SwapEnvironment> {

    public SwapEnvironmentRenderer(SwapEnvironment environment, ImageManager imageManager,
                                   Transition transition, Batch batch, Skin skin) {
        super(environment, imageManager, transition, batch, skin);
    }
}