package com.gdx.wallpaper.transition.type;

import android.content.ContentValues;
import android.database.Cursor;

import com.gdx.wallpaper.setting.database.DatabaseHelper.TransitionColumns;
import com.gdx.wallpaper.transition.Transition;
import com.gdx.wallpaper.transition.TransitionType;

public class NullTransition extends Transition {

    public NullTransition() {
        super(TransitionType.NONE);
    }

    @Override
    public void build(Cursor cursor) {
        cycleTime.setCurrent(cursor.getInt(cursor.getColumnIndex(TransitionColumns.CYCLE_TIME)));
    }

    @Override
    public void provideInsert(ContentValues values) {
        values.put(TransitionColumns.TYPE, TransitionType.NONE.getId());
        values.put(TransitionColumns.CYCLE_TIME, cycleTime.getCurrent());
    }

    @Override
    protected String internalToString() {
        return new StringBuilder().append("NullTransition[").append(']').toString();
    }
}