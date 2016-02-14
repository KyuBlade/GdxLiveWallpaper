package com.gdx.wallpaper.transition.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.setting.ui.TwoLineListItem;
import com.gdx.wallpaper.transition.Transition;
import com.gdx.wallpaper.transition.TransitionManager;

public class TransitionAdapter extends BaseAdapter {

    private final LayoutInflater inflater;

    private Transition[] transitions;

    public TransitionAdapter(Context context) {
        inflater = LayoutInflater.from(context);

        transitions = TransitionManager.getInstance().getAll();
    }

    @Override
    public void notifyDataSetChanged() {
        transitions = TransitionManager.getInstance().getAll();

        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return transitions.length;
    }

    @Override
    public Transition getItem(int position) {
        return transitions[position];
    }

    @Override
    public long getItemId(int position) {
        return transitions[position].getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.transition_list_item, parent, false);
        }

        Transition transition = getItem(position);
        TwoLineListItem listItem = (TwoLineListItem) convertView;
        listItem.setText1(transition.getName());

        return convertView;
    }
}