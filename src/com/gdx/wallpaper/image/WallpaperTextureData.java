package com.gdx.wallpaper.image;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.bumptech.glide.request.RequestFutureTarget;
import com.gdx.wallpaper.util.ImageUtil;

public class WallpaperTextureData implements TextureData {

    private String filename;
    private RequestFutureTarget<String, Bitmap> target;
    private Bitmap data;
    private int width;
    private int height;
    private boolean prepared;

    public WallpaperTextureData(String filename) {
        this.filename = filename;
    }

    @Override
    public TextureDataType getType() {
        return TextureDataType.Custom;
    }

    @Override
    public boolean isPrepared() {
        return prepared;
    }

    @Override
    public void prepare() {
        if (prepared) {
            throw new GdxRuntimeException("Already prepared");
        }

        try {
            data = ImageUtil
                    .getScaledWallpaperBitmap(filename, Gdx.graphics.getWidth(),
                                              Gdx.graphics.getHeight());
        } catch (Exception e) {
            throw new GdxRuntimeException(e);
        }
        width = data.getWidth();
        height = data.getHeight();

        prepared = true;
    }

    @Override
    public Pixmap consumePixmap() {
        throw new GdxRuntimeException("This TextureData implementation does not return a Pixmap");
    }

    @Override
    public boolean disposePixmap() {
        throw new GdxRuntimeException("This TextureData implementation does not return a Pixmap");
    }

    @Override
    public void consumeCustomData(int target) {
        if (!prepared) {
            throw new GdxRuntimeException("Call prepare() before calling consumeCompressedData()");
        }

        Gdx.gl.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MAG_FILTER, GL20.GL_NEAREST);
        Gdx.gl.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MIN_FILTER, GL20.GL_NEAREST);

        Gdx.gl.glPixelStorei(GL20.GL_UNPACK_ALIGNMENT, 1);
        GLUtils.texImage2D(target, 0, data, 0);

        data.recycle();
        data = null;
        prepared = false;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Pixmap.Format getFormat() {
        return Pixmap.Format.RGB565;
    }

    @Override
    public boolean useMipMaps() {
        return true;
    }

    @Override
    public boolean isManaged() {
        return true;
    }
}
