package com.gdx.wallpaper.wallpaper.environment.holder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.gdx.wallpaper.setting.ui.ProgressBarIndicator;
import com.gdx.wallpaper.util.Utils;

public class ImageSurface extends Group {

    protected final ProgressBarIndicator indicator;
    private final TextureRegion region;
    private final TextureRegionDrawable regionDrawable;
    private final WallpaperImage image;

    public ImageSurface(Skin skin) {
        region = new TextureRegion();
        regionDrawable = new TextureRegionDrawable();

        setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        debug();

        indicator = new ProgressBarIndicator(skin);
        indicator.setSize(Gdx.graphics.getWidth(), 25f);
        indicator.setPosition(0f, Gdx.graphics.getHeight() - indicator.getHeight() -
                Utils.getStatusBarHeight());
        indicator.setMaxProgress(1f);
        image = new WallpaperImage();
//        image.debug();

        addActor(image);
        addActor(indicator);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.setColor(Color.WHITE);
    }

    protected void resize(int width, int height) {
        setSize(width, height);
    }

    protected void setTexture(Texture texture) {
        region.setRegion(texture);
        region.flip(false, true);
        regionDrawable.setRegion(region);
        image.setTexture(regionDrawable);
    }
}