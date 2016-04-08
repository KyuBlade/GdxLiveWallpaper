package com.gdx.wallpaper.transition.renderer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdx.wallpaper.image.ImageManager;
import com.gdx.wallpaper.transition.type.CrossFadeTransition;

public class CrossFadeTransitionRenderer extends TimedTransitionRenderer<CrossFadeTransition> {

    public CrossFadeTransitionRenderer(ImageManager imageManager, Batch batch, Skin skin,
                                       Camera camera, CrossFadeTransition transition) {
        super(imageManager, batch, skin, camera, transition);
    }
}
