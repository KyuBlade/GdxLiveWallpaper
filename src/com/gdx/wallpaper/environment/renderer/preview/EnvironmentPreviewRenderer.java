package com.gdx.wallpaper.environment.renderer.preview;

import com.badlogic.gdx.Gdx;
import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.setting.fragment.AbstractPreviewRenderer;
import com.gdx.wallpaper.transition.renderer.TransitionQuadMesh;

public abstract class EnvironmentPreviewRenderer<T extends Environment>
        extends AbstractPreviewRenderer {

    protected final T environment;

    public EnvironmentPreviewRenderer(T environment) {
        super(environment,
              new TransitionQuadMesh(0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()),
              "shaders/environments/environment.vert",
              "shaders/environments/" + environment.getType().getShader());

        this.environment = environment;
    }
}