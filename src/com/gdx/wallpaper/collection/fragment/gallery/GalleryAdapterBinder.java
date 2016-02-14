package com.gdx.wallpaper.collection.fragment.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gdx.wallpaper.R;
import com.gdx.wallpaper.image.fragment.LoadingProgressTarget;

public class GalleryAdapterBinder {

    static class ViewHolder {

        private TextView name;
        private ImageView image;
        private ProgressBar progress;
    }

    private LayoutInflater inflater;

    public GalleryAdapterBinder(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public View newViewInternal(Context context, ViewGroup parent) {
        View newView = new GalleryItem(context);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.name = (TextView) newView.findViewById(R.id.name);
        viewHolder.image = (ImageView) newView.findViewById(R.id.imageView);
        viewHolder.progress = (ProgressBar) newView.findViewById(R.id.progress);
        newView.setTag(viewHolder);

        return newView;
    }

    public void bindViewInternal(View view, String name, String path) {
        final ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.name.setText(name);

        Glide.with(view.getContext()).load(path).asBitmap().override(300, 300).skipMemoryCache(true)
                .into(new LoadingProgressTarget(viewHolder.image, viewHolder.progress));
    }
}
