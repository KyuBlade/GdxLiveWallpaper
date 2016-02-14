package com.gdx.wallpaper.wallpaper;

import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidLiveWallpaperService;
import com.badlogic.gdx.graphics.GL20;
import com.gdx.wallpaper.playlist.Playlist;
import com.gdx.wallpaper.playlist.PlaylistManager;
import com.gdx.wallpaper.setting.database.DatabaseHelper;
import com.gdx.wallpaper.util.ImageUtil;
import com.gdx.wallpaper.wallpaper.render.WallpaperRenderer;

public class WallpaperService extends AndroidLiveWallpaperService
        implements WallpaperListener {

    private UpdateReceiver updateReceiver;

    private WallpaperRenderer renderer;

    private boolean preview;

    @Override
    public void onCreateApplication() {
        super.onCreateApplication();

        initialize(this, new WallpaperConfiguration());
    }

    @Override
    public void create() {
        ImageUtil.initGL();

        DatabaseHelper.initInstance(this);

        Playlist activePlaylist = PlaylistManager.getInstance().getActive();

        renderer = new WallpaperRenderer(activePlaylist);
        updateReceiver = new UpdateReceiver(this);
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(updateReceiver, new IntentFilter(UpdateReceiver.TAG));

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);

    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        if (renderer != null) {
            renderer.update(Gdx.graphics.getDeltaTime());
        }
    }

    @Override
    public void resize(int width, int height) {
        if (renderer != null) {
            renderer.resize(width, height);
        }
    }

    @Override
    public void pause() {
        if (renderer != null) {
            renderer.pause();
        }
    }

    @Override
    public void resume() {
        if (renderer != null) {
            renderer.resume();
        }
    }

    @Override
    public void dispose() {
        if (renderer != null) {
            renderer.dispose();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(updateReceiver);

        DatabaseHelper helper = DatabaseHelper.getInstance();
        if (helper != null) {
            helper.destroy();
        }

        if (renderer != null) {
            renderer.pause();
            renderer.dispose();
        }
    }

    public void updateWallpaperService() {
        renderer.dispose();
        renderer = null;

        Playlist playlist = PlaylistManager.getInstance().getActive();
        renderer = new WallpaperRenderer(playlist);
    }

    @Override
    public void offsetChange(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep,
                             int xPixelOffset, int yPixelOffset) {
//        Log.i("LiveWallpaper",
//              "offsetChange(xOffset:" + xOffset + " yOffset:" + yOffset +
//                      " xOffsetSteep:" + xOffsetStep + " yOffsetStep:" + yOffsetStep +
//                      " xPixelOffset:" + xPixelOffset + " yPixelOffset:" + yPixelOffset + ")");

        if (renderer != null) {
            renderer.offsetChange(xOffset, yOffset, xOffsetStep, yOffsetStep, xPixelOffset,
                                  yPixelOffset);
        }
    }

    @Override
    public void previewStateChange(boolean isPreview) {
        Log.i("LiveWallpaper", "previewStateChange(isPreview:" + isPreview + ")");
        preview = isPreview;
    }
}