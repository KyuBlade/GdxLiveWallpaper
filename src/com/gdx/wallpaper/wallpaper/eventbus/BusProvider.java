package com.gdx.wallpaper.wallpaper.eventbus;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public class BusProvider {

    public static final Bus getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {

        private static final Bus INSTANCE = new Bus(ThreadEnforcer.ANY, "WallpaperEventBus");
    }
}
