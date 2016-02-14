package com.gdx.wallpaper.setting.eventbus.collection;

import com.gdx.wallpaper.collection.Collection;
import com.gdx.wallpaper.setting.database.operation.UpdateOperation;
import com.gdx.wallpaper.transition.Transition;

public class CollectionChangedEvent {

    private Collection collection;
    private UpdateOperation<Transition> updateOperation;

    public CollectionChangedEvent(Collection collection,
                                  UpdateOperation updateOperation) {
        this.collection = collection;
        this.updateOperation = updateOperation;
    }

    public Collection getCollection() {
        return collection;
    }

    public UpdateOperation getUpdateOperation() {
        return updateOperation;
    }
}
