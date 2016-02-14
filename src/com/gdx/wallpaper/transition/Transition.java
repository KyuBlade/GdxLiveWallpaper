package com.gdx.wallpaper.transition;

import android.content.ContentValues;
import android.database.Cursor;

import com.gdx.wallpaper.setting.Builder;
import com.gdx.wallpaper.setting.IntValue;
import com.gdx.wallpaper.setting.database.DatabaseHelper;
import com.gdx.wallpaper.setting.database.DatabaseHelper.CommonColumns;

public abstract class Transition implements Builder {

    protected long id;
    protected TransitionType type;
    protected String name;
    protected boolean random;
    protected boolean displayCyclingProgress;

    protected IntValue cycleTime;

    public Transition(TransitionType type) {
        this.type = type;

        cycleTime = new IntValue(5000, 50000);
    }

    public TransitionType getType() {
        return type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void buildInternal(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(CommonColumns.ID));
        name = cursor
                .getString(cursor.getColumnIndexOrThrow(DatabaseHelper.TransitionColumns.NAME));
        random = cursor
                .getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.TransitionColumns.RANDOM)) != 0;
        displayCyclingProgress = cursor
                .getInt(cursor.getColumnIndexOrThrow(
                        DatabaseHelper.TransitionColumns.DISPLAY_CYCLING_PROGRESS)) != 0;

        build(cursor);
    }

    public abstract void provideInsert(ContentValues values);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRandom() {
        return random;
    }

    public void setRandom(boolean random) {
        this.random = random;
    }

    public boolean isDisplayCyclingProgress() {
        return displayCyclingProgress;
    }

    public void setDisplayCyclingProgress(boolean displayCyclingProgress) {
        this.displayCyclingProgress = displayCyclingProgress;
    }

    public IntValue getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(IntValue cycleTime) {
        this.cycleTime = cycleTime;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Transition[id=").append(id).append(", name=")
                .append(name).append(", type=").append(type).append(", random=").append(random)
                .append(", displayCyclingProgress=").append(displayCyclingProgress)
                .append(", cycleTime=").append(cycleTime.getCurrent()).append(", childTransition=")
                .append(internalToString()).append(
                        "]").toString();
    }

    protected abstract String internalToString();
}