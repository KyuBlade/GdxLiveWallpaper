package com.gdx.wallpaper.wallpaper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.badlogic.gdx.Gdx;

public class UpdateReceiver extends BroadcastReceiver {

    public static final String TAG = "Update";

    public static final String ACTION = "Action";

    public enum Action {
        UPDATE, PAUSE, RESUME
    }

    private WallpaperService service;
    private boolean updateQueued;

    public UpdateReceiver(WallpaperService service) {
        this.service = service;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final Action action = (Action) intent.getSerializableExtra(ACTION);
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                switch (action) {
                    case UPDATE:
                        if (!updateQueued) {
                            updateQueued = true;
                            service.updateWallpaperService();
                        }
                        break;
                    case PAUSE:
                        service.pauseWallpaperService();
                        break;
                    case RESUME:
                        service.resumeWallpaperService();
                        break;
                }

                updateQueued = false;
            }
        });
    }
}