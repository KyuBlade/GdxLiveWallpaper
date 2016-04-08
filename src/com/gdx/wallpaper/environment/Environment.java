package com.gdx.wallpaper.environment;

import android.database.Cursor;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.ObjectMap;
import com.gdx.wallpaper.setting.ShaderValidatable;

public abstract class Environment implements ShaderValidatable {

    private long id;
    private String name;
    private EnvironmentType type = EnvironmentType.NONE;
    private int screenCount = 1;
    private ObjectMap<ShaderProgram, Boolean> validations;

    public Environment(EnvironmentType type) {
        this.type = type;
        validations = new ObjectMap<>(2);
    }

    public Environment(long id, String name, EnvironmentType type, int screenCount) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.screenCount = screenCount;
        validations = new ObjectMap<>(2);
    }

    public abstract void build(Cursor cursor);

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnvironmentType getType() {
        return type;
    }

    public void setType(EnvironmentType type) {
        this.type = type;
    }

    public int getScreenCount() {
        return screenCount;
    }

    public void setScreenCount(int screenCount) {
        this.screenCount = screenCount;
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

    protected abstract void updateShader(ShaderProgram shader);
}