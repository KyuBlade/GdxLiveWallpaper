package com.gdx.wallpaper.setting.database.operation;

import android.content.ContentValues;

public interface UpdateOperation<T> {

    /**
     * Provide the values to update into the ContentValues object.
     *
     * @param object the object to update
     * @param values the values to provide
     */
    void provide(T object, ContentValues values);
}
