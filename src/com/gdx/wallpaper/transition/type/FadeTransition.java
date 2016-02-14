package com.gdx.wallpaper.transition.type;

import android.content.ContentValues;
import android.database.Cursor;

import com.gdx.wallpaper.setting.IntValue;
import com.gdx.wallpaper.setting.database.DatabaseHelper.TransitionColumns;
import com.gdx.wallpaper.transition.Transition;
import com.gdx.wallpaper.transition.TransitionType;
import com.gdx.wallpaper.transition.property.Fadable;

public class FadeTransition extends Transition implements Fadable {

    private IntValue fadeTime;

    public FadeTransition() {
        super(TransitionType.FADE);

        fadeTime = new IntValue(1000, 10000);
    }

    @Override
    public void build(Cursor cursor) {
        cycleTime.setCurrent(cursor.getInt(cursor.getColumnIndex(TransitionColumns.CYCLE_TIME)));
        fadeTime.setCurrent(cursor.getInt(cursor.getColumnIndex(TransitionColumns.FADE_TIME)));
    }

    @Override
    public void provideInsert(ContentValues values) {
        values.put(TransitionColumns.TYPE, TransitionType.FADE.getId());
        values.put(TransitionColumns.CYCLE_TIME, cycleTime.getCurrent());
        values.put(TransitionColumns.FADE_TIME, fadeTime.getCurrent());
    }

    @Override
    public IntValue getFadeTime() {
        return fadeTime;
    }

    @Override
    public void setFadeTime(IntValue value) {
        this.fadeTime = value;
    }

    @Override
    protected String internalToString() {
        return new StringBuilder().append("FadeTransition[fadeTime=")
                .append(String.valueOf(fadeTime.getCurrent())).append(
                        ']').toString();
    }
}