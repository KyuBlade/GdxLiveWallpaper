package com.gdx.wallpaper.setting.database.upgrade;

import android.database.sqlite.SQLiteDatabase;

public interface Upgradable {

    void upgrade(SQLiteDatabase database);
}
