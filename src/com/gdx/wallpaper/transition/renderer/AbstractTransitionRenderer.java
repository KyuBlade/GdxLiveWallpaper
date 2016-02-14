package com.gdx.wallpaper.transition.renderer;

import com.gdx.wallpaper.transition.Transition;

public abstract class AbstractTransitionRenderer<T extends Transition> {

    protected final TransitionRendererManager manager;
    protected final T transition;
    protected TransitionRendererInstance instance;

    public AbstractTransitionRenderer(TransitionRendererManager manager, T transition) {
        this.manager = manager;
        this.transition = transition;
    }

    protected abstract void onTransitionBegin();

    protected abstract void onTransitionFinish();

    protected abstract void render(float deltaTime);
}
