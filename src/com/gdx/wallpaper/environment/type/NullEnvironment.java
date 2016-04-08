package com.gdx.wallpaper.environment.type;

import android.database.Cursor;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.environment.EnvironmentType;

public class NullEnvironment extends Environment {

    public NullEnvironment() {
        super(EnvironmentType.NONE);
    }

    public NullEnvironment(long id, String name, int screenCount) {
        super(id, name, EnvironmentType.NONE, screenCount);
    }

    @Override
    public void build(Cursor cursor) {
    }

    @Override
    protected void updateShader(ShaderProgram shader) {

    }
}