package com.gdx.wallpaper.environment;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.environment.renderer.EnvironmentRenderer;

public enum EnvironmentType {
    NONE(R.string.environment_type_none, NullEnvironmentRenderer.class),
    SLIDE(R.string.environment_type_slide, SlideEnvironmentRenderer.class),
    CUBE(R.string.environment_type_cube, CubeEnvironmentRenderer.class);

    private int nameRes;
    protected Class<? extends EnvironmentRenderer> rendererClass;

    EnvironmentType(int nameRes, Class<? extends EnvironmentRenderer> rendererClass) {
        this.nameRes = nameRes;
        this.rendererClass = rendererClass;
    }

    public int getNameRes() {
        return nameRes;
    }

    public Class<? extends EnvironmentRenderer> getRendererClass() {
        return rendererClass;
    }
}