package com.gdx.wallpaper.playlist.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.collection.Collection;
import com.gdx.wallpaper.collection.CollectionManager;
import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.environment.EnvironmentManager;
import com.gdx.wallpaper.playlist.Playlist;
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.eventbus.collection.CollectionEditEvent;
import com.gdx.wallpaper.setting.eventbus.environment.EnvironmentEditEvent;
import com.gdx.wallpaper.setting.eventbus.transition.TransitionEditEvent;
import com.gdx.wallpaper.setting.ui.ManageableTwoLineListItem;
import com.gdx.wallpaper.setting.ui.TwoLineListItem;
import com.gdx.wallpaper.transition.Transition;
import com.gdx.wallpaper.transition.TransitionManager;

public class PlaylistEditAdapter extends BaseAdapter {

    public static final int NAME = 0;
    public static final int TRANSITION = 1;
    public static final int COLLECTION = 2;
    public static final int ENVIRONMENT = 3;

    private LayoutInflater inflater;
    private Playlist playlist;

    public PlaylistEditAdapter(Context context, Playlist playlist) {
        this.playlist = playlist;

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
                listItem.setText2(playlist.getName());
                break;

            case TRANSITION:
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.playlist_edit_transition_item, parent,
                                                   false);
                }

                ManageableTwoLineListItem listManageItem = (ManageableTwoLineListItem) convertView;

                final Transition transition = TransitionManager.getInstance().get(
                        playlist.getTransitionId());
                if (transition != null) {
                    listManageItem.setText2(transition.getName());
                } else {
                    listManageItem.setText2(R.string.none);
                }
                listManageItem.setManageListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (transition != null) {
                            BusProvider.getInstance().post(new TransitionEditEvent(transition));
                        }
                    }
                });
                break;

            case COLLECTION:
                if (convertView == null) {
                    convertView = inflater
                            .inflate(R.layout.playlist_edit_collection_item, parent, false);
                }

                listManageItem = (ManageableTwoLineListItem) convertView;

                final Collection collection = CollectionManager.getInstance().get(
                        playlist.getCollectionId());
                if (collection != null) {
                    listManageItem.setText2(collection.getName());
                } else {
                    listManageItem.setText2(R.string.none);
                }

                listManageItem.setManageListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (collection != null) {
                            BusProvider.getInstance()
                                    .post(new CollectionEditEvent(collection.getId()));
                        }
                    }
                });
                break;

            case ENVIRONMENT:
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.playlist_edit_environment_list_item,
                                                   parent, false);
                }

                final ManageableTwoLineListItem
                        environmentListItem =
                        (ManageableTwoLineListItem) convertView;

                final Environment
                        environment =
                        EnvironmentManager.getInstance().get(playlist.getCollectionId());
                if (environment != null) {
                    environmentListItem.setText2(environment.getName());
                } else {
                    environmentListItem.setText2(R.string.none);
                }
                environmentListItem.setManageListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (environment != null) {
                            BusProvider.getInstance()
                                    .post(new EnvironmentEditEvent(environment.getId()));
                        }
                    }
                });

                break;

            default:
                throw new IllegalStateException(
                        "This list don't handle item at position " + position);
        }

        return convertView;
    }
}