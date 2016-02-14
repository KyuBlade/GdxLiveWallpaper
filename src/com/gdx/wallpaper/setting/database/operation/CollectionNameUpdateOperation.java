package com.gdx.wallpaper.setting.database.operation;

import android.content.ContentValues;

import com.gdx.wallpaper.collection.Collection;
import com.gdx.wallpaper.setting.database.DatabaseHelper;

public class CollectionNameUpdateOperation implements UpdateOperation<Collection> {

    @Override
    public void provide(Collection object, ContentValues values) {
        values.put(DatabaseHelper.CollectionColumns.NAME, object.getName());
    }
}
