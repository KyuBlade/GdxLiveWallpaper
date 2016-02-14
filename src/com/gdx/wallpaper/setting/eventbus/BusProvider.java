package com.gdx.wallpaper.setting.eventbus;

import com.squareup.otto.Bus;

public class BusProvider {

    public static final Bus getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {

        private static final Bus INSTANCE = new Bus();
    }
}
