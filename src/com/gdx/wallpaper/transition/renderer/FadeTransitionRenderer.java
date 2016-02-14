package com.gdx.wallpaper.transition.renderer;

import com.gdx.wallpaper.image.ManagedImage;
import com.gdx.wallpaper.transition.ManagedImageAccessor;
import com.gdx.wallpaper.transition.type.FadeTransition;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;

public class FadeTransitionRenderer extends AbstractTransitionRenderer<FadeTransition> {

    private BaseTween currentTween;

    public FadeTransitionRenderer(TransitionRendererManager manager, FadeTransition transition) {
        super(manager, transition);
    }

    @Override
    public void onTransitionBegin() {
        final ManagedImage currentImage = instance.currentImage;
        final ManagedImage nextImage = instance.nextImage;

        currentTween = Timeline.createSequence()
                .push(Tween.set(nextImage, ManagedImageAccessor.ALPHA).target(0f))

                .beginParallel()
                .push(Tween.to(currentImage, ManagedImageAccessor.ALPHA,
                               transition.getFadeTime().getCurrent() * 0.001f)
                              .target(0f))
                .push(Tween.to(nextImage, ManagedImageAccessor.ALPHA,
                               transition.getFadeTime().getCurrent() * 0.001f)
                              .target(1f))
                .end()

                .push(Tween.call(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        instance.endTransition();
                    }
                }))
                .start(manager.tweenManager);
    }

    @Override
    protected void onTransitionFinish() {
    }

    @Override
    public void render(float deltaTime) {
    }
}
