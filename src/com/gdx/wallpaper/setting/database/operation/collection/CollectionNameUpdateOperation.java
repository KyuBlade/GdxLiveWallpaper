package com.gdx.wallpaper.setting.database.operation.collection;

import android.content.ContentValues;

import com.gdx.wallpaper.collection.Collection;
import com.gdx.wallpaper.setting.database.DatabaseHelper;
import com.gdx.wallpaper.setting.database.operation.UpdateOperation;

public class CollectionNameUpdateOperation implements UpdateOperation<Collection> {

    @Override
    public void provide(Collection object, ContentValues values) {
        values.put(DatabaseHelper.CollectionColumns.NAME, object.getName());
    }
}
