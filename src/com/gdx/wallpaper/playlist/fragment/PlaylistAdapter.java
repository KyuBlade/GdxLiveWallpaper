package com.gdx.wallpaper.playlist.fragment;

import android.content.Context;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.collection.Collection;
import com.gdx.wallpaper.collection.CollectionManager;
import com.gdx.wallpaper.playlist.Playlist;
import com.gdx.wallpaper.playlist.PlaylistManager;
import com.gdx.wallpaper.setting.ui.PlaylistSwitchChangeListener;
import com.gdx.wallpaper.setting.ui.RadioButton;
import com.gdx.wallpaper.setting.ui.TwoLineListItem;

public class PlaylistAdapter extends BaseAdapter {

    static class ViewHolder {

        protected RadioButton activeButton;
        private TwoLineListItem twoLineListItem;
    }

    private final Context context;
    private final ListFragment listFragment;
    private final LayoutInflater inflater;

    private Playlist[] playlists;

    public PlaylistAdapter(Context context, ListFragment listFragment) {
        this.context = context;
        this.listFragment = listFragment;

        inflater = LayoutInflater.from(context);

        // Load all playlists
        playlists = PlaylistManager.getInstance().getAll();
    }

    @Override
    public void notifyDataSetChanged() {
        playlists = PlaylistManager.getInstance().getAll();

        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return playlists.length;
    }

    @Override
    public Playlist getItem(int position) {
        return playlists[position];
    }

    @Override
    public long getItemId(int position) {
        return playlists[position].getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.playlist_list_item, parent, false);

            holder = new ViewHolder();
            holder.activeButton = (RadioButton) convertView.findViewById(R.id.radioButton);
            RadioButton activeButton = holder.activeButton;
            activeButton.setOnCheckedChangeListener(
                    new PlaylistSwitchChangeListener(listFragment.getListView(), convertView));

            holder.twoLineListItem = (TwoLineListItem) convertView
                    .findViewById(R.id.twoLineListItem);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Playlist playlist = getItem(position);
        final RadioButton activeButton = holder.activeButton;
        activeButton.setCheckedProgrammatically(playlist.isActive());
        if (playlist.getTransitionId() == -1 || playlist.getCollectionId() == -1) {
            activeButton.setEnabled(false);
        } else {
            activeButton.setEnabled(true);
        }

        Collection collection = CollectionManager.getInstance().get(playlist.getCollectionId());
        int size = (collection != null) ?
                collection.size() : 0;

        TwoLineListItem listItem = holder.twoLineListItem;
        listItem.setText1(playlist.getName());
        listItem.setText2(context.getResources().getQuantityString(R.plurals.images_count,
                                                                   size, size));

        return convertView;
    }
}