package com.gdx.wallpaper.setting;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public interface ShaderValidatable {

    /**
     * Will update the shader in next frame.
     */
    void invalidate();

    /**
     * Update the shader if necessary.
     */
    void validate(ShaderProgram shader);

    /**
     * @return needs shader update or not.
     */
    boolean isValidate(ShaderProgram shader);
}