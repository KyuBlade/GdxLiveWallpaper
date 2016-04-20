package com.gdx.wallpaper.transition.fragment;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.setting.database.operation.transition.TransitionDisplayCyclingProgressUpdateOperation;
import com.gdx.wallpaper.setting.database.operation.transition.TransitionRandomUpdateOperation;
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.eventbus.transition.TransitionChangedEvent;
import com.gdx.wallpaper.setting.ui.CheckableListItem;
import com.gdx.wallpaper.setting.ui.TwoLineListItem;
import com.gdx.wallpaper.transition.Transition;

public class TransitionEditAdapter extends BaseAdapter {

    public static final int NAME = 0;
    public static final int TYPE = 1;
    public static final int RANDOM = 2;
    public static final int DISPLAY_PROGRESS = 3;
    public static final int MANAGE = 4;

    private FragmentManager fragmentManager;
    private LayoutInflater inflater;
    private Transition transition;

    public TransitionEditAdapter(Context context, Transition transition) {
        this.transition = transition;

        fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 5;
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
                listItem.setText2(transition.getName());
                break;

            case TYPE:
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.edit_name_item, parent, false);
                }

                listItem = (TwoLineListItem) convertView;
                listItem.setText1(R.string.type);
                listItem.setText2(transition.getType().getNameRes());
                break;

            case RANDOM:
                if (convertView == null) {
                    convertView = inflater
                            .inflate(R.layout.transition_random_list_item, parent, false);
                    final CheckableListItem randomItemView = (CheckableListItem) convertView;
                    randomItemView.setChecked(transition.isRandom());
                    randomItemView.setOnCheckedChangeListener(
                            new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView,
                                                             boolean isChecked) {
                                    transition.setRandom(isChecked);
                                    BusProvider.getInstance().post(
                                            new TransitionChangedEvent(transition.getId(),
                                                                       new TransitionRandomUpdateOperation()));
                                }
                            });
                }
                break;

            case DISPLAY_PROGRESS:
                if (convertView == null) {
                    convertView = inflater
                            .inflate(R.layout.transition_progress_list_item, parent, false);
                    final CheckableListItem displayProgressItemView = (CheckableListItem) convertView;
                    displayProgressItemView.setChecked(transition.isDisplayPauseProgress());
                    displayProgressItemView.setOnCheckedChangeListener(
                            new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView,
                                                             boolean isChecked) {
                                    transition.setDisplayCyclingProgress(isChecked);
                                    BusProvider.getInstance().post(
                                            new TransitionChangedEvent(transition.getId(),
                                                                       new TransitionDisplayCyclingProgressUpdateOperation()));
                                }
                            });
                }
                break;

            case MANAGE:
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.transition_list_item, parent, false);
                }

                listItem = (TwoLineListItem) convertView;
                listItem.setText1(R.string.transition_edit_manage_title);
                listItem.setText2(R.string.transition_edit_manage_description);
                break;

            default:
                throw new IllegalStateException(
                        "This list don't handle item at position " + position);
        }

        return convertView;
    }
}