package com.gdx.wallpaper.environment.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.environment.EnvironmentManager;
import com.gdx.wallpaper.setting.ui.TwoLineListItem;

public class EnvironmentAdapter extends BaseAdapter {

    private final Context context;
    private final LayoutInflater inflater;

    private Environment[] environments;

    public EnvironmentAdapter(Context context) {
        this.context = context;

        inflater = LayoutInflater.from(context);

        // Load all environments
        environments = EnvironmentManager.getInstance().getAll();
    }

    @Override
    public void notifyDataSetChanged() {
        environments = EnvironmentManager.getInstance().getAll();

        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return environments.length;
    }

    @Override
    public Environment getItem(int position) {
        return environments[position];
    }

    @Override
    public long getItemId(int position) {
        return environments[position].getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.environment_two_line_list_item, parent, false);
        }

        Environment environment = getItem(position);

        TwoLineListItem listItem = (TwoLineListItem) convertView.findViewById(R.id.view);
        listItem.setText1(environment.getName());
        listItem.setText2(environment.getType().getNameRes());

        return convertView;
    }
}