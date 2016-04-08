package com.gdx.wallpaper.environment.renderer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdx.wallpaper.environment.type.PageCurlEnvironment;
import com.gdx.wallpaper.image.ImageManager;
import com.gdx.wallpaper.transition.Transition;

public class PageCurlEnvironmentRenderer extends AbstractEnvironmentRenderer<PageCurlEnvironment> {

    public PageCurlEnvironmentRenderer(PageCurlEnvironment environment, ImageManager imageManager,
                                       Transition transition, Batch batch, Skin skin) {
        super(environment, imageManager, transition, batch, skin);
    }
}