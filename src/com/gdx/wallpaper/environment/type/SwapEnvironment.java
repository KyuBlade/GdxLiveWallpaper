package com.gdx.wallpaper.environment.type;

import android.database.Cursor;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.environment.EnvironmentType;
import com.gdx.wallpaper.setting.database.DatabaseHelper;

public class SwapEnvironment extends Environment {

    private float perspective = 0.2f;
    private float reflection = 0.4f;
    private float depth = 3;

    public SwapEnvironment() {
        super(EnvironmentType.SWAP);
    }

    public SwapEnvironment(long id, String name, int screenCount) {
        super(id, name, EnvironmentType.SWAP, screenCount);
    }

    @Override
    public void build(Cursor cursor) {
        perspective =
                cursor.getFloat(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.EnvironmentColumns.PERSPECTIVE));
        reflection =
                cursor.getFloat(
                        cursor.getColumnIndexOrThrow(DatabaseHelper.EnvironmentColumns.REFLECTION));
        depth =
                cursor.getFloat(
                        cursor.getColumnIndexOrThrow(DatabaseHelper.EnvironmentColumns.DEPTH));
    }

    @Override
    protected void updateShader(ShaderProgram shader) {
        shader.setUniformf("perspective", perspective);
        shader.setUniformf("reflection", reflection);
        shader.setUniformf("depth", depth);
    }

    public float getPerspective() {
        return perspective;
    }

    public void setPerspective(float perspective) {
        invalidate();
        this.perspective = perspective;
    }

    public float getReflection() {
        return reflection;
    }

    public void setReflection(float reflection) {
        invalidate();
        this.reflection = reflection;
    }

    public float getDepth() {
        return depth;
    }

    public void setDepth(float depth) {
        invalidate();
        this.depth = depth;
    }
}