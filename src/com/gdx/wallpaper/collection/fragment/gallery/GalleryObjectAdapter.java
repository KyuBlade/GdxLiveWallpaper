package com.gdx.wallpaper.collection.fragment.gallery;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gdx.wallpaper.collection.Collection;
import com.gdx.wallpaper.collection.entry.Entry;

public class GalleryObjectAdapter extends BaseAdapter {

    private Context context;
    private Collection collection;
    private GalleryAdapterBinder binder;

    public GalleryObjectAdapter(Context context, Collection collection) {
        this.context = context;
        this.collection = collection;

        binder = new GalleryAdapterBinder(context);
    }

    @Override
    public int getCount() {
        return collection.size();
    }

    @Override
    public Entry getItem(int position) {
        return collection.valueAt(position);
    }

    @Override
    public long getItemId(int position) {
        if (collection.size() == 0) {
            return -1;
        }

        return collection.keyAt(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = binder.newViewInternal(context, parent);
        }

        String path = "file://" + collection.valueAt(position).getImagePath();
        String[] split = path.split("/");
        String name = split[split.length - 1];

        binder.bindViewInternal(convertView, name, path);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
