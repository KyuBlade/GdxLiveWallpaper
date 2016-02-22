package com.gdx.wallpaper.environment.holder;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.gdx.wallpaper.setting.ui.ProgressBarIndicator;
import com.gdx.wallpaper.util.Utils;

public class ImageSurface extends WidgetGroup {

    protected final ProgressBarIndicator indicator;
    private final WallpaperImage image;

    public ImageSurface(Skin skin) {
        indicator = new ProgressBarIndicator(skin);
        indicator.setMaxProgress(1f);
        image = new WallpaperImage();
        addActor(image);
        addActor(indicator);
    }

    @Override
    public void layout() {
        float width = getPrefWidth();
        float height = getPrefHeight();
        indicator.setSize(width, 25f);
        indicator.setPosition(0f, height - indicator.getHeight() -
                Utils.getStatusBarHeight());
    }

    @Override
    public float getPrefWidth() {
        return image.getPrefWidth();
    }

    @Override
    public float getPrefHeight() {
        return image.getPrefHeight();
    }

    protected void resize(int width, int height) {
        image.setSize(width, height);
    }

    protected void setTexture(Texture texture) {
        image.setTexture(texture);
    }
}