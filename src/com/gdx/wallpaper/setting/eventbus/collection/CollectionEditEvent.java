package com.gdx.wallpaper.setting.eventbus.collection;

public class CollectionEditEvent {

    private long collectionId;

    public CollectionEditEvent(long collectionId) {
        this.collectionId = collectionId;
    }

    public long getCollectionId() {
        return collectionId;
    }
}
