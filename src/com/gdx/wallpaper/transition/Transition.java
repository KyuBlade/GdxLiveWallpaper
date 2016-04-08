package com.gdx.wallpaper.transition;

import android.content.ContentValues;
import android.database.Cursor;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.ObjectMap;
import com.gdx.wallpaper.setting.ShaderValidatable;
import com.gdx.wallpaper.setting.database.DatabaseHelper;
import com.gdx.wallpaper.setting.database.DatabaseHelper.CommonColumns;

public abstract class Transition implements ShaderValidatable {

    private long id;
    private TransitionType type;
    private String name;
    private boolean random;
    private boolean displayCyclingProgress = true;

    protected long transitionDuration = 1000;
    protected long pauseDuration = 5000;

    private ObjectMap<ShaderProgram, Boolean> validations;

    public Transition(TransitionType type) {
        this.type = type;
        validations = new ObjectMap<>(2);
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

    protected final void buildInternal(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(CommonColumns.ID));
        int typeOrd =
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.TransitionColumns.TYPE));
        type = TransitionType.values()[typeOrd];
        name = cursor
                .getString(cursor.getColumnIndexOrThrow(DatabaseHelper.TransitionColumns.NAME));
        random = cursor
                .getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.TransitionColumns.RANDOM)) != 0;
        displayCyclingProgress = cursor
                .getInt(cursor.getColumnIndexOrThrow(
                        DatabaseHelper.TransitionColumns.DISPLAY_CYCLING_PROGRESS)) != 0;
        pauseDuration = cursor.getLong(cursor.getColumnIndexOrThrow(
                DatabaseHelper.TransitionColumns.PAUSE_DURATION));
        transitionDuration = cursor.getLong(cursor.getColumnIndexOrThrow(
                DatabaseHelper.TransitionColumns.TRANSITION_DURATION));

        build(cursor);
    }

    /**
     * Load database into properties.
     *
     * @param cursor the transition table query result cursor
     */
    protected abstract void build(Cursor cursor);

    public final void provideInsertInternal(ContentValues values) {
        values.put(DatabaseHelper.TransitionColumns.TYPE, type.ordinal());
        values.put(DatabaseHelper.TransitionColumns.PAUSE_DURATION, pauseDuration);
        values.put(DatabaseHelper.TransitionColumns.TRANSITION_DURATION, transitionDuration);

    }

    protected abstract void provideInsert(ContentValues values);

    protected abstract void updateShader(ShaderProgram shader);

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

    public long getTransitionDuration() {
        return transitionDuration;
    }

    public void setTransitionDuration(long transitionDuration) {
        this.transitionDuration = transitionDuration;
    }

    public long getPauseDuration() {
        return pauseDuration;
    }

    public void setPauseDuration(long pauseDuration) {
        this.pauseDuration = pauseDuration;
    }

    @Override
    public void invalidate() {
        for (ShaderProgram key : validations.keys()) {
            validations.put(key, false);
        }
    }

    @Override
    public void validate(ShaderProgram shader) {
        if (!isValidate(shader)) {
            validations.put(shader, true);
            updateShader(shader);
        }
    }

    @Override
    public boolean isValidate(ShaderProgram shader) {
        return validations.get(shader, false);
    }

    @Override
    public String toString() {
        StringBuilder
                builder =
                new StringBuilder().append("Transition[id=").append(id).append(", name=")
                        .append(name).append(", type=").append(type).append(", random=")
                        .append(random)
                        .append(", displayCyclingProgress=").append(displayCyclingProgress)
                        .append(", pauseDuration=").append(pauseDuration)
                        .append(", transitionDuration=").append(transitionDuration);
        toString(builder);
        builder.append(
                "]").toString();

        return builder.toString();
    }

    protected abstract void toString(StringBuilder builder);
}