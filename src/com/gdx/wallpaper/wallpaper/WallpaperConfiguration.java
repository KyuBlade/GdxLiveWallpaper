package com.gdx.wallpaper.wallpaper;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class WallpaperConfiguration extends AndroidApplicationConfiguration {

    public WallpaperConfiguration() {
        useAccelerometer = false;
        useCompass = false;
        disableAudio = true;
        useGLSurfaceView20API18 = true;
    }
}