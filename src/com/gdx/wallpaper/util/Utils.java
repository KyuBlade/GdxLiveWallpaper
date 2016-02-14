package com.gdx.wallpaper.util;

import android.content.Context;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidLiveWallpaper;

public class Utils {

    public static String formatBytesSize(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) {
            return bytes + " B";
        }
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public static int getStatusBarHeight() {
        Context context = ((AndroidLiveWallpaper) (Gdx.app)).getContext();
        int result = 0;
        int resourceId =
                context.getResources()
                        .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
