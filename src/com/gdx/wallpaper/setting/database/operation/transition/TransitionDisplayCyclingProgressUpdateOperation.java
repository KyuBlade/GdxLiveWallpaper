package com.gdx.wallpaper.setting.database.operation.transition;

import android.content.ContentValues;

import com.gdx.wallpaper.setting.database.DatabaseHelper;
import com.gdx.wallpaper.setting.database.operation.UpdateOperation;
import com.gdx.wallpaper.transition.Transition;

public class TransitionDisplayCyclingProgressUpdateOperation implements
                                                             UpdateOperation<Transition> {

    @Override
    public void provide(Transition object, ContentValues values) {
        values.put(DatabaseHelper.TransitionColumns.DISPLAY_CYCLING_PROGRESS,
                   object.isDisplayCyclingProgress());
    }
}
