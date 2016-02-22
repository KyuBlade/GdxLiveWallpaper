package com.gdx.wallpaper.environment;

import com.gdx.wallpaper.setting.MappedCache;

public class EnvironmentCache extends MappedCache<Environment> {

    protected EnvironmentCache() {
        super();
    }

    protected void put(Environment environment) {
        put(environment.getId(), environment);
    }

    @Override
    protected void remove(Environment object) {
        cache.remove(object.getId());
    }
}
