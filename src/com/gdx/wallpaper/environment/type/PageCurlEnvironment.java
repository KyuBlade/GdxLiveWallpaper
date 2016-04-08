package com.gdx.wallpaper.environment.type;

import android.database.Cursor;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.environment.EnvironmentType;

public class PageCurlEnvironment extends Environment {

    public PageCurlEnvironment() {
        super(EnvironmentType.PAGE_CURL);
    }

    public PageCurlEnvironment(long id, String name, int screenCount) {
        super(id, name, EnvironmentType.PAGE_CURL, screenCount);
    }

    @Override
    public void build(Cursor cursor) {
    }

    @Override
    protected void updateShader(ShaderProgram shader) {

    }
}