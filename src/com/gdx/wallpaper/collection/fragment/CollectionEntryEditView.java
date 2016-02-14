package com.gdx.wallpaper.collection.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.badlogic.gdx.math.Vector2;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.gdx.wallpaper.R;
import com.gdx.wallpaper.collection.entry.Entry;
import com.gdx.wallpaper.image.fragment.CollectionEntryEditFragment;
import com.gdx.wallpaper.image.fragment.LoadingProgressTarget;
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.eventbus.collection.CollectionEntryChangedEvent;
import com.gdx.wallpaper.setting.ui.EnableableImageButton;
import com.gdx.wallpaper.setting.ui.WallpaperEditImageChangeListener;
import com.gdx.wallpaper.setting.ui.dialog.WallpaperImageEditHelpDialog;
import com.gdx.wallpaper.util.ImageUtil;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class CollectionEntryEditView extends RelativeLayout {

    private static final String SUPER_STATE = "SuperState";
    private static final String SELECTED_ITEM = "SelectedItem";

    private EnableableImageButton resetButton;
    private EnableableImageButton applyButton;
    private ProgressBar waitingProgress;
    private PhotoView imageView;
    private PhotoViewAttacher.OnMatrixChangedListener matrixChangedListener;

    private RecyclerView imageSlider;
    private EntryEditListAdapter adapter;
    private Fragment parentFragment;

    private boolean landscape;
    private Entry currentWallpaper;

    public CollectionEntryEditView(Context context) {
        this(context, null);
    }

    public CollectionEntryEditView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollectionEntryEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.wallpaper_image_edit, this);

        imageView = (PhotoView) view.findViewById(R.id.imageView);
        imageView.setZoomable(false);
        ViewTreeObserver observer = imageView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                initImageMatrix();
            }
        });

        imageSlider = (RecyclerView) view.findViewById(R.id.imageSlider);
        imageSlider.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        imageSlider.setLayoutManager(layoutManager);

        parentFragment = ((AppCompatActivity) getContext()).getSupportFragmentManager()
                .findFragmentByTag(
                        CollectionEntryEditFragment.TAG);
        adapter = new EntryEditListAdapter(parentFragment,
                                           new WallpaperEditImageChangeListener() {
                                               @Override
                                               public void onWallpaperEditImageChange(
                                                       Entry newWallpaper) {
                                                   setCurrentWallpaper(newWallpaper);
                                               }
                                           });
        imageSlider.setAdapter(adapter);

        resetButton = (EnableableImageButton) view.findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                initImageMatrix();
            }
        });
        applyButton = (EnableableImageButton) view.findViewById(R.id.applyButton);
        applyButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resetButton.setEnabled(false);
                applyButton.setEnabled(false);

                if (landscape) {
                    currentWallpaper.setLandscapeOffsetX(imageView.getOffsetX());
                    currentWallpaper.setLandscapeOffsetY(imageView.getOffsetY());
                    currentWallpaper.setLandscapeRotation(imageView.getRotation());
                    currentWallpaper.setLandscapeZoom(imageView.getScale());
                } else {
                    currentWallpaper.setPortraitOffsetX(imageView.getOffsetX());
                    currentWallpaper.setPortraitOffsetY(imageView.getOffsetY());
                    currentWallpaper.setPortraitRotation(imageView.getRotation());
                    currentWallpaper.setPortraitZoom(imageView.getScale());
                }

                BusProvider.getInstance().post(new CollectionEntryChangedEvent(currentWallpaper));
            }
        });

        ImageButton rotateLeftButton = (ImageButton) view.findViewById(R.id.rotateLeftButton);
        rotateLeftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setRotationBy(-90);
            }
        });

        ImageButton rotateRightButton = (ImageButton) view.findViewById(R.id.rotateRightButton);
        rotateRightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setRotationBy(90f);
            }
        });
        ImageButton helpButton = (ImageButton) view.findViewById(R.id.helpButton);
        helpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new WallpaperImageEditHelpDialog()
                        .show(((AppCompatActivity) getContext()).getSupportFragmentManager(), null);
            }
        });

        matrixChangedListener = new PhotoViewAttacher.OnMatrixChangedListener() {
            @Override
            public void onMatrixChanged(RectF rect) {
                resetButton.setEnabled(true);
                applyButton.setEnabled(true);
            }
        };

        waitingProgress = (ProgressBar) view.findViewById(R.id.waitingProgress);

        Vector2 screenSize = ImageUtil.getScreenSize(context);
        landscape = (screenSize.x > screenSize.y);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(SUPER_STATE, super.onSaveInstanceState());
        bundle.putInt(SELECTED_ITEM, adapter.getSelectedItem());

        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = ((Bundle) state);
            state = bundle.getParcelable(SUPER_STATE);
            int selectedItem = bundle.getInt(SELECTED_ITEM);
            adapter.setSelected(selectedItem);
        }

        super.onRestoreInstanceState(state);
    }

    private void initImageMatrix() {
        if (currentWallpaper == null) {
            return;
        }

        float scale;
        float rotation;
        int offsetX;
        int offsetY;

        if (landscape) {
            scale = currentWallpaper.getLandscapeZoom();
            rotation = currentWallpaper.getLandscapeRotation();
            offsetX = currentWallpaper.getLandscapeOffsetX();
            offsetY = currentWallpaper.getLandscapeOffsetY();
        } else {
            scale = currentWallpaper.getPortraitZoom();
            rotation = currentWallpaper.getPortraitRotation();
            offsetX = currentWallpaper.getPortraitOffsetX();
            offsetY = currentWallpaper.getPortraitOffsetY();
        }
        imageView.setRotationTo(rotation);
        imageView.setScaleTo(scale);
        imageView.setOffsetTo(offsetX, offsetY);

        resetButton.setEnabled(false);
        applyButton.setEnabled(false);
    }

    public void setWallpapersQueue(Entry[] entries) {
        adapter.setEntries(entries);
        adapter.notifyDataSetChanged();
    }

    private void setCurrentWallpaper(final Entry entry) {
        Vector2 screenSize = ImageUtil.getScreenSize(getContext());
        Glide.with(parentFragment).load("file://" + entry.getImagePath()).asBitmap()
                .override((int) screenSize.x, (int) screenSize.y).fitCenter()
                .skipMemoryCache(true).format(DecodeFormat.PREFER_RGB_565).diskCacheStrategy(
                DiskCacheStrategy.NONE).into(
                new LoadingProgressTarget(imageView, waitingProgress) {
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);

                        resetButton.setEnabled(false);
                        applyButton.setEnabled(false);
                        imageView.setOnMatrixChangeListener(null);
                    }

                    @Override
                    public void onResourceReady(Bitmap resource,
                                                GlideAnimation<? super Bitmap> glideAnimation) {
                        super.onResourceReady(resource, glideAnimation);

                        currentWallpaper = entry;
                        imageView.setOnMatrixChangeListener(matrixChangedListener);
                    }
                });
    }
}
