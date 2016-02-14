package com.gdx.wallpaper.image;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import com.gdx.wallpaper.collection.entry.Entry;

public class ManagedImage {

    public enum State {
        INITIALIZED, LOADING, LOADED
    }

    private AssetDescriptor<Texture> assetDescriptor;
    private State state = State.INITIALIZED;

    private Texture texture;
    private final Color color;

    private Vector2 initialPortraitOffsets;
    private float portraitRotation;
    private float portraitZoom;

    private Vector2 initialLandscapeOffsets;
    private float landscapeRotation;
    private float landscapeZoom;

    public ManagedImage() {
        color = Color.WHITE.cpy();
        initialPortraitOffsets = new Vector2();
        initialLandscapeOffsets = new Vector2();
    }

    protected void setup(Entry entry) {
        initialLandscapeOffsets.set(entry.getLandscapeOffsetX(), entry.getLandscapeOffsetY());
        landscapeRotation = entry.getLandscapeRotation();
        landscapeZoom = entry.getLandscapeZoom();
        initialPortraitOffsets.set(entry.getPortraitOffsetX(), entry.getPortraitOffsetY());
        portraitRotation = entry.getPortraitRotation();
        portraitZoom = entry.getPortraitZoom();
    }

    public void setAssetDescriptor(
            AssetDescriptor<Texture> assetDescriptor) {
        this.assetDescriptor = assetDescriptor;
    }

    public AssetDescriptor<Texture> getAssetDescriptor() {
        return assetDescriptor;
    }

    public boolean isLoaded() {
        return state.equals(State.LOADED);
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Color getColor() {
        return color;
    }

    public int getWidth() {
        return texture.getWidth();
    }

    public int getHeight() {
        return texture.getHeight();
    }

    public void draw(Batch batch) {
        Vector2 scaledSize =
                Scaling.fill.apply(getWidth(), getHeight(), Gdx.graphics.getWidth(),
                                   Gdx.graphics.getHeight());
        batch.draw(texture, 0f, 0f, scaledSize.x, scaledSize.y);
    }
}