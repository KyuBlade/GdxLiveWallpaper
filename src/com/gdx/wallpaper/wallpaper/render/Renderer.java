package com.gdx.wallpaper.wallpaper.render;

import com.badlogic.gdx.utils.Disposable;

public interface Renderer extends Disposable {

    void update(float delta);

    void pause();

    void resume();

    void resize(int width, int height);

    void dispose();

    void offsetChange(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep,
                      int xPixelOffset, int yPixelOffset);
}
