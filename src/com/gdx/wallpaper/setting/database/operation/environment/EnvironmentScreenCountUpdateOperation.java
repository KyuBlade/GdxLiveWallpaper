package com.gdx.wallpaper.setting.database.operation.environment;

import android.content.ContentValues;

import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.setting.database.DatabaseHelper;
import com.gdx.wallpaper.setting.database.operation.UpdateOperation;

public class EnvironmentScreenCountUpdateOperation implements UpdateOperation<Environment> {

    @Override
    public void provide(Environment object, ContentValues values) {
        values.put(DatabaseHelper.EnvironmentColumns.SCREEN_COUNT, object.getScreenCount());
    }
}