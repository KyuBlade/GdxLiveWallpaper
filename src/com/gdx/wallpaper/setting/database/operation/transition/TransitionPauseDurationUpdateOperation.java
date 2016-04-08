package com.gdx.wallpaper.setting.database.operation.transition;

import android.content.ContentValues;

import com.gdx.wallpaper.setting.database.DatabaseHelper;
import com.gdx.wallpaper.setting.database.operation.UpdateOperation;
import com.gdx.wallpaper.transition.Transition;

public class TransitionPauseDurationUpdateOperation implements UpdateOperation<Transition> {

    @Override
    public void provide(Transition object, ContentValues values) {
        values.put(DatabaseHelper.TransitionColumns.PAUSE_DURATION, object.getPauseDuration());
    }
}
