package com.gdx.wallpaper.image;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import com.gdx.wallpaper.collection.entry.Entry;
import com.gdx.wallpaper.util.Utils;

public class ManagedImage {

    public enum State {
        INITIALIZED, LOADING, LOADED
    }

    private AssetDescriptor<Texture> assetDescriptor;
    private State state = State.INITIALIZED;

    private Texture texture;

    private TextureRegion portraitRegion;
    private TextureRegion landscapeRegion;

    public ManagedImage() {
    }

    protected void setup(Entry entry, Texture texture) {
        this.texture = texture;

        float portOffX = entry.getPortraitOffsetX();
        float portOffY = entry.getPortraitOffsetY();

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        boolean isLandscape = Utils.isLandscape();
        int portraitScreenWidth = (isLandscape) ? screenHeight : screenWidth;
        int portraitScreenHeight = (isLandscape) ? screenWidth : screenHeight;
        int landscapeScreenWidth = (isLandscape) ? screenWidth : screenHeight;
        int landscapeScreenHeight = (isLandscape) ? screenHeight : screenWidth;

        // Portrait Region
        int regionX = MathUtils.floor(portOffX);
        int regionY = MathUtils.floor(portOffY);

        Vector2
                scaling =
                Scaling.fit.apply(portraitScreenWidth, portraitScreenHeight, getTextureWidth(),
                                  getTextureHeight());

        int regionWidth = (int) scaling.x;
        int regionHeight = (int) scaling.y;

        regionX += (getTextureWidth() - regionWidth) * 0.5f;
        regionY += (getTextureHeight() - regionHeight) * 0.5f;
        portraitRegion = new TextureRegion(texture, regionX, regionY, regionWidth, regionHeight);
        portraitRegion.flip(false, true);

        Log.i("Region",
              "Landscape : " + isLandscape + ", region = [" + regionX + ", " + regionY + ", " +
                      regionWidth + " / " + getTextureWidth() + ", " + regionHeight + " / " +
                      getTextureHeight() + "]");

        // Landscape Region
        float landOffX = entry.getLandscapeOffsetX();
        float landOffY = entry.getLandscapeOffsetY();
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

    public int getTextureWidth() {
        return texture.getWidth();
    }

    public int getTextureHeight() {
        return texture.getHeight();
    }

    public TextureRegion getRegion() {
        return Utils.isLandscape() ? landscapeRegion : portraitRegion;
    }

    public void bind(int unit) {
        if (texture != null) {
            texture.bind(unit);
        }
    }
}