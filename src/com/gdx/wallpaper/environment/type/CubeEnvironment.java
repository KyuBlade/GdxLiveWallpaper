package com.gdx.wallpaper.environment.type;

import android.database.Cursor;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.environment.EnvironmentType;
import com.gdx.wallpaper.setting.database.DatabaseHelper;

public class CubeEnvironment extends Environment {

    private float perspective = 0.7f;
    private float unzoom = 0.3f;
    private float reflection = 0.4f;
    private float floating = 3f;

    public CubeEnvironment() {
        super(EnvironmentType.CUBE);
    }

    public CubeEnvironment(long id, String name, int screenCount) {
        super(id, name, EnvironmentType.CUBE, screenCount);
    }

    @Override
    public void build(Cursor cursor) {
        perspective =
                cursor.getFloat(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.EnvironmentColumns.PERSPECTIVE));
        unzoom =
                cursor.getFloat(
                        cursor.getColumnIndexOrThrow(DatabaseHelper.EnvironmentColumns.UNZOOM));
        reflection =
                cursor.getFloat(
                        cursor.getColumnIndexOrThrow(DatabaseHelper.EnvironmentColumns.REFLECTION));
        floating =
                cursor.getFloat(
                        cursor.getColumnIndexOrThrow(DatabaseHelper.EnvironmentColumns.FLOATING));

    }

    @Override
    protected void updateShader(ShaderProgram shader) {
        shader.setUniformf("perspective", perspective);
        shader.setUniformf("unzoom", unzoom);
        shader.setUniformf("reflection", reflection);
        shader.setUniformf("floating", floating);
    }

    public float getPerspective() {
        return perspective;
    }

    public void setPerspective(float persp) {
        invalidate();
        this.perspective = persp;
    }

    public float getUnzoom() {
        return unzoom;
    }

    public void setUnzoom(float unzoom) {
        invalidate();
        this.unzoom = unzoom;
    }

    public float getReflection() {
        return reflection;
    }

    public void setReflection(float reflection) {
        invalidate();
        this.reflection = reflection;
    }

    public float getFloating() {
        return floating;
    }

    public void setFloating(float floating) {
        invalidate();
        this.floating = floating;
    }
}