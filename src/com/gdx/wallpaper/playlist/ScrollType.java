package com.gdx.wallpaper.playlist;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.wallpaper.environment.EnvironmentRenderer;
import com.gdx.wallpaper.wallpaper.environment.CubeEnvironmentRenderer;
import com.gdx.wallpaper.wallpaper.environment.NullEnvironmentRenderer;
import com.gdx.wallpaper.wallpaper.environment.SlideEnvironmentRenderer;

public enum ScrollType {
    NONE(R.string.playlist_scroll_type_none, NullEnvironmentRenderer.class),
    SLIDE(R.string.playlist_scroll_type_slide, SlideEnvironmentRenderer.class),
    CUBE(R.string.playlist_scroll_type_cube, CubeEnvironmentRenderer.class);

    private int nameRes;
    private Class<? extends EnvironmentRenderer> environementRendererClass;

    ScrollType(int nameRes, Class<? extends EnvironmentRenderer> envRendererClass) {
        this.nameRes = nameRes;
        this.environementRendererClass = envRendererClass;
    }

    public int getNameRes() {
        return nameRes;
    }

    public Class<? extends EnvironmentRenderer> getEnvironementRendererClass() {
        return environementRendererClass;
    }
}