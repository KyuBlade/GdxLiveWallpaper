package com.gdx.wallpaper.collection.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.collection.Collection;
import com.gdx.wallpaper.setting.ui.TwoLineListItem;

public class CollectionEditAdapter extends BaseAdapter {

    public static final int NAME = 0;
    public static final int MANAGE = 1;

    private LayoutInflater inflater;
    private Collection collection;

    public CollectionEditAdapter(Context context, Collection collection) {
        this.collection = collection;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        switch (position) {
            case NAME:
                return collection.getName();

            default:
                return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        switch (position) {
            case NAME:
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.edit_name_item, parent, false);
                }

                TwoLineListItem listItem = (TwoLineListItem) convertView;
                listItem.setText2(collection.getName());
                break;

            case MANAGE:
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.collection_manage_item, parent,
                                                   false);
                }
                break;

            default:
                throw new IllegalStateException(
                        "This list doesn't handle item at position " + position);
        }

        return convertView;
    }
}