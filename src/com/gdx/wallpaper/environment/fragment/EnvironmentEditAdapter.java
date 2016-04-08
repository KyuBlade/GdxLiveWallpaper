package com.gdx.wallpaper.environment.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.setting.ui.TwoLineListItem;

public class EnvironmentEditAdapter extends BaseAdapter {

    public static final int NAME = 0;
    public static final int TYPE = 1;
    public static final int SCREEN_COUNT = 2;
    public static final int MANAGE = 3;

    private LayoutInflater inflater;
    private Environment environment;

    public EnvironmentEditAdapter(Context context, Environment environment) {
        this.environment = environment;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
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
                listItem.setText2(environment.getName());

                break;

            case TYPE:
                if (convertView == null) {
                    convertView =
                            inflater.inflate(R.layout.environment_edit_type_item, parent, false);
                }

                listItem = (TwoLineListItem) convertView;
                listItem.setText2(environment.getType().getNameRes());

                break;

            case SCREEN_COUNT:
                if (convertView == null) {
                    convertView =
                            inflater.inflate(R.layout.environment_edit_screen_count_item, parent,
                                             false);
                }
                break;

            case MANAGE:
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.environment_list_item, parent, false);
                }

                listItem = (TwoLineListItem) convertView;
                listItem.setText1(R.string.environment_edit_manage_title);
                listItem.setText2(R.string.environment_edit_manage_description);

                break;

            default:
                throw new IllegalStateException(
                        "This list don't handle item at position " + position);
        }

        return convertView;
    }
}