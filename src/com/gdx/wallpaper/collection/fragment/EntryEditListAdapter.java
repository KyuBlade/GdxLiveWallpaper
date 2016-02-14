package com.gdx.wallpaper.collection.fragment;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gdx.wallpaper.R;
import com.gdx.wallpaper.collection.entry.Entry;
import com.gdx.wallpaper.image.fragment.LoadingProgressTarget;
import com.gdx.wallpaper.setting.ui.SelectionAdapter;
import com.gdx.wallpaper.setting.ui.WallpaperEditImageChangeListener;

public class EntryEditListAdapter extends
        SelectionAdapter<EntryEditListAdapter.ViewHolder> {

    private final Fragment fragment;
    private Entry[] entries;
    private WallpaperEditImageChangeListener listener;

    public EntryEditListAdapter(Fragment fragment, WallpaperEditImageChangeListener listener) {
        this.fragment = fragment;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.loadable_image, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        String imagePath = entries[position].getImagePath();
        Glide.with(fragment).load("file://" + imagePath).asBitmap()
                .format(DecodeFormat.PREFER_RGB_565).skipMemoryCache(
                true)
                .diskCacheStrategy(
                        DiskCacheStrategy.RESULT)
                .into(new LoadingProgressTarget(holder.imageView, holder.progressBar));
    }

    @Override
    public int getItemCount() {
        return (entries != null) ? entries.length : 0;
    }

    public void setEntries(Entry[] entries) {
        this.entries = entries;

        setSelected(0);
    }

    @Override
    protected void setSelected(int position) {
        if (selectedItem != position) {
            listener.onWallpaperEditImageChange(entries[position]);
        }

        super.setSelected(position);
    }

    class ViewHolder extends SelectionAdapter.ViewHolder {

        private ImageView imageView;
        private ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }
}
