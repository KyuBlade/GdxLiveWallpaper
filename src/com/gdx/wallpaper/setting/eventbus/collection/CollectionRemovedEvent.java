package com.gdx.wallpaper.setting.eventbus.collection;

public class CollectionRemovedEvent {

    private long collectionId;

    public CollectionRemovedEvent(long collectionId) {
        this.collectionId = collectionId;
    }

    public long getCollectionId() {
        return collectionId;
    }
}
