package com.gdx.wallpaper.playlist.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.playlist.Playlist;
import com.gdx.wallpaper.setting.Pageable;
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.eventbus.playlist.PlaylistChangedEvent;
import com.gdx.wallpaper.setting.eventbus.playlist.PlaylistCreatedEvent;
import com.gdx.wallpaper.setting.eventbus.playlist.PlaylistEditEvent;
import com.gdx.wallpaper.setting.eventbus.playlist.PlaylistRemoveEvent;
import com.gdx.wallpaper.setting.eventbus.playlist.PlaylistStateChangedEvent;
import com.gdx.wallpaper.setting.ui.RadioButton;
import com.gdx.wallpaper.setting.ui.dialog.playlist.PlaylistRemoveDialog;
import com.squareup.otto.Subscribe;

public class PlaylistListFragment extends ListFragment implements Pageable {

    public static final String TAG = "PlaylistListFragment";

    private PlaylistAdapter adapter;

    public PlaylistListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new PlaylistAdapter(getActivity(), this);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListAdapter(adapter);
        setEmptyText(getString(R.string.playlist_empty));
    }

    @Override
    public void onResume() {
        super.onResume();

        registerForContextMenu(getListView());
        BusProvider.getInstance().register(this);

        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.show();
        activity.supportInvalidateOptionsMenu();
    }

    @Override
    public void onPause() {
        super.onPause();

        unregisterForContextMenu(getListView());
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.options_menu, menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.settings_floating_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.options_menu_add:
                BusProvider.getInstance().post(new PlaylistEditEvent(-1));

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onContextItemSelected(final MenuItem item) {
        if (!getUserVisibleHint()) {
            return false;
        }

        switch (item.getItemId()) {
            case R.id.floating_menu_edit:
                AdapterView.AdapterContextMenuInfo
                        listItem =
                        (AdapterView.AdapterContextMenuInfo) item
                                .getMenuInfo();
                PlaylistAdapter adapter = (PlaylistAdapter) getListAdapter();
                Playlist playlist = adapter.getItem(listItem.position);
                BusProvider.getInstance().post(new PlaylistEditEvent(playlist.getId()));

                return true;

            case R.id.floating_menu_remove:
                listItem = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                adapter = (PlaylistAdapter) getListAdapter();
                playlist = adapter.getItem(listItem.position);

                PlaylistRemoveDialog.newInstance(playlist.getId()).show(getFragmentManager(),
                                                                        PlaylistRemoveDialog.TAG);

                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public int getTitle() {
        return R.string.playlist_page_name;
    }

    @Subscribe
    public void addPlaylist(PlaylistCreatedEvent event) {
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void removePlaylist(PlaylistRemoveEvent event) {
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void changePlaylistState(PlaylistStateChangedEvent event) {
        RadioButton staticView = event.getView();
        ListView list = getListView();
        int count = list.getChildCount();
        for (int i = 0; i < count; i++) {
            View itemView = list.getChildAt(i);
            if (itemView == null) {
                continue;
            }

            PlaylistAdapter.ViewHolder holder = (PlaylistAdapter.ViewHolder) itemView.getTag();
            RadioButton activeButton = holder.activeButton;
            if (activeButton.isChecked() && !staticView.equals(activeButton)) {
                activeButton.setCheckedProgrammatically(false);
            }
        }
    }

    @Subscribe
    public void changePlaylist(PlaylistChangedEvent event) {
        adapter.notifyDataSetChanged();
    }
}