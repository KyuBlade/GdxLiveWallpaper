package com.gdx.wallpaper.transition;

import com.gdx.wallpaper.setting.MappedCache;

public class TransitionCache extends MappedCache<Transition> {

    protected TransitionCache() {
        super();
    }

    protected void put(Transition transition) {
        put(transition.getId(), transition);
    }

    @Override
    protected void remove(Transition object) {
        cache.remove(object.getId());
    }
}
