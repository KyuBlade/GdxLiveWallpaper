package com.gdx.wallpaper.setting;

import java.util.WeakHashMap;

public abstract class MappedCache<T> {

    protected WeakHashMap<Long, T> cache;

    protected MappedCache() {
        cache = new WeakHashMap<>();
    }

    public void put(long key, T object) {
        cache.put(key, object);
    }

    public T getValue(long key) {
        return cache.get(key);
    }

    public void remove(long key) {
        cache.remove(key);
    }

    protected abstract void remove(T object);

    public int getCount() {
        return cache.size();
    }
}
