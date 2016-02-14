package com.gdx.wallpaper.wallpaper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.badlogic.gdx.Gdx;

public class UpdateReceiver extends BroadcastReceiver {

    public static final String TAG = "Update";

    private WallpaperService service;
    private boolean updateQueued;

    public UpdateReceiver(WallpaperService service) {
        this.service = service;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!updateQueued) {
            updateQueued = true;

            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    service.updateWallpaperService();
                    updateQueued = false;
                }
            });
        }
    }
}
