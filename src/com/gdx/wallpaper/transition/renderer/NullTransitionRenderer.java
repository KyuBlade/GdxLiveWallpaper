package com.gdx.wallpaper.transition.renderer;

import com.gdx.wallpaper.transition.type.NullTransition;

public class NullTransitionRenderer extends AbstractTransitionRenderer<NullTransition> {

    public NullTransitionRenderer(TransitionRendererManager manager, NullTransition transition) {
        super(manager, transition);
    }

    @Override
    public void render(float deltaTime) {
    }

    @Override
    public void onTransitionBegin() {
        instance.endTransition();
    }

    @Override
    protected void onTransitionFinish() {
    }
}