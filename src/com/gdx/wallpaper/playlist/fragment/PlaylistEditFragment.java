package com.gdx.wallpaper.playlist.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.collection.fragment.CollectionListFragment;
import com.gdx.wallpaper.playlist.Playlist;
import com.gdx.wallpaper.playlist.PlaylistManager;
import com.gdx.wallpaper.setting.database.operation.PlaylistCollectionUpdateOperation;
import com.gdx.wallpaper.setting.database.operation.PlaylistTransitionUpdateOperation;
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.eventbus.playlist.PlaylistChangedEvent;
import com.gdx.wallpaper.setting.ui.dialog.PlaylistNameEditDialog;
import com.gdx.wallpaper.setting.ui.dialog.ScrollTypeChoiceDialog;
import com.gdx.wallpaper.transition.fragment.TransitionListFragment;
import com.squareup.otto.Subscribe;

public class PlaylistEditFragment extends ListFragment {

    public static final String TAG = "CollectionEditFragment";

    public static final String PLAYLIST_ID = "PlaylistId";

    public static final int SELECT_TRANSITION_REQUEST_CODE = 0;
    public static final int SELECT_COLLECTION_REQUEST_CODE = 1;

    private PlaylistEditAdapter adapter;
    private Playlist playlist;

    public PlaylistEditFragment() {
    }

    public static PlaylistEditFragment newInstance(long playlistId) {
        Bundle args = new Bundle();
        args.putLong(PLAYLIST_ID, playlistId);

        PlaylistEditFragment fragment = new PlaylistEditFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long playlistId = getArguments().getLong(PLAYLIST_ID, -1);
        if (playlistId != -1) {
            playlist = PlaylistManager.getInstance().get(playlistId);
        }

        adapter = new PlaylistEditAdapter(getActivity(), playlist);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, final View v, int position, long id) {
        switch (position) {
            case PlaylistEditAdapter.NAME:
                PlaylistNameEditDialog
                        .newInstance(playlist.getId(), playlist.getName()).show(
                        getFragmentManager(), PlaylistNameEditDialog.TAG);
                break;

            case PlaylistEditAdapter.TRANSITION:
                Fragment fragment = TransitionListFragment.newInstance(true);
                fragment.setTargetFragment(this, SELECT_TRANSITION_REQUEST_CODE);
                getFragmentManager().beginTransaction().hide(this)
                        .add(R.id.content_container, fragment, TransitionListFragment.TAG)
                        .addToBackStack(null).commit();

                break;

            case PlaylistEditAdapter.COLLECTION:
                fragment = CollectionListFragment.newInstance(true);
                fragment.setTargetFragment(this, SELECT_COLLECTION_REQUEST_CODE);
                getFragmentManager().beginTransaction().hide(this)
                        .add(R.id.content_container, fragment, CollectionListFragment.TAG)
                        .addToBackStack(
                                null).commit();

                break;

            case PlaylistEditAdapter.SCROLL_TYPE:
                DialogFragment
                        dialogFragment =
                        ScrollTypeChoiceDialog
                                .newInstance(playlist.getId(), playlist.getScrollType().ordinal());
                dialogFragment.show(getFragmentManager(), TAG);

                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SELECT_TRANSITION_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    getFragmentManager().popBackStack();

                    long id = data.getLongExtra(TransitionListFragment.TRANSITION_ID, -1);
                    playlist.setTransitionId(id);

                    BusProvider.getInstance().post(
                            new PlaylistChangedEvent(playlist.getId(),
                                                     new PlaylistTransitionUpdateOperation()));
                }

                break;
            case SELECT_COLLECTION_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    getFragmentManager().popBackStack();

                    long id = data.getLongExtra(CollectionListFragment.COLLECTION_ID, -1);
                    playlist.setCollectionId(id);

                    BusProvider.getInstance().post(
                            new PlaylistChangedEvent(playlist.getId(),
                                                     new PlaylistCollectionUpdateOperation()));
                }

                break;
        }
    }

    @Subscribe
    public void playlistChanged(PlaylistChangedEvent event) {
        adapter.notifyDataSetChanged();
    }
}