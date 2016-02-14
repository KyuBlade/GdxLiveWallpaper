package com.gdx.wallpaper.setting.eventbus.collection;

import com.gdx.wallpaper.collection.Collection;

public class EntriesInsertedEvent {

    private Collection collection;
    private String[] entries;

    public EntriesInsertedEvent(Collection collection, String[] entries) {
        this.collection = collection;
        this.entries = entries;
    }

    public Collection getCollection() {
        return collection;
    }

    public String[] getEntries() {
        return entries;
    }
}
