package com.gdx.wallpaper.image.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

public class LoadingProgressTarget extends BitmapImageViewTarget {

    private final ProgressBar progressBar;

    public LoadingProgressTarget(ImageView view, ProgressBar progressBar) {
        super(view);

        this.progressBar = progressBar;
    }

    @Override
    public void onLoadStarted(Drawable placeholder) {
        super.onLoadStarted(placeholder);

        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadCleared(Drawable placeholder) {
        super.onLoadCleared(placeholder);

        hideProgress();
    }

    @Override
    public void onLoadFailed(Exception e, Drawable errorDrawable) {
        super.onLoadFailed(e, errorDrawable);

        hideProgress();
    }

    @Override
    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
        super.onResourceReady(resource, glideAnimation);

        hideProgress();
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }
}