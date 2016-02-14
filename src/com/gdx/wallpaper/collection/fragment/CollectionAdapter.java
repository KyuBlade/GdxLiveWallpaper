package com.gdx.wallpaper.collection.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.collection.Collection;
import com.gdx.wallpaper.collection.CollectionManager;
import com.gdx.wallpaper.setting.ui.TwoLineListItem;

public class CollectionAdapter extends BaseAdapter {

    private final Context context;
    private final LayoutInflater inflater;

    private Collection[] collections;

    public CollectionAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);

        collections = CollectionManager.getInstance().getAll();
    }

    @Override
    public void notifyDataSetChanged() {
        collections = CollectionManager.getInstance().getAll();

        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return collections.length;
    }

    @Override
    public Collection getItem(int position) {
        return collections[position];
    }

    @Override
    public long getItemId(int position) {
        return collections[position].getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.collection_list_item, parent, false);
        }

        Collection collection = getItem(position);
        int size = collection.size();
        TwoLineListItem listItem = (TwoLineListItem) convertView;
        listItem.setText1(collection.getName());
        listItem.setText2(context.getResources().getQuantityString(R.plurals.images_count,
                                                                   size, size));

        return convertView;
    }
}