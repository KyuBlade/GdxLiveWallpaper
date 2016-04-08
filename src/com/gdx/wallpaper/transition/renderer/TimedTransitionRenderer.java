package com.gdx.wallpaper.transition.renderer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdx.wallpaper.image.ImageManager;
import com.gdx.wallpaper.transition.Transition;

public class TimedTransitionRenderer<T extends Transition>
        extends AbstractTransitionRenderer<T> {

    private long elapsedTime;

    protected TimedTransitionRenderer(ImageManager imageManager,
                                      Batch batch,
                                      Skin skin,
                                      Camera camera, T transition) {
        super(imageManager, batch, skin, camera, transition);
    }

    @Override
    protected void update(float delta) {
        if (!transitioning) {
            return;
        }
        elapsedTime += delta * 1000L;
        long duration = transition.getTransitionDuration();
        transitionProgress = (float) elapsedTime / duration;
        if (elapsedTime >= duration) {
            endTransition();
        }
    }

    @Override
    protected void onTransitionBegin() {
    }

    @Override
    protected void onTransitionFinish() {
        elapsedTime = 0L;
        transitionProgress = 0L;
    }
}