package com.gdx.wallpaper.setting.eventbus.collection;

import com.gdx.wallpaper.collection.entry.Entry;

public class CollectionEntryChangedEvent {

    private Entry entry;

    public CollectionEntryChangedEvent(Entry entry) {
        this.entry = entry;
    }

    public Entry getEntry() {
        return entry;
    }
}
