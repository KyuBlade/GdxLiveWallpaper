package com.gdx.wallpaper.wallpaper;

import com.badlogic.gdx.input.GestureDetector;

public class WallpaperGestureDetector extends GestureDetector {

    public WallpaperGestureDetector() {
        super(50, 0.55f, 1f, 0.15f, new WallpaperGestureListener());
    }
}
