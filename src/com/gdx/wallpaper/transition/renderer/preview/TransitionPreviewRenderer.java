package com.gdx.wallpaper.transition.renderer.preview;

import com.badlogic.gdx.Gdx;
import com.gdx.wallpaper.setting.fragment.AbstractPreviewRenderer;
import com.gdx.wallpaper.transition.Transition;
import com.gdx.wallpaper.transition.renderer.TransitionQuadMesh;

public abstract class TransitionPreviewRenderer<T extends Transition>
        extends AbstractPreviewRenderer {

    protected final T transition;

    public TransitionPreviewRenderer(T transition) {
        super(transition,
              new TransitionQuadMesh(0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()),
              "shaders/transitions/transition.vert",
              "shaders/transitions/" + transition.getType().getShader());

        this.transition = transition;
    }
}