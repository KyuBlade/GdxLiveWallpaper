package com.gdx.wallpaper.wallpaper.eventbus;

public class WallpaperChangeEvent {

    private final boolean backward;

    public WallpaperChangeEvent(boolean backward) {
        this.backward = backward;
    }

    public boolean isBackward() {
        return backward;
    }
}
