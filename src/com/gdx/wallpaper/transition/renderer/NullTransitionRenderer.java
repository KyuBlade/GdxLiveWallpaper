package com.gdx.wallpaper.transition.renderer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdx.wallpaper.image.ImageManager;
import com.gdx.wallpaper.transition.type.NullTransition;

public class NullTransitionRenderer extends AbstractTransitionRenderer<NullTransition> {

    public NullTransitionRenderer(ImageManager imageManager, Batch batch, Skin skin, Camera camera,
                                  NullTransition transition) {
        super(imageManager, batch, skin, camera, transition);
    }

    @Override
    protected void update(float delta) {

    }

    @Override
    public void onTransitionBegin() {
        endTransition();
    }

    @Override
    protected void onTransitionFinish() {
    }
}