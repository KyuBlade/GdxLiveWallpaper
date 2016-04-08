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

    public static String formatTimestamp(final long timestamp) {
        long localTimestamp = timestamp;
        long msPerSec = 1000L;
        long msPerMin = msPerSec * 60L;
        long msPerHour = msPerMin * 60L;
        long msPerDay = msPerHour * 24;

        long days = localTimestamp / msPerDay;
        localTimestamp = localTimestamp - msPerDay * days;

        long hours = localTimestamp / msPerHour;
        localTimestamp = localTimestamp - msPerHour * hours;

        long minutes = localTimestamp / msPerMin;
        localTimestamp = localTimestamp - msPerMin * minutes;

        long seconds = localTimestamp / msPerSec;
        localTimestamp = localTimestamp - msPerSec * seconds;

        long milliseconds = localTimestamp;

        StringBuilder builder = new StringBuilder();
        builder.append(days).append("d ").append(hours).append("h ").append(minutes).append("m ")
                .append(seconds).append("s ").append(milliseconds).append("ms");

        return builder.toString();
    }

    public static boolean isLandscape() {
        return Gdx.graphics.getWidth() > Gdx.graphics.getHeight();
    }
}
