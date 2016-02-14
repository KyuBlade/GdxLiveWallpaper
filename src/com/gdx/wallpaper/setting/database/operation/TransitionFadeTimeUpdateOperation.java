package com.gdx.wallpaper.setting.database.operation;

import android.content.ContentValues;

import com.gdx.wallpaper.setting.IntValue;
import com.gdx.wallpaper.setting.database.DatabaseHelper;
import com.gdx.wallpaper.transition.Transition;
import com.gdx.wallpaper.transition.property.Fadable;

public class TransitionFadeTimeUpdateOperation implements UpdateOperation<Transition> {

    @Override
    public void provide(Transition object, ContentValues values) {
        Fadable _fadable = (Fadable) object;
        IntValue _fadeTime = _fadable.getFadeTime();
        values.put(DatabaseHelper.TransitionColumns.FADE_TIME, _fadeTime.getCurrent());
    }
}
