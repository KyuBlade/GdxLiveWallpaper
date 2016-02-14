package com.gdx.wallpaper.setting.database.operation;

import android.content.ContentValues;

import com.gdx.wallpaper.setting.IntValue;
import com.gdx.wallpaper.setting.database.DatabaseHelper;
import com.gdx.wallpaper.transition.Transition;

public class TransitionCycleTimeUpdateOperation implements UpdateOperation<Transition> {

    @Override
    public void provide(Transition object, ContentValues values) {
        IntValue _cycleTime = object.getCycleTime();
        values.put(DatabaseHelper.TransitionColumns.CYCLE_TIME, _cycleTime.getCurrent());
    }
}
