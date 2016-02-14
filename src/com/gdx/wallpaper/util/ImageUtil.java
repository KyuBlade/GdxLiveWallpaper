package com.gdx.wallpaper.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidLiveWallpaper;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.Scaling;

import java.nio.IntBuffer;
import java.util.concurrent.ExecutionException;

public class ImageUtil {

    private static final Vector2 temp = new Vector2();
    private static final Vector2 temp2 = new Vector2();
    private static final Rect temp3 = new Rect();
    private static final DisplayMetrics temp4 = new DisplayMetrics();

    private static int maxTextureSize;

    public static void initGL() {
        IntBuffer buffer = BufferUtils.newIntBuffer(1);
        Gdx.gl.glGetIntegerv(GL20.GL_MAX_TEXTURE_SIZE, buffer);
        maxTextureSize = buffer.get();
    }

    public static Vector2 getWallpaperScale(int sourceWidth, int sourceHeight, int targetWidth,
                                            int targetHeight) {
        return Scaling.fill
                .apply(sourceWidth, sourceHeight, targetWidth, targetHeight);
    }

    public static Bitmap getScaledWallpaperBitmap(String filename, int screenWidth,
                                                  int screenHeight)
            throws ExecutionException, InterruptedException {
        BitmapFactory.Options opts = getBitmapSize(filename);

        int maxSize = Math.min(maxTextureSize, Math.max(screenWidth, screenHeight));
        int sourceWidth = opts.outWidth;
        int sourceHeight = opts.outHeight;
        int targetWidth = Math.min(Math.min(sourceWidth, maxSize), screenWidth);
        int targetHeight = Math.min(Math.min(sourceHeight, maxSize), screenHeight);
        if (sourceWidth > maxSize || sourceHeight > maxSize) {
            Vector2 scaledSize = getWallpaperScale(sourceWidth, sourceHeight, targetWidth,
                                                   targetHeight);
            targetWidth = (int) scaledSize.x;
            targetHeight = (int) scaledSize.y;
        }

        opts.inSampleSize = calculateInSampleSize(opts, targetWidth, targetHeight);

        return BitmapFactory.decodeFile(filename, opts);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }

    public static BitmapFactory.Options getBitmapSize(String filename) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filename, opts);
        opts.inJustDecodeBounds = false;

        return opts;
    }

    public static Vector2 getScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(temp4);

        temp2.x = temp4.widthPixels;
        temp2.y = temp4.heightPixels;

        return temp2;
    }

    public static int getStatusBarHeight() {
        Resources res = ((AndroidLiveWallpaper) Gdx.app).getContext().getResources();
        int result = 0;
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }

        return result;
    }
}
