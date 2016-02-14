package com.gdx.wallpaper.collection.fragment.gallery;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;

import com.gdx.wallpaper.R;

public class GalleryItem extends FrameLayout implements Checkable {

    private FrameLayout selectView;
    private boolean checked;

    public GalleryItem(Context context) {
        this(context, null);
    }

    public GalleryItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GalleryItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GalleryItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context);
    }

    public void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.gallery_item, this);
        selectView = (FrameLayout) findViewById(R.id.selectView);
    }

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;

        selectView.setVisibility(checked ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void toggle() {
        setChecked(!checked);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
